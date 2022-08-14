package enfo.crm.vo;

public class AmlVO {
	
	private String tstr;//信托公司可疑交易报告信息
	private String rbif;//可疑交易报告基本信息
	private String finm;//金融机构名称
	private String firc;//金融机构所在地区行政区划
	private String ficd;//金融机构代码
	private String rptp;//报告类型
	private String stcd;//可疑交易特征信息
	private String stcr;//可疑特征
	private String sdgr;//可疑程度
	private String ictp;//涉嫌犯罪类型
	private String tkms;//采取措施
	private String ssds;//可疑行为描述
	private String cttn;//客户总数
	private String tctn;//信托合同总份数
	private String ttnm;//可疑交易总笔数
	private String rpnm;//可疑交易报告的填报人
	
	private String init_xml;
	private String send_xml;
	private String error_xml;
	
	private String status;//当前报告状态
	private String ctifs;//交易主体信息集合
	private String ctif;//
	private String ciif;//客户身份基本信息
	private String ctnm;//客户姓名/名称
	private String cttp;//客户类型
	private String citp;//客户身份证件类型
	private String ctid;//客户证件号码
	private String civt;//客户身份证件有效期限
	private String ctnt;//客户国籍
	private String ctvc;//客户职业/行业
	private String ccif;//客户联系方式
	private String ctar;//客户地址
	private String cctl;//客户联系电话
	private String ccei;//客户其他联系方式
	private String cbif;//客户经营信息
	private String cbsc;//客户经营范围
	private String crft;//客户注册资金币种
	private String crfd;//客户注册资金
	private String ctrn;//客户税务登记证号码
	private String crif;//法定代表人信息
	private String crnm;//客户法定代表人姓名
	private String crit;//客户法定代表人身份证件类型
	private String crid;//客户法定代表人身份证件号码
	private String crvt;//客户法人代表身份证件有效期限
	
	private String chifs;//股东信息集合
	private String chif;//
	private String hdnm;//控股股东/实际控制人姓名
	private String hitp;//控股股东/实际控制人身份证件/证明文件类型
	private String hdid;//控股股东/实际控制人身份证件/证明文件号码
	private Integer hivt;//控股股东/实际控制人身份证件/证明文件有效期限
	private String hivt2;
	private String pcif;//负责人信息
	private String pcnm;//负责人姓名
	private String pitp;//负责人身份证件类型
	private String picd;//负责人身份证件号码
	private String pivt;//负责人身份证件有效期限
	
	private String tcifs;//可疑交易所涉合同信息
	private String tcif;//
	private String tcsn;//信托合同号/受益权转让登记号
	private String tcsm;//信托合同概述
	
	private String stifs;//可疑交易信息集合
	
	private String stif;//
	
	private String taif;//信托资产专户信息
	private String tabn;//信托财产专户开户行名称
	private String taan;//信托财产专户账号
	
	private String clif;//委托人信息
	private String clnm;//委托人姓名
	private String ciit;//委托人身份证件/证明文件类型
	private String clid;//委托人身份证件/证明文件号码
	private String clvt;//委托人身份证件/证明文件有效期限
	private String cobn;//委托人开户行名称
	private String catp;//委托人账号类型
	private String coan;//委托人开户行账号
	
	private String bnifs;//受益人信息集合
	private String bnif;//
	private String bnnm;//受益人姓名/名称
	private String bitp;//受益人身份证件/证明文件类型
	private String bnid;//受益人身份证件/证明文件号码
	private String bivt;//受益人身份证件/证明文件有效期限
	private String bobn;//受益人开户行名称
	private String batp;//受益人账号类型
	private String boan;//受益人开户行账号
	
	private String aaifs;//授权业务办理人信息集合
	private String aaif;//
	private String aban;//授权业务办理人姓名
	private String abit;//授权业务办理人身份证件/证明文件类型
	private String abai;//授权业务办理人身份证件/证明文件号码
	private String abvt;//授权业务办理人身份证件/证明文件有效期限
	
	private String sbif;//可疑交易行为信息集合
	private String bstp;//业务种类
	private String tstp;//交易类型
	private String tcsnInSbif;//信托合同号/受益权转让登记号码
	private String trcd;//业务发生地行政区划代码
	private String tpdd;//信托财产交付日期
	private String tprs;//信托资产来源
	private String tpfd;//信托资产流动方向
	private String tcct;//信托资金支付币种
	private String tcca;//信托资金支付金额
	private String brtd;//受益权转让日期
	private String brdm;//受益权转让交付方式
	private String brtt;//受益权变更次数
	
