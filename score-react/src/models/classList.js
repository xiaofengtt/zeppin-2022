import extend from 'dva-model-extend';
import common from './common'
import { getClass } from '../service'

export default extend(common, {
    namespace: 'classList',
    state: {
        classList: [],
    },
    reducers: {
    },
    effects:{
        * getClassList({ payload }, { put, call }) {
            const { data } = yield call(getClass, payload)
            yield put({ type: 'updateState', payload: { classList: data }})
        },
    },
    subscriptions: {
    },
})