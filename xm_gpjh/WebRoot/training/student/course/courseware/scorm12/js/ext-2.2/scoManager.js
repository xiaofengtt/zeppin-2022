/////////////////param for scoManager
////////scoManager//////////
//整体sco数据，包含运行时数据RTData.
var orgData={};
/*数据模型
var orgData = 	{
					sid:"ORG1",
					rid:null,
					visible:true,
					title:"",
					href:"fsdf1.htm",
					RTData:new cmi(),
					items:	[
							{
								sid:"A",
								rid:"R_A",
								visible:true,
								title:"",
								href:"",
								RTData:new cmi(),
								items:	[
											{
											sid:"A01",
											rid:"R_A01",
											visible:true,
											title:"",
											href:"",
											RTData:new cmi(),
											items:	[
													{
														sid:"A0101",rid:"R_A0101",visible:true,title:"",href:"",RTData:new cmi(),itmes:new Array()
													},
													{
														sid:"A0102",rid:"R_A0102",visible:true,title:"",href:"",RTData:new cmi(),itmes:new Array()
													}
													]
											},
											{
												sid:"A02",rid:"R_A02",visible:true,title:"",href:"",RTData:new cmi(),items:new Array()
											}
										]
							},
							{
								sid:"B",rid:"R_B",visible:true,title:"",href:"",RTData:new cmi(),items:new Array()
							}
							]
				}

*/
//studyStatus的模型，在ext - column-tree.js中用到
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

