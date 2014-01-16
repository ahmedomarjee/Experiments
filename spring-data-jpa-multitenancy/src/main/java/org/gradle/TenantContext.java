package org.gradle;

import java.io.Serializable;

public class TenantContext implements Serializable {
	private static final long serialVersionUID = 5530114048367624952L;

	private Tenant tenant;
 
    private String schemaId;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}
 
}
