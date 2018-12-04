package com.example.comexport.accountant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class AccountantEntry {

    @Id
    @JsonIgnore
    private UUID id;

    @NotNull
    @JsonProperty("contaContabil")
    private Integer accountNumber;

    @NotNull
    @JsonProperty("data")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date entryDate;

    @NotNull
    @JsonProperty("valor")
    private Double amount;

}
