//
//  SettingViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class SettingViewController: UIViewController{
    
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var userImageView: UIImageView!
    @IBOutlet weak var versionLabel: UILabel!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    
    override func viewDidLoad() {
        userNameLabel.text = user.name
        userImageView.image = userImage!
        userImageView.layer.masksToBounds = true
        userImageView.layer.cornerRadius = userImageView.frame.width / 2
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBAction func logout(sender: AnyObject) {
        user = UserModel()
        globalCategoryList = []
        globalUserSubjectList = []
        globalKnowledgeList = []
        globalItemList = []
        knowledgeChangeList = []
        selectSubject = nil
        selectKnowledge = nil
        
        var userDict = user.getDictionary()
        writeLocalData("user", userDict)
        
        ShareSDK.cancelAuthWithType(ShareTypeWeixiSession)
        ShareSDK.cancelAuthWithType(ShareTypeQQSpace)
        ShareSDK.cancelAuthWithType(ShareTypeSinaWeibo)
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("loginViewController") as! UIViewController
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
}