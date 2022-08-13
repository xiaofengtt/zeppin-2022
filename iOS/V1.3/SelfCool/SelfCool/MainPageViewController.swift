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
    
    var baseNavigationBar : BaseNavigationBar
    var mySubjectsViewController : MySubjectsViewController
    var menuViewController : MenuViewController
    var mainView = UIView()
    var menuView = UIView()
    var panGestrue :UIGestureRecognizer?
    
    required init(coder aDecoder: NSCoder) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        self.baseNavigationBar = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! BaseNavigationBar
        self.mySubjectsViewController = self.baseNavigationBar.childViewControllers[0] as! MySubjectsViewController
        self.menuViewController = sb.instantiateViewControllerWithIdentifier("menuViewController") as! MenuViewController
        super.init(coder: aDecoder)
    }

    override func viewDidLoad() {
        self.view.addSubview(self.menuView)
        self.view.addSubview(self.mainView)
        self.mainView.frame = self.view.bounds
        self.menuView.frame = self.view.bounds
        
        self.addChildViewController(self.menuViewController)
        self.menuViewController.view.frame = self.view.bounds
        self.menuView.addSubview(self.menuViewController.view)
        self.menuViewController.didMoveToParentViewController(self)
        
        self.addChildViewController(self.baseNavigationBar)
        self.baseNavigationBar.view.frame = self.view.bounds
        self.mainView.addSubview(self.baseNavigationBar.view)
        self.baseNavigationBar.didMoveToParentViewController(self)
        
        panGestrue = UITapGestureRecognizer(target: self, action: "closeMenu:")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    func menuButton(sender: UIButton){
        self.mainView.userInteractionEnabled = false
        self.menuView.userInteractionEnabled = false
        UIView.animateWithDuration(animationDuration, animations: { () -> Void in
            self.mainView.transform = CGAffineTransformMakeScale(0.7, 0.7)
            self.mainView.center = CGPoint(x: screenWidth * 1.1, y: screenHeight / 2)
        }) { (finished) -> Void in
            self.mainView.userInteractionEnabled = true
            self.menuView.userInteractionEnabled = true
            self.mainView.addGestureRecognizer(self.panGestrue!)
        }
    }
    
    func closeMenu(sender : UIGestureRecognizer){
        self.mainView.userInteractionEnabled = false
        self.menuView.userInteractionEnabled = false
        UIView.animateWithDuration(animationDuration, animations: { () -> Void in
            self.mainView.transform = CGAffineTransformMakeScale(1, 1)
            self.mainView.center = self.view.center
            }) { (finished) -> Void in
                self.mainView.userInteractionEnabled = true
                self.menuView.userInteractionEnabled = true
                self.mainView.removeGestureRecognizer(self.panGestrue!)
        }

    }
    
}