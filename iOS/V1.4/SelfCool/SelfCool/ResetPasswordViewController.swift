//
//  ResetPasswordViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/31.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ResetPasswordViewController: UIViewController , UITextFieldDelegate , HttpDataProtocol{
    
    @IBOutlet weak var codeButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var phoneText: UITextField!
    @IBOutlet weak var codeText: UITextField!
    
    var httpController = HttpController()
    var loadingView : UIView?
    var codeTime : Int?
    var nsTimer : NSTimer?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        phoneText.delegate = self
        codeText.delegate = self
        loadingView = LoadingView(self)
        
        codeButton.layer.masksToBounds = true
        codeButton.layer.cornerRadius = 5
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        nextButton.layer.masksToBounds = true
        nextButton.layer.cornerRadius = 5
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("ResetPassword")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("ResetPassword")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                let userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user = UserModel()
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
                user.phone = userDictionary.objectForKey("phone") as! String
                user.password = userDictionary.objectForKey("password") as! String
                user.name = userDictionary.objectForKey("nickname") as! String
                user.age = userDictionary.objectForKey("age") as! Int
                user.gender = userDictionary.objectForKey("gender") as! Int
                user.profession = userDictionary.objectForKey("professional") as! String
                user.iconUrl = userDictionary.objectForKey("imageUrl") as! String
                
                let sb = UIStoryboard(name: "LoginAndRegister", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("userModifyPasswordViewController") 
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                UIAlertView(title: "登录失败", message: "手机号或验证码错误", delegate: self, cancelButtonTitle: "确认").show()
            }
        }else if recieveType == "sendCode"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status != "success" {
                let message = dataDictionary.objectForKey("Message") as! String
                UIAlertView(title: message, message: nil, delegate: self, cancelButtonTitle: "确认").show()
            }else{
                codeTime = 60
                codeButton.enabled = false
                codeButton.backgroundColor = UIColor.buttonGray()
                codeButton.titleLabel?.text = "重发验证码(\(codeTime!))"
                codeButton.setTitle("重发验证码(\(codeTime!))", forState: UIControlState.Normal)
                nsTimer = NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector: "updateTime", userInfo: nil, repeats: true)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if(!phoneText.exclusiveTouch || !codeText.exclusiveTouch){
            phoneText.resignFirstResponder()
            codeText.resignFirstResponder()
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField == phoneText{
            if !isTelNumber(phoneText.text!){
                SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error  , SelfAlertLevel: SelfAlertLevel.view)
            }
        }else if textField == codeText{
        }
    }
    
    @IBAction func nextButton(sender: UIButton) {
        phoneText.resignFirstResponder()
        codeText.resignFirstResponder()
        if !isTelNumber(phoneText.text!){
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else if codeText.text! == ""{
            SelfAlertView(self, alertText: "请输入验证码", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            self.view.addSubview(loadingView!)
            let postData = getSsoUserCodeLoginString()
            let UserParams = NSDictionary(dictionary: ["postData" : postData])
            self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }
    }
    
    @IBAction func codeButton(sender: UIButton) {
        phoneText.resignFirstResponder()
        codeText.resignFirstResponder()
        if isTelNumber(phoneText.text!){
            let codeParams = NSDictionary(dictionary: ["mobile" : String(phoneText.text!) , "check" : "0"])
            httpController.getNSDataByParams("sendCode", paramsDictionary: codeParams)
        }else{
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }
    }
    
    func getSsoUserCodeLoginString() -> String{
        var json = "{\"type\":4,\"data\":{"
        json += "\"phone\":\(phoneText.text!)"
        json += ",\"code\":\(codeText.text!)"
        json += "}}"
        return json
    }
    
    func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime > 0{
                codeButton.enabled = false
                codeButton.backgroundColor = UIColor.buttonGray()
                codeButton.titleLabel?.text = "重发验证码(\(codeTime!))"
                codeButton.setTitle("重发验证码(\(codeTime!))", forState: UIControlState.Normal)
            }else{
                codeButton.enabled = true
                codeButton.backgroundColor = UIColor.mainColor()
                codeButton.setTitle("发送验证码", forState: UIControlState.Normal)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
}