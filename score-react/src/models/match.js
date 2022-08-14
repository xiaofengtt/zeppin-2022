import extend from 'dva-model-extend';
import common from './common'
import { getMatchList } from "../service";

export default extend(common, {
  namespace: 'match',
  state: {
    matchList: [],
  },
  reducers: {
  },
  effects:{
    * getMatchList({ payload }, { put, call }) {
      const { data } = yield call(getMatchList, { payload})
      yield put({ type: 'updateState', payload: { matchList: data }})
      return data;
    }
  },
  subscriptions: {
  },
})