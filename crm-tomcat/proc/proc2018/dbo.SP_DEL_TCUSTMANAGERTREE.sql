﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TCUSTMANAGERTREE    @IN_SERIAL_NO       INT,            --节点客户经理序列ID
                                            @IN_INPUT_MAN       INT = 0         --操作员

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'删除节点客户经理', @SSUMMARY = N'删除节点客户经理'
    DECLARE @V_LEFT_ID INT,@V_RIGHT_ID INT
    IF EXISTS (SELECT 1 FROM TCustManagerTree WHERE SERIAL_NO = @IN_SERIAL_NO )
    BEGIN
        SET XACT_ABORT ON
        BEGIN TRANSACTION
        SELECT @V_LEFT_ID=LEFT_ID,@V_RIGHT_ID=RIGHT_ID FROM TCustManagerTree WHERE SERIAL_NO = @IN_SERIAL_NO
        --删除节点及其下属节点的成员
    DELETE FROM TCustManagerTreeMembers
            WHERE TREE_ID IN (SELECT SERIAL_NO FROM TCustManagerTree WHERE LEFT_ID>=@V_LEFT_ID AND RIGHT_ID<=@V_RIGHT_ID)
    --删除节点及其下属节点
        DELETE FROM TCustManagerTree WHERE LEFT_ID>=@V_LEFT_ID AND RIGHT_ID<=@V_RIGHT_ID
        ---更改其他节点的左右值
        UPDATE TCustManagerTree SET LEFT_ID=LEFT_ID-(@V_RIGHT_ID-@V_LEFT_ID+1) WHERE LEFT_ID>@V_LEFT_ID
        UPDATE TCustManagerTree SET RIGHT_ID=RIGHT_ID-(@V_RIGHT_ID-@V_LEFT_ID+1) WHERE RIGHT_ID>@V_RIGHT_ID

        SET @SSUMMARY = N'删除节点客户经理，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
        COMMIT TRANSACTION
        SET XACT_ABORT OFF
    END
GO
