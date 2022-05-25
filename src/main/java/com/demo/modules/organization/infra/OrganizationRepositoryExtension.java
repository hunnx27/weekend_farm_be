package com.demo.modules.organization.infra;


import com.demo.modules.organization.application.request.OrganizationSearchRequest;
import com.demo.modules.organization.application.request.OrganizationUpdateRequest;
import com.demo.modules.organization.domain.Organization;
import org.springframework.data.domain.PageImpl;

public interface OrganizationRepositoryExtension {

    PageImpl<Organization> list(OrganizationSearchRequest organizationSearchRequest);

    void update(OrganizationUpdateRequest updateRequest);
}
