//
//  ImageController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/6.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

var userImage : UIImage?

func UserImageDownload(name: String , urlString: String){
    let url = NSURL(string: urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)
    let data = try? NSData(contentsOfURL: url!, options: NSDataReadingOptions())
    if data != nil{
        userImage = UIImage(data: data!)
    }else{
        userImage = UIImage(named: "defult_user_image")
    }
}

func UserImageUpdate(image : UIImage){
    var urlString = UrlBase + "admin/ssoUserUploadImage"
    
    urlString = urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!
    let url : NSURL = NSURL(string: urlString)!
    
    var imageData = NSData()
    var imageFormat : String
    if ((UIImagePNGRepresentation(image)) != nil){
        imageData = UIImagePNGRepresentation(image)!
        imageFormat = "png"
    }else{
        imageData = UIImageJPEGRepresentation(image, 1.0)!
        imageFormat = "jpeg"
    }
    
    let Boundary : NSString = "AaB03x"
    let startBoundary = NSString(format: "--%@", Boundary)
    let endBoundary = NSString(format: "%@--", startBoundary)
    
    let body = NSMutableString()
    let postParems = NSDictionary(dictionary: ["user.id" : String(user.id)])
    var keys = postParems.allKeys
    for i in 0 ..< keys.count{
        let key = keys[i] as! String
        
        body.appendFormat("%@\r\n", startBoundary)
        body.appendFormat("Content-Disposition: form-data; name=\"%@\"\r\n\r\n",key)
        body.appendFormat("%@\r\n", postParems.objectForKey(key) as! String)
    }
    
    body.appendFormat("%@\r\n" , startBoundary)
    body.appendFormat("Content-Disposition: form-data; name=\"file\"; filename=\"userIcon.\(imageFormat)\"\r\n")
    body.appendFormat("Content-Type: image/\(imageFormat)\r\n\r\n")
    
    let end = NSString(format: "\r\n%@\r\n", endBoundary)
    let requestData = NSMutableData()
    requestData.appendData(body.dataUsingEncoding(NSUTF8StringEncoding)!)
    requestData.appendData(imageData)
    requestData.appendData(end.dataUsingEncoding(NSUTF8StringEncoding)!)
    
    let request: NSMutableURLRequest = NSMutableURLRequest(URL: url)
    request.HTTPMethod = "POST"
    request.setValue("multipart/form-data; boundary=\"AaB03x\"; charset=\"UTF-8\"", forHTTPHeaderField: "Content-Type")
    request.setValue("\(requestData.length)" , forHTTPHeaderField: "Content-Length")
    request.HTTPBody = requestData
    
    NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue()) { (response, data, error) -> Void in
        if (error != nil) {
        }else{
            let dictionary = (try? NSJSONSerialization.JSONObjectWithData(data!, options: NSJSONReadingOptions.MutableLeaves)) as? NSDictionary
            if data!.length != 0 && dictionary != nil{
                let status = dictionary!.objectForKey("Status") as! String
                if status == "success" {
                    let dataDictionary = dictionary!.objectForKey("Records") as! NSDictionary
                    let imagePath = dataDictionary.objectForKey("url") as! String
                    user.iconUrl = imagePath
                    let userDict = user.getDictionary()
                    LocalDataController.writeLocalData("user", data: userDict)
                }
            }
        }
    }
}

func saveImageFromUrl(imagePath : String, imageName : String ,urlString : String) -> UIImage?{
    let url = NSURL(string: urlString.stringByAddingPercentEscapesUsingEncoding(NSUTF8StringEncoding)!)
    let data = try? NSData(contentsOfURL: url!, options: NSDataReadingOptions())
    if data != nil{
        saveImage(imagePath, imageName: imageName, imageData: data!)
        return UIImage(data: data!)
    }else{
        return nil
    }
}

func saveImage(imagePath : String, imageName : String , imageData : NSData){
    let path = NSBundle.mainBundle().bundlePath.stringByAppendingString("/\(imagePath)_\(imageName).png")
    imageData.writeToFile(path, atomically: true)
}

func getImageFromPath(imagePath: String , imageName : String) -> UIImage? {
    let path = NSBundle.mainBundle().bundlePath.stringByAppendingString("/\(imagePath)_\(imageName).png")
    let data = try? NSData(contentsOfFile: path, options: NSDataReadingOptions())
    if data != nil{
        return UIImage(data: data!)
    }else{
        return nil
    }
}