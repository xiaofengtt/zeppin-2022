/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._web._bzz_0index._login;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _reg__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.IfTag _jsp_IfTag_0 = null;
    org.apache.struts2.views.jsp.ui.ActionMessageTag _jsp_ActionMessageTag_1 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_2 = null;
    org.apache.struts2.views.jsp.ElseTag _jsp_ElseTag_3 = null;
    org.apache.struts2.views.jsp.ui.ActionMessageTag _jsp_ActionMessageTag_4 = null;
    org.apache.struts2.views.jsp.IteratorTag _jsp_IteratorTag_5 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_6 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack0 = null;
      if (_jsp_IfTag_0 == null) {
        _jsp_IfTag_0 = new org.apache.struts2.views.jsp.IfTag();
        _jsp_IfTag_0.setPageContext(pageContext);
        _jsp_IfTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IfTag_0.setTest("flag");
      }

      int _jspEval2 = _jsp_IfTag_0.doStartTag();
      if (_jspEval2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack0 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IfTag_0.setBodyContent(_jsp_endTagHack0);
        }
        out.write(_jsp_string1, 0, _jsp_string1.length);
        if (_jsp_ActionMessageTag_1 == null) {
          _jsp_ActionMessageTag_1 = new org.apache.struts2.views.jsp.ui.ActionMessageTag();
          _jsp_ActionMessageTag_1.setPageContext(pageContext);
          _jsp_ActionMessageTag_1.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IfTag_0);
        }

        int _jspEval5 = _jsp_ActionMessageTag_1.doStartTag();
        _jsp_ActionMessageTag_1.doEndTag();
        out.write(_jsp_string2, 0, _jsp_string2.length);
        if (_jsp_PropertyTag_2 == null) {
          _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
          _jsp_PropertyTag_2.setPageContext(pageContext);
          _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IfTag_0);
        }

        _jsp_PropertyTag_2.setValue("logID");
        int _jspEval9 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string3, 0, _jsp_string3.length);
        _jsp_PropertyTag_2.setValue("password");
        int _jspEval13 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string4, 0, _jsp_string4.length);
        _jsp_PropertyTag_2.setValue("realName");
        int _jspEval17 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string5, 0, _jsp_string5.length);
        _jsp_PropertyTag_2.setValue("cardId");
        int _jspEval21 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string6, 0, _jsp_string6.length);
        _jsp_PropertyTag_2.setValue("email");
        int _jspEval25 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string7, 0, _jsp_string7.length);
        _jsp_PropertyTag_2.setValue("trainLevel_name");
        int _jspEval29 = _jsp_PropertyTag_2.doStartTag();
        _jsp_PropertyTag_2.doEndTag();
        out.write(_jsp_string8, 0, _jsp_string8.length);
        if (_jspEval2 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IfTag_0.doEndTag();
      if (_jsp_endTagHack0 != null) {
        pageContext.releaseBody(_jsp_endTagHack0);
        _jsp_endTagHack0 = null;
      }
      out.write(_jsp_string9, 0, _jsp_string9.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack32 = null;
      if (_jsp_ElseTag_3 == null) {
        _jsp_ElseTag_3 = new org.apache.struts2.views.jsp.ElseTag();
        _jsp_ElseTag_3.setPageContext(pageContext);
        _jsp_ElseTag_3.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      int _jspEval34 = _jsp_ElseTag_3.doStartTag();
      if (_jspEval34 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval34 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack32 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_ElseTag_3.setBodyContent(_jsp_endTagHack32);
        }
        out.write(_jsp_string10, 0, _jsp_string10.length);
        if (_jsp_ActionMessageTag_4 == null) {
          _jsp_ActionMessageTag_4 = new org.apache.struts2.views.jsp.ui.ActionMessageTag();
          _jsp_ActionMessageTag_4.setPageContext(pageContext);
          _jsp_ActionMessageTag_4.setParent((javax.servlet.jsp.tagext.Tag) _jsp_ElseTag_3);
        }

        int _jspEval37 = _jsp_ActionMessageTag_4.doStartTag();
        _jsp_ActionMessageTag_4.doEndTag();
        out.write(_jsp_string11, 0, _jsp_string11.length);
        com.caucho.jsp.BodyContentImpl _jsp_endTagHack39 = null;
        if (_jsp_IteratorTag_5 == null) {
          _jsp_IteratorTag_5 = new org.apache.struts2.views.jsp.IteratorTag();
          _jsp_IteratorTag_5.setPageContext(pageContext);
          _jsp_IteratorTag_5.setParent((javax.servlet.jsp.tagext.Tag) _jsp_ElseTag_3);
          _jsp_IteratorTag_5.setValue("trainingLevel");
          _jsp_IteratorTag_5.setId("level");
        }

        int _jspEval41 = _jsp_IteratorTag_5.doStartTag();
        if (_jspEval41 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          if (_jspEval41 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
            out = pageContext.pushBody();
            _jsp_endTagHack39 = (com.caucho.jsp.BodyContentImpl) out;
            _jsp_IteratorTag_5.setBodyContent(_jsp_endTagHack39);
          }
          do {
            out.write(_jsp_string12, 0, _jsp_string12.length);
            if (_jsp_PropertyTag_6 == null) {
              _jsp_PropertyTag_6 = new org.apache.struts2.views.jsp.PropertyTag();
              _jsp_PropertyTag_6.setPageContext(pageContext);
              _jsp_PropertyTag_6.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_5);
            }

            _jsp_PropertyTag_6.setValue("#level.id");
            int _jspEval44 = _jsp_PropertyTag_6.doStartTag();
            _jsp_PropertyTag_6.doEndTag();
            out.write(_jsp_string13, 0, _jsp_string13.length);
            _jsp_PropertyTag_6.setValue("#level.name");
            int _jspEval48 = _jsp_PropertyTag_6.doStartTag();
            _jsp_PropertyTag_6.doEndTag();
            out.write(_jsp_string14, 0, _jsp_string14.length);
          } while (_jsp_IteratorTag_5.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
          if (_jspEval41 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
            out = pageContext.popBody();
        }
        _jsp_IteratorTag_5.doEndTag();
        if (_jsp_endTagHack39 != null) {
          pageContext.releaseBody(_jsp_endTagHack39);
          _jsp_endTagHack39 = null;
        }
        out.write(_jsp_string15, 0, _jsp_string15.length);
        if (_jspEval34 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_ElseTag_3.doEndTag();
      if (_jsp_endTagHack32 != null) {
        pageContext.releaseBody(_jsp_endTagHack32);
        _jsp_endTagHack32 = null;
      }
      out.write(_jsp_string16, 0, _jsp_string16.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_IfTag_0 != null)
        _jsp_IfTag_0.release();
      if (_jsp_ActionMessageTag_1 != null)
        _jsp_ActionMessageTag_1.release();
      if (_jsp_PropertyTag_2 != null)
        _jsp_PropertyTag_2.release();
      if (_jsp_ElseTag_3 != null)
        _jsp_ElseTag_3.release();
      if (_jsp_ActionMessageTag_4 != null)
        _jsp_ActionMessageTag_4.release();
      if (_jsp_IteratorTag_5 != null)
        _jsp_IteratorTag_5.release();
      if (_jsp_PropertyTag_6 != null)
        _jsp_PropertyTag_6.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("web/bzz_index/login/reg.jsp"), -6239194481848776128L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.ui.ActionMessageTag.class, 1232734647203350090L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IfTag.class, 2691840914333557849L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IteratorTag.class, -2243808422739547962L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.ElseTag.class, 719326203869585948L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string8;
  private final static char []_jsp_string15;
  private final static char []_jsp_string12;
  private final static char []_jsp_string3;
  private final static char []_jsp_string10;
  private final static char []_jsp_string4;
  private final static char []_jsp_string13;
  private final static char []_jsp_string11;
  private final static char []_jsp_string5;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string16;
  private final static char []_jsp_string7;
  private final static char []_jsp_string14;
  private final static char []_jsp_string6;
  private final static char []_jsp_string9;
  private final static char []_jsp_string1;
  static {
    _jsp_string8 = "</td>\r\n            </tr>\r\n          </table>\r\n         </form>\r\n        </td>\r\n      ".toCharArray();
    _jsp_string15 = "\r\n               </select></td>\r\n            </tr>\r\n            <tr>\r\n            	<td height=\"35\" colspan=\"4\"></td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" colspan=\"2\" align=\"right\"></td>\r\n              <td width=\"194\" align=\"right\"><span class=\"STYLE2\">\r\n                <input type=\"image\" name=\"imageField3\" src=\"images/si.jpg\" />\r\n              </span></td>\r\n              <td width=\"321\" align=\"center\"><span class=\"STYLE2\">\uff08 \u6ce8\uff1a\u4ee5\u4e0a\u4fe1\u606f\u53ea\u9650\u4e8e\u672c\u7ad9\u57fa\u672c\u6570\u636e\uff0c\u51b3\u4e0d\u7528\u4e8e\u5b83\u5904\uff09</span></td>\r\n            </tr>\r\n          </table>\r\n         </form>\r\n        </td>\r\n      ".toCharArray();
    _jsp_string12 = "\r\n                <option value=\"".toCharArray();
    _jsp_string3 = "</td>\r\n            </tr>\r\n            <tr align=\"center\">\r\n              <td height=\"35\" align=\"right\">\u5bc6\u3000\u3000\u7801:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string10 = "\r\n        <td align=\"center\"><form id=\"registForm\" name=\"registForm\" method=\"post\" action=\"/entity/first/userRegist_regist.action\" onSubmit=\"return checkRegsitForm()\">\r\n          <table width=\"680\" cellspacing=\"0\" cellpadding=\"3\" style=\"font-size:12px; font-family:simsun;\">\r\n            <font color=\"red\">".toCharArray();
    _jsp_string4 = "</td>\r\n            </tr>\r\n            <tr align=\"center\">\r\n              <td height=\"35\" align=\"right\">\u59d3\u3000\u3000\u540d:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string13 = "\">".toCharArray();
    _jsp_string11 = "</font>\r\n            <tr>\r\n              <td width=\"133\" height=\"35\" align=\"right\">\u7528 \u6237 \u540d</td>\r\n              <td width=\"6\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"><input type=\"text\" name=\"logID\" class=\"inputs\"/>\r\n                (\u6ce8\uff1a\u9650\u82f1\u6587,\u6570\u5b57\u6216\u4e0b\u5212\u7ebf\uff0c4-16\u4f4d\uff0c\u4e0d\u80fd\u4ee5\u4e0b\u5212\u7ebf\u7ed3\u5c3e)</td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" align=\"right\">\u5bc6\u3000\u3000\u7801</td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"><input type=\"password\" name=\"password\" class=\"inputs\"/>\r\n                (\u6ce8\uff1a\u9650\u5b57\u7b26\u3001\u6570\u5b57\u7b49\uff0c4-16\u4f4d)</td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" align=\"right\">\u786e\u8ba4\u5bc6\u7801</td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"> <input type=\"password\" name=\"rePassword\" class=\"inputs\"/>\r\n                (\u6ce8\uff1a\u9650\u5b57\u7b26\u3001\u6570\u5b57\u7b49\uff0c4-16\u4f4d) </td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" align=\"right\">\u59d3\u3000\u3000\u540d</td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"> <input type=\"text\" name=\"realName\" class=\"inputs\"/>\r\n                (\u6ce8\uff1a\u771f\u5b9e\u59d3\u540d)</td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" align=\"right\">\u8eab \u4efd \u8bc1</td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"><input type=\"text\" name=\"cardId\" class=\"inputs\"/></td>\r\n            </tr>\r\n             <tr>\r\n              <td height=\"35\" align=\"right\">\u6ce8\u518c\u90ae\u7bb1</td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"><input type=\"text\" name=\"email\" class=\"inputs\"/>\r\n              (\u6ce8\uff1a\u8bf7\u586b\u5199\u60a8\u7684\u5e38\u7528\u90ae\u7bb1\uff0c\u6b64\u6ce8\u518c\u4fe1\u606f\u6210\u529f\u540e\u4f1a\u53d1\u9001\u6b64\u90ae\u7bb1)</td>\r\n            </tr>\r\n            <tr>\r\n              <td height=\"35\" align=\"right\">\u57f9\u8bad\u7ea7\u522b </td>\r\n              <td height=\"35\" align=\"left\"><span class=\"STYLE1\">*</span></td>\r\n              <td colspan=\"2\" align=\"left\"><select name=\"trainLevel\" class=\"selects\">\r\n              	".toCharArray();
    _jsp_string5 = "</td>\r\n            </tr>\r\n            <tr align=\"center\">\r\n              <td height=\"35\" align=\"right\">\u8eab \u4efd \u8bc1:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string2 = "</font>\r\n          <table width=\"680\" align=\"center\" cellspacing=\"0\" cellpadding=\"3\" style=\"font-size:14px; color:gray; font-weight:bold; font-family:simsun;\">\r\n            <tr align=\"center\">\r\n              <td width=\"133\" height=\"35\" align=\"right\">\u7528 \u6237 \u540d:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string0 = "\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<title>\u751f\u6b96\u5065\u5eb7\u54a8\u8be2\u5e08\u57f9\u8bad\u7f51</title>\r\n<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<style type=\"text/css\">\r\n<!--\r\n.STYLE1 {color: #FF0000}\r\n.STYLE2 {color: #004DB2}\r\n-->\r\n</style>\r\n\r\n<script language=\"javascript\">\r\nfunction checkForm(){\r\n	if(document.getElementById('searchVal').value=='\u8bf7\u8f93\u5165\u5173\u952e\u5b57' || document.getElementById('searchVal').value==''){\r\n		alert('\u8bf7\u8f93\u5165\u5173\u952e\u5b57');\r\n		return false;\r\n	}else\r\n		return true\r\n}\r\nfunction formtest(){\r\n	if(document.loginform.loginId.value==''){\r\n		alert(\"\u8bf7\u8f93\u5165\u7528\u6237\u540d\uff01\");\r\n		document.loginform.loginId.focus();\r\n		return false;\r\n	}\r\n	if(document.loginform.passwd.value==''){\r\n		alert(\"\u8bf7\u8f93\u5165\u5bc6\u7801\uff01\");\r\n		document.loginform.passwd.focus();\r\n		return false;\r\n	}\r\n	if(document.loginform.authCode.value==''){\r\n		alert(\"\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801\uff01\");\r\n		document.loginform.authCode.focus();\r\n		return false;\r\n	}\r\n	return true;\r\n}\r\nfunction checkRegsitForm(){\r\n	var logID = document.getElementById('logID').value;\r\n	var reglogID = /^\\w{3,15}[A-Za-z0-9]$/;\r\n	if(!reglogID.test(logID)) {\r\n		alert(\"\u7528\u6237\u540d\uff1a\u7528\u6237\u540d\u8f93\u5165\u9519\u8bef\uff01\");\r\n		return false;\r\n	}\r\n	\r\n	var password = document.getElementById('password').value;\r\n	var regPassword = /^[A-Za-z0-9!@#$%^&*()]{4,16}$/;\r\n	if(!regPassword.test(password)) {\r\n		alert(\"\u5bc6\u7801\uff1a\u5bc6\u7801\u8f93\u5165\u9519\u8bef\uff01\");\r\n		return false;\r\n	}\r\n	\r\n	var passwordCheck = document.getElementById('rePassword').value;\r\n	if(passwordCheck != password) {\r\n		alert(\"\u786e\u8ba4\u5bc6\u7801\uff1a\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u540c\uff01\");\r\n		return false;\r\n	}\r\n	\r\n	var realName = document.getElementById('realName').value;\r\n	if(realName == \"\") {\r\n		alert(\"\u59d3\u540d\uff1a\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n		return false;\r\n	}\r\n	\r\n	var cardno = document.getElementById('cardId').value;\r\n	var regCardno =/(^\\d{15}$)|(\\d{17}(?:\\d|x|X))$/;\r\n	if(cardno == \"\" || !regCardno.test(cardno)) {\r\n		alert(\"\u8eab\u4efd\u8bc1\uff1a\u8eab\u4efd\u8bc1\u53f7\u7801\u8f93\u5165\u9519\u8bef\uff01\");\r\n		return false;\r\n	}\r\n	var email = document.getElementById('email').value;\r\n	var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$/;\r\n	if(email==\"\"||!emailReg.test(email)){\r\n	alert(\"\u6ce8\u518c\u90ae\u7bb1\uff1a\u90ae\u7bb1\u586b\u5199\u6709\u8bef\uff01\");\r\n	return false;\r\n		}\r\n	\r\n}\r\n</script>\r\n\r\n</head>\r\n\r\n<body>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"98\" align=\"center\" valign=\"bottom\" background=\"images/bg.jpg\"><table width=\"977\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <!--  <td height=\"22\" colspan=\"2\" align=\"right\"><img src=\"images/gc.jpg\" width=\"44\" height=\"14\" />&nbsp;&nbsp;</td>-->\r\n        </tr>\r\n		<tr>\r\n        <td width=\"378\" align=\"left\" class=\"logo\"><font size=\"4\" style=\"font-family: \u534e\u6587\u65b0\u9b4f\">\u3000\u751f\u6b96\u5065\u5eb7\u54a8\u8be2\u5e08\u57f9\u8bad\u7f51</font></td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"15\" colspan=\"2\"></td>\r\n        </tr>\r\n      <tr>\r\n        <td colspan=\"2\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr>\r\n            <td width=\"53\" height=\"26\" align=\"center\" valign=\"bottom\" background=\"images/index_1.jpg\"><a href=\"/\">\u9996 \u9875</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=PTJS\">\u5e73\u53f0\u4ecb\u7ecd</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=XWZX\">\u65b0\u95fb\u4e2d\u5fc3</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=PXDT\">\u57f9\u8bad\u52a8\u6001</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=ZCFG\">\u653f\u7b56\u6cd5\u89c4</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=KCTX\">\u8bfe\u7a0b\u4f53\u7cfb</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=XXZY\">\u5b66\u4e60\u8d44\u6e90</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=PXSFC\">\u57f9\u8bad\u5e08\u98ce\u91c7</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top_1.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=ZXBM\">\u5728\u7ebf\u62a5\u540d</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=CJWT\">\u5e38\u89c1\u95ee\u9898</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=XZZX\">\u4e0b\u8f7d\u4e2d\u5fc3</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=BKZTC\">\u62a5\u8003\u76f4\u901a\u8f66</a></td>\r\n            <td width=\"2\" align=\"center\" valign=\"bottom\"></td>\r\n            <td width=\"75\" align=\"center\" valign=\"bottom\" background=\"images/top.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=SJTJ\">\u4e66\u7c4d\u63a8\u8350</a></td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"6\" bgcolor=\"#004EB2\"></td>\r\n    <td bgcolor=\"#004EB2\"></td>\r\n    <td bgcolor=\"#004EB2\"></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"13\"></td>\r\n    <td><img name=\"two_r3_c2\" src=\"images/two/two_r3_c2.jpg\" width=\"977\" height=\"148\" border=\"0\" id=\"two_r3_c2\" alt=\"\" /></td>\r\n    <td width=\"12\"></td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"13\"></td>\r\n    <td height=\"36\" valign=\"middle\" style=\"border-left:solid 1px #014CB2; border-right: solid 1px #014CB2; background:#EDEDED;\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <form id=\"searchForm\" name=\"searchForm\" method=\"post\" action=\"/entity/first/pageDispatch_search.action\" onSubmit=\"return checkForm()\">\r\n        <tr>\r\n          <td width=\"1%\"></td>\r\n          <td width=\"18%\" align=\"right\" valign=\"middle\"><input type=\"text\" name=\"searchVal\" value=\"\u8bf7\u8f93\u5165\u5173\u952e\u5b57\" style=\"color:#CCCCCC; border:solid 1px #7b7b7b; border-right:0; height:17px; width:160px;\" /></td>\r\n          <td width=\"33%\" align=\"left\" valign=\"middle\"><input type=\"image\" name=\"imageField\" src=\"images/search.jpg\" /></td>\r\n          <td width=\"47%\" align=\"right\">\u9996 \u9875 &gt; \u5728\u7ebf\u62a5\u540d</td>\r\n          <td width=\"1%\" align=\"right\"></td>\r\n        </tr>\r\n      </form>\r\n    </table></td>\r\n    <td width=\"12\"></td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"13\" rowspan=\"2\"></td>\r\n    <td width=\"228\" height=\"189\" align=\"center\" valign=\"top\" background=\"images/two/two_r6_c2.jpg\"><table width=\"220\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n			<td height=\"10\"></td>\r\n		</tr>\r\n		<tr>\r\n            <td height=\"110\" align=\"center\" valign=\"bottom\" background=\"images/index/index_r3_c6.jpg\"><table width=\"82%\" cellspacing=\"0\" cellpadding=\"0\">\r\n              <form id=\"loginform\" name=\"loginform\" method=\"post\" action=\"/sso/login_login.action\">\r\n                <tr>\r\n                	<td height=\"30\"></td>\r\n                </tr>\r\n                <tr>\r\n                  <td width=\"29%\" height=\"30\" style=\"font-size:12px\">\u7528\u6237\u540d\uff1a<br/></td>\r\n                  <td width=\"71%\" align=\"right\"><input name=\"loginId\" type=\"text\"  style=\"width:122px; height:18px; border: solid 1px #B8C5CE;\"/></td>\r\n                </tr>\r\n                <tr>\r\n                  <td height=\"30\" style=\"font-size:12px\">\u5bc6    \u7801\uff1a</td>\r\n                  <td align=\"right\"><input name=\"passwd\" type=\"password\"  style=\"width:122px; height:18px; border: solid 1px #B8C5CE;\"/></td>\r\n                </tr>\r\n                <tr>\r\n                 <td height=\"30\" style=\"font-size:12px;\">\u9a8c\u8bc1\u7801\uff1a</td>\r\n                 <td align=\"right\"><input type=\"text\" name=\"authCode\" style=\"width:62px; height:18px; border: solid 1px #B8C5CE;\"><img src=\"/sso/authimg\"  border=\"0\" align=\"absmiddle\"/></td>\r\n               </tr>\r\n                <tr>\r\n                  <td height=\"25\"></td>\r\n                  <td align=\"right\"><input type=\"image\" name=\"imageField\" src=\"images/logins.jpg\" />\r\n                      <a href=\"/entity/first/pageDispatch_dispatch.action?type=ZXBM\"><img src=\"images/reds.jpg\" border=\"0\" width=\"42\" height=\"21\" /></a></td>\r\n                </tr>\r\n              </form>\r\n            </table></td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"5\"></td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"63\" align=\"center\" background=\"images/indexbg.jpg\"><a href=\"/entity/first/pageDispatch_dispatch.action?type=ZXBM\"><img src=\"banner/ff.jpg\" border=\"0\" width=\"205\" height=\"49\" /></a></td>\r\n          </tr>\r\n    </table></td>\r\n    <td width=\"749\" align=\"center\" valign=\"top\" background=\"images/two/two_r6_c3.jpg\"><table width=\"693\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <td height=\"30\" valign=\"bottom\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td width=\"28\" align=\"center\" valign=\"middle\"><img src=\"images/d.jpg\" width=\"5\" height=\"15\" /></td>\r\n            <td width=\"635\" height=\"25\" align=\"left\" class=\"greens\">\u7528\u6237\u6ce8\u518c</td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n      <tr>\r\n        <td><img src=\"images/lin.jpg\" width=\"693\" height=\"10\" /></td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"20\"></td>\r\n      </tr>\r\n      <tr>\r\n      ".toCharArray();
    _jsp_string16 = "\r\n      </tr>\r\n      <tr>\r\n        <td align=\"center\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td width=\"12\" rowspan=\"2\"></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"71\" colspan=\"2\" align=\"center\" valign=\"top\" bgcolor=\"#004EB2\"><table width=\"86%\" cellpadding=\"0\" cellspacing=\"0\" class=\"down\">\r\n      <tr>\r\n        <td height=\"20\" colspan=\"2\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"13\" align=\"left\">\u4eacICP\u590706049188\u53f7\u3000Copyright&reg;2009 \u751f\u6b96\u5065\u5eb7\u54a8\u8be2\u5e08\u57f9\u8bad\u7f51</td>\r\n        <td align=\"right\">                                                     \u6280\u672f\u652f\u6301\uff1a \u5317\u4eac\u7f51\u68af\u79d1\u6280\u53d1\u5c55\u6709\u9650\u516c\u53f8</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string7 = "</td>\r\n            </tr>\r\n            </tr>\r\n            <tr align=\"center\">\r\n              <td height=\"35\" align=\"right\">\u57f9\u8bad\u7ea7\u522b:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string14 = "</option>\r\n                ".toCharArray();
    _jsp_string6 = "</td>\r\n              <tr align=\"center\">\r\n              <td height=\"35\" align=\"right\">\u6ce8\u518c\u90ae\u7bb1:</td>\r\n              <td align=\"left\">".toCharArray();
    _jsp_string9 = "\r\n      ".toCharArray();
    _jsp_string1 = "\r\n      	<td align=\"center\">\r\n      		<font color=\"red\">".toCharArray();
  }
}
