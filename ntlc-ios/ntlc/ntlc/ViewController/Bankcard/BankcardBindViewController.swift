//
//  BankcardBindViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BankcardBindViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!

    var navigationBackground: NavigationBackground = NavigationBackground()
    var topView: UIView = UIView()
    var bodyView: UIView = UIView()
    var phoneView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    var confirmCodeView: AlertCodeView = AlertCodeView()
    
    var selectedBank: BankModel? = nil
    var orderNum: String = ""
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createTopView()
        createBodyView()
        createPhoneView()
        createSubmitButton()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        reloadBank()
        createCodeView()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        confirmCodeView.removeFromSuperview()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let bankcardInput = bodyView.viewWithTag(TagController.bankcardTags.inputBankcard) as? UITextField
        let phoneInput = phoneView.viewWithTag(TagController.bankcardTags.inputPhone) as? UITextField
        
        bankcardInput?.endEditing(true)
        phoneInput?.endEditing(true)
        confirmCodeView.codeInput.endEditing(true)
    }
    
    func createTopView(){
        topView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 50 * screenScale))
        topView.backgroundColor = UIColor.white
        topView.layer.cornerRadius = cornerRadius * screenScale
        topView.addBaseShadow()
        
        let image = UIImageView(frame: CGRect(x: 8 * screenScale, y: (topView.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        image.image = UIImage(named: "common_notice")
        topView.addSubview(image)
        
        let label = UILabel(frame: CGRect(x: image.frame.origin.x + image.frame.width + 2 * screenScale, y: (topView.frame.height - 20 * screenScale)/2, width: topView.frame.width - (image.frame.origin.x + image.frame.width + 2 * screenScale), height: 20 * screenScale))
        label.text = "请绑定持卡人本人银行卡"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        topView.addSubview(label)
        mainView.addSubview(topView)
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 150 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let nameView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: bodyView.frame.width, height: bodyView.frame.height/3)))
        let nameTitleLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 70 * screenScale, height: nameView.frame.height))
        nameTitleLabel.text = "持卡人"
        nameTitleLabel.textColor = UIColor.fontBlack()
        nameTitleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        nameView.addSubview(nameTitleLabel)
        let nameTextLabel = UILabel(frame: CGRect(x: nameTitleLabel.frame.origin.x + nameTitleLabel.frame.width, y: 0, width: nameView.frame.width - (nameTitleLabel.frame.origin.x + nameTitleLabel.frame.width) - 8 * screenScale, height: nameView.frame.height))
        nameTextLabel.text = user?.realnameFull
        nameTextLabel.textColor = UIColor.fontBlack()
        nameTextLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        nameView.addSubview(nameTextLabel)
        bodyView.addSubview(nameView)
        
        let bankcardView = UIView(frame: CGRect(x: 0, y: nameView.frame.origin.y + nameView.frame.height, width: bodyView.frame.width, height: nameView.frame.height))
        let bankcardTopLine = CALayer()
        bankcardTopLine.frame = CGRect(x: 0, y: 0, width: bankcardView.frame.width, height: 1)
        bankcardTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        bankcardView.layer.addSublayer(bankcardTopLine)
        let bankcardTitleLabel = UILabel(frame: nameTitleLabel.frame)
        bankcardTitleLabel.text = "银行卡号"
        bankcardTitleLabel.textColor = nameTitleLabel.textColor
        bankcardTitleLabel.font = nameTitleLabel.font
        bankcardView.addSubview(bankcardTitleLabel)
        let bankcardInput = UITextField(frame: nameTextLabel.frame)
        bankcardInput.delegate = self
        bankcardInput.tag = TagController.bankcardTags.inputBankcard
        bankcardInput.keyboardType = UIKeyboardType.numberPad
        bankcardInput.clearButtonMode = UITextFieldViewMode.always
        bankcardInput.textColor = nameTextLabel.textColor
        bankcardInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        bankcardInput.attributedPlaceholder = NSAttributedString(string: "请输入银行卡号", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        bankcardView.addSubview(bankcardInput)
        bodyView.addSubview(bankcardView)
        
        let bankView = UIView(frame: CGRect(x: 0, y: bankcardView.frame.origin.y + bankcardView.frame.height, width: bodyView.frame.width, height: nameView.frame.height))
        let bankTopLine = CALayer()
        bankTopLine.frame = CGRect(x: 0, y: 0, width: bankView.frame.width, height: 1)
        bankTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        bankView.layer.addSublayer(bankTopLine)
        let bankTitleLabel = UILabel(frame: nameTitleLabel.frame)
        bankTitleLabel.text = "选择银行"
        bankTitleLabel.textColor = nameTitleLabel.textColor
        bankTitleLabel.font = nameTitleLabel.font
        bankView.addSubview(bankTitleLabel)
        let bankEnterImage = UIImageView(frame: CGRect(x: bankView.frame.width - (8 + 10) * screenScale, y: (bankView.frame.height - 15 * screenScale) / 2, width: 10 * screenScale, height: 15 * screenScale))
        bankEnterImage.image = UIImage(named: "common_enter")
        bankView.addSubview(bankEnterImage)
        
        let bankName = UILabel()
        bankName.isHidden = true
        bankName.tag = TagController.bankcardTags.bankName
        bankName.textColor = UIColor.fontBlack()
        bankName.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        bankView.addSubview(bankName)
        
        let bankImage = UIImageView()
        bankImage.isHidden = true
        bankImage.tag = TagController.bankcardTags.bankImage
        bankView.addSubview(bankImage)
        
        let bankButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: bankView.frame.size))
        bankButton.addTarget(self, action: #selector(toBankChoose(_:)), for: UIControlEvents.touchUpInside)
        bankView.addSubview(bankButton)
        bodyView.addSubview(bankView)
        
        mainView.addSubview(bodyView)
    }
    
    func createPhoneView(){
        phoneView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: 80 * screenScale))
        
        let textLabel: UILabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: phoneView.frame.width - 8 * 2 * screenScale, height: 30 * screenScale))
        textLabel.text = "请输入该银行卡预留手机号："
        textLabel.textColor = UIColor.fontDarkGray()
        textLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        phoneView.addSubview(textLabel)
        
        let phoneRow = UIView(frame: CGRect(x: 0, y: textLabel.frame.origin.y + textLabel.frame.height, width: phoneView.frame.width, height: phoneView.frame.height - (textLabel.frame.origin.y + textLabel.frame.height)))
        phoneRow.backgroundColor = UIColor.white
        phoneRow.layer.cornerRadius = cornerRadius * screenScale
        phoneRow.addBaseShadow()
        
        let titleLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 70 * screenScale, height: phoneRow.frame.height))
        titleLabel.text = "手机号"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        phoneRow.addSubview(titleLabel)
        let phoneInput = UITextField(frame: CGRect(x: titleLabel.frame.origin.x + titleLabel.frame.width, y: 0, width: phoneRow.frame.width - (titleLabel.frame.origin.x + titleLabel.frame.width) - 8 * screenScale, height: phoneRow.frame.height))
        phoneInput.delegate = self
        phoneInput.tag = TagController.bankcardTags.inputPhone
        phoneInput.text = phoneNum
        phoneInput.keyboardType = UIKeyboardType.numberPad
        phoneInput.clearButtonMode = UITextFieldViewMode.always
        phoneInput.textColor = UIColor.fontBlack()
        phoneInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        phoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        phoneRow.addSubview(phoneInput)
        phoneView.addSubview(phoneRow)
        mainView.addSubview(phoneView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: phoneView.frame.origin.y + phoneView.frame.height + 37 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("下一步", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    func createCodeView(){
        confirmCodeView = AlertCodeView()
        confirmCodeView.codeInput.delegate = self
        confirmCodeView.codeInput.tag = TagController.bankcardTags.inputCode
        confirmCodeView.codeButton.addTarget(self, action: #selector(confirmSendCode(_:)), for: UIControlEvents.touchUpInside)
        confirmCodeView.sureButton.addTarget(self, action: #selector(confirmSure(_:)), for: UIControlEvents.touchUpInside)
        UIApplication.shared.keyWindow?.addSubview(confirmCodeView)
    }
    
    func reloadBank(){
        let bankName = bodyView.viewWithTag(TagController.bankcardTags.bankName) as! UILabel
        let bankImage = bodyView.viewWithTag(TagController.bankcardTags.bankImage) as! UIImageView
        
        if(selectedBank != nil && selectedBank?.uuid != ""){
            bankName.isHidden = false
            bankImage.isHidden = false
            
            bankName.text = selectedBank!.shortName
            bankName.sizeToFit()
            bankName.frame.size = CGSize(width: bankName.frame.width, height: bodyView.frame.height/3)
            bankName.frame.origin = CGPoint(x: bodyView.frame.width - (8 + 10) * screenScale - bankName.frame.width - 3 * screenScale, y: 0)
            
            bankImage.frame = CGRect(x: bankName.frame.origin.x - (22 + 2) * screenScale, y: (bodyView.frame.height/3 - 22 * screenScale)/2, width: 22 * screenScale, height: 22 * screenScale)
            SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + selectedBank!.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                if result{
                    bankImage.image = SDImage
                }
            }
        }else{
            bankName.isHidden = true
            bankImage.isHidden = true
        }
    }
    
    @objc func toBankChoose(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "bankChooseViewController") as! BankChooseViewController
        vc.selectedBank = selectedBank
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let bankcard = (bodyView.viewWithTag(TagController.bankcardTags.inputBankcard) as! UITextField).text!.replacingOccurrences(of: " ", with: "")
            let phone = (phoneView.viewWithTag(TagController.bankcardTags.inputPhone) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("user/bindingSendCode", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "phone": EncodingUtil.getBase64(phone), "bankcard": EncodingUtil.getBase64(bankcard), "cardholder": EncodingUtil.getBase64(user!.realnameFull),"bank": self.selectedBank!.uuid]), data: { (data) in
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
    
    @objc func confirmSendCode(_ sender: CodeSendButton){
        let bankcard = (bodyView.viewWithTag(TagController.bankcardTags.inputBankcard) as! UITextField).text!.replacingOccurrences(of: " ", with: "")
        let phone = (phoneView.viewWithTag(TagController.bankcardTags.inputPhone) as! UITextField).text!
        
        hideKeyboard()
        sender.startTimer()
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.post("user/bindingSendCode", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "phone": EncodingUtil.getBase64(phone), "bankcard": EncodingUtil.getBase64(bankcard), "cardholder": EncodingUtil.getBase64(user!.realnameFull),"bank": self.selectedBank!.uuid]), data: { (data) in
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
            let bankcard = (bodyView.viewWithTag(TagController.bankcardTags.inputBankcard) as! UITextField).text!.replacingOccurrences(of: " ", with: "")
            let phone = (phoneView.viewWithTag(TagController.bankcardTags.inputPhone) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("user/bindingCard", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "phone": EncodingUtil.getBase64(phone), "bankcard": EncodingUtil.getBase64(bankcard), "cardholder": EncodingUtil.getBase64(user!.realnameFull),"bank": self.selectedBank!.uuid, "code": EncodingUtil.getBase64(self.confirmCodeView.codeInput.text!), "orderNum": self.orderNum]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        AlertView(title: "绑卡成功").showByTime(time: 2)
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
    
    func checkSubmit() -> Bool{
        let bankcardInput = bodyView.viewWithTag(TagController.bankcardTags.inputBankcard) as! UITextField
        let phoneInput = phoneView.viewWithTag(TagController.bankcardTags.inputPhone) as! UITextField
        
        if(bankcardInput.text! == ""){
            AlertView(title: "请输入银行卡号").showByTime(time: 2)
            return false
        }
        
        
        if(selectedBank == nil || selectedBank?.uuid == ""){
            AlertView(title: "请选择所属银行").showByTime(time: 2)
            return false
        }
        
        if(phoneInput.text! == ""){
            AlertView(title: "请输入手机号码").showByTime(time: 2)
            return false
        }
        
        if(bankcardInput.text!.count < 10){
            AlertView(title: "请输入正确的银行卡号").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkPhone(phone: phoneInput.text!)){
            AlertView(title: "请输入正确的手机号码").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.bankcardTags.inputBankcard){
            var text = String(textField.text![..<String.Index.init(encodedOffset: range.location)]) + string + String(textField.text![String.Index.init(encodedOffset: range.location + range.length)..<String.Index.init(encodedOffset: textField.text!.count)])
            text = text.replacingOccurrences(of: " ", with: "")
            
            if(text.count > 25){
                return false
            }
            var newText = ""
            for i in 0 ..< text.count{
                if(i > 0 && i % 4 == 0){
                    newText.append(" \(text[String.Index.init(encodedOffset: i)..<String.Index.init(encodedOffset: i+1)])")
                }else{
                    newText.append(String(text[String.Index.init(encodedOffset: i)..<String.Index.init(encodedOffset: i+1)]))
                }
            }
            textField.text = newText
            return false
        }else if(textField.tag == TagController.bankcardTags.inputPhone && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.bankcardTags.inputCode && textField.text!.count + string.count > 6){
            return false
        }else{
            return true
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if(textField.tag == TagController.bankcardTags.inputBankcard && textField.text!.count > 10){
            
            HttpController.getToken(data: { (token) in
                HttpController.get("user/bindingCheckCard", params: NSDictionary(dictionary: ["token": token, "bankcard": EncodingUtil.getBase64(textField.text!.replacingOccurrences(of: " ", with: ""))]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        let data = dataDictionary.object(forKey: "data") as! NSDictionary
                        let errorBank = BankModel(data: data)
                        let successBank = BankModel()
                        successBank.uuid = errorBank.uuid
                        successBank.name = errorBank.name
                        successBank.shortName = errorBank.shortName
                        successBank.iconColorUrl = errorBank.iconColor
                        self.selectedBank = successBank
                        
                        self.reloadBank()
                    }
                }, errors: { (error) in })
            }, errors: { (error) in })
        }
    }
}
