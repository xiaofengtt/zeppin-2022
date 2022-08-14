import Foundation

//设备渠道
let appChannel: String = "xshopping_ios"

//设备尺寸
var screenWidth: CGFloat = UIScreen.main.bounds.size.width
var screenHeight: CGFloat = UIScreen.main.bounds.size.height
var screenScale: CGFloat = UIScreen.main.bounds.size.width / 375
var statusBarHeight: CGFloat = UIApplication.shared.statusBarFrame.height
var bottomSafeHeight: CGFloat = UIApplication.shared.windows[0].safeAreaInsets.bottom

//长连接管理器
var socketManager: LuckySocketManager? = nil
//长连接记录
var socketLotteryList: [LuckyLuckygameGoodsIssueModel] = []
//长连接获得
var socketWinList: [LuckyWinningRollModel] = []
//国家区号
var globalCountryList: [LuckyInternationalInfoModel] = []

//热门开关（Categories用）
var flagJackpot = false

//用户信息
var globalUserData: LuckyFrontUserModel? = nil
//用户账户信息
var globalUserAccount: LuckyFrontUserAccountModel? = nil
//各种比率
var globalRate: LuckyFrontUserRateModel? = nil
//货币汇率
var globalCurrencyRate: LuckyCurrencyRateModel? = nil

//提现说明
var globalWithdrawExplanation: String? = nil
//充值说明
var globalChargeExplanation: String? = nil
//默认选择国家（区号）
var globalSelectedCountry: String? = nil
//本设备UUID
var globalDeviceUuid: String? = nil
//推送Token
var globalDeviceToken: String? = nil

//货币代码
var globalCurrencyCode: String = Locale.current.currencyCode == nil ? "USD" : Locale.current.currencyCode!
//国家代码
var globalCountryCode: String = Locale.current.regionCode == nil ? "US" : Locale.current.regionCode!

//是否主要
var globalFlagUser: Bool = false
//是否可提现
var globalFlagAuth: Bool = false
//版本号
var globalVersion: String = String.valueOf(any: Bundle.main.infoDictionary!["CFBundleShortVersionString"])
