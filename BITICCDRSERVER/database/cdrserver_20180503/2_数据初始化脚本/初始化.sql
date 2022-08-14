
truncate table TOPERATOR_MOBILE;

update TOUT_NUMBER set STATUS='normal';

insert INTO TOPERATOR_MOBILE 
select t.op_code FK_TOPERATOR, null as TO_MOBILE, null as TO_TEL 
from TOPERATOR t 
where t.STATUS=1;

update TOPERATOR_MOBILE set TO_MOBILE=tn.TO_MOBILE,TO_TEL=tn.TO_TEL
from 
	(
	select ROW_NUMBER() over(order by t.id) as num, t.* 
	from TOPERATOR_MOBILE t 
	where t.TO_MOBILE is null or t.TO_TEL is null
	) tm 
left join 
	(
	select ROW_NUMBER() over(order by t.id) as unum, t.TO_MOBILE, t.TO_TEL, t.[STATUS] 
	from TOUT_NUMBER t
	) tn 
on tm.num = tn.unum and tn.STATUS = 'normal'
where TOPERATOR_MOBILE.ID = tm.ID



truncate table TNUMBER_RELATION;

insert into TNUMBER_RELATION
select 'normal' as [STATUS],getdate() as CREATETIME, tc.CUST_ID FK_TCUSTOMERS, t.OP_CODE FK_TOPERATOR,
	tm.TO_MOBILE, tm.TO_TEL, tc.CUST_TEL as TC_PHONE, null as TC_TEL, '-1' as PROCESS_STATUS, tm.ID  FK_OP_MOBILE, null as EXPIRY_DATE, 3600 as MAXDURATION, 1 as ISRECORD, null as WAYBILLNUM 
from TCustomers tc 
left join TOPERATOR t 
on tc.SERVICE_MAN=t.OP_CODE 
left join TOPERATOR_MOBILE tm 
on tc.SERVICE_MAN=tm.FK_TOPERATOR
where tc.SERVICE_MAN is not null and t.OP_CODE is not null and tm.TO_MOBILE is not null and tc.STATUS != '112805' and t.STATUS = 1 and Len(tc.CUST_TEL)>=11;

insert into TNUMBER_RELATION
select 'error' as [STATUS],getdate() as CREATETIME, tc.CUST_ID FK_TCUSTOMERS, t.OP_CODE FK_TOPERATOR,
	tm.TO_MOBILE, tm.TO_TEL, tc.CUST_TEL as TC_PHONE, null as TC_TEL, '-1' as PROCESS_STATUS, tm.ID  FK_OP_MOBILE, null as EXPIRY_DATE, 3600 as MAXDURATION, 1 as ISRECORD, null as WAYBILLNUM 
from TCustomers tc 
left join TOPERATOR t 
on tc.SERVICE_MAN=t.OP_CODE 
left join TOPERATOR_MOBILE tm 
on tc.SERVICE_MAN=tm.FK_TOPERATOR
where tc.SERVICE_MAN is not null and t.OP_CODE is not null and tm.TO_MOBILE is not null and (tc.STATUS = '112805' or t.STATUS != 1 or tc.CUST_TEL is null or Len(tc.CUST_TEL)<11);

update TNUMBER_RELATION set TC_TEL=tn.TC_TEL 
from
	(
	select *,row_number() over (partition by FK_TOPERATOR order by ID desc) as num from TNUMBER_RELATION 
	)tr
	left join 
		(
		select *,row_number() over (order by ID) as num from TIN_NUMBER 
		)tn
	on tr.num = tn.num
where TNUMBER_RELATION.ID = tr.ID and tn.ID is not null

update TNUMBER_RELATION set [STATUS]='error' where TC_TEL is null