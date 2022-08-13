
function FormatAfterDotNumber( ValueString, nAfterDotNum ){
	
	var ValueString,nAfterDotNum ;
	var resultStr,nTen;
	ValueString = ""+ValueString+"";
	strLen = ValueString.length;
	dotPos = ValueString.indexOf(".",0);
	if (dotPos == -1) {
		resultStr = ValueString+".";
		for (i=0;i<nAfterDotNum ;i++) {
			resultStr = resultStr+"0";
		}
		return resultStr;
	} else {
		
		if ((strLen - dotPos - 1) >= nAfterDotNum ){
			
			nAfter = dotPos + nAfterDotNum  + 1;
			nTen =1;
			for(j=0;j<nAfterDotNum ;j++){
				
				nTen = nTen*10;
			}
			
			resultStr = Math.round(parseFloat(ValueString)*nTen)/nTen;
			return resultStr;
		} else {
			resultStr = ValueString;
			for (i=0;i<(nAfterDotNum  - strLen + dotPos + 1);i++){
				
				resultStr = resultStr+"0";
			}
			return resultStr;
		}
	}
} 