//
//  TagViewShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/28.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func TagViewShow(item : ItemModel,index : Int?) -> UIView{
    let tagViewPaddingTop :CGFloat = 10
    let taglViewHeight :CGFloat = 30
    let tagFontSize : CGFloat = 13
    let tagViewPaddingLeft : CGFloat = 25
    let indexLabelWidth : CGFloat = 60
    
    var tagView = UIView(frame: CGRectMake(0, 0, screenWidth,tagViewPaddingTop +  taglViewHeight))
    var itemTypeLabel = UILabel()
    var itemTypeString = " \(item.itemType)   |"
    itemTypeLabel.text = itemTypeString
    itemTypeLabel.font = UIFont.systemFontOfSize(tagFontSize)
    let itemTypeSize = NSString(string: itemTypeString).sizeWithAttributes([NSFontAttributeName : itemTypeLabel.font])
    itemTypeLabel.frame = CGRectMake(tagViewPaddingLeft,tagViewPaddingTop +  (taglViewHeight - itemTypeSize.height) / 2, itemTypeSize.width, itemTypeSize.height)
    tagView.addSubview(itemTypeLabel)
    
    var sourceTypeLabel = UILabel()
    var sourceTypeString = "   \(item.sourceType) "
    sourceTypeLabel.text = sourceTypeString
    sourceTypeLabel.font = UIFont.systemFontOfSize(tagFontSize)
    if index != nil{
        sourceTypeLabel.frame = CGRectMake(tagViewPaddingLeft + itemTypeSize.width, tagViewPaddingTop + (taglViewHeight - itemTypeSize.height) / 2, screenWidth - 2 * tagViewPaddingLeft - itemTypeSize.width - indexLabelWidth, itemTypeSize.height)
        tagView.addSubview(sourceTypeLabel)
        
        var indexLabel = UILabel(frame: CGRectMake(screenWidth - tagViewPaddingLeft - indexLabelWidth, tagViewPaddingTop +  (taglViewHeight - itemTypeSize.height) / 2, indexLabelWidth, itemTypeSize.height))
        indexLabel.text = "\(index! + 1)/\(globalItemList.count)"
        
        indexLabel.textAlignment = NSTextAlignment.Right
        indexLabel.font = UIFont.systemFontOfSize(tagFontSize)
        indexLabel.tag = ItemViewTag.countLabelTag()
        tagView.addSubview(indexLabel)
    }else{
        sourceTypeLabel.frame = CGRectMake(tagViewPaddingLeft + itemTypeSize.width, tagViewPaddingTop + (taglViewHeight - itemTypeSize.height) / 2, screenWidth - 2 * tagViewPaddingLeft - itemTypeSize.width, itemTypeSize.height)
        tagView.addSubview(sourceTypeLabel)
    }
    tagView.tag = ItemViewTag.tagViewTag()
    return tagView
}
