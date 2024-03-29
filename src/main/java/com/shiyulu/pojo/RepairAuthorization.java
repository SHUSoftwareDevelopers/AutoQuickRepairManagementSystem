package com.shiyulu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairAuthorization {
    private Integer rai;
    private Integer clientId;
    private Integer vfi;
    private Integer empId;
    private Double mileage;
    private String onboardItems;
    private String checkResult;
    private String repairMethod;
    private Integer isWashCar;
    private Integer isGetFormerComponent;
    private Double totalRepairCost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estDelivTime;
    private String currentRepairStatus;
}
