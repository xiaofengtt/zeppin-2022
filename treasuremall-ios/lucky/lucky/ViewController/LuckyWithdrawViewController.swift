//
//  LuckyWithdrawViewController.swift
//  lucky
//  提现页
//  Created by Farmer Zhu on 2020/9/7.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyWithdrawViewController: UIViewController, UITextFieldDelegate {
    //头
    private var staticHeaderView: LuckyNavigationView!
    //功能区
    private var staticMainView: UIScrollView!
    //积分规则页
    private var staticAnswerView: UIView!
    
    //账户显示
    private var staticAccountView: UIView!
    //提现功能
    private var staticWithdrawView: UIView!
    //功能和提示
    private var staticFuncView: UIView!
    
    //页边距
    private let paddingLeft: CGFloat = 10 * screenScale
    
    //最大可提现金额
    private var maxAmount: Double = 0
    //是否首次加载
    private var firstLoad: Bool = true
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建功能区
        staticMainView = createMainView()
        self.view.addSubview(staticMainView)
        //创建积分规则页
        staticAnswerView = createAnswerView()
        self.view.addSubview(staticAnswerView)
        
        //取提现提示
        getExplanation()
        //取币种汇率
        getCurrencyRate()
        //取系统汇率
        getRate()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if(firstLoad){
            //首次显示 无活动
            firstLoad = false
        }else{
            //非首次 更新用户账户
            getUserAccount()
        }
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Withdraw", comment: "")
        return headView
    }
    
    //创建 积分规则页
    func createAnswerView() -> UIView {
        let answerView = UIView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size))
        answerView.layer.zPosition = 0.9
        answerView.isHidden = true
        answerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 44 * screenScale, y: answerView.frame.height/4, width: answerView.frame.width - 88 * screenScale, height: 0))
        mainView.backgroundColor = UIColor.white
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 4 * screenScale
        
        //背景
        let bgLayer = CAGradientLayer()
        bgLayer.frame = CGRect(x: 40 * screenScale, y: 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale)
        bgLayer.colors = [UIColor(red: 1, green: 0.73, blue: 0.04, alpha: 0.2).cgColor, UIColor(red: 1, green: 0.52, blue: 0.02, alpha: 0).cgColor]
        bgLayer.masksToBounds = true
        bgLayer.cornerRadius = bgLayer.frame.height/2
        bgLayer.locations = [0, 1]
        bgLayer.startPoint = CGPoint(x: -0.33, y: 0)
        bgLayer.endPoint = CGPoint(x: 0.81, y: 0.81)
        mainView.layer.addSublayer(bgLayer)
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 20 * screenScale, width: mainView.frame.width, height: 24 * screenScale))
        titleLabel.text = NSLocalizedString("How to Get Points?", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBiggest() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(titleLabel)
        
        //内容
        let contentLabel = UILabel(frame: CGRect(x: 20 * screenScale, y: titleLabel.frame.origin.y + titleLabel.frame.height + 14 * screenScale, width: mainView.frame.width - 40 * screenScale, height: 0))
        contentLabel.numberOfLines = 0
        let style = NSMutableParagraphStyle()
        style.paragraphSpacing = 5 * screenScale
        contentLabel.attributedText = NSAttributedString(string: NSLocalizedString("points rules", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        contentLabel.sizeToFit()
        mainView.addSubview(contentLabel)
        
        //关闭按钮
        let button = UIButton(frame: CGRect(x: 0, y: contentLabel.frame.origin.y + contentLabel.frame.height + 24 * screenScale, width: mainView.frame.width, height: 46 * screenScale))
        button.setTitle(NSLocalizedString("OK", comment: ""), for: UIControl.State.normal)
        button.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(hideAnswer), for: UIControl.Event.touchUpInside)
        let buttonTopLine = CALayer()
        buttonTopLine.frame = CGRect(x: 0, y: 0, width: button.frame.width, height: 1)
        buttonTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        button.layer.addSublayer(buttonTopLine)
        mainView.addSubview(button)
        
        mainView.frame.size = CGSize(width: mainView.frame.width, height: button.frame.origin.y + button.frame.height)
        answerView.addSubview(mainView)
        return answerView
    }
    
    //创建功能区
    func createMainView() -> UIScrollView{
        let mainView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        mainView.showsVerticalScrollIndicator = false
        mainView.showsHorizontalScrollIndicator = false
        mainView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(hideKeyboard)))
        
        //创建账户显示区
        staticAccountView = createAccountView()
        mainView.addSubview(staticAccountView)
        //创建提现功能区
        staticWithdrawView = createWithdrawView()
        mainView.addSubview(staticWithdrawView)
        
        return mainView
    }
    
    //创建账户显示区
    func createAccountView() -> UIView{
        let accountView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
        accountView.backgroundColor = UIColor.white
        
        //标题
        let accountTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 16 * screenScale, width: accountView.frame.width - paddingLeft * 2, height: 20 * screenScale))
        accountTitleLabel.text = NSLocalizedString("Receiving Account", comment: "")
        accountTitleLabel.textColor = UIColor.fontDarkGray()
        accountTitleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        accountView.addSubview(accountTitleLabel)
        
        let payImageView = UIImageView(frame: CGRect(x: accountTitleLabel.frame.origin.x, y: accountTitleLabel.frame.origin.y + accountTitleLabel.frame.height + 10 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        payImageView.image = UIImage(named: "image_pay_logo")
        accountView.addSubview(payImageView)
        
        if(globalUserAccount != nil && globalUserAccount!.paypalAccount != ""){
            //有用户账户 且 paypal账户已绑定 显示paypal账户
            let arrowImageView = UIImageView(frame: CGRect(x: accountView.frame.width - paddingLeft - 8 * screenScale, y: payImageView.frame.origin.y + (payImageView.frame.height - 16 * screenScale)/2, width: 8 * screenScale, height: 16 * screenScale))
            arrowImageView.image = UIImage(named: "image_enter_gray")
            accountView.addSubview(arrowImageView)
            
            //显示账户
            let accountLabel = UILabel(frame: CGRect(x: payImageView.frame.origin.x + payImageView.frame.width + 8 * screenScale, y: payImageView.frame.origin.y, width: arrowImageView.frame.origin.x - (payImageView.frame.origin.x + payImageView.frame.width + 16 * screenScale), height: payImageView.frame.height))
            accountLabel.text = globalUserAccount!.paypalAccount
            accountLabel.textColor = UIColor.fontBlack()
            accountLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
            accountView.addSubview(accountLabel)
            
            //去查看/修改按钮
            let accountButton = UIButton(frame: CGRect(x: accountLabel.frame.origin.x, y: accountLabel.frame.origin.y, width: accountView.frame.width - paddingLeft - accountLabel.frame.origin.x, height: accountLabel.frame.height))
            accountButton.addTarget(self, action: #selector(toAccount), for: UIControl.Event.touchUpInside)
            accountView.addSubview(accountButton)
            
            let alertLabel = UILabel(frame: CGRect(x: payImageView.frame.origin.x, y: payImageView.frame.origin.y + payImageView.frame.height, width: accountView.frame.width - paddingLeft * 2, height: 20 * screenScale))
            alertLabel.text = NSLocalizedString("Transfer to account in 2 hours", comment: "")
            alertLabel.textColor = UIColor.fontDarkGray()
            alertLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            accountView.addSubview(alertLabel)
            
            accountView.frame.size = CGSize(width: accountView.frame.width, height: alertLabel.frame.origin.y + alertLabel.frame.height + 12 * screenScale)
        }else{
            //未绑定账户 显示区绑定按钮
            let addLabel = UILabel(frame: CGRect(x: payImageView.frame.origin.x + payImageView.frame.width + 8 * screenScale, y: payImageView.frame.origin.y, width: accountView.frame.width - paddingLeft - (payImageView.frame.origin.x + payImageView.frame.width + 8 * screenScale), height: payImageView.frame.height))
            addLabel.isUserInteractionEnabled = true
            addLabel.text = NSLocalizedString("Add PayPal account", comment: "")
            addLabel.textColor = UIColor.mainBlue()
            addLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
            let addButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: addLabel.frame.size))
            addButton.addTarget(self, action: #selector(toAccount), for: UIControl.Event.touchUpInside)
            addLabel.addSubview(addButton)
            accountView.addSubview(addLabel)
            
            accountView.frame.size = CGSize(width: accountView.frame.width, height: addLabel.frame.origin.y + addLabel.frame.height + 16 * screenScale)
        }
        
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: paddingLeft, y: accountView.frame.height - 1, width: accountView.frame.width - paddingLeft, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        accountView.layer.addSublayer(bottomLine)
        
        return accountView
    }
    
    //创建提现功能区
    func createWithdrawView() -> UIView{
        let withdrawView = UIView(frame: CGRect(x: 0, y: staticAccountView.frame.origin.y + staticAccountView.frame.height, width: screenWidth, height: 190 * screenScale))
        withdrawView.backgroundColor = UIColor.white
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 12 * screenScale, width: withdrawView.frame.width - paddingLeft * 2, height: 20 * screenScale))
        titleLabel.text = NSLocalizedString("Withdrawal Amount", comment: "")
        titleLabel.textColor = UIColor.fontDarkGray()
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        withdrawView.addSubview(titleLabel)
        
        //余额
        let balanceLabel = LuckyBalanceLabelView(frame: CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height, width: withdrawView.frame.width - paddingLeft * 2, height: 20 * screenScale), balance: globalUserAccount == nil ? 0.0 : globalUserAccount!.balance, rate: globalRate == nil ? 10 : globalRate!.goldExcRate)
        balanceLabel.tag = LuckyTagManager.withdrawTags.balanceLabel
        withdrawView.addSubview(balanceLabel)
        
        //币种
        let dollarLabelView = UILabel(frame: CGRect(x: paddingLeft, y: balanceLabel.frame.origin.y + balanceLabel.frame.height + 18 * screenScale, width: 0, height: 26 * screenScale))
        //默认币种美元 本地币种支持用本地币种
        var symbol = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        if let currencySymbol = globalCurrencyRate?.currencySymbol[globalCurrencyCode]{
            symbol = String.valueOf(any:currencySymbol)
        }
        dollarLabelView.text = symbol
        dollarLabelView.textColor = UIColor.black
        dollarLabelView.font = UIFont.boldFont(size: 22 * screenScale)
        dollarLabelView.textAlignment = NSTextAlignment.center
        dollarLabelView.sizeToFit()
        dollarLabelView.frame.size = CGSize(width: dollarLabelView.frame.width, height: 26 * screenScale)
        withdrawView.addSubview(dollarLabelView)
        
        //全部提现按钮
        let allLabel = UILabel()
        allLabel.text = NSLocalizedString("Withdraw all", comment: "")
        allLabel.textColor = UIColor.mainBlue()
        allLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        allLabel.textAlignment = NSTextAlignment.center
        allLabel.sizeToFit()
        allLabel.frame = CGRect(x: withdrawView.frame.width - paddingLeft - allLabel.frame.width, y: dollarLabelView.frame.origin.y - 10 * screenScale, width: allLabel.frame.width, height: dollarLabelView.frame.height + 20 * screenScale)
        allLabel.isUserInteractionEnabled = true
        allLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(withdrawAll)))
        withdrawView.addSubview(allLabel)
        
        //金额输入框
        let amountInput = UITextField(frame: CGRect(x: dollarLabelView.frame.origin.x + dollarLabelView.frame.width + 8 * screenScale, y: allLabel.frame.origin.y, width: allLabel.frame.origin.x - (dollarLabelView.frame.origin.x + dollarLabelView.frame.width + 16 * screenScale), height: allLabel.frame.height))
        amountInput.tag = LuckyTagManager.withdrawTags.amountInput
        amountInput.delegate = self
        amountInput.tintColor = UIColor.mainYellow()
        amountInput.keyboardType = UIKeyboardType.decimalPad
        amountInput.clearButtonMode = UITextField.ViewMode.whileEditing
        amountInput.textColor = UIColor.fontBlack()
        amountInput.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        amountInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter the withdrawal amount", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        withdrawView.addSubview(amountInput)
        
