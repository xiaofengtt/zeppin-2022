-- Add/modify columns 
alter table TRAINEE add fk_city varchar2(50);
alter table TRAINEE add fk_county varchar2(50);
alter table TRAINEE add fk_jobtitle varchar2(50);
alter table TRAINEE add fk_folk varchar2(50);
alter table TRAINEE add fk_main_teaching_grade varchar2(50);
alter table TRAINEE add fk_main_teaching_subject varchar2(50);
alter table TRAINEE add fk_unitattribute varchar2(50);
alter table TRAINEE add fk_education varchar2(50);
alter table TRAINEE add idcard varchar2(100);
alter table TRAINEE add graduation varchar2(50);
alter table TRAINEE add major varchar2(50);
alter table TRAINEE add hiredate varchar2(50);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TRAINEE
  add constraint FK_TRAINEE_CITY foreign key (FK_CITY)
  references city (ID);
alter table TRAINEE
  add constraint FK_TRAINEE_COUNTY foreign key (FK_COUNTY)
  references county (ID);
alter table TRAINEE
  add constraint FK_EDUCATION foreign key (FK_EDUCATION)
  references education (ID);
alter table TRAINEE
  add constraint FK_FOLK foreign key (FK_FOLK)
  references folk (ID);
alter table TRAINEE
  add constraint FK_JOBTITLE foreign key (FK_JOBTITLE)
  references job_title (ID);
alter table TRAINEE
  add constraint FK_MAIN_GRADE foreign key (FK_MAIN_TEACHING_GRADE)
  references main_teaching_grade (ID);
alter table TRAINEE
  add constraint FK_MAIN_SUBJECT foreign key (FK_MAIN_TEACHING_SUBJECT)
  references main_teaching_subject (ID);
alter table TRAINEE
  add constraint FK_UNITATTRIBUTE foreign key (FK_UNITATTRIBUTE)
  references unit_attribute (ID);
