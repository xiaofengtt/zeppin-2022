/**
 * 
 */
package com.zeppin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.entiey.DicAddress;
import com.zeppin.service.IAddressService;

/**
 * @author suijing
 * 
 */
public class baseDataAction extends ActionSupport
{
    static Logger logger = LogManager.getLogger(baseDataAction.class);
    IAddressService iadds;
    String opt;
    String jsonString;
    String hqlString;
    DicAddress dAddress = new DicAddress();
    int id;

    /**
     * @return the iadds
     */
    public IAddressService getIadds()
    {
	return iadds;
    }

    /**
     * @param iadds
     *            the iadds to set
     */
    public void setIadds(IAddressService iadds)
    {
	this.iadds = iadds;
    }

    public void addressManage() throws IOException
    {

	HttpServletRequest res = ServletActionContext.getRequest();
	HttpServletResponse rps = ServletActionContext.getResponse();
	opt = res.getParameter("opt");
	if (opt.equals("getkv"))
	{
	    hqlString = "from DicAddress where class_=1";
	    if (res.getParameterMap().containsKey("id"))
	    {
		id = Integer.parseInt(res.getParameter("id"));
		try
		{
		    dAddress = iadds.getIAddressDao().get(id);
		    hqlString = "from DicAddress where parentCode="
			    + dAddress.getCode();
		}
		catch (Exception e)
		{
		    logger.error(e.getMessage());
		}

	    }
	    if (res.getParameterMap().containsKey("son"))
	    {

		hqlString = "from DicAddress where class_=2";

	    }
	    List<DicAddress> lstDas = new ArrayList<DicAddress>();
	    lstDas = iadds.getIAddressDao().getListByHSQL(hqlString);
	    jsonString = "<select>";
	    if (res.getParameterMap().containsKey("search"))
	    {
		jsonString += "<option value=all>所有</option>";
	    }
	    for (DicAddress da : lstDas)
	    {
		jsonString += "<option value=" + da.getId() + ">"
			+ da.getName() + "</option>";

	    }
	    jsonString = jsonString.substring(0, jsonString.length() - 1);
	    jsonString += "</select>";
	    rps.setContentType("text/html");
	    rps.setCharacterEncoding("UTF-8");
	    rps.setHeader("Cache-Control", "no-cache");
	    rps.getWriter().write(jsonString);
	}

    }
}
