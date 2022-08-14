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
	<input id="scode" type="hidden" value="004004" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TXmXtzjyyhtxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>信托资金运用合同信息</li>
                                
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
										<span class="required hide">*</span> 信托项目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xtxmbh" class="form-control" data-live-search="true" id="xtxmbhSelect">
        									<c:forEach items="${sessionScope.productMap}" var="productMap">
	        									<option value="${productMap.key}">${productMap.key}-${productMap.value}</option>
	        								</c:forEach>	
        								</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托子项目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtzxmbm" maxlength="50" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 运用合同编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="yyhtbh" maxlength="40" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 运用合同名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="yyhtmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 业务种类：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="ywzl" class="form-control">
											<option value="流动资金贷款">流动资金贷款</option>
											<option value="个人贷款">个人贷款</option>
											<option value="固定资产贷款">固定资产贷款</option>
											<option value="其他贷款">其他贷款</option>
											<option value="租赁">租赁</option>
											<option value="股权、实物、收益权投资附回购或附回购选择权（含指定第三方回购、转让）">
											股权、实物、收益权投资附回购或附回购选择权（含指定第三方回购、转让）</option>
											<option value="买入返售或附回购承诺">买入返售或附回购承诺</option>
											<option value="资产或收益权买断式投资">资产或收益权买断式投资</option>
											<option value="长期股权投资">长期股权投资</option>
											<option value="股票投资">股票投资</option>
											<option value="债券投资">债券投资</option>
											<option value="基金投资">基金投资</option>
											<option value="实物投资（无指定第三方转让或回购条款）">实物投资（无指定第三方转让或回购条款）</option>
											<option value="其他金融机构理财产品投资">其他金融机构理财产品投资</option>
											<option value="拆出">拆出</option>
											<option value="存放同业">存放同业</option>
											<option value="其他运用方式">其他运用方式</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 对手方类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="dsflx" class="form-control">
											<option value="个人">个人</option>
											<option value="金融机构">金融机构</option>
											<option value="非金融机构">非金融机构</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 对手方编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="dsfbh" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 合同签订日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="htqdrq" maxlength="8" class="form-control form-date" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 首次放款日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="scfkrq" maxlength="8" class="form-control form-date" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 合同到期日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="htdqrq" maxlength="8" class="form-control form-date" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 合同签订金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="htqdje" class="form-control number form-money" title="合同签订金额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 合同签约利率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="htqyll" class="form-control number form-decimal3" title="合同签约利率" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 交易对手年化资金成本：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jydsnhzjcb" maxlength="18" class="form-control digits form-money" title="交易对手年化资金成本" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 投向行业：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="txhy" class="form-control">
											<option value="基础产业">基础产业</option>
											<option value="房地产">房地产</option>
											<option value="证券">证券</option>
											<option value="金融机构">金融机构</option>
											<option value="工商企业">工商企业</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 投向行业明细：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="txhymx" maxlength="6" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 资金运用地区：<i class="fa icon-question hide"></i></label>
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
										<input name="zjtxdq" type="hidden" value="" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 资金运用方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zjyyfs" class="form-control form-checkbox" multiple="multiple">
        									<option value="贷款">贷款</option>
        									<option value="交易性">交易性</option>
        									<option value="可供出售">可供出售</option>
        									<option value="持有至到期">持有至到期</option>
        									<option value="长期股权投资">长期股权投资</option>
        									<option value="租赁">租赁</option>
        									<option value="买入返售">买入返售</option>
        									<option value="折出">折出</option>
        									<option value="存放同业">存放同业</option>
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
										<span class="required hide">*</span> 还款方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="hkfs" class="form-control">
											<option value="到期还本付息">到期还本付息</option>
											<option value="分期付息">分期付息</option>
											<option value="分期还本">分期还本</option>
											<option value="等额本金">等额本金</option>
											<option value="等额本息">等额本息</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 提前终止：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="tqzz" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 退出方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="tcfs" class="form-control">
											<option value="无">无</option>
											<option value="回购">回购</option>
											<option value="回购选择权">回购选择权</option>
											<option value="买断">买断</option>
											<option value="并购">并购</option>
											<option value="公开上市(IPO)">公开上市(IPO)</option>
											<option value="转让">转让</option>
											<option value="管理层收购(MBO)">管理层收购(MBO)</option>
											<option value="清算">清算</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 资产五级分类：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zcwjfl" class="form-control">
											<option value="正常">正常</option>
											<option value="关注">关注</option>
											<option value="次级">次级</option>
											<option value="可疑">可疑</option>
											<option value="损失">损失</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 标的资产类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="bdzclx" class="form-control">
											<option value="股份">股份</option>
											<option value="非标准金融工具">非标准金融工具</option>
											<option value="债券">债券</option>
											<option value="物业">物业</option>
											<option value="艺术品">艺术品</option>
											<option value="酒类">酒类</option>
											<option value="其他另类投资">其他另类投资</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 标的资产名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="bdzcmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 标的资产入账价值：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="bdzcrzjz" class="form-control number form-money" title="标的资产入账价值" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 原始合同号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="yshth" maxlength="40" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 原始对手方类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="ysdsflx" class="form-control">
											<option value="个人">个人</option>
											<option value="机构">机构</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 原始对手方编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="ysdsfbh" maxlength="40" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 投融资余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="trzye" class="form-control number form-money" title="投融资余额" />
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
