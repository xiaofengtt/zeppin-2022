USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
-- PROCEDURE SP2_QUERY_TVideoRecording_DFID
-- SP2_QUERY_TVideoRecording_DFID 61250,2,888
CREATE PROCEDURE SP2_QUERY_TVideoRecording_DFID
					@IN_CONTRACTID        INT,         --
					@IN_VTYPE             INT,         --1预约2签约
					@IN_INPUT_MAN         INT          --操作员
					
WITH ENCRYPTION
AS
    IF @IN_VTYPE=1
    BEGIN
		SELECT A.VID,A.ProductID,A.SubProductID,A.SaveName,A.OriginName,A.ContractID,A.InputTime,A.InputMan,A.InputManName
				,A.CheckFlag,A.CheckMan,A.CheckManName,A.CheckTime,A.CheckComment
			FROM TVideoRecording A
			WHERE VType=1
				AND A.PreContractID=@IN_CONTRACTID
				AND A.CheckFlag<>4 --未作废
		RETURN
	END
	IF @IN_VTYPE=2
    BEGIN
		SELECT A.VID,A.ProductID,A.SubProductID,A.SaveName,A.OriginName,A.ContractID,A.InputTime,A.InputMan,A.InputManName
				,A.CheckFlag,A.CheckMan,A.CheckManName,A.CheckTime,A.CheckComment
			FROM TVideoRecording A
			WHERE VType=2
				AND A.ContractID=@IN_CONTRACTID
				AND A.CheckFlag<>4 --未作废
		RETURN
	END
	ELSE 
		SELECT A.VID,A.ProductID,A.SubProductID,A.SaveName,A.OriginName,A.ContractID,A.InputTime,A.InputMan,A.InputManName
				,A.CheckFlag,A.CheckMan,A.CheckManName,A.CheckTime,A.CheckComment
			FROM TVideoRecording A
			WHERE 1=2
		
GO
