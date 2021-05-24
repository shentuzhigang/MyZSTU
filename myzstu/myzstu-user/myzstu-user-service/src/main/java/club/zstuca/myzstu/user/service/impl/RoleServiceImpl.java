package club.zstuca.myzstu.user.service.impl;

import club.zstuca.myzstu.user.entity.Role;
import club.zstuca.myzstu.user.mapper.RoleMapper;
import club.zstuca.myzstu.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务实现类
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-22 21:57
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
