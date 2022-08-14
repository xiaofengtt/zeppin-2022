//
//  DocumentViewController.swift
//  ryqiu
//
//  Created by worker on 2019/7/2.
//  Copyright © 2019 worker. All rights reserved.
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
        headView.titleLabel.text = pageTitle
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        
        webView = WKWebView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height) - bottomSafeHeight))
        let url = Bundle.main.url(forResource: urlString, withExtension: "html")
        webView.load(URLRequest(url: url!))
        self.view.addSubview(webView)
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
