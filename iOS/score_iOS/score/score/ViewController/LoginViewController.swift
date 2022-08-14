//
//  LoginViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class LoginViewController: UIViewController, UITextFieldDelegate {
    
    var headView: UIView = UIView()
    var codeView: UIView = UIView()
    
    let paddingLeft: CGFloat = 35 * screenScale
    let submitUncheckedColor = UIColor(red: 255/255, green: 216/255, blue: 120/255, alpha: 1)
    var kapCode: String = ""
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        self.view.addSubview(headView)
        self.view.addSubview(codeView)
        
        createHead()
        createBody()
        
        getKapCode()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func createHead() {
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.origin.y + navigationFrame.size.height + 140 * screenScale))
        
        let logoView = UIImageView(frame: CGRect(x: screenWidth/2 - screenWidth/12, y: navigationFrame.origin.y + 45 * screenScale, width: screenWidth/6, height: screenWidth/6))
        logoView.image = UIImage(named: "login_logo")
        logoView.layer.cornerRadius = 10 * screenScale
        logoView.clipsToBounds = true
        headView.addSubview(logoView)
        
        let textView = UIImageView(frame: CGRect(x: screenWidth / 2 - 50 * screenScale, y: logoView.frame.origin.y + logoView.frame.height + 20 * screenScale, width: 100 * screenScale, height: 24 * screenScale))
        textView.image = UIImage(named: "common_logo")
        headView.addSubview(textView)
    }
    
    func createBody() {
        codeView.frame = CGRect(x: paddingLeft, y: headView.frame.origin.y + headView.frame.height + 25 * screenScale, width: screenWidth - paddingLeft * 2, height: 400 * screenScale)
        let codePhoneInput = UITextField(frame: CGRect(x: 0, y: 0, width: codeView.frame.width, height: 40 * screenScale))
        codePhoneInput.delegate = self
        codePhoneInput.keyboardType = UIKeyboardType.numberPad
        codePhoneInput.tag = TagController.LoginTags.phoneInput
        codePhoneInput.textColor = UIColor.fontBlack()
        codePhoneInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        codePhoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号码", attributes: [NSAttributedString.Key.foregroundColor : UIColor.mainPlaceholder(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codePhoneInput.clearButtonMode = UITextField.ViewMode.always
        let codePhoneBottom = CALayer()
        codePhoneBottom.frame = CGRect(x: 0, y: codePhoneInput.frame.height + 1, width: codePhoneInput.frame.width, height: 1)
        codePhoneBottom.backgroundColor = UIColor.backgroundGray().cgColor
        codePhoneInput.layer.addSublayer(codePhoneBottom)
        codeView.addSubview(codePhoneInput)
        
        let codeKapInput = UITextField(frame: CGRect(x: 0, y: codePhoneInput.frame.origin.y + codePhoneInput.frame.height + 3 * screenScale + 10 * screenScale, width: codeView.frame.width, height: codePhoneInput.frame.height))
        codeKapInput.delegate = self
        codeKapInput.keyboardType = UIKeyboardType.numberPad
        codeKapInput.tag = TagController.LoginTags.kapInput
        codeKapInput.textColor = codePhoneInput.textColor
        codeKapInput.font = codePhoneInput.font
        codeKapInput.attributedPlaceholder = NSAttributedString(string: "请输入图形验证码", attributes: [NSAttributedString.Key.foregroundColor : UIColor.mainPlaceholder(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codeKapInput.clearButtonMode = UITextField.ViewMode.never
        let codeKapBottom = CALayer()
        codeKapBottom.frame = CGRect(x: 0, y: codeKapInput.frame.height + 1, width: codeKapInput.frame.width, height: 1)
        codeKapBottom.backgroundColor = codePhoneBottom.backgroundColor
        codeKapInput.layer.addSublayer(codeKapBottom)
        codeView.addSubview(codeKapInput)
        
        let codeKapView = KaptchaCodeView(frame: CGRect(x: codeView.frame.width - 80 * screenScale, y: codeKapInput.frame.origin.y, width: 80 * screenScale, height: codeKapInput.frame.height - 5 * screenScale))
        codeKapView.tag = TagController.LoginTags.kapImageView
        codeKapView.layer.cornerRadius = 4 * screenScale
        codeKapView.clipsToBounds = true
        codeView.addSubview(codeKapView)
        
        let codeKapButton = UIButton(frame: codeKapView.frame)
        codeKapButton.addTarget(self, action: #selector(newKap(_:)), for: UIControl.Event.touchUpInside)
        codeView.addSubview(codeKapButton)
        
        let codeCodeInput = UITextField(frame: CGRect(x: 0, y: codeKapInput.frame.origin.y + codeKapInput.frame.height + 3 * screenScale + 10 * screenScale, width: codeView.frame.width, height: codePhoneInput.frame.height))
        codeCodeInput.delegate = self
        codeCodeInput.keyboardType = UIKeyboardType.numberPad
        codeCodeInput.tag = TagController.LoginTags.codeInput
        codeCodeInput.textColor = codePhoneInput.textColor
        codeCodeInput.font = codePhoneInput.font
        codeCodeInput.attributedPlaceholder = NSAttributedString(string: "请输入验证码", attributes: [NSAttributedString.Key.foregroundColor : UIColor.mainPlaceholder(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codeCodeInput.clearButtonMode = UITextField.ViewMode.never
        let codeCodeBottom = CALayer()
        codeCodeBottom.frame = CGRect(x: 0, y: codeCodeInput.frame.height + 1, width: codeCodeInput.frame.width, height: 1)
        codeCodeBottom.backgroundColor = codePhoneBottom.backgroundColor
        codeCodeInput.layer.addSublayer(codeCodeBottom)
        codeView.addSubview(codeCodeInput)
        
        let codeCodeButton = CodeSendButton(frame: CGRect(x: codeView.frame.width - 80 * screenScale, y: codeCodeInput.frame.origin.y + codeCodeInput.frame.height - 40 * screenScale, width: 80 * screenScale, height: 40 * screenScale))
        codeCodeButton.addTarget(self, action: #selector(sendCode(_:)), for: UIControl.Event.touchUpInside)
        codeView.addSubview(codeCodeButton)
        
        let submitButton = UIButton(frame: CGRect(x: 0, y: codeCodeInput.frame.origin.y + codeCodeInput.frame.height + 35 * screenScale, width: codeView.frame.width, height: 45 * screenScale))
        submitButton.tag = TagController.LoginTags.submitButton
        submitButton.layer.cornerRadius = submitButton.frame.height/2
        submitButton.backgroundColor = submitUncheckedColor
        submitButton.setTitle("登录", for: UIControl.State.normal)
        submitButton.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControl.Event.touchUpInside)
        submitButton.layer.shadowColor = UIColor.gray.cgColor
        submitButton.layer.shadowOpacity = 0.4
        submitButton.layer.shadowOffset = CGSize(width: 2, height: 2)
        submitButton.layer.shadowRadius = 3
        codeView.addSubview(submitButton)
        
        let documentView = UIView(frame: CGRect(x: 0, y: submitButton.frame.origin.y + submitButton.frame.height + 10 * screenScale, width: codeView.frame.width, height: 20 * screenScale))
        let documentLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        documentLabel.textColor = UIColor.fontGray()
        documentLabel.text = "登录注册表示同意 "
        documentLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        documentLabel.sizeToFit()
        documentLabel.frame = CGRect(x: 0, y: 0, width: documentLabel.frame.width, height: documentView.frame.height)
        documentView.addSubview(documentLabel)
        
        let privacyLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        privacyLabel.isUserInteractionEnabled = true
        privacyLabel.textColor = UIColor.mainYellow()
        privacyLabel.text = " 隐私政策 "
        privacyLabel.font = documentLabel.font
        privacyLabel.sizeToFit()
        privacyLabel.frame = CGRect(x: documentLabel.frame.origin.x + documentLabel.frame.width, y: 0, width: privacyLabel.frame.width, height: documentView.frame.height)
        privacyLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toPrivacy(_:))))
        documentView.addSubview(privacyLabel)
        
        let agreementLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        agreementLabel.isUserInteractionEnabled = true
        agreementLabel.textColor = UIColor.mainYellow()
        agreementLabel.text = " 用户服务协议 "
        agreementLabel.font = documentLabel.font
        agreementLabel.sizeToFit()
        agreementLabel.frame = CGRect(x: privacyLabel.frame.origin.x + privacyLabel.frame.width, y: 0, width: agreementLabel.frame.width, height: documentView.frame.height)
        agreementLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toAgreement(_:))))
        documentView.addSubview(agreementLabel)
        
        codeView.addSubview(documentView)
    }
    
    func getKapCode(){
        kapCode = String(format: "%.4d", (arc4random() % 10000))
        let codeKapView = codeView.viewWithTag(TagController.LoginTags.kapImageView) as! KaptchaCodeView
        codeKapView.load(kaptcha: kapCode)
    }
    
    @objc func toPrivacy(_ recognizer: UITapGestureRecognizer){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "DocumentViewController") as! DocumentViewController
        vc.urlString = "document/privacyProtocol"
        vc.pageTitle = "隐私政策"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toAgreement(_ recognizer: UITapGestureRecognizer){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "DocumentViewController") as! DocumentViewController
        vc.urlString = "document/userAgreement"
        vc.pageTitle = "用户服务协议"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func newKap(_ sender: UIButton){
        getKapCode()
    }
    
    @objc func sendCode(_ sender: CodeSendButton){
        hideKeyboard()
        if(checkCode()){
            let phone = (codeView.viewWithTag(TagController.LoginTags.phoneInput) as! UITextField).text!
            
            sender.startTimer()
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getTime(data: { (timestamp) in
                let codeString = secretKey + timestamp + phone + "code"
                let token = EncodingUtil.getBase64(systemType + self.kapCode + timestamp + codeString.md5)
                
                HttpController.get("front/sms/sendCode", params: NSDictionary(dictionary: ["codeType" : EncodingUtil.getBase64("code"),"mobile": EncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        AlertView(title: "短信验证码发送成功！").showByTime(time: 2)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        AlertView(title: message).showByTime(time: 2)
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
        }else{
            AlertView(title: "请输入正确的手机号和图形验证码！").showByTime(time: 2)
        }
    }
    
    func checkCode() -> Bool{
        let codePhoneInput = codeView.viewWithTag(TagController.LoginTags.phoneInput) as! UITextField
        let codeKapInput = codeView.viewWithTag(TagController.LoginTags.kapInput) as! UITextField
        
        if(codePhoneInput.text! == ""){
            return false
        }
        
        if(!Utils.checkPhone(phone: codePhoneInput.text!)){
            return false
        }
        
        if(codeKapInput.text != kapCode){
            return false
        }
        return true
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let phone = (codeView.viewWithTag(TagController.LoginTags.phoneInput) as! UITextField).text!
            let code = (codeView.viewWithTag(TagController.LoginTags.codeInput) as! UITextField).text!
            let loadingView = HttpController.showLoading(viewController: self)
            
            HttpController.getTime(data: { (timestamp) in
                let codeString = secretKey + timestamp + phone + code;
                let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                
                HttpController.post("loginFront/login", params: NSDictionary(dictionary: ["token" : token,"mobile" : EncodingUtil.getBase64(phone)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    
                    if(status == "SUCCESS"){
                        let data = dataDictionary.object(forKey: "data") as! NSDictionary
                        let frontUser = data.object(forKey: "frontUser") as! NSDictionary
                        user = UserModel(data: frontUser)
                        LocalDataController.writeLocalData("user", data: user!.getDictionary())
                        HttpController.hideLoading(loadingView: loadingView)
                        self.navigationController?.popViewController(animated: true)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        AlertView(title: message).showByTime(time: 2)
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
    
    func checkSubmit() -> Bool{
        let codePhoneInput = codeView.viewWithTag(TagController.LoginTags.phoneInput) as! UITextField
        let codeKapInput = codeView.viewWithTag(TagController.LoginTags.kapInput) as! UITextField
        let codeCodeInput = codeView.viewWithTag(TagController.LoginTags.codeInput) as! UITextField
        
        if(codePhoneInput.text! == ""){
            AlertView(title: "请输入手机号码").showByTime(time: 2)
            return false
        }
        
        if(codeCodeInput.text! == ""){
            AlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        
        if(codeKapInput.text == ""){
            AlertView(title: "请输入图形验证码").showByTime(time: 2)
            return false
        }
        
        if(codeKapInput.text != kapCode){
            AlertView(title: "请输入正确的图形验证码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkPhone(phone: codePhoneInput.text!)){
            AlertView(title: "请输入正确的手机号码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkCode(code: codeCodeInput.text!)){
            AlertView(title: "请输入正确的验证码").showByTime(time: 2)
            return false
        }
   
        return true
    }
    
    @objc func hideKeyboard(){
        let codePhone = codeView.viewWithTag(TagController.LoginTags.phoneInput) as? UITextField
        let codeCode = codeView.viewWithTag(TagController.LoginTags.codeInput) as? UITextField
        let codeKap = codeView.viewWithTag(TagController.LoginTags.kapInput) as? UITextField
        
        codePhone?.endEditing(true)
        codeCode?.endEditing(true)
        codeKap?.endEditing(true)
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let codePhoneInput = codeView.viewWithTag(TagController.LoginTags.phoneInput) as! UITextField
        let codeKapInput = codeView.viewWithTag(TagController.LoginTags.kapInput) as! UITextField
        let codeCodeInput = codeView.viewWithTag(TagController.LoginTags.codeInput) as! UITextField
        let submitButton = codeView.viewWithTag(TagController.LoginTags.submitButton) as! UIButton
        if(codePhoneInput.text! != "" && codeKapInput.text != "" && codeCodeInput.text != ""){
            submitButton.backgroundColor = UIColor.mainYellow()
            submitButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        }else{
            submitButton.backgroundColor = submitUncheckedColor
            submitButton.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.normal)
        }
        
        if(textField.tag == TagController.LoginTags.phoneInput && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.LoginTags.codeInput && textField.text!.count + string.count > 6){
            return false
        }else if(textField.tag == TagController.LoginTags.kapInput && textField.text!.count + string.count > 4){
            return false
        }
        return true
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        UIView.beginAnimations("up", context: nil)
        UIView.setAnimationDuration(0.2)
        self.view.frame.origin = CGPoint(x: 0, y: -30 * screenScale)
        UIView.commitAnimations()
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        UIView.beginAnimations("down", context: nil)
        UIView.setAnimationDuration(0.2)
        self.view.frame.origin = CGPoint(x: 0, y: 0)
        UIView.commitAnimations()
    }
}
