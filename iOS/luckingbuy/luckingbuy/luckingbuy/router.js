import Vue from 'vue'
// import AV from 'leancloud-storage'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'

Vue.use(Router)

const authFilter = (to, from, next) => {
    const UserData = JSON.parse(localStorage.getItem('UserData')) || null
    if (UserData) {
        next()
    } else {
        // next({
        //     path: '/login'
        // })
        window.webkit.messageHandlers.login.postMessage('');
    }
}

export default new Router({
    routes: [{
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'home',
        component: Home
    },
    {
        path: '/my',
        component: () => import('./views/My.vue'),
        beforeEnter: authFilter
    },
    {
        path: '/collect',
        name: 'collect',
        component: () => import('./views/Collect.vue')
    },
    {
        path: '/feedback',
        name: 'feedback',
        component: () => import('./views/Feedback.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    {
        path: '/cart',
        name: 'cart',
        component: () => import('./views/Cart.vue')
    },
    {
        path: '/address',
        name: 'address',
        component: () => import('./views/Address.vue')
    },
    {
        path: '/addressList',
        name: 'addressList',
        component: () => import('./views/AddressList.vue')
    },
    {
        path: '/type',
        name: 'type',
        component: () => import('./views/Type.vue')
    },
    {
        path: '/details/:id',
        name: 'details',
        component: () => import('./views/Details.vue')
    },
    {
        path: '/commodity-list/:keys',
        name: 'commodityList',
        component: () => import('./views/CommodityList.vue')
    },
    {
        path: '/order',
        component: () => import('./views/Order'),
        beforeEnter: authFilter
    }, // 订单列表
    {
        path: '/orderDetail',
        component: () => import('./views/OrderDetail'),
        beforeEnter: authFilter
    },
    {
        path: '/returnGoods',
        component: () => import('./views/ReturnGoods'),
        beforeEnter: authFilter
    },
    {
        path: '/received',
        component: () => import('./views/Received'),
        beforeEnter: authFilter
    },
    {
        path: '/search',
        name: 'search',
        component: () => import('./views/Search')
    },
    {
        path: '/offer',
        name: 'offer',
        component: () => import('./views/Offer')
    },
    {
        path: '/timeDraw',
        name: 'timeDraw',
        component: () => import('./views/TimeDraw')
    },
    {
        path: '/orderLottery',
        name: 'orderLottery',
        component: () => import('./views/OrderLottery')
    },
    {
        path: '/winningRecord',
        name: 'winningRecord',
        component: () => import('./views/WinningRecord')
    },
    {
        path: '/credit-card',
        name: 'creditCard',
        component: () => import('./views/credit-card.vue')
    }
    ]
})
