package enfo.crm.intrust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;

@Component(value="commCredit")
public class CommCreditBean extends enfo.crm.dao.IntrustBusiExBean implements CommCreditLocal {
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CommCreditLocal#listTreeBySql(java.lang.Integer, java.lang.String)
	 */
    @Override
	public String listTreeBySql(Integer integerValue, String value)
            throws BusiException {
		List list = null;
		List arrAyList = new ArrayList();
		Boolean leaf = new Boolean(false);
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(integerValue, new Integer(0));
		param[1] = Utility.trimNull(value);
		list = super.listBySql("{call SP_QUERY_TDICTPARAM_TREE(?,?)}", param);
		for(int i=0;i<list.size();i++){
			HashMap hmap = new HashMap();
			Map map = (Map)list.get(i);
			if(Utility.parseInt(Utility.trimNull(map.get("BOTTOM_FLAG")),0)==0)
				leaf = new Boolean(false);
			else
				leaf = new Boolean(true);
			hmap.put("id",Utility.trimNull(map.get("TYPE_VALUE")));
			hmap.put("text",Utility.trimNull(map.get("TYPE_CONTENT")));
			hmap.put("leaf",leaf);
			arrAyList.add(hmap);
		}
		String ret = enfo.crm.tools.JsonUtil.object2json(arrAyList);
		
		ret = Utility.replaceAll(ret, "\"false\"", "false");
		ret = Utility.replaceAll(ret, "\"true\"", "true");
		return ret;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CommCreditLocal#parentTreeBySql(java.lang.String, java.lang.String)
	 */
    @Override
	public String parentTreeBySql(String channel, String value)
            throws BusiException {
		List list = null;
		List arrAyList = new ArrayList();
		Boolean leaf = new Boolean(false);
		Object[] param = new Object[6];
		param[0] = new Integer(1);
		param[1] = new Integer(0);
		param[2] = null;
		param[3] = null;
		param[4] = Utility.trimNull(channel);
		param[5] = Utility.parseInt(value, null);
		list = super.listBySql("{call SP_QUERY_TCHANNEL(?,?,?,?,?,?)}", param);
		for(int i=0;i<list.size();i++){
			HashMap hmap = new HashMap();
			Map map = (Map)list.get(i);
			if(Utility.parseInt(Utility.trimNull(map.get("BOTTOM_FLAG")),0)==0)
				leaf = new Boolean(false);
			else
				leaf = new Boolean(true);
			hmap.put("id",Utility.trimNull(map.get("CHANNEL_ID")));
			hmap.put("text",Utility.trimNull(map.get("CHANNEL_NAME")));
			hmap.put("leaf",leaf);
			arrAyList.add(hmap);
		}
		String ret = enfo.crm.tools.JsonUtil.object2json(arrAyList);
		
		ret = Utility.replaceAll(ret, "\"false\"", "false");
		ret = Utility.replaceAll(ret, "\"true\"", "true");
		return ret;
    }
}