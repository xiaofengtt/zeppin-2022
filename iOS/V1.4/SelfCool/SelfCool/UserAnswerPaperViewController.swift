//
//  UserAnswerPaperViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/25.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserAnswerPaperViewController: UIViewController{
    
    @IBOutlet weak var webView: UIWebView!
    let url = "http://form.mikecrm.com/f.php?t=60ervz"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        (webView.subviews[0] as! UIScrollView).bounces = false
        webView.loadRequest(NSURLRequest(URL: NSURL(string: url.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)!))
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("UserAnswerPaper")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserAnswerPaper")
    }
    
}