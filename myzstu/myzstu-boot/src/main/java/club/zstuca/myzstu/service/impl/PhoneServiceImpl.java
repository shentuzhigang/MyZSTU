package club.zstuca.myzstu.service.impl;


import club.zstuca.myzstu.beans.Phone;
import club.zstuca.myzstu.service.IPhoneService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-11 19:46
 */
@Service
public class PhoneServiceImpl implements IPhoneService {
    @Override
    public List<Phone> getPhones() throws IOException {
        List<Phone> res = new LinkedList<Phone>();
        String path = URLDecoder.decode(PhoneServiceImpl.class.getClassLoader().getResource("static/phone.xml").getPath(), "UTF-8");
        File phoneXml = new File(path);
        Document doc = Jsoup.parse(phoneXml, "utf-8");
        Element life = doc.getElementsByTag("life").get(0);
        Elements phones = life.getElementsByTag("phone");
        addPhone(res, phones, "life");
        Element work = doc.getElementsByTag("work").get(0);
        phones = work.getElementsByTag("phone");
        addPhone(res, phones, "work");
        Element other = doc.getElementsByTag("other").get(0);
        phones = other.getElementsByTag("phone");
        addPhone(res, phones, "other");
        return res;
    }

    @Override
    public void addPhone(List<Phone> res, Elements phones, String type) {
        for (int i = 0; i < phones.size(); i++) {
            Element p = phones.get(i);
            String name = p.getElementsByTag("name").get(0).text();
            String number = p.getElementsByTag("number").get(0).text();
            Phone phone = new Phone();
            phone.setName(name);
            phone.setNumber(number);
            phone.setType(type);
            res.add(phone);
        }
    }
}
