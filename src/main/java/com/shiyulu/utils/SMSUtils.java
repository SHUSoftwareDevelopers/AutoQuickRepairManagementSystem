package com.shiyulu.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类
 */
@Component
public class SMSUtils {
	@Autowired
	private AliSMSProperties aliSMSProperties;
	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param name 客户姓名
	 * @param license 客户车牌号
	 * @param vfi 车辆故障编号
	 */
	public void sendMessage(String signName, String templateCode,String phoneNumbers,String name,
								   String license, Integer vfi){
		String endpoint = aliSMSProperties.getSMSENDPOINT();
		String accessKeyId = aliSMSProperties.getSMSACCESSKEYID();
		String accessKeySecret = aliSMSProperties.getSMSACCESSKEYSECRET();

		DefaultProfile profile = DefaultProfile.getProfile(endpoint, accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId(endpoint);
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"name\":\""+name+"\", \"license\":\""+ license +"\", \"vfi\":\""+vfi+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
