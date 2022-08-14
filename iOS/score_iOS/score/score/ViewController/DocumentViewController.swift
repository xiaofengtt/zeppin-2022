//
//  DocumentViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/7/2.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation
import WebKit

class DocumentViewController: UIViewController{
    
    var headView: NavigationBackground!
    var webView: WKWebView!
    
    var urlString: String = ""
    var pageTitle: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        headView.logoImageView.isHidden = true
        headView.titleLabel.text = pageTitle
        self.view.addSubview(headView)
        
        webView = WKWebView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height) - bottomSafeHeight))
        let url = Bundle.main.url(forResource: urlString, withExtension: "html")
        webView.load(URLRequest(url: url!))
        self.view.addSubview(webView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
}
