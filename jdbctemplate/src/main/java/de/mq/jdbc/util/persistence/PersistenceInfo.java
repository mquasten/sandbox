package de.mq.jdbc.util.persistence;

class PersistenceInfo {
	
	private final String insertSql; 
	
	private final Class<?> clazz;
	
	
	PersistenceInfo(Class<?> clazz, final String insertSql ) {
		this.clazz=clazz;
		this.insertSql=insertSql;
	}

	

}
