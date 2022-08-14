import extend from 'dva-model-extend';
import common from './common'
import { login, sendSmsCode, register,logout,getUserInfo } from '../service'

export default extend(common, {
    namespace: 'app',
    state: {
    },
    reducers: {
    },
    effects:{
        * login({ payload }, { put, call }) {
            const result = yield call(login, payload)
            if (result.code === 0) {
                window.localStorage.setItem('user', JSON.stringify(result.data))
                return result;
            } else {
                return Promise.reject(result.msg)
            }
        },
        * sendSmsCode({ payload }, { call }) {
            const result = yield call(sendSmsCode, payload)
            if (result.code === 0) {
                return result;
            } else {
                return Promise.reject(result.msg)
            }
        },
        * register({ payload }, { call }) {
            const result = yield call(register, payload)
            if (result.code === 0) {
                window.localStorage.setItem('user', JSON.stringify(result.data))
                return result;
            } else {
                return Promise.reject(result.msg)
            }
        },
        *logout({payload}, { call }) {
            const result = yield call(logout, payload)
            return result;
        },
        * getUserInfo({payload}, { put, call }) {
            const result = yield call(getUserInfo, {token:payload.token})
            return result;
        },
    },
    subscriptions: {
    },
})