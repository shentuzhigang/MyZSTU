<view class="container">
    <view class="row">
        <view class="col-xl-12 col-lg-12 col-md-12 col-sm-12  mx-auto">
            <header class="mt-5 mb-5 text-center">
                <text>浙理计协维修预约平台</text>
                <p class="tm-form-description">浙理计协——义务维修队</p>
            </header>
            <view class="text-monospace lead text-info row-fluid">
                <view class="card">
                    <view class="card-header">
                        报修须知：
                    </view>
                    <view class="textst-group textst-group-flush">
                        <text class="textst-group-item">1.服务是免费的，如果您力所能及，请捐助我们，让我们做的更好！</text>
                        <text class="textst-group-item">2.我们提供包括但不限于以下服务
                            <view>
                                <text>系统安装/重装、软件问题</text>
                                <text>拆机清灰</text>
                                <text>硬件升级（导购、安装、更换等）</text>
                                <text>其他电脑问题咨询</text>
                            </view >
                        </text>
                        <text class="textst-group-item">3.下列情况请直接送售后服务点
                            <view >
                                <text>出现明显的硬件故障，如屏幕破损、进水等</text>
                                <text>电脑未过保修期但需要拆机的</text>
                            </view>
                        </text>
                        <text class="textst-group-item">4.非电脑产品，仅提供有限的服务</text>
                        <text class="textst-group-item">5.如有其他问题，联系客服QQ：1367138194</text>
                    </view>
                </view>
            </view>
            <text>请认真填写以下信息:</text>
            <form id="form" name="form" class="tm-form-white tm-font-big" >
                <view class="text-monospace p-3 bg-textght rounded row">
                    <view class="form-group mb-5 col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <label for="name" class="black-text mb-4 big control-label">称呼</label>
                        <input id="name" name="name" type="text" value=""
                               class="vatextdate form-control tm-input-white-bg" required autofocus/>
                    </view>
                    <view class="form-group mb-5 col-xl-6 col-lg-6 col-md-12 col-sm-12">
                        <label for="tel" class="black-text mb-4 big control-label">电话（长号）</label>
                        <input id="tel" name="telephone" type="text" value=""
                               class="vatextdate form-control tm-input-white-bg" maxlength="11" required/>
                    </view>
                    <view class="form-group mb-5 col-xl-6 col-lg-6 col-md-12 col-sm-12">
                        <label for="qq" class="black-text mb-4 big control-label">QQ（可选）</label>
                        <input id="qq" name="qq" type="text" value=""
                               class="vatextdate form-control tm-input-white-bg" maxlength="10" />
                    </view>
                    <view class="form-group mb-5 col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <label for="brand" class="black-text mb-4 big control-label">电脑品牌</label>
                        <select id="brand" class="form-control" name="brand">
                            <option>请选择</option>
                            <option>Acer宏基</option>
                            <option>Dell戴尔</option>
                            <option>TOSHIBA东芝</option>
                            <option>ASUS华硕</option>
                            <option>HP惠普</option>
                            <option>Lenovo联想</option>
                            <option>MSI微星</option>
                            <option>Apple苹果</option>
                            <option>HASEE神舟</option>
                            <option>SAMSUNG三星</option>
                            <option>其他</option>
                        </select>
                    </view>
                    <view class="form-group mb-5 col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <label for="address" class="black-text mb-4 big control-label">地址</label>
                        <input id="address" name="address" value="" type="text"
                               class="form-control" required/>
                    </view>
                    <view class="form-group mb-5 col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <label for="text" class="black-text mb-2 big">故障描述(可选)</label>
                        <textarea class="form-control" name="description" id="text" cols="25" value="" rows="6"></textarea>
                    </view>
                </view>
                <view class="text-center mt-5">
                    <button id="submit" type="button"
                            class="btn btn-primary btn-large btn-large-white">Submit</button>
                </view>
            </form>
        </view>
    </view>
</view>