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
