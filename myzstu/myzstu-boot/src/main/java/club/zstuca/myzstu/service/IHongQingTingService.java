package club.zstuca.myzstu.boot.service;


import java.io.IOException;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 17:13
 */
public interface IHongQingTingService {
    Boolean randomUploadOne(String sid);

    Boolean randomUploadOne(Long l, String s) throws IOException;
}
