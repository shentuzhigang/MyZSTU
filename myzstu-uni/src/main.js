import {
    createApp
} from 'vue'

import App from './App.vue'

import uView from "uview-ui";

const app = createApp(App)

app.use(uView)

app.mount()
