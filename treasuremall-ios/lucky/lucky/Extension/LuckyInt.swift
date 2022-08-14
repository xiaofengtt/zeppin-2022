import Foundation
extension Int{
    //字典中取Int型
    static func valueOf(any: Any?) -> Int{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return 0
        }else{
            if let value = any as? Int{
                return value
            }else{
                return 0
            }
        }
    }
    
    //获取范围内随机数
    static func random(from: Int, to: Int) -> Int{
        return from + Int(arc4random() % UInt32(to - from + 1))
    }
}
