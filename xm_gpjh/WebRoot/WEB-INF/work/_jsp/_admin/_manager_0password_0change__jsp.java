/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._admin;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _manager_0password_0change__jsp extends com.caucho.jsp.JavaPage
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
    com.caucho.jsp.PageContextImpl pageContext = com.caucho.jsp.QJspFactory.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      
  String admin = (String)session.getValue("admin");
  if(admin!=null&&admin.equals("1")){
  }else{
  
      out.write(_jsp_string1, 0, _jsp_string1.length);
      out.print((request.getContextPath()));
      out.write(_jsp_string2, 0, _jsp_string2.length);
      
  }

      out.write(_jsp_string3, 0, _jsp_string3.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      com.caucho.jsp.QJspFactory.freePageContext(pageContext);
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
    if (com.caucho.server.util.CauchoSystem.getVersionId() != -9019483982733035993L)
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/manager_password_change.jsp"), 2344712145300917400L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/pub/priv.jsp"), -6358081507721480377L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string3;
  private final static char []_jsp_string0;
  private final static char []_jsp_string2;
  static {
    _jsp_string1 = "\r\n  <script language=\"javascript\">\r\n        window.parent.location.href=\"".toCharArray();
    _jsp_string3 = "\r\n\r\n\r\n<html>\r\n<head>\r\n<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<title>\u4fee\u6539\u5bc6\u7801</title>\r\n<script type=\"text/javascript\">\r\n	function pageGuarding() {\r\n		if(document.forms[\"pwd\"].oldpwd.value == \"\") {\r\n			alert(\"\u539f\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\");\r\n			document.forms[\"pwd\"].oldpwd.focus();\r\n			return false;\r\n		}\r\n		\r\n		if(document.forms[\"pwd\"].newpwd.value == \"\") {\r\n			alert(\"\u65b0\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\");\r\n			document.forms[\"pwd\"].newpwd.focus();\r\n			return false;\r\n		}\r\n		\r\n		if(document.forms[\"pwd\"].newpwd_confirm.value != document.forms[\"pwd\"].newpwd.value) {\r\n			alert(\"\u4e24\u6b21\u5bc6\u7801\u8f93\u5165\u4e0d\u4e00\u81f4!\");\r\n			document.forms[\"pwd\"].newpwd.focus();\r\n			return false;\r\n		}\r\n	}\r\n</script>\r\n</head>\r\n<body>\r\n<form name=\"pwd\" action=\"manager_password_changeexe.jsp\" method=post onsubmit=\"return pageGuarding();\">\r\n<table cellPadding=2 cellSpacing=1  border=\"0\" bgcolor=#3F6C61 align=center>\r\n<tr>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u539f\u5bc6\u7801\uff1a</td>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\"><input type=password name=oldpwd size=50></td>\r\n</tr>\r\n<tr>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u65b0\u5bc6\u7801\uff1a</td>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\"><input type=password name=newpwd size=50></td>\r\n</tr>\r\n<tr>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u91cd\u590d\u65b0\u5bc6\u7801\uff1a</td>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\"><input type=password name=newpwd_confirm size=50></td>\r\n</tr>\r\n<tr>\r\n	<td bgcolor=\"#D4E4EB\" class=\"zhengwen\" colspan=2 align=\"center\"><input type=\"submit\" value=\"\u63d0\u4ea4\"></td>\r\n</tr>\r\n</table>\r\n</form>\r\n</html>".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
    _jsp_string2 = "/sso/admin/admin_adminLogin.action\";\r\n  </script>\r\n  ".toCharArray();
  }
}
