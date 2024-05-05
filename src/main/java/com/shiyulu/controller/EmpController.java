package com.shiyulu.controller;

import com.github.pagehelper.Page;
import com.shiyulu.mapper.EmpMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.*;
import com.shiyulu.service.impl.EmpServiceImpl;
import com.shiyulu.utils.AliSMSProperties;
import com.shiyulu.utils.SMSUtils;
import com.shiyulu.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpService empService;
    @Autowired
    private FrontDeskService frontDeskService;
    @Autowired
    private VehicleFaultService vehicleFaultService;
    @Autowired
    private RepairAuthorizationService repairAuthorizationService;
    @Autowired
    private RepairTaskService repairTaskService;
    @Autowired
    private  MaintenanceDispatchOrderService maintenanceDispatchOrderService;
    @Autowired
    private OnGoingTableService onGoingTableService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MaintenanceProgressService maintenanceProgressService;
    @Autowired
    private BillsService billsService;
    @Autowired
    private AliSMSProperties aliSMSProperties;  //SMS常量注解
    @Autowired
    private SMSUtils smsUtils;  //阿里云短信工具类

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

    //Wang:修改增加员工接口
    @PostMapping("/addEmp")
    public Result addEmp1(@Pattern(regexp = "^\\S{3,16}$") String account,
                          @Pattern(regexp = "^\\S{5,16}$") String password,
                          String username,
                          String phone,
                          Integer userType,
                          String trueName,
                          Integer empType) {
        User user = commonService.findByAccount(account);
        if(user == null){
            commonService.addUser(account, password, username, trueName, userType);
            user = commonService.findByAccount(account);
            user.setPhone(phone);
            commonService.updateInfo(user);
            Integer id = empMapper.findIdByAccount(account);
            Emp emp = new Emp();
            emp.setEmpId(id);
            emp.setEmpType(empType);
            empService.updateEmp(emp);
            return Result.success();
        }
        return Result.error("该账号已被占用！");
    }

    // 员工账号所有者查询自己的信息
    @GetMapping("/queryMyInfo")
    public Result queryInfo() {
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
        if (isExist(account)) {
            Emp emp = empService.queryByAccount(account);
            return Result.success(emp);
        } else {
            return Result.error("没有对应的员工！");
        }
    }

    @GetMapping("/queryList")
    public Result queryList(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            Integer empType) {
        log.info("分页查询员工信息，参数为{},{},{}", page, pageSize, empType);
        PageBean pageBean = empService.queryList(page, pageSize, empType);
        return Result.success(pageBean);
    }

    @PutMapping("/update")
    public Result updateEmp(@RequestBody Emp emp) {
        log.info("更新员工信息：{}", emp);
        boolean flag = empService.updateEmp(emp);
        if (flag) return Result.success();
        else return Result.error("没有对应的员工！");
    }

    // 业务员/经理 添加维修车辆的维修信息，添加了维修信息即表明接受了一个总的维修任务
    @PostMapping("/addMaintenanceAttorney")
    public Result addMaintenanceAttorney(@RequestBody VehicleFault vehicleFault) {
        Vehicle vehicle = frontDeskService.queryCarByVin(vehicleFault.getVin());
        if (vehicle == null) {
            log.info("不存在的车辆：{}，为此车辆添加故障信息失败！", vehicleFault.getVin());
            return Result.error("不存在的车辆，为此车辆添加故障信息失败！");
        } else {
            vehicleFault.setRepairStatus(0);
            vehicleFault.setWhetherPay(0);
            log.info("为车辆：{}添加故障信息：{}", vehicleFault.getVin(), vehicleFault);
            vehicleFaultService.addMaintenanceAttorney(vehicleFault);
            return Result.success();
        }
    }

    // 业务员/经理 更新维修车辆的维修信息，需要事先查询有无这个维修信息
    @PutMapping("/updateMaintenanceAttorney")
    public Result updateMaintenanceAttorney(@RequestBody VehicleFault vehicleFault) {
        VehicleFault vehicleFault1 = vehicleFaultService.queryMaintenanceAttorneyByVfi(vehicleFault.getVfi());
        if (vehicleFault1 == null) {
            log.info("不存在的维修任务：{}，更新维修任务失败！", vehicleFault.getVfi());
            return Result.error("不存在的维修任务，更新维修任务失败！");
        }
        else {
            log.info("维修任务号：{} 更新维修任务：{}", vehicleFault.getVfi(), vehicleFault);
            if(vehicleFault.getRepairStatus() == 1 && vehicleFault1.getRepairStatus() == 0) {   //此车辆故障维修结束，发送短信提醒客户缴费
                Vehicle vehicle = frontDeskService.queryCarByVin(vehicleFault1.getVin());
                Client client = clientService.queryClientInfoById(vehicle.getClientId());
                User user = commonService.findByAccount(client.getAccount());
                String smsClientName = user.getUsername();
                smsClientName = smsClientName.replaceAll("\\s+", "");
                //发送短信提醒客户
                smsUtils.sendMessage(aliSMSProperties.getSMSSIGNNAME(),aliSMSProperties.getSMSTEMPLATECODE(),user.getPhone(),smsClientName,vehicle.getLicense(),vehicleFault.getVfi());
            }
            vehicleFaultService.updateMaintenanceAttorney(vehicleFault);
            return Result.success();
        }
    }

    // 业务员/经理 生成维修委托书并添加进数据库接口
    @PostMapping("/addRepairAuthorization")
    public Result addRepairAuthorization(@RequestBody RepairAuthorization repairAuthorization) {
        VehicleFault vehicleFault = vehicleFaultService.queryMaintenanceAttorneyByVfi(repairAuthorization.getVfi());
        Client client = frontDeskService.queryClientById(repairAuthorization.getClientId());
        RepairAuthorization repairAuthorization1 = repairAuthorizationService.getRepairAuthorizationByVfi(repairAuthorization.getVfi());
        if (vehicleFault == null) {
            log.info("不存在的维修任务：{}，生成维修委托书失败！", repairAuthorization.getVfi());
            return Result.error("不存在的维修任务，生成维修委托书失败！");
        }
        else if (client == null) {
            log.info("不存在的客户ID：{}，生成维修委托书失败！", repairAuthorization.getClientId());
            return Result.error("不存在的客户ID，生成维修委托书失败！");
        }
        else if(frontDeskService.checkCarBelong(vehicleFault.getVin(),repairAuthorization.getClientId()) != true) {
            log.info("车辆与客户的从属关系：{}<-{}不存在，生成维修委托书失败！", repairAuthorization.getClientId(), vehicleFault.getVin());
            return Result.error("车辆与客户的从属关系不存在，生成维修委托书失败！");
        }
        else if(repairAuthorization1 != null) {
            log.info("车辆故障号：{} 已存在对应的维修委托书号：{}，无法再次生成维修委托书！",repairAuthorization.getVfi(), repairAuthorization.getRai());
            return Result.error("已存在车辆故障对应的维修委托书，生成维修委托书失败！");
        }
        else {
            log.info("为维修任务号：{}生成维修委托书：{}，添加进数据库", repairAuthorization.getVfi(), repairAuthorization);
            repairAuthorizationService.addRepairAuthorization(repairAuthorization);
            return Result.success();
        }
    }

    // 业务员/经理 修改维修委托书
    @PutMapping("/updateRepairAuthorization")
    public Result updateRepairAuthorization(@RequestBody RepairAuthorization repairAuthorization) {
        RepairAuthorization repairAuthorization1 = repairAuthorizationService.getRepairAuthorizationByRai(repairAuthorization.getRai());
        RepairAuthorization repairAuthorization2 = repairAuthorizationService.getRepairAuthorizationByVfi(repairAuthorization.getVfi());
        if(repairAuthorization.getRai().equals(repairAuthorization2.getRai()) == false) {
            log.info("更新的维修委托书不能为一个已经被分配了维修委托书的车辆故障再次分配维修委托书，车辆故障号：{}，其分配的维修委托书号：{}"
            ,repairAuthorization2.getVfi(),repairAuthorization2.getRai());
            return Result.error("更新的维修委托书不能为一个已经被分配了维修委托书的车辆故障再次分配维修委托书！");
        }
        else if (repairAuthorization1 == null) {
            log.info("不存在的维修委托书号：{}，更新维修委托书失败！", repairAuthorization.getRai());
            return Result.error("不存在的维修委托书号，更新维修委托书失败！");
        }
        else {
            log.info("维修委托书号：{} 更新维修委托书：{}", repairAuthorization.getRai(), repairAuthorization);
            repairAuthorizationService.updateRepairAuthorization(repairAuthorization);
            return Result.success();
        }
    }

    // 经理 新增维修任务
    @PostMapping("/addRepairTask")
    public Result addRepairTask(@RequestBody RepairTask repairTask) {
        RepairAuthorization repairAuthorization = repairAuthorizationService.getRepairAuthorizationByRai(repairTask.getRai());
        if (repairAuthorization == null) {
            log.info("不存在的维修委托书号：{}，新增维修任务失败！", repairTask.getRai());
            return Result.error("不存在的维修委托书号，新增维修任务失败！");
        }
        else {
            log.info("为维修委托书号：{} 新增维修任务：{}，添加进数据库", repairTask.getRai(), repairTask);
            repairTaskService.addRepairTask(repairTask);
            return Result.success();
        }
    }

    // 经理 更新维修任务
    @PutMapping("/updateRepairTask")
    public Result updateRepairTask(@RequestBody RepairTask repairTask) {
        RepairTask repairTask1 = repairTaskService.getRepairTaskByRiid(repairTask.getRiid());
        RepairAuthorization repairAuthorization = repairAuthorizationService.getRepairAuthorizationByRai(repairTask.getRai());
        if(repairTask1 == null) {
            log.info("不存在的维修任务号：{}，更新维修任务失败！", repairTask.getRiid());
            return Result.error("不存在的维修任务号，更新维修任务失败！");
        }
        else if(repairAuthorization == null) {
            log.info("不存在的维修委托书号：{}，维修任务号：{} 更新失败！", repairTask.getRai(), repairTask.getRiid());
            return Result.error("不存在的维修委托书号，维修任务更新失败！");
        }
        else {
            log.info("维修任务号：{} 更新维修任务：{}", repairTask.getRiid(), repairTask);
            repairTaskService.updateRepairTask(repairTask);
            return Result.success();
        }
    }

    // 经理/员工分页联表查询维修分配单和维修任务，可以指定分页参数该分配单此时是否被分配
    @GetMapping("/queryDispatchOrder")
    public Result queryDispatchOrder(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                    Integer isAssigned) {
        log.info("分页查询维修分配单，参数为{},{},{}", page, pageSize,isAssigned);
        PageBean pageBean = maintenanceDispatchOrderService.queryDispatchOrder(page, pageSize,isAssigned);
        return Result.success(pageBean);
    }


    // 经理发布维修分配单，此时无人接单，经理也没有指定任务分配的员工
    @PostMapping("/addDispatchOrder")
    public Result addDispatchOrder(@RequestBody MaintenanceDispatchOrder maintenanceDispatchOrder) {
        maintenanceDispatchOrder.setEmpId(null);
        maintenanceDispatchOrder.setEmpType(null);
        maintenanceDispatchOrder.setIsComplete(0);
        maintenanceDispatchOrderService.addDispatchOrder(maintenanceDispatchOrder);
        return Result.success();
    }

    // 经理更新维修分配单
    @PutMapping("/updateDispatchOrder")
    public Result updateDispatchOrder(@RequestBody MaintenanceDispatchOrder maintenanceDispatchOrder) {
        MaintenanceDispatchOrder maintenanceDispatchOrder1 = maintenanceDispatchOrderService.getDispatchOrderBymdoId(maintenanceDispatchOrder.getMdoid());
        if(maintenanceDispatchOrder1 == null) {
            log.info("不存在的维修任务分配单号：{}，更新维修任务分配单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("不存在的维修任务分配单号，更新维修任务分配单失败！");
        }
        else if(maintenanceDispatchOrder1.getIsComplete() == 1) {
            log.info("维修任务分配单号：{}已经完成，更新维修任务分配单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("维修任务分配单已经完成，更新维修任务分配单失败！");
        }
        else if(maintenanceDispatchOrder.getEmpId() == null && maintenanceDispatchOrder1.getEmpId() != null) {  //经理强制取消了此派工单的委托任务进行表
            log.info("维修派工单：{}由经理强制终止了对应的任务进行表",maintenanceDispatchOrder.getMdoid());
            OnGoingTable onGoingTable = onGoingTableService.getOnGoingTableBymdoid(maintenanceDispatchOrder.getMdoid());
            onGoingTable.setStatus(4);
            onGoingTableService.updateOnGoingTable(onGoingTable);
            // 手动更新任务发配单的信息
            maintenanceDispatchOrder.setEmpId(null);
            maintenanceDispatchOrder.setEmpType(null);
            maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);
            return Result.success();
        }
        else if(maintenanceDispatchOrder.getEmpId().equals(maintenanceDispatchOrder1.getEmpId()) == false) {    //此时更改了接单员工
            log.info("经理强制更改了任务分配表中的员工信息，此时需要重新分配任务！");
            OnGoingTable onGoingTable = onGoingTableService.getOnGoingTableBymdoid(maintenanceDispatchOrder.getMdoid());
            // 此时任务属于强制中断,需要更新onGoingTable的对应行
            onGoingTable.setStatus(4);
            onGoingTableService.updateOnGoingTable(onGoingTable);
            if(maintenanceDispatchOrder.getEmpId() != null) {   //证明有新分配
                Map<String, Object> map = ThreadLocalUtil.get();
                String account = (String) map.get("account");
                Integer userType = (Integer) map.get("usertype");
                Integer assignId = commonService.getId(account, userType);
                System.out.println("assignId: " + assignId);
                OnGoingTable onGoingTable2 = new OnGoingTable();
                onGoingTable2.setMdoid(maintenanceDispatchOrder.getMdoid());
                onGoingTable2.setAssignId(assignId);
                onGoingTable2.setReceivedId(maintenanceDispatchOrder.getEmpId());
                onGoingTable2.setStatus(0);
                // 手动更新任务发配单的信息
                maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);
                onGoingTableService.addOnGoingTable(onGoingTable2);
            }
            return Result.success();
        }
        else {  //此时仅仅修改工时等其他信息
            maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);
            return Result.success();
        }
    }

    // 经理分配维修分配单，并且将分配的历史记录插入ongoingtable中
    @PutMapping("/assignDispatchOrder")
    public Result assignDispatchOrder(@RequestBody MaintenanceDispatchOrder maintenanceDispatchOrder) {
        MaintenanceDispatchOrder maintenanceDispatchOrder1 = maintenanceDispatchOrderService.getDispatchOrderBymdoId(maintenanceDispatchOrder.getMdoid());
        if(maintenanceDispatchOrder1 == null) {
            log.info("不存在的维修任务分配单号：{}，分配维修任务分配单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("不存在的维修任务分配单号，分配维修任务分配单失败！");
        }
        else if(maintenanceDispatchOrder1.getEmpId() != null) {
            log.info("维修任务分配单号：{}已经被分配，分配维修任务分配单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("维修任务分配单已经被分配，分配维修任务分配单失败！");
        }
        else {
            maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);
            Map<String, Object> map = ThreadLocalUtil.get();
            String account = (String) map.get("account");
            Integer userType = (Integer) map.get("usertype");
            Integer assignId = commonService.getId(account, userType);
            System.out.println("assignId: " + assignId);
            OnGoingTable onGoingTable = new OnGoingTable();
            onGoingTable.setMdoid(maintenanceDispatchOrder.getMdoid());
            onGoingTable.setAssignId(assignId);
            onGoingTable.setReceivedId(maintenanceDispatchOrder.getEmpId());
            onGoingTable.setStatus(0);
            onGoingTableService.addOnGoingTable(onGoingTable);
            return Result.success();
        }
    }

    // 员工主动去接单，接单后，维修任务分配单中的员工id会被更新，同时ongoingtable中新增一条数据
    @PutMapping("/applyDispatchOrder")
    public Result applyDispatchOrder(@RequestBody MaintenanceDispatchOrder maintenanceDispatchOrder) {
        MaintenanceDispatchOrder maintenanceDispatchOrder1 = maintenanceDispatchOrderService.getDispatchOrderBymdoId(maintenanceDispatchOrder.getMdoid());
        if(maintenanceDispatchOrder1 == null) {
            log.info("不存在的维修任务分配单号：{}，接单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("不存在的维修任务分配单号，接单失败！");
        }
        else if(maintenanceDispatchOrder1.getEmpId() != null) {
            log.info("维修任务分配单号：{}已经被分配，接单失败！", maintenanceDispatchOrder.getMdoid());
            return Result.error("维修任务分配单已经被分配，接单失败！");
        }
        else {
            Map<String, Object> map = ThreadLocalUtil.get();
            String account = (String) map.get("account");
            Integer userType = (Integer) map.get("usertype");
            Integer receivedId = commonService.getId(account, userType);
            User user = commonService.findByAccount(account);
            maintenanceDispatchOrder.setEmpType(userType);
            maintenanceDispatchOrder.setEmpId(receivedId);
            // 更新任务发配单
            maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);

            System.out.println("receivedId: " + receivedId);
            OnGoingTable onGoingTable = new OnGoingTable();
            onGoingTable.setMdoid(maintenanceDispatchOrder.getMdoid());
            onGoingTable.setAssignId(receivedId);
            onGoingTable.setReceivedId(receivedId);
            onGoingTable.setStatus(0);
            // 更新任务进度表
            onGoingTableService.addOnGoingTable(onGoingTable);
            return Result.success();
        }
    }

    // 员工更新任务，更新ongoingtable中的状态码，以及维修任务分配单中的isComplete字段
    @PutMapping("/updateOnGoingTable")
    public Result updateOnGoingTable(@RequestBody OnGoingTable onGoingTable) {
        OnGoingTable onGoingTable1 = onGoingTableService.getOnGoingTableByogId(onGoingTable.getOgid());
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Integer userType = (Integer) map.get("usertype");
        Integer empId = commonService.getId(account, userType);
        if(onGoingTable1 == null) {
            log.info("不存在的进度单号：{}，更新进度单号失败！", onGoingTable.getMdoid());
            return Result.error("不存在的维修任务分配单号，更新维修任务分配单失败！");
        }
        else if(onGoingTable1.getStatus() == 3) {
            log.info("维修任务分配单号：{}已经完成，无法更新完成此分配单的历史记录{}！", onGoingTable.getMdoid(),onGoingTable.getOgid());
            return Result.error("维修任务分配单号已经完成，无法更新完成此分配单的历史记录！");
        }
        else if(onGoingTable1.getStatus() == 4) {
            log.info("维修任务分配单号：{}已经被强制中断，无法更新完成此分配单的历史记录{}！", onGoingTable.getMdoid(),onGoingTable.getOgid());
            return Result.error("维修任务分配单号已经被强制中断，无法更新完成此分配单的历史记录！");
        }
        else if(onGoingTable1.getStatus() == 2) {
            log.info("此任务记录号：{}已经被拒绝了，无法更新",onGoingTable1.getOgid());
            return Result.error("此任务记录号已经被拒绝了，无法更新");
        }
        else if(!empId.equals(onGoingTable1.getReceivedId())) {
            log.info("员工：{}更新了不属于自己的进度单号：{}，更新进度单号失败！", empId,onGoingTable1.getOgid());
            return Result.error("员工更新了不属于自己的进度单号，更新进度单号失败！");
        }
        else {
            if(onGoingTable.getStatus().equals(4)) {
                log.info("员工无法对任务进度号：{} 强制中断！",onGoingTable.getOgid());
                return Result.error("员工无法强制中断任务进度");
            }else {
                log.info("员工: {} 对任务进度号：{} 的状态修改为：{} ！",empId,onGoingTable.getOgid(), onGoingTable.getStatus());
            }
            onGoingTableService.updateOnGoingTable(onGoingTable);
            MaintenanceDispatchOrder maintenanceDispatchOrder = maintenanceDispatchOrderService.getDispatchOrderBymdoId(onGoingTable1.getMdoid());
            boolean IsFinish =  maintenanceDispatchOrderService.checkRepairTaskIsFinish(maintenanceDispatchOrder.getRiid());
            if(IsFinish) {  //一个repairTask完成后需要置字段isComplete为1，并且需要计算维修委托书中的维修费用
                RepairTask repairTask = repairTaskService.getRepairTaskByRiid(maintenanceDispatchOrder.getRiid());
                repairTask.setIsComplete(1);
                repairTaskService.updateRepairTask(repairTask);
                // 计算维修费abo
                Double partsCost = repairTask.getTotalComponentPrice();
                Double laborCost = repairTaskService.calculateLaborCost(repairTask.getRiid());
                // 将费用更新至维修委托书中(费用递增)
                Integer rai = repairTask.getRai();
                RepairAuthorization repairAuthorization = repairAuthorizationService.getRepairAuthorizationByRai(rai);
                repairAuthorization.setTotalRepairCost(repairAuthorization.getTotalRepairCost() + partsCost + laborCost);
                repairAuthorizationService.updateRepairAuthorization(repairAuthorization);
            }
            return Result.success();
        }
    }

//    // 员工拒绝维修派工单，同步更新ongoingtable中的状态码，以及维修任务分配单中的empId字段,将其置为空
//    @PutMapping("/rejectOnGoingTable")
//    public Result rejectOnGoingTable(@RequestBody OnGoingTable onGoingTable) {
//        onGoingTable.setStatus(2);
//        OnGoingTable onGoingTable1 = onGoingTableService.getOnGoingTableByogId(onGoingTable.getOgid());
//        MaintenanceDispatchOrder maintenanceDispatchOrder = maintenanceDispatchOrderService.getDispatchOrderBymdoId(onGoingTable1.getMdoid());
//        Map<String, Object> map = ThreadLocalUtil.get();
//        String account = (String) map.get("account");
//        Integer userType = (Integer) map.get("usertype");
//        Integer empId = commonService.getId(account, userType);
//        System.out.println("empId: " + empId);
//        if(maintenanceDispatchOrder == null) {
//            log.info("不存在的维修任务分配单号：{}，拒绝进度单号失败！", onGoingTable.getMdoid());
//            return Result.error("不存在的维修任务分配单号，拒绝进度单号失败！");
//        }
//        else if(onGoingTable1 == null) {
//            log.info("不存在的进度单号：{}，拒绝进度单号失败！", onGoingTable.getMdoid());
//            return Result.error("不存在的进度单号，拒绝进度单号失败！");
//        }
//        else if(onGoingTable1.getStatus() == 3) {
//            log.info("维修任务分配单号：{}已经完成，无法拒绝完成此分配单的历史记录{}！", onGoingTable.getMdoid(),onGoingTable.getOgid());
//            return Result.error("维修任务分配单号已经完成，无法拒绝完成此分配单的历史记录！");
//        }
//        else if(!empId.equals(onGoingTable1.getReceivedId())) {
//            log.info("员工：{}拒绝了不属于自己的进度单号：{}，拒绝进度单号失败！", empId,onGoingTable1.getOgid());
//            return Result.error("员工拒绝了不属于自己的进度单号，拒绝进度单号失败！");
//        }
//        else if(onGoingTable1.getStatus() == 4) {
//            log.info("维修任务分配单号：{}已经由历史记录{}强制中断！", onGoingTable.getMdoid(),onGoingTable.getOgid());
//            return Result.error("维修任务分配单号已经被强制中断，无法拒绝此被强制终端的历史记录！");
//        }
//        else if(onGoingTable1.getStatus() == 2) {
//            log.info("维修任务分配单号：{}已经被历史记录{}拒绝过了！", onGoingTable.getMdoid(),onGoingTable.getOgid());
//            return Result.error("维修任务分配单号已经被拒绝，无法再次拒绝此分配单的历史记录！");
//        }
//        else {
//            onGoingTableService.updateOnGoingTable(onGoingTable);
//            // 拒绝进度表后需要将维修任务分配单中的empId字段置空
//            maintenanceDispatchOrder.setEmpId(null);
//            maintenanceDispatchOrder.setEmpType(null);
//            maintenanceDispatchOrderService.updateDispatchOrder(maintenanceDispatchOrder);
//            return Result.success();
//        }
//    }

    //员工分页查询自己的任务记录
    @GetMapping("/empQueryOnGoingTable")
    public Result empQueryOnGoingTable(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       Integer status) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Integer userType = (Integer) map.get("usertype");
        Integer empId = commonService.getId(account, userType);
        log.info("员工：{}分页查询自己的任务记录，参数为{},{},{}", empId, page, pageSize,status);
        PageBean pageBean = onGoingTableService.empQueryOnGoingTable(page, pageSize, empId,status);
        return Result.success(pageBean);
    }

    //前台/经理查询某个车辆故障的维修进度
    @GetMapping("/empQueryMaintenanceProgress")
    public Result empQueryMaintenanceProgress(Integer vfi) {
        VehicleFault vehicleFault = clientService.queryVehicleFaultInfoByVFId(vfi);
        if(vehicleFault == null) {
            log.info("不存在的车辆故障号：{}，查询维修进度失败！", vfi);
            return Result.error("不存在的车辆故障号，查询维修进度失败！");
        }
        else {
            log.info("查询车辆故障号：{}的维修进度", vfi);
            MaintenanceProgress maintenanceProgress = maintenanceProgressService.queryMaintenanceProgress(vfi);
            return Result.success(maintenanceProgress);
        }
    }

    @GetMapping("/listBills")
    public Result listBills(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            Integer clientId) {
        log.info("分页查询支付账单，参数为{},{},{}", page, pageSize, clientId);
        PageBean pageBean = billsService.listBills(page, pageSize, clientId);
        return Result.success(pageBean);
    }

    @PostMapping("/testSMS")
    public Result testSMS() {
        log.info("测试短信发送");
        smsUtils.sendMessage(aliSMSProperties.getSMSSIGNNAME(),aliSMSProperties.getSMSTEMPLATECODE(),
                "18018641963","shiyulu", "沪C-SJ888",6);
        return Result.success();
    }

}
