package com.shiyulu.controller;

import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.FrontDeskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/frontDesk")
public class FrontDeskController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private FrontDeskService frontDeskService;
    // 新增某个客户名下的车辆
    @PostMapping("/addCar")
    public Result addCar(@RequestBody Vehicle vehicle) {
        Client client  = clientService.queryClientInfoById(vehicle.getClientId());
        if(client == null){
            log.info("不存在的客户：{}，为此客户添加车辆失败！",vehicle.getClientId());
            return Result.error("不存在的客户，为此客户添加车辆失败！");
        }else {
            // 为用户添加车辆
            log.info("添加车辆：{}",vehicle);
            frontDeskService.addCar(vehicle);
            return Result.success();
        }
    }
    // 删除某个客户名下的车辆，注意级联
    @DeleteMapping("/deleteCar/{vin}")
    public Result deleteCar(@PathVariable String vin) {
        Vehicle vehicle = frontDeskService.queryCarByVin(vin);
        if(vehicle == null) {
            log.info("不存在的车辆：{}，删除失败！",vin);
            return Result.error("不存在的车辆，删除失败！");
        }else {
            log.info("删除车辆：{}",vehicle);
            frontDeskService.deleteCar(vin);
            return Result.success();
        }
    }
    // 修改某个客户名下的车辆
    @PutMapping("/updateCar")
    public Result updateCar(@RequestBody Vehicle vehicle) {
        if(frontDeskService.checkCarBelong(vehicle.getVin(),vehicle.getClientId() ) ) {
            frontDeskService.updateCar(vehicle);
            return Result.success();
        }else {
            return Result.error("该车辆与用户的归属关系不存在！修改失败！");
        }
    }

    // 分页查询全部客户的信息，user表与client表联查
    @GetMapping("/queryAllClientInfo")
    public Result queryAllClientInfo(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    Integer clientType) {
        log.info("分页查询客户信息，参数为{},{},{}",page,pageSize,clientType);
        PageBean pageBean = frontDeskService.queryAllClientInfo(page,pageSize,clientType);
        return Result.success(pageBean);
    }

    // 分页查询指定客户名下的车辆信息

    // 分页查询所有车辆信息，包括车主信息

    // 查询指定车辆的信息


}
