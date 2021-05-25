const app = getApp()

export function login() {
  var sid = uni.getStorageSync('sid')
  return new Promise((resolve, reject) => {
    uni.request({
      url: app.globalData.server.sports + "/app/index.php",
      method: "POST",
      withCredentials: true,
      cookie: true,
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      data: {
        schoolno: 10338,
        userno: sid,
        usersystem: 1,
        role: 3
      }
    }).then(data => {
      resolve()
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * 获取学生信息和锻炼信息
 */
export function stuInfoAndEvent() {
  var sid = uni.getStorageSync('sid')
  return new Promise((resolve, reject) => {
    login()
      .then(() => {
        uni.request({
          url: app.globalData.server.sports +
            "/app//exercise/ajaxStuExercise.php?title=stuExerciseRecord&action=showStuInfoAndEvent",
          method: "POST",
          withCredentials: true,
          cookie: true,
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            studentNo: sid
          }
        }).then(data => {
          resolve(data.data)
        }).catch(err => {
          reject(err)
        })
      }).catch(err => {
        reject(err)
      })
  })
}

/**
 * 获取锻炼数据
 */
export function stuSelExerciseInfo() {
  var sid = uni.getStorageSync('sid')
  return new Promise((resolve, reject) => {
    login()
      .then(() => {
        uni.request({
          url: app.globalData.server.sports +
            "/app//exercise/ajaxStuExercise.php?title=stuExerciseRecord&action=stuSelExerciseInfo",
          method: "POST",
          withCredentials: true,
          cookie: true,
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            studentNo: sid,
            eventno: -1
          }
        }).then(data => {
          resolve(data.data)
        }).catch(err => {
          reject(err)
        })
      }).catch(err => {
        reject(err)
      })
  })
}
