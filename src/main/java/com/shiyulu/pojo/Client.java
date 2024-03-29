package com.shiyulu.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @NotEmpty
    private Integer clientId;
    private String clientName;
    private Integer clientType;
    private Double discountRate;
    private String businessContact;
    private String businessTele;
    @NotEmpty
    private String account;
}
