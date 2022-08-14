import Foundation
class LuckyLocalDataManager{
    
    //本地存储key
    static let countryKey: String = "countryUuid"
    static let activiteAlertDate: String = "activiteAlertDate"
    static let deviceUuid: String = "deviceUuid"
    static let deviceToken: String = "deviceToken"
    static let guideTime: String = "guideTime"
    
    //写入用户信息
    class func writeLocationData(data : AnyObject?){
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            if(data != nil){
                dict.setValue(data, forKey: "userData")
            }else{
                dict.removeObject(forKey: "userData")
            }
            dict.write(toFile: path, atomically: true)
        }
    }
    
    //读取用户信息
    class func getLocationData() -> LuckyFrontUserModel?{
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let fileManager = FileManager.default
        if(!fileManager.fileExists(atPath: path)) {
            if let bundlePath = Bundle.main.path(forResource: "DataBase", ofType: "plist") {
                do {
                    try fileManager.copyItem(atPath: bundlePath, toPath: path)
                } catch _ {
                    return nil
                }
            }
        }
        let myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            if(dict.object(forKey: "userData") != nil){
                let user : LuckyFrontUserModel? = LuckyFrontUserModel(data: dict.object(forKey: "userData") as! NSDictionary)
                if user != nil {
                    return user
                }
            }
        }
        return nil
    }
    
    //通过key写入
    class func writeWithKey(key: String, data : AnyObject?){
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            if(data != nil){
                dict.setValue(data, forKey: key)
            }else{
                dict.removeObject(forKey: key)
            }
            dict.write(toFile: path, atomically: true)
        }
    }
    
    //通过key获取
    class func getForKey(key: String) -> Any?{
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let fileManager = FileManager.default
        if(!fileManager.fileExists(atPath: path)) {
            if let bundlePath = Bundle.main.path(forResource: "DataBase", ofType: "plist") {
                do {
                    try fileManager.copyItem(atPath: bundlePath, toPath: path)
                } catch _ {
                    return nil
                }
            }
        }
        let myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            return dict.object(forKey: key)
        }
        return nil
    }
}
