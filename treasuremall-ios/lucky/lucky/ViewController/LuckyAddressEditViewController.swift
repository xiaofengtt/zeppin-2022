//
//  LuckyAddressEditViewController.swift
//  lucky
//  编辑地址
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAddressEditViewController: UIViewController, UITextFieldDelegate, UIPickerViewDataSource, UIPickerViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //功能区
    private var staticBodyView: UIView!
    //底
    private var staticBottomView: UIView!
    //国家选择框
    private var staticPickerView: UIView!
    private var staticPicker: UIPickerView!
    
    //类型 add/edit
    var type: String = ""
    //数据 修改时使用
    var data: LuckyFrontUserAddressModel? = nil
    //选中国家
    var selectedCountry: String = ""
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        //创建底
        staticBottomView = createBottomView()
        self.view.addSubview(staticBottomView)
        //创建功能区
        staticBodyView = createBodyView()
        self.view.addSubview(staticBodyView)
        self.view.addSubview(staticHeaderView)
        //创建国家选择页
        staticPickerView = createPickerView()
        self.view.addSubview(staticPickerView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.splitLine.isHidden = true
        if(type == "add"){
            //添加模式
            headView.titleLabel.text = NSLocalizedString("Add a New Address", comment: "")
        }else{
            //修改模式
            headView.titleLabel.text = NSLocalizedString("Edit Address", comment: "")
        }
        //右侧 提交储存按钮
        headView.rightButton.setTitle(NSLocalizedString("Save", comment: ""), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(submit), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - bottomSafeHeight - 50 * screenScale, width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        bottomView.backgroundColor = UIColor.white
        if(type == "edit"){
            //修改模式 删除按钮
            let topLine = CALayer()
            topLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
            topLine.backgroundColor = UIColor.backgroundGray().cgColor
            bottomView.layer.addSublayer(topLine)
            let button = UIButton(frame: CGRect(x: paddingLeft, y: 5 * screenScale, width: bottomView.frame.width - paddingLeft * 2, height: 40 * screenScale))
            button.layer.masksToBounds = true
            button.layer.cornerRadius = 6 * screenScale
            button.backgroundColor = UIColor.mainYellow()
            button.setTitle(NSLocalizedString("Delete", comment: ""), for: UIControl.State.normal)
            button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            button.addTarget(self, action: #selector(deleteAddress), for: UIControl.Event.touchUpInside)
            bottomView.addSubview(button)
        }
        return bottomView
    }
    
    //创建功能区
    func createBodyView() -> UIView{
        let bodyView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: staticBottomView.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        bodyView.backgroundColor = UIColor.white
        
        //国家
        let countryView = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 50 * screenScale))
        let enterIconView = UIImageView(frame: CGRect(x: countryView.frame.width - 18 * screenScale, y: (countryView.frame.height - 13 * screenScale)/2, width: 8 * screenScale, height: 13 * screenScale))
        enterIconView.image = UIImage(named: "image_enter_gray")
        countryView.addSubview(enterIconView)
        let countryLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: enterIconView.frame.origin.x - paddingLeft * 2, height: countryView.frame.height))
        countryLabel.tag = LuckyTagManager.addressTags.countryLabel
        countryLabel.text = NSLocalizedString("Country/Region", comment: "")
        countryLabel.textColor = UIColor.fontBlack()
        countryLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        countryView.addSubview(countryLabel)
        //国家选择按钮
        let countryButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: countryView.frame.size))
        countryButton.addTarget(self, action: #selector(selectCountry), for: UIControl.Event.touchUpInside)
        countryView.addSubview(countryButton)
        let countryBottomLine = UIView()
        countryBottomLine.tag = LuckyTagManager.addressTags.countryBottomLine
        countryBottomLine.frame = CGRect(x: 0, y: countryView.frame.height - 1, width: countryView.frame.width, height: 1)
        countryBottomLine.backgroundColor = UIColor.backgroundGray()
        countryView.addSubview(countryBottomLine)
        bodyView.addSubview(countryView)
        
        //地址 各层级 输入框
        let contactInput = LuckyAddressTextField(frame: CGRect(x: 0, y: countryView.frame.origin.y + countryView.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("Contact Name", comment: ""))
        contactInput.tag = LuckyTagManager.addressTags.contactInput
        contactInput.delegate = self
        bodyView.addSubview(contactInput)
        
        let streetInput = LuckyAddressTextField(frame: CGRect(x: 0, y: contactInput.frame.origin.y + contactInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("Street Address or P.O.Box", comment: ""))
        streetInput.tag = LuckyTagManager.addressTags.streetInput
        streetInput.delegate = self
        bodyView.addSubview(streetInput)
        
        let asubInput = LuckyAddressTextField(frame: CGRect(x: 0, y: streetInput.frame.origin.y + streetInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("Apt,Suite,Unit,Building(optional)", comment: ""))
        asubInput.tag = LuckyTagManager.addressTags.asubInput
        asubInput.delegate = self
        bodyView.addSubview(asubInput)
        
        let cityInput = LuckyAddressTextField(frame: CGRect(x: 0, y: asubInput.frame.origin.y + asubInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("City", comment: ""))
        cityInput.tag = LuckyTagManager.addressTags.cityInput
        cityInput.delegate = self
        bodyView.addSubview(cityInput)
        
        let stateInput = LuckyAddressTextField(frame: CGRect(x: 0, y: cityInput.frame.origin.y + cityInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("State/Province/Region", comment: ""))
        stateInput.tag = LuckyTagManager.addressTags.stateInput
        stateInput.delegate = self
        bodyView.addSubview(stateInput)
        
        let zipCodeInput = LuckyAddressTextField(frame: CGRect(x: 0, y: stateInput.frame.origin.y + stateInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("ZIP Code", comment: ""))
        zipCodeInput.tag = LuckyTagManager.addressTags.zipCodeInput
        zipCodeInput.delegate = self
        bodyView.addSubview(zipCodeInput)
        
        let mobileInput = LuckyAddressTextField(frame: CGRect(x: 0, y: zipCodeInput.frame.origin.y + zipCodeInput.frame.height, width: countryView.frame.width, height: countryView.frame.height), placeholder: NSLocalizedString("Mobile Number", comment: ""))
        mobileInput.tag = LuckyTagManager.addressTags.mobileInput
        mobileInput.keyboardType = UIKeyboardType.phonePad
        mobileInput.delegate = self
        bodyView.addSubview(mobileInput)
        
        //修改模式 初始化数据
        if(data != nil){
            selectedCountry = data!.internationalInfo
            countryLabel.text = data!.country
            contactInput.text = data!.receiver
            streetInput.text = data!.address
            asubInput.text = data!.asub
            cityInput.text = data!.city
            stateInput.text = data!.state
            zipCodeInput.text = data!.zipcode
            mobileInput.text = data!.phone
        }
        
        return bodyView
    }
    
    //创建国家选择页
    func createPickerView() -> UIView{
        let pickerView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        pickerView.isHidden = true
        pickerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //国家选择器
        staticPicker = UIPickerView(frame: CGRect(x: 0, y: pickerView.frame.height - 200 * screenScale - bottomSafeHeight, width: pickerView.frame.width, height: 200 * screenScale))
        staticPicker.backgroundColor = UIColor.white
        staticPicker.dataSource = self
        staticPicker.delegate = self
        pickerView.addSubview(staticPicker)
        
        let bottomView = UIView(frame: CGRect(x: 0, y: pickerView.frame.height - bottomSafeHeight, width: pickerView.frame.width, height: bottomSafeHeight))
        bottomView.backgroundColor = staticPicker.backgroundColor
        pickerView.addSubview(bottomView)
        
        //头
        let headerView = UIView(frame: CGRect(x: 0, y: staticPicker.frame.origin.y - 50 * screenScale, width: pickerView.frame.width, height: 50 * screenScale))
        headerView.backgroundColor = UIColor.white
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: headerView.frame.width - headerView.frame.height, y: 0, width: headerView.frame.height, height: headerView.frame.height))
        closeButton.setTitle(NSLocalizedString("Done", comment: ""), for: UIControl.State.normal)
        closeButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        closeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        closeButton.addTarget(self, action: #selector(closeCountryPicker), for: UIControl.Event.touchUpInside)
        headerView.addSubview(closeButton)
        let headerLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: headerView.frame.width/2, height: headerView.frame.height))
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
        
        //编辑模式 初始化选中状态
        if(data != nil){
            for i in 0 ..< globalCountryList.count{
                if(globalCountryList[i].uuid == data!.internationalInfo){
                    staticPicker.selectRow(i, inComponent: 0, animated: false)
                }
            }
        }
        return pickerView
    }
    
    //删除数据
    func deleteData(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/userAddress/delete", params: ["uuid": data!.uuid], success: { (data) in
            //成功 刷新地址列表页 并返回
            let parents = self.navigationController!.viewControllers
            if let parent = (parents[parents.count - 2] as? LuckyAddressListViewController){
                parent.getList()
            }
            
            self.navigationController?.popViewController(animated: true)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
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
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if(globalCountryList.count > 0){
            return globalCountryList[row].name
        }else{
            return ""
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if(globalCountryList.count > 0){
            //有数据 修改国家显示内容
            if let countryLabel = staticBodyView.viewWithTag(LuckyTagManager.addressTags.countryLabel) as? UILabel{
                let countryBottomLine = staticBodyView.viewWithTag(LuckyTagManager.addressTags.countryBottomLine)
                
                countryBottomLine?.backgroundColor = UIColor.backgroundGray()
                countryLabel.text = globalCountryList[row].name
                selectedCountry = globalCountryList[row].uuid
            }
        }
    }
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if(screenHeight < 800 ){
            //屏幕高度不够800 x以下
            if(textField.tag == LuckyTagManager.addressTags.mobileInput || textField.tag == LuckyTagManager.addressTags.zipCodeInput || textField.tag == LuckyTagManager.addressTags.stateInput){
                //编辑最后三行 功能区内容上移
                UIView.animate(withDuration: 0.5) {
                    self.staticBodyView.frame.origin = CGPoint(x: 0, y: self.staticHeaderView.frame.origin.y + self.staticHeaderView.frame.height - 100 * screenScale)
                }
            }else{
                //其他行 功能区复位
                UIView.animate(withDuration: 0.5) {
                    self.staticBodyView.frame.origin = CGPoint(x: 0, y: self.staticHeaderView.frame.origin.y + self.staticHeaderView.frame.height)
                }
            }
        }
    }
    
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        if(screenHeight < 800){
            //屏幕高度不够800 x以下
            //功能区复位
            UIView.animate(withDuration: 0.5) {
                self.staticBodyView.frame.origin = CGPoint(x: 0, y: self.staticHeaderView.frame.origin.y + self.staticHeaderView.frame.height)
            }
        }
        return true
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(str.count > 50){
            //限长 50
            return false
        }
        
        if(str != ""){
            //内容不为空 红色警告消除
            if let field = textField as? LuckyAddressTextField{
                field.setNormal()
            }
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //删除地址 提示框
    @objc func deleteAddress(){
        let alertController = UIAlertController(title: nil, message: NSLocalizedString("Are you sure you want to delete this address?", comment: ""), preferredStyle: UIAlertController.Style.alert)
        let noAction = UIAlertAction(title: NSLocalizedString("No", comment: ""), style: UIAlertAction.Style.cancel, handler: nil)
        noAction.setValue(UIColor.fontGray(), forKey: "_titleTextColor")
        let okAction = UIAlertAction(title: NSLocalizedString("Yes", comment: ""), style: UIAlertAction.Style.default) { (alertAction) in
            self.deleteData()
        }
        okAction.setValue(UIColor.mainYellow(), forKey: "_titleTextColor")
        
        alertController.addAction(noAction)
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
    }
    
    //显示国家选择页
    @objc func selectCountry(){
        self.view.endEditing(true)
        if(staticPicker.numberOfComponents == 1){
            //初始化选中状态
            staticPicker.reloadAllComponents()
            if(selectedCountry != ""){
                for i in 0 ..< globalCountryList.count{
                    if(globalCountryList[i].uuid == selectedCountry){
                        staticPicker.selectRow(i, inComponent: 0, animated: false)
                    }
                }
            }
        }
        staticPickerView.isHidden = false
    }
    
    //隐藏国家选择页
    @objc func closeCountryPicker(){
        staticPickerView.isHidden = true
    }
    
    //提交保存
    @objc func submit(){
        if(selectedCountry == ""){
            let countryBottomLine = staticBodyView.viewWithTag(LuckyTagManager.addressTags.countryBottomLine)
            countryBottomLine?.backgroundColor = UIColor.mainRed()
            return
        }
        
        //非空判断 为空的标红提示
        let contactInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.contactInput) as! LuckyAddressTextField
        let streetInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.streetInput) as! LuckyAddressTextField
        let asubInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.asubInput) as! LuckyAddressTextField
        let cityInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.cityInput) as! LuckyAddressTextField
        let stateInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.stateInput) as! LuckyAddressTextField
        let zipCodeInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.zipCodeInput) as! LuckyAddressTextField
        let mobileInput = staticBodyView.viewWithTag(LuckyTagManager.addressTags.mobileInput) as! LuckyAddressTextField
        
        if(contactInput.text! == ""){
            contactInput.setError()
            return
        }
        if(streetInput.text! == ""){
            streetInput.setError()
            return
        }
        if(cityInput.text! == ""){
            cityInput.setError()
            return
        }
        if(stateInput.text! == ""){
            stateInput.setError()
            return
        }
        if(zipCodeInput.text! == ""){
            zipCodeInput.setError()
            return
        }
        if(mobileInput.text! == ""){
            mobileInput.setError()
            return
        }
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        let dataDic: NSMutableDictionary = ["receiver": contactInput.text!, "phone": mobileInput.text!, "address": streetInput.text!, "country": selectedCountry, "city": cityInput.text!, "state": stateInput.text!, "zipcode": zipCodeInput.text!, "asub": asubInput.text!]
        
        if(type == "add"){
            //添加模式
            LuckyHttpManager.postWithToken("front/userAddress/add", params: dataDic, success: { (data) in
                //成功 刷新地址列表页并返回
                let parents = self.navigationController!.viewControllers
                if let parent = (parents[parents.count - 2] as? LuckyAddressListViewController){
                    parent.getList()
                }
                
                self.navigationController?.popViewController(animated: true)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }else{
            //修改模式
            dataDic["uuid"] = data!.uuid
            LuckyHttpManager.postWithToken("front/userAddress/edit", params: dataDic, success: { (data) in
                //成功 刷新地址列表页并返回
                let parents = self.navigationController!.viewControllers
                if let parent = (parents[parents.count - 2] as? LuckyAddressListViewController){
                    parent.getList()
                }
                
                self.navigationController?.popViewController(animated: true)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
}
