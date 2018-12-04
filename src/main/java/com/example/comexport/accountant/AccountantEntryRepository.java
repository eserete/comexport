package com.example.comexport.accountant;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface AccountantEntryRepository extends MongoRepository<AccountantEntry, UUID>, AccountantEntryRepositoryCustom {

    Stream<AccountantEntry> findAccountantEntriesByAccountNumber(Integer accountNumber);
}
