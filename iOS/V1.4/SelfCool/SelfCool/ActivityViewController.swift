//
//  ActivityViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/8/4.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ActivityViewController: UIViewController{
    
    @IBOutlet weak var activityWebView: UIWebView!
    
    var activity : ActivityModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = activity.title
        (activityWebView.subviews[0] as! UIScrollView).bounces = false
        activityWebView.loadRequest(NSURLRequest(URL: NSURL(string: activity.urlString)!))
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("Activity\(activity.id)")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("Activity\(activity.id))")
    }
    
    @IBAction func shareButton(sender: UIBarButtonItem) {
        let publishContent : ISSContent = ShareSDK.content("\(activity.title) \(activity.urlString)", defaultContent:"自学酷活动",image: nil, title:"提示",url: activity.urlString ,description:"",mediaType:SSPublishContentMediaTypeNews)
         let shareList = [UInt(ShareTypeWeixiSession.rawValue),UInt(ShareTypeWeixiTimeline.rawValue),UInt(ShareTypeQQ.rawValue),UInt(ShareTypeQQSpace.rawValue),UInt(ShareTypeSinaWeibo.rawValue)]
        ShareSDK.showShareActionSheet(nil, shareList: shareList, content: publishContent, statusBarTips: true, authOptions: nil, shareOptions: nil, result: {(type:ShareType,state:SSResponseState,statusInfo:ISSPlatformShareInfo!,error:ICMErrorInfo!,end:Bool) in
            if (state.rawValue == SSResponseStateSuccess.rawValue){
                let alert = UIAlertView(title: "提示", message:"分享成功", delegate:self, cancelButtonTitle: "ok")
                alert.show()
            }else {
                if (state.rawValue == 2) {
                    let alert = UIAlertView(title: "提示", message:"您没有安装客户端，无法使用分享功能！", delegate:self, cancelButtonTitle: "ok")
                    alert.show()
                }
            }
        })
    }
}