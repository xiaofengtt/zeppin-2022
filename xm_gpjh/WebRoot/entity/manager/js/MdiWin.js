function mywin()
{
	this.winlist = new Array();   //�����б�
	this.maxWins = 20;				//��󴰿���
	this.tagTitleWidth = 120;		//��ǩ���
	this.indentWidth = -1;			//��ǩ�������
	this.currentwin = null;
	this.addwin = addwin;                        //�½����ڷ���
	this.removewin = removewin;                        //�Ƴ�����
	this.removeall = removeall;                                        //�Ƴ����д���
	this.activewin = activewin;                        //�����
	this.container = container;
	this.padLeft = padLeft;					// ���������Ե�ľ���
	this.padRight = padRight;				// �������ұ�Ե�ľ���
	this.scrollWidth = scrollWidth;

	function container(url,title)
	{
		for(var i=0;i<this.winlist.length;i++)
		{
			if(this.winlist[i].title == title && this.winlist[i].url == url)
			{
				return i;
			}
		}
		return -1;
	}
	function activewin(oEl)                 //�����
	{
		if(oEl == null){
			this.currentwin = null;
			return
		}
		var tempzindex = this.currentwin.style.zIndex;
		this.currentwin.wintitle.style.zIndex = this.currentwin.index;
		this.currentwin.style.display = "none";
		this.currentwin.wintitle.style.backgroundImage = 'url(images/tab_b.jpg)';
		oEl.wintitle.style.zIndex = tempzindex;
		oEl.style.display = "";
		oEl.wintitle.style.backgroundImage = 'url(images/tab_a.jpg)';
		this.currentwin = oEl;

		//���������ʾ������
		var mleft = parseInt(titlelist.style.marginLeft);
		if (isNaN(mleft))
			mleft = 0;
		var padleft = this.padLeft(oEl);
		var padright = this.padRight(oEl);
		var clientwidth = titlelist.parentElement.clientWidth
		if(padleft + mleft > clientwidth)
		{
			titlelist.style.marginLeft = clientwidth - padleft;			
		}
		if(padright < clientwidth && mleft < 0)
		{
			mleft = clientwidth - this.scrollWidth();
			if(mleft>0)
				mleft = 0;
			titlelist.style.marginLeft = mleft;
		}
		if(padleft + mleft < this.tagTitleWidth)
		{
			titlelist.style.marginLeft = - (padleft - this.tagTitleWidth);
		}
	}

	function padLeft(oEl)
	{
		var padleft = oEl.index * this.tagTitleWidth - this.indentWidth*(oEl.index-1);
		return padleft;
	}

	function padRight(oEl)
	{
		var count = (this.winlist.length - oEl.index) + 1;
		var padright = this.tagTitleWidth * count - this.indentWidth*(count-1);
		return padright;
	}

	function addwin(url,title)                                        //�����ľ���ʵ��
	{
		var con = this.container(url,title);
		if(con>-1)
		{
			this.activewin(this.winlist[con]);
			return;
		}
		if(this.winlist.length >= this.maxWins)
		{
			alert("������󴰿������ƣ�"+this.maxWins+"�������ȹرղ��ִ���");
			return false;
		}

		oDIV = window.document.createElement( "TABLE" );
		this.winlist[this.winlist.length] = oDIV;                //���б�����Ӵ������
		oDIV.url = url;
		oDIV.title = title;
		oDIV.index = this.winlist.length;
		oDIV.className = "win";
		oDIV.width = "100%";
		oDIV.height = "100%";
		oDIV.cellSpacing=0;
		oDIV.insertRow().insertCell().innerHTML = "<iframe src='"+url+"' class = 'win1' width='100%' height='100%' frameborder='0'></iframe>";

		var oTitle = window.document.createElement( "SPAN" );
		oTitle.className ='wintitle';
		oTitle.style.width = this.tagTitleWidth;
		oTitle.style.backgroundImage = 'url(images/tab_a.jpg)';
		oTitle.style.left = this.winlist.length == 1 ? 0 : this.winlist[this.winlist.length-2].wintitle.style.pixelLeft - this.indentWidth;
		oTitle.title = title;
		title = subStr(title,16);
		oTitle.innerHTML= title == null ? "unkown windows" : title;
		oTitle.win=oDIV;
		oTitle.onclick = new Function("win.activewin(this.win)")

		if(this.currentwin != null) {
			this.currentwin.wintitle.style.backgroundImage = 'url(images/tab_b.jpg)';
			this.currentwin.style.display = "none";
			this.currentwin.wintitle.style.zIndex = this.currentwin.index;
		}
		oDIV.style.zIndex = this.maxWins+1;
		oTitle.style.zIndex = this.maxWins+1;
		oDIV.wintitle = oTitle;
		titlelist.insertAdjacentElement( "beforeEnd" , oTitle );

		var scrollwidth = this.scrollWidth();
		if(scrollwidth > titlelist.parentElement.clientWidth)
		{
			titlelist.style.marginLeft = titlelist.parentElement.clientWidth - scrollwidth;
		}
		mywindows.insertAdjacentElement( "beforeEnd" , oDIV );

		this.currentwin = oDIV;
		return oDIV;
	}

	function scrollWidth()
	{
		var n = this.winlist.length;
		var scrollwidth = this.tagTitleWidth*n - this.indentWidth*(n-1);
		return scrollwidth;
	}

	function removewin(obj)        //�Ƴ�����
	{
		if(obj == null)return;
		var temparr = new Array();
		var afterwin = false;
		for(var i=0;i<this.winlist.length;i++)
		{
			if(afterwin) this.winlist[i].wintitle.style.left =  this.winlist[i].wintitle.style.pixelLeft + this.indentWidth;
			if(this.winlist[i] != obj)
				temparr[temparr.length] = this.winlist[i];
			else
				afterwin = true;
		}
		this.winlist = temparr;
		if(this.currentwin == obj){
			this.activewin(this.winlist[this.winlist.length-1]);
		}
		obj.wintitle.removeNode(true) ;
		obj.removeNode(true) ;
		obj = null;

	}

	function removeall()        //�Ƴ����д���
	{
		var wincount = this.winlist.length;
		for(var i=wincount-1;i>=0;i--)
			this.removewin(this.winlist[i]);
	}
}

