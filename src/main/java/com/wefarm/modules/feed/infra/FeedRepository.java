package com.wefarm.modules.feed.infra;


import com.wefarm.modules.feed.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>,
        FeedRepositoryExtension {

}
