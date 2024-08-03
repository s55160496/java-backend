package com.example.backend.Schedule;

import com.example.backend.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    private final UserService  userService;

    public UserSchedule(UserService userService) {
        this.userService = userService;
    }


    //Schedule Note
    //1 => second
    //2 => minute
    //3 => hour
    //4 => day
    //5 => month
    //6 => year
    @Scheduled(cron = "0 * * * * *",zone = "Asia/Bangkok")
    public void testEveryminute(){
       log.info("Hello");
    }

    /**
     * Everydat at 00:00
     */
    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Bangkok")
    private void testEveryMidNight(){

    }

    /**
     * Everydat at 09:00
     */
    @Scheduled(cron = "0 0 9 * * *",zone = "Asia/Bangkok")
    private void testEveryDayNineAM(){

    }

    /**
     * Everydat at 10:50
     */
    @Scheduled(cron = "0 50 10 * * *",zone = "Asia/Bangkok")
    private void testEveryDayTenAM(){
        log.info("Hello ssss");
    }

}
