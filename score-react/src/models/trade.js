import extend from 'dva-model-extend';
import common from './common'
import { addTrade, getTrade, updateTrade } from '../service'

export default extend(common, {
  namespace: 'trade',
  state: {
    trades: [],
  },
  reducers: {
  },
  effects:{
    * addTrade({ payload }, { put, call }) {
      const result = yield call(addTrade, payload)
      return result;
    },
    * updateTrade({ payload }, { put, call }) {
      const result = yield call(updateTrade, payload)
      return result;
    },
    * getTrade({ payload }, { put, call }) {
      const { data } = yield call(getTrade, payload)
      yield put({ type: 'updateState', payload: { trades: data }})
    }
  },
  subscriptions: {
  },
})