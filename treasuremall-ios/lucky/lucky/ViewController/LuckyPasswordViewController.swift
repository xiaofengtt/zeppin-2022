//
//  LuckyPasswordViewController.swift
//  lucky
//  修改密码页
//  Created by Farmer Zhu on 2020/8/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPasswordViewController: UIViewController, UITextFieldDelegate {
    //头
    private var staticHeaderView: LuckyNavigationView!
    //功能区
    private var staticMainView: UIView!
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建功能区
        staticMainView = createMainView()
        self.view.addSubview(staticMainView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        if(globalUserData!.password == ""){
            //无密码 设置密码
            headView.titleLabel.text = NSLocalizedString("Set Password", comment: "")
        }else{
            //有密码 重置
            headView.titleLabel.text = NSLocalizedString("Reset Password", comment: "")
        }
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitle(NSLocalizedString("Save", comment: ""), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(save), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建功能区
    func createMainView() -> UIView {
        let paddingLeft: CGFloat = 10 * screenScale
        
        let mainView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        var newPasswordInputOrigin = CGPoint.zero
        
        if(globalUserData!.password != ""){
            //有密码
            //原密码
            let oldLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: mainView.frame.width/2 - paddingLeft, height: 50 * screenScale))
            oldLabel.text = NSLocalizedString("Old Password", comment: "")
            oldLabel.textColor = UIColor.fontBlack()
            oldLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
            mainView.addSubview(oldLabel)
            
            //忘记密码按钮
            let forgotLabel = UILabel()
            forgotLabel.isUserInteractionEnabled = true
            forgotLabel.text = NSLocalizedString("Forgot your password?", comment: "")
            forgotLabel.textColor = UIColor.mainYellow()
            forgotLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
            forgotLabel.sizeToFit()
            forgotLabel.frame = CGRect(x: mainView.frame.width - forgotLabel.frame.width - paddingLeft, y: oldLabel.frame.origin.y, width: forgotLabel.frame.width, height: oldLabel.frame.height)
            forgotLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toRetrieve)))
            mainView.addSubview(forgotLabel)
            
            //原密码输入框
            let oldPasswordInput = LuckyPasswordTextField(frame: CGRect(x: 0, y: oldLabel.frame.origin.y + oldLabel.frame.height, width: mainView.frame.width, height: 50 * screenScale))
            oldPasswordInput.tag = LuckyTagManager.settingTags.oldPasswordInput
            oldPasswordInput.delegate = self
            oldPasswordInput.leftViewMode = UITextField.ViewMode.always
            oldPasswordInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: oldPasswordInput.frame.height))
            oldPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter old password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
            mainView.addSubview(oldPasswordInput)
            
            //新密码
            let newLabel = UILabel(frame: CGRect(x: oldLabel.frame.origin.x, y: oldPasswordInput.frame.origin.y + oldPasswordInput.frame.height, width: oldLabel.frame.width, height: oldLabel.frame.height))
            newLabel.text = NSLocalizedString("New Password", comment: "")
            newLabel.textColor = oldLabel.textColor
            newLabel.font = oldLabel.font
            mainView.addSubview(newLabel)
            
            newPasswordInputOrigin = CGPoint(x: 0, y: newLabel.frame.origin.y + newLabel.frame.height)
        }
        
        //新密码输入框
        let newPasswordInput = LuckyPasswordTextField(frame: CGRect(origin: newPasswordInputOrigin, size: CGSize(width: mainView.frame.width, height: 50 * screenScale)))
        newPasswordInput.tag = LuckyTagManager.settingTags.newPasswordInput
        newPasswordInput.delegate = self
        newPasswordInput.leftViewMode = UITextField.ViewMode.always
        newPasswordInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: newPasswordInput.frame.height))
        newPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter new password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        mainView.addSubview(newPasswordInput)
        
        //确认密码输入框
        let confirmPasswordInput = LuckyPasswordTextField(frame: CGRect(x: newPasswordInput.frame.origin.x, y: newPasswordInput.frame.origin.y + newPasswordInput.frame.height + 1 * screenScale, width: newPasswordInput.frame.width, height: newPasswordInput.frame.height))
        confirmPasswordInput.tag = LuckyTagManager.settingTags.confirmPasswordInput
        confirmPasswordInput.delegate = self
        confirmPasswordInput.leftViewMode = UITextField.ViewMode.always
        confirmPasswordInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: confirmPasswordInput.frame.height))
        confirmPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter new password again", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        mainView.addSubview(confirmPasswordInput)
        
        //密码规则提示
        let messageLabel = UILabel(frame: CGRect(x: paddingLeft, y: confirmPasswordInput.frame.origin.y + confirmPasswordInput.frame.height, width: mainView.frame.width - paddingLeft*2, height: 40 * screenScale))
        messageLabel.numberOfLines = 2
        messageLabel.text = NSLocalizedString("Password must be 8-16 characters and contain both numbers and letters", comment: "")
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        mainView.addSubview(messageLabel)
        
        return mainView
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(str.count > 16){
            //限长16
            return false
        }
        
        if(textField.isSecureTextEntry){
            //密文 设值
            textField.text = str
            return false
        }
        
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去忘记密码页
    @objc func toRetrieve(){
        let vc = LuckyRetrieveViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //提交保存
    @objc func save(){
        self.view.endEditing(true)
        let newPwdInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.newPasswordInput) as! LuckyPasswordTextField
        let confirmPwdInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.confirmPasswordInput) as! LuckyPasswordTextField
        newPwdInput.isSelected = false
        confirmPwdInput.isSelected = false
        
        let newPwd = newPwdInput.text!
        let confirmPwd = confirmPwdInput.text!
        
        //原密码
        var oldPwd = ""
        if(staticMainView.viewWithTag(LuckyTagManager.settingTags.oldPasswordInput) != nil){
            let oldPwdInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.oldPasswordInput) as! LuckyPasswordTextField
            oldPwdInput.isSelected = false
            oldPwd = oldPwdInput.text!
            if("" == oldPwd){
                //原密码为空
                oldPwdInput.isSelected = true
                LuckyAlertView(title: NSLocalizedString("Please enter all the mandatory field", comment: "")).showByTime(time: 2)
                return
            }
            if(!LuckyUtils.checkPassword(password: oldPwd)){
                //原密码不符合规范
                oldPwdInput.isSelected = true
                LuckyAlertView(title: NSLocalizedString("Incorrect old password", comment: "")).showByTime(time: 2)
                return
            }
        }
        
        if("" == newPwd || "" == confirmPwd){
            //新密码或确认密码为空
            newPwdInput.isSelected = true
            confirmPwdInput.isSelected = true
            LuckyAlertView(title: NSLocalizedString("Please enter all the mandatory field", comment: "")).showByTime(time: 2)
            return
        }
        
        if(!LuckyUtils.checkPassword(password: newPwd)){
            //新密码不符合规范
            newPwdInput.isSelected = true
            confirmPwdInput.isSelected = true
            LuckyAlertView(title: NSLocalizedString("Password must be 8-16 characters and contain both numbers and letters", comment: "")).showByTime(time: 2)
            return
        }
        
        if(newPwd != confirmPwd){
            //新密码与确认密码不同
            newPwdInput.isSelected = true
            confirmPwdInput.isSelected = true
            LuckyAlertView(title: NSLocalizedString("Passwords do not match", comment: "")).showByTime(time: 2)
            return
        }
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/user/editPwd", params: ["pwd": LuckyEncodingUtil.getBase64(LuckyEncodingUtil.getDes(oldPwd)), "newPwd": LuckyEncodingUtil.getBase64(LuckyEncodingUtil.getDes(newPwd))], success: { (data) in
            //修改成功返回上一页
            LuckyAlertView(title: NSLocalizedString("Password set successfully", comment: "")).showByTime(time: 2)
            self.navigationController?.popViewController(animated: true)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
}
