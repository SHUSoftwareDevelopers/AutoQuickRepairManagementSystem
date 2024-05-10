package com.shiyulu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiidAndMdoid {
    //RepairTask Pojo Part
    @NotEmpty
    private Integer riid;
    private String repairItem;
    private String needComponent;
    private Double pricePerComponent;
    private Double totalComponentPrice;
    @NotEmpty
    private Integer rai;

    //MaintenanceDispatchOrder Pojo Part
    @NotEmpty
    private Integer mdoid;
    private Double workLength;
    private Double pricePerhour;
    @NotEmpty
    private Integer empId;
    private Integer empType;
    private Integer isComplete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer vfi;
}
