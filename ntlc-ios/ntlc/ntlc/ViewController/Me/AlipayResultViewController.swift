//
//  AlipayResultViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/13.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class AlipayResultViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthSuccess),name: NSNotification.Name(rawValue: "alipayAuthSuccess"), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthField),name: NSNotification.Name(rawValue: "alipayAuthField"), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(alipayAuthTimeout),name: NSNotification.Name(rawValue: "alipayAuthTimeout"), object: nil)
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getData()
    }
    
    func createBodyView(){
        bodyView.removeFromSuperview()
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 8 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: screenHeight - 8 * 2 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 330 * screenScale))
        topView.backgroundColor = user!.bindingAliFlag ? UIColor(red: 0, green: 167/255, blue: 1, alpha: 1) : UIColor(red: 248/255, green: 248/255, blue: 248/255, alpha: 1)
        topView.layer.cornerRadius = cornerRadius * screenScale
        
        let topBg = UIImageView(frame: CGRect(x: (screenWidth - 230 * screenScale)/2, y: 40 * screenScale, width: 230 * screenScale, height: 148 * screenScale))
        topBg.image = UIImage(named: "alipay_\(user!.bindingAliFlag ? "bind" : "unbind")_bg")
        topView.addSubview(topBg)
        
        let topHeadBg = UIImageView(frame: CGRect(x: (bodyView.frame.width - 60 * screenScale)/2, y: topView.frame.height - (30 + 37) * screenScale, width: 60 * screenScale, height: 37 * screenScale))
        topHeadBg.image = UIImage(named: "alipay_\(user!.bindingAliFlag ? "bind" : "unbind")_head")
        topView.addSubview(topHeadBg)
        
        if(user!.bindingAliFlag){
            let topHead = UIImageView(frame: CGRect(x: (topHeadBg.frame.width - 28 * screenScale)/2, y: 0, width: 28 * screenScale, height: 28 * screenScale))
            topHead.layer.cornerRadius = topHead.frame.width/2
            topHead.layer.masksToBounds = true
            SDWebImageManager.shared().loadImage(with: URL(string: user!.aliPhoto), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                if result{
                    topHead.image = SDImage
                }
            }
            topHeadBg.addSubview(topHead)
            
            let topNickname = UILabel(frame: CGRect(x: 0, y: topHeadBg.frame.origin.y - (12 + 17) * screenScale, width: topView.frame.width, height: 17 * screenScale))
            topNickname.text = user!.aliNickname
            topNickname.textColor = UIColor.white
            topNickname.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            topNickname.textAlignment = NSTextAlignment.center
            topView.addSubview(topNickname)
            
            let topMessage = UILabel(frame: CGRect(x: 0, y: topNickname.frame.origin.y - (2 + 25) * screenScale, width: topView.frame.width, height: 25 * screenScale))
            topMessage.text = "当前已授权!"
            topMessage.textColor = UIColor.white
            topMessage.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
            topMessage.textAlignment = NSTextAlignment.center
            topView.addSubview(topMessage)
        }else{
            let topMessage = UILabel(frame: CGRect(x: 0, y: topHeadBg.frame.origin.y - (22 + 25) * screenScale, width: topView.frame.width, height: 25 * screenScale))
            topMessage.text = "用户未授权!"
            topMessage.textColor = UIColor.fontBlack()
            topMessage.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
            topMessage.textAlignment = NSTextAlignment.center
            topView.addSubview(topMessage)
        }
        bodyView.addSubview(topView)
        
        let button = UIButton(frame: CGRect(x: (bodyView.frame.width - 300 * screenScale)/2, y: topView.frame.origin.y + topView.frame.height + 68 * screenScale, width: 300 * screenScale, height: 45 * screenScale))
        button.layer.cornerRadius = cornerRadius * screenScale
        button.backgroundColor = UIColor.mainGold()
        button.setTitle(user!.bindingAliFlag ? "重新绑定" : "立即绑定", for: UIControlState.normal)
        button.setTitleColor( UIColor.white, for: UIControlState.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        button.addTarget(self, action: #selector(alipayBind(_:)), for: UIControlEvents.touchUpInside)
        bodyView.addSubview(button)
        
        mainView.addSubview(bodyView)
    }
    
    func getData(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getUser(uuid: user!.uuid, data: { (data) in
            self.createBodyView()
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    @objc func alipayBind(_ sender: UIButton){
        if(user!.realnameAuthFlag){
            AlipayController.auth()
        }else{
            AlertView(title: "请先实名认证").showByTime(time: 2)
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    @objc func alipayAuthSuccess(){
        getData()
    }
    
    @objc func alipayAuthField(){
        AlertView(title: "授权失败").showByTime(time: 2)
    }
    
    @objc func alipayAuthTimeout(){
        HttpController.showTimeout(viewController: self)
    }
}
