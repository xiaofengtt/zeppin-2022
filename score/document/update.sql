-- ------------------------------------------------------------------------------------------
-- ----------------------------------------20190808------------------------------------------
-- ------------------------------------------------------------------------------------------
ALTER TABLE `capital_account`
ADD COLUMN `daily_max`  decimal(20,2) NOT NULL DEFAULT 0.00 AFTER `max`,
ADD COLUMN `daily_sum`  decimal(20,2) NOT NULL DEFAULT 0.00 AFTER `daily_max`;

-- ------------------------------------------------------------------------------------------
-- ----------------------------------------201908014------------------------------------------
-- ------------------------------------------------------------------------------------------

ALTER TABLE `front_user_history`
ADD COLUMN `front_user_bet`  varchar(36) NULL AFTER `front_user_bankcard`;

-- ------------------------------------------------------------------------------------------
-- ----------------------------------------201908019------------------------------------------
-- ------------------------------------------------------------------------------------------

ALTER TABLE `info_match`
ADD INDEX `INDEX_HOMETEAM_INFO` (`hometeam`) USING BTREE ,
ADD INDEX `INDEX_AWAYTEAM_INFO` (`awayteam`) USING BTREE ;



