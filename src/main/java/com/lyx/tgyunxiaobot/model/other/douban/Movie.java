package com.lyx.tgyunxiaobot.model.other.douban;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lyx
 * @createTime 2023/6/19 0:20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String name;
    private String coverUrl;
    private String rating;
    private String movieUrl;
}
