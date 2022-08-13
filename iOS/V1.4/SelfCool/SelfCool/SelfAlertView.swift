//
//  SelfAlertView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/31.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func SelfAlertView(owner : UIViewController , alertText : String , SelfAlertImageStyle : Int , SelfAlertLevel : Int){
    let padding : CGFloat = screenWidth / 320 * 80 / 8
    
    let alertView = UIView()
    alertView.layer.masksToBounds = true
    alertView.layer.cornerRadius = 10
    
    let alertBackgroundView = UIView()
    alertBackgroundView.backgroundColor = UIColor.blackColor()
    alertBackgroundView.alpha = 0.5
    
    let alertImageView = UIImageView()
    switch SelfAlertImageStyle{
        case 1 :
            alertImageView.image = UIImage(named: "alert_image_warning")
        case 2 :
            alertImageView.image = UIImage(named: "alert_image_error")
        case 3 :
            alertImageView.image = UIImage(named: "alert_image_success")
        case 4 :
            alertImageView.image = UIImage(named: "alert_image_smile")
        default :
            alertImageView.image = UIImage(named: "alert_image_warning")
    }
    
    let alertLabel = UILabel()
    alertLabel.text = alertText
    alertLabel.font = UIFont.systemFontOfSize(20)
    alertLabel.textColor = UIColor.whiteColor()
    alertLabel.sizeToFit()
    
    if alertLabel.frame.width < 150 - padding * 2{
        alertView.frame.size = CGSize(width: 150, height: screenWidth / 320 * 80)
    }else{
        alertView.frame.size = CGSize(width: padding * 2 + alertLabel.frame.width, height: screenWidth / 320 * 80)
    }
    alertView.center = CGPoint(x: screenWidth / 2, y: owner.view.frame.height / 4 * 3)
    alertBackgroundView.frame = CGRect(origin: CGPoint(x: 0, y: 0), size: alertView.frame.size)
    alertImageView.frame = CGRectMake((alertView.frame.width - alertView.frame.height / 3) / 2, padding , alertView.frame.height / 3, alertView.frame.height / 3)
    alertLabel.center = CGPoint(x: alertView.frame.width / 2, y: alertView.frame.height - (alertLabel.frame.height / 2) - padding)
    
    alertView.addSubview(alertBackgroundView)
    alertView.addSubview(alertImageView)
    alertView.addSubview(alertLabel)
    
    if SelfAlertLevel == 1{
        UIApplication.sharedApplication().keyWindow!.addSubview(alertView)
    }else if SelfAlertLevel == 2{
        owner.view.addSubview(alertView)
    }
    UIView.animateWithDuration(1, animations: { () -> Void in
        UIView.setAnimationDelay(1.5)
        alertView.alpha = 0
    }) { (finished) -> Void in
        alertView.removeFromSuperview()
    }
}

struct SelfAlertLevel {
    static let screen = 1
    static let view = 2
}

struct SelfAlertViewImageStyle {
    static let warning = 1
    static let error = 2
    static let success = 3
    static let smile = 4
}
