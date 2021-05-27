# myzstu-uni

基于ZSTUHelper(浙理小助手)微信小程序

## 技术栈

- vue
- uni-app
- uniCloud

## 已上线平台

- [ ] Android

- [ ] iOS

- [ ] Web

- [x] 微信小程序

- [ ] 百度小程序

- [ ] 支付宝小程序

- [ ] 字节跳动小程序

- [x] QQ小程序

- [ ] 快应用

- [ ] 360小程序

## 已实现功能

- [x] 课表查询
- [x] 实时课表
- [x] 考试查询
- [x] 成绩查询
- [x] 校历查询
- [x] 常用电话查询
- [x] 推荐课表查询
- [x] 课外锻炼查询
- [ ] 创新创业学分查询
- [ ] 图书借还查询
- [ ] 图书检索
- [ ] 一卡通消费查询

## Project setup

### 安装uView

因uView未支持Vue3，暂时使用下载安方式
#### 下载uView 
[uView下载安装](https://www.uviewui.com/components/install.html#下载安装)
#### 修改index.js源代码
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

### npm install
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
