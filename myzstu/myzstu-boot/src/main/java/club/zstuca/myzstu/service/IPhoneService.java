package club.zstuca.myzstu.service;


import club.zstuca.myzstu.beans.Phone;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-11 19:46
 */
public interface IPhoneService {

    List<Phone> getPhones() throws IOException;

    void addPhone(List<Phone> res, Elements phones, String type);
}
