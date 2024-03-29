package com.shiyulu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairAuthorization {
    @NotEmpty
    private Integer rai;
    @NotEmpty
    private Integer clientId;
    @NotEmpty
    private Integer vfi;
    @NotEmpty
    private Integer empId;
    private Double mileage;
    private String onboardItems;
    private String checkResult;
    private String repairMethod;
    private Integer isWashCar;
    private Integer isGetFormerComponent;
    // 该项数据在整个维修流程结束后更新
    private Double totalRepairCost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estDelivTime;
    private String currentRepairStatus;
}
