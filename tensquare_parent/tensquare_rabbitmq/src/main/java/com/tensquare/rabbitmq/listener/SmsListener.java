package com.tensquare.rabbitmq.listener;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import util.SmsUtil;

@Component
@RabbitListener(queues="sms")
public class SmsListener {
	
	@Value("${aliyun.sms.template_code}")
	private String template_code;//模板编号
	@Value("${aliyun.sms.sign_name}")
	private String sign_name;//签名
	
	@Autowired
	private SmsUtil smsUtil;
	
	@RabbitHandler
	public void showMessage(Map<String,String> message) throws ClientException{
		System.out.println("手机号："+message.get("mobile"));
		System.out.println("短信验证码："+message.get("code"));
		
		String mobile=message.get("mobile");	
		String signName=sign_name;
		String templateCode=template_code;	
		String templateParam="{\"code\":\""+ message.get("code") +"\"}";
		
		
		System.out.println("templateCode:"+templateCode);
		System.out.println("templateParam:"+templateParam);
		
		
		
		//发送短信
//		SendSmsResponse sendSmsResponse=smsUtil.sendSms(mobile, signName, templateCode, templateParam);
//		
//		//打印回调信息
//	    System.out.println("Code=" + sendSmsResponse.getCode());
//        System.out.println("Message=" + sendSmsResponse.getMessage());
//        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
//        System.out.println("BizId=" + sendSmsResponse.getBizId());		
		
		}
	
	
}
