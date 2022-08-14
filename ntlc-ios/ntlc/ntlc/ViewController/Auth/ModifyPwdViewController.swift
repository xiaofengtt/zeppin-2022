//
//  ModifyPwdViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ModifyPwdViewController: UIViewController, UITextFieldDelegate{
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var topView: UIView = UIView()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        if(user!.pwdFlag){
            self.navigationItem.title = "修改密码"
        }else{
            self.navigationItem.title = "设置密码"
        }
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createTopView()
        createBodyView()
        createSubmitButton()
        super.viewDidLoad()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let pwd = bodyView.viewWithTag(TagController.modifyPwdTags.inputPwd) as? UITextField
        let repwd = bodyView.viewWithTag(TagController.modifyPwdTags.inputRepwd) as? UITextField
        let oldpwd = bodyView.viewWithTag(TagController.modifyPwdTags.inputOldPwd) as? UITextField
        
        pwd?.endEditing(true)
        repwd?.endEditing(true)
        oldpwd?.endEditing(true)
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
        if(user!.pwdFlag){
            label.text = "请使用复杂密码，保证账户安全"
        }else{
            label.text = "您尚未设置密码，请设置密码保证账户安全"
        }
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        topView.addSubview(label)
        mainView.addSubview(topView)
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        if(user!.pwdFlag){
            bodyView.frame.size = CGSize(width: screenWidth - paddingLeft * 2 * screenScale, height: 150 * screenScale)
        }else{
            bodyView.frame.size = CGSize(width: screenWidth - paddingLeft * 2 * screenScale, height: 100 * screenScale)
        }
        bodyView.backgroundColor = UIColor.white
        bodyView.addBaseShadow()
        
        let pwdView = UIView()
        if(user!.pwdFlag){
            let oldPwdView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: bodyView.frame.width, height: 50 * screenScale)))
            let oldPwdTitleLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 80 * screenScale, height: oldPwdView.frame.height))
            oldPwdTitleLabel.text = "原密码"
            oldPwdTitleLabel.textColor = UIColor.fontBlack()
            oldPwdTitleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            oldPwdView.addSubview(oldPwdTitleLabel)
            let oldPwdInput = UITextField(frame: CGRect(x: oldPwdTitleLabel.frame.origin.x + oldPwdTitleLabel.frame.width, y: 0, width: oldPwdView.frame.width - (oldPwdTitleLabel.frame.origin.x + oldPwdTitleLabel.frame.width) - 8 * screenScale, height: oldPwdView.frame.height))
            oldPwdInput.delegate = self
            oldPwdInput.tag = TagController.modifyPwdTags.inputOldPwd
            oldPwdInput.keyboardType = UIKeyboardType.alphabet
            oldPwdInput.isSecureTextEntry = true
            oldPwdInput.clearButtonMode = UITextFieldViewMode.always
            oldPwdInput.textColor = UIColor.fontBlack()
            oldPwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
            oldPwdInput.attributedPlaceholder = NSAttributedString(string: "请输原密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
            oldPwdView.addSubview(oldPwdInput)
            bodyView.addSubview(oldPwdView)
            
            pwdView.frame = CGRect(x: 0, y: oldPwdView.frame.origin.y + oldPwdView.frame.height, width: bodyView.frame.width, height: oldPwdView.frame.height)
        }else{
            pwdView.frame = CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 50 * screenScale)
        }
        let pwdTopLine = CALayer()
        pwdTopLine.frame = CGRect(x: 0, y: 0, width: pwdView.frame.width, height: 1)
        pwdTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        pwdView.layer.addSublayer(pwdTopLine)
        let pwdTitleLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 80 * screenScale, height: pwdView.frame.height))
        pwdTitleLabel.text = "新密码"
        pwdTitleLabel.textColor = UIColor.fontBlack()
        pwdTitleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        pwdView.addSubview(pwdTitleLabel)
        let pwdInput = UITextField(frame: CGRect(x: pwdTitleLabel.frame.origin.x + pwdTitleLabel.frame.width, y: 0, width: pwdView.frame.width - (pwdTitleLabel.frame.origin.x + pwdTitleLabel.frame.width) - 8 * screenScale, height: pwdView.frame.height))
        pwdInput.delegate = self
        pwdInput.tag = TagController.modifyPwdTags.inputPwd
        pwdInput.keyboardType = UIKeyboardType.alphabet
        pwdInput.isSecureTextEntry = true
        pwdInput.clearButtonMode = UITextFieldViewMode.always
        pwdInput.textColor = UIColor.fontBlack()
        pwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        pwdInput.attributedPlaceholder = NSAttributedString(string: "请输入8-20位字母数字组合密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        pwdView.addSubview(pwdInput)
        bodyView.addSubview(pwdView)
        
        let rePwdView = UIView(frame: CGRect(x: 0, y: pwdView.frame.origin.y + pwdView.frame.height, width: bodyView.frame.width, height: pwdView.frame.height))
        let rePwdTopLine = CALayer()
        rePwdTopLine.frame = CGRect(x: 0, y: 0, width: rePwdView.frame.width, height: 1)
        rePwdTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        rePwdView.layer.addSublayer(rePwdTopLine)
        let rePwdTitleLabel = UILabel(frame: pwdTitleLabel.frame)
        rePwdTitleLabel.text = "确认密码"
        rePwdTitleLabel.textColor = pwdTitleLabel.textColor
        rePwdTitleLabel.font = pwdTitleLabel.font
        rePwdView.addSubview(rePwdTitleLabel)
        let rePwdInput = UITextField(frame: pwdInput.frame)
        rePwdInput.delegate = self
        rePwdInput.tag = TagController.modifyPwdTags.inputRepwd
        rePwdInput.keyboardType = pwdInput.keyboardType
        rePwdInput.isSecureTextEntry = pwdInput.isSecureTextEntry
        rePwdInput.clearButtonMode = pwdInput.clearButtonMode
        rePwdInput.textColor = pwdInput.textColor
        rePwdInput.font = pwdInput.font
        rePwdInput.attributedPlaceholder = NSAttributedString(string: "请输入确认密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        rePwdView.addSubview(rePwdInput)
        bodyView.addSubview(rePwdView)
        
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 17 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("保存", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            var oldpwd = ""
            if(user!.pwdFlag){
                oldpwd = (bodyView.viewWithTag(TagController.modifyPwdTags.inputOldPwd) as! UITextField).text!
            }
            let pwd = (bodyView.viewWithTag(TagController.modifyPwdTags.inputPwd) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                    let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                    var codeString = ""
                    if(user!.pwdFlag){
                        codeString = secretKey + timestamp + pwd.md5 + oldpwd.md5
                    }else{
                        codeString = secretKey + timestamp + pwd.md5
                    }
                    let encrypt = EncodingUtil.getBase64(timestamp + pwd.md5 + codeString.md5)
                    
                    HttpController.post("user/resetpwd", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "encrypt": encrypt]), data: { (data) in
                        let dataDictionary = data as! NSDictionary
                        let status = dataDictionary.object(forKey: "status") as! String
                        
                        if(status == "SUCCESS"){
                            AlertView(title: "设置密码成功").showByTime(time: 2)
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
        let pwdInput = bodyView.viewWithTag(TagController.modifyPwdTags.inputPwd) as! UITextField
        let repwdInput = bodyView.viewWithTag(TagController.modifyPwdTags.inputRepwd) as! UITextField
        
        if(user!.pwdFlag){
            let oldpwdInput = bodyView.viewWithTag(TagController.modifyPwdTags.inputOldPwd) as! UITextField
            
            if(oldpwdInput.text! == ""){
                AlertView(title: "请输入原密码").showByTime(time: 2)
                return false
            }
            
            if(!Utils.checkPassword(password: oldpwdInput.text!)){
                AlertView(title: "请输正确的原密码").showByTime(time: 2)
                return false
            }
        }
        if(pwdInput.text! == ""){
            AlertView(title: "请输入新密码").showByTime(time: 2)
            return false
        }
        
        if(repwdInput.text! == ""){
            AlertView(title: "请输入确认密码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkPassword(password: pwdInput.text!)){
            AlertView(title: "请输入8-20位字母数字组合密码").showByTime(time: 2)
            return false
        }
        
        if(pwdInput.text != repwdInput.text){
            AlertView(title: "两次密码输入不一致").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.modifyPwdTags.inputPwd && textField.text!.count + string.count > 20){
            return false
        }else if(textField.tag == TagController.modifyPwdTags.inputRepwd && textField.text!.count + string.count > 20){
            return false
        }else if(textField.tag == TagController.modifyPwdTags.inputOldPwd && textField.text!.count + string.count > 20){
            return false
        }
        return true
    }
}
