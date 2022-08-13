package com.whaty.platform.entity.web.action.fee.statistic;

import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.fee.pay.PrFeeDetailForRecieptAction;
import com.whaty.platform.util.Const;

public class PeFeePiCiSearchAction extends PrFeeDetailForRecieptAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("学生交费明细"));
		
		this.getGridConfig().addMenuScript(this.getText("test.back"),"{window.history.back();}");
		
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("入学准考证号"),"peStudent.peRecStudent.examCardNum",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.regNo"),"peStudent.regNo",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("peStudent.trueName"), "peStudent.trueName",true, false, true, "");
//		this.getGridConfig().addColumn(this.getText("prFeeDetail.enumConstByFlagFeeType.name"), "enumConstByFlagFeeType.name",true, false, true, "");
		ColumnConfig column = new ColumnConfig(this.getText("prFeeDetail.enumConstByFlagFeeType.name"),"enumConstByFlagFeeType.name");
		column.setComboSQL("select id,name from enum_const where namespace='FlagFeeType' and code in('0','1')");
		this.getGridConfig().addColumn(column);
		ColumnConfig column2 = new ColumnConfig(this.getText("费用状态"),"enumConstByFlagFeeCheck.name");
		column2.setComboSQL("select id,name from enum_const where namespace='FlagFeeCheck' and code in('0','1','2')");
		this.getGridConfig().addColumn(column2);
//		this.getGridConfig().addColumn(this.getText("费用状态"), "enumConstByFlagFeeCheck.name",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("交费日期"), "inputDate",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("交费金额"), "feeAmount",true, false, true, "");
		this.getGridConfig().addColumn(this.getText("收据发票号"), "invoiceNo",true, true, true, Const.AccountingInvoice_for_extjs);
	}
	
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/fee/peFeePiCi";
	}


}
