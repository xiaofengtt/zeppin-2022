import extend from 'dva-model-extend';
import common from './common'
import { updateUser } from '../service'

export default extend(common, {
    namespace: 'editInfo',
    state: {
    },
    reducers: {
    },
    effects:{
        * updateUser({ payload }, { put, call }) {
            const result = yield call(updateUser, payload)
            if (result.code === 0) {
                window.localStorage.setItem("user", JSON.stringify(result.data));
            }
            return result;
        },
    },
    subscriptions: {
    },
})