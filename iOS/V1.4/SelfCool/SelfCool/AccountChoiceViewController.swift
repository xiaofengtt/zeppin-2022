//
//  AccountChoiceViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/31.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AccountChoiceViewController: UIViewController ,UIScrollViewDelegate{
    
    @IBOutlet weak var registerButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var backgroundScrollView: UIScrollView!
    @IBOutlet weak var backgroundPageControl: UIPageControl!
    
    override func viewDidLoad() {
        super.viewDidLoad() 
        registerButton.setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor().colorWithAlphaComponent(0.4)), forState: UIControlState.Highlighted)
        loginButton.setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor().colorWithAlphaComponent(0.4)), forState: UIControlState.Highlighted)
        
        backgroundScrollView.delegate = self
        AddLoginView(backgroundScrollView, owner: self)
        backgroundPageControl.numberOfPages = Int(backgroundScrollView.contentSize.width / screenWidth)
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("AccountChoice")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("AccountChoice")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((backgroundScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        backgroundPageControl.currentPage = page
    }
    
    @IBAction func registerButton(sender: UIButton) {
        let sb = UIStoryboard(name: "LoginAndRegister", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("registerNavigationBar") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func loginButton(sender: UIButton) {
        let sb = UIStoryboard(name: "LoginAndRegister", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("loginNavigationBar") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
}
