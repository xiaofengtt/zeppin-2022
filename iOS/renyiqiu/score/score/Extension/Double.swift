//
//  Double.swift
//  ntlc
//
//  Created byfarmer on 2017/11/15.
//  Copyright © 2017年 farmer. All rights reserved.
//

import Foundation

extension Double{
    static func valueOf(any: Any?) -> Double{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return 0
        }else{
            return any as! Double
        }
    }
}
