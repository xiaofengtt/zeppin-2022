ALTER TABLE `bank_financial_product_publish_operate`
ADD COLUMN `submittime`  timestamp NULL COMMENT '提交审核时间' AFTER `createtime`;

ALTER TABLE `bank_financial_product_operate`
ADD COLUMN `submittime`  timestamp NULL COMMENT '提交审核时间' AFTER `createtime`;

