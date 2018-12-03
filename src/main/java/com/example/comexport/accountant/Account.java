package com.example.comexport.accountant;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
class Account {

    @Id
    private Integer number;

    private String description;

}
