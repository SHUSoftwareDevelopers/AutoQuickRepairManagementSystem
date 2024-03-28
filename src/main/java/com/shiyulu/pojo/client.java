package com.shiyulu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class client {
    @NotNull
    private Integer clientId;
    private String clientName;
    private Integer clientType;
    private Double discountRate;
    private String businessContact;
    private String businessTele;
    private String account;
}
