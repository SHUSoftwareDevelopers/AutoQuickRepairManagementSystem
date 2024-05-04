-- bills表
INSERT INTO autoquickrepairdatabase.bills (billId, clientId, vfi, discountRate, paymentMethod, payment, payTime) VALUES (5, 1, 1, 0, 0, 160, '2024-05-04 15:58:12');

-- client表
INSERT INTO autoquickrepairdatabase.client (clientId, clientName, clientType, discountRate, businessContact, businessTele, account) VALUES (1, 'Pingfeng Lu', 0, 0, 'Pingfeng Lu', '17721260414', 'clientTest001');
INSERT INTO autoquickrepairdatabase.client (clientId, clientName, clientType, discountRate, businessContact, businessTele, account) VALUES (2, 'Jane Smith', 0, 0.1, 'Jane Smith', '22222222', 'clientTest002');
INSERT INTO autoquickrepairdatabase.client (clientId, clientName, clientType, discountRate, businessContact, businessTele, account) VALUES (3, 'Alice Johnson', 1, 0.3, 'Shanghai University', '33333333', 'clientTest003');

-- emp表
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (1, '王志伟', 0, 'cecily991');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (14, '陆诗雨', 0, '1937714332');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (16, '张师傅', 2, 'yifeizhang');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (17, 'James Smith', 1, 'empTest001');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (18, 'Mechanic Johnson', 2, 'empTest002');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (19, 'Welder Brown', 3, 'empTest003');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (20, 'Painter Lee', 4, 'empTest004');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (21, 'Receptionist Davis', 5, 'empTest005');
INSERT INTO autoquickrepairdatabase.emp (empId, empName, empType, account) VALUES (22, 'Salesperson Wilson', 3, 'empTest006');

-- 派工单表
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (1, 1, 20, 1, 20, 4, 1, '2024-04-19 00:29:56', '2024-04-19 00:30:00');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (2, 0.5, 20, 2, 20, 4, 1, '2024-04-19 00:59:28', '2024-04-26 23:38:02');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (3, 2, 100, 3, 16, 2, 1, '2024-04-19 00:31:39', '2024-04-28 15:21:21');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (4, 0.5, 30, 4, 18, 2, 1, '2024-04-19 00:32:10', '2024-04-19 00:32:12');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (5, 1, 20, 8, 20, 4, 1, '2024-04-26 17:51:42', '2024-04-29 13:10:09');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (6, 0.5, 30, 9, 19, 3, 1, '2024-04-26 17:56:12', '2024-04-26 20:40:07');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (7, 3, 100, 5, 16, 2, 1, '2024-04-28 14:21:11', '2024-04-28 15:39:55');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (8, 1, 90, 6, 19, 3, 1, '2024-04-28 14:21:49', '2024-04-28 16:11:22');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (9, 3, 300, 7, 18, 2, 1, '2024-04-28 14:22:02', '2024-04-28 15:26:42');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (10, 1, 50, 6, 16, 2, 1, '2024-04-28 15:41:51', '2024-04-28 15:54:18');
INSERT INTO autoquickrepairdatabase.maintenancedispatchorder (mdoid, workLength, pricePerhour, riid, empId, empType, isComplete, createTime, updateTime) VALUES (11, 1, 50, 6, 18, 2, 1, '2024-04-28 15:41:59', '2024-04-28 16:05:08');

-- 任务进行表
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (1, 1, 14, 20, 3, '2024-04-19 00:34:23', '2024-04-19 00:34:25');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (2, 2, 14, 20, 3, '2024-04-19 00:34:50', '2024-04-26 23:38:02');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (3, 3, 16, 16, 4, '2024-04-19 00:35:30', '2024-04-26 21:13:32');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (4, 4, 18, 18, 3, '2024-04-19 00:35:47', '2024-04-19 00:35:48');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (5, 5, 14, 20, 3, '2024-04-26 18:37:42', '2024-04-29 13:10:09');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (6, 6, 19, 19, 3, '2024-04-26 19:50:20', '2024-04-26 20:40:07');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (7, 3, 14, 18, 4, '2024-04-26 21:13:32', '2024-04-26 21:21:53');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (8, 3, 14, 16, 2, '2024-04-26 21:21:53', '2024-04-26 22:10:17');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (9, 3, 14, 16, 4, '2024-04-26 22:09:31', '2024-04-28 13:08:57');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (11, 3, 14, 16, 4, '2024-04-28 13:11:35', '2024-04-28 13:11:58');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (14, 9, 16, 16, 4, '2024-04-28 14:31:35', '2024-04-28 15:15:22');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (15, 9, 14, 18, 3, '2024-04-28 15:15:22', '2024-04-28 15:26:42');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (16, 3, 14, 16, 3, '2024-04-28 15:16:53', '2024-04-28 15:21:21');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (17, 7, 16, 16, 3, '2024-04-28 15:34:33', '2024-04-28 15:39:55');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (18, 10, 14, 19, 2, '2024-04-28 15:43:53', '2024-04-28 15:48:00');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (19, 11, 18, 18, 3, '2024-04-28 15:46:29', '2024-04-28 16:05:08');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (20, 10, 14, 19, 4, '2024-04-28 15:48:29', '2024-04-28 15:51:37');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (21, 10, 14, 16, 3, '2024-04-28 15:51:37', '2024-04-28 15:54:18');
INSERT INTO autoquickrepairdatabase.ongoingtable (ogid, mdoid, assignId, receiveId, status, createTime, endTime) VALUES (22, 8, 19, 19, 3, '2024-04-28 16:10:45', '2024-04-28 16:11:22');

