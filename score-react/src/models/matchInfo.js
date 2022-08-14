import extend from 'dva-model-extend';
import common from './common'
import { getMatchInfo,getMatchClash,getMatchRecent,getNewsListRele } from "../service";

export default extend(common, {
  namespace: 'matchInfo',
  state: {
    matchInfo: [],
    matchClash:[],
    matchRecent:[],
    newsList:[],
  },
  reducers: {
  },
  effects:{
    * getMatchInfo({ payload }, { put, call }) {
      const { data } = yield call(getMatchInfo, payload)
      yield put({ type: 'updateState', payload: { matchInfo: data }})
    },
    * getMatchClash({ payload }, { put, call }) {
      const { data } = yield call(getMatchClash, payload)
      yield put({ type: 'updateState', payload: { matchClash: data }})
    },
    * getMatchRecent({ payload }, { put, call }) {
      const { data } = yield call(getMatchRecent, payload)
        yield put({ type: 'updateState', payload: { matchRecent: data }})     
    },
    * getNewsList({payload}, { put, call }) {
      const { data } = yield call(getNewsListRele, { pageSize: 4 ,pageNum:1,category:payload.category,except:payload.except})
      yield put({ type: 'updateState', payload: { newsList: data }})
    },
  },
  subscriptions: {
  },
})