package cn.zeppin.authority;

public class AuthorityInfo
{
    int[] user;
    int[] userGroup;
    int[] denyUser;
    String errMsg;

    public AuthorityInfo(int[] user, int[] userGroup, int[] denyUser, String errMsg)
    {
    	this.user = user;
    	this.userGroup = userGroup;
    	this.denyUser = denyUser;
    	this.errMsg = errMsg;
    }
}
