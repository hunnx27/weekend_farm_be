package com.wefarm.modules.feed.infra;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wefarm.modules.common.type.YN;
import com.wefarm.modules.feed.application.request.FeedSearchRequest;
import com.wefarm.modules.feed.application.request.FeedUpdateRequest;
import com.wefarm.modules.feed.domain.Feed;
import com.wefarm.modules.feed.domain.QFeed;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

public class FeedRepositoryExtensionImpl extends QuerydslRepositorySupport implements
        FeedRepositoryExtension {

    private final JPAQueryFactory jpaQueryFactory;

    public FeedRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Feed.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<Feed> list(FeedSearchRequest feedSearchRequest) {

        QFeed feed = QFeed.feed;
        final Pageable pageable = feedSearchRequest.getPageable();

        BooleanBuilder where = new BooleanBuilder();
        where.and(feed.isDelete.eq(YN.N));

        if (StringUtils.hasText(feedSearchRequest.getName())) {
            where.and(feed.name.eq(feedSearchRequest.getName()));
        }
        if (StringUtils.hasText(feedSearchRequest.getTitle())) {
            where.and(feed.title.eq(feedSearchRequest.getTitle()));
        }
        if (StringUtils.hasText(feedSearchRequest.getMessage())) {
            where.and(feed.message.eq(feedSearchRequest.getMessage()));
        }

        JPQLQuery<Feed> result = from(feed)
            .where(where);

        JPQLQuery<Feed> query = getQuerydsl().applyPagination(pageable, result);
        QueryResults<Feed> fetchResults = query.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    @Override
    public void update(FeedUpdateRequest updateRequest) {

        QFeed feed = QFeed.feed;
        update(feed)
            .set(feed.name, updateRequest.getName())
            .set(feed.title, updateRequest.getTitle())
            .set(feed.message, updateRequest.getMessage())
            .where(feed.id.eq(updateRequest.getId()))
            .execute();
    }
}
