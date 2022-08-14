//
//  LuckyOrderTrackingViewController.swift
//  lucky
//  兑实物订单详情
//  Created by Farmer Zhu on 2020/9/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyOrderTrackingViewController: UIViewController{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //滚动区
    private var staticScrollView: UIScrollView!
    
    //数据ID
    var uuid: String = ""
    
    //数据
    private var data: LuckyWinningInfoModel? = nil
    //推荐数据列表
    private var recommendList: [LuckyLuckygameGoodsIssueModel] = []
    
    //推荐cell高度
    private let recommendCellHeight: CGFloat = screenWidth/2 + 130 * screenScale
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        
        //取数据
        getData()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Order Tracking", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.rightButton.setImage(UIImage(named: "image_order_customer_black"), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        
        return scrollView
    }
    
    //创建滚动区内容
    func createScrollContent(){
        //头部
        //兑奖进度 根据订单不同状态 拼接虚线/实现 不同图片
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 140 * screenScale))
        topView.backgroundColor = UIColor.white
        let appliedImageView = UIImageView(frame: CGRect(x: 35 * screenScale, y: 30 * screenScale, width: 40 * screenScale, height: 45 * screenScale))
        appliedImageView.image = UIImage(named: "image_tracking_applied")
        topView.addSubview(appliedImageView)
        let appliedPointView = UIView(frame: CGRect(x: appliedImageView.frame.origin.x + (appliedImageView.frame.width - 10 * screenScale)/2, y: appliedImageView.frame.origin.y + appliedImageView.frame.height + 2 * screenScale, width: 10 * screenScale, height: 10 * screenScale))
        appliedPointView.layer.masksToBounds = true
        appliedPointView.layer.cornerRadius = appliedPointView.frame.width/2
        appliedPointView.backgroundColor = UIColor.mainYellow()
        topView.addSubview(appliedPointView)
        let appliedLabel = UILabel(frame: CGRect(x: appliedPointView.frame.origin.x - 50 * screenScale, y: appliedPointView.frame.origin.y + appliedPointView.frame.height + 4 * screenScale, width: appliedPointView.frame.width + 100 * screenScale, height: 20 * screenScale))
        appliedLabel.text = NSLocalizedString("Applied", comment: "")
        appliedLabel.textColor = UIColor.fontBlack()
        appliedLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        appliedLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(appliedLabel)
        
        let deliveredImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 40 * screenScale)/2, y: appliedImageView.frame.origin.y, width: 40 * screenScale, height: 45 * screenScale))
        if(data!.status == "finished" || data!.status == "confirm"){
            deliveredImageView.image = UIImage(named: "image_tracking_delivered")
        }else{
            deliveredImageView.image = UIImage(named: "image_tracking_undelivered")
        }
        topView.addSubview(deliveredImageView)
        let deliveredPointView = UIView(frame: CGRect(x: deliveredImageView.frame.origin.x + (deliveredImageView.frame.width - appliedPointView.frame.width)/2, y: appliedPointView.frame.origin.y, width: appliedPointView.frame.width, height: appliedPointView.frame.height))
        deliveredPointView.layer.masksToBounds = true
        deliveredPointView.layer.cornerRadius = deliveredPointView.frame.width/2
        if(data!.status == "finished" || data!.status == "confirm"){
            deliveredPointView.backgroundColor = UIColor.mainYellow()
        }else{
            deliveredPointView.layer.borderColor = UIColor.mainYellow().cgColor
            deliveredPointView.layer.borderWidth = 2 * screenScale
        }
        topView.addSubview(deliveredPointView)
        let deliveredLabel = UILabel(frame: CGRect(x: deliveredPointView.frame.origin.x - 50 * screenScale, y: appliedLabel.frame.origin.y, width: appliedLabel.frame.width, height: appliedLabel.frame.height))
        deliveredLabel.text = NSLocalizedString("Delivered", comment: "")
        if(data!.status == "finished" || data!.status == "confirm"){
            deliveredLabel.textColor = UIColor.fontBlack()
        }else{
            deliveredLabel.textColor = UIColor.fontGray()
        }
        deliveredLabel.font = appliedLabel.font
        deliveredLabel.textAlignment = appliedLabel.textAlignment
        topView.addSubview(deliveredLabel)
        let leftLine = UIView(frame: CGRect(x: appliedPointView.frame.origin.x + appliedPointView.frame.width, y: appliedPointView.frame.origin.y + (appliedPointView.frame.height - 2 * screenScale)/2, width: deliveredPointView.frame.origin.x - (appliedPointView.frame.origin.x + appliedPointView.frame.width), height: 2 * screenScale))
        leftLine.backgroundColor = UIColor.mainYellow()
        topView.addSubview(leftLine)
        
        let completedImageView = UIImageView(frame: CGRect(x: topView.frame.width - 75 * screenScale, y: appliedImageView.frame.origin.y, width: 40 * screenScale, height: 45 * screenScale))
        if(data!.status == "confirm"){
            completedImageView.image = UIImage(named: "image_tracking_completed")
        }else{
            completedImageView.image = UIImage(named: "image_tracking_uncompleted")
        }
        topView.addSubview(completedImageView)
        let completedPointView = UIView(frame: CGRect(x: completedImageView.frame.origin.x + (completedImageView.frame.width - appliedPointView.frame.width)/2, y: appliedPointView.frame.origin.y, width: appliedPointView.frame.width, height: appliedPointView.frame.height))
        completedPointView.layer.masksToBounds = true
        completedPointView.layer.cornerRadius = deliveredPointView.frame.width/2
        if(data!.status == "confirm"){
            completedPointView.backgroundColor = UIColor.mainYellow()
        }else{
            completedPointView.layer.borderColor = UIColor.mainYellow().cgColor
            completedPointView.layer.borderWidth = 2 * screenScale
        }
        topView.addSubview(completedPointView)
        let completedLabel = UILabel(frame: CGRect(x: completedPointView.frame.origin.x - 50 * screenScale, y: appliedLabel.frame.origin.y, width: appliedLabel.frame.width, height: appliedLabel.frame.height))
        completedLabel.text = NSLocalizedString("Completed", comment: "")
        if(data!.status == "confirm"){
            completedLabel.textColor = UIColor.fontBlack()
        }else{
            completedLabel.textColor = UIColor.fontGray()
        }
        completedLabel.font = appliedLabel.font
        completedLabel.textAlignment = appliedLabel.textAlignment
        topView.addSubview(completedLabel)
        if(data!.status == "finished" || data!.status == "confirm"){
            let rightLine = UIView(frame: CGRect(x: deliveredPointView.frame.origin.x + deliveredPointView.frame.width, y: leftLine.frame.origin.y, width: completedPointView.frame.origin.x - (deliveredPointView.frame.origin.x + deliveredPointView.frame.width), height: leftLine.frame.height))
            rightLine.backgroundColor = UIColor.mainYellow()
            topView.addSubview(rightLine)
        }else{
            let rightLine = LuckyDottedLine(frame: CGRect(x: deliveredPointView.frame.origin.x + deliveredPointView.frame.width, y: leftLine.frame.origin.y, width: completedPointView.frame.origin.x - (deliveredPointView.frame.origin.x + deliveredPointView.frame.width), height: leftLine.frame.height), color: UIColor.mainYellow(), direction: LuckyDottedLine.LuckyDottedLineDirection.horizontal)
            topView.addSubview(rightLine)
        }
        staticScrollView.addSubview(topView)
        
        //地址区
        let addressView = UIView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: staticScrollView.frame.width, height: 0))
        addressView.backgroundColor = UIColor.white
        let addressImageView = UIImageView(frame: CGRect(x: 12 * screenScale, y: 24 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
        addressImageView.image = UIImage(named: "image_address_yellow")
        addressView.addSubview(addressImageView)
        staticScrollView.addSubview(addressView)
        let addressNameLabel = UILabel(frame: CGRect(x: addressImageView.frame.origin.x + addressImageView.frame.width + 10 * screenScale, y: 14 * screenScale, width: (addressView.frame.width - 10 * screenScale - (addressImageView.frame.origin.x + addressImageView.frame.width + 10 * screenScale)) * 0.6, height: 20 * screenScale))
        addressNameLabel.text = data!.detailInfo?.name
        addressNameLabel.textColor = UIColor.fontBlack()
        addressNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        addressView.addSubview(addressNameLabel)
        let addressMobileLabel = UILabel(frame: CGRect(x: addressNameLabel.frame.origin.y + addressNameLabel.frame.width, y: addressNameLabel.frame.origin.y, width: addressView.frame.width - 10 * screenScale - (addressNameLabel.frame.origin.y + addressNameLabel.frame.width), height: addressNameLabel.frame.height))
        addressMobileLabel.text = data!.detailInfo?.mobile
        addressMobileLabel.textColor = addressNameLabel.textColor
        addressMobileLabel.font = addressNameLabel.font
        addressMobileLabel.textAlignment = NSTextAlignment.right
        addressView.addSubview(addressMobileLabel)
        let addressLabel = UILabel(frame: CGRect(x: addressNameLabel.frame.origin.x, y: addressNameLabel.frame.origin.y + addressNameLabel.frame.height + 6 * screenScale, width: addressView.frame.width - 10 * screenScale - addressNameLabel.frame.origin.x, height: 0))
        addressLabel.numberOfLines = 0
        let addressStyle = NSMutableParagraphStyle()
        addressStyle.lineSpacing = 4 * screenScale
        addressLabel.attributedText = NSAttributedString(string: data!.detailInfo!.address, attributes: [NSAttributedString.Key.paragraphStyle : addressStyle])
        addressLabel.textColor = UIColor.fontDarkGray()
        addressLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        addressLabel.sizeToFit()
        addressView.addSubview(addressLabel)
        addressView.frame.size = CGSize(width: addressView.frame.width, height: addressLabel.frame.origin.y + addressLabel.frame.height + 16 * screenScale)
        staticScrollView.addSubview(addressView)
        
        //商品信息框
        let goodsView = UIView(frame: CGRect(x: 0, y: addressView.frame.origin.y + addressView.frame.height + 10 * screenScale, width: screenWidth, height: 122 * screenScale))
        goodsView.backgroundColor = UIColor.white
        let goodsImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 16 * screenScale, width: 90 * screenScale, height: 90 * screenScale))
        goodsImageView.sd_setImage(with: URL(string: data!.cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        goodsView.addSubview(goodsImageView)
        let goodsContentLabel = UILabel(frame: CGRect(x: goodsImageView.frame.origin.x + goodsImageView.frame.width + 12 * screenScale, y: goodsImageView.frame.origin.y, width: goodsView.frame.width - 10 * screenScale - (goodsImageView.frame.origin.x + goodsImageView.frame.width + 12 * screenScale), height: 40 * screenScale))
        goodsContentLabel.numberOfLines = 2
        goodsContentLabel.textColor = UIColor.fontBlack()
        goodsContentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let contentStyle = NSMutableParagraphStyle()
        contentStyle.lineBreakMode = NSLineBreakMode.byTruncatingTail
        contentStyle.maximumLineHeight = 20 * screenScale
        contentStyle.minimumLineHeight = 20 * screenScale
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data!.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data!.title, attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        goodsContentLabel.attributedText = contentText
        goodsContentLabel.alignmentTop()
        goodsView.addSubview(goodsContentLabel)
        let goodsNumberLabel = UILabel(frame: CGRect(x: goodsContentLabel.frame.origin.x, y: goodsContentLabel.frame.origin.y + goodsContentLabel.frame.height + 12 * screenScale, width: goodsContentLabel.frame.width, height: 20 * screenScale))
        goodsNumberLabel.text = "x1"
        goodsNumberLabel.textColor = UIColor.fontDarkGray()
        goodsNumberLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        goodsView.addSubview(goodsNumberLabel)
        let goodsAmountLabel = UILabel(frame: CGRect(x: goodsNumberLabel.frame.origin.x, y: goodsNumberLabel.frame.origin.y + goodsNumberLabel.frame.height + 2 * screenScale, width: goodsNumberLabel.frame.width, height: goodsNumberLabel.frame.height))
        goodsAmountLabel.text = "\(LuckyUtils.localCurrencyFormat(amount: data!.price))"
        goodsAmountLabel.textColor = goodsNumberLabel.textColor
        goodsAmountLabel.font = goodsNumberLabel.font
        goodsView.addSubview(goodsAmountLabel)
        let goodsButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: goodsView.frame.size))
        goodsButton.addTarget(self, action: #selector(toThisDetail), for: UIControl.Event.touchUpInside)
        goodsView.addSubview(goodsButton)
        staticScrollView.addSubview(goodsView)
        
        //订单信息区
        let orderView = UIView(frame: CGRect(x: 0, y: goodsView.frame.origin.y + goodsView.frame.height + 10 * screenScale, width: staticScrollView.frame.width, height: 124 * screenScale))
        orderView.backgroundColor = UIColor.white
        let orderAppTimeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 12 * screenScale, width: orderView.frame.width - 20 * screenScale, height: 20 * screenScale))
        orderAppTimeLabel.text = "\(NSLocalizedString("Applied time", comment: "")): \(LuckyUtils.timestampFormat(timestamp: data!.createtime, format: "yyyy-MM-dd HH:mm:ss"))"
        orderAppTimeLabel.textColor = UIColor.fontDarkGray()
        orderAppTimeLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        orderView.addSubview(orderAppTimeLabel)
        let orderDelTimeLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderAppTimeLabel.frame.origin.y + orderAppTimeLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderDelTimeLabel.text = "\(NSLocalizedString("Delivered time", comment: "")): \(data!.operattime == 0 ? "-" : LuckyUtils.timestampFormat(timestamp: data!.operattime, format: "yyyy-MM-dd HH:mm:ss"))"
        orderDelTimeLabel.textColor = orderAppTimeLabel.textColor
        orderDelTimeLabel.font = orderAppTimeLabel.font
        orderView.addSubview(orderDelTimeLabel)
        let orderCompanyLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderDelTimeLabel.frame.origin.y + orderDelTimeLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderCompanyLabel.text = "\(NSLocalizedString("Express Company", comment: "")): \(data!.detailInfo?.company == "" ? "-" : data!.detailInfo!.company)"
        orderCompanyLabel.textColor = orderAppTimeLabel.textColor
        orderCompanyLabel.font = orderAppTimeLabel.font
        orderView.addSubview(orderCompanyLabel)
        let orderNumberLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderCompanyLabel.frame.origin.y + orderCompanyLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderNumberLabel.text = "\(NSLocalizedString("Express Number", comment: "")): \(data!.detailInfo?.expressNumber == "" ? "-" : data!.detailInfo!.expressNumber)"
        orderNumberLabel.textColor = orderAppTimeLabel.textColor
        orderNumberLabel.font = orderAppTimeLabel.font
        orderNumberLabel.sizeToFit()
        orderView.addSubview(orderNumberLabel)
        if(data!.detailInfo?.expressNumber != ""){
            //订单号不为空 点击复制按钮
            let copyButton = UIButton(frame: CGRect(x: orderNumberLabel.frame.origin.x + orderNumberLabel.frame.width + 10 * screenScale, y: orderNumberLabel.frame.origin.y, width: orderNumberLabel.frame.height, height: orderNumberLabel.frame.height))
            copyButton.setImage(UIImage(named: "image_address_copy"), for: UIControl.State.normal)
            copyButton.addTarget(self, action: #selector(copyOrderNumber), for: UIControl.Event.touchUpInside)
            orderView.addSubview(copyButton)
        }
        staticScrollView.addSubview(orderView)
        
        //推荐区
        let recommendView = UIView(frame: CGRect(x: 0, y: orderView.frame.origin.y + orderView.frame.height + 4 * screenScale, width: staticScrollView.frame.width, height: 0))
        let recommendLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: recommendView.frame.width - 20 * screenScale, height: 50 * screenScale))
        recommendLabel.text = NSLocalizedString("Recommended for You", comment: "")
        recommendLabel.textColor = UIColor.fontBlack()
        recommendLabel.font = UIFont.heavyFont(size: UIFont.fontSizeBiggest() * screenScale)
        recommendView.addSubview(recommendLabel)
        
        if(recommendList.count > 0){
            //有推荐数据 创建第一条
            let childView1 = LuckyHomeGoodsCellButton(frame: CGRect(x: 0, y: recommendLabel.frame.origin.y + recommendLabel.frame.height, width: recommendView.frame.width/2, height: recommendCellHeight), data: recommendList[0])
            childView1.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            childView1.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            recommendView.addSubview(childView1)
            recommendView.frame.size = CGSize(width: recommendView.frame.width, height: childView1.frame.origin.y + childView1.frame.height)
        }
        if(recommendList.count > 1){
            //有两条以上 创建第二条
            let childView2 = LuckyHomeGoodsCellButton(frame: CGRect(x: recommendView.frame.width/2, y: recommendLabel.frame.origin.y + recommendLabel.frame.height, width: recommendView.frame.width/2, height: recommendCellHeight), data: recommendList[1])
            childView2.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            childView2.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            recommendView.addSubview(childView2)
        }
        
        if(recommendList.count > 2){
            //有三条以上 创建第三条
            let childView3 = LuckyHomeGoodsCellButton(frame: CGRect(x: 0, y: recommendLabel.frame.origin.y + recommendLabel.frame.height + recommendCellHeight, width: recommendView.frame.width/2, height: recommendCellHeight), data: recommendList[2])
            childView3.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            childView3.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            recommendView.addSubview(childView3)
            recommendView.frame.size = CGSize(width: recommendView.frame.width, height: childView3.frame.origin.y + childView3.frame.height)
        }
        
        if(recommendList.count > 3){
            //有四条以上 创建第四条
            let childView4 = LuckyHomeGoodsCellButton(frame: CGRect(x: recommendView.frame.width/2, y: recommendLabel.frame.origin.y + recommendLabel.frame.height + recommendCellHeight, width: recommendView.frame.width/2, height: recommendCellHeight), data: recommendList[3])
            childView4.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            childView4.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            recommendView.addSubview(childView4)
        }
        staticScrollView.addSubview(recommendView)
        //内容高度
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: recommendView.frame.origin.y + recommendView.frame.height)
    }
    
    //取数据
    func getData(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithToken("front/userAccount/receiveGet", params: ["uuid" : uuid], success: { (data) in
            let dataDic = data as! NSDictionary
            self.data = LuckyWinningInfoModel(data: dataDic)
            //取推荐 4条
            let paramsDic: NSMutableDictionary = ["pageNum": 1, "pageSize": 4, "status": "betting"]
            LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
                
                self.recommendList = []
                let dataArray = data as! [NSDictionary]
                if(dataArray.count > 0){
                    var goodsIssues: [LuckyLuckygameGoodsIssueModel] = []
                    for dataDic in dataArray{
                        goodsIssues.append(LuckyLuckygameGoodsIssueModel(data: dataDic))
                    }
                    self.recommendList.append(contentsOf: goodsIssues)
                }
                
                //创建滚动区内容
                self.createScrollContent()
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //复制订单号到粘贴板
    @objc func copyOrderNumber(){
        if(data!.detailInfo?.expressNumber != ""){
            UIPasteboard.general.string = data!.detailInfo?.expressNumber
            LuckyAlertView(title: "Copied successfully").showByTime(time: 2)
        }
    }
    
    //去本记录的商品详情页
    @objc func toThisDetail(){
        if(data != nil){
            let vc = LuckyDetailViewController()
            vc.uuid = data!.goodsIssue
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //推荐商品 去商品详情页
    @objc func toGoodsDetail(_ sender: LuckyHomeGoodsCellButton){
        let vc = LuckyDetailViewController()
        vc.uuid = sender.data.uuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //推荐商品 去商品购买页
    @objc func toGoodsBuy(_ sender: UIButton){
        if let  cellButton = sender.superview as? LuckyHomeGoodsCellButton{
            let vc = LuckyDetailViewController()
            vc.uuid = cellButton.data.uuid
            vc.type = "buy"
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
