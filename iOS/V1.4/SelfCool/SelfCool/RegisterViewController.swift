//
//  RegisterViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/31.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class RegisterViewController : UIViewController , UITextFieldDelegate , HttpDataProtocol{
    
    @IBOutlet weak var phoneText: UITextField!
    @IBOutlet weak var codeText: UITextField!
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var codeButton: UIButton!
    @IBOutlet weak var completeButton: UIButton!
    @IBOutlet weak var QQButton: UIButton!
    @IBOutlet weak var WXButton: UIButton!
    
    var httpController = HttpController()
    var loadingView : UIView?
    var authOptions = ShareSDK.authOptionsWithAutoAuth(true, allowCallback: true, scopes: nil, powerByHidden: true, followAccounts: nil, authViewStyle: SSAuthViewStyleModal, viewDelegate: nil, authManagerViewDelegate: nil)
    
    var codeTime : Int?
    var nsTimer : NSTimer?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        loadingView = LoadingView(self)
        phoneText.delegate = self
        codeText.delegate = self
        passwordText.delegate = self
        
        passwordText.secureTextEntry = true
        codeButton.layer.masksToBounds = true
        codeButton.layer.cornerRadius = 5
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        completeButton.layer.masksToBounds = true
        completeButton.layer.cornerRadius = 5
        completeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        completeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        completeButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        completeButton.setTitleColor(UIColor.darkGrayColor(), forState: UIControlState.Disabled)
        
        if versionStatus == true{
            QQButton.hidden = false
            WXButton.hidden = false
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("Register")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("Register")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    @IBAction func registerButton(sender: UIButton) {
        phoneText.resignFirstResponder()
        codeText.resignFirstResponder()
        passwordText.resignFirstResponder()
        if !isTelNumber(phoneText.text!){
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else if codeText.text! == ""{
            SelfAlertView(self, alertText: "请输入验证码", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else if passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18{
            SelfAlertView(self, alertText: "密码应在6-18位", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else if NSString(string: passwordText.text!).rangeOfString(" ").length > 0{
            SelfAlertView(self, alertText: "密码中不能含有空格", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            self.view.addSubview(loadingView!)
            let postData = getSsoUserRegisterString()
            let UserParams = NSDictionary(dictionary: ["postData" : postData])
            self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                user = UserModel()
                let userDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                user.id = userDictionary.objectForKey("uid") as! Int
                user.isFirstLogin = userDictionary.objectForKey("isFirstLogin") as! Bool
                user.token = userDictionary.objectForKey("toke") as! String
                user.name = userDictionary.objectForKey("nickname") as! String
                user.age = userDictionary.objectForKey("age") as! Int
                user.gender = userDictionary.objectForKey("gender") as! Int
                user.profession = userDictionary.objectForKey("professional") as! String
                user.iconUrl = userDictionary.objectForKey("imageUrl") as! String
                user.phone = userDictionary.objectForKey("phone") as! String
                user.password = userDictionary.objectForKey("password") as! String
                UserImageDownload("userIcon", urlString: user.iconUrl)
                let userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") as! MainPageViewController
                vc.isRegister = true
                self.presentViewController(vc, animated: true, completion: nil)
            }else{
                UIAlertView(title: "注册失败", message: dataDictionary.objectForKey("Message") as? String , delegate: self, cancelButtonTitle: "确认").show()
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
        if(!phoneText.exclusiveTouch || !codeText.exclusiveTouch) || !passwordText.exclusiveTouch{
            phoneText.resignFirstResponder()
            codeText.resignFirstResponder()
            passwordText.resignFirstResponder()
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField == phoneText{
            if phoneText.text! == ""{
            }else if !isTelNumber(phoneText.text!){
                SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
            }else{
                if codeText.text! != "" && passwordText.text! != "" && !(passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18) && !(NSString(string: passwordText.text!).rangeOfString(" ").length > 0) {
                }
            }
        }else if textField == codeText{
            if codeText.text! != "" && isTelNumber(phoneText.text!) && passwordText.text! != "" && !(passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18) && !(NSString(string: passwordText.text!).rangeOfString(" ").length > 0){
            }
        }else if textField == passwordText{
            if passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18{
                SelfAlertView(self, alertText: "密码应在6-18位", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            }else if NSString(string: passwordText.text!).rangeOfString(" ").length > 0{
                SelfAlertView(self, alertText: "密码中不能含有空格", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            }
        }
    }
    
    @IBAction func codeButton(sender: UIButton) {
        phoneText.resignFirstResponder()
        codeText.resignFirstResponder()
        passwordText.resignFirstResponder()
        if isTelNumber(phoneText.text!){
            let codeParams = NSDictionary(dictionary: ["mobile" : String(phoneText.text!) , "check" : "1"])
            httpController.getNSDataByParams("sendCode", paramsDictionary: codeParams)
        }else{
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }
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
    
    func getSsoUserRegisterString() -> String{
        var json = "{\"type\":3,\"data\":{"
        json += "\"phone\":\(phoneText.text!)"
        json += ",\"password\":\"\(passwordText.text!)\""
        json += ",\"code\":\(codeText.text!)"
        json += "}}"
        return json
    }
    
    @IBAction func qqLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeQQSpace, authOptions: nil) { (result, userInfo, error) -> Void in
            if result{
                MobClick.event("QQLogin")
                self.view.addSubview(self.loadingView!)
                user.authType = 1
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                let postData = user.getUserAuthLoginString()
                let UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }else if error.errorCode() == -6004 {
                UIAlertView(title: "登陆失败", message: "您尚未安装QQ客户端", delegate: self, cancelButtonTitle: "确认").show()
            }
        }
    }
    
    @IBAction func weiboLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeSinaWeibo, authOptions: authOptions) { (result, userInfo, error) -> Void in
            if result{
                MobClick.event("WeiBoLogin")
                self.view.addSubview(self.loadingView!)
                user.authType = 3
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                
                let postData = user.getUserAuthLoginString()
                let UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }
        }
    }
    
    @IBAction func weixinLogin(sender: UIButton) {
        ShareSDK.getUserInfoWithType(ShareTypeWeixiSession, authOptions: authOptions) { (result, userInfo, error) -> Void in
            if result{
                MobClick.event("WeiXinLogin")
                self.view.addSubview(self.loadingView!)
                user.authType = 2
                user.name = userInfo.nickname()
                user.screenName = userInfo.nickname()
                user.iconUrl = userInfo.profileImage()
                user.gender = userInfo.gender()
                user.uid = userInfo.uid()
                
                let postData = user.getUserAuthLoginString()
                let UserParams = NSDictionary(dictionary: ["postData" : postData])
                self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
            }else if error.errorCode() == -22003{
                UIAlertView(title: "登陆失败", message: "您尚未安装微信客户端", delegate: self, cancelButtonTitle: "确认").show()
            }
        }
    }
    
    @IBAction func backButton(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("accountChoiceViewController") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
}