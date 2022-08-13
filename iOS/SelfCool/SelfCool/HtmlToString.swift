//
//  HtmlToString.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func HtmlRemoveTag(HtmlString :String) -> String{
    var result = HtmlString
    var scanner = NSScanner(string: result)
    var text : NSString? = nil
    while (scanner.atEnd == false){
        scanner.scanUpToString("<", intoString: nil)
        scanner.scanUpToString(">", intoString: &text)
        if text != nil{
            result = result.stringByReplacingOccurrencesOfString("\(text!)>", withString: "", options: nil, range: nil)
        }
    }
    return result
}

func HtmlToString(HtmlString :String) -> String{
    var result = HtmlRemoveTag(HtmlString)
    result = HtmlEscape(result)
    return result
}

func HtmlEscape(string: String) -> String{
    var result = string
    result = result.stringByReplacingOccurrencesOfString("&quot;", withString: "\"", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&#39;", withString: "'", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&lt;", withString: "<", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&gt;", withString: ">", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&ldquo;", withString: "\"", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&rdquo;", withString: "\"", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&mdash;", withString: "-", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&bull;", withString: "·", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&middot;", withString: "·", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&hellip;", withString: "…", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&lsquo;", withString: "‘", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&rsquo;", withString: "’", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&rarr;", withString: "→", options: nil, range: nil)
    result = result.stringByReplacingOccurrencesOfString("&larr;", withString: "←", options: nil, range: nil)
    return result
}

func HtmlGetImagesUrl(HtmlString: String) -> [String]{
    var results : [String] = []
    var scanner = NSScanner(string: HtmlString)
    var text : NSString? = nil
    while (scanner.atEnd == false){
        scanner.scanUpToString("data-src=\"http", intoString: nil)
        scanner.scanUpToString("\" />", intoString: &text)
        if text != nil{
            var isSame = false
            var url = String(text!).stringByReplacingOccurrencesOfString("data-src=\"", withString: "", options: nil, range: nil)
            for i in 0 ..< results.count{
                if results[i] == url{
                    isSame = true
                }
            }
            if !isSame{
                results .append(url)
            }
        }
    }
    return results
}