import Foundation
class LuckyEncodingUtil {
    //base64编码
    static func getBase64(_ string:String) -> String{
        let plainData = string.data(using: String.Encoding.utf8)
        return plainData!.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
    }
    //base64解码
    static func getFromBase64(_ string:String) -> String{
        let decodedData = Data(base64Encoded: string, options: NSData.Base64DecodingOptions(rawValue: 0))
        return String(data: decodedData!, encoding: String.Encoding.utf8)!
    }
    //des编码
    static func getDes(_ string:String) -> String{
        return LuckyDESUtils.encryptUseDES(string)
    }
    //des解码
    static func getFromDes(_ string:String) -> String{
        return LuckyDESUtils.decryptUseDES(string)
    }
}
extension String {
    //获取字符串MD5
    var md5 : String{
        let str = self.cString(using: String.Encoding.utf8)
        let strLen = CC_LONG(self.lengthOfBytes(using: String.Encoding.utf8))
        let digestLen = Int(CC_MD5_DIGEST_LENGTH)
        let result = UnsafeMutablePointer<CUnsignedChar>.allocate(capacity: digestLen);
        CC_MD5(str!, strLen, result);
        let hash = NSMutableString();
        for i in 0 ..< digestLen {
            hash.appendFormat("%02x", result[i]);
        }
        result.deinitialize(count: 0);
        return String(format: hash as String)
    }
}
