package enfo.crm.dao;

public interface IBusiFullLocal {
	
	public void remove();
	
	public String getVersion();

	public void list() throws Exception;

	public void listBySql(String paramString) throws Exception;

	public boolean getNext() throws Exception;

	public void load() throws Exception;

	public void append() throws Exception;

	public void save() throws Exception;

	public void delete() throws Exception;

	public void deleteAll() throws Exception;

	public void gotoPage(int paramInt) throws Exception;

	public void gotoPage(String paramString) throws Exception;

	public void gotoPage(String paramString1, String paramString2) throws Exception;

	public int getRows() throws Exception;

	public int getCurrentPage();

	public void setPageSize(int paramInt);

	public int getPageSize();

	public int getPageCount();

	public String getPageLink(String paramString);

	public String getPageLink(String paramString1, String paramString2);

	public String getSecondPageLink(String paramString1, String paramString2, String paramString3);

	public String getPageLinkModal(String paramString);

	public String getPageLinkEx(String paramString);

	public void setOperator(Integer paramInteger);

	public Integer getOperator();

	public Integer getBook();

	public void setBook(Integer paramInteger);

	public int getRowCount();

	public String getTotalUp(String paramString) throws Exception;

	public void addLog(Integer paramInteger, String paramString1, String paramString2) throws Exception;
}
