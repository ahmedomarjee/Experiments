package org.gradle;

public class DummyTenantResolver implements CurrentTenantResolver<String>{

	public String getCurrentTenantId() {
		return "mytenant";
	}

}
