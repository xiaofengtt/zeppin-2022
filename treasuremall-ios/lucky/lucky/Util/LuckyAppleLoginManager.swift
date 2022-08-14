//
//  LuckyAppleLoginManager.swift
//  lucky
//
//  Created by Farmer Zhu on 2021/1/20.
//  Copyright © 2021 shopping. All rights reserved.
//


import UIKit
import AuthenticationServices

@available(iOS 13.0, *)
class LuckyAppleLoginManager: NSObject{

    static var shared = LuckyAppleLoginManager()
    
    private var callBack:((Bool,String)->Void)?
    
    //发起苹果登录
    func loginInWithApple(callBack:((Bool,String)->Void)?) {
      self.callBack = callBack
      // 基于用户的Apple ID授权用户，生成用户授权请求的一种机制
      let appleIDProvide = ASAuthorizationAppleIDProvider()
      // 授权请求AppleID
      let appIDRequest = appleIDProvide.createRequest()
      // 在用户授权期间请求的联系信息
        appIDRequest.requestedScopes = [ASAuthorization.Scope.fullName,ASAuthorization.Scope.email]
      // 由ASAuthorizationAppleIDProvider创建的授权请求 管理授权请求的控制器
      let authorizationController = ASAuthorizationController.init(authorizationRequests: [appIDRequest])
      // 设置授权控制器通知授权请求的成功与失败的代理
      authorizationController.delegate = self
      // 设置提供 展示上下文的代理，在这个上下文中 系统可以展示授权界面给用户
      authorizationController.presentationContextProvider = self
      // 在控制器初始化期间启动授权流
      authorizationController.performRequests()
    }
    
    // 如果存在iCloud Keychain 凭证或者AppleID 凭证提示用户
    func perfomExistingAccountSetupFlows() {
        // 基于用户的Apple ID授权用户，生成用户授权请求的一种机制
       let appleIDProvide = ASAuthorizationAppleIDProvider()
       // 授权请求AppleID
       let appIDRequest = appleIDProvide.createRequest()
       // 为了执行钥匙串凭证分享生成请求的一种机制
       let passwordProvider = ASAuthorizationPasswordProvider()
       let passwordRequest = passwordProvider.createRequest()
       // 由ASAuthorizationAppleIDProvider创建的授权请求 管理授权请求的控制器
       let authorizationController = ASAuthorizationController.init(authorizationRequests: [appIDRequest,passwordRequest])
       // 设置授权控制器通知授权请求的成功与失败的代理
       authorizationController.delegate = self
       // 设置提供 展示上下文的代理，在这个上下文中 系统可以展示授权界面给用户
       authorizationController.presentationContextProvider = self
       // 在控制器初始化期间启动授权流
       authorizationController.performRequests()
    }
}


@available(iOS 13.0, *)
extension LuckyAppleLoginManager : ASAuthorizationControllerDelegate,ASAuthorizationControllerPresentationContextProviding {
    //授权成功地回调
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if authorization.credential.isKind(of: ASAuthorizationAppleIDCredential.classForCoder()) {
            let appleIDCredential = authorization.credential as! ASAuthorizationAppleIDCredential
            
            // 只有首次授权可取得
            let familyName = appleIDCredential.fullName?.familyName ?? ""
            let givenName = appleIDCredential.fullName?.givenName ?? ""
            
            // 昵称
            var nickname = ""
            if(givenName != ""){
                if(familyName != ""){
                    nickname = "\(givenName) \(familyName)"
                }else{
                    nickname = givenName
                }
            }else if(familyName != ""){
                nickname = familyName
            }
            
            // token
            var tokenStr = ""
            if let token = appleIDCredential.identityToken{
                tokenStr = String(NSString(data: token, encoding: String.Encoding.utf8.rawValue) ?? "")
            }
            
            //登录
            LuckyHttpManager.loginByApple(user: appleIDCredential.user, identityToken: tokenStr, nickname: nickname) { (userModel) in
                //储存用户信息
                globalUserData = userModel
                LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                self.callBack?(true, "")
            } fail: { (reason) in
                self.callBack?(false, reason)
            }
        }else{
            callBack?(false, NSLocalizedString("Sign in failed", comment: ""))
        }
    }
    
    //登录失败
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        callBack?(false, NSLocalizedString("Sign in failed", comment: ""))
    }
    
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return UIApplication.shared.windows.last ?? ASPresentationAnchor()
    }
}
