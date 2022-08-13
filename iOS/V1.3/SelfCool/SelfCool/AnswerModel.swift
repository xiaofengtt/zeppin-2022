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
                var optionArray = dictionary.objectForKey("options") as! NSArray
                for i in 0 ..< optionArray.count{
                    var option = optionArray[i] as! NSDictionary
                    var optionModel = OptionModel()
                    optionModel.content = option.objectForKey("content") as! String
                    optionModel.inx = String(option.objectForKey("inx") as! Int)
                    options.append(optionModel)
                }
                self.options = options
                var resultArray = dictionary.objectForKey("results") as! NSArray
                var result = resultArray[0] as! NSDictionary
                var resultInx = (result.objectForKey("inx") as! String).toInt()
                var resultModel = options[resultInx! - 1]
                results.append(resultModel)
                self.results = results
            }else if modelType == 3{
                var trueModel = OptionModel(content: "正确", inx: "1")
                var falseModel = OptionModel(content: "错误", inx: "2")
                options.append(trueModel)
                options.append(falseModel)
                self.options = options
                var resultArray = dictionary.objectForKey("results") as! NSArray
                var result = resultArray[0] as! NSDictionary
                var resultInx = (result.objectForKey("inx") as! String).toInt()
                var resultModel = options[resultInx! - 1]
                results.append(resultModel)
                self.results = results
            }else if modelType == 5{
                var optionArray = dictionary.objectForKey("options") as! NSArray
                for i in 0 ..< optionArray.count{
                    var option = optionArray[i] as! NSDictionary
                    var optionModel = OptionModel()
                    optionModel.content = option.objectForKey("content") as! String
                    optionModel.inx = String(option.objectForKey("inx") as! Int)
                    options.append(optionModel)
                }
                self.options = options
                var resultArray = dictionary.objectForKey("results") as! NSArray
                var result = resultArray[0] as! NSDictionary
                var resultString = (result.objectForKey("inx") as! String)
                var array = resultString.componentsSeparatedByString(",")
                for i in 0 ..< array.count{
                    var resultModel = options[array[i].toInt()! - 1]
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