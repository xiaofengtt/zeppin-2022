USE [EFCRM]
GO

/****** Object:  Table [dbo].[TNUMBER_RELATION]    Script Date: 05/03/2018 09:20:41 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[TNUMBER_RELATION](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[STATUS] [varchar](10) NOT NULL,
	[CREATETIME] [datetime] NOT NULL,
	[FK_TCUSTOMERS] [int] NOT NULL,
	[FK_TOPERATOR] [int] NOT NULL,
	[TO_MOBILE] [varchar](20) NULL,
	[TO_TEL] [varchar](20) NULL,
	[TC_PHONE] [varchar](20) NULL,
	[TC_TEL] [varchar](20) NULL,
	[PROCESS_STATUS] [int] NOT NULL,
	[FK_OP_MOBILE] [int] NULL,
	[EXPIRY_DATE] [datetime] NULL,
	[MAXDURATION] [int] NULL,
	[ISRECORD] [int] NULL,
	[WAYBILLNUM] [varchar](50) NULL,
 CONSTRAINT [PK_TNUMBER_RELATION] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户关系外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'FK_TCUSTOMERS'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'经理关系外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'FK_TOPERATOR'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主叫号码（经理）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'TO_MOBILE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'呼入号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'TO_TEL'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'被叫号码（根据客户实际联系方式改变）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'TC_PHONE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'呼出号码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'TC_TEL'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'失效时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'EXPIRY_DATE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最大通过时常' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'MAXDURATION'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否录音' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'ISRECORD'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TNUMBER_RELATION', @level2type=N'COLUMN',@level2name=N'WAYBILLNUM'
GO

ALTER TABLE [dbo].[TNUMBER_RELATION] ADD  CONSTRAINT [DF_TNUMBER_RELATION_PROCESS_STATUS]  DEFAULT ((-1)) FOR [PROCESS_STATUS]
GO

ALTER TABLE [dbo].[TNUMBER_RELATION] ADD  CONSTRAINT [DF_TNUMBER_RELATION_MAXDURATION]  DEFAULT ((3600)) FOR [MAXDURATION]
GO

ALTER TABLE [dbo].[TNUMBER_RELATION] ADD  CONSTRAINT [DF_TNUMBER_RELATION_ISRECORD]  DEFAULT ((1)) FOR [ISRECORD]
GO

