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
    @IBOutlet weak var knowledgeListImageView: UIImageView!
    @IBOutlet weak var knowledgeListLabel: UILabel!
    @IBOutlet weak var combinedListImageView: UIImageView!
    @IBOutlet weak var combinedListLabel: UILabel!
    
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
    var httpController = HttpController()
    var deleteRows = 1
    var headView : UIView?
    var tableStatus : Int?
    
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
        
        knowledgeTableView.remoteBorder()        
        loadingView = LoadingView(self)
        if globalKnowledgeList.count == 0 || isChangeSubject == true{
            self.view.addSubview(loadingView!)
            let knowledgeParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" :String(selectSubject!.id)])
            httpController.getNSDataByParams("getKnowledges", paramsDictionary: knowledgeParams)
            let getItemTypeParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" :String(selectSubject!.id) , "isStandard" :String(0)])
            httpController.getNSDataByParams("getItemTypes", paramsDictionary: getItemTypeParams)
        }else{
            for i in 0 ..< globalKnowledgeList.count{
                globalKnowledgeList[i].isopen = false
            }
            tableKnowledgeList = globalKnowledgeList
            self.knowledgeTableView.reloadData()
        }
        navigationBar.title = selectSubject!.name
        tableStatus = 0
        
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
        userRankLevelLabel.text = self.getUserRankWord(selectSubject!.rankingRate)
        userRankImageView.image = self.getUserBackgroundImage(selectSubject!.preparingProgress)
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
        
        //进度
        var progressBackgroundView = UIView(frame: CGRectMake(progressBorderPadding, progressBorderPadding, progressViewWidth - 2 * progressBorderPadding, progressViewWidth - 2 * progressBorderPadding))
        progressBackgroundView.layer.masksToBounds = true
        progressBackgroundView.layer.cornerRadius = (progressViewWidth - 2 * progressBorderPadding ) / 2
        progressBackgroundView.layer.borderColor = UIColor(red: 1, green: 1, blue: 1, alpha: 0.5).CGColor
        progressBackgroundView.layer.borderWidth = progressBorderWidth
        progressView.addSubview(progressBackgroundView)
        
        rateImageView = ProgressCurcuitView(frame:CGRectMake(0, 0, progressViewWidth, progressViewWidth))
        rateImageView!.backgroundColor = UIColor.clearColor()
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
        
        var progressWordLabel = UILabel()
        progressWordLabel.text = "备考进度"
        progressWordLabel.font = UIFont(name: "STHeitiSC-Medium", size: 12)
        progressWordLabel.textAlignment = NSTextAlignment.Center
        progressWordLabel.textColor = UIColor.whiteColor()
        progressWordLabel.sizeToFit()
        progressWordLabel.frame = CGRectMake(0, progressViewWidth * 0.8 -  progressWordLabel.frame.height, progressViewWidth, progressWordLabel.frame.height)
        progressView.addSubview(progressWordLabel)
        
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary , inputParam : NSDictionary) {
        if recieveType == "getKnowledges" {
            var knowledgeArray = dataDictionary.objectForKey("Records") as! NSArray
            var knowledgeList : [KnowledgeModel] = []
            for index in 0 ..< knowledgeArray.count{
                var knowledge = knowledgeArray[index] as! NSDictionary
                var knowledgeModel = KnowledgeModel(dictionary: knowledge)
                knowledgeList.append(knowledgeModel)
            }
            globalKnowledgeList = knowledgeList
            tableKnowledgeList = globalKnowledgeList
            self.knowledgeTableView.reloadData()
            loadingView?.removeFromSuperview()
        }
        else if recieveType == "getItemTypes"{
            var itemTpyeArray = dataDictionary.objectForKey("Records") as! NSArray
            var itemTypeList : [ItemTypeModel] = []
            for index in 0 ..< itemTpyeArray.count{
                var itemType = itemTpyeArray[index] as! NSDictionary
                var itemTypeModel = ItemTypeModel(dictionary: itemType)
                itemTypeList.append(itemTypeModel)
            }
            for index in 0 ..< itemTypeList.count{
                itemTypeList[index].progressColor = ItemTypeModel.getProgressColor(index)
            }
            globalItemTypeList = itemTypeList
        }
    }
    
    @IBAction func toKnowledgeTree(sender: UIButton) {
        knowledgeTableView.backgroundColor = UIColor.backgroundGray()
        knowledgeListImageView.image = UIImage(named: "knowledge_list_selected")
        knowledgeListLabel.textColor = UIColor.mainColor()
        combinedListImageView.image = UIImage(named: "combined_list_unselected")
        combinedListLabel.textColor = UIColor.darkGrayColor()
        tableStatus = 0
        knowledgeTableView.reloadData()
    }
    
    @IBAction func toCombinedList(sender: UIButton) {
        knowledgeTableView.backgroundColor = UIColor.whiteColor()
        knowledgeListImageView.image = UIImage(named: "knowledge_list_unselected")
        knowledgeListLabel.textColor = UIColor.darkGrayColor()
        combinedListImageView.image = UIImage(named: "combined_list_selected")
        combinedListLabel.textColor = UIColor.mainColor()
        tableStatus = 1
        knowledgeTableView.reloadData()
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
    
    @IBAction func randomAnswer(sender: UIButton) {
        selectKnowledge = KnowledgeModel()
    }
    
    @IBAction func unstandardAnswer(sender: UIButton) {
        var tableViewCell : UITableViewCell
        if(IOSVersion >= 8.0){
            tableViewCell = sender.superview?.superview?.superview?.superview as! UITableViewCell
        }else{
            tableViewCell = sender.superview?.superview?.superview?.superview?.superview as! UITableViewCell
        }
        var indexPath = knowledgeTableView.indexPathForCell(tableViewCell)
        selectItemType = globalItemTypeList[indexPath!.row]
    }
    
    @IBAction func close(segue: UIStoryboardSegue){
        navigationController?.navigationBarHidden = false
        examCutDownLabel.text = String(selectSubject!.countDown)
        doneItemsCountLabel.text = String(selectSubject!.doneItemCount)
        correctRateLabel.text = "\(Int(selectSubject!.correctRate))%"
        userRankLabel.text = "\(Int(100.0 - selectSubject!.rankingRate))%"
        userRankLevelLabel.text = self.getUserRankWord(selectSubject!.rankingRate)
        userRankImageView.image = self.getUserBackgroundImage(selectSubject!.preparingProgress)
        
        var endAngle :CGFloat = CGFloat(selectSubject!.preparingProgress / 100) * 360
        rateImageView!.removeFromSuperview()
        rateImageView = ProgressCurcuitView(frame:CGRectMake(0, 0, progressViewWidth, progressViewWidth))
        rateImageView!.backgroundColor = UIColor.clearColor()
        rateImageView!.setEndAngle(endAngle)
        progressView.addSubview(rateImageView!)
        progressNumberLabel!.text = String(Int(selectSubject!.preparingProgress))
        progressNumberLabel!.sizeToFit()
        progressNumberLabel!.frame = CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 , (progressViewWidth - progressNumberSize - percentSignSize) / 2, progressNumberLabel!.frame.width, progressNumberLabel!.frame.height)
        percentSignView!.frame = CGRectMake((progressViewWidth - (progressNumberLabel!.frame.width + percentSignSize)) / 2 + progressNumberLabel!.frame.width,  (progressViewWidth - progressNumberSize - percentSignSize) / 2 + progressNumberLabel!.frame.height / 2 , percentSignSize, percentSignSize)
        progressView!.reloadInputViews()
        knowledgeTableView.reloadData()
    }
    
    func getUserBackgroundImage(progress : Double) -> UIImage{
        if progress >= 75{
            return UIImage(named: "subject_rank_4")!
        }else if progress >= 50{
            return UIImage(named: "subject_rank_3")!
        }else if progress >= 25{
            return UIImage(named: "subject_rank_2")!
        }else{
            return UIImage(named: "subject_rank_1")!
        }
    }
    
    func getUserRankWord(rankRate : Double) -> String{
        if rankRate <= 25{
            return "的考生,笑到最后!"
        }else if rankRate <= 50{
            return "的考生,就要胜利了!"
        }else if rankRate <= 75 {
            return "的考生,不要停!"
        }else{
            return "的考生,加油啊亲!"
        }
    }
}