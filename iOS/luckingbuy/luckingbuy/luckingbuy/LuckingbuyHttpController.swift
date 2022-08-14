import Foundation
import AFNetworking
class LuckingbuyHttpController{
    static let baseUrl: String = "http://api.shanpaishop.com/"
    static let timeout: TimeInterval = 10
    static func getVersion(data: @escaping (_ data: NSDictionary) -> (), errors: @escaping (_ error: AnyObject) -> ()){
        let dictionary = NSMutableDictionary(dictionary: ["appid" : "fengkuangshangouIOS", "version" : Bundle.main.infoDictionary!["CFBundleShortVersionString"] as! String])
        LuckingbuyHttpController.getFromNet("apponoff/getversion", params: dictionary, data: { (adata) in
            let dic = adata as! NSDictionary
            let code = dic.object(forKey: "code") as! String
            if(code == "200"){
                data(dic["data"] as! NSDictionary)
            }else{
                errors("连接失败，请重试" as AnyObject)
            }
        },errors: { (error) in
            errors("连接失败，请重试" as AnyObject)
        })
    }
    static func getFromNet(_ url: String, params: NSDictionary, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
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
    static func postFromNet(_ url: String, params: NSDictionary!, data: @escaping (_ data: AnyObject) -> (), errors: @escaping (_ error: AnyObject) -> ()){
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
    static func getBasePath() -> String{
        return Bundle.main.url(forResource: "dist/index", withExtension: "html")!.absoluteString
    }
}
