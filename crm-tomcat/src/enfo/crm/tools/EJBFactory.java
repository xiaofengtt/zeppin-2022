package enfo.crm.tools;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import enfo.crm.affair.AuthorizationLocal;
import enfo.crm.affair.AuthorizationShareLocal;
import enfo.crm.affair.CustManagerChangesLocal;
import enfo.crm.affair.CustManagerTreeLocal;
import enfo.crm.affair.PostSendLocal;
import enfo.crm.affair.RemindersLocal;
import enfo.crm.affair.ScheDulesLocal;
import enfo.crm.affair.ServiceManageLocal;
import enfo.crm.affair.ServiceTaskLocal;
import enfo.crm.affair.TcustmanagersLocal;
import enfo.crm.affair.TmanagerteamsLocal;
import enfo.crm.callcenter.CallCenterLocal;
import enfo.crm.callcenter.SeatLocal;
import enfo.crm.callcenter.SmsRecordLocal;
import enfo.crm.callcenter.SmsTemplateLocal;
import enfo.crm.cont.ContractManagementLocal;
import enfo.crm.contractManage.TcoContractLocal;
import enfo.crm.contractManage.TcoContractMaintainLocal;
import enfo.crm.contractManage.TcoContractMoneyDetailLocal;
import enfo.crm.contractManage.TcoContractPayPlanLocal;
import enfo.crm.contractManage.TcoContractPointsLocal;
import enfo.crm.contractManage.TcoContractRecordLocal;
import enfo.crm.contractManage.TcoMaintainDetailLocal;
import enfo.crm.contractManage.TcoMaintainMoneyDetailLocal;
import enfo.crm.contractManage.TcoMoneyTaskLocal;
import enfo.crm.contractManage.TcoProductLocal;
import enfo.crm.contractManage.TcoTeamInfoLocal;
import enfo.crm.contractManage.TcoTeamMemberLocal;
import enfo.crm.customer.ChannelQueryLocal;
import enfo.crm.customer.CustClassDefineLocal;
import enfo.crm.customer.CustClassDetailLocal;
import enfo.crm.customer.CustClassLogListLocal;
import enfo.crm.customer.CustGroupLocal;
import enfo.crm.customer.CustGroupMemberLocal;
import enfo.crm.customer.CustomerChangesLocal;
import enfo.crm.customer.CustomerClassLocal;
import enfo.crm.customer.CustomerConnectionLocal;
import enfo.crm.customer.CustomerContactLocal;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.customer.CustomerStatLocal;
import enfo.crm.customer.EmailAttachLocal;
import enfo.crm.customer.EmailListLocal;
import enfo.crm.customer.EmailRecipientsLocal;
import enfo.crm.customer.ManualScoringLocal;
import enfo.crm.customer.PartnerLocal;
import enfo.crm.customer.RatingLocal;
import enfo.crm.customer.ScoreOperandLocal;
import enfo.crm.customer.ScoreSubjectLocal;
import enfo.crm.customer.SubjectScoreRatingLocal;
import enfo.crm.customer.SystemConditionLocal;
import enfo.crm.customer.SystemValueLocal;
import enfo.crm.intrust.AmCustInfoLocal;
import enfo.crm.intrust.AmShareHolderLocal;
import enfo.crm.intrust.ApplyreachLocal;
import enfo.crm.intrust.AttachmentLocal;
import enfo.crm.intrust.AttachmentToCrmLocal;
import enfo.crm.intrust.BenChangeLocal;
import enfo.crm.intrust.BenifitorLocal;
import enfo.crm.intrust.ChannelLocal;
import enfo.crm.intrust.CommCreditLocal;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.CustomerInfoLocal;
import enfo.crm.intrust.Customer_INSTLocal;
import enfo.crm.intrust.DeployLocal;
import enfo.crm.intrust.GainLevelLocal;
import enfo.crm.intrust.GainLevelRateLocal;
import enfo.crm.intrust.GradeDetailLocal;
import enfo.crm.intrust.GradeIndexLocal;
import enfo.crm.intrust.GradeInfoLocal;
import enfo.crm.intrust.GradeStandardLocal;
import enfo.crm.intrust.GradeTotalLocal;
import enfo.crm.intrust.IntrustBenifitorLocal;
import enfo.crm.intrust.IntrustCapitalAddLocal;
import enfo.crm.intrust.IntrustCapitalInfoLocal;
import enfo.crm.intrust.IntrustCapitalTypeLocal;
import enfo.crm.intrust.IntrustContractLocal;
import enfo.crm.intrust.IntrustCrmCustInfoLocal;
import enfo.crm.intrust.IntrustCustomerInfoLocal;
import enfo.crm.intrust.IntrustEntCustCardLocal;
import enfo.crm.intrust.IntrustEntCustomerLocal;
import enfo.crm.intrust.IntrustHmdLocal;
import enfo.crm.intrust.IntrustManagerManOperLocal;
import enfo.crm.intrust.IntrustProductAddLocal;
import enfo.crm.intrust.IntrustProductLocal;
import enfo.crm.intrust.MoneyDetailLocal;
import enfo.crm.intrust.OpertypeLocal;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.intrust.ProductAddLocal;
import enfo.crm.intrust.ProductCellLocal;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.intrust.PurconfigLocal;
import enfo.crm.intrust.RedeemLocal;
import enfo.crm.intrust.SqstopContractLocal;
import enfo.crm.intrust.TMoneyDataLocal;
import enfo.crm.intrust.TaskInfoLocal;
import enfo.crm.intrust.ToMoneyAccountLocal;
import enfo.crm.intrust.ValidateprintLocal;
import enfo.crm.marketing.ActivityFeeLocal;
import enfo.crm.marketing.ActivityLocal;
import enfo.crm.marketing.ActivityTaskLocal;
import enfo.crm.marketing.BenifiterQueryLocal;
import enfo.crm.marketing.CustIntegralLocal;
import enfo.crm.marketing.IntegralCalLocal;
import enfo.crm.marketing.MeetingLocal;
import enfo.crm.marketing.NonProductLocal;
import enfo.crm.marketing.PreContractCrmLocal;
import enfo.crm.marketing.PreDoubleRecordInfoLocal;
import enfo.crm.marketing.PreMoneyDetailLocal;
import enfo.crm.marketing.ProfitLocal;
import enfo.crm.marketing.QuestionnaireLocal;
import enfo.crm.marketing.QuotientAffirmLocal;
import enfo.crm.marketing.SaleParameterLocal;
import enfo.crm.marketing.SalesChangesLocal;
import enfo.crm.marketing.SalesResultForStatisticLocal;
import enfo.crm.marketing.SubscribeLocal;
import enfo.crm.project.BusinessLogicLocal;
import enfo.crm.project.LevelAppRecordLocal;
import enfo.crm.service.CustServiceLogLocal;
import enfo.crm.system.AttachmentTypeLocal;
import enfo.crm.system.ConfigLocal;
import enfo.crm.system.DepartmentLocal;
import enfo.crm.system.DictparamLocal;
import enfo.crm.system.FaqsLocal;
import enfo.crm.system.HolidayLocal;
import enfo.crm.system.LogListLocal;
import enfo.crm.system.MenuInfoLocal;
import enfo.crm.system.MessageLocal;
import enfo.crm.system.OperatorLocal;
import enfo.crm.system.PortalLocal;
import enfo.crm.system.ProductInfoReposLocal;
import enfo.crm.system.RoleLocal;
import enfo.crm.system.RolerightLocal;
import enfo.crm.system.SystemInfoLocal;
import enfo.crm.system.UserInfoLocal;
import enfo.crm.webcall.TCrmWebCallLocal;
import enfo.crm.workflow.CRMCheckFlowLocal;
import enfo.crm.workflow.ImportFlowWorkLocal;

