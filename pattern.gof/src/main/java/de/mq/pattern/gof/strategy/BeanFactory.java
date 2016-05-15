package de.mq.pattern.gof.strategy;

public interface BeanFactory {

	public abstract BeanFactory withBeans(final Class<?>... classes);

	public abstract BeanFactory withBeans(final Object... beans);

	public abstract void build();

	public abstract Object bean(Class<?> clazz);

}