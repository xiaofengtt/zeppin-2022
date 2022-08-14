import extend from 'dva-model-extend';
import common from './common'
import { getCollectTeamList,getConcrenList,addConcren,cancleConcren } from '../service'

export default extend(common, {
  namespace: 'favorites',
  state: {
    teamList: [],
    concrenList : []
  },
  reducers: {
  },
  effects:{
    * getCollectTeamList({ payload }, { put, call }) {
      const { data } = yield call(getCollectTeamList, payload)
      yield put({ type: 'updateState', payload: { teamList: data }})
    },
    * getConcrenList({ payload }, { put, call }) {
      const { data } = yield call(getConcrenList, payload)
      yield put({ type: 'updateState', payload: { concrenList: data}})
    },
    * addConcren({ payload }, { put, call }) {
      const data = yield call(addConcren, payload)
      yield put({ type: 'updateState', payload: {data:data}})
      return data;
    },
    * cancleConcren({ payload }, { put, call }) {
      const data = yield call(cancleConcren, payload)
      yield put({ type: 'updateState', payload: {data:data}})
      return data;
    },
  },
  subscriptions: {
  },
})