//******************************************************
// SYSTEM NAME		OA8000
// SUB SYSTEM NAME	JS COMMON COMPONENT
// FILE NAME			systemMsg.js
/**
* �����ύ����ǰ����ʾ����
* @AUTHOR	sissi
* @VERSION 	2004.08.03 �½�
*/
//******************************************************

//ɾ������ǰ����ʾ
function deleteData() {
	if (confirm("ȷʵҪɾ��������")) submitButton("DELETE");
}

//ɾ��ȫ������ 
function deleteAllData() {
	if (confirm("ȷʵҪɾ��ȫ��������")) submitButton("DELETEALL");
}

//���ļ��Ĳ���
function deleteFile() {
	if (confirm("ȷʵҪɾ����ѡ�ļ����ļ�����")) submitButton("DELETE");
}

//����̳�Ĳ���--����
function deleteForumIndex() {
	if (confirm("ȷʵҪɾ����ǰ����ȫ��������")) submitButton("DELETE");
}

//�Իظ���ɾ��
function deleteForumDetail(site) {
	if (confirm("ȷʵҪɾ����ǰ�ظ���")) submitButton("DELETEDETAIL" + site);
}

//��ͶƱ�Ĳ���
function deleteVoting() {
	if (confirm("ȷʵҪɾ����ǰͶƱ��")) submitButton("DELETE");
}

//���ռǵĲ���--����
function deleteDaily() {
	if (confirm("ȷʵҪɾ����ǰ�ռǼ�ȫ���ظ���")) submitButton("DELETE");
}

//�Դ��¼ǵĲ���--����
function deleteEvent() {
	if (confirm("ȷʵҪɾ����ǰ�¼���ȫ���ظ���")) submitButton("DELETE");
}

//�ղ�ǰ����ʾ
function archiveData() {
	if (confirm("ȷʵҪ����ѡ�����ղ���")) submitButton("ARCHIVE");
}

//�������ϵĲ���
function scrapCar() {
	if (confirm("ȷ�ϵ�ǰ��ѡ����Ҫ������")) submitButton("SCRAP");
}

//������������ǰ����ʾ
function cancelBorrowData() {
	if (confirm("ȷʵҪ����������")) submitButton("CANCEL");
}

//���뵵��ǰ����ʾ
function recordData() {
	if (confirm("ȷʵҪ����ѡ���ݹ��뵵����")) submitButton("RECORD");
}