//sco管理类
var scoManager = 	{
						allowDebug:false,
						_alert:function(par)
						{
							window.status = par;
							if(this.allowDebug)	alert(par);
						},
						getStudyStatus:function()
						{
							var ssObj = this.creatSSObj(orgData);
							if(typeof(ssObj)=="array")
							{
								return ssObj;
							}
							else if(typeof ssObj == "object" && ssObj.children)
							{
								return ssObj.children;
							}
							/*return JSON.parse(json, function (key, value) {
															  return value;
															  }
											);*/
						},
						creatSSObj:function(obj)
						{
							var returnObj = new studyStatus();
							returnObj.title=obj.title;
							returnObj.sid = obj.sid;
							if(obj.RTData!=null)
							{
								returnObj.duration=		obj.RTData.core[7].value;
								returnObj.status=		obj.RTData.core[4].value;
								returnObj.score=		obj.RTData.core[6].value[0].value;
								returnObj.effects=		obj.RTData.core[7].value+"|"+obj.RTData.core[6].value[0].value;
								if(obj.href!="#")
									returnObj.links=	obj.href;
							}
							if(typeof(obj.items) != "undefined" && obj.items.length>0)
							{
								var tmpstr=this.calTotalDuration(obj);
								//alert(tmpstr);
								returnObj.duration=	tmpstr.substring(0,tmpstr.indexOf("."));	
								returnObj.score = this.calTotalScore(obj);
								returnObj.status = this.calTotalStatus(obj);
								returnObj.effects = returnObj.duration + "|" + returnObj.score;
								returnObj.cls=			"master-task";
								delete returnObj.leaf;
								returnObj.iconCls=		"task-folder";
							}
							else
								return returnObj;
							//											
							for(var i=0;i<obj.items.length;i++)
							{
								
								returnObj.children.push(this.creatSSObj(obj.items[i]));
								
							}
							return returnObj;
						},
						//设置分数
						calTotalScore:function(obj)
						{
							var totalScore = 0;
							if(obj.RTData!=null && obj.RTData.core[6].value[0].value!='' && obj.RTData.core[6].value[0].value!="null")
							{
								totalScore =	this.combScore(totalScore,obj.RTData.core[6].value[0].value);
							}
							//
							for(var i=0;i<obj.items.length;i++)
							{
								totalScore = 	this.combScore(totalScore,this.calTotalScore(obj.items[i]));
							}
							if(obj.items.length != 0)
								return parseInt(totalScore / obj.items.length);
							else
								return totalScore;
						},
						//设置完成状态
						calTotalStatus:function(obj)
						{
							var totalStatus = "not attempted";
							var completeNum = 0;
							if(obj.RTData!=null && obj.RTData.core[4].value!='' && obj.RTData.core[4].value!="null")
							{
								totalStatus =	obj.RTData.core[4].value;
							}
							//
							if(this.getTotalScoNum(obj) != 0)
								return "完成度:" + parseInt(this.getCompletedScoNum(obj) * 10000 / this.getTotalScoNum(obj))/100 + "%";
							else
								return totalStatus;
						},
						//统计最底层的sco数
						getTotalScoNum:function(obj)
						{
							var totalNum = 0;
							if(obj.RTData!=null //&& obj.RTData.core[4].value!='' 
								&& obj.RTData.core[4].value!="null" && obj.items.length == 0)
							{
								return ++totalNum;
							}
							for(var i=0;i<obj.items.length;i++)
							{
								totalNum += this.getTotalScoNum(obj.items[i]);
							}
							return totalNum;
						},
						
						//统计完成的Sco数
						getCompletedScoNum:function(obj)
						{
							var totalNum = 0;
							if(obj.RTData!=null && obj.RTData.core[4].value!='' 
								&& obj.RTData.core[4].value!="null" && obj.items.length == 0)
							{
								if(obj.RTData.core[4].value == "completed")
									return ++totalNum;
								else
									return totalNum;
							}
							for(var i=0;i<obj.items.length;i++)
							{
								totalNum += this.getCompletedScoNum(obj.items[i]);
							}
							return totalNum;
						},
						calTotalDuration:function(obj)
						{
							var totalDuration = "00:00:00.00";
							if(obj.RTData!=null && obj.RTData.core[7].value!='' && obj.RTData.core[7].value!="null")
							{
								totalDuration =		this.combTime(totalDuration,obj.RTData.core[7].value);
							}
							//
							for(var i=0;i<obj.items.length;i++)
							{
								//var pretotal = totalDuration;
								//var tmpstr=this.calTotalDuration(obj.items[i]);
								totalDuration = 	this.combTime(totalDuration,this.calTotalDuration(obj.items[i]));
								//alert("pre:" + pretotal + " num:" + tmpstr + " total:"+ totalDuration);
							}
							return totalDuration;
						},
						//
						combScore:function(preScore,thisScore)
						{
							try
							{
								return Number(preScore) + Number(thisScore);
							}
							catch(err)
							{
								return 0;
							}
						},
						combTime:function(preTime,thisTime)
						{
							//00:00:01.42
							if(preTime==undefined ||preTime==""|| preTime=="null")	return thisTime;
							var combTAry = new Array();
							var returnString = "";
							var pT = this.formatT2Ary(preTime)
							var tT = this.formatT2Ary(thisTime);
							
							if(pT!=false && tT!=false)
							{
								for(var i=0;i<4;i++)
								{
									if(i==3)
									{
										combTAry.push((Number(pT[i])*100+Number(tT[i])*100)/100);
									}
									else
									{
										combTAry.push(Number(pT[i])+Number(tT[i]));
									}
								}
								for(var i=3;i>=0;i--)
								{
									if(combTAry[i]>=1 && i==3)
									{
										combTAry[i]=combTAry[i]-1;
										combTAry[i-1]=combTAry[i-1]+1;
									}
									else if(combTAry[i]>=60 && i!=0 && i!=3)
									{
										combTAry[i]=combTAry[i]-60;
										combTAry[i-1]=combTAry[i-1]+1;
									}
								}
								for(var i=0;i<4;i++)
								{
									if(i==3)
									{
										var sec = String(combTAry[i]).substring(1,4);
										if(sec.length == 0)
										{
											returnString+=".00";
										}
										else if(sec.length<=3)
										{
											for(var k=0;k<3-sec.length;k++)
											{
												sec+="0"
											}
											returnString+=sec;
										}
									}
									else
										returnString+= this.format2Bit(combTAry[i]);
									//加间隔符号
									if(i<2)
									{
										returnString+= ":";
									}
								}
								//this._alert("return totalTime:"+returnString);
								return returnString;
							}
							else
							{
								return "00:00:00.00";
							}
						},
						formatT2Ary:function(time)
						{
							var a,b;
							if(time.split(":").length==3 && time.indexOf(".")>=0)
							{
								a = time.substring(0,time.indexOf(".")).split(":");
								a.push("0"+time.substring(time.indexOf("."),time.length))
							}
							else if(time.split(":").length==3 && time.indexOf(".")<0)
							{
								a = time.split(":");
								a.push("0.00");
							}
							else
							{
								a = false;
							}
							return a;
						},
						format2Bit:function(number)
						{
							if(String(number).length==1)
								return "0"+String(number);
							else
								return number;
						}
					};