//        let messageLabel = UILabel(frame: CGRect(x: paddingLeft, y: allLabel.frame.origin.y + allLabel.frame.height, width: withdrawView.frame.width - paddingLeft * 2, height: 16 * screenScale))
//        messageLabel.tag = LuckyTagManager.withdrawTags.messageLabel
//        messageLabel.isHidden = true
//        messageLabel.text = NSLocalizedString("If the balance is less than $100,you must withdraw all", comment: "")
//        messageLabel.textColor = UIColor.fontDarkGray()
//        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
//        withdrawView.addSubview(messageLabel)
        
        let inputBottomLine = CALayer()
        inputBottomLine.frame = CGRect(x: paddingLeft, y: amountInput.frame.origin.y + amountInput.frame.height + 4 * screenScale, width: withdrawView.frame.width - paddingLeft, height: 1)
        inputBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        withdrawView.layer.addSublayer(inputBottomLine)
        
        //积分
        let pointLabel = UILabel(frame: CGRect(x: paddingLeft, y: inputBottomLine.frame.origin.y + inputBottomLine.frame.height + 14 * screenScale, width: 0, height: 20 * screenScale))
        pointLabel.tag = LuckyTagManager.withdrawTags.pointLabel
        pointLabel.text = "\(NSLocalizedString("Reward Points", comment: "")):\(globalUserAccount == nil ? 0.0 : globalUserAccount!.scoreBalance)"
        pointLabel.textColor = UIColor.fontDarkGray()
        pointLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        pointLabel.sizeToFit()
        pointLabel.frame.size = CGSize(width: pointLabel.frame.width, height: 20 * screenScale)
        withdrawView.addSubview(pointLabel)
        
        //显示积分规则按钮
        let pointButton = UIButton(frame: CGRect(x: pointLabel.frame.origin.x + pointLabel.frame.width + 4 * screenScale, y: pointLabel.frame.origin.y, width: pointLabel.frame.height, height: pointLabel.frame.height))
        pointButton.tag = LuckyTagManager.withdrawTags.pointButton
        pointButton.setImage(UIImage(named: "image_question"), for: UIControl.State.normal)
        pointButton.addTarget(self, action: #selector(showAnswer), for: UIControl.Event.touchUpInside)
        withdrawView.addSubview(pointButton)
        
        let poundageLabel = LuckyWithdrawPoundageLabel(frame: CGRect(x: paddingLeft, y: pointLabel.frame.origin.y + pointLabel.frame.height + 4 * screenScale, width: withdrawView.frame.width - paddingLeft * 2, height: 20 * screenScale), poundage: 0.0)
        poundageLabel.tag = LuckyTagManager.withdrawTags.poundageLabel
        withdrawView.addSubview(poundageLabel)
        
        return withdrawView
    }
    
    //创建功能和提示
    func createFuncView() -> UIView{
        let funcView = UIView(frame: CGRect(x: 0, y: staticWithdrawView.frame.origin.y + staticWithdrawView.frame.height, width: screenWidth, height: 0))
        
        //确认按钮
        let button = UIButton(frame: CGRect(x: paddingLeft, y: 40 * screenScale, width: funcView.frame.width - paddingLeft * 2, height: 48 * screenScale))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle(NSLocalizedString("Confirm", comment: ""), for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(confirm), for: UIControl.Event.touchUpInside)
        funcView.addSubview( button)
        
        //提示
        let noticeView = UIView(frame: CGRect(x: 0, y: button.frame.origin.y + button.frame.height + 20 * screenScale, width: funcView.frame.width, height: 140 * screenScale))
        noticeView.backgroundColor = UIColor.white
        
        //标题
        let headView = UIView(frame: CGRect(x: 0, y: 0, width: noticeView.frame.width, height: 44 * screenScale))
        let titleLabel = UILabel()
        titleLabel.text = NSLocalizedString("Notice", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(x: (headView.frame.width - titleLabel.frame.width)/2, y: 0, width: titleLabel.frame.width, height: headView.frame.height)
        headView.addSubview(titleLabel)
        let leftImageView = UIImageView(frame: CGRect(x: titleLabel.frame.origin.x - 40 * screenScale, y: (headView.frame.height - 6 * screenScale)/2, width: 30 * screenScale, height: 6 * screenScale))
        leftImageView.image = UIImage(named: "image_notice_left")
        headView.addSubview(leftImageView)
        let rightImageView = UIImageView(frame: CGRect(x: titleLabel.frame.origin.x + titleLabel.frame.width + 10 * screenScale, y: leftImageView.frame.origin.y, width: leftImageView.frame.width, height: leftImageView.frame.height))
        rightImageView.image = UIImage(named: "image_notice_right")
        headView.addSubview(rightImageView)
        noticeView.addSubview(headView)
        
        //提现提示
        let label = UILabel(frame: CGRect(x: 10 * screenScale, y: headView.frame.origin.y + headView.frame.height, width: funcView.frame.width - 20 * screenScale, height: 0))
        if(globalWithdrawExplanation != nil){
            //有提现提示内容
            label.numberOfLines = 0
            do{
                //显示html格式文本
                let srtData = globalWithdrawExplanation!.data(using: String.Encoding.unicode, allowLossyConversion: true)!
                let attrStr = try NSAttributedString(data: srtData, options: [NSAttributedString.DocumentReadingOptionKey.documentType: NSAttributedString.DocumentType.html], documentAttributes: nil)
                label.attributedText = attrStr
            }catch {
                label.text = ""
            }
            label.textColor = UIColor.fontDarkGray()
            label.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
            label.sizeToFit()
            noticeView.frame.size = CGSize(width: noticeView.frame.width, height: label.frame.origin.y + label.frame.height)
        }
        
        //提示背景
        let noticeBgView = UIImageView(frame: CGRect(x: noticeView.frame.width * 0.5, y: noticeView.frame.height - (noticeView.frame.width * 0.5 * 0.4), width: noticeView.frame.width * 0.5, height: noticeView.frame.width * 0.5 * 0.4))
        noticeBgView.image = UIImage(named: "image_notice_bg")
        noticeView.addSubview(noticeBgView)
        noticeView.addSubview(label)
        funcView.addSubview(noticeView)
        
        funcView.frame.size = CGSize(width: funcView.frame.width, height: noticeView.frame.origin.y + noticeView.frame.height + 20 * screenScale)
        return funcView
    }
    
    //获取提现提示内容
    func getExplanation(){
        if(globalWithdrawExplanation == nil){
            //没有内容时获取
            LuckyHttpManager.getWithoutToken("front/explanation/withdraw", params: NSDictionary(), success: { (data) in
                //成功 赋值
                if let dataStr = data as? String{
                    globalWithdrawExplanation = LuckyEncodingUtil.getFromBase64(dataStr)
                }
                
                //创建功能和提示区
                self.staticFuncView = self.createFuncView()
                self.staticMainView.addSubview(self.staticFuncView)
                //内容高度
                self.staticMainView.contentSize = CGSize(width: self.staticMainView.frame.width, height: self.staticFuncView.frame.origin.y + self.staticFuncView.frame.height)
                //更新用户账户数据
                self.getUserAccount()
            }) { (reason) in
                //失败 显示空白
                //创建功能和提示区
                self.staticFuncView = self.createFuncView()
                self.staticMainView.addSubview(self.staticFuncView)
                //内容高度
                self.staticMainView.contentSize = CGSize(width: self.staticMainView.frame.width, height: self.staticFuncView.frame.origin.y + self.staticFuncView.frame.height)
                //更新用户账户数据
                self.getUserAccount()
            }
        }else{
            //有内容 直接使用
            //创建功能和提示区
            self.staticFuncView = self.createFuncView()
            self.staticMainView.addSubview(self.staticFuncView)
            //内容高度
            self.staticMainView.contentSize = CGSize(width: self.staticMainView.frame.width, height: self.staticFuncView.frame.origin.y + self.staticFuncView.frame.height)
            //更新用户账户数据
            self.getUserAccount()
        }
    }
    
    //获取系统费率
    func getRate(){
        LuckyHttpManager.getWithToken("front/user/rate", params: NSDictionary(), success: { (data) in
            let dataDic = data as! NSDictionary
            
            let dataModel = LuckyFrontUserRateModel(data: dataDic)
            
            globalRate = dataModel
            //更新费率
            self.updateRate()
        }) { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getRate()
            }
        }
    }
    
    //取币种汇率
    func getCurrencyRate(){
        LuckyHttpManager.getWithoutToken("front/area/currency", params: NSDictionary(), success: { (data) in
            let dataModel = LuckyCurrencyRateModel(data: data)
            
            globalCurrencyRate = dataModel
        }) { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getCurrencyRate()
            }
        }
    }
    
    //更新系统费率
    func updateRate(){
        //更新余额
        let balanceLabel = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.balanceLabel) as! LuckyBalanceLabelView
        balanceLabel.setValue(balance: globalUserAccount!.balance, rate: globalRate == nil ? 10 : globalRate!.goldExcRate)
    }
    
    //获取用户账户数据
    func getUserAccount(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyUserDataManager.getUserData(success: { (frontUser) in
            globalUserData = frontUser
            LuckyUserDataManager.getUserAccount(success: { (userAccount) in
                globalUserAccount = userAccount
                
                //更新用户账户数据显示
                self.updateAccount()
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }, error: { (reason) in
            //获取失败 删除本地用户存储数据 去登录页
            globalUserData = nil
            globalUserAccount = nil
            LuckyLocalDataManager.writeLocationData(data: nil)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            
            let loginViewController = LuckyLoginViewController()
            loginViewController.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(loginViewController, animated: false)
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //更新账户显示
    func updateAccount(){
        if(staticMainView != nil){
            //积分可抵扣提现金额
            let scorePower = globalUserAccount!.scoreBalance/globalRate!.scoreAmountRate
            //美元余额
            let balanceAmount = globalUserAccount!.balance/globalRate!.goldExcRate
            
            //计算最大可提现金额
            if(scorePower >= balanceAmount){
                //积分可抵扣全部手续费
                var amount: Double = balanceAmount
                
                //若系统支持本地币种 换算为本地币种余额
                if let currencyRate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
                    amount = balanceAmount * Double.valueOf(any: currencyRate)
                }else{
                    amount = balanceAmount
                }
                maxAmount = (round(amount * 100))/100
            }else{
                //积分不可抵扣全部手续费
                //需缴纳手续费的部分
                let poundageAmount = balanceAmount - scorePower
                //提现金额 + 手续费
                let currencyAmount = scorePower + floor(poundageAmount / (1 + globalRate!.poundageRate) * 100) / 100
                
                //若系统支持本地币种 换算为本地币种余额
                var amount: Double = currencyAmount
                if let currencyRate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
                    amount = currencyAmount * Double.valueOf(any: currencyRate)
                }else{
                    amount = currencyAmount
                }
                maxAmount = (round(amount * 100))/100
            }
            
            //重绘用户账户区
            staticAccountView.removeFromSuperview()
            staticAccountView = createAccountView()
            staticMainView.addSubview(staticAccountView)
            
            staticWithdrawView.frame.origin = CGPoint(x: 0, y: staticAccountView.frame.origin.y + staticAccountView.frame.height)
            staticFuncView.frame.origin = CGPoint(x: 0, y: staticWithdrawView.frame.origin.y + staticWithdrawView.frame.height)
            
            //更新各显示值
            let balanceLabel = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.balanceLabel) as! LuckyBalanceLabelView
            let amountInput = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.amountInput) as! UITextField
            let pointLabel = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.pointLabel) as! UILabel
            let pointButton = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.pointButton) as! UIButton
            let poundageLabel = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.poundageLabel) as! LuckyWithdrawPoundageLabel
            
            //余额
            balanceLabel.setValue(balance: globalUserAccount!.balance, rate: globalRate == nil ? 10 : globalRate!.goldExcRate)
            //提现输入框归零
            amountInput.text = ""
            //积分
            pointLabel.text = "\(NSLocalizedString("Points", comment: "")):\(globalUserAccount == nil ? 0.0 : globalUserAccount!.scoreBalance)"
            pointLabel.sizeToFit()
            pointLabel.frame.size = CGSize(width: pointLabel.frame.width, height: pointButton.frame.height)
            pointButton.frame.origin = CGPoint(x: pointLabel.frame.origin.x + pointLabel.frame.width + 4 * screenScale, y: pointButton.frame.origin.y)
            //手续费 归零
            poundageLabel.setValue(poundage: 0.0)
            
