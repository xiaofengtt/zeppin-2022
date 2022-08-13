//
//  UnstandardItemViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/22.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UnstandardItemViewController: UIViewController, UIScrollViewDelegate , UIAlertViewDelegate , HttpDataProtocol{
    
    @IBOutlet weak var titleView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var itemScrollView: UIScrollView!
    @IBOutlet weak var markButton: UIButton!
    @IBOutlet weak var itemListButton: UIButton!
    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var backButton: UIButton!
    var itemListView: UIView?
    var itemListCoverView: UIView?
    
    let paddingLeft : CGFloat = 25
    let fontSize :CGFloat = 16
    let lineSpacing : CGFloat = 10
    let analysisButtonPaddingTop : CGFloat = 20
    let analysisButtonFontSize : CGFloat = 14
    let paddingBottom : CGFloat = 30
    let contentFontName = "STHeitiSC-Medium"
    
    var loadingView : UIView?
    var paragraphStyle = NSMutableParagraphStyle()
    var httpController = HttpController()
    var itemScrollViewHeight : CGFloat = 0
    
    override func viewDidLoad() {
        httpController.delegate = self
        itemScrollView.delegate = self
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        titleLabel.text = selectSubject!.name
        markButton.layer.masksToBounds = true
        markButton.layer.cornerRadius = screenWidth / 16
        messageLabel.layer.masksToBounds = true
        messageLabel.layer.cornerRadius = 5
        
        let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubject!.id) , "itemType.id" : String(selectItemType!.id)])
        httpController.getNSDataByParams("getItemsByItemType", paramsDictionary: itemParams)
        
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
        if recieveType == "getItemsByItemType" {
            if dataDictionary.objectForKey("Status") as! String == "fail"{
                let message = dataDictionary.objectForKey("Message") as! String
                UIAlertView(title: "出题失败", message:message , delegate: self, cancelButtonTitle: "好").show()
            }else{
            var itemArray = dataDictionary.objectForKey("Records")as! NSArray
                var itemList : [ItemModel] = []
                for index in 0 ..< itemArray.count{
                    var item = itemArray[index] as! NSDictionary
                    var itemModel = ItemModel(dictionary: item)
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
                itemListInit()
            }
        }
        if recieveType == "unstandardMark"{
            if dataDictionary.objectForKey("Status") as! String == "success"{
                var data = dataDictionary.objectForKey("Records") as! NSDictionary
                selectSubject!.preparingProgress = data.objectForKey("progress") as! Double
                selectSubject!.countDown = data.objectForKey("nextTestdayCount") as! Int
                selectSubject!.rankingRate = data.objectForKey("rankingRate") as! Double
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        if scrollView == itemScrollView{
            showPages(page)
        }
        if globalItemList[page].isGroup{
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! UIScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            if globalItemList[page].children![childPage].answer.completeType == 1{
                markButton.selected = true
            }else{
                markButton.selected = false
            }
        }else{
            if globalItemList[page].answer.completeType == 1{
                markButton.selected = true
            }else{
                markButton.selected = false
            }
        }
    }
    
    func showData(){
        itemScrollViewHeight = itemScrollView.bounds.height
        
        if globalItemList.count > 0 && globalItemList[0].answer.completeType == 1{
            markButton.selected = true
        }
        
        //试题
        itemScrollView.contentSize = CGSize(width: screenWidth * CGFloat(globalItemList.count), height: itemScrollViewHeight)
        
        var number = 2
        if globalItemList.count < 2{
            number = globalItemList.count
        }
        for index in 0 ..< number{
            if globalItemList[index].modelType == 6{
                writtenResponseShow(index)
            }else{
                groupItemShow(index)
            }
        }
    }
    
    func alertView(alertView: UIAlertView, didDismissWithButtonIndex buttonIndex: Int) {
        if alertView.title == "出题失败"{
            backButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            titleView.hidden = true
        }
    }
    
    @IBAction func itemListButton(sender: UIButton) {
        if sender.selected{
            itemListButton.selected = false
            itemListView!.hidden = true
            itemListCoverView!.hidden = true
        }else{
            itemListButton.selected = true
            itemListView!.hidden = false
            itemListCoverView!.hidden = false
        }
    }
    
    @IBAction func markButton(sender: UIButton) {
        let page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var item = globalItemList[page]
        
        var itemButton : UIButton
        if item.isGroup{
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! UIScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page + (childPage + 1) * ItemViewTag.groupViewTagBase()) as! UIButton
            item = globalItemList[page].children![childPage]
        }else{
            itemButton = itemListView!.viewWithTag(ItemViewTag.itemListButtonBase() + page) as! UIButton
        }
        if sender.selected{
            sender.selected = false
            item.answer.completeType = 0
            itemButton.setTitle(itemButton.titleForState(UIControlState.Disabled)!, forState: UIControlState.Normal)
            itemButton.setImage(nil, forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "flag" : String(0)])
            httpController.getNSDataByParams("unstandardMark", paramsDictionary: itemParams)
            selectItemType!.flagCount--
            messageLabel.text = "   已 取 消 掌 握 该 题   "
            messageLabel.backgroundColor = UIColor.lightGrayColor()
            messageLabel.alpha = 1
            UIView.beginAnimations("messageFade", context: nil)
            UIView.setAnimationDelay(1)
            UIView.setAnimationDuration(0.5)
            messageLabel.alpha = 0
            UIView.commitAnimations()
        }else{
            sender.selected = true
            item.answer.completeType = 1
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor()), forState: UIControlState.Normal)
            itemButton.setTitle("", forState: UIControlState.Normal)
            itemButton.setImage(UIImage(named: "flag_icon"), forState: UIControlState.Normal)
            itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            var infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabelUpdate(infoLabel)
            
            let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "flag" : String(1)])
            httpController.getNSDataByParams("unstandardMark", paramsDictionary: itemParams)
            selectItemType!.flagCount++
            messageLabel.text = "   成 功 设 为 已 掌 握   "
            messageLabel.backgroundColor = UIColor.flagColor()
            messageLabel.alpha = 1
            UIView.beginAnimations("messageFade", context: nil)
            UIView.setAnimationDelay(1)
            UIView.setAnimationDuration(0.5)
            messageLabel.alpha = 0
            UIView.commitAnimations()
        }
    }
    
    @IBAction func backButton(sender: UIButton) {
        titleView.hidden = true
    }
}