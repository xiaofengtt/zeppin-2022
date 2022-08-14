//
//  GiftView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/22.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class GiftView: UIView, UITableViewDelegate, UITableViewDataSource, UICollisionBehaviorDelegate{
    
    var parent: UIViewController!
    var showView: UIView!
    var mainView: UIView!
    var bgView: UIImageView!
    var tableView: UITableView!
    var moneyLabel: UILabel!
    var couponLabel: UILabel!
    var shareButton: UIButton!
    var getButton: UIButton!
    
    var giftList: [CouponModel] = []
    var isShared: Bool = false
    
    init(parent: UIViewController) {
        super.init(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
        self.parent = parent
        self.layer.zPosition = 0.8
        self.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        
        createShowView()
    }
    
    func createShowView(){
        showView = UIView(frame: CGRect(x: (self.frame.width - 245 * screenScale)/2, y: -315 * screenScale, width: 245 * screenScale, height: 315 * screenScale))
        let showImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: showView.frame.size))
        showImage.image = UIImage(named: "gift_show")
        showView.addSubview(showImage)
        let showButton = UIButton(frame: showImage.frame)
        showButton.addTarget(self, action: #selector(show(_:)), for: UIControlEvents.touchUpInside)
        showView.addSubview(showButton)
        self.addSubview(showView)
    }
    
    func createMainView(){
        if(giftList[0].couponType == "redPacket"){
            mainView = UIView(frame: CGRect(x: (self.frame.width - 310 * screenScale)/2, y: (self.frame.height - 435 * screenScale)/2, width: 310 * screenScale, height: 435 * screenScale))
        }else{
            mainView = UIView(frame: CGRect(x: (self.frame.width - 310 * screenScale)/2, y: (self.frame.height - 435 * screenScale)/2, width: 310 * screenScale, height: 415 * screenScale))
        }
        
        let tableBgView = UIView(frame: CGRect(x: 36 * screenScale, y: 22 * screenScale, width: mainView.frame.width - 72 * screenScale, height: 240 * screenScale))
        tableBgView.backgroundColor = UIColor.white
        tableView = UITableView(frame: CGRect(x: 20 * screenScale, y: 0, width: tableBgView.frame.width - 40 * screenScale, height: tableBgView.frame.height))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableBgView.addSubview(tableView)
        mainView.addSubview(tableBgView)
        
        bgView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: mainView.frame.size))
        bgView.image = UIImage(named: "gift_bg_long")
        mainView.addSubview(bgView)
        if(giftList[0].couponType == "redPacket"){
            shareButton = UIButton(frame: CGRect(x: (mainView.frame.width - 185 * screenScale)/2, y: tableBgView.frame.origin.y + tableBgView.frame.height + 15 * screenScale, width: 185 * screenScale, height: 40 * screenScale))
            shareButton.layer.cornerRadius = shareButton.frame.height/2
            shareButton.backgroundColor = UIColor(red: 255/255, green: 215/255, blue: 27/255, alpha: 1)
            shareButton.setTitle("分享额外得2元现金", for: UIControlState.normal)
            shareButton.setTitleColor(UIColor(red: 213/255, green: 49/255, blue: 10/255, alpha: 1), for: UIControlState.normal)
            shareButton.addTarget(self, action: #selector(share(_:)), for: UIControlEvents.touchUpInside)
            shareButton.titleLabel?.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
            mainView.addSubview(shareButton)
            
            getButton = UIButton(frame: CGRect(x: (mainView.frame.width - 185 * screenScale)/2, y: shareButton.frame.origin.y + shareButton.frame.height + 12 * screenScale, width: 185 * screenScale, height: 40 * screenScale))
            getButton.layer.cornerRadius = getButton.frame.height/2
            getButton.backgroundColor = UIColor(red: 255/255, green: 215/255, blue: 27/255, alpha: 1)
            getButton.setTitle("不分享，直接领取", for: UIControlState.normal)
            getButton.setTitleColor(UIColor(red: 213/255, green: 49/255, blue: 10/255, alpha: 1), for: UIControlState.normal)
            getButton.titleLabel?.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
            getButton.addTarget(self, action: #selector(get(_:)), for: UIControlEvents.touchUpInside)
            mainView.addSubview(getButton)
        }else{
            getButton = UIButton(frame: CGRect(x: (mainView.frame.width - 185 * screenScale)/2, y: tableBgView.frame.origin.y + tableBgView.frame.height + 30 * screenScale, width: 185 * screenScale, height: 40 * screenScale))
            getButton.layer.cornerRadius = getButton.frame.height/2
            getButton.backgroundColor = UIColor(red: 255/255, green: 215/255, blue: 27/255, alpha: 1)
            getButton.setTitle("直接领取", for: UIControlState.normal)
            getButton.setTitleColor(UIColor(red: 213/255, green: 49/255, blue: 10/255, alpha: 1), for: UIControlState.normal)
            getButton.titleLabel?.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
            getButton.addTarget(self, action: #selector(get(_:)), for: UIControlEvents.touchUpInside)
            mainView.addSubview(getButton)
        }
        moneyLabel = UILabel(frame: CGRect(x: 0, y: mainView.frame.height - (20 + 15) * screenScale , width: mainView.frame.width, height: 15 * screenScale))
        moneyLabel.text = "现金红包可在“我的账户余额”中查看"
        moneyLabel.textColor = UIColor(red: 144/255, green: 28/255, blue: 24/255, alpha: 1)
        moneyLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        moneyLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(moneyLabel)
        
        couponLabel = UILabel(frame: CGRect(x: 0, y: moneyLabel.frame.origin.y - 15 * screenScale, width: mainView.frame.width, height: 15 * screenScale))
        couponLabel.text = "优惠券可在“我的优惠券”中查看"
        couponLabel.textColor = moneyLabel.textColor
        couponLabel.font = moneyLabel.font
        couponLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(couponLabel)
        
        self.addSubview(mainView)
    }
    
    func showAnimation(){
        self.showView.frame.origin.y = -315 * screenScale
        UIView.animate(withDuration: 1, delay: 0.5, usingSpringWithDamping: 0.5, initialSpringVelocity: 10.0, options: UIViewAnimationOptions.curveEaseOut, animations: {
            self.showView.frame.origin.y = (self.frame.height - 315 * screenScale)/2
        }, completion: nil)
        
    }
    
    @objc func show(_ sender: UIButton){
        showView.removeFromSuperview()
        createMainView()
    }
    
    @objc func share(_ sender: UIButton){
        let message = UMSocialMessageObject()
        let shareObj = UMShareImageObject()
        shareObj.shareImage = UIImage(named: "gift_share_bg")!.addMoneyWatermark(moneyString: String(format: "%.2f", giftList[0].couponPrice + 2))
        message.shareObject = shareObj
        
        UIPasteboard.general.string = "+\(String(format: "%.2f", giftList[0].couponPrice))"
        
        UMSocialManager.default().share(to: UMSocialPlatformType.wechatTimeLine, messageObject: message, currentViewController: parent) { (data, error) in
            if(data != nil){
                self.shareSuccess()
            }else{
                AlertView(title: "分享失败").showByTime(time: 2)
            }
        }
    }
    
    @objc func get(_ sender: UIButton){
        let loadingView = HttpController.showLoading(viewController: parent)
        
        var couponString = ""
        var redPacketString = ""
        
        for gift in giftList{
            if(gift.couponType == "redPacket"){
                redPacketString = redPacketString + gift.uuid + ","
            }else{
                couponString = couponString + gift.uuid + ","
            }
        }
        if couponString != "" {
            couponString = String(couponString[..<String.Index.init(encodedOffset: couponString.count - 1)])
        }
        
        if redPacketString != "" {
            redPacketString = String(redPacketString[..<String.Index.init(encodedOffset: redPacketString.count - 1)])
        }
        
        HttpController.getToken(data: { (token) in
            HttpController.post("coupon/activate", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "flagShare": self.isShared ? "true" : "false", "coupons": couponString, "redPackets": redPacketString]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.removeFromSuperview()
                }else{
                    let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                    AlertView(title: message).showByTime(time: 2)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }, errors: { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self.parent)
            })
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self.parent)
        }
    }
    
    @objc func cancle(_ sender: UIButton){
        self.removeFromSuperview()
    }
    
    func shareSuccess(){
        self.isShared = true
        
        mainView.frame.size = CGSize(width: mainView.frame.width, height: 415 * screenScale)
        bgView.frame.size = mainView.frame.size
        moneyLabel.frame.origin = CGPoint(x: 0, y: mainView.frame.height - (20 + 15) * screenScale)
        couponLabel.frame.origin = CGPoint(x: 0, y: moneyLabel.frame.origin.y - 15 * screenScale)
        getButton.frame.origin = CGPoint(x: getButton.frame.origin.x, y: shareButton.frame.origin.y + 10 * screenScale)
        getButton.setTitle("立即领取", for: UIControlState.normal)
        shareButton.isHidden = true
        tableView.reloadData()
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50 * screenScale
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
        view.backgroundColor = UIColor.white
        let label = UILabel(frame: CGRect(origin: CGPoint.zero, size: view.frame.size))
        label.text = "恭喜您获得了"
        label.textColor = UIColor(red: 250/255, green: 76/255, blue: 55/255, alpha: 1)
        label.font = UIFont.mediumFont(size: 20 * screenScale)
        label.textAlignment = NSTextAlignment.center
        view.addSubview(label)
        return view
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(giftList.count > 0){
            return giftList.count + 1
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(giftList.count > 0 && giftList.count != indexPath.row){
            return 60 * screenScale
        }else if(giftList.count > 0 && giftList.count == indexPath.row){
            return 20 * screenScale
        }else{
            return tableView.frame.height - 50 * screenScale
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(giftList.count > 0 && giftList.count != indexPath.row){
            let gift = giftList[indexPath.row]
            
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
            view.backgroundColor = UIColor(red: 255/255, green: 245/255, blue: 224/255, alpha: 1)
            
            let leftView = UIView(frame: CGRect(x: 0, y: 0, width: view.frame.width * 0.4, height: view.frame.height))
            let leftNumLabel = UILabel()
            if(gift.couponType == "redPacket"){
                if(isShared){
                    leftNumLabel.text = String(format: "%.2f", gift.couponPrice + 2)
                }else{
                    leftNumLabel.text = String(format: "%.2f", gift.couponPrice)
                }
            }else{
                leftNumLabel.text = gift.couponPriceCN
            }
            leftNumLabel.textColor = UIColor(red: 245/255, green: 53/255, blue: 41/255, alpha: 1)
            leftNumLabel.font = UIFont.numFont(size: 20 * screenScale)
            leftNumLabel.textAlignment = NSTextAlignment.center
            leftNumLabel.sizeToFit()
            leftNumLabel.frame.origin = CGPoint(x: (leftView.frame.width - leftNumLabel.frame.width)/2 - 5 * screenScale, y: (leftView.frame.height - leftNumLabel.frame.height)/2)
            leftView.addSubview(leftNumLabel)
            let leftSignLabel = UILabel(frame: CGRect(x: leftNumLabel.frame.origin.x + leftNumLabel.frame.width, y: leftNumLabel.frame.origin.y + leftNumLabel.frame.height - 14 * screenScale, width: 14 * screenScale, height: 14 * screenScale))
            if(gift.couponType == "cash"){
                leftSignLabel.text = "元"
            }else if(gift.couponType == "interests"){
                leftSignLabel.text = "%"
            }else if(gift.couponType == "redPacket"){
                leftSignLabel.text = "元"
            }
            leftSignLabel.textColor = leftNumLabel.textColor
            leftSignLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            leftView.addSubview(leftSignLabel)
            let spaceLine = CAShapeLayer()
            spaceLine.frame = CGRect(x: leftView.frame.width - 1, y: 0, width: 1, height: leftView.frame.height)
            spaceLine.fillColor = UIColor.clear.cgColor
            spaceLine.strokeColor = UIColor(red: 248/255, green: 208/255, blue: 122/255, alpha: 1).cgColor
            spaceLine.lineWidth = 1
            spaceLine.lineJoin = kCALineJoinRound
            spaceLine.lineDashPattern = [1,1]
            let path = CGMutablePath()
            path.move(to: CGPoint.zero)
            path.addLine(to: CGPoint(x: 0, y: spaceLine.frame.height))
            spaceLine.path = path
            leftView.layer.addSublayer(spaceLine)
            view.addSubview(leftView)
            
            let rightView = UIView(frame: CGRect(x: leftView.frame.origin.x + leftView.frame.width, y: 0, width: view.frame.width - leftView.frame.width, height: view.frame.height))
            let rightTypeLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 6 * screenScale, width: rightView.frame.width - 10 * screenScale, height: 14 * screenScale))
            rightTypeLabel.text = gift.couponTypeCN
            rightTypeLabel.textColor = UIColor(red: 250/255, green: 75/255, blue: 55/255, alpha: 1)
            rightTypeLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            rightTypeLabel.sizeToFit()
            rightTypeLabel.frame.size = CGSize(width: rightTypeLabel.frame.width, height: 14 * screenScale)
            rightView.addSubview(rightTypeLabel)
            if(isShared && gift.couponType == "redPacket"){
                let rightSharedLabel = UILabel(frame: CGRect(x: rightTypeLabel.frame.origin.x + rightTypeLabel.frame.width + 4 * screenScale, y: 9 * screenScale, width: 0, height: 10 * screenScale))
                rightSharedLabel.backgroundColor = UIColor(red: 250/255, green: 76/255, blue: 55/255, alpha: 1)
                rightSharedLabel.text = "  +2元  "
                rightSharedLabel.textColor = UIColor(red: 255/255, green: 245/255, blue: 224/255, alpha: 1)
                rightSharedLabel.font = UIFont.mainFont(size: 8 * screenScale)
                rightSharedLabel.sizeToFit()
                rightSharedLabel.frame.size = CGSize(width: rightSharedLabel.frame.width, height: 10 * screenScale)
                rightSharedLabel.layer.cornerRadius = rightSharedLabel.frame.height/2
                rightSharedLabel.layer.masksToBounds = true
                rightView.addSubview(rightSharedLabel)
            }
            let rightLimitLabel = UILabel(frame: CGRect(x: rightTypeLabel.frame.origin.x, y: rightTypeLabel.frame.origin.y + rightTypeLabel.frame.height + 2 * screenScale, width: rightView.frame.width - 10 * screenScale, height: 10 * screenScale))
            if(gift.couponType == "redPacket"){
                rightLimitLabel.text = "可投资，可提现"
            }else{
                rightLimitLabel.text = gift.minInvestAccount > 0 ? "单笔投资\(Utils.getMoneyByUnit(money: gift.minInvestAccount))元起可用" : "单笔投资无限制"
            }
            rightLimitLabel.textColor = UIColor.fontGray()
            rightLimitLabel.font = UIFont.lightFont(size: 8 * screenScale)
            rightView.addSubview(rightLimitLabel)
            let rightTimeLabel = UILabel(frame: CGRect(x: rightTypeLabel.frame.origin.x, y: rightLimitLabel.frame.origin.y + rightLimitLabel.frame.height, width: rightLimitLabel.frame.width, height: 10 * screenScale))
            rightTimeLabel.text = gift.expiryDateCN != "" ? "\(gift.expiryDateCN)截止" : "无期限"
            rightTimeLabel.textColor = rightLimitLabel.textColor
            rightTimeLabel.font = rightLimitLabel.font
            rightView.addSubview(rightTimeLabel)
            
            view.addSubview(rightView)
            cell.addSubview(view)
        }
        return cell
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
