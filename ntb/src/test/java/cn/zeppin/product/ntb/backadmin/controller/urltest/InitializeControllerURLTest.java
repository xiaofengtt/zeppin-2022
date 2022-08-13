/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller.urltest;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author elegantclack
 *
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration({"classpath*:/applicationContext*.xml","classpath*:/spring-mvc.xml"}) 
public class InitializeControllerURLTest {
	
	@Autowired  
	private WebApplicationContext wac; 
	
	private MockMvc mockMvc; 
	/**
	 * 
	 */
	public InitializeControllerURLTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Before  
    public void setup() {   
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();  
    } 
	
	
	@Test  
    public void testBuild() throws Exception {  
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/backadmin/initialize/build");
//		requestBuilder = requestBuilder.param("userName", "admin");
//		requestBuilder = requestBuilder.param("password", "1");
		ResultActions resultAction = mockMvc.perform(requestBuilder);
		resultAction.andExpect(MockMvcResultMatchers.status().isFound());
		resultAction.andDo(MockMvcResultHandlers.print()).andReturn();
    } 
    

}
