//
//  IdcardFailedViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class IdcardFailedViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    
    var message: String = ""
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createBodyView()
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let messageLabel = bodyView.viewWithTag(TagController.idcardAuthTags.faildLabel) as? UILabel
        messageLabel?.text = message
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 300 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 44 * screenScale, width: bodyView.frame.width, height: 22 * screenScale))
        titleLabel.text = "认证未通过！"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        bodyView.addSubview(titleLabel)
        
        let messageLabel = UILabel(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 4 * screenScale, width: bodyView.frame.width, height: 17 * screenScale))
        messageLabel.tag = TagController.idcardAuthTags.faildLabel
        messageLabel.text = message
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        messageLabel.textAlignment = NSTextAlignment.center
        bodyView.addSubview(messageLabel)
        
        let image = UIImageView(frame: CGRect(x: (bodyView.frame.width - 140 * screenScale)/2, y: messageLabel.frame.origin.y + messageLabel.frame.height + 20 * screenScale, width: 140 * screenScale, height: 82 * screenScale))
        image.image = UIImage(named: "auth_failed")
        bodyView.addSubview(image)
        
        let button = UIButton(frame: CGRect(x: (bodyView.frame.width - 100 * screenScale)/2, y: image.frame.origin.y + image.frame.height + 40 * screenScale, width: 100 * screenScale, height: 35 * screenScale))
        button.backgroundColor = UIColor.mainGold()
        button.setTitle("重新认证", for: UIControlState.normal)
        button.setTitleColor(UIColor.white, for: UIControlState.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        button.addTarget(self, action: #selector(back(_:)), for: UIControlEvents.touchUpInside)
        bodyView.addSubview(button)
        
        mainView.addSubview(bodyView)
    }
    
    @objc func back(_ sender: UIButton){
        self.navigationController?.popViewController(animated: true)
    }
}
