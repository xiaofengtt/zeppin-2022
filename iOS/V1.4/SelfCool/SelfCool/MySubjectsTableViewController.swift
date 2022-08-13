//
//  MySubjectsTableViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension MySubjectsViewController{
    func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return headView
    }
    
    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return screenWidth / 1080 * 500
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        if globalUserSubjectList.count == 0 {
            noSubjectView.hidden = false
        }else{
            noSubjectView.hidden = true
        }
        return globalUserSubjectList.count
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
        let subject = globalUserSubjectList[indexPath.row] as SubjectModel
        let subjectCell = self.userSubjectsTableView.dequeueReusableCellWithIdentifier("subjectCell") as UITableViewCell?
        subjectCell!.selected = false
        
        let subjectIcon = subjectCell!.viewWithTag(201) as! UIImageView
        let subjectName = subjectCell!.viewWithTag(202) as! UILabel
        let progressView = subjectCell!.viewWithTag(203) as UIView?
        let progressBar = subjectCell!.viewWithTag(204) as! UIProgressView
        let progressLabel = subjectCell!.viewWithTag(205) as! UILabel
        let examCountdown = subjectCell!.viewWithTag(206) as! UILabel
        
        progressView!.layer.masksToBounds = true
        progressView!.layer.cornerRadius = 5
        subjectName.text = subject.name
        progressBar.progress = Float(subject.preparingProgress / 100)
        examCountdown.text = subject.countDown.description
        progressLabel.text="\(Int(subject.preparingProgress))%"
        
        var image = getImageFromPath("subject", imageName: "\(subject.id)")
        if image == nil{
            image = saveImageFromUrl("subject", imageName: "\(subject.id)", urlString: subject.iconUrl)
        }
        subjectIcon.image = image
        
        return subjectCell!
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
        cell.remoteBorder()
    }
    
    func tableView(tableView: UITableView, titleForDeleteConfirmationButtonForRowAtIndexPath indexPath: NSIndexPath) -> String? {
        return "  删除  "
    }
}
