/**
 * 
 */
package cn.zeppin.entity;

/**
 * @author sj
 * 
 */
public class TrainingStudentSub
{
    private ProjectApply projectApply;
    private String assignNum;
    private String registNum;
    private String abnormalNum;
    private String status;

    /**
     * @return the projectApply
     */
    public ProjectApply getProjectApply()
    {
	return projectApply;
    }

    /**
     * @param projectApply
     *            the projectApply to set
     */
    public void setProjectApply(ProjectApply projectApply)
    {
	this.projectApply = projectApply;
    }

    /**
     * @return the assignNum
     */
    public String getAssignNum()
    {
	return assignNum;
    }

    /**
     * @param assignNum
     *            the assignNum to set
     */
    public void setAssignNum(String assignNum)
    {
	this.assignNum = assignNum;
    }

    /**
     * @return the registNum
     */
    public String getRegistNum()
    {
	return registNum;
    }

    /**
     * @param registNum
     *            the registNum to set
     */
    public void setRegistNum(String registNum)
    {
	this.registNum = registNum;
    }

    /**
     * @return the abnormalNum
     */
    public String getAbnormalNum()
    {
	return abnormalNum;
    }

    /**
     * @param abnormalNum
     *            the abnormalNum to set
     */
    public void setAbnormalNum(String abnormalNum)
    {
	this.abnormalNum = abnormalNum;
    }

    /**
     * @return the status
     */
    public String getStatus()
    {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status)
    {
	this.status = status;
    }
}
