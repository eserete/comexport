package com.example.comexport.accountant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stats {

    @JsonProperty("soma")
    private double sum;

    private double min;

    private double max;

    @JsonProperty("media")
    private double average;

    @JsonProperty("qtde")
    private int count;

}
