/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._manager._training_0expert;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _search_0output__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.IteratorTag _jsp_IteratorTag_0 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_1 = null;
    org.apache.struts2.views.jsp.DateTag _jsp_DateTag_2 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack0 = null;
      if (_jsp_IteratorTag_0 == null) {
        _jsp_IteratorTag_0 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_0.setPageContext(pageContext);
        _jsp_IteratorTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IteratorTag_0.setValue("expertList");
        _jsp_IteratorTag_0.setId("expert");
      }

      int _jspEval2 = _jsp_IteratorTag_0.doStartTag();
      if (_jspEval2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack0 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_0.setBodyContent(_jsp_endTagHack0);
        }
        do {
          out.write(_jsp_string1, 0, _jsp_string1.length);
          if (_jsp_PropertyTag_1 == null) {
            _jsp_PropertyTag_1 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_1.setPageContext(pageContext);
            _jsp_PropertyTag_1.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_0);
          }

          _jsp_PropertyTag_1.setValue("#expert.name");
          int _jspEval5 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string2, 0, _jsp_string2.length);
          _jsp_PropertyTag_1.setValue("#expert.enumConstByFkGender.name");
          int _jspEval9 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string3, 0, _jsp_string3.length);
          _jsp_PropertyTag_1.setValue("#expert.folk");
          int _jspEval13 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string4, 0, _jsp_string4.length);
          if (_jsp_DateTag_2 == null) {
            _jsp_DateTag_2 = new org.apache.struts2.views.jsp.DateTag();
            _jsp_DateTag_2.setPageContext(pageContext);
            _jsp_DateTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_0);
            _jsp_DateTag_2.setName("#expert.birthdate");
            _jsp_DateTag_2.setFormat("yyyy-MM");
          }

          int _jspEval17 = _jsp_DateTag_2.doStartTag();
          _jsp_DateTag_2.doEndTag();
          out.write(_jsp_string5, 0, _jsp_string5.length);
          _jsp_PropertyTag_1.setValue("#expert.education");
          int _jspEval21 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string6, 0, _jsp_string6.length);
          _jsp_PropertyTag_1.setValue("#expert.peSubject.name");
          int _jspEval25 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string7, 0, _jsp_string7.length);
          _jsp_PropertyTag_1.setValue("#expert.major");
          int _jspEval29 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string8, 0, _jsp_string8.length);
          _jsp_PropertyTag_1.setValue("#expert.politics");
          int _jspEval33 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string9, 0, _jsp_string9.length);
          _jsp_PropertyTag_1.setValue("#expert.zhiwu");
          int _jspEval37 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string10, 0, _jsp_string10.length);
          _jsp_PropertyTag_1.setValue("#expert.zhicheng");
          int _jspEval41 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string11, 0, _jsp_string11.length);
          _jsp_PropertyTag_1.setValue("#expert.idcard");
          int _jspEval45 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string12, 0, _jsp_string12.length);
          _jsp_PropertyTag_1.setValue("#expert.peWorkplace.name");
          int _jspEval49 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string13, 0, _jsp_string13.length);
          _jsp_PropertyTag_1.setValue("#expert.researchArea");
          int _jspEval53 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string14, 0, _jsp_string14.length);
          _jsp_PropertyTag_1.setValue("#expert.trainingArea");
          int _jspEval57 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string15, 0, _jsp_string15.length);
        } while (_jsp_IteratorTag_0.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_0.doEndTag();
      if (_jsp_endTagHack0 != null) {
        pageContext.releaseBody(_jsp_endTagHack0);
        _jsp_endTagHack0 = null;
      }
      out.write(_jsp_string16, 0, _jsp_string16.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_IteratorTag_0 != null)
        _jsp_IteratorTag_0.release();
      if (_jsp_PropertyTag_1 != null)
        _jsp_PropertyTag_1.release();
      if (_jsp_DateTag_2 != null)
        _jsp_DateTag_2.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/training_expert/search_output.jsp"), -7542954396904856127L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.DateTag.class, 4924450609192765402L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IteratorTag.class, -2243808422739547962L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string4;
  private final static char []_jsp_string8;
  private final static char []_jsp_string13;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  private final static char []_jsp_string5;
  private final static char []_jsp_string14;
  private final static char []_jsp_string15;
  private final static char []_jsp_string2;
  private final static char []_jsp_string10;
  private final static char []_jsp_string12;
  private final static char []_jsp_string16;
  private final static char []_jsp_string11;
  private final static char []_jsp_string7;
  private final static char []_jsp_string9;
  private final static char []_jsp_string1;
  private final static char []_jsp_string6;
  static {
    _jsp_string4 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u51fa\u751f\u5e74\u6708\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string8 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u653f\u6cbb\u9762\u8c8c\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string13 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u7814&nbsp;\u7a76&nbsp;\u4e13&nbsp;\u957f\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    \r\n    <title>\u67e5\u627e\u57f9\u8bad\u4e13\u5bb6</title>\r\n    \r\n	<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n	<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n	<meta http-equiv=\"expires\" content=\"0\">    \r\n	<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n\r\n\r\n  </head>\r\n  \r\n  <script type=\"text/javascript\">\r\n  \r\n  function checkall(){\r\n  	if(document.getElementById('email').value==''){\r\n  			window.alert(\"\u90ae\u7bb1\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n  			document.getElementById('email').focus();\r\n			return false;\r\n	}else{\r\n		var email=document.getElementById('email').value;\r\n		var pattern= /^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$/;///^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;\r\n		if(!pattern.test(email))\r\n		{\r\n			alert(\"\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e\uff01\\n\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n			document.getElementById('email').focus();\r\n			return false;\r\n		}\r\n	}\r\n	\r\n	if(document.getElementById('phone').value==''){\r\n			window.alert(\"\u56fa\u5b9a\u7535\u8bdd\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n  			document.getElementById('phone').focus();\r\n			return false;\r\n	}else{\r\n	var reg = /(^(\\d{2,4}[-_\uff0d\u2014]?)?\\d{3,8}([-_\uff0d\u2014]?\\d{3,8})?([-_\uff0d\u2014]?\\d{1,7})?$)|(^0?1[3]\\d{9}$)/;\r\n		if(!reg.test(document.getElementById('phone').value)||document.getElementById('phone').value.length<7) {\r\n			alert('\u56fa\u5b9a\u7535\u8bdd\u53f7\u7801\u4e0d\u5408\u6cd5\u3002');\r\n			document.getElementById('phone').focus();\r\n			return false;\r\n		}\r\n	}\r\n	\r\n	if(document.getElementById('mobilephone').value==''){\r\n			window.alert(\"\u624b\u673a\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n  			document.getElementById('mobilephone').focus();\r\n			return false;\r\n	}else{\r\n		var myMobile_no=document.getElementById('mobilephone').value;\r\n		var myreg=/^\\d{11}$/;\r\n		if(!myreg.test(myMobile_no))\r\n		{\r\n			alert(\"\u624b\u673a\u53f7\u7801\u4e0d\u5408\u6cd5\uff01\\n\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n			document.getElementById('mobilephone').focus();\r\n			return false;\r\n		}\r\n	}\r\n	\r\n  }\r\n  \r\n	function checkEmail()\r\n	{\r\n		if(document.getElementById('email').value==''){\r\n			return true;\r\n		}\r\n		var email=document.getElementById('email').value;\r\n		//var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;\r\n		var pattern=/^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$/;\r\n		if(!pattern.test(email))\r\n		{\r\n			alert(\"\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e\uff01\\n\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n			document.getElementById('email').focus();\r\n		}\r\n		return true;\r\n	}\r\n	\r\n	function checkAddress(){\r\n		if(document.getElementById('address').value.length > 25) {\r\n			alert('\u901a\u4fe1\u5730\u5740\u5b57\u6570\u4e0d\u80fd\u8d85\u8fc725\u4e2a\u5b57\u7b26\u3002');\r\n			document.getElementById('address').focus();\r\n		}\r\n	}\r\n	\r\n	function checkMobilePhone()\r\n	{\r\n		\r\n		var myMobile_no=document.getElementById('mobilephone').value;\r\n		var myreg=/^\\d{11}$/;\r\n		if(myMobile_no=='')\r\n		{\r\n			alert(\"\u79fb\u52a8\u7535\u8bdd\u4e0d\u80fd\u4e3a\u7a7a\");\r\n			document.getElementById('mobilephone').focus();\r\n			return false;\r\n		}\r\n		if(!myreg.test(myMobile_no))\r\n		{\r\n			alert(\"\u79fb\u52a8\u7535\u8bdd\u4e0d\u5408\u6cd5\uff01\\n\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n			document.getElementById('mobilephone').focus();\r\n		}\r\n		//return true;\r\n	}\r\n	\r\n	function checkPhone() {\r\n		//var reg =/(^[0-9]{3,4}\\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\\([0-9]{3,4}\\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/\r\n		var reg = /(^(\\d{2,4}[-_\uff0d\u2014]?)?\\d{3,8}([-_\uff0d\u2014]?\\d{3,8})?([-_\uff0d\u2014]?\\d{1,7})?$)|(^0?1[3]\\d{9}$)/;\r\n		var phone=document.getElementById('phone').value;\r\n		if(phone=='')\r\n		{\r\n			alert(\"\u56fa\u5b9a\u7535\u8bdd\u4e0d\u80fd\u4e3a\u7a7a\");\r\n			document.getElementById('phone').focus();\r\n			return false;\r\n		}\r\n		\r\n		if(!reg.test(phone)) {\r\n			alert('\u56fa\u5b9a\u7535\u8bdd\u53f7\u7801\u4e0d\u5408\u6cd5\u3002');\r\n			document.getElementById('phone').focus();\r\n			return false;\r\n		}\r\n		if(phone.length<7||phone.length>12) {\r\n			alert('\u56fa\u5b9a\u7535\u8bdd\u53f7\u7801\u5fc5\u987b\u57287\u4f4d--12\u4f4d\u4e4b\u95f4\uff01');\r\n			document.getElementById('phone').focus();\r\n			return false;\r\n		}\r\n	}\r\n	function chk(){\r\n		var name=document.getElementById('expname');\r\n		var unit=document.getElementById('expunit');\r\n		var subject=document.getElementById('expsubject');\r\n		if(name.value==''){\r\n			alert('\u4e13\u5bb6\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a');\r\n			name.focus();\r\n			return false;\r\n		}\r\n		else if(name.value.length<3){\r\n			alert('\u4e13\u5bb6\u59d3\u540d\u4e0d\u80fd\u5c11\u4e8e\u4e24\u4e2a\u5b57');\r\n			name.focus();\r\n			return false;\r\n		}\r\n		\r\n		if(unit.value==''){\r\n			alert('\u5b66\u6821\u4e0d\u80fd\u4e3a\u7a7a');\r\n			unit.focus();\r\n			return false;\r\n		}\r\n		if(subject.value=''){\r\n			alert('\u4e13\u4e1a\u4e0d\u80fd\u4e3a\u7a7a');\r\n			subject.focus();\r\n			return false;\r\n		}\r\n		document.getElementById('teacherInfo').submit();\r\n	}\r\n</script>\r\n  \r\n  <body topmargin=\"0\" leftmargin=\"0\"  bgcolor=\"#FAFAFA\">\r\n\r\n\r\n<div id=\"main_content\">\r\n    <div class=\"content_title\">\u67e5\u627e\u57f9\u8bad\u4e13\u5bb6</div>\r\n    <div class=\"cntent_k\">\r\n   	  <div class=\"k_cc\">\r\n<table width=\"554\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                          \r\n                          <tr>\r\n                            <td colspan=\"3\" height=\"26\" align=\"center\" valign=\"middle\" >&nbsp;</td>\r\n                          </tr>\r\n						 ".toCharArray();
    _jsp_string3 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u6c11&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u65cf\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string5 = "</td>\r\n                                </tr>\r\n                                \r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u5b66&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u5386\uff1a</span></td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string14 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u6559&nbsp;\u5b66&nbsp;\u4e13&nbsp;\u957f\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string15 = "</td>\r\n                                </tr>\r\n                               ".toCharArray();
    _jsp_string2 = " </td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u6027&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u522b\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string10 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u804c&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u79f0\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string12 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u5de5\u4f5c\u5355\u4f4d\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string16 = "\r\n                           <tr>\r\n                            <td height=\"50\" align=\"center\" colspan=\"2\">\r\n                              <input type=\"button\" value=\"\u8fd4\u56de\" onclick=\"history.back();\"/> </td>\r\n						  </tr>\r\n						    <tr>\r\n                            <td  height=\"10\"> </td>\r\n                          </tr>\r\n        </table>\r\n</form>\r\n	  </div>\r\n    </div>\r\n</div>\r\n<div class=\"clear\"></div>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string11 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u8eab\u4efd\u8bc1\u53f7\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string7 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u6240\u5b66\u4e13\u4e1a\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string9 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u804c&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u52a1\uff1a</td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string1 = "\r\n		                          <tr>\r\n		                            <td height=\"8\"> </td>\r\n		                          </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\">\u59d3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u540d\uff1a</span></td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
    _jsp_string6 = "</td>\r\n                                </tr>\r\n                                <tr valign=\"middle\"> \r\n                                  <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u5b66&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u79d1\uff1a</span></td>\r\n                                  <td class=\"postFormBox\" style=\"padding-left:18px\">".toCharArray();
  }
}
