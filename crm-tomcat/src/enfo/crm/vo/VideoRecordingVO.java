package enfo.crm.vo;

public class VideoRecordingVO
{
  private Integer vid;
  private Integer productID;
  private Integer subProductID;
  private String saveName;
  private String originName;
  private Integer contractID;
  private Integer inputMan;
  private Integer checkFlag;
  private String checkComment;
  private String contract_BH;
  private String productName;
  private String custName;

  public Integer getVid()
  {
    return this.vid;
  }
  public void setVid(Integer vid) {
    this.vid = vid;
  }
  public Integer getProductID() {
    return this.productID;
  }
  public void setProductID(Integer productID) {
    this.productID = productID;
  }
  public Integer getSubProductID() {
    return this.subProductID;
  }
  public void setSubProductID(Integer subProductID) {
    this.subProductID = subProductID;
  }
  public String getSaveName() {
    return this.saveName;
  }
  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }
  public String getOriginName() {
    return this.originName;
  }
  public void setOriginName(String originName) {
    this.originName = originName;
  }
  public Integer getContractID() {
    return this.contractID;
  }
  public void setContractID(Integer contractID) {
    this.contractID = contractID;
  }
  public Integer getInputMan() {
    return this.inputMan;
  }
  public void setInputMan(Integer inputMan) {
    this.inputMan = inputMan;
  }
  public Integer getCheckFlag() {
    return this.checkFlag;
  }
  public void setCheckFlag(Integer checkFlag) {
    this.checkFlag = checkFlag;
  }
  public String getCheckComment() {
    return this.checkComment;
  }
  public void setCheckComment(String checkComment) {
    this.checkComment = checkComment;
  }
  public String getContract_BH() {
    return this.contract_BH;
  }
  public void setContract_BH(String contract_BH) {
    this.contract_BH = contract_BH;
  }
  public String getProductName() {
    return this.productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }
  public String getCustName() {
    return this.custName;
  }
  public void setCustName(String custName) {
    this.custName = custName;
  }
}