-- 维修委托书表
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (1, 1, 1, 21, 500, '车座抱枕，车辆文件', '小型剐蹭掉漆', '重新上漆打光处理', 1, 1, 160, '2024-04-19 00:14:29', '2024-04-19 00:12:20', '2024-06-19 00:12:21');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (2, 2, 2, 21, 371, '双肩包，茶杯', '发动机受损，大面积形变', '维修发动机并更换发动机盖', 1, 0, 4415, '2024-04-19 00:14:40', '2024-04-19 00:14:42', '2024-05-19 00:15:16');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (3, 3, 3, 21, 1841, '书籍，充电器', '制冷系统受损，储油装置漏油，变速装置严重损坏', '更换制冷系统，修补储油装置，更换变速装置', 1, 1, 14690, '2024-04-19 00:18:10', '2024-04-19 00:18:12', '2024-06-19 00:18:14');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (4, 1, 5, 21, 500, '红米手机Note 13 Pro,小爱智能音箱', '小型剐蹭形变', '重新上漆平整处理', 1, 1, 195, '2024-04-25 16:06:21', '2024-04-29 13:10:09', '2024-05-29 00:15:16');

-- 维修项目表
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (1, 1, 1, 21, 500, '车座抱枕，车辆文件', '小型剐蹭掉漆', '重新上漆打光处理', 1, 1, 160, '2024-04-19 00:14:29', '2024-04-19 00:12:20', '2024-06-19 00:12:21');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (2, 2, 2, 21, 371, '双肩包，茶杯', '发动机受损，大面积形变', '维修发动机并更换发动机盖', 1, 0, 4415, '2024-04-19 00:14:40', '2024-04-19 00:14:42', '2024-05-19 00:15:16');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (3, 3, 3, 21, 1841, '书籍，充电器', '制冷系统受损，储油装置漏油，变速装置严重损坏', '更换制冷系统，修补储油装置，更换变速装置', 1, 1, 14690, '2024-04-19 00:18:10', '2024-04-19 00:18:12', '2024-06-19 00:18:14');
INSERT INTO autoquickrepairdatabase.repairauthorization (rai, clientId, vfi, empId, mileage, onboardItems, checkResult, repairMethod, isWashCar, isGetFormerComponent, totalRepairCost, createTime, updateTime, estDelivTime) VALUES (4, 1, 5, 21, 500, '红米手机Note 13 Pro,小爱智能音箱', '小型剐蹭形变', '重新上漆平整处理', 1, 1, 195, '2024-04-25 16:06:21', '2024-04-29 13:10:09', '2024-05-29 00:15:16');

-- user表
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('1937714332', 'e10adc3949ba59abbe56e057f20f883e', 0, 'shiyulu', 'https://shiyulu-autoquickrepair.oss-cn-shanghai.aliyuncs.com/ProgrammeDog2.jpg?Expires=1713454075&OSSAccessKeyId=TMP.3KiPEwzd1ZwDFXxnYLjQUva8aJM8ArqNyKVaf149n8P4PX9srkeqi5LxnEQ97AzVsAbm2FsBT6NrsRMz5keKGxTMfRmwSv&Signature=Pt4HIAqTCylavBP6nmZazQgWcp0%3D', '1937714332@qq.com', '18018641963', '2024-03-29 17:53:06', '2024-04-24 21:42:20');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('cecily991', 'e10adc3949ba59abbe56e057f20f883e', 0, 'wang', 'https://shiyulu-autoquickrepair.oss-cn-shanghai.aliyuncs.com/ProgrammeDog.jpg?Expires=1713454102&OSSAccessKeyId=TMP.3KiPEwzd1ZwDFXxnYLjQUva8aJM8ArqNyKVaf149n8P4PX9srkeqi5LxnEQ97AzVsAbm2FsBT6NrsRMz5keKGxTMfRmwSv&Signature=P9aXQc9js%2FqEP4DH%2Feh8bJTfXIA%3D', '199@qq.com', '15000162680', '2024-01-08 22:19:23', '2024-01-08 22:19:23');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('clientTest001', 'e10adc3949ba59abbe56e057f20f883e', 6, 'John Doe', 'avatar1.jpg', 'john@example.com', '12345678901', '2024-03-28 23:52:05', '2024-03-28 23:52:05');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('clientTest002', 'e10adc3949ba59abbe56e057f20f883e', 6, 'Jane Smith', 'avatar2.jpg', 'jane@example.com', '23456789012', '2024-03-28 23:52:05', '2024-03-28 23:52:05');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('clientTest003', 'e10adc3949ba59abbe56e057f20f883e', 6, 'Alice Johnson', null, 'alice@example.com', '34567890123', '2024-03-28 23:52:05', '2024-03-28 23:52:05');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest001', 'e10adc3949ba59abbe56e057f20f883e', 1, 'James Smith', null, 'james@example.com', '45678901234', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest002', 'e10adc3949ba59abbe56e057f20f883e', 2, 'Mechanic Johnson', null, 'mechanic@example.com', '56789012345', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest003', 'e10adc3949ba59abbe56e057f20f883e', 3, 'Welder Brown', null, 'welder@example.com', '67890123456', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest004', 'e10adc3949ba59abbe56e057f20f883e', 4, 'Painter Lee', null, 'painter@example.com', '78901234567', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest005', 'e10adc3949ba59abbe56e057f20f883e', 5, 'Receptionist Davis', null, 'receptionist@example.com', '89012345678', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('empTest006', 'e10adc3949ba59abbe56e057f20f883e', 3, 'Salesperson Wilson', null, 'salesperson@example.com', '90123456789', '2024-03-28 23:56:53', '2024-03-28 23:56:53');
INSERT INTO autoquickrepairdatabase.user (account, password, userType, username, avatar, email, phone, createTime, updateTime) VALUES ('yifeizhang', 'e10adc3949ba59abbe56e057f20f883e', 2, '张飞', null, 'feiZhang@gmail.com', '17321260414', '2024-04-18 16:24:39', '2024-04-18 16:24:43');

