function initZoomX(o,c,delay){
  //////////
  //by mozart0, 2007.05.31
  //////////
  var steps=12,intv=20;
  var state="hide",tobe="",timer;
  var tw,th;
  var z;
  //////////
  c.style.display="block";
  tw=c.offsetWidth,th=c.offsetHeight;
  c.style.display="none";
  z=document.createElement("div");
  with(z.style){
    position="absolute";
    zIndex="965";
    margin="0px";
    padding="0px";
    overflow="hidden";
    backgroundColor=(c.currentStyle||window.getComputedStyle(c,null)).backgroundColor;
    }
  z.appendChild(c.parentNode.removeChild(c));
  document.body.appendChild(z);
  setRect(z,[-20,-20,10,10]);
  //////////
  addEvent(o,"mouseover",doover);
  addEvent(c,"mouseover",doover);
  addEvent(o,"mouseout",doout);
  addEvent(c,"mouseout",doout);
  //////////
  function doover(){
    tobe="show";
    setTimeout(function(){
      if(state=="hide"&&tobe=="show"){
        state="showing";
        ani(true);
        }
      },delay);
    }
  //////////
  function doout(){
    tobe="hide";
    setTimeout(function(){
      if(state=="show"&&tobe=="hide"){
        state="hidding";
        ani(false);
        }
      },delay);
    }
  //////////
  function ani(bshow){
    var f=bshow?0:steps-1;
    var t=bshow?steps-1:0;
    var x=bshow?1:-1;
    var rects=build(getRect(o),tw,th);
    alpha(z,50);
    for(var i=f,k=0;i!=t+x;i+=x)(function(){
      var j=i;
      setTimeout(function(){
        setRect(z,rects[j]);
        if(j==f&&!bshow)
          c.style.display="none";
        else if(j==t){
          if(bshow){
            c.style.display="block";
            alpha(z,100);
            state="show";
            if(tobe=="hide")
              doout();
            }
          else{
            setTimeout(function(){
              setRect(z,[-20,-20,10,10]);
              state="hide";
              if(tobe=="show")
                doover();
              },intv);
            }
          }
        },(++k)*intv);
      })();
    }
  //////////
  function build(r,w,h){
    var ret=[];
    var d=[r[0]+(r[2]-w)/2,r[1]+r[3]+5,w,h];
    if(d[0]<0)
      c[0]=r[0];
    for(var i=0;i<steps-1;i++){
      var t=[];
      for(var j=0;j<4;j++)
        t[j]=r[j]+(d[j]-r[j])*i/(steps-1);
      ret.push(t);
      }
    ret.push(d);
    return ret;
    }
  //////////
  function alpha(ele,n){
    if(ele.filters)
      ele.style.filter="alpha(opacity="+n+")";
    else
      ele.style.opacity=n/100;
    }
  }

function $(sid){
  return document.getElementById(sid);
  }

function addEvent(obj,evt,func){
  if(obj.attachEvent)
    obj.attachEvent("on"+evt,func);
  else if(obj.addEventListener)
    obj.addEventListener(evt,func,false);
  else
    return false;
  return true;
  }

var _firefox=navigator.userAgent.indexOf("Firefox")>=0;
var _opera=navigator.userAgent.indexOf("Opera")>=0;
var _ie=!_firefox&&!_opera;

function getRect(obj){
  var left=0,top=0;
  var width=obj.offsetWidth,height=obj.offsetHeight;
  var op=obj,st;
  var abs=false;
  if(_firefox)
    while((op=op.parentNode).tagName!="HTML"){
      var st=getComputedStyle(op,null);
      if(st.MozBoxSizing!="border-box"){
        left+=parseInt(st.borderLeftWidth);
        top+=parseInt(st.borderTopWidth);
        }
      }
  while(true){
    left+=obj.offsetLeft;
    top+=obj.offsetTop;
    if(!(op=obj.offsetParent))
      break;
    if(_ie){
      left+=op.clientLeft;
      top+=op.clientTop;
      if(!abs&&obj.currentStyle.position=="absolute")
        abs=true;
      }
    obj=op;
    }
  if(_ie){
    if(!document.documentElement.clientWidth){
      var t=document.body.currentStyle;
      var lx=parseInt(t.borderLeftWidth);
      var tx=parseInt(t.borderTopWidth);
      left-=isNaN(lx)?2:lx;
      top-=isNaN(tx)?2:tx;
      }
    else if(abs){
      left-=2;
      top-=2;
      }
    }
  return [left,top,width,height];
  }

function setRect(obj,rect){
  with(obj.style){
    left=Math.round(rect[0])+"px";
    top=Math.round(rect[1])+"px";
    width=Math.round(rect[2])+"px";
    height=Math.round(rect[3])+"px";
    }
  }

addEvent(window,"load",function(){
  for(var i=0,m=document.getElementsByTagName("*");i<m.length;i++){
  	var urn=m[i].getAttribute("urn");
    if(urn&&/^zoomx#.+/.test(urn)){
      var a=urn.split("#");
      var target=$(a[1]),delay=a[2]||200;
      if(target)
        initZoomX(m[i],target,parseInt(delay));
      }
    }
   });