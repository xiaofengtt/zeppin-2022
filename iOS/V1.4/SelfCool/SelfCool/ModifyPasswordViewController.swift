//
//  ModifyPasswordViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/25.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ModifyPasswordViewController: UIViewController , UITextFieldDelegate , HttpDataProtocol{
    
    var httpController = HttpController()
    @IBOutlet weak var completeButton: UIButton!
    @IBOutlet weak var passwordText: UITextField!
    @IBOutlet weak var confirmPasswordText: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        passwordText.delegate = self
        confirmPasswordText.delegate = self
        
        completeButton.layer.masksToBounds = true
        completeButton.layer.cornerRadius = 5
        completeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        completeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        passwordText.secureTextEntry = true
        confirmPasswordText.secureTextEntry = true
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("UserModifyPassword")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserModifyPassword")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userUpdate"{
            let status = dataDictionary.objectForKey("Status") as? String
            if status == "success"{
                let sb = UIStoryboard(name: "LoginAndRegister", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("loginViewController") as! LoginViewController
                self.navigationController?.pushViewController(vc, animated: true)
                vc.userPhone = user.phone
            }else{
                UIAlertView(title: "网络异常,修改密码失败", message: nil, delegate: self, cancelButtonTitle: "确认").show()
            }
        }
    }
    
    @IBAction func completeButton(sender: UIButton) {
        confirmPasswordText.resignFirstResponder()
        passwordText.resignFirstResponder()
        if passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || passwordText.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18{
            SelfAlertView(self, alertText: "密码应在6-18位", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            completeButton.enabled = false
        }else if NSString(string: passwordText.text!).rangeOfString(" ").length > 0 {
            SelfAlertView(self, alertText: "密码中不能含有空格", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            completeButton.enabled = false
        }else if passwordText.text! != confirmPasswordText.text!{
            SelfAlertView(self, alertText: "密码输入不一致", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            completeButton.enabled = false
        }else{
            user.password = passwordText.text!
            let updateParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "password" : String(user.password)])
            httpController.getNSDataByParams("userUpdate", paramsDictionary: updateParams)
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField == passwordText{
            if textField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || textField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18{
                SelfAlertView(self, alertText: "密码应在6-18位", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            }else if NSString(string: textField.text!).rangeOfString(" ").length > 0 {
                SelfAlertView(self, alertText: "密码中不能含有空格", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            }else if passwordText.text! != confirmPasswordText.text!{
                if confirmPasswordText.text! != ""{
                    SelfAlertView(self, alertText: "密码输入不一致", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
                }
            }
        }else if textField == confirmPasswordText{
            if passwordText.text! != confirmPasswordText.text!{
                SelfAlertView(self, alertText: "密码输入不一致", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
            }else if textField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) < 6 || textField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 18 || NSString(string: textField.text!).rangeOfString(" ").length > 0 {
            }
        }
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if(!passwordText.exclusiveTouch || !confirmPasswordText.exclusiveTouch){
            passwordText.resignFirstResponder()
            confirmPasswordText.resignFirstResponder()
        }
    }
}