

(function(){
    var parent = window.parent;
    //dialog����
    dialog = parent.$EDITORUI[window.frameElement.id.replace(/_iframe$/, '')];
    //��ǰ��dialog�ı༭��ʵ��
    editor = dialog.editor;

    UE = parent.UE;

    domUtils = UE.dom.domUtils;

    utils = UE.utils;

    browser = UE.browser;

    ajax = UE.ajax;

    $G = function(id){return document.getElementById(id)};
    //focusԪ��
    $focus = function(node){
        setTimeout(function(){
            if(browser.ie){
                var r = node.createTextRange();
                r.collapse(false);
                r.select();
            }else{
                node.focus()
            }
        },0)
    }

})();

