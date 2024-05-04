use autoquickrepairdatabase;

# 账号登录表
create table user
(
    account     varchar(30) primary key,
    password    varchar(80) not null,
    userType    tinyint not null,   # 账号类别（0/管理员、1/前台、2/机修工、3/焊工、4/漆工、5/业务员、6/客户）
    username    varchar(20) not null,
    avatar      varchar(300),
    email       varchar(30),
    phone       char(11),
    createTime  datetime not null,
    updateTime  datetime not null
) comment '账号登陆表';


# 客户个人信息表
# clientType中0代表个人，1代表单位
create table client
(
    clientId int unsigned auto_increment primary key comment '客户id',
    clientName varchar(30),
    clientType tinyint,
    discountRate double comment '折扣率',
    businessContact varchar(30) comment '业务联系人',
    businessTele varchar(30) comment '业务联系电话',
    account varchar(30),
    foreign key (account) references user(account)
) comment '客户个人信息表';

# 车辆信息
create table vehicle
(
    vin char(17) primary key comment '车架号',
    license varchar(20) comment '车牌号',
    vehicleType varchar(45) comment '车型',
    vehicleColor varchar(30) comment '颜色',
    clientId int unsigned comment '所属客户编号',
    createTime datetime not null,
    updateTime datetime not null,
    foreign key (clientId) references client(clientId)
) comment '车辆信息表';

# 维修厂员工表
# 0代表经理/管理员，1代表机修，2代表焊工，3代表漆工，4代表前台，5代表业务员
create table emp
(
    empId int unsigned auto_increment primary key comment '员工编号',
    empName varchar(30) comment '员工姓名（实名）',
    empType tinyint comment '员工工种',
    account varchar(30) comment '登录账号',
    foreign key (account) references user(account)
) comment '维修厂员工表';

# 车辆的故障信息表
# 维修类型中:0代表普通，1代表加急
# 作业分类中:0代表大型，1代表中型，2代表小型
# 结算方式中:0代表自付，1代表三方，2代表索赔
create table vehicleFault
(
    vfi int unsigned auto_increment primary key comment 'vehicle fault id',
    maintenanceType tinyint comment '维修类型',
    taskClassification tinyint comment '作业分类',
    paymentMethod tinyint comment '结算方式',
    vin char(17) comment '受维修车架号',
    createTime datetime not null,
    updateTime datetime not null,
    foreign key (vin) references vehicle(vin)
) comment '车辆的故障信息表';

# 维修委托书
create table repairAuthorization
(
    rai int unsigned auto_increment primary key comment '委托书id,repair authorization id',
    clientId int unsigned comment '客户id',
    vfi int unsigned comment '故障单id',
    empId int unsigned comment '受理业务员id',
    mileage double comment '行驶里程km',
    onboardItems varchar(300) comment '随车物品',
    checkResult varchar(300) comment '检查结果',
    repairMethod varchar(300) comment '维修方案',
    isWashCar bool comment '是否洗车',
    isGetFormerComponent bool comment '旧件是否带回',
    totalRepairCost double comment '维修总费用',
    createTime datetime comment '创建时间',
    updateTime datetime comment '修改时间',
    estDelivTime datetime comment '预计交付时间',
    currentRepairStatus varchar(45) comment '目前维修状态',
    foreign key (clientId) references client(clientId),
    foreign key (vfi) references vehicleFault(vfi),
    foreign key (empId) references emp(empId)
) comment '维修委托书';

# 维修委托内容（维修项目）
create table repairtaskre
(
    riid int unsigned auto_increment primary key comment '维修项目编号',
    repairitem varchar(300) comment '维修项目',
    needComponent varchar(300) comment '所需零件',
    pricePerComponent double comment '零件单价',
    totalComponentPrice double comment '零件总金额',
    rai int unsigned comment '委托书id',
    createTime datetime not null,
    updateTime datetime not null,
    foreign key (rai) references repairAuthorization(rai)
) comment '维修委托内容';

# 维修派工单
create table maintenanceDispatchOrder
(
    mdoid int unsigned auto_increment primary key comment '维修派工单编号',
    workLength double comment '工时',
    pricePerhour double comment '工时单价',
    riid int unsigned comment '维修项目编号',
    empId int unsigned comment '受理维修员编号',
    empType tinyint comment '维修员工种',
    isComplete bool comment '是否完成',
    createTime datetime not null,
    updateTime datetime not null,
    foreign key (riid) references repairtask(riid),
    foreign key (empId) references emp(empId)
) comment '维修派工单';

