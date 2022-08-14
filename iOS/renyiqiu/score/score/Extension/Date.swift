//
//  Date.swift
//  ryqiu
//
//  Created by worker on 2019/5/31.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

extension Date{
    var timestamp: Int {
        let timeInterval: TimeInterval = self.timeIntervalSince1970
        return Int(timeInterval * 1000)
    }
}
