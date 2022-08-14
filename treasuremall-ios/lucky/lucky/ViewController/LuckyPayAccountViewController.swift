//
//  LuckyPayAccountViewController.swift
//  lucky
//  设置paypal账户
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPayAccountViewController: UIViewController, UITextFieldDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //显示页
    private var staticShowView: UIView?
    //修改页
    private var staticEditView: UIView?
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //创建显示页
        self.staticShowView = self.createShowView()
        self.view.addSubview(self.staticShowView!)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("PayPal Account", comment: "")
        return headView
    }
    
    //创建显示页
    func createShowView() -> UIView {
        let showView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        if(globalUserAccount!.paypalAccount == ""){
            //未设置paypal账户
            let addView = LuckyAddNewView(frame: CGRect(x: 10 * screenScale, y: 20 * screenScale, width: showView.frame.width - 20 * screenScale, height: 140 * screenScale), title: NSLocalizedString("Add your PayPal account", comment: ""))
            //去绑定按钮
            let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: addView.frame.size))
            button.addTarget(self, action: #selector(toAdd), for: UIControl.Event.touchUpInside)
            addView.addSubview(button)
            showView.addSubview(addView)
        }else{
            //已设置paypal账户 显示账户
            let contentView = UIView(frame: CGRect(x: 10 * screenScale, y: 20 * screenScale, width: showView.frame.width - 20 * screenScale, height: 140 * screenScale))
            contentView.backgroundColor = UIColor.white
            contentView.layer.masksToBounds = true
            contentView.layer.cornerRadius = 4 * screenScale
            
            let payImageView = UIImageView(frame: CGRect(x: (contentView.frame.width - 106 * screenScale)/2, y: 20 * screenScale, width: 106 * screenScale, height: 26 * screenScale))
            payImageView.image = UIImage(named: "image_pay_band")
            contentView.addSubview(payImageView)

            let userImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: payImageView.frame.origin.y + payImageView.frame.height + 30 * screenScale, width: 26 * screenScale, height: 26 * screenScale))
            userImageView.image = UIImage(named: "image_pay_icon")
            contentView.addSubview(userImageView)
            
            let accountLabel = UILabel(frame: CGRect(x: userImageView.frame.origin.x + userImageView.frame.width + 12 * screenScale, y: userImageView.frame.origin.y, width: contentView.frame.width - (userImageView.frame.origin.x + userImageView.frame.width + 22 * screenScale), height: userImageView.frame.height))
            accountLabel.text = globalUserAccount!.paypalAccount
            accountLabel.textColor = UIColor.fontBlack()
            accountLabel.font = UIFont.mediumFont(size: 20 * screenScale)
            contentView.addSubview(accountLabel)
            showView.addSubview(contentView)
            
            //去修改按钮
            let button = UIButton(frame: CGRect(x: contentView.frame.origin.x, y: contentView.frame.origin.y + contentView.frame.height + 40 * screenScale, width: contentView.frame.width, height: 48 * screenScale))
            button.layer.masksToBounds = true
            button.layer.cornerRadius = 6 * screenScale
            button.backgroundColor = UIColor.mainYellow()
            button.setTitle(NSLocalizedString("Change", comment: ""), for: UIControl.State.normal)
            button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            button.addTarget(self, action: #selector(toEdit), for: UIControl.Event.touchUpInside)
            showView.addSubview(button)
        }
        return showView
    }
    
    //创建修改账户页
    func createEditView(flagEdit: Bool) -> UIView{
        let editView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        editView.backgroundColor = UIColor.white
        
        let payImageView = UIImageView(frame: CGRect(x: (editView.frame.width - 106 * screenScale)/2, y: 40 * screenScale, width: 106 * screenScale, height: 26 * screenScale))
        payImageView.image = UIImage(named: "image_pay_band")
        editView.addSubview(payImageView)
        
        let userImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: payImageView.frame.origin.y + payImageView.frame.height + 40 * screenScale, width: 26 * screenScale, height: 26 * screenScale))
        userImageView.image = UIImage(named: "image_pay_icon")
        editView.addSubview(userImageView)
        
        //账号输入框
        let accountInput = UITextField(frame: CGRect(x: userImageView.frame.origin.x + userImageView.frame.width + 12 * screenScale, y: userImageView.frame.origin.y - 10 * screenScale, width: editView.frame.width - (userImageView.frame.origin.x + userImageView.frame.width + 22 * screenScale), height: userImageView.frame.height + 20 * screenScale))
        accountInput.tag = LuckyTagManager.accountTags.payAccountInput
        accountInput.delegate = self
        if(flagEdit && globalUserAccount!.paypalAccount != ""){
            //修改模式 且 有paypal账户 默认值
            accountInput.text = globalUserAccount!.paypalAccount
        }
        accountInput.tintColor = UIColor.mainYellow()
        accountInput.keyboardType = UIKeyboardType.emailAddress
        accountInput.autocapitalizationType = UITextAutocapitalizationType.none
        accountInput.clearButtonMode = UITextField.ViewMode.whileEditing
        accountInput.textColor = UIColor.fontBlack()
        accountInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        accountInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter youy PayPal account", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        editView.addSubview(accountInput)
        
        let spaceLine = CALayer()
        spaceLine.frame = CGRect(x: 0, y: userImageView.frame.origin.y + userImageView.frame.height + 12 * screenScale, width: editView.frame.width, height: 1)
        spaceLine.backgroundColor = UIColor.backgroundGray().cgColor
        editView.layer.addSublayer(spaceLine)
        
        //修改按钮
        let button = UIButton(frame: CGRect(x: 10 * screenScale, y: spaceLine.frame.origin.y + spaceLine.frame.height + 40 * screenScale, width: editView.frame.width - 20 * screenScale, height: 48 * screenScale))
        button.tag = LuckyTagManager.accountTags.paySubmitButton
        button.isEnabled = false
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        button.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        button.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        if(flagEdit && globalUserAccount!.paypalAccount != ""){
            //修改模式
            button.setTitle(NSLocalizedString("Confirm", comment: ""), for: UIControl.State.normal)
        }else{
            //绑定模式
            button.setTitle(NSLocalizedString("Save", comment: ""), for: UIControl.State.normal)
        }
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.disabled)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(submit), for: UIControl.Event.touchUpInside)
        editView.addSubview(button)
        
        return editView
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.accountTags.payAccountInput){
            //PayPal账户输入框
            if(str.count > 40){
                //限长 40
                return false
            }
            
            //判断绑定按钮是否可用
            let button = staticEditView!.viewWithTag(LuckyTagManager.accountTags.paySubmitButton) as! UIButton
            if(LuckyUtils.checkEmail(email: str)){
                //email格式
                button.isEnabled = true
            }else{
                button.isEnabled = false
            }
        }
        return true
    }
    
    //清空PayPal输入框 修改按钮不可用
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        if(textField.tag == LuckyTagManager.accountTags.payAccountInput){
            let button = staticEditView!.viewWithTag(LuckyTagManager.accountTags.paySubmitButton) as! UIButton
            button.isEnabled = false
        }
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去绑定
    @objc func toAdd(){
        self.staticEditView?.removeFromSuperview()
        self.staticEditView = createEditView(flagEdit: false)
        self.view.addSubview(staticEditView!)
    }
    
    //去修改
    @objc func toEdit(){
        self.staticEditView?.removeFromSuperview()
        self.staticEditView = createEditView(flagEdit: true)
        self.view.addSubview(staticEditView!)
    }
    
    //提交修改
    @objc func submit(){
        self.view.endEditing(true)
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        
        let accountInput = staticEditView!.viewWithTag(LuckyTagManager.accountTags.payAccountInput) as! UITextField
        let account = accountInput.text!
        
        LuckyHttpManager.postWithToken("front/card/add", params: ["accountNumber" : account], success: { (data) in
            //成功返回上一页
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            self.navigationController?.popViewController(animated: true)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
}
