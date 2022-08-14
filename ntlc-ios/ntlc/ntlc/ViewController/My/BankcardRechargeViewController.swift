//
//  BankcardRechargeViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2018/1/4.
//  Copyright © 2018年 teacher zhu. All rights reserved.
//

import UIKit

class BankcardRechargeViewController: UIViewController, UITextFieldDelegate, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var bankcardView: UIView = UIView()
    var balanceView: UIView = UIView()
    var moneyView: UIView = UIView()
    var alertView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    var bankcardSelectView: UIView = UIView()
    var confirmCodeView: AlertCodeView = AlertCodeView()
    
    var bankcardList: [BankcardModel] = []
    var selectedBankcard: Int = 0
    var orderNum: String = ""
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5

    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        createHeaderView()
        createBankcardView()
        createBalanceView()
        createMoneyView()
        createAlertView()
        createSubmitButton()
        createBankcardSelectView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getData()
        createCodeView()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        confirmCodeView.removeFromSuperview()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let inputMoney = mainView.viewWithTag(TagController.rechargeTags.inputMoney) as? UITextField
        
        inputMoney?.endEditing(true)
    }
    
    func createHeaderView(){
        if(isIphoneX()){
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 224 * screenScale))
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
            background.image = UIImage(named: "my_recharge_head_x")
            headerView.addSubview(background)
            mainView.addSubview(headerView)
        }else{
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 180 * screenScale))
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
            background.image = UIImage(named: "my_recharge_head")
            headerView.addSubview(background)
            mainView.addSubview(headerView)
        }
    }
    
    func createBankcardView(){
        bankcardView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 70 * screenScale))
        bankcardView.backgroundColor = UIColor.white
        bankcardView.layer.cornerRadius = cornerRadius * screenScale
        bankcardView.addBaseShadow()
        
        let bankIcon = UIImageView(frame: CGRect(x: 8 * screenScale, y: (bankcardView.frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
        bankIcon.tag = TagController.rechargeTags.selectedBankIcon
        bankcardView.addSubview(bankIcon)
        
        let arrowIcon = UIImageView(frame: CGRect(x: bankcardView.frame.width - (10 + 13) * screenScale, y: (bankcardView.frame.height - 9 * screenScale)/2, width: 13 * screenScale, height: 9 * screenScale))
        arrowIcon.tag = TagController.rechargeTags.selectedBankcardArrow
        arrowIcon.isHidden = true
        arrowIcon.image = UIImage(named: "common_arrow_down")
        bankcardView.addSubview(arrowIcon)
        
        let bankcardName = UILabel(frame: CGRect(x: bankIcon.frame.origin.x + bankIcon.frame.width + 6 * screenScale, y: 0, width: arrowIcon.frame.origin.x - (bankIcon.frame.origin.x + bankIcon.frame.width + 6 * screenScale), height: bankcardView.frame.height))
        bankcardName.tag = TagController.rechargeTags.selectedBankcardName
        bankcardName.textColor = UIColor.fontBlack()
        bankcardName.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        bankcardView.addSubview(bankcardName)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: bankcardView.frame.size))
        button.addTarget(self, action: #selector(chooseBankcard(_:)), for: UIControlEvents.touchUpInside)
        bankcardView.addSubview(button)
        mainView.addSubview(bankcardView)
    }
    
    func createBalanceView(){
        balanceView = UIView(frame: CGRect(x: 0, y: bankcardView.frame.origin.y + bankcardView.frame.height, width: screenWidth, height: 34 * screenScale))
        balanceView.backgroundColor = UIColor.clear
        
        let text = UILabel(frame: CGRect(x: 14 * screenScale, y: 0, width: balanceView.frame.width - 10 * 2 * screenScale, height: balanceView.frame.height))
        text.tag = TagController.rechargeTags.balanceLabel
        text.textColor = UIColor.fontDarkGray()
        text.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        text.attributedText = Utils.getColorNumString(string: "账户余额 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: text.font.pointSize))
        balanceView.addSubview(text)
        
        mainView.addSubview(balanceView)
    }
    
    func createMoneyView(){
        moneyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: balanceView.frame.origin.y + balanceView.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: 50 * screenScale))
        moneyView.backgroundColor = UIColor.white
        moneyView.layer.cornerRadius = cornerRadius * screenScale
        moneyView.addBaseShadow()
        
        let moneyLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 80 * screenScale, height: moneyView.frame.height))
        moneyLabel.text = "充值金额"
        moneyLabel.textColor = UIColor.fontBlack()
        moneyLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        moneyView.addSubview(moneyLabel)
        
        let inputMoney = UITextField(frame: CGRect(x: moneyLabel.frame.origin.x + moneyLabel.frame.width, y: 0, width: moneyView.frame.width - (moneyLabel.frame.origin.x + moneyLabel.frame.width) - 5 * screenScale, height: moneyView.frame.height))
        inputMoney.tag = TagController.rechargeTags.inputMoney
        inputMoney.delegate = self
        inputMoney.keyboardType = UIKeyboardType.decimalPad
        inputMoney.clearButtonMode = UITextFieldViewMode.always
        inputMoney.textColor = UIColor.moneyInputColor()
        inputMoney.font = UIFont.numFont(size: 22 * screenScale)
        inputMoney.attributedPlaceholder = NSAttributedString(string: "请输入充值金额", attributes: [NSAttributedStringKey.foregroundColor: UIColor.mainPlaceholder(),NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        moneyView.addSubview(inputMoney)
        
        mainView.addSubview(moneyView)
    }
    
    func createAlertView(){
        alertView = UIView(frame: CGRect(x: 0, y: moneyView.frame.origin.y + moneyView.frame.height + 10 * screenScale, width: screenWidth, height: 20 * screenScale))
        alertView.isHidden = true
        
        let alertIcon = UIImageView(frame: CGRect(x: 14 * screenScale, y: (alertView.frame.height - 12 * screenScale)/2, width: 12 * screenScale, height: 12 * screenScale))
        alertIcon.image = UIImage(named: "common_warn_red")
        alertView.addSubview(alertIcon)
        
        let alertLabel = UILabel(frame: CGRect(x: alertIcon.frame.origin.x + alertIcon.frame.width + 3 * screenScale, y: 0, width: alertView.frame.width - (alertIcon.frame.origin.x + alertIcon.frame.width + 3 * screenScale) - 14 * screenScale, height: alertView.frame.height))
        alertLabel.tag = TagController.rechargeTags.alertLabel
        alertLabel.textColor = UIColor.mainRed()
        alertLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        alertView.addSubview(alertLabel)
        mainView.addSubview(alertView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: alertView.frame.origin.y + alertView.frame.height + 20 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("充值", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    func createBankcardSelectView(){
        bankcardSelectView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        bankcardSelectView.isHidden = true
        bankcardSelectView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        
        let tableView = UITableView()
        tableView.frame.origin = CGPoint(x: 0, y: bankcardSelectView.frame.height)
        if(bankcardList.count < 5){
            tableView.frame.size = CGSize.init(width: bankcardSelectView.frame.width, height: CGFloat((bankcardList.count + 1) * 60 + 50) * screenScale)
        }else{
            tableView.frame.size = CGSize.init(width: bankcardSelectView.frame.width, height: CGFloat(5 * 60 + 50) * screenScale)
        }
        tableView.tag = TagController.rechargeTags.bankcardTable
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.layer.cornerRadius = cornerRadius * screenScale
        tableView.bounces = false
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        bankcardSelectView.addSubview(tableView)
        
        mainView.addSubview(bankcardSelectView)
    }
    
    func createCodeView(){
        confirmCodeView = AlertCodeView()
        confirmCodeView.codeInput.delegate = self
        confirmCodeView.codeInput.tag = TagController.bankcardTags.inputCode
        confirmCodeView.codeButton.addTarget(self, action: #selector(confirmSendCode(_:)), for: UIControlEvents.touchUpInside)
        confirmCodeView.sureButton.addTarget(self, action: #selector(confirmSure(_:)), for: UIControlEvents.touchUpInside)
        UIApplication.shared.keyWindow?.addSubview(confirmCodeView)
    }
    
    func getData(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getUser(uuid: user!.uuid, data: { (userData) in
            HttpController.getToken(data: { (token) in
                HttpController.get("user/getBindingCard", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        self.bankcardList = []
                        var bankcards: [BankcardModel] = []
                        let datas = dataDictionary.object(forKey: "data") as! NSArray
                        for data in datas{
                            bankcards.append(BankcardModel(data: data as! NSDictionary))
                        }
                        self.bankcardList = bankcards
                        
                        self.reloadData()
                        
                        let tableView = self.mainView.viewWithTag(TagController.rechargeTags.bankcardTable) as? UITableView
                        if(self.bankcardList.count < 5){
                            tableView?.frame.size = CGSize.init(width: self.bankcardSelectView.frame.width, height: CGFloat((self.bankcardList.count + 1) * 60 + 50) * screenScale)
                        }else{
                            tableView?.frame.size = CGSize.init(width: self.bankcardSelectView.frame.width, height: CGFloat(5 * 60 + 50) * screenScale)
                        }
                        tableView?.reloadData()
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
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func reloadData(){
        let selectedBankIcon = mainView.viewWithTag(TagController.rechargeTags.selectedBankIcon) as! UIImageView
        let selectedBankcardName = mainView.viewWithTag(TagController.rechargeTags.selectedBankcardName) as! UILabel
        let selectedBankcardArrow = mainView.viewWithTag(TagController.rechargeTags.selectedBankcardArrow) as! UIImageView
        let balanceLabel = mainView.viewWithTag(TagController.rechargeTags.balanceLabel) as! UILabel
        if(bankcardList.count > 0){
            let data = bankcardList[selectedBankcard]
            
            SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + data.iconColor), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                if result{
                    selectedBankIcon.image = SDImage
                }
            }
            selectedBankcardName.text = "\(data.shortName)储蓄卡(\(data.bankcard))"
            selectedBankcardArrow.isHidden = false
        }else{
            selectedBankIcon.image = UIImage(named: "my_withdraw_add")
            selectedBankcardName.text = "添加银行卡"
            selectedBankcardArrow.isHidden = true
        }
        
        balanceLabel.attributedText = Utils.getColorNumString(string: "账户余额 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: balanceLabel.font.pointSize))
    }
    
    @objc func chooseBankcard(_ sender: UIButton){
        hideKeyboard()
        if(bankcardList.count > 0){
            bankcardSelectShow()
        }else{
            if(user!.realnameAuthFlag){
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "bankcardBindViewController") as! BankcardBindViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    @objc func bankcardSelectShow(){
        let tableView = mainView.viewWithTag(TagController.rechargeTags.bankcardTable) as! UITableView
        
        tableView.reloadData()
        bankcardSelectView.isHidden = false
        UIView.animate(withDuration: 0.5, animations: {
            tableView.frame.origin = CGPoint(x: tableView.frame.origin.x, y: self.bankcardSelectView.frame.height - tableView.frame.height)
        })
    }
    
    @objc func bankcardSelectHide(){
        let tableView = mainView.viewWithTag(TagController.rechargeTags.bankcardTable) as! UITableView
        
        UIView.animate(withDuration: 0.2, animations: {
            tableView.frame.origin = CGPoint(x: tableView.frame.origin.x, y: self.bankcardSelectView.frame.height)
        }, completion: { (result) in
            self.bankcardSelectView.isHidden = true
        })
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(!user!.realnameAuthFlag){
            AlertView(title: "请先实名认证").showByTime(time: 2)
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else{
            if(checkSubmit()){
                let money = (mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField).text!
                
                let loadingView = HttpController.showLoading(viewController: self)
                HttpController.getToken(data: { (token) in
                    HttpController.post("pay/rechargeByChanpay", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "price": EncodingUtil.getBase64(String(Double(money)! * 100)) , "bankcard": self.bankcardList[self.selectedBankcard].uuid, "type": "send"]), data: { (data) in
                        let dataDictionary = data as! NSDictionary
                        let status = dataDictionary.object(forKey: "status") as! String
                        if(status == "SUCCESS"){
                            self.confirmCodeView.isHidden = false
                            self.confirmCodeView.codeButton.startTimer()
                            AlertView(title: "验证码发送成功！").showByTime(time: 2)
                            self.orderNum = String.valueOf(any: dataDictionary.object(forKey: "data"))
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
            }
        }
    }
    
    func checkSubmit() -> Bool{
        let moneyInput = mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField
        
        removeError()
        
        if(bankcardList.count <= selectedBankcard){
            setError(string: "请先选择银行卡")
            return false
        }
        
        if(moneyInput.text! == ""){
            setError(string: "请输入充值金额")
            return false
        }
        
        if(!Utils.checkMoney(money: moneyInput.text!)){
            setError(string: "请输入正确的充值金额")
            return false
        }
        
        if(Double(moneyInput.text!)! > 50000){
            setError(string: "单笔充值最高金额5万元，请重新输入")
            return false
        }
        
        return true
    }
    
    @objc func confirmSendCode(_ sender: CodeSendButton){
        let money = (mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField).text!
        
        hideKeyboard()
        sender.startTimer()
        
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.post("pay/rechargeByChanpay", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "price": EncodingUtil.getBase64(String(Double(money)! * 100)) , "bankcard": self.bankcardList[self.selectedBankcard].uuid, "type": "send"]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    self.orderNum = String.valueOf(any: dataDictionary.object(forKey: "data"))
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
    
    @objc func confirmSure(_ sender: UIButton){
        hideKeyboard()
        if(checkConfirmSure()){
            let money = (mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField).text!
            let code = confirmCodeView.codeInput.text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("pay/rechargeByChanpay", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "price": EncodingUtil.getBase64(String(Double(money)! * 100)) , "bankcard": self.bankcardList[self.selectedBankcard].uuid, "type": "check", "code": EncodingUtil.getBase64(code), "orderNum": self.orderNum]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        AlertView(title: "充值成功").showByTime(time: 2)
                        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                            self.navigationController?.popViewController(animated: true)
                        }
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
    
    func checkConfirmSure() -> Bool{
        if(confirmCodeView.codeInput.text == ""){
            AlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkCode(code: confirmCodeView.codeInput.text!)){
            AlertView(title: "请输入正确的验证码").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func removeError(){
        let alertLabel = mainView.viewWithTag(TagController.rechargeTags.alertLabel) as! UILabel
        
        alertView.isHidden = true
        submitButton.frame.origin = CGPoint(x: submitButton.frame.origin.x, y: alertView.frame.origin.y + alertView.frame.height + 20 * screenScale)
        alertLabel.text = ""
    }
    
    func setError(string :String){
        let alertLabel = mainView.viewWithTag(TagController.rechargeTags.alertLabel) as! UILabel
        
        alertView.isHidden = false
        submitButton.frame.origin = CGPoint(x: submitButton.frame.origin.x, y: alertView.frame.origin.y + alertView.frame.height + 20 * screenScale)
        alertLabel.text = string
        AlertView(title: string).showByTime(time: 2)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(bankcardList.count > 0){
            return 60 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50 * screenScale
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(bankcardList.count > 0){
            return bankcardList.count + 1
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
        header.backgroundColor = UIColor.white
        
        let title = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: header.frame.height))
        title.text = "选择充值银行卡"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
        title.textAlignment = NSTextAlignment.center
        header.addSubview(title)
        
        let closeButton = UIButton(frame: CGRect(x: header.frame.width - (18 + 10) * screenScale, y: (header.frame.height - 18 * screenScale)/2, width: 18 * screenScale, height: 18 * screenScale))
        closeButton.setImage(UIImage(named: "common_close"), for: UIControlState.normal)
        closeButton.addTarget(self, action: #selector(bankcardSelectHide), for: UIControlEvents.touchUpInside)
        header.addSubview(closeButton)
        
        return header
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(bankcardList.count > 0){
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 60 * screenScale))
            
            let topLine = CALayer()
            topLine.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: 1)
            topLine.backgroundColor = UIColor.backgroundGray().cgColor
            view.layer.addSublayer(topLine)
            
            if(indexPath.row == bankcardList.count){
                let icon = UIImageView(frame: CGRect(x: 12 * screenScale, y: (view.frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
                icon.image = UIImage(named: "my_withdraw_add")
                view.addSubview(icon)
                
                let enterIcon = UIImageView(frame: CGRect(x: view.frame.width - (9 + 10) * screenScale, y: (view.frame.height - 14 * screenScale)/2, width: 9 * screenScale, height: 14 * screenScale))
                enterIcon.image = UIImage(named: "common_enter")
                view.addSubview(enterIcon)
                
                let title = UILabel(frame: CGRect(x: icon.frame.origin.x + icon.frame.width + 7 * screenScale, y: 0, width: enterIcon.frame.origin.x - (icon.frame.origin.x + icon.frame.width + 7 * screenScale), height: view.frame.height))
                title.text = "增加银行卡"
                title.textColor = UIColor.fontBlack()
                title.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
                view.addSubview(title)
                
                cell.addSubview(view)
            }else{
                let data = bankcardList[indexPath.row]
                
                let bankIcon = UIImageView(frame: CGRect(x: 12 * screenScale, y: (view.frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
                SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + data.iconColor), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                    if result{
                        bankIcon.image = SDImage
                    }
                }
                view.addSubview(bankIcon)
                
                let selectedIcon = UIImageView(frame: CGRect(x: view.frame.width - (18 + 10) * screenScale, y: (view.frame.height - 18 * screenScale)/2, width: 18 * screenScale, height: 18 * screenScale))
                if(indexPath.row == selectedBankcard){
                    selectedIcon.image = UIImage(named: "common_selected")
                }else{
                    selectedIcon.layer.borderWidth = 0.5
                }
                selectedIcon.layer.cornerRadius = selectedIcon.frame.width / 2
                selectedIcon.layer.borderColor = UIColor.lightGray.cgColor
                view.addSubview(selectedIcon)
                
                let bankcardName = UILabel(frame: CGRect(x: bankIcon.frame.origin.x + bankIcon.frame.width + 7 * screenScale, y: 0, width: selectedIcon.frame.origin.x - (bankIcon.frame.origin.x + bankIcon.frame.width + 7 * screenScale), height: view.frame.height))
                bankcardName.text = "\(data.shortName)(\(data.bankcard))"
                bankcardName.textColor = UIColor.fontBlack()
                bankcardName.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
                view.addSubview(bankcardName)
                
                cell.addSubview(view)
            }
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(indexPath.row == bankcardList.count){
            if(user!.realnameAuthFlag){
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "bankcardBindViewController") as! BankcardBindViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else{
            selectedBankcard = indexPath.row
            reloadData()
            bankcardSelectHide()
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        if(textField.tag == TagController.rechargeTags.inputMoney){
            submitButton.setTitle("充值", for: UIControlState.normal)
        }
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.rechargeTags.inputMoney){
            if(textField.text!.count + string.count > 10){
                return false
            }
            
            let text = String(textField.text![..<String.Index.init(encodedOffset: range.location)]) + string + String(textField.text![String.Index.init(encodedOffset: range.location + range.length)..<String.Index.init(encodedOffset: textField.text!.count)])
            
            if(Utils.checkMoney(money: text)){
                submitButton.setTitle("充值\(Utils.formatMoney(money: text))元", for: UIControlState.normal)
            }else{
                submitButton.setTitle("充值", for: UIControlState.normal)
                if(string != "" && string != "." && text != ""){
                    return false
                }
            }
        }
        return true
    }
}
