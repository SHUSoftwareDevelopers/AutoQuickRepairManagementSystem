package com.shiyulu.controller;

import com.shiyulu.mapper.ClientMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.CommonService;
import com.shiyulu.service.FrontDeskService;
import com.shiyulu.service.MaintenanceProgressService;
import com.shiyulu.service.*;
import com.shiyulu.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;

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
    @Autowired
    private VehicleFaultService vehicleFaultService;
    @Autowired
    private BillsService billsService;

    @Autowired
    private ClientMapper clientMapper;

    //查询全部的客户信息，主要用于后台展示  ---Wang: 修改了接口路径
    @GetMapping("/listClient")
    public Result queryAllClientInfo(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     String clientName,
                                     Integer clientType){
        PageBean clients  = clientService.queryAllClientInfo(page,pageSize,clientName,clientType);
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
        if(client == null) {
            return Result.error("该用户不存在");
        }
        return Result.success(client);
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
        clientService.updateInfo(client);
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
    public Result queryAllVehicleInfo(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                      String vehicleColor,
                                      String vehicleType,
                                      Integer clientId){
        PageBean vehicles  = clientService.queryAllVehicleInfo(page,pageSize,vehicleColor, vehicleType, clientId);
        return Result.success(vehicles);
    }

    //WANG：增加自己查自己车
    @GetMapping("/queryOwnCar")
    public Result queryCar(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           String vehicleColor,
                           String vehicleType){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer clientId = (Integer) map.get("id");
        String account = (String) map.get("account");
        Client client = clientService.queryClientInfoByAccount(account);
        if(client == null) {
            return Result.error("该客户不存在");
        }
        PageBean ownVehicles = clientService.queryOwnCar(page,pageSize,vehicleColor,vehicleType,clientId);
        return Result.success(ownVehicles);
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

    //添加客户
    @PostMapping("/addClient")
    public Result addClient(@Pattern(regexp = "^\\S{3,16}$") String account,
                            @Pattern(regexp = "^\\S{5,16}$") String password,
                            String username,
                            String phone,
                            Integer userType,
                            String trueName,
                            Integer clientType,
                            Double discountRate,
                            String businessContact,
                            String businessTele
                            ){
        User user = commonService.findByAccount(account);
        if(user == null){
            commonService.addUser(account, password, username, trueName, userType);
            user = commonService.findByAccount(account);
            user.setPhone(phone);
            commonService.updateInfo(user);
            Integer id = clientMapper.findIdByAccount(account);
            Client client = new Client();
            client.setClientId(id);
            client.setClientType(clientType);
            client.setDiscountRate(discountRate);
            client.setBusinessContact(businessContact);
            client.setBusinessTele(businessTele);
            clientMapper.updateInfo(client);
            return Result.success();
        }

        return Result.error("该账号已被占用！");
    }

    // 客户查询自己车辆的维修进度
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

    @PostMapping("/payBills")
    public Result payBills(@RequestBody Bills bills) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Client client = clientService.queryClientInfoByAccount(account);
        VehicleFault vehicleFault = vehicleFaultService.queryMaintenanceAttorneyByVfi(bills.getVfi());
        if(vehicleFault == null) {
            log.info("客户：{}请求支付车辆故障：{}的费用，但该订单不存在",client.getClientId(),bills.getVfi());
            return Result.error("该订单不存在！");
        }
        else if(vehicleFault.getWhetherPay() == 1) {
            log.info("客户：{}请求支付车辆故障：{}的费用，但该订单已经支付",client.getClientId(),bills.getVfi());
            return Result.error("该订单已经支付！");
        }
        else if(vehicleFault.getRepairStatus() == 0) {
            log.info("客户：{}请求支付车辆故障：{}的费用，但该订单尚未完成",client.getClientId(),bills.getVfi());
            return Result.error("该订单尚未完成！");
        }
        else {
            if (frontDeskService.checkCarBelong(vehicleFault.getVin(), client.getClientId())) {
                log.info("客户：{}为车辆故障：{}付款",client.getClientId(),bills.getVfi());
                billsService.addBills(bills);
                return Result.success();
            }
            else {
                log.info("客户：{}与车辆故障：{}的归属关系不存在！",client.getClientId(),bills.getVfi());
                return Result.error("客户与车辆故障的归属关系不存在！");
            }
        }
    }

    @GetMapping("/listMyBills")
    public Result listMyBills(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Client client = clientService.queryClientInfoByAccount(account);
        PageBean pageBean = billsService.listBills(page,pageSize,client.getClientId());
        return Result.success(pageBean);
    }

}
