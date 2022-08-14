import Foundation
class SportsLoginViewController: UIViewController, UITextFieldDelegate {
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var kapCodeView: UIView = UIView()
    var codeView: UIView = UIView()
    let paddingLeft: CGFloat = 35 * screenScale
    let submitUncheckedColor = UIColor(red: 217/255, green: 217/255, blue: 217/255, alpha: 1)
    var kapCode: String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        kapCodeView.isHidden = true
        self.view.addSubview(kapCodeView)
        codeView.isHidden = true
        self.view.addSubview(codeView)
        createHead()
        createBody()
        createKapCodeView()
        createCodeView()
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    func createHead() {
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: abs(navigationFrame.origin.y) + 140 * screenScale))
        let backButton = UIButton(frame: CGRect(x: 0, y: abs(navigationFrame.origin.y) - navigationFrame.height + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
        backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_close")
        backButton.addSubview(backIconView)
        headView.addSubview(backButton)
        let textLabel = UILabel(frame: CGRect(x: paddingLeft, y: abs(navigationFrame.origin.y) + 60 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        textLabel.text = "短信验证码登录"
        textLabel.textColor = UIColor.colorFontBlack()
        textLabel.font = UIFont.fontBold(size: 28 * screenScale)
        headView.addSubview(textLabel)
        let contentLabel = UILabel(frame: CGRect(x: textLabel.frame.origin.x, y: textLabel.frame.origin.y + textLabel.frame.height + 10 * screenScale, width: textLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = "未验证手机号验证后自动注册"
        contentLabel.textColor = UIColor.colorFontDarkGray()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        headView.addSubview(contentLabel)
    }
    func createBody() {
        bodyView.frame = CGRect(x: paddingLeft, y: headView.frame.origin.y + headView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 200 * screenScale)
        
        let codePhoneView = UIView(frame: CGRect(x: 0, y: 0 , width: bodyView.frame.width, height: 40 * screenScale))
        let codePhoneNum = UILabel(frame: CGRect(x: 0, y: 0, width: 40 * screenScale, height: codePhoneView.frame.height))
        codePhoneNum.text = "+86"
        codePhoneNum.textColor = UIColor.colorFontDarkGray()
        codePhoneNum.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        codePhoneView.addSubview(codePhoneNum)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: codePhoneNum.frame.origin.x + codePhoneNum.frame.width, y: 12 * screenScale, width: 1 * screenScale, height: 16 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        codePhoneView.layer.addSublayer(splitLine)
        
        let codePhoneInput = UITextField(frame: CGRect(x: splitLine.frame.origin.x + splitLine.frame.width + 15 * screenScale, y: 0, width: codePhoneView.frame.width - (splitLine.frame.origin.x + splitLine.frame.width + 15 * screenScale), height: codePhoneView.frame.height))
        codePhoneInput.delegate = self
        codePhoneInput.keyboardType = UIKeyboardType.numberPad
        codePhoneInput.clearButtonMode = UITextField.ViewMode.always
        codePhoneInput.tag = SportsNumberController.LoginNumbers.phoneInput
        codePhoneInput.textColor = UIColor.colorFontBlack()
        codePhoneInput.font = UIFont.fontMedium(size: UIFont.FontSizeBiggest() * screenScale)
        codePhoneInput.attributedPlaceholder = NSAttributedString(string: "请输入手机号码", attributes: [NSAttributedString.Key.foregroundColor : UIColor.ColorPlaceholder(), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)])
        codePhoneInput.clearButtonMode = UITextField.ViewMode.whileEditing
        let codePhoneBottom = CALayer()
        codePhoneBottom.frame = CGRect(x: 0, y: codePhoneView.frame.height + 1, width: codePhoneView.frame.width, height: 1)
        codePhoneBottom.backgroundColor = UIColor.colorBgGray().cgColor
        codePhoneView.layer.addSublayer(codePhoneBottom)
        codePhoneView.addSubview(codePhoneInput)
        bodyView.addSubview(codePhoneView)
        
        let documentView = UIView(frame: CGRect(x: 0, y: codePhoneView.frame.origin.y + codePhoneView.frame.height + 10 * screenScale, width: bodyView.frame.width, height: 20 * screenScale))
        let documentLabel = UILabel(frame: CGRect(x: 0, y: 0, width: documentView.frame.width, height: documentView.frame.height))
        documentLabel.textColor = UIColor.colorFontDarkGray()
        documentLabel.text = "登录/注册表示同意 "
        documentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
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
        bodyView.addSubview(documentView)
        
        let codeButton = UIButton(frame: CGRect(x: 0, y: documentView.frame.origin.y + documentView.frame.height + 50 * screenScale, width: bodyView.frame.width, height: 45 * screenScale))
        codeButton.tag = SportsNumberController.LoginNumbers.codeButton
        codeButton.layer.cornerRadius = 2 * screenScale
        codeButton.backgroundColor = submitUncheckedColor
        codeButton.setTitle("获取验证码", for: UIControl.State.normal)
        codeButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        codeButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        codeButton.addTarget(self, action: #selector(showKapCode(_:)), for: UIControl.Event.touchUpInside)
        bodyView.addSubview(codeButton)
    }
    func createKapCodeView(){
        kapCodeView.frame = CGRect(origin: CGPoint.zero, size: self.view.frame.size)
        kapCodeView.backgroundColor = UIColor.black.withAlphaComponent(0.4)
        let kapCodeMainView = UIView(frame: CGRect(x: (self.view.frame.width - 275 * screenScale)/2, y: (self.view.frame.height - 170 * screenScale) / 2, width: 275 * screenScale, height: 170 * screenScale))
        kapCodeMainView.backgroundColor = UIColor.white
        kapCodeMainView.layer.cornerRadius = 4 * screenScale
        kapCodeMainView.layer.masksToBounds = true
        let kapLabel = UILabel(frame: CGRect(x: 0, y: 15 * screenScale, width: kapCodeMainView.frame.width, height: 20 * screenScale))
        kapLabel.text = "请在下方输入图形验证码"
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
        kapInput.tag = SportsNumberController.LoginNumbers.kapInput
        kapInput.textColor = UIColor.colorFontBlack()
        kapInput.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        kapInput.attributedPlaceholder = NSAttributedString(string: "请输入", attributes: [NSAttributedString.Key.foregroundColor : UIColor.ColorPlaceholder(), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        kapInput.clearButtonMode = UITextField.ViewMode.never
        kapInputView.addSubview(kapInput)
        let kapView = SportsKaptchaCodeView(frame: CGRect(x: kapInputView.frame.width - 100 * screenScale, y: (kapInputView.frame.height - 36 * screenScale)/2, width: 90 * screenScale, height: 36 * screenScale))
        kapView.tag = SportsNumberController.LoginNumbers.kapImageView
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
    func createCodeView(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        codeView.frame = CGRect(origin: CGPoint.zero, size: self.view.frame.size)
        codeView.backgroundColor = UIColor.white
        let returnButton = UIButton(frame: CGRect(x: 0, y: abs(navigationFrame.origin.y) - navigationFrame.height + 5 * screenScale, width: 100 * screenScale, height: 30 * screenScale))
        returnButton.addTarget(self, action: #selector(hideCodeView(_:)), for: UIControl.Event.touchUpInside)
        let returnIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        returnIconView.image = UIImage(named: "image_back")
        returnButton.addSubview(returnIconView)
        codeView.addSubview(returnButton)
        
        let textLabel = UILabel(frame: CGRect(x: paddingLeft, y: abs(navigationFrame.origin.y) + 60 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        textLabel.text = "输入验证码"
        textLabel.textColor = UIColor.colorFontBlack()
        textLabel.font = UIFont.fontBold(size: 28 * screenScale)
        codeView.addSubview(textLabel)
        let contentLabel = UILabel(frame: CGRect(x: textLabel.frame.origin.x, y: textLabel.frame.origin.y + textLabel.frame.height + 10 * screenScale, width: textLabel.frame.width, height: 20 * screenScale))
        contentLabel.text = "未验证手机号验证后自动注册"
        contentLabel.textColor = UIColor.colorFontDarkGray()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        codeView.addSubview(contentLabel)
        
        let codeCodeView = SportsCodeView(frame: CGRect(x: paddingLeft, y: contentLabel.frame.origin.y + contentLabel.frame.height + 35 * screenScale, width: screenWidth - paddingLeft * 2, height: 44 * screenScale))
        codeCodeView.tag = SportsNumberController.LoginNumbers.codeCodeView
        codeCodeView.codeInput.delegate = self
        codeCodeView.codeInput.tag = SportsNumberController.LoginNumbers.codeInput
        codeView.addSubview(codeCodeView)
        
        let submitButton = UIButton(frame: CGRect(x: paddingLeft, y: codeCodeView.frame.origin.y + codeCodeView.frame.height + 50 * screenScale, width: codeView.frame.width - paddingLeft * 2, height: 45 * screenScale))
        submitButton.tag = SportsNumberController.LoginNumbers.submitButton
        submitButton.layer.cornerRadius = 2 * screenScale
        submitButton.backgroundColor = submitUncheckedColor
        submitButton.setTitle("登录", for: UIControl.State.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        submitButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControl.Event.touchUpInside)
        codeView.addSubview(submitButton)
        
        let resendButton = SportsCodeSendButton(frame: CGRect(x: (codeView.frame.width - 80 * screenScale)/2, y: submitButton.frame.origin.y + submitButton.frame.height + 15 * screenScale, width: 80 * screenScale, height: 20 * screenScale))
        resendButton.tag = SportsNumberController.LoginNumbers.codeResendButton
        resendButton.addTarget(self, action: #selector(resendCode(_:)), for: UIControl.Event.touchUpInside)
        codeView.addSubview(resendButton)
    }
    func getKapCode(){
        kapCode = String(format: "%.6d", (arc4random() % 1000000))
        let codeKapView = kapCodeView.viewWithTag(SportsNumberController.LoginNumbers.kapImageView) as! SportsKaptchaCodeView
        let kapInput = kapCodeView.viewWithTag(SportsNumberController.LoginNumbers.kapInput) as! UITextField
        codeKapView.load(kaptcha: kapCode)
        kapInput.text = ""
    }
    @objc func toPrivacy(_ recognizer: UITapGestureRecognizer){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsDocumentViewController") as! SportsDocumentViewController
        vc.urlString = "document/privacyProtocol"
        vc.pageTitle = "隐私政策"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func toAgreement(_ recognizer: UITapGestureRecognizer){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsDocumentViewController") as! SportsDocumentViewController
        vc.urlString = "document/userAgreement"
        vc.pageTitle = "用户服务协议"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func hideCodeView(_ sender: UIButton){
        hideKeyboard()
        let codeCodeView = codeView.viewWithTag(SportsNumberController.LoginNumbers.codeCodeView) as! SportsCodeView
        codeView.isHidden = true
        codeCodeView.setValue(value: "")
        codeCodeView.codeInput.text = ""
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
    @objc func resendCode(_ sender: SportsCodeSendButton){
        let codeCodeView = self.codeView.viewWithTag(SportsNumberController.LoginNumbers.codeCodeView) as! SportsCodeView
        let phone = (bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as! UITextField).text!
        
        codeCodeView.setValue(value: "")
        codeCodeView.codeInput.text = ""
        sender.startTimer()
        
        let loadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.getTime(data: { (timestamp) in
            let codeString = secretKey + timestamp + phone + "code"
            let token = SportsEncodingUtil.getBase64(systemType + "0000" + timestamp + codeString.md5)
            SportsHttpWorker.get("front/sms/sendCode", params: NSDictionary(dictionary: ["codeType" : SportsEncodingUtil.getBase64("code"), "deviceType": SportsEncodingUtil.getBase64("freekick" ), "mobile": SportsEncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    SportsAlertView(title: "短信验证码发送成功！").showByTime(time: 2)
                    codeCodeView.codeInput.becomeFirstResponder()
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
    }
    @objc func sendCode(_ sender: UIButton){
        hideKeyboard()
        let kapInput = kapCodeView.viewWithTag(SportsNumberController.LoginNumbers.kapInput) as! UITextField
        if(examineCode()){
            let phone = (bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as! UITextField).text!
            let loadingView = SportsHttpWorker.showLoading(viewController: self)
            SportsHttpWorker.getTime(data: { (timestamp) in
                let codeString = secretKey + timestamp + phone + "code"
                let token = SportsEncodingUtil.getBase64(systemType + "0000" + timestamp + codeString.md5)
                SportsHttpWorker.get("front/sms/sendCode", params: NSDictionary(dictionary: ["codeType" : SportsEncodingUtil.getBase64("code"), "deviceType": SportsEncodingUtil.getBase64("freekick" ), "mobile": SportsEncodingUtil.getBase64(phone), "token": token]), data: { (data) in
                    let dataDictionary = data as! NSDictionary
                    let status = dataDictionary.object(forKey: "status") as! String
                    if(status == "SUCCESS"){
                        SportsAlertView(title: "短信验证码发送成功！").showByTime(time: 2)
                        self.kapCodeView.isHidden = true
                        self.codeView.isHidden = false
                        let codeInput = self.codeView.viewWithTag(SportsNumberController.LoginNumbers.codeInput) as! UITextField
                        let codeResendButton = self.codeView.viewWithTag(SportsNumberController.LoginNumbers.codeResendButton) as! SportsCodeSendButton
                        codeResendButton.startTimer()
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
        let codePhoneInput = bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as! UITextField
        if(codePhoneInput.text! == ""){
            return false
        }
        if(!SportsUtils.examinePhome(phone: codePhoneInput.text!)){
            return false
        }
        return true
    }
    func examineCode() -> Bool{
        let codeKapInput = kapCodeView.viewWithTag(SportsNumberController.LoginNumbers.kapInput) as! UITextField
        if(codeKapInput.text != kapCode){
            return false
        }
        return true
    }
    @objc func submit(_ sender: UIButton){
        hideKeyboard()
        if(checkSubmit()){
            let phone = (bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as! UITextField).text!
            let code = (codeView.viewWithTag(SportsNumberController.LoginNumbers.codeInput) as! UITextField).text!
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
        let codePhoneInput = bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as! UITextField
        let codeCodeInput = codeView.viewWithTag(SportsNumberController.LoginNumbers.codeInput) as! UITextField
        if(codePhoneInput.text! == ""){
            SportsAlertView(title: "请输入手机号码").showByTime(time: 2)
            return false
        }
        if(codeCodeInput.text! == ""){
            SportsAlertView(title: "请输入验证码").showByTime(time: 2)
            return false
        }
        if(!SportsUtils.examinePhome(phone: codePhoneInput.text!)){
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
        let codePhone = bodyView.viewWithTag(SportsNumberController.LoginNumbers.phoneInput) as? UITextField
        let codeCode = codeView.viewWithTag(SportsNumberController.LoginNumbers.codeInput) as? UITextField
        let codeKap = kapCodeView.viewWithTag(SportsNumberController.LoginNumbers.kapInput) as? UITextField
        codePhone?.endEditing(true)
        codeCode?.endEditing(true)
        codeKap?.endEditing(true)
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
        let codeButton = bodyView.viewWithTag(SportsNumberController.LoginNumbers.codeButton) as! UIButton
        let submitButton = codeView.viewWithTag(SportsNumberController.LoginNumbers.submitButton) as! UIButton
        if(textField.tag == SportsNumberController.LoginNumbers.phoneInput){
            if(str.length > 11){
                str = str[0 ..< 11]
            }
            if(SportsUtils.examinePhome(phone: str)){
                codeButton.backgroundColor = UIColor.colorMainColor()
                codeButton.isEnabled = true
            }else{
                codeButton.backgroundColor = submitUncheckedColor
                codeButton.isEnabled = false
            }
        }
        if(textField.tag == SportsNumberController.LoginNumbers.codeInput){
            let codeCodeView = codeView.viewWithTag(SportsNumberController.LoginNumbers.codeCodeView) as! SportsCodeView
            if(str.length > 6){
                str = str[0 ..< 6]
            }
            codeCodeView.setValue(value: str)
            if(SportsUtils.examineCode(code: str)){
                submitButton.backgroundColor = UIColor.colorMainColor()
                submitButton.isEnabled = true
            }else{
                submitButton.backgroundColor = submitUncheckedColor
                submitButton.isEnabled = false
            }
        }
        if(textField.tag == SportsNumberController.LoginNumbers.phoneInput && textField.text!.count + string.count > 11){
            return false
        }else if(textField.tag == SportsNumberController.LoginNumbers.codeInput && textField.text!.count + string.count > 6){
            return false
        }else if(textField.tag == SportsNumberController.LoginNumbers.kapInput && textField.text!.count + string.count > 6){
            return false
        }
        return true
    }
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if(textField.tag == SportsNumberController.LoginNumbers.codeInput){
            let codeCodeView = codeView.viewWithTag(SportsNumberController.LoginNumbers.codeCodeView) as! SportsCodeView
            codeCodeView.setValue(value: textField.text!)
        }
    }
}
