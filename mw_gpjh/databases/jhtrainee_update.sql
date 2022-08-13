-- Add/modify columns 
alter table JHTRAINEE add fk_city varchar2(50);
alter table JHTRAINEE add fk_county varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_CITY foreign key (FK_CITY)
  references city (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_COUNTY foreign key (FK_COUNTY)
  references county (ID);
-- Add/modify columns 
alter table JHTRAINEE add fk_jobtitle varchar2(50);
alter table JHTRAINEE add fk_folk varchar2(50);
alter table JHTRAINEE add fk_main_teaching_grade varchar2(50);
alter table JHTRAINEE add fk_main_teaching_subject varchar2(50);
alter table JHTRAINEE add fk_unitattribute varchar2(50);
alter table JHTRAINEE add fk_education varchar2(50);
alter table JHTRAINEE add idcard varchar2(100);
alter table JHTRAINEE add graduation varchar2(50);
alter table JHTRAINEE add major varchar2(50);
alter table JHTRAINEE add hiredate varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_EDUCATION foreign key (FK_EDUCATION)
  references education (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_FOLK foreign key (FK_FOLK)
  references folk (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_JOBTITLE foreign key (FK_JOBTITLE)
  references job_title (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_UNITATTRITUBE foreign key (FK_UNITATTRIBUTE)
  references unit_attribute (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_MAIN_GRADE foreign key (FK_MAIN_TEACHING_GRADE)
  references main_teaching_grade (ID);
alter table JHTRAINEE
  add constraint FK_JHTRAINEE_MAIN_SUBJECT foreign key (FK_MAIN_TEACHING_SUBJECT)
  references main_teaching_subject (ID);
