package enfo.crm.util;

public class Const
{
	public static final Integer ZERO = new Integer(0);
	public static final Integer ONE = new Integer(1);
	public static final Integer TWO = new Integer(2);

	public static final String BUSI_COMMCREDIT = "120301";
	public static final String BUSI_COMMTENANCY = "120302";
	public static final String BUSI_GROUPCREDIT = "120303";
	public static final String BUSI_ASSURANCE = "120304";
	public static final String BUSI_INVESTMENT = "120305";
	public static final String BUSI_ENTCREDIT = "120306";
	public static final String BUSI_ENTTENANCY = "120307";
	public static final String BUSI_LOAN = "120309";
	
	public static final String CONTRACT_NEW = "112701";
	public static final String CONTRACT_PERIOD = "112702";
	public static final String CONTRACT_GRADE = "112703";
	public static final String CONTRACT_RATE = "112704";
	public static final String CONTRACT_HUNG_RATE = "112705";
	public static final String CONTRACT_CANCEL_RATE = "112706";
	public static final String CONTRACT_BALANCE = "112707";
	public static final String CONTRACT_END = "112708";
	public static final String CONTRACT_CHARGE = "112709";
	public static final String CONTRACT_PLAN = "112700"; // special
	
	public static final String BUSI_REASSET ="120310";//·µÊÛ×Ê²ú
	
	public static final String STATUS_NORMAL = "113601";
	public static final String STATUS_FINISH_NORMAL = "113602";
	public static final String STATUS_FINISH_PROCESS = "113603";
	public static final String STATUS_FINISH_NO_PROCESS = "113604";
	
	public static final String ENT_CUST_WTR = "112001";
	public static final String ENT_CUST_JKR = "112002";
	public static final String ENT_CUST_CZR = "112003";
	public static final String ENT_CUST_BZR = "112004";
	public static final String ENT_CUST_DYR = "112005";
	public static final String ENT_CUST_ZYR = "112006";
	public static final String ENT_CUST_ZWR = "112007";
	
	
	public static final String BACKUP_CONTRACT_SQL = "{?=call SP_BACKUP_CONTRACT (?, ?, ?)}";
	public static final String RESTORE_CONTRACT_SQL = "{?=call SP_RESTORE_CONTRACT (?, ?, ?)}";

	public static final String QUERY_FINANCE_COMMCREDIT_SQL = "{call SP_QUERY_TPOSTLIST_TCOMMCREDIT(?, ?)}";
	public static final String QUERY_FINANCE_COMMTENANCY_SQL = "{call SP_QUERY_TPOSTLIST_TCOMMTENANCY(?, ?, ?, ?)}";
	public static final String QUERY_FINANCE_GROUPCREDIT_SQL = "{call SP_QUERY_TPOSTLIST_TGROUPCREDIT(?, ?)}";
	public static final String QUERY_FINANCE_ASSURANCE_SQL = "{call SP_QUERY_TPOSTLIST_TASSURE(?)}";
	public static final String QUERY_FINANCE_INVESTMENT_SQL = "{call SP_QUERY_TPOSTLIST_TINVEST(?)}";
	public static final String QUERY_FINANCE_ENTCREDIT_SQL = "{call SP_QUERY_TPOSTLIST_TENTCREDIT(?, ?, ?, ?)}";
	public static final String QUERY_FINANCE_ENTTENANCY_SQL = "{call SP_QUERY_TPOSTLIST_TENTTENANCY(?, ?)}";
	
	public static final String QUERY_CHANGE_RATE_SQL = "{call SP_QUERY_TRATEHUNG (?, ?, ?, ?)}";
}