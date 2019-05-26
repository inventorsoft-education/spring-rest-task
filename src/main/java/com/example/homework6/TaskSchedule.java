package com.example.homework6;

import com.example.homework6.controllers.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TaskSchedule {

    @Autowired
    EmailDeliver emailDeliver;

    @Autowired
    EmailController emailController;

    @Scheduled(fixedRate = 60000)
    public void sendSchedule(){
        System.out.println("Now!");
        if(!emailController.isAllSended()){
            emailDeliver.run();
        }
    }


}
