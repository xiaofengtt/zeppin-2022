//
//  MainPageViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/24.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class MainPageViewController: UIViewController{
    
    let animationDuration : NSTimeInterval = 0.5
    let mainViewTateCenter = CGPoint(x: screenWidth * 1.2, y: screenHeight / 2)
    let mainViewTranslateScale : CGFloat = 0.8
    let menuViewTranslateCenter = CGPoint(x: screenWidth / 8, y: screenHeight / 2)
    let menuViewTranslateScale : CGFloat = 0.5
    
    var baseNavigationBar : BaseNavigationBar
    var mySubjectsViewController : MySubjectsViewController
    var menuViewController : MenuViewController
    var mainView = UIView()
    var menuView = UIView()
    var menuItem : UIBarButtonItem?
    var tapGestrue :UIGestureRecognizer?
    var panGestrue :UIPanGestureRecognizer?
    var isLogin = false
    var isRegister = false
    
    required init?(coder aDecoder: NSCoder) {
        let mainStoryBoard = UIStoryboard(name: "Main", bundle: nil)
        let menuStoryBoard = UIStoryboard(name: "Menu", bundle: nil)
        self.baseNavigationBar = mainStoryBoard.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! BaseNavigationBar
        self.mySubjectsViewController = self.baseNavigationBar.childViewControllers[0] as! MySubjectsViewController
        self.menuViewController = menuStoryBoard.instantiateViewControllerWithIdentifier("menuViewController") as! MenuViewController
        self.menuItem = mySubjectsViewController.navigationItem.leftBarButtonItem
        super.init(coder: aDecoder)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.addSubview(self.menuView)
        self.view.addSubview(self.mainView)
        self.mainView.frame = self.view.bounds
        self.menuView.frame = self.view.bounds
        self.menuView.backgroundColor = UIColor.clearColor()
        
        self.addChildViewController(self.menuViewController)
        self.menuViewController.view.frame = self.view.bounds
        self.menuView.addSubview(self.menuViewController.view)
        self.menuViewController.didMoveToParentViewController(self)
        self.menuView.transform = CGAffineTransformMakeScale(self.menuViewTranslateScale, self.menuViewTranslateScale)
        self.menuView.center = menuViewTranslateCenter
        self.menuView.alpha = 0
        
        self.addChildViewController(self.baseNavigationBar)
        self.baseNavigationBar.view.frame = self.view.bounds
        self.mainView.addSubview(self.baseNavigationBar.view)
        self.baseNavigationBar.didMoveToParentViewController(self)
        
        tapGestrue = UITapGestureRecognizer(target: self, action: "closeMenu:")
        panGestrue = UIPanGestureRecognizer(target: self, action: "panMenu:")
        
    }
    override func viewDidAppear(animated: Bool) {
        if isLogin{
            isLogin = false
            SelfAlertView(self, alertText: "登录成功", SelfAlertImageStyle: SelfAlertViewImageStyle.smile , SelfAlertLevel: SelfAlertLevel.screen)
        }else if isRegister{
            isRegister = false
            SelfAlertView(self, alertText: "注册成功", SelfAlertImageStyle: SelfAlertViewImageStyle.smile , SelfAlertLevel: SelfAlertLevel.screen)
        }
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func menuButton(sender: UIButton){
        self.mainView.userInteractionEnabled = false
        self.menuView.userInteractionEnabled = false
        UIView.animateWithDuration(animationDuration, animations: { () -> Void in
            self.mainView.transform = CGAffineTransformMakeScale(self.mainViewTranslateScale, self.mainViewTranslateScale)
            self.mainView.center = self.mainViewTateCenter
            self.mySubjectsViewController.menuButton.alpha = 0
            self.menuView.transform = CGAffineTransformMakeScale(1,1)
            self.menuView.center = self.view.center
            self.menuView.alpha = 1
        }) { (finished) -> Void in
            self.mainView.userInteractionEnabled = true
            self.menuView.userInteractionEnabled = true
            self.mySubjectsViewController.navigationItem.leftBarButtonItem = nil
            self.mySubjectsViewController.headScrollView.userInteractionEnabled = false
            self.mainView.addGestureRecognizer(self.tapGestrue!)
            self.mainView.addGestureRecognizer(self.panGestrue!)
        }
    }
    
    func panMenu(sender : UIPanGestureRecognizer){
        let mainLong = mainViewTateCenter.x - self.view.center.x
        let menuLong = self.view.center.x - menuViewTranslateCenter.x
        let translate = sender.translationInView(self.view)
        self.mySubjectsViewController.navigationItem.leftBarButtonItem = self.menuItem  
        if translate.x < 0{
            var rate = -translate.x / mainLong
            if -translate.x > mainLong {
                rate = 1
            }
            
            self.mainView.center = CGPoint(x: mainViewTateCenter.x - mainLong * rate, y: self.mainViewTateCenter.y)
            self.mainView.transform = CGAffineTransformMakeScale(self.mainViewTranslateScale + (1 - self.mainViewTranslateScale) * rate, self.mainViewTranslateScale + (1 - self.mainViewTranslateScale) * rate)
            self.mySubjectsViewController.menuButton.alpha = rate
            self.menuView.center = CGPoint(x: menuViewTranslateCenter.x + menuLong * (1 - rate) , y: self.menuViewTranslateCenter.y)
            self.menuView.transform = CGAffineTransformMakeScale(self.menuViewTranslateScale + (1 - self.menuViewTranslateScale) * (1 - rate), self.menuViewTranslateScale + (1 - self.menuViewTranslateScale) * (1 - rate))
            self.menuView.alpha = 1 - rate
        }
        
        if sender.state == UIGestureRecognizerState.Ended{
            var rate = -translate.x / mainLong
            if -translate.x > mainLong {
                rate = 1
            }
            if rate < 0.5{
                let duration = NSTimeInterval(CGFloat(self.animationDuration) * rate * 2)
                self.mainView.userInteractionEnabled = false
                self.menuView.userInteractionEnabled = false
                UIView.animateWithDuration(duration, animations: { () -> Void in
                    self.mainView.transform = CGAffineTransformMakeScale(self.mainViewTranslateScale, self.mainViewTranslateScale)
                    self.mainView.center = self.mainViewTateCenter
                    self.mySubjectsViewController.menuButton.alpha = 0
                    self.menuView.transform = CGAffineTransformMakeScale(1,1)
                    self.menuView.center = self.view.center
                    self.menuView.alpha = 1
                    }) { (finished) -> Void in
                        self.mySubjectsViewController.navigationItem.leftBarButtonItem = nil
                        self.mainView.userInteractionEnabled = true
                        self.menuView.userInteractionEnabled = true
                }
            }else{
                self.mainView.userInteractionEnabled = false
                self.menuView.userInteractionEnabled = false
                UIView.animateWithDuration(animationDuration, animations: { () -> Void in
                    self.mainView.transform = CGAffineTransformMakeScale(1, 1)
                    self.mainView.center = self.view.center
                    self.mySubjectsViewController.menuButton.alpha = 1
                    self.menuView.transform = CGAffineTransformMakeScale(self.menuViewTranslateScale, self.menuViewTranslateScale)
                    self.menuView.center = self.menuViewTranslateCenter
                    self.menuView.alpha = 0
                    }) { (finished) -> Void in
                        self.mainView.userInteractionEnabled = true
                        self.menuView.userInteractionEnabled = true
                        self.mySubjectsViewController.headScrollView.userInteractionEnabled = true
                        self.mainView.removeGestureRecognizer(self.tapGestrue!)
                        self.mainView.removeGestureRecognizer(self.panGestrue!)
                }
            }
        }
    }
    
    func closeMenu(sender : UIGestureRecognizer){
        self.mainView.userInteractionEnabled = false
        self.menuView.userInteractionEnabled = false
        self.mySubjectsViewController.navigationItem.leftBarButtonItem = self.menuItem
        self.mySubjectsViewController.menuButton.alpha = 0
        UIView.animateWithDuration(animationDuration, animations: { () -> Void in
            self.mainView.transform = CGAffineTransformMakeScale(1, 1)
            self.mainView.center = self.view.center
            self.menuView.transform = CGAffineTransformMakeScale(self.menuViewTranslateScale, self.menuViewTranslateScale)
            self.mySubjectsViewController.menuButton.alpha = 1
            self.menuView.center = self.menuViewTranslateCenter
            self.menuView.alpha = 0
            }) { (finished) -> Void in
                self.mainView.userInteractionEnabled = true
                self.menuView.userInteractionEnabled = true
                self.mySubjectsViewController.headScrollView.userInteractionEnabled = true
                self.mainView.removeGestureRecognizer(self.tapGestrue!)
                self.mainView.removeGestureRecognizer(self.panGestrue!)
        }

    }
    
}
