package enfo.crm.vo;

public class ProductCellVO {

    private Integer combo_id; //序号productCombo表主键

    private Integer book_code; //帐套

    private String combo_code; //组合类别代码

    private String combo_name; //类别名称

    private String summary; //备注

    private Integer detail_serial_no; //明细序号

    private Integer cell_id; //单元ID

    private String cell_code; //单元代码

    private String cell_name; //单元名称

    private Integer cell_type; //单元类别

    private Integer product_id; //产品ID

    private String product_code; //产品代码

    private String product_name; //产品名称

    private Integer sub_cell_id; //子单元ID

    private String sub_cell_code; //子单元代码

    private String sub_cell_name; //子单元名称

    private Integer sub_cell_type; //子单元类别

    private Integer sub_cell_flag; //是否已是该单元的子单元标志

    private Integer input_man; //操作员

    //基础数据采集字段
    private Integer data_id;

    private Integer rpt_date;

    private String data_name;

    private String tb_name;

    private String source_tb_name;

    private String proc_name;

    private Integer rpt_month;

    //报表数据核对字段
    private Integer serial_no;

    private Integer rpt_id;//报表id

    private String rpt_name;//报表名称

    private Integer list_id;//核对序号

    private Integer hd_flag;// 核对类别：1表内校验 2 表间校验

    private String hd_content;//-核对内容

    private Integer hd_result;//核对结果：1相等 0 不等

    private String hd_info;//核对说明

	public Integer getCombo_id() {
		return combo_id;
	}

	public void setCombo_id(Integer comboId) {
		combo_id = comboId;
	}

	public Integer getBook_code() {
		return book_code;
	}

	public void setBook_code(Integer bookCode) {
		book_code = bookCode;
	}

	public String getCombo_code() {
		return combo_code;
	}

	public void setCombo_code(String comboCode) {
		combo_code = comboCode;
	}

	public String getCombo_name() {
		return combo_name;
	}

	public void setCombo_name(String comboName) {
		combo_name = comboName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getDetail_serial_no() {
		return detail_serial_no;
	}

	public void setDetail_serial_no(Integer detailSerialNo) {
		detail_serial_no = detailSerialNo;
	}

	public Integer getCell_id() {
		return cell_id;
	}

	public void setCell_id(Integer cellId) {
		cell_id = cellId;
	}

	public String getCell_code() {
		return cell_code;
	}

	public void setCell_code(String cellCode) {
		cell_code = cellCode;
	}

	public String getCell_name() {
		return cell_name;
	}

	public void setCell_name(String cellName) {
		cell_name = cellName;
	}

	public Integer getCell_type() {
		return cell_type;
	}

	public void setCell_type(Integer cellType) {
		cell_type = cellType;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String productCode) {
		product_code = productCode;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String productName) {
		product_name = productName;
	}

	public Integer getSub_cell_id() {
		return sub_cell_id;
	}

	public void setSub_cell_id(Integer subCellId) {
		sub_cell_id = subCellId;
	}

	public String getSub_cell_code() {
		return sub_cell_code;
	}

	public void setSub_cell_code(String subCellCode) {
		sub_cell_code = subCellCode;
	}

	public String getSub_cell_name() {
		return sub_cell_name;
	}

	public void setSub_cell_name(String subCellName) {
		sub_cell_name = subCellName;
	}

	public Integer getSub_cell_type() {
		return sub_cell_type;
	}

	public void setSub_cell_type(Integer subCellType) {
		sub_cell_type = subCellType;
	}

	public Integer getSub_cell_flag() {
		return sub_cell_flag;
	}

	public void setSub_cell_flag(Integer subCellFlag) {
		sub_cell_flag = subCellFlag;
	}

	public Integer getInput_man() {
		return input_man;
	}

	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}

	public Integer getData_id() {
		return data_id;
	}

	public void setData_id(Integer dataId) {
		data_id = dataId;
	}

	public Integer getRpt_date() {
		return rpt_date;
	}

	public void setRpt_date(Integer rptDate) {
		rpt_date = rptDate;
	}

	public String getData_name() {
		return data_name;
	}

	public void setData_name(String dataName) {
		data_name = dataName;
	}

	public String getTb_name() {
		return tb_name;
	}

	public void setTb_name(String tbName) {
		tb_name = tbName;
	}

	public String getSource_tb_name() {
		return source_tb_name;
	}

	public void setSource_tb_name(String sourceTbName) {
		source_tb_name = sourceTbName;
	}

	public String getProc_name() {
		return proc_name;
	}

	public void setProc_name(String procName) {
		proc_name = procName;
	}

	public Integer getRpt_month() {
		return rpt_month;
	}

	public void setRpt_month(Integer rptMonth) {
		rpt_month = rptMonth;
	}

	public Integer getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(Integer serialNo) {
		serial_no = serialNo;
	}

	public Integer getRpt_id() {
		return rpt_id;
	}

	public void setRpt_id(Integer rptId) {
		rpt_id = rptId;
	}

	public String getRpt_name() {
		return rpt_name;
	}

	public void setRpt_name(String rptName) {
		rpt_name = rptName;
	}

	public Integer getList_id() {
		return list_id;
	}

	public void setList_id(Integer listId) {
		list_id = listId;
	}

	public Integer getHd_flag() {
		return hd_flag;
	}

	public void setHd_flag(Integer hdFlag) {
		hd_flag = hdFlag;
	}

	public String getHd_content() {
		return hd_content;
	}

	public void setHd_content(String hdContent) {
		hd_content = hdContent;
	}

	public Integer getHd_result() {
		return hd_result;
	}

	public void setHd_result(Integer hdResult) {
		hd_result = hdResult;
	}

	public String getHd_info() {
		return hd_info;
	}

	public void setHd_info(String hdInfo) {
		hd_info = hdInfo;
	}
}
