package de.mq.pattern.gof.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLIBProxyPersonServiceImpl extends BasicPersonServiceImpl {

	@Override
	protected Kissable proxy(final Kissable person) {
		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Kissable.class);
		enhancer.setCallbackFilter(new CallbackFilter() {

			@Override
			public int accept(final Method method) {
				if (method.getParameterTypes().length != 1) {
					return 0;
				}
				return argumentIsKissable(method.getParameterTypes()[0]);
			}

		});

		enhancer.setCallbacks(new Callback[] { new MethodInterceptor() {

			@Override
			public Object intercept(final Object object, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
				return method.invoke(person, args);
			}
		}, new MethodInterceptor() {

			@Override
			public Object intercept(final Object object, final Method method, Object[] args, final MethodProxy proxy) throws Throwable {
				if (person.type() == ((Kissable) args[0]).type()) {
					throw new IllegalArgumentException(person.type().name().toLowerCase() + " should not kiss " + ((Kissable) args[0]).type().name().toLowerCase());
				}
				return method.invoke(person, args);
			}
		} });

		return (Kissable) enhancer.create();

	}

	private int argumentIsKissable(final Class<?> clazz) {
		try {
			clazz.asSubclass(Kissable.class);
			return 1;
		} catch (ClassCastException cce) {
			return 0;
		}
	}

}
