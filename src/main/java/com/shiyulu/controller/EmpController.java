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
    @GetMapping("/queryById/{id}")
    public Result queryById(@PathVariable Integer id) {
        log.info("根据id:{} 查询员工信息",id);
        Emp emp = empService.queryById(id);
        return Result.success(emp);
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
        log.info("更新帖子信息：{}", emp);

        return Result.success();
    }

    @GetMapping("/queryInfo")
    public Result queryInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Emp emp = empService.queryById(id);
        return Result.success(emp);
    }

}
