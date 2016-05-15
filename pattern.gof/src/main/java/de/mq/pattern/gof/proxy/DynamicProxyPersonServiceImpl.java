package de.mq.pattern.gof.proxy;

import java.lang.reflect.Proxy;

public class DynamicProxyPersonServiceImpl extends BasicPersonServiceImpl {

	@Override
	protected Kissable proxy(final Kissable person) {
		return (Kissable) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { Kissable.class }, new DynamicKissProxyImpl(person));
	}

}
