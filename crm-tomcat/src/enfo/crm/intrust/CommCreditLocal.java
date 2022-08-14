package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface CommCreditLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 功能：查询字典树 SP_QUERY_TDICTPARAM_TREE
	 * 
	 * @IN_TYPE_ID INT,
	 * @IN_TYPE_VALUE VARCHAR(10) =''
	 * @param integerValue
	 * @param value
	 * @return
	 * @throws BusiException
	 */
	String listTreeBySql(Integer integerValue, String value) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * 功能：查询字典树 SP_QUERY_TDICTPARAM_TREE
	 * 
	 * @IN_TYPE_ID INT,
	 * @IN_TYPE_VALUE VARCHAR(10) =''
	 * @param integerValue
	 * @param value
	 * @return
	 * @throws BusiException
	 */
	String parentTreeBySql(String channel, String value) throws BusiException;

}