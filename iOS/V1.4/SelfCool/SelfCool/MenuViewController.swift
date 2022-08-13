//
//  MenuViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/24.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class MenuViewController: UIViewController{
    @IBOutlet weak var userIcon: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        userIcon.layer.masksToBounds = true
        userIcon.layer.cornerRadius = screenWidth * 0.1
        userIcon.image = userImage
        userNameLabel.text = user.name.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}