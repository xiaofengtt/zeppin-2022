//
//  LuckyGradingManager.swift
//  lucky
//  评价管理
//  Created by Farmer Zhu on 2021/1/7.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyGradingManager{
    
    //展示评价框
    static func showGrading(){
        if let rootViewController = UIApplication.shared.keyWindow?.rootViewController{
            let gradingView = GradingView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: screenHeight)))
            rootViewController.view.addSubview(gradingView)
        }
    }
    
    //评价框
    class GradingView: UIView {
        override init(frame: CGRect) {
            super.init(frame: frame)
            self.layer.zPosition = 1
            self.backgroundColor = UIColor.black.withAlphaComponent(0.5)
            self.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(hide)))
            
            //主框体
            let mainView = UIView(frame: CGRect(x: 44 * screenScale, y: 0, width: frame.width - 88 * screenScale, height: 0))
            mainView.backgroundColor = UIColor.white
            mainView.layer.cornerRadius = 4 * screenScale
            mainView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: nil))
            
            //顶部图
            let topImageView = UIImageView(frame: CGRect(x: -28 * screenScale , y: -70 * screenScale, width: mainView.frame.width + 56 * screenScale, height: 210 * screenScale))
            topImageView.image = UIImage(named: "image_grading_head")
            mainView.addSubview(topImageView)
            
            //提示
            let label = UILabel(frame: CGRect(x: 16 * screenScale, y: topImageView.frame.origin.y + topImageView.frame.height + 24 * screenScale, width: mainView.frame.width - 32 * screenScale, height: 0))
            label.numberOfLines = 0
            let style = NSMutableParagraphStyle()
            style.minimumLineHeight = 16 * screenScale
            style.maximumLineHeight = 16 * screenScale
            label.attributedText = NSAttributedString(string: NSLocalizedString("grading_label", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : style])
            label.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
            label.textColor = UIColor.fontDarkGray()
            label.sizeToFit()
            mainView.addSubview(label)
            
            //评价按钮
            let claimButton = UIButton(frame: CGRect(x: label.frame.origin.x, y: label.frame.origin.y + label.frame.height + 24 * screenScale, width: mainView.frame.width - label.frame.origin.x * 2, height: 44 * screenScale))
            claimButton.layer.cornerRadius = 6 * screenScale
            claimButton.backgroundColor = UIColor.mainYellow()
            claimButton.setTitle(NSLocalizedString("Claim Your Prize", comment: ""), for: UIControl.State.normal)
            claimButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            claimButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
            claimButton.addTarget(self, action: #selector(toWinning), for: UIControl.Event.touchUpInside)
            mainView.addSubview(claimButton)
            
            //关闭按钮
            let laterButton = UIButton(frame: CGRect(origin: CGPoint(x: claimButton.frame.origin.x, y: claimButton.frame.origin.y + claimButton.frame.height + 12 * screenScale), size: claimButton.frame.size))
            laterButton.layer.cornerRadius = claimButton.layer.cornerRadius
            laterButton.layer.borderWidth = 1
            laterButton.layer.borderColor = UIColor.mainYellow().cgColor
            laterButton.backgroundColor = UIColor.white
            laterButton.setTitle(NSLocalizedString("Later", comment: ""), for: UIControl.State.normal)
            laterButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
            laterButton.titleLabel?.font = claimButton.titleLabel?.font
            laterButton.addTarget(self, action: #selector(hide), for: UIControl.Event.touchUpInside)
            mainView.addSubview(laterButton)
            
            mainView.frame.size = CGSize(width: mainView.frame.width, height: laterButton.frame.origin.y + laterButton.frame.height + 16 * screenScale)
            mainView.center = CGPoint(x: screenWidth/2, y: screenHeight/2)
            self.addSubview(mainView)
        }
        
        required init(coder aDecoder: NSCoder) {
            super.init(coder: aDecoder)!
        }
        
        //关闭提示框
        @objc func hide(){
            self.removeFromSuperview()
        }
        
        //去订单页并弹出评价
        @objc func toWinning(){
            if(globalUserData != nil){
                //用户存在
                if let tabbarController = UIApplication.shared.keyWindow?.rootViewController as? LuckyBaseTabBarController{
                    if let navigationController = tabbarController.selectedViewController as? UINavigationController{
                        if let vc = navigationController.visibleViewController as? LuckyOrderViewController{
                            vc.selectedType = "winning"
                            //当前在订单页 选择获奖type
                            vc.selectWinning()
                            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                                vc.showGrading()
                            }
                        }else{
                            //不在订单页 跳转到订单页
                            let vc = LuckyOrderViewController()
                            vc.selectedType = "winning"
                            vc.flagGrading = true
                            vc.hidesBottomBarWhenPushed = true
                            navigationController.pushViewController(vc, animated: true)
                        }
                    }
                }
            }
            hide()
        }
    }
}
