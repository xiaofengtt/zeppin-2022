//
//  IdcardSuccessViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class IdcardSuccessViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    var infoView:  UIView = UIView()
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createBodyView()
        createInfoView()
        super.viewDidLoad()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        let vcs = self.navigationController!.viewControllers
        for vc in vcs{
            if(vc.classForCoder == MeViewController.classForCoder()){
                self.navigationController?.popToViewController(vc, animated: true)
                break
            }
        }
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 230 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 44 * screenScale, width: bodyView.frame.width, height: 22 * screenScale))
        titleLabel.text = "你已通过实名认证！"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        bodyView.addSubview(titleLabel)
        
        let messageLabel = UILabel(frame: CGRect(x: 0, y: titleLabel.frame.origin.y + titleLabel.frame.height + 4 * screenScale, width: bodyView.frame.width, height: 17 * screenScale))
        messageLabel.tag = TagController.idcardAuthTags.faildLabel
        messageLabel.text = "恭喜你！"
        messageLabel.textColor = UIColor.fontGray()
        messageLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        messageLabel.textAlignment = NSTextAlignment.center
        bodyView.addSubview(messageLabel)
        
        let image = UIImageView(frame: CGRect(x: (bodyView.frame.width - 140 * screenScale)/2, y: messageLabel.frame.origin.y + messageLabel.frame.height + 20 * screenScale, width: 140 * screenScale, height: 82 * screenScale))
        image.image = UIImage(named: "auth_success")
        bodyView.addSubview(image)
        mainView.addSubview(bodyView)
    }
    
    func createInfoView(){
        infoView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 12 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 82))
        infoView.addBaseShadow()
        infoView.backgroundColor = UIColor.white
        infoView.layer.cornerRadius = cornerRadius * screenScale
        
        let nameView = createRowView(frame: CGRect(x: 0, y: 0, width: infoView.frame.width, height: 41), name: "姓名", value: user!.realname)
        infoView.addSubview(nameView)
        let idcardView = createRowView(frame: CGRect(x: 0, y: nameView.frame.origin.y + nameView.frame.height, width: infoView.frame.width, height: 41), name: "身份证", value: user!.idcard)
        infoView.addSubview(idcardView)
        
        mainView.addSubview(infoView)
    }
    
    func createRowView(frame: CGRect, name: String, value: String) -> UIView {
        let view = UIView(frame: frame)
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(topLine)
        
        let nameLabel = UILabel(frame: CGRect(x: 13 * screenScale, y: 0, width: view.frame.width * 0.4 - 13 * screenScale, height: view.frame.height))
        nameLabel.text = name
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        view.addSubview(nameLabel)
        
        let valueLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: view.frame.width - (nameLabel.frame.origin.x + nameLabel.frame.width) - 7 * screenScale, height: view.frame.height))
        valueLabel.text = value
        valueLabel.textColor = UIColor.black
        valueLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        valueLabel.textAlignment = NSTextAlignment.right
        view.addSubview(valueLabel)
        return view
    }
}
