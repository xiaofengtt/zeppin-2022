<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
     <link href="/web/css/css.css" rel="stylesheet" type="text/css" />   

  </head>
  
 
<body >
		<iframe id="left" name="left" frameborder="0" scrolling="no" src="/web/left-top.jsp" width="239" height="201"></iframe>
		  <table width="239" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td width="119" height="44"><img src="/web/images/index_52.jpg" width="187" height="44" /></td>
	
    <td width="120" background="/web/images/index_53.jpg"  class="black"><a href="/entity/first/firstInfoNews_toNewsList.action?type=_wydt" target="_top">>>更多</a></td>
  </tr>
  <tr>
    <td colspan="2" align="center" bgcolor="#F3F4FB">
	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="margin-top:3px; margin-bottom:3px;">
      <s:iterator value="wydt">
       <tr>
      	<td class="newstyle"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" />   </a>
      	<s:if test="note == 'new'"> <span class="orange">New! </span></s:if>   </td>
      	</tr>
      </s:iterator>
    </table></td>
  </tr>
</table>
<!------------ ----------------->
<table width="239" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td width="119" height="44"><img src="/web/images/index_52a.jpg" width="187" height="44" /></td>
	
    <td width="120" background="/web/images/index_53.jpg"  class="black"><a href="/entity/first/firstInfoNews_toNewsList.action?type=_wjdt" target="_top">>>更多</a></td>
  </tr>
  <tr>
    <td colspan="2" align="center" bgcolor="#F3F4FB">
	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="margin-top:3px; margin-bottom:3px;">
      <s:iterator value="wjdt">
       <tr>
      	<td class="newstyle"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" />   </a>
      	<s:if test="note == 'new'"> <span class="orange">New! </span></s:if></td>
      	</tr>
      </s:iterator>
    </table></td>
  </tr>
</table>
<!------------   --------------------->
<table width="239" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td width="119" height="44"><img src="/web/images/index_52b.jpg" width="187" height="44" /></td>
	
    <td width="120" background="/web/images/index_53.jpg"  class="black"><a href="/entity/first/firstInfoNews_toNewsList.action?type=_zcfg" target="_top">>>更多</a></td>
  </tr>
  <tr>
    <td colspan="2" align="center" bgcolor="#F3F4FB">
	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="margin-top:3px; margin-bottom:3px;">
      <s:iterator value="zcfg">
       <tr>
      	<td class="newstyle"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" />   </a>
      	<s:if test="note == 'new'"> <span class="orange">New! </span></s:if></td>
      	</tr>
      </s:iterator>
    </table></td>
  </tr>
