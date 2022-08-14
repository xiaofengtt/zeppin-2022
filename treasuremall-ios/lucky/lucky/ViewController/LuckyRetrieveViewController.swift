//
//  LuckyRetrieveViewController.swift
//  lucky
//  找回密码
//  Created by Farmer Zhu on 2020/8/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRetrieveViewController: UIViewController, UITextFieldDelegate, UIPickerViewDataSource, UIPickerViewDelegate {
    
    //头
    private var staticHeaderView: UIView!
    //验证
    private var staticCheckView: UIView!
    //重设密码
    private var staticResetView: UIView!
    //区号选择
    private var staticCountryView: UIView!
    private var staticCountryPicker: UIPickerView!
    
    //选中国家
    var selectedCountry: Int? = nil
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //初始化选中区号
        if(globalSelectedCountry != nil && globalCountryList.count > 0){
            for index in 0 ..< globalCountryList.count{
                if(globalCountryList[index].uuid == globalSelectedCountry){
                    selectedCountry = index
                    break
                }
            }
        }
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建验证
        staticCheckView = createCheckView()
        self.view.addSubview(staticCheckView)
        //创建重设密码
        staticResetView = createResetView()
        staticResetView.isHidden = true
        self.view.addSubview(staticResetView)
        //创建区号选择
        staticCountryView = createCountryView()
        self.view.addSubview(staticCountryView)
    }
    
    //创建头
    func createHeaderView() -> UIView {
        let headView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: statusBarHeight + 40 * screenScale)))
        //返回按钮
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight, width: 100 * screenScale, height: 40 * screenScale))
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_black")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        
        //客服按钮
        let customerButton = UIButton(frame: CGRect(x: headView.frame.width - 150 * screenScale, y: backButton.frame.origin.y, width: 140 * screenScale, height: backButton.frame.height))
        let customerTitleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: customerButton.frame.size))
        customerTitleLabel.text = NSLocalizedString("Customer Service", comment: "")
        customerTitleLabel.textColor = UIColor.fontBlack()
        customerTitleLabel.font = UIFont.boldFont(size: UIFont.fontSizeMiddle() * screenScale)
        customerTitleLabel.textAlignment = NSTextAlignment.right
        customerButton.addSubview(customerTitleLabel)
        customerButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        headView.addSubview(customerButton)
        return headView
    }
    
    //创建验证区
    func createCheckView() -> UIView{
        let checkView = UIView(frame: CGRect(x: 40 * screenScale, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth - 80 * screenScale, height: screenHeight - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))

        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: checkView.frame.width, height: 40 * screenScale))
        titleLabel.text = NSLocalizedString("Retrieve Password", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.left
        checkView.addSubview(titleLabel)
        
        let contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 5 * screenScale, width: titleLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = NSLocalizedString("Use SMS verfication code to change password", comment: "")
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        checkView.addSubview(contentLabel)
        
        //手机号
        let phoneView = UIView(frame: CGRect(x: 0, y: contentLabel.frame.origin.y + contentLabel.frame.height + 25 * screenScale , width: checkView.frame.width, height: 40 * screenScale))
        //选区号按钮
        let areaButton = UIButton(frame: CGRect(x: 0, y: 0, width: 40 * screenScale, height: phoneView.frame.height))
        areaButton.tag = LuckyTagManager.retrieveTags.areaButton
        areaButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: -3 * screenScale)
        areaButton.setImage(UIImage(named: "image_arrow_down"), for: UIControl.State.normal)
        areaButton.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
        if(globalCountryList.count > 0){
            if(selectedCountry != nil && globalCountryList.count > selectedCountry!){
                areaButton.setTitle("+\(globalCountryList[selectedCountry!].telCode)", for: UIControl.State.normal)
            }else{
                areaButton.setTitle("+\(globalCountryList[0].telCode)", for: UIControl.State.normal)
                selectedCountry = 0
            }
        }else{
            areaButton.setTitle("+", for: UIControl.State.normal)
        }
        areaButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        areaButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        let areaTitleSize = areaButton.intrinsicContentSize
        areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: phoneView.frame.height)
        areaButton.addTarget(self, action: #selector(selectCountry), for: UIControl.Event.touchUpInside)
        phoneView.addSubview(areaButton)
        //手机号输入框
        let mobileInput = UITextField(frame: CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: phoneView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: phoneView.frame.height))
        mobileInput.delegate = self
        mobileInput.leftViewMode = UITextField.ViewMode.always
        mobileInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: mobileInput.frame.height))
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: (mobileInput.frame.height - 16 * screenScale)/2, width: 1, height: 16 * screenScale)
        splitLine.backgroundColor = UIColor.fontBlack().cgColor
        mobileInput.leftView!.layer.addSublayer(splitLine)
        mobileInput.tintColor = UIColor.mainYellow()
        mobileInput.keyboardType = UIKeyboardType.numberPad
        mobileInput.clearButtonMode = UITextField.ViewMode.whileEditing
        mobileInput.tag = LuckyTagManager.retrieveTags.mobileInput
        mobileInput.textColor = UIColor.fontBlack()
        mobileInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        mobileInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter mobile number", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        let phoneBottom = CALayer()
        phoneBottom.frame = CGRect(x: 0, y: phoneView.frame.height - 1, width: phoneView.frame.width, height: 1)
        phoneBottom.backgroundColor = UIColor.fontGray().cgColor
        phoneView.layer.addSublayer(phoneBottom)
        phoneView.addSubview(mobileInput)
        checkView.addSubview(phoneView)
        
        //验证码区
        let codeView = UIView(frame: CGRect(x: 0, y: phoneView.frame.origin.y + phoneView.frame.height + 20 * screenScale , width: checkView.frame.width, height: 40 * screenScale))
        //发送按钮
        let codeButton = LuckyRetrieveCodeSendButton(frame: CGRect(x: codeView.frame.width - 80 * screenScale, y: 0, width: 80 * screenScale, height: codeView.frame.height))
        codeButton.tag = LuckyTagManager.retrieveTags.codeButton
        codeButton.isEnabled = false
        codeButton.addTarget(self, action: #selector(sendCode(_:)), for: UIControl.Event.touchUpInside)
        codeView.addSubview(codeButton)
        //验证码输入框
        let codeInput = UITextField(frame: CGRect(x: 0, y: 0, width: codeButton.frame.origin.x - 10 * screenScale, height: 40 * screenScale))
        codeInput.tag = LuckyTagManager.retrieveTags.codeInput
        codeInput.delegate = self
        codeInput.tintColor = UIColor.mainYellow()
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        codeView.addSubview(codeInput)
        //底分割线
        let codeBottom = CALayer()
        codeBottom.frame = CGRect(x: 0, y: codeView.frame.height - 1, width: codeInput.frame.width, height: 1)
        codeBottom.backgroundColor = UIColor.fontGray().cgColor
        codeView.layer.addSublayer(codeBottom)
        checkView.addSubview(codeView)
        
        //下一步按钮
        let nextButton = UIButton(frame: CGRect(x: 0, y: codeView.frame.origin.y + codeView.frame.height + 46 * screenScale, width: checkView.frame.width, height: 50 * screenScale))
        nextButton.tag = LuckyTagManager.retrieveTags.nextButton
        nextButton.isEnabled = false
        nextButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        nextButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        nextButton.setTitle(NSLocalizedString("Next", comment: ""), for: UIControl.State.normal)
        nextButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        nextButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        nextButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        nextButton.addTarget(self, action: #selector(toReset), for: UIControl.Event.touchUpInside)
        checkView.addSubview(nextButton)
        
        return checkView
    }
    
    //创建重设密码区
    func createResetView() -> UIView{
        let resetView = UIView(frame: CGRect(x: 40 * screenScale, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth - 80 * screenScale, height: screenHeight - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: resetView.frame.width, height: 40 * screenScale))
        titleLabel.text = NSLocalizedString("Reset Password", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.left
        resetView.addSubview(titleLabel)
        
        //新密码
        let newPasswordView = UIView(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 40 * screenScale , width: resetView.frame.width, height: 40 * screenScale))
        let newPasswordInput = UITextField(frame: CGRect(x: 0, y: 0, width: newPasswordView.frame.width - 40 * screenScale, height: 40 * screenScale))
        newPasswordInput.tag = LuckyTagManager.retrieveTags.newPasswordInput
        newPasswordInput.delegate = self
        newPasswordInput.tintColor = UIColor.mainYellow()
        newPasswordInput.keyboardType = UIKeyboardType.asciiCapable
        newPasswordInput.autocapitalizationType = UITextAutocapitalizationType.none
        newPasswordInput.isSecureTextEntry = true
        newPasswordInput.clearButtonMode = UITextField.ViewMode.whileEditing
        newPasswordInput.textColor = UIColor.fontBlack()
        newPasswordInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        newPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        //密文明文切换按钮
        let newPasswordHideButton = UIButton(frame: CGRect(x: newPasswordView.frame.width - 20 * screenScale, y: newPasswordInput.frame.origin.y + 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        newPasswordHideButton.showsTouchWhenHighlighted = false
        newPasswordHideButton.adjustsImageWhenHighlighted = false
        newPasswordHideButton.setImage(UIImage(named: "image_password_show"), for: UIControl.State.normal)
        newPasswordHideButton.setImage(UIImage(named: "image_password_hide"), for: UIControl.State.selected)
        newPasswordHideButton.addTarget(self, action: #selector(hideNewPassword(_ :)), for: UIControl.Event.touchUpInside)
        newPasswordHideButton.isSelected = true
        newPasswordView.addSubview(newPasswordHideButton)
        let newPasswordBottom = CALayer()
        newPasswordBottom.frame = CGRect(x: 0, y: newPasswordView.frame.height - 1, width: newPasswordView.frame.width, height: 1)
        newPasswordBottom.backgroundColor = UIColor.fontGray().cgColor
        newPasswordView.layer.addSublayer(newPasswordBottom)
        newPasswordView.addSubview(newPasswordInput)
        resetView.addSubview(newPasswordView)
        
        //确认密码
        let confirmPasswordView = UIView(frame: CGRect(x: 0, y: newPasswordView.frame.origin.y + newPasswordView.frame.height + 10 * screenScale , width: resetView.frame.width, height: 40 * screenScale))
        let confirmPasswordInput = UITextField(frame: CGRect(x: 0, y: 0, width: confirmPasswordView.frame.width - 40 * screenScale, height: 40 * screenScale))
        confirmPasswordInput.delegate = self
        confirmPasswordInput.tintColor = UIColor.mainYellow()
        confirmPasswordInput.keyboardType = UIKeyboardType.asciiCapable
        confirmPasswordInput.autocapitalizationType = UITextAutocapitalizationType.none
        confirmPasswordInput.isSecureTextEntry = true
        confirmPasswordInput.clearButtonMode = UITextField.ViewMode.whileEditing
        confirmPasswordInput.tag = LuckyTagManager.retrieveTags.confirmPasswordInput
        confirmPasswordInput.textColor = UIColor.fontBlack()
        confirmPasswordInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        confirmPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        //密文明文切换按钮
        let confirmPasswordHideButton = UIButton(frame: CGRect(x: confirmPasswordView.frame.width - 20 * screenScale, y: confirmPasswordInput.frame.origin.y + 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        confirmPasswordHideButton.showsTouchWhenHighlighted = false
        confirmPasswordHideButton.adjustsImageWhenHighlighted = false
        confirmPasswordHideButton.setImage(UIImage(named: "image_password_show"), for: UIControl.State.normal)
        confirmPasswordHideButton.setImage(UIImage(named: "image_password_hide"), for: UIControl.State.selected)
        confirmPasswordHideButton.addTarget(self, action: #selector(hideConfirmPassword(_ :)), for: UIControl.Event.touchUpInside)
        confirmPasswordHideButton.isSelected = true
        confirmPasswordView.addSubview(confirmPasswordHideButton)
        let confirmPasswordBottom = CALayer()
        confirmPasswordBottom.frame = CGRect(x: 0, y: confirmPasswordView.frame.height - 1, width: confirmPasswordView.frame.width, height: 1)
        confirmPasswordBottom.backgroundColor = UIColor.fontGray().cgColor
        confirmPasswordView.layer.addSublayer(confirmPasswordBottom)
        confirmPasswordView.addSubview(confirmPasswordInput)
        resetView.addSubview(confirmPasswordView)
        
        //提交按钮
        let resetButton = UIButton(frame: CGRect(x: 0, y: confirmPasswordView.frame.origin.y + confirmPasswordView.frame.height + 46 * screenScale, width: resetView.frame.width, height: 50 * screenScale))
        resetButton.tag = LuckyTagManager.retrieveTags.resetButton
        resetButton.isEnabled = false
        resetButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        resetButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        resetButton.setTitle(NSLocalizedString("Done", comment: ""), for: UIControl.State.normal)
        resetButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        resetButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        resetButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        resetButton.addTarget(self, action: #selector(reset), for: UIControl.Event.touchUpInside)
        resetView.addSubview(resetButton)
        
        let messageLabel = UILabel(frame: CGRect(x: 0, y: resetButton.frame.origin.y + resetButton.frame.height + 10 * screenScale, width: resetView.frame.width, height: 40 * screenScale))
        messageLabel.numberOfLines = 0
        messageLabel.text = NSLocalizedString("Password must be 8-16 characters and contain both numbers and letters", comment: "")
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        resetView.addSubview(messageLabel)
        return resetView
    }
    
    //创建区号选择区
    func createCountryView() -> UIView{
        let pickerView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        pickerView.isHidden = true
        pickerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //区号选择组件
        staticCountryPicker = UIPickerView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight - 200 * screenScale, width: pickerView.frame.width, height: 200 * screenScale))
        staticCountryPicker.backgroundColor = UIColor.white
        staticCountryPicker.dataSource = self
        staticCountryPicker.delegate = self
        pickerView.addSubview(staticCountryPicker)
        
        let bottomView = UIView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight, width: pickerView.frame.width, height: bottomSafeHeight))
        bottomView.backgroundColor = staticCountryPicker.backgroundColor
        pickerView.addSubview(bottomView)
        
        //头
        let headerView = UIView(frame: CGRect(x: 0, y: staticCountryPicker.frame.origin.y - 50 * screenScale, width: pickerView.frame.width, height: 50 * screenScale))
        headerView.backgroundColor = UIColor.white
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: headerView.frame.width - headerView.frame.height, y: 0, width: headerView.frame.height, height: headerView.frame.height))
        closeButton.setTitle(NSLocalizedString("Done", comment: ""), for: UIControl.State.normal)
        closeButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        closeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        closeButton.addTarget(self, action: #selector(closeCountryPicker), for: UIControl.Event.touchUpInside)
        headerView.addSubview(closeButton)
        
        let headerLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: headerView.frame.width/2, height: headerView.frame.height))
        headerLabel.text = NSLocalizedString("Please Select", comment: "")
        headerLabel.textColor = UIColor.fontBlack()
        headerLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        headerView.addSubview(headerLabel)
        let headerBottomLine = CALayer()
        headerBottomLine.frame = CGRect(x: 0, y: headerView.frame.height - 1, width: headerView.frame.width, height: 1)
        headerBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        headerView.layer.addSublayer(headerBottomLine)
        pickerView.addSubview(headerView)
        
        //关闭按钮
        let hideButton = UIButton(frame: CGRect(x: 0, y: 0, width: pickerView.frame.width, height: headerView.frame.origin.y))
        hideButton.addTarget(self, action: #selector(closeCountryPicker), for: UIControl.Event.touchUpInside)
        pickerView.addSubview(hideButton)
        return pickerView
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        //取输入结果
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.retrieveTags.mobileInput){
            //手机输入框
            if(str.count > 18){
                //限长 18
                return false
            }
            
            let codeButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.codeButton) as! LuckyRetrieveCodeSendButton
            //发送按钮可用判定
            if(str.count > 5 && codeButton.codeTime <= 0){
                codeButton.isEnabled = true
            }else{
                codeButton.isEnabled = false
            }
            
            let codeInput = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.codeInput) as! UITextField
            let nextButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.nextButton) as! UIButton
            //下一步验证按钮可用判定
            if(str.count > 5 && LuckyUtils.checkMobileCode(code: codeInput.text!)){
                nextButton.isEnabled = true
            }else{
                nextButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.retrieveTags.codeInput){
            //验证码输入框
            if(str.count > 4){
                //限长 4
                return false
            }
            
            let mobileInput = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.mobileInput) as! UITextField
            let nextButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.nextButton) as! UIButton
            //下一步按钮可用判定
            if(LuckyUtils.checkMobileCode(code: str) && mobileInput.text!.count > 5){
                nextButton.isEnabled = true
            }else{
                nextButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.retrieveTags.newPasswordInput){
            //新密码输入框
            if(str.count > 16){
                //限长 16
                return false
            }
            
            let confirmInput = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.confirmPasswordInput) as! UITextField
            let resetButton = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.resetButton) as! UIButton
            //判断重置按钮可用 两密码相同 且 符合密码规范
            if(str == confirmInput.text! && LuckyUtils.checkPassword(password: str)){
                resetButton.isEnabled = true
            }else{
                resetButton.isEnabled = false
            }
            
            //更新值
            if(textField.isSecureTextEntry){
                textField.text = str
                return false
            }
        }
        else if(textField.tag == LuckyTagManager.retrieveTags.confirmPasswordInput){
            //确认密码框
            if(str.count > 16){
                //限长 16
                return false
            }
            
            let newInput = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.newPasswordInput) as! UITextField
            let resetButton = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.resetButton) as! UIButton
            //判断重置按钮可用
            if(str == newInput.text! && LuckyUtils.checkPassword(password: str)){
                resetButton.isEnabled = true
            }else{
                resetButton.isEnabled = false
            }
            
            //更新值
            if(textField.isSecureTextEntry){
                textField.text = str
                return false
            }
        }
        return true
    }
    
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        //清空时修改按钮可用状态
        if(textField.tag == LuckyTagManager.retrieveTags.mobileInput){
            let codeButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.codeButton) as! LuckyRetrieveCodeSendButton
            let nextButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.nextButton) as! UIButton
            codeButton.isEnabled = false
            nextButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.retrieveTags.codeInput){
            let nextButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.nextButton) as! UIButton
            nextButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.retrieveTags.newPasswordInput){
            let resetButton = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.resetButton) as! UIButton
            resetButton.isEnabled = false
        }
        else if(textField.tag == LuckyTagManager.retrieveTags.confirmPasswordInput){
            let resetButton = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.resetButton) as! UIButton
            resetButton.isEnabled = false
        }
        return true
    }
    
    //区号选择器
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if(globalCountryList.count > 0){
            return globalCountryList.count
        }else{
            return 1
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
        return 30 * screenScale
    }
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: pickerView.frame.width, height: 30 * screenScale))
        view.backgroundColor = UIColor.white
        
        if(globalCountryList.count > 0){
            //有数据 创建数据行
            let countryLabel = UILabel(frame: CGRect(x: 20 * screenScale, y: 0, width: view.frame.width/2 - 20 * screenScale, height: view.frame.height))
            countryLabel.text = globalCountryList[row].name
            countryLabel.textColor = UIColor.fontBlack()
            countryLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            view.addSubview(countryLabel)
            
            let areaCodeLabel = UILabel(frame: CGRect(x: view.frame.width/2, y: countryLabel.frame.origin.y, width: countryLabel.frame.width, height: countryLabel.frame.height))
            areaCodeLabel.text = "+\(globalCountryList[row].telCode)"
            areaCodeLabel.textColor = UIColor.fontDarkGray()
            areaCodeLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            areaCodeLabel.textAlignment = NSTextAlignment.right
            view.addSubview(areaCodeLabel)
        }
        return view
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if(globalCountryList.count > 0){
            //有数据 选中时更新区号显示
            if let areaButton = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.areaButton) as? UIButton{
                //赋值 并调整区号按钮大小
                areaButton.setTitle("+\(globalCountryList[row].telCode)", for: UIControl.State.normal)
                let areaTitleSize = areaButton.intrinsicContentSize
                areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: areaButton.frame.height)
                
                let mobileInput = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.mobileInput) as! UITextField
                mobileInput.frame = CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: staticCheckView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: areaButton.frame.height)
            }
            selectedCountry = row
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //显示区号选择区
    @objc func selectCountry(){
        self.view.endEditing(true)
        if(staticCountryPicker.numberOfComponents == 1){
            staticCountryPicker.reloadAllComponents()
            if(selectedCountry != nil){
                //有初始选中 设初始选中状态
                staticCountryPicker.selectRow(selectedCountry!, inComponent: 0, animated: false)
            }
        }
        staticCountryView.isHidden = false
    }
    
    //关闭区号选择区
    @objc func closeCountryPicker(){
        staticCountryView.isHidden = true
    }
    
    //新密码 明文/密文切换
    @objc func hideNewPassword(_ sender: UIButton){
        let newPasswordInput = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.newPasswordInput) as! UITextField
        
        newPasswordInput.isSecureTextEntry = !sender.isSelected
        sender.isSelected = !sender.isSelected
    }
    
    //确认密码 明文/密文切换
    @objc func hideConfirmPassword(_ sender: UIButton){
        let confirmPasswordInput = staticResetView.viewWithTag(LuckyTagManager.retrieveTags.confirmPasswordInput) as! UITextField
        
        confirmPasswordInput.isSecureTextEntry = !sender.isSelected
        sender.isSelected = !sender.isSelected
    }
    
    //发送验证码
    @objc func sendCode(_ sender: LuckyRetrieveCodeSendButton){
        self.view.endEditing(true)
        
        if(selectedCountry == nil){
            return
        }
        
        let mobileInput = staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.mobileInput) as! UITextField
        if(mobileInput.text!.count > 5){
            //号码校验通过
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyMobileCodeManager.codeByMobile(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobileInput.text!)", codeType: LuckyMobileCodeManager.MobileCodeType.PASSWORD, success: { (data) in
                //发送成功 再发倒计时
                sender.startTimer()
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (fail) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: fail).showByTime(time: 2)
            }
        }else{
            LuckyAlertView(title: NSLocalizedString("Incorrect mobile number format, please try again", comment: "")).showByTime(time: 2)
        }
    }
    
    //校验验证码
    @objc func toReset(){
        self.view.endEditing(true)
        let mobile = (staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.mobileInput) as! UITextField).text!
        let code = (staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.codeInput) as! UITextField).text!
        
        if(mobile.length > 5 && LuckyUtils.checkMobileCode(code: code)){
            //手机号 验证码符合规范
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyHttpManager.resetPasswordCheck(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", code: code, success: { (data) in
                //校验通过 显示重设新密码
                self.staticCheckView.isHidden = true
                self.staticResetView.isHidden = false
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
    
    //重设密码
    @objc func reset(){
        self.view.endEditing(true)
        let mobile = (staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.mobileInput) as! UITextField).text!
        let code = (staticCheckView.viewWithTag(LuckyTagManager.retrieveTags.codeInput) as! UITextField).text!
        let password = (staticResetView.viewWithTag(LuckyTagManager.retrieveTags.newPasswordInput) as! UITextField).text!
        let confirmPassword = (staticResetView.viewWithTag(LuckyTagManager.retrieveTags.confirmPasswordInput) as! UITextField).text!
        
        if(confirmPassword == password && LuckyUtils.checkPassword(password: password)){
            //两密码相同 且符合密码规范
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyHttpManager.resetPassword(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", code: code, password: password, success: { (data) in
                //重置成功 返回上一页
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: NSLocalizedString("Successfully", comment: "")).showByTime(time: 2)
                self.navigationController?.popViewController(animated: true)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        self.navigationController?.pushViewController(vc, animated: true)
//        let vc = MQChatViewManager()
//        vc.pushMQChatViewController(in: self)
    }
}
