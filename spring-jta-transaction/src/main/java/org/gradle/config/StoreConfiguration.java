package org.gradle.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.gradle.domain.AuditorAwareImpl;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@EnableTransactionManagement
@Configuration
@ComponentScan("org.gradle.main")
@PropertySource("classpath:app.properties")
@EnableJpaRepositories(value="org.gradle.repository")
@ImportResource("classpath:/META-INF/spring/jpa-audit-config.xml")
public class StoreConfiguration {

    // this is a reference to a specific Java configuration class for JTA 
    @Inject private AtomikosJtaConfiguration jtaConfiguration ;

    @Inject private Environment environment;
    
    @Bean
    public JmsTemplate jmsTemplate() throws Throwable{
        JmsTemplate jmsTemplate = new JmsTemplate(  jtaConfiguration.connectionFactory() );
        jmsTemplate.setDefaultDestinationName(this.environment.getProperty("jms.partnernotifications.destination"));
        return jmsTemplate;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Throwable  {
        LocalContainerEntityManagerFactoryBean entityManager = 
           new LocalContainerEntityManagerFactoryBean();        
        entityManager.setDataSource(jtaConfiguration.dataSource());
        entityManager.setPackagesToScan("org.gradle.domain");
        entityManager.setJpaDialect(new HibernateJpaDialect());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter());
        entityManager.setPersistenceXmlLocation("classpath*:META-INF/persistence.xml");
        entityManager.setPersistenceUnitName("spring-jpa");
        Properties properties = new Properties();
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");        
        jtaConfiguration.tailorProperties(properties);
        entityManager.setJpaProperties(properties);
        return entityManager;
    }
    
    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter()
    {
      HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
      jpaVendorAdapter.setShowSql(true);
      jpaVendorAdapter.setDatabase(Database.MYSQL);
      jpaVendorAdapter.setDatabasePlatform(MySQL5InnoDBDialect.class.getName());
      jpaVendorAdapter.setGenerateDdl(true);
      return jpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager()  throws Throwable {
        return new JtaTransactionManager( 
                         jtaConfiguration.userTransaction(), jtaConfiguration.jtaTransactionManager());
    }
    
}
