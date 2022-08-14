USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TTEAMINFO     @IN_TEAM_ID	              INT,             --项目组ID，自增长
                                        @IN_TEAM_NAME             NVARCHAR(200) ,  --项目组名称
                                        @IN_TEAM_SUMMARY          NVARCHAR(1000),  --项目组说明
                                        @IN_TEAM_ADMIN_NAME       NVARCHAR(20) ,   --项目组长，对应表TOPERATOR.OP_CODE                                    
                                        @IN_INPUT_MAN             INT              --录入人员,对应表TOPERATOR.OP_CODE
WITH ENCRYPTION
AS
    
    IF ISNULL(@IN_TEAM_ID,0) <>0
        SELECT A.*,B.OP_NAME
            FROM TTEAMINFO A,TOPERATOR B
                WHERE A.TEAM_ID =@IN_TEAM_ID AND A.TEAM_ADMIN =B.OP_CODE
    ELSE
        SELECT A.*,B.OP_NAME AS TEAM_ADMIN_NAME
            FROM TTEAMINFO A,TOPERATOR B
                WHERE (ISNULL(@IN_TEAM_NAME,'') ='' OR A.TEAM_NAME LIKE'%'+@IN_TEAM_NAME+'%')
                    AND (ISNULL(@IN_TEAM_SUMMARY,'') ='' OR A.TEAM_SUMMARY LIKE'%'+@IN_TEAM_SUMMARY+'%')
                    AND (ISNULL(@IN_TEAM_ADMIN_NAME,'') ='' OR B.OP_NAME LIKE'%'+@IN_TEAM_ADMIN_NAME+'%')
                    AND A.TEAM_ADMIN =B.OP_CODE
                    
GO
