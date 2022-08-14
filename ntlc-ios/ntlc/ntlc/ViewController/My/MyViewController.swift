//
//  MyViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/7.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class MyViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UIScrollViewDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var mainScrollView: UIScrollView = UIScrollView()
    var headerView: UIView = UIView()
    var tableView: UITableView = UITableView()
    
    var productList: [UserProductModel] = []
    var productType: String = "profit"
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        mainView.backgroundColor = UIColor.backgroundGray()
        
        mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight - self.tabBarController!.tabBar.frame.height)))
        if #available(iOS 11.0, *) {
            mainScrollView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        mainScrollView.backgroundColor = UIColor.clear
        mainScrollView.delegate = self
        mainScrollView.contentSize = CGSize(width: mainScrollView.frame.width, height: mainScrollView.frame.height + 1)
        mainScrollView.showsVerticalScrollIndicator = false
        mainScrollView.showsHorizontalScrollIndicator = false
        mainScrollView.addRefreshView()
        mainView.addSubview(mainScrollView)
        createHeaderView()
        createTableView()
        
        super.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        self.reloadUserData()
        
        if(user != nil){
            getUserAccount()
            getProductList()
        }else{
            userAccount = nil
            reloadUserData()
            tableView.reloadData()
        }
        
        getCouponList()
    }
    
    func createHeaderView(){
        var title = UILabel()
        if(isIphoneX()){
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 300 * screenScale))
            headerView.backgroundColor = UIColor.clear
            
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: headerView.frame.width, height: 264 * screenScale)))
            background.image = UIImage(named: "my_bg_x")
            headerView.addSubview(background)
            
            title = UILabel(frame: CGRect(x: 0, y: 124 * screenScale, width: headerView.frame.width, height: 20 * screenScale))
        }else{
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 255 * screenScale))
            headerView.backgroundColor = UIColor.clear
            
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: headerView.frame.width, height: 220 * screenScale)))
            background.image = UIImage(named: "my_bg")
            headerView.addSubview(background)
            
            title = UILabel(frame: CGRect(x: 0, y: 80 * screenScale, width: headerView.frame.width, height: 20 * screenScale))
        }
        title.tag = TagController.myTags.headerTitle
        title.textColor = UIColor.white
        title.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        title.textAlignment = NSTextAlignment.center
        headerView.addSubview(title)
        
        let num = UILabel(frame: CGRect(x: 0, y: title.frame.origin.y + title.frame.height + 5 * screenScale, width: headerView.frame.width, height: 32 * screenScale))
        num.tag = TagController.myTags.headerNum
        num.textColor = UIColor.white
        num.font = UIFont.numFont(size: 32 * screenScale)
        num.textAlignment = NSTextAlignment.center
        headerView.addSubview(num)
        
        let button = UIButton(frame: CGRect(x: 0, y: title.center.y, width: 16 * screenScale, height: 16 * screenScale))
        button.tag = TagController.myTags.headerSwitch
        button.isHidden = true
        button.adjustsImageWhenHighlighted = false
        button.setImage(UIImage(named: "my_switch"), for: UIControlState.normal)
        button.addTarget(self, action: #selector(switchBalance(_:)), for: UIControlEvents.touchUpInside)
        headerView.addSubview(button)
        
        let messageView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.height - 65 * screenScale, width: headerView.frame.width -  paddingLeft * 2 * screenScale, height: 65 * screenScale))
        messageView.backgroundColor = UIColor.white
        messageView.layer.cornerRadius = cornerRadius * screenScale
        messageView.addBaseShadow()
        
        let balanceTitle = UILabel(frame: CGRect(x: 11 * screenScale, y: 11 * screenScale, width: messageView.frame.width / 2 - 11 * screenScale, height: 20 * screenScale))
        balanceTitle.text = "账户余额(元)"
        balanceTitle.textColor = UIColor.fontBlack()
        balanceTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        messageView.addSubview(balanceTitle)
        
        let balanceNum = UILabel(frame: CGRect(x: balanceTitle.frame.origin.x, y: balanceTitle.frame.origin.y + balanceTitle.frame.height + 3 * screenScale, width: balanceTitle.frame.width, height: 22 * screenScale))
        balanceNum.tag = TagController.myTags.balanceNum
        balanceNum.textColor = UIColor.mainRed()
        balanceNum.font = UIFont.numFont(size: UIFont.bigSize() * screenScale)
        messageView.addSubview(balanceNum)
        
        if(isPublish){
            let withdrawButton = UIButton(frame: CGRect(x: messageView.frame.width - (50 + 14) * screenScale, y: (messageView.frame.height - 24 * screenScale)/2, width: 50 * screenScale, height: 24 * screenScale))
            withdrawButton.layer.cornerRadius = 4 * screenScale
            withdrawButton.backgroundColor = UIColor.mainGold()
            withdrawButton.setTitle("提现", for: UIControlState.normal)
            withdrawButton.setTitleColor(UIColor.white, for: UIControlState.normal)
            withdrawButton.titleLabel?.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            withdrawButton.addTarget(self, action: #selector(toWithdraw(_:)), for: UIControlEvents.touchUpInside)
            messageView.addSubview(withdrawButton)
            
            let rechargeButton = UIButton(frame: CGRect(x: withdrawButton.frame.origin.x - (50 + 10) * screenScale, y: (messageView.frame.height - 24 * screenScale)/2, width: 50 * screenScale, height: 24 * screenScale))
            rechargeButton.layer.cornerRadius = 4 * screenScale
            rechargeButton.backgroundColor = UIColor.mainBlue()
            rechargeButton.setTitle("充值", for: UIControlState.normal)
            rechargeButton.setTitleColor(UIColor.white, for: UIControlState.normal)
            rechargeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            rechargeButton.addTarget(self, action: #selector(toRecharge(_:)), for: UIControlEvents.touchUpInside)
            messageView.addSubview(rechargeButton)
        }
        headerView.addSubview(messageView)
        
        mainScrollView.addSubview(headerView)
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: mainScrollView.frame.height - (headerView.frame.origin.y + headerView.frame.height + 10 * screenScale)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.clear
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.bounces = false
        tableView.layer.cornerRadius = cornerRadius * screenScale
        mainScrollView.addSubview(tableView)
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
    
    @objc func selectProductType(_ sender: UIButton){
        let profitLabel = mainScrollView.viewWithTag(TagController.myTags.profitLabel) as! UILabel
        let profitLine = mainScrollView.viewWithTag(TagController.myTags.profitLine) as! UIImageView
        let transactionLabel = mainScrollView.viewWithTag(TagController.myTags.transactionLabel) as! UILabel
        let transactionLine = mainScrollView.viewWithTag(TagController.myTags.transactionLine) as! UIImageView
        let finishedLabel = mainScrollView.viewWithTag(TagController.myTags.finishedLabel) as! UILabel
        let finishedLine = mainScrollView.viewWithTag(TagController.myTags.finishedLine) as! UIImageView
        
        profitLabel.textColor = UIColor.fontDarkGray()
        profitLine.isHidden = true
        transactionLabel.textColor = UIColor.fontDarkGray()
        transactionLine.isHidden = true
        finishedLabel.textColor = UIColor.fontDarkGray()
        finishedLine.isHidden = true
        
        if (sender.tag == TagController.myTags.finishedButton) {
            productType = "finished"
            finishedLabel.textColor = UIColor.mainBlue()
            finishedLine.isHidden = false
        }else if(sender.tag == TagController.myTags.transactionButton){
            productType = "transaction"
            transactionLabel.textColor = UIColor.mainBlue()
            transactionLine.isHidden = false
        }else{
            productType = "profit"
            profitLabel.textColor = UIColor.mainBlue()
            profitLine.isHidden = false
        }
        
        getProductList()
    }
    
    @objc func toWithdraw(_ sender: UIButton){
        if(user != nil){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "withdrawViewController") as! WithdrawViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            toLogin()
        }
    }
    
    @objc func toRecharge(_ sender: UIButton){
        if(user != nil){
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.get("pay/getPaymentList", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        let payList = dataDictionary.object(forKey: "data") as! NSArray
                        for i in 0 ..< payList.count{
                            let payTypeDic = payList[i] as! NSDictionary
                            if(String.valueOf(any: payTypeDic.object(forKey: "payment")) == "alipay"){
                                if(Bool.valueOf(any: payTypeDic.object(forKey: "flagSwitch"))){
                                    let sb = UIStoryboard(name: "Main", bundle: nil)
                                    let vc = sb.instantiateViewController(withIdentifier: "alipayRechargeViewController") as! AlipayRechargeViewController
                                    vc.hidesBottomBarWhenPushed = true
                                    self.navigationController?.pushViewController(vc, animated: true)
                                }else{
                                    let sb = UIStoryboard(name: "Main", bundle: nil)
                                    let vc = sb.instantiateViewController(withIdentifier: "bankcardRechargeViewController") as! BankcardRechargeViewController
                                    vc.hidesBottomBarWhenPushed = true
                                    self.navigationController?.pushViewController(vc, animated: true)
                                }
                            }
                        }
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        AlertView(title: message).showByTime(time: 2)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }) { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                }
            }) { (errors) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            toLogin()
        }
    }
    
    @objc func toLogin(){
        self.tabBarController?.selectedIndex = 2
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "loginViewController") as! LoginViewController
        vc.hidesBottomBarWhenPushed = true
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.01) {
            (self.tabBarController!.viewControllers![2] as! BaseNavigationController).pushViewController(vc, animated: true)
        }
    }
    
    @objc func switchBalance(_ sender: UIButton){
        let title = mainScrollView.viewWithTag(TagController.myTags.headerTitle) as! UILabel
        let num = mainScrollView.viewWithTag(TagController.myTags.headerNum) as! UILabel
        
        if(sender.isSelected){
            title.text = "总资产(元)"
            num.text = userAccount!.totalAmount
        }else{
            title.text = "总收益(元)"
            num.text = userAccount!.totalReturn
        }
        num.sizeToFit()
        num.frame.origin = CGPoint(x: (mainScrollView.frame.width - num.frame.width)/2, y: num.frame.origin.y)
        sender.frame.origin = CGPoint(x: num.frame.origin.x + num.frame.width + 30 * screenScale, y: sender.frame.origin.y)
        sender.isSelected = !sender.isSelected
    }
    
    func getUserAccount(){
        HttpController.getUserAccount(data: { (data) in
            self.reloadUserData()
        }) { (error) in
            HttpController.showTimeout(viewController: self)
        }
    }
    
    @objc func getProductList(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("financial/getList", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "stage": self.productType]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.productList = []
                    var datas: [UserProductModel] = []
                    let dataArray = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< dataArray.count{
                        let data = dataArray[i] as! NSDictionary
                        datas.append(UserProductModel(data: data))
                    }
                    self.productList = datas
                    self.tableView.reloadData()
                    self.tableView.frame.size = CGSize(width: screenWidth - self.paddingLeft * 2 * screenScale, height: self.mainScrollView.frame.height - (self.headerView.frame.origin.y + self.headerView.frame.height) - 10 * screenScale)
                    
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }, errors: { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        })
    }
    
    func reloadUserData(){
        let headerTitle = mainScrollView.viewWithTag(TagController.myTags.headerTitle) as! UILabel
        let headerNum = mainScrollView.viewWithTag(TagController.myTags.headerNum) as! UILabel
        let headerSwitch = mainScrollView.viewWithTag(TagController.myTags.headerSwitch) as! UIButton
        let balanceNum = mainScrollView.viewWithTag(TagController.myTags.balanceNum) as! UILabel
        
        if(userAccount != nil){
            headerSwitch.isHidden = false
            if(headerSwitch.isSelected){
                headerTitle.text = "总收益(元)"
                headerNum.text = userAccount!.totalReturn
            }else{
                headerTitle.text = "总资产(元)"
                headerNum.text = userAccount!.totalAmount
            }
            balanceNum.text = userAccount!.accountBalance
        }else{
            headerSwitch.isHidden = true
            headerTitle.text = "总资产(元)"
            headerNum.text = "******"
            balanceNum.text = "****"
        }
        headerNum.sizeToFit()
        headerNum.frame.origin = CGPoint(x: (mainScrollView.frame.width - headerNum.frame.width)/2, y: headerNum.frame.origin.y)
        headerSwitch.frame.origin = CGPoint(x: headerNum.frame.origin.x + headerNum.frame.width + 30 * screenScale, y: headerSwitch.frame.origin.y)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(userAccount != nil){
            if(productList.count > 0){
                return 140 * screenScale
            }else{
                return tableView.frame.height - 46 * screenScale
            }
        }else{
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(userAccount != nil && productList.count != 0){
            return productList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(userAccount != nil){
            return 46 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(userAccount != nil){
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 46 * screenScale))
            view.backgroundColor = UIColor.white
            
            let profitView = UIView(frame: CGRect(x: 0, y: 0, width: view.frame.width/3, height: view.frame.height))
            let profitLabel = UILabel()
            profitLabel.tag = TagController.myTags.profitLabel
            profitLabel.text = "持有中"
            profitLabel.textColor = productType == "profit" ? UIColor.mainBlue() : UIColor.fontDarkGray()
            profitLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            profitLabel.sizeToFit()
            profitLabel.frame.size = CGSize(width: profitLabel.frame.width, height: (profitView.frame.height - 10 * 2 * screenScale))
            profitLabel.center = CGPoint(x: profitView.frame.width/2, y: profitView.frame.height/2)
            profitView.addSubview(profitLabel)
            let profitLine = UIImageView(frame: CGRect(x: profitLabel.frame.origin.x, y: profitLabel.frame.origin.y + profitLabel.frame.height - 1, width: profitLabel.frame.width, height: 1))
            profitLine.tag = TagController.myTags.profitLine
            profitLine.isHidden = productType != "profit"
            profitLine.backgroundColor = UIColor.mainBlue()
            profitView.addSubview(profitLine)
            let profitButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: profitView.frame.size))
            profitButton.tag = TagController.myTags.profitButton
            profitButton.addTarget(self, action: #selector(selectProductType(_:)), for: UIControlEvents.touchUpInside)
            profitView.addSubview(profitButton)
            view.addSubview(profitView)
            
            let transactionView = UIView(frame: CGRect(x: view.frame.width/3, y: 0, width: view.frame.width/3, height: view.frame.height))
            let transactionLabel = UILabel()
            transactionLabel.tag = TagController.myTags.transactionLabel
            transactionLabel.text = "交易中"
            transactionLabel.textColor = productType == "transaction" ? UIColor.mainBlue() : UIColor.fontDarkGray()
            transactionLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            transactionLabel.sizeToFit()
            transactionLabel.frame.size = CGSize(width: transactionLabel.frame.width, height: (transactionView.frame.height - 10 * 2 * screenScale))
            transactionLabel.center = CGPoint(x: transactionView.frame.width/2, y: transactionView.frame.height/2)
            transactionView.addSubview(transactionLabel)
            let transactionLine = UIImageView(frame: CGRect(x: transactionLabel.frame.origin.x, y: transactionLabel.frame.origin.y + transactionLabel.frame.height - 1, width: transactionLabel.frame.width, height: 1))
            transactionLine.tag = TagController.myTags.transactionLine
            transactionLine.isHidden = productType != "transaction"
            transactionLine.backgroundColor = UIColor.mainBlue()
            transactionView.addSubview(transactionLine)
            let transactionButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: transactionView.frame.size))
            transactionButton.tag = TagController.myTags.transactionButton
            transactionButton.addTarget(self, action: #selector(selectProductType(_:)), for: UIControlEvents.touchUpInside)
            transactionView.addSubview(transactionButton)
            view.addSubview(transactionView)
            
            let finishedView = UIView(frame: CGRect(x: view.frame.width/3*2, y: 0, width: view.frame.width/3, height: view.frame.height))
            let finishedLabel = UILabel()
            finishedLabel.tag = TagController.myTags.finishedLabel
            finishedLabel.text = "已完成"
            finishedLabel.textColor = productType == "finished" ? UIColor.mainBlue() : UIColor.fontDarkGray()
            finishedLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            finishedLabel.sizeToFit()
            finishedLabel.frame.size = CGSize(width: finishedLabel.frame.width, height: (finishedView.frame.height - 10 * 2 * screenScale))
            finishedLabel.center = CGPoint(x: finishedView.frame.width/2, y: finishedView.frame.height/2)
            finishedView.addSubview(finishedLabel)
            let finishedLine = UIImageView(frame: CGRect(x: finishedLabel.frame.origin.x, y: finishedLabel.frame.origin.y + finishedLabel.frame.height - 1, width: finishedLabel.frame.width, height: 1))
            finishedLine.tag = TagController.myTags.finishedLine
            finishedLine.isHidden = productType != "finished"
            finishedLine.backgroundColor = UIColor.mainBlue()
            finishedView.addSubview(finishedLine)
            let finishedButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: finishedView.frame.size))
            finishedButton.tag = TagController.myTags.finishedButton
            finishedButton.addTarget(self, action: #selector(selectProductType(_:)), for: UIControlEvents.touchUpInside)
            finishedView.addSubview(finishedButton)
            view.addSubview(finishedView)
            
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 0, y: view.frame.height - 1, width: view.frame.width, height: 1)
            bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
            view.layer.addSublayer(bottomLine)
            
            return view
        }else{
            return nil
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(userAccount != nil){
            if(productList.count > 0){
                let product = productList[indexPath.row]
                
                let view = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 130 * screenScale)))
                view.backgroundColor = UIColor.white
                view.layer.cornerRadius = cornerRadius * screenScale
                view.addBaseShadow()
                
                let bankImage = UIImageView(frame: CGRect(x: 12 * screenScale, y: 12 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
                SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + product.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                    if result{
                        bankImage.image = SDImage
                    }
                }
                view.addSubview(bankImage)
                
                let productName = UILabel(frame: CGRect(x: bankImage.frame.origin.x + bankImage.frame.width, y: bankImage.frame.origin.y, width: view.frame.width - (bankImage.frame.origin.x + bankImage.frame.width) - 10 * screenScale, height: bankImage.frame.height))
                productName.text = "【\(product.bankName)】\(product.productName)"
                productName.textColor = UIColor.fontBlack()
                productName.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
                view.addSubview(productName)
                
                let infoView = UIView(frame: CGRect(x: 10 * screenScale, y: view.frame.height - (70 + 12) * screenScale, width: view.frame.width - 10 * 2 * screenScale, height: 70 * screenScale))
                infoView.backgroundColor = UIColor.backgroundGray()
                
                let moneyNum = UILabel(frame: CGRect(x: 8 * screenScale, y: 20 * screenScale, width: 0, height: 16 * screenScale))
                moneyNum.text = product.priceCN
                moneyNum.textColor = UIColor.fontBlack()
                moneyNum.font = UIFont.numFont(size: UIFont.bigSize() * screenScale)
                moneyNum.sizeToFit()
                moneyNum.frame.size = CGSize(width: moneyNum.frame.width, height: 16 * screenScale)
                infoView.addSubview(moneyNum)
                
                if(product.flagInterestsCoupon && product.flagCashCoupon){
                    let interestsImage = UIImageView(frame: CGRect(x: moneyNum.frame.origin.x + moneyNum.frame.width + 8 * screenScale, y: moneyNum.frame.origin.y, width: 30 * screenScale, height: 16 * screenScale))
                    interestsImage.image = UIImage(named: "my_interests")
                    infoView.addSubview(interestsImage)
                    let cashImage = UIImageView(frame: CGRect(x: interestsImage.frame.origin.x + interestsImage.frame.width + 5 * screenScale, y: moneyNum.frame.origin.y, width: 30 * screenScale, height: 16 * screenScale))
                    cashImage.image = UIImage(named: "my_cash")
                    infoView.addSubview(cashImage)
                }else if(product.flagInterestsCoupon){
                    let interestsImage = UIImageView(frame: CGRect(x: moneyNum.frame.origin.x + moneyNum.frame.width + 8 * screenScale, y: moneyNum.frame.origin.y, width: 30 * screenScale, height: 16 * screenScale))
                    interestsImage.image = UIImage(named: "my_interests")
                    infoView.addSubview(interestsImage)
                }else if(product.flagCashCoupon){
                    let cashImage = UIImageView(frame: CGRect(x: moneyNum.frame.origin.x + moneyNum.frame.width + 8 * screenScale, y: moneyNum.frame.origin.y, width: 30 * screenScale, height: 16 * screenScale))
                    cashImage.image = UIImage(named: "my_cash")
                    infoView.addSubview(cashImage)
                }
                
                let moneyText = UILabel(frame: CGRect(x: moneyNum.frame.origin.x, y: moneyNum.frame.origin.y + moneyNum.frame.height + 4 * screenScale, width: infoView.frame.width/2 - 8 * screenScale, height: 14 * screenScale))
                if(product.stage == "finished" || product.stage == "confirming"){
                    moneyText.text = "购买金额(元)"
                }else{
                    moneyText.text = "持有金额(元)"
                }
                moneyText.textColor = UIColor.fontDarkGray()
                moneyText.font = UIFont.lightFont(size: UIFont.smallestSize())
                infoView.addSubview(moneyText)
                
                if(product.stage == "finished"){
                    let incomeNum = UILabel()
                    incomeNum.text = "+\(product.totalReturn)"
                    incomeNum.textColor = UIColor.mainRed()
                    incomeNum.font = UIFont.numFont(size: UIFont.smallSize() * screenScale)
                    incomeNum.textAlignment = NSTextAlignment.right
                    incomeNum.sizeToFit()
                    incomeNum.frame.size = CGSize(width: incomeNum.frame.width, height: 18 * screenScale)
                    incomeNum.frame.origin = CGPoint(x: infoView.frame.width - incomeNum.frame.width - 8 * screenScale, y: 18 * screenScale)
                    infoView.addSubview(incomeNum)
                    
                    let incomeText = UILabel(frame: CGRect(x: infoView.frame.width/2, y: incomeNum.frame.origin.y, width: incomeNum.frame.origin.x - 14 * screenScale - infoView.frame.width/2, height: incomeNum.frame.height))
                    incomeText.text = "实际收益"
                    incomeText.textColor = UIColor.fontDarkGray()
                    incomeText.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
                    incomeText.textAlignment = NSTextAlignment.right
                    infoView.addSubview(incomeText)
                    
                    let rateText = UILabel(frame: CGRect(x: incomeText.frame.origin.x, y: incomeNum.frame.origin.y + incomeNum.frame.height + 4 * screenScale, width: incomeText.frame.width, height: 12 * screenScale))
                    rateText.text = "收益率"
                    rateText.textColor = UIColor.fontDarkGray()
                    rateText.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
                    rateText.textAlignment = NSTextAlignment.right
                    infoView.addSubview(rateText)
                    
                    let rateNum = UILabel(frame: CGRect(x: rateText.frame.origin.x + rateText.frame.width, y: rateText.frame.origin.y, width: infoView.frame.width - (rateText.frame.origin.x + rateText.frame.width) - 8 * screenScale, height: rateText.frame.height))
                    rateNum.text = "\(product.realReturnRateCN)%"
                    rateNum.textColor = UIColor.fontBlack()
                    rateNum.font = UIFont.numFont(size: UIFont.smallestSize() * screenScale)
                    rateNum.textAlignment = NSTextAlignment.right
                    infoView.addSubview(rateNum)
                }else{
                    let timeDate = UILabel()
                    if(product.stage == "profit"){
                        timeDate.text = " \(product.maturityDate) "
                        timeDate.textColor = UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
                    }else if(product.stage == "confirming"){
                        timeDate.text = " \(product.valueDate) "
                        timeDate.textColor = UIColor(red: 113/255, green: 175/255, blue: 81/255, alpha: 1)
                    }else if(product.stage == "balance"){
                        timeDate.text = " \(product.incomeDate) "
                        timeDate.textColor = UIColor(red: 110/255, green: 163/255, blue: 225/255, alpha: 1)
                    }
                    timeDate.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
                    timeDate.layer.borderWidth = 1
                    timeDate.layer.borderColor = timeDate.textColor.withAlphaComponent(0.6).cgColor
                    timeDate.sizeToFit()
                    timeDate.frame.size = CGSize(width: timeDate.frame.width, height: 18 * screenScale)
                    timeDate.frame.origin = CGPoint(x: infoView.frame.width - timeDate.frame.width - 8 * screenScale, y: 20 * screenScale)
                    infoView.addSubview(timeDate)
                    
                    let timeName = UILabel(frame: CGRect(x: infoView.frame.width/2, y: timeDate.frame.origin.y + timeDate.frame.height + 2 * screenScale, width: infoView.frame.width/2 - 8 * screenScale, height: 14 * screenScale))
                    if(product.stage == "profit"){
                        timeName.text = "到期日"
                    }else if(product.stage == "confirming"){
                        timeName.text = "起息日"
                    }else{
                        timeName.text = "预计到账日"
                    }
                    timeName.textColor = UIColor.fontDarkGray()
                    timeName.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
                    timeName.textAlignment = NSTextAlignment.right
                    infoView.addSubview(timeName)
                }
                
                view.addSubview(infoView)
                cell.addSubview(view)
            }else{
                let nodataView = UIView(frame: CGRect(origin: CGPoint.zero, size: tableView.frame.size))
                nodataView.backgroundColor = UIColor.white
                nodataView.layer.cornerRadius = cornerRadius * screenScale
                
                let nodataImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 125 * screenScale, height: 117 * screenScale)))
                nodataImage.image = UIImage(named: "common_nodata")
                nodataImage.center = CGPoint(x: nodataView.frame.width/2, y: nodataView.frame.height/3)
                nodataView.addSubview(nodataImage)
                
                let nodataText = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 15 * screenScale, width: nodataView.frame.width, height: 20 * screenScale))
                nodataText.text = "暂时没有任何数据"
                nodataText.textColor = UIColor.fontGray()
                nodataText.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                nodataText.textAlignment = NSTextAlignment.center
                nodataView.addSubview(nodataText)
                
                let nodataMessage = UILabel(frame: CGRect(x: 0, y: nodataText.frame.origin.y + nodataText.frame.height + 2 * screenScale, width: nodataView.frame.width, height: 17 * screenScale))
                nodataMessage.text = "点击刷新"
                nodataMessage.textColor = UIColor.alertCancleColor()
                nodataMessage.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
                nodataMessage.textAlignment = NSTextAlignment.center
                nodataView.addSubview(nodataMessage)
                
                let nodataButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: nodataView.frame.size))
                nodataButton.addTarget(self, action: #selector(getProductList), for: UIControlEvents.touchUpInside)
                nodataView.addSubview(nodataButton)
                
                cell.addSubview(nodataView)
            }
        }else{
            let unloginView = UIView(frame: CGRect(origin: CGPoint.zero, size: tableView.frame.size))
            unloginView.backgroundColor = UIColor.white
            unloginView.layer.cornerRadius = cornerRadius * screenScale
            
            let unloginImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 101 * screenScale, height: 88 * screenScale)))
            unloginImage.image = UIImage(named: "common_nodata")
            unloginImage.center = CGPoint(x: unloginView.frame.width/2, y: unloginView.frame.height/3)
            unloginView.addSubview(unloginImage)
            
            let unloginText = UILabel(frame: CGRect(x: 0, y: unloginImage.frame.origin.y + unloginImage.frame.height + 15 * screenScale, width: unloginView.frame.width, height: 20 * screenScale))
            unloginText.text = "你还没有登录，请登陆后查看。"
            unloginText.textColor = UIColor.fontGray()
            unloginText.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            unloginText.textAlignment = NSTextAlignment.center
            unloginView.addSubview(unloginText)
            
            let loginButton = UIButton(frame: CGRect(x: (unloginView.frame.width - 100 * screenScale)/2, y: unloginText.frame.origin.y + unloginText.frame.height + 16 * screenScale, width: 100 * screenScale, height: 35 * screenScale))
            loginButton.backgroundColor = UIColor.mainGold()
            loginButton.layer.cornerRadius = cornerRadius * screenScale
            loginButton.setTitle("立即登录", for: UIControlState.normal)
            loginButton.setTitleColor(UIColor.white, for: UIControlState.normal)
            loginButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            loginButton.addTarget(self, action: #selector(toLogin), for: UIControlEvents.touchUpInside)
            unloginView.addSubview(loginButton)
            
            cell.addSubview(unloginView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(userAccount != nil){
            if(productList.count > 0){
                let data = productList[indexPath.row]
                
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "myDetailViewController") as! MyDetailViewController
                vc.uuid = data.uuid
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                getProductList()
            }
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == mainScrollView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                scrollView.refreshToAble()
            }else{
                scrollView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == mainScrollView){
            if(scrollView.getRefreshView().status == UIScrollRefreshStatus.able){
                if(user != nil){
                    getUserAccount()
                    getProductList()
                }
            }
        }
    }
}
