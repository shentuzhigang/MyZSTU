package club.zstuca.myzstu.dto.edu;


import club.zstuca.myzstu.spyder.edu.entity.Grade;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 13:07
 */
@Data
public class GradeDTO {
    private List<Grade> data;
    private Set<String> years;
}

