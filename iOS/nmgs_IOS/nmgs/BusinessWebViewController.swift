//
//  BusinessWebViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/9.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class BusinessWebViewController: UIViewController{
    
    @IBOutlet weak var baseWebView: UIWebView!
    
    var url: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        baseWebView.scrollView.bounces = false
        baseWebView.scrollView.showsHorizontalScrollIndicator = false
        baseWebView.scrollView.showsVerticalScrollIndicator = false
        let urlString = (url).addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let request = URLRequest(url: URL(string: urlString)!)
        baseWebView.loadRequest(request)
        
    }
    
//    override var preferredStatusBarStyle : UIStatusBarStyle {
//        return UIStatusBarStyle.lightContent
//    }
    
    @IBAction func backToPlayView(_ sender: UIButton) {
        let vcList = self.navigationController!.viewControllers
        _ = self.navigationController?.popToViewController(vcList.first!, animated: true)
    }
}
