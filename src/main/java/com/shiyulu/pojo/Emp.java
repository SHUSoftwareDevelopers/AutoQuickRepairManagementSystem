package com.shiyulu.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    private Integer empId;
    private String empName;
    private Integer empType;
    @NotEmpty
    private String account;
}
