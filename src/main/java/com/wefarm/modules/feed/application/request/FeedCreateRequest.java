package com.wefarm.modules.feed.application.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedCreateRequest {

    private Long id;
    private String name;
    private String title;
    private String message;
    private String likeCnt;
}
