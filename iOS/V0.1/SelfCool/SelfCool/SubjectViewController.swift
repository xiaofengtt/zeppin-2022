//
//  SubjectViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/17.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class SubjectViewController: UIViewController , UITableViewDataSource , UITableViewDelegate , HttpDataProtocol{
    
    @IBOutlet weak var navigationBar: UINavigationItem!
    @IBOutlet weak var knowledgeTableView: UITableView!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    var userRankImageView: UIImageView!
    var answerCountView: UIView!
    var correctRateView: UIView!
    var progressView: UIView!
    var doneItemsCountLabel: UILabel!
    var correctRateLabel: UILabel!
    var examCutDownLabel: UILabel!
    var userRankLabel: UILabel!
    var userRankLevelLabel: UILabel!
    
    var isChangeSubject : Bool?
    var tableKnowledgeList : [KnowledgeModel] = []
    var rateImageView : ProgressCurcuitView?
    var percentSignView : UILabel?
    var progressNumberLabel : UILabel?
    var loadingView : UIView?
    var sugueKnowledge = KnowledgeModel()
    var httpController = HttpController()
    var deleteRows = 1
    
    var headView : UIView?
    
    let circularBorderWidth : CGFloat = 1
    let progressBorderPadding : CGFloat = 4
    let progressBorderWidth : CGFloat = 2
    let progressFontSize : CGFloat = 10
    let progressViewWidth : CGFloat = screenWidth / 3
    let progressNumberSize : CGFloat = screenWidth / 7
    let percentSignSize : CGFloat = screenWidth / 20
    
    override func viewDidLoad() {
        httpController.delegate = self
        knowledgeTableView.delegate = self
        knowledgeTableView.dataSource = self
        
        if knowledgeTableView.respondsToSelector(Selector("separatorInset")){
            knowledgeTableView.separatorInset = UIEdgeInsetsZero
        }
        if knowledgeTableView.respondsToSelector(Selector("layoutMargins")){
            knowledgeTableView.layoutMargins = UIEdgeInsetsZero
        }
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: self, options: nil)
        loadingView = nibs.lastObject as? UIView
        loadingView!.frame = UIScreen.mainScreen().bounds
        if globalKnowledgeList.count == 0 || isChangeSubject == true{
            self.view.addSubview(loadingView!)
        
            let knowledgeParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" :String(selectSubject!.id)])
            httpController.getNSDataByParams("getKnowledges", paramsDictionary: knowledgeParams)
        }else{
            for i in 0 ..< globalKnowledgeList.count{
                globalKnowledgeList[i].isopen = false
            }
            tableKnowledgeList = globalKnowledgeList
            self.knowledgeTableView.reloadData()
        }
        navigationBar.title = selectSubject!.name
        
        let headNibs : NSArray = NSBundle.mainBundle().loadNibNamed("KnowledgeTableHeadView", owner: self, options: nil)
        headView = headNibs.lastObject as? UIView
        headView!.frame = CGRectMake(0, 0, screenWidth, screenWidth / 320 * 185)
        userRankImageView = headView!.viewWithTag(601) as! UIImageView
        answerCountView = headView!.viewWithTag(602) as UIView?
        correctRateView = headView!.viewWithTag(603) as UIView?
        progressView = headView!.viewWithTag(604) as UIView?
        doneItemsCountLabel = headView!.viewWithTag(605) as! UILabel
        correctRateLabel = headView!.viewWithTag(606) as! UILabel
        examCutDownLabel = headView!.viewWithTag(607) as! UILabel
        userRankLabel = headView!.viewWithTag(608) as! UILabel
        userRankLevelLabel = headView!.viewWithTag(609) as! UILabel
        
        examCutDownLabel.text = String(selectSubject!.countDown)
        doneItemsCountLabel.text = String(selectSubject!.doneItemCount)
        correctRateLabel.text = "\(Int(selectSubject!.correctRate))%"
        userRankLabel.text = "\(Int(100.0 - selectSubject!.rankingRate))%"
        if selectSubject!.rankingRate <= 25{
            userRankLevelLabel.text = "的考生,笑到最后!"
        }else if selectSubject!.rankingRate <= 50{
            userRankLevelLabel.text = "的考生,就要胜利了!"
        }else if selectSubject!.rankingRate <= 75 {
            userRankLevelLabel.text = "的考生,不要停!"
        }
        if selectSubject!.preparingProgress >= 75{
            userRankImageView.image = UIImage(named: "subject_rank_4")
        }else if selectSubject!.preparingProgress >= 50{
            userRankImageView.image = UIImage(named: "subject_rank_3")
        }else if selectSubject!.preparingProgress >= 25{
            userRankImageView.image = UIImage(named: "subject_rank_2")
        }
        knowledgeTableView.tableFooterView = UIView()
        knowledgeTableView.backgroundColor = UIColor.backgroundGray()
        //画圆
        correctRateView.layer.masksToBounds = true
        correctRateView.layer.cornerRadius = screenWidth / 10
        correctRateView.layer.borderColor = UIColor.whiteColor().CGColor
        correctRateView.layer.borderWidth = circularBorderWidth
        answerCountView.layer.masksToBounds = true
        answerCountView.layer.cornerRadius = screenWidth / 10
        answerCountView.layer.borderColor = UIColor.whiteColor().CGColor
        answerCountView.layer.borderWidth = circularBorderWidth
        
        //进度条
        var progressBackgroundView = UIView(frame: CGRectMake(progressBorderPadding, progressBorderPadding, progressViewWidth - 2 * progressBorderPadding, progressViewWidth - 2 * progressBorderPadding))
        progressBackgroundView.layer.masksToBounds = true
        progressBackgroundView.layer.cornerRadius = (progressViewWidth - 2 * progressBorderPadding ) / 2
        progressBackgroundView.layer.borderColor = UIColor(red: 1, green: 1, blue: 1, alpha: 0.5).CGColor
        progressBackgroundView.layer.borderWidth = progressBorderWidth
        progressView.addSubview(progressBackgroundView)
        
        rateImageView = ProgressCurcuitView(frame:CGRectMake(0, 0, progressViewWidth, progressViewWidth))
        rateImageView!.backgroundColor = UIColor.transparentColor()
        var endAngle :CGFloat = CGFloat(selectSubject!.preparingProgress / 100) * 360
        rateImageView!.setEndAngle(endAngle)
        progressView.addSubview(rateImageView!)
        
        progressNumberLabel = UILabel()
        progressNumberLabel!.textColor = UIColor.whiteColor()
        progressNumberLabel!.textAlignment = NSTextAlignment.Center
        progressNumberLabel!.text = String(Int(selectSubject!.preparingProgress))
        progressNumberLabel!.font = UIFont.systemFontOfSize(progressNumberSize)
        progressNumberLabel!.sizeToFit()
        progressNumberLabel!.frame = CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 , (progressViewWidth - progressNumberSize - percentSignSize) / 2, progressNumberLabel!.frame.width, progressNumberLabel!.frame.height)
        progressView.addSubview(progressNumberLabel!)
        
        percentSignView = UILabel(frame: CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 + progressNumberLabel!.frame.width,  (progressViewWidth - progressNumberSize - percentSignSize) / 2 + progressNumberLabel!.frame.height / 2 , percentSignSize, percentSignSize))
        percentSignView!.font = UIFont.systemFontOfSize(percentSignSize)
        percentSignView!.textColor = UIColor.whiteColor()
        percentSignView!.text = "%"
        progressView.addSubview(percentSignView!)
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
    
    func recieveDataResults(recieveType: String,data: NSData , inputParam : NSDictionary) {
        if recieveType == "getKnowledges" {
            if data.length == 0{
                httpController.getDataError()
            }else{
                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
                var knowledgeArray = dictionary.objectForKey("Records") as! NSArray
                var knowledgeList : [KnowledgeModel] = []
                for index in 0 ..< knowledgeArray.count{
                    var knowledge = knowledgeArray[index] as! NSDictionary
                    var knowledgeModel = DictionaryToKnowledge(knowledge)
                    knowledgeList.append(knowledgeModel)
                }
                globalKnowledgeList = knowledgeList
                tableKnowledgeList = globalKnowledgeList
                self.knowledgeTableView.reloadData()
            }
        }
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    
    func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return headView
    }
    
    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return screenWidth / 320 * 185
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return tableKnowledgeList.count
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return screenHeight / 7
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        var knowledgeCell = self.knowledgeTableView.dequeueReusableCellWithIdentifier("knowledgeCell") as! UITableViewCell
        
        let knowledge = tableKnowledgeList[indexPath.row]
        
        var statusIcon = knowledgeCell.viewWithTag(201) as! UIImageView
        var nameLabel = knowledgeCell.viewWithTag(202) as! UILabel
        var progressBar = knowledgeCell.viewWithTag(203) as! UIProgressView
        var number = knowledgeCell.viewWithTag(204) as! UILabel
        
        var statusIconName = knowledge.level == 1 ? "hasIcon" : "noIcon"
        if statusIconName == "hasIcon"{
            knowledgeCell.backgroundColor = UIColor.backgroundGray()
            progressBar.progressTintColor = UIColor.mainColor()
            statusIconName = knowledge.isopen ? "knowledge_open" : "knowledge_close"
        }else{
            knowledgeCell.backgroundColor = UIColor.whiteColor()
            progressBar.progressTintColor = UIColor.buttonBlue()
        }
        statusIcon.image = UIImage(named: statusIconName)
        nameLabel.text = knowledge.name
        progressBar.progress = Float(knowledge.correctCount) / Float(knowledge.itemCount)
        number.text = "\(knowledge.correctCount)/\(knowledge.itemCount)"
        
        return knowledgeCell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        
        var tableViewCell = tableView.cellForRowAtIndexPath(indexPath)
        var knowledge = tableKnowledgeList[indexPath.row]
        var statusIcon = tableViewCell!.viewWithTag(201) as! UIImageView
        
        if knowledge.haschild == true && knowledge.level == 1{
            var indexPaths :[AnyObject] = []
            if knowledge.isopen == false{
                for i in 0 ..< knowledge.children!.count {
                    var childKnowledge = knowledge.children![i]
                    tableKnowledgeList.insert(childKnowledge, atIndex: (indexPath.row + i + 1))
                    indexPaths.append(NSIndexPath(forRow: indexPath.row + i + 1, inSection: 0))
                }
                knowledge.isopen = true
                statusIcon.image = UIImage(named: "knowledge_open")
                tableView.insertRowsAtIndexPaths(indexPaths, withRowAnimation: UITableViewRowAnimation.Bottom)
            }else{
                deleteRows = 1
                indexPaths = remoteChild(knowledge,row: 0,indexPath: indexPath , indexPaths: indexPaths)
                statusIcon.image = UIImage(named: "knowledge_close")
                tableView.deleteRowsAtIndexPaths(indexPaths, withRowAnimation: UITableViewRowAnimation.Top)
            }
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
    
    func remoteChild(knowledge : KnowledgeModel ,row : Int , indexPath : NSIndexPath, indexPaths: [AnyObject]) -> [AnyObject]{
        var indexPaths = indexPaths
        var row = row
        for i in 0 ..< knowledge.children!.count {
            row++
            var childKnowledge = knowledge.children![i]
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
    
    @IBAction func beginAnswer(sender: UIButton) {
        var tableViewCell : UITableViewCell
        if(IOSVersion >= 8.0){
            tableViewCell = sender.superview?.superview as! UITableViewCell
        }else{
            tableViewCell = sender.superview?.superview?.superview as! UITableViewCell
        }
        var indexPath = knowledgeTableView.indexPathForCell(tableViewCell)
        selectKnowledge = tableKnowledgeList[indexPath!.row]
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        navigationController?.navigationBarHidden = false
        examCutDownLabel.text = String(selectSubject!.countDown)
        doneItemsCountLabel.text = String(selectSubject!.doneItemCount)
        correctRateLabel.text = "\(Int(selectSubject!.correctRate))%"
        userRankLabel.text = "\(Int(100.0 - selectSubject!.rankingRate))%"
        if selectSubject!.rankingRate <= 25{
            userRankLevelLabel.text = "的考生,笑到最后!"
        }else if selectSubject!.rankingRate <= 50{
            userRankLevelLabel.text = "的考生,就要胜利了!"
        }else if selectSubject!.rankingRate <= 75 {
            userRankLevelLabel.text = "的考生,不要停!"
        }
        if selectSubject!.preparingProgress >= 75{
            userRankImageView.image = UIImage(named: "subject_rank_4")
        }else if selectSubject!.preparingProgress >= 50{
            userRankImageView.image = UIImage(named: "subject_rank_3")
        }else if selectSubject!.preparingProgress >= 25{
            userRankImageView.image = UIImage(named: "subject_rank_2")
        }
        
        var endAngle :CGFloat = CGFloat(selectSubject!.preparingProgress / 100) * 360
        rateImageView!.removeFromSuperview()
        rateImageView = ProgressCurcuitView(frame:CGRectMake(0, 0, progressViewWidth, progressViewWidth))
        rateImageView!.backgroundColor = UIColor.transparentColor()
        rateImageView!.setEndAngle(endAngle)
        progressView.addSubview(rateImageView!)
        progressNumberLabel!.text = String(Int(selectSubject!.preparingProgress))
        progressNumberLabel!.sizeToFit()
        progressNumberLabel!.frame = CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 , (progressViewWidth - progressNumberSize - percentSignSize) / 2, progressNumberLabel!.frame.width, progressNumberLabel!.frame.height)
        percentSignView!.frame = CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 + progressNumberLabel!.frame.width,  (progressViewWidth - progressNumberSize - percentSignSize) / 2 + progressNumberLabel!.frame.height / 2 , percentSignSize, percentSignSize)
        progressView!.reloadInputViews()
        knowledgeTableView.reloadData()
    }
    
}