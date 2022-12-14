USE [EFCRM]
GO
/****** Object:  Table [dbo].[TPREDOUBLERECORDINFO]    Script Date: 07/06/2018 19:07:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TPREDOUBLERECORDINFO](
	[SERIAL_NO] [int] IDENTITY(1,1) NOT NULL,
	[PRE_SERIAL_NO] [int] NULL,
	[RECORD_TIME] [nvarchar](50) NULL,
	[RECORD_TYPE] [nvarchar](20) NULL,
	[RECORD_TYPE_NAME] [nvarchar](50) NULL,
	[STATUS] [int] NULL,
	[STATUS_NAME] [nvarchar](50) NULL,
	[INPUT_TIME] [datetime] NOT NULL,
	[INPUT_MAN] [int] NOT NULL,
	[CHECKER] [int] NULL,
	[CHECKER_TIME] [datetime] NULL,
 CONSTRAINT [PK_TPREDOBLERECORDINFO] PRIMARY KEY CLUSTERED 
(
	[SERIAL_NO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'现场签约记录ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'PRE_SERIAL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'双录时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'RECORD_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'双录模式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'RECORD_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'双录模式名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'RECORD_TYPE_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'STATUS_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'INPUT_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'INPUT_MAN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'CHECKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TPREDOUBLERECORDINFO', @level2type=N'COLUMN',@level2name=N'CHECKER_TIME'
GO
ALTER TABLE [dbo].[TPREDOUBLERECORDINFO] ADD  CONSTRAINT [DF_TPREDOBLERECORDINFO_INPUT_TIME]  DEFAULT (getdate()) FOR [INPUT_TIME]
GO
