package com.wefarm.modules.organization.infra;


import com.wefarm.modules.organization.application.request.OrganizationSearchRequest;
import com.wefarm.modules.organization.application.request.OrganizationUpdateRequest;
import com.wefarm.modules.organization.domain.Organization;
import org.springframework.data.domain.PageImpl;

public interface OrganizationRepositoryExtension {

    PageImpl<Organization> list(OrganizationSearchRequest organizationSearchRequest);

    void update(OrganizationUpdateRequest updateRequest);
}
