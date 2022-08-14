//
//  LoginViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/17.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UIScrollViewDelegate, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var typeView: UIView = UIView()
    var scrollView: UIScrollView = UIScrollView()
    var submitButton: UIButton = UIButton()
    var messageView: UIView = UIView()
    var bottomView: UIView = UIView()
    
    let typeNormalColor = UIColor(red: 177/255, green: 177/255, blue: 177/255, alpha: 1)
    let typeSelectedColor = UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1)
    
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
        createTypeView()
        createScrollView()
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
    
    @objc func hideKeyboard(){
        let codePhone = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typePhone) as? UITextField
        let codeCode = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeCode) as? UITextField
        let pwdPhone = scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typePhone) as? UITextField
        let pwdPwd = scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeCode) as? UITextField
        
        codePhone?.endEditing(true)
        codeCode?.endEditing(true)
        pwdPhone?.endEditing(true)
        pwdPwd?.endEditing(true)
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 155 * screenScale))
        
        let imageView = UIImageView(frame: CGRect(x: (screenWidth - 68 * screenScale) / 2, y: 100 * screenScale, width: 68 * screenScale, height: 55 * screenScale))
        imageView.image = UIImage(named: "login_logo")
        headerView.addSubview(imageView)
        mainView.addSubview(headerView)
    }
    
    func createTypeView(){
        typeView = UIView(frame: CGRect(x: 0, y: headerView.frame.origin.y + headerView.frame.height, width: screenWidth, height: 115 * screenScale))
        
        let codeView = UIView(frame: CGRect(x: 0, y: 45 * screenScale, width: 0, height: 25 * screenScale))
        let codeImage = UIImageView(frame: CGRect(x: 2 * screenScale, y: 3 * screenScale, width: 10 * screenScale, height: 14 * screenScale))
        codeImage.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeImage
        codeImage.image = UIImage(named: "login_code_normal")
        codeView.addSubview(codeImage)
        
        let codeLabel = UILabel(frame: CGRect(x: codeImage.frame.origin.x + codeImage.frame.width + 3  * screenScale, y: 0, width: 0, height: 20 * screenScale))
        codeLabel.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeLabel
        codeLabel.text = "手机快速登录"
        codeLabel.textColor = typeNormalColor
        codeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        codeLabel.sizeToFit()
        codeLabel.frame.size = CGSize(width: codeLabel.frame.width, height: 20 * screenScale)
        codeView.addSubview(codeLabel)
        codeView.frame.size = CGSize(width: codeLabel.frame.origin.x + codeLabel.frame.width, height: 25 * screenScale)
        codeView.frame.origin = CGPoint(x: screenWidth / 2 - 25 * screenScale - codeView.frame.width , y: 45 * screenScale)
        
        let codeBottomLine = UIImageView(frame: CGRect(x: 0, y: codeView.frame.height - 2 * screenScale, width: codeView.frame.width, height: 2 * screenScale))
        codeBottomLine.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeBottomLine
        codeBottomLine.backgroundColor = typeNormalColor.withAlphaComponent(0.6)
        codeView.addSubview(codeBottomLine)
        
        let codeButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: codeView.frame.size))
        codeButton.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeButton
        codeButton.addTarget(self, action: #selector(selectType(_:)), for: UIControlEvents.touchUpInside)
        codeView.addSubview(codeButton)
        typeView.addSubview(codeView)
        
        let pwdView = UIView(frame: CGRect(x: 0, y: 45 * screenScale, width: 0, height: 25 * screenScale))
        let pwdImage = UIImageView(frame: CGRect(x: 1 * screenScale, y: 2 * screenScale, width: 13 * screenScale, height: 15 * screenScale))
        pwdImage.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeImage
        pwdImage.image = UIImage(named: "login_pwd_selected")
        pwdView.addSubview(pwdImage)
        
        let pwdLabel = UILabel(frame: CGRect(x: pwdImage.frame.origin.x + pwdImage.frame.width + 3  * screenScale, y: 0, width: 0, height: 20 * screenScale))
        pwdLabel.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeLabel
        pwdLabel.text = "账号密码登录"
        pwdLabel.textColor = typeSelectedColor
        pwdLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        pwdLabel.sizeToFit()
        pwdLabel.frame.size = CGSize(width: pwdLabel.frame.width, height: 20 * screenScale)
        pwdView.addSubview(pwdLabel)
        pwdView.frame.size = CGSize(width: pwdLabel.frame.origin.x + pwdLabel.frame.width, height: 25 * screenScale)
        pwdView.frame.origin = CGPoint(x: screenWidth / 2 + 25 * screenScale , y: 45 * screenScale)
        
        let pwdBottomLine = UIImageView(frame: CGRect(x: 0, y: pwdView.frame.height - 2 * screenScale, width: pwdView.frame.width, height: 2 * screenScale))
        pwdBottomLine.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeBottomLine
        pwdBottomLine.backgroundColor = typeSelectedColor
        pwdView.addSubview(pwdBottomLine)
        
        let pwdButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: pwdView.frame.size))
        pwdButton.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeButton
        pwdButton.isSelected = true
        pwdButton.addTarget(self, action: #selector(selectType(_:)), for: UIControlEvents.touchUpInside)
        pwdView.addSubview(pwdButton)
        typeView.addSubview(pwdView)
        
        mainView.addSubview(typeView)
    }
    
    func createScrollView(){
        let paddingLeft: CGFloat = 24
        
        scrollView = UIScrollView (frame: CGRect(x: 0, y: typeView.frame.origin.y + typeView.frame.height, width: screenWidth, height: 130 * screenScale))
        scrollView.delegate = self
        scrollView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(hideKeyboard)))
        scrollView.contentSize = CGSize(width: scrollView.frame.width * 2, height: scrollView.frame.height)
        scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width, y: 0), size: scrollView.frame.size) , animated: false)
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.isPagingEnabled = true
        scrollView.bounces = false
        
        let codeView = UIView(frame: CGRect(origin: CGPoint.zero, size: scrollView.frame.size))
        let codePhoneInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: scrollView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        codePhoneInput.delegate = self
        codePhoneInput.keyboardType = UIKeyboardType.numberPad
        codePhoneInput.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typePhone
        codePhoneInput.textColor = UIColor.fontBlack()
        codePhoneInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        codePhoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codePhoneInput.clearButtonMode = UITextFieldViewMode.always
        let codePhoneBottom = CALayer()
        codePhoneBottom.frame = CGRect(x: 0, y: codePhoneInput.frame.height + 1, width: codePhoneInput.frame.width, height: 1)
        codePhoneBottom.backgroundColor = UIColor.backgroundGray().cgColor
        codePhoneInput.layer.addSublayer(codePhoneBottom)
        codeView.addSubview(codePhoneInput)
        
        let codeCodeInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: codePhoneInput.frame.origin.y + codePhoneInput.frame.height + 3 * screenScale + 35 * screenScale, width: scrollView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        codeCodeInput.delegate = self
        codeCodeInput.keyboardType = UIKeyboardType.numberPad
        codeCodeInput.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeCode
        codeCodeInput.textColor = UIColor.fontBlack()
        codeCodeInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        codeCodeInput.attributedPlaceholder = NSAttributedString(string: "请输入验证码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        codeCodeInput.clearButtonMode = UITextFieldViewMode.never
        let codeCodeBottom = CALayer()
        codeCodeBottom.frame = CGRect(x: 0, y: codeCodeInput.frame.height + 1, width: codeCodeInput.frame.width, height: 1)
        codeCodeBottom.backgroundColor = UIColor.backgroundGray().cgColor
        codeCodeInput.layer.addSublayer(codeCodeBottom)
        codeView.addSubview(codeCodeInput)
        
        let codeCodeButton = CodeSendButton(frame: CGRect(x: codeView.frame.width - 105 * screenScale, y: codeCodeInput.frame.origin.y + codeCodeInput.frame.height - 34 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
        codeCodeButton.addTarget(self, action: #selector(sendCode(_:)), for: UIControlEvents.touchUpInside)
        codeView.addSubview(codeCodeButton)
        
        let codeMessageLabel = UILabel(frame: CGRect(x: paddingLeft * screenScale, y: codeCodeInput.frame.origin.y + codeCodeInput.frame.height + 13 * screenScale, width: scrollView.frame.width - 2 * paddingLeft * screenScale, height: 30 * screenScale))
        codeMessageLabel.tag = TagController.loginTags.codeTypeView + TagController.loginTags.typeMessage
        codeMessageLabel.isHidden = true
        codeMessageLabel.backgroundColor = UIColor.mainGold().withAlphaComponent(0.1)
        codeMessageLabel.text = ""
        codeMessageLabel.textColor = UIColor.mainGold()
        codeMessageLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        codeMessageLabel.textAlignment = NSTextAlignment.center
        codeView.addSubview(codeMessageLabel)
        scrollView.addSubview(codeView)
        
        
        let pwdView = UIView(frame: CGRect(origin: CGPoint(x: scrollView.frame.width, y: 0), size: scrollView.frame.size))
        let pwdPhoneInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: 0, width: scrollView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        pwdPhoneInput.delegate = self
        pwdPhoneInput.keyboardType = UIKeyboardType.numberPad
        pwdPhoneInput.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typePhone
        pwdPhoneInput.textColor = UIColor.fontBlack()
        pwdPhoneInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        pwdPhoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        pwdPhoneInput.clearButtonMode = UITextFieldViewMode.always
        let pwdPhoneBottom = CALayer()
        pwdPhoneBottom.frame = CGRect(x: 0, y: pwdPhoneInput.frame.height + 1, width: pwdPhoneInput.frame.width, height: 1)
        pwdPhoneBottom.backgroundColor = UIColor.backgroundGray().cgColor
        pwdPhoneInput.layer.addSublayer(pwdPhoneBottom)
        pwdView.addSubview(pwdPhoneInput)
        
        let pwdpwdInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: pwdPhoneInput.frame.origin.y + pwdPhoneInput.frame.height + 3 * screenScale + 35 * screenScale, width: scrollView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        pwdpwdInput.delegate = self
        pwdpwdInput.keyboardType = UIKeyboardType.alphabet
        pwdpwdInput.isSecureTextEntry = true
        pwdpwdInput.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeCode
        pwdpwdInput.textColor = UIColor.fontBlack()
        pwdpwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        pwdpwdInput.attributedPlaceholder = NSAttributedString(string: "请输入密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        pwdpwdInput.clearButtonMode = UITextFieldViewMode.always
        let pwdpwdBottom = CALayer()
        pwdpwdBottom.frame = CGRect(x: 0, y: pwdpwdInput.frame.height + 1, width: pwdpwdInput.frame.width, height: 1)
        pwdpwdBottom.backgroundColor = UIColor.backgroundGray().cgColor
        pwdpwdInput.layer.addSublayer(pwdpwdBottom)
        pwdView.addSubview(pwdpwdInput)
        
        let pwdMessageLabel = UILabel(frame: CGRect(x: paddingLeft * screenScale, y: pwdpwdInput.frame.origin.y + pwdpwdInput.frame.height + 13 * screenScale, width: scrollView.frame.width - 2 * paddingLeft * screenScale, height: 30 * screenScale))
        pwdMessageLabel.tag = TagController.loginTags.pwdTypeView + TagController.loginTags.typeMessage
        pwdMessageLabel.isHidden = true
        pwdMessageLabel.backgroundColor = UIColor.mainGold().withAlphaComponent(0.1)
        pwdMessageLabel.text = ""
        pwdMessageLabel.textColor = UIColor.mainGold()
        pwdMessageLabel.textAlignment = NSTextAlignment.center
        pwdMessageLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        pwdView.addSubview(pwdMessageLabel)
        scrollView.addSubview(pwdView)
        
        mainView.addSubview(scrollView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: scrollView.frame.origin.y + scrollView.frame.height, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("登录", for: UIControlState.normal)
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
        text.text = "登录即视为同意"
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
        
        let forget = UIButton()
        forget.tag = TagController.loginTags.forgetButton
        forget.setTitle("忘记密码？", for: UIControlState.normal)
        forget.setTitleColor(agreement.titleColor(for: UIControlState.normal), for: UIControlState.normal)
        forget.titleLabel?.font = agreement.titleLabel?.font
        forget.sizeToFit()
        forget.frame = CGRect(x: messageView.frame.width - forget.frame.width, y: 0, width: forget.frame.width, height: messageView.frame.height)
        forget.addTarget(self, action: #selector(toResetPwd(_:)), for: UIControlEvents.touchUpInside)
        messageView.addSubview(forget)
        
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
        text.text = "没有账户？"
        text.textColor = UIColor.fontGray()
        text.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        text.textAlignment = NSTextAlignment.right
        bottomView.addSubview(text)
        
        let button = UIButton(frame: CGRect(x: bottomView.frame.width/2, y: text.frame.origin.y, width: 0, height: text.frame.height))
        button.setTitle("立即注册", for: UIControlState.normal)
        button.setTitleColor(UIColor(red: 84/255, green: 139/255, blue: 204/255, alpha: 1), for: UIControlState.normal)
        button.titleLabel?.font = text.font
        button.sizeToFit()
        button.frame.size = CGSize(width: button.frame.width, height: text.frame.height)
        button.addTarget(self, action: #selector(toRegister(_:)), for: UIControlEvents.touchUpInside)
        bottomView.addSubview(button)
        
        mainView.addSubview(bottomView)
    }
    
    @objc func back(_ sender: UIButton){
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        self.navigationController?.popViewController(animated: true)
    }
    
    @objc func toRegister(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "registerViewController") as! RegisterViewController
        self.navigationController?.pushViewController(vc, animated: false)
    }
    
    @objc func toResetPwd(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "resetConfirmViewController") as! ResetConfirmViewController
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toPdf(_ sender: UIButton){
        hideKeyboard()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "pdfViewController") as! PdfViewController
        vc.titleName = "《牛投理财用户服务协议》"
        vc.urlString = "../resource/456.pdf"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func selectType(_ sender: UIButton){
        hideKeyboard()
        if(!sender.isSelected){
            let viewTag = sender.tag - TagController.loginTags.typeButton
            if(viewTag == TagController.loginTags.codeTypeView){
                scrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: scrollView.frame.size) , animated: true)
            }else{
                scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width, y: 0), size: scrollView.frame.size) , animated: true)
            }
        }
    }
    
    @objc func sendCode(_ sender: CodeSendButton){
        hideKeyboard()
        if(checkCode()){
            let phone = (scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typePhone) as! UITextField).text!
            
            sender.startTimer()
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + phone + "code"
                let token = EncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                
                HttpController.get("sms/sendCode", params: NSDictionary(dictionary: ["codeType" : EncodingUtil.getBase64("code"),"phone": EncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    let codeMessage = self.scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeMessage) as! UILabel
                    if(status == "SUCCESS"){
                        codeMessage.text = "短信验证码已发送至\(phone)，请注意查收。"
                        codeMessage.isHidden = false
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        codeMessage.text = message
                        codeMessage.isHidden = false
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
        hideKeyboard()
        if(checkSubmit()){
            let codeButton = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeButton) as! UIButton
            let pwdButton = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeButton) as! UIButton
            
            if(codeButton.isSelected){
                let codeMessage = self.scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeMessage) as! UILabel
                let phone = (scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typePhone) as! UITextField).text!
                let code = (scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeCode) as! UITextField).text!
                let loadingView = HttpController.showLoading(viewController: self)
                
                HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                    let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                    let codeString = secretKey + timestamp + code;
                    let token = EncodingUtil.getBase64(systemType + timestamp + phone + codeString.md5)
                    
                    HttpController.post("login/loginBycode", params: NSDictionary(dictionary: ["token" : token]), data: { (data) in
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
                                    self.back(UIButton())
                                }, errors: { (error) in
                                    HttpController.hideLoading(loadingView: loadingView)
                                    HttpController.showTimeout(viewController: self)
                                })
                            }
                        }else{
                            let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                            codeMessage.text = message
                            codeMessage.isHidden = false
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
            }else if(pwdButton.isSelected){
                let pwdMessage = self.scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeMessage) as! UILabel
                let phone = (scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typePhone) as! UITextField).text!
                let pwd = (scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeCode) as! UITextField).text!
                let loadingView = HttpController.showLoading(viewController: self)
                
                HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                    let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                    let codeString = secretKey + timestamp + pwd.md5;
                    let token = EncodingUtil.getBase64(systemType + timestamp + phone + codeString.md5)
                    
                    HttpController.post("login/login", params: NSDictionary(dictionary: ["token" : token]), data: { (data) in
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
                                    self.back(UIButton())
                                }, errors: { (error) in
                                    HttpController.hideLoading(loadingView: loadingView)
                                    HttpController.showTimeout(viewController: self)
                                })
                            }
                        }else{
                            let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                            pwdMessage.text = message
                            pwdMessage.isHidden = false
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
    }
    
    func checkCode() -> Bool{
        let codeMessage = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeMessage) as! UILabel
        codeMessage.isHidden = true
        
        let codePhoneInput = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typePhone) as! UITextField
        
        if(codePhoneInput.text! == ""){
            codeMessage.isHidden = false
            codeMessage.text = "请输入手机号码"
            return false
        }
        
        if(!Utils.checkPhone(phone: codePhoneInput.text!)){
            codeMessage.isHidden = false
            codeMessage.text = "请输入正确的手机号码"
            return false
        }
        return true
    }
    
    func checkSubmit() -> Bool{
        let codeButton = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeButton) as! UIButton
        let pwdButton = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeButton) as! UIButton
        
        if(codeButton.isSelected){
            let codeMessage = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeMessage) as! UILabel
            codeMessage.isHidden = true
            
            let codePhoneInput = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typePhone) as! UITextField
            let codeCodeInput = scrollView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeCode) as! UITextField
            
            if(codePhoneInput.text! == ""){
                codeMessage.isHidden = false
                codeMessage.text = "请输入手机号码"
                return false
            }
            
            if(codeCodeInput.text! == ""){
                codeMessage.isHidden = false
                codeMessage.text = "请输入验证码"
                return false
            }
            
            if(!Utils.checkPhone(phone: codePhoneInput.text!)){
                codeMessage.isHidden = false
                codeMessage.text = "请输入正确的手机号码"
                return false
            }
            
            if(!Utils.checkCode(code: codeCodeInput.text!)){
                codeMessage.isHidden = false
                codeMessage.text = "请输入正确的验证码"
                return false
            }
        }else if(pwdButton.isSelected){
            let pwdMessage = scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeMessage) as! UILabel
            pwdMessage.isHidden = true
            
            let pwdPhoneInput = scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typePhone) as! UITextField
            let pwdPwdInput = scrollView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeCode) as! UITextField
            
            if(pwdPhoneInput.text! == ""){
                pwdMessage.isHidden = false
                pwdMessage.text = "请输入手机号码"
                return false
            }
            
            if(pwdPwdInput.text! == ""){
                pwdMessage.isHidden = false
                pwdMessage.text = "请输入密码"
                return false
            }
            
            if(!Utils.checkPhone(phone: pwdPhoneInput.text!)){
                pwdMessage.isHidden = false
                pwdMessage.text = "请输入正确的手机号码"
                return false
            }
            
            if(!Utils.checkPassword(password: pwdPwdInput.text!)){
                pwdMessage.isHidden = false
                pwdMessage.text = "请输入正确的密码"
                return false
            }
        }
        
        return true
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let page = Int(floor((scrollView.contentOffset.x * 2.0 + scrollView.frame.width) / (scrollView.frame.width * 2.0)))
        let codeImage = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeImage) as! UIImageView
        let codeLabel = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeLabel) as! UILabel
        let codeBottomLine = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeBottomLine) as! UIImageView
        let codeButton = typeView.viewWithTag(TagController.loginTags.codeTypeView + TagController.loginTags.typeButton) as! UIButton
        let pwdImage = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeImage) as! UIImageView
        let pwdLabel = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeLabel) as! UILabel
        let pwdBottomLine = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeBottomLine) as! UIImageView
        let pwdButton = typeView.viewWithTag(TagController.loginTags.pwdTypeView + TagController.loginTags.typeButton) as! UIButton
        let forgetButton = mainView.viewWithTag(TagController.loginTags.forgetButton) as? UIButton
        
        hideKeyboard()
        if(page == 0 && !codeButton.isSelected){
            codeImage.image = UIImage(named: "login_code_selected")
            codeLabel.textColor = typeSelectedColor
            codeBottomLine.backgroundColor = typeSelectedColor
            codeButton.isSelected = true
            pwdImage.image = UIImage(named: "login_pwd_normal")
            pwdLabel.textColor = typeNormalColor
            pwdBottomLine.backgroundColor = typeNormalColor.withAlphaComponent(0.6)
            pwdButton.isSelected = false
            forgetButton?.isHidden = true
        }else if(page == 1 && !pwdButton.isSelected){
            codeImage.image = UIImage(named: "login_code_normal")
            codeLabel.textColor = typeNormalColor
            codeBottomLine.backgroundColor = typeNormalColor.withAlphaComponent(0.6)
            codeButton.isSelected = false
            pwdImage.image = UIImage(named: "login_pwd_selected")
            pwdLabel.textColor = typeSelectedColor
            pwdBottomLine.backgroundColor = typeSelectedColor
            pwdButton.isSelected = true
            forgetButton?.isHidden = false
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.loginTags.codeTypeView + TagController.loginTags.typePhone && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.loginTags.codeTypeView + TagController.loginTags.typeCode && textField.text!.count + string.count > 6){
            return false
        }else if(textField.tag == TagController.loginTags.pwdTypeView + TagController.loginTags.typePhone && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == TagController.loginTags.pwdTypeView + TagController.loginTags.typeCode && textField.text!.count + string.count > 20){
            return false
        }
        return true
    }
}
