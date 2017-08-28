package com.thefuntasty.taste

import android.support.v7.widget.RecyclerView
import android.view.View

import butterknife.ButterKnife

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	init {
		ButterKnife.bind(this, itemView)
	}
}
