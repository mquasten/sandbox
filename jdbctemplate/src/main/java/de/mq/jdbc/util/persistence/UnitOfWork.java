package de.mq.jdbc.util.persistence;

import java.util.ArrayList;
import java.util.Collection;
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
	
	void add(final Key key, final Collection<Objects> entities) {
		keys.add(key);
		entities.forEach(entity -> {
			final PersistenceInfo persistenceInfo = new PersistenceInfo(entity.getClass());
			if( !this.entities.containsKey(persistenceInfo) ) {
				persistenceInfo.setInsertSql(BeanUtil.insert(entity));
				this.entities.put(persistenceInfo, new ArrayList<>());
			}
			this.entities.get(persistenceInfo).add(entity);
		});
		size++;
	}
	
	Optional<Entry<List<Key>, List<PersistenceInfo>>>  getAndClear(final int limit) {
		if( size < limit) {
			Optional.empty();
		}
		this.entities.clear();
		size=0;
		return null;
	}
	
	Optional<Entry<List<Key>, List<PersistenceInfo>>>  getAndClear() {
		if( size == 0) {
			Optional.empty();
		}
		this.entities.clear();
		size=0;
		return null;
	}

}
