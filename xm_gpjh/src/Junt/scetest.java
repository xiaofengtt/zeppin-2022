package Junt;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.service.GeneralService;

public class scetest {

	static GeneralService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/config/applicationContext.xml");
		service = (GeneralService) context.getBean("generalService");
	}

	@Test
	public void testinfoa(){
		try{
		DetachedCriteria dc = DetachedCriteria.forClass(PrVoteRecord.class);
		DetachedCriteria dcPeVotePaper = dc.createCriteria("peVotePaper", "peVotePaper");
		dcPeVotePaper.add(Restrictions.eq("id", "52cce2fd2514f0e801252418d02d1800"));
		List<PrVoteRecord> list = service.getList(dc);
		System.out.println("list ==============>"+list.size());
		
//		while(){
//			
//		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
