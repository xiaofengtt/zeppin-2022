//
//  ChangeBindViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/3.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ChangeBindViewController: UIViewController , UITextFieldDelegate{
    
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var phoneText: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        phoneText.delegate = self
        nextButton.layer.masksToBounds = true
        nextButton.layer.cornerRadius = 5
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        nextButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("ChangeBind")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("ChangeBind")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        if !phoneText.exclusiveTouch {
            self.phoneText.resignFirstResponder()
        }
    }
    @IBAction func nextButton(sender: UIButton) {
        if !isTelNumber(phoneText.text!){
            SelfAlertView(self, alertText: "请输入正确的手机号", SelfAlertImageStyle: SelfAlertViewImageStyle.error , SelfAlertLevel: SelfAlertLevel.view)
        }else if phoneText.text! == user.phone{
            SelfAlertView(self, alertText: "与当前绑定手机号相同", SelfAlertImageStyle: SelfAlertViewImageStyle.warning , SelfAlertLevel: SelfAlertLevel.view)
        }else{
            let sb = UIStoryboard(name: "Menu", bundle: nil)
            let vc = sb.instantiateViewControllerWithIdentifier("changeBindCodeViewController") as! ChangeBindCodeViewController
            vc.phone = phoneText.text!
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @IBAction func close(segue: UIStoryboardSegue){}
}