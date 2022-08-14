﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TDICTPARAM_9999 @IN_TYPE_ID          INT,
                                          @IN_TYPE_VALUE       NVARCHAR (10)  ='',
                                          @IN_SERIAL_NO        INT = 0,
                                          @IN_QUERY_FLAG       INT = 0  --查询类别，为了不影响原过程的输出而加入：1仅查省份下的市；2仅查市级下的区/县
WITH ENCRYPTION
AS
	IF ISNULL(@IN_QUERY_FLAG,0)=1
		SELECT * FROM TDICTPARAM WHERE TYPE_ID =9999 AND TYPE_VALUE LIKE SUBSTRING(@IN_TYPE_VALUE,1,6)+'__00' AND TYPE_VALUE<>SUBSTRING(@IN_TYPE_VALUE,1,6)+'0000'
	ELSE IF ISNULL(@IN_QUERY_FLAG,0)=2
		SELECT * FROM TDICTPARAM WHERE TYPE_ID =9999 AND TYPE_VALUE LIKE SUBSTRING(@IN_TYPE_VALUE,1,8)+'__' AND TYPE_VALUE<>SUBSTRING(@IN_TYPE_VALUE,1,8)+'00'
    ELSE IF ISNULL(@IN_SERIAL_NO,0) <>0
        SELECT * FROM TDICTPARAM WHERE SERIAL_NO =@IN_SERIAL_NO
    ELSE IF ISNULL(@IN_TYPE_ID,0) =9999 AND ISNULL(@IN_TYPE_VALUE,'') =''       --查省级地名
        SELECT * FROM TDICTPARAM WHERE TYPE_ID =9999 AND TYPE_VALUE LIKE'______0000'
    ELSE IF ISNULL(@IN_TYPE_ID,0) =9999 AND ISNULL(@IN_TYPE_VALUE,'') <>''       --查省级地名
        SELECT * FROM TDICTPARAM WHERE TYPE_ID =9999 AND TYPE_VALUE LIKE SUBSTRING(@IN_TYPE_VALUE,1,6)+'%'

GO
