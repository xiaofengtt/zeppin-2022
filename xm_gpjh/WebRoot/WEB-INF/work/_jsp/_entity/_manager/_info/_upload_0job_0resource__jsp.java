/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._manager._info;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _upload_0job_0resource__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.IfTag _jsp_IfTag_1 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_2 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      
String savepath = request.getContextPath();

      out.write(_jsp_string1, 0, _jsp_string1.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_PropertyTag_0.setValue("bean.id");
      }

      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string2, 0, _jsp_string2.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack4 = null;
      if (_jsp_IfTag_1 == null) {
        _jsp_IfTag_1 = new org.apache.struts2.views.jsp.IfTag();
        _jsp_IfTag_1.setPageContext(pageContext);
        _jsp_IfTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IfTag_1.setTest("msg!=null");
      }

      int _jspEval6 = _jsp_IfTag_1.doStartTag();
      if (_jspEval6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval6 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack4 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IfTag_1.setBodyContent(_jsp_endTagHack4);
        }
        out.write(_jsp_string3, 0, _jsp_string3.length);
        if (_jsp_PropertyTag_2 == null) {
          _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
          _jsp_PropertyTag_2.setPageContext(pageContext);
          _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IfTag_1);
          _jsp_PropertyTag_2.setValue("msg");
        }

        int _jspEval9 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string4, 0, _jsp_string4.length);
        if (_jspEval6 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IfTag_1.doEndTag();
      if (_jsp_endTagHack4 != null) {
        pageContext.releaseBody(_jsp_endTagHack4);
        _jsp_endTagHack4 = null;
      }
      out.write(_jsp_string5, 0, _jsp_string5.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_PropertyTag_0 != null)
        _jsp_PropertyTag_0.release();
      if (_jsp_IfTag_1 != null)
        _jsp_IfTag_1.release();
      if (_jsp_PropertyTag_2 != null)
        _jsp_PropertyTag_2.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/info/upload_job_resource.jsp"), 8601461504263799766L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IfTag.class, 2691840914333557849L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string5;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string4;
  private final static char []_jsp_string3;
  private final static char []_jsp_string0;
  static {
    _jsp_string5 = "\r\n</body>\r\n</html>".toCharArray();
    _jsp_string1 = "\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <title>\u63d0\u4ea4\u4efb\u52a1\u8d44\u6599</title>\r\n	<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n	<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n	<meta http-equiv=\"expires\" content=\"0\">    \r\n	<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n</head>  \r\n  <script type=\"text/javascript\">\r\n  \r\n  function checkall(){\r\n		document.getElementById('jobResource').submit();return false;\r\n	if(document.getElementById('upload').value=='' ){\r\n		alert(\"\u5bf9\u4e0d\u8d77\uff0c\u4efb\u52a1\u8d44\u6599\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n		flag=false;\r\n		\r\n	}\r\n	if(document.getElementById('reply').value=='' ){\r\n		alert(\"\u5bf9\u4e0d\u8d77\uff0c\u63d0\u4ea4\u4efb\u52a1\u56de\u590d\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n		flag=false;\r\n	}\r\n	if(document.getElementById('reply').value.length>2000){\r\n		alert(\"\u5bf9\u4e0d\u8d77\uff0c\u56de\u590d\u5185\u5bb9\u8fc7\u591a\uff01\");\r\n		flag=false;\r\n	}\r\n		document.getElementById('jobResource').submit();\r\n	}\r\n</script>\r\n<body topmargin=\"0\" leftmargin=\"0\"  bgcolor=\"#FAFAFA\">\r\n<div id=\"main_content\">\r\n    <div class=\"content_title\">\u63d0\u4ea4\u4efb\u52a1\u8d44\u6599</div>\r\n    <div class=\"cntent_k\">\r\n   	  <div class=\"k_cc\">\r\n<form id='jobResource' action=\"/entity/information/prJobUnitInit_uploadResource.action\" method=\"post\"  enctype=\"multipart/form-data\">\r\n<table width=\"554\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n			     <tr>\r\n			       <td colspan=\"3\" height=\"100\" align=\"center\" valign=\"middle\" >&nbsp;<input type=\"hidden\" name=\"bean.id\" value=\"".toCharArray();
    _jsp_string2 = "\"/></td>\r\n			     </tr>\r\n			     <tr valign=\"middle\"> \r\n			       <td width=\"200\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\">\u63d0\u4ea4\u4efb\u52a1\u8d44\u6599*\uff1a</span></td>\r\n			       <td class=\"postFormBox\" style=\"padding-left:10px\"><input type=\"file\" id='upload' name=\"upload1\" /></td>\r\n			     </tr>\r\n				<tr valign=\"middle\"> \r\n				  <td width=\"200\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\">\u63d0\u4ea4\u4efb\u52a1\u56de\u590d*\uff1a</span></td>\r\n				  <td class=\"postFormBox\" style=\"padding-left:10px\"><textarea name=\"reply\" id=\"reply\" cols=\"40\" rows=\"5\"></textarea></td>\r\n				</tr>\r\n				<tr>\r\n				  <td height=\"50\" align=\"center\" colspan=\"2\">\r\n				  <input type=\"button\" value=\"\u63d0\u4ea4\" onClick=\"checkall()\"/></td>\r\n				</tr>\r\n</table>\r\n</form>\r\n	  </div>\r\n    </div>\r\n</div>\r\n<div class=\"clear\"></div>\r\n".toCharArray();
    _jsp_string4 = "');\r\n</script>\r\n".toCharArray();
    _jsp_string3 = "\r\n<script>\r\nalert('".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
