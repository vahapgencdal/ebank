package com.ebank.executer;

import com.ebank.model.service.TransactionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 18.09.2018
 * @description TODO: Class Description
 */
@Slf4j
@Singleton
public class TransactionThreadPoolExecuterImpl implements TransactionThreadPoolExecuter {

    private TransactionService transactionService;
    private static ScheduledExecutorService scheduledPoolService = Executors.newScheduledThreadPool(1);

    @Inject
    public TransactionThreadPoolExecuterImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void start() {
        try {
            scheduledPoolService.scheduleWithFixedDelay(new TransactionExecuter(transactionService), 500, 1000, TimeUnit.MILLISECONDS);

            scheduledPoolService.scheduleWithFixedDelay(new TransactionExecuter(transactionService), 500, 1000, TimeUnit.MILLISECONDS);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void stop() {
        scheduledPoolService.shutdown();
    }

}
