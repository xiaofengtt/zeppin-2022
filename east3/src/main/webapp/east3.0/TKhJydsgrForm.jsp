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
	<input id="scode" type="hidden" value="003003" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TKhJydsgr"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>交易对手（个人）</li>
                                
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
										<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control" value="G10110105001163302" readonly />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control input-required" value="K0066H211000001" readonly/>
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
										<span class="required">*</span> 对手编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="dsbh" maxlength="40" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 对手全称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="syrqc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 证件类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zjlx" class="form-control">
											<option value="居民身份证">居民身份证</option>
											<option value="户口簿">户口簿</option>
											<option value="护照">护照</option>
											<option value="军官证">军官证</option>
											<option value="士兵证">士兵证</option>
											<option value="港澳居民来往内地通行证">港澳居民来往内地通行证</option>
											<option value="台湾同胞来往内地通行证">台湾同胞来往内地通行证</option>
											<option value="临时身份证">临时身份证</option>
											<option value="外国人居留证">外国人居留证</option>
											<option value="警官证">警官证</option>
											<option value="无证件">无证件</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 证件号码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zjhm" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 所属国别：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select id="ssgb" name="ssgb" class="form-control form-ssdgb" data-live-search="true">
											<option value="156">中国</option>
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
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 所属地区：<i class="fa icon-question hide"></i></label>
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
										<input name="ssdq" type="hidden" value="" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 居住地址：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jzdz" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 职业：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zy" class="form-control">
											<option value="政府部门">政府部门</option>
											<option value="教科文">教科文</option>
											<option value="金融">金融</option>
											<option value="商贸">商贸</option>
											<option value="房地产">房地产</option>
											<option value="制造业">制造业</option>
											<option value="自由职业">自由职业</option>
											<option value="公务员">公务员</option>
											<option value="专业技术人员">专业技术人员</option>
											<option value="办事人员">办事人员</option>
											<option value="军人">军人</option>
											<option value="商业和服务类人员">商业和服务类人员</option>
											<option value="生产、运输设备操作人员">生产、运输设备操作人员</option>
											<option value="农、林、牧、渔、水利业生产人员">农、林、牧、渔、水利业生产人员</option>
											<option value="其它">其它</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 个人年收入：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="grnsr" class="form-control number form-money" title="个人年收入" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 家庭年收入：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jtnsr" class="form-control number form-money" title="家庭年收入" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 关联类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="gxlx" class="form-control">
											<option value="无">无</option>
											<option value="公司的股东">公司的股东</option>
											<option value="公司股东的关联企业">公司股东的关联企业</option>
											<option value="公司自有资金投资的企业">公司自有资金投资的企业</option>
											<option value="公司自有资金投资的企业的关联企业">公司自有资金投资的企业的关联企业</option>
											<option value="公司以托管或信托等其他方式控制的企业">公司以托管或信托等其他方式控制的企业</option>
											<option value="公司的董事、监事、经理">公司的董事、监事、经理</option>
											<option value="公司的董事、监事、经理投资持股5%以上">公司的董事、监事、经理投资持股5%以上</option>
											<option value="其他关联">其他关联</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 联系电话：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="lxdh" maxlength="30" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 传真电话：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="czdh" maxlength="30" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 邮政编码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="yzbm" maxlength="6" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 工作单位地址：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gzdwdz" maxlength="200" class="form-control"/>
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
