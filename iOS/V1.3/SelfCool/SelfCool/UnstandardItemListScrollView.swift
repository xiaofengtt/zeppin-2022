//
//  UnstandardItemListScrollView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension UnstandardItemViewController {
    func showItems(sender : UIButton){
        itemListButton.selected = false
        itemListView!.hidden = true
        itemListCoverView!.hidden = true
        
        if sender.tag >= ItemViewTag.groupViewTagBase(){
            let page = sender.tag % ItemViewTag.groupViewTagBase() - ItemViewTag.itemListButtonBase()
            showPages(page)
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
            let childPage = sender.tag / ItemViewTag.groupViewTagBase() - 1
            var childScrollView = itemScrollView.viewWithTag(ItemViewTag.childScrollViewTagBase() + page) as! UIScrollView
            var childrenScrollView = itemScrollView.viewWithTag(ItemViewTag.groupChildrenScrollViewTag()) as! ChildrenScrollView
            childrenScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(childPage), 0, screenWidth, itemScrollViewHeight), animated: false)
        }else{
            let page = sender.tag - ItemViewTag.itemListButtonBase()
            showPages(page)
            itemScrollView.scrollRectToVisible(CGRectMake(screenWidth * CGFloat(page), 0, screenWidth, itemScrollView.bounds.height), animated: false)
        }
    }
    
    func itemListInit(){
        let infoFontSize : CGFloat = 15
        let conlumns = 5
        let listViewPaddingTop : CGFloat = 15
        let listViewPaddingLeft : CGFloat = 15
        let listViewPaddingBottom : CGFloat = 10
        let itemViewSize :CGFloat = (screenWidth - 2 * listViewPaddingLeft) / CGFloat(conlumns)
        let itemButtonSize : CGFloat = itemViewSize / 2
        let infoFontName = "STHeitiSC-Medium"
        var itemIndex = 0
        
        itemListView = UIView(frame: CGRectMake(0, itemScrollView.frame.origin.y, screenWidth, itemViewSize * 3 + infoFontSize + listViewPaddingTop * 2 + listViewPaddingBottom))
        itemListView!.backgroundColor = UIColor.whiteColor()
        var infoLabel = UILabel(frame: CGRectMake(listViewPaddingLeft, listViewPaddingTop , screenWidth - 2 * listViewPaddingLeft, infoFontSize))
        infoLabelUpdate(infoLabel)
        infoLabel.font = UIFont(name: infoFontName, size: infoFontSize)
        infoLabel.textAlignment = NSTextAlignment.Center
        infoLabel.tag = ItemViewTag.itemListInfoLabelTag()
        itemListView!.addSubview(infoLabel)
        
        var itemListScrollView = UIScrollView(frame: CGRectMake(listViewPaddingLeft, infoFontSize  + listViewPaddingTop * 2, screenWidth - 2 * listViewPaddingLeft, itemViewSize * 3))
        itemListScrollView.showsHorizontalScrollIndicator = false
        itemListScrollView.showsVerticalScrollIndicator = false
        itemListScrollView.tag = ItemViewTag.itemListScrollViewTag()
        for index in 0 ..< globalItemList.count{
            var item = globalItemList[index]
            
            if item.isGroup{
                for i in 0 ..< item.children!.count{
                    var childItem = item.children![i]
                    let row :Int = itemIndex / conlumns
                    let num :Int = itemIndex % conlumns
                    
                    var itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
                    itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Normal)
                    itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Disabled)
                    itemButton.addTarget(self, action: "showItems:", forControlEvents: UIControlEvents.TouchUpInside)
                    itemButton.tag = ItemViewTag.itemListButtonBase() + index + childItem.index * ItemViewTag.groupViewTagBase()
                    
                    itemButton.layer.masksToBounds = true
                    itemButton.layer.cornerRadius = 5
                    if childItem.answer.completeType == -1{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }else if childItem.answer.completeType == 1{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor()), forState: UIControlState.Normal)
                        itemButton.setTitle("", forState: UIControlState.Normal)
                        itemButton.setImage(UIImage(named: "flag_icon"), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }else if childItem.answer.completeType == 0{
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
                        itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                    }
                    itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
                    itemListScrollView.addSubview(itemButton)
                    itemIndex++
                }
            }else{
                let row :Int = itemIndex / conlumns
                let num :Int = itemIndex % conlumns
                var itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
                itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Normal)
                itemButton.setTitle(String(itemIndex + 1), forState: UIControlState.Disabled)
                itemButton.addTarget(self, action: "showItems:", forControlEvents: UIControlEvents.TouchUpInside)
                itemButton.tag = ItemViewTag.itemListButtonBase() + index
                
                itemButton.layer.masksToBounds = true
                itemButton.layer.cornerRadius = 5
                if item.answer.completeType == -1{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }else if item.answer.completeType == 1{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor()), forState: UIControlState.Normal)
                    itemButton.setTitle("", forState: UIControlState.Normal)
                    itemButton.setImage(UIImage(named: "flag_icon"), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.flagColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
                }else if item.answer.completeType == 0{
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor()), forState: UIControlState.Normal)
                    itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.darkGrayColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
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
        var itemListCoverButton = UIButton(frame: CGRectMake(0, 0, screenWidth, itemListCoverView!.frame.height))
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