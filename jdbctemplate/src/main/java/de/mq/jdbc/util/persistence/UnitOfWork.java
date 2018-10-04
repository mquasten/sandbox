package de.mq.jdbc.util.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class UnitOfWork<Key> {
	
	private final Map<Class<?> , List<Object>> entities = new HashMap<>();
	
	private final List<Key> keys = new ArrayList<>();
	
	private int size =0;
	
	void add(final Key key, final Collection<Objects> entities) {
		keys.add(key);
		size++;
	}

}
