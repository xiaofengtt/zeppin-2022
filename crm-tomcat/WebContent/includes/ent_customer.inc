<%
bzr1.setCust_id(contract.getBzr_cust_id1());
bzr2.setCust_id(contract.getBzr_cust_id2());
bzr3.setCust_id(contract.getBzr_cust_id3());
bzr4.setCust_id(contract.getBzr_cust_id4());

dyr1.setCust_id(contract.getDyr_cust_id1());
dyr2.setCust_id(contract.getDyr_cust_id2());
dyr3.setCust_id(contract.getDyr_cust_id3());

zyr1.setCust_id(contract.getZyr_cust_id1());
zyr2.setCust_id(contract.getZyr_cust_id2());
zyr3.setCust_id(contract.getZyr_cust_id3());

if (bzr1.getCust_id() != null &&  bzr1.getCust_id().intValue() !=0)	
	bzr1 = bzr1bean.load(bzr1);
	
if (bzr2.getCust_id() != null &&  bzr2.getCust_id().intValue() !=0 )	
	bzr2 = bzr2bean.load(bzr2);

if (bzr3.getCust_id() != null &&  bzr3.getCust_id().intValue() !=0)	
	bzr3 = bzr3bean.load(bzr3);
	
if (bzr4.getCust_id() != null &&  bzr4.getCust_id().intValue() !=0 )	
	bzr4 = bzr4bean.load(bzr4);

if (dyr1.getCust_id() != null && dyr1.getCust_id().intValue() !=0)	
	dyr1 = dyr1bean.load(dyr1);
	
if (dyr2.getCust_id() != null && dyr2.getCust_id().intValue() !=0)	
	dyr2 = dyr2bean.load(dyr2);
	
if (dyr3.getCust_id() != null && dyr3.getCust_id().intValue() !=0)	
	dyr3 = dyr3bean.load(dyr3);

if (zyr1.getCust_id() != null && zyr1.getCust_id().intValue() !=0)	
	zyr1 = zyr1bean.load(zyr1);
	
if (zyr2.getCust_id() != null && zyr2.getCust_id().intValue() !=0)	
	zyr2 = zyr2bean.load(zyr2);
	
if (zyr3.getCust_id() != null && zyr3.getCust_id().intValue() !=0)	
	zyr3 = zyr3bean.load(zyr3);

%>
							<tr id="tr_bzr">
								<td><b>保证人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="bzr_list" style="width: 100%" onchange="javascript:changeCustomer(this.value, document.theform);">
									<%if ((bzr1.getCust_id() != null && bzr1.getCust_id().intValue() == 0) && (bzr2.getCust_id() != null &&bzr2.getCust_id().intValue() == 0) && (bzr3.getCust_id() != null &&bzr3.getCust_id().intValue() == 0) && (bzr4.getCust_id() != null &&bzr4.getCust_id().intValue() == 0))
{%>
									<option value="">(无)</option>
									<%}
else
{%>
									<option value="">请选择</option>
									<%}
if (bzr1.getCust_id() != null &&  bzr1.getCust_id().intValue() !=0)
{%>
									<option selected value="bzr1"><%=bzr1.getCust_name()%></option>									
									<%}
if (bzr2.getCust_id() != null &&  bzr2.getCust_id().intValue() !=0)
{%>
									<option value="bzr2"><%=bzr2.getCust_name()%></option> 
									<%}
if (bzr3.getCust_id() != null &&  bzr3.getCust_id().intValue() !=0)
{%>
									<option value="bzr3"><%=bzr3.getCust_name()%></option> 
									<%}
