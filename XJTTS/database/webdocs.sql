select NULL AS `PID`,`document`.`ID` AS `ID`,`document`.`TITLE` AS `TITLE`,`document`.`CREATETIME` AS `CREATETIME`,1 AS `TYPE` from (`project` join `document`) where (`project`.`RED_HEAD_DOCUMENT` = `document`.`ID`) union select `project_apply`.`ID` AS `PID`,`document`.`ID` AS `ID`,`document`.`TITLE` AS `TITLE`,`document`.`CREATETIME` AS `CREATETIME`,2 AS `TYPE` from (`project_apply` join `document`) where (`project_apply`.`START_MESSAGE` = `document`.`ID`) union select NULL AS `PID`,`document`.`ID` AS `ID`,`document`.`TITLE` AS `TITLE`,`document`.`CREATETIME` AS `CREATETIME`,3 AS `TYPE` from ((`project_apply_work_report` join `project_apply`) join `document`) where ((`project_apply_work_report`.`DOCUMENT` = `document`.`ID`) and (`project_apply_work_report`.`PROJECT_APPLY` = `project_apply`.`ID`)) union select NULL AS `PID`,`mail_information`.`ID` AS `ID`,`mail_information`.`TITLE` AS `TITLE`,`mail_information`.`SENDTIME` AS `CREATETIME`,4 AS `TYPE` from `mail_information` where (`mail_information`.`TYPE` = 1)