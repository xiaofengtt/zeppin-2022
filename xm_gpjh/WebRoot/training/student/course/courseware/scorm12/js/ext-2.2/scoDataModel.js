//��ϢԪģ��
function el()
{
	this.id = 		'';
	this.type = 	'';
	this.value = 	'';
	this.io = 		'';
}
//scoRTData��ģ��
function cmi()
{
	this.scoID = '';
	//һ����������
	this.cmiParL1 = ["suspend_data","launch_data","comments"];
	this.cmiParL2 = ["core","student_data"];
	//
	this.core=new Array();
	//this.core.score = new Array();
	this.student_data = new Array();
	//core start
	var a=new el();
	a.id='student_id';
	a.io='r';
	a.value=top.studentID;
	this.core[this.core.length]=a;
	
	var a=new el();
	a.id='student_name';
	a.io='r';
	a.value=top.studentName;
	this.core[this.core.length]=a;
	
	var a=new el();
	a.id='lesson_location';
	a.io='rw';
	this.core[this.core.length]=a;
	
	/* 'credit', 'no-credit' */
	var a=new el();
	a.id='credit';
	a.io='r';
	this.core[this.core.length]=a;
	
	/* 'completed', 'failed', 'passed', 'incomplete', 'browsed', 'not attempted'. */
	var a=new el();
	a.id='lesson_status';
	a.io='rw';
	a.value='not attempted';
	this.core[this.core.length]=a;
	
	/* "ab-initio":��ʼ״̬����δ���� "resume":�������뿪�� */
	var a=new el();
	a.id='entry';
	a.io='r';
	a.value='ab-initio';
	this.core[this.core.length]=a;
	
	var a=new el();
	a.id='score';
	a.io='rw';
	a.value = new Array();
	this.core[this.core.length]=a;
	
	var a=new el();
	a.id='raw';
	a.io='rw';
	a.value = '0';
	this.core[6].value[0]=a;
	

	var a=new el();
	a.id='total_time';
	a.io='r';
	this.core[this.core.length]=a;
	
	/* 'time-out', 'suspend', 'logout' */
	var a=new el();
	a.id='exit';
	a.io='rw';
	this.core[this.core.length]=a;
	
	var a=new el();
	a.id='session_time';
	a.io='rw';
	this.core[this.core.length]=a;
	//core end
	

	//student_data start
	// mastery_score ���Կ�����passed״̬���õ�����;���raw>mastery_score,��passed;����failed
	var a=new el();
	a.id='mastery_score';
	a.io='r';
	a.value='0';
	this.student_data[this.student_data.length]=a;
	//student_data end
	
	//over_all par
	this.suspend_data='';
	this.launch_data='';
	this.comments='';
}

/*
var idata = new cmi();
alert(idata.launch_data);
var a = new el();
a.id = "raw";
a.value = 60;
idata.core["score"][1] = a;
alert(idata.core["score"][1].id+" : "+idata.core["score"][1].value)
*/

//orgData��ģ��
function sco()
{
	this.sid=				null;
	this.rid=				null;
	this.visible=		true;
	this.title=			'{������}';
	this.items=			new Array();
	this.href=			'#';
	this.RTData=		null;												
}

//studyStatus��ģ��
function studyStatus()
{
	this.title=			"";
	this.duration=		"00:00:00.00";
	this.status=		"";
	this.score=			"";
	this.cls=			"";
	this.leaf=			"true";
	this.iconCls=		"task";
	this.uiProvider=	"col";
	this.effects=		"";
	this.links=			"";
	this.children=		new Array();
}

//userData��ģ��
function user()
{
	this.name = 		"";
	this.email = 		"";
	this.orgData = 	null;
}