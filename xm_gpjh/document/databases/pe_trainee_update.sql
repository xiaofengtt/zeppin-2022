-- Add/modify columns 
alter table PE_TRAINEE add cityy varchar2(50);
alter table PE_TRAINEE add countyy varchar2(50);

-- Add/modify columns 
alter table PE_TRAINEE add fk_city varchar2(50);
alter table PE_TRAINEE add fk_county varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_CITY foreign key (FK_CITY)
  references city (ID);
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_COUNTY foreign key (FK_COUNTY)
  references county (ID);

  -- Add/modify columns 
alter table PE_TRAINEE add fk_folk varchar2(50);
alter table PE_TRAINEE add fk_education varchar2(50);
alter table PE_TRAINEE add fk_jobtitle varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_FOLK foreign key (FK_FOLK)
  references folk (ID);
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_EDUCATION foreign key (FK_EDUCATION)
  references education (ID);
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_JOBTITLE foreign key (FK_JOBTITLE)
  references job_title (ID);

  -- Add/modify columns 
alter table PE_TRAINEE add idcard varchar2(100);
alter table PE_TRAINEE add graduation varchar2(50);
alter table PE_TRAINEE add major varchar2(50);
alter table PE_TRAINEE add hiredate date;
-- Add comments to the columns 
comment on column PE_TRAINEE.idcard
  is '身份证号';
comment on column PE_TRAINEE.graduation
  is '毕业院校';
comment on column PE_TRAINEE.major
  is '所学专业';
comment on column PE_TRAINEE.hiredate
  is '入职时间';

  -- Add/modify columns 
alter table PE_TRAINEE add fk_main_teaching_subject varchar2(50);
alter table PE_TRAINEE add fk_main_teaching_grade varchar2(50);
-- Add comments to the columns 
comment on column PE_TRAINEE.fk_main_teaching_subject
  is '主要教学学科';
comment on column PE_TRAINEE.fk_main_teaching_grade
  is '主要教学学段';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_MAIN_SUBJECT foreign key (FK_MAIN_TEACHING_SUBJECT)
  references main_teaching_subject (ID);
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_MAIN_GRADE foreign key (FK_MAIN_TEACHING_GRADE)
  references main_teaching_grade (ID);

-- Add/modify columns 
alter table PE_TRAINEE add fk_unit_attribute varchar2(50);
-- Add comments to the columns 
comment on column PE_TRAINEE.fk_unit_attribute
  is '单位属性';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_TRAINEE
  add constraint FK_PE_TRAINEE_UNITATTRIBUTE foreign key (FK_UNIT_ATTRIBUTE)
  references unit_attribute (ID);
