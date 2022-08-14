import './polyfill';
import 'moment/locale/zh-cn'
import React from 'react';
import dva from 'dva';
//import Router from './router'
import createHistory from 'history/createHashHistory';
import createLoading from 'dva-loading';
import numeral from 'numeral'

// import registerServiceWorker from './registerServiceWorker';
import appModel from './models/app'
import classDetail from './models/classDetail'
import home from './models/home'
import favorites from './models/favorites'

import AV from 'leancloud-storage'

import './index.less';
import ranking from './models/ranking';
import dataModel from './models/dataModel';
import login from './models/login';
import match from './models/match';
import matchInfo from './models/matchInfo';
import teamInfo from './models/teamInfo';

numeral.defaultFormat('0,0.00');

AV.init({
  appId: 'FeEhkBRc5xVyp8TQWzAjrRR0-gzGzoHsz',
  appKey: 'XtS2DHi2xs6a5UfpWxSe9UWy',
})

const app = dva({
  history: createHistory()
})
app.use(createLoading())
app.router(require('./router').default)

app.model(appModel)
app.model(classDetail)
app.model(home)
app.model(favorites)
app.model(ranking)
app.model(dataModel)
app.model(login)
app.model(match)
app.model(matchInfo)
app.model(teamInfo)

app.start('#root');

//registerServiceWorker();