</table>
<!----------- ------------------->
<table width="239" height="100%" border="0" cellspacing="0" cellpadding="0"  style="border-top:1px solid #D4D4D4; ">
  <tr>
    <td height="421" align="center" valign="top" bgcolor="#F3F4FB">
	<table width="225" border="0" cellspacing="0" cellpadding="0" style="padding-top:5px; margin-bottom:5px;">
      <tr>
        <td><img src="/web/images/index_56.jpg" width="225" height="41" /></td>
	  </tr>
	  <tr>
        <td valign="top"  style="background:url(/web/images/index_58.jpg); background-repeat:repeat-y;" align="center">
		<table width="95%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td >&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1" onchange="javascript:window.open(this.options[this.selectedIndex].value)" size="1" >
              <option selected="selected" style="text-align:center"  value="#" >-=校外学习中心=-</option>
            <option value="http://xueyuan.chinaedu.net">北京学习中心</option>
            <option value="http://www.hsdpy.com/">广东省培英学习中心</option>
            <option value="http://www.gdfscl.com/gdou/">广东法商学习中心</option>
            <option value="http://www.uedu.com.cn/">东莞联合学习中心</option>
			<option value="http://www.jmty.com.cn/">江门天缘培训学校</option>
            <option value="http://www.gmjj.com/">三水光明自考学习辅导中心</option>
            <option value="http://www.zhjoc.com/">顺德学习中心</option>
            <option value="http://www.sgou.com.cn">韶关学习中心</option>
            <option value="http://www.360cn.com.cn/">中山公用教育培训中心</option>
            <option value="http://www.jyedu.net/">揭阳博通学习中心</option>
            <option value="http://www.gdfsdd.com/">丰顺县远程学习培训中心</option>
            <option value="http://www.cadsdx.com/">潮安教师继续教育培训中心</option>
            <option value="http://mjdd.meizhou.net">梅州市梅江区网络教育中心</option>
            <option value="http://home.cd200.com/fkjx/">封开教师进修学校</option>
            <option value="http://www.6uc.com/netschool/index.htm">南海成教</option>
            <option value="http://www.rcxy.com/zhaosheng/hsd.htm">浙江人才专修学校</option>
			<option value="http://www.gdhydd.net/">惠阳电大</option>
			<option value="http://www.hswyxazx.com/">西安学习中心</option>
            </select>            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
             <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >-=国内其它网院=-</option>
              <option value="http://www.ne.sysu.org.cn/">中山大学网络教育学院 </option>
              <option value="http://www.scutde.net/">华南理工大学网络学院 </option>
              <option value="http://www.sne.bnu.edu.cn/">北京师范大学网络学院 </option>
              <option value="http://sne.ccnu.edu.cn/">华中师范大学网络学院 </option>
              <option value="http://www.dec.ecnu.edu.cn/">华东师范大学网络学院 </option>
              <option value="http://www.fjtu.com.cn/">福建师范大学网络学院 </option>
              <option value=" http://www.dlc.swnu.edu.cn/">西南师范大学网络学院 </option>
              <option value="http://www.sne.snnu.edu.cn/">陕西师范网络教育学院</option>
              <option value="http://www.itsinghua.com/">清华大学网络教育学院</option>
              <option value="http://www.smde.pku.edu.cn/">北京大学网络教育学院</option>
              <option value="http://www.buptnu.com.cn/">北京邮电大学网教学院</option>
              <option value="http://www.scezju.com/">浙江大学网络教育学院</option>
              <option value="http://www.nip.net.cn/">湖南大学网络教育学院</option>
              <option value="http://www.crtvu.edu.cn/">中央广播电视大学</option>
              <option value="http://neu-nec.sy.ln.cn/">东北大学网络教育学院 </option>
              <option value="http://www.nec.sjtu.edu.cn/">上海交大网络教育学院</option>
              <option value="http://www.hust-snde.com/">华中科技大学远程与继续教育学院</option>
              <option value="http://dis.njtu.edu.cn/">北京交大远程与继续教育学院 </option>
              <option value="http://www.beiwaionline.com/">北京外国语大学网教院 </option>
              <option value="http://www.etju.com/">天津大学网络教育学院 </option>
              <option value="http://dec.fudan.edu.cn/">复旦大学网络教育学院 </option>
              <option value="http://www.tjae.cn/">同济大学网络教育学院 </option>
              <option value="http://netu.js.edu.cn/ ">东南大学网络教育学院 </option>
              <option value="http://www.cmjnu.com.cn/">网上江大 </option>
              <option value="http://202.98.17.55/index.htm">吉林大学网络教育学院 </option>
              <option value="http://www.hagongda.com/">哈工大网络教育学院</option>
              <option value="http://www.cau-edu.net.cn/">中国农业大学远程教育网</option>
              <option value="http://202.113.31.244/">南开大学现代远程教育学院</option>
              <option value="http://www.wljy.sdu.edu.cn/">山东大学网络教育学院 </option>
              <option value="http://www.scude.cc/">四川大学网络教育学院 </option>
              <option value="http://www.5any.com/">重庆大学网络教育学院 </option>
              <option value="http://www.dlc.xjtu.edu.cn/">西安交通大学网络学院 </option>
              <option value="http://learn.bit.edu.cn/">北京理工大学现代远程教育学院</option>
              <option value="http://nec.neau.edu.cn/ ">东北农业大学网络学院 </option>
              <option value="http://www.ibucm.com/">北京中医药大学网教院 </option>
              <option value="http://nec.blcu.edu.cn">北京语言文化大学网院</option>
              <option value="http://mdedu.bbi.edu.cn/ ">中国传媒大学现代远程教育 </option>
              <option value="http://dec.lzu.edu.cn/">兰州大学网络教育学院 </option>
              <option value="http://210.34.0.36/">厦门大学网络教育学院 </option>
              <option value="http://www.upol.cn/">华南师范大学现代远程教育</option>
              <option value="http://e.swjtu.edu.cn/">西南交通大学网络学院</option>
              <option value="http://cne.csu.edu.cn/">中南大学现代远程教育平台</option>
              <option value="http://nec.dhu.edu.cn/">东华大学网络教育学院 </option>
              <option value="http://cugnc.cug.edu.cn/">中国地质大学网络学院 </option>
              <option value="http://wutde.whut.edu.cn/">武汉理工大学网络学院 </option>
              <option value="http://cec.ustb.edu.cn/">北京科技大学远程教育学院</option>
              <option value="http://www.euibe.com/">对外经济贸易大学远程教育学院</option>
              <option value="http://www.beihangonline.com/">北航现代远程教育学院</option>
              <option value="http://open.dufe.edu.cn/">东财在线</option>
              <option value="http://de.nju.edu.cn/">南京大学网络教育学院</option>
              <option value="http://dls.zzu.edu.cn/">郑州大学远程教育学院</option>
              <option value="http://www.nwpunec.net/">西北工业大学网络教育在线</option>
            </select>            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >学生网站链接</option>
              <option value="http://www.luojia.net">珞珈网</option>
              <option value="http://sun.njnu.edu.cn">阳光网</option>
              <option value="http://ucity.cc">华师大学城</option>
              <option value="http://www.hustonline.net">华中大在线</option>
              <option value="http://jd.sjtu.edu.cn">交大焦点</option>
            </select>            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >热门网站精选</option>
              <option value="http://www.sina.com.cn">新浪网</option>
              <option value="http://www.sohu.com">搜狐</option>
              <option value="http://www.163.com">网易</option>
              <option value="http://cn.yahoo.com">雅虎</option>
              <option value="http://www.yesky.com">天极网</option>
              <option value="http://www.enet.com.cn">硅谷动力</option>
              <option value="http://www.newhua.com">华军软件园</option>
              <option value="http://www.peopledaily.edu.cn">人民日报</option>
              <option value="http://www.gmd.com.cn">光明日报</option>
              <option value="http://www.xinhua.org">新华网</option>
              <option value="http://www.eastday.com">东方网</option>
              <option value="http://www.21dnn.com">千龙新闻网</option>
              <option value="http://www.chinanews.com.cn">中国新闻网</option>
              <option value="http://www.cyol.net">中青在线</option>
              <option value="http://www.21.com">21cn</option>
              <option value="http://www.gznet.com">广州视窗</option>
              <option value="http://www.yinsha.com">碧海银沙</option>
			  <option value="http://www.job168.com">南方人才市场</option>
			  <option value="http://www.goodjob.cn">BOLE伯乐</option>
			  <option value="http://www.51job.com">前程无忧</option>
            </select>            </td>
          </tr>
		  <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >世界著名高校</option>
              <option value="http://www.ox.ac.uk">牛津大学</option>
              <option value="http://www.cam.ac.uk">剑桥大学</option>
              <option value="http://www.mit.edu">麻省理工学院</option>
              <option value="http://www.harvard.edu">哈佛大学</option>
              <option value="http://www.nyu.edu">纽约大学</option>
              <option value="http://www.utexas.edu">德克萨斯大学</option>
              <option value="http://www.yale.edu">耶鲁大学</option>
              <option value="http://www.washington.edu">华盛顿大学</option>
              <option value="http://www.stanford.edu">斯坦福大学</option>
              <option value="http://www.utoronto.ca">多伦多大学</option>
              <option value="http://web.yl.is.s.u-tokyo.ac.jp/ut">东京大学</option>
            </select>            </td>
          </tr>
		  <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >中国著名高校</option>
              <option value="http://www.tsinghua.edu.cn">清华大学</option>
              <option value="http://www.pku.edu.cn">北京大学</option>
              <option value="http://www.nju.edu.cn">南京大学</option>
              <option value="http://www.fudan.edu.cn">复旦大学</option>
              <option value="http://www.ustc.edu.cn">中国科大</option>
              <option value="http://www.zju.edu.cn">浙江大学 </option>
              <option value="http://www.nankai.edu.cn">南开大学</option>
              <option value="http://www.sjtu.edu.cn">上海交大</option>
              <option value="http://www.tju.edu.cn">天津大学</option>
              <option value="http://www.hit.edu.cn">哈尔滨工大</option>
              <option value="http://www.whu.edu.cn">武汉大学</option>
              <option value="http://www.tongji.edu.cn">同济大学</option>
              <option value="http://www.xjtu.edu.cn">西安交大</option>
              <option value="http://www.seu.edu.cn">东南大学</option>
            </select>            </td>
          </tr>
		  <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><select name="select2" class="texts1"  onchange="javascript:window.open(this.options[this.selectedIndex].value)">
              <option selected="selected" style="text-align:center"  value="#" >高等师范院校</option>
              <option value="http://www.bnu.edu.cn">北京师范大学</option>
              <option value="http://www.ecnu.edu.cn">华东师范大学</option>
              <option value="http://www.nenu.edu.cn">东北师范大学</option>
              <option value="http://www.hunnu.edu.cn">湖南师范大学</option>
              <option value="http://www.scnu.edu.cn">华南师范大学</option>
            </select>            </td>
          </tr>
        </table></td>
	  </tr>
	  <tr>
        <td valign="top"><img src="/web/images/index_60.jpg" width="225" height="5" /></td>
	  </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
