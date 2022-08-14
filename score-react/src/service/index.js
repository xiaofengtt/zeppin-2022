import queryString from 'query-string'

import request from '../utils/request'
import { prefix } from '../config'
import Base64  from 'base-64';
import MD5  from 'md5';

const app = `${prefix}/front`
const loginFront = `${prefix}/loginFront`
const user = `${prefix}/user`
const class_category = `${prefix}/class_category`
const class_second_category = `${prefix}/class_second_category`
const clazz = `${prefix}/class`
const comment = `${prefix}/front/news/commentList`
const addcomment = `${prefix}/front/news/comment`
const trade = `${prefix}/trade`
const exam = `${prefix}/exam`
const teacher = `${prefix}/teacher`
const favorite = `${prefix}/favorite`
const newsList = `${prefix}/front/news/list`
const matchList = `${prefix}/front/match/list`
const matchInfo = `${prefix}/front/match/get`
const newsDetail = `${prefix}/front/news/get`
const standingList = `${prefix}/front/category/standingList`
const topscoreList = `${prefix}/front/category/topscoreList`
const categoryList = `${prefix}/front/category`
const concrenList = `${prefix}/front/concren`

const getUser = () => {
    return JSON.parse(window.localStorage.getItem('user'))
}

// 登录
export async function login(user) {
    let mobile = Base64.encode(user.mobile);
    let code = Base64.encode('code');
    let key = '5fa526a2bb401ffddb888d1f0445ec6a';
    let md5 = MD5(key+user.time+user.mobile+user.userCode);
    let token = Base64.encode('01'+user.time+md5);
    let param = {
        mobile : mobile,
        token : token
    }
    return await request(`${loginFront}/login`, {
        method: 'POST',
        body: param,
    })
}

// 发送注册验证码
export async function sendSmsCode(user) {
    let mobile = Base64.encode(user.mobile);
    let code = Base64.encode('code');
    let key = '5fa526a2bb401ffddb888d1f0445ec6a';
    let md5 = MD5(key+user.time+user.mobile+`code`);
    let token = Base64.encode('01'+user.password+user.time+md5);
    return await request(`${app}/sms/sendCode?mobile=`+mobile+`&codeType=`+code+`&token=`+token);
}

// 获取时间戳
export async function getTime() {
    return await request(`${loginFront}/getTime`);
}
// 注册
export async function register({ code, ...user }) {
    return await request(`${app}/register?code=${code}`, {
        method: 'POST',
        body: user,
    })
}

// 获取全部用户
export async function getAllUser() {
    return await request(`${user}`)
}

// 修改个人资料
export async function updateUser({ userId, formData }) {
    return await request(`${user}/${userId}`, {
        method: 'PUT',
        body: formData,
    })
}

// 获取课程一级类目
export async function getClassCategory() {
    return await request(class_category)
}
// 获取课程二级类目
export async function getClassSecondCategory({ categoryId }) {
  return await request(`${class_second_category}?${queryString.stringify({ categoryId })}`)
}
// 获取课程列表
export async function getClass({ secondCategoryId }) {
    return await request(`${clazz}?${queryString.stringify({ secondCategoryId })}`)
}
// 获取课程详情
export async function getClassDetail({ classId }) {
    return await request(`${clazz}/${classId}`)
}
// 获取推荐课程
export async function getCommendClass({ count }) {
  return await request(`${clazz}/commend?count=${count}`)
}
// 是否收藏
export async function getFavorite(payload) {
  const param = {
    userId: getUser().userId,
    ...payload,
  }
  return await request(`${favorite}?${queryString.stringify(param)}`)
}

// 收藏
export async function addFavorite(payload) {
  const param = {
    userId: getUser().userId,
    ...payload,
  }
  return await request(`${favorite}`, {
      method: 'POST',
      body: param,
  })
}

// 课程搜索
export async function searchClass({ name }) {
  return await request(`${clazz}/search?name=${name}`)
}
// 获取课程评论
export async function getCommentByClassId({pageSize,pageNum,newsPublish}) {
    return await request(`${comment}?pageSize=${pageSize}&pageNum=${pageNum}&newsPublish=${newsPublish.classId}`)
}
// 获取课程评论
export async function getAllCommentByClassId({pageNum,newsPublish}) {
    return await request(`${comment}?pageNum=${pageNum}&newsPublish=${newsPublish.classId}`)
}
// 评论
export async function addComment(payload) {
    return await request(`${addcomment}`, {
        method: 'POST',
        body: {
            ...payload,
        },
    })
}

