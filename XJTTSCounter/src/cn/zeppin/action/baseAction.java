/**
 * 
 */
package cn.zeppin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.service.IBaseService;
import cn.zeppin.service.impl.BaseServiceImpl;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
public abstract class baseAction extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(baseAction.class);

	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private HttpSession session;
	private HttpServletResponse response;

	/**
	 * @category 鍒濆鍖栧熀鏈睘鎬�
	 * 
	 * @author sj
	 */
	public void init()
	{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
	}

	/**
	 * @category 鍒濆鍖栭〉闈㈠垪琛�
	 * @param bsi
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> initPage(IBaseService ibs, String tableName, int length)
	{
		init();
		List<T> tList = new ArrayList<T>();
		// 璧峰椤�
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN"))
		{
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 鎺掑簭
		String sort = request.getParameter("sort");

		String hql = "from " + tableName;

		// 鍒ゆ柇鍏朵粬鎼滅储瀛楁鏄惁瀛樺湪

		// 鎺掑簭 鍙傛暟
		if (sort != null && !sort.equals(""))
		{
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hql += " order by " + sortname + " " + sorttype;
		}

		tList = ibs.getListForPage(hql, start, length, null);

		return tList;

	}

	/**
	 * @category 鍒犻櫎椤甸潰鍏冪礌
	 * @param tid
	 * @param bsi
	 * @return
	 */
	public <T> String deleteItem(String tid,
			BaseServiceImpl<T, Serializable> bsi)
	{
		String result = "";

		init();
		String id = this.request.getParameter(tid);
		if (id != null && !id.equals(""))
		{
			T t = bsi.get(id);
			if (t != null)
			{
				try
				{

					bsi.delete(t);
					result = "success";

				}
				catch (Exception ex)
				{

					result = "false";
				}
			}
			else
			{
				result = "false";
			}

		}
		else
		{
			result = "false";
		}
		return result;
	}

	public <T> void getPageJson(String tableName, IBaseDao<T, Serializable> ibs)
	{
		init();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN"))
		{
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		String hql = "select count(*) from " + tableName + "where 1=1 ";

		// 鍒ゆ柇鍏朵粬鎼滅储瀛楁鏄惁瀛樺湪

		List<Object> licount = ibs.findByHSQL(hql);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		if (licount != null && licount.size() > 0)
		{
			int records = Integer.parseInt(licount.get(0).toString());
			int total = records / 2;
			if ((records % 2) > 0)
			{
				total += 1;
			}
			sb.append("\"totalPage\":" + total);
		}
		else
		{
			sb.append("\"totalPage\":" + 0);
		}
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	public <T> String addItme(T t, IBaseService<T, Serializable> ibs)
	{
		String result = "";
		try
		{
			ibs.add(t);
			result = "success";
		}
		catch (Exception e)
		{
			result = "false";
		}

		return result;

	}

	/**
	 * @category 鏇存柊椤甸潰鍏冪礌
	 * @param t
	 * @param bsi
	 * @return
	 */
	public <T> String updateItme(T t, IBaseService<T, Serializable> ibs)
	{
		String result = "";
		try
		{
			ibs.update(t);
			result = "success";
		}
		catch (Exception e)
		{
			result = "false";
		}

		return result;

	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getPageList(String hql, int offset, int length,
			IBaseService ibs, String tableName, Object[] objects)
			{
		List<T> tList = new ArrayList<T>();
		return tList;
			}
}
