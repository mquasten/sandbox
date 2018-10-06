package de.mq.jdbc.util.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.util.CollectionUtils;

class PersistenceInfo {
	
	private  String insertSql; 
	
	

	private final Class<?> clazz;
	private  int order = Integer.MAX_VALUE;
	
	public int getOrder() {
		return order;
	}

	private Optional<SqlParameterSource[]> sqlParameterSource = Optional.empty(); 
	
	
	PersistenceInfo(final Class<?> clazz) {
		this.clazz=clazz;
	}
	
	
	public Optional<SqlParameterSource[]> getSqlParameterSource() {
		return sqlParameterSource;
	}

	

	public void setEntities(final Collection<?> entities) {
		if( CollectionUtils.isEmpty(entities) ) {
			sqlParameterSource=Optional.empty();
			return;
		}
		sqlParameterSource=Optional.of(SqlParameterSourceUtils.createBatch(entities.toArray()));
	}
	
	
	@Override
	public int hashCode() {
		return clazz.hashCode();
	}


	@Override
	public boolean equals(final Object obj) {
		if(! (obj instanceof PersistenceInfo)) {
			return super.equals(obj);
			
		}
		
		return ((PersistenceInfo) obj).clazz.equals(this.clazz);
	}

	public final String getInsertSql() {
		return insertSql;
	}
	
	public void setInsertSql(final String insertSql) {
		this.insertSql = insertSql;
	}


	public void setOrder(final int order) {
		this.order=order;
		
	}

}
