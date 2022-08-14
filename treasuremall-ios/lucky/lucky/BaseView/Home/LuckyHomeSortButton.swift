//
//  LuckyHomeSortButton.swift
//  lucky
//  首页商品排序按钮
//  Created by Farmer Zhu on 2020/9/1.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyHomeSortButton: UIButton{
    
    var type: SortType!
    var flagDesc: Bool!
    
    var selectedLine: CALayer!
    
    //排序方式
    enum SortType {
        case hot
        case progress
        case participant
    }
    
    init(frame: CGRect, type: SortType) {
        super.init(frame: frame)
        self.type = type
        self.flagDesc = true
        
        self.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.fontBlack(), for: UIControl.State.selected)
        self.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        self.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
        
        //选中下划线
        selectedLine = CALayer()
        selectedLine.isHidden = true
        selectedLine.frame = CGRect(x: (frame.width - 16 * screenScale)/2, y: frame.height/4*3, width: 16 * screenScale, height: 3 * screenScale)
        selectedLine.backgroundColor = UIColor.mainYellow().cgColor
        selectedLine.masksToBounds = true
        selectedLine.cornerRadius = 2 * screenScale
        self.layer.addSublayer(selectedLine)
        
        //三种排序方式
        if(type == SortType.hot){
            //热门
            self.setTitle(NSLocalizedString("Hot", comment: ""), for: UIControl.State.normal)
        }else if(type == SortType.progress){
            //进度
            self.titleEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
            self.setTitle(NSLocalizedString("Progress", comment: ""), for: UIControl.State.normal)
            self.setImage(UIImage(named: "image_sort_unselected"), for: UIControl.State.normal)
        }else if(type == SortType.participant){
            //参加者
            self.titleEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
            self.setTitle(NSLocalizedString("Participant", comment: ""), for: UIControl.State.normal)
            self.setImage(UIImage(named: "image_sort_unselected"), for: UIControl.State.normal)
        }
    }
    
    //重写选中
    override var isSelected: Bool{
        willSet{
            self.selectedLine.isHidden = !newValue
            if newValue{
                //hot无箭头
                if(self.type != SortType.hot){
                    self.setImage(UIImage(named: "image_sort_desc"), for: UIControl.State.normal)
                }
            }else{
                if(self.type != SortType.hot){
                    self.setImage(UIImage(named: "image_sort_unselected"), for: UIControl.State.normal)
                }
                flagDesc = true
            }
        }
    }
    
    //设为倒叙
    func setDesc(){
        self.flagDesc = true
        self.setImage(UIImage(named: "image_sort_desc"), for: UIControl.State.normal)
    }
    
    //设为正序
    func setAsc(){
        self.flagDesc = false
        self.setImage(UIImage(named: "image_sort_asc"), for: UIControl.State.normal)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
}
