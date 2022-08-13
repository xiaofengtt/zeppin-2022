var psid="";

function DoLoad(form,funtypev){
        var n;
        var i,j,k;
        var num;
        num= GetObjID('funtype[]');
        num1= GetObjID('funtypeca');

        if (!funtypev)
           return;
        k=0;
        for (i=0;i<po_ca_show.length;i++) {
         for(j = 0; j < po_detail_value[i].length; j++){
                if(funtypev.indexOf(po_detail_value[i][j])!=-1) {
                    NewOptionName = new Option(po_detail_show[i][j], po_detail_value[i][j]);
                    form.elements[num].options[k] = NewOptionName;
                    k++;
                    }
         }
        }
}

function Do_po_Change(form){
        var num,n, i, m;
        num= GetObjID('d_position1');
        m = document.select.elements[num].selectedIndex-1;
        n = document.select.elements[num + 1].length;
        for(i = n - 1; i >= 0; i--)
                document.select.elements[num + 1].options[i] = null;

        if (m>=0) {
        for(i = 0; i < po_detail_show[m].length; i++){
                NewOptionName = new Option(po_detail_show[m][i], po_detail_value[m][i]);
                document.select.elements[num + 1].options[i] = NewOptionName;
        }
        document.select.elements[num + 1].options[0].selected = true;
        }
}

function InsertItem(ObjID, Location)
{
  len=document.select.elements[ObjID].length;
  for (counter=len; counter>Location; counter--)
  {
    Value = document.select.elements[ObjID].options[counter-1].value;
    Text2Insert  = document.select.elements[ObjID].options[counter-1].text;
    document.select.elements[ObjID].options[counter] = new Option(Text2Insert, Value);
  }
}
function GetLocation(ObjID, Value)
{
  total=document.select.elements[ObjID].length;
  for (pp=0; pp<total; pp++)
     if (document.select.elements[ObjID].options[pp].text == "---"+Value+"---")
     { return (pp);
       break;
     }
  return (-1);
}

function GetObjID(ObjName)
{
  for (var ObjID=0; ObjID < window.select.elements.length; ObjID++)
    if ( window.select.elements[ObjID].name == ObjName )
    {  return(ObjID);
       break;
    }
  return(-1);
}
function ToSubmit()
{
//  if (CheckOK)
//  {
    SelectTotal('lore_select');
    SelectTotal('questiontype_select');
    document.select.submit();
//  }
}
function SelectTotal(ObjName)
{
  ObjID = GetObjID(ObjName);
  if (ObjID != -1)
  { for (i=0; i<document.select.elements[ObjID].length; i++)
      document.select.elements[ObjID].options[i].selected = true;
  }
}
function AddItem(ObjName, DesName, CatName)
{
  //GET OBJECT ID AND DESTINATION OBJECT
  ObjID    = GetObjID(ObjName);
  DesObjID = GetObjID(DesName);
//  window.alert(document.select.elements[DesObjID].length);
  k=0;
  i = document.select.elements[ObjID].options.length;
  if (i==0)
    return;
  maxselected=0
  for (h=0; h<i; h++)
     if (document.select.elements[ObjID].options[h].selected ) {
         k=k+1;
         maxselected=h+1;
         }
  if (maxselected>=i)
     maxselected=0;

  if (CatName != "")
    CatObjID = GetObjID(CatName);
  else
    CatObjID = 0;
  if ( ObjID != -1 && DesObjID != -1 && CatObjID != -1 )
  { jj = document.select.elements[CatObjID].selectedIndex;
    if ( CatName != "")
    { CatValue = document.select.elements[CatObjID].options[jj].text;
      CatCode  = document.select.elements[CatObjID].options[jj].value;
    }
    else
      CatValue = "";
    i = document.select.elements[ObjID].options.length;
    j = document.select.elements[DesObjID].options.length;
    for (h=0; h<i; h++)
    { if (document.select.elements[ObjID].options[h].selected )
      {  Code = document.select.elements[ObjID].options[h].value;
         Text = document.select.elements[ObjID].options[h].text;
         j = document.select.elements[DesObjID].options.length;
         if (Text.indexOf('--')!=-1) {
            for (k=j-1; k>=0; k-- ) {
              document.select.elements[DesObjID].options[k]=null;
            }
            j=0;
         }
         if (Text.substring(0,1)=='-' && Text.substring(1,2)!='-') {
            for (k=j-1; k>=0; k-- ) {
              if (((document.select.elements[DesObjID].options[k].value).substring(0,2))==(Code.substring(0,2)))
                 document.select.elements[DesObjID].options[k]=null;
            }
            j= document.select.elements[DesObjID].options.length;
         }
         HasSelected = false;
         for (k=0; k<j; k++ ) {
           if ((document.select.elements[DesObjID].options[k].text).indexOf('--')!=-1){
              HasSelected = true;
              window.alert('????????????????'+Text);
              break;
           }else if ((document.select.elements[DesObjID].options[k].text).indexOf('-')!=-1 && ((document.select.elements[DesObjID].options[k].value).substring(0,2)==Code.substring(0,2))){
              HasSelected = true;
              window.alert('????????????????'+Text);
              break;
           }
           if (document.select.elements[DesObjID].options[k].value == Code)
           {  HasSelected = true;
              break;
           }
         }
         if ( HasSelected == false)
         { if (CatValue !="")
           { Location = GetLocation(DesObjID, CatValue);
             if ( Location == -1 )
             { document.select.elements[DesObjID].options[j] =  new Option("---"+CatValue+"---",CatCode);
               document.select.elements[DesObjID].options[j+1] = new Option(Text, Code);
             }//if
             else
             { InsertItem(DesObjID, Location+1);
               document.select.elements[DesObjID].options[Location+1] = new Option(Text, Code);
             }//else
           }
           else
             document.select.elements[DesObjID].options[j] = new Option(Text, Code);
         }//if
         document.select.elements[ObjID].options[h].selected =false;
       }//if
    }//for
    document.select.elements[ObjID].options[maxselected].selected =true;
  }//if
}//end of function

function DeleteItem(ObjName)
{
  ObjID = GetObjID(ObjName);
  minselected=0;
  if ( ObjID != -1 )
  {
    for (i=window.select.elements[ObjID].length-1; i>=0; i--)
    {  if (window.select.elements[ObjID].options[i].selected)
       { // window.alert(i);
          if (minselected==0 || i<minselected)
            minselected=i;
          window.select.elements[ObjID].options[i] = null;
       }
    }
    i=window.select.elements[ObjID].length;

    if (i>0)  {
        if (minselected>=i)
           minselected=i-1;
        window.select.elements[ObjID].options[minselected].selected=true;
        }
  }
}
