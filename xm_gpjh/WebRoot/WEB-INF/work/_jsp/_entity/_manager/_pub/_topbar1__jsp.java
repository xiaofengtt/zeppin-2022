/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._entity._manager._pub;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _topbar1__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, null, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/pub/topBar1.jsp"), -8246152312045013730L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>topbar</title>\r\n<link href=\"images/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<style type=\"text/css\">\r\n<!--\r\n.STYLE1 {font-family: \"\u5b8b\u4f53\"}\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body style=\"padding:0px; margin:0px;\">\r\n<table width=\"100%\" height=\"62px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"images/images/top_02.jpg\">\r\n  <tr>\r\n    <td width=\"923\" align=\"left\"><img src=\"images/images/gp.gif\" alt=\"\u56fd\u57f9\u8ba1\u5212\u9879\u76ee\u7ba1\u7406\u7cfb\u7edf\" /> </td>\r\n   \r\n    <td width=\"160\" height=\"62\" style=\"background-image:url(images/images/top_02.jpg); background-repeat:no-repeat;\">\r\n    <table width=\"70%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   style=\"margin-top:35px; \" align=\"right\">\r\n          <tr>\r\n            <td valign=\"bottom\">\r\n			<table width=\"147\" height=\"19\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:1px; margin-left:28px\">\r\n                  <tr>\r\n 					<td align=\"left\" background=\"images/images/quit.gif\" class=\"topButtonText\" onclick=\"window.open('/cms');\">\u6253\u5f00\u9996\u9875 </td>\r\n                   <td>&nbsp;</td>\r\n                    <td align=\"right\" background=\"images/images/quit.gif\" class=\"topButtonText\" onMouseUp=\"if(confirm('\u786e\u5b9a\u9000\u51fa\u5e73\u53f0\uff1f'))top.logout()\">\u6ce8\u9500\u9000\u51fa</td>\r\n\r\n                  </tr>\r\n              </table>\r\n			</td>\r\n          </tr>\r\n        </table>\r\n    </td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n".toCharArray();
  }
}