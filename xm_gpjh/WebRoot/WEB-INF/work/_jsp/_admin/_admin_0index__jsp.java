/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._admin;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _admin_0index__jsp extends com.caucho.jsp.JavaPage
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
      
String path = request.getContextPath();   
String basePase = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  

      out.write(_jsp_string1, 0, _jsp_string1.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/admin_index.jsp"), 1714737557627396987L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n<title>\u56fd\u57f9\u8ba1\u5212\u9879\u76ee\u7ba1\u7406\u7cfb\u7edf</title>\r\n<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<style type=\"text/css\">\r\n<!--\r\n.STYLE1 {color: #FFFFFF}\r\n.STYLE4 {\r\n	font-size: 18px;\r\n	font-weight: bold;\r\n}\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\" >\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"113\"><table width=\"100%\" height=\"113\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#E6EEF2\">\r\n      <tr>\r\n        <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td width=\"10\"><img src=\"images/top.gif\" width=\"10\" height=\"28\" /></td>\r\n            <td background=\"images/top1.jpg\">&nbsp;</td>\r\n            <td width=\"10\"><img src=\"images/top2.gif\" width=\"10\" height=\"28\" /></td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n      <tr >\r\n        <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\r\n          <tr>\r\n          <td align=\"center\">\r\n            <span class=\"text4 STYLE4\"><font >\u56fd\u57f9\u8ba1\u5212\u9879\u76ee\u7ba1\u7406\u7cfb\u7edf</font></span>          </td>\r\n          <!--      <td width=\"130\"><img src=\"images/top3.jpg\" width=\"130\" height=\"62\" /></td>\r\n            <td width=\"66\"><img src=\"images/top31.jpg\" width=\"66\" height=\"62\" /></td>\r\n            <td width=\"141\"><img src=\"images/top4.gif\" width=\"141\" height=\"62\" /></td>\r\n            <td width=\"140\"><img src=\"images/top5.gif\" width=\"140\" height=\"62\" /></td>\r\n            <td background=\"images/top9.jpg\">&nbsp;</td>\r\n            <td width=\"275\"><img src=\"images/top7.gif\" width=\"275\" height=\"62\" /></td>\r\n            <td width=\"186\" background=\"images/top10.gif\"></td>\r\n            <td width=\"10\"><img src=\"images/top8.gif\" width=\"10\" height=\"62\" /></td>\r\n            -->  \r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n      <tr>\r\n        <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td width=\"35\"><img src=\"images/top11.gif\" width=\"35\" height=\"23\" /></td>\r\n            <td width=\"158\" background=\"images/top22.jpg\" class=\"css\">\u60a8\u7684\u4f4d\u7f6e:\u7ba1\u7406\u5e73\u53f0</td>\r\n            <td background=\"images/top33.gif\">&nbsp;</td>\r\n            <td width=\"9\"><img src=\"images/top44.gif\" width=\"9\" height=\"23\" /></td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#E6EEF2\">\r\n      <tr>\r\n        <td width=\"193\" valign=\"top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td height=\"28\" background=\"images/m1-1.gif\"></td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"28\"><img src=\"images/m4.gif\" width=\"193\" height=\"28\" /></td>\r\n          </tr>\r\n          <tr>\r\n            <td valign=\"top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n              <tr>\r\n                <td valign=\"top\" background=\"images/leftline.gif\"><div align=\"center\">\r\n                  <table width=\"126\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                    <tr>\r\n                      <td height=\"6\" colspan=\"2\" background=\"images/1231.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td width=\"15\" height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"middle.jsp\" class=\"STYLE1\" target=\"main\">\u7cfb\u7edf\u4fe1\u606f</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"manager_password_change.jsp\" class=\"STYLE1\" target=\"main\">\u4fee\u6539\u5bc6\u7801</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <!-- tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\" class=\"title\"><div align=\"center\"><a href=\"manage_setdb.jsp\" class=\"STYLE1\" target=\"main\">\u6570\u636e\u5e93\u8fde\u63a5</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"platformparam_set.jsp\" class=\"STYLE1\" target=\"main\">\u5e73\u53f0\u53c2\u6570\u8bbe\u5b9a</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"entityparam_set.jsp\" class=\"STYLE1\" target=\"main\">\u6559\u52a1\u53c2\u6570\u8bbe\u5b9a</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\" class=\"title\"><div align=\"center\"><a href=\"ssoparam_set.jsp\" class=\"STYLE1\" target=\"main\">SSO\u53c2\u6570\u8bbe\u5b9a</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"infoparam_set.jsp\" class=\"STYLE1\" target=\"main\">\u65b0\u95fb\u53c2\u6570\u8bbe\u5b9a</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"coursewareparam_set.jsp\" class=\"STYLE1\" target=\"main\">\u8bfe\u4ef6\u53c2\u6570\u8bbe\u5b9a</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr-->\r\n                     <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"right_main.jsp\" class=\"STYLE1\" target=\"main\">\u6743\u9650\u7ba1\u7406</a></div></td>\r\n                    </tr>\r\n                      <!-- \r\n                     <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    \r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"manager_manage.jsp\" class=\"STYLE1\" target=\"main\">\u7ba1\u7406\u5458\u7ba1\u7406</a></div></td>\r\n                    </tr>\r\n                     -->\r\n                     <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"/sso/admin/log4j.action\" class=\"STYLE1\" target=\"main\">\u64cd\u4f5c\u65e5\u5fd7</a></div></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" colspan=\"2\" background=\"images/3.gif\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td height=\"15\" background=\"images/1.JPG\"></td>\r\n                      <td height=\"15\" background=\"images/2.JPG\"><div align=\"center\" class=\"title\"><a href=\"logout.jsp\" class=\"STYLE1\">\u6ce8\u9500\u9000\u51fa</a></div></td>\r\n                    </tr>\r\n                     \r\n                  </table>\r\n                </div>                  </td>\r\n              </tr>\r\n            </table></td>\r\n          </tr>\r\n          \r\n          <tr>\r\n            <td background=\"images/leftline.gif\"><p>&nbsp;</p>\r\n              <p>&nbsp;</p>\r\n              <p><br />\r\n              </p></td>\r\n          </tr>\r\n          \r\n          <tr>\r\n            <td><img src=\"images/m1.gif\" width=\"193\" height=\"23\" /></td>\r\n          </tr>\r\n        </table></td>\r\n        <td align=\"left\" valign=\"top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td width=\"29\">&nbsp;</td>\r\n            <td valign=\"top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"27\" valign=\"top\"><table width=\"100%\" height=\"27\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                      <tr>\r\n                        <td width=\"4\"><img src=\"images/22.gif\" width=\"4\" height=\"27\" /></td>\r\n                        <td background=\"images/23.gif\">&nbsp;</td>\r\n                        <td width=\"4\"><img src=\"images/21.gif\" width=\"4\" height=\"27\" /></td>\r\n                      </tr>\r\n                  </table></td>\r\n                </tr>\r\n                <tr valign=\"top\">\r\n                  <td height=\"100%\"><iframe name=\"main\" width=\"100%\" height=\"100%\" scrolling=\"auto\" frameborder=\"0\" src=\"middle.jsp\" allowtransparency=\"true\"></iframe></td>\r\n                </tr>\r\n                <tr>\r\n                  <td>&nbsp;</td>\r\n                </tr>\r\n            </table></td>\r\n            <td width=\"33\" background=\"images/zhongjianbeijing-right.gif\">&nbsp;</td>\r\n          </tr>\r\n        </table></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"62\"><table width=\"100%\" height=\"62\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td width=\"14\"><img src=\"images/bot1.gif\" width=\"14\" height=\"62\" /></td>\r\n        <td background=\"images/bot2.gif\"><div align=\"center\" class=\"title\">\u5317\u4eac\u5e08\u8303\u5927\u5b66\u7ee7\u7eed\u6559\u80b2\u4e0e\u6559\u5e08\u57f9\u8bad\u5b66\u9662\u7248\u6743\u6240\u6709</div></td>\r\n        <td width=\"46\"><img src=\"images/bot3.gif\" width=\"46\" height=\"62\" /></td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string0 = "\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n".toCharArray();
  }
}
