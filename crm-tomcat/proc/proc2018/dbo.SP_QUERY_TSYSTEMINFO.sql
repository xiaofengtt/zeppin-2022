﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSYSTEMINFO @IN_USER_ID     SMALLINT =0,
                                      @IN_SYSTEM_ID   INT =0
WITH ENCRYPTION
AS
    IF @IN_USER_ID IS NULL OR @IN_USER_ID =''
        SELECT @IN_USER_ID =0
    IF @IN_USER_ID <>0
        BEGIN
            SELECT A.USER_ID,A.USER_NAME,A.ADDRESS,A.POST_CODE,A.TELEPHONE,B.BACKUP_URL,ISNULL(A.USER_NAME_EN,A.USER_NAME) AS USER_NAME_EN,
                            B.APPLICATION_NAME,INTRUST_ADDRESS
                FROM TUSERINFO A,TSYSTEMINFO B
                WHERE A.USER_ID = B.USER_ID
                AND A.USER_ID = @IN_USER_ID
        END
    ELSE
        BEGIN
            SELECT A.USER_ID,A.USER_NAME,A.ADDRESS,A.POST_CODE,A.TELEPHONE,B.BACKUP_URL,ISNULL(A.USER_NAME_EN,A.USER_NAME) AS USER_NAME_EN,
                            B.APPLICATION_NAME,INTRUST_ADDRESS
                FROM TUSERINFO A,TSYSTEMINFO B
                WHERE A.USER_ID = B.USER_ID
                AND B.SYSTEM_ID = @IN_SYSTEM_ID
        END
GO