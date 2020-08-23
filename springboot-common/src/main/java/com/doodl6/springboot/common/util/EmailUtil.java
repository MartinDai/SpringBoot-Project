package com.doodl6.springboot.common.util;


import com.doodl6.springboot.common.tuple.Tuple2;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.Authenticator;
import java.util.List;

/**
 * 邮件工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmailUtil {

    /**
     * 发送文本邮件
     */
    public static void sendTextEmail(Authenticator authenticator, String hostName, Tuple2<String, String> fromInfo, List<Tuple2<String, String>> toList, List<Tuple2<String, String>> ccList, String subject, String textContent) throws EmailException {
        SimpleEmail simpleEmail = buildSimpleEmail(authenticator, hostName, fromInfo, toList, ccList, subject);
        simpleEmail.setMsg(textContent);

        simpleEmail.send();
    }

    /**
     * 发送HTML格式邮件
     */
    public static void sendHtmlEmail(Authenticator authenticator, String hostName, Tuple2<String, String> fromInfo, List<Tuple2<String, String>> toList, List<Tuple2<String, String>> ccList, String subject, String htmlContent) throws EmailException {
        HtmlEmail htmlEmail = buildHtmlEmail(authenticator, hostName, fromInfo, toList, ccList, subject);
        htmlEmail.setHtmlMsg(htmlContent);

        htmlEmail.send();
    }

    private static SimpleEmail buildSimpleEmail(Authenticator authenticator, String hostName, Tuple2<String, String> fromInfo, List<Tuple2<String, String>> toList, List<Tuple2<String, String>> ccList, String subject) throws EmailException {
        SimpleEmail simpleEmail = new SimpleEmail();
        return setEmailBaseInfo(simpleEmail, authenticator, hostName, fromInfo, toList, ccList, subject);
    }

    private static HtmlEmail buildHtmlEmail(Authenticator authenticator, String hostName, Tuple2<String, String> fromInfo, List<Tuple2<String, String>> toList, List<Tuple2<String, String>> ccList, String subject) throws EmailException {
        HtmlEmail htmlEmail = new HtmlEmail();
        return setEmailBaseInfo(htmlEmail, authenticator, hostName, fromInfo, toList, ccList, subject);
    }

    private static <T extends Email> T setEmailBaseInfo(T email, Authenticator authenticator, String hostName, Tuple2<String, String> fromInfo, List<Tuple2<String, String>> toList, List<Tuple2<String, String>> ccList, String subject) throws EmailException {
        email.setCharset("UTF-8");
        email.setAuthenticator(authenticator);
        email.setHostName(hostName);
        email.setFrom(fromInfo.getP1(), fromInfo.getP2());
        email.setSubject(subject);

        for (Tuple2<String, String> toTuple : toList) {
            email.addTo(toTuple.getP1(), toTuple.getP2());
        }

        if (CollectionUtils.isNotEmpty(ccList)) {
            if (CollectionUtils.isNotEmpty(ccList)) {
                for (Tuple2<String, String> toTuple : ccList) {
                    email.addCc(toTuple.getP1(), toTuple.getP2());
                }
            }
        }

        return email;
    }

}