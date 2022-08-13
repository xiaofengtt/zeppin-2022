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
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var userIcon: UIImageView!
    @IBOutlet weak var userSubjectsTableView: UITableView!
    @IBOutlet weak var addButton: UIBarButtonItem!
    
    var httpController = HttpController()
    var deleteRow = 0
    var loadingView : UIView?
    var isChangeSubject : Bool?
    
    override func viewDidLoad() {
        httpController.delegate = self
        userSubjectsTableView.delegate = self
        userSubjectsTableView.dataSource = self
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: self, options: nil)
        loadingView = nibs.lastObject as? UIView
        loadingView!.frame = UIScreen.mainScreen().bounds
        self.view.addSubview(loadingView!)
        
        userNameLabel.text = user.name
        userIcon.layer.masksToBounds = true
        userIcon.layer.borderColor = UIColor.whiteColor().CGColor
        
        if userSubjectsTableView.respondsToSelector(Selector("separatorInset")){
            userSubjectsTableView.separatorInset = UIEdgeInsetsZero
        }
        if userSubjectsTableView.respondsToSelector(Selector("layoutMargins")){
            userSubjectsTableView.layoutMargins = UIEdgeInsetsZero
        }
        userSubjectsTableView.tableFooterView = UIView()
        userSubjectsTableView.backgroundColor = UIColor.whiteColor()
        
        let subjectParams = NSDictionary(dictionary: ["user.id" : String(user.id) ])
        httpController.getNSDataByParams("getUserSubjects", paramsDictionary: subjectParams)
        super.viewDidLoad()
    }
    
    func recieveError(recieveType: String, error: NSError) {
        httpController.getDataError()
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    
    func recieveDataResults(recieveType: String,data: NSData ,inputParam : NSDictionary) {
        if recieveType == "getUserSubjects" {
            if data.length == 0{
                httpController.getDataError()
            }else{
                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
                var userSubjectArray = dictionary.objectForKey("Records") as! NSArray
                var userSubjectList : [SubjectModel] = []
                for index in 0 ..< userSubjectArray.count{
                    var userSubject = userSubjectArray[index] as! NSDictionary
                    var userSubjectModel = DictionaryToUserSubject(userSubject)
                    userSubjectModel.isSelect = true
                    userSubjectList.append(userSubjectModel)
                }
                globalUserSubjectList = userSubjectList
                self.userSubjectsTableView.reloadData()
            }
        }
        if recieveType == "deleteSubject"{
            if data.length == 0{
                httpController.getDataError()
            }else{
                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
                var status = dictionary.objectForKey("Status") as! String
                if status == "success"{
                    
                }else{
                    httpController.getDataError()
                }
            }
        }
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        userIcon.image = userImage!
        userIcon.layer.borderWidth = 2
        userIcon.layer.cornerRadius = CGFloat(Float(userIcon.bounds.width) / 2)
        
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        if globalUserSubjectList.count == 0 {
            noSubjectView.hidden = false
        }else{
            noSubjectView.hidden = true
        }
        return globalUserSubjectList.count
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        var tableViewCell = tableView.cellForRowAtIndexPath(indexPath)
    }
    
    func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        if selectSubject?.id == globalUserSubjectList[indexPath.row].id{
            isChangeSubject = false
        }else{
            isChangeSubject = true
        }
        selectSubject = globalUserSubjectList[indexPath.row]
        return indexPath
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return screenHeight / 6
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        
        var subjectCell = self.userSubjectsTableView.dequeueReusableCellWithIdentifier("subjectCell") as! UITableViewCell
        subjectCell.selected = false
        var subject = globalUserSubjectList[indexPath.row] as SubjectModel
        
        var subjectIcon = subjectCell.viewWithTag(201) as! UIImageView
        var subjectName = subjectCell.viewWithTag(202) as! UILabel
        var progressView = subjectCell.viewWithTag(203) as UIView?
        var progressBar = subjectCell.viewWithTag(204) as! UIProgressView
        var progressLabel = subjectCell.viewWithTag(205) as! UILabel
        var examCountdown = subjectCell.viewWithTag(206) as! UILabel
        
        progressView!.layer.masksToBounds = true
        progressView!.layer.cornerRadius = 5
        
        subjectIcon.image = UIImage(named: subject.iconUrl)
        subjectName.text = subject.name
        progressBar.progress = Float(subject.preparingProgress / 100)
        examCountdown.text = subject.countDown.description
        progressLabel.text="\(Int(subject.preparingProgress))%"
        
        return subjectCell
    }
    
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == UITableViewCellEditingStyle.Delete{
            let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(globalUserSubjectList[indexPath.row].id)])
            httpController.getNSDataByParams("deleteSubject", paramsDictionary: deleteParams)
            deleteRow = indexPath.row
            globalUserSubjectList.removeAtIndex(deleteRow)
            self.userSubjectsTableView.reloadData()
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
    
    func tableView(tableView: UITableView, titleForDeleteConfirmationButtonForRowAtIndexPath indexPath: NSIndexPath) -> String! {
        return "  删除  "
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        userSubjectsTableView.reloadData()
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "MySubjectsToSubject"{
            var vc =  segue.destinationViewController as! SubjectViewController
            vc.isChangeSubject = self.isChangeSubject
        }
    }
}