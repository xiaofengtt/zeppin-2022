//
//  AddSubjectViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/17.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AddSubjectViewController: UIViewController,UIScrollViewDelegate,UITableViewDataSource,UITableViewDelegate,HttpDataProtocol{
    
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
        httpController.delegate = self
        retrieveScrollView.delegate = self
        subjectTableView.delegate = self
        subjectTableView.dataSource = self
        
        if subjectTableView.respondsToSelector(Selector("separatorInset")){
            subjectTableView.separatorInset = UIEdgeInsetsZero
        }
        if subjectTableView.respondsToSelector(Selector("layoutMargins")){
            subjectTableView.layoutMargins = UIEdgeInsetsZero
        }
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: self, options: nil)
        loadingView = nibs.lastObject as? UIView
        loadingView!.frame = UIScreen.mainScreen().bounds
        self.view.addSubview(loadingView!)
        
        let categoryParams = NSDictionary(dictionary: ["category.id" : String(50) , "user.id" :String(user.id)])
        httpController.getNSDataByParams("getCategorySubjects", paramsDictionary: categoryParams)
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    func recieveError(recieveType: String, error: NSError) {
        httpController.getDataError()
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        
        var subjectCell = self.subjectTableView.dequeueReusableCellWithIdentifier("subjectCell") as! UITableViewCell
        var subject = retrieveList[selectRetrieve].subjects[indexPath.row] as SubjectModel
        
        var selectButton = subjectCell.viewWithTag(201) as! UIButton
        var selectName = subjectCell.viewWithTag(202) as! UILabel
        
        selectButton.layer.masksToBounds = true
        selectButton.layer.cornerRadius = selectButton.frame.width / 2
        selectButton.adjustsImageWhenHighlighted = false
        selectButton.setImage(UIImage(named: "subject_unselected"), forState: UIControlState.Normal)
        selectButton.setImage(UIImage(named: "subject_selected"), forState: UIControlState.Selected)
        selectButton.userInteractionEnabled = false
        selectName.text = subject.name
        if subject.isSelect{
            selectButton.selected = true
        }else{
            selectButton.selected = false
        }
        return subjectCell
    }
    func recieveDataResults(recieveType: String,data: NSData , inputParam : NSDictionary) {
        if recieveType == "getCategorySubjects" {
            if data.length == 0{
                httpController.getDataError()
            }else{
                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
                var retrieveArray = dictionary.objectForKey("Records") as! NSArray
                for index in 0 ..< retrieveArray.count{
                    var retrieve = retrieveArray[index] as! NSDictionary
                    var retrieveModel = DictionaryToRetrieve(retrieve)
                    retrieveList.append(retrieveModel)
                }
                let scrollViewHeight = retrieveScrollView.bounds.height
                let scrollViewWidth = CGFloat(80)
                
                retrieveScrollView.contentSize = CGSize(width: scrollViewWidth * CGFloat(retrieveList.count) , height: scrollViewHeight)
                for index in 0 ..< retrieveList.count{
                    var retrieve = retrieveList[index]
                    let xFloat = Float(index) * Float(scrollViewWidth)
                    var retrieveView = UIView(frame: CGRectMake(CGFloat(xFloat),0,scrollViewWidth, scrollViewHeight))
                    
                    var retrieveButton = UIButton(frame: CGRectMake(0, 0, scrollViewWidth, scrollViewHeight))
                    retrieveButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
                    retrieveButton.titleLabel!.font = UIFont.systemFontOfSize(16)
                    retrieveButton.setTitle(retrieve.name, forState: UIControlState.Normal)
                    retrieveButton.tag = 100 + index
                    retrieveButton.setBackgroundImage(UIImage(named: "background_gray_highlight"), forState: UIControlState.Highlighted)
                    retrieveButton.addTarget(self, action: "clickRetrieve:", forControlEvents: UIControlEvents.TouchUpInside)
                    retrieveButtons.append(retrieveButton)
                    retrieveView.addSubview(retrieveButton)
                    var spaceLineView = UIView(frame: CGRectMake(scrollViewWidth - 1,0,1, scrollViewHeight))
                    spaceLineView.backgroundColor = UIColor.spaceLineGray()
                    retrieveView.addSubview(spaceLineView)
                    retrieveScrollView.addSubview(retrieveView)
                }
                retrieveButtons[selectRetrieve].backgroundColor = UIColor.mainColor()
                retrieveButtons[selectRetrieve].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                subjectTableView.reloadData()
            }
        }else if recieveType == "addSubject"{
            if data.length == 0{
                 httpController.getDataError()
            }
//            else{
//                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
//                var status = dictionary.objectForKey("Status") as! String
//                if status == "success"{
//                    var subjectId = (inputParam.objectForKey("subject.id") as! String).toInt()
//                }
//            }
        }else if recieveType == "deleteSubject"{
            if data.length == 0{
                httpController.getDataError()
            }
//            else{
//                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
//                var status = dictionary.objectForKey("Status") as! String
//                if status == "success"{
//                    var subjectId = (inputParam.objectForKey("subject.id") as! String).toInt()
//                    for i in 0 ..< globalUserSubjectList.count{
//                        if globalUserSubjectList[i].id == subjectId{
//                            globalUserSubjectList.removeAtIndex(i)
//                            break
//                        }
//                    }
//                }
//            }
        }
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    
    func clickRetrieve(sender:UIButton){
        retrieveButtons[selectRetrieve].setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        retrieveButtons[selectRetrieve].backgroundColor = UIColor.whiteColor()
        selectRetrieve = sender.tag - 100
        retrieveButtons[selectRetrieve].setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        retrieveButtons[selectRetrieve].backgroundColor = UIColor.mainColor()
        subjectTableView.reloadData()
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        if retrieveList.count != 0{
            return retrieveList[selectRetrieve].subjects.count
        }else{
            return 0
        }
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        var subjectCell = tableView.cellForRowAtIndexPath(indexPath)
        var selectButton = subjectCell?.viewWithTag(201) as! UIButton
        selectSubjectId = retrieveList[selectRetrieve].subjects[indexPath.row].id
        if selectButton.selected {
            selectButton.selected = false
            let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubjectId)])
            httpController.getNSDataByParams("deleteSubject", paramsDictionary: deleteParams)
        }else {
            selectButton.selected = true
            let addParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubjectId)])
            httpController.getNSDataByParams("addSubject", paramsDictionary: addParams)
        }
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        if cell.respondsToSelector(Selector("separatorInset")){
            cell.separatorInset = UIEdgeInsetsZero
        }
        if cell.respondsToSelector(Selector("layoutMargins")){
            cell.layoutMargins = UIEdgeInsetsZero
        }
    }
    
    @IBAction func compelete(sender: UIButton) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mySubjectsNavigationBar") as! UIViewController
        self.presentViewController(vc, animated: true, completion: nil)
    }
}