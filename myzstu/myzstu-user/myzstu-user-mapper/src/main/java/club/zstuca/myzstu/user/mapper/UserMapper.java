package club.zstuca.myzstu.user.mapper;

import club.zstuca.myzstu.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-22 17:09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
