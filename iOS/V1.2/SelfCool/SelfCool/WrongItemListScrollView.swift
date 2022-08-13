//
//  WrongItemListScrollView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/25.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    func itemListInit(){
        let infoFontSize : CGFloat = 15
        let conlumns = 5
        let rows = Int((globalItemList.count - 1) / conlumns) + 1
        let listViewPaddingTop : CGFloat = 15
        let listViewPaddingLeft : CGFloat = 15
        let listViewPaddingBottom : CGFloat = 10
        let itemViewSize :CGFloat = (screenWidth - 2 * listViewPaddingLeft) / CGFloat(conlumns)
        let itemButtonSize : CGFloat = itemViewSize / 2
        let infoFontName = "STHeitiSC-Medium"
        
        itemListView = UIView(frame: CGRectMake(0, itemScrollView.frame.origin.y, screenWidth, itemViewSize * 3 + infoFontSize + listViewPaddingTop * 2 + listViewPaddingBottom))
        itemListView!.backgroundColor = UIColor.whiteColor()
        var infoLabel = UILabel(frame: CGRectMake(listViewPaddingLeft, listViewPaddingTop , screenWidth - 2 * listViewPaddingLeft, infoFontSize))
        infoLabelUpdate(infoLabel)
        infoLabel.font = UIFont(name: infoFontName, size: infoFontSize)
        infoLabel.textAlignment = NSTextAlignment.Center
        infoLabel.tag = ItemViewTag.itemListInfoLabelTag()
        itemListView!.addSubview(infoLabel)
        
        var itemListScrollView = UIScrollView(frame: CGRectMake(listViewPaddingLeft, infoFontSize  + listViewPaddingTop * 2, screenWidth - 2 * listViewPaddingLeft, itemViewSize * 3))
        itemListScrollView.contentSize = CGSize(width: screenWidth - 2 * listViewPaddingLeft, height: itemViewSize * CGFloat(rows))
        itemListScrollView.showsHorizontalScrollIndicator = false
        itemListScrollView.showsVerticalScrollIndicator = false
        itemListScrollView.tag = ItemViewTag.itemListScrollViewTag()
        for index in 0 ..< globalItemList.count{
            let row :Int = index / conlumns
            let num :Int = index % conlumns
            var item = globalItemList[index]
            
            var itemButton = UIButton(frame: CGRectMake(itemViewSize * CGFloat(num) + itemViewSize / 6 , itemViewSize * CGFloat(row) + itemViewSize / 6 , itemViewSize / 3 * 2, itemViewSize / 3 * 2))
            itemButton.setTitle(String(index + 1), forState: UIControlState.Normal)
            itemButton.addTarget(self, action: "showItems:", forControlEvents: UIControlEvents.TouchUpInside)
            itemButton.tag = ItemViewTag.itemListButtonBase() + index
            
            itemButton.layer.masksToBounds = true
            itemButton.layer.cornerRadius = 5
            if item.answer.completeType == -1{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.buttonGray().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            }else if item.answer.completeType == 1{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            }else if item.answer.completeType == 0{
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.redColor()), forState: UIControlState.Normal)
                itemButton.setBackgroundImage(UIImage.imageWithColor(UIColor.redColor().colorWithAlphaComponent(0.5)), forState: UIControlState.Highlighted)
            }
            itemButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
            itemListScrollView.addSubview(itemButton)
        }
        itemListView!.addSubview(itemListScrollView)
        
        if globalItemList.count > 15{
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
    
    func showItems(sender : UIButton){
        let page = sender.tag - ItemViewTag.itemListButtonBase()
        showPages(page)
        itemListButton.selected = false
        itemListView!.hidden = true
        itemListCoverView!.hidden = true
        itemScrollView.scrollRectToVisible(CGRectMake(itemScrollView.bounds.width * CGFloat(page), 0, itemScrollView.bounds.width, itemScrollView.bounds.height), animated: false)
    }
}