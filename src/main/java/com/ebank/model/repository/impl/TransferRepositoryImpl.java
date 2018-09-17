package com.ebank.model.repository.impl;

import com.ebank.model.repository.TransferRepository;
import com.ebank.model.request.InternalTransferWithAccountNoRequest;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransferRepositoryImpl extends BaseRepositoryImpl<InternalTransferWithAccountNoRequest> implements TransferRepository {

    private List<InternalTransferWithAccountNoRequest> internalTransferWithAccountNos;

    public TransferRepositoryImpl() {
        this.internalTransferWithAccountNos = new ArrayList<>();
    }

    public InternalTransferWithAccountNoRequest getById(long id) {
        return this.internalTransferWithAccountNos.parallelStream().filter(transactionType -> transactionType.getId() == id).findAny().orElse(new InternalTransferWithAccountNoRequest());
    }

    public List<InternalTransferWithAccountNoRequest> getAll() {
        return this.internalTransferWithAccountNos;
    }

    @Override
    public InternalTransferWithAccountNoRequest create(InternalTransferWithAccountNoRequest internalTransferWithAccountNo) {
        internalTransferWithAccountNo.setId(getCurrentMaxId() + 1);
        this.internalTransferWithAccountNos.add(internalTransferWithAccountNo);
        return internalTransferWithAccountNo;
    }

    @Override
    public InternalTransferWithAccountNoRequest update(InternalTransferWithAccountNoRequest internalTransferWithAccountNo) {
        return this.getById(internalTransferWithAccountNo.getId());
    }

    @Override
    public void remove(long id) {
        InternalTransferWithAccountNoRequest byId = this.getById(id);
        this.internalTransferWithAccountNos.remove(byId);
    }

    @Override
    public int getSize() {
        return this.internalTransferWithAccountNos.size();
    }

    private List<InternalTransferWithAccountNoRequest> createTransactionTypes() {
        int numberOfTransactionTypes = 10;
        for (int i = 0; i < numberOfTransactionTypes; i++) {
            InternalTransferWithAccountNoRequest internalTransferWithAccountNo = new InternalTransferWithAccountNoRequest();
            internalTransferWithAccountNo.setId(i + 1);
            this.internalTransferWithAccountNos.add(internalTransferWithAccountNo);
        }
        return this.internalTransferWithAccountNos;
    }


    private long getCurrentMaxId() {
        if (this.internalTransferWithAccountNos.isEmpty()) return 0;
        Ordering<InternalTransferWithAccountNoRequest> ordering = new Ordering<InternalTransferWithAccountNoRequest>() {
            @Override
            public int compare(InternalTransferWithAccountNoRequest left, InternalTransferWithAccountNoRequest right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.internalTransferWithAccountNos).getId();
    }
}
