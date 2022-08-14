import Foundation
class SportsLocalDataController{
    class func writeLocalData(_ name : String , data : AnyObject?){
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let myDict = NSMutableDictionary(contentsOfFile: path)
        if let dict = myDict {
            if(data != nil){
                dict.setValue(data, forKey: name)
            }else{
                dict.removeObject(forKey: name)
            }
            dict.write(toFile: path, atomically: true)
        }
    }
    class func loadLocalData() {
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true) as NSArray
        let documentsDirectory = paths[0] as! String
        let path = documentsDirectory + "/DataBase.plist"
        let fileManager = FileManager.default
        if(!fileManager.fileExists(atPath: path)) {
            if let bundlePath = Bundle.main.path(forResource: "DataBase", ofType: "plist") {
                do {
                    try fileManager.copyItem(atPath: bundlePath, toPath: path)
                } catch _ {
                }
            }
        }
        let myDict = NSDictionary(contentsOfFile: path)
        if let dict = myDict {
            let userDictionary : NSDictionary? = dict.object(forKey: "user") as? NSDictionary
            if userDictionary != nil && String.valueOf(any: userDictionary!.value(forKey: "uuid")) != "" {
                user = SportsFrontUserModel(data: userDictionary!)
            }
        }
    }
}
