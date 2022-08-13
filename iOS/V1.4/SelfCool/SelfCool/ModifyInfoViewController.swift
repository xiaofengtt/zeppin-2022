//
//  ModifyInfoViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/30.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ModifyInfoViewController: UIViewController , UITextFieldDelegate , HttpDataProtocol{
    
    var httpController = HttpController()
    var model : String?
    
    @IBOutlet weak var modifyTextField: UITextField!
    @IBOutlet weak var submitButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        modifyTextField.delegate = self
        submitButton.layer.masksToBounds = true
        submitButton.layer.cornerRadius = 5
        submitButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        submitButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        
        if model == "name"{
            self.navigationController?.title = "修改昵称"
            modifyTextField.placeholder = "昵称名"
            modifyTextField.text = user.name.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        }else if model == "profession"{
            self.navigationController?.title = "修改职业"
            modifyTextField.placeholder = "职业名"
            modifyTextField.text = user.profession.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("ModifyInfo")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("ModifyInfo")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
    }
    
    
    @IBAction func submitButton(sender: UIButton) {
        if modifyTextField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 32{
            SelfAlertView(self, alertText: "不能超过16个汉字", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            if model == "name"{
                user.name = modifyTextField.text!.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
                let userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                let updateParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "nickname" : String(user.name)])
                httpController.getNSDataByParams("userUpdate", paramsDictionary: updateParams)
            }else if model == "profession"{
                user.profession = modifyTextField.text!.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
                let userDict = user.getDictionary()
                LocalDataController.writeLocalData("user", data: userDict)
                let updateParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "professional" : String(user.profession)])
                httpController.getNSDataByParams("userUpdate", paramsDictionary: updateParams)
            }
            self.navigationController?.popViewControllerAnimated(true)
        }
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        if textField.text!.lengthOfBytesUsingEncoding(NSUTF8StringEncoding) > 32{
            SelfAlertView(self, alertText: "不能超过16个汉字", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }
    }
    
     override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if !modifyTextField.exclusiveTouch{
            modifyTextField.resignFirstResponder()
        }
    }
}