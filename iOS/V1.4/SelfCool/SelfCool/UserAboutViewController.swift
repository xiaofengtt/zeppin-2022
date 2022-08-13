//
//  UserAboutViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/30.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserAboutViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("UserAbout")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserAbout")
    }
}