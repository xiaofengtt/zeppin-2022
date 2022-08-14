//
//  BankcardListViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BankcardListViewController: UIViewController, UIScrollViewDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var scrollView: UIScrollView = UIScrollView()
    
    var bankcardList: [BankcardModel] = []
    
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
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    func createScrollView(){
        scrollView = UIScrollView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: screenHeight - navigationBackground.frame.height))
        scrollView.delegate = self
        if #available(iOS 11.0, *) {
            scrollView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.addRefreshView()
        
        for i in 0 ..< bankcardList.count {
            let bankcardView = createBankcardView(index: i)
            scrollView.addSubview(bankcardView)
        }
        
        let addView = UIButton(frame: CGRect(x: paddingLeft * screenScale, y: CGFloat((95 + 10) * bankcardList.count) * screenScale + 11 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 60 * screenScale))
        addView.layer.cornerRadius = cornerRadius * screenScale
        addView.backgroundColor = UIColor.white
        
        let addImage = UIImageView(frame: CGRect(x: 18 * screenScale, y: (addView.frame.height - 14 * screenScale)/2, width: 14 * screenScale, height: 14 * screenScale))
        addImage.image = UIImage(named: "bankcard_list_add")
        addView.addSubview(addImage)
        
        let label = UILabel(frame: CGRect(x: addImage.frame.origin.x + addImage.frame.width + 6 * screenScale, y: (addView.frame.height - 22 * screenScale)/2, width: addView.frame.width - (addImage.frame.origin.x + addImage.frame.width + 6 * screenScale), height: 22 * screenScale))
        label.text = "添加银行卡"
        label.textColor = UIColor(red: 74/255, green: 144/255, blue: 226/255, alpha: 1)
        label.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        addView.addSubview(label)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: addView.frame.size))
        button.addTarget(self, action: #selector(toBankcardBind(_:)), for: UIControlEvents.touchUpInside)
        addView.addSubview(button)
        scrollView.addSubview(addView)
        
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: addView.frame.origin.y + addView.frame.height + 10 * screenScale)
        if(scrollView.frame.height >= scrollView.contentSize.height){
            scrollView.contentSize = CGSize(width: scrollView.contentSize.width, height: scrollView.frame.height + 1)
        }
        mainView.addSubview(scrollView)
    }
    
    func createBankcardView(index: Int) -> UIView{
        let bankcard = bankcardList[index]
        
        let bankcardView = UIView(frame: CGRect(x: 0, y: 10 + (95 + 10) * CGFloat(index) * screenScale, width: scrollView.frame.width, height: 95 * screenScale))
        bankcardView.layer.cornerRadius = cornerRadius * screenScale
        bankcardView.layer.masksToBounds = true
        bankcardView.backgroundColor = UIColor.getColorByHex(hexString: bankcard.color)
        
        let logo = UIImageView(frame: CGRect(x: 15 * screenScale, y: 15 * screenScale, width: 33 * screenScale, height: 33 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + bankcard.icon), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
            if result{
                logo.image = SDImage
            }
        }
        bankcardView.addSubview(logo)
        
        let name = UILabel(frame: CGRect(x: logo.frame.origin.x + logo.frame.width + 3 * screenScale, y: logo.frame.origin.y, width: bankcardView.frame.width - (logo.frame.origin.x + logo.frame.width + 3 * screenScale), height: logo.frame.height))
        name.text = bankcard.name
        name.textColor = UIColor.white
        name.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        bankcardView.addSubview(name)
        
        let bankType = UILabel(frame: CGRect(x: name.frame.origin.x, y: name.frame.origin.y + name.frame.height, width: name.frame.width, height: 17 * screenScale))
        bankType.text = "储蓄卡"
        bankType.textColor = UIColor.white
        bankType.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        bankcardView.addSubview(bankType)
        
        let bankcardNum = UILabel(frame: CGRect(x: 0, y: bankcardView.frame.height - (13 + 28) * screenScale, width: bankcardView.frame.width - 13 * screenScale, height: 28 * screenScale))
        bankcardNum.text = "**** **** **** \(bankcard.bankcard)"
        bankcardNum.textColor = UIColor.white
        bankcardNum.font = UIFont.mainFont(size: 20 * screenScale)
        bankcardNum.textAlignment = NSTextAlignment.right
        bankcardView.addSubview(bankcardNum)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: bankcardView.frame.size))
        button.tag = TagController.bankcardTags.bankcardListBase + index
        button.addTarget(self, action: #selector(toBankcardDetail(_:)), for: UIControlEvents.touchUpInside)
        bankcardView.addSubview(button)
        return bankcardView
    }
    
    func getList(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("user/getBindingCard", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.bankcardList = []
                    var bankcards: [BankcardModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        bankcards.append(BankcardModel(data: data))
                    }
                    self.bankcardList = bankcards
                    
                    self.scrollView.removeFromSuperview()
                    self.createScrollView()
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    @objc func toBankcardDetail(_ sender: UIButton){
        let index = sender.tag - TagController.bankcardTags.bankcardListBase
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "bankcardDetailViewController") as! BankcardDetailViewController
        vc.bankcard = bankcardList[index]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toBankcardBind(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "bankcardBindViewController") as! BankcardBindViewController
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
            scrollView.refreshToAble()
        }else{
            scrollView.refreshToNormal()
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView.getRefreshView().status == UIScrollRefreshStatus.able){
            getList()
        }
    }
}