@Component
public class EJBFactory {
	
	@SuppressWarnings("unused")
	private static EJBFactory ejbFactory;
	
	@PostConstruct
	public void init(){
		ejbFactory = this;
	}
	private static final ApplicationContext getContext(){return ApplicationContextUtil.getApplicationContext();}
	
	/*--------------------Begin - system---------------------------------------------*/

	public static final  DepartmentLocal getDepartment() throws Exception {
		return (DepartmentLocal)getContext().getBean("department");
	}
	
	public static final DictparamLocal getDictparam() throws Exception {
		return (DictparamLocal)getContext().getBean("dictparam");
	}
	
	public static final LogListLocal getLogList() throws Exception {
		return (LogListLocal)getContext().getBean("logList");
	}
	
	public static final MenuInfoLocal getMenuInfo() throws Exception {
		return (MenuInfoLocal)getContext().getBean("menuInfo");
	}
	
	public static final MessageLocal getMessage() throws Exception {
		return (MessageLocal)getContext().getBean("messageLocal");
	}
	
	public static final OperatorLocal getOperator() throws Exception {
		return (OperatorLocal)getContext().getBean("operator");
	}
	
	public static final RoleLocal getRole() throws Exception {
		return (RoleLocal)getContext().getBean("role");
	}
	
	public static final AttachmentTypeLocal getAttachmentType() throws Exception {
		return (AttachmentTypeLocal)getContext().getBean("attachmentType");
	}
	
