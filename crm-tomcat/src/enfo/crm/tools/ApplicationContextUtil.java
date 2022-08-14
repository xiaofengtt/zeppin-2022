package enfo.crm.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.listener.TimeApplicationListener;

/**
 * 
 * ApplicationContext Util
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		
		//context.publishEvent(new TimeApplicationListener());
	}

	public static ApplicationContext getApplicationContext() {  
        return context;  
    }
}
