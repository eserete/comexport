package com.example.comexport.accountant;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
public class AccountantEntry {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Account account;

    private LocalDate entryDate;

    private BigDecimal amount;

}
