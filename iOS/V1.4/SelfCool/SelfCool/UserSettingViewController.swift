//
//  UserSettingViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserSettingViewController: UIViewController , HttpDataProtocol{
    @IBOutlet weak var logoutButton: UIButton!
    
    var httpController = HttpController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        logoutButton.layer.masksToBounds = true
        logoutButton.layer.cornerRadius = 5
        logoutButton.layer.borderWidth = 1
        logoutButton.layer.borderColor = UIColor.spaceLineGray().CGColor
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("UserSetting")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserSetting")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
    }
    
    @IBAction func backButton(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") as! MainPageViewController
        self.presentViewController(vc, animated: true) { () -> Void in
            UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
        }
    }
    
    @IBAction func logoutButton(sender: UIButton) {
        user = UserModel()
        globalUserSubjectList = []
        globalKnowledgeList = []
        globalItemList = []
        knowledgeChangeList = []
        selectSubject = nil
        selectKnowledge = nil
        
        let userDict = user.getDictionary()
        LocalDataController.writeLocalData("user", data: userDict)
        
        ShareSDK.cancelAuthWithType(ShareTypeWeixiSession)
        ShareSDK.cancelAuthWithType(ShareTypeQQSpace)
        ShareSDK.cancelAuthWithType(ShareTypeSinaWeibo)
        httpController.getNSDataByParams("logout", paramsDictionary: NSDictionary())
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("accountChoiceViewController") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func close(segue: UIStoryboardSegue){}
}