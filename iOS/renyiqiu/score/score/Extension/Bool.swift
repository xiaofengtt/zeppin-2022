//
//  Bool.swift
//  ntlc
//
//  Created byfarmer on 2017/11/15.
//  Copyright © 2017年 farmer. All rights reserved.
//

import Foundation

extension Bool{
    static func valueOf(any: Any?) -> Bool{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return false
        }else{
            return any as! Bool
        }
    }
}
