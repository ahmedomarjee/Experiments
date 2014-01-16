package org.gradle.repositories;

import org.gradle.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long>{

	Tenant findByTenantId(String tenantId);
}
