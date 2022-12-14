USE [EFCRM]
GO
/****** Object:  Table [dbo].[TCUSTOMOERS_CONNECTION_MODI]    Script Date: 07/06/2018 19:07:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TCUSTOMOERS_CONNECTION_MODI](
	[SERIAL_NO] [int] IDENTITY(1,1) NOT NULL,
	[CUST_ID] [int] NOT NULL,
	[INPUT_MAN] [int] NOT NULL,
	[INPUT_TIME] [datetime] NOT NULL,
	[APPLY_REASON] [nvarchar](200) NULL,
	[STATUS] [int] NOT NULL,
	[STATUS_NAME] [nvarchar](50) NOT NULL,
	[CHECKER] [int] NULL,
	[CHECK_TIME] [datetime] NULL,
	[CHECK_REASON] [nvarchar](200) NULL,
	[RE_CHECKER] [int] NULL,
	[RE_CHECK_TIME] [datetime] NULL,
	[RE_CHAECK_REASON] [nvarchar](200) NULL,
	[O_CUST_TEL] [nvarchar](40) NULL,
	[N_CUST_TEL] [nvarchar](40) NULL,
	[O_MOBILE] [nvarchar](40) NULL,
	[N_MOBILE] [nvarchar](40) NULL,
	[O_O_TEL] [nvarchar](40) NULL,
	[N_O_TEL] [nvarchar](40) NULL,
	[O_H_TEL] [nvarchar](40) NULL,
	[N_H_TEL] [nvarchar](40) NULL,
	[O_BP] [nvarchar](40) NULL,
	[N_BP] [nvarchar](40) NULL,
 CONSTRAINT [PK_TCUSTOMOERS_CONNECTION_MODI] PRIMARY KEY CLUSTERED 
(
	[SERIAL_NO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [SDATA]
) ON [SDATA]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'CUST_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'INPUT_MAN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态 1-待确认 2-确认未通过 3-待审核 4-审核未通过 5-已通过' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'CHECKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'CHECK_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'复审人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'RE_CHECKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'复审时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'RE_CHECK_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'旧BP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'O_BP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'新BP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TCUSTOMOERS_CONNECTION_MODI', @level2type=N'COLUMN',@level2name=N'N_BP'
GO
ALTER TABLE [dbo].[TCUSTOMOERS_CONNECTION_MODI] ADD  CONSTRAINT [DF_TCUSTOMOERS_CONNECTION_MODI_INPUT_TIME]  DEFAULT (getdate()) FOR [INPUT_TIME]
GO
