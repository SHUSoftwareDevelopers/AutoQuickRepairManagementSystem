## 汽车维修管理系统SQL表

### 账号登录表

| 登录账号 | 登录密码 | 账号类别 | 用户名 | 用户头像 | 用户邮箱 | 用户手机号 | 创建时间 | 更改时间 |
| -------- | -------- | -------- | ------ | -------- | -------- | ---------- | -------- | -------- |
|          |          |          |        |          |          |            |          |          |

### 客户个人信息表

| 客户编号 | 客户名称（实名） | 客户性质（个人/单位） | 折扣率 | 业务联系人 | 业务联系电话 | 登录账号 |
| -------- | ---------------- | --------------------- | ------ | ---------- | ------------ | -------- |
|          |                  |                       |        |            |              |          |

### 车辆信息

| 车牌号 | 车型 | 车架号 | 颜色 | 所属客户（编号） |
| ------ | ---- | ------ | ---- | ---------------- |
|        |      |        |      |                  |

### 维修厂员工表

| 员工编号 | 登录账号 | 登录密码 | 员工姓名（实名） | 员工工种 | 登录账号 |
| -------- | -------- | -------- | ---------------- | -------- | -------- |
|          |          |          |                  |          |          |

### 车辆的故障信息表

| 故障单id | 维修类型 | 作业分类 | 结算方式 | 受维修车架号 |
| -------- | -------- | -------- | -------- | ------------ |
|          |          |          |          |              |

### 维修委托书

| 委托书id | 客户编号 | 故障单id | 业务员员工编号 | 行驶里程 | 随车物品 | 检查结果 | 维修方案 | 是否洗车 | 维修总费用 | 旧件是否带回 | 创建时间 | 修改时间 | 预计交付时间 | 目前维修状态 |
| -------- | -------- | -------- | -------------- | -------- | -------- | -------- | -------- | -------- | ---------- | ------------ | -------- | -------- | ------------ | ------------ |
|          |          |          |                |          |          |          |          |          |            |              |          |          |              |              |

### 维修委托内容（维修项目）

| 维修项目编号 | 委托书id | 维修项目 | 所需零件 | 零件单价 | 零件总金额 |
| ------------ | -------- | -------- | -------- | -------- | ---------- |
|              |          |          |          |          |            |

### 维修派工单

| 维修派工单id | 维修项目编号 | 工时 | 工时单价 | 维修员编号 | 维修员工种 | 是否完成 |
| ------------ | ------------ | ---- | -------- | ---------- | ---------- | -------- |
|              |              |      |          |            |            |          |
