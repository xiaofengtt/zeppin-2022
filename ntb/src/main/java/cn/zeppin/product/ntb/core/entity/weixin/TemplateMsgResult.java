package cn.zeppin.product.ntb.core.entity.weixin;

/**
 * <p>Title:TemplateMsgResult</p>
 * <p>Description:模板消息 返回结果</p>
 * @author geng
 * @date 2017年8月4日 下午3:59:54
 */
public class TemplateMsgResult extends ResultState {  
    /** 
     *  
     */  
    private static final long serialVersionUID = 3198012785950215862L;  
  
    private String msgid; // 消息id(发送模板消息)  
  
    public String getMsgid() {  
        return msgid;  
    }  
  
    public void setMsgid(String msgid) {  
        this.msgid = msgid;  
    }  
}  