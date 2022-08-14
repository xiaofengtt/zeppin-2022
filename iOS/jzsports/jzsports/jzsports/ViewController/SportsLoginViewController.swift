import Foundation
class SportsLoginViewController: UIViewController, UITextFieldDelegate {
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var kapCodeView: UIView = UIView()
    let paddingLeft: CGFloat = 35 * screenScale
    let submitUncheckedColor = UIColor(red: 222/255, green: 222/255, blue: 222/255, alpha: 1)
    var kapCode: String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        kapCodeView.isHidden = true
        self.view.addSubview(kapCodeView)
        createHead()
        createBody()
        createKapCodeView()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    func createHead() {
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: (navigationFrame.height + statusBarHeight) + 140 * screenScale))
        let backButton = UIButton(frame: CGRect(x: 0, y: (navigationFrame.height + statusBarHeight) - navigationFrame.height + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
        backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_close")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        let logoView = UIImageView(frame: CGRect(x: screenWidth/2 - screenWidth/12, y: abs(navigationFrame.origin.y) + 15 * screenScale, width: screenWidth/6, height: screenWidth/6))
        logoView.image = UIImage(named: "image_logo")
        logoView.layer.cornerRadius = 10 * screenScale
        logoView.clipsToBounds = true
        headView.addSubview(logoView)
        
        let textView = UIImageView(frame: CGRect(x: screenWidth / 2 - 40 * screenScale, y: logoView.frame.origin.y + logoView.frame.height + 10 * screenScale, width: 80 * screenScale, height: 20 * screenScale))
        textView.image = UIImage(named: "image_logo_words")
        headView.addSubview(textView)
    }
    func createBody() {
        bodyView.frame = CGRect(x: paddingLeft, y: headView.frame.origin.y + headView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2, height: self.view.frame.height - (headView.frame.origin.y + headView.frame.height + 10 * screenScale))
        let codePhoneLabel = UILabel(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 30 * screenScale))
        codePhoneLabel.text = "手机号"
        codePhoneLabel.textColor = UIColor.colorFontBlack()
        codePhoneLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        bodyView.addSubview(codePhoneLabel)
        let codePhoneInput = UITextField(frame: CGRect(x: 0, y: codePhoneLabel.frame.origin.y + codePhoneLabel.frame.height, width: bodyView.frame.width, height: 40 * screenScale))
        codePhoneInput.delegate = self
        codePhoneInput.keyboardType = UIKeyboardType.numberPad
        codePhoneInput.tag = SportsTagController.LoginTags.phoneInput
        codePhoneInput.textColor = UIColor.colorFontBlack()
        codePhoneInput.font = UIFont.fontMedium(size: UIFont.FontSizeBiggest() * screenScale)
        codePhoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号", attributes: [NSAttributedString.Key.foregroundColor : submitUncheckedColor, NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        codePhoneInput.clearButtonMode = UITextField.ViewMode.whileEditing
        let codePhoneBottom = CALayer()
        codePhoneBottom.frame = CGRect(x: 0, y: codePhoneInput.frame.height + 1, width: codePhoneInput.frame.width, height: 1)
        codePhoneBottom.backgroundColor = UIColor.colorBgGray().cgColor
        codePhoneInput.layer.addSublayer(codePhoneBottom)
        bodyView.addSubview(codePhoneInput)
        let codeCodeLabel = UILabel(frame: CGRect(x: 0, y: codePhoneInput.frame.origin.y + codePhoneInput.frame.height + 15 * screenScale, width: bodyView.frame.width, height: 30 * screenScale))
        codeCodeLabel.text = "短信验证码"
        codeCodeLabel.textColor = codePhoneLabel.textColor
        codeCodeLabel.font = codePhoneLabel.font
        bodyView.addSubview(codeCodeLabel)
        let codeCodeInput = UITextField(frame: CGRect(x: 0, y: codeCodeLabel.frame.origin.y + codeCodeLabel.frame.height, width: bodyView.frame.width - 80 * screenScale, height: codePhoneInput.frame.height))
        codeCodeInput.delegate = self
        codeCodeInput.clearButtonMode = UITextField.ViewMode.whileEditing
        codeCodeInput.keyboardType = UIKeyboardType.numberPad
        codeCodeInput.tag = SportsTagController.LoginTags.codeInput
        codeCodeInput.textColor = codePhoneInput.textColor
        codeCodeInput.font = codePhoneInput.font
        codeCodeInput.attributedPlaceholder = NSAttributedString(string: "请输入验证码", attributes: [NSAttributedString.Key.foregroundColor : submitUncheckedColor, NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        let codeCodeBottom = CALayer()
        codeCodeBottom.frame = CGRect(x: 0, y: codeCodeInput.frame.height + 1, width: bodyView.frame.width, height: 1)
        codeCodeBottom.backgroundColor = codePhoneBottom.backgroundColor
        codeCodeInput.layer.addSublayer(codeCodeBottom)
        bodyView.addSubview(codeCodeInput)
        
        let codeCodeButton = SportsCodeSendButton(frame: CGRect(x: bodyView.frame.width - 80 * screenScale, y: codeCodeInput.frame.origin.y, width: 80 * screenScale, height: 40 * screenScale))
        codeCodeButton.tag = SportsTagController.LoginTags.codeButton
        codeCodeButton.addTarget(self, action: #selector(showKapCode), for: UIControl.Event.touchUpInside)
        bodyView.addSubview(codeCodeButton)
        
        let alertLabel = UILabel(frame: CGRect(x: 0, y: codeCodeInput.frame.origin.y + codeCodeInput.frame.height + 12 * screenScale, width: bodyView.frame.width, height: 20 * screenScale))
        alertLabel.text = "未验证手机号在验证后自动注册"
        alertLabel.textColor = UIColor.colorFontDarkGray()
        alertLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        alertLabel.textAlignment = NSTextAlignment.right
        bodyView.addSubview(alertLabel)
        
        let submitButton = UIButton(frame: CGRect(x: 0, y: alertLabel.frame.origin.y + alertLabel.frame.height + 30 * screenScale, width: bodyView.frame.width, height: 45 * screenScale))
        submitButton.tag = SportsTagController.LoginTags.submitButton
        submitButton.layer.cornerRadius = submitButton.frame.height/2
        submitButton.backgroundColor = submitUncheckedColor
        submitButton.isEnabled = false
        submitButton.setTitle("登录", for: UIControl.State.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        submitButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControl.Event.touchUpInside)
        bodyView.addSubview(submitButton)
        
        let documentView = UIView(frame: CGRect(x: 0, y: submitButton.frame.origin.y + submitButton.frame.height + 20 * screenScale, width: bodyView.frame.width, height: 20 * screenScale))
        let documentLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        documentLabel.textColor = UIColor.colorFontGray()
        documentLabel.text = "登录/注册表示您同意 "
        documentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        documentLabel.sizeToFit()
        documentLabel.frame = CGRect(x: 0, y: 0, width: documentLabel.frame.width, height: documentView.frame.height)
        documentView.addSubview(documentLabel)
        let privacyLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        privacyLabel.isUserInteractionEnabled = true
        privacyLabel.textColor = UIColor.colorMainColor()
        privacyLabel.text = " 隐私政策 "
        privacyLabel.font = documentLabel.font
        privacyLabel.sizeToFit()
        privacyLabel.frame = CGRect(x: documentLabel.frame.origin.x + documentLabel.frame.width, y: 0, width: privacyLabel.frame.width, height: documentView.frame.height)
        privacyLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toPrivacy(_:))))
        documentView.addSubview(privacyLabel)
        let andLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        andLabel.textColor = documentLabel.textColor
        andLabel.text = " 和 "
        andLabel.font = documentLabel.font
        andLabel.sizeToFit()
        andLabel.frame = CGRect(x: privacyLabel.frame.origin.x + privacyLabel.frame.width, y: 0, width: andLabel.frame.width, height: documentView.frame.height)
        documentView.addSubview(andLabel)
        let agreementLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        agreementLabel.isUserInteractionEnabled = true
        agreementLabel.textColor = UIColor.colorMainColor()
        agreementLabel.text = " 用户服务协议 "
        agreementLabel.font = documentLabel.font
        agreementLabel.sizeToFit()
        agreementLabel.frame = CGRect(x: andLabel.frame.origin.x + andLabel.frame.width, y: 0, width: agreementLabel.frame.width, height: documentView.frame.height)
        agreementLabel.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toAgreement(_:))))
        documentView.addSubview(agreementLabel)
        documentView.frame = CGRect(x: (bodyView.frame.width - (agreementLabel.frame.origin.x + agreementLabel.frame.width)) / 2, y: submitButton.frame.origin.y + submitButton.frame.height + 20 * screenScale, width: agreementLabel.frame.origin.x + agreementLabel.frame.width, height: 20 * screenScale)
        bodyView.addSubview(documentView)
    }
    func createKapCodeView(){
        kapCodeView.frame = CGRect(origin: CGPoint.zero, size: self.view.frame.size)
        kapCodeView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        let kapCodeMainView = UIView(frame: CGRect(x: (self.view.frame.width - 275 * screenScale)/2, y: (self.view.frame.height - 170 * screenScale) / 2, width: 275 * screenScale, height: 170 * screenScale))
        kapCodeMainView.backgroundColor = UIColor.white
        kapCodeMainView.layer.cornerRadius = 4 * screenScale
        kapCodeMainView.layer.masksToBounds = true
        let kapLabel = UILabel(frame: CGRect(x: 0, y: 15 * screenScale, width: kapCodeMainView.frame.width, height: 20 * screenScale))
        kapLabel.text = "请在输入下方的图形验证码"
        kapLabel.textColor = UIColor.colorFontBlack()
        kapLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        kapLabel.textAlignment = NSTextAlignment.center
        kapCodeMainView.addSubview(kapLabel)
        let kapInputView = UIView(frame: CGRect(x: 10 * screenScale, y: kapLabel.frame.origin.y + kapLabel.frame.height + 15 * screenScale, width: kapCodeMainView.frame.width - 20 * screenScale, height: 50 * screenScale))
        kapInputView.backgroundColor = UIColor.colorBgGray()
        kapInputView.layer.cornerRadius = 4 * screenScale
        kapInputView.clipsToBounds = true
        let kapInput = UITextField(frame: CGRect(x: 10 * screenScale, y: 0, width: kapInputView.frame.width - 20 * screenScale, height: kapInputView.frame.height))
        kapInput.delegate = self
        kapInput.keyboardType = UIKeyboardType.numberPad
        kapInput.tag = SportsTagController.LoginTags.kapInput
        kapInput.textColor = UIColor.colorFontBlack()
        kapInput.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        kapInput.attributedPlaceholder = NSAttributedString(string: "图形验证码", attributes: [NSAttributedString.Key.foregroundColor : UIColor.ColorPlaceholder(), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        kapInput.clearButtonMode = UITextField.ViewMode.never
        kapInputView.addSubview(kapInput)
        let kapView = SportsKaptchaCodeView(frame: CGRect(x: kapInputView.frame.width - 100 * screenScale, y: (kapInputView.frame.height - 36 * screenScale)/2, width: 90 * screenScale, height: 36 * screenScale))
        kapView.tag = SportsTagController.LoginTags.kapImageView
        kapView.layer.cornerRadius = 4 * screenScale
        kapView.clipsToBounds = true
        kapInputView.addSubview(kapView)
        let kapButton = UIButton(frame: kapView.frame)
        kapButton.addTarget(self, action: #selector(newKap(_:)), for: UIControl.Event.touchUpInside)
        kapInputView.addSubview(kapButton)
        kapCodeMainView.addSubview(kapInputView)
        let cancelButton = UIButton(frame: CGRect(x: 0, y: kapCodeMainView.frame.height - 40 * screenScale, width: kapCodeMainView.frame.width/2, height: 40 * screenScale))
        cancelButton.layer.borderColor = UIColor.colorBgGray().cgColor
        cancelButton.layer.borderWidth = 1 * screenScale
        cancelButton.setTitle("取消", for: UIControl.State.normal)
        cancelButton.setTitleColor(UIColor.colorFontGray(), for: UIControl.State.normal)
        cancelButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        cancelButton.addTarget(self, action: #selector(hideKapView(_:)), for: UIControl.Event.touchUpInside)
        kapCodeMainView.addSubview(cancelButton)
        let sureButton = UIButton(frame: CGRect(x: kapCodeMainView.frame.width/2, y: cancelButton.frame.origin.y, width: cancelButton.frame.width, height: cancelButton.frame.height))
        sureButton.layer.borderColor = cancelButton.layer.borderColor
        sureButton.layer.borderWidth = cancelButton.layer.borderWidth
        sureButton.setTitle("确定", for: UIControl.State.normal)
        sureButton.setTitleColor(UIColor.colorFontBlack(), for: UIControl.State.normal)
        sureButton.titleLabel?.font = cancelButton.titleLabel?.font
        sureButton.addTarget(self, action: #selector(sendCode(_:)), for: UIControl.Event.touchUpInside)
        kapCodeMainView.addSubview(sureButton)
        kapCodeView.addSubview(kapCodeMainView)
    }
    func getKapCode(){
        kapCode = String(format: "%.6d", (arc4random() % 1000000))
        let codeKapView = kapCodeView.viewWithTag(SportsTagController.LoginTags.kapImageView) as! SportsKaptchaCodeView
        let kapInput = kapCodeView.viewWithTag(SportsTagController.LoginTags.kapInput) as! UITextField
        codeKapView.load(kaptcha: kapCode)
        kapInput.text = ""
    }
    @objc func toPrivacy(_ recognizer: UITapGestureRecognizer){
        let vc = SportsDocumentViewController()
        vc.urlString = "document/privacyProtocol"
        vc.pageTitle = "隐私政策"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func toAgreement(_ recognizer: UITapGestureRecognizer){
        let vc = SportsDocumentViewController()
        vc.urlString = "document/userAgreement"
        vc.pageTitle = "用户服务协议"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func hideKapView(_ sender: UIButton){
        hideKeyboard()
        kapCodeView.isHidden = true
    }
    @objc func newKap(_ sender: UIButton){
        getKapCode()
    }
    @objc func showKapCode(_ sender: UIButton){
        hideKeyboard()
        getKapCode()
        if(checkKap()){
            kapCodeView.isHidden = false
        }else{
            SportsAlertView(title: "请输入正确的手机号码！").showByTime(time: 2)
        }
    }
    @objc func sendCode(_ sender: UIButton){
        hideKeyboard()
        let kapInput = kapCodeView.viewWithTag(SportsTagController.LoginTags.kapInput) as! UITextField
        if(examineCode()){
            let phone = (bodyView.viewWithTag(SportsTagController.LoginTags.phoneInput) as! UITextField).text!
            let loadingView = SportsHttpWorker.showLoading(viewController: self)
            SportsHttpWorker.getTime(data: { (timestamp) in
                let codeString = secretKey + timestamp + phone + "code"
                let token = SportsEncodingUtil.getBase64(systemType + "0000" + timestamp + codeString.md5)
                SportsHttpWorker.get("front/sms/sendCode", params: NSDictionary(dictionary: ["codeType" : SportsEncodingUtil.getBase64("code"), "deviceType": SportsEncodingUtil.getBase64("jiuzhou" ), "mobile": SportsEncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        let codeButton = self.bodyView.viewWithTag(SportsTagController.LoginTags.codeButton) as! SportsCodeSendButton
                        codeButton.startTimer()
                        SportsAlertView(title: "短信验证码发送成功！").showByTime(time: 2)
                        self.kapCodeView.isHidden = true
                        let codeInput = self.bodyView.viewWithTag(SportsTagController.LoginTags.codeInput) as! UITextField
                        codeInput.becomeFirstResponder()
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        SportsAlertView(title: message).showByTime(time: 2)
                    }
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                }, errors: { (error) in
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                    SportsHttpWorker.showTimeout(viewController: self)
                })
            }, errors: { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            })
        }else{
            SportsAlertView(title: "请输入正确的图形验证码！").showByTime(time: 2)
        }
        getKapCode()
        kapInput.text = ""
    }
    func checkKap() -> Bool{
        let codePhoneInput = bodyView.viewWithTag(SportsTagController.LoginTags.phoneInput) as! UITextField
        if(codePhoneInput.text! == ""){
            return false
        }
        if(!SportsUtils.examinePhone(phone: codePhoneInput.text!)){
            return false
        }
        return true
    }
    func examineCode() -> Bool{
        let codeKapInput = kapCodeView.viewWithTag(SportsTagController.LoginTags.kapInput) as! UITextField
        if(codeKapInput.text != kapCode){
            return false
        }
        return true
    }
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let phone = (bodyView.viewWithTag(SportsTagController.LoginTags.phoneInput) as! UITextField).text!
            let code = (bodyView.viewWithTag(SportsTagController.LoginTags.codeInput) as! UITextField).text!
            let loadingView = SportsHttpWorker.showLoading(viewController: self)
            SportsHttpWorker.getTime(data: { (timestamp) in
                let codeString = secretKey + timestamp + phone + code;
                let token = SportsEncodingUtil.getBase64(systemType + timestamp + codeString.md5)
                SportsHttpWorker.post("loginFront/login", params: NSDictionary(dictionary: ["token" : token,"mobile" : SportsEncodingUtil.getBase64(phone)]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        let data = dataDictionary.object(forKey: "data") as! NSDictionary
                        let frontUser = data.object(forKey: "frontUser") as! NSDictionary
                        user = SportsFrontUserModel(data: frontUser)
                        SportsLocalDataController.writeLocalData("user", data: user!.getDictionary())
                        SportsHttpWorker.hideLoading(loadingView: loadingView)
                        self.navigationController?.popViewController(animated: true)
                    }else{
                        let message = String.valueOf(any: dataDictionary.object(forKey: "message"))
                        SportsAlertView(title: message).showByTime(time: 2)
                        SportsHttpWorker.hideLoading(loadingView: loadingView)
                    }
                }, errors: { (error) in
                    SportsHttpWorker.hideLoading(loadingView: loadingView)
                    SportsHttpWorker.showTimeout(viewController: self)
                })
            }, errors: { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            })
        }else{
            SportsAlertView(title: "请输入正确的验证码").showByTime(time: 2)
        }
    }
    func checkSubmit() -> Bool{
        let codePhoneInput = bodyView.viewWithTag(SportsTagController.LoginTags.phoneInput) as! UITextField
        let codeCodeInput = bodyView.viewWithTag(SportsTagController.LoginTags.codeInput) as! UITextField
        if(codePhoneInput.text! == ""){
            SportsAlertView(title: "请输入手机号码").showByTime(time: 2)
            return false
        }
        if(codeCodeInput.text! == ""){
            SportsAlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        if(!SportsUtils.examinePhone(phone: codePhoneInput.text!)){
            SportsAlertView(title: "请输入正确的手机号码").showByTime(time: 2)
            return false
        }
        if(!SportsUtils.examineCode(code: codeCodeInput.text!)){
            SportsAlertView(title: "请输入正确的验证码").showByTime(time: 2)
            return false
        }
        return true
    }
    @objc func hideKeyboard(){
        self.view.endEditing(true)
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        let codePhoneInput = bodyView.viewWithTag(SportsTagController.LoginTags.phoneInput) as! UITextField
        let codeCodeInput = bodyView.viewWithTag(SportsTagController.LoginTags.codeInput) as! UITextField
        let submitButton = bodyView.viewWithTag(SportsTagController.LoginTags.submitButton) as! UIButton
        let codeButton = bodyView.viewWithTag(SportsTagController.LoginTags.codeButton) as! SportsCodeSendButton
        if(textField.tag == SportsTagController.LoginTags.phoneInput){
            if(str.length > 11){
                str = str[0 ..< 11]
            }
            if(SportsUtils.examinePhone(phone: str)){
                codeButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
                codeButton.isEnabled = true
            }else{
                codeButton.setTitleColor(UIColor(red: 171/255, green: 192/255, blue: 255/255, alpha: 1), for: UIControl.State.normal)
                codeButton.isEnabled = false
            }
            if(SportsUtils.examinePhone(phone: str) && SportsUtils.examineCode(code: codeCodeInput.text!)){
                submitButton.backgroundColor = UIColor.colorMainColor()
                submitButton.isEnabled = true
            }else{
                submitButton.backgroundColor = submitUncheckedColor
                submitButton.isEnabled = false
            }
            
        }else if(textField.tag == SportsTagController.LoginTags.codeInput){
            if(str.length > 6){
                str = str[0 ..< 6]
            }
            if(SportsUtils.examinePhone(phone: codePhoneInput.text!) && SportsUtils.examineCode(code: str)){
                submitButton.backgroundColor = UIColor.colorMainColor()
                submitButton.isEnabled = true
            }else{
                submitButton.backgroundColor = submitUncheckedColor
                submitButton.isEnabled = false
            }
        }
        if(textField.tag == SportsTagController.LoginTags.phoneInput && textField.text!.count + string.count > 11){
           return false
        }else if(textField.tag == SportsTagController.LoginTags.codeInput && textField.text!.count + string.count > 6){
           return false
        }else if(textField.tag == SportsTagController.LoginTags.kapInput && textField.text!.count + string.count > 6){
           return false
        }
        return true
    }
}
