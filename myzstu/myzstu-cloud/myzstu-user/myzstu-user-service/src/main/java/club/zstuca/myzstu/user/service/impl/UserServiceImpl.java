package club.zstuca.myzstu.user.service.impl;

import club.zstuca.myzstu.user.entity.User;
import club.zstuca.myzstu.user.mapper.UserMapper;
import club.zstuca.myzstu.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现类
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-22 17:23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
