package com.shiyulu.controller;

import com.shiyulu.mapper.FrontDeskMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.FrontDeskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/frontDesk")
public class FrontDeskController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private FrontDeskService frontDeskService;
    @Autowired
    private FrontDeskMapper frontDeskMapper;
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
        // 客户车号VIN重复，MySQL自动抛出异常
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

    // 分页查询全部客户的信息，user表与client表联查，分页参数可以设置客户类型
    @GetMapping("/queryAllClientInfo")
    public Result queryAllClientInfo(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    Integer clientType) {
        log.info("分页查询所有客户信息，参数为{},{},{}",page,pageSize,clientType);
        PageBean pageBean = frontDeskService.queryAllClientInfo(page,pageSize,clientType);
        return Result.success(pageBean);
    }

    // 分页查询指定客户名下的车辆信息
    @GetMapping("/queryClient_Car")
    public Result queryClient_Car(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Integer clientId) {
        log.info("分页查询客户名下的车辆信息，参数为{},{},{}",page,pageSize,clientId);
        PageBean pageBean = frontDeskService.queryClient_Car(page,pageSize,clientId);
        return Result.success(pageBean);
    }

    // 分页查询所有车辆信息，包括车主信息，分页参数可以设置汽车类型，汽车颜色
    @GetMapping("/queryAllCarInfo")
    public Result queryAllCarInfo(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Integer vehicleType, String vehicleColor) {
        log.info("分页查询所有车辆信息，参数为{},{},{},{}",page,pageSize,vehicleType,vehicleColor);
        PageBean pageBean = frontDeskService.queryAllCarInfo(page,pageSize,vehicleType,vehicleColor);
        return Result.success(pageBean);
    }

    //获取所有车辆的vin
    @GetMapping("/getVinList")
    public Result getVinList(){
        List<String> vins = frontDeskMapper.getVinList();
        return Result.success(vins);
    }

}
