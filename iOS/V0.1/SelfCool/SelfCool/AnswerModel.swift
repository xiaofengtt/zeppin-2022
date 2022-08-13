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
        self.completeType = 0
    }
}

func DictionaryToAnswer(dictionary: NSDictionary) -> AnswerModel{
    var answer = AnswerModel()
    answer.stem = dictionary.objectForKey("stem") as! String
    
    var options : [OptionModel] = []
    var optionArray = dictionary.objectForKey("options") as! NSArray
    for i in 0 ..< optionArray.count{
        var option = optionArray[i] as! NSDictionary
        var optionModel = OptionModel()
        optionModel.content = option.objectForKey("content") as! String
        optionModel.inx = String(option.objectForKey("inx") as! Int)
        options.append(optionModel)
    }
    answer.options = options
    
    var results : [OptionModel] = []
    var resultArray = dictionary.objectForKey("results") as! NSArray
    var result = resultArray[0] as! NSDictionary
    var resultInx = (result.objectForKey("inx") as! String).toInt()
    
    var resultModel = options[resultInx! - 1]
    results.append(resultModel)
    answer.results = results
    return answer
}