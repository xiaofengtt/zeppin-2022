<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<style>
th{white-space: nowrap;}
.errorList{overflow:auto;}
</style>
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width:90%">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">错误信息</h4>
                </div>
                <div class="modal-body">
                	<h4 class="modal-title model-title-tableName">数据列表</h4>
					<div class="errorList module-2-tablebox">
						<table class="table tableContent table-hover" style="width:auto;">
							
						</table>
						
					</div>
					<h4 class="modal-title">错误枚举</h4>
                    <div class="errorBox module-2-tablebox">					
						<table class="table tableContent table-hover">
							
						</table>
					</div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>