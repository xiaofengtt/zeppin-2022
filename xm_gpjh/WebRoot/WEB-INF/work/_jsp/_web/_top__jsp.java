/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._web;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class _top__jsp extends com.caucho.jsp.JavaPage
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
      
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      out.write(_jsp_string1, 0, _jsp_string1.length);
      out.print((basePath));
      out.write(_jsp_string2, 0, _jsp_string2.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("web/top.jsp"), -6118708546467916349L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  private final static char []_jsp_string2;
  static {
    _jsp_string1 = "\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <base href=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string2 = "\">\r\n    \r\n    <title></title>\r\n<link href=\"/web/css/css.css\" rel=\"stylesheet\" type=\"text/css\" />    \r\n\r\n  </head>\r\n\r\n<body onselectstart=\"return flase\">\r\n<table width=\"1002\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-top:4px;\">\r\n  <tr>\r\n    <td width=\"150\" align=\"center\"><img src=\"/web/images/index_03.jpg\" width=\"93\" height=\"49\" /></td>\r\n    <td width=\"850\"  align=\"right\">\r\n	<table width=\"100%\" height=\"30\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#FFFFFF\" style=\"margin-top:14px;\" >\r\n      <tr>\r\n        <td  width=\"143\" background=\"/web/images/index_06.jpg\" class=\"topmenu\" ><a href=\"/entity/first/firstInfoNews_toIndex.action\" target=\"_parent\">\u8fd4\u56de\u9996\u9875</a></td>\r\n        <td width=\"109\"  background=\"/web/images/index_08.jpg\" class=\"topmenu\"><a href=\"#\">\u5b66\u9662\u7b80\u4ecb</a></td>\r\n        <td width=\"121\" background=\"/web/images/index_10.jpg\" class=\"topmenu\"><a href=\"/web/help/faq.jsp\" target=\"_parent\">\u62db\u751f\u4e0e\u5165\u5b66</a></td>\r\n        <td width=\"109\" background=\"/web/images/index_12.jpg\" class=\"topmenu\"><a href=\"#\">\u5b66\u9662\u6559\u80b2</a></td>\r\n        <td width=\"109\" background=\"/web/images/index_14.jpg\" class=\"topmenu\"><a href=\"#\">\u5b66\u4e60\u56ed\u5730</a></td>\r\n        <td width=\"109\" background=\"/web/images/index_16.jpg\" class=\"topmenu\"><a href=\"#\">\u5b66\u5458\u5929\u5730</a></td>\r\n        <td width=\"124\" background=\"/web/images/index_18.jpg\" class=\"topmenu\"><a href=\"/web/help/map.jsp\" target=\"_parent\">\u7f51\u7ad9\u5bfc\u822a</a></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td align=\"align\"><object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"1002\" height=\"157\">\r\n      <param name=\"movie\" value=\"/web/images/00.swf\" />\r\n      <param name=\"quality\" value=\"high\" />\r\n      <embed src=\"/web/images/00.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"1002\" height=\"157\"></embed>\r\n    </object></td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n".toCharArray();
  }
}
