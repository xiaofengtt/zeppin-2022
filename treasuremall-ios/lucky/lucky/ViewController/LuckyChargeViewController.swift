//
//  LuckyChargeViewController.swift
//  lucky
//  充值页
//  Created by Farmer Zhu on 2020/8/26.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyChargeViewController: UIViewController, UIScrollViewDelegate, LuckyBannerScrollViewDelegate, UITextFieldDelegate, LuckyChargeCapitalAccountViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //滚动区
    private var staticScrollView: UIScrollView!
    //自定金额输入页
    private var staticAmountView: UIView!
    //充值渠道选择页
    private var staticCapitalAccountView: LuckyChargeCapitalAccountView?
    
    //金币汇率
    private var coinRate: Double = 1
    
    //banner数据列表
    private var bannerList: [LuckyBannerModel] = []
    //首充数据
    private var firstChargeModel: LuckyActivityModel!
    //固定充值金额列表
    private var chargeAmountList : [LuckyFrontUserRechargeAmountSetModel] = []
    //充值渠道列表
    private var capitalAccountList: [LuckyCapitalAccountModel] = []
    
    //充值金额 美元
    private var chargeAmount: Double?
    //充值金币数
    private var chargeDAmount: Double?
    //是否自定金额
    private var isFree: Bool?
    
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
        //创建自定金额选择页
        staticAmountView = createAmountView()
        self.view.addSubview(staticAmountView)
        //获取充值说明
        getExplanation()
        //获取banner列表
        getBannerList()
        //获取货币汇率
        getCurrencyRate()
        //获取系统汇率
        getRate()
        //获取充值渠道列表
        getMethodList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Top Up", comment: "")
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        
        return scrollView
    }
    
    //创建自定金额选择页
    func createAmountView() -> UIView {
        let amountView = UIView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size))
        amountView.layer.zPosition = 0.9
        amountView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        amountView.isHidden = true
        
        //主框体
        let mainView = UIView(frame: CGRect(x: 44 * screenScale, y: (amountView.frame.height - 220 * screenScale)/2, width: amountView.frame.width - 88 * screenScale, height: 210 * screenScale))
        mainView.backgroundColor = UIColor.white
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 4 * screenScale
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 22 * screenScale, width: mainView.frame.width, height: 22 * screenScale))
        titleLabel.text = NSLocalizedString("Top Up Amount", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(titleLabel)
        
        //金额输入框
        let amountInput = UITextField(frame: CGRect(x: mainView.frame.width * 0.3, y: titleLabel.frame.origin.y + titleLabel.frame.height + 24 * screenScale, width: mainView.frame.width * 0.4, height: 40 * screenScale))
        amountInput.tag = LuckyTagManager.chargeTags.amountInput
        amountInput.delegate = self
        amountInput.tintColor = UIColor.mainYellow()
        amountInput.textAlignment = NSTextAlignment.center
        amountInput.keyboardType = UIKeyboardType.numberPad
        amountInput.clearButtonMode = UITextField.ViewMode.never
        amountInput.textColor = UIColor.fontBlack()
        amountInput.font = UIFont.mediumFont(size: 24 * screenScale)
        let inputBottomLine = CALayer()
        inputBottomLine.frame = CGRect(x: 0, y: amountInput.frame.height - 1, width: amountInput.frame.width, height: 1)
        inputBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        amountInput.layer.addSublayer(inputBottomLine)
        mainView.addSubview(amountInput)
        
        //货币符号
        let signLabel = UILabel(frame: CGRect(x: amountInput.frame.origin.x - 50 * screenScale, y: amountInput.frame.origin.y, width: 50 * screenScale, height: amountInput.frame.height))
        signLabel.text = String.valueOf(any:globalCurrencyRate?.currencySymbol["USD"])
        signLabel.textColor = amountInput.textColor
        signLabel.font = amountInput.font
        signLabel.textAlignment = NSTextAlignment.right
        mainView.addSubview(signLabel)
        
        //充值金币数
        let dAmountView = LuckyChargeDAmountView(frame: CGRect(x: 0, y: amountInput.frame.origin.y + amountInput.frame.height + 18 * screenScale, width: mainView.frame.width, height: 20 * screenScale))
        dAmountView.tag = LuckyTagManager.chargeTags.dAmountView
        mainView.addSubview(dAmountView)
        
        //取消按钮
        let cancelButton = UIButton(frame: CGRect(x: 0, y: mainView.frame.height - 46 * screenScale, width: mainView.frame.width/2, height: 46 * screenScale))
        cancelButton.setTitle(NSLocalizedString("Cancel", comment: ""), for: UIControl.State.normal)
        cancelButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        cancelButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        cancelButton.layer.borderColor = UIColor.backgroundGray().cgColor
        cancelButton.layer.borderWidth = 1
        cancelButton.addTarget(self, action: #selector(amountCancel), for: UIControl.Event.touchUpInside)
        mainView.addSubview(cancelButton)
        
        //确认按钮
        let sureButton = UIButton(frame: CGRect(x: mainView.frame.width/2, y: cancelButton.frame.origin.y, width: mainView.frame.width/2, height: cancelButton.frame.height))
        sureButton.tag = -1
        sureButton.setTitle(NSLocalizedString("OK", comment: ""), for: UIControl.State.normal)
        sureButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        sureButton.titleLabel?.font = cancelButton.titleLabel?.font
        sureButton.layer.borderColor = cancelButton.layer.borderColor
        sureButton.layer.borderWidth = cancelButton.layer.borderWidth
        sureButton.addTarget(self, action: #selector(charge(_:)), for: UIControl.Event.touchUpInside)
        mainView.addSubview(sureButton)
        
        amountView.addSubview(mainView)
        return amountView
    }
    
    //创建滚动区内容
    func createScrollViewContent(hasActivity: Bool) -> UIView{
        let paddingLeft = 10 * screenScale
        
        //内容区
        let contentView = UIView()
        if(bannerList.count == 0){
            //没有充值banner
            contentView.frame = CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0)
        }else{
            //有充值banner 预留banner位置
            contentView.frame = CGRect(x: 0, y: self.staticScrollView.frame.width/3.75 + 40 * screenScale, width: staticScrollView.frame.width, height: 0)
        }
        
        //主功能区
        let mainView = UIView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width, height: 0))
        mainView.backgroundColor = UIColor.white
        
        //首冲活动按钮
        let firstChargeButton = UIButton(frame: CGRect(x: paddingLeft, y: 0, width: 0, height: 0))
        if(hasActivity){
            //如果有首冲活动 显示首冲活动按钮
            firstChargeButton.frame.size = CGSize(width: mainView.frame.width - paddingLeft * 2, height: (mainView.frame.width - paddingLeft * 2)/355*75)
            firstChargeButton.sd_setBackgroundImage(with: URL(string: firstChargeModel.bannerUrl), for: UIControl.State.normal, placeholderImage: nil, options: SDWebImageOptions.retryFailed, context: nil)
            mainView.addSubview(firstChargeButton)
        }
        
        //标题
        let chargeTitleView = UIView(frame: CGRect(x: 0, y: firstChargeButton.frame.height == 0 ? 10 * screenScale : firstChargeButton.frame.origin.y + firstChargeButton.frame.height + 30 * screenScale, width: mainView.frame.width, height: 20 * screenScale))
        let chargeTitleIcon = UIView(frame: CGRect(x: paddingLeft, y: (chargeTitleView.frame.height - 14 * screenScale)/2, width: 4 * screenScale, height: 14 * screenScale))
        chargeTitleIcon.backgroundColor = UIColor.mainYellow()
        chargeTitleIcon.layer.masksToBounds = true
        chargeTitleIcon.layer.cornerRadius = 2 * screenScale
        chargeTitleView.addSubview(chargeTitleIcon)
        let chargeTitleLabel = UILabel(frame: CGRect(x: chargeTitleIcon.frame.origin.x + chargeTitleIcon.frame.width + 6 * screenScale, y: 0, width: chargeTitleView.frame.width - (chargeTitleIcon.frame.origin.x + chargeTitleIcon.frame.width + 6 * screenScale + paddingLeft), height: chargeTitleView.frame.height))
        chargeTitleLabel.text = NSLocalizedString("SELECT AMOUNT", comment: "")
        chargeTitleLabel.textColor = UIColor.fontBlack()
        chargeTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        chargeTitleView.addSubview(chargeTitleLabel)
        mainView.addSubview(chargeTitleView)
        
        //充值金额列表
        let chargeAmountsView = UIView(frame: CGRect(x: 0, y: chargeTitleView.frame.origin.y + chargeTitleView.frame.height + 16 * screenScale, width: mainView.frame.width, height: 0))
        //循环充值金额列表数据 逐条添加
        for i in 0 ..< chargeAmountList.count{
            let chargeAmountCell = LuckyChargeAmountCellView(frame: CGRect(x: 0, y: (44 * screenScale) * CGFloat(i), width: chargeAmountsView.frame.width, height: 44 * screenScale), data: chargeAmountList[i])
            chargeAmountCell.button.tag = i
            chargeAmountCell.button.addTarget(self, action: #selector(charge(_:)), for: UIControl.Event.touchUpInside)
            chargeAmountsView.addSubview(chargeAmountCell)
        }
        chargeAmountsView.frame.size = CGSize(width: chargeAmountsView.frame.width, height: (44 * screenScale) * CGFloat(chargeAmountList.count))
        
        //在最后 添加自由金额充值按钮
//        let chargeFreeCell = LuckyChargeAmountCellView(frame: CGRect(x: 0, y: (44 * screenScale) * CGFloat(chargeAmountList.count), width: chargeAmountsView.frame.width, height: 44 * screenScale), data: nil)
//        chargeFreeCell.button.addTarget(self, action: #selector(showAmountView), for: UIControl.Event.touchUpInside)
//        chargeAmountsView.addSubview(chargeFreeCell)
//        chargeAmountsView.frame.size = CGSize(width: chargeAmountsView.frame.width, height: chargeFreeCell.frame.origin.y + chargeFreeCell.frame.height)
        mainView.addSubview(chargeAmountsView)
        
        //计算内容高度
        mainView.frame.size = CGSize(width: mainView.frame.width, height: chargeAmountsView.frame.origin.y + chargeAmountsView.frame.height + 18 * screenScale)
        contentView.addSubview(mainView)
        
        //提醒
        let noticeView = UIView(frame: CGRect(x: 0, y: mainView.frame.origin.y + mainView.frame.height + 10 * screenScale, width: contentView.frame.width, height: 140 * screenScale))
        noticeView.backgroundColor = UIColor.white
        //头
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
        
        //内容
        let label = UILabel(frame: CGRect(x: 10 * screenScale, y: headView.frame.origin.y + headView.frame.height, width: contentView.frame.width - 20 * screenScale, height: 0))
        if(globalChargeExplanation != nil){
            //有提醒数据
            label.numberOfLines = 0
            do{
                //显示html格式文本
                let srtData = globalChargeExplanation!.data(using: String.Encoding.unicode, allowLossyConversion: true)!
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
        contentView.addSubview(noticeView)
        
        //内容高度
        contentView.frame.size = CGSize(width: contentView.frame.width, height: noticeView.frame.origin.y + noticeView.frame.height + 20 * screenScale)
        return contentView
    }
    
    //获取banner数据
    func getBannerList(){
        LuckyHttpManager.getWithoutToken("front/banner/list", params: ["level": globalUserData!.level, "type": "type_payment"], success: { (data) in
            let dataArray = data as! [NSDictionary]
            
            var banners: [LuckyBannerModel] = []
            for dataDic in dataArray{
                let dataModel = LuckyBannerModel(data: dataDic)
                if("type_payment" == dataModel.type){
                    //类型为充值相关
                    banners.append(dataModel)
                }
            }
            self.bannerList = banners
            if(self.bannerList.count > 0){
                //如果有数据 创建banner
                let topView = UIView(frame: CGRect(x: 0, y: 0, width: self.staticScrollView.frame.width, height: self.staticScrollView.frame.width/3.75 + 40 * screenScale))
                topView.backgroundColor = UIColor.white
                let topScrollView = LuckyBannerScrollView(frame: CGRect(x: 0, y: 20 * screenScale, width: self.staticScrollView.frame.width, height: self.staticScrollView.frame.width/3.75), paddingLeft: 10 * screenScale, cornerRadius: 4 * screenScale, bannerList: self.bannerList)
                topScrollView.delegate = self
                topView.addSubview(topScrollView)
                self.staticScrollView.addSubview(topView)
            }
        }) { (reason) in
            //失败1s后重新获取
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getBannerList()
            }
        }
    }
    
    //获取充值相关数据
    func getActivity(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //获取首冲活动数据
        LuckyHttpManager.getWithoutToken("front/activity/get", params: ["uuid": "firstcharge", "frontUser" : globalUserData!.uuid], success: { (data) in
            //成功赋值
            let dataDic = data as! NSDictionary
            let dataModel = LuckyActivityModel(data: dataDic)
            self.firstChargeModel = dataModel
            //获取固定充值金额列表
            LuckyHttpManager.getWithoutToken("front/userRecharge/getAmountSet", params: NSDictionary(), success: { (adata) in
                //成功 赋值
                let dataArray = adata as! [NSDictionary]
                
                var datas: [LuckyFrontUserRechargeAmountSetModel] = []
                for dataDic in dataArray{
                    let dataModel = LuckyFrontUserRechargeAmountSetModel(data: dataDic)
                    datas.append(dataModel)
                }
                self.chargeAmountList = datas
                
                //创建滚动区内容
                let contentView = self.createScrollViewContent(hasActivity: (self.firstChargeModel.isPartake && self.firstChargeModel.status == "normal"))
                self.staticScrollView.addSubview(contentView)
                self.staticScrollView.contentSize = CGSize(width: self.staticScrollView.frame.width, height: contentView.frame.origin.y +  contentView.frame.height)
                
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //获取系统汇率
    func getRate(){
        LuckyHttpManager.getWithToken("front/user/rate", params: NSDictionary(), success: { (data) in
            let dataDic = data as! NSDictionary
            
            let dataModel = LuckyFrontUserRateModel(data: dataDic)
            
            globalRate = dataModel
            self.coinRate = dataModel.goldExcRate
            
            //获取充值相关
            self.getActivity()
        }) { (reason) in
            //失败1s后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getRate()
            }
        }
    }
    
    //获取币种汇率
    func getCurrencyRate(){
        LuckyHttpManager.getWithoutToken("front/area/currency", params: NSDictionary(), success: { (data) in
            let dataModel = LuckyCurrencyRateModel(data: data)
            
            globalCurrencyRate = dataModel
        }) { (reason) in
            //失败1s后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getCurrencyRate()
            }
        }
    }
    
    //获取充值渠道数据
    func getMethodList(){
        LuckyHttpManager.getWithToken("front/capital/accountList", params: NSDictionary(), success: { (data) in
            let dataArray = data as! [NSDictionary]
            
            var datas: [LuckyCapitalAccountModel] = []
            for dataDic in dataArray{
                let dataModel = LuckyCapitalAccountModel(data: dataDic)
                datas.append(dataModel)
            }
            
            self.capitalAccountList = datas
        }) { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getMethodList()
            }
        }
    }
    
    //获取充值提示
    func getExplanation(){
        if(globalChargeExplanation == nil){
            LuckyHttpManager.getWithoutToken("front/explanation/recharge", params: NSDictionary(), success: { (data) in
                if let dataStr = data as? String{
                    globalChargeExplanation = LuckyEncodingUtil.getFromBase64(dataStr)
                }
            }) { (reason) in
            }
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.chargeTags.amountInput){
            //金额输入框
            if(str.count > 6){
                //限长 6
                return false
            }
            
            //空值设为0
            if("" == str){
                str = "0"
            }
            
            if(!LuckyUtils.checkMoney(money: str)){
                //非金额格式 无法输入
                return false
            }
            
            if(textField.text == "" && "0" == str){
                //0值 跳出判断
                return false
            }
            
            if(Double(str) == nil){
                //非double类型 无法输入
                return false
            }
            
            //金额 美元
            let amount: Double = Double(str)!
            //固定充值金额 小到大排序
            var chargeAmounts = chargeAmountList
            chargeAmounts.sort { (new, old) -> Bool in
                return new.amount < old.amount
            }
            
            //计算应得金币数
            let dAmountView = staticAmountView.viewWithTag(LuckyTagManager.chargeTags.dAmountView) as! LuckyChargeDAmountView
            var giveDAmount: Double = 0
            //循环固定金额列表 计算应得金币数
            for ca in chargeAmounts{
                if(ca.amount == amount){
                    //金额与固定金额列表中金额相等 取该金额应得金币数 并返回
                    dAmountView.setDAmount(dAmount: ca.dAmount + ca.giveDAmount)
                    return true
                }else if(ca.amount < amount){
                    //金额大于该档次固定金额 赠送金币数为该档次赠送金币数
                    giveDAmount = ca.giveDAmount
                }else{
                    //金额小于该档次固定金额 跳出循环
                    break
                }
            }
            //金币数显示 赋值
            dAmountView.setDAmount(dAmount: amount + giveDAmount)
        }
        return true
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //banner事件
    func clickBannerButton(_ code: String) {
        
    }
    
    //展示自定金额输入页
    @objc func showAmountView(){
        staticAmountView.isHidden = false
        
        let amountInput = staticAmountView.viewWithTag(LuckyTagManager.chargeTags.amountInput) as! UITextField
        let dAmountView = staticAmountView.viewWithTag(LuckyTagManager.chargeTags.dAmountView) as! LuckyChargeDAmountView
        //赋初值 0
        dAmountView.setDAmount(dAmount: 0)
        amountInput.text = ""
        amountInput.becomeFirstResponder()
    }
    
    //关闭自定金额输入页
    @objc func amountCancel(){
        staticAmountView.isHidden = true
        
        let amountInput = staticAmountView.viewWithTag(LuckyTagManager.chargeTags.amountInput) as! UITextField
        amountInput.endEditing(true)
    }
    
    //充值
    @objc func charge(_ sender: UIButton){
        if(sender.tag == -1){
            //自定金额充值
            let amountInput = staticAmountView.viewWithTag(LuckyTagManager.chargeTags.amountInput) as! UITextField
            if(Double(amountInput.text!) != nil){
                //自定金额为数字类型
                isFree = true
                chargeAmount = Double(amountInput.text!)!
                amountInput.endEditing(true)
                
                //计算赠与金币数
                //固定充值金额排序 小到大
                var chargeAmounts = chargeAmountList
                chargeAmounts.sort { (new, old) -> Bool in
                    return new.amount < old.amount
                }
                
                var giveDAmount: Double = 0
                //循环固定金额
                for ca in chargeAmounts{
                    if(ca.amount == chargeAmount!){
                        //金额与固定金额列表中金额相等 赠送金币数为赠送金币数 并跳出循环
                        giveDAmount = ca.giveDAmount
                        break
                    }else if(ca.amount < chargeAmount!){
                        //金额大于该档次固定金额 赠送金币数为该档次赠送金币数
                        giveDAmount = ca.giveDAmount
                    }else{
                        //金额小于该档次固定金额 跳出循环
                        break
                    }
                }
                chargeDAmount = chargeAmount! + giveDAmount
            }else{
                return
            }
        }else{
            //固定金额充值
            isFree = false
            chargeAmount = chargeAmountList[sender.tag].amount
            chargeDAmount = chargeAmountList[sender.tag].dAmount
        }
        
        //取可用充值渠道列表
        var enableCapitalAccountList: [LuckyCapitalAccountModel] = []
        //循环所有充值渠道
        for capitalAccount in capitalAccountList{
            if(chargeAmount! >= capitalAccount.min && chargeAmount! <= capitalAccount.max){
                //充值金额在 限额范围内 插入可用列表
                enableCapitalAccountList.append(capitalAccount)
            }
        }
        
        //没有符合的渠道 提示
        if(enableCapitalAccountList.count == 0){
            LuckyAlertView(title: NSLocalizedString("unavailable top up amount", comment: "")).showByTime(time: 2)
            return
        }
        
        //隐藏自定金额输入页 显示充值渠道选择页
        staticAmountView.isHidden = true
        staticCapitalAccountView = LuckyChargeCapitalAccountView(frame: staticAmountView.frame, dataList: enableCapitalAccountList)
        staticCapitalAccountView!.delegate = self
        self.view.addSubview(staticCapitalAccountView!)
        staticCapitalAccountView?.show()
    }
    
    //选择充值渠道
    func selectedCapitalAccount(_ capitalAccount: LuckyCapitalAccountModel) {
        if(chargeAmount == nil || chargeDAmount == nil || isFree == nil){
            //金额数据不全 提示失败
            LuckyAlertView(title: NSLocalizedString("unavailable top up amount", comment: "")).showByTime(time: 2)
            return
        }
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        
        //默认币种美元
        var currencyAmount: Double = chargeAmount!
        var currencyRate: Double = 1.0
        var currencyCode = "USD"
        
        //若本地币种支持 取币种汇率 换算充值金额
        if let rate = globalCurrencyRate?.exchangeRate[globalCurrencyCode]{
            currencyRate = Double.valueOf(any: rate)
            currencyAmount = currencyAmount * Double.valueOf(any: rate)
            currencyCode = globalCurrencyCode
        }
        
        if("paypal" == capitalAccount.type){
            //paypal充值
            LuckyHttpManager.postWithToken("front/userRecharge/byPaypal", params: ["capitalAccount": capitalAccount.uuid,"returnUrl": "https://xshopping.paypal.goback()", "amount": LuckyEncodingUtil.getBase64(String(chargeAmount!)), "dAmount": LuckyEncodingUtil.getBase64(String(chargeDAmount!)), "isFree": (isFree! ? "true" : "false"), "currency": LuckyEncodingUtil.getBase64(currencyCode), "currencyRate": LuckyEncodingUtil.getBase64(String(currencyRate)), "currencyAmount": LuckyEncodingUtil.getBase64(LuckyUtils.currencyFormat(amount: currencyAmount))], success: { (data) in
                if("paypal" == capitalAccount.transType || "credit" == capitalAccount.transType){
                    //渠道核对成功 跳支付页
                    let url = data as! String
                    self.staticCapitalAccountView?.close()
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                    
                    let vc = LuckyPayViewController()
                    vc.url = url
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }else if("stripe" == capitalAccount.type){
            //stripe充值
            LuckyHttpManager.postWithToken("front/userRecharge/byStripe", params: ["capitalAccount": capitalAccount.uuid,"returnUrl": "https://xshopping.stripe.goback()", "amount": LuckyEncodingUtil.getBase64(String(chargeAmount!)), "dAmount": LuckyEncodingUtil.getBase64(String(chargeDAmount!)), "isFree": (isFree! ? "true" : "false"), "currency": LuckyEncodingUtil.getBase64(currencyCode), "currencyRate": LuckyEncodingUtil.getBase64(String(currencyRate)), "currencyAmount": LuckyEncodingUtil.getBase64(LuckyUtils.currencyFormat(amount: currencyAmount))], success: { (data) in
                if("stripe" == capitalAccount.transType){
                    //渠道核对成功 跳支付页
                    let url = data as! String
                    self.staticCapitalAccountView?.close()
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                    
                    let vc = LuckyPayViewController()
                    vc.url = url
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }else if("credit" == capitalAccount.type){
            //马甲信用卡充值（伪） 直接跳页面
            let vc = LuckyPayCreditViewController()
            vc.capitalAccount = capitalAccount.uuid
            vc.amount = chargeAmount!
            vc.dAmount = chargeDAmount!
            vc.isFree = isFree!
            self.navigationController?.pushViewController(vc, animated: true)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }else{
            //其他方式 报错
            LuckyAlertView(title: NSLocalizedString("unavailable method", comment: "")).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
}
