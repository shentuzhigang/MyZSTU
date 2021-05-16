# myzstu-uni

基于ZSTUHelper(浙理小助手)

## 安装uView
因uView未支持Vue3，暂时使用下载安方式
### 下载uView 
[uView下载安装](https://www.uviewui.com/components/install.html#下载安装)
### 修改index.js源代码
```javasrcipt
const install = Vue => {
	Vue.mixin(mixin) 
	if (Vue.config.globalProperties.openShare) {
		Vue.mixin(mpShare);
	}
	// Vue.mixin(vuexStore);
	// 时间格式化，同时两个名称，date和timeFormat
	Vue.config.globalProperties.$filter = {
		timeFormat(timestamp, format) {
			return timeFormat(timestamp, format)
		},
	    date(timestamp, format) {
			return timeFormat(timestamp, format)
		},
		// 将多久以前的方法，注入到全局过滤器
		timeFrom(timestamp, format) {
			return timeFrom(timestamp, format)
		}
	}
	Vue.config.globalProperties.$u = $u
}
```

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
