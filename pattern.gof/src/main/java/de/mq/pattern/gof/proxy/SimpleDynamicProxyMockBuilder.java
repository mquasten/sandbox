package de.mq.pattern.gof.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class SimpleDynamicProxyMockBuilder<T> {
	
	private Class<? extends T> clazz;
	
	private Map<MethodContext, Object> results = new HashMap<MethodContext, Object>();
	
	public SimpleDynamicProxyMockBuilder<T> forClass(final Class<? extends T> clazz){
		this.clazz=clazz;
		return this;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public final T replay(final Object result){
	
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz}, new InvocationHandler() {

			@Override
			public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
				
				results.put(new MethodContext(method, args), result);
				
				return result;
			}}) ; 
			
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public final T mock() {
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz}, new InvocationHandler(){

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				final MethodContext methodContext = new MethodContext(method, args);
			
				if( ! results.containsKey(methodContext)){
					throw new IllegalStateException("Unexpected methodcall " + method + " no result specified");	
				}
				return results.get(methodContext);
			}});
	}
	
	
	class MethodContext{
		
		private Method method;
		private Object[] args;
		MethodContext(final Method method, final Object[] args){
			this.method=method;
			this.args=args;
		}
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(final Object obj) {
			if (!(obj instanceof SimpleDynamicProxyMockBuilder.MethodContext)) {
				return false;
			}
			if( ! this.method.equals(((MethodContext)obj).method) ) {
				return false;
			}
			if( this.args.length != ((MethodContext)obj).args.length){
				return false;
			}
			
			for(int i =0; i < args.length; i++){
				if( ! args[i].equals(((MethodContext)obj).args[i])){
				   return false;	
				}
			}
			return true;
			
		}
		@Override
		public int hashCode() {
			int hashCode =  method.hashCode();
			for(Object arg : args){
				hashCode+=arg.hashCode();
			}
			return hashCode;
		}
		
		
	}

}
