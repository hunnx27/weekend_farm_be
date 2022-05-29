package com.wefarm.modules.organization.infra;


import com.wefarm.modules.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long>,
    OrganizationRepositoryExtension {

}
