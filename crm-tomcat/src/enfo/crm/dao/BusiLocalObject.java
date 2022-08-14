package enfo.crm.dao;

/**
 * <p>Title: Business Bean Interface</p>
 * <p>Description: Session Bean interface for Business object</p>
 * <p>Copyright: Copyright (c) Singlee Software 2003</p>
 * <p>Company: Singlee Software</p>
 * @author <a href="mailto:zhou_bin@singlee.com">Zhou bin</a>
 * @version 1.0
 */


public interface BusiLocalObject
{
    public String getVersion();

    public void list() throws Exception;
    public void listBySql(String sql) throws Exception;

    public boolean getNext() throws Exception;

    public void load() throws Exception;
    public void append() throws Exception;
    public void save() throws Exception;
    public void delete() throws Exception;
    public void deleteAll() throws Exception;

    public void gotoPage(int page) throws Exception;
    public void gotoPage(String page) throws Exception;
    public void gotoPage(String page, String pagesize) throws Exception;
    public int getRows() throws Exception;
    public int getCurrentPage();
    public void setPageSize(int pageSize);
    public int getPageSize();
    public int getPageCount();  
    public String getPageLink(String strPageName);
    public String getPageLink(String strPageName, String language); //20101103 dingyj 通过语言来显示不同的分页效果
    /**
     *author 蒋娅萍
     *在同一页面上需要同时出现多个分页标签时用这个方法
     **/
    public String getSecondPageLink(String strPageName,String pageArgu,String pagesizeArgu);
	public String  getPageLinkModal(String strPageName);
	public String getPageLinkEx(String strPageName);
    public void setOperator(Integer operator);
    public Integer getOperator();

    public Integer getBook();
    public void setBook(Integer book);
    
    public int getRowCount();
	public String getTotalUp(String columnName) throws Exception;
	public void addLog(Integer busi_flag, String busi_name, String summary)throws java.lang.Exception;
}