package com.bbl.business.finance;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bbl.business.user.service.IUserService;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/finance/")
@Api(value = "金融",description = "金融接口")
public class FinanceController {

    @Autowired
    IUserService iUserService;



    /**
     * 热门期货
     */
    @GetMapping("findqh")
    @ApiOperation(value="热门期货", notes="热门期货")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object findqh() {
        String qhlist = StaticJedisUtils.get ("qhlist");

        return Response.successResponce (qhlist);
    }





    /**
     * 答题计数
     */
    @GetMapping("dtcount")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userid" ,value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="buttontype" ,value = "1:正确 0:错误 3:重置 4:查询", required = true, dataType = "Int")
    })
    @ApiOperation(value="答题计数", notes="答题计数")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object dtcount(String userid,Integer buttontype) {
        if(buttontype==3){
            StaticJedisUtils.del ("dt_user_"+userid);
            return Response.successResponce ("删除成功");
        }


        String dt = StaticJedisUtils.get ("dt_user_"+userid);
        if(buttontype==4){
            return Response.successResponce (JSONObject.parseObject (dt));
        }
        int truecount=0;
        int falsecount=0;
//        JSONArray aj=new JSONArray ();
        JSONObject jo=new JSONObject ();
        if(dt!=null){
            jo = JSONObject.parseObject (dt);
//             aj=jo.getJSONArray ("error");
            truecount=jo.getInteger ("truecount");
            falsecount=jo.getInteger ("falsecount");
        }

        if(buttontype==1){
            jo.put ("truecount",buttontype==1? truecount+1:truecount);
            jo.put ("falsecount",falsecount);
        }else{
            jo.put ("falsecount",buttontype==0? falsecount+1:falsecount);
            jo.put ("truecount",truecount);
        }

        StaticJedisUtils.set ("dt_user_"+userid,jo,60*60*24*7);


        return Response.successResponce (jo);
    }


    /**
     * 聊天室聊天
     */
    @GetMapping("setmessage")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="type" ,value = "1-10", required = true, dataType = "Int")
    })
    @ApiOperation(value="聊天", notes="聊天")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object setmessage(Integer type,String name,String isteacher,String message) {

        String messagetype = StaticJedisUtils.get ("message"+type);
        JSONArray jr = JSONArray.parseArray (messagetype);
        JSONObject jo=new JSONObject ();
        jo.put ("name",name); jo.put ("isteacher",isteacher);
        jo.put ("message",message);
        jr.add (jo);
        StaticJedisUtils.set ("message"+type,jr,0);
        return Response.successResponce (jr);

    }

    /**
     * 聊天室
     */
    @GetMapping("message")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="type" ,value = "1-10", required = true, dataType = "Int")
    })
    @ApiOperation(value="聊天室", notes="聊天室")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object message(Integer type) {
        String message = StaticJedisUtils.get ("message"+type);
        if(message==null||message.equals ("")){



            if(type==1){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","农产品");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","吞下余晖"); jo.put ("isteacher","0");
//                jo.put ("message","老师，沥青怎么看，能多吗");
//                aj.add (jo);jo=new JSONObject ();
//                jo.put ("name","专家[庄睿明]"); jo.put ("isteacher","1");
//                jo.put ("message","沥青日线价格站上3000压力，走出反弹形态，可尝试短多，支撑3000");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","盐如玉"); jo.put ("isteacher","0");
//                jo.put ("message","老师你好，请问01鸡蛋怎么看");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[庄睿明]"); jo.put ("isteacher","1");
//                jo.put ("message","鸡蛋日线价格在5日线下方运行，短期走势偏弱，维持短空思路，压力4450");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","眷念阑珊"); jo.put ("isteacher","0");
//                jo.put ("message","老师好，白糖趋势分析一下");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[庄睿明]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，白糖01目前趋势和短期都处于震荡走势之中，盘面没有明显的波段和方向，建议先观望为宜");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","古月文刀"); jo.put ("isteacher","0");
//                jo.put ("message","古月文刀老师，能讲讲苹果吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[庄睿明]"); jo.put ("isteacher","1");
//                jo.put ("message","苹果2001合约日线反弹，短期高位震荡，压力8270附近。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","念初心"); jo.put ("isteacher","0");
//                jo.put ("message","老师铁矿石05合约怎么看");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[庄睿明]"); jo.put ("isteacher","1");
//                jo.put ("message","铁矿石目前短期处于反弹形态和走势之中，当前下方支撑590一线，上方压力位置610一线，不建议追高，建议保持逢低买入的思路。");

                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==2){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","持仓走势");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","凤舞冬凌"); jo.put ("isteacher","0");
//                jo.put ("message","老师螺纹怎么看");
//                aj.add (jo);jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，螺纹01目前中线处于反弹趋势之中，建议继续保持多头思路，当前上方压力位置3770一线，下方支撑位置3680一线");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","凉州大司"); jo.put ("isteacher","0");
//                jo.put ("message","老师您好，菜柏什么价位开空比较好，谢谢您！");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，菜粕目前短期处于震荡筑底的阶段，中线还是处于回调阶段，盘面暂时没有新的下跌趋势和动能的产生，暂时建议短期先观望为宜，如果想逢压力位置开空，建议可以参考2220一线的压力位置。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","小南瓜U"); jo.put ("isteacher","0");
//                jo.put ("message","老师你好，分析下棉花01走势如何？谢谢！");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，棉花01当前短期和中线趋势震荡，并没有明显的趋势和方向， 建议当前先观望为宜。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","林尽欢"); jo.put ("isteacher","0");
//                jo.put ("message","老师好玉米可以多吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，玉米目前处于震荡偏空的波段之中，盘面并没有清晰和明显的波段和力度，当前开空并也没有有利的方向和空间，建议先观望为宜。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","岁月梧桐"); jo.put ("isteacher","0");
//                jo.put ("message","老师好，河北山东部分焦企停产，焦炭可以做多吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，焦炭01今天涨幅过高，并且长期趋势还是回调趋势，并且在1912一线有较强的趋势压力，当前追多的意思不是很大，建议谨慎。");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if (type==3){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","能源化工");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","小虫会飞"); jo.put ("isteacher","0");
//                jo.put ("message","老师，你好，请问燃油1840现在能空吗？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","路守"); jo.put ("isteacher","0");
//                jo.put ("message","你好老师，燃油1776空单还能拿吗？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[段和硕]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，燃料油当前中短线处于反弹走势，但是长线还是下跌形态，目前可以等待燃料油重新回到下跌趋势之中的机会，但是目前盘面并没有形成新的下跌力度，空头也没有重新增仓入场，建议再等等。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","路守"); jo.put ("isteacher","0");
//                jo.put ("message","老师你好，eg怎么看，可以空吗，谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[段和硕]"); jo.put ("isteacher","1");
//                jo.put ("message","你好，EG整体大的波段还是空头回调的波段，但是短期并没有产生新的下跌力度，也就是说没有新的开空点位，建议保持空头的思路，关注盘面的变化，等待新的下跌的波段产生。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","梦在天边"); jo.put ("isteacher","0");
//                jo.put ("message","老师你好，近期对豆油的看法");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[段和硕]"); jo.put ("isteacher","1");
//                jo.put ("message","您好，目前豆油今天日内偏空，长线趋势还是偏多，当前可以关注6300一线的支撑，在6300之上还是处于涨势形态之中。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","千面怪"); jo.put ("isteacher","0");
//                jo.put ("message","老师，沥青1912合约个人能持仓到什么时候");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);

            }else if(type==4){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","金属");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","嬴九霄"); jo.put ("isteacher","0");
