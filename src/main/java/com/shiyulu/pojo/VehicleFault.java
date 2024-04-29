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
public class VehicleFault {
    @NotEmpty
    private Integer vfi;
    private String maintenanceType; //0 普通  1 加急
    private String taskClassification; //0 大修  1 中修  2 小修
    private String paymentMethod; //0 自付  1 三包  2 索赔
    @NotEmpty
    private String vin;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    Integer repairStatus;
}
