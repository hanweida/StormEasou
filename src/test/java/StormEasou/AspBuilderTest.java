package StormEasou;

import com.easou.let.bolt.AspNormalBolt;
import com.easou.let.pojo.ShowClickLog;

import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/4/27.
 */
public class AspBuilderTest {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String str = "NOTICE: 04-26 23:51:04:  asp * 3244484928 [src/search.cpp][asp_do_search][799] show_ver=136 sid=8b5bb0ad-ee3c-4552-8d69-c712d4809ad4 esid= uid= pd=29 pt=6 sty=0 qry= qid0=0 qid1=0 url= pi=0 ref= cid=bvf1328_10928_001 ip=14.210.131.125 iploc=0 mobile= mobloc=0 ngo=0 ngp=0 ngc=0 ipo=2 ipp=44 ipc=8 loc_src=6 lbsloc=0 phn=0 ua=Mozilla%2F5.0+(Linux;+Android+6.0.1;+MI+MAX+Build%2FMMB29M;+wv)+AppleWebKit%2F537.36+(KHTML,+like+Gecko)+Version%2F4.0+Chrome%2F46.0.2490.76+Mobile+Safari%2F537.36 sim=89860085191475600517 mid=869270027330348 mac=02:00:00:00:00:00 idfa= udid= openud= wvar= op=0 qry_src=0 muid=11581 mmid=1522 pclist= ex_rate=0 rq_cnt=3 ct=1 rq_cm=0 tgds= dl=31 cngo=1 loc_ds=广东省,湛江市,坡头区 op_type=1 system_type=none industry_id=0 customer_type=none ds_ret_src_no_list= src=1006 ad_type=16777216 tn=xs ads=1006 pid=44 city=8 np=3 cbd=0 tm=0|0|416|0|0|0|934|0|0|0|0|0 at=4879 rc=2 discount=20 indv_type=0 rep_query= appid= appt=0 gender=0 bhd= loc=21.259116|110.492237 filtered=0 cmatch_ad_src_type=1006,4;1006,4 third_ad_info= req_sg=0 req_sg_cnt=0 req_sg_rs=0 sg_rtn_cnt=0 sg_all_cnt=0 req_gg=0 ds_cnt=1006,1,2; dsp_ad= kdad=0\t5\t13027\t165305\t3691888\t0\t0\t9304162\t0\t82\t82\t插屏\thas+no+description\thttp%3A%2F%2Fwuzhou.decoparkgd.com%2Findex-ad.html\t\t\t11\t2\t1\t1006\t1006\t0\t1\t2\t5\t1\t0\t4000\t1052\t0\t0\t\t\t|1\t5\t13451\t165270\t3691729\t0\t0\t9303803\t0\t50\t50\t2017二级建造师\thas+no+description\thttp%3A%2F%2Fvip.hotkao.com%2F%2Ferjian.html\t\t\t11\t2\t1\t1006\t1006\t0\t1\t2\t5\t1\t0\t2682\t2682\t0\t0\t\t\t \n";
        AspNormalBolt aspNormalBolt = new AspNormalBolt();
        List<ShowClickLog> list = aspNormalBolt.aspParse(str, null);
        for(ShowClickLog showClickLog : list){
            System.out.println(showClickLog.toKeyValue());
        }
    }
}
