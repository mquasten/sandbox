package de.mq.pattern.di.state.support;

import java.util.HashMap;
import java.util.Map;

import de.mq.pattern.di.state.ArtistState;

class NonFinalArtistStateImpl extends AbstractArtistState {

	private final Map<CheckResult, ArtistState> transitions = new HashMap<CheckResult, ArtistState>();

	private final Checker checker;

	private double rate;

	private final String failed;

	NonFinalArtistStateImpl(final Checker checker, final String name, final double rate, final ArtistState success,
			final String failed) {

		super(name);
		this.rate = rate;
		this.checker = checker;
		transitions.put(CheckResult.Success, success);
		transitions.put(CheckResult.Stay, this);
		this.failed = failed;
	}

	@Override
	final boolean finalState() {
		return false;
	}
	@Override
	public final ArtistState continueLifecycle() {
		return transitions.get(transitionNotFoundGuard(checker.checkResult()));
	}

	private CheckResult transitionNotFoundGuard(final CheckResult checkResult) {
		if (!transitions.containsKey(checkResult)) {
			throw new IllegalStateException("No Transistion for " + checkResult.name() + " found: " + getClass());
		}
		return checkResult;
	}

	@Override
	final double rate() {
		return rate;
	}

	final String failed() {
		return failed;
	}

	final void assignFailedState(final ArtistState failedState) {
		transitions.put(CheckResult.Failed, failedState);
	}

}
