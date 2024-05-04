package com.shiyulu.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bills {
    @NotEmpty
    private Integer billId;
    @NotEmpty
    private Integer clientId;
    @NotEmpty
    private Integer vfi;
    @NotEmpty
    private Double discountRate;
    @NotEmpty
    private Integer paymentMethod;
    @NotEmpty
    private Double payment;
    @NotEmpty
    private LocalDateTime payTime;
}
