package com.ebank.executer;

import com.ebank.model.entity.Transaction;
import com.ebank.model.service.TransactionService;
import com.ebank.util.TransactionStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 18.09.2018
 * @description TODO: Class Description
 */
@Slf4j
public class TransactionExecuter implements Runnable {

    private final TransactionService transactionService;

    public TransactionExecuter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void run() {
        //  DataSource.userAccounts
        try {
            Transaction transaction = transactionService.getAnyPendingTransaction();
            String message = "";
            if (transaction != null && TransactionStatus.PENDING.toString().equals(transaction.getStatus())) {
                Transaction completed = transactionService.complete(transaction);
                if (completed != null && completed.getStatus().equals(TransactionStatus.COMPLETED.toString())) {
                    message = "OKEY: " + completed.toString() + " : " + LocalDateTime.now();
                } else {
                    message = "ERROR: " + transaction.toString() + " : " + LocalDateTime.now();
                }
            } else if (transaction == null) {
                message = "NOT FIND ANY PENDING TRANSACTION AT: " + LocalDateTime.now();
            }
            Logger.getLogger(TransactionExecuter.class.getName()).log(Level.INFO, message);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
