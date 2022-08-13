// info showBox strat//
var showBoxE = null;
function createInfoBox()
{
	showBoxE = document.createElement("<div id='showBox'  style='position:absolute;z-index:1;display:block;padding:4px;width:200px;height:0px;background-color:#ff9900;border:4px solid #EFEFEF;color:#ffffff;font-size:12px;'></div>");
	document.body.insertBefore(showBoxE);
}
function showInfo(str)
{
	if(document.getElementById("showBox")==null)
	{
		createInfoBox();
	}
	showBox.style.left = event.clientX+document.body.scrollLeft+event.srcElement.clientWidth-event.offsetX+"px";
	showBox.style.top = event.clientY+document.body.scrollTop-event.srcElement.clientHeight+"px";
	showBox.innerText = str;
	showBox.style.display = "block";
}
function clearInfo()
{
	showBox.style.display = "none";
	document.releaseCapture();
}
// info showBox end//

// select attach strat//
var optionC = new Array();
optionC[0] = new Array('0','--请选择--','')
optionC[1] = new Array('0','(1)函授专科','1')
optionC[2] = new Array('0','(2)函授本科','2')
optionC[3] = new Array('1','(3)函授专升本','3')
optionC[4] = new Array('0','(4)网络秋专科','4')
optionC[5] = new Array('0','(5)网络秋本科','5')
optionC[6] = new Array('1','(6)网络秋专升本','6')
optionC[7] = new Array('0','(7)网络春本科','7')
optionC[8] = new Array('1','(8)网络春专升本','8')
optionC[9] = new Array('0','(9)网络春专科','9')

//
function detectSelectChange(iIndex,divId,reversBl,functionId)
{
	if(document.getElementById("sexBox")==null) return;
	if(document.getElementById("card_type").options[iIndex].selected)
	{
		eval(divId).style.display = (reversBl==true)? "block":"none";
	}
	else
	{
		eval(divId).style.display = (reversBl==true)? "none":"block";
	}
	if(birthBox.style.display == "none")
		sexBox.style.display = "none";
	else
		sexBox.style.display = "block";
	if(functionId == null)	return;
}
function detectMultSelectChange(str,divId,reversBl)
{
	var tempArray = str.split("--");
	var tempN = 0;
	for(var i=0;i<event.srcElement.options.length;i++)
	{
		if(event.srcElement.options[i].selected)
		{
			for(var j=0;j<tempArray.length;j++)
			{
				tempN++;
				//alert(event.srcElement.options[i].innerText+"  "+tempArray[j])
				if(event.srcElement.options[i].innerText.indexOf(tempArray[j])>=0)
				{
					if(reversBl==true)
					{
						eval(divId).style.display = (reversBl==true)? "block":"none";
					}
					else
					{
						eval(divId).style.display = (reversBl==true)? "none":"block";	
					}
					return;
				}
			}
			if(tempN==tempArray.length)
			{
				eval(divId).style.display = "none";
			}
		}
	}

	if(birthBox.style.display == "none")
		sexBox.style.display = "none";
	else
		sexBox.style.display = "block";
}

function switchOption(str)
{
	var tempArray = str.split("--");
	var tempN = 0;
	var tempO = document.getElementById("whcd");
	var tempT = document.getElementById("edutype_id");
	for(var i=0;i<tempO.options.length;i++)
	{
		if(tempO.options[i].selected)
		{
			for(var k=0;k<tempArray.length;k++)
			{
				if(tempO.options[i].innerText.indexOf(tempArray[k])>=0)
				{
					for(var j=0;j<tempT.options.length;j++)
					{
						if(tempT.options[j].innerText.indexOf("升本")>=0)
						{
							tempT.options.remove(j);
						}
						document.getElementById("additionSelect").style.display = "none";
					}
				}
			}
		}
	}
}
// select attach end//

function doLoad()
{
	detectSelectChange(1,'birthBox',false,1);
}
window.onload = doLoad;