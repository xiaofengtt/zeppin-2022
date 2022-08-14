import React from 'react';
import { Router, Route, Switch, Redirect } from 'dva/router';
import AuthRoute from './components/AuthRoute'
// import dynamic from 'dva/dynamic';

import Login from './pages/login'
import Home from './pages/home'
import ClassDetail from './pages/class-detail'
import newsDetail from './pages/news-detail'
import My from './pages/my'
import MyCollect from './pages/my-collect'
import About from './pages/about'
import DataModel from './pages/data-model';
import Ranking from './pages/ranking';
import Match from './pages/match';
import MatchInfo from './pages/matchInfo';
import AllComment from './pages/allComment';
import TeamInfo from './pages/teamInfo'


const routers = [
    {
        path: '/login',
        component: Login
    },
    {
        path: '/home',
        component: Home,

    },{
        path: '/my',
        component: My,
        // auth: true,
    },{
        path: '/my/collect',
        component: MyCollect,
        // auth: true,
    },{
        path: '/class/:classId',
        component: ClassDetail,
        // auth: true,
    },{
        path: '/news/:classId',
        component: newsDetail,
        // auth: true,
    },{
        path: '/setting/about',
        component: About,
    },{
        path: '/data-model',
        component: DataModel,
    },{
        path: '/ranking',
        component: Ranking,
    },{
        path: '/match',
        component: Match,
    },{
        path: '/matchInfo/:uuid',
        component: MatchInfo,
        // auth: true,
    },{
        path:'/allComment/:classId',
        component:AllComment
    },{
        path:'/teamInfo/:uuid',
        component:TeamInfo
    }
]

export default ({ history, app }) => (
  <Router history={history}>
    <Switch>
        {
            routers.map(({ path, exact = true, animated = false, auth = false, component, models }) => {
                return (
                    !auth ?
                    <Route key={path} exact={exact} path={path} component={component} /> :
                    <AuthRoute key={path} exact={exact} path={path} component={component} />
                )
            })
        }
        <Redirect from="/" to="/home" />
        <Redirect to="/" />
    </Switch>
  </Router>
)

