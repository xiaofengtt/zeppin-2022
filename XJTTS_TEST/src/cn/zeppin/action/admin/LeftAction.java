/**
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.FunCategory;
import cn.zeppin.entity.OrgaCateMap;
import cn.zeppin.service.IOrgaCateMapService;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 左侧导航列表
 * 
 * @author sj
 * @category 根据数据库中的对应表进行查找关联
 */
public class LeftAction extends ActionSupport
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(LeftAction.class);

    private HttpServletRequest request;
    private HttpSession session;

    private IOrgaCateMapService iOrgaCateMapService;

    private LinkedHashMap<FunCategory, List<FunCategory>> leftHash;

    private String leftName;

    public LeftAction()
    {

    }

    public void initServlert()
    {
	request = ServletActionContext.getRequest();
	session = request.getSession();
    }

    /**
     * @return
     * @category 初始化左侧导航菜单
     */
    public String init()
    {

	initServlert();

	UserSession us = (UserSession) session.getAttribute("usersession");

	short roleid = us.getRole();

	leftHash = new LinkedHashMap<FunCategory, List<FunCategory>>();

	ROLEENUM ROLE = ROLEENUM.valueof(roleid);

	if (ROLE == ROLEENUM.SUPERADMIN || ROLE == ROLEENUM.ADMIN
		|| ROLE == ROLEENUM.TRAINING ||ROLE == ROLEENUM.PROJECTEXPERT)
	{

	    List<OrgaCateMap> li = this.iOrgaCateMapService.findByRoleId(
		    roleid, us.getOrganizationLevel());

	    for (int i = 0; i < li.size(); i++)
	    {

		OrgaCateMap ocm = li.get(i);

		FunCategory fc = ocm.getFunCategory(); // 功能类型
		FunCategory pfc = fc.getFunCategory(); // 父功能类型

		if (pfc != null)
		{
		    // 如果父不为空，则以父为key
		    if (leftHash.containsKey(pfc))
		    {
			List<FunCategory> t = this.leftHash.get(pfc);
			t.add(fc);
		    }
		    else
		    {
			List<FunCategory> t = new ArrayList<FunCategory>();
			t.add(fc);
			this.leftHash.put(pfc, t);
		    }
		}
		else
		{
		    // 如果父为空为一级菜单
		    if (!this.leftHash.containsKey(fc))
		    {
			List<FunCategory> t = new ArrayList<FunCategory>();
			this.leftHash.put(fc, t);

		    }
		}
	    }
	}
	return "init";
    }

    public IOrgaCateMapService getiOrgaCateMapService()
    {
	return iOrgaCateMapService;
    }

    public void setiOrgaCateMapService(IOrgaCateMapService iOrgaCateMapService)
    {
	this.iOrgaCateMapService = iOrgaCateMapService;
    }

    public LinkedHashMap<FunCategory, List<FunCategory>> getLeftHash()
    {
	return leftHash;
    }

    public void setLeftHash(
	    LinkedHashMap<FunCategory, List<FunCategory>> leftHash)
    {
	this.leftHash = leftHash;
    }

    public String getLeftName()
    {
	return leftName;
    }

    public void setLeftName(String leftName)
    {
	this.leftName = leftName;
    }

}