	private String tsifs;//受让方信息集合
	private String tsif;//
	private String tsnm;//受让方姓名/名称
	private String titp;//受让方身份证件/证明文件类型
	private String tfid;//受让方身份证件/证明文件号码
	private String tivt;//受让方身份证件/证明文件有效期限
	private String tobn;//受让方制定开立收益分配账户的银行名称
	private String tatp;//受让方账户类型
	private String tban;//受让方指定的收益分配账号
	
	private String seq_no;//当日文件编号
	
	private String CTIFFlag;
	private String CHIFFlag;
	private String TCIFFlag;
	private String STIFFlag;
	private String BNIFFlag;
	private String AAIFFlag;
	private String TSIFFlag;
	
	private String summary;//报告名称描述,便于用户识别
	/**
	 * 报告录入时间，时间段用于查询
	 */
	private Integer start_date;
	private Integer end_date;
	private Integer check_flag;
	
	private Integer cust_id;
	private Integer input_man;
	private Integer serial_no;
	private Integer relate_flag;
	private Integer relate_serial;
	private Integer prior_serial;
	
	public String getCTIFFlag() {
		return CTIFFlag;
	}
	public void setCTIFFlag(String flag) {
		CTIFFlag = flag;
	}
	public String getCHIFFlag() {
		return CHIFFlag;
	}
	public void setCHIFFlag(String flag) {
		CHIFFlag = flag;
	}
	public String getTCIFFlag() {
		return TCIFFlag;
	}
	public void setTCIFFlag(String flag) {
		TCIFFlag = flag;
	}
	public String getSTIFFlag() {
		return STIFFlag;
	}
	public void setSTIFFlag(String flag) {
		STIFFlag = flag;
	}
	public String getBNIFFlag() {
		return BNIFFlag;
	}
	public void setBNIFFlag(String flag) {
		BNIFFlag = flag;
	}
	public String getAAIFFlag() {
		return AAIFFlag;
	}
	public void setAAIFFlag(String flag) {
		AAIFFlag = flag;
	}
	public String getTSIFFlag() {
		return TSIFFlag;
	}
	public void setTSIFFlag(String flag) {
		TSIFFlag = flag;
	}
	public String getTstr() {
		return tstr;
	}
	public void setTstr(String tstr) {
		this.tstr = tstr;
	}
	public String getRbif() {
		return rbif;
	}
	public void setRbif(String rbif) {
		this.rbif = rbif;
	}
	public String getFinm() {
		return finm;
	}
	public void setFinm(String finm) {
		this.finm = finm;
	}
	public String getFirc() {
		return firc;
	}
	public void setFirc(String firc) {
		this.firc = firc;
	}
	public String getFicd() {
		return ficd;
	}
	public void setFicd(String ficd) {
		this.ficd = ficd;
	}
	public String getRptp() {
		return rptp;
	}
	public void setRptp(String rptp) {
		this.rptp = rptp;
	}
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public String getStcr() {
		return stcr;
	}
	public void setStcr(String stcr) {
		this.stcr = stcr;
	}
	public String getSdgr() {
		return sdgr;
	}
	public void setSdgr(String sdgr) {
		this.sdgr = sdgr;
	}
	public String getIctp() {
		return ictp;
	}
	public void setIctp(String ictp) {
		this.ictp = ictp;
	}
	public String getTkms() {
		return tkms;
	}
	public void setTkms(String tkms) {
		this.tkms = tkms;
	}
	public String getSsds() {
		return ssds;
	}
	public void setSsds(String ssds) {
		this.ssds = ssds;
	}
	public String getCttn() {
		return cttn;
	}
	public void setCttn(String cttn) {
		this.cttn = cttn;
	}
	public String getTctn() {
		return tctn;
	}
	public void setTctn(String tctn) {
		this.tctn = tctn;
	}
	public String getTtnm() {
		return ttnm;
	}
	public void setTtnm(String ttnm) {
		this.ttnm = ttnm;
	}
	public String getRpnm() {
		return rpnm;
	}
	public void setRpnm(String rpnm) {
		this.rpnm = rpnm;
	}
	public String getCtifs() {
		return ctifs;
	}
	public void setCtifs(String ctifs) {
		this.ctifs = ctifs;
	}
	public String getCtif() {
		return ctif;
	}
	public void setCtif(String ctif) {
		this.ctif = ctif;
	}
	public String getCiif() {
		return ciif;
	}
	public void setCiif(String ciif) {
		this.ciif = ciif;
	}
	public String getCtnm() {
		return ctnm;
	}
	public void setCtnm(String ctnm) {
		this.ctnm = ctnm;
	}
	public String getCttp() {
		return cttp;
	}
	public void setCttp(String cttp) {
		this.cttp = cttp;
	}
	public String getCitp() {
		return citp;
	}
	public void setCitp(String citp) {
		this.citp = citp;
	}
	public String getCtid() {
		return ctid;
	}
	public void setCtid(String ctid) {
		this.ctid = ctid;
	}
	public String getCivt() {
		return civt;
	}
	public void setCivt(String civt) {
		this.civt = civt;
	}
	public String getCtnt() {
		return ctnt;
	}
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
	public String getCtvc() {
		return ctvc;
	}
	public void setCtvc(String ctvc) {
		this.ctvc = ctvc;
	}
	public String getCcif() {
		return ccif;
	}
	public void setCcif(String ccif) {
		this.ccif = ccif;
	}
	public String getCtar() {
		return ctar;
	}
	public void setCtar(String ctar) {
		this.ctar = ctar;
	}
	public String getCctl() {
		return cctl;
	}
	public void setCctl(String cctl) {
		this.cctl = cctl;
	}
	public String getCcei() {
		return ccei;
	}
	public void setCcei(String ccei) {
		this.ccei = ccei;
	}
	public String getCbif() {
		return cbif;
	}
	public void setCbif(String cbif) {
		this.cbif = cbif;
	}
	public String getCbsc() {
		return cbsc;
	}
	public void setCbsc(String cbsc) {
		this.cbsc = cbsc;
	}
	public String getCrft() {
		return crft;
	}
	public void setCrft(String crft) {
		this.crft = crft;
	}
	public String getCrfd() {
		return crfd;
	}
	public void setCrfd(String crfd) {
		this.crfd = crfd;
	}
	public String getCtrn() {
		return ctrn;
	}
	public void setCtrn(String ctrn) {
		this.ctrn = ctrn;
	}
	public String getCrif() {
		return crif;
	}
	public void setCrif(String crif) {
		this.crif = crif;
	}
	public String getCrnm() {
		return crnm;
	}
	public void setCrnm(String crnm) {
		this.crnm = crnm;
	}
	public String getCrit() {
		return crit;
	}
	public void setCrit(String crit) {
		this.crit = crit;
	}
	public String getCrid() {
		return crid;
	}
	public void setCrid(String crid) {
		this.crid = crid;
	}
	public String getCrvt() {
		return crvt;
	}
	public void setCrvt(String crvt) {
		this.crvt = crvt;
	}
	public String getChifs() {
		return chifs;
	}
	public void setChifs(String chifs) {
		this.chifs = chifs;
	}
	public String getChif() {
		return chif;
	}
	public void setChif(String chif) {
		this.chif = chif;
	}
	public String getHdnm() {
		return hdnm;
	}
	public void setHdnm(String hdnm) {
		this.hdnm = hdnm;
	}
	public String getHitp() {
		return hitp;
	}
	public void setHitp(String hitp) {
		this.hitp = hitp;
	}
	public String getHdid() {
		return hdid;
	}
	public void setHdid(String hdid) {
		this.hdid = hdid;
	}
	public Integer getHivt() {
		return hivt;
	}
	public void setHivt(Integer hivt) {
		this.hivt = hivt;
	}
	public String getPcif() {
		return pcif;
	}
	public void setPcif(String pcif) {
		this.pcif = pcif;
	}
	public String getPcnm() {
		return pcnm;
	}
	public void setPcnm(String pcnm) {
		this.pcnm = pcnm;
	}
	public String getPitp() {
		return pitp;
	}
	public void setPitp(String pitp) {
		this.pitp = pitp;
	}
	public String getPicd() {
		return picd;
	}
	public void setPicd(String picd) {
		this.picd = picd;
	}
	public String getPivt() {
		return pivt;
	}
	public void setPivt(String pivt) {
		this.pivt = pivt;
	}
	public String getTcifs() {
		return tcifs;
	}
	public void setTcifs(String tcifs) {
		this.tcifs = tcifs;
	}
	public String getTcif() {
		return tcif;
	}
	public void setTcif(String tcif) {
		this.tcif = tcif;
	}
	public String getTcsn() {
		return tcsn;
	}
	public void setTcsn(String tcsn) {
		this.tcsn = tcsn;
	}
	public String getTcsm() {
		return tcsm;
	}
	public void setTcsm(String tcsm) {
		this.tcsm = tcsm;
	}
	public String getStifs() {
		return stifs;
	}
	public void setStifs(String stifs) {
		this.stifs = stifs;
	}
	public String getStif() {
		return stif;
	}
	public void setStif(String stif) {
		this.stif = stif;
	}
	public String getTaif() {
		return taif;
	}
	public void setTaif(String taif) {
		this.taif = taif;
	}
	public String getTabn() {
		return tabn;
	}
	public void setTabn(String tabn) {
		this.tabn = tabn;
	}
	public String getTaan() {
		return taan;
	}
	public void setTaan(String taan) {
		this.taan = taan;
	}
	public String getClif() {
		return clif;
	}
	public void setClif(String clif) {
		this.clif = clif;
	}
	public String getClnm() {
		return clnm;
	}
	public void setClnm(String clnm) {
		this.clnm = clnm;
	}
	public String getCiit() {
		return ciit;
	}
	public void setCiit(String ciit) {
		this.ciit = ciit;
	}
	public String getClid() {
		return clid;
	}
	public void setClid(String clid) {
		this.clid = clid;
	}
	public String getClvt() {
		return clvt;
	}
	public void setClvt(String clvt) {
		this.clvt = clvt;
	}
	public String getCobn() {
		return cobn;
	}
	public void setCobn(String cobn) {
		this.cobn = cobn;
	}
	public String getCatp() {
		return catp;
	}
	public void setCatp(String catp) {
		this.catp = catp;
	}
	public String getCoan() {
		return coan;
	}
	public void setCoan(String coan) {
		this.coan = coan;
	}
	public String getBnifs() {
		return bnifs;
	}
	public void setBnifs(String bnifs) {
		this.bnifs = bnifs;
	}
	public String getBnif() {
		return bnif;
	}
	public void setBnif(String bnif) {
		this.bnif = bnif;
	}
	public String getBnnm() {
		return bnnm;
	}
	public void setBnnm(String bnnm) {
		this.bnnm = bnnm;
	}
	public String getBitp() {
		return bitp;
	}
	public void setBitp(String bitp) {
		this.bitp = bitp;
	}
	public String getBnid() {
		return bnid;
	}
	public void setBnid(String bnid) {
		this.bnid = bnid;
	}
	public String getBivt() {
		return bivt;
	}
	public void setBivt(String bivt) {
		this.bivt = bivt;
	}
	public String getBobn() {
		return bobn;
	}
	public void setBobn(String bobn) {
		this.bobn = bobn;
	}
	public String getBatp() {
		return batp;
	}
	public void setBatp(String batp) {
		this.batp = batp;
	}
	public String getBoan() {
		return boan;
	}
	public void setBoan(String boan) {
		this.boan = boan;
	}
	public String getAaifs() {
		return aaifs;
	}
	public void setAaifs(String aaifs) {
		this.aaifs = aaifs;
	}
	public String getAaif() {
		return aaif;
	}
	public void setAaif(String aaif) {
		this.aaif = aaif;
	}
	public String getAban() {
		return aban;
	}
	public void setAban(String aban) {
		this.aban = aban;
	}
	public String getAbit() {
		return abit;
	}
	public void setAbit(String abit) {
		this.abit = abit;
	}
	public String getAbai() {
		return abai;
	}
	public void setAbai(String abai) {
		this.abai = abai;
	}
	public String getAbvt() {
		return abvt;
	}
	public void setAbvt(String abvt) {
		this.abvt = abvt;
	}
	public String getSbif() {
		return sbif;
	}
	public void setSbif(String sbif) {
		this.sbif = sbif;
	}
	public String getBstp() {
		return bstp;
	}
	public void setBstp(String bstp) {
		this.bstp = bstp;
	}
	public String getTstp() {
		return tstp;
	}
	public void setTstp(String tstp) {
		this.tstp = tstp;
	}
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getTpdd() {
		return tpdd;
	}
	public void setTpdd(String tpdd) {
		this.tpdd = tpdd;
	}
	public String getTprs() {
		return tprs;
	}
	public void setTprs(String tprs) {
		this.tprs = tprs;
	}
	public String getTpfd() {
		return tpfd;
	}
	public void setTpfd(String tpfd) {
		this.tpfd = tpfd;
	}
	public String getTcct() {
		return tcct;
	}
	public void setTcct(String tcct) {
		this.tcct = tcct;
	}
	public String getTcca() {
		return tcca;
	}
	public void setTcca(String tcca) {
		this.tcca = tcca;
	}
	public String getBrtd() {
		return brtd;
	}
	public void setBrtd(String brtd) {
		this.brtd = brtd;
	}
	public String getBrdm() {
		return brdm;
	}
	public void setBrdm(String brdm) {
		this.brdm = brdm;
	}
	public String getBrtt() {
		return brtt;
	}
	public void setBrtt(String brtt) {
		this.brtt = brtt;
	}
	public String getTsifs() {
		return tsifs;
	}
	public void setTsifs(String tsifs) {
		this.tsifs = tsifs;
	}
	public String getTsif() {
		return tsif;
	}
	public void setTsif(String tsif) {
		this.tsif = tsif;
	}
	public String getTsnm() {
		return tsnm;
	}
	public void setTsnm(String tsnm) {
		this.tsnm = tsnm;
	}
	public String getTitp() {
		return titp;
	}
	public void setTitp(String titp) {
		this.titp = titp;
	}
	public String getTfid() {
		return tfid;
	}
	public void setTfid(String tfid) {
		this.tfid = tfid;
	}
	public String getTivt() {
		return tivt;
	}
	public void setTivt(String tivt) {
		this.tivt = tivt;
	}
	public String getTobn() {
		return tobn;
	}
	public void setTobn(String tobn) {
		this.tobn = tobn;
	}
	public String getTatp() {
		return tatp;
	}
	public void setTatp(String tatp) {
		this.tatp = tatp;
	}
	public String getTban() {
		return tban;
	}
	public void setTban(String tban) {
		this.tban = tban;
	}
	public String getTcsnInSbif() {
		return tcsnInSbif;
	}
	public void setTcsnInSbif(String tcsnInSbif) {
		this.tcsnInSbif = tcsnInSbif;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String string) {
		summary = string;
	}
	public Integer getEnd_date() {
		return end_date;
	}
	public Integer getStart_date() {
		return start_date;
	}
	public void setEnd_date(Integer integer) {
		end_date = integer;
	}
	public void setStart_date(Integer integer) {
		start_date = integer;
	}
	public Integer getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(Integer integer) {
		check_flag = integer;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}
	public void setInput_man(Integer integer) {
		input_man = integer;
	}
	public Integer getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}	
	public Integer getRelate_flag() {
		return relate_flag;
	}
	public void setRelate_flag(Integer integer) {
		relate_flag = integer;
	}
	public Integer getRelate_serial() {
		return relate_serial;
	}
	public void setRelate_serial(Integer integer) {
		relate_serial = integer;
	}
	public Integer getPrior_serial() {
		return prior_serial;
	}
	public void setPrior_serial(Integer integer) {
		prior_serial = integer;
	}	
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @return
	 */
	public String getError_xml() {
		return error_xml;
	}

	/**
	 * @return
	 */
	public String getInit_xml() {
		return init_xml;
	}

	/**
	 * @return
	 */
	public String getSend_xml() {
		return send_xml;
	}

	/**
	 * @param string
	 */
	public void setError_xml(String string) {
		error_xml = string;
	}

	/**
	 * @param string
	 */
	public void setInit_xml(String string) {
		init_xml = string;
	}

	/**
	 * @param string
	 */
	public void setSend_xml(String string) {
		send_xml = string;
	}

	/**
	 * @return
	 */
	public String getHivt2() {
		return hivt2;
	}

	/**
	 * @param string
	 */
	public void setHivt2(String string) {
		hivt2 = string;
	}

}
