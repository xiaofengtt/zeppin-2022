import extend from 'dva-model-extend';
import common from './common'
import { getTopscoreList } from "../service";

export default extend(common, {
  namespace: 'dataModel',
  state: {
    topScoreList: [],
  },
  reducers: {
  },
  effects:{
    * getTopscoreList({ payload }, { put, call }) {
      const { data } = yield call(getTopscoreList, { category:payload})
      yield put({ type: 'updateState', payload: { topScoreList: data }})
    }
  },
  subscriptions: {
  },
})