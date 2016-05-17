package de.mq.pattern.di.state.support;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import java.util.Map;
import java.util.Optional;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mq.pattern.di.state.ArtistState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/beans.xml" })
public class ArtistContextIntegrationTest {
	
	@Autowired
	private Checker checker;
	
	@Autowired
	private Collection<ArtistState> artistStates;
	
	private static String OUT = "out";
	private static String UNKOWN = "unkown";
	private static String BLESSED = "blessed";
	private static String FAMOUS = "famous";
	private static String HOT = "hot";
	private static String BREAKED = "breaked";
	private static String INMORTABLE = "inmortable";
	
	private final Collection<String> names =Arrays.asList(OUT,UNKOWN, BLESSED,FAMOUS, HOT, BREAKED,  INMORTABLE  );
	
	
	@Test
	public final void states() {
		Assert.assertNotNull(checker);
		Assert.assertEquals(7, artistStates.size());
		
		artistStates.stream().map(as -> as.name()).forEach(name -> Assert.assertTrue(names.contains(name)));
	}
	
	@Test
	public final void out() {
		final Optional<ArtistState> artistState = state(OUT);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(0d), (Double) artistState.get().amount());
		Assert.assertEquals(OUT, artistState.get().name());
		Assert.assertTrue(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof FinalArtistStateImpl);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public final void outFinalStateException() {
		final Optional<ArtistState> artistState = state(OUT);
		Assert.assertTrue(artistState.isPresent());
		artistState.get().continueLifecycle();
	}
	
