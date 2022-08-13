//
//  SelectCategoryViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/8.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class SelectCategoryViewController: UIViewController{
    
    @IBOutlet weak var SelectView: UIView!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    override func viewDidLoad() {
        SelectView.layer.masksToBounds = true
        SelectView.layer.cornerRadius  = 10
        SelectView.layer.borderWidth = 0.2
        SelectView.layer.borderColor = UIColor.darkGrayColor().CGColor
        super.viewDidLoad()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func reloadUserSubjects(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! UIViewController
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
    }
}