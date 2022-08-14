package cn.product.treasuremall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//ServletContextInitializer
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//	@Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
	
	/**
     * 注册一个STOMP的endpoint,并指定使用SockJS协议
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/link").setAllowedOrigins("*").withSockJS();
    }

//    /**
//     * 通讯内容长度大小限制
//     */
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		System.out.println("org.apache.tomcat.websocket.textBufferSize");
//        servletContext.addListener(WebAppRootListener.class);
//        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","1");
//        servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","1");
//	}
}
