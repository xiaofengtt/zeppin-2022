//
//  LuckyBindingViewController.swift
//  lucky
//  绑定手机号
//  Created by Farmer Zhu on 2021/1/26.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyBindingViewController: UIViewController, UITextFieldDelegate, UIPickerViewDataSource, UIPickerViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //功能区
    private var staticMainView: UIView!
    
    //区号选择
    private var staticCountryView: UIView!
    private var staticCountryPicker: UIPickerView!
    
    //入口类型 mobile password
    var type = "mobile"
    //选中区号
    var selectedCountry: Int? = nil
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
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
        //创建功能区
        staticMainView = createMainView()
        self.view.addSubview(staticMainView)
        //创建区号选择
        staticCountryView = createCountryView()
        self.view.addSubview(staticCountryView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        if(type == "mobile"){
            //设置手机号
            headView.titleLabel.text = NSLocalizedString("Binding Mobile Phone", comment: "")
        }else{
            //设置密码
            headView.titleLabel.text = NSLocalizedString("Set Password", comment: "")
        }
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitle(NSLocalizedString("Save", comment: ""), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(save), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建功能区
    func createMainView() -> UIView{
        let mainView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: screenHeight - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        //手机号
        let mobileLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: mainView.frame.width - 20 * screenScale, height: 50 * screenScale))
        mobileLabel.text = NSLocalizedString("Binding mobile phone", comment: "")
        mobileLabel.textColor = UIColor.fontBlack()
        mobileLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(mobileLabel)
        
        let mobileView = UIView(frame: CGRect(x: 0, y: mobileLabel.frame.origin.y + mobileLabel.frame.height, width: mainView.frame.width, height: 50 * screenScale))
        mobileView.backgroundColor = UIColor.white
        //区号选择按钮
        let areaButton = UIButton(frame: CGRect(x: 0, y: 0, width: 60 * screenScale, height: mobileView.frame.height))
        areaButton.tag = LuckyTagManager.settingTags.mobileAreaButton
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
        areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: mobileView.frame.height)
        areaButton.addTarget(self, action: #selector(selectCountry), for: UIControl.Event.touchUpInside)
        mobileView.addSubview(areaButton)
        
        //手机号输入框
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
        bottomLine.frame = CGRect(x: 0, y: mobileView.frame.height - 1, width: mainView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        mobileView.layer.addSublayer(bottomLine)
        mainView.addSubview(mobileView)
        
        //验证码
        let codeView = UIView(frame: CGRect(x: mobileView.frame.origin.x, y: mobileView.frame.origin.y + mobileView.frame.height, width: mobileView.frame.width, height: mobileView.frame.height))
        codeView.backgroundColor = UIColor.white
        //发送验证码按钮
        let codeButton = LuckyMobileCodeSendButton(frame: CGRect(x: codeView.frame.width - 60 * screenScale, y: 0, width: 50 * screenScale, height: codeView.frame.height))
        codeButton.tag = LuckyTagManager.settingTags.mobileChangeSendButton
        codeButton.setTitle(NSLocalizedString("Send", comment: ""), for: UIControl.State.normal)
        codeButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.right
        codeButton.addTarget(self, action: #selector(changeSendCode), for: UIControl.Event.touchUpInside)
        codeView.addSubview(codeButton)
        
        //验证码输入框
        let codeInput = UITextField(frame: CGRect(x: mobileLabel.frame.origin.x, y: 0, width: codeButton.frame.origin.x - mobileLabel.frame.origin.x , height: codeView.frame.height))
        codeInput.tag = LuckyTagManager.settingTags.mobileChangeCodeInput
        codeInput.delegate = self
        codeInput.tintColor = UIColor.mainYellow()
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeInput.textColor = UIColor.fontBlack()
        codeInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        codeView.addSubview(codeInput)
        mainView.addSubview(codeView)
        
        //客服按钮
        let helpButton = UIButton()
        helpButton.setTitle(NSLocalizedString("Haven’t got it?", comment: ""), for: UIControl.State.normal)
        helpButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        helpButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        helpButton.sizeToFit()
        helpButton.frame = CGRect(x: mainView.frame.width - 10 * screenScale - helpButton.frame.width, y: codeView.frame.origin.y + codeView.frame.height + 8 * screenScale, width: helpButton.frame.width, height: 30 * screenScale)
        helpButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        mainView.addSubview(helpButton)
        
        //密码
        let passwordLabel = UILabel(frame: CGRect(x: mobileLabel.frame.origin.x, y: helpButton.frame.origin.y + helpButton.frame.height + 10 * screenScale, width: mobileLabel.frame.width, height: 50 * screenScale))
        passwordLabel.text = NSLocalizedString("Set Password", comment: "")
        passwordLabel.textColor = UIColor.fontBlack()
        passwordLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        mainView.addSubview(passwordLabel)
        
        //密码输入框
        let passwordInput = LuckyPasswordTextField(frame: CGRect(origin: CGPoint(x: 0, y: passwordLabel.frame.origin.y + passwordLabel.frame.height), size: CGSize(width: mainView.frame.width, height: 50 * screenScale)))
        passwordInput.tag = LuckyTagManager.settingTags.newPasswordInput
        passwordInput.delegate = self
        passwordInput.leftViewMode = UITextField.ViewMode.always
        passwordInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: passwordInput.frame.height))
        passwordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        mainView.addSubview(passwordInput)
        
        //密码规则
        let messageLabel = UILabel(frame: CGRect(x: mobileLabel.frame.origin.x, y: passwordInput.frame.origin.y + passwordInput.frame.height + 10 * screenScale, width: mobileLabel.frame.width, height: 40 * screenScale))
        messageLabel.numberOfLines = 0
        messageLabel.text = NSLocalizedString("Password must be 8-16 characters and contain both numbers and letters", comment: "")
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        mainView.addSubview(messageLabel)
        
        return mainView
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
        
        //底安全区
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
    
    //提交保存
    @objc func save(){
        self.view.endEditing(true)
        
        let mobileInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
        let codeInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeCodeInput) as! UITextField
        let newPwdInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.newPasswordInput) as! LuckyPasswordTextField
        newPwdInput.isSelected = false
        let mobile = mobileInput.text!
        let code = codeInput.text!
        
        //校验验证码规则
        if(!LuckyUtils.checkMobileCode(code: code)){
            LuckyAlertView(title: NSLocalizedString("Incorrect verification code, please try again", comment: "")).showByTime(time: 2)
            return
        }
        
        //校验手机号长度
        if(mobile.count < 6){
            LuckyAlertView(title: NSLocalizedString("Incorrect mobile number format, please try again", comment: "")).showByTime(time: 2)
            return
        }
        
        
        let newPwd = newPwdInput.text!
        //密码不为空
        if("" == newPwd){
            newPwdInput.isSelected = true
            LuckyAlertView(title: NSLocalizedString("Please enter password", comment: "")).showByTime(time: 2)
            return
        }
        //校验密码规则
        if(!LuckyUtils.checkPassword(password: newPwd)){
            newPwdInput.isSelected = true
            LuckyAlertView(title: NSLocalizedString("Password must be 8-16 characters and contain both numbers and letters", comment: "")).showByTime(time: 2)
            return
        }
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/user/editMobilePassword", params: ["code": LuckyEncodingUtil.getBase64(code), "mobile": LuckyEncodingUtil.getBase64("\(globalCountryList[selectedCountry!].telCode)\(mobile)"), "password": LuckyEncodingUtil.getBase64(LuckyEncodingUtil.getDes(newPwd)), "country": globalCountryList[selectedCountry!].uuid], success: { (data) in
            //成功 修改本地用户数据 返回上一页
            globalUserData!.mobile = "\(globalCountryList[self.selectedCountry!].telCode)\(mobile)"
            LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
            LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.countryKey, data: globalCountryList[self.selectedCountry!].uuid as AnyObject)
            globalSelectedCountry = globalCountryList[self.selectedCountry!].uuid
            LuckyAlertView(title: NSLocalizedString("Mobile phone number changed successfully", comment: "")).showByTime(time: 2)
            self.navigationController?.popViewController(animated: true)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //关闭区号选择框
    @objc func closeCountryPicker(){
        staticCountryView.isHidden = true
    }
    
    //选择区号
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
    
    //发送手机验证码
    @objc func changeSendCode(){
        let sendButton = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeSendButton) as! LuckyMobileCodeSendButton
        let mobileInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
        let mobile = mobileInput.text!
        
        //校验手机号长度
        if(mobile.count < 6){
            LuckyAlertView(title: NSLocalizedString("Incorrect mobile number format, please try again", comment: "")).showByTime(time: 2)
            return
        }
        
        //再发送倒计时
        sendButton.startTimer()
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyMobileCodeManager
            .codeByMobile(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", codeType: LuckyMobileCodeManager.MobileCodeType.CODE, success: { (data) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        self.navigationController?.pushViewController(vc, animated: true)
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
            if let areaButton = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileAreaButton) as? UIButton{
                //赋值 并调整区号按钮大小
                areaButton.setTitle("+\(globalCountryList[row].telCode)", for: UIControl.State.normal)
                let areaTitleSize = areaButton.intrinsicContentSize
                areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: areaButton.frame.height)
                
                let mobileInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeMobileInput) as! UITextField
                mobileInput.frame = CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: staticMainView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: areaButton.frame.height)
            }
            selectedCountry = row
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.settingTags.newPasswordInput){
            //密码框
            if(str.count > 16){
                //限长16
                return false
            }
            
            //密文赋值
            if(textField.isSecureTextEntry){
                textField.text = str
                return false
            }
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeMobileInput){
            //手机号
            if(str.count > 18){
                //限长 18
                return false
            }
            
            //发送验证码按钮 手机号长度大于5 可用
            let codeButton = staticMainView.viewWithTag(LuckyTagManager.settingTags.mobileChangeSendButton) as! UIButton
            if(str.count > 5){
                codeButton.isEnabled = true
            }else{
                codeButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.settingTags.mobileChangeCodeInput){
            //验证码
            if(str.count > 4){
                //限长 4
                return false
            }
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
}
