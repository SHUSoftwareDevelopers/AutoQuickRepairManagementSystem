package com.shiyulu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class emp {
    private Integer empId;
    private String empName;
    private Integer empType;
    private String account;
}
