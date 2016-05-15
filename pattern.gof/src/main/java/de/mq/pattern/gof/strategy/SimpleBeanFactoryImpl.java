package de.mq.pattern.gof.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleBeanFactoryImpl implements BeanFactory {

	private Map<Class<?>, Object> managedBeans = new HashMap<Class<?>, Object>();

	/* (non-Javadoc)
	 * @see de.mq.strategy.BeanFactory#withBeans(java.lang.Class)
	 */
	public final BeanFactory withBeans(final Class<?>... classes) {
		for (final Class<?> clazz : classes) {
			addBeanToManagedBeans(clazz, createObject(clazz));
		}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see de.mq.strategy.BeanFactory#withBeans(java.lang.Object)
	 */
	public final BeanFactory withBeans(final Object... beans) {
		
		
		for (final Object bean : beans) {
			
			addBeanToManagedBeans(bean.getClass(), bean);
		}
		return this;
		
	}

	/* (non-Javadoc)
	 * @see de.mq.strategy.BeanFactory#build()
	 */
	public final void build() {
		for (final Class<?> clazz : managedBeans.keySet()) {
			inject(clazz);
		}
	}

	/* (non-Javadoc)
	 * @see de.mq.strategy.BeanFactory#bean(java.lang.Class)
	 */
	public final Object bean(Class<?> clazz) {
		if (!managedBeans.containsKey(clazz)) {
			throw new IllegalArgumentException("No bean found for type " + clazz.getName());
		}
		return managedBeans.get(clazz);
	}

	private void inject(final Class<?> clazz) {
		for (final Field field : clazz.getDeclaredFields()) {
			if (!managedBeans.containsKey(field.getType())) {
				continue;
			}
			inject(clazz, field);
		}
	}

	private void inject(final Class<?> clazz, final Field field) {
		field.setAccessible(true);

		try {
			field.set(managedBeans.get(clazz), managedBeans.get(field.getType()));

		} catch (final Exception ex) {
			throw new IllegalStateException("Unable to inject bean of type " + managedBeans.get(field.getType()) + " into class " + clazz.getName() + " field " + field.getName(), ex);
		}

	}

	

	private void addBeanToManagedBeans(final Class<?> clazz, final Object bean) {
		managedBeans.put(clazz, bean);
		for (final Class<?> interfaceClass : clazz.getInterfaces()) {	
			managedBeans.put(interfaceClass, bean);
		}
	}

	private Object createObject(final Class<?> clazz) {
		try {
			final Constructor<?> constructor = (Constructor<?>) clazz.getDeclaredConstructor(new Class[] {});
			constructor.setAccessible(true);
			return constructor.newInstance(new Object[] {});
		} catch (final Exception ex) {
			throw new IllegalStateException("Unable to create bean of type " + clazz.getName(), ex);
		}
	}

}
