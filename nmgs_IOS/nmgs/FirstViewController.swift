//
//  FirstViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/22.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if nmgsUser.isAutoLogin{
            let userParams = NSDictionary(dictionary: ["loginname" : nmgsUser.phone ,"token" : nmgsUser.token])
            HttpController.getNSDataByParams("verifyUser", paramsDictionary: userParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let dataDic = dataDictionary.object(forKey: "data") as! NSDictionary
                    let rtnCode = dataDic.object(forKey: "rtnCode") as! String
                    if rtnCode == "0"{
                        let sb = UIStoryboard(name: "Main", bundle: nil)
                        let vc = sb.instantiateViewController(withIdentifier: "mainTabBar") as! UITabBarController
                        self.present(vc, animated: true, completion: nil)
                    }else{
                        self.gotoLogin()
                    }
                }else{
                    self.gotoLogin()
                }
            }, errors: { (error) in
                self.gotoLogin()
            })
        }else{
            gotoLogin()
        }
    }
    
    func gotoLogin(){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "loginViewController") as! LoginViewController
        self.present(vc, animated: true, completion: nil)
    }
}
