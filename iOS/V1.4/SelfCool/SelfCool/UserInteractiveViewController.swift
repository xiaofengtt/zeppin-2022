//
//  UserInteractiveViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserInteractiveViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("UserInteractive")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserInteractive")
    }
    
    @IBAction func backButton(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") as! MainPageViewController
        self.presentViewController(vc, animated: true) { () -> Void in
            UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
        }
    }
    
    @IBAction func close(segue: UIStoryboardSegue){}
}