import extend from 'dva-model-extend';
import common from './common'
import { getTime,sendSmsCode,login } from "../service";

export default extend(common, {
  namespace: 'login',
  state: {
    time : '',
    sendSms : {}
  },
  reducers: {
  },
  effects:{
    * getTime({ payload }, { put, call }) {
      const  datas = yield call(getTime, {})
      const  {data} = yield call(getTime, {})
      yield put({ type: 'updateState', payload: {time:data}})
      return datas;

    },
    * sendSms({ payload }, { put, call }) {
      const data = yield call(sendSmsCode, payload)
      yield put({ type: 'updateState', payload: { }})
      return data;
    },
    * loginBtn({ payload }, { put, call }) {
      const data = yield call(login, payload)
      yield put({ type: 'updateState', payload: {}})
      return data;
    }

  },
  subscriptions: {
  },
})