package com.makati.task;


import com.makati.common.util.StaticJedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EnableScheduling
@Component
@Slf4j
public class qhTask {

    @Scheduled(cron = "0 0 6 * * ?")
    private void configureTasks() {
        StaticJedisUtils.del ("OnlineToday");

        log.info ("执行今日统计删除: " + LocalDateTime.now());
    }

//


}
