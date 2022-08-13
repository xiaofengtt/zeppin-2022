select count(*) from prochildapplyno po where po.name like '%	%';
select po.name from prochildapplyno po where po.name like '%	%';
update prochildapplyno set name=replace(name,'	','') where name like '%	%';