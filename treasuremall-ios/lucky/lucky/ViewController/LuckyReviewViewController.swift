//
//  LuckyReviewViewController.swift
//  lucky
//  晒单
//  Created by Farmer Zhu on 2020/9/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyReviewViewController: UIViewController, UITextViewDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //商品显示
    private var staticGoodsView: UIView!
    //评论
    private var staticReviewView: UIView!
    //底
    private var staticBottomView: UIView!
//    private var staticSuccessView: UIView!
    //晒单图区域
    private var imagesView: UIView!
    //新增晒单图按钮
    private var imageAddButton: UIButton!
    
    //晒单图列表
    private var imageList: [UIImage] = []
    
    //商品信息
    var uuid: String = ""
    var goodsUuid: String = ""
    var goodsName: String = ""
    var goodsImageUrl: String = ""
    
    //图片选择器
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
        //创建商品显示
        staticGoodsView = createGoodsView()
        self.view.addSubview(staticGoodsView)
        //创建评论区
        staticReviewView = createReviewView()
        self.view.addSubview(staticReviewView)
        //创建底
        staticBottomView = createBottomView()
        self.view.addSubview(staticBottomView)
//        staticSuccessView = createSuccessView()
//        self.view.addSubview(staticSuccessView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Review", comment: "")
        return headView
    }
    
    //创建商品显示
    func createGoodsView() -> UIView{
        let goodsView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 92 * screenScale))
        goodsView.backgroundColor = UIColor.white
        
        //商品图
        let imageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 16 * screenScale, width: goodsView.frame.height - 32 * screenScale, height: goodsView.frame.height - 32 * screenScale))
        imageView.sd_setImage(with: URL(string: goodsImageUrl), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        goodsView.addSubview(imageView)
        
        //商品名
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 8 * screenScale, y: 0, width: goodsView.frame.width - 10 * screenScale - (imageView.frame.origin.x + imageView.frame.width + 8 * screenScale), height: 0))
        nameLabel.numberOfLines = 2
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 4 * screenScale
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        nameLabel.attributedText = NSAttributedString(string: goodsName, attributes: [NSAttributedString.Key.paragraphStyle : style])
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        nameLabel.sizeToFit()
        nameLabel.frame = CGRect(x: nameLabel.frame.origin.x, y: imageView.frame.origin.y + (imageView.frame.height - nameLabel.frame.height)/2, width: nameLabel.frame.width, height: nameLabel.frame.height)
        goodsView.addSubview(nameLabel)
        
        //去订单详情按钮
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: goodsView.frame.size))
        button.addTarget(self, action: #selector(toDetail), for: UIControl.Event.touchUpInside)
        goodsView.addSubview(button)
        
        return goodsView
    }
    
    //创建评论区
    func createReviewView() -> UIView{
        let reviewView = UIView(frame: CGRect(x: 0, y: staticGoodsView.frame.origin.y + staticGoodsView.frame.height + 10 * screenScale, width: screenWidth, height: 0))
        reviewView.backgroundColor = UIColor.white
        
        //评论输入框
        let textView = UITextView(frame: CGRect(x: 10 * screenScale, y: 14 * screenScale, width: reviewView.frame.width - 20 * screenScale, height: 150 * screenScale))
        textView.delegate = self
        textView.tag = LuckyTagManager.reviewTags.textView
        textView.tintColor = UIColor.mainYellow()
        textView.keyboardType = UIKeyboardType.default
        textView.textColor = UIColor.fontBlack()
        textView.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        reviewView.addSubview(textView)
        
        //评论占位
        let placeholderView = UIView(frame: CGRect(origin: CGPoint.zero, size: textView.frame.size))
        placeholderView.tag = LuckyTagManager.reviewTags.placeholderView
        placeholderView.isUserInteractionEnabled = false
        let placeholderImageView = UIImageView(frame: CGRect(x: 2 * screenScale, y: 5 * screenScale, width: 17 * screenScale, height: 18 * screenScale))
        placeholderImageView.image = UIImage(named: "image_review_edit")
        placeholderView.addSubview(placeholderImageView)
        let placeholderLabel = UILabel(frame: CGRect(x: placeholderImageView.frame.origin.x + placeholderImageView.frame.width + 10 * screenScale, y: 4 * screenScale, width: placeholderView.frame.width - (placeholderImageView.frame.origin.x + placeholderImageView.frame.width + 10 * screenScale), height: 20 * screenScale))
        placeholderLabel.text = NSLocalizedString("Say something...", comment: "")
        placeholderLabel.textColor = UIColor.placeholderGray()
        placeholderLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        placeholderView.addSubview(placeholderLabel)
        textView.addSubview(placeholderView)
        
        //晒单图宽度 每行3个
        let imageWidth: CGFloat = (screenWidth - 52 * screenScale)/3
        //晒单图去区
        imagesView = UIView(frame: CGRect(x: 0, y: textView.frame.origin.y + textView.frame.height + 10 * screenScale, width: screenWidth, height: imageWidth + 28 * screenScale))
        //新增晒单图按钮
        imageAddButton = UIButton(frame: CGRect(x: 10 * screenScale, y: 14 * screenScale, width: imageWidth, height: imageWidth))
        imageAddButton.backgroundColor = UIColor.white
        imageAddButton.layer.cornerRadius = 4 * screenScale
        imageAddButton.layer.shadowColor = UIColor.black.withAlphaComponent(0.06).cgColor
        imageAddButton.layer.shadowOffset = CGSize.zero
        imageAddButton.layer.shadowOpacity = 1
        imageAddButton.layer.shadowRadius = 12
        //按钮底图
        let cameraImageView = UIImageView(frame: CGRect(x: (imageAddButton.frame.width - 26 * screenScale)/2, y: (imageAddButton.frame.height - 54 * screenScale)/2, width: 26 * screenScale, height: 24 * screenScale))
        cameraImageView.image = UIImage(named: "image_review_camera")
        imageAddButton.addSubview(cameraImageView)
        //按钮文字
        let imageAddLabel = UILabel(frame: CGRect(x: 0, y: cameraImageView.frame.origin.y + cameraImageView.frame.height + 10 * screenScale, width: imageAddButton.frame.width, height: 20 * screenScale))
        imageAddLabel.text = NSLocalizedString("Add photos", comment: "")
        imageAddLabel.textColor = UIColor.fontGray()
        imageAddLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        imageAddLabel.textAlignment = NSTextAlignment.center
        imageAddButton.addSubview(imageAddLabel)
        imageAddButton.addTarget(self, action: #selector(toAddPhoto), for: UIControl.Event.touchUpInside)
        imagesView.addSubview(imageAddButton)
        
        reviewView.addSubview(imagesView)
        reviewView.frame.size = CGSize(width: reviewView.frame.width, height: imagesView.frame.origin.y + imagesView.frame.height)
        return reviewView
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - (bottomSafeHeight + 50 * screenScale), width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        bottomView.backgroundColor = UIColor.white
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        //提交按钮
        let button = UIButton(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: bottomView.frame.width - 20 * screenScale, height: 40 * screenScale))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle(NSLocalizedString("Submit", comment: "") , for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(toSubmit), for: UIControl.Event.touchUpInside)
        bottomView.addSubview(button)
        
        return bottomView
    }
    
