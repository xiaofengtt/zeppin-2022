import Foundation
extension Array{
    func copy() -> Array{
        var newArray: Array = []
        for e in self{
            newArray.insert(e, at: newArray.count)
        }
        return newArray
    }
}
