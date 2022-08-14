Public Sub sl_info(s)
	MsgBox s, vbInformation, "提示"
End Sub

Public Function sl_vb_comfirm(s)
	sl_vb_comfirm = (MsgBox(s, vbYesNo + vbDefaultButton2, "提示") = vbYes)
End Function

Public Function sl_vb_comfirm2(s)
	sl_vb_comfirm = (MsgBox(s, vbYesNoCancel + vbDefaultButton2, "提示") = vbYes)
End Function

Public Function trim_comma(s)
	trim_comma = Replace(s, ",", "")
End Function

Public Sub trim_money(element)
	element.value =  trim_comma(element.value)
End Sub