/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._entity._manager._information._vote;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _search_0to_0votepaper__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");
    org.apache.struts2.views.jsp.IteratorTag _jsp_IteratorTag_0 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_1 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack0 = null;
      if (_jsp_IteratorTag_0 == null) {
        _jsp_IteratorTag_0 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_0.setPageContext(pageContext);
        _jsp_IteratorTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IteratorTag_0.setValue("paperType");
        _jsp_IteratorTag_0.setId("proapply");
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

          _jsp_PropertyTag_1.setValue("#proapply[0]");
          int _jspEval5 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string2, 0, _jsp_string2.length);
          _jsp_PropertyTag_1.setValue("#proapply[1]");
          int _jspEval9 = _jsp_PropertyTag_1.doStartTag();
          _jsp_PropertyTag_1.doEndTag();
          out.write(_jsp_string3, 0, _jsp_string3.length);
        } while (_jsp_IteratorTag_0.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_0.doEndTag();
      if (_jsp_endTagHack0 != null) {
        pageContext.releaseBody(_jsp_endTagHack0);
        _jsp_endTagHack0 = null;
      }
      out.write(_jsp_string4, 0, _jsp_string4.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_IteratorTag_0 != null)
        _jsp_IteratorTag_0.release();
      if (_jsp_PropertyTag_1 != null)
        _jsp_PropertyTag_1.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/information/vote/search_to_VotePaper.jsp"), 7440105709695064446L, false);
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

  private final static char []_jsp_string4;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  static {
    _jsp_string4 = "\r\n					</select>\r\n				</td>\r\n             </tr>\r\n            <tr valign=\"middle\"> \r\n               <td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\">\r\n					<span class=\"name\"> \u57f9\u8bad\u5355\u4f4d\uff1a&nbsp;&nbsp;</span>\r\n               </td>\r\n               <td class=\"postFormBox\" style=\"padding-left:10px\">\r\n               	<select name=\"peUnit\" id='peunit' onchange=\"initSubject(this.value,'applyno')\">\r\n               		<option value = \"\">\u8bf7\u9009\u62e9...</option>\r\n               	</select>\r\n               </td>\r\n             </tr>\r\n			<tr valign=\"middle\"> \r\n				<td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\">\r\n					<span class=\"name\"> \u5b66&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u79d1\uff1a&nbsp;&nbsp;</span>\r\n				</td>\r\n				<td class=\"postFormBox\" style=\"padding-left:10px\">\r\n					<select name=\"peSubject\" id='expsubject'>\r\n						<option value = \"\">\u8bf7\u9009\u62e9...</option>\r\n					</select>\r\n			   </td>\r\n			</tr>\r\n			<tr valign=\"middle\"> \r\n				<td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\">\r\n					<span class=\"name\"> \u7701&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u4efd\uff1a&nbsp;&nbsp;</span>\r\n				</td>\r\n				<td class=\"postFormBox\" style=\"padding-left:10px\">\r\n					<select name=\"peProvince\" id='expprovince' >\r\n						<option value = \"\">\u8bf7\u9009\u62e9...</option>\r\n					</select>\r\n					<span class=\"STYLE1\">&nbsp;&nbsp;&nbsp;*\u4ec5\u4f9b\u4e2d\u897f\u90e8\u9879\u76ee\u9009\u62e9</span>\r\n			   </td>\r\n			</tr>\r\n			<tr>\r\n				<td height=\"50\" align=\"center\" colspan=\"2\">\r\n					<input type=\"button\" value=\"\u67e5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\u770b\" style=\"width:100\" onClick=\"chk()\"/>\r\n				</td>\r\n			</tr>\r\n        </table>\r\n	</form>\r\n	</div>\r\n</div>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string2 = "\">".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n  <style type=\"text/css\">\r\n<!--\r\n.STYLE1 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n	font-family: \"\u65b0\u5b8b\u4f53\";\r\n}\r\n-->\r\n</style>\r\n	<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n	  <script type=\"text/javascript\">\r\n		function chk(){\r\n			var applyno=document.getElementById('applyno');\r\n			var unit=document.getElementById('peunit');\r\n			var subject=document.getElementById('expsubject');\r\n			var province = document.getElementById('expprovince');\r\n			\r\n			if(applyno.value==''){\r\n				alert('\u57f9\u8bad\u9879\u76ee\u4e3a\u5fc5\u586b\u9879\uff0c\u8bf7\u786e\u8ba4\u9009\u62e9\uff01');\r\n				applyno.focus();\r\n				return false;\r\n			}\r\n			if(unit.value !='' && province.value !=''){\r\n				alert('\u9009\u62e9\u9519\u8bef\uff0c\u8bf7\u60a8\u91cd\u65b0\u9009\u62e9\uff01 ');\r\n				unit.focus();\r\n				return false;\r\n			}\r\n			document.getElementById('searchVote').submit();\r\n		}\r\n		// \u521b\u5efaXMLHttpRequest\u5bf9\u8c61\u5e76\u8fd4\u56de\r\n		function getXmlHttpRequest() {\r\n			var xhr;\r\n			if(window.XMLHttpRequest) {\r\n				xhr = new XMLHttpRequest();\r\n			}else {\r\n				xhr = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n			}\r\n			return xhr;\r\n		}\r\n		/**\r\n		*@description \u5728\u8ffd\u52a0option\u4e4b\u524d\u6e05\u9664select\u6807\u7b7e\u4e2d\u7684option\r\n		*@param \u9700\u8981\u5904\u7406\u7684select\u6807\u7b7eid\u53f7\r\n		*/\r\n		function clearOptions(selElementId) {\r\n			var options = document.getElementById(selElementId).options;\r\n			var length = options.length;\r\n			for(var i=1;i<length;i++) {\r\n				options.remove(1);\r\n			}\r\n		}\r\n		/**\r\n		*@description \u5904\u7406\u6570\u636e\uff0c\u5e76\u521b\u5efaoption\u52a0\u5230select\u5217\u8868\u4e2d\r\n		*@param selID \u9700\u8981\u5904\u7406\u7684select\u6807\u7b7eid\u53f7\r\n		*@param jsonData \u76ee\u6807\u6570\u636e\uff0c\u9700\u8981\u7b26\u5408\u4e00\u5b9a\u89c4\u5219\r\n		*/\r\n		function operateSel(selID,jsonData){\r\n			var sel = document.getElementById(selID);\r\n			clearOptions(selID);\r\n			var option;\r\n			if(jsonData.length==0) {\r\n				//option = document.createElement(\"<option>\u6ca1\u6709\u7b26\u5408\u6761\u4ef6\u7684\u6570\u636e</option>\");\r\n				option = new Option('\u6ca1\u6709\u7b26\u5408\u6761\u4ef6\u7684\u6570\u636e',null);\r\n				sel.add(option);\r\n				return;\r\n			}\r\n			var jsonArr = jsonData.split(\";\");\r\n			var json;\r\n			for(var index in jsonArr) {\r\n				json = jsonArr[index].split(\",\");\r\n				//option = document.createElement(\"<option value='\"+json[0]+\"'>\"+json[1]+\"</option>\");\r\n				option = new Option(json[1],json[0]);\r\n				sel.add(option);\r\n			}\r\n		}\r\n		// \u521d\u59cb\u5316\u5355\u4f4d\u4e0b\u62c9\u5217\u8868\r\n		function initUnit(proApplyNo) {\r\n			clearOptions('expsubject');\r\n			var xhr = getXmlHttpRequest();\r\n			xhr.open(\"get\",\"/entity/information/peVotePaper_initUnit.action?proApplyNo=\"+encodeURI(proApplyNo));\r\n			xhr.onreadystatechange=function () {\r\n				var state = xhr.readyState;\r\n				if(state == 4) {\r\n					operateSel('peunit',xhr.responseText);\r\n				}\r\n			};\r\n			xhr.send(null);\r\n			\r\n			initProvince(proApplyNo);\r\n		}\r\n		// \u521d\u59cb\u5316\u5b66\u79d1\u4e0b\u62c9\u5217\u8868\r\n		function initSubject(unitValue,proApplyId) {\r\n			var xhr = getXmlHttpRequest();\r\n			var proApply = document.getElementById(proApplyId);\r\n			//alert(proApply.options[proApply.selectedIndex].value);\r\n			var proApplyValue = proApply.options[proApply.selectedIndex].value;\r\n			xhr.open(\"get\",\"/entity/information/peVotePaper_initSubject.action?unitValue=\"+encodeURI(unitValue)+\"&proApplyNo=\"+proApplyValue);\r\n			xhr.onreadystatechange = function() {\r\n				var state = xhr.readyState;\r\n				if(state == 4) {\r\n					operateSel('expsubject',xhr.responseText);\r\n				}\r\n			}\r\n			xhr.send(null);\r\n		}\r\n		\r\n		// \u521d\u59cb\u5316\u7701\u4efd\u4e0b\u62c9\u5217\u8868\r\n		function initProvince(proApplyNo) {\r\n		//clearOptions('expprovince');\r\n			var xhr = getXmlHttpRequest();\r\n			//alert(proApply.options[proApply.selectedIndex].value);\r\n			//var proApplyValue = proApply.options[proApply.selectedIndex].value;\r\n			xhr.open(\"get\",\"/entity/information/peVotePaper_initProvince.action?proApplyNo=\"+encodeURI(proApplyNo));\r\n			xhr.onreadystatechange = function() {\r\n				var state = xhr.readyState;\r\n				if(state == 4) {\r\n					operateSel('expprovince',xhr.responseText);\r\n				}\r\n			}\r\n			xhr.send(null);\r\n		}\r\n	</script>\r\n</head>\r\n  \r\n<body topmargin=\"0\" leftmargin=\"0\"  bgcolor=\"#FAFAFA\">\r\n<div id=\"main_content\">\r\n    <div class=\"content_title\">\u67e5\u770b\u8c03\u67e5\u95ee\u5377\u5217\u8868</div>\r\n   	<div class=\"k_cc\">\r\n		<form id='searchVote' action=\"/entity/information/peVotePaper.action\" method=\"get\">\r\n		<table width=\"554\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:50px\">\r\n            <tr valign=\"middle\"> \r\n				<td width=\"140\" height=\"30\" align=\"left\" class=\"postFormBox\" style=\"padding-left:50px\"><span class=\"name\"> \u57f9\u8bad\u9879\u76ee\uff1a*</span></td>\r\n				<td class=\"postFormBox\" style=\"padding-left:10px\">\r\n					<select name=\"search1_peProApplyno.id\" id='applyno' onchange=\"initUnit(this.value)\">\r\n						<option value = \"\">\u8bf7\u9009\u62e9...</option>\r\n						".toCharArray();
    _jsp_string3 = "</option>\r\n						".toCharArray();
    _jsp_string1 = "\r\n							<option value=\"".toCharArray();
  }
}
