package io.github.wendergalan.consumer.models;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Order implements Serializable {
    private String identifier;
    private String customer;
    private BigDecimal value;
}
