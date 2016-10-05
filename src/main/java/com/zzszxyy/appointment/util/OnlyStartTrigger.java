package com.zzszxyy.appointment.util;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Component;

@Component
public class OnlyStartTrigger implements Trigger {
    
    private static final int DELAY = 1000 * 5;

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {

        Date date = triggerContext.lastCompletionTime();
        
        if (date == null) {
            return new Date(System.currentTimeMillis() + DELAY);
        }
        
        return null;
    }

}
