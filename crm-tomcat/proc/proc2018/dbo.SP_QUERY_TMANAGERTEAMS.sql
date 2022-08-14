USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMANAGERTEAMS @IN_TEAM_ID             INT = 0 ,           --团队ID
                                        @IN_TEAM_NO             NVARCHAR(64) = N'',  --团队编号
                                        @IN_TEAM_NAME           NVARCHAR(64) = N'',  --团队名称
                                        @IN_BEGIN_DATE          INT = 0,            --开始日期
                                        @IN_END_DATE            INT = 0 ,           --结束日期
                                        @IN_LEADER_NAME         NVARCHAR(64)= N'',  --负责人姓名
                                        @IN_PARENT_ID           INT = 0             --父团队ID
WITH ENCRYPTION
AS
    IF ISNULL(@IN_TEAM_ID,0) <> 0
        SELECT TEAM_ID,TEAM_NO,TEAM_NAME,CREATE_DATE,LEADER,LEADER_NAME,DESCRIPTION,MARK_FLAG
        FROM TManagerTeams
        WHERE TEAM_ID = @IN_TEAM_ID AND ISNULL(PARENT_ID,0)=@IN_PARENT_ID
    ELSE
        SELECT TEAM_ID,TEAM_NO,TEAM_NAME,CREATE_DATE,LEADER,LEADER_NAME,DESCRIPTION,MARK_FLAG,PARENT_ID,
			(SELECT TEAM_NAME FROM TManagerTeams A WHERE TManagerTeams.PARENT_ID = A.TEAM_ID) PARENT_NAME
        FROM TManagerTeams
        WHERE (TEAM_NO LIKE '%' + @IN_TEAM_NO + '%' OR ISNULL(@IN_TEAM_NO,'') = N'')
            AND (TEAM_NAME LIKE '%' + @IN_TEAM_NAME + '%' OR ISNULL(@IN_TEAM_NAME,'') = N'')
            AND (CREATE_DATE >= @IN_BEGIN_DATE OR ISNULL(@IN_BEGIN_DATE,0) = 0)
            AND (CREATE_DATE <= @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0)
            AND (LEADER_NAME LIKE '%' + @IN_LEADER_NAME + '%'  OR ISNULL(@IN_LEADER_NAME,'') = N'')
            AND (ISNULL(PARENT_ID,0)=@IN_PARENT_ID OR @IN_PARENT_ID = 0)
        ORDER BY TEAM_NO
    RETURN 100
GO
