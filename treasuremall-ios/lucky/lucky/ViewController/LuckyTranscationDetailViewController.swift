//
//  LuckyTranscationDetailViewController.swift
//  lucky
//  交易记录详情
//  Created by Farmer Zhu on 2020/9/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTranscationDetailViewController: UIViewController{

    //头
    private var staticHeaderView: LuckyNavigationView!
    //显示区
    private var staticMainView: UIView?
    
    //数据ID
    var uuid: String = ""
    //数据
    private var data: LuckyFrontUserHistoryModel? = nil
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        
        //取数据
        getData()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Order Details", comment: "")
        return headView
    }
    
    //创建县市区
    func createBodyView() -> UIView{
        let mainView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        mainView.backgroundColor = UIColor.white
        
        //交易类型
        let typeLabel = UILabel()
        typeLabel.text = LuckyUtils.getHistoryType(type: data!.orderType)
        typeLabel.textColor = UIColor.fontBlack()
        typeLabel.font = UIFont.mainFont(size: UIFont.fontSizeBiggest() * screenScale)
        typeLabel.sizeToFit()
        typeLabel.frame = CGRect(x: (mainView.frame.width - (typeLabel.frame.width + 16 * screenScale))/2 + 16 * screenScale, y: 32 * screenScale, width: typeLabel.frame.width, height: 20 * screenScale)
        mainView.addSubview(typeLabel)
        let typePoint = UIView(frame: CGRect(x: typeLabel.frame.origin.x - 16 * screenScale, y: typeLabel.frame.origin.y + 4 * screenScale, width: 12 * screenScale, height: 12 * screenScale))
        typePoint.layer.masksToBounds = true
        typePoint.layer.cornerRadius = typePoint.frame.height/2
        if(data!.type == "user_add"){
            typePoint.backgroundColor = UIColor.mainGreen()
        }else{
            typePoint.backgroundColor = UIColor.mainRed()
        }
        mainView.addSubview(typePoint)
            
        //交易金币数
        let amountLabel = UILabel()
        amountLabel.text = "\(data!.type == "user_add" ? "+" : "-")\(LuckyUtils.coinFormat(amount: data!.dAmount))"
        if(data!.type == "user_add"){
            //加金币 绿
            amountLabel.textColor = UIColor.mainGreen()
        }else{
            //减金币 红
            amountLabel.textColor = UIColor.fontBlack()
        }
        amountLabel.font = UIFont.mediumFont(size: 30 * screenScale)
        amountLabel.sizeToFit()
        amountLabel.frame = CGRect(x: (mainView.frame.width - (amountLabel.frame.width + 22 * screenScale))/2, y: typeLabel.frame.origin.y + typeLabel.frame.height + 20 * screenScale, width: amountLabel.frame.width, height: amountLabel.frame.height)
        mainView.addSubview(amountLabel)
        let coinImageView = UIImageView(frame: CGRect(x: amountLabel.frame.origin.x + amountLabel.frame.width + 6 * screenScale, y: amountLabel.frame.origin.y + (amountLabel.frame.height - 22 * screenScale), width: 16 * screenScale, height: 16 * screenScale))
        if(globalFlagUser){
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        mainView.addSubview(coinImageView)
        
        let bodyView = UIView(frame: CGRect(x: 0, y: amountLabel.frame.origin.y + amountLabel.frame.height + 34 * screenScale, width: mainView.frame.width, height: 0))
        //根据不同类型 显示不同字段
        if(data!.orderType == "user_payment" || data!.orderType == "user_exchange"){
            //用户投注 或 兑换金币
            //详情
            let descriptionView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Description", comment: ""), value: data!.goodsTitle, isEnter: true, singleRow: true)
            descriptionView.button.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 0, y: descriptionView.frame.height - 1, width: bodyView.frame.width, height: 1)
            bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
            descriptionView.layer.addSublayer(bottomLine)
            bodyView.addSubview(descriptionView)
            bodyView.frame.size = CGSize(width: bodyView.frame.width, height: descriptionView.frame.origin.y + descriptionView.frame.height + 16 * screenScale)
            
            if(data!.orderType == "user_payment" && data!.voucher != ""){
                //用户投注 且 有使用优惠券 显示抵扣金币数
                let voucherView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: bodyView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Coupon Applied", comment: ""), value: "\(LuckyUtils.coinFormat(amount: data!.voucherDAmount)) coins", isEnter: false, singleRow: true)
                bodyView.addSubview(voucherView)
                bodyView.frame.size = CGSize(width: bodyView.frame.width, height: voucherView.frame.origin.y + voucherView.frame.height)
            }
        }
        if(data!.orderType == "user_withdraw" || data!.orderType == "user_recharge"){
            //用户提现 或 用户充值
            //法币金额
            let amountView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Amount", comment: ""), value: data!.currencyAmount, isEnter: false, singleRow: true)
            bodyView.addSubview(amountView)
            bodyView.frame.size = CGSize(width: bodyView.frame.width, height: amountView.frame.origin.y + amountView.frame.height)
            
            if(data!.orderType == "user_withdraw"){
                //提现
                //手续费
                let feeView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: bodyView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Fee", comment: ""), value: data!.currencyAmountPoundage, isEnter: false, singleRow: true)
                bodyView.addSubview(feeView)
                
                //积分抵扣
                let pointView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: feeView.frame.origin.y + feeView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Points", comment: ""), value: String(data!.points), isEnter: false, singleRow: true)
                bodyView.addSubview(pointView)
                
                bodyView.frame.size = CGSize(width: bodyView.frame.width, height: pointView.frame.origin.y + pointView.frame.height)
            }
        }
        
        //备注
        let remarkView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: bodyView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Remarks", comment: ""), value: data!.remark, isEnter: false, singleRow: false)
        bodyView.addSubview(remarkView)
        
        //订单时间
        let orderDateView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: remarkView.frame.origin.y + remarkView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Order Date", comment: ""), value: LuckyUtils.timestampFormat(timestamp: data!.createtime, format: "yyyy-MM-dd HH:mm"), isEnter: false, singleRow: true)
        bodyView.addSubview(orderDateView)
        
        //订单号
        let orderIdView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: orderDateView.frame.origin.y + orderDateView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Order ID", comment: ""), value: data!.orderNum, isEnter: false, singleRow: true)
        bodyView.addSubview(orderIdView)
        bodyView.frame.size = CGSize(width: bodyView.frame.width, height: orderIdView.frame.origin.y + orderIdView.frame.height)
        
        if(data!.orderType == "user_withdraw" || data!.orderType == "user_recharge"){
            //充值 或 提现
            //订单状态
            let statusView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: bodyView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Status", comment: ""), value: NSLocalizedString("Completed", comment: ""), isEnter: false, singleRow: true)
            bodyView.addSubview(statusView)
            bodyView.frame.size = CGSize(width: bodyView.frame.width, height: statusView.frame.origin.y + statusView.frame.height)
        }
        
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: bodyView.frame.height - 1, width: bodyView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        bodyView.layer.addSublayer(bottomLine)
        bodyView.frame.size = CGSize(width: bodyView.frame.width, height: bodyView.frame.height + 16 * screenScale)
        
        //客服按钮
        let inquiriesView = LuckyTranscationDetailCellView(frame: CGRect(x: 0, y: bodyView.frame.height, width: bodyView.frame.width, height: 36 * screenScale), title: NSLocalizedString("Order Inquiries", comment: ""), value: "", isEnter: true, singleRow: true)
        inquiriesView.button.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        bodyView.addSubview(inquiriesView)
        bodyView.frame.size = CGSize(width: bodyView.frame.width, height: inquiriesView.frame.origin.y + inquiriesView.frame.height)
        
        mainView.addSubview(bodyView)
        return mainView
    }
    
    //取数据
    func getData(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithToken("front/userAccount/historyGet", params: ["uuid": uuid]) { (data) in
            let dataDic = data as! NSDictionary
            self.data = LuckyFrontUserHistoryModel(data: dataDic)
            
            //创建显示区
            self.staticMainView = self.createBodyView()
            self.view.addSubview(self.staticMainView!)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        } fail: { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去商品详情
    @objc func toGoodsDetail(){
        if(data!.gameType == "group2"){
            //PK模式 去PK详情
            let vc = LuckyGroupDetailViewController()
            vc.uuid = data!.goodsIssue
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //普通模式 去商品详情
            let vc = LuckyDetailViewController()
            vc.uuid = data!.goodsIssue
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
