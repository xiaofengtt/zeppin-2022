/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._manager._programapply;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _note__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_0 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_PropertyTag_0.setValue("getNote()");
        _jsp_PropertyTag_0.setEscape(false);
      }

      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string1, 0, _jsp_string1.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_PropertyTag_0 != null)
        _jsp_PropertyTag_0.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/programApply/note.jsp"), 1833799072655973746L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "&nbsp;</td> \r\n  </tr> \r\n</table>    \r\n</div>\r\n</div>   \r\n \r\n</div>\r\n</body>\r\n</html>\r\n    ".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<title>\u67e5\u770b\u5ba1\u6838\u610f\u89c1</title>\r\n<link href=\"/entity/student/images/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n<style type=\"text/css\">\r\n<!--\r\n.STYLE1 {color: #FF0000}\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<div id=\"main_content\">\r\n	<div class=\"content_title\">\u67e5\u770b\u5ba1\u6838\u610f\u89c1</div>\r\n<div class=\"student_cntent_k\">\r\n    	<div class=\"k_cc\">\r\n\r\n  \r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n	<tr class=\"table_bg1\"><td colspan=\"1\">&nbsp;&nbsp; \u67e5\u770b\u5ba1\u6838\u610f\u89c1</td>\r\n	</tr>\r\n\r\n   <tr class=\"table_bg2\" >\r\n      <td >".toCharArray();
  }
}