	public static final SaleParameterLocal getSaleParameter() throws Exception {
		return (SaleParameterLocal)getContext().getBean("saleParameter");
	}
	
	public static final RolerightLocal getRolering() throws Exception {
		return (RolerightLocal)getContext().getBean("roleright");
	}
	
	public static final SystemInfoLocal getSystemInfo() throws Exception {
		return (SystemInfoLocal)getContext().getBean("systemInfo");
	}
	
	public static final UserInfoLocal getUserInfo() throws Exception {
		return (UserInfoLocal)getContext().getBean("userInfo");
	}
	/**
	 * Ê×Ò³Portal
	 * @return
	 * @throws Exception
	 */
	public static final PortalLocal getPortal() throws Exception {
		return (PortalLocal)getContext().getBean("portal");
	}
	/*--------------------End - system---------------------------------------------*/
	
	/*--------------------Begin - callcenter---------------------------------------------*/
	public static final CallCenterLocal getCallCenter() throws Exception {
		return (CallCenterLocal)getContext().getBean("callCenter");
	}
	
	public static final SeatLocal getSeat() throws Exception {
		return (SeatLocal)getContext().getBean("seat");
	}
	
	public static final SmsRecordLocal getSmsRecord() throws Exception{
		return (SmsRecordLocal)getContext().getBean("smsRecord");
	}
	
	public static final SmsTemplateLocal getSmsTemplate() throws Exception{
		return (SmsTemplateLocal)getContext().getBean("smsTemplate");
	}
	
	/*--------------------End - callcenter---------------------------------------------*/
	
	/*--------------------Begin - affair---------------------------------------------*/
	public static final CustManagerChangesLocal getCustManagerChanges() throws Exception {
		return (CustManagerChangesLocal)getContext().getBean("custManagerChanges");
	}
	
	public static final RemindersLocal getReminders() throws Exception {
		return (RemindersLocal)getContext().getBean("reminders");
	}
	
	public static final PostSendLocal getPostSend() throws Exception {
		return (PostSendLocal)getContext().getBean("postSend");
	}
	
	public static final ScheDulesLocal getScheDules() throws Exception {
		return (ScheDulesLocal)getContext().getBean("scheDules");
	}
	
	public static final ServiceManageLocal getServiceManage() throws Exception {
		return (ServiceManageLocal)getContext().getBean("serviceManage");
	}
	
	public static final ServiceTaskLocal getServiceTask() throws Exception {
		return (ServiceTaskLocal)getContext().getBean("serviceTask");
	}
	
