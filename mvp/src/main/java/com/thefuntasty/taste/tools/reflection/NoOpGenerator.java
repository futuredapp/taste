package com.thefuntasty.taste.tools.reflection;

import com.thefuntasty.taste.mvp.MvpView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static java.lang.reflect.Proxy.newProxyInstance;

public final class NoOpGenerator {

	private static InvocationHandler DEFAULT_VALUE = new DefaultValueInvocationHandler();

	private NoOpGenerator() {
		// No instances
	}

	@SuppressWarnings("unchecked")
	public static <V extends MvpView> V fromMvpInterface(Class<?> baseClass) {
		Class<V> interfaceClass = ReflectionUtils.tryGetMvpInterface(baseClass);
		Class[] interfaceClasses = new Class[] { interfaceClass };
		return (V) newProxyInstance(baseClass.getClassLoader(), interfaceClasses, DEFAULT_VALUE);
	}

	private static class DefaultValueInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return Defaults.defaultValue(method.getReturnType());
		}
	}
}
