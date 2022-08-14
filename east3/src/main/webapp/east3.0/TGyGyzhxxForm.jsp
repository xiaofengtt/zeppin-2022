<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
    <%@ include file="./globalLink.jsp" %>
</head>
<body>
	<input id="scode" type="hidden" value="006001" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TGyGyzhxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>固有账户信息</li>
                                
                                <li class="active"></li>
                            </ol>
                        </div>
                        <div class="col-md-12">
                        	<button type="button" class="btn btn-md btn-primary pull-right bt-15" id="formGoBackBtn"><i class="fa fa-reply-all"></i>返回</button>
                        </div>
                        <div class="clear"></div>
        				<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control" value="K0066H211000001" readonly/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control input-required" value="G10110105001163302" readonly />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托机构名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgmc" maxlength="200" class="form-control" value="北京国际信托有限公司" readonly />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 账户编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zhbh" maxlength="60" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有账户类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gyzhlc" maxlength="20" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 账户状态：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zhzt" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开户机构名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="khjgmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开户机构证件号码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="khjgzjhm" maxlength="30" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 账户对账频度：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zhdzpd" class="form-control">
											<option value="日">日</option>
											<option value="周">周</option>
											<option value="旬">旬</option>
											<option value="月度">月度</option>
											<option value="季度">季度</option>
											<option value="年度">年度</option>
											<option value="其他频度">其他频度</option>
											<option value="不定期">不定期</option>
											<option value="无">无</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开户区域：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select class="form-control col-sm-2 form-ssdq">
											<option value="1">境内</option>
											<option value="2">境外</option>
										</select>
										<select class="form-control col-sm-one-third form-jn form-province">
											<option value="">请选择</option>
										</select>
										<select class="form-control col-sm-one-third form-jn form-city">
											<option value="">请选择</option>
										</select>
										<select class="form-control col-sm-one-third form-jn form-county">
											<option value="">请选择</option>
										</select>
										<select id="ssdq" class="form-control form-ssgb col-sm-10 form-jw" data-live-search="true">
											<option value="840">美国</option>
											<option value="826">英国</option>
											<option value="124">加拿大</option>
											<option value="076">巴西</option>
											<option value="392">日本</option>
											<option value="410">韩国</option>
											<option value="702">新加坡</option>
											<option value="276">德国</option>
											<option value="380">意大利</option>
											<option value="250">法国</option>
											<option value="756">瑞士</option>
											<option value="752">瑞典</option>
											<option value="578">挪威</option>
											<option value="372">爱尔兰</option>
											<option value="040">奥地利</option>
											<option value="620">葡萄牙</option>
											<option value="724">西班牙</option>
											<option value="036">澳大利亚</option>
											<option value="554">新西兰</option>
											<option value="004">阿富汗</option>
											<option value="008">阿尔巴尼亚</option>
											<option value="012">阿尔及利亚</option>
											<option value="016">美属萨摩亚</option>
											<option value="020">安道尔</option>
											<option value="024">安哥拉</option>
											<option value="660">安圭拉</option>
											<option value="010">南极洲</option>
											<option value="028">安提瓜和巴布达</option>
											<option value="032">阿根廷</option>
											<option value="051">亚美尼亚</option>
											<option value="533">阿鲁巴</option>
											<option value="031">阿塞拜疆</option>
											<option value="044">巴哈马</option>
											<option value="048">巴林</option>
											<option value="050">孟加拉国</option>
											<option value="052">巴巴多斯</option>
											<option value="112">白俄罗斯</option>
											<option value="056">比利时</option>
											<option value="084">伯利兹</option>
											<option value="204">贝宁</option>
											<option value="060">百慕大</option>
											<option value="064">不丹</option>
											<option value="068">玻利维亚</option>
											<option value="070">波黑</option>
											<option value="072">博茨瓦纳</option>
											<option value="074">布维岛</option>
											<option value="086">英属印度洋领地</option>
											<option value="096">文莱</option>
											<option value="100">保加利亚</option>
											<option value="854">布基纳法索</option>
											<option value="108">布隆迪</option>
											<option value="116">柬埔寨</option>
											<option value="120">喀麦隆</option>
											<option value="132">佛得角</option>
											<option value="136">开曼群岛</option>
											<option value="140">中非</option>
											<option value="148">乍得</option>
											<option value="152">智利</option>
											<option value="162">圣诞岛</option>
											<option value="166">科科斯（基林）群岛</option>
											<option value="170">哥伦比亚</option>
											<option value="174">科摩罗</option>
											<option value="178">刚果（布）</option>
											<option value="180">刚果（金）</option>
											<option value="184">库克群岛</option>
											<option value="188">哥斯达黎加</option>
											<option value="384">科特迪瓦</option>
											<option value="191">克罗地亚</option>
											<option value="192">古巴</option>
											<option value="196">塞浦路斯</option>
											<option value="203">捷克</option>
											<option value="208">丹麦</option>
											<option value="262">吉布提</option>
											<option value="212">多米尼克</option>
											<option value="214">多米尼加</option>
											<option value="626">东帝汶</option>
											<option value="218">厄瓜多尔</option>
											<option value="818">埃及</option>
											<option value="222">萨尔瓦多</option>
											<option value="226">赤道几内亚</option>
											<option value="232">厄立特里亚</option>
											<option value="233">爱沙尼亚</option>
											<option value="231">埃塞俄比亚</option>
											<option value="238">福克兰群岛（马尔维纳斯）</option>
											<option value="234">法罗群岛</option>
											<option value="242">斐济</option>
											<option value="246">芬兰</option>
											<option value="254">法属圭亚那</option>
											<option value="258">法属波利尼西亚</option>
											<option value="260">法属南部领地</option>
											<option value="266">加蓬</option>
											<option value="270">冈比亚</option>
											<option value="268">格鲁吉亚</option>
											<option value="288">加纳</option>
											<option value="292">直布罗陀</option>
											<option value="300">希腊</option>
											<option value="304">格陵兰</option>
											<option value="308">格林纳达</option>
											<option value="312">瓜德罗普</option>
											<option value="316">关岛</option>
											<option value="320">危地马拉</option>
											<option value="324">几内亚</option>
											<option value="624">几内亚比绍</option>
											<option value="328">圭亚那</option>
											<option value="332">海地</option>
											<option value="334">赫德岛和麦克唐纳岛</option>
											<option value="340">洪都拉斯</option>
											<option value="348">匈牙利</option>
											<option value="352">冰岛</option>
											<option value="356">印度</option>
											<option value="360">印度尼西亚</option>
											<option value="364">伊朗</option>
											<option value="368">伊拉克</option>
											<option value="376">以色列</option>
											<option value="388">牙买加</option>
											<option value="400">约旦</option>
											<option value="398">哈萨克斯坦</option>
											<option value="404">肯尼亚</option>
											<option value="296">基里巴斯</option>
											<option value="408">朝鲜</option>
											<option value="414">科威特</option>
											<option value="417">吉尔吉斯斯坦</option>
											<option value="418">老挝</option>
											<option value="428">拉脱维亚</option>
											<option value="422">黎巴嫩</option>
											<option value="426">莱索托</option>
											<option value="430">利比里亚</option>
											<option value="434">利比亚</option>
											<option value="438">列支敦士登</option>
											<option value="440">立陶宛</option>
											<option value="442">卢森堡</option>
											<option value="807">前南巴其顿</option>
											<option value="450">马达加斯加</option>
											<option value="454">马拉维</option>
											<option value="458">马来西亚</option>
											<option value="462">马尔代夫</option>
											<option value="466">马里</option>
											<option value="470">马耳他</option>
											<option value="584">马绍尔群岛</option>
											<option value="474">马提尼克</option>
											<option value="478">毛里塔尼亚</option>
											<option value="480">毛里求斯</option>
											<option value="175">马约特</option>
											<option value="484">墨西哥</option>
											<option value="583">密克罗尼西亚联邦</option>
											<option value="498">摩尔多瓦</option>
											<option value="492">摩纳哥</option>
											<option value="496">蒙古</option>
											<option value="500">蒙特塞拉特</option>
											<option value="504">摩洛哥</option>
											<option value="508">莫桑比克</option>
											<option value="104">缅甸</option>
											<option value="516">纳米比亚</option>
											<option value="520">瑙鲁</option>
											<option value="524">尼泊尔</option>
											<option value="528">荷兰</option>
											<option value="530">荷属安的列斯</option>
											<option value="540">新喀里多尼亚</option>
											<option value="558">尼加拉瓜</option>
											<option value="562">尼日尔</option>
											<option value="566">尼日利亚</option>
											<option value="570">纽埃</option>
											<option value="574">诺福克岛</option>
											<option value="580">北马里亚纳</option>
											<option value="512">阿曼</option>
											<option value="586">巴基斯坦</option>
											<option value="585">帕劳</option>
											<option value="275">巴勒斯坦</option>
											<option value="591">巴拿马</option>
											<option value="598">巴布亚新几内亚</option>
											<option value="600">巴拉圭</option>
											<option value="604">秘鲁</option>
											<option value="608">菲律宾</option>
											<option value="612">皮特凯恩</option>
											<option value="616">波兰</option>
											<option value="630">波多黎各</option>
											<option value="634">卡塔尔</option>
											<option value="638">留尼汪</option>
											<option value="642">罗马尼亚</option>
											<option value="643">俄罗斯联邦</option>
											<option value="646">卢旺达</option>
											<option value="654">圣赫勒拿</option>
											<option value="659">圣基茨和尼维斯</option>
											<option value="662">圣卢西亚</option>
											<option value="666">圣皮埃尔和密克隆</option>
											<option value="670">圣文森特和格林纳丁斯</option>
											<option value="882">萨摩亚</option>
											<option value="674">圣马力诺</option>
											<option value="678">圣多美和普林西比</option>
											<option value="682">沙特阿拉伯</option>
											<option value="686">塞内加尔</option>
											<option value="690">塞舌尔</option>
											<option value="694">塞拉利昂</option>
											<option value="703">斯洛伐克</option>
											<option value="705">斯洛文尼亚</option>
											<option value="090">所罗门群岛</option>
											<option value="706">索马里</option>
											<option value="710">南非</option>
											<option value="239">南乔治亚岛和南桑德韦奇岛</option>
											<option value="144">斯里兰卡</option>
											<option value="736">苏丹</option>
											<option value="740">苏里南</option>
											<option value="744">斯瓦尔巴岛和扬马延岛</option>
											<option value="748">斯威士兰</option>
											<option value="760">叙利亚</option>
											<option value="762">塔吉克斯坦</option>
											<option value="834">坦桑尼亚</option>
											<option value="764">泰国</option>
											<option value="768">多哥</option>
											<option value="772">托克劳</option>
											<option value="776">汤加</option>
											<option value="780">特立尼克和多巴哥</option>
											<option value="788">突尼斯</option>
											<option value="792">土耳其</option>
											<option value="795">土库曼斯坦</option>
											<option value="796">特克斯和凯科斯群岛</option>
											<option value="798">图瓦卢</option>
											<option value="800">乌干达</option>
											<option value="804">乌克兰</option>
											<option value="784">阿联酋</option>
											<option value="581">美国本土外小岛屿</option>
											<option value="858">乌拉圭</option>
											<option value="860">乌兹别克斯坦</option>
											<option value="548">瓦努阿图</option>
											<option value="336">梵蒂冈</option>
											<option value="862">委内瑞拉</option>
											<option value="704">越南</option>
											<option value="092">英属维尔京群岛</option>
											<option value="850">美属维尔京群岛</option>
											<option value="876">瓦利斯和富图纳</option>
											<option value="732">西撒哈拉</option>
											<option value="887">也门</option>
											<option value="891">南斯拉夫</option>
											<option value="894">赞比亚</option>
											<option value="716">津巴布韦</option>
										</select>
										<input name="khqy" type="hidden" value="" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否托管：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="sftg" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 账户实物保管方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zhswglfs" class="form-control">
											<option value="本公司">本公司</option>
											<option value="非本公司">非本公司</option>
											<option value="不适用">不适用</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 账户印鉴保管方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zhyjbgfs" class="form-control">
											<option value="本公司">本公司</option>
											<option value="非本公司">非本公司</option>
											<option value="不适用">不适用</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="ye" class="form-control number form-money" title="余额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 币种：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="bz" class="form-control form-bz" data-live-search="true" id="bzSelect">
											<option value="CNY">人民币元</option>
											<option value="USD">美元</option>
											<option value="EUR">欧元</option>
											<option value="GBP">英镑</option>
											<option value="AUD">澳大利亚元</option>
											<option value="JPY">日元</option>
											<option value="CAD">加元</option>
											<option value="NZD">新西兰元</option>
											<option value="MOP">澳门元</option>
											<option value="HKD">香港元</option>
											<option value="TWD">新台湾元</option>
											<option value="SGD">新加坡元</option>
											<option value="KRW">韩元</option>
											<option value="ZAR">南非兰特</option>
											<option value="RUB">俄罗斯卢布</option>
											<option value="INR">印度卢比</option>
											<option value="BRL">巴西雷亚尔</option>
											<option value="CHF">瑞士法郎</option>
											<option value="SEK">瑞典克朗</option>
											<option value="PHP">菲律宾比索</option>
											<option value="AFA">阿富汗尼</option>
											<option value="ALL">列克</option>
											<option value="DZD">阿尔及利亚第纳尔</option>
											<option value="ADP">安道尔比塞塔</option>
											<option value="AZM">阿塞拜疆马纳特</option>
											<option value="ARS">阿根廷比索</option>
											<option value="ATS">先令</option>
											<option value="BSD">巴哈马元</option>
											<option value="BHD">巴林第纳尔</option>
											<option value="BDT">塔卡</option>
											<option value="AMD">亚美尼亚达姆</option>
											<option value="BBD">巴巴多斯元</option>
											<option value="BEF">比利时法郎</option>
											<option value="BMD">百慕大元</option>
											<option value="BTN">努尔特鲁姆</option>
											<option value="BOB">玻利瓦诺</option>
											<option value="BWP">普拉</option>
											<option value="BZD">伯利兹元</option>
											<option value="SBD">所罗门群岛元</option>
											<option value="BND">文莱元</option>
											<option value="BGL">列弗</option>
											<option value="MMK">缅元</option>
											<option value="BIF">布隆迪法郎</option>
											<option value="KHR">瑞尔</option>
											<option value="CVE">佛得角埃斯库多</option>
											<option value="KYD">开曼群岛元</option>
											<option value="LKR">斯里兰卡卢比</option>
											<option value="CLP">智利比索</option>
											<option value="COP">哥伦比亚比索</option>
											<option value="KMF">科摩罗法郎</option>
											<option value="CRC">哥斯达黎加科郎</option>
											<option value="HRK">克罗地亚库纳</option>
											<option value="CUP">古巴比索</option>
											<option value="CYP">塞浦路斯镑</option>
											<option value="CZK">捷克克朗</option>
											<option value="DKK">丹麦克朗</option>
											<option value="DOP">多米尼加比索</option>
											<option value="SVC">萨尔瓦多科郎</option>
											<option value="ETB">埃塞俄比亚比尔</option>
											<option value="ERN">纳克法</option>
											<option value="EEK">克罗姆</option>
											<option value="FKP">福克兰群岛镑</option>
											<option value="FJD">斐济元</option>
											<option value="FIM">马克</option>
											<option value="FRF">法国法郎</option>
											<option value="DJF">吉布提法郎</option>
											<option value="GMD">达拉西</option>
											<option value="DEM">德国马克</option>
											<option value="GHC">塞地</option>
											<option value="GIP">直布罗陀镑</option>
											<option value="GRD">德拉克马</option>
											<option value="GTQ">格查尔</option>
											<option value="GNF">几内亚法郎</option>
											<option value="GYD">圭亚那元</option>
											<option value="HTG">古德</option>
											<option value="HNL">伦皮拉</option>
											<option value="HUF">福林</option>
											<option value="ISK">冰岛克朗</option>
											<option value="IDR">卢比</option>
											<option value="IRR">伊朗里亚尔</option>
											<option value="IQD">伊拉克第纳尔</option>
											<option value="IEP">爱尔兰镑</option>
											<option value="ILS">新谢客尔</option>
											<option value="ITL">意大利里拉</option>
											<option value="JMD">牙买加元</option>
											<option value="KZT">坚戈</option>
											<option value="JOD">约旦第纳尔</option>
											<option value="KES">肯尼亚先令</option>
											<option value="KPW">北朝鲜圆</option>
											<option value="KWD">科威特第纳尔</option>
											<option value="KGS">索姆</option>
											<option value="LAK">基普</option>
											<option value="LBP">黎巴嫩镑</option>
											<option value="LSL">罗提</option>
											<option value="LVL">拉脱维亚拉特</option>
											<option value="LRD">利比里亚元</option>
											<option value="LYD">利比亚第纳尔</option>
											<option value="LTL">立陶宛</option>
											<option value="LUF">卢森堡法郎</option>
											<option value="MGF">马尔加什法郎</option>
											<option value="MWK">克瓦查</option>
											<option value="MYR">马来西亚林吉特</option>
											<option value="MVR">卢菲亚</option>
											<option value="MTL">马尔他里拉</option>
											<option value="MRO">乌吉亚</option>
											<option value="MUR">毛里求斯卢比</option>
											<option value="MXN">墨西哥比索</option>
											<option value="MNT">图格里克</option>
											<option value="MDL">摩尔瓦多列伊</option>
											<option value="MAD">摩洛哥迪拉姆</option>
											<option value="MZM">麦梯卡尔</option>
											<option value="OMR">阿曼里亚尔</option>
											<option value="NAD">纳米比亚元</option>
											<option value="NPR">尼泊尔卢比</option>
											<option value="NLG">荷兰盾</option>
											<option value="ANG">荷属安的列斯盾</option>
											<option value="AWG">阿鲁巴盾</option>
											<option value="VUV">瓦图</option>
											<option value="NIO">金科多巴</option>
											<option value="NGN">奈拉</option>
											<option value="NOK">挪威克朗</option>
											<option value="PKR">巴基斯坦卢比</option>
											<option value="PAB">巴波亚</option>
											<option value="PGK">基那</option>
											<option value="PYG">瓜拉尼</option>
											<option value="PEN">索尔</option>
											<option value="PTE">葡萄牙埃斯库多</option>
											<option value="GWP">几内亚比绍比索</option>
											<option value="TPE">东帝汶埃斯库多</option>
											<option value="QAR">卡塔尔里亚尔</option>
											<option value="ROL">列伊</option>
											<option value="RWF">卢旺达法郎</option>
											<option value="SHP">圣赫勒拿磅</option>
											<option value="STD">多布拉</option>
											<option value="SAR">沙特里亚尔</option>
											<option value="SCR">塞舌尔卢比</option>
											<option value="SLL">利昂</option>
											<option value="SKK">斯洛伐克克朗</option>
											<option value="VND">越南盾</option>
											<option value="SIT">托拉尔</option>
											<option value="SOS">索马里先令</option>
											<option value="ZWD">津巴布韦元</option>
											<option value="ESP">西班牙比塞塔</option>
											<option value="SDD">苏丹第纳尔</option>
											<option value="SRG">苏里南盾</option>
											<option value="SZL">里兰吉尼</option>
											<option value="SYP">叙利亚镑</option>
											<option value="THB">泰铢</option>
											<option value="TOP">邦加</option>
											<option value="TTD">特立尼达和多巴哥元</option>
											<option value="AED">UAE迪拉姆</option>
											<option value="TND">突尼斯第纳尔</option>
											<option value="TRL">土耳其里拉</option>
											<option value="TMM">马纳特</option>
											<option value="UGX">乌干达先令</option>
											<option value="MKD">第纳尔</option>
											<option value="RUR">俄罗斯卢布</option>
											<option value="EGP">埃及镑</option>
											<option value="TZS">坦桑尼亚先令</option>
											<option value="UYU">乌拉圭比索</option>
											<option value="UZS">乌兹别克斯坦苏姆</option>
											<option value="WST">塔拉</option>
											<option value="YER">也门里亚尔</option>
											<option value="YUM">南斯拉夫第纳尔</option>
											<option value="ZMK">克瓦查</option>
											<option value="XAF">CFA法郎BEAC</option>
											<option value="XCD">东加勒比元</option>
											<option value="XOF">CFA法郎BCEAO</option>
											<option value="XPF">CFP法郎</option>
											<option value="TJS">索莫尼</option>
											<option value="AOA">宽扎</option>
											<option value="BYR">白俄罗斯卢布</option>
											<option value="BGN">保加利亚列弗</option>
											<option value="CDF">刚果法郎</option>
											<option value="BAM">可自由兑换标记</option>
											<option value="MXV">墨西哥发展单位</option>
											<option value="UAH">格里夫纳</option>
											<option value="GEL">拉里</option>
											<option value="BOV">Mvdol(玻利维亚)</option>
											<option value="PLN">兹罗提</option>
											<option value="XFU">UIC法郎</option>
										</select>										
									</div>
								</div>
							</div>
						</div>
						<input name="id" type="hidden"/>
        			</div>
        			<div class="box-footer">
        				<div class="row">
        					<div class="col-sm-offset-2 col-sm-10">
        						<button type="button" class="btn btn-md btn-primary" data-toggle="modal" data-target="#myModal"><i class="fa fa-check"></i>保存</button>&nbsp;
        						<button type="button" class="btn btn-md btn-default" id="btnCancel"><i class="fa fa-reply-all"></i>取消</button>
        					</div>
        				</div>
        			</div>
        		</form>
        	</div>
        </div><%-- content --%>
    </div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">提示</h4>
                </div>
                <div class="modal-body">
                    <h4>确认提交吗？</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="btnSubmit">确认</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
    <script src="./js/globalForm.js"></script>
    <script src="./js/getArea.js"></script>
</body>
</html>