	public static final TcustmanagersLocal getTcustmanagers() throws Exception {
		return (TcustmanagersLocal)getContext().getBean("tcustmanagers");
	}
	
	public static final TmanagerteamsLocal getTmanagerteams() throws Exception {
		return (TmanagerteamsLocal)getContext().getBean("tmanagerteams");
	}	
	/*--------------------End - affair---------------------------------------------*/
	
	/*--------------------Begin - customer---------------------------------------------*/
	public static final CustClassDefineLocal getCustClassDefine() throws Exception {
		return (CustClassDefineLocal)getContext().getBean("custClassDefine");
	}
	
	public static final CustClassDetailLocal getCustClassDetail() throws Exception {
		return (CustClassDetailLocal)getContext().getBean("custClassDetail");
	}
	
	public static final CustGroupLocal getCustGroup() throws Exception {
		return (CustGroupLocal)getContext().getBean("custGroup");
	}
	
	public static final CustGroupMemberLocal getCustGroupMember() throws Exception {
		return (CustGroupMemberLocal)getContext().getBean("custGroupMember");
	}
	
	public static final CustomerLocal getCustomer() throws Exception {
		return (CustomerLocal)getContext().getBean("customer");
	}
	
	public static final CustomerChangesLocal getCustomerChanges() throws Exception {
		return (CustomerChangesLocal)getContext().getBean("customerChanges");
	}
	
	public static final CustomerClassLocal getCustomerClass() throws Exception {
		return (CustomerClassLocal)getContext().getBean("customerClass");
	}
	
	public static final CustomerContactLocal getCustomerContact() throws Exception {
		return (CustomerContactLocal)getContext().getBean("customerContact");
	}
	
	public static final CustomerStatLocal getCustomerStat() throws Exception {
		return (CustomerStatLocal)getContext().getBean("customerStat");
	}
	
	public static final EmailAttachLocal getEmailAttach() throws Exception {
		return (EmailAttachLocal)getContext().getBean("emailAttach");
	}
	
	public static final EmailListLocal getEmailList() throws Exception {
		return (EmailListLocal)getContext().getBean("emailList");
	}
	
	public static final EmailRecipientsLocal getEmailRecipients() throws Exception {
		return (EmailRecipientsLocal)getContext().getBean("emailRecipients");
	}
	
	public static final PartnerLocal getPartner() throws Exception {
		return (PartnerLocal)getContext().getBean("partner");
	}
	
	/*--------------------End - customer---------------------------------------------*/
	
	/*--------------------Begin - marketing---------------------------------------------*/
	public static final ActivityLocal getActivity() throws Exception {
		return (ActivityLocal)getContext().getBean("activity");
	}
	
	public static final ActivityFeeLocal getActivityFee() throws Exception {
		return (ActivityFeeLocal)getContext().getBean("activityFee");
	}
	
	public static final ActivityTaskLocal getActivityTask() throws Exception {
		return (ActivityTaskLocal)getContext().getBean("activityTask");
	}
	
	public static final MeetingLocal getMeeting() throws Exception {
		return (MeetingLocal)getContext().getBean("meeting");
	}
	
	public static final QuestionnaireLocal getQuestionnaire() throws Exception {
		return (QuestionnaireLocal)getContext().getBean("questionnaire");
	}
	
	public static final CustIntegralLocal getCustIntegral() throws Exception {
		return (CustIntegralLocal)getContext().getBean("custIntegral");
	}
	
	public static final IntegralCalLocal getIntegralCal() throws Exception{
		return (IntegralCalLocal)getContext().getBean("integralCal");
	}
	
	/*--------------------End - marketing---------------------------------------------*/
	

	/*--------------------Begin - intrust---------------------------------------------*/
	public static final AmCustInfoLocal getAmCustInfo() throws Exception {
		return (AmCustInfoLocal)getContext().getBean("amCustInfo");
	}
	
