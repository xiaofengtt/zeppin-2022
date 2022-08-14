//
//  AlipayRechargeViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/7.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class AlipayRechargeViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var alipayView: UIView = UIView()
    var balanceView: UIView = UIView()
    var moneyView: UIView = UIView()
    var alertView: UIView = UIView()
    var messageLabel: UILabel = UILabel()
    var submitButton: UIButton = UIButton()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthSuccess),name: NSNotification.Name(rawValue: "alipayAuthSuccess"), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthField),name: NSNotification.Name(rawValue: "alipayAuthField"), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthTimeout),name: NSNotification.Name(rawValue: "alipayAuthTimeout"), object: nil)
        
        mainView.backgroundColor = UIColor.backgroundGray()
        createHeaderView()
        createAlipayView()
        createBalanceView()
        createMoneyView()
        createAlertView()
        createMessageLabel()
        createSubmitButton()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getData()
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
    
    func createAlipayView(){
        alipayView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 12 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 60 * screenScale))
        alipayView.backgroundColor = UIColor.white
        alipayView.layer.cornerRadius = cornerRadius * screenScale
        alipayView.addBaseShadow()
        
        let alipayIcon = UIImageView(frame: CGRect(x: 10 * screenScale, y: (alipayView.frame.height - 20 * screenScale)/2, width: 20 * screenScale, height: 20 * screenScale))
        alipayIcon.image = UIImage(named: "my_recharge_alipay")
        alipayView.addSubview(alipayIcon)
        
        let alipayTitle = UILabel(frame: CGRect(x: alipayIcon.frame.origin.x + alipayIcon.frame.width + 4 * screenScale, y: 0, width: 0, height: alipayView.frame.height))
        alipayTitle.text = "支付宝"
        alipayTitle.textColor = UIColor.fontBlack()
        alipayTitle.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        alipayTitle.sizeToFit()
        alipayTitle.frame.size = CGSize(width: alipayTitle.frame.width, height: alipayView.frame.height)
        alipayView.addSubview(alipayTitle)
        
        let enterIcon = UIImageView(frame: CGRect(x: alipayView.frame.width - (16 + 9) * screenScale, y: (alipayView.frame.height - 14 * screenScale)/2, width: 9 * screenScale, height: 14 * screenScale))
        enterIcon.image = UIImage(named: "common_enter")
        alipayView.addSubview(enterIcon)
        
        let statusLabel = UILabel()
        statusLabel.tag = TagController.rechargeTags.alipayStatusLabel
        statusLabel.text = user!.bindingAliFlag ? "已绑定" : "未绑定"
        statusLabel.textColor = user!.bindingAliFlag ? UIColor.selectedColor() : UIColor.fontGray()
        statusLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        statusLabel.sizeToFit()
        statusLabel.frame.size = CGSize(width: statusLabel.frame.width, height: alipayView.frame.height)
        statusLabel.frame.origin = CGPoint(x: enterIcon.frame.origin.x - 4 * screenScale - statusLabel.frame.width, y: 0)
        alipayView.addSubview(statusLabel)
        
        let alipayNickname = UILabel(frame: CGRect(x: alipayTitle.frame.origin.x + alipayTitle.frame.width + 2 * screenScale, y: 0, width: statusLabel.frame.origin.x - (alipayTitle.frame.origin.x + alipayTitle.frame.width + 2 * screenScale), height: alipayView.frame.height))
        alipayNickname.tag = TagController.rechargeTags.alipayNicknameLabel
        alipayNickname.text = user!.bindingAliFlag ? "（\(user!.aliNickname)）" : ""
        alipayNickname.textColor = UIColor.fontBlack()
        alipayNickname.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        alipayView.addSubview(alipayNickname)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: alipayView.frame.size))
        button.addTarget(self, action: #selector(toAlipayBind(_:)), for: UIControlEvents.touchUpInside)
        alipayView.addSubview(button)
        
        mainView.addSubview(alipayView)
    }
    
    func createBalanceView(){
        balanceView = UIView(frame: CGRect(x: 0, y: alipayView.frame.origin.y + alipayView.frame.height, width: screenWidth, height: 34 * screenScale))
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
        inputMoney.attributedPlaceholder = NSAttributedString(string: "请输入1000的整数倍，最高5万元", attributes: [NSAttributedStringKey.foregroundColor: UIColor.mainPlaceholder(),NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
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
    
    func createMessageLabel(){
        messageLabel = UILabel(frame: CGRect(x: 14 * screenScale, y: moneyView.frame.origin.y + moneyView.frame.height, width: screenWidth - 14 * 2 * screenScale, height: 40 * screenScale))
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        let string = "平台官方账号名称为北京知鹏汇仁科技有限公司"
        let attributedString = NSMutableAttributedString(string: string)
        attributedString.setAttributes([NSAttributedStringKey.foregroundColor : UIColor.mainBlue()], range: NSRange(location: string.count - "北京知鹏汇仁科技有限公司".count, length: "北京知鹏汇仁科技有限公司".count))
        messageLabel.attributedText = attributedString
        mainView.addSubview(messageLabel)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: messageLabel.frame.origin.y + messageLabel.frame.height + 20 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("充值", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    func getData(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getUser(uuid: user!.uuid, data: { (userData) in
            self.reloadData()
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func reloadData(){
        let alipayNicknameLabel = mainView.viewWithTag(TagController.rechargeTags.alipayNicknameLabel) as! UILabel
        let alipayStatusLabel = mainView.viewWithTag(TagController.rechargeTags.alipayStatusLabel) as! UILabel
        let balanceLabel = mainView.viewWithTag(TagController.rechargeTags.balanceLabel) as! UILabel
        
        alipayNicknameLabel.text = user!.bindingAliFlag ? "（\(user!.aliNickname)）" : ""
        alipayStatusLabel.text = user!.bindingAliFlag ? "已绑定" : "未绑定"
        alipayStatusLabel.textColor = user!.bindingAliFlag ? UIColor.selectedColor() : UIColor.fontGray()
        
        balanceLabel.attributedText = Utils.getColorNumString(string: "账户余额 \(user!.accountBalance) 元", color: UIColor.mainGold(), numFont: UIFont.numFont(size: balanceLabel.font.pointSize))
    }
    
    @objc func toAlipayBind(_ sender: UIButton){
        hideKeyboard()
        if(user!.realnameAuthFlag){
            hideKeyboard()
            if(user!.bindingAliFlag){
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "alipayResultViewController") as! AlipayResultViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                AlipayController.auth()
            }
        }else{
            AlertView(title: "请先实名认证").showByTime(time: 2)
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
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
        }else if(!user!.bindingAliFlag){
            AlertView(title: "请先绑定支付宝").showByTime(time: 2)
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "alipayResultViewController") as! AlipayResultViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else{
            if(checkSubmit()){
                let money = (mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField).text!
                
                let loadingView = HttpController.showLoading(viewController: self)
                HttpController.getToken(data: { (token) in
                    HttpController.get("pay/getAliQrCode", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                        let dic = data as! NSDictionary
                        let status = dic.object(forKey: "status") as! String
                        if(status == "SUCCESS"){
                            var flag = false
                            let datas = dic.object(forKey: "data") as! NSArray
                            for dataMap in datas{
                                let dataDic = dataMap as! NSDictionary
                                
                                let key = String.valueOf(any: dataDic.object(forKey: "priceCN"))
                                if(key == money + "元"){
                                    flag = true
                                    let code = String.valueOf(any: dataDic.object(forKey: "priceUrlcode"))
                                    AlipayController.pay(code: code)
                                    self.showPayAlert()
                                    break
                                }
                            }
                            
                            if(!flag){
                                self.setError(string: "请输入正确的充值金额")
                            }
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
    
    func showPayAlert(){
        let payAlert = UIAlertController(title: "温馨提示", message: "付款完成前请不要关闭此窗口，完成付款后请根据您的情况点击下面按钮。", preferredStyle: UIAlertControllerStyle.alert)
        let cancleAction = UIAlertAction(title: "付款遇到问题", style: UIAlertActionStyle.cancel){ (cancleAction) in
            self.getData()
        }
        cancleAction.setCancleStyle()
        payAlert.addAction(cancleAction)
        
        let sureAction = UIAlertAction(title: "已完成付款", style: UIAlertActionStyle.default) { (sureAction) in
            self.navigationController?.popViewController(animated: true)
        }
        
        sureAction.setSureStyle()
        payAlert.addAction(sureAction)
        self.present(payAlert, animated: true, completion: nil)
    }
    
    func checkSubmit() -> Bool{
        let moneyInput = mainView.viewWithTag(TagController.rechargeTags.inputMoney) as! UITextField
        
        removeError()
        
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
        
        if(Double(moneyInput.text!)!.truncatingRemainder(dividingBy: (1000)) != 0){
            setError(string: "请输入1000元的整数倍")
            return false
        }
        
        return true
    }
    
    func removeError(){
        let alertLabel = mainView.viewWithTag(TagController.rechargeTags.alertLabel) as! UILabel
        
        alertView.isHidden = true
        messageLabel.frame.origin = CGPoint(x: messageLabel.frame.origin.x, y: moneyView.frame.origin.y + moneyView.frame.height)
        submitButton.frame.origin = CGPoint(x: submitButton.frame.origin.x, y: messageLabel.frame.origin.y + messageLabel.frame.height + 20 * screenScale)
        alertLabel.text = ""
    }
    
    func setError(string :String){
        let alertLabel = mainView.viewWithTag(TagController.rechargeTags.alertLabel) as! UILabel
        
        alertView.isHidden = false
        messageLabel.frame.origin = CGPoint(x: messageLabel.frame.origin.x, y: alertView.frame.origin.y + alertView.frame.height)
        submitButton.frame.origin = CGPoint(x: submitButton.frame.origin.x, y: messageLabel.frame.origin.y + messageLabel.frame.height + 20 * screenScale)
        alertLabel.text = string
        AlertView(title: string).showByTime(time: 2)
    }
    
    @objc func alipayAuthSuccess(){
        getData()
    }
    
    @objc func alipayAuthField(){
        AlertView(title: "授权失败").showByTime(time: 2)
    }
    
    @objc func alipayAuthTimeout(){
        HttpController.showTimeout(viewController: self)
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
