package com.lyx.tgyunxiaobot.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/10 15:13
 */
@Validated
@HttpExchange(url = "https://www.bing.com/HPImageArchive.aspx")
public interface BingDailyImageClient {

    StringBuilder baseUrl = new StringBuilder("https://www.bing.com");

    /**
     * GET /HPImageArchive.aspx : bing每日一图
     *
     * @param format 返回的数据格式，目前支持JSON和XML两种格式，取值为\&quot;js\&quot;或\&quot;xml\&quot;。例如：&#x60;format&#x3D;js&#x60; (required)
     * @param idx    指定返回的图片是今天往前第几天的图片。0表示今天，1表示昨天，以此类推。例如：&#x60;idx&#x3D;0&#x60;   (required)
     * @param mkt    指定图片的地区和语言，格式为\&quot;地区代码-语言代码\&quot;，例如\&quot;zh-CN\&quot;表示中国大陆地区的中文。不指定该参数时，默认使用en-US。例如：&#x60;mkt&#x3D;zh-CN&#x60; (required)
     * @param n      指定返回的图片数量，最多返回8张。例如：&#x60;n&#x3D;3&#x60; (required)
     * @return 成功 (status code 200)
     */
    @GetExchange
    Mono<String> getImageOnDay(@NotNull @RequestParam(value = "format") String format,
                               @NotNull @RequestParam(value = "idx") Integer idx,
                               @NotNull @RequestParam(value = "mkt") String mkt,
                               @NotNull @RequestParam(value = "n") Integer n);
}
