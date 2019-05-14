package kdmc_kumar.Doctor_Modules;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String lang = LocaleHelper.getPersistedData(context, Locale.getDefault().getLanguage());
        return LocaleHelper.setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = LocaleHelper.getPersistedData(context, defaultLanguage);
        return LocaleHelper.setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return LocaleHelper.getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        LocaleHelper.persist(context, language);

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return LocaleHelper.updateResources(context, language);
        }

        return LocaleHelper.updateResourcesLegacy(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LocaleHelper.SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();

        editor.putString(LocaleHelper.SELECTED_LANGUAGE, language);
        editor.apply();
    }

    @TargetApi(VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }
}