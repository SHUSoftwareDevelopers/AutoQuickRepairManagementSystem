package com.shiyulu.controller;

import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.CommonService;
import com.shiyulu.service.FrontDeskService;
import com.shiyulu.service.MaintenanceProgressService;
import com.shiyulu.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FrontDeskService frontDeskService;
    @Autowired
    private MaintenanceProgressService maintenanceProgressService;

    //查询全部的客户信息，主要用于后台展示
    @GetMapping("/queryInfo")
    public Result queryAllClientInfo(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean clients  = clientService.queryAllClientInfo(page,pageSize);
        log.info("请求成功");
        return Result.success(clients);
    }

    //查询当前登录的客户账号的信息
    @GetMapping("/queryMyInfo")
    public Result queryAllClientInfoById(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        log.info("账号：{} 查询自身客户信息",account);
        Client client = clientService.queryClientInfoByAccount(account);
        if(client == null) {
            return Result.error("该用户不存在");
        }
        return Result.success(client);
    }

    //更新此时登录账号的客户的信息,客户无法修改折扣率和绑定账号
    @PutMapping("/update")
    public Result updateClientInfo(@RequestBody Client client){
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        client.setAccount(account);
        log.info("客户更新自己client信息，更新后的信息为：{}", client);
        clientService.updateClientInfo(client);
        return Result.success();
    }

    //查询全部的客户车辆信息，主要用于后台展示
//    @GetMapping("/findCar")
//    public Result queryAllVehicleInfo(@RequestParam(defaultValue = "1") Integer page,
//                                     @RequestParam(defaultValue = "10") Integer pageSize){
//        PageBean vehicles  = clientService.queryAllVehicleInfo(page,pageSize);
//        return Result.success(vehicles);
//    }

    //查询此时**登录账号**名下的车辆信息
    @GetMapping("/findCar")
    public Result queryVehicleInfoByClientId(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize){
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Client client = clientService.queryClientInfoByAccount(account);
        if(client == null) {
            return Result.error("该用户不存在");
        }
        PageBean userVehicles  = clientService.queryVehicleInfoByClientId(page,pageSize,client.getClientId());
        if(userVehicles == null){
            return Result.error("该客户不存在");
        }
        if(userVehicles.getTotal() == 0){
            return Result.error("该客户的数据不存在");
        }
        return Result.success(userVehicles);
    }

    //查询全部的客户车辆故障信息，主要用于后台展示
    @GetMapping("/findFault")
    public Result queryAllVehicleFaultInfo(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean vehicleFaults  = clientService.queryAllVehicleFaultInfo(page,pageSize);
        return Result.success(vehicleFaults);
    }

    //根据订单号查询某个车辆的故障信息
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
                                              @PathVariable Integer clientId) {
        PageBean vehicleFaults  = clientService.queryVehicleFaultInfoByClientId(page,pageSize,clientId);
        if(vehicleFaults == null){
            return Result.error("该客户不存在或名下没有车辆");
        }
        if(vehicleFaults.getTotal() == 0){
            return Result.error("该客户的数据不存在");
        }
        return Result.success(vehicleFaults);
    }

    // 此时登录账号的客户分页查询自己车辆的维修信息
    @GetMapping("/queryMyVehicleFaultInfo")
    public Result queryMyVehicleFaultInfo(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          Integer repairStatus) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Client client = clientService.queryClientInfoByAccount(account);
        if(client == null) {
            log.info("查询的客户账号不存在！");
            return Result.error("该用户不存在");
        }
        PageBean vehicleFaults  = clientService.queryMyVehicleFaultInfo(page,pageSize,client.getClientId(),repairStatus);
        return Result.success(vehicleFaults);
    }

    // 客户查询自己车辆的维修进度
    @GetMapping("/queryMaintenanceProgress")
    public Result queryMaintenanceProgress(Integer vfi) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Integer userType = (Integer) map.get("usertype");
        Integer clientId = commonService.getId(account, userType);
        System.out.println(clientId);
        VehicleFault vehicleFault = clientService.queryVehicleFaultInfoByVFId(vfi);
        if(vehicleFault == null) {
            log.info("客户：{} 请求查询了一个不存在的订单：{}",clientId,vfi);
            return Result.error("该维修订单不存在");
        }
        else if(!frontDeskService.checkCarBelong(vehicleFault.getVin(),clientId)) {
            log.info("客户：{} 请求查询车辆故障号：{}，但客户与车辆的归属关系不存在",clientId,vfi);
            return Result.error("该车辆与用户的归属关系不存在！");
        }
        else {  //此时是正常的查询
            log.info("客户：{} 请求查询车辆故障号：{}的维修进度信息",clientId,vfi);
            MaintenanceProgress maintenanceProgress = maintenanceProgressService.queryMaintenanceProgress(vfi);
            return Result.success(maintenanceProgress);
        }
    }

}
