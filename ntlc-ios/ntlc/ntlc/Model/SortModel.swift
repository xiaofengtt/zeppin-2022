//
//  SortModel.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/22.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

class SortModel : NSObject{
    var value: String
    var name: String
    var selected: Bool
    
    override init() {
        self.value = ""
        self.name = ""
        self.selected = false
    }
    
    init(value: String, name: String) {
        self.value = value
        self.name = name
        self.selected = false
    }
    
    func copyModel() -> SortModel{
        let copy = SortModel()
        copy.value = self.value
        copy.name = self.name
        copy.selected = self.selected
        return copy
    }
}
