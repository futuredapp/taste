package com.thefuntasty.taste.infinity;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class InfinityAdapter<T> extends RecyclerView.Adapter implements InfinityRemote {
	private static final int FOOTER = -10;
	private @InfinityConstant.Status int loadingStatus;

	private ArrayList<T> content = new ArrayList<>();
	private InfinityFiller<T> filler;
	private InfinityEventListener eventListener;

	private int limit = 10;
	private int offset = 0;
	private int visibleThreshold = 5;

	private boolean errorOccurred = false;
	private boolean footerVisible = false;
	private boolean pullToRefresh = false;

	private RecyclerView.OnScrollListener onScrollListener;
	private RecyclerView recyclerView;

	public abstract RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType);

	public abstract void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position);

	public @LayoutRes int getFooterLayout() {
		return R.layout.footer_layout;
	}

	public abstract int getContentItemViewType(int position);

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == FOOTER) {
			return onCreateFooterViewHolder(parent);
		} else {
			return onCreateContentViewHolder(parent, viewType);
		}
	}


	public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
		View footer = LayoutInflater.from(parent.getContext()).inflate(getFooterLayout(), parent, false);
		footer.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				if (errorOccurred && loadingStatus != InfinityConstant.LOADING) {
					errorOccurred = false;
					tryAgain();
				}
			}
		});

		return new FooterViewHolder(footer);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == FOOTER) {
			onBindFooterViewHolder(holder);
		} else {
			onBindContentViewHolder(holder, position);
		}
	}

	public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
		if (holder instanceof FooterViewHolder) {
			if (errorOccurred) {
				((FooterViewHolder) holder).loading.setVisibility(View.GONE);
				((FooterViewHolder) holder).tryAgain.setVisibility(View.VISIBLE);
			} else {
				((FooterViewHolder) holder).tryAgain.setVisibility(View.GONE);
				((FooterViewHolder) holder).loading.setVisibility(View.VISIBLE);
			}
			if (recyclerView != null && recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
				StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
				layoutParams.setFullSpan(true);
			}
		}
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		this.recyclerView = recyclerView;
		if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
			final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
			onScrollListener = new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
					int visibleItemCount = recyclerView.getChildCount();
					int totalItemCount = linearLayoutManager.getItemCount();

					if (!errorOccurred && loadingStatus == InfinityConstant.IDLE && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
						footerVisible = true;
						requestNext();
						recyclerView.scrollBy(0, 1);
					}
				}

			};
			recyclerView.addOnScrollListener(onScrollListener);
		} else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			onScrollListener = new RecyclerView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
					int visibleItemCount = recyclerView.getChildCount();
					int totalItemCount = staggeredGridLayoutManager.getItemCount();
					int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

					staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
					int lastVisibleItem = findMax(lastPositions);
					staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions);
					int firstVisibleItem = findMin(lastPositions);

					if (!errorOccurred && loadingStatus == InfinityConstant.IDLE && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
						footerVisible = true;
						requestNext();
						recyclerView.scrollBy(0, 1);
					}
				}
			};
			recyclerView.addOnScrollListener(onScrollListener);
		}
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		recyclerView.removeOnScrollListener(onScrollListener);
		super.onDetachedFromRecyclerView(recyclerView);
	}

	public T getItem(int position) {
		return content.get(position);
	}

	@Override
	public int getItemCount() {
		return content.size() + (footerVisible ? 1 : 0);
	}

	@Override
	public int getItemViewType(int position) {
		if (position == content.size()) {
			return FOOTER;
		} else {
			return getContentItemViewType(position);
		}
	}

	public void setFiller(@NonNull InfinityFiller<T> filler) {
		this.filler = filler;
	}

	@UiThread
	private void addDataAndResolveState(List<T> data, @InfinityConstant.Part int part) {
		if (part == InfinityConstant.FIRST) {
			content.clear();
			notifyDataSetChanged();
		}
		content.addAll(data);
		offset += data.size();

		errorOccurred = false;
		refreshFooter();
		notifyItemRangeInserted(offset - data.size(), data.size());

		if (part == InfinityConstant.FIRST && data.size() == 0) {
			loadingStatus = InfinityConstant.IDLE;
			onFirstEmpty(pullToRefresh);
		} else {
			setIdle(part);
			if (part == InfinityConstant.FIRST) {
				onFirstLoaded(pullToRefresh);
			} else {
				onNextLoaded();
			}
			if (data.size() < limit) {
				setFinished();
			}
		}
	}

	private void requestFirst(@NonNull InfinityFiller<T> filler) {
		setLoading(InfinityConstant.FIRST);
		offset = 0;
		refreshFooter();
		filler.onLoad(limit, offset, new InfinityFiller.Callback<T>() {
			@Override public void onData(List<T> list) {
				addDataAndResolveState(list, InfinityConstant.FIRST);
			}

			@Override public void onError(Object payload) {
				errorOccurred = true;
				onFirstUnavailable(payload, pullToRefresh);
				setIdle(InfinityConstant.FIRST);
				refreshFooter();
			}
		});
	}

	private void requestNext() {
		setLoading(InfinityConstant.NEXT);
		filler.onLoad(limit, offset, new InfinityFiller.Callback<T>() {
			@Override public void onData(List<T> list) {
				addDataAndResolveState(list, InfinityConstant.NEXT);
			}

			@Override public void onError(Object payload) {
				errorOccurred = true;
				onNextUnavailable(payload);
				setIdle(InfinityConstant.NEXT);
				refreshFooter();
			}
		});
	}

	private void refreshFooter() {
		if (footerVisible) {
			notifyItemChanged(content.size());
		}
	}


	private void setLoading(@InfinityConstant.Part int part) {
		loadingStatus = InfinityConstant.LOADING;
		if (part == InfinityConstant.FIRST) {
			onPreLoadFirst(pullToRefresh);
		} else {
			onPreLoadNext();
		}
	}

	private void setFinished() {
		loadingStatus = InfinityConstant.FINISHED;
		onFinished();

		footerVisible = false;
		notifyDataSetChanged();
	}

	private void setIdle(@InfinityConstant.Part int part) {
		loadingStatus = InfinityConstant.IDLE;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setEventListener(InfinityEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public void tryAgain() {
		requestNext();
	}

	public ArrayList<T> getData() {
		return content;
	}

	public void reset() {
		footerVisible = false;
		pullToRefresh = false;
		requestFirst(filler);
	}

	public void reset(boolean pullToRefresh) {
		footerVisible = false;
		this.pullToRefresh = pullToRefresh;
		requestFirst(filler);
	}

	/**
	 * Called when list started to loading of first chunk
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onPreLoadFirst(boolean pullToRefresh) {
		if (eventListener != null) {
			eventListener.onPreLoadFirst(pullToRefresh);
		}
	}

	/**
	 * Called when list started to loading content of next chunk
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onPreLoadNext() {
		if (eventListener != null) {
			eventListener.onPreLoadNext();
		}
	}

	/**
	 * Called when first chunk of data has been loaded
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onFirstLoaded(boolean pullToRefresh) {
		if (eventListener != null) {
			eventListener.onFirstLoaded(pullToRefresh);
		}
	}

	/**
	 * Called when second or next chunk of data has been loaded
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onNextLoaded() {
		if (eventListener != null) {
			eventListener.onNextLoaded();
		}
	}

	/**
	 * Called when first chunk of data is unavailable
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onFirstUnavailable(Object payload, boolean pullToRefresh) {
		if (eventListener != null) {
			eventListener.onFirstUnavailable(payload, pullToRefresh);
		}
	}

	/**
	 * Called when first chunk of data is unavailable
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onFirstEmpty(boolean pullToRefresh) {
		if (eventListener != null) {
			eventListener.onFirstEmpty(pullToRefresh);
		}
	}

	/**
	 * Called when second or next chunk of data is unavailable
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onNextUnavailable(Object payload) {
		if (eventListener != null) {
			eventListener.onNextUnavailable(payload);
		}
	}

	/**
	 * Called when whole list have been loaded
	 * If overriding, do not forget call super
	 */
	@Override
	@CallSuper
	public void onFinished() {
		if (eventListener != null) {
			eventListener.onFinished();
		}
	}

	private static class FooterViewHolder extends RecyclerView.ViewHolder {
		public View loading;
		public View tryAgain;

		public FooterViewHolder(View v) {
			super(v);
			loading = v.findViewById(R.id.loading);
			tryAgain = v.findViewById(R.id.try_again);

			if (loading == null || tryAgain == null) {
				throw new IllegalStateException("Footer view doesn't contain View with id/loading or id/tryAgain");
			}
		}
	}

	private int findMax(int[] lastPositions) {
		int max = Integer.MIN_VALUE;
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	private int findMin(int[] lastPositions) {
		int min = Integer.MAX_VALUE;
		for (int value : lastPositions) {
			if (value != RecyclerView.NO_POSITION && value < min) {
				min = value;
			}
		}
		return min;
	}
}
