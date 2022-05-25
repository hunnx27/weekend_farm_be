package com.demo.modules.organization.infra;


import com.demo.modules.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long>,
    OrganizationRepositoryExtension {

}
