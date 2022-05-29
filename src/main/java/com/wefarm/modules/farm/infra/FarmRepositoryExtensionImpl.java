package com.wefarm.modules.farm.infra;

import com.wefarm.modules.common.type.YN;
import com.wefarm.modules.farm.application.request.FarmSearchRequest;
import com.wefarm.modules.farm.application.request.FarmUpdateRequest;
import com.wefarm.modules.farm.domain.Farm;
import com.wefarm.modules.farm.domain.QOrganization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

public class FarmRepositoryExtensionImpl extends QuerydslRepositorySupport implements
        FarmRepositoryExtension {

    private final JPAQueryFactory jpaQueryFactory;

    public FarmRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Farm.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<Farm> list(FarmSearchRequest farmSearchRequest) {
        QOrganization organization = QOrganization.organization;

        final Pageable pageable = farmSearchRequest.getPageable();

        BooleanBuilder where = new BooleanBuilder();
        where.and(organization.isDelete.eq(YN.N));

        if (StringUtils.hasText(farmSearchRequest.getCode())) {
            where.and(organization.code.eq(farmSearchRequest.getCode()));
        }
        if (StringUtils.hasText(farmSearchRequest.getName())) {
            where.and(organization.name.eq(farmSearchRequest.getName()));
        }
        if (StringUtils.hasText(farmSearchRequest.getAddress())) {
            where.and(
                organization.address.city.like("%" + farmSearchRequest.getAddress() + "%")
                    .or(organization.address.street.like(
                        "%" + farmSearchRequest.getAddress() + "%")));
        }

        JPQLQuery<Farm> result = from(organization)
            .where(where);

        JPQLQuery<Farm> query = getQuerydsl().applyPagination(pageable, result);
        QueryResults<Farm> fetchResults = query.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    @Override
    public void update(FarmUpdateRequest updateRequest) {
        QOrganization organization = QOrganization.organization;
        update(organization)
            .set(organization.name, updateRequest.getName())
            .where(organization.id.eq(updateRequest.getId()))
            .execute();
    }
}
