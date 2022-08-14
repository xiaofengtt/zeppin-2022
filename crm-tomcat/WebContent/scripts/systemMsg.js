//******************************************************
// SYSTEM NAME		OA8000
// SUB SYSTEM NAME	JS COMMON COMPONENT
// FILE NAME			systemMsg.js
/**
* 窗口提交数据前的提示处理
* @AUTHOR	sissi
* @VERSION 	2004.08.03 新建
*/
//******************************************************

//删除数据前的提示
function deleteData() {
	if (confirm("确实要删除数据吗？")) submitButton("DELETE");
}

//删除全部数据 
function deleteAllData() {
	if (confirm("确实要删除全部数据吗？")) submitButton("DELETEALL");
}

//对文件的操作
function deleteFile() {
	if (confirm("确实要删除所选文件或文件夹吗？")) submitButton("DELETE");
}

//对论坛的操作--主贴
function deleteForumIndex() {
	if (confirm("确实要删除当前贴及全部跟贴吗？")) submitButton("DELETE");
}

//对回复的删除
function deleteForumDetail(site) {
	if (confirm("确实要删除当前回复吗？")) submitButton("DELETEDETAIL" + site);
}

//对投票的操作
function deleteVoting() {
	if (confirm("确实要删除当前投票吗？")) submitButton("DELETE");
}

//对日记的操作--主贴
function deleteDaily() {
	if (confirm("确实要删除当前日记及全部回复吗？")) submitButton("DELETE");
}

//对大事记的操作--主贴
function deleteEvent() {
	if (confirm("确实要删除当前事件及全部回复吗？")) submitButton("DELETE");
}

//收藏前的提示
function archiveData() {
	if (confirm("确实要将所选数据收藏吗？")) submitButton("ARCHIVE");
}

//车辆报废的操作
function scrapCar() {
	if (confirm("确认当前所选车辆要报废吗？")) submitButton("SCRAP");
}

//档案撤消借阅前的提示
function cancelBorrowData() {
	if (confirm("确实要撤消借阅吗？")) submitButton("CANCEL");
}

//归入档案前的提示
function recordData() {
	if (confirm("确实要将所选数据归入档案吗？")) submitButton("RECORD");
}