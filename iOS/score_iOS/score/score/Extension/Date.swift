//
//  Date.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/31.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

extension Date{
    var timestamp: Int {
        let timeInterval: TimeInterval = self.timeIntervalSince1970
        return Int(timeInterval * 1000)
    }
}
