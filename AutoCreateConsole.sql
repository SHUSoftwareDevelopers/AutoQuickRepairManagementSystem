use autoquickrepairdatabase;

# 账号登录表
create table user
(
    account     varchar(30) primary key,
    password    varchar(30) not null,
    userType    tinyint not null,
    username    varchar(20) not null,
    avatar      varchar(300),
    email       varchar(30),
    phone       char(11),
    createTime  datetime not null,
    updateTime  datetime not null
) comment '账号登陆表';

# 客户个人信息表
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
    vin int primary key comment '车架号',
    license int comment '车牌号',
    vehicleType tinyint comment '车型',
    vehicleColor varchar(30) comment '颜色',
    clientId int unsigned comment '所属客户编号',
    foreign key (clientId) references client(clientId)
) comment '车辆信息表';

# 维修厂员工表
create table emp
(
    empId int unsigned auto_increment primary key comment '员工编号',
    empName varchar(30) comment '员工姓名（实名）',
    empType tinyint comment '员工工种',
    account varchar(30) comment '登录账号',
    foreign key (account) references user(account)
) comment '维修厂员工表';

# 车辆的故障信息表
create table vehicleFault
(
    vfi int unsigned auto_increment primary key comment 'vehicle fault id',
    maintenanceType tinyint comment '维修类型',
    taskClassification tinyint comment '作业分类',
    paymentMethod tinyint comment '结算方式',
    vin int comment '受维修车架号',
    foreign key (vin) references vehicle(vin)
) comment '车辆的故障信息表';

# 维修委托书
create table repairAuthorization
(
    rai int unsigned auto_increment primary key comment '委托书id,repair authorization id',
    clientId int unsigned comment '客户id',
    vfi int unsigned comment '故障单id',
    empId int unsigned comment '受理业务员id',
    mileage double comment '行驶里程',
    onboardItems varchar(300) comment '随车物品',
    checkResult varchar(300) comment '检查结果',
    repairMethod varchar(300) comment '维修方案',
    isWashCar bool comment '是否洗车',
    isGetFormerComponent bool comment '旧件是否带回',
    totalRepairCost double comment '维修总费用',
    createTime datetime comment '创建时间',
    updateTime datetime comment '修改时间',
    estDelivTime datetime comment '预计交付时间',
    currentRepairStatus int comment '目前维修状态',
    foreign key (clientId) references client(clientId),
    foreign key (vfi) references vehicleFault(vfi),
    foreign key (empId) references emp(empId)
) comment '维修委托书';

# 维修委托内容（维修项目）
create table repairtask
(
    riid int unsigned auto_increment primary key comment '维修项目编号',
    repairitem varchar(300) comment '维修项目',
    needComponent varchar(300) comment '所需零件',
    pricePerComponent double comment '零件单价',
    totalComponentPrice double comment '零件总金额',
    rai int unsigned comment '委托书id',
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
    foreign key (riid) references repairtask(riid),
    foreign key (empId) references emp(empId)
) comment '维修派工单';