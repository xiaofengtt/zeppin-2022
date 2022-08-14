//
//  CategoryScrollView.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/28.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import UIKit

public protocol CategoryScrollViewDelegate{
    func changeCategory(_ uuid: String)
}

class CategoryScrollView: UIScrollView {
    
    var buttonDelegate: CategoryScrollViewDelegate?
    
    let cellWidth: CGFloat = 52 * screenScale
    var dataList: Array<String>!
    var uuidList: Array<String>!
    
    init(frame: CGRect, dataList: Array<String>) {
        super.init(frame: frame)
        self.dataList = dataList
        self.uuidList = []
        
        self.backgroundColor = UIColor.clear
        self.showsVerticalScrollIndicator = false
        self.showsHorizontalScrollIndicator = false
        self.contentSize = CGSize(width: cellWidth * CGFloat(dataList.count), height: self.frame.height)
        
        for index in 0 ..< dataList.count{
            let params = dataList[index].components(separatedBy: "_")
            let name = params[0]
            let uuid = params[1]
            uuidList.append(uuid)
            
            let cellView = UIView(frame: CGRect(x: cellWidth * CGFloat(index), y: 0, width: cellWidth, height: self.frame.height))
            cellView.backgroundColor = UIColor.clear
            
            let nameLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            nameLabel.tag = TagController.CategoryScrollViewTags.scrollViewBase + index * TagController.CategoryScrollViewTags.childBase + TagController.CategoryScrollViewTags.cellLabel
            nameLabel.text = name
            nameLabel.textColor = UIColor.fontDarkGray()
            nameLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
            nameLabel.textAlignment = NSTextAlignment.center
            
            let selectImageView = UIImageView(frame: CGRect(x: (cellWidth - (nameLabel.intrinsicContentSize.width + 10 * screenScale)) / 2, y: self.frame.height * 0.6, width: nameLabel.intrinsicContentSize.width + 10 * screenScale, height: 6 * screenScale))
            selectImageView.tag = TagController.CategoryScrollViewTags.scrollViewBase + index * TagController.CategoryScrollViewTags.childBase + TagController.CategoryScrollViewTags.selectImageView
            selectImageView.image = UIImage(named: "common_category_selected")
            selectImageView.isHidden = true
            cellView.addSubview(selectImageView)
            
            nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            cellView.addSubview(nameLabel)
            
            let cellButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            cellButton.tag = TagController.CategoryScrollViewTags.scrollViewBase + index * TagController.CategoryScrollViewTags.childBase + TagController.CategoryScrollViewTags.cellButton
            cellButton.addTarget(self, action: #selector(changeCategory(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(cellButton)
            
            self.addSubview(cellView)
        }
        
        if(dataList.count > 0){
            let fisrtButton = self.viewWithTag(TagController.CategoryScrollViewTags.scrollViewBase + TagController.CategoryScrollViewTags.cellButton) as! UIButton
            fisrtButton.sendActions(for: UIControl.Event.touchUpInside)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func changeCategory(_ sender: UIButton){
        let index = (sender.tag - TagController.CategoryScrollViewTags.scrollViewBase) / TagController.CategoryScrollViewTags.childBase
        
        for i in 0 ..< dataList.count{
            let cellLabel = self.viewWithTag(TagController.CategoryScrollViewTags.scrollViewBase + i * TagController.CategoryScrollViewTags.childBase + TagController.CategoryScrollViewTags.cellLabel) as! UILabel
            let selectImageView = self.viewWithTag(TagController.CategoryScrollViewTags.scrollViewBase + i * TagController.CategoryScrollViewTags.childBase + TagController.CategoryScrollViewTags.selectImageView) as! UIImageView
            if(index == i){
                cellLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
                cellLabel.textColor = UIColor.fontBlack()
                selectImageView.isHidden = false
            }else{
                cellLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                cellLabel.textColor = UIColor.fontDarkGray()
                selectImageView.isHidden = true
            }
        }
        
        if(self.buttonDelegate != nil){
            self.buttonDelegate!.changeCategory(uuidList[index])
        }
    }
}
