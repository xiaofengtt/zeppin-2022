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
