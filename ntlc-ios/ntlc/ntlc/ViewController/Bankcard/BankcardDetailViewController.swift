//
//  BankcardDetailViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BankcardDetailViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var topView: UIView = UIView()
    var limitView: UIView = UIView()
    var deleteButton: UIButton = UIButton()
    var deleteView: AlertCodeView = AlertCodeView()
    
    var bankcard: BankcardModel? = nil
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        if(bankcard != nil){
            topView.removeFromSuperview()
            limitView.removeFromSuperview()
            deleteButton.removeFromSuperview()
            createTopView()
            createLimitView()
            createDeleteButton()
            createCodeView()
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        deleteView.removeFromSuperview()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        deleteView.codeInput.endEditing(true)
    }
    
    func createTopView(){
        topView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: 250 * screenScale))
        topView.backgroundColor = UIColor.white
        topView.layer.cornerRadius = cornerRadius * screenScale
        
        let bankcardView = UIView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: topView.frame.width - 10 * 2 * screenScale, height: 220 * screenScale))
        bankcardView.backgroundColor = UIColor.getColorByHex(hexString: bankcard!.color)
        bankcardView.layer.cornerRadius = 15 * screenScale
        bankcardView.layer.masksToBounds = true
        
        let background = UIImageView(frame: CGRect(x: bankcardView.frame.width/2, y: bankcardView.frame.height/3*1, width: bankcardView.frame.height, height: bankcardView.frame.height))
        background.alpha = 0.2
        bankcardView.addSubview(background)
        
        let icon = UIImageView(frame: CGRect(x: 19 * screenScale, y: 28 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + bankcard!.icon), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
            if result{
                background.image = SDImage
                icon.image = SDImage
            }
        }
        bankcardView.addSubview(icon)
        
        let name = UILabel(frame: CGRect(x: icon.frame.origin.x + icon.frame.width + 3 * screenScale, y: icon.frame.origin.y, width: 0, height: 0))
        name.text = bankcard!.shortName
        name.textColor = UIColor.white
        name.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        name.sizeToFit()
        name.frame.size = CGSize(width: name.frame.width, height: icon.frame.height)
        bankcardView.addSubview(name)
        
        let type = UILabel()
        type.text = "储蓄卡"
        type.textColor = UIColor.white.withAlphaComponent(0.8)
        type.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        type.sizeToFit()
        type.frame.size = CGSize(width: type.frame.width, height: icon.frame.height)
        type.frame.origin = CGPoint(x: bankcardView.frame.width - type.frame.width - 17 * screenScale, y: icon.frame.origin.y)
        bankcardView.addSubview(type)
        
        let num = UILabel(frame: CGRect(x: 0, y: bankcardView.frame.height - (44 + 33) * screenScale, width: bankcardView.frame.width - 20 * screenScale, height: 33 * screenScale))
        num.text = "**** **** **** \(bankcard!.bankcard)"
        num.textColor = UIColor.white
        num.font = UIFont.mainFont(size: 24 * screenScale)
        num.textAlignment = NSTextAlignment.right
        bankcardView.addSubview(num)
        
        topView.addSubview(bankcardView)
        mainView.addSubview(topView)
    }
    
    func createLimitView(){
        limitView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 133 * screenScale))
        limitView.backgroundColor = UIColor.white
        limitView.layer.cornerRadius = cornerRadius * screenScale
        
        let title = UILabel(frame: CGRect(x: 0, y: 18 * screenScale, width: limitView.frame.width, height: 22 * screenScale))
        title.text = "银行支付限额"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.lightFont(size: UIFont.bigSize() * screenScale)
        title.textAlignment = NSTextAlignment.center
        limitView.addSubview(title)
        
        let singleView = UILabel(frame: CGRect(x: 0, y: title.frame.origin.y + title.frame.height + 20 * screenScale, width: limitView.frame.width, height: 20 * screenScale))
        let singleImage = UIImageView(frame: CGRect(x: 26 * screenScale, y: 0, width: 21 * screenScale, height: singleView.frame.height))
        singleImage.image = UIImage(named: "bankcard_limit_single")
        singleView.addSubview(singleImage)
        let singleLabel = UILabel(frame: CGRect(x: singleImage.frame.origin.x + singleImage.frame.width + 10 * screenScale, y: 0, width: 0, height: 0))
        singleLabel.text = "单笔限额"
        singleLabel.textColor = UIColor.fontDarkGray()
        singleLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        singleLabel.sizeToFit()
        singleLabel.frame.size = CGSize(width: singleLabel.frame.width, height: singleView.frame.height)
        singleView.addSubview(singleLabel)
        let singleNum = UILabel(frame: CGRect(x: singleLabel.frame.origin.x + singleLabel.frame.width, y: 0, width: singleView.frame.width - (singleLabel.frame.origin.x + singleLabel.frame.width) - 20 * screenScale, height: singleView.frame.height))
        singleNum.text = String(bankcard!.singleLimit)
        singleNum.textColor = UIColor.fontBlack()
        singleNum.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        singleNum.textAlignment = NSTextAlignment.right
        singleView.addSubview(singleNum)
        limitView.addSubview(singleView)
        
        let dailyView = UILabel(frame: CGRect(x: 0, y: singleView.frame.origin.y + singleView.frame.height + 15 * screenScale, width: limitView.frame.width, height: 20 * screenScale))
        let dailyImage = UIImageView(frame: CGRect(x: 26 * screenScale, y: 0, width: 21 * screenScale, height: dailyView.frame.height))
        dailyImage.image = UIImage(named: "bankcard_limit_daily")
        dailyView.addSubview(dailyImage)
        let dailyLabel = UILabel(frame: CGRect(x: dailyImage.frame.origin.x + dailyImage.frame.width + 10 * screenScale, y: 0, width: 0, height: 0))
        dailyLabel.text = "每日限额"
        dailyLabel.textColor = UIColor.fontDarkGray()
        dailyLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        dailyLabel.sizeToFit()
        dailyLabel.frame.size = CGSize(width: dailyLabel.frame.width, height: dailyView.frame.height)
        dailyView.addSubview(dailyLabel)
        let dailyNum = UILabel(frame: CGRect(x: dailyLabel.frame.origin.x + dailyLabel.frame.width, y: 0, width: dailyView.frame.width - (dailyLabel.frame.origin.x + dailyLabel.frame.width) - 20 * screenScale, height: dailyView.frame.height))
        dailyNum.text = String(bankcard!.dailyLimit)
        dailyNum.textColor = UIColor.fontBlack()
        dailyNum.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        dailyNum.textAlignment = NSTextAlignment.right
        dailyView.addSubview(dailyNum)
        limitView.addSubview(dailyView)
        
        mainView.addSubview(limitView)
    }
    
    func createDeleteButton(){
        deleteButton = UIButton(frame: CGRect(x: paddingLeft * screenScale, y: limitView.frame.origin.y + limitView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 45 * screenScale))
        deleteButton.backgroundColor = UIColor.white
        deleteButton.layer.cornerRadius = cornerRadius * screenScale
        deleteButton.setTitle("解除绑定", for: UIControlState.normal)
        deleteButton.setTitleColor(UIColor(red: 0/255, green: 141/255, blue: 251/255, alpha: 1), for: UIControlState.normal)
        deleteButton.titleLabel?.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        deleteButton.addTarget(self, action: #selector(deleteButton(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(deleteButton)
    }
    
    func createCodeView(){
        deleteView = AlertCodeView()
        deleteView.codeInput.delegate = self
        deleteView.codeInput.tag = TagController.bankcardTags.inputCode
        deleteView.codeButton.addTarget(self, action: #selector(deleteSendCode(_:)), for: UIControlEvents.touchUpInside)
        deleteView.sureButton.addTarget(self, action: #selector(deleteSure(_:)), for: UIControlEvents.touchUpInside)
        UIApplication.shared.keyWindow?.addSubview(deleteView)
    }
    
    @objc func deleteButton(_ sender: UIButton) {
        hideKeyboard()
        deleteView.isHidden = false
    }
    
    @objc func deleteSure(_ sender: UIButton){
        hideKeyboard()
        if(checkDeleteSure()){
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("user/unbindBankcard", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "bankcard": self.bankcard!.uuid, "code": EncodingUtil.getBase64(self.deleteView.codeInput.text!)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if status == "SUCCESS" {
                        AlertView(title: "解绑成功").showByTime(time: 2)
                        self.navigationController?.popViewController(animated: true)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        AlertView(title: message).showByTime(time: 2)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }, errors: { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)
                })
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }
    }
    
    @objc func deleteSendCode(_ sender: CodeSendButton) {
        hideKeyboard()
        let loadingView = HttpController.showLoading(viewController: self)
        sender.startTimer()
        HttpController.getToken(data: { (token) in
            HttpController.post("user/unbindSendCode", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "bankcard": self.bankcard!.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    AlertView(title: "验证码发送成功！").showByTime(time: 2)
                }else{
                    let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                    AlertView(title: message).showByTime(time: 2)
                    sender.codeTime = 0
                }
                HttpController.hideLoading(loadingView: loadingView)
            }, errors: { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
                sender.codeTime = 0
            })
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
            sender.codeTime = 0
        }
        
    }
    
    func checkDeleteSure() -> Bool{
        if(deleteView.codeInput.text == ""){
            AlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkCode(code: deleteView.codeInput.text!)){
            AlertView(title: "请输入正确的验证码").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.bankcardTags.inputCode && textField.text!.count + string.count > 6){
            return false
        }else{
            return true
        }
    }
}
