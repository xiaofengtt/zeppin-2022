/*
Navicat MySQL Data Transfer

Source Server         : xjjspxgl.zeppin.cn
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : xjttsss

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-11-22 17:14:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `teacher_signup_sizer`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_signup_sizer`;
CREATE TABLE `teacher_signup_sizer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ACTION_SCOPE` text NOT NULL COMMENT '作用域',
  `CONDITIONS` text NOT NULL COMMENT '筛选条件',
  `WEIGHT` int(7) NOT NULL COMMENT '权重',
  `RE_ACTION_SCOPE` text NOT NULL COMMENT '重复报名筛查范围',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_signup_sizer
-- ----------------------------
INSERT INTO `teacher_signup_sizer` VALUES ('1', '123', '-1', '2016-11-14 15:28:13', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"0\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":1,\"teachingAge\":{\"endTimeAge\":\"10\",\"startTimeAge\":\"1\"},\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('2', '123', '-1', '2016-11-02 17:41:27', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":{\"endTimeAge\":\"10\",\"startTimeAge\":\"1\"},\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('3', '123', '1', '2016-11-16 11:53:25', '{\"hours\":{\"endhours\":\"100\",\"starthours\":\"0\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":{\"endTimeAge\":\"10\",\"startTimeAge\":\"1\"},\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"100\",\"starthours\":\"0\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('4', '123', '0', '2016-11-16 18:59:44', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"3\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":{\"endTimeAge\":\"10\",\"startTimeAge\":\"1\"},\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('6', '123', '0', '2016-11-02 18:31:24', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":{\"endTimeAge\":\"10\",\"startTimeAge\":\"1\"},\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('7', '123', '0', '2016-11-03 17:24:41', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('8', '123', '0', '2016-11-04 10:36:23', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('9', '123', '0', '2016-11-04 11:18:26', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('10', '123', '0', '2016-11-04 11:18:33', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('11', '123', '0', '2016-11-04 11:19:04', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('12', '123', '0', '2016-11-04 11:21:34', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('13', '123', '0', '2016-11-04 11:22:24', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"50\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\",\"8\"]}', '10', '{\"hours\":null,\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000002\",\"350000003\",\"350000004\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('14', '123', '0', '2016-11-08 12:23:37', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('15', '123', '-1', '2016-11-08 12:25:03', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('16', '123', '-1', '2016-11-08 12:27:08', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('17', '123', '0', '2016-11-08 14:35:52', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"350000033\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\",\"3\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"1\",\"endAge\":\"30\"},\"jobTitle\":[\"31\",\"32\"],\"multiLanguage\":0,\"teachingAge\":null,\"teachingLanguage\":[\"1\",\"2\",\"3\"],\"teachingSubject\":[\"1\",\"2\",\"3\"]}', '10', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"100\"},\"projectLevel\":[\"1\",\"2\",\"3\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2014\",\"endyear\":\"2020\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('18', '20161114测试', '0', '2016-11-14 10:55:23', '{\"hours\":{\"endhours\":\"9\",\"starthours\":\"6\"},\"projectLevel\":[\"1\"],\"subject\":[\"7\"],\"projectType\":[\"350000017\"],\"project\":[\"350000164\"],\"years\":{\"startyear\":\"2017\",\"endyear\":\"2023\"}}', '{\"teachingGrade\":[\"7\"],\"ethnic\":[\"1\"],\"police\":[\"1\"],\"teacherAge\":{\"startAge\":\"0\",\"endAge\":\"0\"},\"jobTitle\":[\"23\"],\"multiLanguage\":1,\"teachingAge\":{\"endTimeAge\":\"0\",\"startTimeAge\":\"0\"},\"teachingLanguage\":[\"1\"],\"teachingSubject\":[\"10\"]}', '4', '{\"hours\":{\"endhours\":\"9\",\"starthours\":\"6\"},\"projectLevel\":[\"2\"],\"subject\":[\"all\"],\"projectType\":[\"350000017\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"1\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2017\",\"endyear\":\"2023\"}}');
INSERT INTO `teacher_signup_sizer` VALUES ('19', '筛查器测试', '-1', '2016-11-14 15:38:35', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"0\"},\"projectLevel\":[\"all\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"years\":{\"startyear\":\"2015\",\"endyear\":\"2020\"}}', '{\"teachingGrade\":[\"1\",\"2\"],\"ethnic\":[\"1\",\"2\"],\"police\":[\"1\",\"2\"],\"teacherAge\":{\"startAge\":\"0\",\"endAge\":\"30\"},\"jobTitle\":[\"23\",\"31\"],\"multiLanguage\":1,\"teachingAge\":{\"endTimeAge\":\"15\",\"startTimeAge\":\"0\"},\"teachingLanguage\":[\"1\",\"2\"],\"teachingSubject\":[\"10\",\"12\"]}', '1', '{\"hours\":{\"endhours\":\"1000\",\"starthours\":\"0\"},\"projectLevel\":[\"all\"],\"subject\":[\"all\"],\"projectType\":[\"all\"],\"project\":[\"all\"],\"trainingCount\":{\"count1\":\"5\",\"count2\":\"1\",\"count3\":\"1\"},\"years\":{\"startyear\":\"2015\",\"endyear\":\"2020\"}}');
