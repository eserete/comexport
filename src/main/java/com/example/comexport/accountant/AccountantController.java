package com.example.comexport.accountant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountantController {

    @PostMapping(value = "/lancamentos-contabeis")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save() {

    }

}
