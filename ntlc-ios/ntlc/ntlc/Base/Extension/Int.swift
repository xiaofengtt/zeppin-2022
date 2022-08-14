//
//  Int.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
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
}
