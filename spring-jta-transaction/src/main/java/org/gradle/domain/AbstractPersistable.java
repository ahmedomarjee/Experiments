package org.gradle.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.proxy.HibernateProxy;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.springframework.data.domain.Persistable;

/**
 * Abstract base class for entities. Allows parameterization of id type, chooses auto-generation and implements
 * {@link #equals(Object)} and {@link #hashCode()} based on that id. "Borrowed" from spring-data-jpa.
 * 
 * @author Anil Kamath
 */
@MappedSuperclass
public abstract class AbstractPersistable implements java.io.Serializable, Persistable<Long> {

    private static final long serialVersionUID = 2535090450811888936L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    @Property(policy = PojomaticPolicy.TO_STRING)
    private Long id;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Persistable#getId()
     */
    public Long getId() {

        return id;
    }

    /**
     * Sets the id of the entity.
     * 
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {

        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    public boolean isNew() {

        return null == getId();
    }

    /**
     * Don't override. Use Pojomatic annotations instead.
     */
    @Override
    public final String toString() {
        return Pojomatic.toString(getEntity());
    }

    /*
     * Method is final because derived classes should use Pojomatic annotations.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        return Pojomatic.equals(getEntity(), obj);
    }

    /*
     * Method is final because derived classes should use Pojomatic annotations.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return Pojomatic.hashCode(getEntity());
    }

    private Object getEntity() {
        /*if (this instanceof HibernateProxy) {
            return HibernateProxy.class.cast(this).getHibernateLazyInitializer().getImplementation();
        } else {
            return this;
        }*/
    	
    	return this;

    }
}