# 任务进行记录及历史记录表
# 任务进行情况中0代表未受理，1代表已受理，2代表已拒绝，3代表已完成，4代表中断受理
create table onGoingTable
(
    ogid int unsigned auto_increment primary key comment '任务进行id',
    mdoid int unsigned comment '维修派工单id',
    assignId int unsigned comment '任务分配者id',
    receiveId int unsigned comment '任务目标者id',
    status tinyint comment '任务进行情况',
    createTime datetime comment '创建时间',
    endTime datetime comment '终止时间',
    foreign key (mdoid) references maintenanceDispatchOrder(mdoid),
    foreign key (assignId) references emp(empId),
    foreign key (receiveId) references emp(empId)
) comment '任务进行记录及历史记录表';

# **********后续对于表的修改**********
alter table repairtask
    add isComplete boolean;

# 0代表进行中，1代表未完成
alter table vehicleFault add repairStatus tinyint;
alter table repairAuthorization DROP currentRepairStatus;
alter table vehicleFault modify whetherPay tinyint;

# 客户账单表
create table bills
(
    billId int unsigned primary key auto_increment comment '账单id号',
    clientId int unsigned comment '客户id',
    vfi int unsigned comment 'vehicle fault id',
    discountRate double comment '支付时的折扣率',
    paymentMethod tinyint comment '结算方式',
    payment double comment '实际付款金额',
    payTime datetime comment '支付时间',
    foreign key (clientId) references client(clientId),
    foreign key (vfi) references vehicleFault(vfi)
) comment '客户支付账单表';

-- 设置repairTask的触发器
create trigger LimitPublishTask before insert on repairtask # 在插入操作前检查
    for each row
BEGIN
    if exists(select * from vehicleFault inner join repairAuthorization on vehicleFault.vfi = repairAuthorization.vfi
              where repairAuthorization.rai = new.rai and vehicleFault.repairStatus = 1) then
        SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = '该车辆故障已经维修完毕，无法继续添加任务';
    end if;
end;

-- 测试案例
# insert into repairtask
#     (repairitem, needComponent, pricePerComponent, totalComponentPrice, rai, createTime, updateTime, isComplete)
# values ('发动机维修','发动机',10000,20000,1,now(),now(),0);
# SHOW TRIGGERS;

-- 设置maintenanceDispatchOrder的触发器
create trigger LimitPublishDispatchOrder before insert on maintenancedispatchorder
    for each row
BEGIN
    if (exists(select * from repairtask where repairtask.riid = new.riid and repairtask.isComplete = 1)) then
        SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = '该维修任务已经完成，无法继续派发派工单';
    end if;
end;

# -- 测试案例
# insert into maintenancedispatchorder
# (workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime)
# values (1,100,1,16,2,0,now(),now());

-- 设置更新repairTask的零件费用后repairAuthorization同步更新的触发器
create trigger RepairFeeSynchronous after update on repairtask
    for each row
BEGIN
    if (old.totalComponentPrice != new.totalComponentPrice) #针对已经完成的任务的价格更新
        and old.isComplete = 1 and new.isComplete = 1 then
        update repairAuthorization
        set totalRepairCost = totalRepairCost + (new.totalComponentPrice-old.totalComponentPrice)
        where rai = new.rai;
    end if;
end;

# drop trigger RepairFeeSynchronous;

-- 测试案例
# update repairtask
# set needComponent = '高压空气，打光剂',pricePerComponent = 50, totalComponentPrice = 50
# where riid = 2;

-- 设置更新maintenanceDispatchOrder的工时和工时单价后repairAuthorization同步更新的触发器
create trigger LaborFeeSynchronous after update on maintenancedispatchorder
    for each row
BEGIN
    if (new.workLength != old.workLength or new.pricePerhour != old.pricePerhour)
        and old.isComplete = 1 and new.isComplete = 1
        and exists(select * from repairtask where repairtask.riid = new.riid and repairtask.isComplete = 1)  then
        update repairAuthorization  # 在repairAuthorization表中更新人工费
        set totalRepairCost = totalRepairCost + (new.pricePerhour*new.workLength - old.pricePerhour*old.workLength)
        where repairAuthorization.rai in (select rai from repairtask inner join maintenanceDispatchOrder on repairtask.riid = maintenanceDispatchOrder.riid
                                          where mdoid = new.mdoid);
    end if;
end;
# drop trigger LaborFeeSynchronous;

-- 测试案例
# update maintenanceDispatchOrder
# set workLength = 1,pricePerhour = 20
# where mdoid = 1;