	public static final ApplyreachLocal getApplyreach() throws Exception {
		return (ApplyreachLocal)getContext().getBean("applyreach");
	}
	
	public static final BenChangeLocal getBenChange() throws Exception {
		return (BenChangeLocal)getContext().getBean("benChange");
	}
	
	public static final BenifitorLocal getBenifitor() throws Exception {
		return (BenifitorLocal)getContext().getBean("benifitor");
	}
	
	public static final ContractLocal getContract() throws Exception {
		return (ContractLocal)getContext().getBean("contract");
	}
	
	public static final Customer_INSTLocal getCustomer_INST() throws Exception {
		return (Customer_INSTLocal)getContext().getBean("customer_INST");
	}
	
	public static final DeployLocal getDeploy() throws Exception {
		return (DeployLocal)getContext().getBean("deploy");
	}
	
	public static final GradeDetailLocal getGradeDetail() throws Exception {
		return (GradeDetailLocal)getContext().getBean("gradeDetail");
	}
	
	public static final GradeIndexLocal getGradeIndex() throws Exception {
		return (GradeIndexLocal)getContext().getBean("gradeIndex");
	}
	
	public static final GradeInfoLocal getGradeInfo() throws Exception {
		return (GradeInfoLocal)getContext().getBean("gradeInfo");
	}
	
	public static final GradeStandardLocal getGradeStandard() throws Exception {
		return (GradeStandardLocal)getContext().getBean("gradeStandard");
	}
	
	public static final GradeTotalLocal getGradeTotal() throws Exception {
		return (GradeTotalLocal)getContext().getBean("gradeTotal");
	}
	
	public static final MoneyDetailLocal getMoneyDetail() throws Exception {
		return (MoneyDetailLocal)getContext().getBean("moneyDetail");
	}
	
	public static final PreContractLocal getPreContract() throws Exception {
		return (PreContractLocal)getContext().getBean("preContract");
	}
	
	public static final ProductAddLocal getProductAdd() throws Exception {
		return (ProductAddLocal)getContext().getBean("productAdd");
	}
	
	public static final ProductLocal getProduct() throws Exception {
		return (ProductLocal)getContext().getBean("product");
	}
	
	public static final RedeemLocal getRedeem() throws Exception {
		return (RedeemLocal)getContext().getBean("redeem");
	}
	
	public static final SqstopContractLocal getSqstopContract() throws Exception {
		return (SqstopContractLocal)getContext().getBean("sqstopContract");
	}
	
	public static final ToMoneyAccountLocal getToMoneyAccount() throws Exception {
		return (ToMoneyAccountLocal)getContext().getBean("toMoneyAccount");
	}
	
	public static final AttachmentLocal getAttachment() throws Exception {
		return (AttachmentLocal)getContext().getBean("attachment");
	}

	public static final ChannelLocal getChannel() throws Exception {
		return (ChannelLocal)getContext().getBean("channel");
	}
	
	public static final TMoneyDataLocal getTMoneyData() throws Exception {
		return (TMoneyDataLocal)getContext().getBean("tMoneyData");
	}
	
	/*--------------------End - intrust---------------------------------------------*/
	public static final BenChangeLocal getBenChanage() throws Exception {
		return (BenChangeLocal)getContext().getBean("benChange");
	}
	
	public static final GradeIndexLocal getGradIndex() throws Exception {
		return (GradeIndexLocal)getContext().getBean("gradeIndex");
	}
	
	public static final GradeStandardLocal getGradStandard() throws Exception {
		return (GradeStandardLocal)getContext().getBean("gradeStandard");
	}
	
	public static final GradeInfoLocal getGradInfo() throws Exception {
		return (GradeInfoLocal)getContext().getBean("gradeInfo");
	}
	
	public static final AttachmentToCrmLocal getAttachmentToCrm() throws Exception {
		return (AttachmentToCrmLocal)getContext().getBean("attachmentToCrm");
	}
	