//            if(maxAmount <= 100){ staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.messageLabel)?.isHidden = false
//
//                amountInput.text = LuckyUtils.moneyFormat(amount: maxAmount)
//                amountInput.isUserInteractionEnabled = false
//                countPoundage(value: maxAmount)
//            }
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let oldStr = textField.text!
        let str = oldStr.replacingCharacters(in: oldStr.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.withdrawTags.amountInput){
            //金额输入框
            if(globalUserAccount == nil){
                return false
            }
            
            //空值设0
            if(str == ""){
                countPoundage(value: 0)
                return true
            }
            
            //输入非数字 不变
            if(str == "."){
                return false
            }
            
            //取数值
            let newValue: String = str.replacingOccurrences(of: ",", with: "")
            if(Double(newValue) != nil){
                //输入内容为数值
                if(str[str.count - 1] == "." ){
                    //最后一位是小数点
                    if(oldStr.contains(".") && string == "."){
                        //多个小数点 不可输入
                        return false
                    }else{
                        //新增小数点 允许
                        return true
                    }
                }
                
                if(str[str.count - 4] == "."){
                    //小数部分三位 不可输入
                    return false
                }
                
                //取新金额
                let newMoney = Double(newValue)!
                if(str[str.count - 1] == "0" && str[str.count - 2] == "."){
                    // .0 格式允许
                    countPoundage(value: newMoney)
                    return true
                }
                
                if(newMoney < maxAmount){
                    //新金额小于可提现额 使用当前输入金额
                    textField.text = LuckyUtils.coinFullFormat(amount: newMoney)
                    countPoundage(value: newMoney)
                }else{
                    //否则 使用最大可提现额
                    textField.text = LuckyUtils.coinFullFormat(amount: maxAmount)
                    countPoundage(value: maxAmount)
                }
                return false
            }else{
                //否则 不变
                return false
            }
        }
        return true
    }
    
    //清空 金额归零
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        countPoundage(value: 0)
        return true
    }
    
    //计算手续费
    func countPoundage(value: Double){
        //默认美元
        var dollorValue: Double = value
        
        //系统支持本地币种 换算本地币种
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            dollorValue = value / Double.valueOf(any: rate)
        }else{
            dollorValue = value
        }
        
        //数据不足 返回
        if(globalUserAccount == nil || globalRate == nil){
            return
        }
        
        //更新手续费
        let poundageLabel = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.poundageLabel) as! LuckyWithdrawPoundageLabel
        let poundageRate = globalRate!.poundageRate
        
        //积分可抵扣总提现金额
        let scorePower = globalUserAccount!.scoreBalance/globalRate!.scoreAmountRate
        
        if(scorePower >= dollorValue){
            //积分可抵扣全部手续费 手续费 0
            poundageLabel.setValue(poundage: 0)
        }else{
            //积分不足 计算手续费 显示
            let poundage = (dollorValue - scorePower) * poundageRate
            poundageLabel.setValue(poundage: ceil(poundage * 100)/100)
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    @objc func hideKeyboard(){
        self.view.endEditing(true)
    }
    
    //去paypal账户页
    @objc func toAccount(){
        let vc = LuckyPayAccountViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //全部提现按钮 使用最大可提现额
    @objc func withdrawAll(){
//        if(maxAmount >= 100){
            if(globalUserAccount == nil){
                return
            }
            
            let amountInput = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.amountInput) as! UITextField
            
            amountInput.text = LuckyUtils.coinFullFormat(amount: maxAmount)
            countPoundage(value: maxAmount)
//        }
    }
    
    //显示积分规则
    @objc func showAnswer(){
        staticAnswerView.isHidden = false
    }
    
    //隐藏积分规则
    @objc func hideAnswer(){
        staticAnswerView.isHidden = true
    }
    
    //确认提现
    @objc func confirm(){
        self.view.endEditing(true)
        let amountInput = staticWithdrawView.viewWithTag(LuckyTagManager.withdrawTags.amountInput) as! UITextField
        let amountText = amountInput.text!.replacingOccurrences(of: ",", with: "")
        //判断输入金额格式
        if(!LuckyUtils.checkMoney(money: amountText)){
            LuckyAlertView(title: NSLocalizedString("unavailable withdraw amount", comment: "")).showByTime(time: 2)
            return
        }
        let amount = Double(amountText)!
        
        //币种默认美元
        var currencyAmount: Double = amount
        var currencyRate: Double = 1.0
        var currencyCode = "USD"
        
        //系统支持本地币种 汇率换算
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            currencyRate = Double.valueOf(any: rate)
            currencyAmount = currencyAmount / Double.valueOf(any: rate)
            currencyCode = globalCurrencyCode
        }
        
//        if(currencyAmount > globalUserAccount!.balance/globalRate!.goldExcRate){
//            currencyAmount = globalUserAccount!.balance/globalRate!.goldExcRate
//        }
        currencyAmount = round(currencyAmount * 100)/100
        
        //计算手续费
        var poundage: Double = 0
        let poundageRate = globalRate!.poundageRate
        let scorePower = globalUserAccount!.scoreBalance/globalRate!.scoreAmountRate
        if(scorePower >= currencyAmount){
            poundage = 0
        }else{
            poundage = (currencyAmount - scorePower) * poundageRate
            poundage = ceil(poundage * 100)/100
        }
        //总提现消耗金币数
        let dAmount = (poundage + currencyAmount) * globalRate!.goldExcRate
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/userWithdraw/withdraw", params: ["amount" : LuckyEncodingUtil.getBase64(LuckyUtils.moneyFormat(amount: currencyAmount)), "dAmount": LuckyEncodingUtil.getBase64(LuckyUtils.moneyFormat(amount: dAmount)), "currency": LuckyEncodingUtil.getBase64(currencyCode), "currencyRate": LuckyEncodingUtil.getBase64(String(currencyRate)), "currencyAmount": LuckyEncodingUtil.getBase64(LuckyUtils.moneyFormat(amount: amount))], success: { (data) in
            //成功 提示并返回上一页
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            LuckyAlertView(title: NSLocalizedString("Withdraw successfully", comment: "")).showByTime(time: 2)
            self.navigationController?.popViewController(animated: true)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
}
