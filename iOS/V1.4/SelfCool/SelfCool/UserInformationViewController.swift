//
//  UserInformationViewController.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class UserInformationViewController: UIViewController ,UIActionSheetDelegate ,UIImagePickerControllerDelegate , UINavigationControllerDelegate , UIPickerViewDataSource , UIPickerViewDelegate , HttpDataProtocol{
    
    @IBOutlet weak var userImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var userAgeLabel: UILabel!
    @IBOutlet weak var userPhoneLabel: UILabel!
    @IBOutlet weak var userProfessionLabel: UILabel!
    @IBOutlet weak var userGenderView: UIView!
    @IBOutlet weak var manButton: UIButton!
    @IBOutlet weak var womanButton: UIButton!
    @IBOutlet weak var userBindButton: UIButton!
    @IBOutlet weak var changeBindButton: UIButton!
    
    var httpController = HttpController()
    let userImageSheetTag = 991
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        httpController.delegate = self
        self.navigationController?.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forBarMetrics: UIBarMetrics.Default)
        self.navigationController?.navigationBar.shadowImage = UIImage.imageWithColor(UIColor.clearColor())
        self.navigationController?.navigationBar.barStyle = UIBarStyle.Black
        UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.LightContent
        
        userImageView.layer.masksToBounds = true
        userImageView.layer.cornerRadius = (screenWidth / 5 - 10) / 2
        userGenderView.layer.masksToBounds = true
        userGenderView.layer.cornerRadius = 5
        userGenderView.layer.borderColor = UIColor.spaceLineGray().CGColor
        userGenderView.layer.borderWidth = 1
        
        manButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        manButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Selected)
        manButton.setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor()), forState: UIControlState.Normal)
        manButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Selected)
        womanButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        womanButton.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Selected)
        womanButton.setBackgroundImage(UIImage.imageWithColor(UIColor.whiteColor()), forState: UIControlState.Normal)
        womanButton.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forState: UIControlState.Selected)
        
        userImageView.image = userImage
        userNameLabel.text = user.name.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        if user.phone != ""{
            userPhoneLabel.text = user.phone
            userBindButton.removeFromSuperview()
        }else{
            userPhoneLabel.text = "未绑定"
            changeBindButton.removeFromSuperview()
        }
        if user.age > 0{
            userAgeLabel.text = "\(user.age)"
        }else{
            userAgeLabel.text = "未设置"
        }
        userProfessionLabel.text = user.profession.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        
        if user.gender == 0{
            manButton.selected = true
        }else if user.gender == 1{
            womanButton.selected = true
        }
        
    }
    
    override func viewDidAppear(animated: Bool) {
        userNameLabel.text = user.name.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        userProfessionLabel.text = user.profession.stringByReplacingPercentEscapesUsingEncoding(NSUTF8StringEncoding)
        MobClick.beginLogPageView("UserInformation")
    }
    
    override func viewDidDisappear(animated: Bool) {
        MobClick.endLogPageView("UserInformation")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func recieveError(recieveType: String, error: NSError?) {
        httpController.getDataError(self)
    }
    
    func recieveDataResults(recieveType: String,dataDictionary: NSDictionary ,inputParam : NSDictionary) {
    }
    
    @IBAction func manButton(sender: UIButton) {
        if user.gender != 0{
            manButton.selected = true
            womanButton.selected = false
            user.gender = 0
            updateGender()
        }
    }
    
    @IBAction func womanButton(sender: UIButton) {
        if user.gender != 1{
            manButton.selected = false
            womanButton.selected = true
            user.gender = 1
            updateGender()
        }
    }
    
    func updateGender(){
        let userDict = user.getDictionary()
        LocalDataController.writeLocalData("user", data: userDict)
        let updateParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "gender" : String(user.gender)])
        httpController.getNSDataByParams("userUpdate", paramsDictionary: updateParams)
    }
    
    @IBAction func changeAge(sender: UIButton) {
        let cancelButton = UIButton(frame: CGRectMake(0, 0, self.view.frame.width, self.view.frame.height))
        cancelButton.tag = 999
        cancelButton.addTarget(self, action: "cancelUpdateAge:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(cancelButton)
        
        let nibs : NSArray = NSBundle.mainBundle().loadNibNamed("ActionPickerView", owner: self, options: nil)
        let actionPickerView = nibs.lastObject as? UIView
        actionPickerView!.frame = CGRectMake(0, self.view.frame.height, screenWidth, 200)
        actionPickerView!.tag = 990
        let ageCancelButton = actionPickerView?.viewWithTag(992) as! UIButton
        let ageConfirmButton = actionPickerView?.viewWithTag(993) as! UIButton
        let agePicker = actionPickerView?.viewWithTag(994) as! UIPickerView
        
        ageCancelButton.addTarget(self, action: "cancelUpdateAge:", forControlEvents: UIControlEvents.TouchUpInside)
        ageConfirmButton.addTarget(self, action: "updateAge:", forControlEvents: UIControlEvents.TouchUpInside)
        
        agePicker.dataSource = self
        agePicker.delegate = self
        agePicker.showsSelectionIndicator = true
        if user.age > 0{
            agePicker.selectRow(user.age, inComponent: 0, animated: false)
        }else{
            agePicker.selectRow(18, inComponent: 0, animated: false)
        }
        self.view.addSubview(actionPickerView!)
        
        UIView.animateWithDuration(0.5, animations: { () -> Void in
            actionPickerView!.frame = CGRectMake(0, self.view.frame.height - 200, screenWidth, 200)
        })
    }
    
    func cancelUpdateAge(sender : UIButton){
        let actionPickerView = self.view.viewWithTag(990) as UIView?
        let cancelButton = self.view.viewWithTag(999) as! UIButton
        actionPickerView!.removeFromSuperview()
        cancelButton.removeFromSuperview()
    }
    
    func updateAge(sender : UIButton){
        let actionPickerView = self.view.viewWithTag(990) as UIView?
        let cancelButton = self.view.viewWithTag(999) as! UIButton
        let agePicker = actionPickerView!.viewWithTag(994) as! UIPickerView
        userAgeLabel.text = "\(agePicker.selectedRowInComponent(0))"
        user.age = agePicker.selectedRowInComponent(0)
        
        let userDict = user.getDictionary()
        LocalDataController.writeLocalData("user", data: userDict)
        actionPickerView!.removeFromSuperview()
        cancelButton.removeFromSuperview()
        let updateParams = NSDictionary(dictionary: ["user.id" : String(user.id) , "age" : String(user.age)])
        httpController.getNSDataByParams("userUpdate", paramsDictionary: updateParams)
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return 100
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return "\(row)"
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
        
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        imagePickerController.allowsEditing = true
        imagePickerController.sourceType = sourceType
        imagePickerController.navigationBar.setBackgroundImage(UIImage.imageWithColor(UIColor.mainColor()), forBarMetrics: UIBarMetrics.Default)
        imagePickerController.navigationBar.tintColor = UIColor.whiteColor()
        let attributes = [NSForegroundColorAttributeName : UIColor.whiteColor()]
        imagePickerController.navigationBar.titleTextAttributes = attributes
        self.presentViewController(imagePickerController, animated: true) {}
    }
    
    @IBAction func changeIcon(sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.Camera){
            let imageSheetView = UIActionSheet(title: nil, delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: nil, otherButtonTitles: "从相册选取","拍照")
            imageSheetView.tag = userImageSheetTag
            imageSheetView.showFromRect(CGRectMake(0, 0, screenWidth, screenHeight), inView: self.view, animated: true)
        }else{
            let imageSheetView = UIActionSheet(title: nil, delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: nil, otherButtonTitles: "从相册选取")
            imageSheetView.tag = userImageSheetTag
            imageSheetView.showFromRect(CGRectMake(0, 0, screenWidth, screenHeight), inView: self.view, animated: true)
        }
    }
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        picker.dismissViewControllerAnimated(true, completion: {})
        var imageIcon = info[UIImagePickerControllerEditedImage] as! UIImage
        if imageIcon.size.width > 300{
            imageIcon = imageIcon.imageWithScale(0.3)
        }
        userImage = imageIcon
        UserImageUpdate(imageIcon)
        self.userImageView.image = userImage
    }
    
    @IBAction func backButton(sender: UIBarButtonItem) {
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewControllerWithIdentifier("mainPageViewController") as! MainPageViewController
        self.presentViewController(vc, animated: true) { () -> Void in
            UIApplication.sharedApplication().statusBarStyle = UIStatusBarStyle.Default
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "modifyName"{
            let vc = segue.destinationViewController as! ModifyInfoViewController
            vc.model = "name"
        }else if segue.identifier == "modifyProfession"{
            let vc = segue.destinationViewController as! ModifyInfoViewController
            vc.model = "profession"
        }
    }
    
    @IBAction func close(segue: UIStoryboardSegue){}
}