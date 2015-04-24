package com.webtrekk.android.tracking;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 11/03/15.
 *
 * this class deals with all the possible tracking parameters, it offers a static enum for all the valid ones
 * it also allows to return the params as url, and maybe later as json as well
 * it offers the user some helper functions for easy of use, and will be created before a tracking request is send
 * the manual information has to be added by the user, the automatic information will be added by the sdk
 */
public class TrackingParams {
    // general tracking params
    private TreeMap<Params, String> tparams;
    // customer trackingparams, defined by the app
    private TreeMap<String, String> page_params;
    private TreeMap<String, String> session_params;
    private TreeMap<String, String> ecom_params;
    private TreeMap<String, String> user_categories;
    private TreeMap<String, String> page_categories;
    private TreeMap<String, String> ad_params;
    private TreeMap<String, String> action_params;
    private TreeMap<String, String> product_categories;
    private TreeMap<String, String> media_categories;
    private HashMap<String, Object> plugin_params;


    public TrackingParams() {
        this.tparams = new TreeMap<>();
        this.page_params = new TreeMap<>();
        this.session_params = new TreeMap<>();
        this.ecom_params = new TreeMap<>();
        this.user_categories = new TreeMap<>();
        this.page_categories = new TreeMap<>();
        this.ad_params = new TreeMap<>();
        this.action_params = new TreeMap<>();
        this.product_categories = new TreeMap<>();
        this.media_categories = new TreeMap<>();
    }

    /*
     * this method adds a tracking param to the HashMap
     * if the key already exists it will be updated
     */
    public TrackingParams add(Params key, String value) {
        tparams.put(key, value);
        return this;
    }
    /**
     * this method ads new plugin params, each plugin can check by the key if it has the neccesary information
     * this can be used for example to pass the activity as reference for a plugin which needs to get resource access
     *
     * @param key
     * @Param value
     * @return this
     */
    public TrackingParams add(String key, Object value) {
        this.plugin_params.put(key, value);
        return this;
    }

    //TODO: noch mehr add Methoden für int/long/double usw, dann muss der nutzer nicht mehr manuell String.valueOf nehmen beispiel mediatracking positionen


    public TrackingParams add(Params key, String index, String value) {
        switch(key) {
            case ACTION:
                this.action_params.put(index, value);
                break;
            case PAGE:
                this.page_params.put(index, value);
                break;
            case SESSION:
                this.session_params.put(index, value);
                break;
            case ECOM:
                this.ecom_params.put(index, value);
                break;
            case AD:
                this.ad_params.put(index, value);
                break;
            case USER_CAT:
                this.user_categories.put(index, value);
                break;
            case PAGE_CAT:
                this.page_categories.put(index, value);
                break;
            case PRODUCT_CAT:
                this.product_categories.put(index, value);
                break;
            case MEDIA_CAT:
                this.media_categories.put(index, value);
                break;
            default:
                Log.d(WTrack.LOGTAG, "invalid trackingparam type");
        }
        return this;
    }

