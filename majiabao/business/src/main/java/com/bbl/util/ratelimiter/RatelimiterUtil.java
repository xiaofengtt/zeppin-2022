package com.bbl.util.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

public class RatelimiterUtil {

    /*
     * 使用指定的稳定吞吐量参数创建一个RateLimiter,参数为“每秒许可”(通常称为QPS，每秒查询)。
     * 2代表每秒允许释放2个许可证或每秒允许两次请求
     * 返回的RateLimiter确保在任何给定的秒内平均不超过permitsPerSecond,持续的请求在每秒钟平稳地传播。
     * 当传入请求率超过permitsPerSecond，速率限制器将每(1.0/permitsPerSecond秒)秒释放一个许可证。
     * 当速率限制器未被使用时，将允许对permitsPerSecond许可的爆发，
     * 随后的请求以稳定的permitsPerSecond的稳定速率被限制。
     */
    public static  RateLimiter rateLimiter = RateLimiter.create(100);


}
