package de.mq.jdbc.util.persistence;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import de.mq.jdbc.util.BeanUtil;

public class UnitOfWork<Key> {
	
	private final Map<PersistenceInfo , List<Object>> entities = new HashMap<>();
	
	private final List<Key> keys = new ArrayList<>();
	
	private int size =0;
	
	Optional<Entry<List<Key>, List<PersistenceInfo>>> add(final Key key, final Collection<Objects> entities, final int limit) {
		keys.add(key);
		entities.forEach(entity -> addEntity(entity));
		size++;
		
		return getAndClear(limit);
	}

	private void addEntity(final Objects entity) {
		final PersistenceInfo persistenceInfo = new PersistenceInfo(entity.getClass());
		if( !this.entities.containsKey(persistenceInfo) ) {
			persistenceInfo.setInsertSql(BeanUtil.insert(entity));
			persistenceInfo.setOrder(entities.size());
			this.entities.put(persistenceInfo, new ArrayList<>());
		}
		this.entities.get(persistenceInfo).add(entity);
	}
	
	private Optional<Entry<List<Key>, List<PersistenceInfo>>>  getAndClear(final int limit) {
		if( size < limit) {
			Optional.empty();
		}
		
	    this.entities.entrySet().forEach(entry -> entry.getKey().setEntities(entry.getValue()));
		
		final List<PersistenceInfo> results = new ArrayList<>();
		results.addAll(entities.keySet());
		
		clear();
		
		Collections.sort(results , (entity1,entity2) -> (int)  Math.signum(entity1.getOrder() - entity2.getOrder())); 
		return Optional.of(new SimpleImmutableEntry<>(Collections.unmodifiableList(keys), Collections.unmodifiableList(results)));
		
	}

	private void clear() {
		this.entities.clear();
		keys.clear();
		size=0;
	}
	
	Optional<Entry<List<Key>, List<PersistenceInfo>>>  getAndClear() {
		return getAndClear(1);
	}

}
