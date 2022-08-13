//
//  StandradItemAnalysisViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/23.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class StandradItemAnalysisViewController: UIViewController,UIScrollViewDelegate{
    
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
    let contentFontName = "STHeitiSC-Medium"
    
    var loadingView : UIView?
    var analysisScrollViewHeight : CGFloat = 0
    var progressHeight : CGFloat = 0
    var progressWidth : CGFloat = 0
    var progressViews : [UIView] = []
    var selectView = UIImageView()
    var paragraphStyle = NSMutableParagraphStyle()
    var wrongItemList : [ItemModel] = []
    
    override func viewDidLoad() {
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
                wrongItemList.append(globalItemList[index])
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
            if globalItemList[index].modelType == 1{
                singleChioceAnalysis(index , itemList: globalItemList , scrollView:analysisScrollView)
            }else{
                singleChioceAnalysis(index, itemList: globalItemList , scrollView:analysisScrollView)
            }
        }
        
        if let itemIndex = itemIndex{
             analysisScrollView.scrollRectToVisible(CGRectMake(analysisScrollView.bounds.width * CGFloat(itemIndex), 0, analysisScrollView.bounds.width, analysisScrollView.bounds.height), animated: false)
        }
        
        //错题
        analysisFailScrollView.contentSize = CGSize(width: screenWidth * CGFloat(wrongItemList.count), height: analysisScrollViewHeight)
        for index in 0 ..< wrongItemList.count{
            if wrongItemList[index].modelType == 1{
                singleChioceAnalysis(index , itemList: wrongItemList , scrollView: analysisFailScrollView)
            }else{
                singleChioceAnalysis(index , itemList: wrongItemList , scrollView: analysisFailScrollView)
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
        selectView.frame = CGRectMake(progressWidth * CGFloat(page) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        if globalItemList[page].answer.completeType == 1{
            selectView.image = UIImage(named: "item_select_true")
        }else{
            selectView.image = UIImage(named: "item_select_false")
        }
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].answer.completeType == 1{
                progressViews[i].backgroundColor = UIColor.mainColor()
            }
        }
    }
    
    @IBAction func wrongItemShow(sender: UIButton) {
        allItemsButton.selected = false
        wrongItemButton.selected = true
        analysisScrollView.hidden = true
        analysisFailScrollView.hidden = false
        let page = Int(floor((analysisFailScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let newPage = wrongItemList[page].index
        selectView.frame = CGRectMake(progressWidth * CGFloat(newPage) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        selectView.image = UIImage(named: "item_select_false")
        for i in 0 ..< globalItemList.count{
            if globalItemList[i].answer.completeType == 1{
                progressViews[i].backgroundColor = UIColor.backgroundGray()
            }
        }
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
            let newPage = wrongItemList[page].index
            selectView.frame = CGRectMake(progressWidth * CGFloat(newPage) + (progressWidth - selectViewWidth) / 2, selectViewHeight * (-1), selectViewWidth, selectViewHeight)
        }
    }
}