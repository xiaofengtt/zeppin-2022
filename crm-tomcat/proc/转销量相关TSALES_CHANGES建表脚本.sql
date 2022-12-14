USE [EFCRM]
GO
/****** Object:  Table [dbo].[TSALES_CHANGES]    Script Date: 07/18/2018 15:09:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TSALES_CHANGES](
	[SERIAL_NO] [int] IDENTITY(1,1) NOT NULL,
	[FK_TPREMONEYDETAIL] [int] NOT NULL,
	[PRE_SERIAL_NO] [int] NOT NULL,
	[DZ_DATE] [datetime] NULL,
	[DZ_MONEY] [decimal](16, 3) NULL,
	[FROM_SERVICE_MAN] [int] NOT NULL,
	[FROM_SERVICE_MAN_NAME] [nvarchar](100) NULL,
	[TO_SERVICE_MAN] [int] NOT NULL,
	[TO_SERVICE_MAN_NAME] [nvarchar](100) NULL,
	[CHANGE_MONEY] [decimal](16, 3) NOT NULL,
	[CHANGE_REASON] [nvarchar](500) NULL,
	[INPUT_MAN] [int] NOT NULL,
	[INPUT_TIME] [datetime] NOT NULL,
	[STATUS] [int] NULL,
	[STATUS_NAME] [nvarchar](50) NULL,
	[CHECKER] [int] NULL,
	[CHECK_TIME] [datetime] NULL,
	[CHECK_REASON] [nvarchar](500) NULL,
	[RE_CHECKER] [int] NULL,
	[RE_CHECK_TIME] [datetime] NULL,
	[RE_CHECK_REASON] [nvarchar](500) NULL,
	[FK_TSALESRESULT] [int] NULL,
 CONSTRAINT [PK_T_SALES_CHANGES] PRIMARY KEY CLUSTERED 
(
	[SERIAL_NO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [SDATA]
) ON [SDATA]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键唯一标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'SERIAL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预约到帐情况外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'FK_TPREMONEYDETAIL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预约表主键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'PRE_SERIAL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'到账日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'DZ_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'到帐金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'DZ_MONEY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'原销量客户经理ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'FROM_SERVICE_MAN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'原销量客户经理姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'FROM_SERVICE_MAN_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转销量后客户经理ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'TO_SERVICE_MAN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转销量后客户经理姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'TO_SERVICE_MAN_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转让金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'CHANGE_MONEY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转销量事由' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'CHANGE_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入人员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'INPUT_MAN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'INPUT_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态 1待确认/2确认通过待审核/3确认不通过/4审核通过/5审核不通过' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'确认人员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'CHECKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'确认时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'CHECK_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'确认意见' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'CHECK_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'RE_CHECKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'RE_CHECK_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核意见' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'RE_CHECK_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'记录表ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALES_CHANGES', @level2type=N'COLUMN',@level2name=N'FK_TSALESRESULT'
GO
ALTER TABLE [dbo].[TSALES_CHANGES] ADD  CONSTRAINT [DF_T_SALES_CHANGES_INPUT_TIME]  DEFAULT (getdate()) FOR [INPUT_TIME]
GO