-- vehicle表
INSERT INTO autoquickrepairdatabase.vehicle (vin, license, vehicleType, vehicleColor, clientId, createTime, updateTime) VALUES ('1GNSKJKCXFR158903', '沪C-SE213', '2', 'silver', 1, '2024-04-18 23:51:20', '2024-04-18 23:51:25');
INSERT INTO autoquickrepairdatabase.vehicle (vin, license, vehicleType, vehicleColor, clientId, createTime, updateTime) VALUES ('1GNSKJKCXFR158904', '沪C-SE214', '2', 'white', 1, '2024-04-25 00:01:55', '2024-04-25 00:41:36');
INSERT INTO autoquickrepairdatabase.vehicle (vin, license, vehicleType, vehicleColor, clientId, createTime, updateTime) VALUES ('5UXFA53502LX07351', '沪C-SH086', '0', 'black', 2, '2024-04-18 23:52:28', '2024-04-18 23:52:30');
INSERT INTO autoquickrepairdatabase.vehicle (vin, license, vehicleType, vehicleColor, clientId, createTime, updateTime) VALUES ('5UXFA53502LX07352', '沪A-88888', '2', 'black', 2, '2024-04-26 15:08:31', '2024-04-26 15:08:31');
INSERT INTO autoquickrepairdatabase.vehicle (vin, license, vehicleType, vehicleColor, clientId, createTime, updateTime) VALUES ('JH4DC53024C005771', '沪B-HA088', '1', 'white', 3, '2024-04-18 23:53:18', '2024-04-18 23:53:20');

-- vehiclefault表
INSERT INTO autoquickrepairdatabase.vehiclefault (vfi, maintenanceType, taskClassification, paymentMethod, vin, createTime, updateTime, repairStatus, whetherPay) VALUES (1, 0, 2, 0, '1GNSKJKCXFR158903', '2024-04-18 23:57:31', '2024-04-18 23:57:33', 1, 1);
INSERT INTO autoquickrepairdatabase.vehiclefault (vfi, maintenanceType, taskClassification, paymentMethod, vin, createTime, updateTime, repairStatus, whetherPay) VALUES (2, 1, 1, 1, '5UXFA53502LX07351', '2024-04-18 23:58:15', '2024-04-18 23:58:16', 1, 0);
INSERT INTO autoquickrepairdatabase.vehiclefault (vfi, maintenanceType, taskClassification, paymentMethod, vin, createTime, updateTime, repairStatus, whetherPay) VALUES (3, 0, 0, 2, 'JH4DC53024C005771', '2024-04-18 23:58:41', '2024-04-18 23:58:42', 1, 0);
INSERT INTO autoquickrepairdatabase.vehiclefault (vfi, maintenanceType, taskClassification, paymentMethod, vin, createTime, updateTime, repairStatus, whetherPay) VALUES (5, 1, 2, 0, '1GNSKJKCXFR158904', '2024-04-25 15:18:01', '2024-04-25 15:35:45', 1, 0);
INSERT INTO autoquickrepairdatabase.vehiclefault (vfi, maintenanceType, taskClassification, paymentMethod, vin, createTime, updateTime, repairStatus, whetherPay) VALUES (6, 1, 1, 1, '5UXFA53502LX07352', '2024-04-26 15:13:01', '2024-04-26 15:13:01', 0, 0);
