INSERT INTO `fun_category` (`ID`, `NAME`, `PID`, `PATH`, `LEVEL`, `CODE`)
VALUES
(44,'调整学员培训学科',12,'../admin/trainingStudentOpt_changeSubjectInit.action',2,1);

INSERT INTO `orga_cate_map` (`ID`, `CATEGORY`, `ORGANIZATION`, `ROLEID`)
VALUES (80,44,NULL,2);