<%
String sIsauto="";
int count_direction=Utility.parseInt(request.getParameter("count_direction"),1);
balanceTotal=crBalanceTotal;
if(count_direction==2)
	balanceTotal=drBalanceTotal;

if(iIndex==iPages)
	printcount=1;
if(user_id!=null)
{
	if(user_id.intValue()==0||user_id.intValue()==3)
	{
		sIsauto="ϵͳ����";
		if(local.getIs_auto()!=null)
		{
			if(local.getIs_auto().intValue()==2)
			sIsauto="�ֹ�¼��";
		}
}	}
%>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td ></td>
								<td colspan="4" valign="bottom" ></td>
							</tr>
							<tr>
								<td ></td>
								<td colspan="4" valign="bottom"></td> 
							</tr>
							<%if(user_id.intValue()!=4){%>
							<tr>
								<td colspan="5" align="center" height="25">
								<font size="5"><b>��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;ƾ&nbsp;&nbsp;&nbsp;֤</b></font></td>
							</tr> 
							<tr>
							
								<td width="85%" colspan="4" align="left"><b>��Ŀ����:<%=Utility.trimNull(local.getProduct_code())%> - <%=Utility.trimNull(local.getProduct_name())%><%if(local.getProduct_id()!=null){%>(<%=Argument.getTgBankAacctByProductId(local.getProduct_id())%>)<%}%></b></td>
								<td width="15%"  align="RIGHT"><%=sIsauto%></td>
							</tr>
							<tr>
								<td colspan=2 width="45%" colspan="1" align="left">��˾���ƣ�<%=application.getAttribute("COMPANY_NAME")%></td>
								<td width="20%" colspan="1" align="left">
								<%=Format.formatDateCn(finacialcard_date.intValue())%></td>
								<td width="25%" colspan="1" align="right">ƾ֤��ţ�<INPUT class="ednoline"  size=4 value="<%=local.getPost_bh()%>" ></td>
								<td width="10%" colspan="1" align="right"><INPUT class="ednoline"  size=8 value="<%=iIndex%>/<%=iPages%>" ><INPUT class="ednoline" size=4 value="<%=Utility.trimNull(local.getProduct_post_bh())%>" ></td>
							</tr>
							<%}else{%>
							<tr>
								<td colspan="5" align="center" height="25">
								<font size="4" COLOR="#e3e3e3" ><b><%=application.getAttribute("COMPANY_NAME")%></b></font></td>
							</tr>
							<tr>
								<td colspan="5" align="center" height="25">
								<font size="5"><b>��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;ƾ&nbsp;&nbsp;&nbsp;֤</b></font></td>
							</tr>
							<tr>
								<td width="90%" valign=bottom colspan="4" align="center" height="20">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<%=Format.formatDateCn(finacialcard_date.intValue())%></td>
								<td width="10%" valign=bottom  align="RIGHT" height="20"></td>
							</tr> 
							<tr>
								<td colspan=3 width="60%" colspan="1" align="left"><b>��Ŀ����:<%=Utility.trimNull(local.getProduct_name())%><%if(local.getProduct_id()!=null){%>(<%=Argument.getTgBankAacctByProductId(local.getProduct_id())%>)<%}%></b></td>
								<td width="25%" colspan="1" align="right">ƾ֤��ţ�<INPUT class="ednoline"  size=4 value="<%=local.getPost_bh()%>" ></td>
								<td width="10%" colspan="1" align="right"><INPUT class="ednoline"  size=8 value="<%=iIndex%>/<%=iPages%>" ><INPUT class="ednoline" size=4 value="<%=local.getProduct_post_bh()%>"></td>
							</tr>
							<%}%>
						    </table>
							<table  style='border-style:solid;border-width:1px;' width="100%" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0">
									<tr id="trtitle" style="display:">
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:1px;border-right-width:1px;border-top-width:1px;border-bottom-width:1px;' width="25%" align="center" height="<%=widths%>" ><font size="2"><b>ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ</b></font></td>
										<td bgcolor="#FFFFFF" width="45%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:1px;border-bottom-width:1px;' align="center" height="<%=widths%>" ><font size="2"><b>��&nbsp;��&nbsp;��&nbsp;Ŀ</b></font></td>
										<td bgcolor="#FFFFFF" width="15%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:1px;border-bottom-width:1px;' align="center" height="<%=widths%>"><font size="2"><b>��&nbsp;��</b></font></td>
										<td bgcolor="#FFFFFF" width="15%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:1px;border-bottom-width:1px;' align="center" height="<%=widths%>" ><font size="2"><b>��&nbsp;��</b></font></td>
									</tr>

									<%  iIndex++;
										iCurrent=0;
										pageflag=0;
										
									}
									int curList_id=listLocal.getList_id()==null?0:listLocal.getList_id().intValue();
									if(iCurrent<rowlnum)
									{
									%>
									<tr >
									
										<td <%if(curList_id>0 && list_id>0 && curList_id==list_id) out.print("bgcolor='#EEF7F7'");else out.print("bgcolor='#FFFFFF'"); %>  style='border-style:solid;border-left-width:1px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="25%" align="left" height="<%=widths%>"><%if(Utility.trimNull(listLocal.getDescription()).getBytes().length>72)out.print("<font size=1>");%><%=Utility.trimNull(listLocal.getDescription())%>&nbsp;</td>
										<td <%if(curList_id>0 && list_id>0 && curList_id==list_id) out.print("bgcolor='#EEF7F7'");else out.print("bgcolor='#FFFFFF'"); %> style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="45%" align="left" height="<%=widths%>"><%if(Utility.trimNull(listLocal.getSub_name()).getBytes().length>134)out.print("<font size=1>");%><%=Utility.trimNull(listLocal.getSub_name())%></td>
										<td <%if(curList_id>0 && list_id>0 && curList_id==list_id) out.print("bgcolor='#EEF7F7'");else out.print("bgcolor='#FFFFFF'"); %> style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp; <%=Format.formatMoney(listLocal.getCr_balance())%></td>
										<td <%if(curList_id>0 && list_id>0 && curList_id==list_id) out.print("bgcolor='#EEF7F7'");else out.print("bgcolor='#FFFFFF'"); %> style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp; <%=Format.formatMoney(listLocal.getDr_balance())%></td>
									</tr>
									<%}
									if((iCurrent>0) && ((iCurrent%(rowlnum-1))==0))
									{//���iRowsѭ�������,��������Ϊ��������ʾ�ϼ�:%>
									<tr>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:1px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="25%" align="center" height="<%=widths%>">������<%=Utility.parseInt(local.getAdditional_num(),new Integer(0))%>��</td>
										
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="45%" align="left" height="<%=widths%>">&nbsp;<%if(iRows==(iRowcount-1))out.print("�ϼƣ�"+Utility.numToChinese(balanceTotal.toString()));%></td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp;<%=Format.formatMoney(crBalanceTotal)%></td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp;<%=Format.formatMoney(drBalanceTotal)%></td>
									</tr>
									</table>
									<%if(user_id.intValue()==2){%>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
                          				<tr>
                            			<td width="40%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ܻ�ƣ�&nbsp;<INPUT class="ednoline"  size=8 value="������" ></td>
                            			<td width="30%" align="left">���ˣ�<INPUT class="ednoline"  size=8 value="<%=Utility.trimNull(Argument.getOpName(local.getCheck_man()))%>" ></td>
                            			<td width="30%" align="left">�Ƶ���<INPUT class="ednoline"  size=8 value="<%=Utility.trimNull(Argument.getOpName(local.getInput_man()))%>" ></td>
                          				</tr>
                        	   		</table>
									<%}else{%>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
                          				<tr>
                            			<td width="26%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ˣ�<INPUT class="ednoline"  size=6 value="" ></td>
                            			<td width="20%" align="left">����:<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getPost_man()))%>" ></td>
                            			<td width="20%" align="left">���ˣ�<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getCheck_man()))%>" ></td>
                            			<td width="17%" align="left">����:<INPUT class="ednoline"  size=6 value="" ></td>
                            			<td width="17%" align="left">�Ƶ���<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getInput_man()))%>" ></td>
                          				</tr>
                        	   		</table>
                        	   		<%}%>
                        	 		</TD>
									</TR>
								</TBODY>
								</TABLE>
									<%
									 pageflag=1;
									  }
									  iCount++;
									  iCurrent++;
									}
									for (; iCurrent < rowlnum; iCurrent++)
									{
									%>

									<tr>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:1px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="25%" align="center" height="<%=widths%>">&nbsp;</td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="45%" align="left" height="<%=widths%>">&nbsp;</td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>">&nbsp;</td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>">&nbsp;</td>
									</tr>
									<%}
									if(pageflag==0)
									{
									%>
									<tr>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:1px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="25%" align="center" height="<%=widths%>">������<%=local.getAdditional_num()%>��</td> 									
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="45%" align="left" height="<%=widths%>">&nbsp;<%if(printcount==1) out.print("�ϼƣ�"+Utility.numToChinese(balanceTotal.toString()));%></td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp;<%=Format.formatMoney(crBalanceTotal)%></td>
										<td bgcolor="#FFFFFF" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' width="15%" align="right" height="<%=widths%>" <%if(printflag==0 && user_id.intValue()!=4){%> class=cardfont <%}%>>&nbsp;<%=Format.formatMoney(drBalanceTotal)%></td>
									</tr>
									</table>
									<%if(user_id.intValue()==2){%>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
                          				<tr>
                            			<td width="40%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ܻ�ƣ�&nbsp;<INPUT class="ednoline"  size=8 value="������" ></td>
                            			<td width="30%" align="left">���ˣ�<INPUT class="ednoline"  size=8 value="<%=Utility.trimNull(Argument.getOpName(local.getCheck_man()))%>" ></td>
                            			<td width="30%" align="left">�Ƶ���<INPUT class="ednoline"  size=8 value="<%=Utility.trimNull(Argument.getOpName(local.getInput_man()))%>" ></td>
                          				</tr>
                        	   		</table>
									<%}else{%>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
                          				<tr>
                            			<td width="26%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ˣ�<INPUT class="ednoline"  size=6 value="" ></td>
                            			<td width="20%" align="left">����:<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getPost_man()))%>" ></td>
                            			<td width="20%" align="left">���ˣ�<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getCheck_man()))%>" ></td>
                            			<td width="17%" align="left">����:<INPUT class="ednoline"  size=6 value="" ></td>
                            			<td width="17%" align="left">�Ƶ���<INPUT class="ednoline"  size=6 value="<%=Utility.trimNull(Argument.getOpName(local.getInput_man()))%>" ></td>
                          				</tr>
                        	   		</table>
                        	   		<%}%>
                        	   		</TD>
								</TR>
			   				</TBODY>
							</TABLE>	
							<%}%>