	public static final  CustClassLogListLocal getCustClassLogList() throws Exception{
		return (CustClassLogListLocal)getContext().getBean("custClassLogList");
	}

	public static final  AmShareHolderLocal getAmShareHolder() throws Exception{
		return (AmShareHolderLocal)getContext().getBean("amShareHolder");
	}
	
	public static final  CustomerInfoLocal getCustomerInfo() throws Exception{
		return (CustomerInfoLocal)getContext().getBean("customerInfo");
	}
	public static final  IntrustContractLocal getIntrustContract() throws Exception{
		return (IntrustContractLocal)getContext().getBean("intrustContract");
	}
	public static final  IntrustProductLocal getIntrustProduct() throws Exception{
		return (IntrustProductLocal)getContext().getBean("intrustProduct");
	}
	public static final  IntrustCapitalInfoLocal getIntrustCapitalInfo() throws Exception{
		return (IntrustCapitalInfoLocal)getContext().getBean("intrustCapitalInfo");
	}
	public static final  IntrustCustomerInfoLocal getIntrustCustomerInfo() throws Exception{
		return (IntrustCustomerInfoLocal)getContext().getBean("intrustCustomerInfo");
	}
	public static final  IntrustBenifitorLocal getIntrustBenifitor() throws Exception{
		return (IntrustBenifitorLocal)getContext().getBean("intrustBenifitor");
	}
	public static final  IntrustEntCustomerLocal getIntrustEntCustomer() throws Exception{
		return (IntrustEntCustomerLocal)getContext().getBean("intrustEntCustomer");
	}
	public static final  IntrustCapitalAddLocal getIntrustCapitalAdd() throws Exception{
		return (IntrustCapitalAddLocal)getContext().getBean("intrustCapitalAdd");
	}
	public static final  IntrustCrmCustInfoLocal getIntrustCrmCustInfo() throws Exception{
		return (IntrustCrmCustInfoLocal)getContext().getBean("intrustCrmCustInfo");
	}
	public static final  IntrustManagerManOperLocal getIntrustManagerManOperLocal() throws Exception{
		return (IntrustManagerManOperLocal)getContext().getBean("intrustManagerManOper");
	}
	public static final  IntrustProductAddLocal getIntrustProductAddLocal() throws Exception{
		return (IntrustProductAddLocal)getContext().getBean("intrustProductAdd");
	}
	
	public static final  IntrustCapitalTypeLocal getIntrustCapitalTypeLocal() throws Exception{
		return (IntrustCapitalTypeLocal)getContext().getBean("intrustCapitalType");
	}
	public static final  IntrustHmdLocal getIntrustHmdLocal() throws Exception{
		return (IntrustHmdLocal)getContext().getBean("intrustHmd");
	}
	public static final  CustManagerTreeLocal getCustManagerTreeLocal() throws Exception{
		return (CustManagerTreeLocal)getContext().getBean("custManagerTree");
	}
	public static final  AuthorizationLocal getAuthorizationLocal() throws Exception{
		return (AuthorizationLocal)getContext().getBean("authorization");
	}
	public static final  AuthorizationShareLocal getAuthorizationShareLocal() throws Exception{
		return (AuthorizationShareLocal)getContext().getBean("authorizationShare");
	}
	
	public static final  NonProductLocal getNonProductLocal() throws Exception{
		return (NonProductLocal)getContext().getBean("nonProduct");
	}
	
	public static final  SubscribeLocal getSubscribeLocal() throws Exception{
		return (SubscribeLocal)getContext().getBean("subscribe");
	}
	
	public static final QuotientAffirmLocal getQuotientAffirmLocal() throws Exception{
		return (QuotientAffirmLocal)getContext().getBean("quotientAffirm");
	}
	
	public static final ProfitLocal getProfitLocal() throws Exception{
		return (ProfitLocal)getContext().getBean("profit");
	}
	
