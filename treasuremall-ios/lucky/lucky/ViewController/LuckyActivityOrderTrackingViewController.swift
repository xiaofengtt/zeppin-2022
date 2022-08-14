//
//  LuckyActivityOrderTrackingViewController.swift
//  lucky
//  活动兑实物详情
//  Created by Farmer Zhu on 2020/11/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityOrderTrackingViewController: UIViewController{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //滚动区
    private var staticScrollView: UIScrollView!
    
    //活动类型
    var type: String = ""
    
    //零元购数据
    var buyfreeData: LuckyFrontUserBuyfreeOrderModel? = nil
    //抽奖数据
    var scorelotteryData: LuckyFrontUserScorelotteryHistoryModel? = nil
    
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
        //创建滚动区内容
        createScrollContent()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backgroundColor = UIColor.white
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
        //需显示值
        var status: String = ""
        var cover: String = ""
        var title: String = ""
        var price: String = ""
        var createtime: String = ""
        var operattime: String = ""
        var detailInfo: LuckyProvideInfoModel!
        //根据不同活动类型赋值
        if(type == "buyfree"){
            //零元购
            status = buyfreeData!.status
            cover = buyfreeData!.cover
            title = buyfreeData!.title
            price = LuckyUtils.localCurrencyFormat(amount: buyfreeData!.price)
            createtime = LuckyUtils.timestampFormat(timestamp: buyfreeData!.winningTime, format: "yyyy-MM-dd HH:mm:ss")
            operattime = buyfreeData!.operattime == 0 ? "-" : LuckyUtils.timestampFormat(timestamp: buyfreeData!.operattime, format: "yyyy-MM-dd HH:mm:ss")
            detailInfo = buyfreeData!.detailInfo
        }else if(type == "scorelottery"){
            //抽奖
            status = scorelotteryData!.status
            cover = scorelotteryData!.prizeCoverUrl
            title = scorelotteryData!.prizeTitle
            price = LuckyUtils.localCurrencyFormat(amount: scorelotteryData!.price)
            createtime = LuckyUtils.timestampFormat(timestamp: scorelotteryData!.createtime, format: "yyyy-MM-dd HH:mm:ss")
            operattime = scorelotteryData!.oprattime == 0 ? "-" : LuckyUtils.timestampFormat(timestamp: scorelotteryData!.oprattime, format: "yyyy-MM-dd HH:mm:ss")
            detailInfo = scorelotteryData!.detailInfo
        }
        
        //滚动头
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 140 * screenScale))
        topView.backgroundColor = UIColor.white
        let appliedImageView = UIImageView(frame: CGRect(x: 35 * screenScale, y: 30 * screenScale, width: 40 * screenScale, height: 45 * screenScale))
        appliedImageView.image = UIImage(named: "image_activity_tracking_applied")
        topView.addSubview(appliedImageView)
        let appliedPointView = UIView(frame: CGRect(x: appliedImageView.frame.origin.x + (appliedImageView.frame.width - 10 * screenScale)/2, y: appliedImageView.frame.origin.y + appliedImageView.frame.height + 2 * screenScale, width: 10 * screenScale, height: 10 * screenScale))
        appliedPointView.layer.masksToBounds = true
        appliedPointView.layer.cornerRadius = appliedPointView.frame.width/2
        appliedPointView.backgroundColor = UIColor.activityMainColor()
        topView.addSubview(appliedPointView)
        let appliedLabel = UILabel(frame: CGRect(x: appliedPointView.frame.origin.x - 50 * screenScale, y: appliedPointView.frame.origin.y + appliedPointView.frame.height + 4 * screenScale, width: appliedPointView.frame.width + 100 * screenScale, height: 20 * screenScale))
        appliedLabel.text = NSLocalizedString("Applied", comment: "")
        appliedLabel.textColor = UIColor.fontBlack()
        appliedLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        appliedLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(appliedLabel)
        
        //兑奖进度 根据订单不同状态 拼接虚线/实现 不同图片
        let deliveredImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 40 * screenScale)/2, y: appliedImageView.frame.origin.y, width: 40 * screenScale, height: 45 * screenScale))
        if(status == "finished" || status == "confirm"){
            deliveredImageView.image = UIImage(named: "image_activity_tracking_delivered")
        }else{
            deliveredImageView.image = UIImage(named: "image_activity_tracking_undelivered")
        }
        topView.addSubview(deliveredImageView)
        let deliveredPointView = UIView(frame: CGRect(x: deliveredImageView.frame.origin.x + (deliveredImageView.frame.width - appliedPointView.frame.width)/2, y: appliedPointView.frame.origin.y, width: appliedPointView.frame.width, height: appliedPointView.frame.height))
        deliveredPointView.layer.masksToBounds = true
        deliveredPointView.layer.cornerRadius = deliveredPointView.frame.width/2
        if(status == "finished" || status == "confirm"){
            deliveredPointView.backgroundColor = UIColor.activityMainColor()
        }else{
            deliveredPointView.layer.borderColor = UIColor.activityMainColor().cgColor
            deliveredPointView.layer.borderWidth = 2 * screenScale
        }
        topView.addSubview(deliveredPointView)
        let deliveredLabel = UILabel(frame: CGRect(x: deliveredPointView.frame.origin.x - 50 * screenScale, y: appliedLabel.frame.origin.y, width: appliedLabel.frame.width, height: appliedLabel.frame.height))
        deliveredLabel.text = NSLocalizedString("Delivered", comment: "")
        if(status == "finished" || status == "confirm"){
            deliveredLabel.textColor = UIColor.fontBlack()
        }else{
            deliveredLabel.textColor = UIColor.fontGray()
        }
        deliveredLabel.font = appliedLabel.font
        deliveredLabel.textAlignment = appliedLabel.textAlignment
        topView.addSubview(deliveredLabel)
        let leftLine = UIView(frame: CGRect(x: appliedPointView.frame.origin.x + appliedPointView.frame.width, y: appliedPointView.frame.origin.y + (appliedPointView.frame.height - 2 * screenScale)/2, width: deliveredPointView.frame.origin.x - (appliedPointView.frame.origin.x + appliedPointView.frame.width), height: 2 * screenScale))
        leftLine.backgroundColor = UIColor.activityMainColor()
        topView.addSubview(leftLine)
        
        let completedImageView = UIImageView(frame: CGRect(x: topView.frame.width - 75 * screenScale, y: appliedImageView.frame.origin.y, width: 40 * screenScale, height: 45 * screenScale))
        if(status == "confirm"){
            completedImageView.image = UIImage(named: "image_activity_tracking_completed")
        }else{
            completedImageView.image = UIImage(named: "image_activity_tracking_uncompleted")
        }
        topView.addSubview(completedImageView)
        let completedPointView = UIView(frame: CGRect(x: completedImageView.frame.origin.x + (completedImageView.frame.width - appliedPointView.frame.width)/2, y: appliedPointView.frame.origin.y, width: appliedPointView.frame.width, height: appliedPointView.frame.height))
        completedPointView.layer.masksToBounds = true
        completedPointView.layer.cornerRadius = deliveredPointView.frame.width/2
        if(status == "confirm"){
            completedPointView.backgroundColor = UIColor.activityMainColor()
        }else{
            completedPointView.layer.borderColor = UIColor.activityMainColor().cgColor
            completedPointView.layer.borderWidth = 2 * screenScale
        }
        topView.addSubview(completedPointView)
        let completedLabel = UILabel(frame: CGRect(x: completedPointView.frame.origin.x - 50 * screenScale, y: appliedLabel.frame.origin.y, width: appliedLabel.frame.width, height: appliedLabel.frame.height))
        completedLabel.text = NSLocalizedString("Completed", comment: "")
        if(status == "confirm"){
            completedLabel.textColor = UIColor.fontBlack()
        }else{
            completedLabel.textColor = UIColor.fontGray()
        }
        completedLabel.font = appliedLabel.font
        completedLabel.textAlignment = appliedLabel.textAlignment
        topView.addSubview(completedLabel)
        if(status == "finished" || status == "confirm"){
            let rightLine = UIView(frame: CGRect(x: deliveredPointView.frame.origin.x + deliveredPointView.frame.width, y: leftLine.frame.origin.y, width: completedPointView.frame.origin.x - (deliveredPointView.frame.origin.x + deliveredPointView.frame.width), height: leftLine.frame.height))
            rightLine.backgroundColor = UIColor.activityMainColor()
            topView.addSubview(rightLine)
        }else{
            let rightLine = LuckyDottedLine(frame: CGRect(x: deliveredPointView.frame.origin.x + deliveredPointView.frame.width, y: leftLine.frame.origin.y, width: completedPointView.frame.origin.x - (deliveredPointView.frame.origin.x + deliveredPointView.frame.width), height: leftLine.frame.height), color: UIColor.activityMainColor(), direction: LuckyDottedLine.LuckyDottedLineDirection.horizontal)
            topView.addSubview(rightLine)
        }
        staticScrollView.addSubview(topView)
        
        //地址框
        let addressView = UIView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: staticScrollView.frame.width, height: 0))
        addressView.backgroundColor = UIColor.white
        let addressImageView = UIImageView(frame: CGRect(x: 12 * screenScale, y: 24 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
        addressImageView.image = UIImage(named: "image_activity_address")
        addressView.addSubview(addressImageView)
        staticScrollView.addSubview(addressView)
        let addressNameLabel = UILabel(frame: CGRect(x: addressImageView.frame.origin.x + addressImageView.frame.width + 10 * screenScale, y: 14 * screenScale, width: (addressView.frame.width - 10 * screenScale - (addressImageView.frame.origin.x + addressImageView.frame.width + 10 * screenScale)) * 0.6, height: 20 * screenScale))
        addressNameLabel.text = detailInfo.name
        addressNameLabel.textColor = UIColor.fontBlack()
        addressNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        addressView.addSubview(addressNameLabel)
        let addressMobileLabel = UILabel(frame: CGRect(x: addressNameLabel.frame.origin.y + addressNameLabel.frame.width, y: addressNameLabel.frame.origin.y, width: addressView.frame.width - 10 * screenScale - (addressNameLabel.frame.origin.y + addressNameLabel.frame.width), height: addressNameLabel.frame.height))
        addressMobileLabel.text = detailInfo.mobile
        addressMobileLabel.textColor = addressNameLabel.textColor
        addressMobileLabel.font = addressNameLabel.font
        addressMobileLabel.textAlignment = NSTextAlignment.right
        addressView.addSubview(addressMobileLabel)
        let addressLabel = UILabel(frame: CGRect(x: addressNameLabel.frame.origin.x, y: addressNameLabel.frame.origin.y + addressNameLabel.frame.height + 6 * screenScale, width: addressView.frame.width - 10 * screenScale - addressNameLabel.frame.origin.x, height: 0))
        addressLabel.numberOfLines = 0
        let addressStyle = NSMutableParagraphStyle()
        addressStyle.lineSpacing = 4 * screenScale
        addressLabel.attributedText = NSAttributedString(string: detailInfo.address, attributes: [NSAttributedString.Key.paragraphStyle : addressStyle])
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
        goodsImageView.sd_setImage(with: URL(string: cover), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        goodsView.addSubview(goodsImageView)
        let goodsContentLabel = UILabel(frame: CGRect(x: goodsImageView.frame.origin.x + goodsImageView.frame.width + 12 * screenScale, y: goodsImageView.frame.origin.y, width: goodsView.frame.width - 10 * screenScale - (goodsImageView.frame.origin.x + goodsImageView.frame.width + 12 * screenScale), height: 40 * screenScale))
        goodsContentLabel.numberOfLines = 2
        goodsContentLabel.textColor = UIColor.fontBlack()
        goodsContentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let contentStyle = NSMutableParagraphStyle()
        contentStyle.lineBreakMode = NSLineBreakMode.byTruncatingTail
        contentStyle.maximumLineHeight = 20 * screenScale
        contentStyle.minimumLineHeight = 20 * screenScale
        var contentString = ""
        if(type == "buyfree"){
            //零元购
            contentString = "\(NSLocalizedString("Issue", comment: "")):\(buyfreeData!.issueNum) | "
        }
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + title, attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
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
        goodsAmountLabel.text = "\(price)"
        goodsAmountLabel.textColor = goodsNumberLabel.textColor
        goodsAmountLabel.font = goodsNumberLabel.font
        goodsView.addSubview(goodsAmountLabel)
        staticScrollView.addSubview(goodsView)
        
        //订单信息
        let orderView = UIView(frame: CGRect(x: 0, y: goodsView.frame.origin.y + goodsView.frame.height + 10 * screenScale, width: staticScrollView.frame.width, height: 124 * screenScale))
        orderView.backgroundColor = UIColor.white
        let orderAppTimeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 12 * screenScale, width: orderView.frame.width - 20 * screenScale, height: 20 * screenScale))
        orderAppTimeLabel.text = "\(NSLocalizedString("Applied time", comment: "")): \(createtime)"
        orderAppTimeLabel.textColor = UIColor.fontDarkGray()
        orderAppTimeLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        orderView.addSubview(orderAppTimeLabel)
        let orderDelTimeLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderAppTimeLabel.frame.origin.y + orderAppTimeLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderDelTimeLabel.text = "\(NSLocalizedString("Delivered time", comment: "")): \(operattime)"
        orderDelTimeLabel.textColor = orderAppTimeLabel.textColor
        orderDelTimeLabel.font = orderAppTimeLabel.font
        orderView.addSubview(orderDelTimeLabel)
        let orderCompanyLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderDelTimeLabel.frame.origin.y + orderDelTimeLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderCompanyLabel.text = "\(NSLocalizedString("Express Company", comment: "")): \(detailInfo.company == "" ? "-" : detailInfo.company)"
        orderCompanyLabel.textColor = orderAppTimeLabel.textColor
        orderCompanyLabel.font = orderAppTimeLabel.font
        orderView.addSubview(orderCompanyLabel)
        let orderNumberLabel = UILabel(frame: CGRect(x: orderAppTimeLabel.frame.origin.x, y: orderCompanyLabel.frame.origin.y + orderCompanyLabel.frame.height + 6 * screenScale, width: orderAppTimeLabel.frame.width, height: orderAppTimeLabel.frame.height))
        orderNumberLabel.text = "\(NSLocalizedString("Express Number", comment: "")): \(detailInfo.expressNumber == "" ? "-" : detailInfo.expressNumber)"
        orderNumberLabel.textColor = orderAppTimeLabel.textColor
        orderNumberLabel.font = orderAppTimeLabel.font
        orderNumberLabel.sizeToFit()
        orderView.addSubview(orderNumberLabel)
        if(detailInfo.expressNumber != ""){
            //订单号不为空 点击复制按钮
            let copyButton = UIButton(frame: CGRect(x: orderNumberLabel.frame.origin.x + orderNumberLabel.frame.width + 10 * screenScale, y: orderNumberLabel.frame.origin.y, width: orderNumberLabel.frame.height, height: orderNumberLabel.frame.height))
            copyButton.setImage(UIImage(named: "image_activity_address_copy"), for: UIControl.State.normal)
            copyButton.addTarget(self, action: #selector(copyOrderNumber), for: UIControl.Event.touchUpInside)
            orderView.addSubview(copyButton)
        }
        staticScrollView.addSubview(orderView)
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: orderView.frame.origin.y + orderView.frame.height)
    }
    
    //返回上一页
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
        if(type == "buyfree"){
            //零元购
            if let expressNumber = buyfreeData?.detailInfo?.expressNumber{
                if(expressNumber != ""){
                    UIPasteboard.general.string = expressNumber
                    LuckyAlertView(title: "Copied successfully").showByTime(time: 2)
                }
            }
        }else if(type == "scorelottery"){
            //抽奖
            if let expressNumber = scorelotteryData?.detailInfo?.expressNumber{
                if(expressNumber != ""){
                    UIPasteboard.general.string = expressNumber
                    LuckyAlertView(title: "Copied successfully").showByTime(time: 2)
                }
            }
        }
    }
}
