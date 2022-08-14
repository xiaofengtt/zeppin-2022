//
//  BaseTabController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/17.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BaseTabBarController: UITabBarController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.tabBar.isTranslucent = false
        self.tabBar.backgroundImage = UIImage.imageWithColor(UIColor.white)
        
//        self.tabBar.tintColor = UIColor.fontGray()
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
//        for  i in 0 ..< self.tabBar.items!.count {
//            self.tabBar.items![i].setTitleTextAttributes([NSAttributedString.Key.font: UIFont.mainFont(size: UIFont.smallestSize()) as Any], for: UIControl.State.normal)
//            self.tabBar.items![i].image = self.tabBar.items![i].image!.imageWithScale(0.68)
//            self.tabBar.items![i].selectedImage = self.tabBar.items![i].selectedImage!.imageWithScale(0.68)
//            self.tabBar.items![i].imageInsets = UIEdgeInsets(top: 2, left: 0, bottom: -2, right: 0)
//            self.tabBar.items![i].titlePositionAdjustment = UIOffset(horizontal: 0, vertical: -2)
//            self.tabBar.backgroundImage = UIImage()
//            self.tabBar.shadowImage = UIImage.imageWithColor(UIColor.backgroundGray())
//        }
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()

//        var frame = self.tabBar.frame
//        frame.size.height = tabBarHeight
//        frame.origin.y = self.view.frame.size.height - frame.size.height
//        self.tabBar.frame = frame
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
