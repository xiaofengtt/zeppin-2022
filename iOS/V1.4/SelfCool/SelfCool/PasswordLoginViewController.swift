//
//  PasswordLoginViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/15.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController , UITextFieldDelegate , HttpDataProtocol{

    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var phoneText: UITextField!
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var QQButton: UIButton!
    @IBOutlet weak var WXButton: UIButton!
    
    var httpController = HttpController()
    var loadingView : UIView?
    var authOptions = ShareSDK.authOptionsWithAutoAuth(true, allowCallback: true, scopes: nil, powerByHidden: true, followAccounts: nil, authViewStyle: SSAuthViewStyleModal, viewDelegate: nil, authManagerViewDelegate: nil)
    var userPhone : String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        loadingView = LoadingView(self)
        phoneText.delegate = self
        passwordText.delegate = self
        
        if userPhone != nil{
            phoneText.text = userPhone
        }
        
        passwordText.secureTextEntry = true
        loginButton.layer.masksToBounds = true
        loginButton.layer.cornerRadius = 5
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        
        if versionStatus == true{
            QQButton.hidden = false
            WXButton.hidden = false
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("PasswordLogin")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("PasswordLogin")
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
                vc.isLogin = true
                self.presentViewController(vc, animated: true, completion: nil)
            }else{
                UIAlertView(title: "登录失败", message: "手机号或密码错误", delegate: self, cancelButtonTitle: "确认").show()
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    @IBAction func passwordLogin(sender: UIButton) {
        phoneText.resignFirstResponder()
        passwordText.resignFirstResponder()
        if !isTelNumber(phoneText.text!){
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else if passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18{
            SelfAlertView(self, alertText: "密码应在6-18位", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else if NSString(string: passwordText.text!).rangeOfString(" ").length > 0{
            SelfAlertView(self, alertText: "密码中不能含有空格", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            self.view.addSubview(loadingView!)
            user.phone = phoneText.text!
            user.password = passwordText.text!
            let postData = user.getSsoUserLoginString()
            let UserParams = NSDictionary(dictionary: ["postData" : postData])
            self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if(!phoneText.exclusiveTouch || !passwordText.exclusiveTouch){
            phoneText.resignFirstResponder()
            passwordText.resignFirstResponder()
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField == phoneText{
            if !isTelNumber(phoneText.text!){
                SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error ,SelfAlertLevel: SelfAlertLevel.view)
            }
        }else if textField == passwordText{
        }
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
    
    @IBAction func codeLogin(sender: UIButton) {
        let sb = UIStoryboard(name: "LoginAndRegister", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("codeLoginNavigationBar") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    
    @IBAction func backButton(sender: AnyObject) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("accountChoiceViewController") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func close(segue: UIStoryboardSegue){}
}

