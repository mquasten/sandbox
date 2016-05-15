package de.mq.pattern.gof.proxy;

class Girl extends Person implements Kissable {

	@Override
	public PersonType type() {
		return PersonType.Girl;
	}

}
