package com.wefarm.modules.farm.infra;

import com.wefarm.modules.common.type.YN;
import com.wefarm.modules.farm.application.request.FarmSearchRequest;
import com.wefarm.modules.farm.application.request.FarmUpdateRequest;
import com.wefarm.modules.farm.domain.Farm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wefarm.modules.farm.domain.QFarm;
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
        QFarm farm = QFarm.farm;

        final Pageable pageable = farmSearchRequest.getPageable();

        BooleanBuilder where = new BooleanBuilder();
        where.and(farm.isDelete.eq(YN.N));

        if (StringUtils.hasText(farmSearchRequest.getName())) {
            where.and(farm.name.eq(farmSearchRequest.getName()));
        }
        if (StringUtils.hasText(farmSearchRequest.getAddr())) {
            where.and(farm.addr.eq(farmSearchRequest.getAddr()));
        }

        JPQLQuery<Farm> result = from(farm)
            .where(where);

        JPQLQuery<Farm> query = getQuerydsl().applyPagination(pageable, result);
        QueryResults<Farm> fetchResults = query.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    @Override
    public void update(FarmUpdateRequest updateRequest) {
        QFarm farm = QFarm.farm;
        update(farm)
            .set(farm.name, updateRequest.getName())
            .set(farm.addr, updateRequest.getAddr())
            .where(farm.id.eq(updateRequest.getId()))
            .execute();
    }
}
