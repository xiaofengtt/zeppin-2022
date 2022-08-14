<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>后台管理系统</title>
		<%@ include file="./globalLink.jsp" %>
		<link rel="stylesheet" href="./css/operatorMethodList.css" />
		<link rel="stylesheet" href="./css/operatorContent.css" />
	</head>
	<body>
        <%@ include file="./header.jsp" %>
		<%@ include file="./super-menu.jsp" %> 
        <div class="clearfix" id="main">
            <div id="content">
                <div class="row">
                    <ol class="breadcrumb">
                        <li class="active">中间库ETL采集</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">	
        					<div class="row">
	        					<div class="col-md-5">
		        					<a class="col-md-2 btn btn-primary btn-sm" onclick="getList();" style="width:100px;">开始采集</a>
	        					</div>
	        				</div>		        					
							<!-- 列表 -->
							<div class="row" style="padding-left:15px; padding-right:15px;">
								
		                        <table class="table table-hover">
		                        	<tr>
		                            	<th>
		                                    <input type="checkbox" id="allCheck" name="" value="">
		                                </th>
		                                <th>序号</th>
		                                <th>表名</th>
		                                <th>表名称</th>
		                                <th>修改sql语句</th>
		                                <th>采集结果</th>
		                            </tr>
		                            <tbody id="queboxCnt">
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGgJgxxb"></td><td>1</td>
		                            		<td>JGXXB</td><td>机构信息表</td><td><a class="editSql">修改</a></td><td class="TGgJgxxbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGgYgxxb"></td><td>2</td>
		                            		<td>YGXXB</td><td>员工信息表</td><td><a class="editSql">修改</a></td><td class="TGgYgxxbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGgGdxxb"></td><td>3</td>
		                            		<td>GDXXB</td><td>股东信息表</td><td><a class="editSql">修改</a></td><td class="TGgGdxxbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjXtnbkmdzb"></td><td>4</td>
		                            		<td>XTNBKMDZB</td><td>信托内部科目对照表</td><td><a class="editSql">修改</a></td><td class="TKjXtnbkmdzbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjXtxmzzkjqkmb"></td><td>5</td>
		                            		<td>XTXMZZKJQKMB</td><td>信托项目总账会计全科目表</td><td><a class="editSql">修改</a></td><td class="TKjXtxmzzkjqkmbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjXtxmzcfztjb"></td><td>6</td>
		                            		<td>XTXMZCFZTJB</td><td>信托项目资产负债科目统计表</td><td><a class="editSql">修改</a></td><td class="TKjXtxmzcfztjbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjGynbkmdzb"></td><td>7</td>
		                            		<td>GYNBKMDZB</td><td>固有内部科目对照表</td><td><a class="editSql">修改</a></td><td class="TKjGynbkmdzbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjGyzzkjqkmb"></td><td>8</td>
		                            		<td>GYZZKJQKMB</td><td>固有总账会计全科目表</td><td><a class="editSql">修改</a></td><td class="TKjGyzzkjqkmbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKjGyzcfzkmtjb"></td><td>9</td>
		                            		<td>GYZCFZKMTJB</td><td>固有资产负债科目统计表</td><td><a class="editSql">修改</a></td><td class="TKjGyzcfzkmtjbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhXtkhgr"></td><td>10</td>
		                            		<td>XTKHGR</td><td>信托客户（个人）</td><td><a class="editSql">修改</a></td><td class="TKhXtkhgrTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhXtkhjg"></td><td>11</td>
		                            		<td>XTKHJG</td><td>信托客户（机构）</td><td><a class="editSql">修改</a></td><td class="TKhXtkhjgTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhJydsgr"></td><td>12</td>
		                            		<td>JYDSGR</td><td>交易对手（个人）</td><td><a class="editSql">修改</a></td><td class="TKhJydsgrTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhJydsjg"></td><td>13</td>
		                            		<td>JYDSJG</td><td>交易对手（机构）</td><td><a class="editSql">修改</a></td><td class="TKhJydsjgTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhJydsjggdxx"></td><td>14</td>
		                            		<td>JYDSJGGDXX</td><td>交易对手（机构）股东信息</td><td><a class="editSql">修改</a></td><td class="TKhJydsjggdxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhDsfhzjgxx"></td><td>15</td>
		                            		<td>DSFHZJGXX</td><td>第三方合作机构信息</td><td><a class="editSql">修改</a></td><td class="TKhDsfhzjgxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TKhTzgwhtb"></td><td>16</td>
		                            		<td>TZGWHTB</td><td>投资顾问合同表</td><td><a class="editSql">修改</a></td><td class="TKhTzgwhtbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtxmxx"></td><td>17</td>
		                            		<td>XTXMXX</td><td>信托项目信息</td><td><a class="editSql">修改</a></td><td class="TXmXtxmxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtzhxx"></td><td>18</td>
		                            		<td>XTZHXX</td><td>信托账户信息</td><td><a class="editSql">修改</a></td><td class="TXmXtzhxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtzjmjhtxx"></td><td>19</td>
		                            		<td>XTZJMJHTXX</td><td>信托资金募集合同信息</td><td><a class="editSql">修改</a></td><td class="TXmXtzjmjhtxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtzjyyhtxx"></td><td>20</td>
		                            		<td>XTZJYYHTXX</td><td>信托资金运用合同信息</td><td><a class="editSql">修改</a></td><td class="TXmXtzjyyhtxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmFdcjsxmxx"></td><td>21</td>
		                            		<td>FDCJSXMXX</td><td>房地产建设项目信息</td><td><a class="editSql">修改</a></td><td class="TXmFdcjsxmxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmFfdcjsxmxx"></td><td>22</td>
		                            		<td>FFDCJSXMXX</td><td>非房地产建设项目信息</td><td><a class="editSql">修改</a></td><td class="TXmFfdcjsxmxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtdbhtb"></td><td>23</td>
		                            		<td>XTDBHTB</td><td>信托担保合同表</td><td><a class="editSql">修改</a></td><td class="TXmXtdbhtbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtdbgxb"></td><td>24</td>
		                            		<td>XTDBGXB</td><td>信托担保关系表</td><td><a class="editSql">修改</a></td><td class="TXmXtdbgxbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtdzywb"></td><td>25</td>
		                            		<td>XTDZYWB</td><td>信托抵质押物表</td><td><a class="editSql">修改</a></td><td class="TXmXtdzywbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtxmsyqb"></td><td>26</td>
		                            		<td>XTXMSYQB</td><td>信托项目受益权表</td><td><a class="editSql">修改</a></td><td class="TXmXtxmsyqbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtxmqsxx"></td><td>27</td>
		                            		<td>XTXMQSXX</td><td>信托项目清算信息</td><td><a class="editSql">修改</a></td><td class="TXmXtxmqsxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TXmXtxmyjhklypgb"></td><td>28</td>
		                            		<td>XTXMYJHKLYPGB</td><td>信托项目预计还款来源评估表</td><td><a class="editSql">修改</a></td><td class="TXmXtxmyjhklypgbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyXtzjmjjfpl"></td><td>29</td>
		                            		<td>XTZJMJJFPLS</td><td>信托资金募集及分配流水</td><td><a class="editSql">修改</a></td><td class="TJyXtzjmjjfplTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyXtzjyyjyl"></td><td>30</td>
		                            		<td>XTZJYYJYLS</td><td>信托资金运用交易流水</td><td><a class="editSql">修改</a></td><td class="TJyXtzjyyjylTd lastTd"></td>		                            	
		                            	</tr>   
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyQjglxxzq"></td><td>31</td>
		                            		<td>QJGLXXZQ</td><td>期间管理信息（证劵类）</td><td><a class="editSql">修改</a></td><td class="TJyQjglxxzqTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyQjglxxfzq"></td><td>32</td>
		                            		<td>QJGLXXFZQ</td><td>期间管理信息（非证劵类）</td><td><a class="editSql">修改</a></td><td class="TJyQjglxxfzqTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyXtsypz"></td><td>33</td>
		                            		<td>XTSYPZ</td><td>信托受益凭据</td><td><a class="editSql">修改</a></td><td class="TJyXtsypzTd lastTd"></td>		                            	
		                            	</tr>     
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TJyXtsyqzrxx"></td><td>34</td>
		                            		<td>XTSYQZRXX</td><td>信托受益权转让信息</td><td><a class="editSql">修改</a></td><td class="TJyXtsyqzrxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGyzhxx"></td><td>35</td>
		                            		<td>GYZHXX</td><td>固有账户信息</td><td><a class="editSql">修改</a></td><td class="TGyGyzhxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGyzzyyhtxx"></td><td>36</td>
		                            		<td>GYZZYYHTXX</td><td>固有资金运用合同信息</td><td><a class="editSql">修改</a></td><td class="TGyGyzzyyhtxxTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGyzjyyjyl"></td><td>37</td>
		                            		<td>GYZJYYJYLS</td><td>固有资金运用交易流水</td><td><a class="editSql">修改</a></td><td class="TGyGyzjyyjylTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGydbhtb"></td><td>38</td>
		                            		<td>GYDBHTB</td><td>固有担保合同表</td><td><a class="editSql">修改</a></td><td class="TGyGydbhtbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGydbgxb"></td><td>39</td>
		                            		<td>GYDBGXB</td><td>固有担保关系表</td><td><a class="editSql">修改</a></td><td class="TGyGydbgxbTd lastTd"></td>		                            	
		                            	</tr>
		                            	<tr>
		                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="TGyGydzywb"></td><td>40</td>
		                            		<td>GYDZYWB</td><td>固有抵质押物表</td><td><a class="editSql">修改</a></td><td class="TGyGydzywbTd lastTd"></td>		                            	
		                            	</tr>
		                            </tbody>
		                        </table>
		                        
		                    </div>
        				</div>
        				
        			</div>
        			<div class="clear"></div>
        		</div>
            </div>
        </div>

    	<input id="scode" type="hidden" value="008007" />
        <script src="./js/ETLMiddlecollect.js"></script>
	</body>
</html>
