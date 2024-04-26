package com.shiyulu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAndOwner {
    // user
    @NotEmpty
    private String account;
    @JsonIgnore
    private String password;
    private Integer userType;
    private String username;
    private String avatar;
    @Email
    private String email;
    private String phone;
    // 账号创建时间不需要

    // client表
    @NotEmpty
    private Integer clientId;
    private String clientName;
    private Integer clientType;
    private Double discountRate;
    private String businessContact;
    private String businessTele;

    // vehicle
    @NotEmpty
    private String vin; //车架号，主键
    private String license;
    private String vehicleType;
    private String vehicleColor;
    // 外键 clientId

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