    /**
     * this function adds the auto tracked values to the trackingparams map
     * it will be called during before the request is send by the sdk
     * @param auto_tracked_values
     * @return
     *
     */
    public TrackingParams add(HashMap<Params, String> auto_tracked_values) {
        for(Map.Entry<Params, String> entry : auto_tracked_values.entrySet()) {
            tparams.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public TreeMap<Params, String> getTparams() {
        return tparams;
    }

    public TreeMap<String, String> getPage_params() {
        return page_params;
    }

    public TreeMap<String, String> getSession_params() {
        return session_params;
    }

    public TreeMap<String, String> getEcom_params() {
        return ecom_params;
    }

    public TreeMap<String, String> getUser_categories() {
        return user_categories;
    }

    public TreeMap<String, String> getPage_categories() {
        return page_categories;
    }

    public TreeMap<String, String> getAd_params() {
        return ad_params;
    }

    public TreeMap<String, String> getAction_params() {
        return action_params;
    }

    public TreeMap<String, String> getProduct_categories() {
        return product_categories;
    }

    public void setTparams(TreeMap<Params, String> tparams) {
        this.tparams = tparams;
    }

    public TreeMap<String, String> getMedia_categories() {
        return media_categories;
    }

    public HashMap<String, Object> getPlugin_params() {
        return plugin_params;
    }

    public enum Params {
        // automatic data
        SCREEN_RESOLUTION("res"),
        SCREEN_DEPTH("depth"),
        DEV_LANG("la"),
        TIMEZONE("tz"),
        OS_NAME("osname"),
        OS_VERSION("osversion"),
        DEVICE("dev"),
        TRACKING_LIB_VERSION("tracklib"),
        APP_VERSION_NAME("aversion_name"),
        APP_VERSION_CODE("aversion_code"),
        APP_LANGUAGE("alang"),
        APP_UPDATE("aupdate"),
        APP_PREINSTALLED("apreinstalled"),
        APP_FIRST_START("one"),
        EVERID("eid"),
        PLAYSTORE_GNAME("ps_gname"),
        PLAYSTORE_SNAME("ps_sname"),
        PLAYSTORE_MAIL("ps_mail"),
        ACTIVITY_NAME("aname"),
        API_LEVEL("api"),
        ADVERTISER_ID("adid"),
        ADVERTISER_OPTOUT("adoo"),
        // we dont use a haspmap here but instead breadcrumbs like path values so e.g. /de/appname/start
        ACTIVITY_CATEGORY("acat"),
        // manual data
        BIRTHDAY("uc707"), // geburtstag
        CAMPAIGN("cp"),
        CITY("uc708"),
        COUNTRY("uc709"),
        VOUCHER("cb563"), //Gutscheinwert
        CURRENCY("cr"),
        CAMPAIGN_PARAMS("cp_params"),
        CUSTOMER_ID("cd"), // kundennnummer
        EMAIL("uc700"), // email
        EMAIL_RID("uc701"), // email rid
        NEWSLETTER("uc702"),
        GNAME("uc703"), // vorname
        SNAME("uc704"), // nachname
        SSL("ssl"),
        GENDER("uc705"), //geschlecht
        INTERN_SEARCH("is"),
        ACTION_NAME("ct"),
        ORDER_NUMBER("oi"), // Bestellnummer
        ORDER_TOTAL("ov"), // Gesamtbestellwert
        ZIP("uc710"), // postleitzahl
        STREET("uc711"),
        STREETNUMBER("uc712"),
        PHONE("uc705"), // telefonnummer
        PRODUCT("ba"), //produkt
        PRODUCT_COST("co"), // produktkosten
        PRODUCT_COUNT("qn"), // produkt anzahl
        PRODUCT_STATUS("st"), // produkt status ( ad, view, conf)
        USER_CATEGORY("u_cat"),
        IP_ADDRESS("X_WT_IP"),
        TIMESTAMP("ts"),
        CURRENT_TIME("mts"),
        USERAGENT("X-WT-UA"),
        ADVERTISEMENT("mc"), // Werbemittel
        //mediatrackingparams
        MEDIA_FILE("mi"), // media datei
        MEDIA_ACTION("mk"), //play,pause,stop,pos,seek,eof
        MEDIA_POS("mt1"), //aktuelle position
        MEDIA_LENGTH("mt2"), // laenge der aktuellen media datei
        MEDIA_BANDWITH("bw"), // bandbreite der mediendatei
        MEDIA_VOLUME("vol"), // lautstaerke der mediendatei
        MEDIA_MUTED("mut"),
        MEDIA_TIMESTAMP("x"),// timestamp um caching zu umgehen
        // die vormaligen paramtypes
        //TODO: die url params ergaenzen und in der request klasse verwenden
        PAGE(""),
        SESSION(""),
        ECOM(""),
        AD(""),
        ACTION(""),
        USER_CAT(""),
        PAGE_CAT(""),
        PRODUCT_CAT(""),
        MEDIA_CAT(""),
        // this are string/value pairs which are just used to pass objects/references and values to the plugins, they are not used by the tracking lib,
        PLUGIN_PARAM("");

        private final String value;

        Params(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

}
