//
//  LoadHttpData.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

protocol HttpDataProtocol{
    func recieveDataResults(recieveType : String,data : NSData ,inputParam : NSDictionary)
    func recieveError(recieveType : String,error : NSError)
}

class HttpController: NSObject{
    
    let timeoutInterval = 30.0
    var delegate : HttpDataProtocol?
    
    func getNSDataByParams(recieveType :String , paramsDictionary : NSDictionary) {
        
        var dataResource = NSData()
        var urlString = UrlBase
        urlString += (recieveTypeToUrl.objectForKey(recieveType) as! String )
        
        var params : [String] = paramsDictionary.allKeys as! [String]
        
        if params.count > 0{
            urlString += "?"
            for i in 0 ..< params.count {
                urlString += params[i]
                urlString += "="
                urlString += paramsDictionary.objectForKey(params[i]) as! String
                if i != (params.count - 1){
                    urlString += "&"
                }
            }
        }
        urlString = urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
        var url : NSURL = NSURL(string: urlString)!
        let request: NSMutableURLRequest = NSMutableURLRequest(URL: url)
        request.timeoutInterval = timeoutInterval
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue(), completionHandler:{
            (response, data, error) -> Void in
            
            if (error != nil) {
                self.delegate?.recieveError(recieveType, error: error)
            }else{
                self.delegate?.recieveDataResults(recieveType , data: data , inputParam :paramsDictionary)
            }
        })
    }
    
    func postNSDataByParams(recieveType :String , paramsDictionary : NSDictionary){
        var dataResource = NSData()
        var urlString = UrlBase
        urlString += (recieveTypeToUrl.objectForKey(recieveType) as! String )
        urlString = urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
        var url : NSURL = NSURL(string: urlString)!
        let request: NSMutableURLRequest = NSMutableURLRequest(URL: url)
        request.HTTPMethod = "post"
        request.timeoutInterval = timeoutInterval
        
        var params : [String] = paramsDictionary.allKeys as! [String]
        
        var json : String = ""
        for i in 0 ..< params.count {
            json += params[i]
            json += "="
            json += paramsDictionary.objectForKey(params[i]) as! String
            if i != (params.count - 1){
                json += "&"
            }
        }
        var postData = json.dataUsingEncoding(NSUTF8StringEncoding)
        request.HTTPBody = postData
        
        NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue(), completionHandler:{
            (response, data, error) -> Void in
            
            if (error != nil) {
                self.delegate?.recieveError(recieveType, error: error)
            }else{
                self.delegate?.recieveDataResults(recieveType , data: data , inputParam : paramsDictionary)
            }
        })
    }
    
    func getDataError(){
        var alert = UIAlertView(title: "网络不给力,无法获取数据!", message: "", delegate: self, cancelButtonTitle: "确认")
        alert.show()
    }
}