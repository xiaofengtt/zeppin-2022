/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._query_0expert;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _query_0certificate__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html; charset=utf-8");
    request.setCharacterEncoding("UTF-8");
    org.apache.struts2.views.jsp.IfTag _jsp_IfTag_0 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_1 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack0 = null;
      if (_jsp_IfTag_0 == null) {
        _jsp_IfTag_0 = new org.apache.struts2.views.jsp.IfTag();
        _jsp_IfTag_0.setPageContext(pageContext);
        _jsp_IfTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IfTag_0.setTest("msg!=null");
      }

      int _jspEval2 = _jsp_IfTag_0.doStartTag();
      if (_jspEval2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack0 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IfTag_0.setBodyContent(_jsp_endTagHack0);
        }
        out.write(_jsp_string1, 0, _jsp_string1.length);
        if (_jsp_PropertyTag_1 == null) {
          _jsp_PropertyTag_1 = new org.apache.struts2.views.jsp.PropertyTag();
          _jsp_PropertyTag_1.setPageContext(pageContext);
          _jsp_PropertyTag_1.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IfTag_0);
          _jsp_PropertyTag_1.setValue("msg");
        }

        int _jspEval5 = _jsp_PropertyTag_1.doStartTag();
        _jsp_PropertyTag_1.doEndTag();
        out.write(_jsp_string2, 0, _jsp_string2.length);
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IfTag_0.doEndTag();
      if (_jsp_endTagHack0 != null) {
        pageContext.releaseBody(_jsp_endTagHack0);
        _jsp_endTagHack0 = null;
      }
      out.write(_jsp_string3, 0, _jsp_string3.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_IfTag_0 != null)
        _jsp_IfTag_0.release();
      if (_jsp_PropertyTag_1 != null)
        _jsp_PropertyTag_1.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/query_expert/query_certificate.jsp"), 5477268338963463436L, false);
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

  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  private final static char []_jsp_string2;
  static {
    _jsp_string3 = "\r\n</html>\r\n".toCharArray();
    _jsp_string1 = "\r\n<SCRIPT type=\"text/javascript\">\r\n	alert('".toCharArray();
    _jsp_string0 = "\r\n\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<title>\u6559\u80b2\u90e8  \u8d22\u653f\u90e8\u4e2d\u5c0f\u5b66\u6559\u5e08\u56fd\u5bb6\u7ea7\u57f9\u8bad\u8ba1\u5212</title>\r\n<link href=\"/entity/query_expert/css/zscx.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script type=\"text/javascript\">\r\nfunction chek(){\r\n	var u=document.getElementById('expname');\r\n	\r\n	if(u.value==''){\r\n		alert('\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a');\r\n		u.focus();\r\n		return false;\r\n	}\r\n	\r\n	return true;\r\n}\r\n</script>\r\n</head>\r\n\r\n<body style=\"text-align:center;\">\r\n	<div id=\"wrap\">\r\n	  <div id=\"header\"></div>\r\n	  <div id=\"main\">\r\n	 	<div id=\"main1\"></div>\r\n		<div id=\"main1_b\"></div>\r\n		<div id=\"main2\">\r\n			<div id=\"main2_l\"></div>\r\n			<div id=\"main2_2\">\r\n			<form id=\"queryform\" action=\"/entity/first/userQueryCertificateNo_query.action\" onsubmit=\"return chek()\" method=\"post\">\r\n				<div id=\"login\">\r\n					<div class=\"loginbox\"><span class=\"login_text\">\u59d3 \u540d\uff1a</span>&nbsp;&nbsp;<span id=\"input\"><input type=\"text\" name=\"name\" id='expname'/></span></div>\r\n					\r\n					<div class=\"loginbox\"><span class=\"login_btn\">\r\n					  <input type=\"image\" name=\"imageField\" src=\"/entity/query_expert/images/btn.jpg\" />\r\n					</span></div>\r\n					</form>\r\n				</div>\r\n			</div>\r\n			<div id=\"main2_3\"></div>\r\n		</div><div class=\"clear\"></div>\r\n		<div id=\"main3\"></div> \r\n	  </div>\r\n		<div id=\"bottom\">\u4e3b\u7ba1\u5355\u4f4d\uff1a\u6559\u80b2\u90e8\u5e08\u8303\u6559\u80b2\u53f8 \u4e3b\u529e\u5355\u4f4d\uff1a\u56fd\u57f9\u8ba1\u5212\u2014\u4e2d\u5c0f\u5b66\u9aa8\u5e72\u6559\u5e08\u57f9\u8bad\u9879\u76ee\u6267\u884c\u529e\u516c\u5ba4<br />\r\n		  \u8054\u7cfb\u7535\u8bdd\uff1a010-58800182\u3000 \u4f20\u771f\uff1a010-58802946\u3000 \u7535\u5b50\u90ae\u7bb1\uff1axmb@gpjh.cn<br />\r\n		  \u5730\u5740\uff1a\u5317\u4eac\u5e02\u65b0\u8857\u53e3\u5916\u5927\u885719\u53f7\u5317\u4eac\u5e08\u8303\u5927\u5b66\u7ee7\u7eed\u6559\u80b2\u4e0e\u6559\u5e08\u57f9\u8bad\u5b66\u9662<br />\r\n	    \u4eacICP\u590710031106\u53f7</div>\r\n		\r\n\r\n</body>\r\n".toCharArray();
    _jsp_string2 = "');\r\n</SCRIPT>\r\n".toCharArray();
  }
}
