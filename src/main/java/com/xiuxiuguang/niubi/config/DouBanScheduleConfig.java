package com.xiuxiuguang.niubi.config;

import com.xiuxiuguang.niubi.service.reptile.DouBanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: DouBanScheduleConfig
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/4/9     lixiuguang    Create the current class
 *******************************************************************************/
@Slf4j
@Configuration
@EnableScheduling
public class DouBanScheduleConfig {


    @Autowired
    private DouBanService douBanService;

    /**
     * 添加定时任务
     * [秒] [分] [小时] [日] [月] [周] [年]
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks() {
//        douBanService.douBanMovieTop250("C:\\LiXiuGuang\\Doc\\商兆科技\\Test_File\\" + System.currentTimeMillis() + ".xls");
        log.info("执行定时任务：{}", LocalDateTime.now());
    }
}