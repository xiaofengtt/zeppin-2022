extension Notification.Name {
    //APP重回前台
    static let AppWillShow = Notification.Name(rawValue:"AppWillShow")
    //长连接 中奖列表刷新
    static let SocketLotteryList = Notification.Name(rawValue:"SocketLotteryList")
    //长连接 新开奖
    static let SocketWinList = Notification.Name(rawValue:"SocketWinList")
    //需刷新用户信息
    static let RefreshUserAccount = Notification.Name(rawValue:"RefreshUserAccount")
}
