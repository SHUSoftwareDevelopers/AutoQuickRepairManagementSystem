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
public class MaintenanceDispatchOrder {
    @NotEmpty
    private Integer mdoid;
    private Double workLength;
    private Double pricePerhour;
    @NotEmpty
    private Integer riid;
    @NotEmpty
    private Integer empId;
    private Integer empType;
    private Integer isComplete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
