package de.mq.pattern.gof.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicKissProxyImpl implements InvocationHandler {

	private final Kissable subject;

	public DynamicKissProxyImpl(Kissable subject) {
		this.subject = subject;
	}

	@Override
	public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (args == null) {
			return method.invoke(subject);
		}

		if (!(args[0] instanceof Kissable)) {
			return method.invoke(subject, args);
		}

		if (((Kissable) args[0]).type() == subject.type()) {
			throw new IllegalArgumentException(subject.getClass().getSimpleName().toLowerCase() + " should not kiss " + ((Kissable) args[0]).type().name().toLowerCase());
		}

		return method.invoke(subject, args);

	}

}