// 根据课程实例查询
export async function getTrade(payload) {
    const param = {
        userId: getUser().userId,
        ...payload,
    }
    return await request(`${trade}?${queryString.stringify(param)}`);
}

// 购买（未付款）
export async function addTrade(payload) {
    return await request(`${trade}`, {
        method: 'POST',
        body: {
            userId: getUser().userId,
            ...payload,
        }
    })
}

// 购买（付款）
export async function updateTrade(payload) {
  return await request(`${trade}`, {
      method: 'PUT',
      body: {
            userId: getUser().userId,
            ...payload,
      }
  })
}

// 获取试题
export async function getExam() {
    return await request(`${exam}`)
}

// 获取题目
export async function getQuestions({ examId }) {
    return await request(`${exam}/${examId}`)
}

// 获取全部教师
export async function getAllTeacher() {
    return await request(`${teacher}`)
}

// 获取新闻列表
export async function getNewsList({pageSize,pageNum,category}) {
    return await request(`${newsList}?pageSize=${pageSize}&pageNum=${pageNum}&category=${category}&sort=checktime desc`)
}
// 获取新闻列表--相关推荐
export async function getNewsListRele({pageSize,pageNum,category,except}) {
    return await request(`${newsList}?pageSize=${pageSize}&pageNum=${pageNum}&category=${category}&except=${except}&sort=checktime desc`)
}
// 获取赛事列表
export async function getMatchList(payload) {
    return await request(`${matchList}?${payload.payload}`)
}
// 获取赛事列表--不传category的
export async function getMatchListNocategory({pageSize,pageNum,category}) {
    return await request(`${matchList}?pageSize=${pageSize}&pageNum=${pageNum}&sort=time desc`)
}
// 获取赛事列表
export async function getMatchInfo(payload) {
    return await request(`${matchInfo}?uuid=${payload.uuid}`)
}
// 获取赛事列表--历史交锋
export async function getMatchClash(payload) {
    return await request(`${matchList}?teamToTeam=${ payload.hometeam+','+payload.awayteam}&status=finished&pageSize=5&pageNum=1&sort=time desc`)
}
// 获取赛事列表--近期战绩
export async function getMatchRecent(payload) {
    return await request(`${matchList}?team=${payload.hometeam}&status=finished&pageSize=5&pageNum=1&sort=time desc`)
}
//新闻内容详情
export async function getNewsDetail(payload) {
    return await request(`${newsDetail}?uuid=${payload.classId}`);
}
//排名积分榜
export async function getStandingList({category}) {
    return await request(`${standingList}?category=${category}`);
}
//数据射手榜
export async function getTopscoreList({category}) {
    return await request(`${topscoreList}?category=${category}`);
}
//关注--获取球队列表
export async function getCollectTeamList(category) {
    return await request(`${categoryList}/teamList?category=${category.uuid}`);
}
//关注--已关注列表
export async function getConcrenList(category) {
    return await request(`${concrenList}/list?token=${category.token}`);
}
//关注--添加关注
export async function addConcren(category) {
    return await request(`${concrenList}/add?team=${category.uuid}&token=${category.token}`);
}

//关注--取消关注
export async function cancleConcren(category) {
    return await request(`${concrenList}/cancel?userConcern=${category.uuid}&token=${category.token}`);
}

//获取用户信息
export async function getUserInfo(category) {
    return await request(`${app}/user/get?token=${category.token}`);
}
//退出登录
export async function logout() {
    return await request(`${loginFront}/logout`);
}

//球队信息
export async function getTeamInfo(payload){
    return await request(`${app}/team/get?uuid=${payload.uuid}`);
}
// 获取新闻列表
export async function getTeamNewsList({pageSize,pageNum,category,team}) {
    return await request(`${newsList}?pageSize=${pageSize}&pageNum=${pageNum}&category=${category}&sort=checktime desc&team=${team}`)
}
//球队信息是否关注
export async function getTeamConcren(payload){
    return await request(`${app}/concren/check?team=${payload.team}&token=${payload.token}`);
}
