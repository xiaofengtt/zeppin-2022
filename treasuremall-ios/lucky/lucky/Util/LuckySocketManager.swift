//
//  LuckySocketManager.swift
//  lucky
//  长连接管理
//  Created by Farmer Zhu on 2020/8/5.
//  Copyright © 2020 shopping. All rights reserved.
//

import SocketIO

class LuckySocketManager{
    
//    let urlString = "http://192.168.1.120:12345"
//    let adString = "http://192.168.1.120:12346"
    
    //马甲
    let adString = "https://apiweb.happyxmall.com"
    //正式
    let urlString = "https://api.happyxmall.com"
    
    var socketManager: SocketManager!
    var socket: SocketIOClient?
    var reconnectTimer: Timer!
    
    //初始化
    init() {
        //取token
        getToken { (token) in
            let url = globalFlagUser ? self.urlString : self.adString
            //创建连接管理
            self.socketManager = SocketManager(socketURL: URL(string: url)!, config: ["compress": true, "connectParams": ["token": token], "doubleEncodeUTF8": true])
            self.socket = self.socketManager.defaultSocket
            //注册事件
            self.socket!.on("put_event", callback: {(_ response: [Any]?, _ ack: SocketAckEmitter?) -> Void in
                if(response != nil){
                    let resDic: NSDictionary? = response![0] as? NSDictionary
                    if(resDic != nil){
                        let dataType = String.valueOf(any: resDic!["dataType"])
                        if(SocketDataType.lotteryList.rawValue == dataType){
                            //开奖记录列表
                            let dataString: String = String.valueOf(any: resDic!["data"])
                            self.updateLotteryList(dataString: dataString)
                        }else if(SocketDataType.winInfo.rawValue == dataType){
                            //获奖通知
                            let dataString: String = String.valueOf(any: resDic!["data"])
                            self.updateWinList(dataString: dataString)
                        }
                    }
                }
            })
            //进行链接
            self.socket!.on("connect", callback: {(_ response: [Any]?, _ ack: SocketAckEmitter?) -> Void in
                self.getData()
            })
            self.socket!.connect()
            
            //每10秒确认链接状态
            self.reconnectTimer = Timer.scheduledTimer(withTimeInterval: TimeInterval(10), repeats: true, block: { (timer) in
                if(self.socket!.status != SocketIOStatus.connected && self.socket!.status != SocketIOStatus.connecting){
                    //如果断开重连
                    self.socket!.connect()
                }
            })
        }
    }
    
    //获取开奖记录列表
    func getData(){
        let dic: NSDictionary = ["dataType": "lotteryList", "pageNum": 1, "pageSize": 20, "version": globalVersion, "channel": "appStore"]
        if(socket != nil){
            socket!.emit("push_event", "\(LuckyJsonUtils.dictionaryToJson(dictionary: dic))")
        }
    }
    
    //获取链接用token
    private func getToken(data: @escaping (_ data: String) -> ()){
        LuckyHttpManager.getTime { (time) in
            var timestamp = time
            if("" == timestamp){
                timestamp = "\(Date().timestamp)"
            }
            let desString = LuckyEncodingUtil.getDes("\(LuckyHttpManager.appKey)\(timestamp)")
            data(LuckyEncodingUtil.getBase64("\(LuckyHttpManager.device)\(timestamp)\(desString)"))
        }
    }
    
    //长连接事件类型
    private enum SocketDataType : String{
        case lotteryList = "lotteryList"
        case winInfo = "winInfo"
    }
    
    //更新开奖记录列表数据
    private func updateLotteryList(dataString: String){
        var dataList: [LuckyLuckygameGoodsIssueModel] = []
        let dataArray = LuckyJsonUtils.jsonToArray(jsonString: dataString)
        for data in dataArray{
            let dataDic = data as! NSDictionary
            let dataModel = LuckyLuckygameGoodsIssueModel(data: dataDic)
            if(!globalFlagUser && dataModel.status == "lottery"){
                continue
            }else{
                dataList.append(dataModel)
            }
        }
        socketLotteryList = dataList
        //广播通知
        NotificationCenter.default.post(name: NSNotification.Name.SocketLotteryList, object: nil)
    }
    
    //更新获奖记录
    private func updateWinList(dataString: String){
        var dataList: [LuckyWinningRollModel] = []
        let dataArray = LuckyJsonUtils.jsonToArray(jsonString: dataString)
        for data in dataArray{
            let dataDic = data as! NSDictionary
            let dataModel = LuckyWinningRollModel(data: dataDic)
            dataList.append(dataModel)
        }
        socketWinList = dataList
        //广播通知
        NotificationCenter.default.post(name: NSNotification.Name.SocketWinList, object: nil)
    }
}
