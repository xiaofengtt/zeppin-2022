<%
bzr1.setCust_id(contract.getBzr_cust_id1());
bzr2.setCust_id(contract.getBzr_cust_id2());
bzr3.setCust_id(contract.getBzr_cust_id3());
dyr1.setCust_id(contract.getDyr_cust_id1());
dyr2.setCust_id(contract.getDyr_cust_id2());
dyr3.setCust_id(contract.getDyr_cust_id3());
zyr1.setCust_id(contract.getZyr_cust_id1());
zyr2.setCust_id(contract.getZyr_cust_id2());
zyr3.setCust_id(contract.getZyr_cust_id3());

if (bzr1.getCust_id() != null)	bzr1 = bzr1bean.load(bzr1);
if (bzr2.getCust_id() != null)	bzr2 = bzr2bean.load(bzr2);
if (bzr3.getCust_id() != null)	bzr3 = bzr3bean.load(bzr3);
if (dyr1.getCust_id() != null)	dyr1 = dyr1bean.load(dyr1);
if (dyr2.getCust_id() != null)	dyr2 = dyr2bean.load(dyr2);
if (dyr3.getCust_id() != null)	dyr3 = dyr3bean.load(dyr3);

if (zyr1.getCust_id() != null)	zyr1 = zyr1bean.load(zyr1);
if (zyr2.getCust_id() != null)	zyr2 = zyr2bean.load(zyr2);
if (zyr3.getCust_id() != null)	zyr3 = zyr3bean.load(zyr3);
%>

							<tr id="tr_bzr">
					 			<td><b>保证人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" id="bzr_list" name="bzr_list" style="width: 100%" onchange="javascript: changeCustomer(this.value, document.theform);">
									<%if ((bzr1.getCust_id() == null) && (bzr2.getCust_id() == null) && (bzr3.getCust_id() == null)){%>
									<option value="">(无)</option>
									<%}else{%>
									<option value="">请选择</option>
									<%}if (bzr1.getCust_id() != null){%>
									<option selected value="bzr1"><%=bzr1.getCust_name()%></option>
									<%}if (bzr2.getCust_id() != null){%>
									<option value="bzr2"><%=bzr2.getCust_name()%></option>
									<%}if (bzr3.getCust_id() != null){%>
									<option value="bzr3"><%=bzr3.getCust_name()%></option>
									<%}%>
								</select>
								</td>
								<td colspan=3>
								<%if(capital_flag!=null && capital_flag.intValue()==1){%>
								<button class="xpbutton5"  name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191102');">保证资产</button>
								<%}%>
								</td>
							</tr>

							<tr id="bzr11">
								<td align="right">保证人1:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_cust_name" size="20" value="<%=Utility.trimNull(bzr1.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">保证人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_ent_type_name" size="20" value="<%=Utility.trimNull(bzr1.getEnt_type_name())%>"></td>
							</tr>
							<tr id="bzr12">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_bank_name" size="20" value="<%=Utility.trimNull(bzr1.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(bzr1.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_bank_acct" size="20" value="<%=Utility.trimNull(bzr1.getBank_acct())%>"></td>
								
							</tr>
							<tr id="bzr17">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(bzr1.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(bzr1.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(bzr1.getCredit_level_name())%>"></td>
							</tr>
							<tr id="bzr13">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_reg_address" size="20" value="<%=Utility.trimNull(bzr1.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_reg_postcode" size="20" value="<%=Utility.trimNull(bzr1.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_card_id" size="20" value="<%=Utility.trimNull(bzr1.getCard_id())%>"></td>
							</tr>
							<tr id="bzr14">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_address" size="20" value="<%=Utility.trimNull(bzr1.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_postcode" size="20" value="<%=Utility.trimNull(bzr1.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_link_man" size="20" value="<%=Utility.trimNull(bzr1.getLink_man())%>"></td>
							</tr>
							<tr id="bzr15">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_telphone" size="20" value="<%=Utility.trimNull(bzr1.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_fax" size="20" value="<%=Utility.trimNull(bzr1.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_email" size="20" value="<%=Utility.trimNull(bzr1.getEmail())%>"></td>
							</tr>
							<tr id="bzr16">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="bzr1_summary" cols="100"><%=Utility.trimNull(bzr1.getSummary())%></textarea></td>
							</tr>

							<tr id="bzr21" style="display:none">
								<td align="right">保证人2:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_cust_name" size="20" value="<%=Utility.trimNull(bzr2.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">保证人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_ent_type_name" size="20" value="<%=Utility.trimNull(bzr2.getEnt_type_name())%>"></td>
							</tr>
							<tr id="bzr22" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_bank_name" size="20" value="<%=Utility.trimNull(bzr2.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(bzr2.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_bank_acct" size="20" value="<%=Utility.trimNull(bzr2.getBank_acct())%>"></td>
								
							</tr>
							<tr id="bzr27" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(bzr2.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(bzr2.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(bzr2.getCredit_level_name())%>"></td>
							</tr>
							<tr id="bzr23" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_reg_address" size="20" value="<%=Utility.trimNull(bzr2.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_reg_postcode" size="20" value="<%=Utility.trimNull(bzr2.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_card_id" size="20" value="<%=Utility.trimNull(bzr2.getCard_id())%>"></td>
							</tr>
							<tr id="bzr24" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_address" size="20" value="<%=Utility.trimNull(bzr2.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_postcode" size="20" value="<%=Utility.trimNull(bzr2.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_link_man" size="20" value="<%=Utility.trimNull(bzr2.getLink_man())%>"></td>
							</tr>
							<tr id="bzr25" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_telphone" size="20" value="<%=Utility.trimNull(bzr2.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_fax" size="20" value="<%=Utility.trimNull(bzr2.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_email" size="20" value="<%=Utility.trimNull(bzr2.getEmail())%>"></td>
							</tr>
							<tr id="bzr26" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="bzr2_summary" cols="100"><%=Utility.trimNull(bzr2.getSummary())%></textarea></td>
							</tr>

							<tr id="bzr31" style="display:none">
								<td align="right">保证人3:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_cust_name" size="20" value="<%=Utility.trimNull(bzr3.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">保证人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_ent_type_name" size="20" value="<%=Utility.trimNull(bzr3.getEnt_type_name())%>"></td>
							</tr>
							<tr id="bzr32" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_bank_name" size="20" value="<%=Utility.trimNull(bzr3.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(bzr3.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_bank_acct" size="20" value="<%=Utility.trimNull(bzr3.getBank_acct())%>"></td>
								
							</tr>
							<tr id="bzr37" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(bzr3.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(bzr3.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(bzr3.getCredit_level_name())%>"></td>
							</tr>
							<tr id="bzr33" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_reg_address" size="20" value="<%=Utility.trimNull(bzr3.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_reg_postcode" size="20" value="<%=Utility.trimNull(bzr3.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_card_id" size="20" value="<%=Utility.trimNull(bzr3.getCard_id())%>"></td>
							</tr>
							<tr id="bzr34" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_address" size="20" value="<%=Utility.trimNull(bzr3.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_postcode" size="20" value="<%=Utility.trimNull(bzr3.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_link_man" size="20" value="<%=Utility.trimNull(bzr3.getLink_man())%>"></td>
							</tr>
							<tr id="bzr35" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_telphone" size="20" value="<%=Utility.trimNull(bzr3.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_fax" size="20" value="<%=Utility.trimNull(bzr3.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_email" size="20" value="<%=Utility.trimNull(bzr3.getEmail())%>"></td>
							</tr>
							<tr id="bzr36" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="bzr3_summary" cols="100"><%=Utility.trimNull(bzr3.getSummary())%></textarea></td>
							</tr>
							
							<tr id="tr_zyr">
								<td><b>质押人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="zyr_list" style="width: 100%" onchange="javascript: changeCustomer(this.value, document.theform);">
									<%if (zyr1.getCust_id() == null)
{%>
									<option value="">(无)</option>
									<%}
else
{%>
									<option value="">请选择</option>
									<option selected value="zyr1"><%=zyr1.getCust_name()%></option>
									<%}%>
									
									<%
if (zyr2.getCust_id() != null)
{%>
									<option value="zyr2"><%=zyr2.getCust_name()%></option>
									<%}
if (zyr3.getCust_id() != null)
{%>
									<option value="zyr3"><%=zyr3.getCust_name()%></option>
									<%}%>
								</select></td>
								<td colspan="3">
								<%if(capital_flag!=null && capital_flag.intValue()==1){%>
									<button class="xpbutton5" accessKey=n name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191103');">质押资产(<u>N</u>)</button>
									<%}%>
								</td>
							</tr>
							<tr id="zyr11">
								<td align="right">质押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr1.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">质押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_ent_type_name" size="20" value="<%=Utility.trimNull(zyr1.getEnt_type_name())%>"></td>
							</tr>
							<tr id="zyr12">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_bank_name" size="20" value="<%=Utility.trimNull(zyr1.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(zyr1.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(zyr1.getBank_acct())%>"></td>
								
							</tr>
							<tr id="zyr17">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr1.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(zyr1.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(zyr1.getCredit_level_name())%>"></td>
							</tr>
							<tr id="zyr13">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr1.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr1.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(zyr1.getCard_id())%>"></td>
							</tr>
							<tr id="zyr14">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr1.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr1.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(zyr1.getLink_man())%>"></td>
							</tr>
							<tr id="zyr15">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(zyr1.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_fax" size="20" maxlength="20" value="<%=Utility.trimNull(zyr1.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_email" size="20" maxlength="30" value="<%=Utility.trimNull(zyr1.getEmail())%>"></td>
							</tr>
							<tr id="zyr16">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea  readonly class="edline_textarea" rows="3" name="zyr1_summary" cols="60"><%=Utility.trimNull(zyr1.getSummary())%></textarea></td>
							</tr>
							
							<tr id="zyr21" style="display:none">
								<td align="right">质押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr2.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">质押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_ent_type_name" size="20" value="<%=Utility.trimNull(zyr2.getEnt_type_name())%>"></td>
							</tr>
							<tr id="zyr22" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_bank_name" size="20" value="<%=Utility.trimNull(zyr2.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(zyr2.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(zyr2.getBank_acct())%>"></td>
								
							</tr>
							<tr id="zyr27" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr2.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(zyr2.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(zyr2.getCredit_level_name())%>"></td>
							</tr>
							
							<tr id="zyr23" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr2.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr2.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(zyr2.getCard_id())%>"></td>
							</tr>
							<tr id="zyr24" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr2.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr2.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(zyr2.getLink_man())%>"></td>
							</tr>
							<tr id="zyr25" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(zyr2.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_fax" size="20" maxlength="20" value="<%=Utility.trimNull(zyr2.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_email" size="20" maxlength="30" value="<%=Utility.trimNull(zyr2.getEmail())%>"></td>
							</tr>
							<tr id="zyr26" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea  readonly class="edline_textarea" rows="3" name="zyr2_summary" cols="60"><%=Utility.trimNull(zyr2.getSummary())%></textarea></td>
							</tr>
							<tr id="zyr31" style="display:none">
								<td align="right">质押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr3.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">质押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_ent_type_name" size="20" value="<%=Utility.trimNull(zyr3.getEnt_type_name())%>"></td>
							</tr>
							<tr id="zyr32" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_bank_name" size="20" value="<%=Utility.trimNull(zyr3.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(zyr3.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(zyr3.getBank_acct())%>"></td>
								
							</tr>
							<tr id="zyr37" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(zyr3.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(zyr3.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(zyr3.getCredit_level_name())%>"></td>
							</tr>
							
							<tr id="zyr33" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr3.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr3.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(zyr3.getCard_id())%>"></td>
							</tr>
							<tr id="zyr34" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_address" size="20" maxlength="60" value="<%=Utility.trimNull(zyr3.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(zyr3.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(zyr3.getLink_man())%>"></td>
							</tr>
							<tr id="zyr35" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(zyr3.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_fax" size="20" maxlength="20" value="<%=Utility.trimNull(zyr3.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_email" size="20" maxlength="30" value="<%=Utility.trimNull(zyr3.getEmail())%>"></td>
							</tr>
							<tr id="zyr36" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea  readonly class="edline_textarea" rows="3" name="zyr3_summary" cols="60"><%=Utility.trimNull(zyr3.getSummary())%></textarea></td>
							</tr>
							
							<tr id="tr_dyr">
								<td><b>抵押人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="dyr_list" style="width: 100%" onchange="javascript: changeCustomer(this.value, document.theform);">
									<%if (dyr1.getCust_id() == null)
{%>
									<option value="">(无)</option>
									<%}
else
{%>
									<option value="">请选择</option>
									<option selected value="dyr1"><%=dyr1.getCust_name()%></option>
									<%}
if (dyr2.getCust_id() != null)
{%>
									<option value="dyr2"><%=dyr2.getCust_name()%></option>
									<%}
if (dyr3.getCust_id() != null)
{%>
									<option value="dyr3"><%=dyr3.getCust_name()%></option>
									<%}%>
								</select></td>
								<td colspan="3">
								<%if(capital_flag!=null && capital_flag.intValue()==1){%>
									<button class="xpbutton5" accessKey=n name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191104');">抵押资产(<u>N</u>)</button>
									<%}%>
								</td>
							</tr>
							<tr id="dyr11">
								<td align="right">抵押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr1.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">抵押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_ent_type_name" size="20" value="<%=Utility.trimNull(dyr1.getEnt_type_name())%>"></td>
							</tr>
							<tr id="dyr12">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_bank_name" size="20" value="<%=Utility.trimNull(dyr1.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(dyr1.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(dyr1.getBank_acct())%>"></td>
								
							</tr>
							<tr id="dyr17">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr1.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(dyr1.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(dyr1.getCredit_level_name())%>"></td>
							</tr>
							<tr id="dyr13">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr1.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr1.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(dyr1.getCard_id())%>"></td>
							</tr>
							<tr id="dyr14">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr1.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr1.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(dyr1.getLink_man())%>"></td>
							</tr>
							<tr id="dyr15">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(dyr1.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_fax" size="20" maxlength="20" value="<%=Utility.trimNull(dyr1.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_email" size="20" maxlength="30" value="<%=Utility.trimNull(dyr1.getEmail())%>"></td>
							</tr>
							<tr id="dyr16">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="dyr1_summary" cols="60"><%=Utility.trimNull(dyr1.getSummary())%></textarea></td>
							</tr>
							
							<tr id="dyr21" style="display:none">
								<td align="right">抵押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr2.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">抵押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_ent_type_name" size="20" value="<%=Utility.trimNull(dyr2.getEnt_type_name())%>"></td>
							</tr>
							<tr id="dyr22" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_bank_name" size="20" value="<%=Utility.trimNull(dyr2.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(dyr2.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(dyr2.getBank_acct())%>"></td>
							</tr>
							<tr id="dyr27" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr2.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(dyr2.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(dyr2.getCredit_level_name())%>"></td>
							</tr>
							<tr id="dyr23" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr2.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr2.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(dyr2.getCard_id())%>"></td>
							</tr>
							<tr id="dyr24" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr2.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr2.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(dyr2.getLink_man())%>"></td>
							</tr>
							<tr id="dyr25" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(dyr2.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_fax" size="20" maxlength="20" value="<%=Utility.trimNull(dyr2.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_email" size="20" maxlength="30" value="<%=Utility.trimNull(dyr2.getEmail())%>"></td>
							</tr>
							<tr id="dyr26" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="dyr2_summary" cols="60"><%=Utility.trimNull(dyr2.getSummary())%></textarea></td>
							</tr>

							<tr id="dyr31" style="display:none">
								<td align="right">抵押人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr3.getCust_name())%>"></td>
								<td align="right"></td>
								<td></td>
								<td align="right">抵押人性质:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_ent_type_name" size="20" value="<%=Utility.trimNull(dyr3.getEnt_type_name())%>"></td>
							</tr>
							<tr id="dyr32" style="display:none">
								<td align="right">开户银行:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_bank_name" size="20" value="<%=Utility.trimNull(dyr3.getBank_name())%>"></td>
								<td align="right">支行名称:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(dyr3.getBank_sub_name())%>"></td>
								<td align="right">银行账号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_bank_acct" size="20" maxlength="30" value="<%=Utility.trimNull(dyr3.getBank_acct())%>"></td>
							</tr>
							<tr id="dyr37" style="display:none">
								<td align="right">证件类型:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_card_type_name" size="20" maxlength="60" value="<%=Utility.trimNull(dyr3.getCard_type_name())%>"></td>
								<td align="right">证件号码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_card_code" size="20" maxlength="20" value="<%=Utility.trimNull(dyr3.getCard_code())%>"></td>
								<td align="right">信用级别:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_credit_level_name" size="20" maxlength="6" value="<%=Utility.trimNull(dyr3.getCredit_level_name())%>"></td>
							</tr>
							<tr id="dyr33" style="display:none">
								<td align="right">注册地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_reg_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr3.getReg_address())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_reg_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr3.getReg_post_code())%>"></td>
								<td align="right">贷款卡号:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(dyr3.getCard_id())%>"></td>
							</tr>
							<tr id="dyr34" style="display:none">
								<td align="right">通讯地址:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_address" size="20" maxlength="60" value="<%=Utility.trimNull(dyr3.getAddress())%>"></td>
								<td align="right">邮政编码:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_postcode" size="20" maxlength="6" value="<%=Utility.trimNull(dyr3.getPostcode())%>"></td>
								<td align="right">联系人:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_link_man" size="20" maxlength="10" value="<%=Utility.trimNull(dyr3.getLink_man())%>"></td>
							</tr>
							<tr id="dyr35" style="display:none">
								<td align="right">电话:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_telphone" size="20" maxlength="20" value="<%=Utility.trimNull(dyr3.getTelphone())%>"></td>
								<td align="right">传真:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_fax" size="20" maxlength="20" value="<%=Utility.trimNull(dyr3.getFax())%>"></td>
								<td align="right">Email:</td>
								<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_email" size="20" maxlength="30" value="<%=Utility.trimNull(dyr3.getEmail())%>"></td>
							</tr>
							<tr id="dyr36" style="display:none">
								<td valign="top" align="right">备注:</td>
								<td colspan="5"><textarea readonly class="edline_textarea" rows="3" name="dyr3_summary" cols="60"><%=Utility.trimNull(dyr3.getSummary())%></textarea></td>
							</tr>