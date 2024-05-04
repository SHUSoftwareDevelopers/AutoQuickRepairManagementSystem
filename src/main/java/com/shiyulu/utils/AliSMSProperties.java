package com.shiyulu.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class AliSMSProperties {
    private String SMSACCESSKEYID;
    private String SMSACCESSKEYSECRET;
    private String SMSENDPOINT;
    private String SMSTEMPLATECODE;
    private String SMSSIGNNAME;
}
