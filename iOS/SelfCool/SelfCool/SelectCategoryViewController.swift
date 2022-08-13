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
    
    var httpController = HttpController()
    var loadingView : UIView?
    
    override func viewDidLoad() {
        httpController.delegate = self
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clearColor())
        self.navigationController?.navigationBar.tintColor = UIColor.whiteColor()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Black
        
        teacherButton.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.25)), forState: UIControlState.Highlighted)
        bondButton.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.25)), forState: UIControlState.Highlighted)
//        loadingView = LoadingView(self)
//        self.view.addSubview(loadingView!)
//        let subjectParams = NSDictionary(dictionary: ["level" : "1"])
//        httpController.getNSDataByParams("getCategorys", paramsDictionary: subjectParams)
        
        super.viewDidLoad()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "getCategorys" {
            var categoryArray = dataDictionary.objectForKey("Records") as! NSArray
            for i in 0 ..< categoryArray.count{
                var category = categoryArray[i] as! NSDictionary
                var categoryId = category.objectForKey("id") as! Int
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
    
    @IBAction func reloadUserSubjects(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! UIViewController
        self.presentViewController(vc, animated: true, completion: nil)
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clearColor())
        self.navigationController?.navigationBar.tintColor = UIColor.whiteColor()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Black
    }
}