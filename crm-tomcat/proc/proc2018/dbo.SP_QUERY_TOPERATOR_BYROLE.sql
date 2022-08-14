﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TOPERATOR_BYROLE @IN_ROLE_ID INT
WITH ENCRYPTION
AS
    --数字签名图片字段不返回
    IF @IN_ROLE_ID = 2
    BEGIN
        IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE2' AND VALUE <> 0)
            SELECT @IN_ROLE_ID = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE2'
        SELECT A.OP_CODE,A.OP_NAME
        FROM TOPERATOR A,
             (SELECT OP_CODE FROM TOPROLE WHERE ROLE_ID = @IN_ROLE_ID) B
        WHERE A.STATUS IN (1,3) AND A.OP_CODE = B.OP_CODE
    END
    ELSE
    IF @IN_ROLE_ID = 3
    BEGIN
        IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE3' AND VALUE <> 0)
            SELECT @IN_ROLE_ID = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE3'
        SELECT A.OP_CODE,A.OP_NAME
        FROM TOPERATOR A,
             (SELECT OP_CODE FROM TOPROLE WHERE ROLE_ID = @IN_ROLE_ID) B
        WHERE A.STATUS IN (1,3) AND A.OP_CODE = B.OP_CODE
    END
    ELSE
    IF @IN_ROLE_ID = 90
    BEGIN
        IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE90' AND VALUE <> 0)
            SELECT @IN_ROLE_ID = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'AROLE90'
        SELECT A.OP_CODE,A.OP_NAME
        FROM TOPERATOR A,
             (SELECT OP_CODE FROM TOPROLE WHERE ROLE_ID = @IN_ROLE_ID) B
        WHERE A.STATUS IN (1,3) AND A.OP_CODE = B.OP_CODE
    END
    ELSE
        SELECT A.OP_CODE,A.OP_NAME
        FROM TOPERATOR A,
             (SELECT OP_CODE FROM TOPROLE WHERE ROLE_ID = @IN_ROLE_ID) B
        WHERE A.STATUS IN (1,3) AND A.OP_CODE = B.OP_CODE
GO