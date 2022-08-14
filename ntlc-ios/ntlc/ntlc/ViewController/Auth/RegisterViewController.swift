//
//  RegisterViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/29.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class RegisterViewController: UIViewController, UIScrollViewDelegate, UITextFieldDelegate{

    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    var messageView: UIView = UIView()
    var bottomView: UIView = UIView()
    var successView: UIView = UIView()
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        let leftItem = UIBarButtonItem()
        self.navigationItem.leftBarButtonItem = leftItem
        
        let rightItem = UIBarButtonItem(image: UIImage(named: "login_back")?.imageWithScale(2), style: UIBarButtonItemStyle.plain, target: self, action: #selector(back(_:)))
        rightItem.tintColor = UIColor.black
        self.navigationItem.rightBarButtonItem = rightItem
        
        mainView.backgroundColor = UIColor.white
        createHeaderView()
        createBottomView()
        createBodyView()
        createSubmitButton()
        createMessageView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.default, animated: false)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let phone = bodyView.viewWithTag(TagController.registerTags.inputPhone) as? UITextField
        let code = bodyView.viewWithTag(TagController.registerTags.inputCode) as? UITextField
        let pwd = bodyView.viewWithTag(TagController.registerTags.inputPwd) as? UITextField
        
        phone?.endEditing(true)
        code?.endEditing(true)
        pwd?.endEditing(true)
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 155 * screenScale))
        let imageView = UIImageView(frame: CGRect(x: (screenWidth - 68 * screenScale) / 2, y: 100 * screenScale, width: 68 * screenScale, height: 55 * screenScale))
        imageView.image = UIImage(named: "login_logo")
        headerView.addSubview(imageView)
        mainView.addSubview(headerView)
    }
    
    func createBodyView(){
        let paddingLeft: CGFloat = 24
        
        bodyView = UIView(frame: CGRect(x: 0, y: headerView.frame.origin.y + headerView.frame.height + 50 * screenScale, width: screenWidth, height: 200 * screenScale))
        
        let phoneInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        phoneInput.delegate = self
        phoneInput.keyboardType = UIKeyboardType.numberPad
        phoneInput.tag = TagController.registerTags.inputPhone
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
        codeInput.tag = TagController.registerTags.inputCode
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
        
        let pwdInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: codeInput.frame.origin.y + codeInput.frame.height + 3 * screenScale + 35 * screenScale, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        pwdInput.delegate = self
        pwdInput.keyboardType = UIKeyboardType.alphabet
        pwdInput.isSecureTextEntry = true
        pwdInput.tag = TagController.registerTags.inputPwd
        pwdInput.textColor = UIColor.fontBlack()
        pwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        pwdInput.attributedPlaceholder = NSAttributedString(string: "请输入8-20位字母数字组合密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        pwdInput.clearButtonMode = UITextFieldViewMode.always
        let pwdBottom = CALayer()
        pwdBottom.frame = CGRect(x: 0, y: pwdInput.frame.height + 1, width: pwdInput.frame.width, height: 1)
        pwdBottom.backgroundColor = UIColor.backgroundGray().cgColor
        pwdInput.layer.addSublayer(pwdBottom)
        bodyView.addSubview(pwdInput)
        
        let messageLabel = UILabel(frame: CGRect(x: paddingLeft * screenScale, y: pwdInput.frame.origin.y + pwdInput.frame.height + 13 * screenScale, width: bodyView.frame.width - 2 * paddingLeft * screenScale, height: 30 * screenScale))
        messageLabel.tag = TagController.registerTags.message
        messageLabel.isHidden = true
        messageLabel.backgroundColor = UIColor.mainGold().withAlphaComponent(0.1)
        messageLabel.text = ""
        messageLabel.textColor = UIColor.mainGold()
        messageLabel.textAlignment = NSTextAlignment.center
        messageLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bodyView.addSubview(messageLabel)
        
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("注册", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    func createMessageView(){
        messageView = UIView(frame: CGRect(x: submitButton.frame.origin.x, y: submitButton.frame.origin.y + submitButton.frame.height + 15 * screenScale, width: submitButton.frame.width, height: 14 * screenScale))
        
        let image = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 14 * screenScale, height: 14 * screenScale)))
        image.image = UIImage(named: "login_agreement")
        messageView.addSubview(image)
        
        let text = UILabel()
        text.text = "已经阅读并同意"
        text.textColor = UIColor.fontGray()
        text.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        text.sizeToFit()
        text.frame = CGRect(x: image.frame.origin.x + image.frame.width + 2 * screenScale, y: 0, width: text.frame.width, height: messageView.frame.height)
        messageView.addSubview(text)
        
        let agreement = UIButton()
        agreement.setTitle("《牛投理财用户服务协议》", for: UIControlState.normal)
        agreement.setTitleColor(UIColor(red: 84/255, green: 139/255, blue: 204/255, alpha: 1), for: UIControlState.normal)
        agreement.titleLabel?.font = text.font
        agreement.sizeToFit()
        agreement.frame = CGRect(x: text.frame.origin.x + text.frame.width, y: 0, width: agreement.frame.width, height: messageView.frame.height)
        agreement.addTarget(self, action: #selector(toPdf(_:)), for: UIControlEvents.touchUpInside)
        messageView.addSubview(agreement)
        
        mainView.addSubview(messageView)
    }
    
    func createBottomView(){
        if(isIphoneX()){
            bottomView = UIView(frame: CGRect(x: 0, y: screenHeight - 68 * screenScale, width: screenWidth, height: 48 * screenScale))
        }else{
            bottomView = UIView(frame: CGRect(x: 0, y: screenHeight - 48 * screenScale, width: screenWidth, height: 48 * screenScale))
        }
        
        let topLine = CALayer()
        topLine.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: bottomView.frame.width, height: 1))
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        let text = UILabel(frame: CGRect(x: 0, y: 12 * screenScale, width: bottomView.frame.width/2, height: 20 * screenScale))
        text.text = "已有账户？"
        text.textColor = UIColor.fontGray()
        text.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        text.textAlignment = NSTextAlignment.right
        bottomView.addSubview(text)
        
        let button = UIButton(frame: CGRect(x: bottomView.frame.width/2, y: text.frame.origin.y, width: 0, height: text.frame.height))
        button.setTitle("立即登录", for: UIControlState.normal)
        button.setTitleColor(UIColor(red: 84/255, green: 139/255, blue: 204/255, alpha: 1), for: UIControlState.normal)
        button.titleLabel?.font = text.font
        button.sizeToFit()
        button.frame.size = CGSize(width: button.frame.width, height: text.frame.height)
        button.addTarget(self, action: #selector(toLogin(_:)), for: UIControlEvents.touchUpInside)
        bottomView.addSubview(button)
        
        mainView.addSubview(bottomView)
    }
    
    func createSuccessView(){
        successView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
        successView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        
        let body = UIView(frame: CGRect(x: (successView.frame.width - 270 * screenScale) / 2, y: (successView.frame
            .height - 245 * screenScale)/7*3 , width: 270 * screenScale, height: 245 * screenScale))
        body.backgroundColor = UIColor.white
        
        let label = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: body.frame.width, height: 22 * screenScale))
        label.text = "恭喜你！注册成功！"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
        label.textAlignment = NSTextAlignment.center
        body.addSubview(label)
        
        let image = UIImageView(frame: CGRect(x: (body.frame.width - 134 * screenScale)/2, y: label.frame.origin.y + label.frame.height + 22 * screenScale, width: 134 * screenScale, height: 90 * screenScale))
        image.image = UIImage(named: "register_success")
        body.addSubview(image)
        
        let sureButton = UIButton(frame: CGRect(x: (body.frame.width - 100 * screenScale)/2, y: image.frame.origin.y + image.frame.height + 26 * screenScale, width: 100 * screenScale, height: 35 * screenScale))
        sureButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainGold()), for: UIControlState.normal)
        sureButton.setTitle("实名认证", for: UIControlState.normal)
        sureButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        sureButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        sureButton.addTarget(self, action: #selector(toCertificate(_:)), for: UIControlEvents.touchUpInside)
        body.addSubview(sureButton)
        successView.addSubview(body)
        
        let cancleButton = UIButton(frame: CGRect(x: (successView.frame.width - 26 * screenScale)/2, y: body.frame.origin.y + body.frame.height + 42 * screenScale, width: 26 * screenScale, height: 26 * screenScale))
        cancleButton.setBackgroundImage(UIImage(named: "common_close"), for: UIControlState.normal)
        cancleButton.addTarget(self, action: #selector(back(_:)), for: UIControlEvents.touchUpInside)
        successView.addSubview(cancleButton)
        
        mainView.addSubview(successView)
    }
    
    @objc func toLogin(_ sender: UIButton){
        hideKeyboard()
        self.navigationController?.popViewController(animated: false)
    }
    
    @objc func toPdf(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "pdfViewController") as! PdfViewController
        vc.titleName = "《牛投理财用户服务协议》"
        vc.urlString = "../resource/456.pdf"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toCertificate(_ sender: UIButton){
        let tabBar = self.tabBarController!
        for i in 0 ..< tabBar.childViewControllers.count{
            if(tabBar.childViewControllers[i].childViewControllers[0].classForCoder == MeViewController.classForCoder()){
                let me = tabBar.childViewControllers[2].childViewControllers[0] as! MeViewController
                
                UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                
                me.navigationController?.pushViewController(vc, animated: true)
                return
            }
        }
    }
    
    @objc func back(_ sender: UIButton){
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        self.navigationController?.popToRootViewController(animated: true)
    }
    
    @objc func sendCode(_ sender: CodeSendButton){
        if(checkCode()){
            let phone = (bodyView.viewWithTag(TagController.registerTags.inputPhone) as! UITextField).text!
            
            sender.startTimer()
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + phone + "register"
                let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                
                HttpController.get("sms/sendCode", params: NSDictionary(dictionary: ["codeType" : EncodingUtil.getBase64("register"),"phone": EncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    let messageLabel = self.bodyView.viewWithTag(TagController.registerTags.message) as! UILabel
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
            let messageLabel = bodyView.viewWithTag(TagController.registerTags.message) as! UILabel
            let phone = (bodyView.viewWithTag(TagController.registerTags.inputPhone) as! UITextField).text!
            let code = (bodyView.viewWithTag(TagController.registerTags.inputCode) as! UITextField).text!
            let pwd = (bodyView.viewWithTag(TagController.registerTags.inputPwd) as! UITextField).text!
            let loadingView = HttpController.showLoading(viewController: self)
            
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + phone + code;
                let token = EncodingUtil.getBase64(systemType + timestamp + pwd.md5 + codeString.md5)
                
                HttpController.post("login/register", params: NSDictionary(dictionary: ["token": token, "phone": EncodingUtil.getBase64(phone)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    
                    if(status == "SUCCESS"){
                        let data = dataDictionary.object(forKey: "data") as! NSDictionary
                        
                        if(String.valueOf(any: data.object(forKey: "uuid")) != ""){
                            HttpController.getUser(uuid: String.valueOf(any: data.object(forKey: "uuid")), data: { (usr) in
                                phoneNum = phone
                                LocalDataController.writeLocalData("phoneNum", data: phone as AnyObject)
                                HttpController.hideLoading(loadingView: loadingView)
                                HttpController.getUserAccount(data: { (data) in }) { (error) in }
                                self.createSuccessView()
                            }, errors: { (error) in
                                HttpController.hideLoading(loadingView: loadingView)
                                HttpController.showTimeout(viewController: self)
                            })
                        }
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        messageLabel.text = message
                        messageLabel.isHidden = false
                        HttpController.hideLoading(loadingView: loadingView)
                    }
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
        let messageLabel = bodyView.viewWithTag(TagController.registerTags.message) as! UILabel
        messageLabel.isHidden = true
        
        let phoneInput = bodyView.viewWithTag(TagController.registerTags.inputPhone) as! UITextField
        
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
        let phoneInput = bodyView.viewWithTag(TagController.registerTags.inputPhone) as! UITextField
        let codeInput = bodyView.viewWithTag(TagController.registerTags.inputCode) as! UITextField
        let pwdInput = bodyView.viewWithTag(TagController.registerTags.inputPwd) as! UITextField
        let messageLabel = bodyView.viewWithTag(TagController.registerTags.message) as! UILabel
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
        
        if(pwdInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入密码"
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
        
        if(!Utils.checkPassword(password: pwdInput.text!)){
            messageLabel.isHidden = false
            messageLabel.text = "请输入8-20位字母数字组合密码"
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.registerTags.inputPhone && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.registerTags.inputCode && textField.text!.count + string.count > 6){
            return false
        }else if(textField.tag == TagController.registerTags.inputPwd && textField.text!.count + string.count > 20){
            return false
        }
        return true
    }
}
