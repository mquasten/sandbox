package de.mq.pattern.gof.proxy;

public interface Kissable {
	enum PersonType {
		Boy(new Boy()), 
		Girl(new Girl());

		private final Kissable person;

		private PersonType(final Kissable person) {
			this.person = person;
		}

		Kissable person() {
			return person;
		}

	}

	String kiss(final Kissable kissed);

	PersonType type();

}
