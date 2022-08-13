package com.whaty.platform.interfa;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.whaty.platform.message.bean.MsgInfo;
import com.whaty.platform.message.service.MessageService;

public class MessageFactory {

		private static  MessageFactory messagefactory;
		
		private MessageFactory(){}
		
		public static MessageFactory getInstance(){
			if(messagefactory == null){
				return new MessageFactory();
			}else
				return messagefactory;
		}
		
		public static void sendMessage(String toUserLoginId,String fromUserLoginId,String fromName,String canReply,String content){
			
			HttpServletRequest request = ServletActionContext.getRequest();
//			ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
			ServletContext servletContext = (ServletContext) request.getSession().getServletContext();
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			
			MessageService ms = (MessageService)wc.getBean("messageService");
				MsgInfo msg = new MsgInfo();
				msg.setUserId(toUserLoginId);
				msg.setFromUserId(fromUserLoginId);
				msg.setFromUserName(fromName);
				msg.setCanReply(canReply);
				msg.setContent(content);
				ms.save(msg);
		}
		
		public static void main(String[] args){
			MessageFactory f = MessageFactory.getInstance();
			f.sendMessage("001", "a", "a", "1", "你是不是想我啦");
		}
}
