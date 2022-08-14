//
//  IdcardAuthViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/30.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class IdcardAuthViewController: UIViewController, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var topView: UIView = UIView()
    var infoView: UIView = UIView()
    var photoView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createTopView()
        createInfoView()
//        createPhotoView()
        createSubmitButton()
        super.viewDidLoad()
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func hideKeyboard(){
        let nameInput = infoView.viewWithTag(TagController.idcardAuthTags.inputName) as? UITextField
        let idcardInput = infoView.viewWithTag(TagController.idcardAuthTags.inputIdcard) as? UITextField
        
        nameInput?.endEditing(true)
        idcardInput?.endEditing(true)
    }
    
    func createTopView(){
        topView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 40 * screenScale))
        topView.backgroundColor = UIColor.white
        topView.layer.cornerRadius = cornerRadius * screenScale
        
        let image = UIImageView(frame: CGRect(x: 8 * screenScale, y: 12 * screenScale, width: 16 * screenScale, height: 16 * screenScale))
        image.image = UIImage(named: "common_notice")
        topView.addSubview(image)
        
        let label = UILabel(frame: CGRect(x: image.frame.origin.x + image.frame.width + 2 * screenScale, y: image.frame.origin.y, width: topView.frame.width - (image.frame.origin.x + image.frame.width + 2 * screenScale), height: image.frame.height))
        label.text = "温馨提示：实名认证一旦成功将不能修改"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        topView.addSubview(label)
        mainView.addSubview(topView)
    }
    
    func createInfoView(){
        infoView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: topView.frame.origin.y + topView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 100 * screenScale))
        infoView.backgroundColor = UIColor.white
        infoView.layer.cornerRadius = cornerRadius * screenScale
        
        let nameView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: infoView.frame.width, height: 50 * screenScale)))
        let nameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 68 * screenScale, height: nameView.frame.height))
        nameLabel.text = "姓名"
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        nameView.addSubview(nameLabel)
        let nameInput = UITextField(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: nameView.frame.width - (nameLabel.frame.origin.x + nameLabel.frame.width) - 8 * screenScale, height: nameView.frame.height))
        nameInput.delegate = self
        nameInput.tag = TagController.idcardAuthTags.inputName
        nameInput.keyboardType = UIKeyboardType.default
        nameInput.textColor = UIColor.fontBlack()
        nameInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        nameInput.attributedPlaceholder = NSAttributedString(string: "请填写真实的姓名", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        nameInput.clearButtonMode = UITextFieldViewMode.always
        nameView.addSubview(nameInput)
        let nameBottomLine = CALayer()
        nameBottomLine.frame = CGRect(x: 0, y: nameView.frame.height - 1, width: nameView.frame.width, height: 1)
        nameBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        nameView.layer.addSublayer(nameBottomLine)
        infoView.addSubview(nameView)
        
        let idcardView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: nameView.frame.origin.y + nameView.frame.height), size: CGSize(width: infoView.frame.width, height: 50 * screenScale)))
        let idcardLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 68 * screenScale, height: idcardView.frame.height))
        idcardLabel.text = "身份证"
        idcardLabel.textColor = UIColor.fontBlack()
        idcardLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        idcardView.addSubview(idcardLabel)
        let idcardInput = UITextField(frame: CGRect(x: idcardLabel.frame.origin.x + idcardLabel.frame.width, y: 0, width: idcardView.frame.width - (idcardLabel.frame.origin.x + idcardLabel.frame.width) - 8 * screenScale, height: idcardView.frame.height))
        idcardInput.delegate = self
        idcardInput.tag = TagController.idcardAuthTags.inputIdcard
        idcardInput.keyboardType = UIKeyboardType.alphabet
        idcardInput.textColor = UIColor.fontBlack()
        idcardInput.font = UIFont.mediumFont(size: UIFont.biggestSize() * screenScale)
        idcardInput.attributedPlaceholder = NSAttributedString(string: "请输入18位身份证号码", attributes: [NSAttributedStringKey.foregroundColor : UIColor.mainPlaceholder(), NSAttributedStringKey.font: UIFont.mainFont(size: UIFont.middleSize() * screenScale)])
        idcardInput.clearButtonMode = UITextFieldViewMode.always
        idcardView.addSubview(idcardInput)
        infoView.addSubview(idcardView)
        mainView.addSubview(infoView)
    }
    
    func createPhotoView(){
        photoView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: infoView.frame.origin.y + infoView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 230 * screenScale))
        photoView.backgroundColor = UIColor.white
        photoView.layer.cornerRadius = cornerRadius * screenScale
        
        let titleLabel = UILabel(frame: CGRect(x: 9 * screenScale, y: 0, width: photoView.frame.width - 9 * 2 * screenScale, height: 52 * screenScale))
        titleLabel.text = "请分别上传身份证正面和背面照片"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        photoView.addSubview(titleLabel)
        
        let frontView = UIView(frame: CGRect(x: 9 * screenScale, y: titleLabel.frame.origin.y + titleLabel.frame.height, width: photoView.frame.width/2 - 13 * screenScale, height: 130 * screenScale))
        frontView.backgroundColor = UIColor.backgroundGray()
        frontView.layer.cornerRadius = cornerRadius * screenScale
        let frontImage = UIImageView(frame: CGRect(x: frontView.frame.width*3/10, y: (frontView.frame.height - frontView.frame.width/5)/2, width: frontView.frame.width*2/5, height: frontView.frame.width/5))
        frontImage.image = UIImage(named: "auth_front")
        frontView.addSubview(frontImage)
        let frontLabel = UILabel(frame: CGRect(x: 0, y: frontImage.frame.origin.y + frontImage.frame.height + 15 * screenScale, width: frontView.frame.width, height: 17 * screenScale))
        frontLabel.tag = TagController.idcardAuthTags.frontLabel
        frontLabel.text = "上传身份证正面"
        frontLabel.textColor = UIColor.fontGray()
        frontLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        frontLabel.textAlignment = NSTextAlignment.center
        frontView.addSubview(frontLabel)
        let frontPhoto = UIImageView()
        frontPhoto.tag = TagController.idcardAuthTags.frontPhoto
        frontPhoto.isHidden = true
        frontView.addSubview(frontPhoto)
        let frontButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: frontView.frame.size))
        frontButton.addTarget(self, action: #selector(frontUpload(_:)), for: UIControlEvents.touchUpInside)
        frontView.addSubview(frontButton)
        photoView.addSubview(frontView)
        
        let backView = UIView(frame: CGRect(x: photoView.frame.width/2 + 4 * screenScale, y: frontView.frame.origin.y , width: frontView.frame.width, height: frontView.frame.height))
        backView.backgroundColor = frontView.backgroundColor
        backView.layer.cornerRadius = frontView.layer.cornerRadius
        let backImage = UIImageView(frame: frontImage.frame)
        backImage.image = UIImage(named: "auth_back")
        backView.addSubview(backImage)
        let backLabel = UILabel(frame: frontLabel.frame)
        backLabel.tag = TagController.idcardAuthTags.backLabel
        backLabel.text = "上传身份证反面"
        backLabel.textColor = frontLabel.textColor
        backLabel.font = frontLabel.font
        backLabel.textAlignment = frontLabel.textAlignment
        backView.addSubview(backLabel)
        let backPhoto = UIImageView()
        backPhoto.tag = TagController.idcardAuthTags.backPhoto
        backPhoto.isHidden = true
        backView.addSubview(backPhoto)
        let backButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: backView.frame.size))
        backButton.addTarget(self, action: #selector(backUpload(_:)), for: UIControlEvents.touchUpInside)
        backView.addSubview(backButton)
        photoView.addSubview(backView)
        
        let textLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: frontView.frame.origin.y + frontView.frame.height + 15 * screenScale, width: titleLabel.frame.width, height: 17 * screenScale))
        textLabel.text = "注释：上传的身份证必须真实清晰才能通过审核"
        textLabel.textColor = UIColor.fontDarkGray()
        textLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        photoView.addSubview(textLabel)
        mainView.addSubview(photoView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: infoView.frame.origin.y + infoView.frame.height + 44 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("提交认证", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    
    @objc func frontUpload(_ sender: UIButton){
        hideKeyboard()
    }
    
    @objc func backUpload(_ sender: UIButton){
        hideKeyboard()
    }
    
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let name = (infoView.viewWithTag(TagController.idcardAuthTags.inputName) as! UITextField).text!
            let idcard = (infoView.viewWithTag(TagController.idcardAuthTags.inputIdcard) as! UITextField).text!
            
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.getToken(data: { (token) in
                HttpController.post("user/certification", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "name": EncodingUtil.getBase64(name), "idcard": EncodingUtil.getBase64(idcard)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        HttpController.getUser(uuid: user!.uuid, data: { (data) in
                            let sb = UIStoryboard(name: "Main", bundle: nil)
                            let vc = sb.instantiateViewController(withIdentifier: "idcardSuccessViewController") as! IdcardSuccessViewController
                            self.navigationController?.pushViewController(vc, animated: true)
                        }, errors: { (error) in
                            let sb = UIStoryboard(name: "Main", bundle: nil)
                            let vc = sb.instantiateViewController(withIdentifier: "idcardSuccessViewController") as! IdcardSuccessViewController
                            self.navigationController?.pushViewController(vc, animated: true)
                        })
                    }else{
                        let message = dataDictionary.object(forKey: "message") as! String
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "idcardFailedViewController") as! IdcardFailedViewController
                        vc.message = message
                        self.navigationController?.pushViewController(vc, animated: true)
                    }
                    HttpController.hideLoading(loadingView: loadingView)
                }, errors: { (error) in
                    HttpController.hideLoading(loadingView: loadingView)
                    HttpController.showTimeout(viewController: self)    
                })
            }, errors: { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            })
        }
    }
    
    func checkSubmit() -> Bool{
        let nameInput = infoView.viewWithTag(TagController.idcardAuthTags.inputName) as! UITextField
        let idcardInput = infoView.viewWithTag(TagController.idcardAuthTags.inputIdcard) as! UITextField
        
        if(nameInput.text! == ""){
            AlertView(title: "请输入真实姓名").showByTime(time: 2)
            return false
        }
        
        if(idcardInput.text! == ""){
            AlertView(title: "请输入身份证号码").showByTime(time: 2)
            return false
        }
        
        if(!Utils.checkIdcard(idcard: idcardInput.text!)){
            AlertView(title: "请输入正确的身份证号码").showByTime(time: 2)
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if(textField.tag == TagController.idcardAuthTags.inputIdcard && textField.text!.count + string.count > 18){
            return false
        }else if(textField.tag == TagController.idcardAuthTags.inputName && textField.text!.count + string.count > 36){
            return false
        }
        return true
    }
}
