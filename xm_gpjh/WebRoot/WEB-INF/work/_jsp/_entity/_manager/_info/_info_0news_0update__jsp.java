/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._manager._info;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.sso.web.action.SsoConstant;

public class _info_0news_0update__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_0 = null;
    org.apache.struts2.views.jsp.IteratorTag _jsp_IteratorTag_1 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_2 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      out.print((request.getContextPath()));
      out.write(_jsp_string1, 0, _jsp_string1.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("cando");
      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string2, 0, _jsp_string2.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.enumConstByFlagIstop.name");
      int _jspEval6 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string3, 0, _jsp_string3.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.enumConstByFlagIsvalid.name");
      int _jspEval10 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string4, 0, _jsp_string4.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.scopeString");
      int _jspEval14 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string5, 0, _jsp_string5.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.id");
      int _jspEval18 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string6, 0, _jsp_string6.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.id");
      int _jspEval22 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string7, 0, _jsp_string7.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("cando");
      int _jspEval26 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string8, 0, _jsp_string8.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.title");
      int _jspEval30 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string9, 0, _jsp_string9.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack32 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("cbScop");
      _jsp_IteratorTag_1.setId("cb_");
      int _jspEval34 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval34 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval34 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack32 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack32);
        }
        do {
          out.write(_jsp_string10, 0, _jsp_string10.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#cb_[0]");
          int _jspEval37 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string11, 0, _jsp_string11.length);
          _jsp_PropertyTag_2.setValue("#cb_[1]");
          int _jspEval41 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string12, 0, _jsp_string12.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval34 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack32 != null) {
        pageContext.releaseBody(_jsp_endTagHack32);
        _jsp_endTagHack32 = null;
      }
      out.write(_jsp_string13, 0, _jsp_string13.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack44 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("stScop");
      _jsp_IteratorTag_1.setId("st_");
      int _jspEval46 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval46 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack44 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack44);
        }
        do {
          out.write(_jsp_string14, 0, _jsp_string14.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#st_[0]");
          int _jspEval49 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string11, 0, _jsp_string11.length);
          _jsp_PropertyTag_2.setValue("#st_[1]");
          int _jspEval53 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string12, 0, _jsp_string12.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack44 != null) {
        pageContext.releaseBody(_jsp_endTagHack44);
        _jsp_endTagHack44 = null;
      }
      out.write(_jsp_string15, 0, _jsp_string15.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack56 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("qtstScop");
      _jsp_IteratorTag_1.setId("qtst_");
      int _jspEval58 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval58 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack56 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack56);
        }
        do {
          out.write(_jsp_string16, 0, _jsp_string16.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#qtst_[0]");
          int _jspEval61 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string11, 0, _jsp_string11.length);
          _jsp_PropertyTag_2.setValue("#qtst_[1]");
          int _jspEval65 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string12, 0, _jsp_string12.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack56 != null) {
        pageContext.releaseBody(_jsp_endTagHack56);
        _jsp_endTagHack56 = null;
      }
      out.write(_jsp_string17, 0, _jsp_string17.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("bean.note");
      int _jspEval70 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string18, 0, _jsp_string18.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_PropertyTag_0 != null)
        _jsp_PropertyTag_0.release();
      if (_jsp_IteratorTag_1 != null)
        _jsp_IteratorTag_1.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/info/info_news_update.jsp"), 3079465477408081217L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IteratorTag.class, -2243808422739547962L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string5;
  private final static char []_jsp_string4;
  private final static char []_jsp_string3;
  private final static char []_jsp_string16;
  private final static char []_jsp_string12;
  private final static char []_jsp_string8;
  private final static char []_jsp_string7;
  private final static char []_jsp_string11;
  private final static char []_jsp_string14;
  private final static char []_jsp_string1;
  private final static char []_jsp_string6;
  private final static char []_jsp_string10;
  private final static char []_jsp_string15;
  private final static char []_jsp_string17;
  private final static char []_jsp_string13;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string18;
  private final static char []_jsp_string9;
  static {
    _jsp_string5 = "\";\r\n		var person =document.getElementsByName(\"person\");\r\n		for(var n=0;n<person.length;n++ ){\r\n			if(str.indexOf(person[n].value)>=0)\r\n			\r\n			person[n].checked = true;\r\n		}		\r\n		clickCB();\r\n		clickST();\r\n		clickST2();\r\n		var cbs = window.document.getElementsByName(\"cb_id\");\r\n		for(var i=0;i<cbs.length;i++){\r\n			if(str.indexOf(cbs[i].value)>=0){\r\n				cbs[i].checked = true;\r\n				}\r\n		}\r\n		var sts = window.document.getElementsByName(\"st_id\");\r\n		for(var i=0;i<sts.length;i++){\r\n			if(str.indexOf(sts[i].value)>=0){\r\n				sts[i].checked = true;\r\n				}\r\n		}\r\n	var sts2 = window.document.getElementsByName(\"st2_id\");\r\n		for(var i=0;i<sts2.length;i++){\r\n			if(str.indexOf(sts2[i].value)>=0){\r\n				sts2[i].checked = true;\r\n				}\r\n		}\r\n		var bean_id = \"".toCharArray();
    _jsp_string4 = "\"==window.document.getElementById(\"flagIsvalid_no\").value)\r\n			window.document.getElementById(\"flagIsvalid_no\").checked = true;\r\n		else\r\n			window.document.getElementById(\"flagIsvalid_is\").checked = true;\r\n		var str = \"".toCharArray();
    _jsp_string3 = "\"==window.document.getElementById(\"flagIstop_is\").value)\r\n			window.document.getElementById(\"flagIstop_is\").checked = true;\r\n		else\r\n			window.document.getElementById(\"flagIstop_no\").checked = true;\r\n		if(\"".toCharArray();
    _jsp_string16 = "\r\n                	<tr>\r\n                    <td height=\"22\"><input id=\"st2_id_check\" type=\"checkbox\" name=\"st2_id\" value=\"".toCharArray();
    _jsp_string12 = "</td>\r\n                    </tr>\r\n                ".toCharArray();
    _jsp_string8 = "\"/>\r\n        <div class=\"cntent_k\" id=\"zlb_content_start\">\r\n          <table width=\"85%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"bottom\" width=\"90\" nowrap><span class=\"name\">\u516c\u544a\u4e3b\u9898*</span></td>\r\n              <td width=\"626\"> <input name=\"bean.title\"   value=\"".toCharArray();
    _jsp_string7 = "\"/>\r\n<input type=\"hidden\" id=\"cando\" name=\"cando\" value=\"".toCharArray();
    _jsp_string11 = "\">".toCharArray();
    _jsp_string14 = "\r\n                	<tr>\r\n                    <td height=\"22\"><input id=\"st_id_check\" type=\"checkbox\" name=\"st_id\" value=\"".toCharArray();
    _jsp_string1 = "/FCKeditor/fckeditor.js\"></script>\r\n<script>\r\n<!--\u5e73\u53f0\u7ba1\u7406\u5458 -->\r\n	var bSubmit=false\r\n	var bLoaded=false;\r\n	function doCommit() {		\r\n		var content;\r\n		if(FCKeditor_IsCompatibleBrowser()){\r\n			var oEditor = FCKeditorAPI.GetInstance(\"bean.note\"); \r\n			content=oEditor.GetXHTML(); \r\n		}else{\r\n			alert(\"\u8868\u5355\u6b63\u5728\u4e0b\u8f7d\");\r\n			return false;\r\n		}\r\n		if(isNull(document.getElementById(\"bean.title\").value)){ \r\n		}else{\r\n			alert(\"\u8bf7\u586b\u5199\u516c\u544a\u4e3b\u9898!!\");\r\n			document.getElementById(\"bean.title\").focus();\r\n\r\n			return false;\r\n		}\r\n\r\n	\r\n		var dd =document.getElementsByName(\"person\")\r\n		var n=0;\r\n		for(k=0;k<dd.length;k++){\r\n			if(dd[k].checked){\r\n			 n++;\r\n			}\r\n		}\r\n		if(n==0){\r\n			alert(\"\u8bf7\u9009\u62e9\u516c\u544a\u53d1\u9001\u7684\u8303\u56f4\");\r\n			return false;\r\n		}\r\n		\r\n		if(content.length <1){\r\n			alert(\"\u516c\u544a\u5185\u5bb9\u4e3a\u7a7a!\");\r\n			return false;\r\n		}\r\n		\r\n		document.forms[\"info_news_add\"].submit();		\r\n	}\r\n	\r\n	function showDiv(objID){\r\n		var tempObj;\r\n		if(document.getElementById(objID))	tempObj = document.getElementById(objID);\r\n		if(tempObj.style.display == \"none\")	tempObj.style.display = \"block\";\r\n		else	tempObj.style.display = \"none\";\r\n		\r\n	}\r\n	\r\n	function listSelectCB() {\r\n		var form = document.forms['info_news_add'];\r\n		for (var i = 0 ; i < form.elements.length; i++) {\r\n			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {\r\n				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {\r\n					form.elements[i].checked = form.cb_all.checked;\r\n				}\r\n			}\r\n		}\r\n		return true;\r\n	}\r\n	function listSelectST() {\r\n		var form = document.forms['info_news_add'];\r\n		for (var i = 0 ; i < form.elements.length; i++) {\r\n			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st_id')) {\r\n				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {\r\n					form.elements[i].checked = form.st_all.checked;\r\n				}\r\n			}\r\n		}\r\n		return true;\r\n	}\r\n	function listSelectST2() {\r\n		var form = document.forms['info_news_add'];\r\n		for (var i = 0 ; i < form.elements.length; i++) {\r\n			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st2_id')) {\r\n				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {\r\n					form.elements[i].checked = form.st2_all.checked;\r\n				}\r\n			}\r\n		}\r\n		return true;\r\n	}\r\n	function clickCB()\r\n	{\r\n		var cbObj;\r\n		if(document.getElementById(\"CBS\"))\r\n		{\r\n			cbObj = document.getElementById(\"CBS\");\r\n		}\r\n		if(cbObj.checked){\r\n			document.getElementById(\"cb_button\").style.display=\"block\";\r\n		}else{\r\n			document.getElementById(\"cb_button\").style.display=\"none\";\r\n		}\r\n	}\r\n	function clickST()\r\n	{\r\n		var stObj;\r\n		if(document.getElementById(\"ZXSTS\"))\r\n		{\r\n			stObj = document.getElementById(\"ZXSTS\");\r\n		}\r\n		if(stObj.checked){\r\n			document.getElementById(\"st_button\").style.display=\"block\";\r\n		}else{\r\n			document.getElementById(\"st_button\").style.display=\"none\";\r\n		}\r\n	}\r\n	function clickST2()\r\n	{\r\n		var qtstObj;\r\n		if(document.getElementById(\"QTSTS\"))\r\n		{\r\n			qtstObj = document.getElementById(\"QTSTS\");\r\n		}\r\n		if(qtstObj.checked){\r\n			document.getElementById(\"st2_button\").style.display=\"block\";\r\n		}else{\r\n			document.getElementById(\"st2_button\").style.display=\"none\";\r\n		}\r\n	}\r\n	function onload_edit(){\r\n		if(!".toCharArray();
    _jsp_string6 = "\";\r\n	}\r\n</script>\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\" class=\"scllbar\"  onload=\"onload_edit();\" >\r\n  <form name='info_news_add' method='post' action=\"/entity/information/peBulletin_editexe.action\" class='nomargin'>\r\n<table width=\"86%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr> \r\n    <td><div class=\"content_title\" id=\"zlb_title_start\">\u4fee\u6539\u901a\u77e5\u516c\u544a</div></td>\r\n  </tr>\r\n\r\n  <tr> \r\n    <td valign=\"top\" class=\"pageBodyBorder_zlb\"> \r\n<input type=\"hidden\" name=\"bean.id\" value=\"".toCharArray();
    _jsp_string10 = "\r\n                	<tr>\r\n                    <td height=\"22\"><input id=\"cb_id_check\" type=\"checkbox\" name=\"cb_id\" value=\"".toCharArray();
    _jsp_string15 = "\r\n                <tr><td><input type='button' onclick=\"showDiv('cnt2')\" value=\"\u786e\u5b9a\" style=\"font-size:12px;height:23;background-color:#ece9d8;border-width:1;\"></td></tr>\r\n                </table>\r\n              </div>\r\n		   <input id=\"st2_button\" type=\"button\" name=\"st2\" onClick=\"showDiv('cnt3')\" value=\"\u8bf7\u9009\u62e9\" style=\"display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;\">&nbsp;\r\n              <div id=\"cnt3\" style=\"width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none\">\r\n              \r\n                <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"22\"><input id=\"st2_id_all\" type=\"checkbox\" name=\"st2_all\" value=\"\" onclick=listSelectST2();>\u5168\u9009</td>\r\n              </tr>\r\n              	".toCharArray();
    _jsp_string17 = "\r\n                <tr><td><input type='button' onclick=\"showDiv('cnt3')\" value=\"\u786e\u5b9a\" style=\"font-size:12px;height:23;background-color:#ece9d8;border-width:1;\"></td></tr>\r\n                </table>\r\n              </div>\r\n			  </td>\r\n            </tr>\r\n             <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"top\"><span class=\"name\">\u516c\u544a\u5185\u5bb9*</span></td>\r\n              <td> \r\n              <textarea class=\"smallarea\"  name=\"bean.note\" cols=\"70\" rows=\"12\" id=\"bean.note\">".toCharArray();
    _jsp_string13 = "\r\n                <tr><td><input type='button' onclick=\"showDiv('cnt1')\" value=\"\u786e\u5b9a\" style=\"font-size:12px;height:23;background-color:#ece9d8;border-width:1;\"></td></tr>\r\n                </table>\r\n              </div>\r\n               <input id=\"st_button\" type=\"button\" name=\"st\" onClick=\"showDiv('cnt2')\" value=\"\u8bf7\u9009\u62e9\" style=\"display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;\">&nbsp;\r\n              <div id=\"cnt2\" style=\"width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none\">\r\n              \r\n                <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"22\"><input id=\"st_id_all\" type=\"checkbox\" name=\"st_all\" value=\"\" onclick=listSelectST();>\u5168\u9009</td>\r\n              </tr>\r\n              	".toCharArray();
    _jsp_string2 = "){\r\n			alert(\"\u5bf9\u4e0d\u8d77,\u4f60\u65e0\u6743\u4fee\u6539!\");\r\n			window.history.back();\r\n		}\r\n	\r\n		if(\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>\u4fee\u6539\u516c\u544a</title>\r\n<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"entity/manager/js/calendar/calendar-win2k-cold-1.css\" title=\"win2k-cold-1\">\r\n<script language=\"javascript\" src=\"/entity/manager/js/check.js\"></script>		\r\n<script src=\"".toCharArray();
    _jsp_string18 = "</textarea>\r\n			  </td>\r\n            </tr>\r\n          </table> \r\n      </div>\r\n   </td>\r\n  </tr>\r\n  <tr> \r\n    <td align=\"center\" id=\"pageBottomBorder_zlb\"><div class=\"content_bottom\"> <table width=\"98%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>\r\n      	<td width=\"120\">&nbsp;</td>\r\n       <td align=\"center\" valign=\"middle\" width=\"80\">\r\n       	<a href=\"#\" title=\"\u63d0\u4ea4\" class='button'>\r\n       		<span unselectable=\"on\" class=\"norm\"  style=\"width:46px;height:15px;padding-top:3px\" onClick=\"doCommit()\" onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\">\r\n       			<span class=\"text\">\u63d0\u4ea4</span>       			\r\n       		</span>\r\n       	</a> \r\n       	</td>\r\n       <td  align=\"center\"  valign=\"middle\" width=\"150\">\r\n       <a href='#' title='\u8fd4\u56de' class='button' ><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=\"window.navigate('/entity/information/peBulletin.action')\" onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>\u8fd4\u56de</span></span></a>\r\n       	</td>            \r\n       	</tr>\r\n      </table></td>\r\n  </tr>\r\n</table>\r\n</form>\r\n<script type=\"text/javascript\"> \r\n\r\n\r\nvar oFCKeditor = new FCKeditor( 'bean.note' ) ; \r\noFCKeditor.Height = 300 ; \r\noFCKeditor.Width  = 700 ; \r\n\r\noFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';\r\noFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';\r\n\r\noFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';\r\noFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';\r\n\r\noFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';\r\noFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';\r\n\r\n\r\noFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; \r\noFCKeditor.ReplaceTextarea() ; \r\n//--> \r\n</script> \r\n<script>bLoaded=true;</script>\r\n\r\n</body>\r\n\r\n</html>\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string9 = "\" type=\"text\" class=\"selfScale\" id=\"bean.title\" size=\"50\"> \r\n                \r\n            <!--  &nbsp;<img src=\"/entity/manager/images/buttons/help.png\" width=\"16\" height=\"16\" class=\"helpImg\" onMouseOver=\"window.status='\u6dfb\u52a0\u516c\u544a\u7684\u4e3b\u9898';\" onMouseOut=\"window.status='\u4fe1\u606f\u63d0\u793a...'\"> --> </td>\r\n            </tr>\r\n                        \r\n            <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"bottom\"><span class=\"name\">\u662f\u5426\u7f6e\u9876*</span></td>\r\n              <td> \r\n              \u662f<input type=\"radio\" id=\"flagIstop_is\" name=\"bean.enumConstByFlagIstop.name\" value=\"\u7f6e\u9876\">\r\n              \u5426<input type=\"radio\" id=\"flagIstop_no\" name=\"bean.enumConstByFlagIstop.name\" value=\"\u4e0d\u7f6e\u9876\" checked>\r\n              <img src=\"/entity/manager/images/buttons/help.png\" width=\"16\" height=\"16\" class=\"helpImg\" onMouseOver=\"window.status='\u8bbe\u7f6e\u65b0\u95fb\u7f6e\u9876\u4e0e\u5426';\" onMouseOut=\"window.status='\u4fe1\u606f\u63d0\u793a...'\"></td>\r\n            </tr>\r\n            \r\n             <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"bottom\"><span class=\"name\">\u6d3b\u52a8\u72b6\u6001*</span></td>\r\n              <td> \r\n              \u662f<input type=\"radio\" id=\"flagIsvalid_is\"  name=\"bean.enumConstByFlagIsvalid.name\" value=\"\u662f\" checked>\r\n              \u5426<input type=\"radio\" id=\"flagIsvalid_no\"  name=\"bean.enumConstByFlagIsvalid.name\" value=\"\u5426\">\r\n              <img src=\"/entity/manager/images/buttons/help.png\" width=\"16\" height=\"16\" class=\"helpImg\" onMouseOver=\"window.status='\u8bbe\u7f6e\u65b0\u95fb\u6d3b\u52a8\u72b6\u6001';\" onMouseOut=\"window.status='\u4fe1\u606f\u63d0\u793a...'\"></td>\r\n            </tr>\r\n            \r\n			<tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"top\"><span class=\"name\">\u4eba\u5458\u8303\u56f4*</span></td>\r\n              <td>\r\n			  \u8bc4\u5ba1\u4e13\u5bb6<input type=\"checkbox\" value=\"ps\"  id=\"PSS\" name=\"person\"> &nbsp;&nbsp;&nbsp;&nbsp;\r\n			  \u627f\u529e\u5355\u4f4d<input type=\"checkbox\" value=\"cb\" id=\"CBS\" name=\"person\" onClick=\"clickCB()\">&nbsp;&nbsp;&nbsp;&nbsp;\r\n			  \u4e2d\u897f\u90e8\u5e08\u8303\u5904<input type=\"checkbox\" value=\"zxst\" id=\"ZXSTS\" name=\"person\" onClick=\"clickST()\">&nbsp;&nbsp;&nbsp;&nbsp;\r\n			  \u5176\u4ed6\u5e08\u8303\u5904<input type=\"checkbox\" value=\"qtst\" id=\"QTSTS\" name=\"person\" onClick=\"clickST2()\">&nbsp;&nbsp;&nbsp;&nbsp;\r\n			  \u5e08\u8303\u53f8\u9879\u76ee\u529e<input type=\"checkbox\" value=\"sf\" id=\"SFS\" name=\"person\" >&nbsp;&nbsp;&nbsp;&nbsp;\r\n			  \u9879\u76ee\u6267\u884c\u529e<input type=\"checkbox\" value=\"xm\" id=\"XMS\" name=\"person\" >&nbsp;&nbsp;&nbsp;&nbsp;\r\n		<img src=\"/entity/manager/images/buttons/help.png\" width=\"16\" height=\"16\" class=\"helpImg\" onMouseOver=\"window.status='\u9009\u62e9\u53d1\u5e03\u5bf9\u8c61\uff0c\u53ef\u591a\u9009';\" onMouseOut=\"window.status='\u4fe1\u606f\u63d0\u793a...'\">\r\n			  \r\n			  <input id=\"CB_button\" type=\"button\" name=\"cb\" onClick=\"showDiv('cnt1')\" value=\"\u8bf7\u9009\u62e9\" style=\"display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;\">&nbsp;\r\n              <div id=\"cnt1\" style=\"width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none\">\r\n              \r\n                <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"22\"><input id=\"cb_id_all\" type=\"checkbox\" name=\"cb_all\" value=\"\" onclick=listSelectCB();>\u5168\u9009</td>\r\n              </tr>\r\n              	".toCharArray();
  }
}
