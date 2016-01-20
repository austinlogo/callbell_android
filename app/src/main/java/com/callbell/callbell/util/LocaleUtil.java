package com.callbell.callbell.util;

import android.app.Activity;
import android.content.Context;

public class LocaleUtil {

    public enum AvailableLocales {
        EN,
        ES
    }

    public static AvailableLocales getLocale(Context ctx) {
        return AvailableLocales.valueOf(ctx.getResources().getConfiguration().locale.getLanguage().toUpperCase());
    }
}



