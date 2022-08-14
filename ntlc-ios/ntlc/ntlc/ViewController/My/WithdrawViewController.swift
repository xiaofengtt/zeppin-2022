//
//  WithdrawViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/8.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class WithdrawViewController: UIViewController, UITextFieldDelegate, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var bankcardView: UIView = UIView()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    var submitCodeView: AlertCodeView = AlertCodeView()
    var bankcardSelectView: UIView = UIView()
    
    var bankcardList: [BankcardModel] = []
    var selectedBankcard: Int = 0
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        createHeaderView()
        createBankcardView()
        createBodyView()
        createSubmitButton()
        createBankcardSelectView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        let moneyInput = mainView.viewWithTag(TagController.withdrawTags.inputMoney) as? UITextField
        moneyInput?.text = ""
        submitButton.setTitle("提现", for: UIControlState.normal)
        self.createSendCodeView()
        getData()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        submitCodeView.removeFromSuperview()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let moneyInput = mainView.viewWithTag(TagController.withdrawTags.inputMoney) as? UITextField
        
        moneyInput?.endEditing(true)
        submitCodeView.codeInput.endEditing(true)
    }
    
    func createHeaderView(){
        if(isIphoneX()){
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 224 * screenScale))
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
            background.image = UIImage(named: "my_withdraw_head_x")
            headerView.addSubview(background)
            mainView.addSubview(headerView)
        }else{
            headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 180 * screenScale))
            let background = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
            background.image = UIImage(named: "my_withdraw_head")
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
        bankIcon.tag = TagController.withdrawTags.selectedBankIcon
        bankcardView.addSubview(bankIcon)
        
        let arrowIcon = UIImageView(frame: CGRect(x: bankcardView.frame.width - (10 + 13) * screenScale, y: (bankcardView.frame.height - 9 * screenScale)/2, width: 13 * screenScale, height: 9 * screenScale))
        arrowIcon.tag = TagController.withdrawTags.selectedBankcardArrow
        arrowIcon.isHidden = true
        arrowIcon.image = UIImage(named: "common_arrow_down")
        bankcardView.addSubview(arrowIcon)
        
        let bankcardName = UILabel(frame: CGRect(x: bankIcon.frame.origin.x + bankIcon.frame.width + 6 * screenScale, y: 0, width: arrowIcon.frame.origin.x - (bankIcon.frame.origin.x + bankIcon.frame.width + 6 * screenScale), height: bankcardView.frame.height))
        bankcardName.tag = TagController.withdrawTags.selectedBankcardName
        bankcardName.textColor = UIColor.fontBlack()
        bankcardName.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        bankcardView.addSubview(bankcardName)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: bankcardView.frame.size))
        button.addTarget(self, action: #selector(chooseBankcard(_:)), for: UIControlEvents.touchUpInside)
        bankcardView.addSubview(button)
        mainView.addSubview(bankcardView)
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: bankcardView.frame.origin.y + bankcardView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 90 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let inputView = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 50 * screenScale))
        
        let moneyTitle = UILabel(frame: CGRect(x: 9 * screenScale, y: 0, width: 80 * screenScale, height: inputView.frame.height))
        moneyTitle.text = "提现金额"
        moneyTitle.textColor = UIColor.fontBlack()
        moneyTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        inputView.addSubview(moneyTitle)
        
        let moneyInput = UITextField(frame: CGRect(x: moneyTitle.frame.origin.x + moneyTitle.frame.width, y: 0, width: inputView.frame.width - (moneyTitle.frame.origin.x + moneyTitle.frame.width) - 9 * screenScale, height: inputView.frame.height))
        moneyInput.tag = TagController.withdrawTags.inputMoney
        moneyInput.delegate = self
        moneyInput.keyboardType = UIKeyboardType.decimalPad
        moneyInput.clearButtonMode = UITextFieldViewMode.whileEditing
        moneyInput.textColor = UIColor.moneyInputColor()
        moneyInput.font = UIFont.numFont(size: 22 * screenScale)
        moneyInput.attributedPlaceholder = NSAttributedString(string: "请输入提现金额", attributes: [NSAttributedStringKey.foregroundColor: UIColor.mainPlaceholder(),NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        inputView.addSubview(moneyInput)
        
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: inputView.frame.height - 1, width: bodyView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        inputView.layer.addSublayer(bottomLine)
        bodyView.addSubview(inputView)
        
        let balanceLabel = UILabel(frame: CGRect(x: 9 * screenScale, y: inputView.frame.origin.y + inputView.frame.height, width: bodyView.frame.width - 9 * 2 * screenScale, height: 40 * screenScale))
        balanceLabel.attributedText = Utils.getColorNumString(string: "当前可提现金额为 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: balanceLabel.font.pointSize))
        balanceLabel.tag = TagController.withdrawTags.balanceLabel
        balanceLabel.textColor = UIColor.fontDarkGray()
        balanceLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        bodyView.addSubview(balanceLabel)
        
        let alertView = UIView(frame: CGRect(x: 0, y: balanceLabel.frame.origin.y + balanceLabel.frame.height, width: bodyView.frame.width, height: 40 * screenScale))
        alertView.tag = TagController.withdrawTags.alertView
        alertView.isHidden = true
        
        let alertIcon = UIImageView(frame: CGRect(x: 12 * screenScale, y: 10 * screenScale, width: 12 * screenScale, height: 12 * screenScale))
        alertIcon.image = UIImage(named: "common_warn_red")
        alertView.addSubview(alertIcon)
        
        let alertLabel = UILabel(frame: CGRect(x: alertIcon.frame.origin.x + alertIcon.frame.width + 3 * screenScale, y: alertIcon.frame.origin.y, width: alertView.frame.width - (alertIcon.frame.origin.x + alertIcon.frame.width + 3 * screenScale), height: alertIcon.frame.height))
        alertLabel.tag = TagController.withdrawTags.alertLabel
        alertLabel.textColor = UIColor.mainRed()
        alertLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        alertView.addSubview(alertLabel)
        bodyView.addSubview(alertView)
        
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 36 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("提现", for: UIControlState.normal)
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
        tableView.tag = TagController.withdrawTags.bankcardTable
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
    
    func createSendCodeView(){
        submitCodeView = AlertCodeView()
        submitCodeView.codeInput.delegate = self
        submitCodeView.codeInput.tag = TagController.withdrawTags.inputCode
        submitCodeView.codeButton.addTarget(self, action: #selector(submitSendCode(_:)), for: UIControlEvents.touchUpInside)
        submitCodeView.sureButton.addTarget(self, action: #selector(submitSure(_:)), for: UIControlEvents.touchUpInside)
        UIApplication.shared.keyWindow?.addSubview(submitCodeView)
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
                        
                        let tableView = self.mainView.viewWithTag(TagController.withdrawTags.bankcardTable) as? UITableView
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
        let selectedBankIcon = mainView.viewWithTag(TagController.withdrawTags.selectedBankIcon) as! UIImageView
        let selectedBankcardName = mainView.viewWithTag(TagController.withdrawTags.selectedBankcardName) as! UILabel
        let selectedBankcardArrow = mainView.viewWithTag(TagController.withdrawTags.selectedBankcardArrow) as! UIImageView
        let inputMoney = mainView.viewWithTag(TagController.withdrawTags.inputMoney) as! UITextField
        let balanceLabel = mainView.viewWithTag(TagController.withdrawTags.balanceLabel) as! UILabel
        let alertView = mainView.viewWithTag(TagController.withdrawTags.alertView)!
        let alertLabel = mainView.viewWithTag(TagController.withdrawTags.alertLabel) as! UILabel
        
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
        
        balanceLabel.attributedText = Utils.getColorNumString(string: "当前可提现金额为 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: balanceLabel.font.pointSize))
        
        if(Double(user!.accountBalance)! <= 100.0){
            inputMoney.text = user!.accountBalance
            inputMoney.isEnabled = false
            bodyView.frame.size = CGSize(width: bodyView.frame.width, height: 130 * screenScale)
            alertView.isHidden = false
            alertLabel.text = "当前余额不足最小提现金额100元，请全部提现"
            submitButton.setTitle("提现\(Utils.formatMoney(money: user!.accountBalance))元", for: UIControlState.normal)
        }else{
            inputMoney.isEnabled = true
            bodyView.frame.size = CGSize(width: bodyView.frame.width, height: 90 * screenScale)
            alertView.isHidden = true
            alertLabel.text = ""
            submitButton.setTitle("提现", for: UIControlState.normal)
        }
        submitButton.frame.origin = CGPoint(x: submitButton.frame.origin.x, y: bodyView.frame.origin.y + bodyView.frame.height + 36 * screenScale)
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            self.submitCodeView.isHidden = false
        }
    }
    
    @objc func submitSure(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmitSure()){
            let money = (mainView.viewWithTag(TagController.withdrawTags.inputMoney) as! UITextField).text!
            let code = (submitCodeView.viewWithTag(TagController.withdrawTags.inputCode) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("pay/withdraw", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "bankcard": self.bankcardList[self.selectedBankcard].uuid, "code": EncodingUtil.getBase64(code), "price": EncodingUtil.getBase64(String(Double(money)! * 100))]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "withdrawResultViewController") as! WithdrawResultViewController
                        vc.money = money
                        vc.bankcard = self.bankcardList[self.selectedBankcard]
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
    
    @objc func submitSendCode(_ sender: CodeSendButton) { hideKeyboard()
        sender.startTimer()
        
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("sms/sendCodeById", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
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
        let moneyInput = mainView.viewWithTag(TagController.withdrawTags.inputMoney) as! UITextField
        let alertView = mainView.viewWithTag(TagController.withdrawTags.alertView)!
        let alertLabel = mainView.viewWithTag(TagController.withdrawTags.alertLabel) as! UILabel
        
        alertView.isHidden = true
        alertLabel.text = ""
        
        
        if(bankcardList.count == 0){
            setError(string: "请先选择提现的银行卡")
            return false
        }
        
        if(moneyInput.text! == ""){
            setError(string: "请输入提现金额")
            return false
        }
        
        if(!Utils.checkMoney(money: moneyInput.text!)){
            setError(string: "请输入正确的提现金额")
            return false
        }
        
        if(Double(moneyInput.text!) == 0.0){
            setError(string: "请输入正确的提现金额")
            return false
        }
        
        if(Double(user!.accountBalance)! > 100.0 && Double(moneyInput.text!)! < 100.0){
            setError(string: "最小提现金额为100元")
            return false
        }
        
        if(Double(moneyInput.text!)! > Double(user!.accountBalance)!){
            setError(string: "输入的提现金额大于账户余额")
            return false
        }
        return true
    }
    
    func setError(string :String){
        let alertView = mainView.viewWithTag(TagController.withdrawTags.alertView)!
        let alertLabel = mainView.viewWithTag(TagController.withdrawTags.alertLabel) as! UILabel
        
        alertView.isHidden = false
        alertLabel.text = string
        AlertView(title: string).showByTime(time: 2)
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
        let tableView = mainView.viewWithTag(TagController.withdrawTags.bankcardTable) as! UITableView
        
        tableView.reloadData()
        bankcardSelectView.isHidden = false
        UIView.animate(withDuration: 0.5, animations: {
            tableView.frame.origin = CGPoint(x: tableView.frame.origin.x, y: self.bankcardSelectView.frame.height - tableView.frame.height)
        })
    }

    @objc func bankcardSelectHide(){
        let tableView = mainView.viewWithTag(TagController.withdrawTags.bankcardTable) as! UITableView
        
        UIView.animate(withDuration: 0.2, animations: {
            tableView.frame.origin = CGPoint(x: tableView.frame.origin.x, y: self.bankcardSelectView.frame.height)
        }, completion: { (result) in
            self.bankcardSelectView.isHidden = true
        })
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
        title.text = "选择提现银行卡"
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
        if(textField.tag == TagController.withdrawTags.inputMoney){
            submitButton.setTitle("提现", for: UIControlState.normal)
        }
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.withdrawTags.inputMoney){
            if(textField.text!.count + string.count > 10){
                return false
            }
            
            let text = String(textField.text![..<String.Index.init(encodedOffset: range.location)]) + string + String(textField.text![String.Index.init(encodedOffset: range.location + range.length)..<String.Index.init(encodedOffset: textField.text!.count)])
            
            if(Utils.checkMoney(money: text)){
                submitButton.setTitle("提现\(Utils.formatMoney(money: text))元", for: UIControlState.normal)
            }else{
                submitButton.setTitle("提现", for: UIControlState.normal)
                if(string != "" && string != "." && text != ""){
                    return false
                }
            }
        }else if(textField.tag == TagController.withdrawTags.inputCode && textField.text!.count + string.count > 6){
            return false
        }
        return true
    }
}
