/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._score;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _first__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_0 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("msg");
      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string1, 0, _jsp_string1.length);
      _jsp_PropertyTag_0.setValue("bean.name");
      int _jspEval6 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string2, 0, _jsp_string2.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("score/first.jsp"), -278896402915746647L, false);
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

  private final static char []_jsp_string0;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  static {
    _jsp_string0 = "\r\n\r\n<HTML>\r\n<HEAD>\r\n<TITLE>\u767b\u5206\u4eba\u5e10\u6237\u5165\u53e3</TITLE>\r\n<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\r\n<link href=\"../admin/css_index.css\" rel=\"stylesheet\" type=\"text/css\">\r\n</HEAD>\r\n<BODY BGCOLOR=#FFFFFF background=\"../admin/images/bak.jpg\" LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>\r\n<form action=\"/score/scoreinputlogin_login.action\" method=\"post\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\">\r\n  <tr>\r\n    <td><TABLE WIDTH=640 BORDER=0 align=\"center\" CELLPADDING=0 CELLSPACING=0>\r\n        <TR> \r\n          <TD COLSPAN=3 bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n          <TD COLSPAN=5 bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n          <TD COLSPAN=3 bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n        </TR>\r\n        <TR bgcolor=\"#FFFFFF\"> \r\n          <TD COLSPAN=11>&nbsp; </TD>\r\n        </TR>\r\n        <TR> \r\n          <TD COLSPAN=2 bgcolor=\"#FFFFFF\">&nbsp; </TD>\r\n          <TD COLSPAN=2 ROWSPAN=6> <IMG SRC=\"../admin/images/index_06.jpg\" WIDTH=84 HEIGHT=334 ALT=\"\"></TD>\r\n          <TD height=\"53\" COLSPAN=7 bgcolor=\"#FFFFFF\">&nbsp; </TD>\r\n        </TR>\r\n        <TR> \r\n          <TD background=\"../admin/images/index_08.jpg\"> <IMG SRC=\"../admin/images/index_08.jpg\" WIDTH=20 HEIGHT=19 ALT=\"\"></TD>\r\n          <TD ROWSPAN=3> <IMG SRC=\"../admin/images/index_09.jpg\" WIDTH=109 HEIGHT=172 ALT=\"\"></TD>\r\n          <TD ROWSPAN=2> <IMG SRC=\"../admin/images/index_10.jpg\" WIDTH=37 HEIGHT=21 ALT=\"\"></TD>\r\n          <TD COLSPAN=5 ROWSPAN=2 background=\"../admin/images/index_11.jpg\">\u8bf7\u8f93\u5165\u767b\u5206\u4eba\u5e10\u6237\u5bc6\u7801</TD>\r\n          <TD ROWSPAN=2> <IMG SRC=\"../admin/images/index_12.jpg\" WIDTH=13 HEIGHT=21 ALT=\"\"></TD>\r\n        </TR>\r\n        <TR> \r\n          <TD> <IMG SRC=\"../admin/images/index_13.jpg\" WIDTH=20 HEIGHT=2 ALT=\"\"></TD>\r\n        </TR>\r\n        <TR> \r\n          <TD bgcolor=\"#FFFFFF\">&nbsp; </TD>\r\n          <TD ROWSPAN=3> <IMG SRC=\"../admin/images/index_15.jpg\" WIDTH=37 HEIGHT=260 ALT=\"\"></TD>\r\n          <TD height=\"151\" COLSPAN=6 align=\"left\" valign=\"top\" background=\"../admin/images/index_29.jpg\"> \r\n            <blockquote> \r\n              <p>&nbsp;<font color=\"red\">".toCharArray();
    _jsp_string1 = "</font>&nbsp;</p>\r\n              <p> \u3000\u5e10\u3000\u6237\uff1a \r\n                <input class=\"input\" type=\"text\" name=\"bean.name\"  size=\"14\" value=\"".toCharArray();
    _jsp_string2 = "\">\r\n                <br> \u3000\u5bc6\u3000\u7801\uff1a \r\n                <input class=\"input\" type=\"password\" name=\"bean.password\" size=\"16\" value=\"\">\r\n                <br>\r\n                <br>\r\n                \u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000 \r\n                <input type=\"submit\" name=\"Submit\" value=\"\u786e\u5b9a\">\r\n              </p>\r\n            </blockquote>\r\n            <div align=\"left\"></div></TD>\r\n        </TR>\r\n        <TR> \r\n          <TD> <IMG SRC=\"../admin/images/index_18.jpg\" WIDTH=20 HEIGHT=21 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/index_19.jpg\" WIDTH=109 HEIGHT=21 ALT=\"\"></TD>\r\n          <TD COLSPAN=5> <IMG SRC=\"../admin/images/index_20.jpg\" WIDTH=377 HEIGHT=21 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/index_21.jpg\" WIDTH=13 HEIGHT=21 ALT=\"\"></TD>\r\n        </TR>\r\n        <TR> \r\n          <TD bgcolor=\"#FFFFFF\">&nbsp; </TD>\r\n          <TD> <IMG SRC=\"../admin/images/index_23.jpg\" WIDTH=109 HEIGHT=88 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/index_24.jpg\" WIDTH=172 HEIGHT=88 ALT=\"\"></TD>\r\n          <TD COLSPAN=5 bgcolor=\"#FFFFFF\">&nbsp; </TD>\r\n        </TR>\r\n        <TR> \r\n          <TD width=\"100%\" bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n          <TD COLSPAN=8 bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n          <TD height=\"73\" COLSPAN=2 bgcolor=\"#F5F6F8\">&nbsp; </TD>\r\n        </TR>\r\n        <TR> \r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=20 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=109 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=79 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=5 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=37 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=172 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=43 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=37 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=114 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=11 HEIGHT=1 ALT=\"\"></TD>\r\n          <TD> <IMG SRC=\"../admin/images/spacer.gif\" WIDTH=13 HEIGHT=1 ALT=\"\"></TD>\r\n        </TR>\r\n      </TABLE></td>\r\n  </tr>\r\n</table>\r\n</form>\r\n<!-- End ImageReady Slices -->\r\n</BODY>\r\n</HTML>".toCharArray();
  }
}
