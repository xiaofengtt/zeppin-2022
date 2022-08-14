//Start initialization portlet layout;
function resizeTableInquiry(){
    j$(".tableInquiry").width(j$(".tableInquiry").parent().width()-5)
    j$(".tableInquiry").find("td").children(":input").hide();
    j$(".tableInquiry").find("td").children(":input").each(function(i,input){
        if(j$(input).hasClass("TI-dept")){
            j$(input).width(j$(input).parent().width()-240);
        }else{
            j$(input).width(j$(input).parent().width()-40);
        }
    });
    j$(".tableInquiry").find("td").children(":input").show();
}
j$(document).ready(function(){
    if(j$("#container").length==0){return}
    var myLayoutInner;
    var myLayoutOuter;
    function reSizeConta(){
        var j$Container  = j$('#container')
        ,j$Pane  = j$('.centerWrap')
        ,j$PaneW = j$('.ui-layout-west')
        ,j$PaneE = j$('.ui-layout-east')
        ,j$PaneCC= j$Pane.children()
        ,j$PaneWC= j$PaneW.children()
        ,j$PaneEC= j$PaneE.children()
        ,j$conChi=j$('#container').children()
        ,CH=0,WH=0,EH=0;
        j$(j$PaneCC).each(function(i,pcc){CH=CH+j$(pcc).outerHeight(true,true);});
        j$(j$PaneWC).each(function(i,pwc){WH=WH+j$(pwc).outerHeight(true,true);});
        j$(j$PaneEC).each(function(i,pec){EH=EH+j$(pec).outerHeight(true,true);});
        var allPH=[CH,WH,EH]
        var MaxPH=Math.max.apply(null,allPH)+20;
        if(MaxPH<j$(document).height()){MaxPH=j$(document).height()}
        j$Container.height(MaxPH);
        //myLayoutInner.allowOverflow("west");
        myLayoutInner.resizeAll();
        //IntTableList();
        //j$.timer.set("scrollbar",function(){IntTableList()},500);
        //j$.timer.set("scrollbar",function(){j$(".myShortcut").mCustomScrollbar("update");},500)
    }
    
    /*
    function reSizeConta2(){
        var j$Container  = j$('#container')
        ,j$Pane  = j$('.centerWrap')
        ,j$PaneW = j$('.ui-layout-west')
        ,j$PaneE = j$('.ui-layout-east')
        ,CH= j$Pane[0].scrollHeight
        ,WH= j$PaneW[0].scrollHeight
        ,EH= j$PaneE[0].scrollHeight
        j$Container.height(0);
        j$Pane.height(0);
        j$PaneW.height(0);
        j$PaneE.height(0);
        //,j$conChi=j$('#container').children();
        //var CH=20;j$(j$PaneCC).each(function(i,pcc){CH=CH+j$(pcc).outerHeight(true,true);});
        //var WH=20;j$(j$PaneWC).each(function(i,pwc){WH=WH+j$(pwc).outerHeight(true,true);});
        //var EH=20;j$(j$PaneEC).each(function(i,pec){EH=EH+j$(pec).outerHeight(true,true);});
        //alert(CH+" "+j$Pane[0].scrollHeight)
        var allPH=[CH,WH,EH]
        var MaxPH=Math.max.apply(null,allPH)+20;
        if(MaxPH<j$(document).height()){MaxPH=j$(document).height()}
        j$Container.height(MaxPH);
        myLayoutInner.resizeAll();
    }
    */
    
    j$( ".column").sortable({
        distance:10
        ,connectWith: ".column"
        ,handle: ".portlet-header"
        //,placeholder: 'widget-placeholder'
        ,stop:function(){reSizeConta();}
        ,helper:function (evt,ui) { return j$(ui).clone().appendTo('body').show(); }
        //,distance:1
        //,dropOnEmpty:true
        ,tolerance:'pointer'
        /*, over:           function (evt, ui) {
                            var
                                j$target_UL  = j$(ui.placeholder).parent()
                            ,   targetWidth = j$target_UL.width()
                            ,   helperWidth = ui.helper.width()
                            ,   padding     = parseInt( ui.helper.css('paddingLeft') )
                                            + parseInt( ui.helper.css('paddingRight') )
                                            + parseInt( ui.helper.css('borderLeftWidth') )
                                            + parseInt( ui.helper.css('borderRightWidth') )
                            ;
                            //if (( (helperWidth + padding) - targetWidth ) > 20)
                                ui.helper
                                    .height('auto')
                                    .width( targetWidth - padding )
                                ;
                        }*/
    });
    // j$( ".column" ).disableSelection();
    j$(".portlet")
        .each(function(i,plt){
            if(j$(plt).find(".tabs").size()==0){
                 j$(plt).find(".portlet-header").wrapInner("<span class='desc'/>");
            }
        })
        .addClass( " ui-widget-content " )
        .find(".portlet-header")
            .addClass( "ui-widget-header ui-corner-all" )
            .prepend( "<span class='icon minus'/><span class='icon more'/>")
            .end()
        .find( ".portlet-content" );
    
    
    j$( ".portlet").hover(function(){
        j$(this).find(".more").fadeIn(j$.ms(300)).css({left:function(){
            if(j$(this).css("left")!="auto"){return;};
            var desc=j$(this).parent().find(".desc");
            if(desc.size()==1){
                return desc.width()+20;
            }else{
                var j$tabs=j$(this).parent().find(".ui-tabs-nav").children(),
                    widthPlus=0;
                j$tabs.each(function(i,tabs){
                    widthPlus+=j$(tabs).outerWidth(true,true)
                })    
                return widthPlus+20;
            }
            //alert(j$(this).parent().attr("class"))
            
        }});
    },function(){
        j$(this).find(".more").fadeOut(j$.ms(300));
    });
    j$( ".portlet-header .icon" ).hover(function(){
        j$(this).addClass('hover');
    },function(){
        j$(this).removeClass("hover");
    });
    
    j$( ".portlet-header .minus" ).click(function(){
        j$( this ).toggleClass( "minus" ).toggleClass( "plus" );
        j$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle(0,function(){reSizeConta();});
        if(j$(this).hasClass("minus")){j$(this).parent().css({"border-radius":"5px 5px 0 0 ","box-shadow":"0 0 8px #ddd"});}
        //else{j$(this).parent().css({"border-radius":"5px","box-shadow":"none"});}
    });
    
    var j$Container = j$('#container')
    myLayoutInner = j$('#container').layout({
        west__size:"28%"
        ,fxName:"none"
        /* ,togglerLength_open:"80"*/
        ,east__size:"28%"
        ,closable:true
        ,spacing_open:6
        ,spacing_closed:8
        ,togglerTip_open:"\u6536\u8D77"
        ,togglerTip_closed:"\u5C55\u5F00"
        ,resizerTip:"\u8C03\u6574\u5927\u5C0F"
        ,sliderTip:"\u6ED1\u51FA"
        ,minSize:"20%"
        ,maxSize:"50%"
        ,togglerAlign_open:270
        ,togglerAlign_closed:270
        //,west__paneSelector:".ui-layout-east"
    });
    myLayoutOuter = j$("body").layout({fxName:"none"});
    myLayoutInner.options.center.onresize=function(){resizeTableInquiry();}
    myLayoutOuter.options.center.onresize=function(){reSizeConta();}
    reSizeConta();
    j$(".portlet-scroll").mCustomScrollbar({
          advanced:{
            updateOnBrowserResize:false,
            updateOnContentResize:true,
            autoExpandHorizontalScroll:false
          },
          callbacks:{
           //onScroll:function(){},
           // onTotalScroll:function(){alert(123)},
           // onTotalScrollOffset:0
          }
    });
    j$(".tableInquiry").formly();
    j$( "#radio" ).buttonset();
    j$(".TI-chooseBtn,.TI-submitBtn").button();
    resizeTableInquiry();
});
//End initialization portlet layout;
