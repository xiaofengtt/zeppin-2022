//
//  StandardItemAnswerViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class StandardItemAnswerViewController: UIViewController ,UIAlertViewDelegate ,UIScrollViewDelegate,HttpDataProtocol{
    
    @IBOutlet weak var titleView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var progressView: UIView!
    @IBOutlet weak var itemScrollView: UIScrollView!
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var submitButton: UIButton!
    @IBOutlet weak var exitButton: UIButton!
    
    let answerCardPaddingTop :CGFloat = 50
    let paddingLeft : CGFloat = 25
    let submitButtonHeight :CGFloat = screenHeight / 11
    let fontSize :CGFloat = 16
    let optionIconSize : CGFloat = 30
    let optionPadding : CGFloat = 20
    let selectViewHeight :CGFloat = 4
    let selectViewWidth :CGFloat = 6
    let optionLabelPaddingIcon : CGFloat = 10
    let optionViewPaddingBottom : CGFloat = 30
    let lineSpacing : CGFloat = 10
    
    var loadingView : UIView?
    var httpController = HttpController()
    var paperId = 0
    var ssoUserTestId = 0
    var exitAlert = UIAlertView()
    var progressViews : [UIView] = []
    var answerCardButtons : [UIButton] = []
    var allOptionButtons : [[UIButton]] = []
    var answerCardSubmitButton : UIButton?
    var itemScrollViewHeight : CGFloat = 0
    var progressHeight : CGFloat = 0
    var progressWidth : CGFloat = 0
    var selectView = UIImageView()
    var paragraphStyle = NSMutableParagraphStyle()
    var correctCount = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        itemScrollView.delegate = self
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        titleLabel.text = selectSubject!.name
        exitAlert = UIAlertView(title: "亲爱的，坚持做完这几道吧!", message: "", delegate: self , cancelButtonTitle: "继续做", otherButtonTitles: "不做了")
        backButton.addTarget(self, action: "exitButton:", forControlEvents: UIControlEvents.TouchUpInside)
        backButton.setImage(UIImage(named: "answer_exit_highlight"), forState: UIControlState.Highlighted)
        if selectKnowledge!.id != 0 {
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubject!.id) , "knowledge.id" : String(selectKnowledge!.id)])
            httpController.getNSDataByParams("getItemsByKnowledge", paramsDictionary: itemParams)
        }else{
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubject!.id)])
            httpController.getNSDataByParams("getItemsByKnowledge", paramsDictionary: itemParams)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("StandardItemAnswer")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("StandardItemAnswer")
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(nil)
        loadingView?.removeFromSuperview()
        answerCardSubmitButton?.userInteractionEnabled = true
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary , inputParam : NSDictionary) {
        if recieveType == "getItemsByKnowledge" {
            if dataDictionary.objectForKey("Status") as! String == "fail"{
                let message = dataDictionary.objectForKey("Message") as! String
                UIAlertView(title: "出题失败", message:message , delegate: self, cancelButtonTitle: "好").show()
            }else{
                let paperDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                ssoUserTestId = paperDictionary.objectForKey("ssoUserTest.id") as! Int
                paperId = paperDictionary.objectForKey("paper.id") as! Int
                let itemArray = paperDictionary.objectForKey("items")as! NSArray
                var itemList : [ItemModel] = []
                for index in 0 ..< itemArray.count{
                    let item = itemArray[index] as! NSDictionary
                    let itemModel = ItemModel(dictionary: item)
                    itemModel.index = index
                    if itemModel.isGroup{
                        for i in 0 ..< itemModel.children!.count{
                            itemModel.children![i].parentIndex = index
                        }
                    }
                    itemList.append(itemModel)
                }
                globalItemList = itemList
                showData()
            }
        }else if recieveType == "submitAnswer"{
            self.navigationController?.navigationBarHidden = false
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewControllerWithIdentifier("answerResultViewController") as! AnswerResultViewController
            vc.dataDictionary = dataDictionary
            vc.correctCount = correctCount
            self.navigationController?.pushViewController(vc, animated: true)
        }
        loadingView?.removeFromSuperview()
    }
    
    func showData(){
        itemScrollViewHeight = itemScrollView.bounds.height
        progressHeight = progressView.bounds.height
        
        var itemNum = 0
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].isGroup{
                itemNum += globalItemList[i].children!.count
            }else{
                itemNum++
            }
        }
        progressWidth = progressView.bounds.width / CGFloat(itemNum)
        //进度条
        for index in 0 ..< itemNum {
            let childView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, progressWidth, progressHeight))
            childView.backgroundColor = UIColor.backgroundGray()
            progressViews.append(childView)
            progressView.addSubview(childView)
            if index != 0{
                let breakView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, 1, progressHeight))
                breakView.backgroundColor = UIColor.spaceLineGray()
                progressView.addSubview(breakView)
            }
        }
        selectView = UIImageView(frame: CGRectMake((progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight))
        selectView.image = UIImage(named: "item_select_true")
        progressView.addSubview(selectView)
        
        //答题卡
        showAnswerCard()
        
        //试题
        itemScrollView.contentSize = CGSize(width: screenWidth * CGFloat(globalItemList.count + 1), height: itemScrollViewHeight)
        for index in 0 ..< globalItemList.count{
            if globalItemList[index].modelType == 1{
                singleChoiceAnswer(index)
            }else if globalItemList[index].modelType == 3{
                judgeAnswer(index)
            }else if globalItemList[index].modelType == 5{
                multipleChoiceAnswer(index)
            }else{
                groupAnswer(index)
            }
        }
    }
    
    func scrollViewDidEndScrollingAnimation(scrollView: UIScrollView) {
        self.view.userInteractionEnabled = true
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var index = 0
        for i in 0 ..< page{
            if globalItemList[i].isGroup{
                index += globalItemList[i].children!.count
            }else{
                index++
            }
        }
        if page < globalItemList.count{
            if globalItemList[page].isGroup{
                let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
                index += childPage
            }
        }
        if scrollView == itemScrollView{
            if page == globalItemList.count{
                titleLabel.text = "答题卡"
                submitButton.hidden = true
                backButton.setImage(UIImage(named: "back_icon"), forState: UIControlState.Normal)
                backButton.setImage(UIImage(named: "back_icon_highlight"), forState: UIControlState.Highlighted)
                backButton.removeTarget(self, action: "exitButton", forControlEvents: UIControlEvents.TouchUpInside)
                backButton.addTarget(self, action: "backButton:", forControlEvents: UIControlEvents.TouchUpInside)
                progressView.hidden = true
            }else{
                titleLabel.text = selectSubject!.name
                submitButton.hidden = false
                backButton.setImage(UIImage(named: "answer_exit"), forState: UIControlState.Normal)
                backButton.setImage(UIImage(named: "answer_exit_highlight"), forState: UIControlState.Highlighted)
                backButton.removeTarget(self, action: "backButton", forControlEvents: UIControlEvents.TouchUpInside)
                backButton.addTarget(self, action: "exitButton:", forControlEvents: UIControlEvents.TouchUpInside)
                progressView.hidden = false
            }
        }
        UIView.beginAnimations("move", context: nil)
        UIView.setAnimationDuration(0.3)
        selectView.frame = CGRectMake(progressWidth * CGFloat(index) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        UIView.commitAnimations()
    }
    
    func alertView(alertView: UIAlertView, didDismissWithButtonIndex buttonIndex: Int) {
        if alertView == exitAlert{
            if buttonIndex == 1{
                MobClick.event("ExitAnswer")
                exitButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
                titleView.hidden = true
            }
        }else if alertView.title == "出题失败"{
            exitButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            titleView.hidden = true
        }
    }
    
    @IBAction func toSubmitButton(sender: UIButton) {
        itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(globalItemList.count), 0, screenWidth, itemScrollView.bounds.height), animated: false)
    }
    
    func backButton(sender: UIButton) {
        itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(globalItemList.count - 1), 0, screenWidth, itemScrollView.bounds.height), animated: true)
    }
    
    func exitButton(sender: UIButton) {
        exitAlert.show()
    }
    

}