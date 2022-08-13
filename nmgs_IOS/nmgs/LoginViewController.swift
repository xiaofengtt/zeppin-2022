//
//  ViewController.swift
//  nmgs
//
//  Created by zeppin on 16/10/17.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, loginViewDelegate{
    
    @IBOutlet weak var selectView: UIView!
    @IBOutlet weak var passwordButton: UIButton!
    @IBOutlet weak var messageButton: UIButton!
    @IBOutlet weak var contentView: UIView!
    
    var passwordView: PasswordLoginView?
    var messageView: MessageLoginView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        selectView.layer.cornerRadius = 5
        selectView.layer.borderWidth = 1
        selectView.layer.borderColor=UIColor.white.cgColor
        
        passwordButton.layer.masksToBounds = true
        passwordButton.layer.cornerRadius = 5
        passwordButton.setTitleColor(UIColor.mobileBlue(), for: UIControlState.selected)
        passwordButton.setTitleColor(UIColor.mobileBlue(), for: UIControlState.highlighted)
        passwordButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControlState.selected)
        passwordButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white.withAlphaComponent(0.6)), for: UIControlState.highlighted)
        messageButton.layer.masksToBounds = true
        messageButton.layer.cornerRadius = 5
        messageButton.setTitleColor(UIColor.mobileBlue(), for: UIControlState.selected)
        messageButton.setTitleColor(UIColor.mobileBlue(), for: UIControlState.highlighted)
        messageButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white), for: UIControlState.selected)
        messageButton.setBackgroundImage(UIImage.imageWithColor(UIColor.white.withAlphaComponent(0.6)), for: UIControlState.highlighted)
        messageButton.isSelected = true
        
        let passwordNib = Bundle.main.loadNibNamed("PasswordLoginView", owner: self, options: nil)
        passwordView = passwordNib?.first as? PasswordLoginView
        passwordView?.isHidden = true
        passwordView?.delegate = self
        let messageNib = Bundle.main.loadNibNamed("MessageLoginView", owner: self, options: nil)
        messageView = messageNib?.first as? MessageLoginView
        messageView?.delegate = self
        contentView.addSubview(passwordView!)
        contentView.addSubview(messageView!)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        messageView?.nsTimer?.invalidate()
        messageView?.nsTimer = nil
        super.viewDidDisappear(animated)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func pushViewController(viewController: UIViewController) {
        self.present(viewController, animated: true, completion: nil)
    }
    
    func pushAlertController(alertController: UIAlertController) {
        self.present(alertController, animated: true, completion: nil)
    }
    
    @IBAction func clickPasswordButton(_ sender: UIButton) {
//        UMSocialManager.default().getUserInfo(with: UMSocialPlatformType.wechatSession, currentViewController: self, completion: {(result,error) in
//            if(result != nil){
//                let userInfo = result as! UMSocialUserInfoResponse
//                self.messageView?.phoneLabel.text = userInfo.name
//                self.messageView?.codeLabel.text = userInfo.gender
//            }
//        })
        passwordButton.isSelected = true
        messageButton.isSelected = false
        passwordView?.isHidden = false
        messageView?.isHidden = true
        passwordView?.phoneLabel.text = messageView?.phoneLabel.text
    }
    
    @IBAction func clickMessageButton(_ sender: UIButton) {
//        UMSocialManager.default().getUserInfo(with: UMSocialPlatformType.sina, currentViewController: self, completion: {(result,error) in
//            if(result != nil){
//                let userInfo = result as! UMSocialUserInfoResponse
//                self.messageView?.phoneLabel.text = userInfo.name
//                self.messageView?.codeLabel.text = userInfo.gender
//            }
//        })
        passwordButton.isSelected = false
        messageButton.isSelected = true
        passwordView?.isHidden = true
        messageView?.isHidden = false
        messageView?.phoneLabel.text = passwordView?.phoneLabel.text
    }
   
}

