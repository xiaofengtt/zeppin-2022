package cn.product.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.product.score.control.transfer.TransferService;

public class BaseServiceImpl {

	@Autowired
	protected TransferService transferScoreWebServiceImpl;
}
