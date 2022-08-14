//
//  LeftCategoryScrollView.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

public protocol LeftCategoryScrollViewDelegate{
    func changeCategory(_ uuid: String)
}

class LeftCategoryScrollView: UIScrollView {
    
    var buttonDelegate: LeftCategoryScrollViewDelegate?
    
    let paddingTop = 2 * screenScale
    let cellHeight = 50 * screenScale
    var dataList: Array<String>!
    var uuidList: Array<String>!
    
    init(frame: CGRect, dataList: Array<String>) {
        super.init(frame: frame)
        self.dataList = dataList
        self.uuidList = []
        
        self.backgroundColor = UIColor.backgroundGray()
        self.showsVerticalScrollIndicator = false
        self.showsHorizontalScrollIndicator = false
        self.contentSize = CGSize(width: frame.width, height: cellHeight * CGFloat(dataList.count))
        
        for index in 0 ..< dataList.count{
            let params = dataList[index].components(separatedBy: "_")
            let name = params[0]
            let uuid = params[1]
            uuidList.append(uuid)
            
            let cellView = UIView(frame: CGRect(x: 0, y: paddingTop + cellHeight * CGFloat(index), width: frame.width, height: cellHeight))
            cellView.backgroundColor = UIColor.clear
            
            let selectView = UIView(frame: CGRect(x: 0, y: 0, width: 4 * screenScale, height: cellView.frame.height))
            selectView.tag = TagController.LeftCategoryScrollViewTags.scrollViewBase + index * TagController.LeftCategoryScrollViewTags.childBase + TagController.LeftCategoryScrollViewTags.selectView
            selectView.backgroundColor = UIColor.mainRed()
            selectView.isHidden = true
            cellView.addSubview(selectView)
            
            let cellLabel = UILabel(frame: CGRect(x: selectView.frame.width, y: 0, width: cellView.frame.width - selectView.frame.width, height: cellView.frame.height))
            cellLabel.tag = TagController.LeftCategoryScrollViewTags.scrollViewBase + index * TagController.LeftCategoryScrollViewTags.childBase + TagController.LeftCategoryScrollViewTags.cellLabel
            cellLabel.backgroundColor = UIColor.clear
            cellLabel.text = name
            cellLabel.textColor = UIColor.fontBlack()
            cellLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
            cellLabel.textAlignment = NSTextAlignment.center
            cellView.addSubview(cellLabel)
            
            let cellButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            cellButton.tag = TagController.LeftCategoryScrollViewTags.scrollViewBase + index * TagController.LeftCategoryScrollViewTags.childBase + TagController.LeftCategoryScrollViewTags.cellButton
            cellButton.addTarget(self, action: #selector(changeCategory(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(cellButton)
            
            self.addSubview(cellView)
        }
        
        if(dataList.count > 0){
            let fisrtButton = self.viewWithTag(TagController.LeftCategoryScrollViewTags.scrollViewBase + TagController.LeftCategoryScrollViewTags.cellButton) as! UIButton
            fisrtButton.sendActions(for: UIControl.Event.touchUpInside)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func changeCategory(_ sender: UIButton){
        let index = (sender.tag - TagController.LeftCategoryScrollViewTags.scrollViewBase) / TagController.LeftCategoryScrollViewTags.childBase
        
        for i in 0 ..< dataList.count{
            let cellLabel = self.viewWithTag(TagController.LeftCategoryScrollViewTags.scrollViewBase + i * TagController.LeftCategoryScrollViewTags.childBase + TagController.LeftCategoryScrollViewTags.cellLabel) as! UILabel
            let selectView = self.viewWithTag(TagController.LeftCategoryScrollViewTags.scrollViewBase + i * TagController.LeftCategoryScrollViewTags.childBase + TagController.LeftCategoryScrollViewTags.selectView)!
            if(index == i){
                cellLabel.textColor = UIColor.mainRed()
                cellLabel.backgroundColor = UIColor.white
                selectView.isHidden = false
            }else{
                cellLabel.textColor = UIColor.fontBlack()
                cellLabel.backgroundColor = UIColor.clear
                selectView.isHidden = true
            }
        }
        
        if(self.buttonDelegate != nil){
            self.buttonDelegate!.changeCategory(uuidList[index])
        }
    }
}
