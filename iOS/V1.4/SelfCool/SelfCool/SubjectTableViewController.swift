//
//  SubjectTableViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/22.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension SubjectViewController {
    func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return headView
    }
    
    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return screenWidth / 320 * 185
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        if tableStatus == 1{
            if globalItemTypeList.count != 0{
                return globalItemTypeList.count
            }else{
                return 1
            }
        }else{
            return tableKnowledgeList.count
        }
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
            return screenHeight / 7
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        var cell : UITableViewCell?
        if tableStatus == 1{
            if globalItemTypeList.count != 0{
                cell = self.knowledgeTableView.dequeueReusableCellWithIdentifier("combinedCell") as UITableViewCell?
                cell!.selectionStyle = UITableViewCellSelectionStyle.None
                
                let itemType = globalItemTypeList[indexPath.row]
                
                let backgroundView = cell!.viewWithTag(201) as UIView?
                let itemTypeNameLabel = cell!.viewWithTag(202) as! UILabel
                let countLabel = cell!.viewWithTag(203) as! UILabel
                let progressView = cell!.viewWithTag(204) as! UIProgressView
                let button = cell!.viewWithTag(205) as! UIButton
                
                itemTypeNameLabel.text = itemType.name
                countLabel.text = "\(itemType.flagCount)/\(itemType.itemCount)"
                progressView.progress = Float(itemType.flagCount) / Float(itemType.itemCount)
                progressView.tintColor = itemType.progressColor
                backgroundView!.layer.masksToBounds = true
                backgroundView!.layer.borderColor = itemType.progressColor.CGColor
                backgroundView!.layer.borderWidth = 1
                backgroundView!.layer.cornerRadius = screenHeight / 25
                button.setBackgroundImage(UIImage.imageWithColor(UIColor.lightGrayColor().colorWithAlphaComponent(0.2)), forState: UIControlState.Highlighted)
            }else{
                cell = self.knowledgeTableView.dequeueReusableCellWithIdentifier("noCombinedCell") as UITableViewCell?
                cell!.selectionStyle = UITableViewCellSelectionStyle.None
            }
        }else{
            cell = self.knowledgeTableView.dequeueReusableCellWithIdentifier("knowledgeCell") as UITableViewCell?
            
            let knowledge = tableKnowledgeList[indexPath.row]
            
            let statusIcon = cell!.viewWithTag(201) as! UIImageView
            let nameLabel = cell!.viewWithTag(202) as! UILabel
            let progressBar = cell!.viewWithTag(203) as! UIProgressView
            let number = cell!.viewWithTag(204) as! UILabel
            
            var statusIconName = knowledge.level == 1 ? "hasIcon" : "noIcon"
            if statusIconName == "hasIcon"{
                cell!.backgroundColor = UIColor.backgroundGray()
                progressBar.progressTintColor = UIColor.mainColor()
                statusIconName = knowledge.isopen ? "knowledge_open" : "knowledge_close"
            }else{
                cell!.backgroundColor = UIColor.whiteColor()
                progressBar.progressTintColor = UIColor.buttonBlue()
                nameLabel.font = UIFont.systemFontOfSize(16)
            }
            statusIcon.image = UIImage(named: statusIconName)
            nameLabel.text = knowledge.name
            progressBar.progress = Float(knowledge.correctCount) / Float(knowledge.itemCount)
            number.text = "\(knowledge.correctCount)/\(knowledge.itemCount)"
        }
        return cell!
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        if tableStatus == 0{
            let tableViewCell = tableView.cellForRowAtIndexPath(indexPath)
            let knowledge = tableKnowledgeList[indexPath.row]
            let statusIcon = tableViewCell!.viewWithTag(201) as! UIImageView
            
            if knowledge.haschild && knowledge.level == 1{
                var indexPaths :[AnyObject] = []
                if knowledge.isopen == false{
                    for i in 0 ..< knowledge.children!.count {
                        let childKnowledge = knowledge.children![i]
                        tableKnowledgeList.insert(childKnowledge, atIndex: (indexPath.row + i + 1))
                        indexPaths.append(NSIndexPath(forRow: indexPath.row + i + 1, inSection: 0))
                    }
                    knowledge.isopen = true
                    statusIcon.image = UIImage(named: "knowledge_open")
                    tableView.insertRowsAtIndexPaths(indexPaths as! [NSIndexPath], withRowAnimation: UITableViewRowAnimation.Bottom)
                }else{
                    deleteRows = 1
                    indexPaths = remoteChild(knowledge,row: 0,indexPath: indexPath , indexPaths: indexPaths)
                    statusIcon.image = UIImage(named: "knowledge_close")
                    tableView.deleteRowsAtIndexPaths(indexPaths as! [NSIndexPath], withRowAnimation: UITableViewRowAnimation.Top)
                }
            }
        }
//        AlipayController().pay()
//        WXpayController().getSign()
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        cell.remoteBorder()
    }
    
    func remoteChild(knowledge : KnowledgeModel ,row : Int , indexPath : NSIndexPath, indexPaths: [AnyObject]) -> [AnyObject]{
        var indexPaths = indexPaths
        var row = row
        for i in 0 ..< knowledge.children!.count {
            row++
            let childKnowledge = knowledge.children![i]
            if childKnowledge.isopen == true{
                indexPaths = remoteChild(childKnowledge, row: row , indexPath: indexPath, indexPaths: indexPaths)
            }
            tableKnowledgeList.removeAtIndex(indexPath.row + 1)
            indexPaths.append(NSIndexPath(forRow: indexPath.row + deleteRows, inSection: 0))
            deleteRows++
        }
        knowledge.isopen = false
        return indexPaths
    }
}
