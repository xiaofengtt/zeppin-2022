/*
 * JSP generated by Resin-3.1.3 (built Sun, 07 Oct 2007 03:58:02 PDT)
 */

package _jsp._entity._manager._info;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.sso.web.action.SsoConstant;

public class _add_0job__jsp extends com.caucho.jsp.JavaPage
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
    org.apache.struts2.views.jsp.IfTag _jsp_IfTag_3 = null;
    org.apache.struts2.views.jsp.DateTag _jsp_DateTag_4 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      out.print((request.getContextPath()));
      out.write(_jsp_string1, 0, _jsp_string1.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("scopeString");
      int _jspEval2 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string2, 0, _jsp_string2.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("peJob.id");
      int _jspEval6 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string3, 0, _jsp_string3.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("peJob.id");
      int _jspEval10 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string4, 0, _jsp_string4.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("peJob.name");
      int _jspEval14 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string5, 0, _jsp_string5.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack16 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("jobPriority");
      _jsp_IteratorTag_1.setId("priority");
      int _jspEval18 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval18 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack16 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack16);
        }
        do {
          out.write(_jsp_string6, 0, _jsp_string6.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#priority[0]");
          int _jspEval21 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string7, 0, _jsp_string7.length);
          _jsp_PropertyTag_2.setValue("#priority[0]");
          int _jspEval25 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string8, 0, _jsp_string8.length);
          com.caucho.jsp.BodyContentImpl _jsp_endTagHack27 = null;
          if (_jsp_IfTag_3 == null) {
            _jsp_IfTag_3 = new org.apache.struts2.views.jsp.IfTag();
            _jsp_IfTag_3.setPageContext(pageContext);
            _jsp_IfTag_3.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
            _jsp_IfTag_3.setTest("peJob.enumConstByFkJobPriority.id==#priority[0]");
          }

          int _jspEval29 = _jsp_IfTag_3.doStartTag();
          if (_jspEval29 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspEval29 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
              out = pageContext.pushBody();
              _jsp_endTagHack27 = (com.caucho.jsp.BodyContentImpl) out;
              _jsp_IfTag_3.setBodyContent(_jsp_endTagHack27);
            }
            out.write(_jsp_string9, 0, _jsp_string9.length);
            if (_jspEval29 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
              out = pageContext.popBody();
          }
          _jsp_IfTag_3.doEndTag();
          if (_jsp_endTagHack27 != null) {
            pageContext.releaseBody(_jsp_endTagHack27);
            _jsp_endTagHack27 = null;
          }
          out.write(_jsp_string10, 0, _jsp_string10.length);
          _jsp_PropertyTag_2.setValue("#priority[1]");
          int _jspEval33 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string11, 0, _jsp_string11.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval18 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack16 != null) {
        pageContext.releaseBody(_jsp_endTagHack16);
        _jsp_endTagHack16 = null;
      }
      out.write(_jsp_string12, 0, _jsp_string12.length);
      if (_jsp_DateTag_4 == null) {
        _jsp_DateTag_4 = new org.apache.struts2.views.jsp.DateTag();
        _jsp_DateTag_4.setPageContext(pageContext);
        _jsp_DateTag_4.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_DateTag_4.setFormat("yyyy-MM-dd");
      }

      _jsp_DateTag_4.setName("peJob.startDate");
      int _jspEval38 = _jsp_DateTag_4.doStartTag();
      _jsp_DateTag_4.doEndTag();
      out.write(_jsp_string13, 0, _jsp_string13.length);
      if (_jsp_DateTag_4 == null) {
        _jsp_DateTag_4 = new org.apache.struts2.views.jsp.DateTag();
        _jsp_DateTag_4.setPageContext(pageContext);
        _jsp_DateTag_4.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_DateTag_4.setFormat("yyyy-MM-dd");
      }

      _jsp_DateTag_4.setName("peJob.finishDate");
      int _jspEval42 = _jsp_DateTag_4.doStartTag();
      _jsp_DateTag_4.doEndTag();
      out.write(_jsp_string14, 0, _jsp_string14.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack44 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("cbScop");
      _jsp_IteratorTag_1.setId("cb_");
      int _jspEval46 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval46 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack44 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack44);
        }
        do {
          out.write(_jsp_string15, 0, _jsp_string15.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#cb_[0]");
          int _jspEval49 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string16, 0, _jsp_string16.length);
          _jsp_PropertyTag_2.setValue("#cb_[1]");
          int _jspEval53 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string17, 0, _jsp_string17.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval46 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack44 != null) {
        pageContext.releaseBody(_jsp_endTagHack44);
        _jsp_endTagHack44 = null;
      }
      out.write(_jsp_string18, 0, _jsp_string18.length);
      com.caucho.jsp.BodyContentImpl _jsp_endTagHack56 = null;
      if (_jsp_IteratorTag_1 == null) {
        _jsp_IteratorTag_1 = new org.apache.struts2.views.jsp.IteratorTag();
        _jsp_IteratorTag_1.setPageContext(pageContext);
        _jsp_IteratorTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_IteratorTag_1.setValue("zxbScop");
      _jsp_IteratorTag_1.setId("zxb_");
      int _jspEval58 = _jsp_IteratorTag_1.doStartTag();
      if (_jspEval58 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
          out = pageContext.pushBody();
          _jsp_endTagHack56 = (com.caucho.jsp.BodyContentImpl) out;
          _jsp_IteratorTag_1.setBodyContent(_jsp_endTagHack56);
        }
        do {
          out.write(_jsp_string19, 0, _jsp_string19.length);
          if (_jsp_PropertyTag_2 == null) {
            _jsp_PropertyTag_2 = new org.apache.struts2.views.jsp.PropertyTag();
            _jsp_PropertyTag_2.setPageContext(pageContext);
            _jsp_PropertyTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IteratorTag_1);
          }

          _jsp_PropertyTag_2.setValue("#zxb_[0]");
          int _jspEval61 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string16, 0, _jsp_string16.length);
          _jsp_PropertyTag_2.setValue("#zxb_[1]");
          int _jspEval65 = _jsp_PropertyTag_2.doStartTag();
          _jsp_PropertyTag_2.doEndTag();
          out.write(_jsp_string17, 0, _jsp_string17.length);
        } while (_jsp_IteratorTag_1.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
        if (_jspEval58 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED)
          out = pageContext.popBody();
      }
      _jsp_IteratorTag_1.doEndTag();
      if (_jsp_endTagHack56 != null) {
        pageContext.releaseBody(_jsp_endTagHack56);
        _jsp_endTagHack56 = null;
      }
      out.write(_jsp_string20, 0, _jsp_string20.length);
      if (_jsp_PropertyTag_0 == null) {
        _jsp_PropertyTag_0 = new org.apache.struts2.views.jsp.PropertyTag();
        _jsp_PropertyTag_0.setPageContext(pageContext);
        _jsp_PropertyTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_PropertyTag_0.setValue("peJob.note");
      int _jspEval70 = _jsp_PropertyTag_0.doStartTag();
      _jsp_PropertyTag_0.doEndTag();
      out.write(_jsp_string21, 0, _jsp_string21.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_PropertyTag_0 != null)
        _jsp_PropertyTag_0.release();
      if (_jsp_IteratorTag_1 != null)
        _jsp_IteratorTag_1.release();
      if (_jsp_PropertyTag_2 != null)
        _jsp_PropertyTag_2.release();
      if (_jsp_IfTag_3 != null)
        _jsp_IfTag_3.release();
      if (_jsp_DateTag_4 != null)
        _jsp_DateTag_4.release();
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("entity/manager/info/add_job.jsp"), 3389264965052351629L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/struts-tags.tld"), 2632518462704396565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.PropertyTag.class, 3516723674932103087L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IfTag.class, 2691840914333557849L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.IteratorTag.class, -2243808422739547962L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.struts2.views.jsp.DateTag.class, 4924450609192765402L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private final static char []_jsp_string6;
  private final static char []_jsp_string5;
  private final static char []_jsp_string14;
  private final static char []_jsp_string3;
  private final static char []_jsp_string12;
  private final static char []_jsp_string17;
  private final static char []_jsp_string8;
  private final static char []_jsp_string1;
  private final static char []_jsp_string21;
  private final static char []_jsp_string16;
  private final static char []_jsp_string13;
  private final static char []_jsp_string19;
  private final static char []_jsp_string10;
  private final static char []_jsp_string4;
  private final static char []_jsp_string15;
  private final static char []_jsp_string9;
  private final static char []_jsp_string0;
  private final static char []_jsp_string20;
  private final static char []_jsp_string18;
  private final static char []_jsp_string11;
  private final static char []_jsp_string2;
  private final static char []_jsp_string7;
  static {
    _jsp_string6 = "\r\n           <input type=\"radio\" id=\"".toCharArray();
    _jsp_string5 = "\" type=\"text\" class=\"selfScale\"  size=\"50\" maxlength='20'>            </tr>\r\n                        \r\n            <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"bottom\"><span class=\"name\">\u4efb\u52a1\u91cd\u8981\u6027:*</span></td>\r\n              <td colspan=\"2\"> ".toCharArray();
    _jsp_string14 = "\" id=\"finishDate\" class=selfScale readonly>\r\n										<img src=\"/js/calendar/img.gif\" width=\"20\" height=\"14\"\r\n											id=\"f_trigger_c\"\r\n											style=\"border: 1px solid #551896; cursor: pointer;\"\r\n											title=\"Date selector\"\r\n											onmouseover=\"this.style.background='white';\"\r\n											onmouseout=\"this.style.background=''\"> \r\n&nbsp;&nbsp;&nbsp;									</td>\r\n								</tr>\r\n            \r\n			<tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"top\"><span class=\"name\">\u4eba\u5458\u8303\u56f4:*&nbsp;&nbsp;&nbsp;&nbsp;</span> </td>\r\n              <td width=\"20%\" valign=\"top\" align=\"left\">\r\n			  \u793a\u8303\u6027\u627f\u529e\u5355\u4f4d\r\n			    <input type=\"checkbox\" value=\"cb\" id=\"CBS\" name=\"person\" onClick=\"clickCB()\">\r\n		       <input id=\"CB_button\" type=\"button\" name=\"cb\" onClick=\"showDiv('cnt1')\" value=\"\u8bf7\u9009\u62e9\" style=\"display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;\">&nbsp;\r\n              <div id=\"cnt1\" style=\"width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none\">\r\n              \r\n                <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"22\"><input id=\"cb_id_all\" type=\"checkbox\" name=\"cb_all\" value=\"\" onclick=listSelectCB();>\u5168\u9009</td>\r\n              </tr>\r\n              	".toCharArray();
    _jsp_string3 = "\";\r\n		if(bean_id==null||bean_id.length<=0){\r\n			document.getElementById('zlb_title_start').innerHTML='\u5206\u914d\u4efb\u52a1';\r\n			//window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';\r\n		}else{\r\n			document.getElementById('zlb_title_start').innerHTML='\u4fee\u6539\u4efb\u52a1';\r\n			//window.document.info_news_add.action='/entity/information/peBulletin_editexe.action';\r\n			window.document.getElementById('back').innerHTML=\"<a href='#' title='\u8fd4\u56de' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.navigate('/entity/information/peBulletin.action') onMouseOver=\\\"className='over'\\\" onMouseOut=\\\"className='norm'\\\" onMouseDown=\\\"className='push'\\\" onMouseUp=\\\"className='over'\\\"><span class='text'>\u8fd4\u56de</span></span></a>\";\r\n		}\r\n	}\r\n</script>\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\" class=\"scllbar\" onload=\"onload_edit();\">\r\n  <form name='job' method='post' id='job' action=\"/entity/information/prJobUnit_addJob.action\" class='nomargin'>\r\n<table width=\"86%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr> \r\n    <td><div class=\"content_title\" id=\"zlb_title_start\">\u5206\u914d\u4efb\u52a1</div></td>\r\n  </tr>\r\n\r\n  <tr> \r\n    <td valign=\"top\" class=\"pageBodyBorder_zlb\"> \r\n		<input type=\"hidden\" name=\"bean.id\" value=\"".toCharArray();
    _jsp_string12 = "            </tr>\r\n            <tr valign=\"bottom\" class=\"postFormBox\">\r\n									<td valign=\"bottom\">\r\n										<span class=\"name\">\u5f00\u59cb\u65f6\u95f4:*&nbsp;&nbsp;&nbsp;&nbsp;</span>									</td>\r\n									<td width=\"626\" colspan=\"2\">\r\n										<input type=text name=\"startDate\" id=\"startDate\" value=\"".toCharArray();
    _jsp_string17 = "</td>\r\n                    </tr>\r\n                ".toCharArray();
    _jsp_string8 = "\" ".toCharArray();
    _jsp_string1 = "/FCKeditor/fckeditor.js\"></script>\r\n<link rel=\"stylesheet\" type=\"text/css\" media=\"all\"	href=\"/js/calendar/calendar-win2000.css\">\r\n<script type=\"text/javascript\" src=\"/js/calendar/calendar.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/calendar/calendar-zh_utf8.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/calendar/calendar-setup.js\"></script>\r\n<script>\r\n	function doCommit() {		\r\n		var content;\r\n		if(FCKeditor_IsCompatibleBrowser()){\r\n			var oEditor = FCKeditorAPI.GetInstance(\"jobNote\"); \r\n			content=oEditor.GetXHTML(); \r\n		}else{\r\n			alert(\"\u8868\u5355\u6b63\u5728\u4e0b\u8f7d\");\r\n			return false;\r\n		}\r\n		\r\n		if(isNull(document.getElementById(\"jobName\").value)){ \r\n		}else{\r\n			alert(\"\u8bf7\u586b\u4efb\u52a1\u540d\u79f0!\");\r\n			document.getElementById(\"jobName\").focus();\r\n\r\n			return false;\r\n		}\r\n		\r\n		if(document.getElementById(\"startDate\").value==\"\") {\r\n				alert(\"\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a!\");\r\n				return false;\r\n			}\r\n			\r\n		if(document.getElementById(\"finishDate\").value==\"\") {\r\n				alert(\"\u7ed3\u675f\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a!\");\r\n				return false;\r\n			}\r\n			\r\n		if(!checkRight(document.getElementById(\"startDate\").value,document.getElementById(\"finishDate\").value)) {\r\n				alert(\"\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4\u4e0d\u5408\u7406\uff01\");\r\n				return false;\r\n			}\r\n	\r\n		var oEditor = FCKeditorAPI.GetInstance('jobNote') ;\r\n    	var acontent=oEditor.GetXHTML();\r\n       	if(acontent.length > 2000)\r\n			{\r\n				alert(\"\u4efb\u52a1\u5907\u6ce8\u5185\u5bb9\u8fc7\u591a\uff0c\u8bf7\u91cd\u65b0\u586b\u5199!\");\r\n				return false;\r\n			}\r\n	var plen =document.getElementsByName(\"cb_id\");\r\n		var n=0;\r\n		for(k=0;k<plen.length;k++){\r\n			if(plen[k].checked){\r\n			 n++;\r\n			}\r\n		}\r\n	var plen2 =document.getElementsByName(\"zxb_id\");\r\n		var m=0;\r\n		for(p=0;p<plen2.length;p++){\r\n			if(plen2[p].checked){\r\n			 m++;\r\n			}\r\n		}\r\n		if(n==0&&m==0){\r\n			alert(\"\u8bf7\u9009\u62e9\u4efb\u52a1\u4eba\u5458\u8303\u56f4\uff01\");\r\n			return false;\r\n		}\r\n		var s=document.getElementById('job');\r\n		s.submit();\r\n	}\r\n	function checkRight(startDateStr, finishDateStr) {\r\n			var startArr = startDateStr.split(\"-\");\r\n			var finishArr = finishDateStr.split(\"-\");\r\n			var startDate = new Date(startArr[0],startArr[1],startArr[2]);\r\n			var finishDate = new Date(finishArr[0],finishArr[1],finishArr[2]);\r\n			if(startDate > finishDate)\r\n				return false;\r\n			return true;\r\n		}\r\n	function listSelectCB() {\r\n		var form = document.forms['job'];\r\n		for (var i = 0 ; i < form.elements.length; i++) {\r\n			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {\r\n				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {\r\n					form.elements[i].checked = form.cb_all.checked;\r\n				}\r\n			}\r\n		}\r\n		return true;\r\n	}\r\n	function clickCB()\r\n	{\r\n		var cbObj;\r\n		if(document.getElementById(\"CBS\"))\r\n		{\r\n			cbObj = document.getElementById(\"CBS\");\r\n		}\r\n		if(cbObj.checked){\r\n			document.getElementById(\"cb_button\").style.display=\"block\";\r\n		}else{\r\n			document.getElementById(\"cb_button\").style.display=\"none\";\r\n		}\r\n	}\r\n	function clickZXB()\r\n	{\r\n		var zxbObj;\r\n		if(document.getElementById(\"ZXB\"))\r\n		{\r\n			zxbObj = document.getElementById(\"ZXB\");\r\n		}\r\n		if(zxbObj.checked){\r\n			document.getElementById(\"zxb_button\").style.display=\"block\";\r\n		}else{\r\n			document.getElementById(\"zxb_button\").style.display=\"none\";\r\n		}\r\n	}\r\n	function listSelectZXB() {\r\n		var form = document.forms['job'];\r\n		for (var i = 0 ; i < form.elements.length; i++) {\r\n			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxb_id')) {\r\n				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {\r\n					form.elements[i].checked = form.zxb_all.checked;\r\n				}\r\n			}\r\n		}\r\n		return true;\r\n	}\r\n	function showDiv(objID){\r\n		var tempObj;\r\n		if(document.getElementById(objID)) {\r\n			tempObj = document.getElementById(objID);\r\n		}\r\n		if(tempObj.style.display == 'none') {\r\n			tempObj.style.display = 'block';\r\n		}\r\n		else {\r\n			tempObj.style.display = 'none';\r\n		}\r\n		\r\n	}\r\n	function onload_edit(){\r\n		\r\n		var str = \"".toCharArray();
    _jsp_string21 = "</textarea>			  </td>\r\n            </tr>\r\n  <tr valign=\"bottom\" class=\"postFormBox\"> \r\n	<td valign=\"bottom\">\r\n		<span class=\"name\">\u662f\u5426\u7ed9\u8054\u7cfb\u4eba\u53d1\u90ae\u4ef6\uff1a</span>	</td>\r\n	<td colspan=\"2\">\r\n		<select name=\"send\" id=\"send\" class=\"input6303\">\r\n			<option value=\"0\" selected>\r\n				\u5426			</option>\r\n			<option value=\"1\">\r\n				\u662f			</option>\r\n		</select> \r\n&nbsp;&nbsp;&nbsp;	</td>\r\n</tr>\r\n          </table> \r\n      </div>\r\n   </td>\r\n  </tr>\r\n  <tr> \r\n    <td align=\"center\" id=\"pageBottomBorder_zlb\"><div class=\"content_bottom\"> <table width=\"98%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>\r\n       <td  align=\"center\" valign=\"middle\">\r\n       	<a href=\"#\" title=\"\u63d0\u4ea4\" class=\"button\">\r\n       		<span unselectable=\"on\" class=\"norm\"  style=\"width:46px;height:15px;padding-top:3px\" onClick=\"doCommit()\" onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\">\r\n       			<span class=\"text\">\u63d0\u4ea4</span>       			\r\n       		</span>\r\n       	</a>\r\n       	</td>\r\n       <td  align=\"center\" id=\"back\" valign=\"middle\">\r\n       	</td>            \r\n       	</tr>\r\n      </table></td>\r\n  </tr>\r\n</table>\r\n</form>\r\n<script type=\"text/javascript\">\r\n    Calendar.setup({\r\n        inputField     :    \"startDate\",     // id of the input field\r\n        ifFormat       :    \"%Y-%m-%d\",       // format of the input field\r\n        button         :    \"f_trigger_b\",  // trigger for the calendar (button ID)\r\n        align          :    \"Tl\",           // alignment (defaults to \"Bl\")\r\n        singleClick    :    true\r\n    });\r\n    \r\n    Calendar.setup({\r\n        inputField     :    \"finishDate\",     // id of the input field\r\n        ifFormat       :    \"%Y-%m-%d\",       // format of the input field\r\n        button         :    \"f_trigger_c\",  // trigger for the calendar (button ID)\r\n        align          :    \"Tl\",           // alignment (defaults to \"Bl\")\r\n        singleClick    :    true\r\n    });\r\n</script>\r\n\r\n<script type=\"text/javascript\"> \r\nvar oFCKeditor = new FCKeditor( 'jobNote' ) ; \r\noFCKeditor.Height = 300 ; \r\noFCKeditor.Width  = 700 ; \r\n\r\noFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';\r\noFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';\r\n\r\noFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';\r\noFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';\r\n\r\noFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';\r\noFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';\r\n\r\n\r\noFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; \r\noFCKeditor.ReplaceTextarea() ; \r\n//--> \r\n</script> \r\n<script>bLoaded=true;</script>\r\n\r\n</body>\r\n\r\n</html>\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string16 = "\">".toCharArray();
    _jsp_string13 = "\" class=selfScale readonly>\r\n										<img src=\"/js/calendar/img.gif\" width=\"20\" height=\"14\"\r\n											id=\"f_trigger_b\"\r\n											style=\"border: 1px solid #551896; cursor: pointer;\"\r\n											title=\"Date selector\"\r\n											onmouseover=\"this.style.background='white';\"\r\n											onmouseout=\"this.style.background=''\"> \r\n&nbsp;&nbsp;&nbsp;									</td>\r\n								</tr>\r\n								<tr valign=\"bottom\" class=\"postFormBox\">\r\n									<td valign=\"bottom\">\r\n										<span class=\"name\">\u7ed3\u675f\u65f6\u95f4:*&nbsp;&nbsp;&nbsp;&nbsp;</span>								</td>\r\n									<td width=\"626\" colspan=\"2\">\r\n										<input type=\"text\" name=\"finishDate\"  value=\"".toCharArray();
    _jsp_string19 = "\r\n                	<tr>\r\n                    <td height=\"22\"><input id=\"zxb_id_check\" type=\"checkbox\" name=\"zxb_id\" value=\"".toCharArray();
    _jsp_string10 = "   >  ".toCharArray();
    _jsp_string4 = "\"/>\r\n        <div class=\"cntent_k\" id=\"zlb_content_start\">\r\n          <table width=\"85%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"bottom\" width=\"130\" nowrap><span class=\"name\">\u4efb\u52a1\u540d\u79f0:*&nbsp;&nbsp;&nbsp;&nbsp;</span></td>\r\n            <td width=\"626\" colspan=\"2\"> <input name=\"jobName\"   value=\"".toCharArray();
    _jsp_string15 = "\r\n                	<tr>\r\n                    <td height=\"22\"><input id=\"cb_id_check\" type=\"checkbox\" name=\"cb_id\" value=\"".toCharArray();
    _jsp_string9 = "checked".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>\u5206\u914d\u4efb\u52a1</title>\r\n<link href=\"/entity/manager/css/admincss.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"entity/manager/js/calendar/calendar-win2k-cold-1.css\" title=\"win2k-cold-1\">\r\n<script language=\"javascript\" src=\"/entity/manager/js/check.js\"></script>		\r\n<script src=\"".toCharArray();
    _jsp_string20 = "\r\n                <tr><td><input type='button' onClick=\"showDiv('zxbDiv')\" value=\"\u786e\u5b9a\" style=\"font-size:12px;height:23;background-color:#ece9d8;border-width:1;\"></td></tr>\r\n                </table>\r\n              </div></td>\r\n			</tr>\r\n		   \r\n             <tr valign=\"bottom\" class=\"postFormBox\"> \r\n              <td valign=\"top\"><span class=\"name\">\u4efb\u52a1\u5907\u6ce8:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>\r\n              <td colspan=\"2\"> \r\n              <textarea class=\"smallarea\"  name=\"jobNote\" cols=\"70\" rows=\"12\" id=\"bean.note\">".toCharArray();
    _jsp_string18 = "\r\n                <tr><td><input type='button' onClick=\"showDiv('cnt1')\" value=\"\u786e\u5b9a\" style=\"font-size:12px;height:23;background-color:#ece9d8;border-width:1;\"></td></tr>\r\n                </table>\r\n              </div></td>\r\n              <td width=\"80%\" valign=\"top\" align=\"left\"> &nbsp; &nbsp;\u4e2d\u897f\u90e8\u627f\u529e\u5355\u4f4d\r\n              <input type=\"checkbox\" value=\"zxb\" id=\"ZXB\" name=\"person\" onClick=\"clickZXB()\">\r\n			   <input id=\"zxb_button\" type=\"button\" name=\"zxb\" onClick=\"showDiv('zxbDiv')\" value=\"\u8bf7\u9009\u62e9\" style=\"display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;\">&nbsp;\r\n              <div id=\"zxbDiv\" style=\"width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none\">\r\n              \r\n                <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                  <td height=\"22\"><input id=\"zxb_id_all\" type=\"checkbox\" name=\"zxb_all\" value=\"\" onclick=listSelectZXB();>\u5168\u9009</td>\r\n              </tr>\r\n              	".toCharArray();
    _jsp_string11 = ":\r\n            ".toCharArray();
    _jsp_string2 = "\";\r\n		var person =document.getElementsByName(\"person\");\r\n		for(var n=0;n<person.length;n++ ){\r\n			if(str.indexOf(person[n].value)>=0)\r\n			person[n].checked = true;\r\n		}		\r\n		clickCB();\r\n		clickZXB();\r\n		\r\n		var cbs = window.document.getElementsByName(\"cb_id\");\r\n		for(var i=0;i<cbs.length;i++){\r\n			if(str.indexOf(cbs[i].value)>=0){\r\n				cbs[i].checked = true;\r\n				}\r\n		}\r\n		var zxb = window.document.getElementsByName(\"zxb_id\");\r\n		for(var i=0;i<zxb.length;i++){\r\n			if(str.indexOf(zxb[i].value)>=0){\r\n				zxb[i].checked = true;\r\n				}\r\n		}\r\n		\r\n		var bean_id = \"".toCharArray();
    _jsp_string7 = "\" name=\"enum1\" value=\"".toCharArray();
  }
}
