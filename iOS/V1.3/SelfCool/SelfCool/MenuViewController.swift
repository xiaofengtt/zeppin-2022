//
//  MenuViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/24.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class MenuViewController: UIViewController , UIActionSheetDelegate ,UIImagePickerControllerDelegate , UINavigationControllerDelegate{
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func changeIcon(sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.Camera){
            var selectSheetView = UIActionSheet(title: nil, delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: nil, otherButtonTitles: "从相册选取","拍照")
            selectSheetView.showFromRect(CGRectMake(0, 0, screenWidth, screenHeight), inView: self.view, animated: true)
        }else{
            var selectSheetView = UIActionSheet(title: nil, delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: nil, otherButtonTitles: "从相册选取")
            selectSheetView.showFromRect(CGRectMake(0, 0, screenWidth, screenHeight), inView: self.view, animated: true)
        }
    }
    
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int) {
        var sourceType : UIImagePickerControllerSourceType
        if buttonIndex == 1{
            sourceType = UIImagePickerControllerSourceType.PhotoLibrary
        }else if buttonIndex == 2{
            sourceType = UIImagePickerControllerSourceType.Camera
        }else{
            return
        }
        
        var imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        imagePickerController.allowsEditing = true
        imagePickerController.sourceType = sourceType
        self.presentViewController(imagePickerController, animated: true) {}
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [NSObject : AnyObject]) {
        picker.dismissViewControllerAnimated(true, completion: {})
        var imageIcon = info[UIImagePickerControllerEditedImage] as! UIImage
        userImage = imageIcon
        var mainPageViewController = self.parentViewController as! MainPageViewController
    }
}