function tabScroll(direction)
{
	tabScrollStop();
	direction == "right" ? tabMoveRight() : tabMoveLeft();
}

function tabMoveRight()
{
	tabMove("right",8);
	timer=setTimeout(tabMoveRight,10);
}

function tabMoveLeft()
{
	tabMove("left",8);
	timer=setTimeout(tabMoveLeft,10);
}

function tabScrollStop()
{
	clearTimeout(timer);
	timer = null;
}

function tabMove(direction,speed)
{
	var mleft = parseInt(titlelist.style.marginLeft);
	if (isNaN(mleft))
		mleft = 0;
	if(direction=="right")
	{
		if(titlelist.parentElement.clientWidth >= titlelist.parentElement.scrollWidth)
		{
			tabScrollStop();
			return;
		}
		else
		{
			titlelist.style.marginLeft = mleft - speed;
		}
	}
	else
	{
		if(mleft + speed >=0)
		{
			titlelist.style.marginLeft = 0;
			tabScrollStop();
			return;
		}
		else
		{
			titlelist.style.marginLeft = mleft + speed;
		}
	}
}
var timer = null;
var win = null;
var wins = new Array();

function init()
{
	win =  new mywin();                        //�½�����
}

function AddWin(Url,Title)
{
	wins[wins.length] = win.addwin(Url, Title);                        //��Ӵ��壻
}

function subStr(str,len)
{
	var strlength=0;
	var newstr = "";
	for (var i=0;i<str.length;i++)
	{
		if(str.charCodeAt(i)>=1000)
			strlength += 2;
		else
			strlength += 1;
		if(strlength > len)
		{
			newstr += "...";
			break;
		}
		else
		{
			newstr += str.substr(i,1);
		}
	}
	return newstr;
}

