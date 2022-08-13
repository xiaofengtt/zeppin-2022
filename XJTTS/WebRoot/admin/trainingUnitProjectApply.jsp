<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">可申报项目</s:param>
</s:action>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">可申报项目</div>
		<div class="cui-menu2">
			
		</div>

		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-year"><a href="../admin/trainingUnitProjectApply_initPage.action?sort=year-asc" data-name="year"><span>年份</span></a></li>
				<li id="sorting-name" class=""><a href="../admin/trainingUnitProjectApply_initPage.action?sort=name-asc" data-name="name"><span>名称</span></a></li>
				<li id="sorting-status" class=""><a href="../admin/trainingUnitProjectApply_initPage.action?sort=status-asc" data-name="status"><span>状态</span></a></li>
				<li id="sorting-funds" class=""><a href="../admin/trainingUnitProjectApply_initPage.action?sort=funds-asc" data-name="funds"><span>经费标准</span></a></li>
				<li id="sorting-creator" class=""><a href="../admin/trainingUnitProjectApply_initPage.action?sort=creator-asc" data-name="creator"><span>创建人</span></a></li>
				<li id="sorting-creattime" class=""><a href="../admin/trainingUnitProjectApply_initPage.action?sort=creattime-asc" data-name="creattime"><span>创建时间</span></a></li>

			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="projectHash" id="pros">
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="150px"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span class="text-primary">ID：</span><s:property value="key" /></td>
									<td width="auto"><span class="text-primary list-title">名称：</span><span><s:property
												value="value[0]" /><s:if test="value[20]==2" ><font color=red>(自主报名)</font></s:if></span></td>
									<td width="100px"><span class="text-primary">状态：</span><span
										id="status_<s:property value="key" />" class="text-danger"><s:property value="value[1]" /></span></td>
									<td align="center" width="120px">&nbsp;&nbsp; 
										<a class="ifrmbox"  href="../admin/trainingUnitProjectApply_projectApply.action?id=<s:property value="key" />" data-fancybox-type="iframe">申报此项目</a>&nbsp;&nbsp; 
										
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">开设年份：</span> <s:property
										value="value[2]" /></li>
								<li><span class="text-primary">项目类型：</span> 
								<div onmouseout="this.className='longtxt2'" onmouseover="this.className='longtxt2 longtxt2_hover'" class="longtxt2">
								<s:property
										value="value[3]" /></div></li>
								<li><span class="text-primary">项目简称：</span> <s:property
										value="value[4]" /></li>
								<li><span class="text-primary">所属地区：</span> <s:property
										value="value[5]" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">申报截止日期：</span> <s:property
										value="value[6]" /></li>
								<li><span class="text-primary">经费标准：</span> <s:property
										value="value[7]" /><s:property
										value="value[21]" /></li>
								<li><span class="text-primary">计划培训人数：</span> <s:property
										value="value[8]" /></li>
								<li><span class="text-primary">最大申报学科：</span> <s:property
										value="value[9]" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">招标类型：</span> <s:property
										value="value[10]" /></li>
								<li><span class="text-primary">学科范围：</span> <s:property
										value="value[11]" /></li>
								<li><span class="text-primary">培训方式：</span> <s:property
										value="value[12]" /></li>
								<li><span class="text-primary">项目级别：</span> <s:property
										value="value[13]" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">申报模板：</span> <s:property value="value[14]" />
										<s:if test="value[18]!=null">
										<a href="..<s:property value="value[18]" />">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_<s:property value="key" />" href="../admin/documentIframe.jsp?id=checkbox_<s:property value="key" />" data-fancybox-type="iframe" data-url='<s:property value="value[18]" />'>查看</a>
										</s:if>
										</li>
								<li><span class="text-primary">红头文件：</span> <s:property value="value[22]" />
										<s:if test="value[23]!=null">
										<a href="..<s:property value="value[23]" />">下载</a>
<%-- 										<a href='javascript:void(0);' onclick="checkDoc(this);" data-url='<s:property value="value[23]" />'>查看</a> --%>
										<a class="checkbox" style="display:inline;" id="redHead_<s:property value="key" />" href="../admin/documentIframe.jsp?id=redHead_<s:property value="key" />" data-fancybox-type="iframe" data-url='<s:property value="value[23]" />'>查看</a>
										</s:if>
										</li>
								<li><span class="text-primary">评审指标：</span> <s:property 
										value="value[15]" /></li>
								<li><span class="text-primary">&nbsp;&nbsp;
										创建人：</span> <s:property value="value[16]" /></li>
								<li><span class="text-primary">创建时间：</span> <s:property
										value="value[17]" /></li>

							</ul>
						</div>

					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>


	</div>

</div>
<script>
	$(function() {//添加按钮
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
	
		$(".checkbox").colorbox({
			iframe : true,
			width : "1060px",
			height : "900px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
	})

	var page = url('?page');
	var id = <s:property value="collegeId" />;
	$.getJSON('../admin/projectBase_getPageJsonForTrainingCollege.action?', {page : page,id : id}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage:function(type, page, current){
                switch(type)
                {
                    default:
                        return true;
                }
            },
			onPageClicked : function(e, originalEvent, type, page) {
				window.location = updateURLParameter(url(),'page',page);
			}

		}
		$('#pagination').bootstrapPaginator(options);
	})
	
	
	$(function(){
		var sort = (url('?sort')) ? url('?sort') : 'year-asc';
		var _ = sort.split('-');
		$('#sorting-'+_[0]).addClass('crt');
		$('#sorting-'+_[0]).find('span').addClass(_[1]);
		
	})
	
	$('.sorting-btns a').click(function(){
		
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	})
	
	function sortasc(name) {
		var order = (url('?sort').split('-')[1] == 'asc') ? 'desc' : 'asc';
		window.location = url('protocol') + '://' + url('hostname') + url('path') + '?sort=' + name + '-' + order;
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>