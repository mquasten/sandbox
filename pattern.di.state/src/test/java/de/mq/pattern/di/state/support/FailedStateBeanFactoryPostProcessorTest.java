package de.mq.pattern.di.state.support;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.util.ReflectionTestUtils;

import de.mq.pattern.di.state.ArtistState;
import junit.framework.Assert;

public class FailedStateBeanFactoryPostProcessorTest {
	
	private static final String BREAKED_ARTIST_STATE = "breaked";

	private static final String HOT_ARTIST_STATE = "hot";

	private final BeanFactoryPostProcessor postProcessor = new FailedStateBeanFactoryPostProcessor();
	
	private final ConfigurableListableBeanFactory configurableListableBeanFactory = Mockito.mock(ConfigurableListableBeanFactory.class);
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public final void postProcessBeanFactory() {
		final ArtistState successState = Mockito.mock(ArtistState.class);
		final ArtistState failedState = successState;
		Mockito.when(configurableListableBeanFactory.getBean(BREAKED_ARTIST_STATE, ArtistState.class)).thenReturn(failedState);
		final NonFinalArtistStateImpl hotState = new NonFinalArtistStateImpl(Mockito.mock(Checker.class), HOT_ARTIST_STATE, 10e6, successState, BREAKED_ARTIST_STATE);
		Mockito.when(configurableListableBeanFactory.getBeanNamesForType(NonFinalArtistStateImpl.class)).thenReturn(new String[] {HOT_ARTIST_STATE});
		Mockito.when(configurableListableBeanFactory.getBean(HOT_ARTIST_STATE, NonFinalArtistStateImpl.class)).thenReturn(hotState);
		postProcessor.postProcessBeanFactory(configurableListableBeanFactory);
		
	    final Optional<Map<CheckResult,ArtistState>> states =  Arrays.asList(hotState.getClass().getDeclaredFields()).stream().filter(field -> field.getType().equals(Map.class)).map(field -> (Map<CheckResult,ArtistState>)ReflectionTestUtils.getField(hotState, field.getName())).findAny();
		Assert.assertTrue(states.isPresent());
		Assert.assertEquals(3, states.get().size());
		Assert.assertEquals(failedState, states.get().get(CheckResult.Failed));
		Assert.assertEquals(hotState, states.get().get(CheckResult.Stay));
		Assert.assertEquals(successState, states.get().get(CheckResult.Success));
		
	}

}
