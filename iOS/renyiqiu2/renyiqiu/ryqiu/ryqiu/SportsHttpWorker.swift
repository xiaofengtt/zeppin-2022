import Foundation
import AFNetworking
class SportsHttpWorker {
    static let timeout: TimeInterval = 10
    static func showTimeout(viewController: UIViewController){
        SportsAlertView(title: "连接失败，请重试").showByTime(time: 2)
    }
    static func showLoading(viewController: UIViewController) -> SportsLoadingView{
        let loadingView = SportsLoadingView()
        loadingView.show(animated: true)
        return loadingView
    }
    static func hideLoading(loadingView: SportsLoadingView){
        loadingView.hide(animated: true)
    }
    static func getTime(data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        self.get("loginFront/getTime", params: NSDictionary(), data: { (adata) in
            data(String((adata as! NSDictionary).value(forKey: "data") as! Int))
        },errors: { (error) in
            errors("连接失败，请重试" as AnyObject)
        })
    }
    static func getPrivate(data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        self.get("loginFront/getTime", params: NSDictionary(), data: { (adata) in
            let timestamp = String((adata as! NSDictionary).value(forKey: "data") as! Int)
            let codeString = secretKey + timestamp + user!.mobile
            data(SportsEncodingUtil.getBase64(systemType + timestamp + user!.mobile + codeString.md5))
        },errors: { (error) in
            errors("连接失败，请重试" as AnyObject)
        })
    }
    static func getUser(uuid: String, data: @escaping (_ data: String) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        if(user == nil){
            user = SportsFrontUserModel()
            user?.uuid = uuid
        }
        SportsHttpWorker.getPrivate(data: { (token) in
            let userParams = NSDictionary(dictionary: ["uuid" : uuid ,"token" : token])
            SportsHttpWorker.get("front/user/get", params: userParams, data: { (adata) in
                let dic = adata as! NSDictionary
                let status = dic.object(forKey: "status") as! String
                if(status == "SUCCESS"){
                    user = SportsFrontUserModel(data: dic.value(forKey: "data") as! NSDictionary)
                    SportsLocalDataController.writeLocalData("user", data: user!.getDictionary())
                    data("用户信息更新成功")
                }else{
                    user = nil
                    errors("连接失败，请重试" as AnyObject)
                }
            }, errors: { (error) in
                errors("连接失败，请重试" as AnyObject)
            })
        },errors: { (error) in
            errors("连接失败，请重试" as AnyObject)
        })
    }
    static func get(_ url: String, params: NSDictionary, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        var urlString = baseUrl + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let paramDic = NSMutableDictionary(dictionary: params)
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        manager.get(urlString, parameters: paramDic, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription as AnyObject)
        })
    }
    static func post(_ url: String, params: NSDictionary!, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        var urlString = baseUrl + url
        urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let paramDic = NSMutableDictionary(dictionary: params)
        let manager = AFHTTPSessionManager()
        manager.requestSerializer.timeoutInterval = timeout
        manager.responseSerializer.acceptableContentTypes = NSSet(objects: "text/plain", "text/json", "application/json","text/javascript","text/html", "application/javascript", "text/js") as? Set<String>
        manager.post(urlString, parameters: paramDic, progress: { (downloadProgress: Progress) -> Void in
        }, success: { (task: URLSessionDataTask, responseObject: Any?) -> Void in
            data(responseObject! as AnyObject)
        }, failure: { (task: URLSessionDataTask?, error : Error) -> Void in
            errors(error.localizedDescription as AnyObject)
        })
    }
}
