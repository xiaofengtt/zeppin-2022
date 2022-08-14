//
//  LuckyLoginViewController.swift
//  lucky
//  登录页
//  Created by Farmer Zhu on 2020/8/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import AuthenticationServices
import FBSDKLoginKit

class LuckyLoginViewController: UIViewController, UITextFieldDelegate, UIPickerViewDelegate, UIPickerViewDataSource {
    
    //头
    private var staticHeaderView: UIView!
    //底
    private var staticBottomView: UIView!
    //验证码登录
    private var staticCodeView: UIView!
    //密码登录
    private var staticPwdView: UIView!
    //图形验证码
    private var staticCapCodeView: UIView!
    //手机验证码
    private var staticMobileCodeView: UIView!
    //国家区号选择
    private var staticCountryView: UIView!
    private var staticCountryPicker: UIPickerView!
    
    //图形码
    private var capCode: String = ""
    //选中国家
    var selectedCountry: Int? = nil
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //初始化区号选择
        if(globalSelectedCountry != nil && globalCountryList.count > 0){
            //有预选国家 且 有国家数据
            for index in 0 ..< globalCountryList.count{
                //选中相应国家
                if(globalCountryList[index].uuid == globalSelectedCountry){
                    selectedCountry = index
                    break
                }
            }
        }
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建底
        staticBottomView = createBottomView()
        self.view.addSubview(staticBottomView)
        //创建验证码登录
        staticCodeView = createCodeView()
        self.view.addSubview(staticCodeView)
        //创建密码登录 隐藏
        staticPwdView = createPwdView()
        staticCodeView.isHidden = true
        self.view.addSubview(staticPwdView)
        //创建图形验证码框 隐藏
        staticCapCodeView = createCapCodeView()
        staticCapCodeView.isHidden = true
        self.view.addSubview(staticCapCodeView)
        //创建手机验证码框 隐藏
        staticMobileCodeView = createMobileCodeView()
        staticMobileCodeView.isHidden = true
        self.view.addSubview(staticMobileCodeView)
        //创建国家选择框
        staticCountryView = createCountryView()
        self.view.addSubview(staticCountryView)
//        getImage()
        
    }
    
    //创建头
    func createHeaderView() -> UIView {
        let headView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: statusBarHeight + 40 * screenScale)))
        //返回按钮
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight, width: 100 * screenScale, height: 40 * screenScale))
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_close_black")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        
        //客服按钮
        let customerButton = UIButton(frame: CGRect(x: headView.frame.width - 150 * screenScale, y: backButton.frame.origin.y, width: 140 * screenScale, height: backButton.frame.height))
        let customerTitleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: customerButton.frame.size))
        customerTitleLabel.text = NSLocalizedString("Customer Service", comment: "")
        customerTitleLabel.textColor = UIColor.mainYellow()
        customerTitleLabel.font = UIFont.boldFont(size: UIFont.fontSizeMiddle() * screenScale)
        customerTitleLabel.textAlignment = NSTextAlignment.right
        customerButton.addSubview(customerTitleLabel)
        customerButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        headView.addSubview(customerButton)
        return headView
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 40 * screenScale, y: screenHeight - 50 * screenScale - 180 * screenScale, width: screenWidth - 80 * screenScale, height: 180 * screenScale ))
        
        //底部广告图
