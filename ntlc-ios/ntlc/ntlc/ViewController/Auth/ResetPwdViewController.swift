//
//  ResetPwdViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/30.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ResetPwdViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    
    var phone: String!
    var code: String!
    
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
        let pwd = bodyView.viewWithTag(TagController.resetPwdTags.inputPwd) as? UITextField
        let repwd = bodyView.viewWithTag(TagController.resetPwdTags.inputRepwd) as? UITextField
        
        pwd?.endEditing(true)
        repwd?.endEditing(true)
    }
    
    func createBodyView(){
        let paddingLeft: CGFloat = 24
        
        bodyView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize.init(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
        let pwdInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: 40 * screenScale, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        pwdInput.delegate = self
        pwdInput.keyboardType = UIKeyboardType.alphabet
        pwdInput.isSecureTextEntry = true
        pwdInput.tag = TagController.resetPwdTags.inputPwd
        pwdInput.textColor = UIColor.fontBlack()
        pwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        pwdInput.attributedPlaceholder = NSAttributedString(string: "请输入8-20位字母数字组合密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        pwdInput.clearButtonMode = UITextFieldViewMode.always
        let pwdBottom = CALayer()
        pwdBottom.frame = CGRect(x: 0, y: pwdInput.frame.height + 1, width: pwdInput.frame.width, height: 1)
        pwdBottom.backgroundColor = UIColor.backgroundGray().cgColor
        pwdInput.layer.addSublayer(pwdBottom)
        bodyView.addSubview(pwdInput)
        
        let repwdInput = UITextField(frame: CGRect(x: paddingLeft * screenScale, y: pwdInput.frame.origin.y + pwdInput.frame.height + 3 * screenScale + 35 * screenScale, width: bodyView.frame.width - paddingLeft * 2 * screenScale, height: 20 * screenScale))
        repwdInput.delegate = self
        repwdInput.keyboardType = UIKeyboardType.alphabet
        repwdInput.isSecureTextEntry = true
        repwdInput.tag = TagController.resetPwdTags.inputRepwd
        repwdInput.textColor = UIColor.fontBlack()
        repwdInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        repwdInput.attributedPlaceholder = NSAttributedString(string: "请再次输入密码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        repwdInput.clearButtonMode = UITextFieldViewMode.always
        let repwdBottom = CALayer()
        repwdBottom.frame = CGRect(x: 0, y: repwdInput.frame.height + 1, width: repwdInput.frame.width, height: 1)
        repwdBottom.backgroundColor = UIColor.backgroundGray().cgColor
        repwdInput.layer.addSublayer(repwdBottom)
        bodyView.addSubview(repwdInput)
        
        let modifyMessage = UILabel(frame: CGRect(x: paddingLeft * screenScale, y: repwdInput.frame.origin.y + repwdInput.frame.height + 13 * screenScale, width: bodyView.frame.width - 2 * paddingLeft * screenScale, height: 30 * screenScale))
        modifyMessage.tag = TagController.resetPwdTags.modifyMessage
        modifyMessage.isHidden = true
        modifyMessage.backgroundColor = UIColor.mainGold().withAlphaComponent(0.1)
        modifyMessage.text = ""
        modifyMessage.textColor = UIColor.mainGold()
        modifyMessage.textAlignment = NSTextAlignment.center
        modifyMessage.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bodyView.addSubview(modifyMessage)
        
        bodyView.frame.size = CGSize(width: bodyView.frame.width, height: modifyMessage.frame.origin.y + modifyMessage.frame.height)
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 13 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("重置密码", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    @objc func submit(_ sender: UIButton){
        if(checkSubmit()){
            let messageLabel = bodyView.viewWithTag(TagController.resetPwdTags.modifyMessage) as! UILabel
            let pwd = (bodyView.viewWithTag(TagController.resetPwdTags.inputPwd) as! UITextField).text!
            let loadingView = HttpController.showLoading(viewController: self)
            
            HttpController.get("product/getTime", params: NSDictionary(), data: { (adata) in
                let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
                let codeString = secretKey + timestamp + self.phone + self.code;
                let token = EncodingUtil.getBase64(systemType + timestamp + pwd.md5 + codeString.md5)
                
                HttpController.post("login/resetpwd", params: NSDictionary(dictionary: ["token": token, "phone": EncodingUtil.getBase64(self.phone)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    
                    if(status == "SUCCESS"){
                        let vcs = self.navigationController!.childViewControllers
                        for i in 0 ..< vcs.count{
                            if(vcs[i].classForCoder == LoginViewController.classForCoder()){
                                self.navigationController?.popToViewController(vcs[i], animated: true)
                                AlertView(title: "重置密码成功").showByTime(time: 2)
                                break
                            }
                        }
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
    
    func checkSubmit() -> Bool{
        let pwdInput = bodyView.viewWithTag(TagController.resetPwdTags.inputPwd) as! UITextField
        let repwdInput = bodyView.viewWithTag(TagController.resetPwdTags.inputRepwd) as! UITextField
        let messageLabel = bodyView.viewWithTag(TagController.resetPwdTags.modifyMessage) as! UILabel
        messageLabel.isHidden = true
        
        if(pwdInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入密码"
            return false
        }
        
        if(repwdInput.text! == ""){
            messageLabel.isHidden = false
            messageLabel.text = "请输入确认密码"
            return false
        }
        
        if(!Utils.checkPassword(password: pwdInput.text!)){
            messageLabel.isHidden = false
            messageLabel.text = "请输入8-20位字母数字组合密码"
            return false
        }
        
        if(pwdInput.text != repwdInput.text){
            messageLabel.isHidden = false
            messageLabel.text = "两次密码输入不一致"
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.resetPwdTags.inputPwd && textField.text!.count + string.count > 20){
            return false
        }else if(textField.tag == TagController.resetPwdTags.inputRepwd && textField.text!.count + string.count > 20){
            return false
        }
        return true
    }
}
