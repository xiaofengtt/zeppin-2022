//
//  LuckyMobileViewController.swift
//  lucky
//  修改绑定手机号
//  Created by Farmer Zhu on 2020/8/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMobileViewController: UIViewController, UITextFieldDelegate, UIPickerViewDataSource, UIPickerViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //原手机号显示
    private var staticShowView: UIView!
    //原手机号验证码发送
    private var staticSendCodeView: UIView!
    //原手机号验证码验证
    private var staticVerifyView: UIView!
    //绑定新手机号
    private var staticChangeView: UIView!
    
    //区号选择
    private var staticCountryView: UIView!
    private var staticCountryPicker: UIPickerView!

    //选中区号
    var selectedCountry: Int? = nil
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //初始化区号选择
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
        //创建原手机号显示页
        staticShowView = createShowView()
        self.view.addSubview(staticShowView)
        //创建原手机号验证码发送页
        staticSendCodeView = createSendCodeView()
        self.view.addSubview(staticSendCodeView)
        //创建原手机号验证码验证页
        staticVerifyView = createVerifyView()
        self.view.addSubview(staticVerifyView)
        //创建绑定新手机号页
        staticChangeView = createChangeView()
        self.view.addSubview(staticChangeView)
        //创建区号选择
        staticCountryView = createCountryView()
        self.view.addSubview(staticCountryView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Mobile Number", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建原手机号显示页
    func createShowView() -> UIView {
        let showView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        showView.backgroundColor = UIColor.white
        
        //显示手机号
        let contentLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: showView.frame.width, height: 20 * screenScale))
        contentLabel.text = NSLocalizedString("Your current number", comment: "")
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        showView.addSubview(contentLabel)
        
        let mobileLabel = UILabel(frame: CGRect(x: 0, y: contentLabel.frame.origin.y + contentLabel.frame.height + 16 * screenScale, width: showView.frame.width, height: 30 * screenScale))
        var areaCodeStr = globalUserData!.areaCode
        let mobileStr = globalUserData!.mobile[areaCodeStr.count ..< globalUserData!.mobile.count]
        if(areaCodeStr.count > 0){
            areaCodeStr = "+\(areaCodeStr) "
        }
        mobileLabel.text = "\(areaCodeStr)\(mobileStr)"
        mobileLabel.textColor = contentLabel.textColor
        mobileLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        mobileLabel.textAlignment = contentLabel.textAlignment
        showView.addSubview(mobileLabel)
        
        //进入修改步骤按钮
        let changeButton = UIButton(frame: CGRect(x: 10 * screenScale, y: mobileLabel.frame.origin.y + mobileLabel.frame.height + 38 * screenScale, width: showView.frame.width - 20 * screenScale, height: 50 * screenScale))
        changeButton.backgroundColor = UIColor.mainYellow()
        changeButton.layer.masksToBounds = true
        changeButton.layer.cornerRadius = 6 * screenScale
        changeButton.setTitle(NSLocalizedString("Change", comment: ""), for: UIControl.State.normal)
        changeButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        changeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        changeButton.addTarget(self, action: #selector(change), for: UIControl.Event.touchUpInside)
        showView.addSubview(changeButton)
        return showView
    }
    
    //创建原手机号验证码发送页
    func createSendCodeView() -> UIView {
        let sendCodeView = UIView(frame: staticShowView.frame)
        sendCodeView.isHidden = true
        sendCodeView.backgroundColor = UIColor.white
        
        //显示手机号
        let contentLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 16 * screenScale, width: sendCodeView.frame.width - 20 * screenScale, height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.text = NSLocalizedString("Before you change your mobile number,verify your current number", comment: "")
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        sendCodeView.addSubview(contentLabel)
        
        let mobileLabel = UILabel(frame: CGRect(x: 0, y: contentLabel.frame.origin.y + contentLabel.frame.height + 40 * screenScale, width: sendCodeView.frame.width, height: 26 * screenScale))
        var areaCodeStr = globalUserData!.areaCode
        let mobileStr = globalUserData!.mobile[areaCodeStr.count ..< globalUserData!.mobile.count]
        if(areaCodeStr.count > 0){
            areaCodeStr = "+\(areaCodeStr) "
        }
        mobileLabel.text = "\(areaCodeStr)\(mobileStr)"
        mobileLabel.textColor = UIColor.fontBlack()
        mobileLabel.font = UIFont.mediumFont(size: 22 * screenScale)
        mobileLabel.textAlignment = NSTextAlignment.center
        sendCodeView.addSubview(mobileLabel)
        
        //发送验证码按钮
        let sendCodeButton = UIButton(frame: CGRect(x: 10 * screenScale, y: mobileLabel.frame.origin.y + mobileLabel.frame.height + 38 * screenScale, width: sendCodeView.frame.width - 20 * screenScale, height: 50 * screenScale))
        sendCodeButton.backgroundColor = UIColor.mainYellow()
        sendCodeButton.layer.masksToBounds = true
        sendCodeButton.layer.cornerRadius = 6 * screenScale
        sendCodeButton.setTitle(NSLocalizedString("Send Code", comment: ""), for: UIControl.State.normal)
        sendCodeButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        sendCodeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        sendCodeButton.addTarget(self, action: #selector(verifySendCode), for: UIControl.Event.touchUpInside)
        sendCodeView.addSubview(sendCodeButton)
        
        return sendCodeView
    }
    
    //创建原手机号验证码验证页
    func createVerifyView() -> UIView {
        let paddingLeft: CGFloat = 10 * screenScale
        
        let verifyView = UIView(frame: staticShowView.frame)
        verifyView.isHidden = true
        
        //提示
        let contentLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: verifyView.frame.width - paddingLeft*2, height: 46 * screenScale))
        contentLabel.text = NSLocalizedString("A Verification code has been sent", comment: "")
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        verifyView.addSubview(contentLabel)
        
        //验证码区
        let codeView = UIView(frame: CGRect(x: 0, y: contentLabel.frame.origin.y + contentLabel.frame.height, width: verifyView.frame.width, height: 50 * screenScale))
        codeView.backgroundColor = UIColor.white
        let codeLabel = UILabel()
        codeLabel.text = NSLocalizedString("Code", comment: "")
        codeLabel.textColor = UIColor.fontBlack()
        codeLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeLabel.sizeToFit()
        codeLabel.frame = CGRect(x: paddingLeft, y: 0, width: codeLabel.frame.width, height: codeView.frame.height)
        codeView.addSubview(codeLabel)
        //重发按钮
        let resendButton = LuckyMobileCodeSendButton(frame: CGRect(x: codeView.frame.width - paddingLeft - 50 * screenScale, y: 0, width: 50 * screenScale, height: codeView.frame.height))
        resendButton.tag = LuckyTagManager.settingTags.mobileVerifyResendButton
        resendButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.right
        resendButton.addTarget(self, action: #selector(verifySendCode), for: UIControl.Event.touchUpInside)
        codeView.addSubview(resendButton)
        //验证码输入框
        let codeInput = UITextField(frame: CGRect(x: codeLabel.frame.origin.x + codeLabel.frame.width + 40 * screenScale, y: 0, width: resendButton.frame.origin.x - (codeLabel.frame.origin.x + codeLabel.frame.width + 40 * screenScale), height: codeView.frame.height))
        codeInput.tag = LuckyTagManager.settingTags.mobileVerifyCodeInput
        codeInput.delegate = self
        codeInput.tintColor = UIColor.mainYellow()
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        codeView.addSubview(codeInput)
        verifyView.addSubview(codeView)
        
        //客服按钮
        let helpButton = UIButton()
        helpButton.setTitle(NSLocalizedString("Haven’t got it?", comment: ""), for: UIControl.State.normal)
        helpButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        helpButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        helpButton.sizeToFit()
        helpButton.frame = CGRect(x: verifyView.frame.width - paddingLeft - helpButton.frame.width, y: codeView.frame.origin.y + codeView.frame.height + 8 * screenScale, width: helpButton.frame.width, height: 30 * screenScale)
        helpButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        verifyView.addSubview(helpButton)
        
        //验证提交按钮
        let verifyButton = UIButton(frame: CGRect(x: paddingLeft, y: helpButton.frame.origin.y + helpButton.frame.height + 30 * screenScale, width: verifyView.frame.width - paddingLeft*2, height: 50 * screenScale))
        verifyButton.tag = LuckyTagManager.settingTags.mobileVerifyButton
        verifyButton.layer.masksToBounds = true
        verifyButton.layer.cornerRadius = 6 * screenScale
        verifyButton.isEnabled = false
        verifyButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        verifyButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        verifyButton.setTitle(NSLocalizedString("Verify", comment: ""), for: UIControl.State.normal)
        verifyButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        verifyButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        verifyButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        verifyButton.addTarget(self, action: #selector(verify), for: UIControl.Event.touchUpInside)
        verifyView.addSubview(verifyButton)
        
        return verifyView
    }
    
    //创建绑定新手机号页
    func createChangeView() -> UIView {
        let paddingLeft: CGFloat = 10 * screenScale
        
        let changeView = UIView(frame: staticShowView.frame)
        changeView.isHidden = true
        
        //新手机号
        let mobileView = UIView(frame: CGRect(x: 0, y: 0, width: changeView.frame.width, height: 50 * screenScale))
        mobileView.backgroundColor = UIColor.white
        //区号选择按钮
        let areaButton = UIButton(frame: CGRect(x: 0, y: 0, width: 60 * screenScale, height: mobileView.frame.height))
        areaButton.tag = LuckyTagManager.settingTags.mobileAreaButton
        areaButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: -3 * screenScale)
        areaButton.setImage(UIImage(named: "image_arrow_down"), for: UIControl.State.normal)
        areaButton.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
        //初始化区号
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
        areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: mobileView.frame.height)
        areaButton.addTarget(self, action: #selector(selectCountry), for: UIControl.Event.touchUpInside)
        mobileView.addSubview(areaButton)
        
        //新手机号输入框
        let mobileInput = UITextField(frame: CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: mobileView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: mobileView.frame.height))
        mobileInput.delegate = self
        mobileInput.tag = LuckyTagManager.settingTags.mobileChangeMobileInput
        mobileInput.leftViewMode = UITextField.ViewMode.always
        mobileInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: mobileInput.frame.height))
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: (mobileInput.frame.height - 16 * screenScale)/2, width: 1, height: 16 * screenScale)
        splitLine.backgroundColor = UIColor.fontBlack().cgColor
        mobileInput.leftView!.layer.addSublayer(splitLine)
        mobileInput.tintColor = UIColor.mainYellow()
        mobileInput.keyboardType = UIKeyboardType.numberPad
        mobileInput.clearButtonMode = UITextField.ViewMode.whileEditing
        mobileInput.textColor = UIColor.fontBlack()
        mobileInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        mobileInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter new mobile number", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        mobileView.addSubview(mobileInput)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: mobileView.frame.height - 1, width: changeView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        mobileView.layer.addSublayer(bottomLine)
        changeView.addSubview(mobileView)
        
        //新手机号验证码
        let codeView = UIView(frame: CGRect(x: mobileView.frame.origin.x, y: mobileView.frame.origin.y + mobileView.frame.height, width: mobileView.frame.width, height: mobileView.frame.height))
        codeView.backgroundColor = UIColor.white
        //发送验证码按钮
        let codeButton = LuckyMobileCodeSendButton(frame: CGRect(x: codeView.frame.width - paddingLeft - 50 * screenScale, y: 0, width: 50 * screenScale, height: codeView.frame.height))
        codeButton.tag = LuckyTagManager.settingTags.mobileChangeSendButton
        codeButton.setTitle(NSLocalizedString("Send", comment: ""), for: UIControl.State.normal)
        codeButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.right
        codeButton.addTarget(self, action: #selector(changeSendCode), for: UIControl.Event.touchUpInside)
        codeView.addSubview(codeButton)
        //新手机号验证码输入框
        let codeInput = UITextField(frame: CGRect(x: paddingLeft, y: 0, width: codeButton.frame.origin.x - paddingLeft, height: codeView.frame.height))
        codeInput.tag = LuckyTagManager.settingTags.mobileChangeCodeInput
        codeInput.delegate = self
        codeInput.tintColor = UIColor.mainYellow()
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        codeView.addSubview(codeInput)
        changeView.addSubview(codeView)
        
        //客服按钮
        let helpButton = UIButton()
        helpButton.setTitle(NSLocalizedString("Haven’t got it?", comment: ""), for: UIControl.State.normal)
        helpButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        helpButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        helpButton.sizeToFit()
        helpButton.frame = CGRect(x: changeView.frame.width - paddingLeft - helpButton.frame.width, y: codeView.frame.origin.y + codeView.frame.height + 8 * screenScale, width: helpButton.frame.width, height: 30 * screenScale)
        helpButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        changeView.addSubview(helpButton)
        
        //提交修改按钮
        let doneButton = UIButton(frame: CGRect(x: paddingLeft, y: helpButton.frame.origin.y + helpButton.frame.height + 30 * screenScale, width: changeView.frame.width - paddingLeft*2, height: 50 * screenScale))
        doneButton.tag = LuckyTagManager.settingTags.mobileChangeDoneButton
        doneButton.isEnabled = false
        doneButton.layer.masksToBounds = true
        doneButton.layer.cornerRadius = 6 * screenScale
        doneButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        doneButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        doneButton.setTitle(NSLocalizedString("Done", comment: ""), for: UIControl.State.normal)
        doneButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        doneButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        doneButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        doneButton.addTarget(self, action: #selector(done), for: UIControl.Event.touchUpInside)
        changeView.addSubview(doneButton)
        return changeView
    }
    
    //创建区号选择页
    func createCountryView() -> UIView{
        let pickerView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        pickerView.isHidden = true
        pickerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //区号选择器
        staticCountryPicker = UIPickerView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight - 200 * screenScale, width: pickerView.frame.width, height: 200 * screenScale))
        staticCountryPicker.backgroundColor = UIColor.white
        staticCountryPicker.dataSource = self
        staticCountryPicker.delegate = self
        pickerView.addSubview(staticCountryPicker)
        
        //底部安全区
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
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.settingTags.mobileVerifyCodeInput){
            //原手机号验证码输入框
            if(str.count > 4){
                //限长 4
                return false
            }
            
            //提交验证按钮是否可用
            let verifyButton = staticVerifyView.viewWithTag(LuckyTagManager.settingTags.mobileVerifyButton) as! UIButton
            if(LuckyUtils.checkMobileCode(code: str)){
                verifyButton.isEnabled = true
            }else{
                verifyButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeMobileInput){
            //新手机号输入框
            if(str.count > 18){
                //限长 18
                return false
            }
            
            //发送验证码按钮是否可用
            let codeButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeSendButton) as! UIButton
            if(str.count > 5){
                //长度 大于 5
                codeButton.isEnabled = true
            }else{
                codeButton.isEnabled = false
            }
            
            //提交修改按钮是否可用
            let doneButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeDoneButton) as! UIButton
            let codeInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeCodeInput) as! UITextField
            if(str.count > 5 && LuckyUtils.checkMobileCode(code: codeInput.text!)){
                //长度大于5 且验证码符合规则
                doneButton.isEnabled = true
            }else{
                doneButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeCodeInput){
            //新手机号验证码输入框
            if(str.count > 4){
                //限长 4
                return false
            }
            
            //提交修改按钮是否可用
            let mobileInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
            let doneButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeDoneButton) as! UIButton

            if(LuckyUtils.checkMobileCode(code: str) && mobileInput.text!.count > 5){
                //验证码符合规则 且 新手机号长度大于5
                doneButton.isEnabled = true
            }else{
                doneButton.isEnabled = false
            }
        }
        return true
    }
    
    //清空输入框 判断对应按钮是否可用
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        if(textField.tag == LuckyTagManager.settingTags.mobileVerifyCodeInput){
            let verifyButton = staticVerifyView.viewWithTag(LuckyTagManager.settingTags.mobileVerifyButton) as! UIButton
            verifyButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeMobileInput){
            let codeButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeSendButton) as! UIButton
            let doneButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeDoneButton) as! UIButton
            codeButton.isEnabled = false
            doneButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeCodeInput){
            let doneButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeDoneButton) as! UIButton
            doneButton.isEnabled = false
        }
        return true
    }
    
    //区号选择框
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
            //有数据
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
            //有数据
            if let areaButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileAreaButton) as? UIButton{
                //赋值 并调整区号按钮大小
                areaButton.setTitle("+\(globalCountryList[row].telCode)", for: UIControl.State.normal)
                let areaTitleSize = areaButton.intrinsicContentSize
                areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: areaButton.frame.height)
                
                let mobileInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
                mobileInput.frame = CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: staticChangeView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: areaButton.frame.height)
            }
            selectedCountry = row
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //显示区号选择
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
    
    //关闭区号选择
    @objc func closeCountryPicker(){
        staticCountryView.isHidden = true
    }
    
    //修改手机号 原手机号显示页 -> 原手机号验证码发送页
    @objc func change(){
        staticShowView.isHidden = true
        staticSendCodeView.isHidden = false
        staticHeaderView.titleLabel.text = NSLocalizedString("Verify Mobile Number", comment: "")
    }
    
    //发送原手机号密码 原手机号验证码发送页 -> 原手机号验证码验证页
    @objc func verifySendCode(){
        self.view.endEditing(true)
        let resendButton = staticVerifyView.viewWithTag(LuckyTagManager.settingTags.mobileVerifyResendButton) as! LuckyMobileCodeSendButton
        resendButton.startTimer()
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //给用户发验证码
        LuckyMobileCodeManager.codeByUser(success: { (data) in
            self.staticSendCodeView.isHidden = true
            self.staticVerifyView.isHidden = false
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //验证原手机号密码 原手机号验证码验证页 -> 绑定新手机号页
    @objc func verify(){
        self.view.endEditing(true)
        let codeInput = staticVerifyView.viewWithTag(LuckyTagManager.settingTags.mobileVerifyCodeInput) as! UITextField
        let code = codeInput.text!
        
        if(LuckyUtils.checkMobileCode(code: code)){
            //短信验证码符合规则
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyHttpManager.postWithToken("front/user/checkMobile", params: ["code": LuckyEncodingUtil.getBase64(code)], success: { (data) in
                self.staticHeaderView.titleLabel.text = NSLocalizedString("Change Mobile Number", comment: "")
                self.staticVerifyView.isHidden = true
                self.staticChangeView.isHidden = false
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
    
    //新手机号 发送验证码
    @objc func changeSendCode(){
        if(selectedCountry == nil){
            //未选区号
            return
        }
        
        let sendButton = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeSendButton) as! LuckyMobileCodeSendButton
        let mobileInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
        let mobile = mobileInput.text!
        //倒计时
        sendButton.startTimer()
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyMobileCodeManager
            .codeByMobile(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", codeType: LuckyMobileCodeManager.MobileCodeType.CODE, success: { (data) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //修改绑定手机号
    @objc func done(){
        if(selectedCountry == nil){
            //未选区号
            return
        }
        
        let codeInput = staticVerifyView.viewWithTag(LuckyTagManager.settingTags.mobileVerifyCodeInput) as! UITextField
        let newCodeInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeCodeInput) as! UITextField
        let mobileInput = staticChangeView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
        let code = codeInput.text!
        let newCode = newCodeInput.text!
        let mobile = mobileInput.text!
        
        if(LuckyUtils.checkMobileCode(code: code) && mobile.count > 5){
            //验证码符合规范且 新手机号长度大于5
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            let realMobile = "\(globalCountryList[selectedCountry!].telCode)\(mobile)"
            LuckyHttpManager.postWithToken("front/user/editMobile", params: ["code": LuckyEncodingUtil.getBase64(code), "mobile": LuckyEncodingUtil.getBase64(realMobile), "newCode": LuckyEncodingUtil.getBase64(newCode), "country": globalCountryList[selectedCountry!].uuid], success: { (data) in
                //发送成功 修改本地用户信息 返回上一页
                globalUserData!.mobile = realMobile
                LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.countryKey, data: globalCountryList[self.selectedCountry!].uuid as AnyObject)
                globalSelectedCountry = globalCountryList[self.selectedCountry!].uuid
                LuckyAlertView(title: "Mobile phone number changed successfully").showByTime(time: 2)
                self.navigationController?.popViewController(animated: true)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
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
    }
}
