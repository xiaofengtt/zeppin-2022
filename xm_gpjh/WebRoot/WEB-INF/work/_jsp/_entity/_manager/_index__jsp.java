/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._entity._manager;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.whaty.platform.sso.web.servlet.UserSession;

public class _index__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      
String path = request.getContextPath();

      out.write(_jsp_string1, 0, _jsp_string1.length);
      //@ include file="pub/priv.jsp"
      out.write(_jsp_string2, 0, _jsp_string2.length);
      out.print((path));
      out.write(_jsp_string3, 0, _jsp_string3.length);
      out.print((path));
      out.write(_jsp_string4, 0, _jsp_string4.length);
      
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession.getSsoUser().getId().equals("402880a9203242180120324733d70001")){
      out.write(_jsp_string5, 0, _jsp_string5.length);
      }
      out.write(_jsp_string6, 0, _jsp_string6.length);
      

/*
		String loginId = "guocheng";
		SsoFactory factory=SsoFactory.getInstance();
		SsoUserPriv ssoUserPriv=factory.creatSsoUserPriv(loginId);
		SsoUserOperation ssoUserOperation=factory.creatSsoUserOperation(ssoUserPriv);
		SsoManagerPriv ssoManagerPriv =factory.creatSsoManagerPriv();
		SsoManage  ssoManager =factory.creatSsoManage(ssoManagerPriv);
		
		session.setAttribute("ssoUserPriv",ssoUserPriv);
		
		PlatformFactory pfactory=PlatformFactory.getInstance();
		PlatformManage platformManage=pfactory.createPlatformManage();
		
		EntityUser enuser=platformManage.getEntityUser(ssoUserPriv.getId(),"manager");
		
			session.removeAttribute("infomanager_priv");	  
		  	session.removeAttribute("smsmanager_priv");  	  	
		  	session.setAttribute("eduplatform_user",enuser);
			ManagerPriv managerPriv= pfactory.getManagerPriv(enuser.getId());
			session.setAttribute("eduplatform_priv",managerPriv);
*/

        session.setAttribute("type",request.getParameter("type"));

 
      out.write(_jsp_string7, 0, _jsp_string7.length);
      if(userSession.getSsoUser().getId().equals("402880a9203242180120324733d70001")){
      out.write(_jsp_string8, 0, _jsp_string8.length);
      }
      out.write(_jsp_string9, 0, _jsp_string9.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 5783496155892875126L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    manager.addTaglibFunctions(_jsp_functionMap, "s", "/WEB-INF/struts-tags.tld");
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/index.jsp"), -1937653922096773575L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string7;
  private final static char []_jsp_string8;
  private final static char []_jsp_string5;
  private final static char []_jsp_string4;
  private final static char []_jsp_string3;
  private final static char []_jsp_string6;
  private final static char []_jsp_string0;
  private final static char []_jsp_string9;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  static {
    _jsp_string7 = "\r\n ".toCharArray();
    _jsp_string8 = "\r\n<form id=\"loginform\" name=\"loginform\" action=\"http://www.gpjh.cn/cms/login/CmsSubmit.do\" method=\"post\" target=\"top\" style=\"display:none\">\r\n<input type=\"hidden\" name=\"loginName\" value=\"admin\"/>\r\n<input type=\"hidden\" name=\"password\" value=\"111111\"/>\r\n</form>\r\n".toCharArray();
    _jsp_string5 = "\r\n<script>\r\nfunction logcms(){\r\n	var s=document.getElementById('loginform');\r\n	s.submit();\r\n}\r\n</script>\r\n".toCharArray();
    _jsp_string4 = "/sso/login_close.action?loginErrMessage=clear\";\r\n		if (window.XMLHttpRequest) {\r\n	        req = new XMLHttpRequest();\r\n	    }\r\n	    else if (window.ActiveXObject) {\r\n	        req = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n	    }\r\n	    req.open(\"Get\",url,true);\r\n	    req.onreadystatechange = function(){\r\n	    	if(req.readyState == 4){\r\n		    	window.top.navigate(\"/\");\r\n	    	}\r\n	    };\r\n  		req.send(null);\r\n	}\r\n</script>\r\n".toCharArray();
    _jsp_string3 = "/sso/login_close.action?loginErrMessage=clear\";\r\n			if (window.XMLHttpRequest) {\r\n		        req = new XMLHttpRequest();\r\n		    }\r\n		    else if (window.ActiveXObject) {\r\n		        req = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n		    }\r\n		    req.open(\"Get\",url,true);\r\n		    req.onreadystatechange = function(){\r\n		    	if(req.readyState == 4);\r\n		    };\r\n	  		req.send(null);\r\n  		}\r\n	}\r\n	function logout(){\r\n		var url=\"".toCharArray();
    _jsp_string6 = "\r\n</head>\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string9 = "\r\n<frameset rows=\"62,34,*,38\" frameborder=\"no\" border=\"0\" framespacing=\"0\" name=\"TCB\">\r\n	<frame src=\"/entity/manager/pub/topBar1.jsp\" name=\"banner\" scrolling=\"NO\" noresize>\r\n	<frame src=\"/entity/manager/pub/topBar2.jsp\" name=\"banner\" scrolling=\"NO\" noresize>\r\n	<frameset cols=\"213,*\" frameborder=\"no\" border=\"0\" framespacing=\"0\" name=\"TC\">\r\n		<frameset rows=\"*,0\" frameborder=\"no\" border=\"0\" framespacing=\"0\" name=\"TH\">\r\n			<frame src=\"/entity/manager/pub/tree.jsp\" name=\"tree\" scrolling=\"no\" noresize/>\r\n			<frame src=\"/entity/manager/pub/help.htm\" name=\"help\" scrolling=\"no\" noresize/>\r\n		</frameset>\r\n		<frameset rows=\"0,*\" frameborder=\"no\" border=\"0\" framespacing=\"0\" name=\"MC\">\r\n			<frame src=\"/entity/manager/pub/menu.htm\" name=\"menu\" scrolling=\"no\" noresize/>	\r\n			<frame src=\"/entity/manager/pub/content_pszj.htm\" name=\"content\" scrolling=\"no\" noresize/>\r\n		</frameset>\r\n	</frameset>\r\n	<frame src=\"/entity/manager/pub/bottom.htm\" name=\"bottom\" scrolling=\"NO\" noresize>\r\n</frameset>\r\n<noframes></noframes>\r\n<body>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string1 = "\r\n".toCharArray();
    _jsp_string2 = "\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n\r\n<title>\u56fd\u57f9\u8ba1\u5212\u9879\u76ee\u7ba1\u7406\u7cfb\u7edf</title>\r\n<script language=\"JavaScript\" src=\"js/frame.js\"></script>\r\n\r\n<script type=\"text/javascript\">\r\n	function   window.onunload()   { \r\n		if(window.screenLeft>10000|| window.screenTop>10000){ \r\n			var url=\"".toCharArray();
  }
}
