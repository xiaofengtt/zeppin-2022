//
//  StandardItemAnalysisViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/23.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class StandardItemAnalysisViewController: UIViewController,UIScrollViewDelegate{
    
    @IBOutlet weak var titleView: UIView!
    @IBOutlet weak var progressView: UIView!
    @IBOutlet weak var analysisScrollView: UIScrollView!
    @IBOutlet weak var analysisFailScrollView: UIScrollView!
    @IBOutlet weak var switchView: UIView!
    @IBOutlet weak var allItemsButton: UIButton!
    @IBOutlet weak var wrongItemButton: UIButton!
    
    var itemIndex :Int?
    
    let paddingLeft : CGFloat = 25
    let fontSize :CGFloat = 16
    let lineSpacing : CGFloat = 10
    let optionIconSize : CGFloat = 30
    let optionPadding : CGFloat = 20
    let optionViewPaddingBottom : CGFloat = 30
    let labelViewHeight :CGFloat = 30
    let selectViewHeight :CGFloat = 4
    let selectViewWidth :CGFloat = 6
    let optionLabelPaddingIcon : CGFloat = 10
    
    var loadingView : UIView?
    var analysisScrollViewHeight : CGFloat = 0
    var progressHeight : CGFloat = 0
    var progressWidth : CGFloat = 0
    var progressViews : [UIView] = []
    var selectView = UIImageView()
    var paragraphStyle = NSMutableParagraphStyle()
    var wrongItemList : [ItemModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        analysisScrollView.delegate = self
        analysisFailScrollView.delegate = self
        
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        switchView.layer.masksToBounds = true
        switchView.layer.cornerRadius = 5
        switchView.layer.borderWidth = 1
        switchView.layer.borderColor = UIColor.backgroundGray().CGColor
        allItemsButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Selected)
        wrongItemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Selected)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("StandardItemAnalysis")
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("StandardItemAnalysis")
        analysisScrollViewHeight = analysisScrollView.bounds.height
        progressHeight = progressView.bounds.height
        
        var pageCount = 0
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].isGroup{
                pageCount += globalItemList[i].children!.count
            }else{
                pageCount++
            }
        }
        progressWidth = progressView.bounds.width / CGFloat(pageCount)
        //进度条
        var progressIndex = 0
        for index in 0 ..< globalItemList.count {
            let item = globalItemList[index]
            
            if item.isGroup{
                var failItemList : [ItemModel] = []
                for i in 0 ..< item.children!.count{
                    let childView = UIView(frame: CGRectMake(progressWidth * CGFloat(progressIndex), 0, progressWidth, progressHeight))
                    
                    if item.children![i].answer.completeType == 1{
                        childView.backgroundColor = UIColor.correctColor()
                    }else{
                        childView.backgroundColor = UIColor.wrongColor()
                        failItemList.append(item.children![i])
                    }
                    progressViews.append(childView)
                    progressView.addSubview(childView)
                    if progressIndex != 0{
                        let breakView = UIView(frame: CGRectMake(progressWidth * CGFloat(progressIndex), 0, 1, progressHeight))
                        breakView.backgroundColor = UIColor.whiteColor()
                        progressView.addSubview(breakView)
                    }
                    progressIndex++
                }
                if failItemList.count != 0{
                    let failItemModel : ItemModel = ItemModel()
                    failItemModel.isGroup = item.isGroup
                    failItemModel.answer = item.answer
                    failItemModel.index = item.index
                    failItemModel.modelType = item.modelType
                    failItemModel.children = failItemList
                    wrongItemList.append(failItemModel)
                }
            }else{
                let childView = UIView(frame: CGRectMake(progressWidth * CGFloat(progressIndex), 0, progressWidth, progressHeight))
                
                if globalItemList[index].answer.completeType == 1{
                    childView.backgroundColor = UIColor.correctColor()
                }else{
                    childView.backgroundColor = UIColor.wrongColor()
                    wrongItemList.append(globalItemList[index])
                }
                progressViews.append(childView)
                progressView.addSubview(childView)
                if progressIndex != 0{
                    let breakView = UIView(frame: CGRectMake(progressWidth * CGFloat(progressIndex), 0, 1, progressHeight))
                    breakView.backgroundColor = UIColor.whiteColor()
                    progressView.addSubview(breakView)
                }
                progressIndex++
            }
        }
        selectView = UIImageView(frame: CGRectMake((progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight))
        if globalItemList[0].isGroup{
            if globalItemList[0].children![0].answer.completeType == 1{
                selectView.image = UIImage(named: "item_select_true")
            }else{
                selectView.image = UIImage(named: "item_select_false")
            }
        }else{
            if globalItemList[0].answer.completeType == 1{
                selectView.image = UIImage(named: "item_select_true")
            }else{
                selectView.image = UIImage(named: "item_select_false")
            }
        }
        progressView.addSubview(selectView)
        
        //全部试题
        analysisScrollView.contentSize = CGSize(width: screenWidth * CGFloat(globalItemList.count), height: analysisScrollViewHeight)
        for index in 0 ..< globalItemList.count{
            if globalItemList[index].modelType == 1{
                singleChoiceAnalysis(index , itemList: globalItemList , scrollView:analysisScrollView)
            }else if globalItemList[index].modelType == 3{
                 judgeAnalysis(index, itemList: globalItemList , scrollView:analysisScrollView)
            }else  if globalItemList[index].modelType == 5{
                multipleChoiceAnalysis(index, itemList: globalItemList , scrollView:analysisScrollView)
            }else{
                groupItemAnalysis(index, itemList: globalItemList , scrollView:analysisScrollView)
            }
        }
        
        if let itemIndex = itemIndex{
            if itemIndex > ItemViewTag.groupViewTagBase(){
                let page = itemIndex % ItemViewTag.groupViewTagBase() - ItemViewTag.resultItemButtonTagBase()
                let childPage = itemIndex / ItemViewTag.groupViewTagBase() - 1
                let childScrollView = analysisScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                
                analysisScrollView.scrollRectToVisible(CGRectMake(analysisScrollView.bounds.width * CGFloat(page), 0, analysisScrollView.bounds.width, analysisScrollView.bounds.height), animated: false)
                childrenScrollView.scrollRectToVisible(CGRectMake(childrenScrollView.bounds.width * CGFloat(childPage), 0, analysisScrollView.bounds.width, analysisScrollView.bounds.height), animated: false)
            }else{
                let page = itemIndex - ItemViewTag.resultItemButtonTagBase()
                analysisScrollView.scrollRectToVisible(CGRectMake(analysisScrollView.bounds.width * CGFloat(page), 0, analysisScrollView.bounds.width, analysisScrollView.bounds.height), animated: false)
            }
        }
        
        //错题
        analysisFailScrollView.contentSize = CGSize(width: screenWidth * CGFloat(wrongItemList.count), height: analysisScrollViewHeight)
        for index in 0 ..< wrongItemList.count{
            if wrongItemList[index].modelType == 1{
                singleChoiceAnalysis(index , itemList: wrongItemList , scrollView: analysisFailScrollView)
            }else if wrongItemList[index].modelType == 3{
                judgeAnalysis(index, itemList: wrongItemList , scrollView:analysisFailScrollView)
            }else  if wrongItemList[index].modelType == 5{
                multipleChoiceAnalysis(index, itemList: wrongItemList , scrollView:analysisFailScrollView)
            }else{
                groupItemAnalysis(index, itemList: wrongItemList , scrollView:analysisFailScrollView)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    @IBAction func backButton(sender: UIButton) {
        self.navigationController?.popViewControllerAnimated(true)
        self.navigationController?.navigationBarHidden = false
        titleView.hidden = true
    }
    
    @IBAction func allItemShow(sender: UIButton) {
        allItemsButton.selected = true
        wrongItemButton.selected = false
        analysisScrollView.hidden = false
        analysisFailScrollView.hidden = true
        let page = Int(floor((analysisScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let item = globalItemList[page]
        var index = 0
        for i in 0 ..< page{
            if globalItemList[i].isGroup{
                index += globalItemList[i].children!.count
            }else{
                index++
            }
        }
        if item.isGroup{
            let childScrollView = analysisScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            index += childPage
            if item.children![childPage].answer.completeType == 1{
                selectView.image = UIImage(named: "item_select_true")
            }else{
                selectView.image = UIImage(named: "item_select_false")
            }
        }else{
            if item.answer.completeType == 1{
                selectView.image = UIImage(named: "item_select_true")
            }else{
                selectView.image = UIImage(named: "item_select_false")
            }
        }
        selectView.frame = CGRectMake(progressWidth * CGFloat(index) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        for i in 0 ..< progressViews.count{
             if progressViews[i].backgroundColor == UIColor.backgroundGray(){
                progressViews[i].backgroundColor = UIColor.correctColor()
            }
        }
    }
    
    @IBAction func wrongItemShow(sender: UIButton) {
        if wrongItemList.count > 0{
            MobClick.event("WrongAnalysisButton")
            allItemsButton.selected = false
            wrongItemButton.selected = true
            analysisScrollView.hidden = true
            analysisFailScrollView.hidden = false
            let page = Int(floor((analysisFailScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let item = wrongItemList[page]
            let newPage = item.index
            var index = 0
            for i in 0 ..< newPage{
                if globalItemList[i].isGroup{
                    index += globalItemList[i].children!.count
                }else{
                    index++
                }
            }
            if item.isGroup{
                let childScrollView = analysisFailScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
                let childItem = item.children![childPage]
                index += (childItem.index - 1)
            }
            selectView.frame = CGRectMake(progressWidth * CGFloat(index) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
            selectView.image = UIImage(named: "item_select_false")
            for i in 0 ..< progressViews.count{
                if progressViews[i].backgroundColor == UIColor.correctColor(){
                    progressViews[i].backgroundColor = UIColor.backgroundGray()
                }
            }
        }
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        if allItemsButton.selected{
            let page = Int(floor((analysisScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let item = globalItemList[page]
            var index = 0
            for i in 0 ..< page{
                if globalItemList[i].isGroup{
                    index += globalItemList[i].children!.count
                }else{
                    index++
                }
            }
            if item.isGroup{
                let childScrollView = analysisScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
                index += childPage
                if item.children![childPage].answer.completeType == 1{
                    selectView.image = UIImage(named: "item_select_true")
                }else{
                    selectView.image = UIImage(named: "item_select_false")
                }
            }else{
                if item.answer.completeType == 1{
                    selectView.image = UIImage(named: "item_select_true")
                }else{
                    selectView.image = UIImage(named: "item_select_false")
                }
            }
            UIView.beginAnimations("move", context: nil)
            UIView.setAnimationDuration(0.3)
            selectView.frame = CGRectMake(progressWidth * CGFloat(index) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
            UIView.commitAnimations()
        }else{
            let page = Int(floor((analysisFailScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let item = wrongItemList[page]
            let newPage = item.index
            var index = 0
            for i in 0 ..< newPage{
                if globalItemList[i].isGroup{
                    index += globalItemList[i].children!.count
                }else{
                    index++
                }
            }
            if item.isGroup{
                let childScrollView = analysisFailScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
                let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
                let childItem = item.children![childPage]
                index += (childItem.index - 1)
            }
            UIView.beginAnimations("move", context: nil)
            UIView.setAnimationDuration(0.3)
            selectView.frame = CGRectMake(progressWidth * CGFloat(index) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
            UIView.commitAnimations()
        }
    }
}