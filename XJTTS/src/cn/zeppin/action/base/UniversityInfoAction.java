/**
 * 
 */
package cn.zeppin.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.entity.UniversityInfo;
import cn.zeppin.service.IUniversityInfoService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
public class UniversityInfoAction extends ActionSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    static Logger logger = LogManager.getLogger(UniversityInfoAction.class);
    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;

    private IUniversityInfoService iUniversityInfoService;

    public void initServlert()
    {
	request = ServletActionContext.getRequest();
	session = request.getSession();
	response = ServletActionContext.getResponse();
    }

    public void getInfo()
    {
	initServlert();
	if (request.getParameterMap().containsKey("search"))
	{
	    String infoString = request.getParameter("search");
	    StringBuffer sb = new StringBuffer();
	    String hqlString = "from UniversityInfo where name like '%"
		    + infoString + "%' or pinyin like '%" + infoString + "%'";
	    List<UniversityInfo> lstUniversityInfos = new ArrayList<>();
	    try
	    {
		lstUniversityInfos = iUniversityInfoService.getListForPage(
			hqlString, 1, 10, null);
	    }
	    catch (Exception e)
	    {
		// TODO: handle exception
		lstUniversityInfos.clear();
	    }

	    sb.append("{");
	    sb.append("\"Result\":\"OK\",");
	    sb.append("\"Options\":");
	    String ret = "[";
	    String st = "";

	    if (lstUniversityInfos.size() > 0)
	    {
		for (UniversityInfo ui : lstUniversityInfos)
		{
		    st += "{\"id\":" + ui.getId() + ",\"DisplayText\":\""
			    + ui.getName() + "\"},";
		}
	    }
	    if (st.length() > 0)
	    {
		st = st.substring(0, st.length() - 1);
	    }
	    ret += st + "]";

	    sb.append(ret);
	    sb.append("}");

	    Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}
    }

    /**
     * @return the iUniversityInfoService
     */
    public IUniversityInfoService getiUniversityInfoService()
    {
	return iUniversityInfoService;
    }

    /**
     * @param iUniversityInfoService
     *            the iUniversityInfoService to set
     */
    public void setiUniversityInfoService(
	    IUniversityInfoService iUniversityInfoService)
    {
	this.iUniversityInfoService = iUniversityInfoService;
    }
}
