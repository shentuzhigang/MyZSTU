import {
    createApp
} from 'vue'

import App from './App.vue'

import uView from "uview-ui";

// 支持 cookie
import 'weapp-cookie'

const app = createApp(App)

app.use(uView)

app.mount()
