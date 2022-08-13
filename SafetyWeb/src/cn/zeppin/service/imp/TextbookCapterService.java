/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月31日 下午2:15:18  
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ITextbookCapterDAO;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.service.api.ITextbookCapterService;

/**
 * @author sj
 *
 */
public class TextbookCapterService implements ITextbookCapterService
{
	private ITextbookCapterDAO textbookCapterDAO;
	

	
	public ITextbookCapterDAO getTextbookCapterDAO() {
		return textbookCapterDAO;
	}

	public void setTextbookCapterDAO(ITextbookCapterDAO textbookCapterDAO) {
		this.textbookCapterDAO = textbookCapterDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextbookCapterService#add(cn.zeppin.entity.
	 * TextbookCapter)
	 */
	@Override
	public TextbookCapter add(TextbookCapter textbookCapter)
	{
		// TODO Auto-generated method stub
		return this.getTextbookCapterDAO().save(textbookCapter);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextbookCapterService#getById(int)
	 */
	@Override
	public TextbookCapter getById(int id)
	{
		// TODO Auto-generated method stub
		return this.getTextbookCapterDAO().get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextbookCapterService#update(cn.zeppin.entity.
	 * TextbookCapter)
	 */
	@Override
	public TextbookCapter update(TextbookCapter oldTextbookCapter)
	{
		// TODO Auto-generated method stub
		return this.getTextbookCapterDAO().update(oldTextbookCapter);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextbookCapterService#getAllTextbookCapter(int,
	 * int, java.lang.String, java.util.HashMap)
	 */
	@Override
	public List<TextbookCapter> getAllTextbookCapter(int offset, int pageSize, String sorts, HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getTextbookCapterDAO().getAllTextbookCapter(offset, pageSize, sorts, paras);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextbookCapterService#hasChild(java.lang.Integer)
	 */
	@Override
	public boolean hasChild(Integer id)
	{
		// TODO Auto-generated method stub
		int count = this.getTextbookCapterDAO().getChild(id);
		if (count > 0)
		{
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.api.ITextbookCapterService#getCountByParas(java.util
	 * .HashMap, boolean)
	 */
	@Override
	public int getCountByParas(HashMap<String, Object> paras)
	{
		// TODO Auto-generated method stub
		return this.getTextbookCapterDAO().getCountByParas(paras);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.api.ITextbookCapterService#deleteById(int)
	 */
	@Override
	public void deleteById(int id)
	{
		// TODO Auto-generated method stub
		TextbookCapter textbookCapter = getById(id);
		this.getTextbookCapterDAO().delete(textbookCapter);
		
	}
	
}
