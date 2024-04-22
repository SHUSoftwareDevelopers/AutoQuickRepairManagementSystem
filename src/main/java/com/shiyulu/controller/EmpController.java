package com.shiyulu.controller;

import com.github.pagehelper.Page;
import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.Result;
import com.shiyulu.service.EmpService;
import com.shiyulu.service.impl.EmpServiceImpl;
import com.shiyulu.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;

    // 查询数据表中是否有这条数据
    private boolean isExist(String account) {
        return empService.isExist(account);
    }

    // 注册账号阶段向用户表添加数据
    public void registEmp(String empname, String account, Integer empType) {
        log.info("注册方式新增员工信息。对应注册账号：{}，注册员工姓名：{}，注册员工工种：{}。",
                account, empname, empType);
        Emp emp = new Emp();
        emp.setAccount(account);
        emp.setEmpName(empname);
        emp.setEmpType(empType);
        empService.addEmp(emp);
    }

    @PostMapping("/add")
    public Result addEmp(@RequestBody Emp emp) {
        log.info("新增员工信息：{}", emp);
        empService.addEmp(emp);
        return Result.success();
    }

    // 员工账号所有者查询自己的信息
    @GetMapping("/queryMyInfo")
    public Result queryMyInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        log.info("查询当前员工账号：{}的信息", account);
        Emp emp = empService.queryMyInfo(account);
        return Result.success(emp);
    }

    // 根据账号查询员工表中的信息
    @GetMapping("/queryByAccount/{account}")
    public Result queryByAccount(@PathVariable String account) {
        System.out.println(account);
        log.info("根据账号：{} 查询员工信息", account);
        if(isExist(account)) {
            Emp emp = empService.queryByAccount(account);
            return Result.success(emp);
        }else {
            return Result.error("没有对应的员工！");
        }
    }
    @GetMapping("/queryList")
    public Result queryList(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            Integer empType) {
        log.info("分页查询员工信息，参数为{},{},{}",page,pageSize,empType);
        PageBean pageBean = empService.queryList(page,pageSize,empType);
        return Result.success(pageBean);
    }
    @PutMapping("/update")
    public Result updateEmp(@RequestBody Emp emp) {
        log.info("更新员工信息：{}", emp);
        boolean  flag = empService.updateEmp(emp);
        if(flag) return Result.success();
        else return Result.error("没有对应的员工！");
    }

}
