package com.wefarm.modules.feed.web;

import com.wefarm.modules.account.domain.Account;
import com.wefarm.modules.common.web.BaseApiController;
import com.wefarm.modules.feed.application.FeedService;
import com.wefarm.modules.feed.application.request.FeedCreateRequest;
import com.wefarm.modules.feed.application.request.FeedSearchRequest;
import com.wefarm.modules.feed.application.request.FeedUpdateRequest;
import com.wefarm.modules.feed.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FeedController extends BaseApiController {

    private final FeedService feedService;
    private final ModelMapper modelMapper;

    @GetMapping("/feeds")
    public Page<Feed> list(FeedSearchRequest feedSearchRequest) {
        return feedService.list(feedSearchRequest);
    }

    @PostMapping("/feeds")
    public void create(@RequestBody FeedCreateRequest farmCreateRequest) {
        feedService.create(modelMapper.map(farmCreateRequest, Feed.class));
    }

    @PatchMapping("/feeds/{id}")
    public void update(@PathVariable Long id,
        @RequestBody FeedUpdateRequest updateRequest) {
        updateRequest.setId(id);
        feedService.update(updateRequest);
    }

    @GetMapping("/feeds/{id}")
    public Feed findOne(@PathVariable Long id) {
        return feedService.findOne(id);
    }

    @PostMapping("/feeds/{id}/add")
    public ResponseEntity addFeed(@PathVariable Long id, Account account) {
        Feed one = feedService.findOne(id);
//        one.addAccount(account);
        return ResponseEntity.ok().build();
    }
}
