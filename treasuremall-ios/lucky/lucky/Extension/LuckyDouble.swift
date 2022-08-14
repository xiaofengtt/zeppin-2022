import Foundation
extension Double{
    //字典里取Double型
    static func valueOf(any: Any?) -> Double{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return 0
        }else{
            if let value = any as? Double{
                return value
            }else{
                return 0
            }
        }
    }
}
