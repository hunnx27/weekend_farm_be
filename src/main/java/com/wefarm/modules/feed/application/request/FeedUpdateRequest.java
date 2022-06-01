package com.wefarm.modules.feed.application.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedUpdateRequest {

    private Long id;
    private String name;
    private String title;
    private String message;
    private String likeCnt;
}
