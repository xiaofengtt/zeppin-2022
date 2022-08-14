/**
 *  ueditor����������
 *  �������������������༭��������
 */

/**************************��ʾ********************************
 * ���б�ע�͵��������Ĭ��ֵ����ע�����߸���ֵ����������ʾĬ��ֵ�Ƕ��٣��������ģ���ȥ��ע�ͣ��޸�Ĭ��ֵ��������ʵ�����༭��ʱ������Ӧ��ֵ���ͻḲ��Ĭ�ϵ�����ֵ
 * ÿ�������༭��ʱ������ֱ������ĵ�editor_config.jsֱ�Ӹ����°汾��editor_config.js,�����õ��Ļ���ִ�����ʾ
 **************************��ʾ********************************/


(function () {
    /**
     * �༭����Դ�ļ���·����������ʾ�ĺ����ǣ��Ա༭��ʵ����ҳ��Ϊ��ǰ·����ָ��༭����Դ�ļ�����dialog���ļ��У���·����
     * ���ںܶ�ͬѧ��ʹ�ñ༭����ʱ����ֵ�����·�����⣬�˴�ǿ�ҽ�����ʹ��"�������վ��Ŀ¼�����·��"�������á�
     * "�������վ��Ŀ¼�����·��"Ҳ������б�ܿ�ͷ������"/myProject/ueditor/"������·����
     * ���վ�����ж������ͬһ�㼶��ҳ����Ҫʵ�����༭������������ͬһUEditor��ʱ�򣬴˴���URL���ܲ�������ÿ��ҳ��ı༭����
     * ��ˣ�UEditor�ṩ����Բ�ͬҳ��ı༭���ɵ������õĸ�·����������˵������Ҫʵ�����༭����ҳ�����д�����´��뼴�ɡ���Ȼ����Ҫ��˴���URL���ڶ�Ӧ�����á�
     * window.UEDITOR_HOME_URL = "/xxxx/xxxx/";
     */
    var URL;

    /**
     * �˴�����д��������UEditorС���Ա����ʹ�ã��ⲿ�����û��밴������˵����ʽ���ü��ɣ����鱣���������У��Լ��ݿ��ھ���ÿ��ҳ������window.UEDITOR_HOME_URL�Ĺ��ܡ�
     */
    var tmp = window.location.pathname;
        URL = window.UEDITOR_HOME_URL||tmp.substr(0,tmp.lastIndexOf("\/")+1).replace("_examples/","").replace("website/","");//������������ó�ueditorĿ¼������վ�����·�����߾���·����ָ��http��ͷ�ľ���·����

    /**
     * ���������塣ע�⣬�˴������漰��·�������ñ���©URL������
     */
    window.UEDITOR_CONFIG = {

        //Ϊ�༭��ʵ�����һ��·����������ܱ�ע��
        UEDITOR_HOME_URL : URL

        //�������ϵ����еĹ��ܰ�ť�������򣬿�����new�༭����ʵ��ʱѡ���Լ���Ҫ�Ĵ��¶���
        ,toolbars:[
            ['Bold', 'Italic', 'Underline', 'StrikeThrough', 'RemoveFormat', 'ForeColor', 'FontFamily', 'FontSize', 'RowSpacingTop', 'RowSpacingBottom', 'LineHeight', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyJustify', 'Indent', 'InsertTable', 'DeleteTable', 'InsertParagraphBeforeTable', 'InsertRow', 'DeleteRow', 'InsertCol', 'DeleteCol', 'MergeCells', 'MergeRight', 'MergeDown', 'SplittoCells', 'SplittoRows', 'SplittoCols', 'Undo', 'Redo', 'Preview', 'Source','FullScreen', 'PrintElement']
        ]
        //�������ڹ�������ʱ��ʾ��tooltip��ʾ
        ,labelMap:{
            'bold':'�Ӵ�','italic':'б��','underline':'�»���','strikethrough':'ɾ����','removeformat':'�����ʽ','forecolor':'������ɫ','fontfamily':'����','fontsize':'�ֺ�','rowspacingtop':'��ǰ���','rowspacingbottom':'�κ���','lineheight':'�м��','justifyleft':'�������','justifycenter':'���ж���','justifyright':'���Ҷ���','justifyjustify':'���˶���','indent':'��������','inserttable':'������','deletetable':'ɾ�����','insertparagraphbeforetable':'����Ϸ�������','insertrow':'������','deleterow':'ɾ����','insertcol':'������','deletecol':'ɾ����','mergecells':'�ϲ���Ԫ��','mergeright':'���Һϲ���Ԫ��','mergedown':'���ºϲ���Ԫ��','splittocells':'��ȫ��ֵ�Ԫ��','splittorows':'��ֳ���','splittocols':'��ֳ���','undo':'����','redo':'����','preview':'Ԥ��','source':'�л�Դ��','fullscreen':'ȫ��','printelement':'Ҫ����'
        }

        //����������Ŀ
        //,isShow : true    //Ĭ����ʾ�༭��

        //,initialContent:'��ӭʹ��ueditor!'    //��ʼ���༭��������,Ҳ����ͨ��textarea/script��ֵ������������

        //,autoClearinitialContent:false //�Ƿ��Զ�����༭����ʼ���ݣ�ע�⣺���focus��������Ϊtrue,���ҲΪ�棬��ô�༭��һ�����ͻᴥ�����³�ʼ�������ݿ�������

        //,iframeCssUrl: URL + '/themes/default/iframe.css' //���༭���ڲ�����һ��css�ļ�

        //,textarea:'editorValue' // �ύ��ʱ����������ȡ�༭���ύ���ݵ����õĲ�������ʵ��ʱ���Ը�����name���ԣ��Ὣname������ֵ��Ϊÿ��ʵ���ļ�ֵ������ÿ��ʵ������ʱ���������ֵ

        //,focus:true //��ʼ��ʱ���Ƿ��ñ༭����ý���true��false

        //,minFrameHeight:320  // ��С�߶�,Ĭ��320

        //,autoClearEmptyNode : true //getContentʱ���Ƿ�ɾ���յ�inlineElement�ڵ㣨����Ƕ�׵������

        //,fullscreen : false //�༭����ʼ��������,�༭�����Ƿ���ֻ���ģ�Ĭ����false

        //,readonly : false //�༭���㼶�Ļ���,Ĭ����999

        //,zIndex : 999 //ͼƬ�����ĸ��㿪�أ�Ĭ�ϴ�

        //,imagePopup:true

        ,initialStyle:'body{font-size:18px}'   //�༭���ڲ���ʽ,���������ı������

        //,emotionLocalization:false //�Ƿ������鱾�ػ���Ĭ�Ϲرա���Ҫ������ȷ��emotion�ļ����°��������ṩ��images�����ļ���

        ,enterTag:'p' //�༭���س���ǩ��p��br

        //,pasteplain:false  //�Ƿ��ı�ճ����falseΪ��ʹ�ô��ı�ճ����trueΪʹ�ô��ı�ճ��

        //iframeUrlMap
        //dialog���ݵ�·�� ���ᱻ�滻��URL,������һ���򿪣����������е�dialog��Ĭ��·��
        //,iframeUrlMap:{
        // 'anchor':'~/dialogs/anchor/anchor.html',
        // }

		//tab
        //���tab��ʱ�ƶ��ľ���,tabSize������tabNodeʲô�ַ���Ϊ��λ
        //,tabSize:4
        //,tabNode:'&nbsp;'

		//removeFormat
        //�����ʽʱ����ɾ���ı�ǩ������
        //removeForamtTags��ǩ
        //,removeFormatTags:'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var'
        //removeFormatAttributes����
        //,removeFormatAttributes:'class,style,lang,width,height,align,hspace,valign'
        
		//indent
        //������������,Ĭ����2em
        //,indentValue:'2em'
        
		//undo
         //���������˵Ĵ���,Ĭ��20
         //,maxUndoCount:20
         //��������ַ���������ֵʱ������һ���ֳ�
         //,maxInputCount:20
        
		//source
        //Դ��Ĳ鿴��ʽ,codemirror �Ǵ��������textarea���ı���,Ĭ����codemirror
        //,sourceEditor:"codemirror"
        //���sourceEditor��codemirror����������һ����������
        //codeMirrorJsUrl js���ص�·����Ĭ���� URL + "third-party/codemirror2.15/codemirror.js"
        //,codeMirrorJsUrl:URL + "third-party/codemirror2.15/codemirror.js"
        //codeMirrorCssUrl css���ص�·����Ĭ���� URL + "third-party/codemirror2.15/codemirror.css"
        //,codeMirrorCssUrl:URL + "third-party/codemirror2.15/codemirror.css"
        
		//autoHeight
        // �Ƿ��Զ�����,Ĭ��true
        ,autoHeightEnabled:true
        
		//autoFloat
        //�Ƿ񱣳�toolbar��λ�ò���,Ĭ��true
        ,autoFloatEnabled:true
        
		//fontfamily
        //����
        ,'fontfamily': [['����',['����', 'SimSun']],
                        ['����',['����', '����_GB2312', 'SimKai']],
                        ['����',['����', 'SimHei']],
                        ['����',['����', 'SimLi']],
                        ['andale mono',['andale mono']],
                        ['arial',['arial', 'helvetica', 'sans-serif']],
                        ['arial black',['arial black', 'avant garde']],
                        ['comic sans ms',['comic sans ms']],
                        ['impact',['impact', 'chicago']],
                        ['times new roman',['times new roman']]
                       ]
        
		//fontsize
        //�ֺ�
        ,'fontsize': ['10','12','14','16','18','20','22','24','26','28']
        
		//rowspacingtop
        //�μ�� ֵ����ʾ��������ͬ
        ,'rowspacingtop':['5']
        
		//rowspacingBottom
        //�μ�� ֵ����ʾ��������ͬ
        ,'rowspacingbottom':['5']
        
		//lineheight
        //���ڼ�� ֵ����ʾ��������ͬ
        ,'lineheight': ['1']
        //printelement
        //��ӡԪ��
        ,'printelement:':[]
        
    };
})();
