//
//  WrongItemListScrollView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/25.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    func showItems(sender : UIButton){
        let tag = sender.tag
        if tag > ItemViewTag.groupViewTagBase(){
            let page = tag % ItemViewTag.groupViewTagBase() - ItemViewTag.itemListButtonBase()
            let childPage = tag / ItemViewTag.groupViewTagBase() - 1
            showPages(page)
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
            let childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            let childrenScrollView = childScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            childrenScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(childPage), 0, screenWidth, childrenScrollView.frame.height), animated: false)
        }else{
            let page = tag - ItemViewTag.itemListButtonBase()
            showPages(page)
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
        }
        itemListButton.selected = false
        itemListView!.hidden = true
        itemListCoverView!.hidden = true
    }
    
    func itemListInit(){
        let infoFontSize : CGFloat = 15
        let conlumns = 5
        let listViewPaddingTop : CGFloat = 15
        let listViewPaddingLeft : CGFloat = 15
        let listViewPaddingBottom : CGFloat = 10
        let itemViewSize :CGFloat = (screenWidth - 2 * listViewPaddingLeft) / CGFloat(conlumns)
        var itemIndex = 0
        
        itemListView = UIView(frame: CGRectMake(0, itemScrollView.frame.origin.y, screenWidth, itemViewSize * 3 + infoFontSize + listViewPaddingTop * 2 + listViewPaddingBottom))
        itemListView!.backgroundColor = UIColor.whiteColor()
        let infoLabel = UILabel(frame: CGRectMake(listViewPaddingLeft, listViewPaddingTop , screenWidth - 2 * listViewPaddingLeft, infoFontSize))
        infoLabelUpdate(infoLabel)
        infoLabel.font = UIFont.systemFontOfSize(infoFontSize)
        infoLabel.textAlignment = NSTextAlignment.Center
        infoLabel.tag = ItemViewTag.itemListInfoLabelTag()
        itemListView!.addSubview(infoLabel)
        
        let itemListScrollView = UIScrollView(frame: CGRectMake(listViewPaddingLeft, infoFontSize  + listViewPaddingTop * 2, screenWidth - 2 * listViewPaddingLeft, itemViewSize * 3))
        itemListScrollView.showsHorizontalScrollIndicator = false
        itemListScrollView.showsVerticalScrollIndicator = false
        itemListScrollView.tag = ItemViewTag.itemListScrollViewTag()
        for index in 0 ..< globalItemList.count{
            let item = globalItemList[index]
            
            if item.isGroup{
                for i in 0 ..< item.children!.count{
                    let childItem = item.children![i]
                    let row :Int = itemIndex / conlumns
                    let num :Int = itemIndex % conlumns
                    
                    let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 12 , itemViewSize * CGFloat(row) + itemViewSize / 12 , itemViewSize / 6 * 5, itemViewSize / 6 * 5))
                    itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Normal)
                    itemButton.addTarget(self, action: "showItems:", forControlEvents: UIControlEvents.TouchUpInside)
                    itemButton.tag = ItemViewTag.itemListButtonBase() + index + childItem.index * ItemViewTag.groupViewTagBase()
                    
                    itemButton.layer.masksToBounds = true
                    itemButton.layer.cornerRadius = 5
                    if childItem.answer.completeType == -1{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }else if childItem.answer.completeType == 1{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }else if childItem.answer.completeType == 0{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }
                    itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                    itemListScrollView.addSubview(itemButton)
                    itemIndex++
                }
            }else{
                let row :Int = itemIndex / conlumns
                let num :Int = itemIndex % conlumns
                
                let itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 12 , itemViewSize * CGFloat(row) + itemViewSize / 12 , itemViewSize / 6 * 5, itemViewSize / 6 * 5))
                itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Normal)
                itemButton.addTarget(self, action: "showItems:", forControlEvents: UIControlEvents.TouchUpInside)
                itemButton.tag = ItemViewTag.itemListButtonBase() + index
                
                itemButton.layer.masksToBounds = true
                itemButton.layer.cornerRadius = 5
                if item.answer.completeType == -1{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }else if item.answer.completeType == 1{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.correctColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }else if item.answer.completeType == 0{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.wrongColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }
                itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                itemListScrollView.addSubview(itemButton)
                itemIndex++
            }
        }
        let rows = Int((itemIndex - 1) / conlumns) + 1
        itemListScrollView.contentSize = CGSize(width: screenWidth - 2 * listViewPaddingLeft, height: itemViewSize * CGFloat(rows + 1))
        itemListView!.addSubview(itemListScrollView)
        
        if itemIndex > 14{
            itemListView!.frame.size = CGSize(width: screenWidth, height: itemScrollViewHeight)
            itemListScrollView.frame.size = CGSize(width: screenWidth, height: itemScrollViewHeight - 2 * infoFontSize)
        }
        itemListCoverView = UIView(frame: CGRectMake(0, itemScrollView.frame.origin.y + itemListView!.frame.height, screenWidth, itemScrollViewHeight - itemListView!.frame.height))
        itemListCoverView!.backgroundColor = UIColor.blackColor().colorWithAlphaComponent(0.5)
        let itemListCoverButton = UIButton(frame: CGRectMake(0, 0, screenWidth, itemListCoverView!.frame.height))
        itemListCoverButton.addTarget(self, action: "closeItemList:", forControlEvents: UIControlEvents.TouchUpInside)
        itemListCoverView!.addSubview(itemListCoverButton)
        itemListView!.hidden = true
        itemListCoverView!.hidden = true
        self.view.addSubview(itemListView!)
        self.view.addSubview(itemListCoverView!)
    }
    
    func closeItemList(sender: UIButton) {
        itemListButton.selected = false
        itemListCoverView!.hidden = true
        itemListView!.hidden = true
    }
    
}