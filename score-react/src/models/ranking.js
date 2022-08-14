import extend from 'dva-model-extend';
import common from './common'
import { getStandingList } from "../service";

export default extend(common, {
  namespace: 'ranking',
  state: {
    standingList: [],
  },
  reducers: {
  },
  effects:{
    * getStandingList({ payload }, { put, call }) {
      const { data } = yield call(getStandingList, { category:payload})
      yield put({ type: 'updateState', payload: { standingList: data }})
    }
  },
  subscriptions: {
  },
})