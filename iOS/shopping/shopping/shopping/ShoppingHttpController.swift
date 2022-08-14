import Foundation
import AFNetworking
class ShoppingHttpController{
    static let baseUrl = ""
    static let timeout: TimeInterval = 10
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
    static func getBasePath() -> String{
        return Bundle.main.url(forResource: "dist/index", withExtension: "html")!.absoluteString
    }
}
