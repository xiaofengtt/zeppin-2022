//
//  MainTabBarController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/24.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

protocol TabBarPointObserverDelegate {
    func addRedPoint()
    func deleteRedPoint()
}

class MainTabBarController : UITabBarController ,UITabBarControllerDelegate, TabBarPointObserverDelegate{
    
    var tabBarPointObserver: TabBarPointObserver!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        tabBarPointObserver = TabBarPointObserver(tabBar: self.tabBar)
        tabBarPointObserver.delegate = self
        Singleton.main().tabBlock  = { (index:Int) in
            self.selectedIndex = index
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.selectedIndex = 2
    }
    
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        if (self.selectedIndex == 3){
            NotificationCenter.default.post(Notification(name: NSNotification.Name(rawValue: "hideTabBarRedView")))
        }
        PlayerView.playerListPause()
    }
    
    internal func addRedPoint() {
        self.tabBar.showBadgePoint(index: 3)
    }

    internal func deleteRedPoint() {
        self.tabBar.hideBadgePoint(index: 3)
    }
    
    class TabBarPointObserver: NSObject{
        
        var delegate : TabBarPointObserverDelegate?
        
        init(tabBar: UITabBar) {
            super.init()
            NotificationCenter.default.addObserver(self, selector: #selector(showRedPoint),name: NSNotification.Name(rawValue: "showRedView"), object: nil)
            NotificationCenter.default.addObserver(self, selector: #selector(hideRedPoint),name: NSNotification.Name(rawValue: "hideTabBarRedView"), object: nil)
        }
        
        deinit {
            NotificationCenter.default.removeObserver(self)
        }
        
        func hideRedPoint(tabBar: UITabBar){
            self.delegate?.deleteRedPoint()
        }
        
        func showRedPoint(tabBar: UITabBar){
            self.delegate?.addRedPoint()
        }
    }
}

