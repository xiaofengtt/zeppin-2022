package cn.zeppin.product.utility.weixin;

import java.io.Serializable;

/**
 * <p>Title:ResultState</p>
 * <p>Description:微信API返回状态 </p>
 * @author geng
 * @date 2017年8月4日 下午3:59:03
 */
public class ResultState implements Serializable {  
	  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1692432930341768342L;  
  
    //@SerializedName("errcode")  
    private int errcode; // 状态  
      
    //@SerializedName("errmsg")  
    private String errmsg; //信息  
  
    public int getErrcode() {  
        return errcode;  
    }  
  
    public void setErrcode(int errcode) {  
        this.errcode = errcode;  
    }  
  
    public String getErrmsg() {  
        return errmsg;  
    }  
  
    public void setErrmsg(String errmsg) {  
        this.errmsg = errmsg;  
    }  
}  