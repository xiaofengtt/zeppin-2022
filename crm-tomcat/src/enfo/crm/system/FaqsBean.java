 
package enfo.crm.system;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.FaqsVO;
import enfo.crm.vo.TreeVO;
import net.sf.json.JSONArray;

@Component(value="faqs")
public class FaqsBean extends enfo.crm.dao.CrmBusiExBean implements FaqsLocal {
	private String listSql = "{call SP_QUERY_TFAQS(?,?,?,?,?)}";//查询知识库信息
	
	private String appendSql = "{?=call SP_ADD_TFAQS(?,?,?,?,?,?)}";//新增知识库信息
	
	private String modiSql = "{?=call SP_MODI_TFAQS(?,?,?,?,?,?)}";//修改知识库信息
	
	private String deleteSql = "{?=call SP_DEL_TFAQS(?,?)}";//删除知识库信息表
	
	private String searchSql = "{call SP_SERACH_TFAQS(?,?)}"; //检索
	
	private String countSql = "{?=call SP_COUNT_TFAQS(?,?,?)}";//记录支持反对数
	
	private String listClassSql = "{call SP_QUERY_FAQ_CLASS(?,?)}";//查询知识库分类
	
	private String addClassSql = "{?=call SP_ADD_FAQ_CLASS(?,?,?)}";//增加分类
	
	private String modiClassSql = "{?=call SP_MODI_FAQ_CLASS(?,?,?)}";//修改分类
	
	private String deleteClassSql = "{?=call SP_DEL_FAQ_CLASS(?,?)}";//删除分类
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#listByMul(enfo.crm.vo.FaqsVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listByMul(FaqsVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[2] = Utility.trimNull(vo.getWiki_class());
		params[3] = Utility.trimNull(vo.getSearch_key());
		params[4] = Utility.trimNull(vo.getSortfield());
		
		rsList = super.listProcPage(listSql,params,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#searchFaqs(enfo.crm.vo.FaqsVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList searchFaqs(FaqsVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.trimNull(vo.getFaq_keywords());
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		rsList = super.listProcPage(searchSql,params,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#append(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public Integer append(FaqsVO vo) throws Exception{
		Object[]param = new Object[5];
		param[0] = Utility.trimNull(vo.getFaq_title());
		param[1] = Utility.trimNull(vo.getFaq_keywords());
		param[2] = Utility.trimNull(vo.getFaq_content());
		param[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[4] = Utility.trimNull(vo.getFaq_class_no());	
		return (Integer)super.cudProc(appendSql, param, 7, Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#listBySql(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public List listBySql(FaqsVO vo) throws Exception{
		List list = null;
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[2] = Utility.trimNull(vo.getWiki_class());
		params[3] = Utility.trimNull(vo.getSearch_key());
		params[4] = Utility.trimNull(vo.getSortfield());
		list = super.listBySql(listSql,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#modi(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public void modi(FaqsVO vo) throws Exception{
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		param[1] = Utility.trimNull(vo.getFaq_title());
		param[2] = Utility.trimNull(vo.getFaq_keywords());
		param[3] = Utility.trimNull(vo.getFaq_content());
		param[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[5] = Utility.trimNull(vo.getFaq_class_no());	
		super.cudProc(modiSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#delete(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public void delete(FaqsVO vo) throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		super.cudProc(deleteSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#countFaq(enfo.crm.vo.FaqsVO, java.lang.Integer)
	 */
	@Override
	public void countFaq(FaqsVO vo , Integer flag) throws Exception{
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		param[1] = flag;
		param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		super.cudProc(countSql,param);
		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#listFaqClass(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public List listFaqClass(FaqsVO vo) throws Exception{
		List list = null;
		Object[] param = new Object[2];
		param[0] = Utility.trimNull(vo.getFaq_class_no());	
		param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		list = super.listBySql(listClassSql,param);
		return list;
	}
    /* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#queryClassJosn(enfo.crm.vo.FaqsVO, java.util.Locale)
	 */
	@Override
	public String queryClassJosn(FaqsVO vo,java.util.Locale clientLocale) throws Exception{
		List rsList = new ArrayList();
		
		TreeVO nodep = new TreeVO();
		//主节点设置
		nodep.setId(new Integer(0));
		nodep.setFaq_class_no("0");
		nodep.setName("知识库分类");
		nodep.setParentId(new Integer(0));
		nodep.setOpen(true);
		nodep.setBottom_flag(new Integer(0));
		rsList.add(nodep);
		recursive(nodep.getNodes(),vo.getInput_man());
		String json  = JSONArray.fromObject(rsList).toString();
		System.out.println(">>"+json);
		return json;
	}
	//递归List集合组合节点信息
	private void recursive(List dataList,Integer input_man) throws Exception{
		FaqsVO vo = new FaqsVO();
		vo.setInput_man(input_man);
		List list = this.listFaqClass(vo);
		if(list.size()>0){
			for(int i=0;i<list.size();i++)
			{	
			    Map map = (Map)list.get(i);
				String faq_class_no  = Utility.trimNull(map.get("FAQ_CLASS_NO"));
				String faq_class_name = Utility.trimNull(map.get("FAQ_CLASS_NAME"));
				TreeVO tnode = null;
				tnode = new TreeVO();
				tnode.setFaq_class_no(faq_class_no);
				tnode.setName(faq_class_name);
				tnode.setParentId(new Integer(0));
				tnode.setOpen(true);
				tnode.setBottom_flag(new Integer(1));
				dataList.add(tnode);
			}	
		}
	}
       
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#addClass(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public String addClass(FaqsVO vo) throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.trimNull(vo.getFaq_class_name());
		param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		int outParamPos = 4 ;
		int outParamType = Types.VARCHAR;	
		return Utility.trimNull(super.cudProc(addClassSql,param,outParamPos,outParamType));
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#modiClass(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public void modiClass(FaqsVO vo) throws Exception{
		Object[] param = new Object[3];
		param[0] = Utility.trimNull(vo.getFaq_class_no());
		param[1] = Utility.trimNull(vo.getFaq_class_name());
		param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		super.cudProc(modiClassSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.FaqsLocal#deleteClass(enfo.crm.vo.FaqsVO)
	 */
	@Override
	public void deleteClass(FaqsVO vo) throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.trimNull(vo.getFaq_class_no());
		param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		super.cudProc(deleteClassSql,param);
	}
	
}