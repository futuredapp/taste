package com.thefuntasty.taste.tools.reflexion;

import com.thefuntasty.taste.mvp.MvpView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflexionUtil {

	public static <V extends MvpView> Class<V> tryGetMvpInterface(Class<?> originClass) {
		try {
			return getMvpInterface(originClass);
		} catch (Throwable t) {
			throw new IllegalArgumentException(
				"The generic type <V extends MvpView> must be the interface " +
				"and his parent must implement generic type argument of MvpView class. " +
				"Otherwise we can't determine which type of View this " +
				"presenter coordinates.", t);
		}
	}

	private static <V extends MvpView> Class<V> getMvpInterface(Class<?> originClass) {
		Class<V> viewClass = null;
		Class<?> currentClass = originClass;

		while (viewClass == null) {
			Type genericSuperType = tryGetParametrizedGenericSuperType(currentClass);
			viewClass = tryGetGenericMvpInterface(genericSuperType);
			currentClass = currentClass.getSuperclass();
		}

		return viewClass;
	}

	/**
	 * Scan inheritance tree until we find the ParametrizedType, which should be subclass of MvpView.
	 */
	private static Type tryGetParametrizedGenericSuperType(Class<?> currentClass) {
		Type genericSuperType = currentClass.getGenericSuperclass();

		while (!(genericSuperType instanceof ParameterizedType)) {
			currentClass = currentClass.getSuperclass();
			genericSuperType = currentClass.getGenericSuperclass();
		}

		return genericSuperType;
	}

	/**
	 * Scan generic params if there is some which is interface and also subclass of MvpView.
	 */
	@SuppressWarnings("unchecked")
	private static  <V extends MvpView> Class<V> tryGetGenericMvpInterface(Type genericSuperType) {
		Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();
		for (Type type : types) {
			try {
				Class<?> genericType = (Class<?>) type;
				if (genericType.isInterface() && isSubTypeOfMvpView(genericType)) {
					return (Class<V>) genericType;
				}
			} catch (ClassCastException e) {
				// Pass
			}
		}

		return null;
	}

	/**
	 * Check if class implements interface MvpView
	 */
	private static boolean isSubTypeOfMvpView(Class<?> currentClass) {
		if (currentClass.equals(MvpView.class)) {
			return true;
		}
		Class[] superInterfaces = currentClass.getInterfaces();
		for (Class superInterface : superInterfaces) {
			if (isSubTypeOfMvpView(superInterface)) {
				return true;
			}
		}
		return false;
	}

}