//                jo.put ("message","老师，1440空的1309玻璃，怎么办呢，急急啊");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","玻璃空单可以持有。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","天下九州"); jo.put ("isteacher","0");
//                jo.put ("message","老师P1305空还能拿吗，现在成本价");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","可以持有 目标6600");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","七贝勒"); jo.put ("isteacher","0");
//                jo.put ("message","橡胶(12875,180.00,1.42%)怎么看，谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","看多的方向还是没变 ");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","千里狼锋"); jo.put ("isteacher","0");
//                jo.put ("message","老师，问了几遍了，对螺纹怎么看？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[任圣杰]"); jo.put ("isteacher","1");
//                jo.put ("message","螺纹还是震荡 方向还是偏多 所以逢低买入。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","一梦缘起"); jo.put ("isteacher","0");
//                jo.put ("message","老师白糖(5581,-5.00,-0.09%)05多单可以进吗");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==5){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","股指");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","柯宝"); jo.put ("isteacher","0");
//                jo.put ("message","股指今日策略？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[苏立果]"); jo.put ("isteacher","1");
//                jo.put ("message","股指今日上行压力2466-2478，下行支撑2438-2440，昨日强势上攻后，今日有调整的可能");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","松露"); jo.put ("isteacher","0");
//                jo.put ("message","老师，焦炭怎么看？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[苏立果]"); jo.put ("isteacher","1");
//                jo.put ("message","焦炭1305，昨日增仓上攻后筑平台底1703，之上仍可看多，下破则有调整可能，下行支撑1694、1680。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","风无羲"); jo.put ("isteacher","0");
//                jo.put ("message","老师您好，螺纹空单什么位置撤出来合适？谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[苏立果]"); jo.put ("isteacher","1");
//                jo.put ("message","如我在博客中所提示，今日螺纹1305短线压力3875，上破空单应暂离场，目前短空也可以此为止损。 ");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","安思"); jo.put ("isteacher","0");
//                jo.put ("message","老师 说说胶吧 谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[苏立果]"); jo.put ("isteacher","1");
//                jo.put ("message","天胶1305近期高位震荡，支撑是在25130，25265可作为多空分界位，之上，暂谨慎看多，看能否突破前高，之下，可作为短空切入位置，但须严格入市止损。目前保持震荡强势。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","青玉烬"); jo.put ("isteacher","0");
//                jo.put ("message","老师，我有空胶25000,持有还是止损？");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==6){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","贵金属");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","花零落"); jo.put ("isteacher","0");
//                jo.put ("message","老师您好01合约螺纹钢现价能进多吗？？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","螺纹钢日内调整的幅度较大，多单先回避，后期走强之后再考虑介入的机会。这里的交流毕竟是有时差的。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","亘古苍茫"); jo.put ("isteacher","0");
//                jo.put ("message","对于9天连涨的 沥青合约1912 是什么观点？ 2950点的空单割肉什么位置好？是不是 这周5个人投资者必须强行平仓了？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","沥青1912合约已经快到交割月了，这个时候期货的价格会无限接近现货的价格，投机户的话不能持有到交割月也就是本周五下午三点之前必须平掉，1期货公司也会提前要求你这边平仓，至于2950被套的空单想全身而退的可能性不大了，找一个回调的低点出掉就行。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","风云散01"); jo.put ("isteacher","0");
//                jo.put ("message","甲醇怎么又下来了，1960附近开空怎么样");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","甲醇原本就是弱势走势的格局，早上有人也问了化工板块，也是给出了暂时观望的建议。甲醇的空单建议在有效下破1890之后再介入比较稳妥。( ");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","远方有海"); jo.put ("isteacher","0");
//                jo.put ("message","老师请说下焦炭的支撑点位，谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","焦炭下方的支撑可以参考均线的支撑，均线支撑在黑色系里面的有效度还是很高的。操作的时候走势是第一位的");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","渡口无人"); jo.put ("isteacher","0");
//                jo.put ("message","老师你好，焦炭2001支撑点在哪");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==7){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","内盘行情");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","帘外雨"); jo.put ("isteacher","0");
//                jo.put ("message","老师，棕榈油跌停了，多头趋势是否已破，转换为弱势");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[弘光亮]"); jo.put ("isteacher","1");
//                jo.put ("message","棕榈油就和上午答疑的时候提示的一样，目前陷入前高位的宽幅震荡之中，趋势不是一天形成的自然也不会以就凭一个跌停板就就扭转趋势，需要后期更多的K线去辅助判断。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","让笑意折了枝"); jo.put ("isteacher","0");
//                jo.put ("message","老师，螺纹3600附近多？止损什么位置合适？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[弘光亮]"); jo.put ("isteacher","1");
//                jo.put ("message","止损的设置有三种：其一，固定止损（一般就是合约报价的1%-2%来设置，螺纹就是40-80个点左右）：其二，按照K图的形态来设置（参考压力及支撑位）；其三，综合考虑固定止损及K线形态来设置。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","温良情境"); jo.put ("isteacher","0");
//                jo.put ("message","老师，菜粕可多吗？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[弘光亮]"); jo.put ("isteacher","1");
//                jo.put ("message","菜粕多单不急，大方向还是弱势，这两天走势偏强，但是不改大的走势结构，多单先回避，空单可关注一下有色板块里面弱势品种的参与机会。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","晨露"); jo.put ("isteacher","0");
//                jo.put ("message","橡胶下方支撑在哪老师2001");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[弘光亮]"); jo.put ("isteacher","1");
//                jo.put ("message","橡胶下方的支撑参考均线的支撑就可以。这个品种日内调整的幅度也不小有多单注意做好自己的盈利保护。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","只影天涯"); jo.put ("isteacher","0");
//                jo.put ("message","老师好，棕油05的5660的多单，还能持有吗？谢谢");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==8){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","外盘行情");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","乱世尘棕"); jo.put ("isteacher","0");
//                jo.put ("message","榈油已经破位，依然看多吗？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[松阳波]"); jo.put ("isteacher","1");
//                jo.put ("message","这和你看的操作周期有关，在日K级别而言，棕榈油依旧还处在明显的上升趋势线之上，还没有跌破这上升趋势线，目前就说棕榈油破位有失偏颇。交易做的就是顺势，“势（兔子）”不出来我们是不会撒鹰的，退一步讲，棕榈油这个位置真的下来了，后世自然会有很多个“价差”的机会让我们入场。我们的操作理念是经过无数次实战总结出来的，简单且高效。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","浑然不觉"); jo.put ("isteacher","0");
//                jo.put ("message","铁矿怎么看？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[松阳波]"); jo.put ("isteacher","1");
//                jo.put ("message","铁矿看反弹，继续走强之后多单还可以去关注参与机会。这个品种也是属于黑色系里面反弹比较强势的一个品种，可优先关注，此外，焦炭、螺纹的表现均不错，后世还可以重点去关注。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","一半浑浊"); jo.put ("isteacher","0");
//                jo.put ("message","一半浑浊的遗憾老师您好！01锌下方支撑点位是多少？是否可多？如何操作？多谢！");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[松阳波]"); jo.put ("isteacher","1");
//                jo.put ("message","沪锌明显的空头走势格局，下方支撑最近的就是前低17885的支撑，接下来继续走弱之后还可以去参与空单参与及机会。 ");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","眉画犹思"); jo.put ("isteacher","0");
//                jo.put ("message","老师，请问铁矿可以多吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[松阳波]"); jo.put ("isteacher","1");
//                jo.put ("message","铁矿石的多单还可以去关注，这个品种近期反弹的力度不弱，虽然日内陷入调整，但是不改整体反弹运行的格局，后市继续走强之后多单还可以去关注。除开铁矿石黑色系里面的焦炭、螺纹等品种也可以关注。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","残缺的余烬"); jo.put ("isteacher","0");
//                jo.put ("message","郑油空单怎么看");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==9){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","期货研究");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","枉费情"); jo.put ("isteacher","0");
//                jo.put ("message","棉花如何操作？可以空吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","棉花看高位震荡运行，关注期价于前高位13655的压力的方向选择问题，暂时建议观望。想参与空单的话可以考虑早盘提示的有色板块里面弱势品种的试空机会。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","无怨"); jo.put ("isteacher","0");
//                jo.put ("message","老师好 获利的单子是持有还是止盈取决于什么 是短线长线 还是点位 或是品种");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","获利的单子是否持有隔夜只取决于一个情况——持仓的浮盈情况，浮盈明显的品种可以考虑止盈部分之后剩余的挂好盈利保护留隔夜；浮盈一般而仓位又不重的品种可以挂止损继续持有；浮盈一般而仓位又很重的品种可以挂保本持有；浮亏的单子原则上是不建议隔夜（仓位轻的可以考虑挂止损去隔夜，仓位重的一定要考虑减仓）。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","心奴独伤"); jo.put ("isteacher","0");
//                jo.put ("message","沥青和白糖接下来几天怎么走");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","沥青还是看反弹，大方向还是偏空对待，白糖关注上方5640附近的强压力位。这两个品种近期整体的操作机会一般，建议多关注。具体的操作可关注早盘提示的黑色系强势继续反弹的参与机会，有色板块弱势品种继续走弱的空单参与机会。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","绾青丝"); jo.put ("isteacher","0");
//                jo.put ("message","老师，粕今天会怎么走？");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[陈力]"); jo.put ("isteacher","1");
//                jo.put ("message","在3200之下仍然以看空为主");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","青桅"); jo.put ("isteacher","0");
//                jo.put ("message","青桅老师，粕压力和支撑是多少？");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }else if(type==10){
                JSONArray aj=new JSONArray ();
                JSONObject jotitle=new JSONObject ();
                jotitle.put ("title","商品持仓");aj.add (jotitle);
