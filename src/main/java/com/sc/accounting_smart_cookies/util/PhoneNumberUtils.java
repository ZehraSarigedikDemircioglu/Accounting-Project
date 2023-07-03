package com.sc.accounting_smart_cookies.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberUtils {

    public static String formatPhoneNumber(String phoneNumber, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber parsedNumber;

        try {
            parsedNumber = phoneNumberUtil.parse(phoneNumber, regionCode);
        } catch (NumberParseException e) {
            return phoneNumber;
        }

        String number= phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        return number.replaceFirst(" "," (").replaceFirst("-",") ");
    }
}