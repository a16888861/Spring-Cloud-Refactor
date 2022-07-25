package com.peri.fashion.userInfo.utils;

import cn.hutool.extra.mail.MailUtil;
import com.peri.fashion.common.exception.ExceptionEnum;
import com.peri.fashion.common.exception.MailException;

import java.io.File;

/**
 * 网易邮件发送工具类
 *
 * @author Elliot
 */
public class Mail163Util {

    /**
     * 发送邮件(单用户)
     *
     * @param title         邮件标题
     * @param content       邮件内容
     * @param toMailAddress 发给谁
     */
    public static void sendMail(String title, String content, String toMailAddress, File... files) {
        try {
            MailUtil.send(toMailAddress, title, content, false, files);
        } catch (Exception e) {
            throw new MailException(ExceptionEnum.MAIL_EXCEPTION.getMessage(), e);
        }
    }

    /**
     * 发送异常提醒邮件(单用户)
     *
     * @param title         邮件标题
     * @param content       邮件内容
     * @param toMailAddress 发给谁
     */
    public static void sendExceptionMail(String title, String content, String toMailAddress) {
        try {
            MailUtil.send(toMailAddress, title, "异常" + content, false, null);
        } catch (Exception e) {
            throw new MailException(ExceptionEnum.MAIL_EXCEPTION.getMessage(), e);
        }
    }

}
