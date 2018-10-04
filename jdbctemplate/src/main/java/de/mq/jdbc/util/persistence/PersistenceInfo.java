package de.mq.jdbc.util.persistence;

class PersistenceInfo {
	
	private  String insertSql; 
	
	

	private final Class<?> clazz;
	
	
	
	
	PersistenceInfo(final Class<?> clazz) {
		this.clazz=clazz;
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

}
