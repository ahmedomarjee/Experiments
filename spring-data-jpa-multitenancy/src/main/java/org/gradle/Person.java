package org.gradle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.eclipse.persistence.config.PersistenceUnitProperties;




@Entity
@Table(name = "PERSON")
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT", contextProperty = PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT)
public class Person extends AbstractAuditable{
	private static final long serialVersionUID = -8322615242768411739L;

	@Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
