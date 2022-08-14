//
//  LuckyPayCreditViewController.swift
//  lucky
//  马甲信用卡支付（伪）
//  Created by Farmer Zhu on 2020/9/30.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPayCreditViewController: UIViewController, UITextFieldDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //功能区
    private var staticBodyView: UIView!
    
    //金额参数
    var capitalAccount: String!
    var amount: Double!
    var dAmount: Double!
    var isFree: Bool!
    
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
        staticBodyView = createBodyView()
        self.view.addSubview(staticBodyView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Checkout", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建功能区
    func createBodyView() -> UIView{
        let bodyView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        //卡号
        let accountLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: bodyView.frame.width - 20 * screenScale, height: 46 * screenScale))
        accountLabel.text = NSLocalizedString("Card Number", comment: "")
        accountLabel.textColor = UIColor.fontBlack()
        accountLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        bodyView.addSubview(accountLabel)
        
        let accountInput = UITextField(frame: CGRect(x: 0, y: accountLabel.frame.origin.y + accountLabel.frame.height, width: bodyView.frame.width, height: 48 * screenScale))
        accountInput.tag = LuckyTagManager.chargeTags.creditAccountInput
        accountInput.delegate = self
        accountInput.backgroundColor = UIColor.white
        accountInput.leftViewMode = UITextField.ViewMode.always
        accountInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: accountInput.frame.height))
        accountInput.tintColor = UIColor.mainYellow()
        accountInput.keyboardType = UIKeyboardType.numberPad
        accountInput.clearButtonMode = UITextField.ViewMode.whileEditing
        accountInput.textColor = UIColor.fontBlack()
        accountInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        accountInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter the card number", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        bodyView.addSubview(accountInput)
        
        //有效期
        let dateLabel = UILabel(frame: CGRect(x: accountLabel.frame.origin.x, y: accountInput.frame.origin.y + accountInput.frame.height, width: accountLabel.frame.width, height: accountLabel.frame.height))
        dateLabel.text = NSLocalizedString("Expiry Date", comment: "")
        dateLabel.textColor = accountLabel.textColor
        dateLabel.font = accountLabel.font
        bodyView.addSubview(dateLabel)
        
        //月
        let monthInput = UITextField(frame: CGRect(x: 0, y: dateLabel.frame.origin.y + dateLabel.frame.height, width: bodyView.frame.width/2 - 10 * screenScale, height: accountInput.frame.height))
        monthInput.tag = LuckyTagManager.chargeTags.creditMonthInput
        monthInput.delegate = self
        monthInput.backgroundColor = UIColor.white
        monthInput.leftViewMode = UITextField.ViewMode.always
        monthInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: accountInput.frame.height))
        monthInput.tintColor = accountInput.tintColor
        monthInput.keyboardType = UIKeyboardType.numberPad
        monthInput.clearButtonMode = accountInput.clearButtonMode
        monthInput.textColor = accountInput.textColor
        monthInput.font = accountInput.font
        monthInput.attributedPlaceholder = NSAttributedString(string: "08", attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        bodyView.addSubview(monthInput)
        
        //年
        let yearInput = UITextField(frame: CGRect(x: bodyView.frame.width/2, y: monthInput.frame.origin.y, width: monthInput.frame.width, height: accountInput.frame.height))
        yearInput.tag = LuckyTagManager.chargeTags.creditYearInput
        yearInput.delegate = self
        yearInput.backgroundColor = UIColor.white
        yearInput.leftViewMode = UITextField.ViewMode.always
        yearInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: accountInput.frame.height))
        yearInput.tintColor = accountInput.tintColor
        yearInput.keyboardType = UIKeyboardType.numberPad
        yearInput.clearButtonMode = accountInput.clearButtonMode
        yearInput.textColor = accountInput.textColor
        yearInput.font = accountInput.font
        yearInput.attributedPlaceholder = NSAttributedString(string: "22", attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        bodyView.addSubview(yearInput)
        
        //CVC
        let cvcLabel = UILabel(frame: CGRect(x: accountLabel.frame.origin.x, y: monthInput.frame.origin.y + monthInput.frame.height, width: accountLabel.frame.width, height: accountLabel.frame.height))
        cvcLabel.text = NSLocalizedString("CVC", comment: "")
        cvcLabel.textColor = accountLabel.textColor
        cvcLabel.font = accountLabel.font
        bodyView.addSubview(cvcLabel)
        
        let cvcInput = UITextField(frame: CGRect(x: 0, y: cvcLabel.frame.origin.y + cvcLabel.frame.height, width: accountInput.frame.width, height: accountInput.frame.height))
        cvcInput.tag = LuckyTagManager.chargeTags.creditCvcInput
        cvcInput.delegate = self
        cvcInput.backgroundColor = UIColor.white
        cvcInput.leftViewMode = UITextField.ViewMode.always
        cvcInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: accountInput.frame.height))
        cvcInput.tintColor = accountInput.tintColor
        cvcInput.keyboardType = UIKeyboardType.numberPad
        cvcInput.clearButtonMode = accountInput.clearButtonMode
        cvcInput.textColor = accountInput.textColor
        cvcInput.font = accountInput.font
        cvcInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter the CVC", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        bodyView.addSubview(cvcInput)
        
        //zipCode
        let zipCodeLabel = UILabel(frame: CGRect(x: accountLabel.frame.origin.x, y: cvcInput.frame.origin.y + cvcInput.frame.height, width: accountLabel.frame.width, height: accountLabel.frame.height))
        zipCodeLabel.text = NSLocalizedString("ZIP Code", comment: "")
        zipCodeLabel.textColor = accountLabel.textColor
        zipCodeLabel.font = accountLabel.font
        bodyView.addSubview(zipCodeLabel)
        
        let zipCodeInput = UITextField(frame: CGRect(x: 0, y: zipCodeLabel.frame.origin.y + zipCodeLabel.frame.height, width: accountInput.frame.width, height: accountInput.frame.height))
        zipCodeInput.tag = LuckyTagManager.chargeTags.creditZipCodeInput
        zipCodeInput.delegate = self
        zipCodeInput.backgroundColor = UIColor.white
        zipCodeInput.leftViewMode = UITextField.ViewMode.always
        zipCodeInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: accountInput.frame.height))
        zipCodeInput.tintColor = accountInput.tintColor
        zipCodeInput.keyboardType = UIKeyboardType.numberPad
        zipCodeInput.clearButtonMode = accountInput.clearButtonMode
        zipCodeInput.textColor = accountInput.textColor
        zipCodeInput.font = accountInput.font
        zipCodeInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter the ZIP code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        bodyView.addSubview(zipCodeInput)
        
        //提交按钮
        let button = UIButton(frame: CGRect(x: 10 * screenScale, y: zipCodeInput.frame.origin.y + zipCodeInput.frame.height + 40 * screenScale, width: bodyView.frame.width - 20 * screenScale, height: 48 * screenScale))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle("Pay Now", for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(pay), for: UIControl.Event.touchUpInside)
        bodyView.addSubview(button)
        
        return bodyView
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.chargeTags.creditAccountInput){
            //信用卡号
            if(str.length > 30){
                //限长 30
                return false
            }
        }else if(textField.tag == LuckyTagManager.chargeTags.creditMonthInput){
            //有效期 月
            if(str.length > 2){
                return false
            }
        }else if(textField.tag == LuckyTagManager.chargeTags.creditYearInput){
            //有效期 年
            if(str.length > 2){
                return false
            }
        }else if(textField.tag == LuckyTagManager.chargeTags.creditCvcInput){
            //CVC
            if(str.length > 3){
                return false
            }
        }else if(textField.tag == LuckyTagManager.chargeTags.creditZipCodeInput){
            //ZipCode
            if(str.length > 10){
                return false
            }
        }
        return true
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //支付
    @objc func pay(){
        let account = (staticBodyView.viewWithTag(LuckyTagManager.chargeTags.creditAccountInput) as! UITextField).text!
        let month = (staticBodyView.viewWithTag(LuckyTagManager.chargeTags.creditMonthInput) as! UITextField).text!
        let year = (staticBodyView.viewWithTag(LuckyTagManager.chargeTags.creditYearInput) as! UITextField).text!
        let cvc = (staticBodyView.viewWithTag(LuckyTagManager.chargeTags.creditCvcInput) as! UITextField).text!
        let zipCode = (staticBodyView.viewWithTag(LuckyTagManager.chargeTags.creditZipCodeInput) as! UITextField).text!
        
        //必填校验
        if(account == "" || month == "" || year == "" || cvc == "" || zipCode == ""){
            LuckyAlertView(title: NSLocalizedString("Please enter all the mandatory field", comment: "")).showByTime(time: 2)
            return
        }
        
        //长度校验
        if(account.length < 10){
            LuckyAlertView(title: "\(NSLocalizedString("Incorrect", comment: "")) \(NSLocalizedString("Card Number", comment: ""))").showByTime(time: 2)
            return
        }
        if(month.length != 2 || year.length != 2){
            LuckyAlertView(title: "\(NSLocalizedString("Incorrect", comment: "")) \(NSLocalizedString("Expiry Date", comment: ""))").showByTime(time: 2)
            return
        }
        if(cvc.length != 3){
            LuckyAlertView(title: "\(NSLocalizedString("Incorrect", comment: "")) \(NSLocalizedString("CVC", comment: ""))").showByTime(time: 2)
            return
        }
        if(zipCode.length < 2){
            LuckyAlertView(title: "\(NSLocalizedString("Incorrect", comment: "")) \(NSLocalizedString("ZIP Code", comment: ""))").showByTime(time: 2)
            return
        }
        
        //提交数据
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/userRecharge/byCreditcard", params: ["capitalAccount": String(capitalAccount), "amount": LuckyEncodingUtil.getBase64(String(amount)), "dAmount": LuckyEncodingUtil.getBase64(String(dAmount)), "isFree": (isFree ? "true" : "false")]) { (data) in
//            LuckyAlertView(title: NSLocalizedString("Top Up successfully", comment: "")).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            self.navigationController?.popToRootViewController(animated: true)
        } fail: { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }

    }
}
