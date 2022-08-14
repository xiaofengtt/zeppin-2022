USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
--drop PROCEDURE SP_ADD_TVideoRecording
--go

CREATE PROCEDURE SP_ADD_TVideoRecording
				  @IN_ProductID      INTEGER,         --产品ID
                  @IN_PreProductID   INTEGER,         --产品ID
                  @IN_SubProductID   INTEGER,         --子产品ID
                  @IN_SaveName       NVARCHAR(200),   --文件名
                  @IN_OriginName     NVARCHAR(200),   --原文件名
                  @IN_PreContractID  INTEGER,         --预约记录ID
                  @IN_ContractID     INTEGER,         --合同记录ID
                  @IN_VType          INTEGER,         --录像类型 1预约2签约
                  @IN_INPUT_MAN      INTEGER          --操作员
                                         
WITH ENCRYPTION
AS
    DECLARE @V_INPUT_MAN_ENAME NVARCHAR(80),@V_MSG NVARCHAR(80)
    DECLARE @V_PreProductID INT,@V_ProductID INT,@V_SubProductID INT
    BEGIN TRY
		--IF ISNULL(@IN_ProductID,0)=0 AND ISNULL(@IN_PreProductID,0)=0 AND ISNULL(@IN_SubProductID,0)=0
		--	RAISERROR('未指定产品',16,1)
		IF ISNULL(@IN_SaveName,'')=''
			RAISERROR('文件名不能为空',16,1)
		--IF ISNULL(@IN_OriginName,'')=''
		--	RAISERROR('原文件名不能为空',16,1)
		IF ISNULL(@IN_VType,0) NOT IN (1,2)
			RAISERROR('未指定录像类型',16,1)
		IF @IN_VType=1
		BEGIN
			IF ISNULL(@IN_PreContractID,0)=0
				RAISERROR('未指定预约记录ID',16,1)
			SELECT @V_PreProductID=PREPRODUCT_ID FROM TPRECONTRACT WHERE SERIAL_NO=@IN_PreContractID
			IF @@ROWCOUNT=0
			BEGIN
				SET @V_MSG='预约记录['+CAST(@IN_PreContractID AS NVARCHAR)+']不存在'
				RAISERROR(@V_MSG,16,1)
			END
			--IF ISNULL(@IN_PreProductID,0)=0
				SET @IN_PreProductID=@V_PreProductID
			--ELSE IF @IN_PreProductID<>@V_PreProductID
			--	RAISERROR('指定的产品与预约产品不相同',16,1)
			IF EXISTS (SELECT * FROM TVideoRecording WHERE PreContractID=@IN_PreContractID AND CheckFlag IN (1,2))
				RAISERROR('本预约的双录视频已经上传，不能重复操作',16,1)
			UPDATE TVideoRecording SET CheckFlag=4 WHERE PreContractID=@IN_PreContractID AND CheckFlag<>4
		END
		ELSE IF @IN_VType=2
		BEGIN
			IF ISNULL(@IN_ContractID,0)=0
				RAISERROR('未指定合同记录ID',16,1)
			SELECT @V_ProductID=PRODUCT_ID,@V_SubProductID=SUB_PRODUCT_ID FROM INTRUST..TCONTRACT WHERE SERIAL_NO=@IN_ContractID
			IF @@ROWCOUNT=0
			BEGIN
				SET @V_MSG='合同记录['+CAST(@IN_ContractID AS NVARCHAR)+']不存在'
				RAISERROR(@V_MSG,16,1)
			END
			--IF ISNULL(@IN_ProductID,0)=0
				SET @IN_ProductID=@V_ProductID
				SET @IN_SubProductID=@V_SubProductID
			--ELSE IF @IN_PreProductID<>@V_PreProductID
			--	RAISERROR('指定的产品与预约产品不相同',16,1)
			IF EXISTS (SELECT * FROM TVideoRecording WHERE ContractID=@IN_ContractID AND CheckFlag IN (1,2))
				RAISERROR('本合同的双录视频已经上传，不能重复操作',16,1)
			UPDATE TVideoRecording SET CheckFlag=4 WHERE ContractID=@IN_ContractID AND CheckFlag<>4
		END
		SELECT @V_INPUT_MAN_ENAME=OP_NAME FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN
		IF @@ROWCOUNT=0
			RAISERROR('操作员不存在',16,1)
		BEGIN TRANSACTION
		INSERT INTO TVideoRecording([ProductID]
           ,[PreProductID]
           ,[SubProductID]
           ,[SaveName]
           ,[OriginName]
           ,[PreContractID]
           ,[ContractID]
           ,[VType]
           ,[InputTime]
           ,[InputMan]
           ,[InputManName]
           ,[CheckFlag])
            SELECT ISNULL(@IN_ProductID,0),ISNULL(@IN_PreProductID,0),ISNULL(@IN_SubProductID,0),@IN_SaveName,@IN_OriginName
				,ISNULL(@IN_PreContractID,0),ISNULL(@IN_ContractID,0),@IN_VType,GETDATE(),@IN_INPUT_MAN,@V_INPUT_MAN_ENAME,1
    
    COMMIT TRANSACTION
    END TRY

    --异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N'Message:%s<BR><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d</font>',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()
        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE)
        RETURN -100
    END CATCH
    RETURN 100

GO
