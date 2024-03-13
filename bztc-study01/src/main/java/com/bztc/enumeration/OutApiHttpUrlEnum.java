package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 外部api http地址
 */
public enum OutApiHttpUrlEnum {
    /**
     * 探数黄金数据查询
     */
    TANSHU_GOLD_DATA("QueryGoldData", "http://api.tanshuapi.com/api/gold/v1/%s?key=%s"),
    /**
     * 探数白银数据查询
     */
    TANSHU_SILVER_DATA("QuerySilverData", "http://api.tanshuapi.com/api/silver/v1/%s?key=%s"),
    /**
     * 探数油价查询
     */
    TANSHU_OIL_PRICE_DATA("QueryOilPrice", "http://api.tanshuapi.com/api/youjia/v1/index?key=%s"),
    /**
     * 探数全球ip地址查询
     */
    TANSHU_GLOBAL_IP_ADDRESS_DATA("QueryGlobalIpAddress", "http://api.tanshuapi.com/api/attribution_ip/v1/index?key=%s&ip=%s"),
    /**
     * 探数手机归属查询
     */
    TANSHU_PHONE_OWNERSHIP_DATA("QueryPhoneOwnership", "http://api.tanshuapi.com/api/attribution_phone/v1/index?key=%s&phone=%s"),
    /**
     * 探数星座运势查询
     */
    TANSHU_CONSTELLATION_FORTUNE_DATA("QueryConstellationFortune", "http://api.tanshuapi.com/api/constellation/v1/index?key=%s&cid=%s&type=%s"),
    CIBA_EVERYDAY_SENTENCE("词霸每日一句", "https://open.iciba.com/dsapi/?date=%s"),
    WEIXIN_JSCODE2_SESSION("微信临时登录凭证校验", "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"),
    WEIXIN_GET_ACCESS_TOKEN("微信获取access_token", "https://api.weixin.qq.com/cgi-bin/token?appid=%s&secret=%s&grant_type=client_credential"),
    WEIXIN_GET_UNLIMIT_QRCODE("微信小程序-获取不限制的小程序码", "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s"),
    ;

    public static final Map<String, String> KEY_VALUE = new HashMap<>();

    static {
        for (OutApiHttpUrlEnum enumData : EnumSet.allOf(OutApiHttpUrlEnum.class)) {
            KEY_VALUE.put(enumData.name, enumData.url);
        }
    }

    public final String name;
    public final String url;

    private OutApiHttpUrlEnum(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static String lookup(String name) {
        return KEY_VALUE.get(name);
    }

    public final String value() {
        return KEY_VALUE.get(this.name);
    }
}
