package de.mq.pattern.gof.proxy;

public class GOFKissProxyServiceImpl  extends BasicPersonServiceImpl {
	
	protected Kissable proxy(final Kissable person) {
		
		return new KissProtectionProxyImpl(person);
	}
	
	
	

}
