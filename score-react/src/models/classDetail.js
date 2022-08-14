import extend from 'dva-model-extend';
import common from './common'
import { getNewsDetail, getCommentByClassId, getNewsListRele, addFavorite,addComment,getUserInfo,getAllCommentByClassId } from '../service'

export default extend(common, {
    namespace: 'classDetail',
    state: {
        newsDetail: {},
        comments: [],
        isfavorite: false,
        category:'',
        newsList: [],
    },
    reducers: {
    },
    effects:{
        * getNewsDetail({ payload }, { put, call }) {
            const { data } = yield call(getNewsDetail, payload)
            yield put({ type: 'updateState', payload: { newsDetail: data ,category:data.category}})
            const result = yield call(getNewsDetail, payload)
            return result;
        },
        * getComment({ payload }, { put, call }) {
            const { data } = yield call(getCommentByClassId, { pageSize: 10 ,pageNum:1,newsPublish:payload})
            yield put({ type: 'updateState', payload: { comments: data }})
            const result = yield call(getCommentByClassId, { pageSize: 10 ,pageNum:1,newsPublish:payload})
            return result;
        },
        * getAllComment({ payload }, { put, call }) {
            const { data } = yield call(getAllCommentByClassId, { pageSize: 10 ,pageNum:1,newsPublish:payload})
            yield put({ type: 'updateState', payload: { comments: data }})
            const result = yield call(getCommentByClassId, { pageSize: 10 ,pageNum:1,newsPublish:payload})
            return result;
        },
        * getNewsList({payload}, { put, call }) {
            const { data } = yield call(getNewsListRele, { pageSize: 4 ,pageNum:1,category:payload.category,except:payload.except})
            yield put({ type: 'updateState', payload: { newsList: data }})
          },
        * favorite({ payload }, { put, call }) {
            return yield call(addFavorite, payload)
        },
        * addComment({ payload }, { put, call }) {
            return yield call(addComment, payload)
        },
        * getUserInfo({payload}, { put, call }) {
            const result = yield call(getUserInfo, {token:payload.token})
            return result;
        },
    },
    subscriptions: {
    },
})