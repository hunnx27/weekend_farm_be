package com.wefarm.modules.feed.infra;


import com.wefarm.modules.feed.application.request.FeedSearchRequest;
import com.wefarm.modules.feed.application.request.FeedUpdateRequest;
import com.wefarm.modules.feed.domain.Feed;
import org.springframework.data.domain.PageImpl;

public interface FeedRepositoryExtension {

    PageImpl<Feed> list(FeedSearchRequest farmSearchRequest);

    void update(FeedUpdateRequest updateRequest);
}
