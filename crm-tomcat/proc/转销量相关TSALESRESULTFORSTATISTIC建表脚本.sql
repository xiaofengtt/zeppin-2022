USE [EFCRM]
GO
/****** Object:  Table [dbo].[TSALESRESULTFORSTATISTIC]    Script Date: 07/18/2018 15:09:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TSALESRESULTFORSTATISTIC](
	[SERIAL_NO] [int] IDENTITY(1,1) NOT NULL,
	[PRE_SERIAL_NO] [int] NULL,
	[DZ_DATE] [datetime] NULL,
	[DZ_MONEY] [decimal](16, 3) NULL,
	[REFUND_DATE] [int] NULL,
	[REFUND_MONEY] [decimal](16, 3) NULL,
	[JK_TYPE] [nvarchar](10) NULL,
	[JK_TYPE_NAME] [nvarchar](30) NULL,
	[INPUT_MAN] [int] NOT NULL,
	[INPUT_TIME] [datetime] NOT NULL,
	[PRINT_COUNT] [int] NULL,
	[SMS1_CUSTOMER] [nvarchar](200) NULL,
	[SMS1_COUNT] [int] NULL,
	[SMS2_SERVICEMAN] [nvarchar](200) NULL,
	[SMS2_COUNT] [int] NULL,
	[CONTRACT_SERIAL_NO] [int] NULL,
	[ONWAY_FLAG] [int] NULL,
	[FILE_PATH] [nvarchar](200) NULL,
	[ETL_timestamp] [timestamp] NOT NULL,
	[PRE_PRODUCT_TYPE] [int] NULL,
	[PRE_PRODUCT_TYPE_NAME] [nvarchar](50) NULL,
	[FK_TPREMONEYDETAIL] [int] NULL,
	[RECORDS_COUNT] [int] NULL,
	[FK_TSALES_CHANGES] [int] NULL,
	[REMARK] [nvarchar](500) NULL,
	[STATUS] [int] NOT NULL,
	[RG_PRODUCT_NAME] [nvarchar](200) NULL,
	[RG_CUST_TYPE] [int] NULL,
	[RG_CUST_TYPE_NAME] [nvarchar](20) NULL,
	[RG_CUST_NAME] [nvarchar](200) NULL,
	[RG_SERVICE_MAN] [int] NULL,
	[RG_SERVICE_MAN_NAME] [nvarchar](200) NULL,
 CONSTRAINT [PRI_TSALESRESULTFORSTATISTIC1] PRIMARY KEY CLUSTERED 
(
	[SERIAL_NO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [SDATA]
) ON [SDATA]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'产品分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'PRE_PRODUCT_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'产品分类名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'PRE_PRODUCT_TYPE_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预约到账记录外键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'FK_TPREMONEYDETAIL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'计数器' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'RECORDS_COUNT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转销量记录表外键 用于编辑' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'FK_TSALES_CHANGES'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'REMARK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1-正常 0-失效 -1-待生效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户经理' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TSALESRESULTFORSTATISTIC', @level2type=N'COLUMN',@level2name=N'RG_SERVICE_MAN'
GO
ALTER TABLE [dbo].[TSALESRESULTFORSTATISTIC] ADD  DEFAULT (getdate()) FOR [INPUT_TIME]
GO
ALTER TABLE [dbo].[TSALESRESULTFORSTATISTIC] ADD  CONSTRAINT [DF_TSALESRESULTFORSTATISTIC_STATUS]  DEFAULT ((1)) FOR [STATUS]
GO
