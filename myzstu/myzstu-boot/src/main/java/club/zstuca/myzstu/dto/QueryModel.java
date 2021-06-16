package club.zstuca.myzstu.dto;

import lombok.Data;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-14 10:30
 */
@Data
public class QueryModel {
    private Integer showCount;
    private Integer currentPage;
    private String sortName;
    private String sortOrder;
}
