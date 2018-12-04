package com.example.comexport.accountant;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
        return accountantEntryRepository.save(accountantEntry);
    }

    Optional<AccountantEntry> findOne(UUID id) {
        return accountantEntryRepository.findById(id);
    }

    Stream<AccountantEntry> findByAccountNumber(Integer accountNumber) {
        return accountantEntryRepository.findAccountantEntriesByAccountNumber(accountNumber);
    }
}
