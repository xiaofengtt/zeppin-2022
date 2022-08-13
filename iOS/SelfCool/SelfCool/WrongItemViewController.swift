//
//  WrongItemViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/22.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class WrongItemViewController: UIViewController , UIScrollViewDelegate , UIAlertViewDelegate , HttpDataProtocol {
    
    @IBOutlet weak var titleView: UIView!
    @IBOutlet weak var itemListButton: UIButton!
    @IBOutlet weak var itemScrollView: UIScrollView!
    @IBOutlet weak var functionButton: UIButton!
    @IBOutlet weak var deleteButton: UIButton!
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var noItemView: UIView!
    var itemListView: UIView?
    var itemListCoverView: UIView?
    
    let paddingLeft : CGFloat = 25
    let fontSize :CGFloat = 16
    let lineSpacing : CGFloat = 10
    let optionIconSize : CGFloat = 30
    let optionPadding : CGFloat = 20
    let optionLabelPaddingIcon : CGFloat = 10
    let paddingBottom : CGFloat = 30
    let analysisButtonPaddingTop : CGFloat = 20
    let analysisButtonFontSize : CGFloat = 14
    let contentFontName = "STHeitiSC-Medium"
    
    var itemScrollViewHeight : CGFloat = 0
    var paragraphStyle = NSMutableParagraphStyle()
    var loadingView : UIView?
    var httpController = HttpController()
    
    override func viewDidLoad() {
        httpController.delegate = self
        itemScrollView.delegate = self
        self.navigationController!.setNavigationBarHidden(true, animated: true)
        
        loadingView = LoadingView(self)
        self.view.addSubview(loadingView!)
        functionButton.layer.masksToBounds = true
        functionButton.layer.cornerRadius = screenWidth / 16
        deleteButton.layer.masksToBounds = true
        deleteButton.layer.cornerRadius = screenWidth / 16
        itemListButton.setImage(UIImage.imageWithColor(UIColor.clearColor()), forState: UIControlState.Disabled)
        let itemParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "subject.id" : String(selectSubject!.id)])
        httpController.getNSDataByParams("getWrongBookItems", paramsDictionary: itemParams)
        
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
        if recieveType == "getWrongBookItems" {
            if dataDictionary.objectForKey("Status") as! String == "fail"{
                let message = dataDictionary.objectForKey("Message") as! String
                UIAlertView(title: "出题失败", message:message , delegate: self, cancelButtonTitle: "好").show()
            }else{
                var itemArray = dataDictionary.objectForKey("Records")as! NSArray
                if itemArray.count != 0{
                    var itemList : [ItemModel] = []
                    for index in 0 ..< itemArray.count{
                        var item = itemArray[index] as! NSDictionary
                        var itemModel = ItemModel(dictionary: item)
                        itemModel.userWrongCount = item.objectForKey("testItemWrongCount") as! Int
                        itemModel.userCorrectCount = item.objectForKey("testItemCorrectCount") as! Int
                        var isWrongbookItemTested = item.objectForKey("isWrongbookItemTested") as! Bool
                        itemModel.blankInx = item.objectForKey("blankInx") as! Int
                        itemModel.index = index
                        if isWrongbookItemTested{
                            var lastTestCompleteType = item.objectForKey("lastTestCompleteType") as! Int
                            itemModel.answer.completeType = lastTestCompleteType
                        }else{
                            itemModel.answer.completeType = -1
                        }
                        itemList.append(itemModel)
                    }
                    globalItemList = itemList
                    showData()
                    itemListInit()
                }else{
                    noItemView.hidden = false
                    functionButton.hidden = true
                    itemListButton.enabled = false
                }
            }
        }else if recieveType == "wrongBookAnswer" {
            if dataDictionary.objectForKey("Status") as! String == "success"{
                var recordsDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                UpdateGlobalData(recordsDictionary)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    func scrollViewDidScroll(scrollView: UIScrollView) {
        let page = Int(floor((scrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        showPages(page)
        deleteButton.hidden = true
        functionButton.selected = false
    }
    
    func showData(){
        itemScrollViewHeight = itemScrollView.bounds.height
        //试题
        itemScrollView.contentSize = CGSize(width: screenWidth * CGFloat(globalItemList.count), height: itemScrollViewHeight)
        
        var number = 2
        if globalItemList.count < 2{
            number = globalItemList.count
        }
        for index in 0 ..< number{
            if globalItemList[index].modelType == 1{
                singleChoiceShow(index)
            }else if globalItemList[index].modelType == 3{
                judgeShow(index)
            }else if globalItemList[index].modelType == 5{
                multipleChoiceShow(index)
            }
        }
    }
    
    func alertView(alertView: UIAlertView, didDismissWithButtonIndex buttonIndex: Int) {
        if alertView.title == "出题失败"{
            backButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
            titleView.hidden = true
        }
    }
    
    @IBAction func deleteButton(sender: UIButton) {
        functionButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
        var page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        var item = globalItemList[page]
        globalItemList.removeAtIndex(page)
        var deleteScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
        deleteScrollView.removeFromSuperview()
        if globalItemList.count != 0{
            for index in 0 ..< globalItemList.count + 1{
                var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + index) as! UIScrollView?
                if index >= page && index != globalItemList.count{
                    globalItemList[index].index = globalItemList[index].index - 1
                }
                if let childScrollView = childScrollView{
                    var pageLabel = childScrollView.viewWithTag(ItemViewTag.countLabelTag()) as! UILabel
                    if index >= page{
                        var analysisButton =  childScrollView.viewWithTag(ItemViewTag.analysisButtonTag()) as! UIButton
                        childScrollView.tag = childScrollView.tag - 1
                        childScrollView.frame = CGRectMake(childScrollView.frame.origin.x - screenWidth, 0, screenWidth, childScrollView.frame.height)
                        pageLabel.text = "\(index) / \(globalItemList.count)"
                    }else{
                        pageLabel.text = "\(index + 1) / \(globalItemList.count)"
                    }
                }
            }
            itemScrollView.contentSize.width = itemScrollView.contentSize.width - screenWidth
            if page == globalItemList.count{
                page = page - 1
            }
            showPages(page)
            var listScrollView = itemListView!.viewWithTag(ItemViewTag.itemListScrollViewTag()) as! UIScrollView
            listScrollView.removeFromSuperview()
            var infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabel.removeFromSuperview()
            itemListInit()
        }else{
            noItemView.hidden = false
            functionButton.hidden = true
            itemListButton.enabled = false
        }
        let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "item.blankInx" : String(item.blankInx)])
        httpController.getNSDataByParams("deleteWrongBookItem", paramsDictionary: deleteParams)
    }
    
    @IBAction func functionButton(sender: UIButton) {
        if sender.selected {
            sender.selected = false
            deleteButton.hidden = true
        }else{
            sender.selected = true
            deleteButton.hidden = false
        }
    }
    
    @IBAction func itemList(sender: UIButton) {
        if sender.selected {
            sender.selected = false
            itemListCoverView!.hidden = true
            itemListView!.hidden = true
        }else{
            sender.selected = true
            itemListCoverView!.hidden = false
            itemListView!.hidden = false
        }
    }
    
    @IBAction func backButton(sender: UIButton) {
        titleView.hidden = true
    }
}