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
    let submitButtonHeight = screenHeight / 11
    
    var itemScrollViewHeight : CGFloat = 0
    var paragraphStyle = NSMutableParagraphStyle()
    var loadingView : UIView?
    var httpController = HttpController()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        MobClick.beginLogPageView("WrongItem")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("WrongItem")
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
                let itemArray = dataDictionary.objectForKey("Records")as! NSArray
                if itemArray.count != 0{
                    var itemList : [ItemModel] = []
                    for index in 0 ..< itemArray.count{
                        let item = itemArray[index] as! NSDictionary
                        let itemModel = ItemModel(dictionary: item)
                        itemModel.index = index
                        if itemModel.isGroup{
                            if itemModel.isGroup{
                                let childList = item.objectForKey("children") as! NSArray
                                for i in 0 ..< itemModel.children!.count{
                                    let childItem = childList[i] as! NSDictionary
                                    let childItemModel = itemModel.children![i]
                                    childItemModel.parentIndex = index
                                    childItemModel.blankInx = childItem.objectForKey("blankInx") as! Int
                                    let isWrongbookItemTested = childItem.objectForKey("isWrongbookItemTested") as! Bool
                                    if isWrongbookItemTested{
                                        let lastTestCompleteType = childItem.objectForKey("lastTestCompleteType") as! Int
                                        childItemModel.answer.completeType = lastTestCompleteType
                                    }else{
                                        childItemModel.answer.completeType = -1
                                    }
                                }
                            }
                        }else{
                            let isWrongbookItemTested = item.objectForKey("isWrongbookItemTested") as! Bool
                            itemModel.blankInx = item.objectForKey("blankInx") as! Int
                            if isWrongbookItemTested{
                                let lastTestCompleteType = item.objectForKey("lastTestCompleteType") as! Int
                                itemModel.answer.completeType = lastTestCompleteType
                            }else{
                                itemModel.answer.completeType = -1
                            }
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
                let recordsDictionary = dataDictionary.objectForKey("Records") as! NSDictionary
                let newProgress = recordsDictionary.objectForKey("progress") as! Double
                checkAchieve(selectSubject!.preparingProgress, newProgress: newProgress)
                UpdateGlobalData(recordsDictionary)
            }
        }
        loadingView?.removeFromSuperview()
    }
    
    func checkAchieve(oldProgress: Double , newProgress : Double){
        AchieveController.progressControl(self, oldProgress: oldProgress, newProgress: newProgress)
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
    
    
    @IBAction func functionButton(sender: UIButton) {
        if sender.selected {
            sender.selected = false
            deleteButton.hidden = true
        }else{
            sender.selected = true
            deleteButton.hidden = false
        }
    }
    
    @IBAction func deleteButton(sender: UIButton) {
        functionButton.sendActionsForControlEvents(UIControlEvents.TouchUpInside)
        var page = Int(floor((itemScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
        let item = globalItemList[page]
        var itemIndex = 0
        var itemCount = 0
        
        MobClick.event("WrongBookDelete")
        
        for index in 0 ..< globalItemList.count{
            if globalItemList[index].isGroup{
                itemCount += globalItemList[index].children!.count
            }else{
                itemCount++
            }
        }
        if item.isGroup && item.children!.count != 1 {
            let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            let childPage = Int(floor((childrenScrollView.contentOffset.x * 2.0 + screenWidth) / (screenWidth * 2.0)))
            let childItem = item.children![childPage]
            let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(childItem.id) , "item.blankInx" : String(childItem.blankInx)])
            httpController.getNSDataByParams("deleteWrongBookItem", paramsDictionary: deleteParams)
            
            let deleteScrollView = childrenScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page + ItemViewTag.groupViewTagBase() * (childPage + 1)) as! UIScrollView
            deleteScrollView.removeFromSuperview()
            for index in 0 ..< globalItemList.count{
                let thisChildScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + index) as! UIScrollView?
                let thisItem = globalItemList[index]
                if let thisChildScrollView = thisChildScrollView{
                    if thisItem.isGroup{
                        let thisChildrenScrollView = thisChildScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
                        for i in 0 ..< thisItem.children!.count{
                            if index == page && i == childPage{
                                itemIndex++
                            }else{
                                let thisGroupChildScrollView = thisChildrenScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + index + ItemViewTag.groupViewTagBase() * (i + 1)) as! UIScrollView
                                let pageLabel = thisGroupChildScrollView.viewWithTag(ItemViewTag.countLabelTag()) as! UILabel
                                if index == page && i > childPage{
                                    thisGroupChildScrollView.tag = thisGroupChildScrollView.tag - ItemViewTag.groupViewTagBase()
                                    thisGroupChildScrollView.frame = CGRectMake(thisGroupChildScrollView.frame.origin.x - screenWidth, 0, screenWidth, thisGroupChildScrollView.frame.height)
                                    pageLabel.text = "\(itemIndex)/\(itemCount - 1)"
                                }else if index > page{
                                    pageLabel.text = "\(itemIndex)/\(itemCount - 1)"
                                }else{
                                    pageLabel.text = "\(itemIndex + 1)/\(itemCount - 1)"
                                }
                                itemIndex++
                            }
                        }
                    }else{
                        let pageLabel = thisChildScrollView.viewWithTag(ItemViewTag.countLabelTag()) as! UILabel
                        if index >= page{
                            pageLabel.text = "\(itemIndex)/\(itemCount - 1)"
                        }else{
                            pageLabel.text = "\(itemIndex + 1)/\(itemCount - 1)"
                        }
                        itemIndex++
                    }
                }else{
                    if thisItem.isGroup{
                        itemIndex += thisItem.children!.count
                    }else{
                        itemIndex++
                    }
                }
                
            }
            for i in 0 ..< item.children!.count{
                if i > childPage{
                    item.children![i].index--
                }
            }
            item.children!.removeAtIndex(childPage)
            childrenScrollView.contentSize.width = childrenScrollView.contentSize.width - screenWidth
            childrenScrollView.scrollRectToVisible(CGRectMake(0, 0, screenWidth, childrenScrollView.frame.height), animated: false)
            let listScrollView = itemListView!.viewWithTag(ItemViewTag.itemListScrollViewTag()) as! UIScrollView
            listScrollView.removeFromSuperview()
            let infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabel.removeFromSuperview()
            itemListInit()
        }else{
            if item.isGroup{
                let childItem = item.children![0]
                let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(childItem.id) , "item.blankInx" : String(childItem.blankInx)])
                httpController.getNSDataByParams("deleteWrongBookItem", paramsDictionary: deleteParams)
            }else{
                let deleteParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "item.id" : String(item.id) , "item.blankInx" : String(item.blankInx)])
                httpController.getNSDataByParams("deleteWrongBookItem", paramsDictionary: deleteParams)
            }
            let deleteScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            deleteScrollView.removeFromSuperview()
            for index in 0 ..< globalItemList.count{
                if index != page{
                    let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + index) as! UIScrollView?
                    let thisItem = globalItemList[index]
                    if index > page {
                        thisItem.index = thisItem.index - 1
                        if thisItem.isGroup{
                            for i in 0 ..< thisItem.children!.count{
                                thisItem.children![i].parentIndex = thisItem.index
                            }
                        }
                    }
                    if let childScrollView = childScrollView{
                        if thisItem.isGroup{
                            for i in 0 ..< thisItem.children!.count{
                                let thisGroupChildScrollView = childScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + index + ItemViewTag.groupViewTagBase() * (i + 1)) as! UIScrollView
                                let pageLabel = thisGroupChildScrollView.viewWithTag(ItemViewTag.countLabelTag()) as! UILabel
                                if index > page {
                                    thisGroupChildScrollView.tag = thisGroupChildScrollView.tag - 1
                                    pageLabel.text = "\(itemIndex)/\(itemCount - 1)"
                                }else{
                                    pageLabel.text = "\(itemIndex + 1)/\(itemCount - 1)"
                                }
                                itemIndex++
                            }
                            if index > page{
                                childScrollView.tag = childScrollView.tag - 1
                                childScrollView.frame = CGRectMake(childScrollView.frame.origin.x - screenWidth, 0, screenWidth, childScrollView.frame.height)
                            }
                        }else{
                            let pageLabel = childScrollView.viewWithTag(ItemViewTag.countLabelTag()) as! UILabel
                            if index > page{
                                childScrollView.tag = childScrollView.tag - 1
                                childScrollView.frame = CGRectMake(childScrollView.frame.origin.x - screenWidth, 0, screenWidth, childScrollView.frame.height)
                                pageLabel.text = "\(itemIndex)/\(itemCount - 1)"
                            }else{
                                pageLabel.text = "\(itemIndex + 1)/\(itemCount - 1)"
                            }
                            itemIndex++
                        }
                    }else{
                        if thisItem.isGroup{
                            itemIndex += thisItem.children!.count
                        }else{
                            itemIndex++
                        }
                    }
                }else{
                    itemIndex++
                }
            }
            globalItemList.removeAtIndex(page)
            itemScrollView.contentSize.width = itemScrollView.contentSize.width - screenWidth
            if page == globalItemList.count{
                page = page - 1
            }
            showPages(page)
            let listScrollView = itemListView!.viewWithTag(ItemViewTag.itemListScrollViewTag()) as! UIScrollView
            listScrollView.removeFromSuperview()
            let infoLabel = itemListView!.viewWithTag(ItemViewTag.itemListInfoLabelTag()) as! UILabel
            infoLabel.removeFromSuperview()
            itemListInit()
        }
        if globalItemList.count == 0{
            noItemView.hidden = false
            functionButton.hidden = true
            itemListButton.enabled = false
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