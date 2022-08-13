//
//  AnswerModel.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class AnswerModel : NSObject{
    var options: [OptionModel]
    var stem: String
    var results: [OptionModel]
    var userResults: [OptionModel]
    var completeType : Int
    
    override init() {
        self.options = []
        self.stem = ""
        self.results = []
        self.userResults = []
        self.completeType = -1
    }
    
    init(dictionary: NSDictionary , modelType: Int) {
        self.userResults = []
        self.completeType = -1
        if modelType != 4{
            self.stem = dictionary.objectForKey("stem") as! String
            var options : [OptionModel] = []
            var results : [OptionModel] = []
            if modelType == 1{
                let optionArray = dictionary.objectForKey("options") as! NSArray
                for i in 0 ..< optionArray.count{
                    let option = optionArray[i] as! NSDictionary
                    let optionModel = OptionModel()
                    optionModel.content = option.objectForKey("content") as! String
                    optionModel.inx = String(option.objectForKey("inx") as! Int)
                    options.append(optionModel)
                }
                self.options = options
                let resultArray = dictionary.objectForKey("results") as! NSArray
                let result = resultArray[0] as! NSDictionary
                let resultInx = Int((result.objectForKey("inx") as! String))
                let resultModel = options[resultInx! - 1]
                results.append(resultModel)
                self.results = results
            }else if modelType == 3{
                let trueModel = OptionModel(content: "正确", inx: "1")
                let falseModel = OptionModel(content: "错误", inx: "2")
                options.append(trueModel)
                options.append(falseModel)
                self.options = options
                let resultArray = dictionary.objectForKey("results") as! NSArray
                let result = resultArray[0] as! NSDictionary
                let resultInx = Int((result.objectForKey("inx") as! String))
                let resultModel = options[resultInx! - 1]
                results.append(resultModel)
                self.results = results
            }else if modelType == 5{
                let optionArray = dictionary.objectForKey("options") as! NSArray
                for i in 0 ..< optionArray.count{
                    let option = optionArray[i] as! NSDictionary
                    let optionModel = OptionModel()
                    optionModel.content = option.objectForKey("content") as! String
                    optionModel.inx = String(option.objectForKey("inx") as! Int)
                    options.append(optionModel)
                }
                self.options = options
                let resultArray = dictionary.objectForKey("results") as! NSArray
                let result = resultArray[0] as! NSDictionary
                let resultString = (result.objectForKey("inx") as! String)
                var array = resultString.componentsSeparatedByString(",")
                for i in 0 ..< array.count{
                    let resultModel = options[Int(array[i])! - 1]
                    results.append(resultModel)
                }
                self.results = results
            }else{
                self.stem = dictionary.objectForKey("stem") as! String
                self.options = []
                self.results = []
            }
        }else{
            //组合题
            self.options = []
            self.stem = ""
            self.results = []
        }
    }
}