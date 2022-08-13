//
//  ChangeBindCodeViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/3.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ChangeBindCodeViewController: UIViewController , UITextFieldDelegate ,HttpDataProtocol{
    
    @IBOutlet weak var codeButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var codeText: UITextField!
    
    var httpController = HttpController()
    var loadingView : UIView?
    var codeTime : Int?
    var nsTimer : NSTimer?
    var phone : String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        codeText.delegate = self
        httpController.delegate = self
        codeButton.layer.masksToBounds = true
        codeButton.layer.cornerRadius = 5
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        codeButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        nextButton.layer.masksToBounds = true
        nextButton.layer.cornerRadius = 5
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("ChangeBindCode")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("ChangeBindCode")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "userVerify"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                user.phone = phone
                let userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                let sb = UIStoryboard(name: "Menu", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("userInformationNavigationBar") as! UINavigationController
                self.presentViewController(vc, animated: true, completion: nil)
            }else{
                UIAlertView(title: "绑定失败", message: dataDictionary.objectForKey("Message") as? String, delegate: self, cancelButtonTitle: "确认").show()
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
    
    @IBAction func bindButton(sender: UIButton) {
        if codeText.text! == ""{
            SelfAlertView(self, alertText: "请输入验证码", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            self.view.addSubview(loadingView!)
            let postData = getSsoUserCodeLoginString()
            let UserParams = NSDictionary(dictionary: ["postData" : postData])
            self.httpController.postNSDataByParams("userVerify", paramsDictionary: UserParams)
        }
    }
    
    func getSsoUserCodeLoginString() -> String{
        var json = "{\"type\":5,\"data\":{"
        json += "\"phone\":\(phone)"
        json += ",\"user.id\":\(user.id)"
        json += ",\"code\":\(codeText.text!)"
        json += "}}"
        return json
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if !codeText.exclusiveTouch{
            codeText.resignFirstResponder()
        }
    }
    
    @IBAction func codeButton(sender: UIButton) {
        let codeParams = NSDictionary(dictionary: ["mobile" : String(phone) , "check" : "1"])
        httpController.getNSDataByParams("sendCode", paramsDictionary: codeParams)
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