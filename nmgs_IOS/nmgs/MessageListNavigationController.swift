//
//  MessageListNavigationController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/29.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class MessageListNavigationController: UINavigationController{
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.navigationBar.isTranslucent = false
        let viewController:MessageListViewController = MessageListViewController(nibName: "LiveFramework.framework/MessageListViewController", bundle: nil)
        self.pushViewController(viewController, animated: true)
    }
}
