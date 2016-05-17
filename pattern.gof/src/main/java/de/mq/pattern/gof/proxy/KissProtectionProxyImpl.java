package de.mq.pattern.gof.proxy;

class KissProtectionProxyImpl  implements Kissable{
	
	private final Kissable target; 

	KissProtectionProxyImpl(final Kissable kissable) {
		this.target = kissable;
	}

	@Override
	public final String kiss(final Kissable kissable) {
		if ( this.target.type() == kissable.type()){
			throw new IllegalArgumentException(this.target.getClass().getSimpleName().toLowerCase() + " should not kiss " + kissable.type().name().toLowerCase());
		} 
		
		return this.target.kiss(kissable);
	}

	@Override
	public final PersonType type() {
		return target.type();
	}

}
