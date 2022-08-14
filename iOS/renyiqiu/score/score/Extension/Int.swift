//
//  Int.swift
//  ntlc
//
//  Created byfarmer on 2017/11/15.
//  Copyright © 2017年 farmer. All rights reserved.
//

import Foundation

extension Int{
    static func valueOf(any: Any?) -> Int{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return 0
        }else{
            return any as! Int
        }
    }
    
    static func getRandomNum(from: Int, to: Int) -> Int{
        return from + Int(arc4random() % UInt32(to - from + 1))
    }
}
