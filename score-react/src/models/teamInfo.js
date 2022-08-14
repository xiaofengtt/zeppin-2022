import extend from 'dva-model-extend';
import common from './common'
import { getTeamInfo,getMatchClash,getMatchRecent,getTeamNewsList,getTeamConcren } from "../service";

export default extend(common, {
    namespace: 'teamInfo',
    state: {
      teamInfo: [],
      matchClash:[],
      matchRecent:[],
      newsList:[],
    },
    reducers: {
    },
    effects:{
      * getTeamInfo({ payload }, { put, call }) {
        const { data } = yield call(getTeamInfo, payload)
        yield put({ type: 'updateState', payload: { teamInfo: data }})
        return data;
      },
      * getMatchClash({ payload }, { put, call }) {
        const { data } = yield call(getMatchClash, payload)
        yield put({ type: 'updateState', payload: { matchClash: data }})
      },
      * getTeamConcren({ payload }, { put, call }) {
        const { data } = yield call(getTeamConcren, {team:payload.team,token:payload.token})
        return data;    
      },
      * getTeamNewsList({payload}, { put, call }) {
        const { data } = yield call(getTeamNewsList, { pageSize: payload.pageSize ,pageNum:payload.pageNum,category:payload.category,team:payload.team})
        yield put({ type: 'updateState', payload: { newsList: data }})
      },
    },
    subscriptions: {
    },
  })