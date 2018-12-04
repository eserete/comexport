package com.example.comexport.accountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountantController {

    private final AccountantEntryService accountService;

    @Autowired
    public AccountantController(AccountantEntryService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/lancamentos-contabeis", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public AccountantEntry save(@Valid @RequestBody AccountantEntry accountantEntry) {
        return accountService.save(accountantEntry);
    }


}
