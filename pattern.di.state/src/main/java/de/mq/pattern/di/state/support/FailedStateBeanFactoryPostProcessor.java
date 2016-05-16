package de.mq.pattern.di.state.support;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import de.mq.pattern.di.state.ArtistState;

class FailedStateBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Arrays.asList(beanFactory.getBeanNamesForType(NonFinalArtistStateImpl.class)).forEach(beanName -> beanFactory.getBean(beanName, NonFinalArtistStateImpl.class).assignFailedState(beanFactory.getBean(beanFactory.getBean(beanName, NonFinalArtistStateImpl.class).failed(),ArtistState.class)));
	}

}
