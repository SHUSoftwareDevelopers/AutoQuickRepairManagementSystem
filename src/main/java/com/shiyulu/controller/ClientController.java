package com.shiyulu.controller;

import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //查询全部的客户信息，主要用于后台展示  ---Wang: 修改了接口路径
    @GetMapping("/listClient")
    public Result queryAllClientInfo(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean clients  = clientService.queryAllClientInfo(page,pageSize);
        log.info("请求成功");
        return Result.success(clients);

    }

    //Wang: 增加了客户查自己信息的接口
    @GetMapping("/queryInfo")
    public Result queryInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer clientId = (Integer) map.get("id");
        Client client  = clientService.queryClientInfoById(clientId);
        return Result.success(client);
    }

    //Wang: 增加修改客户信息的接口
    @PutMapping("updateInfo")
    public Result updateInfo(@RequestBody @Validated Client client){
        clientService.updateInfo(client);
        return Result.success();
    }

    //根据客户ID查询某个客户的信息
    @GetMapping("/queryById/{clientId}")
    public Result queryAllClientInfoById(@PathVariable Integer clientId){
        Client client  = clientService.queryClientInfoById(clientId);
        if(client == null){
            return Result.error("该用户不存在");
        }
        return Result.success(client);
    }

    //查询全部的客户车辆信息，主要用于后台展示
    @GetMapping("/findCar")
    public Result queryAllVehicleInfo(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean vehicles  = clientService.queryAllVehicleInfo(page,pageSize);
        return Result.success(vehicles);
    }

    //根据客户ID查询某个客户的车辆信息
    @GetMapping("/findCar/{clientId}")
    public Result queryVehicleInfoByClientId(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @PathVariable Integer clientId){
        PageBean userVehicles  = clientService.queryVehicleInfoByClientId(page,pageSize,clientId);
        if(userVehicles == null){
            return Result.error("该客户不存在");
        }
        if(userVehicles.getTotal() == 0){
            return Result.error("该客户的数据不存在");
        }
        return Result.success(userVehicles);
    }

    //查询全部的客户车辆信息，主要用于后台展示
    @GetMapping("/findFault")
    public Result queryAllVehicleFaultInfo(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean vehicleFaults  = clientService.queryAllVehicleFaultInfo(page,pageSize);
        return Result.success(vehicleFaults);
    }

    //根据订单号查询某个车辆的维修信息
    @GetMapping("/findFault/{vehicleFaultId}")
    public Result queryVehicleFaultInfoByVFId(@PathVariable Integer vehicleFaultId){
        VehicleFault vehicleFault  = clientService.queryVehicleFaultInfoByVFId(vehicleFaultId);
        if(vehicleFault == null){
            return Result.error("该维修订单不存在");
        }
        return Result.success(vehicleFault);
    }
    //根据客户ID查询该客户的所有车辆维修信息
    @GetMapping("/findFaultByClient/{clientId}")
    public Result queryVehicleFaultInfoByVFId(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @PathVariable Integer clientId){
        PageBean vehicleFaults  = clientService.queryVehicleFaultInfoByClientId(page,pageSize,clientId);
        if(vehicleFaults == null){
            return Result.error("该客户不存在");
        }
        if(vehicleFaults.getTotal() == 0){
            return Result.error("该客户的数据不存在");
        }
        return Result.success(vehicleFaults);
    }
}
