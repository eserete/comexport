package com.example.comexport.accountant;

import com.example.comexport.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class AccountantController {

    private final ObjectMapper objectMapper;

    private final AccountantEntryService accountService;

    @Autowired
    public AccountantController(AccountantEntryService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/lancamentos-contabeis", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ObjectNode save(@Valid @RequestBody AccountantEntry accountantEntry) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("id", accountService.save(accountantEntry).getId().toString());
        return rootNode;
    }

    @GetMapping(value = {"/lancamentos-contabeis/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountantEntry get(@PathVariable UUID id) {
        return accountService.findOne(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = {"/lancamentos-contabeis"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Stream<AccountantEntry> get(@RequestParam(name = "contaContabil") Integer accountNumber) {
        return accountService.findByAccountNumber(accountNumber);
    }

    @GetMapping(value = {"/lancamentos-contabeis/_stats"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountantStats stats(@RequestParam(name = "contaContabil", required = false) Integer accountNumber) {
        return accountService.stats(accountNumber);
    }

}
