package de.mq.pattern.gof.proxy;

public abstract class Person implements Kissable {

	public final String kiss(final Kissable kissed) {
		return "I'm a " + type().name().toLowerCase() + ", I kissed the " + kissed.type().name().toLowerCase();
	}

	public abstract PersonType type();

}