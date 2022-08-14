import Foundation
extension String{
    static func valueOf(any: Any?) -> String{
        if(any == nil || any.debugDescription == "Optional(<null>)"){
            return ""
        }else{
            return any as! String
        }
    }
    var length: Int {
        return self.lengthOfBytes(using: String.Encoding.utf8)
    }
    subscript (i: Int) -> String {
        return self[i ..< i + 1]
    }
    func substring(fromIndex: Int) -> String {
        return self[min(fromIndex, length) ..< length]
    }
    func substring(toIndex: Int) -> String {
        return self[0 ..< max(0, toIndex)]
    }
    func substring(fromIndex: Int, toIndex: Int) -> String {
        return self[min(fromIndex, length) ..< max(0, toIndex)]
    }
    subscript (r: Range<Int>) -> String {
        let range = Range(uncheckedBounds: (lower: max(0, min(length, r.lowerBound)), upper: min(length, max(0, r.upperBound))))
        let start = index(startIndex, offsetBy: range.lowerBound)
        let end = index(start, offsetBy: range.upperBound - range.lowerBound)
        return String(self[start ..< end])
    }
    func nsRange(from range: Range<String.Index>) -> NSRange {
        let from = range.lowerBound.samePosition(in: utf16)
        let to = range.upperBound.samePosition(in: utf16)
        return NSRange(location: utf16.distance(from: utf16.startIndex, to: from!), length: utf16.distance(from: from!, to: to!))
    }
    func range(from nsRange: NSRange) -> Range<String.Index>? {
        guard
            let from16 = utf16.index(utf16.startIndex, offsetBy: nsRange.location, limitedBy: utf16.endIndex),
            let to16 = utf16.index(from16, offsetBy: nsRange.length, limitedBy: utf16.endIndex),
            let from = String.Index(from16, within: self),
            let to = String.Index(to16, within: self)
            else { return nil }
        return from ..< to
    }
    func indexOf(sub:String)->Int {
        var pos = -1
        if let range = range(of:sub, options: .literal ) {
            if !range.isEmpty {
                pos = self.distance(from:startIndex, to:range.lowerBound)
            }
        }
        return pos
    }
    func urlEncoded() -> String {
        let encodeUrlString = self.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        return encodeUrlString ?? ""
    }
    func urlDecoded() -> String {
        return self.removingPercentEncoding ?? ""
    }
}
