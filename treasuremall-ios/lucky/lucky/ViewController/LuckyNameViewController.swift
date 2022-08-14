//
//  LuckyNameViewController.swift
//  lucky
//  修改昵称页
//  Created by Farmer Zhu on 2020/8/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyNameViewController: UIViewController, UITextFieldDelegate {
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
        headView.titleLabel.text = NSLocalizedString("Name", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitle(NSLocalizedString("Save", comment: ""), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(save), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建功能区
    func createMainView() -> UIView {
        let paddingLeft: CGFloat = 10 * screenScale
        
        let mainView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        //字数统计
        let nameCountLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: mainView.frame.width - paddingLeft*2, height: 30 * screenScale))
        nameCountLabel.tag = LuckyTagManager.settingTags.nameCountLabel
        nameCountLabel.text = "0/11"
        nameCountLabel.textColor = UIColor.fontGray()
        nameCountLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        mainView.addSubview(nameCountLabel)
        
        //输入框
        let nameInput = UITextField(frame: CGRect(x: 0, y: nameCountLabel.frame.origin.y + nameCountLabel.frame.height, width: mainView.frame.width, height: 50 * screenScale))
        nameInput.tag = LuckyTagManager.settingTags.nameInput
        nameInput.delegate = self
        nameInput.leftViewMode = UITextField.ViewMode.always
        nameInput.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: nameInput.frame.height))
        nameInput.backgroundColor = UIColor.white
        nameInput.clearButtonMode = UITextField.ViewMode.whileEditing
        nameInput.leftView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: paddingLeft, height: nameInput.frame.height)))
        nameInput.tintColor = UIColor.mainYellow()
        nameInput.keyboardType = UIKeyboardType.asciiCapable
        nameInput.autocapitalizationType = UITextAutocapitalizationType.none
        nameInput.textColor = UIColor.fontBlack()
        nameInput.attributedPlaceholder = NSAttributedString(string: NSLocalizedString("Enter your name", comment: ""), attributes: [NSAttributedString.Key.foregroundColor : UIColor.placeholderGray(), NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)])
        mainView.addSubview(nameInput)
        
        return mainView
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(str.count > 11){
            //限长 11
            return false
        }
        
        //修改字数统计
        let nameCountLabel = staticMainView.viewWithTag(LuckyTagManager.settingTags.nameCountLabel) as! UILabel
        nameCountLabel.text = "\(str.count)/11"
        
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //提交保存
    @objc func save(){
        self.view.endEditing(true)
        let nameInput = staticMainView.viewWithTag(LuckyTagManager.settingTags.nameInput) as! UITextField
        let name = nameInput.text!
        
        if("" == name){
            //昵称为空
            LuckyAlertView(title: NSLocalizedString("Nicknname can’t be blank", comment: "")).showByTime(time: 2)
            return
        }
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/user/editNickname", params: ["nickname": name], success: { (data) in
            //修改成功 返回上一页
            self.navigationController?.popViewController(animated: true)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
}
