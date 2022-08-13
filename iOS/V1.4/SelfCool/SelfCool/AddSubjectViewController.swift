//
//  AddSubjectViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/17.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AddSubjectViewController: UIViewController,UIScrollViewDelegate,UITableViewDataSource,UITableViewDelegate,HttpDataProtocol{
    
    @IBOutlet weak var titleImage: UIImageView!
    @IBOutlet weak var subjectTableView: UITableView!
    @IBOutlet weak var retrieveScrollView: UIScrollView!
    @IBOutlet weak var completeButton: UIButton!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    var loadingView : UIView?
    var httpController = HttpController()
    var retrieveList : [RetrieveModel] = []
    var retrieveButtons : [UIButton] = []
    var selectRetrieve = 0
    var selectSubjectId = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        retrieveScrollView.delegate = self
        subjectTableView.delegate = self
        subjectTableView.dataSource = self
        
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.tintColor = UIColor.blackColor()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Default
        self.navigationItem.title = "\(selectCategory!.name)考试设置"
        completeButton.layer.masksToBounds = true
        completeButton.layer.cornerRadius = 5
        
        subjectTableView.remoteBorder()
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        
        let categoryParams = NSDictionary(dictionary: ["category.id" : String(selectCategory!.id) , "user.id" :String(user.id)])
        httpController.getNSDataByParams("getCategorySubjects", paramsDictionary: categoryParams)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("AddSubject")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("AddSubject")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary , inputParam : NSDictionary) {
        if recieveType == "getCategorySubjects" {
            let status = dataDictionary.objectForKey("Status") as! String
            if status == "success" {
                let retrieveArray = dataDictionary.objectForKey("Records") as! NSArray
                for index in 0 ..< retrieveArray.count{
                    let retrieve = retrieveArray[index] as! NSDictionary
                    let retrieveModel = RetrieveModel(dictionary: retrieve)
                    retrieveList.append(retrieveModel)
                }
                let scrollViewHeight = retrieveScrollView.bounds.height
                var scrollViewWidth : CGFloat = 80
                if retrieveArray.count < 5{
                    scrollViewWidth = screenWidth / CGFloat(retrieveArray.count)
                }
                retrieveScrollView.contentSize = CGSize(width: scrollViewWidth * CGFloat(retrieveList.count) , height: scrollViewHeight)
                for index in 0 ..< retrieveList.count{
                    let retrieve = retrieveList[index]
                    let xFloat = Float(index) * Float(scrollViewWidth)
                    let retrieveView = UIView(frame: CGRectMake(CGFloat(xFloat),0,scrollViewWidth, scrollViewHeight))
                    
                    let retrieveButton = UIButton(frame: CGRectMake(0, 0, scrollViewWidth, scrollViewHeight))
                    retrieveButton.setTitleColor(UIColor.darkGrayColor(), forState: UIControlState.Normal)
                    retrieveButton.setTitleColor(UIColor.mainColor(), forState: UIControlState.Selected)
                    retrieveButton.setTitleColor(UIColor.mainColor(), forState: UIControlState.Highlighted)
                    retrieveButton.setBackgroundImage(UIImage(named: "retrieve_selected"), forState: UIControlState.Selected)
                    retrieveButton.setBackgroundImage(UIImage(named: "retrieve_selected"), forState: UIControlState.Highlighted)
                    retrieveButton.titleLabel!.font = UIFont.systemFontOfSize(16)
                    retrieveButton.setTitle(retrieve.name, forState: UIControlState.Normal)
                    retrieveButton.tag = 100 + index
                    retrieveButton.addTarget(self, action: "clickRetrieve:", forControlEvents: UIControlEvents.TouchUpInside)
                    retrieveButtons.append(retrieveButton)
                    retrieveView.addSubview(retrieveButton)
                    let spaceLineView = UIView(frame: CGRectMake(scrollViewWidth - 1,0,1, scrollViewHeight))
                    spaceLineView.backgroundColor = UIColor.backgroundGray()
                    retrieveView.addSubview(spaceLineView)
                    retrieveScrollView.addSubview(retrieveView)
                }
                if retrieveButtons.count > 0{
                    retrieveButtons[selectRetrieve].selected = true
                }
                subjectTableView.reloadData()
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    func clickRetrieve(sender:UIButton){
        retrieveButtons[selectRetrieve].selected = false
        selectRetrieve = sender.tag - 100
        retrieveButtons[selectRetrieve].selected = true
        subjectTableView.reloadData()
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        if retrieveList.count != 0{
            return retrieveList[selectRetrieve].subjects.count
        }else{
            return 0
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        
        let subjectCell = self.subjectTableView.dequeueReusableCellWithIdentifier("subjectCell") as UITableViewCell?
        let subject = retrieveList[selectRetrieve].subjects[indexPath.row] as SubjectModel
        
        let selectButton = subjectCell!.viewWithTag(201) as! UIButton
        let selectName = subjectCell!.viewWithTag(202) as! UILabel
        
        selectButton.layer.masksToBounds = true
        selectButton.layer.cornerRadius = 3
        selectButton.adjustsImageWhenHighlighted = false
        selectButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
        selectButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Selected)
        selectButton.userInteractionEnabled = false
        selectName.text = subject.name
        if subject.isSelect{
            selectButton.selected = true
        }else{
            selectButton.selected = false
        }
        return subjectCell!
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        let subjectCell = tableView.cellForRowAtIndexPath(indexPath)
        let selectButton = subjectCell?.viewWithTag(201) as! UIButton
        selectSubjectId = retrieveList[selectRetrieve].subjects[indexPath.row].id
        if selectButton.selected {
            selectButton.selected = false
            retrieveList[selectRetrieve].subjects[indexPath.row].isSelect = false
            let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubjectId)])
            httpController.getNSDataByParams("deleteSubject", paramsDictionary: deleteParams)
        }else {
            selectButton.selected = true
            retrieveList[selectRetrieve].subjects[indexPath.row].isSelect = true
            let addParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubjectId)])
            httpController.getNSDataByParams("addSubject", paramsDictionary: addParams)
        }
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        cell.remoteBorder()
    }
    
    @IBAction func compelete(sender: UIButton) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") 
        self.presentViewController(vc, animated: true, completion: nil)
    }
}