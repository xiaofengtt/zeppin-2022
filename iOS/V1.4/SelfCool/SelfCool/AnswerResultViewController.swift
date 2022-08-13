//
//  AnswerResultViewControler.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/20.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AnswerResultViewController: UIViewController{
    
    @IBOutlet weak var answerAgainButton: UIButton!
    @IBOutlet weak var analysisButton: UIButton!
    @IBOutlet weak var resultScrollView: UIScrollView!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    var dataDictionary : NSDictionary?
    var correctCount : Int?
    var preparingProgress : Double?
    var paragraphStyle = NSMutableParagraphStyle()
    
    let correctRateViewHeight : CGFloat = 115
    let paddingLeft : CGFloat = 20
    let correctRateViewPaddingTop : CGFloat = 35
    let correctRateViewNumberSize : CGFloat = 64
    let answerCardPaddingTop : CGFloat = 25
    let abilityChangeViewPaddingTop : CGFloat = 10
    let abilityChangeCellHeight : CGFloat = 50
    let changeIconWidth : CGFloat = 16
    let changeValueWidth : CGFloat = 100
    let changeStatusWidth : CGFloat = 30
    let correctRateViewFontSize : CGFloat = 14
    let knowledgeChangeFontSize : CGFloat = 15
    let preparingProgressFontSize : CGFloat = 18
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        answerAgainButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Highlighted)
        analysisButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
        analysisButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainHighlightedColor()), forState: UIControlState.Highlighted)
        let recordsDictionary = dataDictionary!.objectForKey("Records") as! NSDictionary
        preparingProgress = recordsDictionary.objectForKey("progress") as! Double?
        let knoledgesData = recordsDictionary.objectForKey("changeKnowledges") as! NSArray
        knowledgeChangeList = []
        for index in 0 ..< knoledgesData.count{
            let knowledgeChange = knoledgesData[index] as! NSDictionary
            if knowledgeChange.objectForKey("changeFlag") as! Bool == true{
                let knowledgeChangeModel = KnowledgeChangeModel(dictionary: knowledgeChange)
                knowledgeChangeList.append(knowledgeChangeModel)
            }
        }
        resultScrollView.backgroundColor = UIColor.backgroundGray()
        
        var itemCount = 0
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].isGroup{
                itemCount += globalItemList[i].children!.count
            }else{
                itemCount++
            }
        }
        //正确率
        let correctRateView = UIView(frame: CGRectMake(0, 0, screenWidth, correctRateViewHeight))
        correctRateView.backgroundColor = UIColor.mainColor()
        let topLabel = UILabel(frame: CGRectMake(paddingLeft, correctRateViewPaddingTop, screenWidth - 2 * paddingLeft , correctRateViewFontSize))
        topLabel.font = UIFont.systemFontOfSize(correctRateViewFontSize)
        topLabel.textColor = UIColor.whiteColor()
        topLabel.text = "答对"
        correctRateView.addSubview(topLabel)
        
        let scoreLabel = UILabel(frame: CGRectMake(paddingLeft, correctRateViewPaddingTop - correctRateViewFontSize, screenWidth - 2 * paddingLeft, correctRateViewNumberSize))
        scoreLabel.font = UIFont.systemFontOfSize(correctRateViewNumberSize)
        scoreLabel.textColor = UIColor.whiteColor()
        scoreLabel.textAlignment = NSTextAlignment.Center
        scoreLabel.text = String(correctCount!)
        correctRateView.addSubview(scoreLabel)
        
        let bottomLabel = UILabel(frame: CGRectMake(paddingLeft, correctRateViewHeight - correctRateViewPaddingTop - correctRateViewFontSize, screenWidth - 2 * paddingLeft, correctRateViewFontSize))
        bottomLabel.font = UIFont.systemFontOfSize(correctRateViewFontSize)
        bottomLabel.textColor = UIColor.whiteColor()
        bottomLabel.textAlignment = NSTextAlignment.Right
        bottomLabel.text = "/\(itemCount)"
        correctRateView.addSubview(bottomLabel)
        resultScrollView.addSubview(correctRateView)
        
        //答题卡
        let answerCardView = UIView()
        answerCardView.backgroundColor = UIColor.whiteColor()
        
        let conlumns = 5
        let rows: Int = (itemCount + conlumns - 1 ) / conlumns
        let itemViewSize = (screenWidth - paddingLeft) / CGFloat(conlumns)
        
        let answerCardResultView = UIView(frame: CGRectMake(paddingLeft / 2 , answerCardPaddingTop, screenWidth - paddingLeft, itemViewSize * CGFloat(rows)))
        var itemIndex = 0
        for index in 0 ..< globalItemList.count{
            let item = globalItemList[index]
            
            if item.modelType == 4{
                for i in 0 ..< item.children!.count{
                    let row :Int = itemIndex / conlumns
                    let num :Int = itemIndex % conlumns
                    
                    let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize  * CGFloat(row) + itemViewSize / 6 , itemViewSize / 1.5, itemViewSize / 1.5))
                    itemButton.tag = ItemViewTag.resultItemButtonTagBase() + index + ItemViewTag.groupViewTagBase() * (i + 1)
                    itemButton.addTarget(self, action: "answerCardButton:", forControlEvents: UIControlEvents.TouchUpInside)
                    itemButton.setTitle(String(itemIndex+1), forState: UIControlState.Normal)
                    itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                    itemButton.layer.masksToBounds = true
                    itemButton.layer.cornerRadius = itemViewSize / 3
                    if globalItemList[index].children![i].answer.completeType == 1{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }else{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }
                    itemIndex++
                    answerCardResultView.addSubview(itemButton)
                }
            }else{
                let row :Int = itemIndex / conlumns
                let num :Int = itemIndex % conlumns
                
                let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize  * CGFloat(row) + itemViewSize / 6 , itemViewSize / 1.5, itemViewSize / 1.5))
                itemButton.tag = ItemViewTag.resultItemButtonTagBase() + index
                itemButton.addTarget(self, action: "answerCardButton:", forControlEvents: UIControlEvents.TouchUpInside)
                itemButton.setTitle(String(itemIndex+1), forState: UIControlState.Normal)
                itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                itemButton.layer.masksToBounds = true
                itemButton.layer.cornerRadius = itemViewSize / 3
                if globalItemList[index].answer.completeType == 1{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonBlue().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }else{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }
                itemIndex++
                answerCardResultView.addSubview(itemButton)
            }
        }
        answerCardView.addSubview(answerCardResultView)
        answerCardView.frame = CGRectMake(0, correctRateViewHeight, screenWidth, answerCardResultView.bounds.height + 2 * answerCardPaddingTop)
        resultScrollView.addSubview(answerCardView)
        
        //能力变化
        let abilityChangeView = UIView()
        let preparingProgressView = UIView(frame: CGRectMake(paddingLeft, abilityChangeViewPaddingTop, screenWidth - 2 * paddingLeft, abilityChangeCellHeight))
        
        let preparingProgressNameLabel = UILabel(frame: CGRectMake(0, (abilityChangeCellHeight - preparingProgressFontSize) / 2, (screenWidth - 2 * paddingLeft) / 2, preparingProgressFontSize))
        preparingProgressNameLabel.font = UIFont.systemFontOfSize(preparingProgressFontSize)
        preparingProgressNameLabel.text = "备考进度"
        preparingProgressNameLabel.textColor = UIColor.blackColor()
        preparingProgressView.addSubview(preparingProgressNameLabel)
        
        
        let preparingProgressChangeIcon = UIImageView(frame: CGRectMake(preparingProgressView.frame.width - changeIconWidth  , (preparingProgressView.frame.height - changeIconWidth) / 2, changeIconWidth , changeIconWidth))
        if Int(preparingProgress!) > Int(selectSubject!.preparingProgress){
            preparingProgressChangeIcon.image = UIImage(named: "ability_change_up")
        }else if Int(preparingProgress!) < Int(selectSubject!.preparingProgress){
            preparingProgressChangeIcon.image = UIImage(named: "ability_change_down")
        }else if Int(preparingProgress!) == Int(selectSubject!.preparingProgress){
            preparingProgressChangeIcon.image = UIImage(named: "ability_change_keep")
        }
        preparingProgressView.addSubview(preparingProgressChangeIcon)
        
        let preparingProgressChangeLabel = UILabel()
        preparingProgressChangeLabel.font = UIFont.systemFontOfSize(preparingProgressFontSize)
        preparingProgressChangeLabel.text = "\(Int(preparingProgress!))%"
        preparingProgressChangeLabel.textAlignment = NSTextAlignment.Right
        preparingProgressChangeLabel.frame = CGRectMake(preparingProgressView.frame.width - changeStatusWidth - changeValueWidth , (preparingProgressView.frame.height - preparingProgressFontSize) / 2, changeValueWidth , preparingProgressFontSize)
        if Int(preparingProgress!) > Int(selectSubject!.preparingProgress){
            preparingProgressChangeLabel.textColor = UIColor.mainColor()
        }else if Int(preparingProgress!) < Int(selectSubject!.preparingProgress){
            preparingProgressChangeLabel.textColor = UIColor.wrongColor()
        }
        preparingProgressView.addSubview(preparingProgressChangeLabel)
        abilityChangeView.addSubview(preparingProgressView)
        
        
        //知识点变化
        let knowledgeChangeView = UIView(frame: CGRectMake(paddingLeft, abilityChangeViewPaddingTop + abilityChangeCellHeight, screenWidth - 2 * paddingLeft, abilityChangeCellHeight * CGFloat(knowledgeChangeList.count)))
        for index in 0 ..< knowledgeChangeList.count{
            let knowledgeChange = knowledgeChangeList[index]
            let knowledgeChangeCell = UIView(frame: CGRectMake(0, abilityChangeCellHeight * CGFloat(index) , screenWidth - 2 * paddingLeft, abilityChangeCellHeight))
            
            let spaceLine = UIView (frame: CGRectMake(0, 0, knowledgeChangeCell.frame.width , 1))
            spaceLine.backgroundColor = UIColor.spaceLineGray()
            knowledgeChangeCell.addSubview(spaceLine)
            
            let knowledgeChangeNameLabel = UILabel(frame: CGRectMake(0, (abilityChangeCellHeight - knowledgeChangeFontSize) / 2, knowledgeChangeCell.frame.width / 2 , knowledgeChangeFontSize))
            knowledgeChangeNameLabel.textColor = UIColor.blackColor()
            knowledgeChangeNameLabel.font = UIFont.systemFontOfSize(knowledgeChangeFontSize)
            knowledgeChangeNameLabel.text = knowledgeChange.name
            knowledgeChangeCell.addSubview(knowledgeChangeNameLabel)
            
            let knowledgeItemChangeLabel = UILabel()
            if knowledgeChange.changeValue >= 0 {
                knowledgeItemChangeLabel.text = "+\(knowledgeChange.changeValue)"
                knowledgeItemChangeLabel.textColor = UIColor.mainColor()
            }else{
                knowledgeItemChangeLabel.text = String(knowledgeChange.changeValue)
                knowledgeItemChangeLabel.textColor = UIColor.wrongColor()
            }
            knowledgeItemChangeLabel.font = UIFont.systemFontOfSize(knowledgeChangeFontSize)
            knowledgeItemChangeLabel.sizeToFit()
            knowledgeItemChangeLabel.frame = CGRectMake(knowledgeChangeCell.frame.width - knowledgeItemChangeLabel.frame.width, (abilityChangeCellHeight - knowledgeItemChangeLabel.frame.height) / 2, knowledgeItemChangeLabel.frame.width, knowledgeItemChangeLabel.frame.height)
            knowledgeChangeCell.addSubview(knowledgeItemChangeLabel)
            
            let knowledgeChangeDescriptionLabel = UILabel()
            knowledgeChangeDescriptionLabel.font = UIFont.systemFontOfSize(knowledgeChangeFontSize)
            knowledgeChangeDescriptionLabel.text = "掌握题"
            knowledgeChangeDescriptionLabel.textAlignment = NSTextAlignment.Right
            knowledgeChangeDescriptionLabel.frame = CGRectMake(knowledgeChangeCell.frame.width - changeStatusWidth - changeValueWidth , (abilityChangeCellHeight - knowledgeChangeFontSize)/2 , changeValueWidth , knowledgeChangeFontSize)
            knowledgeChangeCell.addSubview(knowledgeChangeDescriptionLabel)
            knowledgeChangeView.addSubview(knowledgeChangeCell)
        }
        abilityChangeView.addSubview(knowledgeChangeView)
        abilityChangeView.frame = CGRectMake(0, correctRateViewHeight + answerCardView.frame.height, screenWidth, preparingProgressView.frame.height + knowledgeChangeView.frame.height)
        resultScrollView.addSubview(abilityChangeView)
        let topBounceView = UIView(frame: CGRectMake(0, -500, screenWidth, 500))
        topBounceView.backgroundColor = UIColor.mainColor()
        resultScrollView.addSubview(topBounceView)
        resultScrollView.contentSize = CGSize(width: screenWidth, height: correctRateView.frame.height + answerCardView.frame.height + abilityChangeView.frame.height)
        
        if !AchieveController.progressControl(self, oldProgress: selectSubject!.preparingProgress, newProgress: preparingProgress!){
            AchieveController.answerResultControl(self, rightCount: correctCount!, totalCount: itemCount)
        }
        UpdateGlobalData(recordsDictionary)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("AnswerResult")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("AnswerResult")
    }
    
    @IBAction func answerAgainButton(sender: UIButton) {
        MobClick.event("AnswerAgain")
    }
    
    func answerCardButton(sender:UIButton) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("standardItemAnalysisViewController") as!  StandardItemAnalysisViewController
        vc.itemIndex = sender.tag
        self.navigationController?.pushViewController(vc, animated: true)
    }
}