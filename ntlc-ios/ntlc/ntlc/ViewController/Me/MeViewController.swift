//
//  ViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/14.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class MeViewController: UIViewController{

    @IBOutlet weak var mainScrollView: UIScrollView!
    
    var noticeBarButton: UIBarButtonItem!
    var navigationBackground: NavigationBackground = NavigationBackground()
    var headerLoginView: UIView = UIView()
    var headerUnloginView: UIView = UIView()
    var authView: UIView = UIView()
    var funcView: UIView = UIView()
    var logoutView: UIView = UIView()
    var logoutAlert: UIAlertController = UIAlertController()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    var willDisapper = false
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        let rightButton = UIButton()
        let rightImage = UIImage(named: "me_navigation_notice")!
        rightButton.setImage(rightImage.imageWithScale(20/rightImage.size.height), for: UIControlState.normal)
        rightButton.setImage(rightImage.imageWithScale(20/rightImage.size.height).imageWithAlpha(0.5), for: UIControlState.highlighted)
        rightButton.sizeToFit()
        rightButton.addTarget(self, action: #selector(toNoticeList(_:)), for: UIControlEvents.touchUpInside)
        noticeBarButton = UIBarButtonItem(customView: rightButton)
        if(user != nil){
            self.navigationItem.rightBarButtonItem = noticeBarButton
            if(user!.messageFlag){
                noticeBarButton.showRedPoint()
            }else{
                noticeBarButton.hideRedPoint()
            }
        }
        
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)

        mainScrollView.backgroundColor = UIColor.backgroundGray()
        if #available(iOS 11.0, *) {
            mainScrollView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }

        createHeaderLogin()
        createHeaderUnlogin()
        createAuthView()
        createFuncView()
        createLogout()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        willDisapper = false
        reloadViews()
        if(user != nil){
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getUser(uuid: user!.uuid, data: { (data) in
                if(!self.willDisapper){
                    self.reloadViews()
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }
        getCouponList()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        willDisapper = true
    }

    func createHeaderUnlogin(){
        headerUnloginView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft*2 * screenScale, height: 90 * screenScale))
        headerUnloginView.backgroundColor = UIColor.white
        headerUnloginView.layer.cornerRadius = cornerRadius * screenScale
        headerUnloginView.addBaseShadow()
        
        let title = UILabel(frame: CGRect(x: 10 * screenScale, y: 23 * screenScale, width: screenWidth * 0.8, height: 22 * screenScale))
        title.text = "点击注册／登录"
        title.textColor = UIColor(red: 33/255, green: 33/255, blue: 33/255, alpha: 0.7)
        title.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        headerUnloginView.addSubview(title)
        
        let content = UILabel(frame: CGRect(x: 10 * screenScale, y: title.frame.origin.y + title.frame.height + 5 * screenScale, width: screenWidth * 0.8, height: 17 * screenScale))
        content.text = "1秒登录，查看更多理财产品"
        content.textColor = UIColor(red: 33/255, green: 33/255, blue: 33/255, alpha: 0.5)
        content.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        headerUnloginView.addSubview(content)
        
        let image = UIImageView(frame: CGRect(x: headerUnloginView.frame.width - 12 * screenScale - (headerUnloginView.frame.height - 15*2 * screenScale), y: 15 * screenScale, width: headerUnloginView.frame.height - 15*2 * screenScale, height: headerUnloginView.frame.height - 15*2 * screenScale))
        image.image = UIImage.init(named: "me_head_unlogin")
        headerUnloginView.addSubview(image)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: headerUnloginView.frame.size))
        button.addTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
        headerUnloginView.addSubview(button)
        
        mainScrollView.addSubview(headerUnloginView)
    }
    
    func createHeaderLogin(){
        if(isIphoneX()){
            headerLoginView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 224 * screenScale))
            
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerLoginView.frame.size))
            background.image = UIImage.init(named: "me_header_x")
            headerLoginView.addSubview(background)
            
            let icon = UIImageView(frame: CGRect(x: (headerLoginView.frame.width - 60 * screenScale) / 2, y: headerLoginView.frame.height - 108 * screenScale, width: 60 * screenScale, height: 60 * screenScale))
            icon.image = UIImage.init(named: "me_head_login")
            headerLoginView.addSubview(icon)
            
            let name = UILabel(frame: CGRect(x: 0, y: headerLoginView.frame.height - 38 * screenScale, width: headerLoginView.frame.width, height: 20 * screenScale))
            name.tag = TagController.meTags.nameTitle
            name.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            name.textAlignment = NSTextAlignment.center
            name.textColor = UIColor.white
            headerLoginView.addSubview(name)
        }else{
            headerLoginView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 190 * screenScale))
            
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerLoginView.frame.size))
            background.image = UIImage.init(named: "me_header")
            headerLoginView.addSubview(background)
            
            let icon = UIImageView(frame: CGRect(x: (headerLoginView.frame.width - 60 * screenScale) / 2, y: headerLoginView.frame.height - 108 * screenScale, width: 60 * screenScale, height: 60 * screenScale))
            icon.tag = TagController.meTags.userIcon
            icon.layer.masksToBounds = true
            icon.layer.cornerRadius = icon.frame.width/2
            if(user != nil && user!.bindingAliFlag){
                SDWebImageManager.shared().loadImage(with: URL(string: user!.aliPhoto), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                    if result && SDImage != nil{
                        icon.image = SDImage
                    }else{
                        icon.image = UIImage.init(named: "me_head_login")
                    }
                }
            }else{
                icon.image = UIImage.init(named: "me_head_login")
            }
            headerLoginView.addSubview(icon)
            
            let name = UILabel(frame: CGRect(x: 0, y: headerLoginView.frame.height - 38 * screenScale, width: headerLoginView.frame.width, height: 20 * screenScale))
            name.tag = TagController.meTags.nameTitle
            name.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            name.textAlignment = NSTextAlignment.center
            name.textColor = UIColor.white
            headerLoginView.addSubview(name)
        }
        mainScrollView.addSubview(headerLoginView)
    }
    
    func createAuthView(){
        authView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: screenWidth - paddingLeft*2 * screenScale, height: 100 * screenScale))
        authView.backgroundColor = UIColor.white
        authView.layer.cornerRadius = cornerRadius * screenScale
        authView.addBaseShadow()
        
        let leftView = UIView(frame: CGRect(x: 0, y: 0, width: authView.frame.width / 2, height: authView.frame.height))
        
        let leftImage = UIImageView(frame: CGRect(x: (leftView.frame.width - 56 * screenScale)/2, y: 16 * screenScale, width: 56 * screenScale, height: 35 * screenScale))
        leftImage.tag = TagController.meTags.idcardImage
        leftView.addSubview(leftImage)
        
        let leftTitle = UILabel(frame: CGRect(x: 0, y: leftImage.frame.origin.y + leftImage.frame.height + 3 * screenScale, width: leftView.frame.width, height: 20 * screenScale))
        leftTitle.text = "实名认证"
        leftTitle.textAlignment = NSTextAlignment.center
        leftTitle.textColor = UIColor.black
        leftTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        leftView.addSubview(leftTitle)
        
        let leftStatus = UILabel(frame: CGRect(x: 0, y: leftTitle.frame.origin.y + leftTitle.frame.height + 1 * screenScale, width: leftView.frame.width, height: 14 * screenScale))
        leftStatus.tag = TagController.meTags.idcardStatus
        leftStatus.textAlignment = NSTextAlignment.center
        leftStatus.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        leftView.addSubview(leftStatus)
        
        let spaceLine = UIView(frame: CGRect(x: leftView.frame.width - 1, y: leftView.frame.height/4, width: 1, height: leftView.frame.height/2))
        spaceLine.backgroundColor = UIColor.backgroundGray()
        leftView.addSubview(spaceLine)
        
        let leftButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: leftView.frame.size))
        leftButton.tag = TagController.meTags.idcardButton
        leftView.addSubview(leftButton)
        authView.addSubview(leftView)
        
        let rightView = UIView(frame: CGRect(x: authView.frame.width / 2, y: 0, width: authView.frame.width / 2, height: authView.frame.height))
        
        let rightImage = UIImageView(frame: CGRect(x: (rightView.frame.width - 56 * screenScale)/2, y: 16 * screenScale, width: 56 * screenScale, height: 35 * screenScale))
        rightImage.tag = TagController.meTags.bankcardImage
        rightView.addSubview(rightImage)
        
        let rightTitle = UILabel(frame: CGRect(x: 0, y: rightImage.frame.origin.y + rightImage.frame.height + 3 * screenScale, width: leftView.frame.width, height: 20 * screenScale))
        rightTitle.text = "我的银行卡"
        rightTitle.textAlignment = NSTextAlignment.center
        rightTitle.textColor = UIColor.black
        rightTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        rightView.addSubview(rightTitle)
        
        let rightStatus = UILabel(frame: CGRect(x: 0, y: rightTitle.frame.origin.y + rightTitle.frame.height + 1 * screenScale, width: leftView.frame.width, height: 14 * screenScale))
        rightStatus.tag = TagController.meTags.bankcardStatus
        rightStatus.textAlignment = NSTextAlignment.center
        rightStatus.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        rightView.addSubview(rightStatus)
        
        let rightButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: rightView.frame.size))
        rightButton.tag = TagController.meTags.bankcardButton
        rightView.addSubview(rightButton)
        authView.addSubview(rightView)
        
        mainScrollView.addSubview(authView)
    }
    
    func createFuncView(){
        funcView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: screenWidth - paddingLeft*2 * screenScale, height: 0))
        funcView.backgroundColor = UIColor.clear
        funcView.layer.cornerRadius = cornerRadius * screenScale
        
        let rowHeight: CGFloat = 45
        
        let history = UIView(frame: CGRect(x: 0, y: 0, width: funcView.frame.width, height: rowHeight * screenScale))
        history.backgroundColor = UIColor.white
        
        let historyLable = UILabel(frame: CGRect(x: 7 * screenScale, y: (rowHeight - 20) / 2 * screenScale, width: screenWidth/2 - 7 * screenScale, height: 20 * screenScale))
        historyLable.text = "交易记录"
        historyLable.textColor = UIColor.fontBlack()
        historyLable.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        history.addSubview(historyLable)
        
        let historyIcon = UIImageView(frame: CGRect(x: history.frame.width - (8 + 10) * screenScale, y: (rowHeight - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
        historyIcon.image = UIImage.init(named: "common_enter")
        history.addSubview(historyIcon)
        
        let historyButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: history.frame.size))
        historyButton.tag = TagController.meTags.historyButton
        history.addSubview(historyButton)
        funcView.addSubview(history)
        
        let contact = UIView()
        if(user != nil){
            let coupon = UIView(frame: CGRect(x: 0, y: history.frame.origin.y + history.frame.height, width: funcView.frame.width, height: rowHeight * screenScale))
            coupon.backgroundColor = UIColor.white
            
            let couponTopLine = CALayer()
            couponTopLine.frame = CGRect(x: 0, y: 0, width: coupon.frame.width, height: 1)
            couponTopLine.backgroundColor = UIColor.backgroundGray().cgColor
            coupon.layer.addSublayer(couponTopLine)
            
            let couponLable = UILabel(frame: historyLable.frame)
            couponLable.text = "优惠券"
            couponLable.textColor = historyLable.textColor
            couponLable.font = historyLable.font
            coupon.addSubview(couponLable)
            
            let couponIcon = UIImageView(frame: CGRect(x: history.frame.width - (8 + 10) * screenScale, y: (rowHeight - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
            couponIcon.image = UIImage.init(named: "common_enter")
            coupon.addSubview(couponIcon)
            
            let couponStatus = UILabel(frame: CGRect(x: coupon.frame.width/2, y: 0, width: couponIcon.frame.origin.x - 8 * screenScale - coupon.frame.width/2, height: coupon.frame.height))
            couponStatus.text = user!.couponCount > 0 ? "\(user!.couponCount)张" : "无可用"
            couponStatus.textColor = UIColor.fontGray()
            couponStatus.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
            couponStatus.textAlignment = NSTextAlignment.right
            coupon.addSubview(couponStatus)
            
            let couponButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: coupon.frame.size))
            couponButton.addTarget(self, action: #selector(toCouponList(_:)), for: UIControlEvents.touchUpInside)
            coupon.addSubview(couponButton)
            funcView.addSubview(coupon)
            
            let modifyPwd = UIView()
            if(isPublish){
                let alipay = UIView(frame: CGRect(x: 0, y: coupon.frame.origin.y + coupon.frame.height, width: funcView.frame.width, height: rowHeight * screenScale))
                alipay.backgroundColor = UIColor.white
                
                let alipayTopLine = CALayer()
                alipayTopLine.frame = CGRect(x: 0, y: 0, width: alipay.frame.width, height: 1)
                alipayTopLine.backgroundColor = UIColor.backgroundGray().cgColor
                alipay.layer.addSublayer(alipayTopLine)
                
                let alipayLable = UILabel(frame: historyLable.frame)
                alipayLable.text = "绑定支付宝"
                alipayLable.textColor = historyLable.textColor
                alipayLable.font = historyLable.font
                alipay.addSubview(alipayLable)
                
                let alipayIcon = UIImageView(frame: CGRect(x: history.frame.width - (8 + 10) * screenScale, y: (rowHeight - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
                alipayIcon.image = UIImage.init(named: "common_enter")
                alipay.addSubview(alipayIcon)
                
                let alipayStatus = UILabel(frame: CGRect(x: alipay.frame.width/2, y: 0, width: alipayIcon.frame.origin.x - 8 * screenScale - alipay.frame.width/2, height: alipay.frame.height))
                alipayStatus.text = user!.bindingAliFlag ? "已绑定" : "未绑定"
                alipayStatus.textColor = user!.bindingAliFlag ? UIColor.selectedColor() : UIColor.fontGray()
                alipayStatus.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
                alipayStatus.textAlignment = NSTextAlignment.right
                alipay.addSubview(alipayStatus)
                
                let alipayButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: alipay.frame.size))
                alipayButton.addTarget(self, action: #selector(toAlipayResult(_:)), for: UIControlEvents.touchUpInside)
                alipay.addSubview(alipayButton)
                funcView.addSubview(alipay)
                
                modifyPwd.frame = CGRect(x: 0, y: alipay.frame.origin.y + alipay.frame.height, width: funcView.frame.width, height: rowHeight * screenScale)
            }else{
                modifyPwd.frame = CGRect(x: 0, y: coupon.frame.origin.y + coupon.frame.height, width: funcView.frame.width, height: rowHeight * screenScale)
            }
            modifyPwd.backgroundColor = UIColor.white
            
            let modifyTopLine = CALayer()
            modifyTopLine.frame = CGRect(x: 0, y: 0, width: modifyPwd.frame.width, height: 1)
            modifyTopLine.backgroundColor = UIColor.backgroundGray().cgColor
            modifyPwd.layer.addSublayer(modifyTopLine)
            
            let modifyLable = UILabel(frame: historyLable.frame)
            if(user!.pwdFlag){
                modifyLable.text = "修改密码"
            }else{
                modifyLable.text = "设置密码"
            }
            modifyLable.textColor = historyLable.textColor
            modifyLable.font = historyLable.font
            modifyPwd.addSubview(modifyLable)
            
            let modifyIcon = UIImageView(frame: CGRect(x: history.frame.width - (8 + 10) * screenScale, y: (rowHeight - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
            modifyIcon.image = UIImage.init(named: "common_enter")
            modifyPwd.addSubview(modifyIcon)
            
            let modifyButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: modifyPwd.frame.size))
            modifyButton.addTarget(self, action: #selector(toModifyPwd(_:)), for: UIControlEvents.touchUpInside)
            modifyPwd.addSubview(modifyButton)
            funcView.addSubview(modifyPwd)
            
            contact.frame = CGRect(x: 0, y: modifyPwd.frame.origin.y + modifyPwd.frame.height, width: funcView.frame.width, height: rowHeight * screenScale)
        }else{
            contact.frame = CGRect(x: 0, y: history.frame.origin.y + history.frame.height, width: funcView.frame.width, height: rowHeight * screenScale)
        }
        contact.backgroundColor = UIColor.white
        
        let contactTopLine = CALayer()
        contactTopLine.frame = CGRect(x: 0, y: 0, width: contact.frame.width, height: 1)
        contactTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        contact.layer.addSublayer(contactTopLine)
        
        let contactLable = UILabel(frame: historyLable.frame)
        contactLable.text = "联系我们"
        contactLable.textColor = historyLable.textColor
        contactLable.font = historyLable.font
        contact.addSubview(contactLable)
        
        let contactIcon = UIImageView(frame: CGRect(x: history.frame.width - (8 + 10) * screenScale, y: (rowHeight - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
        contactIcon.image = UIImage.init(named: "common_enter")
        contact.addSubview(contactIcon)
        
        let contactButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: contact.frame.size))
        contactButton.addTarget(self, action: #selector(toContact(_:)), for: UIControlEvents.touchUpInside)
        contact.addSubview(contactButton)
        funcView.addSubview(contact)
        funcView.frame.size = CGSize(width: funcView.frame.width, height: contact.frame.origin.y + contact.frame.height)
        funcView.addBaseShadow()
        mainScrollView.addSubview(funcView)
    }
    
    func createLogout(){
        logoutView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: screenWidth - paddingLeft*2 * screenScale, height: 45 * screenScale))
        logoutView.backgroundColor = UIColor.white
        logoutView.layer.cornerRadius = cornerRadius * screenScale
        logoutView.addBaseShadow()
        
        let logoutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: logoutView.frame.size))
        logoutButton.setTitle("退出登录", for: UIControlState.normal)
        logoutButton.setTitleColor(UIColor(red: 138/255, green: 138/255, blue: 138/255, alpha: 1), for: UIControlState.normal)
        logoutButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        logoutButton.addTarget(self, action: #selector(logout(_:)), for: UIControlEvents.touchUpInside)
        logoutView.addSubview(logoutButton)
        
        logoutAlert = UIAlertController(title: "温馨提示", message: "您确定要退出登录吗？", preferredStyle: UIAlertControllerStyle.alert)
        let cancleAction = UIAlertAction(title: "取消", style: UIAlertActionStyle.cancel, handler: nil)
        cancleAction.setCancleStyle()
        logoutAlert.addAction(cancleAction)

        let sureAction = UIAlertAction(title: "确认", style: UIAlertActionStyle.default) { (sureAction) in
            user = nil
            userAccount = nil
            phoneNum = ""
            self.viewDidAppear(false)
            self.login(UIButton())
            LocalDataController.writeLocalData("user", data: nil)
            LocalDataController.writeLocalData("phoneNum", data: nil)
        }
        sureAction.setSureStyle()
        logoutAlert.addAction(sureAction)
        
        mainScrollView.addSubview(logoutView)
    }
    
    func getCouponList(){
        if(user != nil){
            HttpController.getToken(data: { (token) in
                HttpController.get("coupon/getNotActiveList", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        var coupons: [CouponModel] = []
                        let dataMap = dataDictionary.object(forKey: "data") as! NSDictionary
                        
                        let redPacketList = dataMap.object(forKey: "redPacket") as! NSArray
                        for i in 0 ..< redPacketList.count{
                            let data = redPacketList[i] as! NSDictionary
                            coupons.append(CouponModel(data: data))
                        }
                        
                        let couponList = dataMap.object(forKey: "coupon") as! NSArray
                        for i in 0 ..< couponList.count{
                            let data = couponList[i] as! NSDictionary
                            coupons.append(CouponModel(data: data))
                        }
                        
                        if(coupons.count > 0){
                            let gift = GiftView(parent: self)
                            gift.giftList = coupons
                            UIApplication.shared.keyWindow?.addSubview(gift)
                            gift.showAnimation()
                        }
                    }
                }, errors: { (error) in })
            }) { (error) in }
        }
    }
    
    func reloadViews(){
        funcView.removeFromSuperview()
        createFuncView()
        
        if(user != nil){
            self.navigationItem.rightBarButtonItem = noticeBarButton
            if(user!.messageFlag){
                noticeBarButton.showRedPoint()
            }else{
                noticeBarButton.hideRedPoint()
            }
            
            navigationBackground.isHidden = true
            headerUnloginView.isHidden = true
            headerLoginView.isHidden = false
            logoutView.isHidden = false
            
            
            authView.frame.origin = CGPoint(x: authView.frame.origin.x, y: headerLoginView.frame.origin.y + headerLoginView.frame.height + 10 * screenScale)
            funcView.frame.origin = CGPoint(x: funcView.frame.origin.x, y: authView.frame.origin.y + authView.frame.height + 10 * screenScale)
            logoutView.frame.origin = CGPoint(x: logoutView.frame.origin.x, y: funcView.frame.origin.y + funcView.frame.height + 10 * screenScale)
            
            let nameTitle = self.view.viewWithTag(TagController.meTags.nameTitle) as! UILabel
            let userIcon = self.view.viewWithTag(TagController.meTags.userIcon) as! UIImageView
            if(user!.realnameAuthFlag){
                nameTitle.text = user!.publicName
            }else{
                nameTitle.text = user!.nickname
            }
            if(user!.bindingAliFlag){
                SDWebImageManager.shared().loadImage(with: URL(string: user!.aliPhoto), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                    if result && SDImage != nil{
                        userIcon.image = SDImage
                    }else{
                        userIcon.image = UIImage.init(named: "me_head_login")
                    }
                }
            }else{
                userIcon.image = UIImage.init(named: "me_head_login")
            }
            
            let idcardImage = self.view.viewWithTag(TagController.meTags.idcardImage) as! UIImageView
            let idcardStatus = self.view.viewWithTag(TagController.meTags.idcardStatus) as! UILabel
            let idcardButton = self.view.viewWithTag(TagController.meTags.idcardButton) as! UIButton
            if(user!.realnameAuthFlag){
                idcardImage.image = UIImage.init(named: "me_idcard_yes")
                idcardStatus.text = "已认证"
                idcardStatus.textColor = UIColor.selectedColor()
                idcardButton.removeTarget(self, action: #selector(toIdcardAuth(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.removeTarget(self, action: #selector(toIdcardSuccess(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.addTarget(self, action: #selector(toIdcardSuccess(_:)), for: UIControlEvents.touchUpInside)
            }else{
                idcardImage.image = UIImage.init(named: "me_idcard")
                idcardStatus.text = "未认证"
                idcardStatus.textColor = UIColor.unselectedColor()
                idcardButton.removeTarget(self, action: #selector(toIdcardAuth(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.removeTarget(self, action: #selector(toIdcardSuccess(_:)), for: UIControlEvents.touchUpInside)
                idcardButton.addTarget(self, action: #selector(toIdcardAuth(_:)), for: UIControlEvents.touchUpInside)
            }
            
            let bankcardImage = self.view.viewWithTag(TagController.meTags.bankcardImage) as! UIImageView
            let bankcardStatus = self.view.viewWithTag(TagController.meTags.bankcardStatus) as! UILabel
            let bankcardButton = self.view.viewWithTag(TagController.meTags.bankcardButton) as! UIButton
            if(Int(user!.bankcardCount)! > 0){
                bankcardImage.image = UIImage.init(named: "me_bankcard_yes")
                bankcardStatus.text = "\(user!.bankcardCount)张"
                bankcardStatus.textColor = UIColor.selectedColor()
                bankcardButton.removeTarget(self, action: #selector(toBankcardList(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.removeTarget(self, action: #selector(toBankcardBind(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.addTarget(self, action: #selector(toBankcardList(_:)), for: UIControlEvents.touchUpInside)
            }else{
                bankcardImage.image = UIImage.init(named: "me_bankcard")
                bankcardStatus.text = "未绑定"
                bankcardStatus.textColor = UIColor.unselectedColor()
                bankcardButton.removeTarget(self, action: #selector(toBankcardList(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.removeTarget(self, action: #selector(toBankcardBind(_:)), for: UIControlEvents.touchUpInside)
                bankcardButton.addTarget(self, action: #selector(toBankcardBind(_:)), for: UIControlEvents.touchUpInside)
            }
            
            let historyButton = self.view.viewWithTag(TagController.meTags.historyButton) as! UIButton
            historyButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            historyButton.removeTarget(self, action: #selector(toHistory(_:)), for: UIControlEvents.touchUpInside)
            historyButton.addTarget(self, action: #selector(toHistory(_:)), for: UIControlEvents.touchUpInside)
        }else{
            self.navigationItem.rightBarButtonItem = nil
            
            navigationBackground.isHidden = false
            headerUnloginView.isHidden = false
            headerLoginView.isHidden = true
            logoutView.isHidden = true
            
            authView.frame.origin = CGPoint(x: authView.frame.origin.x, y: headerUnloginView.frame.origin.y + headerUnloginView.frame.height + 10 * screenScale)
            funcView.frame.origin = CGPoint(x: funcView.frame.origin.x, y: authView.frame.origin.y + authView.frame.height + 10 * screenScale)
            
            let idcardImage = self.view.viewWithTag(TagController.meTags.idcardImage) as! UIImageView
            let idcardStatus = self.view.viewWithTag(TagController.meTags.idcardStatus) as! UILabel
            let idcardButton = self.view.viewWithTag(TagController.meTags.idcardButton) as! UIButton
            idcardImage.image = UIImage.init(named: "me_idcard")
            idcardStatus.text = "未认证"
            idcardButton.removeTarget(self, action: #selector(toIdcardAuth(_:)), for: UIControlEvents.touchUpInside)
            idcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            idcardButton.removeTarget(self, action: #selector(toIdcardSuccess(_:)), for: UIControlEvents.touchUpInside)
            idcardButton.addTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            idcardStatus.textColor = UIColor.unselectedColor()
            
            let bankcardImage = self.view.viewWithTag(TagController.meTags.bankcardImage) as! UIImageView
            let bankcardStatus = self.view.viewWithTag(TagController.meTags.bankcardStatus) as! UILabel
            let bankcardButton = self.view.viewWithTag(TagController.meTags.bankcardButton) as! UIButton
            bankcardImage.image = UIImage.init(named: "me_bankcard")
            bankcardStatus.text = "未绑定"
            bankcardButton.removeTarget(self, action: #selector(toBankcardList(_:)), for: UIControlEvents.touchUpInside)
            bankcardButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            bankcardButton.removeTarget(self, action: #selector(toBankcardBind(_:)), for: UIControlEvents.touchUpInside)
            bankcardButton.addTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            bankcardStatus.textColor = UIColor.unselectedColor()
            
            let historyButton = self.view.viewWithTag(TagController.meTags.historyButton) as! UIButton
            historyButton.removeTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
            historyButton.removeTarget(self, action: #selector(toHistory(_:)), for: UIControlEvents.touchUpInside)
            historyButton.addTarget(self, action: #selector(login(_:)), for: UIControlEvents.touchUpInside)
        }
    }
    
    @objc func logout(_ sender: UIButton){
        self.present(logoutAlert, animated: true, completion: nil)
    }
    
    @objc func login(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "loginViewController") as! LoginViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toModifyPwd(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "modifyPwdViewController") as! ModifyPwdViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toBankcardBind(_ sender: UIButton){
        if(user!.realnameAuthFlag){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "bankcardBindViewController") as! BankcardBindViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func toBankcardList(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "bankcardListViewController") as! BankcardListViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toIdcardAuth(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toIdcardSuccess(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "idcardSuccessViewController") as! IdcardSuccessViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toHistory(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "historyViewController") as! HistoryViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toAlipayResult(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "alipayResultViewController") as! AlipayResultViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toContact(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "contactViewController") as! ContactViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toNoticeList(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "messageViewController") as! MessageViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toCouponList(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "couponListViewController") as! CouponListViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

