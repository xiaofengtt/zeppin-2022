// version:0.2
// date:2008/11/5
// http://hi.baidu.com/ycsy520
var showDialog = function(){
    var iframebg,overlay,wrap__,dialogId,xScroll,yScroll,iScrollTop,wWidth,wHeight;
    return {
        show:function(idOsrc,iWidth,iHeight,scrolling){
            if(!document.createElement){return false;}

            xScroll = showDialog.pageWidth();
    
            yScroll = showDialog.pageHeight();
            showDialog.showOverlay();
            if(showDialog.isIE()){showDialog.createIfra();}
            if((/([a-z0-9-]+\.)+[a-z0-9]{2,4}.*$/.test(idOsrc))){
                var inserStr = "<iframe src='"+idOsrc+"' width = '"+iWidth+"' height = '"+iHeight+"' scrolling = '"+scrolling+"' frameborder = '0'></iframe>";
                wrap__ = document.createElement('div');
                wrap__.setAttribute('id','wrap__');
                overlay.parentNode.insertBefore(wrap__,overlay);
                wrap__.innerHTML = inserStr;
                wrap__.style.width = iWidth+"px";
                wrap__.style.height = iHeight +"px";
                showDialog.posWindow(wrap__);
            }else if(id__(idOsrc)){
                dialogId = id__(idOsrc);
                dialogId.style.display="block";
                showDialog.posWindow(dialogId);
            }else{return false;}
        },
        showOverlay:function(){
            var body = document.body;
            showDialog.setStyle(body,{
                padding:'0px',
                margin:'0px',
    
                overflowX:'hidden'
            });
            overlay = document.createElement('div');
            overlay.setAttribute('id','overlay__');
            wWidth = showDialog.windowWidth();
            wHeight = showDialog.windowHeight();
            showDialog.setStyle(overlay,{
                width : (xScroll>wWidth ? xScroll : wWidth)+ 'px',
                height : (yScroll>wHeight ? yScroll : wHeight)+ 'px',
                backgroundColor:'#000000',
                position:'absolute',
                top:'0px',
                left:'0px',
                zIndex:'998'
            });
            showDialog.setOpacity(overlay,70);
            return body.appendChild(overlay);
        },
        setOpacity:function(ele,level){
            if(showDialog.isIE()){
                ele.style.filter = 'Alpha(opacity=' + level + ')';
            }else{
                ele.style.opacity = level/100;
            }
        },
        createIfra:function(){
            if(!document.createElement("iframe")){return false;};
            try{
            var iframebg = document.createElement("iframe");
            iframebg.setAttribute("id","ifrbg__");
            iframebg.setAttribute("src","javascript:void(0);");
            iframebg.setAttribute("scrolling","no");
            iframebg.setAttribute("frameborder","0");
            iframebg.setAttribute("allowtransparency","true");
            showDialog.insertAfter(iframebg,overlay);
            showDialog.setStyle(iframebg,{
                height : overlay.style.height,
                width : overlay.style.width,
                backgroundColor:'#FFFFFF',
                filter:'Alpha(opacity=0)',
                zIndex:'997',
                top:'0px',
                left:'0px',
                position:'absolute'
            });
            iframebg.style.backgroundColor="transparency";
            }catch(e){}
        },
        hide:function(obj){
            try{
                var body = document.body;

                body.style.overflowX = 'auto';
                if(id__(obj)){id__(obj).style.display='none';}
                if(overlay){body.removeChild(overlay)}
                if(showDialog.isIE() && id__('ifrbg__')){body.removeChild(id__('ifrbg__'));iframebg=null}
                if(wrap__){body.removeChild(wrap__);}
                overlay = null;wrap__=null;dialogId= null;xScroll=null;yScroll=null;iScrollTop=null;wWidth=null;wHeight=null;
            }catch(e){}
        },
        setStyle:function(ele,styles){
            for(var i in styles){
                ele.style[i] = styles[i];
            }
        },
        adjust:function(){
            wHeight = showDialog.windowHeight();
            xScroll = showDialog.pageWidth();
            yScroll = showDialog.pageHeight();
            overlay = id__('overlay__');
            iframebg = id__('ifrbg__');
            overlay.style.height =(yScroll>wHeight ? yScroll : wHeight)+ 'px';
            overlay.style.width =xScroll +'px';
            if(dialogId){showDialog.posWindow(dialogId);}
            if(wrap__){showDialog.posWindow(wrap__);}
            if(iframebg){
                iframebg.style.height = overlay.style.height;
                iframebg.style.width = overlay.style.width;
            }
        },
        posWindow:function(obj){
                var __obj = id__(obj) || obj;
                try{

                    iScrollTop = document.body.scrollTop||document.documentElement.scrollTop;
                    wWidth = showDialog.windowWidth(),wHeight = showDialog.windowHeight();
                    if(showDialog.isIE()){
                        yScroll = showDialog.pageHeight();
                    }
                    __obj.style.position="absolute";
                    __obj.style.zIndex = "999";
                    __obj.style.backgroundColor="#FFFFFF";
                    __obj.style.marginLeft = "-" + __obj.clientWidth/2 + "px";
                    __obj.style.marginTop = "-" + __obj.clientHeight/2 + "px";
                    __obj.style.left = wWidth/2 +"px";
                    __obj.style.top = wHeight/2 + iScrollTop + "px";
                    __obj = null;
                }catch(e){};
        },

        pageHeight:function(){
            if (window.innerHeight && window.scrollMaxY) {yScroll = window.innerHeight + window.scrollMaxY;} else if (document.body.scrollHeight > document.body.offsetHeight){yScroll = document.body.scrollHeight;} else {yScroll = document.body.offsetHeight;} 
            return yScroll;        
        },
        pageWidth:function(){
            if (window.innerWidth && window.scrollMaxX) {xScroll = window.innerWidth + window.scrollMaxX;} else if (document.body.scrollWidth > document.body.offsetWidth){xScroll = document.body.scrollWidth;} else {xScroll = document.body.offsetWidth;} 
            return xScroll;        
        },
        windowHeight:function(){
            return (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;
        },
        windowWidth:function(){
            return (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.offsetWidth;
        },
        isIE:function(){
            var sUserAgent = navigator.userAgent;
        return sUserAgent.indexOf("compatible")>-1 && sUserAgent.indexOf("MSIE")>-1 && !(sUserAgent.indexOf("Opera")>-1);
        },
        insertAfter:function(nele,tele) {
            var p = tele.parentNode;
            if (p.lastChild == tele) {
                p.appendChild(nele);
            }else {
                p.insertBefore(nele,tele.nextSibling);
            }
        }
    };
}();
function id__(obj){return document.getElementById(obj);}
//window.onresize = window.onscroll = function(){setTimeout(function(){if(id__('overlay__')){try{showDialog.adjust();}catch(e){};}},100);}
(function(){setInterval(function(){if(id__('overlay__')){try{showDialog.adjust();}catch(e){};}},10);})();