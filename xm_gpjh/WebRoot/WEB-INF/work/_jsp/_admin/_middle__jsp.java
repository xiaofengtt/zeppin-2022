/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._admin;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.whaty.platform.util.*;

public class _middle__jsp extends com.caucho.jsp.JavaPage
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
      out.print(( SystemInfoUtil.getRuntimeName() ));
      out.write(_jsp_string4, 0, _jsp_string4.length);
      out.print(( SystemInfoUtil.getVmVersion() ));
      out.write(_jsp_string5, 0, _jsp_string5.length);
      out.print(( SystemInfoUtil.getRuntimeVersion() ));
      out.write(_jsp_string6, 0, _jsp_string6.length);
      out.print(( SystemInfoUtil.getVmVendor() ));
      out.write(_jsp_string7, 0, _jsp_string7.length);
      out.print(( SystemInfoUtil.getVmInfo() ));
      out.write(_jsp_string8, 0, _jsp_string8.length);
      //SystemInfoUtil.getJavaCompiler() 
      out.write(_jsp_string9, 0, _jsp_string9.length);
      out.print(( SystemInfoUtil.getAppEnviromentRoot() ));
      out.write(_jsp_string10, 0, _jsp_string10.length);
      out.print(( SystemInfoUtil.getOsArch() ));
      out.write(_jsp_string11, 0, _jsp_string11.length);
      out.print(( SystemInfoUtil.getOsName() ));
      out.write(_jsp_string12, 0, _jsp_string12.length);
      out.print(( SystemInfoUtil.getLibraryPath() ));
      out.write(_jsp_string13, 0, _jsp_string13.length);
      out.print(( SystemInfoUtil.getClassPth() ));
      out.write(_jsp_string14, 0, _jsp_string14.length);
      out.print(( SystemInfoUtil.getTimeZone() ));
      out.write(_jsp_string15, 0, _jsp_string15.length);
      out.print(( SystemInfoUtil.getFileEncoding() ));
      out.write(_jsp_string16, 0, _jsp_string16.length);
      out.print(( SystemInfoUtil.getLang() ));
      out.write(_jsp_string17, 0, _jsp_string17.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/middle.jsp"), 5929993439612977975L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/pub/priv.jsp"), -6358081507721480377L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string4;
  private final static char []_jsp_string13;
  private final static char []_jsp_string7;
  private final static char []_jsp_string8;
  private final static char []_jsp_string2;
  private final static char []_jsp_string9;
  private final static char []_jsp_string0;
  private final static char []_jsp_string11;
  private final static char []_jsp_string3;
  private final static char []_jsp_string5;
  private final static char []_jsp_string10;
  private final static char []_jsp_string6;
  private final static char []_jsp_string17;
  private final static char []_jsp_string12;
  private final static char []_jsp_string16;
  private final static char []_jsp_string14;
  private final static char []_jsp_string15;
  static {
    _jsp_string1 = "\r\n  <script language=\"javascript\">\r\n        window.parent.location.href=\"".toCharArray();
    _jsp_string4 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u865a\u62df\u673a\u7248\u672c</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string13 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u7c7b\u8def\u5f84</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string7 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u865a\u62df\u673a\u4fe1\u606f</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string8 = "</td>\r\n  </tr>\r\n  <!-- tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u7f16\u8bd1\u5668</td>\r\n    <td bgcolor=\"#D4E4EB\">".toCharArray();
    _jsp_string2 = "/sso/admin/admin_adminLogin.action\";\r\n  </script>\r\n  ".toCharArray();
    _jsp_string9 = "</td>\r\n  </tr-->\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u5e94\u7528\u73af\u5883\u6839\u76ee\u5f55</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n".toCharArray();
    _jsp_string11 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u64cd\u4f5c\u7cfb\u7edf\u540d\u79f0</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string3 = "\r\n\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n<title>Java\u73af\u5883\u4fe1\u606f</title>\r\n<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head>\r\n\r\n<body topmargin=\"0\" leftmargin=\"0\" bottommargin=\"0\" rightmargin=\"0\" style=\"background-color:transparent\" scroll=\"no\">\r\n<table width=\"100%\" height=\"500\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#000000\">\r\n  <tr>\r\n    <td width=\"20%\" bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u8fd0\u884c\u73af\u5883\u540d\u79f0</td>\r\n    <td width=\"80%\" bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string5 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u8fd0\u884c\u73af\u5883\u7248\u672c</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string10 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u64cd\u4f5c\u7cfb\u7edf\u67b6\u6784</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string6 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u865a\u62df\u673a\u63d0\u4f9b\u5546</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string17 = "</td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string12 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"40\" bgcolor=\"#D4E4EB\" class=\"zhengwen\">java\u5e93\u8def\u5f84</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string16 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u7528\u6237\u8bed\u8a00</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string14 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u7528\u6237\u65f6\u533a</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
    _jsp_string15 = "</td>\r\n  </tr>\r\n  <tr>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">\u6587\u4ef6\u7f16\u7801</td>\r\n    <td bgcolor=\"#D4E4EB\" class=\"zhengwen\">".toCharArray();
  }
}
