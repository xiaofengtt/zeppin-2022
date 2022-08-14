//
//  LuckyUIScrollView.swift
//  lucky
//
//  Created by Farmer Zhu on 2020/8/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
extension UIScrollView{
    //增加顶部下拉刷新刷新区域
    func addRefreshView(bottomLine: Bool){
        let refreshView = SportsScrollRefreshView(parent: self,bottomLine: bottomLine)
        refreshView.tag = LuckyTagManager.globalTags.refreshView
        self.addSubview(refreshView)
    }
    //获取下拉刷新View
    func refreshView() -> SportsScrollRefreshView{
        let refreshView = self.viewWithTag(LuckyTagManager.globalTags.refreshView) as! SportsScrollRefreshView
        return refreshView
    }
    //设为已触发下拉刷新 待刷新
    func refreshViewToAble(){
        let refreshView = self.viewWithTag(LuckyTagManager.globalTags.refreshView) as! SportsScrollRefreshView
        refreshView.setAble()
    }
    //设为正常 刷新完毕后
    func refreshViewToNormal(){
        let refreshView = self.viewWithTag(LuckyTagManager.globalTags.refreshView) as! SportsScrollRefreshView
        refreshView.setNormal()
    }
}
