/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:51:33  
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITextbookDAO;
import cn.zeppin.entity.Textbook;
import cn.zeppin.service.api.ITextBookService;

/**
 * @author sj
 *
 */
public class TextBookService implements ITextBookService
{
	private ITextbookDAO textbookDAO;
	

	
	public ITextbookDAO getTextbookDAO() {
		return textbookDAO;
	}

	public void setTextbookDAO(ITextbookDAO textbookDAO) {
		this.textbookDAO = textbookDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextBookService#add(cn.zeppin.entity.Textbook)
	 */
	@Override
	public Textbook add(Textbook textbook)
	{
		// TODO Auto-generated method stub
		return this.getTextbookDAO().save(textbook);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextBookService#getById(int)
	 */
	@Override
	public Textbook getById(int id)
	{
		// TODO Auto-generated method stub
		return this.getTextbookDAO().get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextBookService#update(cn.zeppin.entity.Textbook)
	 */
	@Override
	public void update(Textbook textbook)
	{
		// TODO Auto-generated method stub
		this.getTextbookDAO().update(textbook);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextBookService#delete(cn.zeppin.entity.Textbook)
	 */
	@Override
	public void delete(Textbook oldTextbook)
	{
		// TODO Auto-generated method stub
		oldTextbook.setStatus((short) 0);
		this.getTextbookDAO().update(oldTextbook);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextBookService#getAllByAdmin(int, int,
	 * java.lang.String, java.util.HashMap)
	 */
	@Override
	public List<Textbook> getAllByAdmin(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getTextbookDAO().getAllTextBooks(offset, pageSize, sorts, true, paras);
	}
	
	@Override
	public List<Textbook> getTextbookByParam(Map<String, Object> searchMap)
	{
		// TODO Auto-generated method stub
		return this.getTextbookDAO().getTextbookByParam(searchMap);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextBookService#getCountByParas(java.util.HashMap,
	 * boolean)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras, boolean isAdmin)
	{
		// TODO Auto-generated method stub
		return getTextbookDAO().getCountByParas(paras, isAdmin);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextBookService#getAllByUser(int, int,
	 * java.lang.String, java.util.HashMap)
	 */
	@Override
	public List<Textbook> getAllByUser(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getTextbookDAO().getAllTextBooks(offset, pageSize, sorts, false, paras);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextBookService#isExistByName(java.lang.String)
	 */
	@Override
	public boolean isExistByName(String name, Integer id)
	{
		// TODO Auto-generated method stub
		boolean result = false;
		int count = this.getTextbookDAO().getCountByName(name, id);
		if (count > 0)
		{
			result = true;
		}
		
		return result;
	}
}
