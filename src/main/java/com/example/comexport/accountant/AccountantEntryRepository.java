package com.example.comexport.accountant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountantEntryRepository extends JpaRepository<AccountantEntry, UUID> {
}
