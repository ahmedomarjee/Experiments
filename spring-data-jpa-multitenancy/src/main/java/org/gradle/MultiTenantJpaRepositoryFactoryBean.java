package org.gradle;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

public class MultiTenantJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {
    private CurrentTenantResolver currentTenantResolver;
 
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new MultiTenantJpaRepositoryFactory(entityManager, currentTenantResolver);
    }
 
    @Override
    public void afterPropertiesSet() {
        Assert.notNull(currentTenantResolver, "CurrentTenantResolver must not be null!");
        super.afterPropertiesSet();
    }
 
    @Autowired
    public void setCurrentTenantResolver(CurrentTenantResolver currentTenantResolver) {
        this.currentTenantResolver = currentTenantResolver;
    }
}
