//
//  BaseTabController.swift
//  ntlc
//
//  Created byfarmer on 2017/11/17.
//  Copyright © 2017年 farmer. All rights reserved.
//

import UIKit

class BaseTabBarController: UITabBarController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.tabBar.isTranslucent = false
        self.tabBar.backgroundImage = UIImage.imageWithColor(UIColor.white)
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
