//
//  MyViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/24.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MyViewController: UIViewController {
    
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var logoutView: UIView = UIView()
    
    let paddingLeft: CGFloat = 10 * screenScale
    let tableCellHeight: CGFloat = 60 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.fontBlack()], for: UIControl.State.selected)
        
        self.view.backgroundColor = UIColor.backgroundGray()
        
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        self.view.addSubview(logoutView)
        
        createHead()
        createBody()
        createLogout()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        let headIconView = headView.viewWithTag(TagController.MyTags.headIconView) as! UIImageView
        let headTextView = headView.viewWithTag(TagController.MyTags.headTextView) as! UILabel
        
        if(user == nil){
            logoutView.isHidden = true
            headIconView.image = UIImage(named: "my_head_unlogin")
            headTextView.text = "立即登录"
            headTextView.textColor = UIColor.fontDarkGray()
        }else{
            logoutView.isHidden = false
            headIconView.image = UIImage(named: "my_head_login")
            headTextView.text = user!.mobile
            headTextView.textColor = UIColor.fontBlack()
        }
    }
    
    func createHead(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.origin.y + navigationFrame.size.height + 140 * screenScale))
        
        let backgroundView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundView.image = UIImage(named: "my_head_background")
        headView.addSubview(backgroundView)
        
        let iconView = UIImageView(frame: CGRect(origin: CGPoint(x: screenWidth * 0.4, y: navigationFrame.origin.y + navigationFrame.size.height - 15 * screenScale), size: CGSize(width: screenWidth/5, height: screenWidth/5)))
        iconView.tag = TagController.MyTags.headIconView
        iconView.image = UIImage(named: "my_head_unlogin")
        iconView.layer.cornerRadius = iconView.frame.width/2
        iconView.layer.borderWidth = 2 * screenScale
        iconView.layer.borderColor = UIColor.white.cgColor
        headView.addSubview(iconView)
        
        let textView = UILabel(frame: CGRect(x: 0, y: iconView.frame.origin.y + iconView.frame.height + 18 * screenScale, width: screenWidth, height: 20 * screenScale))
        textView.tag = TagController.MyTags.headTextView
        textView.text = "立即登录"
        textView.textColor = UIColor.fontDarkGray()
        textView.textAlignment = NSTextAlignment.center
        textView.font = UIFont.mainFont(size: UIFont.biggestSize() * screenScale)
        headView.addSubview(textView)
        
        let headButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        headButton.addTarget(self, action: #selector(enterHead(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(headButton)
    }
    
    func createBody(){
        bodyView.frame.origin = CGPoint(x: paddingLeft, y: headView.frame.height + 18 * screenScale)
        bodyView.frame.size.width = screenWidth - paddingLeft * 2
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = 10 * screenScale
        bodyView.clipsToBounds = true
        
        let concrenView = UIButton(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: tableCellHeight))
        concrenView.backgroundColor = UIColor.clear
        let concrenEnterImage = UIImageView(frame: CGRect(x: concrenView.frame.width - 10 * screenScale - 25 * screenScale, y: 20 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        concrenEnterImage.image = UIImage(named: "common_enter_arrow")
        concrenView.addSubview(concrenEnterImage)
        let concrenIcon = UIImageView(frame: CGRect(x: 20 * screenScale, y: 15 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        concrenIcon.image = UIImage(named: "my_body_concren")
        concrenView.addSubview(concrenIcon)
        let concrenLabel = UILabel(frame: CGRect(x: concrenIcon.frame.origin.x + concrenIcon.frame.width + 15 * screenScale, y: 0, width: 100 * screenScale, height: concrenView.frame.height))
        concrenLabel.text = "我的关注"
        concrenLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        concrenLabel.textColor = UIColor.fontBlack()
        concrenView.addSubview(concrenLabel)
        let concrenButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: concrenView.frame.size))
        concrenButton.addTarget(self, action: #selector(enterConcren(_:)), for: UIControl.Event.touchUpInside)
        concrenView.addSubview(concrenButton)
        bodyView.addSubview(concrenView)
        
        let splitLine = UIView(frame: CGRect(x: 8 * screenScale, y: concrenView.frame.height, width:  bodyView.frame.width - 8 * screenScale, height: 1 * screenScale))
        splitLine.backgroundColor = UIColor.backgroundGray()
        bodyView.addSubview(splitLine)
        
        let aboutView = UIButton(frame: CGRect(x: concrenView.frame.origin.x, y: splitLine.frame.origin.y + splitLine.frame.height, width: concrenView.frame.width, height: concrenView.frame.height))
        aboutView.backgroundColor = UIColor.clear
        let aboutEnterImage = UIImageView(frame: concrenEnterImage.frame)
        aboutEnterImage.image = concrenEnterImage.image
        aboutView.addSubview(aboutEnterImage)
        let aboutIcon = UIImageView(frame: concrenIcon.frame)
        aboutIcon.image = UIImage(named: "my_body_about")
        aboutView.addSubview(aboutIcon)
        let aboutLabel = UILabel(frame: concrenLabel.frame)
        aboutLabel.text = "关于我们"
        aboutLabel.font = concrenLabel.font
        aboutLabel.textColor = concrenLabel.textColor
        aboutView.addSubview(aboutLabel)
        let aboutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: aboutView.frame.size))
        aboutButton.addTarget(self, action: #selector(enterAbout(_:)), for: UIControl.Event.touchUpInside)
        aboutView.addSubview(aboutButton)
        bodyView.addSubview(aboutView)
        
        bodyView.frame.size.height = aboutView.frame.origin.y + aboutView.frame.height
        bodyView.addBaseShadow()
    }
    
    func createLogout(){
        logoutView.frame = CGRect(x: paddingLeft, y: bodyView.frame.origin.y + bodyView.frame.height + 20 * screenScale, width: screenWidth - paddingLeft * 2, height: 50 * screenScale)
        logoutView.backgroundColor = UIColor.white
        logoutView.layer.cornerRadius = 10 * screenScale
        logoutView.addBaseShadow()
        logoutView.isHidden = true
        
        let logoutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: logoutView.frame.size))
        logoutButton.setTitle("退出登录", for: UIControl.State.normal)
        logoutButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        logoutButton.addTarget(self, action: #selector(logout(_:)), for: UIControl.Event.touchUpInside)
        logoutView.addSubview(logoutButton)
    }
    
    @objc func logout(_ sender: UIButton){
        user = nil
        LocalDataController.writeLocalData("user", data: nil)
        self.viewDidAppear(false)
    }
    
    @objc func enterHead(_ sender: UIButton){
        if(user == nil){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func enterConcren(_ sender: UIButton){
        if(user != nil){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "ConcrenViewController") as! ConcrenViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func enterAbout(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "AboutViewController") as! AboutViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
