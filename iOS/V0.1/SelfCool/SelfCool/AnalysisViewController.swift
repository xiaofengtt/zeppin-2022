//
//  AnalysisViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/23.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AnalysisViewController: UIViewController,UIScrollViewDelegate{
    
    @IBOutlet weak var progressView: UIView!
    @IBOutlet weak var analysisScrollView: UIScrollView!
    @IBOutlet weak var analysisFailScrollView: UIScrollView!
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var switchView: UIView!
    
    var itemIndex :Int?
    
    let tagViewPaddingTop :CGFloat = 10
    let answerCardPaddingTop :CGFloat = 50
    let paddingLeft : CGFloat = 25
    let submitButtonHeight :CGFloat = 45
    let tagFontSize : CGFloat = 11
    let fontSize :CGFloat = 16
    let lineSpacing : CGFloat = 10
    let optionIconSize : CGFloat = 30
    let optionViewPadding : CGFloat = 30
    let optionPadding : CGFloat = 20
    let labelViewHeight :CGFloat = 30
    let selectViewHeight :CGFloat = 4
    let selectViewWidth :CGFloat = 6
    let analysisPaddingTop : CGFloat = 10
    let optionLabelPaddingIcon : CGFloat = 10
    let paddingButtom : CGFloat = 20
    let contentFontName = "STHeitiSC-Medium"
    
    var loadingView : UIView?
    var analysisScrollViewHeight : CGFloat = 0
    var progressHeight : CGFloat = 0
    var progressWidth : CGFloat = 0
    var progressViews : [UIView] = []
    var selectView = UIImageView()
    var paragraphStyle = NSMutableParagraphStyle()
    var failItemList : [ItemModel] = []
    
    override func viewDidLoad() {
        analysisScrollView.delegate = self
        analysisFailScrollView.delegate = self
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("LoadingView", owner: self, options: nil)
        loadingView = nibs.lastObject as? UIView
        loadingView!.frame = UIScreen.mainScreen().bounds
        self.view.addSubview(loadingView!)
        
        switchView.layer.masksToBounds = true
        switchView.layer.cornerRadius = 5
        super.viewDidLoad() 
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        analysisScrollViewHeight = analysisScrollView.bounds.height
        progressHeight = progressView.bounds.height
        progressWidth = progressView.bounds.width / CGFloat(globalItemList.count)
        
        //进度条
        for index in 0 ..< globalItemList.count {
            var childView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, progressWidth, progressHeight))
            if globalItemList[index].answer.completeType == 1{
                childView.backgroundColor = UIColor.mainColor()
            }else{
                childView.backgroundColor = UIColor.redColor()
                failItemList.append(globalItemList[index])
            }
            progressViews.append(childView)
            progressView.addSubview(childView)
            if index != 0{
                var breakView = UIView(frame: CGRectMake(progressWidth * CGFloat(index), 0, 1, progressHeight))
                breakView.backgroundColor = UIColor.whiteColor()
                progressView.addSubview(breakView)
            }
        }
        selectView = UIImageView(frame: CGRectMake((progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight))
        if globalItemList[0].answer.completeType == 1{
            selectView.image = UIImage(named: "item_select_true")
        }else{
            selectView.image = UIImage(named: "item_select_false")
            
        }
        progressView.addSubview(selectView)
        
        //全部试题
        analysisScrollView.contentSize = CGSize(width: screenWidth * CGFloat(globalItemList.count), height: analysisScrollViewHeight)
        for index in 0 ..< globalItemList.count{
            switch globalItemList[index].modelType{
            case 24 :
                singleChioceAnalysis(index , itemList: globalItemList , scrollView:analysisScrollView)
            default :
                singleChioceAnalysis(index, itemList: globalItemList , scrollView:analysisScrollView)
            }
        }
        
        if let itemIndex = itemIndex{
             analysisScrollView.scrollRectToVisible(CGRectMake(analysisScrollView.bounds.width * CGFloat(itemIndex), 0, analysisScrollView.bounds.width, analysisScrollView.bounds.height), animated: false)
        }
        
        //错题
        analysisFailScrollView.contentSize = CGSize(width: screenWidth * CGFloat(failItemList.count), height: analysisScrollViewHeight)
        for index in 0 ..< failItemList.count{
            switch failItemList[index].modelType{
            case 24 :
                singleChioceAnalysis(index , itemList: failItemList , scrollView: analysisFailScrollView)
            default :
                singleChioceAnalysis(index , itemList: failItemList , scrollView: analysisFailScrollView)
            }
        }
        if let loadingView = loadingView{
            loadingView.removeFromSuperview()
        }
    }
    
    @IBAction func backButton(sender: UIButton) {
        self.navigationController?.popViewControllerAnimated(true)
        self.navigationController?.navigationBarHidden = false
    }
    
    @IBAction func allItemShow(sender: UIButton) {
        analysisScrollView.hidden = false
        analysisFailScrollView.hidden = true
        let page = Int(floor((analysisScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        selectView.frame = CGRectMake(progressWidth * CGFloat(page) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        if globalItemList[page].answer.completeType == 1{
            selectView.image = UIImage(named: "item_select_true")
        }else{
            selectView.image = UIImage(named: "item_select_false")
        }
    }
    
    @IBAction func failItemShow(sender: UIButton) {
        analysisScrollView.hidden = true
        analysisFailScrollView.hidden = false
        let page = Int(floor((analysisFailScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let newPage = failItemList[page].index
        selectView.frame = CGRectMake(progressWidth * CGFloat(newPage) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        selectView.image = UIImage(named: "item_select_false")
    }
    
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((scrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        if scrollView == analysisScrollView{
            selectView.frame = CGRectMake(progressWidth * CGFloat(page) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
            if globalItemList[page].answer.completeType == 1{
                selectView.image = UIImage(named: "item_select_true")
            }else{
                selectView.image = UIImage(named: "item_select_false")
            }
        }else if scrollView == analysisFailScrollView{
            let newPage = failItemList[page].index
            selectView.frame = CGRectMake(progressWidth * CGFloat(newPage) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        }
    }
}