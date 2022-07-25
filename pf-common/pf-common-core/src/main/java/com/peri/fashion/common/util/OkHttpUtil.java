package com.peri.fashion.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * 封装Okhttp请求
 *
 * @author Author
 */
@Slf4j
public class OkHttpUtil {
    private static final OkHttpClient CLIENT;

    static {
        //创建OkHttpClient对象
        CLIENT = new OkHttpClient().newBuilder()
                //单位是秒
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String requestGet(String url) {
        try {
            //创建 Request 对象
            Request request = new Request.Builder()
                    //请求接口，如果需要传参拼接到接口后面。
                    .url(url).get()
                    .build();
            //得到 Response 对象
            Response response = CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                log.info("GET请求返回数据:" + string);
                return string;
            }
        } catch (Exception e) {
            log.error("IO异常，请检查网络或者URL", e);
        }
        return null;
    }

    /**
     * POST请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String requestPost(String url, String data, String reqType) {
        try {
            MediaType mediaType = MediaType.parse(reqType);
            RequestBody body = RequestBody.create(mediaType, data);
            //创建Request 对象
            Request request = new Request.Builder()
                    //请求接口，如果需要传参拼接到接口后面。
                    .url(url).post(body)
                    .build();
            //得到Response 对象
            Response response = CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                log.info("POST请求返回数据" + string);
                return string;
            }
        } catch (Exception e) {
            log.error("IO异常，请检查网络或者URL", e);
        }
        return null;
    }
}