//    func createSuccessView() -> UIView{
//        let successView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
//        successView.backgroundColor = UIColor.white
//
//        let imageView = UIImageView(frame: CGRect(x: successView.frame.width/3, y: 20 * screenScale, width: successView.frame.width/3, height: successView.frame.width/3))
//        imageView.image = UIImage(named: "image_review_success")
//        successView.addSubview(imageView)
//
//        let label = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 20 * screenScale, width: successView.frame.width, height: 20 * screenScale))
//        label.text = NSLocalizedString("Review successfully!", comment: "")
//        label.textColor = UIColor.fontBlack()
//        label.font = UIFont.mainFont(size: UIFont.fontSizeBiggest() * screenScale)
//        label.textAlignment = NSTextAlignment.center
//        successView.addSubview(label)
//
//        let homeButton = UIButton(frame: CGRect(x: successView.frame.width/2 - 100 * screenScale, y: label.frame.origin.y + label.frame.height + 68 * screenScale, width: 90 * screenScale, height: 30 * screenScale))
//        homeButton.layer.cornerRadius = homeButton.frame.height/2
//        homeButton.layer.borderWidth = 1
//        homeButton.layer.borderColor = UIColor.mainYellow().cgColor
//        homeButton.setTitle(NSLocalizedString("Home", comment: ""), for: UIControl.State.normal)
//        homeButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
//        homeButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
//        homeButton.addTarget(self, action: #selector(toHome), for: UIControl.Event.touchUpInside)
//        successView.addSubview(homeButton)
//
//        let reviewButton = UIButton(frame: CGRect(x: successView.frame.width/2 + 10 * screenScale, y: homeButton.frame.origin.y, width: homeButton.frame.width, height: homeButton.frame.height))
//        reviewButton.layer.cornerRadius = homeButton.frame.height/2
//        reviewButton.layer.borderWidth = 1
//        reviewButton.layer.borderColor = UIColor.mainYellow().cgColor
//        reviewButton.setTitle(NSLocalizedString("My Reviews", comment: ""), for: UIControl.State.normal)
//        reviewButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
//        reviewButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
//        reviewButton.addTarget(self, action: #selector(toMyReview), for: UIControl.Event.touchUpInside)
//        successView.addSubview(reviewButton)
//        return successView
//    }
    
    func textViewShouldBeginEditing(_ textView: UITextView) -> Bool {
        //开始输入时 占位文字隐藏
        if let placeholderView = textView.viewWithTag(LuckyTagManager.reviewTags.placeholderView){
            placeholderView.isHidden = true
        }
        return true
    }
    
    func textViewShouldEndEditing(_ textView: UITextView) -> Bool {
        //结束输入时 若内容为空 显示占位文字
        if let placeholderView = textView.viewWithTag(LuckyTagManager.reviewTags.placeholderView){
            if(textView.text == ""){
                placeholderView.isHidden = false
            }
        }
        return true
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        var str = textView.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: text)
        
        if(str.count > 500){
            //限长 500
            return false
        }
        
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //显示增加晒单图选择框
    @objc func toAddPhoto(){
        let photoActionSheet: UIAlertController = UIAlertController(title: nil, message: nil, preferredStyle: UIAlertController.Style.actionSheet)
        photoActionSheet.addAction(UIAlertAction(title:"Camera", style: UIAlertAction.Style.default, handler: { (UIAlertAction) in
            //打开相机
            self.openCamera()
            
        }))
        photoActionSheet.addAction(UIAlertAction(title:"Photo", style: UIAlertAction.Style.default, handler: { (UIAlertAction) in
            //打开相册
            self.openPhotoLibrary()
            
        }))
        //取消按钮
        photoActionSheet.addAction(UIAlertAction(title:"Cancel", style: UIAlertAction.Style.cancel, handler:nil))
        self.present(photoActionSheet, animated: true, completion: nil)
    }
    
    //打开相机
    func openCamera(){
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            pickViewController.sourceType = .camera
            self.navigationController?.present(pickViewController, animated: true, completion: nil)
        } else {
            LuckyAlertView(title: NSLocalizedString("This camera is invalid", comment: "")).showByTime(time: 2)
        }
    }
    
    //打开相册
    func openPhotoLibrary(){
        pickViewController.sourceType = .photoLibrary
        pickViewController.allowsEditing = true
        self.navigationController?.present(pickViewController, animated: true, completion: nil)
    }
    
    //图像选择控制器
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let typeStr = info[UIImagePickerController.InfoKey.mediaType] as? String {
            //取到了资源
            if typeStr == "public.image" {
                //图片格式
                if let image = info[UIImagePickerController.InfoKey.editedImage] as? UIImage {
                    //图片可用 将图片加入评论区
                    self.imageList.append(image)
                    self.createImageView()
                }
            }
        } else {
            //未取到资源
            LuckyAlertView(title: NSLocalizedString("Get photo failure", comment: "")).showByTime(time: 2)
        }
        picker.dismiss(animated: true, completion: nil)
    }
    
    //将图片加入评论区
    func createImageView(){
        //图序号
        let index = imageList.count - 1
        
        //图片框
        let photoView = UIView(frame: CGRect(x: 10 * screenScale + (imageAddButton.frame.width + 16 * screenScale) * CGFloat(index), y: 0, width: imageAddButton.frame.width + 16 * screenScale, height: imageAddButton.frame.height + 28 * screenScale))
        photoView.tag = LuckyTagManager.reviewTags.phototViewBase + index
        //图片
        let imageView = UIImageView(frame: CGRect(x: 0, y: 14 * screenScale, width: imageAddButton.frame.width, height: imageAddButton.frame.height))
        imageView.layer.cornerRadius = 4 * screenScale
        imageView.image = imageList[index]
        photoView.addSubview(imageView)
        //删除按钮
        let deleteButton = UIButton(frame: CGRect(x: 0, y: 0, width: 18 * screenScale, height: 18 * screenScale))
        deleteButton.center = CGPoint(x: imageView.frame.width, y: 14 * screenScale)
        deleteButton.tag = LuckyTagManager.reviewTags.phototDeleteButtonBase + index
        deleteButton.setImage(UIImage(named: "image_close_white"), for: UIControl.State.normal)
        deleteButton.addTarget(self, action: #selector(deleteImage(_:)), for: UIControl.Event.touchUpInside)
        photoView.addSubview(deleteButton)
        imagesView.addSubview(photoView)
        
        if(imageList.count >= 3){
            //超过三张 隐藏继续添加按钮
            imageAddButton.isHidden = true
        }else{
            //否则 继续添加按钮后移一格
            imageAddButton.isHidden = false
            imageAddButton.frame.origin = CGPoint(x: 10 * screenScale + (imageAddButton.frame.width + 16 * screenScale) * CGFloat(index + 1), y: imageAddButton.frame.origin.y)
        }
    }
    
    //删除图片
    @objc func deleteImage(_ sender: UIButton){
        let index = sender.tag - LuckyTagManager.reviewTags.phototDeleteButtonBase
        //循环图片列表中 删除图片和其后面的图片
        for i in index ..< imageList.count{
            if let photoView = imagesView.viewWithTag(LuckyTagManager.reviewTags.phototViewBase + i){
                //取图片框
                if(i == index){
                    //是要删除的图 删除
                    photoView.removeFromSuperview()
                }else{
                    //不是要删除的图 序号-1 位置向前挪一格
                    photoView.frame.origin = CGPoint(x: photoView.frame.origin.x - (imageAddButton.frame.width + 16 * screenScale), y: photoView.frame.origin.y)
                    photoView.tag = photoView.tag - 1
                    if let deleteButton = photoView.viewWithTag(LuckyTagManager.reviewTags.phototDeleteButtonBase + i){
                        deleteButton.tag = deleteButton.tag - 1
                    }
                }
            }
        }
        //删除该数据
        imageList.remove(at: index)
        
        //调整添加图片按钮位置
        imageAddButton.isHidden = false
        imageAddButton.frame.origin = CGPoint(x: 10 * screenScale + (imageAddButton.frame.width + 16 * screenScale) * CGFloat(imageList.count), y: imageAddButton.frame.origin.y)
    }
    
    //提交晒单
    @objc func toSubmit(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        
        let textView = staticReviewView.viewWithTag(LuckyTagManager.reviewTags.textView) as! UITextView
        
        if(imageList.count > 0){
            //有晒单图
            //循环上传晒单图
            uploadFiles(index: 0, imageListString: "", success: { (data) in
                //上传成功后 晒单接口
                LuckyHttpManager.postWithToken("front/userAccount/commentAdd", params: ["winningInfo": self.uuid, "imageList": data, "detail": textView.text!], success: { (data) in
                    //成功 返回上一页
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                    LuckyAlertView(title: NSLocalizedString("Review successfully!", comment: "")).showByTime(time: 2)
                    self.navigationController?.popToRootViewController(animated: true)
                }) { (reason) in
                    LuckyAlertView(title: reason).showByTime(time: 2)
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                }
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }else{
            //无晒单图 直接晒单接口
            LuckyHttpManager.postWithToken("front/userAccount/commentAdd", params: ["winningInfo": uuid, "detail": textView.text!], success: { (data) in
                //成功 返回上一页
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: NSLocalizedString("Review successfully!", comment: "")).showByTime(time: 2)
                self.navigationController?.popToRootViewController(animated: true)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
    
    //上传图片 自循环
    func uploadFiles(index: Int, imageListString: String, success: @escaping (_ data: String) -> (), fail: @escaping (_ reason: String) -> ()){
        //上传资源
        LuckyUploadDataManager.uploadImageFile("front/resource/add", params: NSDictionary(), image: imageList[index], success: { (data) in
            //拼接图片uuid字符串 uuid,uuid,uuid
            var uuid = String.valueOf(any: data["uuid"])
            if(index != 0){
                uuid = "," + uuid
            }
            
            if(self.imageList.count > index + 1){
                //不是最后一张图 继续上传下一张
                self.uploadFiles(index: index + 1, imageListString: imageListString + uuid, success: { (adata) in
                    success(adata)
                }, fail: { (reason) in
                    fail(reason)
                })
            }else{
                //最后一张上传完成 返回uuid字符串
                success(imageListString + uuid)
            }
        }) { (reason) in
            fail(reason)
        }
    }
    
    //去商品详情页
    @objc func toDetail(){
        let vc = LuckyDetailViewController()
        vc.uuid = goodsUuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
//
//    @objc func toHome(){
//        self.navigationController?.tabBarController?.selectedIndex = 0
//        self.navigationController?.popToRootViewController(animated: false)
//    }
//
//    @objc func toMyReview(){
//        let vc = LuckyShareViewController()
//        let rootVc = self.navigationController?.viewControllers[0]
//
//        self.navigationController?.popToRootViewController(animated: false)
//        rootVc?.navigationController?.pushViewController(vc, animated: true)
//    }
}
