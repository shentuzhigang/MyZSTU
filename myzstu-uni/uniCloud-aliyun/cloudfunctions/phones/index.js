'use strict';
exports.main = async (event, context) => {
  //event为客户端上传的参数
  console.log('event : ', event)

  //返回数据给客户端
  return {
    code: 0,
    "phones": {
      "life": [{
          "name": "校园110",
          "number": "86843110"
        },
        {
          "name": "网络信息中心",
          "number": "86843388"
        },
        {
          "name": "后勤服务中心水电维修",
          "number": "86843985"
        },
        {
          "name": "后勤服务中心公寓管理",
          "number": "86843918"
        },
        {
          "name": "空调服务中心",
          "number": "4007110060"
        }
      ],
      "work": [{
          "name": "理学院",
          "number": "86845302"
        },
        {
          "name": "理学院",
          "number": "86843224"
        },
        {
          "name": "材料与纺织学院、丝绸学院",
          "number": "86843255"
        },
        {
          "name": "材料与纺织学院、丝绸学院",
          "number": "86843826"
        },
        {
          "name": "服装学院",
          "number": "86843480"
        },
        {
          "name": "信息学院",
          "number": "86843886"
        },
        {
          "name": "机械与自动控制学院",
          "number": "86843495"
        },
        {
          "name": "建筑工程学院",
          "number": "86843672"
        },
        {
          "name": "生命科学学院",
          "number": "86843303"
        },
        {
          "name": "经济管理学院",
          "number": "86843397"
        },
        {
          "name": "艺术与设计学院",
          "number": "86843291"
        },
        {
          "name": "法政学院",
          "number": "86843422"
        },
        {
          "name": "外国语学院",
          "number": "86843458"
        },
        {
          "name": "史量才新闻与传播学院",
          "number": "86843472"
        },
        {
          "name": "马克思主义学院",
          "number": "86843579"
        },
        {
          "name": "启新学院、创业学院",
          "number": "86843791"
        },
        {
          "name": "国际教育学院",
          "number": "86845360"
        },
        {
          "name": "继续教育学院",
          "number": "88929606"
        }
      ],
      "other": []
    }
  }
};
