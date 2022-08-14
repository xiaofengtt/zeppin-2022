import UIKit
let baseUrl = "https://api.ttscore.cn/"
let systemType = "01"
let secretKey = "5fa526a2bb401ffddb888d1f0445ec6a"
var screenWidth: CGFloat = UIScreen.main.bounds.size.width
var screenHeight: CGFloat = UIScreen.main.bounds.size.height
var screenScale: CGFloat = UIScreen.main.bounds.size.width / 375
var bottomSafeHeight: CGFloat = UIApplication.shared.keyWindow!.safeAreaInsets.bottom
let league: Array = ["中超_f4e00df4-6f3f-4de3-b64e-0b2b6910e07e",
                   "英超_9bd4e736-e57f-46d2-ab25-b91a4c36b061",
                   "西甲_dad45ea2-5c9d-4102-8445-b9720267f93d",
                   "意甲_adf1fb28-306d-4870-96e9-875402d044b7",
                   "德甲_42fee8ba-677f-4152-b10c-69d3befc6467",
                   "欧冠_5f61cb0b-8d40-4449-9d25-cbcddde89a57",
                   "亚冠_5c3a7159-70e5-490e-b242-328c2f5c3cc1"]
var user : SportsFrontUserModel? = nil
class CategoryUuid{
    static let OUGUAN = "5f61cb0b-8d40-4449-9d25-cbcddde89a57"
    static let YINGCHAO = "9bd4e736-e57f-46d2-ab25-b91a4c36b061"
    static let XIJIA = "dad45ea2-5c9d-4102-8445-b9720267f93d"
    static let YIJIA = "adf1fb28-306d-4870-96e9-875402d044b7"
    static let DEJIA = "42fee8ba-677f-4152-b10c-69d3befc6467"
    static let ZHONGCHAO = "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e"
    static let YAGUAN = "5c3a7159-70e5-490e-b242-328c2f5c3cc1"
}
