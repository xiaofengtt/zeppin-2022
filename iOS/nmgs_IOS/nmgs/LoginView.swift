//
//  LoginView.swift
//  nmgs
//
//  Created by zeppin on 2016/11/21.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

public protocol loginViewDelegate{
    func pushViewController(viewController: UIViewController)
    func pushAlertController(alertController: UIAlertController)
}

class PasswordLoginView: UIView, UITextFieldDelegate{
    
    var delegate: loginViewDelegate?
    
    @IBOutlet weak var phoneLabel: UITextField!
    @IBOutlet weak var passwordLabel: UITextField!
    @IBOutlet weak var autoButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    
    
    override func draw(_ rect: CGRect) {
        super.draw(rect)
        
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControlState.normal)
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white.withAlphaComponent(0.6)), for: UIControlState.highlighted)
        phoneLabel.delegate = self
//        phoneLabel.text = "15896786295"
//        passwordLabel.text = "1qaz!QAZ"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        self.backgroundColor = UIColor.clear
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let phoneString = textField.text! as NSString
        if phoneString.length >= 11 && string != ""{
            return false
        }else{
            return true
        }
    }
    
    @IBAction func clickAutoButton(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected
    }
    
    @IBAction func clickFindPasswordButton(_ sender: UIButton) {
        let alert = UIAlertController(title: "尊敬的用户,您可以拨打10086,根据语音提示重置密码,谢谢", message: nil, preferredStyle: UIAlertControllerStyle.alert)
        let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
        alert.addAction(acCancel)
        self.delegate?.pushAlertController(alertController: alert)
    }
    
    @IBAction func clickLoginButton(_ sender: UIButton) {
//        Singleton.main().createAll(withUserName: nmgsUser.phone, token: nmgsUser.token)
//        let sb = UIStoryboard(name: "Main", bundle: nil)
//        let vc = sb.instantiateViewController(withIdentifier: "mainTabBar") as! UITabBarController
//        self.delegate?.pushViewController(viewController: vc)
        
        if !validatePhone(phoneNumber: phoneLabel.text!){
            let alert = UIAlertController(title: "请输入11位移动手机号码", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.delegate?.pushAlertController(alertController: alert)
        }else if passwordLabel.text! == ""{
            let alert = UIAlertController(title: "请输入密码", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.delegate?.pushAlertController(alertController: alert)
        }else{
            sender.isEnabled = false
            phoneLabel.resignFirstResponder()
            passwordLabel.resignFirstResponder()
            
            let loginParams = NSDictionary(dictionary: ["loginname" : phoneLabel.text! , "password" : EncodeController.getCipher(passwordLabel.text!)])
            HttpController.getNSDataByParams("passwordLogin", paramsDictionary: loginParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    nmgsUser = SsoUserModel()
                    nmgsUser.phone = data.object(forKey: "phone") as! String
                    nmgsUser.name = data.object(forKey: "name") as! String
                    nmgsUser.token = data.object(forKey: "token") as! String
                    nmgsUser.isAutoLogin = self.autoButton.isSelected
                    LocalDataController.writeLocalData("user", data: nmgsUser.getDictionary())
                    
                    Singleton.main().createAll(withUserName: nmgsUser.phone, token: nmgsUser.token, complete: {
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "mainTabBar") as! UITabBarController
                        self.delegate?.pushViewController(viewController: vc)
                    })
                }else{
                    sender.isEnabled = true
                    let alert = UIAlertController(title: "登录失败", message: "用户名或密码错误", preferredStyle: UIAlertControllerStyle.alert)
                    let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                    alert.addAction(acCancel)
                    self.delegate?.pushAlertController(alertController: alert)
                }
            }, errors: { (error) in
                sender.isEnabled = true
                let alert = UIAlertController(title: "网络连接失败", message: "请检查当前网络环境", preferredStyle: UIAlertControllerStyle.alert)
                let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                alert.addAction(acCancel)
                self.delegate?.pushAlertController(alertController: alert)
            })
        }
    }
}

class MessageLoginView: UIView, UITextFieldDelegate{
    
    var delegate: loginViewDelegate?
    
    @IBOutlet weak var codeLabel: UITextField!
    @IBOutlet weak var phoneLabel: UITextField!
    @IBOutlet weak var codeButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    
    var codeTime : Int?
    var nsTimer : Timer?
    
