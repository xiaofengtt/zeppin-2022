import Foundation
class SportsFrontUserModel : NSObject{
    var uuid: String
    var nickname: String
    var realname: String
    var idcard: String
    var mobile: String
    var email: String
    var sex: String
    var password: String
    var realnameFlag: Bool
    var type: String
    var status: String
    override init() {
        self.uuid = ""
        self.nickname = ""
        self.realname = ""
        self.idcard = ""
        self.mobile = ""
        self.email = ""
        self.sex = ""
        self.password = ""
        self.realnameFlag = false
        self.type = ""
        self.status = ""
    }
    init(data: NSDictionary) {
        self.uuid = String.valueOf(any: data.value(forKey: "uuid"))
        self.nickname = String.valueOf(any: data.value(forKey: "nickname"))
        self.realname = String.valueOf(any: data.value(forKey: "realname"))
        self.idcard = String.valueOf(any: data.value(forKey: "idcard"))
        self.mobile = String.valueOf(any: data.value(forKey: "mobile"))
        self.email = String.valueOf(any: data.value(forKey: "email"))
        self.sex = String.valueOf(any: data.value(forKey: "sex"))
        self.password = String.valueOf(any: data.value(forKey: "password"))
        self.realnameFlag = Bool.valueOf(any: data.value(forKey: "realnameFlag"))
        self.type = String.valueOf(any: data.value(forKey: "type"))
        self.status = String.valueOf(any: data.value(forKey: "status"))
    }
    func getDictionary() -> NSDictionary{
        let dictionary = NSMutableDictionary()
        dictionary.setObject(uuid, forKey: "uuid" as NSCopying)
        dictionary.setObject(nickname, forKey: "nickname" as NSCopying)
        dictionary.setObject(realname, forKey: "realname" as NSCopying)
        dictionary.setObject(idcard, forKey: "idcard" as NSCopying)
        dictionary.setObject(mobile, forKey: "mobile" as NSCopying)
        dictionary.setObject(email, forKey: "email" as NSCopying)
        dictionary.setObject(sex, forKey: "sex" as NSCopying)
        dictionary.setObject(password, forKey: "password" as NSCopying)
        dictionary.setObject(realnameFlag, forKey: "realnameFlag" as NSCopying)
        dictionary.setObject(type, forKey: "type" as NSCopying)
        dictionary.setObject(status, forKey: "status" as NSCopying)
        return dictionary
    }
}
