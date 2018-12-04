package com.example.comexport.accountant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class AccountantEntry {

    @Id
    private UUID id;

    @NotNull
    @JsonProperty("contaContabil")
    private String accountNumber;

    @NotNull
    @JsonProperty("data")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date entryDate;

    @NotNull
    @JsonProperty("valor")
    private BigDecimal amount;

}
