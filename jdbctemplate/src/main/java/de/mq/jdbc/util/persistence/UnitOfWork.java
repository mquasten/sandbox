package de.mq.jdbc.util.persistence;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import de.mq.jdbc.util.BeanUtil;

public class UnitOfWork<Key> {
	
	private final Map<PersistenceInfo , List<Object>> entities = new HashMap<>();
	
	private final List<Key> keys = new ArrayList<>();
	
	private int size =0;
	
	private final int limit;
	
	public UnitOfWork(final int limit) {
		this.limit = limit;
	}

	Optional<Entry<List<Key>, List<PersistenceInfo>>> add(final Key key, final Collection<Object> entities) {
		keys.add(key);
		entities.forEach(entity -> addEntity(entity));
		size++;
		
		return getAndClear(limit);
	}

	private void addEntity(final Object entity) {
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
			return Optional.empty();
		}
		
	    this.entities.entrySet().forEach(entry -> entry.getKey().setEntities(entry.getValue()));
		
		final List<PersistenceInfo> results = new ArrayList<>();
		results.addAll(entities.keySet());
		final List<Key> keyList =  new ArrayList<>();
		keyList.addAll(keys);
		clear();
		
		Collections.sort(results , (entity1,entity2) -> (int)  Math.signum(entity1.getOrder() - entity2.getOrder())); 
		
		return Optional.of(new SimpleImmutableEntry<>( Collections.unmodifiableList(keyList), Collections.unmodifiableList(results)));
		
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
