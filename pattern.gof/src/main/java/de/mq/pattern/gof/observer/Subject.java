package de.mq.pattern.gof.observer;

public interface Subject {
	

	void addObserver(final Observer observer);

	void removeObserver(Observer observer);
	

}
