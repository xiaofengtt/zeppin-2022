/**
 * 
 */
package cn.zeppin.entity;

/**
 * @author sj
 * 
 */
public class teachingSubjectEx implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7785521095299435472L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private String id;
    private String name;
    private String searchString;// 模糊检索

    /**
     * @return the id
     */
    public String getId()
    {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
	this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
	this.name = name;
    }

    /**
     * @return the searchString
     */
    public String getSearchString()
    {
	return searchString;
    }

    /**
     * @param searchString
     *            the searchString to set
     */
    public void setSearchString(String searchString)
    {
	this.searchString = searchString;
    }

}