	public static final  ValidateprintLocal getValidateprint() throws Exception{
		return (ValidateprintLocal)getContext().getBean("validateprint");
	}
	
	public static final  PurconfigLocal getPurconfig() throws Exception{
		return (PurconfigLocal)getContext().getBean("purconfig");
	}
	
	public static final  CommCreditLocal getCommCredit() throws Exception{
		return (CommCreditLocal)getContext().getBean("commCredit");
	}
	
	public static final  ChannelQueryLocal getChannelQuery() throws Exception{
		return (ChannelQueryLocal)getContext().getBean("channelQuery");
	}
	
	public static final  ProductCellLocal getProductCell() throws Exception{
		return (ProductCellLocal)getContext().getBean("productCell");
	}
	
	public static final ContractManagementLocal getContractManagement()throws Exception{
		return (ContractManagementLocal)getContext().getBean("contractManagement");
	}

	public static final  OpertypeLocal getOpertype() throws Exception{
		return (OpertypeLocal)getContext().getBean("opertype");
	}

	public static final  CustServiceLogLocal getCustServiceLog() throws Exception{
		return (CustServiceLogLocal)getContext().getBean("custServiceLog");
	}
	public static final  TaskInfoLocal getTaskInfo() throws Exception{
		return (TaskInfoLocal)getContext().getBean("taskInfo");
	}
	public static final  TcoContractLocal getTcoContract() throws Exception{
		return (TcoContractLocal)getContext().getBean("tcoContract");
	}
	public static final  TcoContractPayPlanLocal getTcoContractPayPlan() throws Exception{
		return (TcoContractPayPlanLocal)getContext().getBean("tcoContractPayPlan");
	}
	public static final  TcoContractPointsLocal getTcoContractPoints() throws Exception{
		return (TcoContractPointsLocal)getContext().getBean("tcoContractPoints");
	}
	public static final  TcoProductLocal getTcoProduct() throws Exception{
		return (TcoProductLocal)getContext().getBean("tcoProduct");
	}
	public static final  TcoContractMoneyDetailLocal getTcoContractMoneyDetail() throws Exception{
		return (TcoContractMoneyDetailLocal)getContext().getBean("tcoContractMoneyDetail");
	}
	
	public static final  TcoContractMaintainLocal getTcoContractMaintain() throws Exception{
		return (TcoContractMaintainLocal)getContext().getBean("tcoContractMaintain");
	}
	public static final  TcoMaintainDetailLocal getTcoMaintainDetail() throws Exception{
		return (TcoMaintainDetailLocal)getContext().getBean("tcoMaintainDetail");
	}
	public static final  TcoMaintainMoneyDetailLocal getTcoMaintainMoneyDetail() throws Exception{
		return (TcoMaintainMoneyDetailLocal)getContext().getBean("tcoMaintainMoneyDetail");
	}
	public static final  TcoTeamInfoLocal getTcoTeamInfo() throws Exception{
		return (TcoTeamInfoLocal)getContext().getBean("tcoTeamInfo");
	}
	public static final  TcoTeamMemberLocal getTcoTeamMember() throws Exception{
		return (TcoTeamMemberLocal)getContext().getBean("tcoTeamMember");
	}
	public static final  TcoContractRecordLocal getTcoContractRecord() throws Exception{
		return (TcoContractRecordLocal)getContext().getBean("tcoContractRecord");
	}
	
	public static final  GainLevelLocal getGainLevel() throws Exception{
		return (GainLevelLocal)getContext().getBean("gainLevel");
	}
	
	public static final  GainLevelRateLocal getGainLevelRate() throws Exception{
		return (GainLevelRateLocal)getContext().getBean("gainLevelRate");
	}
	
	public static final  BenifiterQueryLocal getBenifiterQuery() throws Exception{
		return (BenifiterQueryLocal)getContext().getBean("benifiterQuery");
	}
	public static final  TcoMoneyTaskLocal getTcoMoneyTask() throws Exception{
		return (TcoMoneyTaskLocal)getContext().getBean("tcoMoneyTask");
	}
	
