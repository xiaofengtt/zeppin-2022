//
//  ResetConfirmViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/30.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ResetConfirmViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.white
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createBodyView()
        createSubmitButton()
        super.viewDidLoad()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let phone = bodyView.viewWithTag(TagController.resetPwdTags.inputPhone) as? UITextField
        let code = bodyView.viewWithTag(TagController.resetPwdTags.inputCode) as? UITextField
        
        phone?.endEditing(true)
        code?.endEditing(true)
    }
    
    func createBodyView(){
        let paddingLeft: CGFloat = 24
        
        bodyView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize.init(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
        let phoneInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: 40 * screenScale, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        phoneInput.delegate = self
        phoneInput.keyboardType = UIKeyboardType.numberPad
        phoneInput.tag = TagController.resetPwdTags.inputPhone
        phoneInput.textColor = UIColor.fontBlack()
        phoneInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        phoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        phoneInput.clearButtonMode = UITextFieldViewMode.always
        let phoneBottom = CALayer()
        phoneBottom.frame = CGRect(x: 0, y: phoneInput.frame.height + 1, width: phoneInput.frame.width, height: 1)
        phoneBottom.backgroundColor = UIColor.backgroundGray().cgColor
        phoneInput.layer.addSublayer(phoneBottom)
        bodyView.addSubview(phoneInput)
        
        let codeInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: phoneInput.frame.origin.y + phoneInput.frame.height + 3 * screenScale + 35 * screenScale, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        codeInput.delegate = self
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.tag = TagController.resetPwdTags.inputCode
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: "请输入验证码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codeInput.clearButtonMode = UITextFieldViewMode.never
        let codeBottom = CALayer()
        codeBottom.frame = CGRect(x: 0, y: codeInput.frame.height + 1, width: codeInput.frame.width, height: 1)
        codeBottom.backgroundColor = UIColor.backgroundGray().cgColor
        codeInput.layer.addSublayer(codeBottom)
        bodyView.addSubview(codeInput)
        let codeButton = CodeSendButton(frame: CGRect(x: bodyView.frame.width - 105 * screenScale, y: codeInput.frame.origin.y + codeInput.frame.height - 34 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
        codeButton.addTarget(self, action: #selector(sendCode(_:)), for: UIControlEvents.touchUpInside)
        bodyView.addSubview(codeButton)
        
        let confirmMessage = UILabel(frame: CGRect(x: paddingLeft * screenScale, y: codeInput.frame.origin.y + codeInput.frame.height + 13 * screenScale, width: bodyView.frame.width - 2 * paddingLeft * screenScale, height: 30 * screenScale))
        confirmMessage.tag = TagController.resetPwdTags.confirmMessage
        confirmMessage.isHidden = true
        confirmMessage.backgroundColor = UIColor.mainGold().withAlphaComponent(0.1)
        confirmMessage.text = ""
        confirmMessage.textColor = UIColor.mainGold()
        confirmMessage.textAlignment = NSTextAlignment.center
        confirmMessage.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bodyView.addSubview(confirmMessage)
        
        bodyView.frame.size = CGSize(width: bodyView.frame.width, height: confirmMessage.frame.origin.y + confirmMessage.frame.height)
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 13 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("下一步", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }

    @objc func sendCode(_ sender: CodeSendButton){
        if(checkCode()){
            let phone = (bodyView.viewWithTag(TagController.resetPwdTags.inputPhone) as! UITextField).text!
            
            sender.startTimer()
            let loadingView = HttpController.showLoading(viewController: self)
            
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + phone + "resetpwd"
                let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                
                HttpController.get("sms/sendCode", params: NSDictionary(dictionary: ["codeType" : EncodingUtil.getBase64("resetpwd"),"phone": EncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    let messageLabel = self.bodyView.viewWithTag(TagController.resetPwdTags.confirmMessage) as! UILabel
                    if(status == "SUCCESS"){
                        messageLabel.text = "短信验证码已发送至\(phone)，请注意查收。"
                        messageLabel.isHidden = false
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        messageLabel.text = message
                        messageLabel.isHidden = false
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
    }
    
    @objc func submit(_ sender: UIButton){
        if(checkSubmit()){
            let messageLabel = bodyView.viewWithTag(TagController.resetPwdTags.confirmMessage) as! UILabel
            let phone = (bodyView.viewWithTag(TagController.resetPwdTags.inputPhone) as! UITextField).text!
            let code = (bodyView.viewWithTag(TagController.resetPwdTags.inputCode) as! UITextField).text!
            let loadingView = HttpController.showLoading(viewController: self)
            
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + phone + code;
                let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                
                HttpController.post("login/resetCheck", params: NSDictionary(dictionary: ["token": token, "phone": EncodingUtil.getBase64(phone)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    
                    if(status == "SUCCESS"){
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "resetPwdViewController") as! ResetPwdViewController
                        vc.phone = phone
                        vc.code = code
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        messageLabel.text = message
                        messageLabel.isHidden = false
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
    
    func checkCode() -> Bool{
        let messageLabel = bodyView.viewWithTag(TagController.resetPwdTags.confirmMessage) as! UILabel
        messageLabel.isHidden = true
        
        let phoneInput = bodyView.viewWithTag(TagController.resetPwdTags.inputPhone) as! UITextField
        
        if(phoneInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入手机号码"
            return false
        }
        
        if(!Utils.checkPhone(phone: phoneInput.text!)){
            messageLabel.isHidden = false
            messageLabel.text = "请输入正确的手机号码"
            return false
        }
        return true
    }
    
    func checkSubmit() -> Bool{
        let phoneInput = bodyView.viewWithTag(TagController.resetPwdTags.inputPhone) as! UITextField
        let codeInput = bodyView.viewWithTag(TagController.resetPwdTags.inputCode) as! UITextField
        let messageLabel = bodyView.viewWithTag(TagController.resetPwdTags.confirmMessage) as! UILabel
        messageLabel.isHidden = true
        
        if(phoneInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入手机号码"
            return false
        }
        
        if(codeInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入验证码"
            return false
        }
        
        if(!Utils.checkPhone(phone: phoneInput.text!)){
            messageLabel.isHidden = false
            messageLabel.text = "请输入正确的手机号码"
            return false
        }
        
        if(!Utils.checkCode(code: codeInput.text!)){
            messageLabel.isHidden = false
            messageLabel.text = "请输入正确的验证码"
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.resetPwdTags.inputPhone && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.resetPwdTags.inputCode && textField.text!.count + string.count > 6){
            return false
        }
        return true
    }
}