//        let bottomImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: bottomView.frame.size))
//        bottomImageView.tag = LuckyTagManager.loginTags.bottomImageView
//        bottomView.addSubview(bottomImageView)
        
        //or分割
        let topLabel = UILabel(frame: CGRect(x: (bottomView.frame.width - 80 * screenScale) / 2, y: 0, width: 80 * screenScale, height: 18 * screenScale))
        topLabel.text = NSLocalizedString("OR", comment: "")
        topLabel.textColor = UIColor(red: 211/255, green: 211/255, blue: 211/255, alpha: 1)
        topLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        topLabel.textAlignment = NSTextAlignment.center
        bottomView.addSubview(topLabel)
        
        let topLeftLine = CALayer()
        topLeftLine.frame = CGRect(x: 0, y: 9 * screenScale, width: topLabel.frame.origin.x, height: 1)
        topLeftLine.backgroundColor = topLabel.textColor.cgColor
        bottomView.layer.addSublayer(topLeftLine)
        let topRightLine = CALayer()
        topRightLine.frame = CGRect(x: bottomView.frame.width - topLeftLine.frame.width, y: topLeftLine.frame.origin.y, width: topLeftLine.frame.width, height: topLeftLine.frame.height)
        topRightLine.backgroundColor = topLeftLine.backgroundColor
        bottomView.layer.addSublayer(topRightLine)
        
        //非死不可按钮
        let fbButton = UIButton()
        fbButton.tag = LuckyTagManager.loginTags.fbSignInButton
        if #available(iOS 13.2, *) {
            //13.2以上 有苹果登录
            //苹果signin 按钮
            let appleButton = ASAuthorizationAppleIDButton(authorizationButtonType: ASAuthorizationAppleIDButton.ButtonType.signIn, authorizationButtonStyle: ASAuthorizationAppleIDButton.Style.black)
            appleButton.tag = LuckyTagManager.loginTags.appSignInButton
            appleButton.frame = CGRect(x: 0, y: topLabel.frame.origin.y + topLabel.frame.height + 15 * screenScale, width: bottomView.frame.width, height: 48 * screenScale)
            appleButton.cornerRadius = 6 * screenScale
            appleButton.addTarget(self, action: #selector(appleLogin), for: UIControl.Event.touchUpInside)
            bottomView.addSubview(appleButton)
            
            //苹果signup 按钮 隐藏
            let appleSignUpButton = ASAuthorizationAppleIDButton(authorizationButtonType: ASAuthorizationAppleIDButton.ButtonType.signUp, authorizationButtonStyle: ASAuthorizationAppleIDButton.Style.black)
            appleSignUpButton.isHidden = true
            appleSignUpButton.tag = LuckyTagManager.loginTags.appSignUpButton
            appleSignUpButton.frame = CGRect(x: 0, y: topLabel.frame.origin.y + topLabel.frame.height + 15 * screenScale, width: bottomView.frame.width, height: 48 * screenScale)
            appleSignUpButton.cornerRadius = 6 * screenScale
            appleSignUpButton.addTarget(self, action: #selector(appleLogin), for: UIControl.Event.touchUpInside)
            bottomView.addSubview(appleSignUpButton)
            
            //非死不可按钮位置
            fbButton.frame = CGRect(x: appleButton.frame.origin.x, y: appleButton.frame.origin.y + appleButton.frame.height + 12 * screenScale, width: appleButton.frame.width, height: appleButton.frame.height)
        }else{
            //否则 只有非死不可按钮
            fbButton.frame = CGRect(x: 0, y: topLabel.frame.origin.y + topLabel.frame.height + 15 * screenScale, width: bottomView.frame.width, height: 48 * screenScale)
        }
        
        //非死不可按钮
        fbButton.layer.cornerRadius = 6 * screenScale
        fbButton.adjustsImageWhenHighlighted = false
        fbButton.backgroundColor = UIColor(red: 24/255, green: 119/255, blue: 242/255, alpha: 1)
        fbButton.setImage(UIImage(named: "image_fb_white"), for: UIControl.State.normal)
        fbButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 15 * screenScale)
        fbButton.setTitle("Sign in with Facebook", for: UIControl.State.normal)
        fbButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        fbButton.titleLabel?.font = UIFont.mediumFont(size: (UIFont.fontSizeBiggest() + 2) * screenScale)
        fbButton.addTarget(self, action: #selector(fbLogin), for: UIControl.Event.touchUpInside)
        bottomView.addSubview(fbButton)
        
        //登录时底部区 隐藏
        let bottomSignInView = UIView(frame: CGRect(x: 0, y: bottomView.frame.height - 20, width: bottomView.frame.width, height: 20 * screenScale))
        bottomSignInView.tag = LuckyTagManager.loginTags.bottomSignInView
        bottomSignInView.isHidden = true
        let bottomSignInLabel = UILabel(frame: CGRect(x: 0, y: 0, width: bottomSignInView.frame.width, height: bottomSignInView.frame.height))
        bottomSignInLabel.numberOfLines = 1
        bottomSignInLabel.text = NSLocalizedString("Already have an account?", comment: "")
        bottomSignInLabel.textColor = UIColor.fontBlack()
        bottomSignInLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        bottomSignInLabel.sizeToFit()
        let bottomSignInButton = UILabel(frame: CGRect(x: 0, y: 0, width: bottomSignInView.frame.width, height: bottomSignInView.frame.height))
        bottomSignInButton.numberOfLines = 1
        bottomSignInButton.text = NSLocalizedString("Sign in", comment: "")
        bottomSignInButton.textColor = UIColor.mainYellow()
        bottomSignInButton.font = bottomSignInLabel.font
        bottomSignInButton.sizeToFit()
        bottomSignInButton.isUserInteractionEnabled = true
        bottomSignInButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(showPwdView)))
        bottomSignInLabel.frame = CGRect(origin: CGPoint(x: (bottomSignInView.frame.width - (bottomSignInLabel.frame.width + 2 * screenScale + bottomSignInButton.frame.width)) / 2, y: 0), size: CGSize(width: bottomSignInLabel.frame.width, height: bottomSignInView.frame.height))
        bottomSignInButton.frame = CGRect(x: bottomSignInLabel.frame.origin.x + bottomSignInLabel.frame.width + 2 * screenScale, y: 0, width: bottomSignInButton.frame.width, height: bottomSignInView.frame.height)
        bottomSignInView.addSubview(bottomSignInLabel)
        bottomSignInView.addSubview(bottomSignInButton)
        bottomView.addSubview(bottomSignInView)
        
        //注册时底部区
        let bottomSignUpView = UIView(frame: CGRect(x: 0, y: bottomView.frame.height - 20, width: bottomView.frame.width, height: 20 * screenScale))
        bottomSignUpView.tag = LuckyTagManager.loginTags.bottomSignUpView
        let bottomSignUpLabel = UILabel(frame: CGRect(x: 0, y: 0, width: bottomSignUpView.frame.width, height: bottomSignUpView.frame.height))
        bottomSignUpLabel.numberOfLines = 1
        bottomSignUpLabel.text = NSLocalizedString("Don’t have an account yet?", comment: "")
        bottomSignUpLabel.textColor = UIColor.fontBlack()
        bottomSignUpLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        bottomSignUpLabel.sizeToFit()
        let bottomSignUpButton = UILabel(frame: CGRect(x: 0, y: 0, width: bottomSignUpView.frame.width, height: bottomSignUpView.frame.height))
        bottomSignUpButton.numberOfLines = 1
        bottomSignUpButton.text = NSLocalizedString("Sign up", comment: "")
        bottomSignUpButton.textColor = UIColor.mainYellow()
        bottomSignUpButton.font = bottomSignUpLabel.font
        bottomSignUpButton.sizeToFit()
        bottomSignUpButton.isUserInteractionEnabled = true
        bottomSignUpButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(showCodeView)))
        bottomSignUpLabel.frame = CGRect(origin: CGPoint(x: (bottomSignUpView.frame.width - (bottomSignUpLabel.frame.width + 2 * screenScale + bottomSignUpButton.frame.width)) / 2, y: 0), size: CGSize(width: bottomSignUpLabel.frame.width, height: bottomSignUpView.frame.height))
        bottomSignUpButton.frame = CGRect(x: bottomSignUpLabel.frame.origin.x + bottomSignUpLabel.frame.width + 2 * screenScale, y: 0, width: bottomSignUpButton.frame.width, height: bottomSignUpView.frame.height)
        bottomSignUpView.addSubview(bottomSignUpLabel)
        bottomSignUpView.addSubview(bottomSignUpButton)
        bottomView.addSubview(bottomSignUpView)
        return bottomView
    }
    
    //创建验证码登录注册区
    func createCodeView() -> UIView{
        let codeView = UIView(frame: CGRect(x: 40 * screenScale, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth - 80 * screenScale, height: staticBottomView.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height) - 40 * screenScale))

        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: codeView.frame.width, height: 40 * screenScale))
        titleLabel.text = NSLocalizedString("Sign Up/Sign In", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.left
        codeView.addSubview(titleLabel)
        
        //手机号行
        let phoneView = UIView(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 40 * screenScale , width: codeView.frame.width, height: 40 * screenScale))
        //选地区按钮
        let areaButton = UIButton(frame: CGRect(x: 0, y: 0, width: 40 * screenScale, height: phoneView.frame.height))
        areaButton.tag = LuckyTagManager.loginTags.codeAreaButton
        areaButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: -3 * screenScale)
        areaButton.setImage(UIImage(named: "image_arrow_down"), for: UIControl.State.normal)
        areaButton.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
        if(globalCountryList.count > 0){
            //有国家数据
            if(selectedCountry != nil && globalCountryList.count > selectedCountry!){
                //有默认选中地区 选中
                areaButton.setTitle("+\(globalCountryList[selectedCountry!].telCode)", for: UIControl.State.normal)
            }else{
                //没有 选第一条
                areaButton.setTitle("+\(globalCountryList[0].telCode)", for: UIControl.State.normal)
                selectedCountry = 0
            }
        }else{
            //无国家数据
            areaButton.setTitle("+", for: UIControl.State.normal)
        }
        areaButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        areaButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        let areaTitleSize = areaButton.intrinsicContentSize
        areaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: phoneView.frame.height)
        areaButton.addTarget(self, action: #selector(selectCountry), for: UIControl.Event.touchUpInside)
        phoneView.addSubview(areaButton)
        //手机号输入框
        let codeMobileInput = UITextField(frame: CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: phoneView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: areaButton.frame.height))
        codeMobileInput.tag = LuckyTagManager.loginTags.codeMobileInput
        codeMobileInput.delegate = self
        codeMobileInput.leftViewMode = UITextField.ViewMode.always
        codeMobileInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: codeMobileInput.frame.height))
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: (codeMobileInput.frame.height - 16 * screenScale)/2, width: 1, height: 16 * screenScale)
        splitLine.backgroundColor = UIColor.fontBlack().cgColor
        codeMobileInput.leftView!.layer.addSublayer(splitLine)
        codeMobileInput.tintColor = UIColor.mainYellow()
        codeMobileInput.keyboardType = UIKeyboardType.numberPad
        codeMobileInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeMobileInput.textColor = UIColor.fontBlack()
        codeMobileInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        codeMobileInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter mobile number", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        let codePhoneBottom = CALayer()
        codePhoneBottom.frame = CGRect(x: 0, y: phoneView.frame.height - 1, width: phoneView.frame.width, height: 1)
        codePhoneBottom.backgroundColor = UIColor.fontGray().cgColor
        phoneView.layer.addSublayer(codePhoneBottom)
        phoneView.addSubview(codeMobileInput)
        codeView.addSubview(phoneView)
        
        //提示
        let messageLabel = UILabel(frame: CGRect(x: 0, y: phoneView.frame.origin.y + phoneView.frame.height + 6 * screenScale , width: codeView.frame.width, height: 40 * screenScale))
        messageLabel.numberOfLines = 0
        messageLabel.text = NSLocalizedString("Unverified mobile number is automatically registered after verification", comment: "")
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller())
        codeView.addSubview(messageLabel)
        
        //登录注册按钮
        let loginButton = UIButton(frame: CGRect(x: 0, y: messageLabel.frame.origin.y + messageLabel.frame.height + 15 * screenScale, width: codeView.frame.width, height: 50 * screenScale))
        loginButton.tag = LuckyTagManager.loginTags.codeLoginButton
        loginButton.isEnabled = false
        loginButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        loginButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        loginButton.setTitle(NSLocalizedString("Sign Up/Sign In", comment: ""), for: UIControl.State.normal)
        loginButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        loginButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        loginButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        loginButton.addTarget(self, action: #selector(codeCapViewShow), for: UIControl.Event.touchUpInside)
        codeView.addSubview(loginButton)
        
        //用户协议
        let privacyNormalLabel = UILabel()
        privacyNormalLabel.text = NSLocalizedString("I have read and accept the ", comment: "")
        privacyNormalLabel.textColor = UIColor.fontGray()
        privacyNormalLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        privacyNormalLabel.sizeToFit()
        privacyNormalLabel.frame = CGRect(x: 0, y: loginButton.frame.origin.y + loginButton.frame.height + 20 * screenScale, width: privacyNormalLabel.frame.width, height: 20 * screenScale)
        codeView.addSubview(privacyNormalLabel)
        
        let privacyEnableLabel = UILabel()
        privacyEnableLabel.isUserInteractionEnabled = true
        privacyEnableLabel.text = NSLocalizedString("Terms of Service", comment: "")
        privacyEnableLabel.textColor = UIColor.mainYellow()
        privacyEnableLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        privacyEnableLabel.sizeToFit()
        privacyEnableLabel.frame = CGRect(x: privacyNormalLabel.frame.origin.x + privacyNormalLabel.frame.width, y: privacyNormalLabel.frame.origin.y, width: privacyEnableLabel.frame.width, height: 20 * screenScale)
        privacyEnableLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toPrivacy)))
        codeView.addSubview(privacyEnableLabel)
        
        //密码登录按钮
        let pwdButton = UIButton(frame: CGRect(x: 0, y: privacyNormalLabel.frame.origin.y + privacyNormalLabel.frame.height + 20 * screenScale, width: codeView.frame.width, height: 20 * screenScale))
        pwdButton.layer.masksToBounds = true
        pwdButton.layer.cornerRadius = 6 * screenScale
        pwdButton.setTitle(NSLocalizedString("Sign in with password", comment: ""), for: UIControl.State.normal)
        pwdButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        pwdButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        pwdButton.addTarget(self, action: #selector(showPwdView), for: UIControl.Event.touchUpInside)
        codeView.addSubview(pwdButton)
        
        return codeView
    }
    
    //创建密码登录区
    func createPwdView() -> UIView{
        let pwdView = UIView(frame: staticCodeView.frame)
        
        //标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 30 * screenScale, width: pwdView.frame.width, height: 40 * screenScale))
        titleLabel.text = NSLocalizedString("Sign In with Password", comment: "")
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mediumFont(size: 26 * screenScale)
        titleLabel.textAlignment = NSTextAlignment.left
        pwdView.addSubview(titleLabel)
        
        //手机号行
        let phoneView = UIView(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 40 * screenScale , width: pwdView.frame.width, height: 40 * screenScale))
        //选地区按钮
        let areaButton = UIButton(frame: CGRect(x: 0, y: 0, width: 40 * screenScale, height: phoneView.frame.height))
        areaButton.tag = LuckyTagManager.loginTags.pwdAreaButton
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
        let pwdMobileInput = UITextField(frame: CGRect(x: areaButton.frame.origin.x + areaButton.frame.width, y: 0, width: phoneView.frame.width - (areaButton.frame.origin.x + areaButton.frame.width), height: areaButton.frame.height))
        pwdMobileInput.delegate = self
        pwdMobileInput.tag = LuckyTagManager.loginTags.pwdMobileInput
        pwdMobileInput.leftViewMode = UITextField.ViewMode.always
        pwdMobileInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: pwdMobileInput.frame.height))
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 0, y: (pwdMobileInput.frame.height - 16 * screenScale)/2, width: 1, height: 16 * screenScale)
        splitLine.backgroundColor = UIColor.fontBlack().cgColor
        pwdMobileInput.leftView!.layer.addSublayer(splitLine)
        pwdMobileInput.tintColor = UIColor.mainYellow()
        pwdMobileInput.keyboardType = UIKeyboardType.numberPad
        pwdMobileInput.clearButtonMode = UITextField.ViewMode.whileEditing
        pwdMobileInput.textColor = UIColor.fontBlack()
        pwdMobileInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        pwdMobileInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter mobile number", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        let pwdPhoneBottom = CALayer()
        pwdPhoneBottom.frame = CGRect(x: 0, y: phoneView.frame.height - 1, width: phoneView.frame.width, height: 1)
        pwdPhoneBottom.backgroundColor = UIColor.fontGray().cgColor
        phoneView.layer.addSublayer(pwdPhoneBottom)
        phoneView.addSubview(pwdMobileInput)
        pwdView.addSubview(phoneView)
        
        //密码输入框
        let passwordView = UIView(frame: CGRect(x: 0, y: phoneView.frame.origin.y + phoneView.frame.height + 10 * screenScale , width: pwdView.frame.width, height: 40 * screenScale))
        let pwdPasswordInput = UITextField(frame: CGRect(x: 0, y: 0, width: phoneView.frame.width - 40 * screenScale, height: phoneView.frame.height))
        pwdPasswordInput.tag = LuckyTagManager.loginTags.pwdPasswordInput
        pwdPasswordInput.delegate = self
        pwdPasswordInput.isSecureTextEntry = true
        pwdPasswordInput.tintColor = UIColor.mainYellow()
        pwdPasswordInput.keyboardType = UIKeyboardType.asciiCapable
        pwdPasswordInput.autocapitalizationType = UITextAutocapitalizationType.none
        pwdPasswordInput.clearButtonMode = UITextField.ViewMode.whileEditing
        pwdPasswordInput.textColor = UIColor.fontBlack()
        pwdPasswordInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        pwdPasswordInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter password", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        let passwordHideButton = UIButton(frame: CGRect(x: passwordView.frame.width - 25 * screenScale, y: pwdPasswordInput.frame.origin.y + 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        passwordHideButton.isSelected = true
        passwordHideButton.showsTouchWhenHighlighted = false
        passwordHideButton.adjustsImageWhenHighlighted = false
        passwordHideButton.setImage(UIImage(named: "image_password_show"), for: UIControl.State.normal)
        passwordHideButton.setImage(UIImage(named: "image_password_hide"), for: UIControl.State.selected)
        passwordHideButton.addTarget(self, action: #selector(hidePwd(_ :)), for: UIControl.Event.touchUpInside)
        passwordView.addSubview(passwordHideButton)
        let pwdPasswordBottom = CALayer()
        pwdPasswordBottom.frame = CGRect(x: 0, y: phoneView.frame.height - 1, width: phoneView.frame.width, height: 1)
        pwdPasswordBottom.backgroundColor = UIColor.fontGray().cgColor
        passwordView.layer.addSublayer(pwdPasswordBottom)
        passwordView.addSubview(pwdPasswordInput)
        pwdView.addSubview(passwordView)
        
        //忘记密码
        let forgotLabel = UILabel()
        forgotLabel.isUserInteractionEnabled = true
        forgotLabel.text = NSLocalizedString("Forgot your password?", comment: "")
        forgotLabel.textColor = UIColor.mainYellow()
        forgotLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        forgotLabel.sizeToFit()
        forgotLabel.frame = CGRect(x: pwdView.frame.width - forgotLabel.frame.width, y: passwordView.frame.origin.y + passwordView.frame.height + 10 * screenScale, width: forgotLabel.frame.width, height: 20 * screenScale)
        forgotLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toRetrieve)))
        pwdView.addSubview(forgotLabel)
        
        //登录按钮
        let loginButton = UIButton(frame: CGRect(x: 0, y: forgotLabel.frame.origin.y + forgotLabel.frame.height + 15 * screenScale, width: pwdView.frame.width, height: 50 * screenScale))
        loginButton.tag = LuckyTagManager.loginTags.pwdLoginButton
        loginButton.isEnabled = false
        loginButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.normal)
        loginButton.setBackgroundImage(UIImage.getImageByColor(UIColor.mainLightYellow()), for: UIControl.State.disabled)
        loginButton.setTitle(NSLocalizedString("Sign In", comment: ""), for: UIControl.State.normal)
        loginButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        loginButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.disabled)
        loginButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        loginButton.addTarget(self, action: #selector(pwdLogin), for: UIControl.Event.touchUpInside)
        pwdView.addSubview(loginButton)
        
        //用户协议
//        let privacyNormalLabel = UILabel()
//        privacyNormalLabel.text = NSLocalizedString("I have read and accept the ", comment: "")
//        privacyNormalLabel.textColor = UIColor.fontGray()
//        privacyNormalLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
//        privacyNormalLabel.sizeToFit()
//        privacyNormalLabel.frame = CGRect(x: 0, y: loginButton.frame.origin.y + loginButton.frame.height + 20 * screenScale, width: privacyNormalLabel.frame.width, height: 20 * screenScale)
//        pwdView.addSubview(privacyNormalLabel)
//
//        let privacyEnableLabel = UILabel()
//        privacyEnableLabel.isUserInteractionEnabled = true
//        privacyEnableLabel.text = NSLocalizedString("Terms of Service", comment: "")
//        privacyEnableLabel.textColor = UIColor.mainYellow()
//        privacyEnableLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
//        privacyEnableLabel.sizeToFit()
//        privacyEnableLabel.frame = CGRect(x: privacyNormalLabel.frame.origin.x + privacyNormalLabel.frame.width, y: privacyNormalLabel.frame.origin.y, width: privacyEnableLabel.frame.width, height: 20 * screenScale)
//        privacyEnableLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toPrivacy)))
//        pwdView.addSubview(privacyEnableLabel)
        
        //验证码登录按钮
        let codeButton = UIButton(frame: CGRect(x: 0, y: loginButton.frame.origin.y + loginButton.frame.height + 20 * screenScale, width: pwdView.frame.width, height: 20 * screenScale))
        codeButton.layer.masksToBounds = true
        codeButton.layer.cornerRadius = 6 * screenScale
        codeButton.setTitle(NSLocalizedString("Sign in with verification code", comment: ""), for: UIControl.State.normal)
        codeButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        codeButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        codeButton.addTarget(self, action: #selector(showCodeView), for: UIControl.Event.touchUpInside)
        pwdView.addSubview(codeButton)
        
        return pwdView
    }
    
    //创建图形验证码区
    func createCapCodeView() -> UIView{
        let capCodeView = UIView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size))
        capCodeView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        let capCodeMainView = UIView(frame: CGRect(x: (self.view.frame.width - 275 * screenScale)/2, y: (self.view.frame.height - 170 * screenScale) / 2, width: 275 * screenScale, height: 170 * screenScale))
        capCodeMainView.backgroundColor = UIColor.white
        capCodeMainView.layer.cornerRadius = 4 * screenScale
        capCodeMainView.layer.masksToBounds = true
        let capLabel = UILabel(frame: CGRect(x: 0, y: 15 * screenScale, width: capCodeMainView.frame.width, height: 20 * screenScale))
        capLabel.text = NSLocalizedString("Enter CAPTCHA code", comment: "")
        capLabel.textColor = UIColor.fontBlack()
        capLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        capLabel.textAlignment = NSTextAlignment.center
        capCodeMainView.addSubview(capLabel)
        //输入框
        let capInputView = UIView(frame: CGRect(x: 10 * screenScale, y: capLabel.frame.origin.y + capLabel.frame.height + 15 * screenScale, width: capCodeMainView.frame.width - 20 * screenScale, height: 50 * screenScale))
        capInputView.backgroundColor = UIColor.backgroundGray ()
        capInputView.layer.cornerRadius = 4 * screenScale
        capInputView.clipsToBounds = true
        let capInput = UITextField(frame: CGRect(x: 10 * screenScale, y: 0, width: capInputView.frame.width - 20 * screenScale, height: capInputView.frame.height))
        capInput.delegate = self
        capInput.keyboardType = UIKeyboardType.numberPad
        capInput.tag = LuckyTagManager.loginTags.capCodeInput
        capInput.textColor = UIColor.fontBlack()
        capInput.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        capInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Captcha code", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)])
        capInput.clearButtonMode = UITextField.ViewMode.never
        capInputView.addSubview(capInput)
        //验证码
        let capView = LuckyCaptchaCodeView(frame: CGRect(x: capInputView.frame.width - 100 * screenScale, y: (capInputView.frame.height - 36 * screenScale)/2, width: 90 * screenScale, height: 36 * screenScale))
        capView.tag = LuckyTagManager.loginTags.capImageView
        capView.layer.cornerRadius = 4 * screenScale
        capView.clipsToBounds = true
        capInputView.addSubview(capView)
        //刷新按钮
        let capButton = UIButton(frame: capView.frame)
        capButton.addTarget(self, action: #selector(newCap), for: UIControl.Event.touchUpInside)
        capInputView.addSubview(capButton)
        capCodeMainView.addSubview(capInputView)
        //取消按钮
        let cancelButton = UIButton(frame: CGRect(x: 0, y: capCodeMainView.frame.height - 40 * screenScale, width: capCodeMainView.frame.width/2, height: 40 * screenScale))
        cancelButton.layer.borderColor = UIColor.backgroundGray().cgColor
        cancelButton.layer.borderWidth = 1 * screenScale
        cancelButton.setTitle(NSLocalizedString("Cancel", comment: ""), for: UIControl.State.normal)
        cancelButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        cancelButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        cancelButton.addTarget(self, action: #selector(hideCapView), for: UIControl.Event.touchUpInside)
        capCodeMainView.addSubview(cancelButton)
        //确认按钮
        let sureButton = UIButton(frame: CGRect(x: capCodeMainView.frame.width/2, y: cancelButton.frame.origin.y, width: cancelButton.frame.width, height: cancelButton.frame.height))
        sureButton.layer.borderColor = cancelButton.layer.borderColor
        sureButton.layer.borderWidth = cancelButton.layer.borderWidth
        sureButton.setTitle(NSLocalizedString("OK", comment: ""), for: UIControl.State.normal)
        sureButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        sureButton.titleLabel?.font = cancelButton.titleLabel?.font
        sureButton.addTarget(self, action: #selector(sendCode), for: UIControl.Event.touchUpInside)
        capCodeMainView.addSubview(sureButton)
        capCodeView.addSubview(capCodeMainView)
        
        return capCodeView
    }
    
    //创建手机验证码区
    func createMobileCodeView() -> UIView{
        let mobileCodeView = UIView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size))
        mobileCodeView.backgroundColor = UIColor.white
        //关闭按钮
        let returnButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight, width: 100 * screenScale, height: 40 * screenScale))
        returnButton.addTarget(self, action: #selector(hideMobileCodeView), for: UIControl.Event.touchUpInside)
        let returnIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        returnIconView.image = UIImage(named: "image_back_black")
        returnButton.addSubview(returnIconView)
        mobileCodeView.addSubview(returnButton)
        
        //客服按钮
        let customerButton = UIButton(frame: CGRect(x: mobileCodeView.frame.width - 150 * screenScale, y: returnButton.frame.origin.y, width: 140 * screenScale, height: returnButton.frame.height))
        let customerTitleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: customerButton.frame.size))
        customerTitleLabel.text = NSLocalizedString("Customer Service", comment: "")
        customerTitleLabel.textColor = UIColor.fontBlack()
        customerTitleLabel.font = UIFont.boldFont(size: UIFont.fontSizeMiddle() * screenScale)
        customerTitleLabel.textAlignment = NSTextAlignment.right
        customerButton.addSubview(customerTitleLabel)
        customerButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        mobileCodeView.addSubview(customerButton)
        
        //标题
        let textLabel = UILabel(frame: CGRect(x: 40 * screenScale, y: returnButton.frame.origin.y + returnButton.frame.height + 60 * screenScale, width: screenWidth - 40 * screenScale * 2, height: 30 * screenScale))
        textLabel.text = NSLocalizedString("Enter Verification Code", comment: "")
        textLabel.textColor = UIColor.fontBlack()
        textLabel.font = UIFont.mediumFont(size: 28 * screenScale)
        mobileCodeView.addSubview(textLabel)
        
        let contentLabel = UILabel(frame: CGRect(x: textLabel.frame.origin.x, y: textLabel.frame.origin.y + textLabel.frame.height + 10 * screenScale, width: textLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = NSLocalizedString("A Verification code has been sent", comment: "")
        contentLabel.textColor = UIColor.fontDarkGray()
        contentLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        mobileCodeView.addSubview(contentLabel)
        
        //验证码区
        let codeCodeView = LuckyMobileCodeView(frame: CGRect(x: 40 * screenScale, y: contentLabel.frame.origin.y + contentLabel.frame.height + 35 * screenScale, width: screenWidth - (40 * screenScale) * 2, height: 44 * screenScale))
        codeCodeView.tag = LuckyTagManager.loginTags.mobileCodeView
        codeCodeView.codeInput.delegate = self
        codeCodeView.codeInput.tag = LuckyTagManager.loginTags.mobileCodeInput
        mobileCodeView.addSubview(codeCodeView)
        
        //重发按钮
        let resendButton = LuckyMobileCodeSendButton(frame: CGRect(x: 40 * screenScale, y: codeCodeView.frame.origin.y + codeCodeView.frame.height + 40 * screenScale, width: 80 * screenScale, height: 20 * screenScale))
        resendButton.tag = LuckyTagManager.loginTags.mobileCodeResendButton
        resendButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.left
        resendButton.addTarget(self, action: #selector(resendCode(_:)), for: UIControl.Event.touchUpInside)
        mobileCodeView.addSubview(resendButton)
        
        //客服按钮
        let helpButton = UIButton()
        helpButton.setTitle(NSLocalizedString("Haven’t got it?", comment: ""), for: UIControl.State.normal)
        helpButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        helpButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        helpButton.sizeToFit()
        helpButton.frame = CGRect(x: mobileCodeView.frame.width - 40 * screenScale - helpButton.frame.width, y: resendButton.frame.origin.y, width: helpButton.frame.width, height: resendButton.frame.height)
        helpButton.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        mobileCodeView.addSubview(helpButton)
        return mobileCodeView
    }
    
    //创建国家选择框
    func createCountryView() -> UIView{
        let pickerView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        pickerView.isHidden = true
        pickerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //选择控件
        staticCountryPicker = UIPickerView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight - 200 * screenScale, width: pickerView.frame.width, height: 200 * screenScale))
        staticCountryPicker.backgroundColor = UIColor.white
        staticCountryPicker.dataSource = self
        staticCountryPicker.delegate = self
        pickerView.addSubview(staticCountryPicker)
        
        //底
        let bottomView = UIView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight, width: pickerView.frame.width, height: bottomSafeHeight))
        bottomView.backgroundColor = staticCountryPicker.backgroundColor
        pickerView.addSubview(bottomView)
        
        //头
        let headerView = UIView(frame: CGRect(x: 0, y: staticCountryPicker.frame.origin.y - 50 * screenScale, width: pickerView.frame.width, height: 50 * screenScale))
        headerView.backgroundColor = UIColor.white
        //关闭
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
        
        //关闭
        let hideButton = UIButton(frame: CGRect(x: 0, y: 0, width: pickerView.frame.width, height: headerView.frame.origin.y))
        hideButton.addTarget(self, action: #selector(closeCountryPicker), for: UIControl.Event.touchUpInside)
        pickerView.addSubview(hideButton)
        return pickerView
    }
    
    //获取底部广告图
    func getImage(){
        LuckyHttpManager.getWithoutToken("front/banner/list", params: NSDictionary(), success: { (datas) in
            let dataList = datas as! NSArray
            for data in dataList{
                let dataDic = data as! NSDictionary
                if("type_login" == String.valueOf(any: dataDic["type"])){
                    let imageView = self.staticBottomView.viewWithTag(LuckyTagManager.loginTags.bottomImageView) as! UIImageView
                    let imageUrl = String.valueOf(any: dataDic["imageUrl"])
                    imageView.sd_setImage(with: URL(string: imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed)
                    return
                }
            }
        }, fail: { (reason) in
        })
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        //获取新字符串
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.loginTags.codeMobileInput){
            //验证码登录区手机框
            if(str.count > 18){
                //最长18
                return false
            }
            
            let codeLoginButton = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeLoginButton) as! UIButton
            //长度超过5 发验证码可用
            if(str.count > 5){
                codeLoginButton.isEnabled = true
            }else{
                codeLoginButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.loginTags.pwdMobileInput){
            //密码登录区手机框
            if(str.count > 18){
                //限长18
                return false
            }
            
            let pwdPasswordInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdPasswordInput) as! UITextField
            let pwdLoginButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdLoginButton) as! UIButton
            //通过密码校验 且手机号长度大于5 登录按钮可用
            if(str.count > 5 && LuckyUtils.checkPassword(password: pwdPasswordInput.text!)){
                pwdLoginButton.isEnabled = true
            }else{
                pwdLoginButton.isEnabled = false
            }
        }else if(textField.tag == LuckyTagManager.loginTags.pwdPasswordInput){
            //密码框
            if(str.count > 16){
                //限长16
                return false
            }
            
            let pwdMobileInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdMobileInput) as! UITextField
            let pwdLoginButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdLoginButton) as! UIButton
            //通过密码校验 且手机号长度大于5 登录按钮可用
            if(pwdMobileInput.text!.count > 5 && LuckyUtils.checkPassword(password: str)){
                pwdLoginButton.isEnabled = true
            }else{
                pwdLoginButton.isEnabled = false
            }
            if(textField.isSecureTextEntry){
                //密文时替换内容
                textField.text = str
                return false
            }
        }else if(textField.tag == LuckyTagManager.loginTags.mobileCodeInput){
            //手机验证码框
            if(str.length > 4){
                //限长4
                return false
            }
            
            let mobileCodeView = staticMobileCodeView.viewWithTag(LuckyTagManager.loginTags.mobileCodeView) as! LuckyMobileCodeView
            mobileCodeView.setValue(value: str)
            //通过验证码规则校验 登录可用
            if(LuckyUtils.checkMobileCode(code: str)){
                codeLogin(code: str)
            }
        }
        return true
    }
    
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        //清空手机号 密码 登录按钮禁用
        if(textField.tag == LuckyTagManager.loginTags.codeMobileInput){
            let codeLoginButton = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeLoginButton) as! UIButton
            codeLoginButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.loginTags.pwdMobileInput){
            let pwdLoginButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdLoginButton) as! UIButton
            pwdLoginButton.isEnabled = false
        }else if(textField.tag == LuckyTagManager.loginTags.pwdPasswordInput){
            let pwdLoginButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdLoginButton) as! UIButton
            pwdLoginButton.isEnabled = false
        }
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //返回按钮收起键盘
        self.view.endEditing(true)
        return true
    }
    
    //国家选择器
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
            //有国家数据
            let countryLabel = UILabel(frame: CGRect(x: 20 * screenScale, y: 0, width: view.frame.width/2 - 20 * screenScale, height: view.frame.height))
            countryLabel.text = globalCountryList[row].name
            countryLabel.textColor = UIColor.fontBlack()
            countryLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            view.addSubview(countryLabel)
            
            //内容
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
            //有国家数据
            if let codeAreaButton = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeAreaButton) as? UIButton{
                //赋值 并调整区号按钮大小
                codeAreaButton.setTitle("+\(globalCountryList[row].telCode)", for: UIControl.State.normal)
                let areaTitleSize = codeAreaButton.intrinsicContentSize
                codeAreaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: codeAreaButton.frame.height)
                
                let codeMobileInput = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField
                codeMobileInput.frame = CGRect(x: codeAreaButton.frame.origin.x + codeAreaButton.frame.width, y: 0, width: staticCodeView.frame.width - (codeAreaButton.frame.origin.x + codeAreaButton.frame.width), height: codeAreaButton.frame.height)
            }
            if let pwdAreaButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdAreaButton) as? UIButton{
                //密码登录区域
                //更新两区域显示内容
                pwdAreaButton.setTitle("+\(globalCountryList[row].telCode)", for: UIControl.State.normal)
                let areaTitleSize = pwdAreaButton.intrinsicContentSize
                pwdAreaButton.frame.size = CGSize(width: areaTitleSize.width + 20 * screenScale, height: pwdAreaButton.frame.height)
                
                let pwdMobileInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdMobileInput) as! UITextField
                pwdMobileInput.frame = CGRect(x: pwdAreaButton.frame.origin.x + pwdAreaButton.frame.width, y: 0, width: staticPwdView.frame.width - (pwdAreaButton.frame.origin.x + pwdAreaButton.frame.width), height: pwdAreaButton.frame.height)
            }
            selectedCountry = row
        }
    }
    
    //生成图形验证码 内容值
    func getCapCode(){
        capCode = String(format: "%.6d", (arc4random() % 1000000))
        let codeCapView = staticCapCodeView.viewWithTag(LuckyTagManager.loginTags.capImageView) as! LuckyCaptchaCodeView
        let capInput = staticCapCodeView.viewWithTag(LuckyTagManager.loginTags.capCodeInput) as! UITextField
        codeCapView.load(captcha: capCode)
        capInput.text = ""
    }
    
    //手机号校验
    func checkPhone() -> Bool{
        if(selectedCountry == nil){
            return false
        }
        
        let codeMobileInput = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField
        if(codeMobileInput.text! == ""){
            return false
        }
        if(codeMobileInput.text!.count < 6 ){
            return false
        }
        return true
    }
    
    //校验是否满足发验证码条件
    func examineCode() -> Bool{
        //选择国家
        if(selectedCountry == nil){
            return false
        }
        
        //校验图形验证码
        let codeCapInput = staticCapCodeView.viewWithTag(LuckyTagManager.loginTags.capCodeInput) as! UITextField
        if(codeCapInput.text != capCode){
            return false
        }
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        //收起键盘
        self.view.endEditing(true)
    }
    
    //返回
    @objc func goBack(){
        if(self.navigationController?.tabBarController?.selectedIndex == 4){
            //上一页我的 返回首页
            self.navigationController?.tabBarController?.selectedIndex = 0
            self.navigationController?.popViewController(animated: true)
        }else{
            //其他 返回上一页
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    //隐藏手机验证码框
    @objc func hideMobileCodeView(){
        self.view.endEditing(true)
        let mobileCodeView = staticMobileCodeView.viewWithTag(LuckyTagManager.loginTags.mobileCodeView) as! LuckyMobileCodeView
        staticMobileCodeView.isHidden = true
        mobileCodeView.setValue(value: "")
        mobileCodeView.codeInput.text = ""
    }
    
    //明文/密文显示密码
    @objc func hidePwd(_ sender: UIButton){
        let pwdPasswordInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdPasswordInput) as! UITextField
        
        pwdPasswordInput.isSecureTextEntry = !sender.isSelected
        sender.isSelected = !sender.isSelected
    }
    
    //关闭图形验证码框
    @objc func hideCapView(){
        self.view.endEditing(true)
        staticCapCodeView.isHidden = true
    }
    
    //刷新图形验证码
    @objc func newCap(){
        getCapCode()
    }
    
    //展示图形验证码
    @objc func codeCapViewShow(){
        self.view.endEditing(true)
        //生成新验证码
        getCapCode()
        //校验手机号
        if(checkPhone()){
            staticCapCodeView.isHidden = false
        }else{
            LuckyAlertView(title: NSLocalizedString("Incorrect mobile number format, please try again", comment: "")).showByTime(time: 2)
        }
    }
    
    //验证码登录
    func codeLogin(code: String){
        let mobile = (staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField).text!
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.loginByCode(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", country: globalCountryList[selectedCountry!].uuid, code: code, success: { (userModel) in
            //成功
            //更新用户信息
            globalUserData = userModel
            LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
            LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.countryKey, data: globalCountryList[self.selectedCountry!].uuid as AnyObject)
            globalSelectedCountry = globalCountryList[self.selectedCountry!].uuid
            
            //取用户账户信息
            LuckyUserDataManager.getUserAccount { (userAccount) in
                globalUserAccount = userAccount
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                self.navigationController!.viewControllers[0].viewWillAppear(true)
                self.navigationController?.popViewController(animated: true)
            } fail: { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                self.navigationController!.viewControllers[0].viewWillAppear(true)
                self.navigationController?.popViewController(animated: true)
            }
        }) { (reason) in
            //失败 显示原因
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //密码登录
    @objc func pwdLogin(){
        //校验区号
        if(selectedCountry == nil){
            return
        }
        
        let mobile = (staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdMobileInput) as! UITextField).text!
        let password = (staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdPasswordInput) as! UITextField).text!
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.loginByPassword(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", password: password, success: { (userModel) in
            //成功
            //储存用户数据
            globalUserData = userModel
            LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
            LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.countryKey, data: globalCountryList[self.selectedCountry!].uuid as AnyObject)
            globalSelectedCountry = globalCountryList[self.selectedCountry!].uuid
            
            //获取账户数据
            LuckyUserDataManager.getUserAccount { (userAccount) in
                globalUserAccount = userAccount
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                self.navigationController!.viewControllers[0].viewWillAppear(true)
                self.navigationController?.popViewController(animated: true)
            } fail: { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                self.navigationController!.viewControllers[0].viewWillAppear(true)
                self.navigationController?.popViewController(animated: true)
            }
        }) { (reason) in
            //失败 显示原因
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //发送手机验证码
    @objc func sendCode(_ sender: UIButton){
        self.view.endEditing(true)
        let capInput = staticCapCodeView.viewWithTag(LuckyTagManager.loginTags.capCodeInput) as! UITextField
        if(examineCode()){
            //发送校验通过
            let mobile = (staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField).text!
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyMobileCodeManager.codeByMobile(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", codeType: LuckyMobileCodeManager.MobileCodeType.CODE, success: { (data) in
                //成功 显示验证码框 重发倒计时
                self.staticCapCodeView.isHidden = true
                self.staticMobileCodeView.isHidden = false
                let mobileCodeInput = self.staticMobileCodeView.viewWithTag(LuckyTagManager.loginTags.mobileCodeInput) as! UITextField
                let mobileCodeResendButton = self.staticMobileCodeView.viewWithTag(LuckyTagManager.loginTags.mobileCodeResendButton) as! LuckyMobileCodeSendButton
                mobileCodeResendButton.startTimer()
                mobileCodeInput.becomeFirstResponder()
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                //失败 显示原因
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: reason).showByTime(time: 2)
            }
        }else{
            LuckyAlertView(title: NSLocalizedString("Incorrect CAPTCHA code, please try again", comment: "")).showByTime(time: 2)
        }
        //重置图形验证码
        getCapCode()
        capInput.text = ""
    }
    
    //重发手机验证码
    @objc func resendCode(_ sender: LuckyMobileCodeSendButton){
        let mobileCodeView = self.staticMobileCodeView.viewWithTag(LuckyTagManager.loginTags.mobileCodeView) as! LuckyMobileCodeView
        let mobile = (staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField).text!
        
        //清空验证码输入
        mobileCodeView.setValue(value: "")
        mobileCodeView.codeInput.text = ""
        //再倒计时
        sender.startTimer()
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyMobileCodeManager.codeByMobile(mobile: "\(globalCountryList[selectedCountry!].telCode)\(mobile)", codeType: LuckyMobileCodeManager.MobileCodeType.CODE, success: { (data) in
            mobileCodeView.codeInput.becomeFirstResponder()
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            LuckyAlertView(title: reason).showByTime(time: 2)
        }
    }
    
    //苹果登录
    @objc func appleLogin(){
        if #available(iOS 13.0, *) {
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            LuckyAppleLoginManager.shared.loginInWithApple { (result, reason) in
                if(result){
                    //登录成功 取账户信息
                    LuckyUserDataManager.getUserAccount { (userAccount) in
                        globalUserAccount = userAccount
                        LuckyHttpManager.hideLoading(loadingView: loadingView)
                        self.navigationController!.viewControllers[0].viewWillAppear(true)
                        self.navigationController?.popViewController(animated: true)
                    } fail: { (reason) in
                        LuckyHttpManager.hideLoading(loadingView: loadingView)
                        self.navigationController!.viewControllers[0].viewWillAppear(true)
                        self.navigationController?.popViewController(animated: true)
                    }
                }else{
                    //失败 显示原因
                    LuckyAlertView(title: reason).showByTime(time: 2)
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                }
            }
        }
    }
    
    //非死不可登录
    @objc func fbLogin(){
        // 打开 FBSDKProfile 自动追踪 FBSDKAccessToken
        Profile.enableUpdatesOnAccessTokenChange(true)
        // 清空FBSDKAccessToken
        AccessToken.current = nil
        
        // 登录
        let loginManager: LoginManager = LoginManager()
        loginManager.logOut() // 先退出登录
        loginManager.logIn(permissions: ["public_profile"], from: self) { (result, error) in
            guard let res = result else {
                return
            }
            
            if error != nil {
                //登录失败
                LuckyAlertView(title: NSLocalizedString("Sign in failed", comment: "")).showByTime(time: 2)
            } else if res.isCancelled {
                //取消登录
            } else{
                //登录成功
                //相关值
                let tokenString: String = res.token?.tokenString ?? ""
                let userID: String = res.token?.userID ?? ""
                var imageUrl = ""
                var nickname = ""
                
                //取200*200头像
                if let profile = Profile.current {
                    nickname = profile.name ?? ""
                    if let url = profile.imageURL(forMode: Profile.PictureMode.normal, size: CGSize(width: 200, height: 200)) as NSURL?{
                        imageUrl = url.absoluteString ?? ""
                    }
                }
                
                if(tokenString == "" || userID == ""){
                    //内容不全 失败
                    LuckyAlertView(title: NSLocalizedString("Sign in failed", comment: "")).showByTime(time: 2)
                }else{
                    //非死不可成功 后台登录
                    let loadingView = LuckyHttpManager.showLoading(viewController: self)
                    LuckyHttpManager.loginByFacebook(userID: userID, token: tokenString, nickname: nickname, imageUrl: imageUrl) { (userModel) in
                        //成功
                        //存储用户信息
                        globalUserData = userModel
                        LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                        //获取账户信息
                        LuckyUserDataManager.getUserAccount { (userAccount) in
                            globalUserAccount = userAccount
                            LuckyHttpManager.hideLoading(loadingView: loadingView)
                            self.navigationController!.viewControllers[0].viewWillAppear(true)
                            self.navigationController?.popViewController(animated: true)
                        } fail: { (reason) in
                            LuckyHttpManager.hideLoading(loadingView: loadingView)
                            self.navigationController!.viewControllers[0].viewWillAppear(true)
                            self.navigationController?.popViewController(animated: true)
                        }
                    } fail: { (reason) in
                        //失败显示原因
                        LuckyAlertView(title: reason).showByTime(time: 2)
                        LuckyHttpManager.hideLoading(loadingView: loadingView)
                    }
                }
            }
        }
    }
    
    //显示选择区号框
    @objc func selectCountry(){
        self.view.endEditing(true)
        if(staticCountryPicker.numberOfComponents == 1){
            //如果未加载 重载
            staticCountryPicker.reloadAllComponents()
            if(selectedCountry != nil){
                //有初始选中 设初始选中状态
                staticCountryPicker.selectRow(selectedCountry!, inComponent: 0, animated: false)
            }
        }
        staticCountryView.isHidden = false
    }
    
    //关闭选择区号框
    @objc func closeCountryPicker(){
        staticCountryView.isHidden = true
    }
    
    //显示密码登录方式
    @objc func showPwdView(){
        staticCodeView.isHidden = true
        staticPwdView.isHidden = false
        
        //三方登录Signin状态
        if let appSignInButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.appSignInButton){
            appSignInButton.isHidden = false
        }
        if let appSignUpButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.appSignUpButton){
            appSignUpButton.isHidden = true
        }
        if let fbSignInButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.fbSignInButton) as? UIButton{
            fbSignInButton.setTitle("Sign in with Facebook", for: UIControl.State.normal)
        }
        if let bottomSignInView = staticBottomView.viewWithTag(LuckyTagManager.loginTags.bottomSignInView){
            bottomSignInView.isHidden = true
        }
        if let bottomSignUpView = staticBottomView.viewWithTag(LuckyTagManager.loginTags.bottomSignUpView){
            bottomSignUpView.isHidden = false
        }
        
        //同步手机号输入框内容
        let pwdMobileInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdMobileInput) as! UITextField
        let codeMobileInput = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField
        let pwdPasswordInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdPasswordInput) as! UITextField
        let pwdLoginButton = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdLoginButton) as! UIButton
        
        pwdMobileInput.text = codeMobileInput.text
        //判断登录按钮是否可用
        if(pwdMobileInput.text!.count > 5 && pwdPasswordInput.text!.count >= 8){
            pwdLoginButton.isEnabled = true
        }else{
            pwdLoginButton.isEnabled = false
        }
    }
    
    //显示验证码登录方式
    @objc func showCodeView(){
        staticPwdView.isHidden = true
        staticCodeView.isHidden = false
        
        //三方登录signup状态
        if let appSignInButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.appSignInButton){
            appSignInButton.isHidden = true
        }
        if let appSignUpButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.appSignUpButton){
            appSignUpButton.isHidden = false
        }
        if let fbSignInButton = staticBottomView.viewWithTag(LuckyTagManager.loginTags.fbSignInButton) as? UIButton{
            fbSignInButton.setTitle("Sign up with Facebook", for: UIControl.State.normal)
        }
        if let bottomSignUpView = staticBottomView.viewWithTag(LuckyTagManager.loginTags.bottomSignUpView){
            bottomSignUpView.isHidden = true
        }
        if let bottomSignInView = staticBottomView.viewWithTag(LuckyTagManager.loginTags.bottomSignInView){
            bottomSignInView.isHidden = false
        }
        
        //同步手机号输入框内容
        let pwdMobileInput = staticPwdView.viewWithTag(LuckyTagManager.loginTags.pwdMobileInput) as! UITextField
        let codeMobileInput = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeMobileInput) as! UITextField
        let codeLoginButton = staticCodeView.viewWithTag(LuckyTagManager.loginTags.codeLoginButton) as! UIButton
        
        codeMobileInput.text = pwdMobileInput.text
        
        //判断发手机验证码按钮是否可用
        if(codeMobileInput.text!.count > 5){
            codeLoginButton.isEnabled = true
        }else{
            codeLoginButton.isEnabled = false
        }
        
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去找回密码页
    @objc func toRetrieve(){
        let vc = LuckyRetrieveViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去用隐私条款页
    @objc func toPrivacy(){
        let vc = LuckyPrivacyViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
