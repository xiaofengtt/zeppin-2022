//
//  Object.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/30.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

extension Array{
    func copy() -> Array{
        var newArray: Array = []
        for e in self{
            newArray.insert(e, at: newArray.count)
        }
        return newArray
    }
}
