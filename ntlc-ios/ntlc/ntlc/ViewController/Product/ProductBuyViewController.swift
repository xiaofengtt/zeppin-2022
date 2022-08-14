//
//  ProductBuyViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/27.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

class ProductBuyViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var headerView: UIView = UIView()
    var balanceView: UIView = UIView()
    var bodyView: UIView = UIView()
    var couponView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    var agreementView: UIView = UIView()
    var bottomLabel: UILabel = UILabel()
    var submitCodeView: AlertCodeView = AlertCodeView()
    
    var uuid: String! = nil
    var couponList: [CouponModel] = []
    var flagCoupon = true
    var selectedCoupon: CouponModel? = nil
    var product: ProductDetailModel?
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createHeaderView()
        createBalanceView()
        createBodyView()
        createCouponView()
        createSubmitButton()
        createAgreementView()
        createBottomLabel()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        let moneyInput = mainView.viewWithTag(TagController.productBuyTags.moneyInput) as? UITextField
        
        createSendCodeView()
        getData()
        getUser()
        if(moneyInput != nil){
            let money = Utils.checkMoney(money: moneyInput!.text!) ? moneyInput!.text! : "0"
            getCouponList(money: Double(money)!)
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        submitCodeView.removeFromSuperview()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let moneyInput = mainView.viewWithTag(TagController.productBuyTags.moneyInput) as? UITextField
        
        moneyInput?.endEditing(true)
        submitCodeView.codeInput.endEditing(true)
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 75 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        headerView.addBaseShadow()
        
        let imageView = UIView(frame: CGRect(x: 8 * screenScale, y: (headerView.frame.height - 45 * screenScale)/2, width: 45 * screenScale, height: 45 * screenScale))
        imageView.backgroundColor = UIColor.backgroundGray()
        imageView.layer.cornerRadius = cornerRadius * screenScale
        let image = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: imageView.frame.width - 14 * screenScale, height: imageView.frame.width - 14 * screenScale)))
        image.center = CGPoint(x: imageView.frame.width/2, y: imageView.frame.height/2)
        image.tag = TagController.productBuyTags.bankIcon
        imageView.addSubview(image)
        headerView.addSubview(imageView)
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: 18 * screenScale, width: headerView.frame.width - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale), height: 21 * screenScale))
        nameLabel.tag = TagController.productBuyTags.productName
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        nameLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        headerView.addSubview(nameLabel)
        let bankLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x - 2 * screenScale, y: nameLabel.frame.origin.y + nameLabel.frame.height + 1 * screenScale, width: 0, height: 17 * screenScale))
        bankLabel.tag = TagController.productBuyTags.bankInfo
        bankLabel.textColor = UIColor.fontDarkGray()
        bankLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        headerView.addSubview(bankLabel)
        
        let termLabel = UILabel()
        termLabel.tag = TagController.productBuyTags.productTerm
        termLabel.layer.borderWidth = 0.5
        termLabel.layer.borderColor = UIColor.mainGold().cgColor
        termLabel.textColor = UIColor.mainGold()
        termLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        headerView.addSubview(termLabel)
        
        mainView.addSubview(headerView)
    }
    
    func createBalanceView(){
        balanceView = UIView(frame: CGRect(x: 0, y: headerView.frame.origin.y + headerView.frame.height, width: screenWidth, height: 33 * screenScale))
        balanceView.backgroundColor = UIColor.clear
        
        let text = UILabel(frame: CGRect(x: 10 * screenScale, y: 11 * screenScale, width: balanceView.frame.width - 10 * 2 * screenScale, height: 17 * screenScale))
        text.tag = TagController.productBuyTags.userBalance
        text.textColor = UIColor.fontBlack()
        text.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        balanceView.addSubview(text)
        
        let rechargeButton = UIButton()
        rechargeButton.setTitle("充值", for: UIControlState.normal)
        rechargeButton.setTitleColor(UIColor.mainBlue(), for: UIControlState.normal)
        rechargeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        rechargeButton.addTarget(self, action: #selector(toRecharge(_:)), for: UIControlEvents.touchUpInside)
        rechargeButton.sizeToFit()
        rechargeButton.frame.size = CGSize(width: rechargeButton.frame.width, height: 17 * screenScale)
        rechargeButton.frame.origin = CGPoint(x: (balanceView.frame.width - rechargeButton.frame.width)/2, y: 11 * screenScale)
        balanceView.addSubview(rechargeButton)
        
        mainView.addSubview(balanceView)
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: balanceView.frame.origin.y + balanceView.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: 110 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 70 * screenScale))
        let label = UILabel(frame: CGRect(x: 5 * screenScale, y: 31 * screenScale, width: 72 * screenScale, height: 20 * screenScale))
        label.text = "买入金额"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        topView.addSubview(label)
        
        let moneyInput = UITextField(frame: CGRect(x: label.frame.origin.x + label.frame.width, y: 30 * screenScale, width: bodyView.frame.width - (label.frame.origin.x + label.frame.width) - 5 * screenScale, height: 22 * screenScale))
        moneyInput.tag = TagController.productBuyTags.moneyInput
        moneyInput.delegate = self
        moneyInput.clearButtonMode = UITextFieldViewMode.always
        moneyInput.keyboardType = UIKeyboardType.decimalPad
        moneyInput.textColor = UIColor.moneyInputColor()
        moneyInput.font = UIFont.numFont(size: 22 * screenScale)
        topView.addSubview(moneyInput)
        bodyView.addSubview(topView)
        
        let bottomView = UIView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height, width: bodyView.frame.width, height: bodyView.frame.height - (topView.frame.origin.y + topView.frame.height)))
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        let targetText = UILabel(frame: CGRect(x: 5 * screenScale, y: 0, width: 0, height: bottomView.frame.height))
        targetText.text = "预期年化利率"
        targetText.textColor = UIColor.fontGray()
        targetText.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        targetText.sizeToFit()
        targetText.frame.size = CGSize(width: targetText.frame.width, height: bottomView.frame.height)
        bottomView.addSubview(targetText)
        let productTarget = UILabel(frame: CGRect(x: targetText.frame.origin.x + targetText.frame.width + 4 * screenScale, y: 0, width: 38 * screenScale, height: bottomView.frame.height))
        productTarget.tag = TagController.productBuyTags.productTarget
        productTarget.textColor = UIColor.mainRed()
        productTarget.font = UIFont.numFont(size: UIFont.smallestSize() * screenScale)
        bottomView.addSubview(productTarget)
        
        let incomeText = UILabel(frame: CGRect(x: productTarget.frame.origin.x + productTarget.frame.width, y: 0, width: 0, height: bottomView.frame.height))
        incomeText.tag = TagController.productBuyTags.incomeText
        incomeText.textColor = UIColor.fontGray()
        incomeText.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        bottomView.addSubview(incomeText)
        let productIncome = UILabel(frame: CGRect(x: 0, y: 0, width: 100 * screenScale, height: bottomView.frame.height))
        productIncome.tag = TagController.productBuyTags.productIncome
        productIncome.textColor = UIColor.mainRed()
        productIncome.font = UIFont.numFont(size: UIFont.smallestSize() * screenScale)
        bottomView.addSubview(productIncome)
        bodyView.addSubview(bottomView)
        mainView.addSubview(bodyView)
    }
    
    func createCouponView(){
        couponView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 60 * screenScale))
        couponView.backgroundColor = UIColor.white
        couponView.layer.cornerRadius = cornerRadius * screenScale
        couponView.addBaseShadow()
        
        let title = UILabel(frame: CGRect(x: 5 * screenScale, y: 0, width: 100 * screenScale, height: couponView.frame.height))
        title.text = "我的优惠券"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        couponView.addSubview(title)
        
        let enterIcon = UIImageView(frame: CGRect(x: couponView.frame.width - (10 + 9) * screenScale, y: (couponView.frame.height - 14 * screenScale)/2, width: 9 * screenScale, height: 14 * screenScale))
        enterIcon.isHidden = true
        enterIcon.tag = TagController.productBuyTags.couponImage
        enterIcon.image = UIImage(named: "common_enter")
        couponView.addSubview(enterIcon)
        
        let statusLabel = UILabel(frame: CGRect(x: couponView.frame.width/2, y: 0, width: couponView.frame.width/2 - 10 * screenScale, height: couponView.frame.height))
        statusLabel.tag = TagController.productBuyTags.couponLabel
        statusLabel.text = "无可用"
        statusLabel.textColor = UIColor.fontGray()
        statusLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        statusLabel.textAlignment = NSTextAlignment.right
        couponView.addSubview(statusLabel)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: couponView.frame.size))
        button.addTarget(self, action: #selector(toCouponList(_:)), for: UIControlEvents.touchUpInside)
        couponView.addSubview(button)
        mainView.addSubview(couponView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: couponView.frame.origin.y + couponView.frame.height + 29 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("确认购买", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    func createAgreementView(){
        agreementView = UIView(frame: CGRect(x: submitButton.frame.origin.x, y: submitButton.frame.origin.y + submitButton.frame.height + 10 * screenScale, width: submitButton.frame.width, height: 14 * screenScale))
        
        let image = UIImageView(frame: CGRect(origin: CGPoint(x: 0, y: 2 * screenScale), size: CGSize(width: 10 * screenScale, height: 10 * screenScale)))
        image.image = UIImage(named: "product_buy_agreement")
        agreementView.addSubview(image)
        
        let text = UILabel()
        text.text = "我已阅读并同意"
        text.textColor = UIColor.fontGray()
        text.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        text.sizeToFit()
        text.frame = CGRect(x: image.frame.origin.x + image.frame.width + 2 * screenScale, y: 0, width: text.frame.width, height: agreementView.frame.height)
        agreementView.addSubview(text)
        
        let agreement = UIButton()
        agreement.setTitle("《牛投理财定向委托投资管理协议》", for: UIControlState.normal)
        agreement.setTitleColor(UIColor(red: 83/255, green: 146/255, blue: 221/255, alpha: 1), for: UIControlState.normal)
        agreement.titleLabel?.font = text.font
        agreement.sizeToFit()
        agreement.frame = CGRect(x: text.frame.origin.x + text.frame.width, y: 0, width: agreement.frame.width, height: agreementView.frame.height)
        agreement.addTarget(self, action: #selector(toPdf(_:)), for: UIControlEvents.touchUpInside)
        agreementView.addSubview(agreement)
        
        mainView.addSubview(agreementView)
    }
    
    func createSendCodeView(){
        submitCodeView = AlertCodeView()
        submitCodeView.codeInput.delegate = self
        submitCodeView.codeInput.tag = TagController.productBuyTags.codeInput
        submitCodeView.codeButton.addTarget(self, action: #selector(submitSendCode(_:)), for: UIControlEvents.touchUpInside)
        submitCodeView.sureButton.addTarget(self, action: #selector(submitSure(_:)), for: UIControlEvents.touchUpInside)
        UIApplication.shared.keyWindow?.addSubview(submitCodeView)
    }
    
    func createBottomLabel(){
        bottomLabel = UILabel(frame: CGRect(x: 0, y: screenHeight - (23 + 14) * screenScale, width: screenWidth, height: 14 * screenScale))
        bottomLabel.text = "预期年化利率不代表实际利息收益"
        bottomLabel.textColor = UIColor(white: 153/255, alpha: 1)
        bottomLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bottomLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(bottomLabel)
    }
    
    func getUser(){
        HttpController.getUser(uuid: user!.uuid, data: { (data) in
            self.reloadUserData()
        }) { (error) in }
    }
    
    func getCouponList(money: Double){
        HttpController.getToken(data: { (token) in
            HttpController.get("coupon/getList", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "status": "use", "price": EncodingUtil.getBase64(String(money * 100))]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.couponList = []
                    var coupons: [CouponModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        coupons.append(CouponModel(data: data))
                    }
                    self.couponList = coupons
                    if(self.selectedCoupon == nil && self.couponList.count > 0 && self.flagCoupon){
                        self.selectedCoupon = self.couponList[0]
                    }
                    self.reloadCouponData()
                }
            }, errors: { (error) in })
        }) { (error) in }
    }
    
    func getData(){
        if(uuid != nil){
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.get("product/get", params: NSDictionary(dictionary: ["uuid" : uuid, "device": EncodingUtil.getBase64(systemType)]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    self.product = ProductDetailModel(data: data)
                    self.reloadProductData()
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            self.navigationController!.popViewController(animated: true)
        }
    }
    
    func reloadUserData(){
        let userBalance = mainView.viewWithTag(TagController.productBuyTags.userBalance) as! UILabel
        
        userBalance.attributedText = Utils.getColorNumString(string: "当前余额 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: userBalance.font.pointSize))
    }
    
    func reloadCouponData(){
        let couponImage = mainView.viewWithTag(TagController.productBuyTags.couponImage) as! UIImageView
        let couponLabel = mainView.viewWithTag(TagController.productBuyTags.couponLabel) as! UILabel
        
        if(couponList.count > 0){
            couponImage.isHidden = false
            couponLabel.frame = CGRect(x: couponView.frame.width/2, y: 0, width: couponImage.frame.origin.x - couponView.frame.width/2 - 4 * screenScale, height: couponView.frame.height)
            couponLabel.text = selectedCoupon != nil ? "使用\(Utils.doubleToMoney(doubleString: "\(selectedCoupon!.couponPrice)"))\(selectedCoupon!.couponType == "cash" ? "元" : "%")\(selectedCoupon!.couponTypeCN)" : "未使用"
            couponLabel.textColor = UIColor.fontDarkGray()
            if(selectedCoupon != nil){
                var flagCoupon = false
                for coupon in couponList{
                    if(coupon.uuid == selectedCoupon?.uuid){
                        flagCoupon = true
                        break
                    }
                }
                if(!flagCoupon){
                    selectedCoupon = nil
                }
            }
        }else{
            couponImage.isHidden = true
            couponLabel.frame = CGRect(x: couponView.frame.width/2, y: 0, width: couponView.frame.width/2 - 10 * screenScale, height: couponView.frame.height)
            couponLabel.text = "无可用"
            couponLabel.textColor = UIColor.fontGray()
            selectedCoupon = nil
        }
    }
    
    func reloadProductData(){
        let bankIcon = mainView.viewWithTag(TagController.productBuyTags.bankIcon) as! UIImageView
        let productName = mainView.viewWithTag(TagController.productBuyTags.productName) as! UILabel
        let bankInfo = mainView.viewWithTag(TagController.productBuyTags.bankInfo) as! UILabel
        let productTerm = mainView.viewWithTag(TagController.productBuyTags.productTerm) as! UILabel
        let moneyInput = mainView.viewWithTag(TagController.productBuyTags.moneyInput) as! UITextField
        let productTarget = mainView.viewWithTag(TagController.productBuyTags.productTarget) as! UILabel
        let incomeText = mainView.viewWithTag(TagController.productBuyTags.incomeText) as! UILabel
        let productIncome = mainView.viewWithTag(TagController.productBuyTags.productIncome) as! UILabel
        
        SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + product!.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
            if result{
                bankIcon.image = SDImage
            }
        }
        productName.text = product!.name
        bankInfo.text = "【\(product!.custodianCN)】编码\(product!.scode) "
        bankInfo.sizeToFit()
        productTerm.text = "  期限/\(product!.term)天  "
        productTerm.sizeToFit()
        productTerm.frame.origin = CGPoint(x: bankInfo.frame.origin.x + bankInfo.frame.width, y: bankInfo.frame.origin.y + 2 * screenScale)
        if(headerView.frame.width - bankInfo.frame.origin.x < bankInfo.frame.width){
            bankInfo.frame.size = CGSize(width: headerView.frame.width - bankInfo.frame.origin.x, height: 17 * screenScale)
            productTerm.frame.size = CGSize.zero
        }else{
            bankInfo.frame.size = CGSize(width: bankInfo.frame.width, height: 17 * screenScale)
            if(headerView.frame.width - productTerm.frame.origin.x < productTerm.frame.width){
                productTerm.frame.size = CGSize(width: headerView.frame.width - productTerm.frame.origin.x, height: 13 * screenScale)
            }else{
                productTerm.frame.size = CGSize(width: productTerm.frame.width, height: 13 * screenScale)
            }
        }
        moneyInput.attributedPlaceholder = NSAttributedString(string: "\(Utils.doubleToMoney(doubleString: "\(product!.minInvestAmount)"))元起，递增\(Utils.doubleToMoney(doubleString: "\(product!.minInvestAmountAdd)"))元", attributes: [NSAttributedStringKey.foregroundColor: UIColor.mainPlaceholder(),NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.bigSize() * screenScale)])
        productTarget.text = product!.targetAnnualizedReturnRate + "%"
        incomeText.text = "\(product!.term)天预期收益"
        incomeText.sizeToFit()
        incomeText.frame.size = CGSize(width: incomeText.frame.width, height: productTarget.frame.height)
        var income: Double = 0
        if(Utils.checkMoney(money: moneyInput.text!)){
            if(selectedCoupon != nil){
                if(selectedCoupon!.couponType == "cash"){
                    income = (Double(moneyInput.text!)! + selectedCoupon!.couponPrice) * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
                }else if(selectedCoupon!.couponType == "interests"){
                    income = Double(moneyInput.text!)! * (Double(product!.targetAnnualizedReturnRate)! + selectedCoupon!.couponPrice) / 100 * Double(product!.term) / 365
                }else{
                    income = Double(moneyInput.text!)! * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
                }
            }else{
                income = Double(moneyInput.text!)! * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
            }
        }
        productIncome.text = "\(String(format: "%.2f", income))元"
        productIncome.frame.origin = CGPoint(x: incomeText.frame.origin.x + incomeText.frame.width + 7 * screenScale, y: 0)
    }
    
    @objc func toPdf(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "pdfViewController") as! PdfViewController
        vc.titleName = "《牛投理财定向委托投资管理协议》"
        vc.urlString = "../resource/123.pdf"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toRecharge(_ sender: UIButton){
        hideKeyboard()
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
                                self.navigationController?.pushViewController(vc, animated: true)
                            }else{
                                let sb = UIStoryboard(name: "Main", bundle: nil)
                                let vc = sb.instantiateViewController(withIdentifier: "bankcardRechargeViewController") as! BankcardRechargeViewController
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
    }
    
    @objc func toCouponList(_ sender: UIButton){
        hideKeyboard()
        if(couponList.count > 0){
            let moneyLabel = mainView.viewWithTag(TagController.productBuyTags.moneyInput)
            let money = Utils.checkMoney(money: (moneyLabel as! UITextField).text!) ? (moneyLabel as! UITextField).text! : "0"
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "couponListViewController") as! CouponListViewController
            vc.viewType = "select"
            vc.price = (Double(money)! * 100)
            vc.selectedCoupon = selectedCoupon
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let money = (mainView.viewWithTag(TagController.productBuyTags.moneyInput) as! UITextField).text!
            
            if(Double(money)! <= Double(user!.accountBalance)!){
                let loadingView = HttpController.showLoading(viewController: self)
                HttpController.getToken(data: { (token) in
                    HttpController.get("pay/sendCodeById", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "product": self.product!.uuid, "price": EncodingUtil.getBase64(String(Double(money)! * 100))]), data: { (data) in
                        let dataDictionary = data as! NSDictionary
                        let status = dataDictionary.object(forKey: "status") as! String
                        if(status == "SUCCESS"){
                            self.submitCodeView.isHidden = false
                            self.submitCodeView.codeButton.startTimer()
                            AlertView(title: "验证码发送成功！").showByTime(time: 2)
                        }else{
                            let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                            AlertView(title: message).showByTime(time: 2)
                        }
                        HttpController.hideLoading(loadingView: loadingView)
                    }, errors: { (error) in
                        HttpController.hideLoading(loadingView: loadingView)
                        HttpController.showTimeout(viewController: self)
                    })
                }, errors: { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                })
            }else{
                AlertView(title: "账户余额不足，请先充值").showByTime(time: 2)
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
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
                                            self.navigationController?.pushViewController(vc, animated: true)
                                        }else{
                                            let sb = UIStoryboard(name: "Main", bundle: nil)
                                            let vc = sb.instantiateViewController(withIdentifier: "bankcardRechargeViewController") as! BankcardRechargeViewController
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
                }
            }
        }
    }
    
    @objc func submitSure(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmitSure()){
            let money = (mainView.viewWithTag(TagController.productBuyTags.moneyInput) as! UITextField).text!
            let code = (submitCodeView.viewWithTag(TagController.productBuyTags.codeInput) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("pay/productPay", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "type": "balance", "product": self.product!.uuid, "code": EncodingUtil.getBase64(code), "price": EncodingUtil.getBase64(String(Double(money)! * 100)), "coupon": self.selectedCoupon != nil ? self.selectedCoupon!.uuid : ""]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "productBuyResultViewController") as! ProductBuyResultViewController
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        AlertView(title: message).showByTime(time: 2)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }, errors: { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                })
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }
    }
    
    @objc func submitSendCode(_ sender: CodeSendButton) {
        hideKeyboard()
        let money = (mainView.viewWithTag(TagController.productBuyTags.moneyInput) as! UITextField).text!
        sender.startTimer()
        
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("pay/sendCodeById", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "product": self.product!.uuid, "price": EncodingUtil.getBase64(String(Double(money)! * 100))]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    AlertView(title: "验证码发送成功！").showByTime(time: 2)
                }else{
                    let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                    AlertView(title: message).showByTime(time: 2)
                    sender.codeTime = 0
                }
                HttpController.hideLoading(loadingView: loadingView)
            }, errors: { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
                sender.codeTime = 0
            })
        }, errors: { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
            sender.codeTime = 0
        })
    }
    
    func checkSubmitSure() -> Bool{
        if(submitCodeView.codeInput.text == ""){
            AlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkCode(code: submitCodeView.codeInput.text!)){
            AlertView(title: "请输入正确的验证码").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func checkSubmit() -> Bool{
        let moneyInput = bodyView.viewWithTag(TagController.productBuyTags.moneyInput) as! UITextField
        
        if(moneyInput.text! == ""){
            AlertView(title: "请输入买入金额").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkMoney(money: moneyInput.text!)){
            AlertView(title: "请输入正确的买入金额").showByTime(time: 2)
            return false
        }
        
        if(Double(moneyInput.text!)! < Double(product!.minInvestAmount)) {
             AlertView(title: "买入金额不能小于最小起购金额\(product!.minInvestAmountLess)元").showByTime(time: 2)
            return false
        }
        
        if (((Double(moneyInput.text!)! - product!.minInvestAmount) * 100).truncatingRemainder(dividingBy: (product!.minInvestAmountAdd * 100)) != 0) {
            AlertView(title: "买入金额不符合投资递增金额要求").showByTime(time: 2)
            return false
        }
        
        if (Double(moneyInput.text!)! > product!.maxInvestAmount) {
            AlertView(title: "买入金额不能超过最大投资金额\(product!.maxInvestAmount)元").showByTime(time: 2)
            return false
        }
        
        if (Double(moneyInput.text!)! > product!.totalRaise) {
            AlertView(title: "买入金额不能超过剩余募集额\(product!.totalRaise)元").showByTime(time: 2)
            return false
        }
        return true
    }
    
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        if(textField.tag == TagController.productBuyTags.moneyInput){
            let productIncome = mainView.viewWithTag(TagController.productBuyTags.productIncome) as! UILabel
            productIncome.text = "0.00元"
            submitButton.setTitle("确认购买", for: UIControlState.normal)
            getCouponList(money: 0)
        }
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.productBuyTags.moneyInput){
            flagCoupon = true
            if(textField.text!.count + string.count > 10){
                return false
            }
            
            let productIncome = mainView.viewWithTag(TagController.productBuyTags.productIncome) as! UILabel
            
            let text = String(textField.text![..<String.Index.init(encodedOffset: range.location)]) + string + String(textField.text![String.Index.init(encodedOffset: range.location + range.length)..<String.Index.init(encodedOffset: textField.text!.count)])
            
            var income: Double = 0
            if(Utils.checkMoney(money: text)){
                getCouponList(money: Double(text)!)
                
                if(selectedCoupon != nil){
                    if(selectedCoupon!.couponType == "cash"){
                        income = (Double(text)! + selectedCoupon!.couponPrice) * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
                    }else if(selectedCoupon!.couponType == "interests"){
                        income = Double(text)! * (Double(product!.targetAnnualizedReturnRate)! + selectedCoupon!.couponPrice) / 100 * Double(product!.term) / 365
                    }else{
                        income = Double(text)! * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
                    }
                }else{
                    income = Double(text)! * Double(product!.targetAnnualizedReturnRate)! / 100 * Double(product!.term) / 365
                }
                
                productIncome.text = "\(String(format: "%.2f", income))元"
                
                submitButton.setTitle("确认购买\(Utils.formatMoney(money: text))元", for: UIControlState.normal)
            }else{
                submitButton.setTitle("确认购买", for: UIControlState.normal)
                productIncome.text = "0.00元"
                getCouponList(money: 0)
                if(string != "" && string != "." && text != ""){
                    return false
                }
            }
        }else if(textField.tag == TagController.productBuyTags.codeInput && textField.text!.count + string.count > 6){
            return false
        }
        return true
    }
}
