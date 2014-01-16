package org.gradle.main;

import org.gradle.config.AtomikosJtaConfiguration;
import org.gradle.config.StoreConfiguration;
import org.gradle.domain.Person;
import org.gradle.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Runner {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	public static void main(String...strings) {
		AnnotationConfigApplicationContext ctx = 
				   new AnnotationConfigApplicationContext();
		ctx.register(StoreConfiguration.class, AtomikosJtaConfiguration.class);
		ctx.refresh();
		
		Runner runner = (Runner)ctx.getBean(Runner.class);
		runner.canConstructAPersonWithAName();
		runner.canConstructAPersonWithAName_1();
	}
	
	@Transactional
    public void canConstructAPersonWithAName() {
        Person person = new Person();
        person.setName("Larry");
        person = personRepository.saveAndFlush(person);
        System.out.println(person.getId());
        //jmsTemplate.convertAndSend("example.A","anish");
           
    }
	
	@Transactional
    public void canConstructAPersonWithAName_1() {
        Person person = new Person();
        person.setName("Larry");
        person = personRepository.saveAndFlush(person);
        System.out.println(person.getId());
        //jmsTemplate.convertAndSend("some","anish");
        
    }
}
