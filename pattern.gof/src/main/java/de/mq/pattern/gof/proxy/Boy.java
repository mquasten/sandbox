package de.mq.pattern.gof.proxy;

class Boy extends Person implements Kissable {

	@Override
	public PersonType type() {
		return PersonType.Boy;
	}

}
