//
//  SelectCategoryViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/8.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class SelectCategoryViewController: UIViewController , HttpDataProtocol{
    
    @IBOutlet weak var backButton: UIBarButtonItem!
    @IBOutlet weak var teacherButton: UIButton!
    @IBOutlet weak var bondButton: UIButton!
    @IBOutlet weak var psychologyButton: UIButton!
    
    var httpController = HttpController()
    var loadingView : UIView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clearColor())
        self.navigationController?.navigationBar.tintColor = UIColor.whiteColor()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Black
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent
        
        teacherButton.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.25)), forState: UIControlState.Highlighted)
        bondButton.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.25)), forState: UIControlState.Highlighted)
        psychologyButton.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.25)), forState: UIControlState.Highlighted)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("SelectCategory")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("SelectCategory")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "getCategorys" {
            let categoryArray = dataDictionary.objectForKey("Records") as! NSArray
            for i in 0 ..< categoryArray.count{
                let category = categoryArray[i] as! NSDictionary
                let categoryId = category.objectForKey("id") as! Int
                CategoryModel.getEnable(categoryId)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    override func preferredStatusBarStyle() -> UIStatusBarStyle {
        return UIStatusBarStyle.LightContent
    }
    
    @IBAction func teacherButton(sender: UIButton) {
        selectCategory = globalCategoryList[0]
    }
    @IBAction func bondButton(sender: UIButton) {
        selectCategory = globalCategoryList[1]
    }
    @IBAction func psychologyButton(sender: UIButton) {
        selectCategory = globalCategoryList[2]
    }
    
    @IBAction func reloadUserSubjects(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") 
        self.presentViewController(vc, animated: true) { () -> Void in
            UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
        }
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clearColor())
        self.navigationController?.navigationBar.tintColor = UIColor.whiteColor()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Black
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent
    }
}