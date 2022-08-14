import extend from 'dva-model-extend';
import common from './common'
import { getMatchList, getNewsList,getMatchListNocategory } from "../service";

export default extend(common, {
  namespace: 'home',
  state: {
    newsList: [],
    matchList: [],
  },
  reducers: {
  },
  effects:{
    * getNewsList({ payload }, { put, call }) {
      const { data } = yield call(getNewsList, { pageSize: payload.pageSize ,pageNum:payload.pageNum,category:payload.category})
      yield put({ type: 'updateState', payload: { newsList: data }})
      return data;
    },
    * getMatchList({ payload }, { put, call }) {
      const { data } = yield call(getMatchList, { payload})
      yield put({ type: 'updateState', payload: { matchList: data }})
    },
    * getMatchListNocategory({ payload }, { put, call }) {
      const { data } = yield call(getMatchListNocategory, { pageSize: payload.pageSize,pageNum:payload.pageNum,category:payload.category,startTime:payload.startTime,endTime:payload.endTime,sort:payload.sort})
      yield put({ type: 'updateState', payload: { matchList: data }})
    }
  },
  subscriptions: {
  },
})