	public static final ScoreSubjectLocal getScoreSubject() throws Exception{
		return (ScoreSubjectLocal)getContext().getBean("scoreSubject");
	}
	
	public static final SubjectScoreRatingLocal getSubjectScoreRating() throws Exception{
		return (SubjectScoreRatingLocal)getContext().getBean("subjectScoreRating");
	}
	
	public static final ScoreOperandLocal getScoreOperand() throws Exception{
		return (ScoreOperandLocal)getContext().getBean("scoreOperand");
	}
	
	public static final ManualScoringLocal getManualScoring() throws Exception{
		return (ManualScoringLocal)getContext().getBean("manualScoring");
	}
	
	public static final SystemValueLocal getSystemValue() throws Exception{
		return (SystemValueLocal)getContext().getBean("systemValue");
	}
	
	public static final SystemConditionLocal getSystemCondition() throws Exception{
		return (SystemConditionLocal)getContext().getBean("systemCondition");
	}
	
	public static final RatingLocal getRating() throws Exception{
		return (RatingLocal)getContext().getBean("rating");
	}
	
	public static final FaqsLocal getFaqs() throws Exception{
		return (FaqsLocal)getContext().getBean("faqs");
	}
	
	public static final ProductInfoReposLocal getProductInfoRepos() throws Exception{
		return (ProductInfoReposLocal)getContext().getBean("productInfoRepos");
	}
	public static final  PreContractCrmLocal getPreContractCrm() throws Exception {
		return (PreContractCrmLocal)getContext().getBean("preContractCrm");
	}
	public static final HolidayLocal getHoliday() throws Exception {
		return (HolidayLocal)getContext().getBean("holiday");
	}
	public static final PreMoneyDetailLocal getPreMoneyDetail() throws Exception {
		return (PreMoneyDetailLocal)getContext().getBean("preMoneyDetail");
	}
	public static final TCrmWebCallLocal getTCrmWebCall() throws Exception {
		return (TCrmWebCallLocal)getContext().getBean("tCrmWebCall");
	}
	public static final LevelAppRecordLocal getLevelAppRecord() throws Exception {
		return (LevelAppRecordLocal)getContext().getBean("levelAppRecord");
	}
	public static final BusinessLogicLocal getBusinessLogic() throws Exception {
		return (BusinessLogicLocal)getContext().getBean("businessLogic");
	}
	public static final ConfigLocal getConfig() throws Exception {
		return (ConfigLocal)getContext().getBean("config");
	}
	public static final ConfigLocal getConfigCatalog() throws Exception {
		return (ConfigLocal)getContext().getBean("config");
	}
	public static final ImportFlowWorkLocal getImportFlowWork() throws Exception {
		return (ImportFlowWorkLocal)getContext().getBean("importFlowWork");
	}
	public static final CRMCheckFlowLocal getCRMCheckFlowWork() throws Exception {
		return (CRMCheckFlowLocal)getContext().getBean("cRMCheckFlow");
	}
	public static final IntrustEntCustCardLocal getIntrustEntCustCard() throws Exception {
		return (IntrustEntCustCardLocal)getContext().getBean("intrustEntCustCard");
	}
	
	public static final PreDoubleRecordInfoLocal getPreDoubleRecordInfo() throws Exception {
		return (PreDoubleRecordInfoLocal)getContext().getBean("preDoubleRecordInfo");
	}
	
	public static final CustomerConnectionLocal getCustomerConnection() throws Exception {
		return (CustomerConnectionLocal)getContext().getBean("customerConnection");
	}
	
	public static final SalesChangesLocal getSalesChanges() throws Exception {
		return (SalesChangesLocal)getContext().getBean("salesChanges");
	}
	
	public static final SalesResultForStatisticLocal getSalesResultForStatistic() throws Exception {
		return (SalesResultForStatisticLocal)getContext().getBean("salesResultForStatistic");
	}
}