	@Test
	public final void inmortable() {
		final Optional<ArtistState> artistState = state(INMORTABLE);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(0d), (Double) artistState.get().amount());
		Assert.assertEquals(INMORTABLE, artistState.get().name());
		Assert.assertTrue(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof FinalArtistStateImpl);
	}
	
	@Test(expected=IllegalStateException.class)
	public final void inmortableFinalStateException() {
		final Optional<ArtistState> artistState = state(INMORTABLE);
		Assert.assertTrue(artistState.isPresent());
		artistState.get().continueLifecycle();
	}
	
	@Test
	public final void unkown() {
		final Optional<ArtistState> artistState = state(UNKOWN);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(0d), (Double) artistState.get().amount());
		Assert.assertEquals(UNKOWN, artistState.get().name());
		Assert.assertFalse(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof NonFinalArtistStateImpl);
		
		
		final Optional<Checker> checkerDependency = dependency(artistState, Checker.class);
		Assert.assertTrue(checkerDependency.isPresent());
		Assert.assertEquals(checker, checkerDependency.get());
		@SuppressWarnings("rawtypes")
		final Optional<Map> statesDependency = dependency(artistState, Map.class); 
		Assert.assertTrue(statesDependency.isPresent());
		@SuppressWarnings("unchecked")
		final Map<CheckResult, ArtistState> states = statesDependency.get();
		Assert.assertEquals(3, states.size());
		
		Assert.assertEquals(artistState.get(), states.get(CheckResult.Stay));
		Assert.assertEquals(state(BLESSED).get(), states.get(CheckResult.Success));
		Assert.assertEquals(state(OUT).get(), states.get(CheckResult.Failed));
	}
	
	
	@Test
	public final void blessed() {
		final Optional<ArtistState> artistState = state(BLESSED);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(10e3d), (Double) artistState.get().amount());
		Assert.assertEquals(BLESSED, artistState.get().name());
		Assert.assertFalse(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof NonFinalArtistStateImpl);
		
		
		final Optional<Checker> checkerDependency = dependency(artistState, Checker.class);
		Assert.assertTrue(checkerDependency.isPresent());
		Assert.assertEquals(checker, checkerDependency.get());
		@SuppressWarnings("rawtypes")
		final Optional<Map> statesDependency = dependency(artistState, Map.class); 
		Assert.assertTrue(statesDependency.isPresent());
		@SuppressWarnings("unchecked")
		final Map<CheckResult, ArtistState> states = statesDependency.get();
		Assert.assertEquals(3, states.size());
		Assert.assertEquals(artistState.get(), states.get(CheckResult.Stay));
		Assert.assertEquals(state(HOT).get(), states.get(CheckResult.Success));
		Assert.assertEquals(state(OUT).get(), states.get(CheckResult.Failed));
	}
	
	
	@Test
	public final void hot() {
		final Optional<ArtistState> artistState = state(HOT);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(500e3d), (Double) artistState.get().amount());
		Assert.assertEquals(HOT, artistState.get().name());
		Assert.assertFalse(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof NonFinalArtistStateImpl);
		
		
		final Optional<Checker> checkerDependency = dependency(artistState, Checker.class);
		Assert.assertTrue(checkerDependency.isPresent());
		Assert.assertEquals(checker, checkerDependency.get());
		@SuppressWarnings("rawtypes")
		final Optional<Map> statesDependency = dependency(artistState, Map.class); 
		Assert.assertTrue(statesDependency.isPresent());
		@SuppressWarnings("unchecked")
		final Map<CheckResult, ArtistState> states = statesDependency.get();
		Assert.assertEquals(3, states.size());
		Assert.assertEquals(artistState.get(), states.get(CheckResult.Stay));
		Assert.assertEquals(state(FAMOUS).get(), states.get(CheckResult.Success));
		Assert.assertEquals(state(BREAKED).get(), states.get(CheckResult.Failed));
	}
	
	@Test
	public final void breaked() {
		final Optional<ArtistState> artistState = state(BREAKED);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(0d), (Double) artistState.get().amount());
		Assert.assertEquals(BREAKED, artistState.get().name());
		Assert.assertFalse(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof NonFinalArtistStateImpl);
		
		
		final Optional<Checker> checkerDependency = dependency(artistState, Checker.class);
		Assert.assertTrue(checkerDependency.isPresent());
		Assert.assertEquals(checker, checkerDependency.get());
		@SuppressWarnings("rawtypes")
		final Optional<Map> statesDependency = dependency(artistState, Map.class); 
		Assert.assertTrue(statesDependency.isPresent());
		@SuppressWarnings("unchecked")
		final Map<CheckResult, ArtistState> states = statesDependency.get();
		Assert.assertEquals(3, states.size());
		Assert.assertEquals(artistState.get(), states.get(CheckResult.Stay));
		Assert.assertEquals(state(HOT).get(), states.get(CheckResult.Success));
		Assert.assertEquals(state(OUT).get(), states.get(CheckResult.Failed));
	}
	
	@Test
	public final void famous() {
		final Optional<ArtistState> artistState = state(FAMOUS);
		Assert.assertTrue(artistState.isPresent());
		Assert.assertEquals(Double.valueOf(10e6), (Double) artistState.get().amount());
		Assert.assertEquals(FAMOUS, artistState.get().name());
		Assert.assertFalse(artistState.get().isFinal());
		Assert.assertTrue(artistState.get() instanceof NonFinalArtistStateImpl);
		
		
		final Optional<Checker> checkerDependency = dependency(artistState, Checker.class);
		Assert.assertTrue(checkerDependency.isPresent());
		Assert.assertEquals(checker, checkerDependency.get());
		@SuppressWarnings("rawtypes")
		final Optional<Map> statesDependency = dependency(artistState, Map.class); 
		Assert.assertTrue(statesDependency.isPresent());
		@SuppressWarnings("unchecked")
		final Map<CheckResult, ArtistState> states = statesDependency.get();
		Assert.assertEquals(3, states.size());
		Assert.assertEquals(artistState.get(), states.get(CheckResult.Stay));
		Assert.assertEquals(state(INMORTABLE).get(), states.get(CheckResult.Success));
		Assert.assertEquals(state(BREAKED).get(), states.get(CheckResult.Failed));
	}
	

	@SuppressWarnings("unchecked")
	private <T> Optional<T> dependency(final Optional<ArtistState> artistState, Class<T> clazz) {
		return (Optional<T>) Arrays.asList(artistState.get().getClass().getDeclaredFields()).stream().filter(field -> field.getType().equals(clazz)).map(field -> getFromField(artistState.get(), field)).findAny();
	}

	@SuppressWarnings("unchecked")
	private <T> T  getFromField(final ArtistState artistState, Field field) {
		try {
			field.setAccessible(true);
			return (T) field.get(artistState);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		} 
	}

	private Optional<ArtistState> state(final String name) {
		return artistStates.stream().filter(as -> as.name().equals(name)).findAny();
	}

}
