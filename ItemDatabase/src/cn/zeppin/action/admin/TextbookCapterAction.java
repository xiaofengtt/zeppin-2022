/**  
 * This class is used for 章节操作
 * @author suijing
 * @version  
 *       1.0, 2014年7月31日 下午2:13:16  
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Textbook;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ITextBookService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * @author sj
 *
 */
public class TextbookCapterAction extends BaseAction implements IStandardAction
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2544684952052117752L;
	
	private ITextbookCapterService textbookCapterService;
	private ITextBookService textbookService;
	

	public ITextBookService getTextbookService() {
		return textbookService;
	}

	public void setTextbookService(ITextBookService textbookService) {
		this.textbookService = textbookService;
	}

	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(
			ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
	}

	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权添加章节")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "number", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	public void Add()
	{
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		TextbookCapter textbookCapter = null;
		textbookCapter = getFromFront(actionResult);
		if (textbookCapter != null)
		{
			
			textbookCapter = textbookCapterService.add(textbookCapter);
			HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.textbookCapter2Map(textbookCapter);
			actionResult.init(SUCCESS, "添加章节成功", data);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.action.base.IStandardAction#Update()
	 */
	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权添加章节")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "number", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	public void Update()
	{
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		TextbookCapter oldTextbookCapter = textbookCapterService.getById(id);
		ActionResult actionResult = new ActionResult();
		TextbookCapter textbookCapter = null;
		textbookCapter = getFromFront(actionResult);
		if (textbookCapter != null)
		{
			oldTextbookCapter.setName(textbookCapter.getName());
			oldTextbookCapter.setFcode(textbookCapter.getFcode());
			oldTextbookCapter.setNumber(textbookCapter.getNumber());
			oldTextbookCapter = textbookCapterService.update(oldTextbookCapter);
			HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.textbookCapter2Map(textbookCapter);
			actionResult.init(SUCCESS, "修改章节成功", data);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
		
	}
	
	private TextbookCapter getFromFront(ActionResult actionResult)
	{
		TextbookCapter textbookCapter = new TextbookCapter();
		short level = 1;
		Textbook textbook = this.getTextbookService().getById(Integer.parseInt(request.getParameter("textbook.id")));
		if (textbook == null)
		{
			actionResult.init(FAIL_STATUS, "教材不存在", null);
			return null;
		}
		textbookCapter.setTextbook(textbook);
		TextbookCapter pTextbookCapter = null;
		if (request.getParameterMap().containsKey("textbookCapter.id"))
		{
			if (Utlity.isNumeric(request.getParameter("textbookCapter.id")))
			{
				int pid = Integer.parseInt(request.getParameter("textbookCapter.id"));
				pTextbookCapter = textbookCapterService.getById(pid);
				if (pTextbookCapter == null)
				{
					actionResult.init(FAIL_STATUS, "父章节不存在", null);
					return null;
				}
				else
				{
					level = (short) (pTextbookCapter.getLevel() + 1);
				}
				
			}
		}
		textbookCapter.setTextbookCapter(pTextbookCapter);
		textbookCapter.setLevel(level);
		textbookCapter.setName(request.getParameter("name"));
		textbookCapter.setFcode(request.getParameter("fcode"));
		textbookCapter.setNumber(request.getParameter("number"));
		
		return textbookCapter;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.action.base.IStandardAction#Delete()
	 */
	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权删除章节")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete()
	{
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		// 策略表外键判断
		// 实体表外键判断
		textbookCapterService.deleteById(id);
		actionResult.init(SUCCESS, "删除章节成功", null);
		Utlity.ResponseWrite(actionResult, dataType, response);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.action.base.IStandardAction#List()
	 */
	@Override
	public void List()
	{
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.action.base.IStandardAction#Search()
	 */
	@SuppressWarnings("unused")
	@Override
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "number", type = ValueType.STRING)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	public void Search()
	{
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		HashMap<String, Object> hashMap = getPageAndSortParas();// 获取分页及排序参数
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		HashMap<String, Object> paras = new HashMap<String, Object>();
		if (request.getParameterMap().containsKey("name"))
		{
			paras.put("name", request.getParameter("name"));
		}
		if (request.getParameterMap().containsKey("number"))
		{
			paras.put("number", request.getParameter("number"));
		}
		if (request.getParameterMap().containsKey("level"))
		{
			if (Utlity.isNumeric(request.getParameter("level")))
			{
				paras.put("level", request.getParameter("level"));
			}
		}
		if (request.getParameterMap().containsKey("textbookCapter.id"))
		{
			if (Utlity.isNumeric(request.getParameter("textbookCapter.id")))
			{
				paras.put("textbookCapter", request.getParameter("textbookCapter.id"));
			}
		}
		if (request.getParameterMap().containsKey("textbook.id"))
		{
			if (Utlity.isNumeric(request.getParameter("textbook.id")))
			{
				paras.put("textbook", request.getParameter("textbook.id"));
			}
		}
		if (request.getParameterMap().containsKey("id"))
		{
			if (Utlity.isNumeric(request.getParameter("id")))
			{
				paras.put("id", request.getParameter("id"));
			}
		}
		List<TextbookCapter> lstTextbookCapters = new ArrayList<>();
		lstTextbookCapters = textbookCapterService.getAllTextbookCapter(offset, pagesize, sorts, paras);
		List<Map<String, Object>> lstData = new ArrayList<>();
		
		if (lstTextbookCapters != null && lstTextbookCapters.size() > 0)
		{
			for (TextbookCapter tTextbookCapter : lstTextbookCapters)
			{
				Map<String, Object> data = SerializeEntity.textbookCapter2Map(tTextbookCapter);
				boolean hasChild = textbookCapterService.hasChild(tTextbookCapter.getId());
				data.put("haschild", Utlity.Boolean2Integer(hasChild));
				lstData.add(data);
			}
		}
		
		actionResult.init(SUCCESS_STATUS, "数据获取成功", lstData);
		int totalCount = textbookCapterService.getCountByParas(paras);
		actionResult.setTotalCount(totalCount);
		actionResult.setPageSize(pagesize);
		actionResult.setPageNum(offset);
		Utlity.ResponseWrite(actionResult, dataType, response);
		
	}
	
	/**
	 * 章节导航
	 * @author Administrator
	 * @date: 2014年8月27日 下午12:20:16 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	public void LoadCapterNav() {
		
		String id = request.getParameter("textbookCapter.id");
		int i_id = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();
		
		TextbookCapter grade = this.getTextbookCapterService().getById(i_id);
		if(grade == null){
			result.init(FAIL_STATUS, null, null);
		}
		else{
			LinkedList<TextbookCapter> lnklist = new LinkedList<TextbookCapter>();
			lnklist.add(grade);
			
			while(grade.getTextbookCapter()!=null){
				grade = grade.getTextbookCapter();
				lnklist.add(grade);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i=lnklist.size()-1;
			for(;i>=0;i--){
				TextbookCapter cate = lnklist.get(i);
				Map<String, Object> data = SerializeEntity.textbookCapter2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}
		
		Utlity.ResponseWrite(result, dataType, response);

	}
	
}
