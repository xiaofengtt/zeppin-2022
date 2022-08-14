 
package enfo.crm.callcenter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SmsTemplateVO;

@Component(value="smsTemplate")
public class SmsTemplateBean extends enfo.crm.dao.CrmBusiExBean implements SmsTemplateLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#append(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public void append(SmsTemplateVO vo) throws BusiException{
			String summary = "添加短信模板信息，操作员："+ vo.getInput_man();
			String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(40308,N'添加短信模板信息',"
												+vo.getInput_man()+",N'"+summary+"')";
//			StringBuffer sqlStr = new StringBuffer("INSERT INTO TSmsTemplates(Title,Content,TempType,TempTypeName,AutoSendFlag,PRIORITY) VALUES");
			StringBuffer sqlStr = new StringBuffer("INSERT INTO TSmsTemplates(Title,Content,TempType,TempTypeName) VALUES");
			try {
				sqlStr.append("(");
				sqlStr.append("N'"+vo.getTitle()+"',");
				sqlStr.append("N'"+vo.getContent()+"',");
				sqlStr.append("N'"+vo.getTempType()+"',");
				sqlStr.append("N'"+Argument.getDictParamName(4038,vo.getTempType())+"'");
//				sqlStr.append(vo.getAutosendflag()+",");
//				sqlStr.append(vo.getPriority());
				sqlStr.append(")");
				
				try {
					super.executeSql(sqlStr.toString());
					super.executeSql(loglistSql);
				} catch (BusiException e) {
					throw new BusiException("添加短信模板信息失败:" + e.getMessage());
				}
				
			} catch (Exception e1) {
				throw new BusiException("查找模板类别名称信息失败:" + e1.getMessage());
			}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#addSms(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public void addSms(SmsTemplateVO vo) throws BusiException{
		String sql  = "{?=call SP_ADD_TSMSTEMPLATES(?,?,?,?,?,?)}";
			Object[] params = new Object[6];
			params[0] = Utility.trimNull(vo.getTitle());
			params[1] = Utility.trimNull(vo.getContent());
			params[2] = Utility.trimNull(vo.getTempType());
			params[3] = Utility.parseInt(vo.getAutosendflag(),new Integer(0));
			params[4] = Utility.parseInt(vo.getPriority(), new Integer(888));
			params[5] = Utility.parseInt(vo.getInput_man(), new Integer(888));
			try {
				super.cudProc(sql, params);
			} catch (Exception e1) {
				throw new BusiException("新增模板类别信息失败:" + e1.getMessage());
			}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#modiSms(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public void modiSms(SmsTemplateVO vo) throws BusiException{
		String sql  = "{?=call SP_MODI_TSMSTEMPLATES(?,?,?,?,?,?,?)}";
			Object[] params = new Object[7];
			params[0] = Utility.parseInt(vo.getTempId(),new Integer(0));
			params[1] = Utility.trimNull(vo.getTitle());
			params[2] = Utility.trimNull(vo.getContent());
			params[3] = Utility.trimNull(vo.getTempType());
			params[4] = Utility.parseInt(vo.getAutosendflag(),new Integer(0));
			params[5] = Utility.parseInt(vo.getPriority(), new Integer(888));
			params[6] = Utility.parseInt(vo.getInput_man(), new Integer(888));
			try {
				super.cudProc(sql, params);
			} catch (Exception e1) {
				throw new BusiException("新增模板类别信息失败:" + e1.getMessage());
			}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#modi(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public void modi(SmsTemplateVO vo) throws BusiException{
		String summary = "修改短信模板信息，操作员："+ vo.getInput_man();
		String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(40308,N'修改短信模板信息',"
										+vo.getInput_man()+",N'"+summary+"')";

		try {
			StringBuffer sqlStr = new StringBuffer(" UPDATE TSmsTemplates ");
			sqlStr.append(" SET ");
			sqlStr.append(" Title = N'"+vo.getTitle()+"',");
			sqlStr.append(" Content = N'"+vo.getContent()+"',");
			sqlStr.append(" TempType = N'"+vo.getTempType()+"',");
			sqlStr.append(" TempTypeName = N'"+Argument.getDictParamName(4038,vo.getTempType())+"'");
//			sqlStr.append(" AutoSendFlag ="+vo.getAutosendflag()+",");
//			sqlStr.append(" PRIORITY ="+vo.getPriority());
			sqlStr.append(" WHERE TempID = "+ vo.getTempId());
			
			try {
				super.executeSql(sqlStr.toString());
				super.executeSql(loglistSql);
			} catch (BusiException e) {
				throw new BusiException("修改短信模板信息失败:" + e.getMessage());
			}
		} catch (Exception e) {
			throw new BusiException("查找模板类别名称信息失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#delete(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public void delete(SmsTemplateVO vo) throws BusiException{
		String summary = "删除短信模板信息，操作员："+ vo.getInput_man();
		String loglistSql = "INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(40308,N'删除短信模板信息',"
			+vo.getInput_man()+",N'"+summary+"')";
		String sqlStr = "DELETE FROM  TSmsTemplates WHERE TempID = " + vo.getTempId();
		
		try {
			super.executeSql(sqlStr);
			super.executeSql(loglistSql);
		} catch (BusiException e) {
			throw new BusiException("删除短信模板信息失败:" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#load(enfo.crm.vo.SmsTemplateVO)
	 */
	@Override
	public Map load(SmsTemplateVO vo) throws BusiException{
		String sqlStr = "SELECT * FROM TSmsTemplates WHERE TempID = "+ vo.getTempId();
		Map ret = new HashMap();
		List retList = super.listBySql(sqlStr);
		
		if(retList.size()>0){
			ret = (Map) retList.get(0);
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.callcenter.SmsTemplateLocal#listAll(enfo.crm.vo.SmsTemplateVO, int, int)
	 */
	@Override
	public IPageList listAll(SmsTemplateVO vo,int pageIndex, int pageSize) throws BusiException{
		StringBuffer sqlStr = new StringBuffer(" SELECT * FROM TSmsTemplates ");
		sqlStr.append(" WHERE "); 
		sqlStr.append(" ( TempType =\'"+vo.getTempType()+"\' OR ISNULL(\'"+vo.getTempType()+"\',\'\') = \'\')");
		sqlStr.append(" AND (Title LIKE \'%\' + \'"+ vo.getTitle() + "\'+ \'%\' OR ISNULL(\'"+ vo.getTitle()+"\',\'\') = \'\')");
		
		try {
			return super.listSqlPage(sqlStr.toString(),pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询短信模板信息失败:" + e.getMessage());
		}
	}
}