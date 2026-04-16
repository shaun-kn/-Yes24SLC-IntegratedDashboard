import { createRouter, createWebHistory } from 'vue-router'

import OsrLayout from '../layouts/OsrLayout.vue'
import Osr2View from '../views/Osr2View.vue'
import Osr3View from '../views/Osr3View.vue'

const routes = [
    {
        path: '/',
        redirect: '/osr/2'
    },
    {
        path: '/osr',
        component: OsrLayout,
        children: [
            {
                path: '2',
                component: Osr2View,
                meta: { osrName: '시퀀스버퍼(OSR#2) 운영현황' }
            },
            {
                path: '3',
                component: Osr3View,
                meta: { osrName: '시퀀스버퍼(OSR#3) 운영현황' }
            }
        ]
    }
    // ,
    // {
    //     path: '/other',
    //     component: OtherPageView
    // }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
