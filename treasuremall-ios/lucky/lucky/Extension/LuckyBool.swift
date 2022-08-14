import Foundation
extension Bool{
    //字典里取Bool型
    static func valueOf(any: Any?) -> Bool{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return false
        }else{
            if let value = any as? Bool{
                return value
            }else{
                return false
            }
        }
    }
}
