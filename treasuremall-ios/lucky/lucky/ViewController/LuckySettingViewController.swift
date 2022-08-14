//
//  LuckySettingViewController.swift
//  lucky
//  用户设置
//  Created by Farmer Zhu on 2020/8/21.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckySettingViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate{
    //头
    private var staticHeaderView: LuckyNavigationView!
    //设置列表
    private var staticSettingView: UIView!
    //登出按钮
    private var staticLogoutButton: UIButton!
    
    //头像选择器
    private lazy var pickViewController: UIImagePickerController = {
        let pickViewController = UIImagePickerController()
        pickViewController.delegate = self
        pickViewController.allowsEditing = true
        return pickViewController
    }()
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建设置列表
        staticSettingView = createSettingView()
        self.view.addSubview(staticSettingView)
        //创建登出按钮
        staticLogoutButton = createLogoutButton()
        self.view.addSubview(staticLogoutButton)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if(globalUserData != nil){
            //有用户 刷新数据
            getUserData()
        }else{
            //无用户 异常返回
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Settings", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建设置列表
    func createSettingView() -> UIView {
        let settingView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 0))
        settingView.backgroundColor = UIColor.white
        
        //头像
        let photoView = LuckyUserSettingCellView(frame: CGRect(x: 0, y: 0, width: settingView.frame.width, height: 46 * screenScale))
        photoView.tag =  LuckyTagManager.settingTags.photoView
        photoView.titleLabel.text = NSLocalizedString("Profile Photo", comment: "")
        photoView.button.addTarget(self, action: #selector(toChangePhoto), for: UIControl.Event.touchUpInside)
        photoView.imageView.contentMode = UIView.ContentMode.scaleAspectFill
        settingView.addSubview(photoView)
        
        //昵称
        let nameView = LuckyUserSettingCellView(frame: CGRect(x: photoView.frame.origin.x, y: photoView.frame.origin.y + photoView.frame.height, width: photoView.frame.width, height: photoView.frame.height))
        nameView.tag = LuckyTagManager.settingTags.nameView
        nameView.titleLabel.text = NSLocalizedString("Name", comment: "")
        nameView.button.addTarget(self, action: #selector(toChangeName), for: UIControl.Event.touchUpInside)
        settingView.addSubview(nameView)
        
        //手机号
        let mobileView = LuckyUserSettingCellView(frame: CGRect(x: photoView.frame.origin.x, y: nameView.frame.origin.y + nameView.frame.height, width: photoView.frame.width, height: photoView.frame.height))
        mobileView.tag = LuckyTagManager.settingTags.mobileView
        mobileView.titleLabel.text = NSLocalizedString("Phone No.", comment: "")
        mobileView.button.addTarget(self, action: #selector(toChangeMobile), for: UIControl.Event.touchUpInside)
        settingView.addSubview(mobileView)
        
        //密码
        let passwordView = LuckyUserSettingCellView(frame: CGRect(x: photoView.frame.origin.x, y: mobileView.frame.origin.y + mobileView.frame.height, width: photoView.frame.width, height: photoView.frame.height))
        passwordView.tag = LuckyTagManager.settingTags.passwordView
        passwordView.titleLabel.text = NSLocalizedString("Password", comment: "")
        passwordView.button.addTarget(self, action: #selector(toChangePassword), for: UIControl.Event.touchUpInside)
        settingView.addSubview(passwordView)
        
        //内容高度
        settingView.frame.size = CGSize(width: screenWidth, height: passwordView.frame.origin.y + passwordView.frame.height)
        return settingView
    }
    
    //创建登出按钮
    func createLogoutButton() -> UIButton {
        let logoutButton = UIButton(frame: CGRect(x: 10 * screenScale, y: staticSettingView.frame.origin.y + staticSettingView.frame.height + 40 * screenScale, width: screenWidth - 20 * screenScale, height: 48 * screenScale))
        logoutButton.layer.masksToBounds = true
        logoutButton.layer.cornerRadius = 6 * screenScale
        logoutButton.setTitle(NSLocalizedString("Log Out", comment: ""), for: UIControl.State.normal)
        logoutButton.backgroundColor = UIColor.mainYellow()
        logoutButton.setTitleColor(UIColor.fontBlack() , for: UIControl.State.normal)
        logoutButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        logoutButton.addTarget(self, action: #selector(logout), for: UIControl.Event.touchUpInside)
        return logoutButton
    }
    
    //获取用户信息
    func getUserData(){
        self.updateUserData()
        LuckyUserDataManager.getUserData(success: { (frontUser) in
            globalUserData = frontUser
            self.updateUserData()
        }, error: { (reason) in
            self.navigationController?.popViewController(animated: true)
        }) { (reason) in
        }
    }
    
    //更新用户信息显示
    func updateUserData(){
        let photoView = staticSettingView.viewWithTag(LuckyTagManager.settingTags.photoView) as! LuckyUserSettingCellView
        let nameView = staticSettingView.viewWithTag(LuckyTagManager.settingTags.nameView) as! LuckyUserSettingCellView
        let mobileView = staticSettingView.viewWithTag(LuckyTagManager.settingTags.mobileView) as! LuckyUserSettingCellView
        let passwordView = staticSettingView.viewWithTag(LuckyTagManager.settingTags.passwordView) as! LuckyUserSettingCellView
        
        //头像
        if(globalUserData!.image != ""){
            //有头像 显示
            photoView.imageView.sd_setImage(with: URL(string: globalUserData!.imageURL), placeholderImage: nil, options: SDWebImageOptions.retryFailed)
        }else{
            //无头像 默认头像
            photoView.imageView.image = UIImage(named: "image_user_icon_default")
        }
        
        //昵称
        nameView.textLabel.text = globalUserData!.nickname
        
        //手机号
        if(globalUserData!.mobile != ""){
            //有手机号 截取区号 分开显示
            var areaCodeStr = globalUserData!.areaCode
            let mobileStr = globalUserData!.mobile[areaCodeStr.count ..< globalUserData!.mobile.count]
            if(areaCodeStr.count > 0){
                areaCodeStr = "+\(areaCodeStr) "
            }
            mobileView.textLabel.text = "\(areaCodeStr)\(LuckyUtils.getHideMobile(mobile: mobileStr))"
        }else{
            //无手机号
            mobileView.textLabel.text = NSLocalizedString("binding", comment: "")
        }
        
        //密码
        if(globalUserData!.mobile != "" && globalUserData!.password != ""){
            //有密码
            passwordView.textLabel.text = NSLocalizedString("modify", comment: "")
        }else{
            //无密码
            passwordView.textLabel.text = NSLocalizedString("set", comment: "")
        }
        
    }
    
    //换头像
    @objc func toChangePhoto(){
        //图片选择表单
        let photoActionSheet: UIAlertController = UIAlertController(title: nil, message: nil, preferredStyle: UIAlertController.Style.actionSheet)
        //照相机
        photoActionSheet.addAction(UIAlertAction(title:"Camera", style: UIAlertAction.Style.default, handler: { (UIAlertAction) in
            self.openCamera()
        }))
        //相册
        photoActionSheet.addAction(UIAlertAction(title:"Photo", style: UIAlertAction.Style.default, handler: { (UIAlertAction) in
            self.openPhotoLibrary()
        }))
        //取消
        photoActionSheet.addAction(UIAlertAction(title:"Cancel", style: UIAlertAction.Style.cancel, handler:nil))
        self.present(photoActionSheet, animated: true, completion: nil)
    }
    
    //打开相机
    func openCamera(){
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            //有权限 打开
            pickViewController.sourceType = .camera
            self.navigationController?.present(pickViewController, animated: true, completion: nil)
        } else {
            //无权限
            LuckyAlertView(title: NSLocalizedString("This camera is invalid", comment: "")).showByTime(time: 2)
        }
    }
    
    //打开相册
    func openPhotoLibrary(){
        pickViewController.sourceType = .photoLibrary
        pickViewController.allowsEditing = true
        self.navigationController?.present(pickViewController, animated: true, completion: nil)
    }
    
    //图像上传
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let typeStr = info[UIImagePickerController.InfoKey.mediaType] as? String {
            if typeStr == "public.image" {
                //是图像
                if let image = info[UIImagePickerController.InfoKey.editedImage] as? UIImage {
                    //上传图像
                    let loadingView = LuckyHttpManager.showLoading(viewController: self)
                    LuckyUploadDataManager.uploadImageFile("front/resource/add", params: NSDictionary(), image: image, success: { (data) in
                        //成功 获取资源id
                        //更改头像
                        LuckyHttpManager.postWithToken("front/user/editImage", params: ["image": String.valueOf(any: data["uuid"])], success: { (adata) in
                            //成功 更新头像显示
                            let photoView = self.staticSettingView.viewWithTag(LuckyTagManager.settingTags.photoView) as! LuckyUserSettingCellView
                            photoView.imageView.image = image
                            LuckyHttpManager.hideLoading(loadingView: loadingView)
                        }) { (reason) in
                            LuckyAlertView(title: NSLocalizedString("Get photo failure", comment: "")).showByTime(time: 2)
                            LuckyHttpManager.hideLoading(loadingView: loadingView)
                        }
                    }) { (reason) in
                        LuckyAlertView(title: NSLocalizedString("Get photo failure", comment: "")).showByTime(time: 2)
                        LuckyHttpManager.hideLoading(loadingView: loadingView)
                    }
                }
            }
        } else {
            //未取到文件
            LuckyAlertView(title: NSLocalizedString("Get photo failure", comment: "")).showByTime(time: 2)
        }
        picker.dismiss(animated: true, completion: nil)
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去改昵称页
    @objc func toChangeName(){
        let nameViewController = LuckyNameViewController()
        self.navigationController?.pushViewController(nameViewController, animated: true)
    }
    
    //去修改手机
    @objc func toChangeMobile(){
        if(globalUserData!.mobile == ""){
            //没有绑定手机 去绑定页
            let bindingViewController = LuckyBindingViewController()
            bindingViewController.type = "mobile"
            self.navigationController?.pushViewController(bindingViewController, animated: true)
        }else{
            //已绑定 去修改页
            let mobileViewController = LuckyMobileViewController()
            self.navigationController?.pushViewController(mobileViewController, animated: true)
        }
    }
    
    //去修改密码
    @objc func toChangePassword(){
        if(globalUserData!.mobile == ""){
            //未绑定手机 去绑定手机页
            let bindingViewController = LuckyBindingViewController()
            bindingViewController.type = "password"
            self.navigationController?.pushViewController(bindingViewController, animated: true)
        }else{
            //已绑定 去修改密码页
            let passwordViewController = LuckyPasswordViewController()
            self.navigationController?.pushViewController(passwordViewController, animated: true)
        }
    }
    
    //登出
    @objc func logout(){
        //清除我的页数据显示
        let accountViewController = self.navigationController!.viewControllers[0] as? LuckyAccountViewController
        accountViewController?.clearUserData()
        
        //清本地存储
        globalUserData = nil
        globalUserAccount = nil
        LuckyLocalDataManager.writeLocationData(data: nil)
        //返回主页
        self.navigationController?.tabBarController?.selectedIndex = 0
        self.navigationController?.popToRootViewController(animated: true)
    }
}
