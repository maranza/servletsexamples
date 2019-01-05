package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;

public abstract class DBService {
	
	protected EntityManager entityManager;
	
	public DBService(EntityManager entityManager) {

		setEntityManager(entityManager);
	}
	public void setEntityManager(EntityManager entityManager) {
		
		this.entityManager = entityManager;
	}
}