//                JSONObject jo=new JSONObject ();
//                jo.put ("name","一梦是三秋"); jo.put ("isteacher","0");
//                jo.put ("message","老师05焦炭与螺纹我多单怎办");
//                aj.add (jo);jo=new JSONObject ();
//                jo.put ("name","专家[丁高格]"); jo.put ("isteacher","1");
//                jo.put ("message","目前看走势偏空，建议您减仓或离场为主");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","一栀海"); jo.put ("isteacher","0");
//                jo.put ("message","老师说说螺纹吧");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[丁高格]"); jo.put ("isteacher","1");
//                jo.put ("message","昨日罕见放量增仓推动价格走低。在跌破前期震荡支撑3600后螺纹大幅走低，而单日单合约增仓40余万手实属罕见，目前无法从单一日线判断长期趋势，但短线下跌几率较大，如果有价格触及3570上方可能笔者计划以3600作为止损逢高沽空，空单下方第一目标3500附近。");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","你还梦不梦"); jo.put ("isteacher","0");
//                jo.put ("message","老师塑料(7200,-15.00,-0.21%)日内能空吗");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[丁高格]"); jo.put ("isteacher","1");
//                jo.put ("message","上方的压力在9900。激进的话可以尝试");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","弥弥浅浪"); jo.put ("isteacher","0");
//                jo.put ("message","今天早上怎么没有飞信？我现在持多橡胶(12545,-260.00,-2.03%)一手，多棕榈两手，请给个指示，谢谢");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[丁高格]"); jo.put ("isteacher","1");
//                jo.put ("message","您持有的两个品种飞信建议中并不存在，您可以根据昨日预测选择日内品种交易。从目前的走势看，橡胶日内略偏空，应当离场。棕榈可以持有看看");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","入青丝"); jo.put ("isteacher","0");
//                jo.put ("message","老师，铜多单可以看到57000不");
//                aj.add (jo);  jo=new JSONObject ();
//                jo.put ("name","专家[丁高格]"); jo.put ("isteacher","1");
//                jo.put ("message","多单的反弹目标继续定位在56800—57100之间");
//                aj.add (jo);
                StaticJedisUtils.set ("message"+type,aj,0);
            }

        }else{
            return Response.successResponce (message);
        }


        message = StaticJedisUtils.get ("message"+type);



        return Response.successResponce (message);
    }


}