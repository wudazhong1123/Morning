package com.wdz;

import com.wdz.utils.PushUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WechatpushApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.wdz.WechatpushApplication.class, args);
    }


    @Scheduled(cron = "0 0 08 * * ?")
    //@Scheduled(cron = "0 56 19 * * ?")
    public void goodMorning(){
        PushUtils.push();
    }

}
