package com.example.comexport.accountant;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountantEntryService {

    private final AccountantEntryRepository accountantEntryRepository;

    private final ObjectIdGenerators.UUIDGenerator uuidGenerator;

    @Autowired
    public AccountantEntryService(ObjectIdGenerators.UUIDGenerator uuidGenerator, AccountantEntryRepository accountantEntryRepository) {
        this.uuidGenerator = uuidGenerator;
        this.accountantEntryRepository = accountantEntryRepository;
    }

    AccountantEntry save(AccountantEntry accountantEntry) {
        UUID accountId = uuidGenerator.generateId(accountantEntry);
        accountantEntry.setId(accountId);
        accountantEntryRepository.save(accountantEntry);

        AccountantEntry newAccountant = new AccountantEntry();
        newAccountant.setId(accountantEntry.getId());
        return newAccountant;
    }
}
