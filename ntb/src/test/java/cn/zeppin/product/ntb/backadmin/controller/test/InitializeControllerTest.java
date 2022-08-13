/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zeppin.product.ntb.backadmin.controller.InitializeController;

/**
 * @author elegantclack
 *
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath*:/applicationContext*.xml","classpath*:/spring-mvc.xml"}) 
public class InitializeControllerTest {

	@Autowired  
	private InitializeController initializeController;
	
    private MockHttpServletRequest request;  
	private MockHttpServletResponse response; 
    
	/**
	 * 
	 */
	public InitializeControllerTest() {
		// TODO Auto-generated constructor stub
		
	}
	
    //执行测试方法之前初始化模拟request,response  
    @Before    
    public void setUp(){    
        request = new MockHttpServletRequest();      
        request.setCharacterEncoding("UTF-8");      
        response = new MockHttpServletResponse();      
    }         
    
    /** 
     *  
     * @Title：testBuild
     * @Description: 测试初始化  
     */  
    @Test  
    public void testBuild() {   
        try {  
//        	request.setParameter("userName", "admin");
//        	request.setParameter("password", "2");
        	Assert.assertEquals(null, initializeController.build()) ;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
