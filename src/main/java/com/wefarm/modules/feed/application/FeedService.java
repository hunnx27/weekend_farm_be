package com.wefarm.modules.feed.application;

import com.wefarm.modules.feed.application.request.FeedSearchRequest;
import com.wefarm.modules.feed.application.request.FeedUpdateRequest;
import com.wefarm.modules.feed.domain.Feed;
import com.wefarm.modules.feed.infra.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {

    private final FeedRepository feedRepository;

    public Page<Feed> list(FeedSearchRequest feedSearchRequest) {
        return feedRepository.list(feedSearchRequest);
    }

    public void create(Feed feed) {
        feedRepository.save(feed);
    }

    public void update(FeedUpdateRequest updateRequest) {
        feedRepository.update(updateRequest);
    }

    public Feed findOne(Long id) {
        return feedRepository.findById(id)
            .orElseThrow();
    }
}
