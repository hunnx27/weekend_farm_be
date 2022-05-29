package com.wefarm.modules.organization.infra;

import com.wefarm.modules.common.type.YN;
import com.wefarm.modules.organization.application.request.OrganizationSearchRequest;
import com.wefarm.modules.organization.application.request.OrganizationUpdateRequest;
import com.wefarm.modules.organization.domain.Organization;
import com.demo.modules.organization.domain.QOrganization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

public class OrganizationRepositoryExtensionImpl extends QuerydslRepositorySupport implements
    OrganizationRepositoryExtension {

    private final JPAQueryFactory jpaQueryFactory;

    public OrganizationRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Organization.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<Organization> list(OrganizationSearchRequest organizationSearchRequest) {
        QOrganization organization = QOrganization.organization;

        final Pageable pageable = organizationSearchRequest.getPageable();

        BooleanBuilder where = new BooleanBuilder();
        where.and(organization.isDelete.eq(YN.N));

        if (StringUtils.hasText(organizationSearchRequest.getCode())) {
            where.and(organization.code.eq(organizationSearchRequest.getCode()));
        }
        if (StringUtils.hasText(organizationSearchRequest.getName())) {
            where.and(organization.name.eq(organizationSearchRequest.getName()));
        }
        if (StringUtils.hasText(organizationSearchRequest.getAddress())) {
            where.and(
                organization.address.city.like("%" + organizationSearchRequest.getAddress() + "%")
                    .or(organization.address.street.like(
                        "%" + organizationSearchRequest.getAddress() + "%")));
        }

        JPQLQuery<Organization> result = from(organization)
            .where(where);

        JPQLQuery<Organization> query = getQuerydsl().applyPagination(pageable, result);
        QueryResults<Organization> fetchResults = query.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    @Override
    public void update(OrganizationUpdateRequest updateRequest) {
        QOrganization organization = QOrganization.organization;
        update(organization)
            .set(organization.name, updateRequest.getName())
            .where(organization.id.eq(updateRequest.getId()))
            .execute();
    }
}
