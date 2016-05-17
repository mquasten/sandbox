package de.mq.pattern.gof.proxy;

import de.mq.pattern.gof.proxy.Kissable.PersonType;

public class BasicPersonServiceImpl implements PersonService {

	protected Kissable proxy(final Kissable person) {
		return person;
	}

	@Override
	public final Kissable boy() {
		return proxy(PersonType.Boy.person());
	}

	@Override
	public final Kissable girl() {
		return proxy(PersonType.Girl.person());
	}

}
