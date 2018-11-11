package yilu.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import yilu.task.service.DailyPlanGenerator;

@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    DailyPlanGenerator planGenerator;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        planGenerator.generatePlan();
        System.out.println("...init resources by implements ApplicationRunner");
    }
}

