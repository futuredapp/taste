package com.thefuntasty.taste.mvp;

import com.thefuntasty.taste.mvp.data.presenters.invalid.SimplePresenterWithGenericClass;
import com.thefuntasty.taste.mvp.data.presenters.invalid.SimplePresenterWithoutSpecificGenericArgument;
import com.thefuntasty.taste.mvp.data.presenters.valid.inherited.InheritedSimplePresenter;
import com.thefuntasty.taste.mvp.data.presenters.valid.inherited.InheritedSimplePresenterWithGenericViewFirst;
import com.thefuntasty.taste.mvp.data.presenters.valid.inherited.InheritedSimplePresenterWithGenericViewSecond;
import com.thefuntasty.taste.mvp.data.presenters.valid.inherited.InheritedViewPresenter;
import com.thefuntasty.taste.mvp.data.presenters.valid.multiple.MultipleViewPresenter;
import com.thefuntasty.taste.mvp.data.presenters.valid.simple.SimplePresenter;
import com.thefuntasty.taste.mvp.data.presenters.valid.simple.SimplePresenterWithGenericViewFirst;
import com.thefuntasty.taste.mvp.data.presenters.valid.simple.SimplePresenterWithGenericViewSecond;
import com.thefuntasty.taste.mvp.data.presenters.valid.simple.SimplePresenterWithSingleGeneric;
import com.thefuntasty.taste.mvp.data.view.InheritedMvpView;
import com.thefuntasty.taste.mvp.data.view.MultipleMvpView;
import com.thefuntasty.taste.mvp.data.view.SimpleMvpView;
import com.thefuntasty.taste.mvp.data.view.implemented.InheritedMvpViewImpl;
import com.thefuntasty.taste.mvp.data.view.implemented.MultipleMvpViewImpl;
import com.thefuntasty.taste.mvp.data.view.implemented.SimpleMvpViewImpl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseNoOpPresenterTest {

	//region Simple presenter tests

	@Test
	public void simplePresenter_simpleMvpView() {
		SimplePresenter presenter = new SimplePresenter();
		SimpleMvpView mvpView = new SimpleMvpView() {
			@Override public void show() {}
		};

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void simplePresenter_implementedSimpleMvpView() {
		SimplePresenter presenter = new SimplePresenter();
		SimpleMvpViewImpl mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void simplePresenter_multipleMvpView() {
		SimplePresenter presenter = new SimplePresenter();
		MultipleMvpViewImpl mvpView = new MultipleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	//endregion

	//region Inherited presenter tests

	@Test
	public void inheritedPresenter_simpleMvpView() {
		InheritedSimplePresenter presenter = new InheritedSimplePresenter();
		SimpleMvpView mvpView = new SimpleMvpView() {
			@Override public void show() {}
		};

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedPresenter_implementedSimpleMvpView() {
		InheritedSimplePresenter presenter = new InheritedSimplePresenter();
		SimpleMvpViewImpl mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedPresenter_inheritedMvpView() {
		InheritedViewPresenter presenter = new InheritedViewPresenter();
		InheritedMvpView mvpView = new InheritedMvpView() {
			@Override public void showFromInherited() {}
			@Override public void show() {}
		};

		testAttachAndDetachForInheritedMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedPresenter_implementedInheritedMvpView() {
		InheritedViewPresenter presenter = new InheritedViewPresenter();
		InheritedMvpViewImpl mvpView = new InheritedMvpViewImpl();

		testAttachAndDetachForInheritedMvpView(presenter, mvpView);
	}

	//endregion

	//region Multiple view presenter tests

	@Test
	public void multipleViewPresenter_multipleMvpView() {
		MultipleViewPresenter presenter = new MultipleViewPresenter();
		MultipleMvpView mvpView = new MultipleMvpViewImpl();

		testAttachAndDetachForMultipleMvpView(presenter, mvpView);
	}

	//endregion

	//region Simple presenter with generic param first

	@Test
	public void genericPresenter_simpleMvpViewFirst() {
		SimplePresenterWithGenericViewFirst presenter = new SimplePresenterWithGenericViewFirst();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void genericPresenter_inheritedMvpViewFirst() {
		SimplePresenterWithGenericViewFirst presenter = new SimplePresenterWithGenericViewFirst();
		InheritedMvpViewImpl mvpView = new InheritedMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	//endregion

	//region Presenter with generic param first

	@Test
	public void inheritedGenericPresenter_simpleMvpViewFirst() {
		InheritedSimplePresenterWithGenericViewFirst presenter = new InheritedSimplePresenterWithGenericViewFirst();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedGenericPresenter_inheritedMvpViewFirst() {
		InheritedSimplePresenterWithGenericViewFirst presenter = new InheritedSimplePresenterWithGenericViewFirst();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedGenericPresenter_multipleMvpViewFirst() {
		InheritedSimplePresenterWithGenericViewFirst presenter = new InheritedSimplePresenterWithGenericViewFirst();
		MultipleMvpViewImpl mvpView = new MultipleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	//endregion

	//region Presenter with generic param second

	@Test
	public void simpleGenericPresenter_simpleMvpViewSecond() {
		SimplePresenterWithGenericViewSecond<String, SimpleMvpView> presenter = new SimplePresenterWithGenericViewSecond<>();
		SimpleMvpViewImpl mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedGenericPresenter_simpleMvpViewSecond() {
		InheritedSimplePresenterWithGenericViewSecond presenter = new InheritedSimplePresenterWithGenericViewSecond();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void inheritedGenericPresenter_multipleMvpViewSecond() {
		InheritedSimplePresenterWithGenericViewFirst presenter = new InheritedSimplePresenterWithGenericViewFirst();
		MultipleMvpViewImpl mvpView = new MultipleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	//endregion

	//region Simple presenter with concrete generic argument

	@Test
	public void concreteGenericPresenter_simpleMvpView() {
		SimplePresenterWithSingleGeneric<SimpleMvpView> presenter = new SimplePresenterWithSingleGeneric<>();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	@Test
	public void concreteGenericPresenter_inheritedMvpView() {
		SimplePresenterWithSingleGeneric<InheritedMvpView> presenter = new SimplePresenterWithSingleGeneric<>();
		InheritedMvpView mvpView = new InheritedMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	//endregion

	/**
	 * This test expect exception because generic argument "V" of BaseNoOpPresenter<V>
	 * is not explicitly specified.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void presenterWithoutConcreteGenericArgument_throwsException() {
		SimplePresenterWithoutSpecificGenericArgument<SimpleMvpView> presenter = new SimplePresenterWithoutSpecificGenericArgument<>();
		SimpleMvpView mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	/**
	 * This test expect exception because generic argument "V" of BaseNoOpPresenter<V>
	 * is class and not interface
	 */
	@Test(expected = IllegalArgumentException.class)
	public void presenterWithoutGenericInterface_throwsException() {
		SimplePresenterWithGenericClass presenter = new SimplePresenterWithGenericClass();
		SimpleMvpViewImpl mvpView = new SimpleMvpViewImpl();

		testAttachAndDetachForSimpleMvpView(presenter, mvpView);
	}

	private <V extends SimpleMvpView> void testAttachAndDetachForSimpleMvpView(BaseNoOpPresenter<V> presenter, V mvpView) {
		attach(presenter, mvpView);
		presenter.getView().show();

		detach(presenter, mvpView);
		presenter.getView().show();
	}

	private <V extends InheritedMvpView> void testAttachAndDetachForInheritedMvpView(BaseNoOpPresenter<V> presenter, V mvpView) {
		attach(presenter, mvpView);
		presenter.getView().show();
		presenter.getView().showFromInherited();

		detach(presenter, mvpView);
		presenter.getView().show();
		presenter.getView().showFromInherited();
	}

	private <V extends MultipleMvpView> void testAttachAndDetachForMultipleMvpView(BaseNoOpPresenter<V> presenter, V mvpView) {
		attach(presenter, mvpView);
		presenter.getView().show();
		presenter.getView().showAnother();

		detach(presenter, mvpView);
		presenter.getView().show();
		presenter.getView().showAnother();
	}

	private <V extends MvpView> void attach(BaseNoOpPresenter<V> presenter, V view) {
		try {
			presenter.getView();
			Assert.fail();
		} catch (NullPointerException e) {
			// Expected exception
		}

		presenter.attachView(view);

		BaseNoOpPresenter<V> spyPresenter = Mockito.spy(presenter);

		MvpView originalView = spyPresenter.getView();
		assertNotNull(originalView);
		assertSame(originalView, view);
		assertTrue(spyPresenter.isRealViewAttached());

		verify(spyPresenter, never()).logGetViewAfterDetachEvent();
	}

	private <V extends MvpView> void detach(BaseNoOpPresenter<V> presenter, V view) {
		presenter.detachView();

		BaseNoOpPresenter<V> spyPresenter = Mockito.spy(presenter);

		MvpView noOpView = spyPresenter.getView();
		assertNotNull(noOpView);
		assertNotSame(noOpView, view);
		assertFalse(spyPresenter.isRealViewAttached());

		verify(spyPresenter, times(1)).logGetViewAfterDetachEvent();
	}
}
