/**
 * 
 */
package cn.zeppin.entity;

/**
 * @author sj
 * 
 */
public class fileInfo implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2259329193352323276L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private String fileName;
    private String filePath;
    private String fileGuid;
    private String fileExt;
    private int fileSize;
    EDocumentType fileType;

    /**
     * @return the fileName
     */
    public String getFileName()
    {
	return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName)
    {
	this.fileName = fileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath()
    {
	return filePath;
    }

    /**
     * @param filePath
     *            the filePath to set
     */
    public void setFilePath(String filePath)
    {
	this.filePath = filePath;
    }

    /**
     * @return the fileGuid
     */
    public String getFileGuid()
    {
	return fileGuid;
    }

    /**
     * @param fileGuid
     *            the fileGuid to set
     */
    public void setFileGuid(String fileGuid)
    {
	this.fileGuid = fileGuid;
    }

    /**
     * @return the fileExt
     */
    public String getFileExt()
    {
	return fileExt;
    }

    /**
     * @param fileExt
     *            the fileExt to set
     */
    public void setFileExt(String fileExt)
    {
	this.fileExt = fileExt;
    }

    /**
     * @return the fileSize
     */
    public int getFileSize()
    {
	return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(int fileSize)
    {
	this.fileSize = fileSize;
    }

    /**
     * @return the fileType
     */
    public EDocumentType getFileType()
    {
	return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(EDocumentType fileType)
    {
	this.fileType = fileType;
    }
}
