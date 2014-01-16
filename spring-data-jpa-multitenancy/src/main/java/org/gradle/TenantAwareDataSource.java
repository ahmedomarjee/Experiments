package org.gradle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.gradle.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.AbstractDataSource;
 
public class TenantAwareDataSource extends AbstractDataSource {

	private String driverClass;
	private String jdbcUrl;
	private String username;
	private String password;
	
    private TenantRepository tenantRepository;
 
    @Autowired
    private TenantContext tenantContext;
 
    // [tenant,schema] to [DataSource] mapping
    private final Map<String, DataSource> schemaToDataSource = new HashMap<String, DataSource>();
 
    public TenantRepository getTenantRepository() {
		return tenantRepository;
	}

	public void setTenantRepository(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	/**
     * @return Take current tenant+schema and convert it to string token
     */
    protected String currentLookupKey() {
    	if(tenantContext.getTenant()!=null){
    		String tenantId = tenantContext.getTenant().getTenantId();
    		if (tenantContext.getSchemaId() == null)
    			return tenantId + "/<noschema>";
    		else
    			return tenantId + "/" + tenantContext.getSchemaId();
    	}else{
    		return null;
    	}
        
    }
 
    /**
     * @return DataSource for current tenant+schema
     */
    protected DataSource determineTargetDataSource() {
 
        // lookupKey represent current tenant and schema
        String lookupKey = currentLookupKey();
        DataSource dataSource = null;
        if(lookupKey != null){
        	dataSource = schemaToDataSource.get(lookupKey);
        	
        	if (dataSource == null) {
        		dataSource = createDataSourceForTenantAndSchema();
        		schemaToDataSource.put(lookupKey, dataSource);
        	}
        }
        return dataSource;
    }
 
    /**
     * Create new pooled datasource for current tenant and schema. It must be
     * additionally configured to always return connections with a search path
     * set to current schema.
     */
    private DataSource createDataSourceForTenantAndSchema() {
        // tenantContext is a session-scoped spring bean
        //String tenantId = tenantContext.getTenant().getTenantId();
        String schemaId = tenantContext.getSchemaId();
 
       // Tenant tenant = tenantRepository.findByTenantId(tenantId);
 
        // created simple pooled datasource (like Apache DBCP) based
        // on tenant database connection settings
        DataSource dataSource = createDataSource(schemaId) ;
 
      /*  String appSchema = schemaId != null ? "'" + schemaId + "'," : "";
        String searchPath = "SET search_path = " + appSchema + DbConst.PUBLIC_SCHEMA + ";";
 
        // set search path for the datasource
        // (for DBCP use BasicDataSource.setConnectionInitSqls(Collection))
        configureSearchPath(dataSource, searchPath);*/
 
        return dataSource;
    }
 
    private DataSource createDataSource(String schemaId) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClass);		
		dataSource.setUrl(jdbcUrl + schemaId);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	private void configureSearchPath(DataSource dataSource, String searchPath) {
		// TODO Auto-generated method stub
		if(dataSource instanceof BasicDataSource) {
			ArrayList<String> collection = new ArrayList<String>();
			collection.add(searchPath);
			((BasicDataSource) dataSource).setConnectionInitSqls(collection);
		}
	}

	public void destroy() {
        // dispose all the datasources
    }
 
    public Connection getConnection() throws SQLException {
        DataSource determineTargetDataSource = determineTargetDataSource();
        Connection connection = null;
        if(determineTargetDataSource != null){
        	connection = determineTargetDataSource.getConnection();
        }
        return connection;
    }

	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TenantContext getTenantContext() {
		return tenantContext;
	}

	public void setTenantContext(TenantContext tenantContext) {
		this.tenantContext = tenantContext;
	}
 
}
