package org.gradle;

import org.gradle.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class PersonTest {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	TenantContext tenantContext;
	
	@Before
	public void setup() {
		tenantContext.setSchemaId("tenant1");
    	Tenant tenant = new Tenant();
    	tenant.setTenantId("mytenant");
    	tenantContext.setTenant(tenant);
	}
	
	
    @Test
    public void canConstructAPersonWithAName() {    	
    	testName();
    }
	

	public void testName() {    	
        Person person = new Person();
        person.setName("Larry tenant1");        
        person = personRepository.save(person);
        
        tenantContext.setSchemaId("tenant2");
    	Tenant tenant = new Tenant();
    	tenant.setTenantId("mytenant2");
    	tenantContext.setTenant(tenant);
        person = new Person();
        person.setName("Larry tenant2");        
        person = personRepository.save(person);
    }
    
    
}
