//
//  MySubjectsViewControll.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class MySubjectsViewController: UIViewController,UITableViewDataSource,UITableViewDelegate,HttpDataProtocol {
    
    @IBOutlet weak var noSubjectView: UIView!
    @IBOutlet weak var userSubjectsTableView: UITableView!
    @IBOutlet weak var addButton: UIBarButtonItem!
    @IBOutlet weak var menuButton: UIButton!
    
    var headView : UIView?
    var headScrollView : UIScrollView!
    var headPageControl : UIPageControl!
    
    let activetyButtonTagBase = 100
    var timer : NSTimer!
    var httpController = HttpController()
    var deleteRow = 0
    var loadingView : UIView?
    var isChangeSubject : Bool?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        userSubjectsTableView.delegate = self
        userSubjectsTableView.dataSource = self
        
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        userSubjectsTableView.remoteBorder()
        userSubjectsTableView.tableFooterView = UIView()
        userSubjectsTableView.backgroundColor = UIColor.whiteColor()
        
        menuButton.imageView?.layer.masksToBounds = true
        menuButton.imageView?.layer.cornerRadius = menuButton.frame.width / 2
        menuButton.setImage(userImage, forState: UIControlState.Normal)
        menuButton.addTarget(self.parentViewController?.parentViewController , action: "menuButton:", forControlEvents: UIControlEvents.TouchUpInside)
        menuButton.hasRedPoint(true)
        
        let headNibs : NSArray = NSBundle.mainBundle().loadNibNamed("MySubjectsTableHeadView", owner: self, options: nil)
        headView = headNibs.lastObject as? UIView
        headView!.frame.size = CGSize(width: screenWidth, height: screenWidth / 1080 * 500)
        headScrollView = headView!.viewWithTag(601) as! UIScrollView
        headScrollView.frame.size = headView!.frame.size
        headPageControl = headView!.viewWithTag(602) as! UIPageControl
        
        headScrollView.delegate = self
        
        let activityParams = NSDictionary()
        httpController.getNSDataByParams("getActivityList", paramsDictionary: activityParams)
        let subjectParams = NSDictionary(dictionary: ["user.id" : String(user.id) ])
        httpController.getNSDataByParams("getUserSubjects", paramsDictionary: subjectParams)
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("MySubjects")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("MySubjects")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
        if recieveType == "getUserSubjects" {
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success"{
                let userSubjectArray = dataDictionary.objectForKey("Records") as! NSArray
                var userSubjectList : [SubjectModel] = []
                for index in 0 ..< userSubjectArray.count{
                    let userSubject = userSubjectArray[index] as! NSDictionary
                    let userSubjectModel = SubjectModel(dictionary: userSubject)
                    userSubjectModel.isSelect = true
                    userSubjectList.append(userSubjectModel)
                }
                globalUserSubjectList = userSubjectList
                self.userSubjectsTableView.reloadData()
            }
        }else if recieveType == "deleteSubject"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status != "success"{
                httpController.getDataError(nil)
            }
        }else if recieveType == "getActivityList"{
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success"{
                let activityArray = dataDictionary.objectForKey("Records") as! NSArray
                activityList.removeAll(keepCapacity: false)
                for i in 0 ..< activityArray.count{
                    let activityDictionary = activityArray[i] as! NSDictionary
                    let activity = ActivityModel()
                    activity.id = activityDictionary.objectForKey("id") as! Int
                    activity.title = activityDictionary.objectForKey("title") as! String
                    activity.urlString = activityDictionary.objectForKey("url") as! String
                    activity.imageUrl = UrlBase + (activityDictionary.objectForKey("image.url") as! String)
                    activityList.append(activity)
                }
                headViewInit()
                
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        if scrollView == headScrollView{
            let page = Int(floor((scrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            headPageControl.currentPage = page
        }
    }
    
    func headViewInit(){
        addTimer()
        
        headScrollView.contentSize = CGSize(width: screenWidth * CGFloat(activityList.count) , height: screenWidth / 1080 * 500)
        for i in 0 ..< activityList.count{
            let imageView = UIImageView(frame: CGRect(x: screenWidth * CGFloat(i), y: 0, width: screenWidth , height: headScrollView.frame.height))
            var image = activityImageDictionary.objectForKey("\(activityList[i].id)") as? UIImage
            if image == nil{
                image = getImageFromPath("activity", imageName: "\(activityList[i].id)")
                if image == nil{
                    image = saveImageFromUrl("activity", imageName: "\(activityList[i].id)", urlString: activityList[i].imageUrl)
                    activityImageDictionary.setValue(image, forKey: "\(activityList[i].id)")
                }else{
                    activityImageDictionary.setValue(image, forKey: "\(activityList[i].id)")
                }
            }
            imageView.image = image
            
            let button = UIButton(frame: imageView.frame)
            button.tag = activetyButtonTagBase + i
            button.addTarget(self, action: "activetyButton:", forControlEvents: UIControlEvents.TouchUpInside)
            
            headScrollView.addSubview(imageView)
            headScrollView.addSubview(button)
        }
        headPageControl.numberOfPages = activityList.count
    }
    
    func addTimer(){
        self.timer = NSTimer.scheduledTimerWithTimeInterval(5, target: self, selector: "nextImage", userInfo: nil, repeats: true)
    }
    
    func nextImage(){
        let page = Int(floor((headScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        if page != headPageControl.numberOfPages - 1{
            headScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page + 1), 0, headScrollView.frame.width, headScrollView.frame.height), animated: true)
        }else{
            headScrollView.scrollRectToVisible(CGRectMake(0, 0, headScrollView.frame.width, headScrollView.frame.height), animated: true)
        }
    }
    
    func activetyButton(sender : UIButton){
        let index = sender.tag - activetyButtonTagBase
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("activityViewController") as! ActivityViewController
        vc.activity = activityList[index]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        userSubjectsTableView.reloadData()
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "MySubjectsToSubject"{
            let vc =  segue.destinationViewController as! SubjectViewController
            vc.isChangeSubject = self.isChangeSubject
        }
    }
}