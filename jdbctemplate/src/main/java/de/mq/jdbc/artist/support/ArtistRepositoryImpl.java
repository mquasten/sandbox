package de.mq.jdbc.artist.support;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import de.mq.jdbc.util.BeanUtil;

@Repository
class ArtistRepositoryImpl {

	private final NamedParameterJdbcOperations namedParameterJdbcOperations;

	@Autowired
	ArtistRepositoryImpl(final NamedParameterJdbcOperations namedParameterJdbcOperations) {
		this.namedParameterJdbcOperations = namedParameterJdbcOperations;
	}

	public <T> void persist(final T entity) {
		final String insert = BeanUtil.insert(entity);
		final Map<String, Object> values = BeanUtil.toMap(entity);
	//	System.out.println(insert);
		
		namedParameterJdbcOperations.update(insert, values);
	}
	
	public <T> void persist(final List<T> entities) {
		final String insert = BeanUtil.insert(entities.get(0));
		final   SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entities.toArray());
		
		namedParameterJdbcOperations.batchUpdate(insert, batch);
		
	}
	
	
	public <T> void persist2(final List<T> entities) {
		final String insert = BeanUtil.insert(entities.get(0));
		
		
		
		final List<Map<String,Object>> data  = entities.stream().map(entity -> BeanUtil.toMap(entity)).collect(Collectors.toList());
		
		final   SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(data.toArray());
		
		namedParameterJdbcOperations.batchUpdate(insert, batch);
		
	}

}