    override func draw(_ rect: CGRect) {
        super.draw(rect)
        codeButton.layer.masksToBounds = true
        codeButton.layer.cornerRadius = 5
        codeButton.setTitleColor(UIColor.mobileBlue(), for: UIControlState.disabled)
        codeButton.setTitleColor(UIColor.white, for: UIControlState.disabled)
        
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControlState.normal)
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white.withAlphaComponent(0.6)), for: UIControlState.highlighted)
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControlState.normal)
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white.withAlphaComponent(0.6)), for: UIControlState.highlighted)
        
        phoneLabel.delegate = self
//        phoneLabel.text = "15896786295"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        self.backgroundColor = UIColor.clear
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let phoneString = textField.text! as NSString
        if phoneString.length >= 11 && string != ""{
            return false
        }else{
            return true
        }
    }
    
    func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime > 0{
                codeButton.setTitle("\(codeTime!)秒后再次获取", for: UIControlState.disabled)
            }else{
                codeButton.isEnabled = true
                codeButton.backgroundColor = UIColor.white.withAlphaComponent(0.6)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
    
    @IBAction func clickCodeButton(_ sender: UIButton) {
        if validatePhone(phoneNumber: phoneLabel.text!){
            codeTime = 60
            codeButton.isEnabled = false
            codeButton.backgroundColor = UIColor.darkGray.withAlphaComponent(0.6)
            codeButton.setTitle("\(codeTime!)秒后再次获取", for: UIControlState.disabled)
            nsTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
            
            let codeParams = NSDictionary(dictionary: ["phone" : phoneLabel.text!])
            HttpController.getNSDataByParams("sendCode", paramsDictionary: codeParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let alert = UIAlertController(title: "获取验证码成功", message: nil, preferredStyle: UIAlertControllerStyle.alert)
                    let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                    alert.addAction(acCancel)
                    self.delegate?.pushAlertController(alertController: alert)
                }else{
                    let alert = UIAlertController(title: "获取验证码失败", message: nil, preferredStyle: UIAlertControllerStyle.alert)
                    let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                    alert.addAction(acCancel)
                    self.delegate?.pushAlertController(alertController: alert)
                }
            }, errors: { (error) in
                
            })
        }else{
            let alert = UIAlertController(title: "请输入11位移动手机号码", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.delegate?.pushAlertController(alertController: alert)
        }
    }
    
    @IBAction func clickLoginButton(_ sender: UIButton) {
        if !validatePhone(phoneNumber: phoneLabel.text!){
            let alert = UIAlertController(title: "请输入11位移动手机号码", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.delegate?.pushAlertController(alertController: alert)
        }else if !validateCode(codeString: codeLabel.text!){
            let alert = UIAlertController(title: "验证码输入错误", message: nil, preferredStyle: UIAlertControllerStyle.alert)
            let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
            alert.addAction(acCancel)
            self.delegate?.pushAlertController(alertController: alert)
        }else{
            sender.isEnabled = false
            phoneLabel.resignFirstResponder()
            codeLabel.resignFirstResponder()
            
            let loginParams = NSDictionary(dictionary: ["phone" : phoneLabel.text! , "code" : codeLabel.text!])
            HttpController.getNSDataByParams("messageLogin", paramsDictionary: loginParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    nmgsUser = SsoUserModel()
                    nmgsUser.phone = data.object(forKey: "phone") as! String
                    nmgsUser.name = data.object(forKey: "name") as! String
                    nmgsUser.token = data.object(forKey: "token") as! String
                    nmgsUser.isAutoLogin = true
                    LocalDataController.writeLocalData("user", data: nmgsUser.getDictionary())
                    
                    Singleton.main().createAll(withUserName: nmgsUser.phone, token: nmgsUser.token, complete: {
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "mainTabBar") as! UITabBarController
                        self.delegate?.pushViewController(viewController: vc)
                    })
                }else{
                    sender.isEnabled = true
                    let alert = UIAlertController(title: "登录失败", message: "手机号或验证码错误", preferredStyle: UIAlertControllerStyle.alert)
                    let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                    alert.addAction(acCancel)
                    self.delegate?.pushAlertController(alertController: alert)
                }
            }, errors: { (error) in
                sender.isEnabled = true
                let alert = UIAlertController(title: "网络连接失败", message: "请检查当前网络环境", preferredStyle: UIAlertControllerStyle.alert)
                let acCancel = UIAlertAction(title: "确认", style: UIAlertActionStyle.cancel, handler: nil)
                alert.addAction(acCancel)
                self.delegate?.pushAlertController(alertController: alert)
            })
            
        }
    }
}
