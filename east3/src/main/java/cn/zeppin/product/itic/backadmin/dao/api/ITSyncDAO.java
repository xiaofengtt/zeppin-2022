/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.api;

import java.util.Map;


public interface ITSyncDAO {
	Map<String,Object> sync(Map<String,Boolean> map);
	
	Map<String,Object> middleSync(Map<String,Boolean> map);
}
