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
	<input id="scode" type="hidden" value="002005" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TKjGyzzkjqkmb"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>固有总账会计全科目表</li>
                                
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
										<span class="required">*</span> 会计日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="kjrq" maxlength="8" class="form-control form-date input-required" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 报送周期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="bszq" class="form-control digits input-required">
											<option value="1">日报</option>
											<option value="2">月报</option>
											<option value="3">季报</option>
											<option value="4">半年报</option>
											<option value="5">年报</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 固有总账会计科目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gyzzkjkmbh" maxlength="60" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有总账会计科目名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gyzzkjkmmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有总账会计科目级次：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gyzzkjkmjc" maxlength="18" class="form-control digits"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有总账会计科目类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="gyzzkjkmlx" class="form-control digits">
											<option value="1">资产</option>
											<option value="2">负债</option>
											<option value="3">所有者权益</option>
											<option value="4">损益</option>
											<option value="5">资产负债共同类</option>
											<option value="6">表外</option>
											<option value="7">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 期初借方余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="qcjfye" class="form-control number form-money" title="期初借方余额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 期初贷方余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="qcdfye" class="form-control number form-money" title="期初贷方余额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 本期借方发生额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="bqjffse" class="form-control number form-money" title="本期借方发生额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 本期贷方发生额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="bqdffse" class="form-control number form-money" title="本期贷方发生额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 期末借方余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="qmjfye" class="form-control number form-money" title="期末借方余额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 期末贷方余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="qmdfye" class="form-control number form-money" title="期末贷方余额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 核算币种：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="hsbz" class="form-control form-bz input-required" data-live-search="true" id="bzSelect">
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
</body>
</html>
