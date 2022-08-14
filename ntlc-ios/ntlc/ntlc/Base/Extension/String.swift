//
//  String.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/15.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

extension String{
    static func valueOf(any: Any?) -> String{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return ""
        }else{
            return any as! String
        }
    }
}
