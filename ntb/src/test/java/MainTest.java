import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.utility.Utlity;

public class MainTest {

	public MainTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//		testMap2Str();
//		testDataTypeCheck();
		testPOJOJsonGen();
	}
	
	
	
	private static void testPOJOJsonGen() {
		// TODO Auto-generated method stub
		Bank bank1 = new Bank();
		bank1.setUuid("uuid");
		bank1.setLogo("logo");
		bank1.setName("name");
		bank1.setStatus("status");
		bank1.setCreator("creator");
		System.out.println(bank1.toString());
		
		
		Bank bank2 = new Bank();
		bank2.setLogo("logo");
		bank2.setStatus("status");
		bank2.setUuid("uuid");
		bank2.setCreator("creator");
		bank2.setName("name");
		System.out.println(bank2.toString());
		
	}

	private static void testDataTypeCheck() {
		// TODO Auto-generated method stub
		System.out.println(Utlity.isNumeric("002321.324"));
	}

	private static void testMap2Str() {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("a1", "aa1");
		map1.put("a2", "aa2");
		map1.put("a3", "aa3");
		map1.put("a4", "aa4");
		map1.put("a5", "aa5");
		map1.put("a6", "aa6");
		map1.put("a7", "aa7");
		System.out.println(map1.toString());
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("a1", "aa1");
		map2.put("a2", "aa2");
		map2.put("a4", "aa4");
		map2.put("a3", "aa3");
		map2.put("a8", "aa5");
		map2.put("a6", "aa6");
		map2.put("a7", "aa7");
		System.out.println(map2.toString());
		
		List<Bank> banks=  getList(new Bank());
	}

	public static <T> List<T> getList(T t) {
		System.out.println(t.getClass());
		return null;
	}

}
