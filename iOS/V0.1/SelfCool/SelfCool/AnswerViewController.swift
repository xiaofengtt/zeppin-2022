//
//  AnswerViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AnswerViewController: UIViewController ,UIAlertViewDelegate ,UIScrollViewDelegate,HttpDataProtocol{
    
    @IBOutlet weak var titleView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var progressView: UIView!
    @IBOutlet weak var itemScrollView: UIScrollView!
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var submitButton: UIButton!
    @IBOutlet weak var exitButton: UIButton!
    
    let tagViewPaddingTop :CGFloat = 10
    let answerCardPaddingTop :CGFloat = 50
    let paddingLeft : CGFloat = 25
    let submitButtonHeight :CGFloat = screenHeight / 11
    let tagFontSize : CGFloat = 11
    let fontSize :CGFloat = 16
    let lineSpacing : CGFloat = 10
    let optionIconSize : CGFloat = 30
    let optionViewPadding : CGFloat = 30
    let optionPadding : CGFloat = 20
    let labelViewHeight :CGFloat = 30
    let selectViewHeight :CGFloat = 4
    let selectViewWidth :CGFloat = 6
    let optionLabelPaddingIcon : CGFloat = 10
    let paddingButtom : CGFloat = 20
    let contentFontName = "STHeitiSC-Medium"
    
    var loadingView : UIView?
    var httpController = HttpController()
    var paperId = 0
    var ssoUserTestId = 0
    var exitAlert = UIAlertView()
    var progressViews : [UIView] = []
    var answerCardButtons : [UIButton] = []
    var allOptionButtons : [[UIButton]] = []
    var itemScrollViewHeight : CGFloat = 0
    var progressHeight : CGFloat = 0
    var progressWidth : CGFloat = 0
    var selectView = UIImageView()
    var paragraphStyle = NSMutableParagraphStyle()
    var correctCount = 0

    override func viewDidLoad() {
        httpController.delegate = self
        itemScrollView.delegate = self
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: self, options: nil)
        loadingView = nibs.lastObject as? UIView
        loadingView!.frame = UIScreen.mainScreen().bounds
        self.view.addSubview(loadingView!)
        
        titleLabel.text = selectSubject!.name
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        exitAlert = UIAlertView(title: "亲爱的，坚持做完这几道吧!", message: "", delegate: self , cancelButtonTitle: "继续做", otherButtonTitles: "下次做")
        submitButton.setImage(UIImage(named: "answer_submit_highlight"), forState: UIControlState.Highlighted)
        backButton.addTarget(self, action: "exitButton:", forControlEvents: UIControlEvents.TouchUpInside)
        backButton.setImage(UIImage(named: "answer_exit_highlight"), forState: UIControlState.Highlighted)
        let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubject!.id) , "knowledge.id" : String(selectKnowledge!.id)])
        httpController.getNSDataByParams("getItemsByKnowledge", paramsDictionary: itemParams)
        
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
        if recieveType == "getItemsByKnowledge" {
            if data.length == 0{
                httpController.getDataError()
            }else{
                var dictionary = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableLeaves, error: nil) as! NSDictionary
                if dictionary.objectForKey("Status") as! String == "fail"{
                    let message = dictionary.objectForKey("Message") as! String
                    UIAlertView(title: "出题失败", message:message , delegate: self, cancelButtonTitle: "好").show()
                }else{
                    var paperDictionary = dictionary.objectForKey("Records") as! NSDictionary
                    ssoUserTestId = paperDictionary.objectForKey("ssoUserTest.id") as! Int
                    paperId = paperDictionary.objectForKey("paper.id") as! Int
                    var itemArray = paperDictionary.objectForKey("items")as! NSArray
                    var itemList : [ItemModel] = []
                    for index in 0 ..< itemArray.count{
                        var item = itemArray[index] as! NSDictionary
                        var itemModel = DictionaryToItem(item)
                        itemModel.index = index
                        itemList.append(itemModel)
                    }
                    globalItemList = itemList
                    showData()
                }
            }
        }else if recieveType == "submitAnswer"{
            if data.length == 0{
                httpController.getDataError()
            }else{
                self.navigationController?.navigationBarHidden = false
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewControllerWithIdentifier("answerResultViewController") as! AnswerResultViewController
                vc.data = data
                vc.correctCount = correctCount
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }

    func showData(){
        itemScrollViewHeight = itemScrollView.bounds.height
        progressHeight = progressView.bounds.height
        progressWidth = progressView.bounds.width / CGFloat(globalItemList.count)
        
        //进度条
        for index in 0 ..< globalItemList.count {
            var childView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, progressWidth, progressHeight))
            childView.backgroundColor = UIColor.backgroundGray()
            progressViews.append(childView)
            progressView.addSubview(childView)
            if index != 0{
                var breakView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, 1, progressHeight))
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
            switch globalItemList[index].modelType{
            case 24 :
                singleChioceShow(index)
            default :
                singleChioceShow(index)
            }
        }
    }
    
    func scrollViewDidEndScrollingAnimation(scrollView: UIScrollView) {
        scrollView.userInteractionEnabled = true
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((scrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
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
        selectView.frame = CGRectMake(progressWidth * CGFloat(page) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
    }
    
    func alertView(alertView: UIAlertView, didDismissWithButtonIndex buttonIndex: Int) {
        if alertView == exitAlert{
            if buttonIndex == 1{
                exitButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
                titleView.hidden = true
            }
        }else if alertView.title == "出题失败"{
            exitButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            titleView.hidden = true
        }
    }
    
    @IBAction func toSubmitButton(sender: UIButton) {
        itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.bounds.width * CGFloat(globalItemList.count), 0, itemScrollView.bounds.width, itemScrollView.bounds.height), animated: false)
    }
    
    func backButton(sender: UIButton) {
        itemScrollView.userInteractionEnabled = false
        itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.bounds.width * CGFloat(globalItemList.count - 1), 0, itemScrollView.bounds.width, itemScrollView.bounds.height), animated: true)
    }
    
    func exitButton(sender: UIButton) {
        exitAlert.show()
    }
    

}