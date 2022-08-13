//
//  StandardItemSubmit.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/2.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController{
    func judgeIsCorrect(){
        correctCount = 0
        for index in 0 ..< globalItemList.count{
            var item = globalItemList[index]
            if item.modelType == 4{
                for i in 0 ..< item.children!.count{
                    if item.children![i].answer.userResults.count > 0{
                        if item.children![i].answer.userResults.count == item.children![i].answer.results.count{
                            var flag = true
                            for optionIndex in 0 ..< item.children![i].answer.userResults.count{
                                if item.children![i].answer.userResults[optionIndex] != item.children![i].answer.results[optionIndex]{
                                    flag = false
                                }
                            }
                            if flag {
                                item.children![i].answer.completeType = 1
                                item.children![i].userCorrectCount++
                                correctCount++
                            }else{
                                item.children![i].answer.completeType = 0
                                item.children![i].userWrongCount++
                            }
                        }else{
                            item.children![i].answer.completeType = 0
                            item.children![i].userWrongCount++
                        }
                    }else{
                        item.children![i].answer.completeType = 0
                        item.children![i].userWrongCount++
                    }
                }
            }else{
                if item.answer.userResults.count > 0{
                    if item.answer.userResults.count == item.answer.results.count{
                        var flag = true
                        for optionIndex in 0 ..< item.answer.userResults.count{
                            if item.answer.userResults[optionIndex] != item.answer.results[optionIndex]{
                                flag = false
                            }
                        }
                        if flag {
                            item.answer.completeType = 1
                            item.userCorrectCount++
                            correctCount++
                        }else{
                            item.answer.completeType = 0
                            item.userWrongCount++
                        }
                    }else{
                        item.answer.completeType = 0
                        item.userWrongCount++
                    }
                }else{
                    item.answer.completeType = 0
                    item.userWrongCount++
                }
            }
        }
    }

    func getUserAnswer() ->String{
        var result = "["
        for index in 0 ..< globalItemList.count{
            var item = globalItemList[index]
            if item.modelType == 4{
                for i in 0 ..< item.children!.count{
                    result += "{\"ssoUserTestItem.id\":"
                    result += String(item.children![i].ssoUserTestItemId)
                    if item.children![i].answer.userResults.count != 0{
                        result += ",\"completeType\":"
                        result += String(item.children![i].answer.completeType)
                        result += ",\"isAnswered\":"
                        result += "1"
                        result += ",\"reference\":\""
                        for i in 0 ..< item.children![i].answer.userResults.count{
                            result += item.children![i].answer.userResults[i].inx
                            if i != item.children![i].answer.userResults.count - 1{
                                result += ","
                            }
                        }
                        result += "\""
                    }else{
                        result += ",\"completeType\":"
                        result += String(0)
                        result += ",\"isAnswered\":"
                        result += "0"
                        result += ",\"reference\":\"\""
                    }
                    result += "}"
                }
            }else{
                result += "{\"ssoUserTestItem.id\":"
                result += String(item.ssoUserTestItemId)
                if item.answer.userResults.count != 0{
                    result += ",\"completeType\":"
                    result += String(item.answer.completeType)
                    result += ",\"isAnswered\":"
                    result += "1"
                    result += ",\"reference\":\""
                    for i in 0 ..< item.answer.userResults.count{
                        result += item.answer.userResults[i].inx
                        if i != item.answer.userResults.count - 1{
                            result += ","
                        }
                    }
                    result += "\""
                }else{
                    result += ",\"completeType\":"
                    result += String(0)
                    result += ",\"isAnswered\":"
                    result += "0"
                    result += ",\"reference\":\"\""
                }
                result += "}"
            }
            if index != globalItemList.count - 1 {
                result += ","
            }
        }
        result += "]"
        return result
    }
}