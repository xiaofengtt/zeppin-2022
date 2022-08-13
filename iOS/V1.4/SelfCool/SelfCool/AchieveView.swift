//
//  AchieveView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/18.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
import AudioToolbox

class AchieveView: UIView {
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var button: UIButton!
    
    override func drawRect(rect: CGRect) {
        super.drawRect(rect)
        button.addTarget(nil, action: "closeButton:", forControlEvents: UIControlEvents.TouchUpInside)
    }
    
    func closeButton(sender : UIButton){
        self.removeFromSuperview()
    }
    
    struct imageType {
        static let allRight = 1
        static let allFail = 2
        static let progress10 = 3
        static let progress30 = 4
        static let progress60 = 5
        static let progress100 = 6
    }
}

func ShowAchieveView(owner: UIViewController , imageType : Int){
    let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("AchieveView", owner: owner, options: nil)
    let achieveView = nibs.lastObject as! AchieveView
    
    let imageView = achieveView.viewWithTag(801) as! UIImageView
    if imageType == AchieveView.imageType.allRight{
        imageView.image = UIImage(named: "achieve_all_right")
    }else if imageType == AchieveView.imageType.allFail{
        imageView.image = UIImage(named: "achieve_all_fail")
    }else if imageType == AchieveView.imageType.progress10{
        imageView.image = UIImage(named: "achieve_progress_10")
    }else if imageType == AchieveView.imageType.progress30{
        imageView.image = UIImage(named: "achieve_progress_30")
    }else if imageType == AchieveView.imageType.progress60{
        imageView.image = UIImage(named: "achieve_progress_60")
    }else if imageType == AchieveView.imageType.progress100{
        imageView.image = UIImage(named: "achieve_progress_100")
    }
    
    achieveView.frame = UIScreen.mainScreen().bounds
    owner.view.addSubview(achieveView)
    
    var soundString = ""
    if imageType == AchieveView.imageType.allFail{
        soundString = "achieve_fail"
    }else{
        soundString = "achieve_right"
    }
    let soundUrl = NSBundle.mainBundle().URLForResource(soundString, withExtension: "wav")
    var soundId : SystemSoundID = 0
    AudioServicesCreateSystemSoundID(soundUrl!, &soundId)
    AudioServicesPlaySystemSound(soundId)
}
