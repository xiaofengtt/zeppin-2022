/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._bzz_22dstudents;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _student_0vote__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.IteratorTag _jsp_IteratorTag_1 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_2 = null;
    org.apache.struts2.views.jsp.DateTag _jsp_DateTag_3 = null;
    org.apache.struts2.views.jsp.IfTag _jsp_IfTag_4 = null;
    org.apache.struts2.views.jsp.ElseTag _jsp_ElseTag_5 = null;
    org.apache.struts2.views.jsp.PropertyTag _jsp_PropertyTag_6 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("peVotePaper.id");
      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string1, 0, _jsp_string1.length);
      _jsp_PropertyTag_0.setValue("peVotePaper.id");
      int _jspEval6 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string2, 0, _jsp_string2.length);
      _jsp_PropertyTag_0.setValue("peVotePaper.id");
      int _jspEval10 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string3, 0, _jsp_string3.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack12 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IteratorTag_1.setId("");
        _jsp_IteratorTag_1.setValue("peVotePaperList");
        _jsp_IteratorTag_1.setStatus("pt");
      }

      int _jspEval14 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval14 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval14 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack12 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack12);
        }
        do {
          out.write(_jsp_string4, 0, _jsp_string4.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("id");
          int _jspEval17 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string5, 0, _jsp_string5.length);
          _jsp_PropertyTag_2.setValue("title");
          int _jspEval21 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string6, 0, _jsp_string6.length);
          if (_jsp_DateTag_3 == null) {
            _jsp_DateTag_3 = new org.apache.struts2.views.jsp.DateTag();
            _jsp_DateTag_3.setPageContext(pageContext);
            _jsp_DateTag_3.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
            _jsp_DateTag_3.setName("foundDate");
            _jsp_DateTag_3.setFormat("yyyy-MM-dd");
          }

          int _jspEval25 = _jsp_DateTag_3.doStartTag();
          _jsp_DateTag_3.doEndTag();
          out.write(_jsp_string7, 0, _jsp_string7.length);
          _jsp_PropertyTag_2.setValue("id");
          int _jspEval29 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string8, 0, _jsp_string8.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval14 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack12 != null) {
        pageContext.releaseBody(_jsp_endTagHack12);
        _jsp_endTagHack12 = null;
      }
      out.write(_jsp_string9, 0, _jsp_string9.length);
      _jsp_PropertyTag_0.setValue("totalPage");
      int _jspEval34 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string10, 0, _jsp_string10.length);
      _jsp_PropertyTag_0.setValue("totalSize");
      int _jspEval38 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string11, 0, _jsp_string11.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack40 = null;
      if (_jsp_IfTag_4 == null) {
        _jsp_IfTag_4 = new org.apache.struts2.views.jsp.IfTag();
        _jsp_IfTag_4.setPageContext(pageContext);
        _jsp_IfTag_4.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IfTag_4.setTest("curPage == 1");
      int _jspEval42 = _jsp_IfTag_4.doStartTag();
      if (_jspEval42 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval42 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack40 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IfTag_4.setBodyContent(_jsp_endTagHack40);
        }
        out.write(_jsp_string12, 0, _jsp_string12.length);
        if (_jspEval42 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IfTag_4.doEndTag();
      if (_jsp_endTagHack40 != null) {
        pageContext.releaseBody(_jsp_endTagHack40);
        _jsp_endTagHack40 = null;
      }
      out.write(_jsp_string13, 0, _jsp_string13.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack44 = null;
      if (_jsp_ElseTag_5 == null) {
        _jsp_ElseTag_5 = new org.apache.struts2.views.jsp.ElseTag();
        _jsp_ElseTag_5.setPageContext(pageContext);
        _jsp_ElseTag_5.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      int _jspEval46 = _jsp_ElseTag_5.doStartTag();
      if (_jspEval46 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack44 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_ElseTag_5.setBodyContent(_jsp_endTagHack44);
        }
        out.write(_jsp_string14, 0, _jsp_string14.length);
        if (_jsp_PropertyTag_6 == null) {
          _jsp_PropertyTag_6 = new org.apache.struts2.views.jsp.PropertyTag();
          _jsp_PropertyTag_6.setPageContext(pageContext);
          _jsp_PropertyTag_6.setParent((javax.servlet.jsp.tagext.Tag) _jsp_ElseTag_5);
        }

        _jsp_PropertyTag_6.setValue("%{curPage-1}");
        int _jspEval49 = _jsp_PropertyTag_6.doStartTag();
        _jsp_PropertyTag_6.doEndTag();
        out.write(_jsp_string15, 0, _jsp_string15.length);
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_ElseTag_5.doEndTag();
      if (_jsp_endTagHack44 != null) {
        pageContext.releaseBody(_jsp_endTagHack44);
        _jsp_endTagHack44 = null;
      }
      out.write(_jsp_string13, 0, _jsp_string13.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack52 = null;
      _jsp_IfTag_4.setTest("curPage == totalPage");
      int _jspEval54 = _jsp_IfTag_4.doStartTag();
      if (_jspEval54 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval54 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack52 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IfTag_4.setBodyContent(_jsp_endTagHack52);
        }
        out.write(_jsp_string16, 0, _jsp_string16.length);
        if (_jspEval54 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IfTag_4.doEndTag();
      if (_jsp_endTagHack52 != null) {
        pageContext.releaseBody(_jsp_endTagHack52);
        _jsp_endTagHack52 = null;
      }
      out.write(_jsp_string13, 0, _jsp_string13.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack56 = null;
      int _jspEval58 = _jsp_ElseTag_5.doStartTag();
      if (_jspEval58 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack56 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_ElseTag_5.setBodyContent(_jsp_endTagHack56);
        }
        out.write(_jsp_string17, 0, _jsp_string17.length);
        if (_jsp_PropertyTag_6 == null) {
          _jsp_PropertyTag_6 = new org.apache.struts2.views.jsp.PropertyTag();
          _jsp_PropertyTag_6.setPageContext(pageContext);
          _jsp_PropertyTag_6.setParent((javax.servlet.jsp.tagext.Tag) _jsp_ElseTag_5);
        }

        _jsp_PropertyTag_6.setValue("%{curPage+1}");
        int _jspEval61 = _jsp_PropertyTag_6.doStartTag();
        _jsp_PropertyTag_6.doEndTag();
        out.write(_jsp_string18, 0, _jsp_string18.length);
        _jsp_PropertyTag_6.setValue("totalPage");
        int _jspEval65 = _jsp_PropertyTag_6.doStartTag();
        _jsp_PropertyTag_6.doEndTag();
        out.write(_jsp_string19, 0, _jsp_string19.length);
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_ElseTag_5.doEndTag();
      if (_jsp_endTagHack56 != null) {
        pageContext.releaseBody(_jsp_endTagHack56);
        _jsp_endTagHack56 = null;
      }
      out.write(_jsp_string20, 0, _jsp_string20.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_PropertyTag_0 != null)
        _jsp_PropertyTag_0.release();
      if (_jsp_IteratorTag_1 != null)
        _jsp_IteratorTag_1.release();
      if (_jsp_PropertyTag_2 != null)
        _jsp_PropertyTag_2.release();
      if (_jsp_DateTag_3 != null)
        _jsp_DateTag_3.release();
      if (_jsp_IfTag_4 != null)
        _jsp_IfTag_4.release();
      if (_jsp_ElseTag_5 != null)
        _jsp_ElseTag_5.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/bzz-students/student_vote.jsp"), -3642778050218617556L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.DateTag.class, 4924450609192765402L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IteratorTag.class, -2243808422739547962L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IfTag.class, 2691840914333557849L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.ElseTag.class, 719326203869585948L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string3;
  private final static char []_jsp_string17;
  private final static char []_jsp_string20;
  private final static char []_jsp_string19;
  private final static char []_jsp_string4;
  private final static char []_jsp_string5;
  private final static char []_jsp_string16;
  private final static char []_jsp_string0;
  private final static char []_jsp_string10;
  private final static char []_jsp_string8;
  private final static char []_jsp_string15;
  private final static char []_jsp_string12;
  private final static char []_jsp_string9;
  private final static char []_jsp_string6;
  private final static char []_jsp_string14;
  private final static char []_jsp_string1;
  private final static char []_jsp_string18;
  private final static char []_jsp_string2;
  private final static char []_jsp_string11;
  private final static char []_jsp_string13;
  private final static char []_jsp_string7;
  static {
    _jsp_string3 = ");\r\n</script>\r\n<!-- \r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">\r\n  <tr>\r\n    <td>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <IFRAME NAME=\"top\" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC=\"/entity/bzz-students/pub/top.jsp\" scrolling=no allowTransparency=\"true\"></IFRAME>\r\n  <tr>\r\n    <td height=\"13\"></td>\r\n  </tr>\r\n</table>\r\n<table width=\"1002\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td width=\"237\" valign=\"top\"><IFRAME NAME=\"leftA\" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC=\"/entity/bzz-students/pub/left.jsp\" scrolling=no allowTransparency=\"true\"></IFRAME></td>\r\n   <td width=\"752\" valign=\"top\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr align=\"center\" valign=\"top\">\r\n        <td height=\"17\" align=\"left\" class=\"twocentop\"><img src=\"/entity/bzz-students/images/two/1.jpg\" width=\"11\" height=\"12\" /> \u5f53\u524d\u4f4d\u7f6e\uff1a\u8c03\u67e5\u95ee\u5377</td>\r\n      </tr>\r\n       <tr>\r\n            <td align=\"left\"><img src=\"/entity/bzz-students/images/two/vote.jpg\" width=\"124\" height=\"25\" /></td>\r\n          </tr>\r\n      <tr align=\"center\">\r\n        <td height=\"29\" background=\"/entity/bzz-students/images/two/two2_r15_c5.jpg\">\r\n        <table width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"twocencetop\">\r\n          <tr>\r\n            <td width=\"10%\" align=\"center\">&nbsp;</td>\r\n            <td width=\"52%\" align=\"left\">\u540d\u79f0</td>\r\n            <td width=\"8%\" align=\"right\">                                   \u53d1\u5e03\u65f6\u95f4                  </td>\r\n            <td width=\"30%\" align=\"center\">\u64cd\u4f5c</td>\r\n          </tr>\r\n        </table>\r\n        </td>\r\n      </tr>\r\n      <tr valign=\"top\" align=\"center\">\r\n        <td ><table width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"twocencetop\">\r\n        ".toCharArray();
    _jsp_string17 = "\r\n						            		<a class=\"twocen1\" href=\"/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=".toCharArray();
    _jsp_string20 = "\r\n			                      </td>\r\n         					</tr>\r\n         			</table>\r\n         		</td>\r\n         	</tr> \r\n          </table></td>\r\n      </tr>\r\n      <tr>\r\n       <td width=\"13\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td width=\"13\">&nbsp;</td>\r\n  </tr>\r\n</table>\r\n<IFRAME NAME=\"top\" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC=\"/entity/bzz-students/pub/bottom.jsp\" scrolling=no allowTransparency=\"true\" align=center></IFRAME>\r\n</td>\r\n</tr>\r\n</table>\r\n -->\r\n</body>\r\n</html>".toCharArray();
    _jsp_string19 = "\">\u5c3e\u9875</a>\r\n						            	".toCharArray();
    _jsp_string4 = "\r\n          <tr>\r\n            <td width=\"10%\" align=\"center\"><img src=\"/entity/bzz-students/images/two/4.jpg\" width=\"9\" height=\"9\" /></td>\r\n            <td width=\"52%\" align=\"left\"><a target=\"_blank\" href=\"/entity/first/firstPeVotePaper_toVote.action?bean.id=".toCharArray();
    _jsp_string5 = "\">".toCharArray();
    _jsp_string16 = "\r\n						            		<font color=\"gray\">\u4e0b\u4e00\u9875 \u5c3e\u9875</font>\r\n						            	".toCharArray();
    _jsp_string0 = "\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n<title>\u56fd\u57f9\u8ba1\u5212\u5b66\u5458\u5de5\u4f5c\u5ba4</title>\r\n<link href=\"/entity/bzz-students/css.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<style type=\"text/css\">\r\n<!--\r\nbody {\r\n	margin-left: 0px;\r\n	margin-top: 0px;\r\n	margin-right: 0px;\r\n	margin-bottom: 0px;\r\n}\r\n.STYLE1 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n}\r\n.STYLE2 {\r\n	color: #FF5F01;\r\n	font-weight: bold;\r\n}\r\n\r\na:link { text-decoration: none;color: #000000}\r\na:active { text-decoration:blink}\r\na:hover { text-decoration:underline;color: red} \r\na:visited { text-decoration: none;color: #000000}\r\n\r\n-->\r\n</style>\r\n<script type=\"text/javascript\">\r\n	window.onload = function() {\r\n		window.location.href('/entity/first/firstPeVotePaper_toVote.action?bean.id=".toCharArray();
    _jsp_string10 = " \u9875 ".toCharArray();
    _jsp_string8 = "\" title=\"\u5f00\u59cb\u6295\u7968\"><img src=\"/entity/bzz-students/images/kctp.jpg\" width=\"100\" height=\"22\" border=\"0\" /></a>\r\n            </td>\r\n          </tr>\r\n          <tr>\r\n            <td colspan=\"4\"><img src=\"/entity/bzz-students/images/two/7.jpg\" width=\"752\" height=\"4\" /></td>\r\n          </tr>\r\n         ".toCharArray();
    _jsp_string15 = "\">\u4e0a\u4e00\u9875</a>\r\n						            	".toCharArray();
    _jsp_string12 = "\r\n						            		<font color=\"gray\">\u9996\u9875 \u4e0a\u4e00\u9875</font>\r\n						            	".toCharArray();
    _jsp_string9 = "\r\n         \r\n         <tr align=\"center\">\r\n         		<td height=\"29\" background=\"/entity/bzz-students/images/two/two2_r15_c5.jpg\" colspan=\"8\">\r\n         			<table width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"twocencetop\">\r\n         					<tr>\r\n         						<td align=\"right\" style=\"font-size:12px\">\r\n			                      	\u5171 ".toCharArray();
    _jsp_string6 = "</a></td>\r\n            <td width=\"8%\" align=\"left\">".toCharArray();
    _jsp_string14 = "\r\n						            		<a class=\"twocen1\" href=\"/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=1\">\u9996\u9875</a>\r\n						            		<a class=\"twocen1\" href=\"/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=".toCharArray();
    _jsp_string1 = "');\r\n	}\r\n</script>\r\n</head>\r\n\r\n<body bgcolor=\"#E0E0E0\">\r\n<script type=\"text/javascript\">\r\n	//window.location.href('/entity/first/firstPeVotePaper_toVote.action?bean.id=".toCharArray();
    _jsp_string18 = "\">\u4e0b\u4e00\u9875</a>\r\n						            		<a class=\"twocen1\" href=\"/entity/workspaceStudent/bzzstudent_getVoteList.action?curPage=".toCharArray();
    _jsp_string2 = "');\r\n	//document.write(\"hehe \");\r\n	//document.write(\"id:\" + ".toCharArray();
    _jsp_string11 = " \u6761\u8bb0\u5f55 | \r\n						            	".toCharArray();
    _jsp_string13 = "\r\n						            	".toCharArray();
    _jsp_string7 = "</td>\r\n            <td width=\"30%\"><a target=\"_blank\" href=\"/entity/first/firstPeVotePaper_toVote.action?bean.id=".toCharArray();
  }
}
