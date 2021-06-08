package club.zstuca.myzstu.boot.service.impl;

import club.zstuca.myzstu.boot.service.IHongQingTingService;
import club.zstuca.myzstu.spyder.hongqingting.HongQingTingSpyder;
import club.zstuca.myzstu.spyder.hongqingting.entity.RunData;
import club.zstuca.myzstu.spyder.hongqingting.entity.RunWay;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 17:14
 */
@Service
public class HongQingTingServiceImpl implements IHongQingTingService {
    @Autowired
    private HongQingTingSpyder hongQingTingSpyder;

    private String data="{'begintime':'1603459548','endtime':'1603460086'," +
                "'uid':'ad71efb3-14d7-4fbe-8841-36b36e984e63b6e291662b08449695b8fb7636686f4c1603452653$e0e2c1869bcf2698bc2dbc38aa93f4f7'," +
                "'schoolno':'10338'," +
                "'distance':'1720.0'," +
                "'speed':'3.197026022304833'," +
                "'studentno':'2018329621200'," +
                "'atttype':'2'," +
                "'eventno':'801'," +
                "'location':'30.313021481328853,120.35585328110142;1603459544;null;null;1.0;null" +
                "@30.31300945733687,120.35581321063516;1603459548;null;null;1.0;null" +
                "@30.31299949342286,120.35578317056158;1603459551;null;null;1.0;null" +
                "@30.3129903738019,120.3557531373638;1603459554;null;null;1.0;null" +
                "@30.312944148707683,120.35567101966762;1603459558;null;null;8.0;null" +
                "@30.31284405807743,120.35561494616168;1603459561;null;null;3.0;null" +
                "@30.312763998147883,120.35560291883348;1603459564;null;null;3.0;null" +
                "@30.312683115731687,120.35560291168393;1603459567;null;null;3.0;null" +
                "@30.312602118875127,120.35560291324282;1603459570;null;null;3.0;null" +
                "@30.31252097848262,120.35560289249683;1603459573;null;null;3.0;null" +
                "@30.312467139629298,120.35560489907185;1603459599;null;null;3.0;null" +
                "@30.31235904119001,120.3556088959597;1603459602;null;null;3.0;null" +
                "@30.312197065703867,120.35562090012687;1603459608;null;null;3.0;null" +
                "@30.31211596709472,120.35562689648717;1603459611;null;null;3.0;null" +
                "@30.312036036979357,120.35564491633721;1603459614;null;null;3.0;null" +
                "@30.311958148852963,120.35567595670767;1603459618;null;null;3.0;null" +
                "@30.311880227047276,120.3557089817891;1603459621;null;null;3.0;null" +
                "@30.311778296817614,120.35575505014619;1603459640;null;null;3.0;null" +
                "@30.31168227314364,120.35581512653718;1603459643;null;null;3.0;null" +
                "@30.31161036769226,120.35586117021167;1603459646;null;null;3.0;null" +
                "@30.31154235090238,120.35591122737803;1603459650;null;null;3.0;null" +
                "@30.31155467374745,120.35600135403577;1603459653;null;null;3.0;null" +
                "@30.311566844490912,120.35609148339903;1603459656;null;null;3.0;null" +
                "@30.31158200555962,120.35618158860201;1603459659;null;null;3.0;null" +
                "@30.31158186509078,120.3561815909867;1603459678;null;null;3.0;null" +
                "@30.31168815322133,120.35637186012346;1603459682;null;null;8.0;null" +
                "@30.311844550418837,120.35653608358957;1603459685;null;null;8.0;null" +
                "@30.312041684985903,120.35657515366177;1603459688;null;null;8.0;null" +
                "@30.312254586802357,120.3565591427932;1603459691;null;null;8.0;null" +
                "@30.31246765038509,120.35654415347109;1603459694;null;null;8.0;null" +
                "@30.312680535925026,120.35651812187501;1603459713;null;null;8.0;null" +
                "@30.312829553977142,120.3564901033198;1603459716;null;null;3.0;null" +
                "@30.31289657275579,120.35644603928921;1603459719;null;null;3.0;null" +
                "@30.312956458735663,120.35638397639522;1603459722;null;null;3.0;null" +
                "@30.313019336023043,120.35632390384572;1603459725;null;null;3.0;null" +
                "@30.313048131272403,120.35623979448803;1603459729;null;null;3.0;null" +
                "@30.313072061643865,120.35614966592904;1603459740;null;null;3.0;null" +
                "@30.31309366327129,120.35606374450931;1603459743;null;null;3.0;null" +
                "@30.313012457344303,120.35582322628836;1603459751;null;null;3.0;null" +
                "@30.31297520649187,120.35570306640865;1603459755;null;null;3.0;null" +
                "@30.31291021321656,120.35565099451702;1603459758;null;null;3.0;null" +
                "@30.312702112465324,120.35560291612283;1603459761;null;null;8.0;null" +
                "@30.311799256560906,120.35574504009195;1603459776;null;null;3.0;null" +
                "@30.31172628261363,120.35578708639602;1603459779;null;null;3.0;null" +
                "@30.311630379099203,120.35584715024564;1603459782;null;null;3.0;null" +
                "@30.31156033045621,120.35589522029127;1603459785;null;null;3.0;null" +
                "@30.311550555093653,120.35597532616987;1603459788;null;null;3.0;null" +
                "@30.31160095718616,120.35621465074757;1603459792;null;null;8.0;null" +
                "@30.311718273974428,120.35642291799715;1603459795;null;null;8.0;null" +
                "@30.31188758730224,120.35657112641745;1603459798;null;null;8.0;null" +
                "@30.31216569414656,120.35656314471565;1603459801;null;null;8.0;null" +
                "@30.312378725905315,120.35655215712045;1603459804;null;null;8.0;null" +
                "@30.312591544210413,120.35653114650798;1603459807;null;null;8.0;null" +
                "@30.312804537545656,120.3564951097851;1603459811;null;null;8.0;null" +
                "@30.31297937500542,120.35636394996655;1603459814;null;null;8.0;null" +
                "@30.313033317684173,120.35629986925444;1603459817;null;null;3.0;null" +
                "@30.313064130473702,120.3561797036361;1603459820;null;null;3.0;null" +
                "@30.313087922402573,120.35608958568403;1603459823;null;null;3.0;null" +
                "@30.313082889650957,120.35603352035304;1603459826;null;null;3.0;null" +
                "@30.313052655898527,120.35594340235494;1603459830;null;null;3.0;null" +
                "@30.313042611366697,120.35591335713531;1603459834;null;null;3.0;null" +
                "@30.313003470115493,120.35579319025462;1603459837;null;null;3.0;null" +
                "@30.312975326538076,120.35570307784481;1603459841;null;null;3.0;null" +
                "@30.31291013314081,120.35565100056252;1603459844;null;null;3.0;null" +
                "@30.312835211205286,120.35561094397937;1603459847;null;null;3.0;null" +
                "@30.31275514557604,120.35560292357258;1603459850;null;null;3.0;null" +
                "@30.312674058406476,120.35560290790183;1603459861;null;null;3.0;null" +
                "@30.31247613570404,120.35560490119626;1603459864;null;null;8.0;null" +
                "@30.31225997543306,120.35561790429004;1603459867;null;null;8.0;null" +
                "@30.31204511866628,120.35564190674096;1603459870;null;null;8.0;null" +
                "@30.31184115503894,120.35572801924499;1603459873;null;null;8.0;null" +
                "@30.311647254334215,120.3558391404645;1603459877;null;null;8.0;null" +
                "@30.31157538438792,120.35588520250023;1603459880;null;null;3.0;null" +
                "@30.31155254721293,120.3559853271594;1603459883;null;null;3.0;null" +
                "@30.311564667653947,120.35607544579767;1603459886;null;null;3.0;null" +
                "@30.31157789609707,120.35616556771029;1603459889;null;null;3.0;null" +
                "@30.31161711290991,120.35624468680265;1603459893;null;null;3.0;null" +
                "@30.31163115034835,120.35627071369285;1603459920;null;null;3.0;null" +
                "@30.31168826612163,120.3563728622756;1603459923;null;null;3.0;null@" +
                "30.311735443172964,120.35644595465318;1603459926;null;null;3.0;null" +
                "@30.31180147324165,120.35650003899416;1603459929;null;null;3.0;null" +
                "@30.311865524656703,120.35655310623594;1603459932;null;null;3.0;null" +
                "@30.312067719202545,120.35657115300755;1603459936;null;null;8.0;null" +
                "@30.312351560715125,120.35655514130262;1603459939;null;null;8.0;null" +
                "@30.31256462182324,120.35653413168069;1603459942;null;null;8.0;null" +
                "@30.31277762214956,120.35650010190433;1603459945;null;null;8.0;null" +
                "@30.312959342878898,120.3563839755003;1603459948;null;null;8.0;null" +
                "@30.31301437835897,120.35632989806112;1603459956;null;null;8.0;null" +
                "@30.313080015381846,120.3561176226729;1603459960;null;null;3.0;null" +
                "@30.31309384063028,120.3560637426684;1603459963;null;null;3.0;null" +
                "@30.31306272196528,120.35597343296338;1603459966;null;null;3.0;null" +
                "@30.313032505562344,120.35588330936196;1603459969;null;null;3.0;null" +
                "@30.313003376000907,120.35579319006165;1603459972;null;null;3.0;null" +
                "@30.312837122895004,120.355611941102;1603459976;null;null;8.0;null" +
                "@30.312622004758175,120.35560290703582;1603459979;null;null;8.0;null" +
                "@30.31240614261134,120.35560890091989;1603459982;null;null;8.0;null" +
                "@30.31219011782924,120.3556219027903;1603459985;null;null;8.0;null" +
                "@30.31197912533598,120.3556699509;1603459988;null;null;8.0;null" +
                "@30.311843085054303,120.35572700669844;1603459999;null;null;8.0;null" +
                "@30.31166426833086,120.35582714228795;1603460003;null;null;3.0;null" +
                "@30.311592455644416,120.35587319489774;1603460006;null;null;3.0;null" +
                "@30.31154556861804,120.35593327154973;1603460009;null;null;3.0;null" +
                "@30.311557670325623,120.35602338209928;1603460012;null;null;3.0;null" +
                "@30.311569717386533,120.35611350436545;1603460015;null;null;3.0;null" +
                "@30.3115929253881,120.35619962173661;1603460018;null;null;3.0;null" +
                "@30.311606981223008,120.35622565151499;1603460029;null;null;3.0;null" +
                "@30.31166318241275,120.35632980362972;1603460032;null;null;3.0;null" +
                "@30.311771426922228,120.35647599860177;1603460035;null;null;8.0;null" +
                "@30.311952671671737,120.35658716603609;1603460038;null;null;8.0;null" +
                "@30.31216566816645,120.35656315077803;1603460041;null;null;8.0;null" +
                "@30.312378588801028,120.35655215023831;1603460044;null;null;8.0;null" +
                "@30.312591622102325,120.35653113910728;1603460048;null;null;8.0;null" +
                "@30.31280463636048,120.3564950989235;1603460061;null;null;8.0;null" +
                "@30.312999365681097,120.35634391666751;1603460065;null;null;3.0;null" +
                "@30.313040177697665,120.35626982462338;1603460068;null;null;3.0;null" +
                "@30.31306410396498,120.35617970888097;1603460071;null;null;3.0;null" +
                "@30.313088001948017,120.35608958612312;1603460074;null;null;3.0;null" +
                "@30.31308293926407,120.35603350906055;1603460077;null;null;3.0;null" +
                "@30.31299634913409,120.35576614796771;1603460081;null;null;8.0;null" +
                "@30.312840209333054,120.35561393789713;1603460084;null;null;8.0;null'," +
                "'usetime':'538.0'}";
    @Override
    public Boolean randomUploadOne(String sid){
        RunData demoData = RunData.parse(this.data);
        RunData runData = new RunData();
        Long now  = System.currentTimeMillis()/1000;
        Long t = now - demoData.getBeginTime();
        runData.setBeginTime(now - (demoData.getEndTime() - demoData.getBeginTime()));
        runData.setEndTime(now );
        runData.setUid(demoData.getUid());
        runData.setSchoolNo(demoData.getSchoolNo());
        runData.setDistance(demoData.getDistance());
        runData.setSpeed(demoData.getSpeed());
        runData.setStudentNo(sid);
        runData.setAttType(demoData.getAttType());
        runData.setEventNo(demoData.getEventNo());
        runData.setLocation(demoData.getLocation(t));
        runData.setUseTime(demoData.getUseTime());
        System.out.println(JSONObject.parseObject(runData.toString(), RunData.class));
        System.out.println(runData.toString());
        System.out.println(hongQingTingSpyder.uploadRunData(runData));
        return true;
    }
    @Override
    public Boolean randomUploadOne(Long t,String id) {
        try {
            RunWay randomRoute = RunWay.getRandomRouteWith(t, Math.random() * 2000 + 1000, null, 3.0);
            System.out.println(randomRoute);
            RunData runData = new RunData();
            runData.setStudentNo(id);
            runData.setBeginTime(randomRoute.getBeginTime());
            runData.setEndTime(randomRoute.getEndTime());
            runData.setUid(UUID.randomUUID().toString()+"faf4352521394a859275687a47b786721602508814$9d7370a8eb0c1286ce3c949c0c53469e");
            runData.setLocation(randomRoute.toString());
            runData.setAttType("2");
            runData.setEventNo("801");
            runData.setSchoolNo("10338");
            runData.setSpeed(String.valueOf(randomRoute.getSpeed()));
            runData.setDistance(String.valueOf(Math.floor(randomRoute.getDistance() * 10) / 10000));
            runData.setUseTime((randomRoute.getEndTime() - randomRoute.getBeginTime()) * 1.0f);
            System.out.println(runData.toString());
            System.out.println(hongQingTingSpyder.uploadRunData(runData));
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
