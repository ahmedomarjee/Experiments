package org.gradle;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class TenantAwareEntityManagerFactory implements EntityManagerFactory {
    private final EntityManagerFactory delegate;
    private final CurrentTenantResolver<Long> resolver;
 
    public TenantAwareEntityManagerFactory(EntityManagerFactory delegate,
                                           CurrentTenantResolver<Long> resolver) {
        this.delegate = delegate;
        this.resolver = resolver;
    }
     
    public EntityManager createEntityManager() {
        Long tenantID = resolver.getCurrentTenantId();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
        return delegate.createEntityManager(map);
    }
     
    public EntityManager createEntityManager(Map map) {
        Long tenantID = resolver.getCurrentTenantId();
        map.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, tenantID);
        return delegate.createEntityManager(map);
    }

	public EntityManager createEntityManager(
			SynchronizationType synchronizationType) {		
		return delegate.createEntityManager(synchronizationType);
	}

	public EntityManager createEntityManager(
			SynchronizationType synchronizationType, Map map) {
		return delegate.createEntityManager(synchronizationType, map);
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return delegate.getCriteriaBuilder();
	}

	public Metamodel getMetamodel() {
		return delegate.getMetamodel();
	}

	public boolean isOpen() {
		return delegate.isOpen();
	}

	public void close() {
		delegate.close();
	}

	public Map<String, Object> getProperties() {
		return delegate.getProperties();
	}

	public Cache getCache() {
		return delegate.getCache();
	}

	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return delegate.getPersistenceUnitUtil();
	}

	public void addNamedQuery(String name, Query query) {
		delegate.addNamedQuery(name, query);		
	}

	public <T> T unwrap(Class<T> cls) {
		return delegate.unwrap(cls);
	}

	public <T> void addNamedEntityGraph(String graphName,
			EntityGraph<T> entityGraph) {
		delegate.addNamedEntityGraph(graphName, entityGraph);
	}
}
