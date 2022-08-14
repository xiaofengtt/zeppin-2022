import extend from 'dva-model-extend';
import common from './common'
import { getExam, getQuestions } from '../service'

export default extend(common, {
  namespace: 'exam',
  state: {
    exams: [],
    questions: [],
  },
  reducers: {
  },
  effects:{
    * getExam({ payload }, { put, call }) {
      const { data } = yield call(getExam, payload)
      yield put({ type: 'updateState', payload: { exams: data }})
    },
    * getQuestions({ payload }, { put, call }) {
      const { data } = yield call(getQuestions, payload)
      yield put({ type: 'updateState', payload: { questions: data }})
    }
  },
  subscriptions: {
  },
})