if (bzr4.getCust_id() != null  &&  bzr4.getCust_id().intValue() !=0)
{%>
									<option value="bzr4"><%=bzr4.getCust_name()%></option> 
									<%}%>  
								</select></td> 
								<td colspan="3"><%if( readonly != 1 ){%>
									<button class="xpbutton3"  accessKey=a id="bzr_btnAdd" name="bzr_btnAdd" onclick="javascript: addCustomer('bzr', document.theform);">添加(<u>A</u>)</button>&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=r id="bzr_btnRemove" name="bzr_btnRemove" onclick="javascript: removeCustomer('bzr', document.theform);">删除(<u>R</u>)</button>&nbsp;&nbsp;
									<%}if(capital_flag!=null && capital_flag.intValue()==1){%>	
									<button class="xpbutton5" accessKey=n name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191102');">保证资产(<u>N</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=b name="btnNew"  title="保证人" onclick="javascript:getCustomer2('bzr');">保证人(<u>B</u>)</button>
									<%}%>
								</td>
							</tr>
								
							
							<tr id="bzr11">
								<td align="right">客户类型:</td>
								<td>
									<%if(bzr1.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_cust_type" size="18" maxlength="60" value="<%if(bzr1.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(bzr1.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(bzr1.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="bzr1_is_link" value="<%=Utility.trimNull(bzr1.getIs_link())%>" <%if(bzr1.getIs_link()!=null){if(bzr1.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr> 
							<tr id="bzr12">
								<td align=right>保证人1:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(bzr1.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_voc_type_name" size="31" value="<%=Utility.trimNull(bzr1.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="bzr13">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_address" size="70" maxlength="60" value="<%=Utility.trimNull(bzr1.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(bzr1.getTelphone())%>">
								</td>
							</tr>
							<tr id="bzr14">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(bzr1.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								证件号码:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(bzr1.getCard_code())%>">
								</td>
							</tr>		
							<tr id="bzr15">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_bank_sub_name" size="30" value=" <%=Utility.trimNull(bzr1.getBank_name())%><%=Utility.trimNull(bzr1.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
									银行账号:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr1_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(bzr1.getBank_acct())%>">
								</td>
							</tr>
							<tr id="bzr21" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(bzr2.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_cust_type" size="18" maxlength="60" value="<%if(bzr2.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(bzr2.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(bzr2.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="bzr2_is_link" value="<%=Utility.trimNull(bzr2.getIs_link())%>" <%if(bzr2.getIs_link()!=null){if(bzr2.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="bzr22" style="display: none">
								<td align=right>保证人2:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(bzr2.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_voc_type_name" size="31" value="<%=Utility.trimNull(bzr2.getVoc_type_name())%>">			
								</td>	
						
							<tr id="bzr23" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_address" size="70" maxlength="60" value="<%=Utility.trimNull(bzr2.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(bzr2.getTelphone())%>">
								</td>
							</tr>
							<tr id="bzr24" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(bzr2.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(bzr2.getCard_code())%>">
								</td>
							</tr>		
							<tr id="bzr25" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_bank_sub_name" size="30" value=" <%=Utility.trimNull(bzr2.getBank_name())%><%=Utility.trimNull(bzr2.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr2_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(bzr2.getBank_acct())%>">
								</td>
							</tr>
							<tr id="bzr31" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(bzr3.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_cust_type" size="18" maxlength="60" value="<%if(bzr3.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(bzr3.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(bzr3.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="bzr3_is_link" value="<%=Utility.trimNull(bzr3.getIs_link())%>" <%if(bzr3.getIs_link()!=null){if(bzr3.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="bzr32" style="display: none">
								<td align=right>保证人3:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(bzr3.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_voc_type_name" size="31" value="<%=Utility.trimNull(bzr3.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="bzr33" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_address" size="70" maxlength="60" value="<%=Utility.trimNull(bzr3.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(bzr3.getTelphone())%>">
								</td>
							</tr>
							<tr id="bzr34" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(bzr3.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(bzr3.getCard_code())%>">
								</td>
							</tr>		
							<tr id="bzr35" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_bank_sub_name" size="30" value=" <%=Utility.trimNull(bzr3.getBank_name())%><%=Utility.trimNull(bzr3.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr3_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(bzr3.getBank_acct())%>">
								</td>
							</tr>	
							<input type="hidden" name="bzr3" value="<%=Utility.trimNull(bzr3.getCust_id())%>">
							<tr id="bzr41">
								<td align="right">客户类型:</td>
								<td>
									<%if(bzr4.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_cust_type" size="18" maxlength="60" value="<%if(bzr4.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(bzr4.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(bzr4.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="bzr4_is_link" value="<%=Utility.trimNull(bzr4.getIs_link())%>" <%if(bzr4.getIs_link()!=null){if(bzr4.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr> 
							<tr id="bzr42" style="display: none">
								<td align=right>保证人4:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(bzr4.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_voc_type_name" size="31" value="<%=Utility.trimNull(bzr4.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="bzr43" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_address" size="70" maxlength="60" value="<%=Utility.trimNull(bzr4.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(bzr4.getTelphone())%>">
								</td>
							</tr>
							<tr id="bzr44" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(bzr4.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								证件号码:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(bzr4.getCard_code())%>">
								</td>
							</tr>		
							<tr id="bzr45" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_bank_sub_name" size="30" value=" <%=Utility.trimNull(bzr4.getBank_name())%><%=Utility.trimNull(bzr4.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
									银行账号:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bzr4_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(bzr4.getBank_acct())%>">
								</td>
							</tr>
							<tr id="tr_zyr">
								<td><b>质押人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="zyr_list" style="width: 100%" onchange="javascript: changeCustomer(this.value, document.theform);">
									<%if (zyr1.getCust_id() != null &&zyr1.getCust_id().intValue() == 0)
{%>
									<option value="">(无)</option>
									<%}
else
{%>
									<option value="">请选择</option>
									<option selected value="zyr1"><%=zyr1.getCust_name()%></option>
									<%}%>
									
									<%
if (zyr2.getCust_id() != null && zyr2.getCust_id().intValue() !=0)
{%>
									<option value="zyr2"><%=zyr2.getCust_name()%></option>
									<%}
if (zyr3.getCust_id() != null && zyr3.getCust_id().intValue() !=0)
{%>
									<option value="zyr3"><%=zyr3.getCust_name()%></option>
									<%}%>
								</select></td>
								<td colspan="3"><%if( readonly != 1 ){%>
									<button class="xpbutton3" accessKey=a id="zyr_btnAdd" name="zyr_btnAdd" onclick="javascript: addCustomer('zyr', document.theform);">添加(<u>A</u>)</button>&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=r id="zyr_btnRemove" name="zyr_btnRemove" onclick="javascript: removeCustomer('zyr', document.theform);">删除(<u>R</u>)</button>&nbsp;&nbsp;
									<%}if(capital_flag!=null && capital_flag.intValue()==1){%>
									<button class="xpbutton5" accessKey=n name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191103');">质押资产(<u>N</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=Z name="btnNew"  title="质押人" onclick="javascript:getCustomer2('zyr');">质押人(<u>Z</u>)</button>
									<%}%>
								</td>
							</tr>
							<tr id="zyr11">
								<td align="right">客户类型:</td>
								<td>
									<%if(zyr1.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_cust_type" size="18" maxlength="60" value="<%if(zyr1.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(zyr1.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(zyr1.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="zyr1_is_link" value="<%=Utility.trimNull(zyr1.getIs_link())%>" <%if(zyr1.getIs_link()!=null){if(zyr1.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="zyr12">
								<td align=right>质押人1:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(zyr1.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_voc_type_name" size="31" value="<%=Utility.trimNull(zyr1.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="zyr13">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_address" size="70" maxlength="60" value="<%=Utility.trimNull(zyr1.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(zyr1.getTelphone())%>">
								</td>
							</tr>
							<tr id="zyr14">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(zyr1.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(zyr1.getCard_code())%>">
								</td>
							</tr>		
							<tr id="zyr15">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_bank_sub_name" size="30" value=" <%=Utility.trimNull(zyr1.getBank_name())%><%=Utility.trimNull(zyr1.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr1_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(zyr1.getBank_acct())%>">
								</td>
							</tr>
							
							<tr id="zyr21" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(zyr2.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_cust_type" size="18" maxlength="60" value="<%if(zyr2.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(zyr2.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(zyr2.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="zyr2_is_link" value="<%=Utility.trimNull(zyr2.getIs_link())%>" <%if(zyr2.getIs_link()!=null){if(zyr2.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="zyr22" style="display: none">
								<td align=right>质押人2:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(zyr2.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_voc_type_name" size="31" value="<%=Utility.trimNull(zyr2.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="zyr23" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_address" size="70" maxlength="60" value="<%=Utility.trimNull(zyr2.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(zyr2.getTelphone())%>">
								</td>
							</tr>
							<tr id="zyr24" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(zyr2.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(zyr2.getCard_code())%>">
								</td>
							</tr>		
							<tr id="zyr25" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_bank_sub_name" size="30" value=" <%=Utility.trimNull(zyr2.getBank_name())%><%=Utility.trimNull(zyr2.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr2_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(zyr2.getBank_acct())%>">
								</td>
							</tr>	
							<tr id="zyr31" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(zyr3.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_cust_type" size="18" maxlength="60" value="<%if(zyr3.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(zyr3.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(zyr3.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="zyr3_is_link" value="<%=Utility.trimNull(zyr3.getIs_link())%>" <%if(zyr3.getIs_link()!=null){if(zyr3.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="zyr32" style="display: none">
								<td align=right>质押人3:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(zyr3.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_voc_type_name" size="31" value="<%=Utility.trimNull(zyr3.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="zyr33" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_address" size="70" maxlength="60" value="<%=Utility.trimNull(zyr3.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(zyr3.getTelphone())%>">
								</td>
							</tr>
							<tr id="zyr34" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(zyr3.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(zyr3.getCard_code())%>">
								</td>
							</tr>		
							<tr id="zyr35" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_bank_sub_name" size="30" value=" <%=Utility.trimNull(zyr3.getBank_name())%><%=Utility.trimNull(zyr3.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="zyr3_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(zyr3.getBank_acct())%>">
								</td>
							</tr>	
							 
							<tr id="tr_dyr">
								<td><b>抵押人信息</b></td>
								<td colspan="2"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="dyr_list" style="width: 100%" onchange="javascript: changeCustomer(this.value, document.theform);">
									<%if (dyr1.getCust_id() != null && dyr1.getCust_id().intValue() == 0)
{%>
									<option value="">(无)</option>
									<%}
else
{%>
									<option value="">请选择</option> 
									<option selected value="dyr1"><%=dyr1.getCust_name()%></option>
									<%}
if (dyr2.getCust_id() != null && dyr2.getCust_id().intValue() !=0)
{%>
									<option value="dyr2"><%=dyr2.getCust_name()%></option>
									<%} 
if (dyr3.getCust_id() != null && dyr3.getCust_id().intValue() !=0)
{%>
									<option value="dyr3"><%=dyr3.getCust_name()%></option>
									<%}%>
								</select></td>
								<td colspan="3"><%if( readonly != 1 ){%>
									<button class="xpbutton3" accessKey=a accessKey=a id="dyr_btnAdd" name="dyr_btnAdd" onclick="javascript: addCustomer('dyr', document.theform);">添加(<u>A</u>)</button>&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=r id="dyr_btnRemove" name="dyr_btnRemove" onclick="javascript: removeCustomer('dyr', document.theform);">删除(<u>R</u>)</button>&nbsp;&nbsp;
									<%}if(capital_flag!=null && capital_flag.intValue()==1){%>
									<button class="xpbutton5" accessKey=n name="btnNew"  title="查看" onclick="javascript:showCapitalDeatil(0,'','191104');">抵押资产(<u>N</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton3" accessKey=D name="btnNew"  title="抵押人" onclick="javascript:getCustomer2('dyr');">抵押人(<u>D</u>)</button>
									<%}%>
								</td>
							</tr>
							<tr id="dyr11">
								<td align="right">客户类型:</td>
								<td>
									<%if(dyr1.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_cust_type" size="18" maxlength="60" value="<%if(dyr1.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(dyr1.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(dyr1.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="dyr1_is_link" value="<%=Utility.trimNull(dyr1.getIs_link())%>" <%if(dyr1.getIs_link()!=null){if(dyr1.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="dyr12">
								<td align=right>抵押人1:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(dyr1.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_voc_type_name" size="31" value="<%=Utility.trimNull(dyr1.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="dyr13">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_address" size="70" maxlength="60" value="<%=Utility.trimNull(dyr1.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(dyr1.getTelphone())%>">
								</td>
							</tr>
							<tr id="dyr14">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(dyr1.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(dyr1.getCard_code())%>">
								</td>
							</tr>		
							<tr id="dyr15">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_bank_sub_name" size="30" value=" <%=Utility.trimNull(dyr1.getBank_name())%><%=Utility.trimNull(dyr1.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr1_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(dyr1.getBank_acct())%>">
								</td>
							</tr>
						
							<tr id="dyr21" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(dyr2.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_cust_type" size="18" maxlength="60" value="<%if(dyr2.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(dyr2.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(dyr2.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="dyr2_is_link" value="<%=Utility.trimNull(dyr2.getIs_link())%>" <%if(dyr2.getIs_link()!=null){if(dyr2.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="dyr22" style="display: none">
								<td align=right>抵押人2:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(dyr2.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_voc_type_name" size="31" value="<%=Utility.trimNull(dyr2.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="dyr23" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_address" size="70" maxlength="60" value="<%=Utility.trimNull(dyr2.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(dyr2.getTelphone())%>">
								</td>
							</tr>
							<tr id="dyr24" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(dyr2.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(dyr2.getCard_code())%>">
								</td>
							</tr>		
							<tr id="dyr25" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_bank_sub_name" size="30" value=" <%=Utility.trimNull(dyr2.getBank_name())%><%=Utility.trimNull(dyr2.getBank_sub_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr2_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(dyr2.getBank_acct())%>">
								</td>
							</tr>

							<tr id="dyr31" style="display: none">
								<td align="right">客户类型:</td>
								<td>
									<%if(dyr3.getCust_type() != null){%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_cust_type" size="18" maxlength="60" value="<%if(dyr3.getCust_type().intValue()==1){out.print("机构");}else{out.print("个人");}%>">
									<%}else{%>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_cust_type" size="18" maxlength="60" value="<%=Utility.trimNull(dyr3.getCust_type())%>">
									<%}%>
								</td>	
								<td align="right">信用级别:</td>
								<td>
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_credit_level_name" size="18" maxlength="20" value="<%=Utility.trimNull(dyr3.getCredit_level_name())%>">
								</td>
								<td align="right">关 联 方:</td>
								<td>
									<input type="checkbox" disabled name="dyr3_is_link" value="<%=Utility.trimNull(dyr3.getIs_link())%>" <%if(dyr3.getIs_link()!=null){if(dyr3.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox">
								</td>	
							</tr>
							<tr id="dyr32" style="display: none">
								<td align=right>抵押人3:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_cust_name" size="70" maxlength="60" value="<%=Utility.trimNull(dyr3.getCust_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行业/职业:
									 <input class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_voc_type_name" size="31" value="<%=Utility.trimNull(dyr3.getVoc_type_name())%>">			
								</td>	
							</tr>
							<tr id="dyr33" style="display: none">
								<td align="right">通讯地址:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_address" size="70" maxlength="60" value="<%=Utility.trimNull(dyr3.getAddress())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_telphone" size="31" maxlength="20" value="<%=Utility.trimNull(dyr3.getTelphone())%>">
								</td>
							</tr>
							<tr id="dyr34" style="display: none">
								<td align="right">证件类型:</td>
								<td colspan="5">
									<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_card_type_name" size="30" maxlength="60" value="<%=Utility.trimNull(dyr3.getCard_type_name())%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
								证件号码:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_card_code" size="49" maxlength="20" value="<%=Utility.trimNull(dyr3.getCard_code())%>">
								</td>
							</tr>		
							<tr id="dyr35" style="display: none">
								<td align="right">开户银行:</td>
								<td colspan="5"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_bank_sub_name" size="30" value=" <%=Utility.trimNull(dyr3.getBank_name())%><%=Utility.trimNull(dyr3.getBank_sub_name())%>">
								银行账号:
								<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="dyr3_bank_acct" size="49" maxlength="30" value="<%=Utility.trimNull(dyr3.getBank_acct())%>">
								</td>
							</tr>