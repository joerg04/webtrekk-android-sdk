package com.webtrekk.webtrekksdk.Configuration;

import com.webtrekk.webtrekksdk.TrackingParameter;
import com.webtrekk.webtrekksdk.Utils.WebtrekkLogging;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * a global configuration class which gets its values from the xml config parser
 * all plugins, activity and other tracking details are configured here
 */
public class TrackingConfiguration {
    // the current version of the configuration, used to check if a new one is available
    private int version = 0;

    //global tracking configuration

    private String trackDomain;
    private String trackId;
    private int sampling = 0;
    private int sendDelay = 300;
    private int maxRequests = 5000;

    // activitylifycycle callbacks for automated activity tracking
    private boolean autoTracked = true;

    // auto tracking configuration
    private boolean autoTrackAppUpdate = true;
    private boolean autoTrackAdvertiserId = true;
    private boolean autoTrackAppVersionName = true;
    private boolean autoTrackAppVersionCode = true;
    private boolean autoTrackAppPreInstalled = true;
    private boolean autoTrackPlaystoreUsername = false;
    private boolean autoTrackPlaystoreMail = false;
    private boolean autoTrackPlaystoreGivenName = false;
    private boolean autoTrackPlaystoreFamilyName = false;
    private boolean autoTrackApiLevel = true;
    private boolean autoTrackScreenorientation = true;
    private boolean autoTrackConnectionType = true;
    private boolean autoTrackAdvertismentOptOut= true;

    // enabled plugins
    private boolean enablePluginHelloWorld;
    private boolean enableRemoteConfiguration = false;
    private String trackingConfigurationUrl;
    private boolean autoTrackRequestUrlStoreSize = true;
    //intervall when autotracked start activity is send again
    private int resendOnStartEventTime = 30;
    private boolean mErrorLogEnable = false;
    private int mErrorLogLevel = 3;


    // global trackingparameter xml values
    private TrackingParameter globalTrackingParameter;
    //global contant trackingparameter
    private TrackingParameter constGlobalTrackingParameter;

    private Map<String, ActivityConfiguration> activityConfigurations;

    // the customParameter map from the xml configuration
    private Map<String, String> customParameter;
    private Map<String, String> mRecommendationConfiguration;


    public enum AutoTrackedParameters{

        screenOrientation("783", TrackingParameter.Parameter.PAGE),
        requestUrlStoreSize("784", TrackingParameter.Parameter.PAGE),
        appVersion("804", TrackingParameter.Parameter.SESSION),
        appVersionCode("805", TrackingParameter.Parameter.SESSION),
        connectionType("807", TrackingParameter.Parameter.SESSION),
        advertiserId("809", TrackingParameter.Parameter.SESSION),
        playstoreMail("810", TrackingParameter.Parameter.SESSION),
        playstoreGivenname("811", TrackingParameter.Parameter.SESSION),
        playstoreFamilyname("812", TrackingParameter.Parameter.SESSION),
        advertisingOptOut("813", TrackingParameter.Parameter.SESSION),
        apiLevel("814", TrackingParameter.Parameter.SESSION),
        appUpdated("815", TrackingParameter.Parameter.SESSION),
        appPreinstalled("816", TrackingParameter.Parameter.SESSION);

        private final String mParValue;
        private final TrackingParameter.Parameter mParType;

        AutoTrackedParameters(String value, TrackingParameter.Parameter parType)
        {
            mParValue = value;
            mParType = parType;
        }

        public String getValue(){
            return mParValue;
        }

        public TrackingParameter.Parameter getParType()
        {
            return mParType;
        }
    }


    public TrackingConfiguration() {
        activityConfigurations = new HashMap<String, ActivityConfiguration>();
        customParameter = new HashMap<String, String>();
    }

    /**
     * this method validates that the tracking configuration is valid
     *
     * @return
     */
    public boolean validateConfiguration() {
        //TODO: implement validation rules
        boolean valid = true;
        if(sendDelay < 0) {
            WebtrekkLogging.log("invalid sendDelay Value");
            valid = false;
        }
        if(sampling < 0) {
            WebtrekkLogging.log("invalid sampling Value");
            valid = false;
        }
        if(maxRequests < 100) {
            WebtrekkLogging.log("invalid maxRequests Value");
            valid = false;
        }

        // check for mandatory values
        if(trackId == null || trackId.isEmpty() || trackId.length() < 5) {
            WebtrekkLogging.log("missing value: trackId");
            valid = false;
        }
        // make sure a trackdomain is configured
        if(trackDomain == null || trackDomain.isEmpty() || trackDomain.length() < 5) {
            WebtrekkLogging.log("missing value: trackId");
            valid = false;
        }
        //try if its a valid url
        try {
            new URL(trackDomain);
        } catch (MalformedURLException e) {
            WebtrekkLogging.log("invalid trackDomain Value");
            valid = false;
        }

        //try if autotrackedParametersOverrided. If yes - just inform
        checkParametersForAutoTrackOverride(globalTrackingParameter);
        checkParametersForAutoTrackOverride(constGlobalTrackingParameter);
        for (ActivityConfiguration activityConf: activityConfigurations.values())
        {
            checkParametersForAutoTrackOverride(activityConf.getConstActivityTrackingParameter());
            checkParametersForAutoTrackOverride(activityConf.getActivityTrackingParameter());
        }



        // check for details like valid url, min max values and so on
        return valid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTrackDomain() {
        return trackDomain;
    }

    public void setTrackDomain(String trackDomain) {
        this.trackDomain = trackDomain;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getSampling() {
        return sampling;
    }

    public void setSampling(int sampling) {
        this.sampling = sampling;
    }

    public int getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(int sendDelay) {
        this.sendDelay = sendDelay;
    }

    public int getResendOnStartEventTime() {
        return resendOnStartEventTime;
    }

    public void setResendOnStartEventTime(int resendOnStartEventTime) {
        this.resendOnStartEventTime = resendOnStartEventTime;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }



    public Map<String, ActivityConfiguration> getActivityConfigurations() {
        return activityConfigurations;
    }

    public void setActivityConfigurations(Map<String, ActivityConfiguration> activityConfigurations) {
        this.activityConfigurations = activityConfigurations;
    }


    public String getTrackingConfigurationUrl() {
        return trackingConfigurationUrl;
    }

    public void setTrackingConfigurationUrl(String trackingConfigurationUrl) {
        this.trackingConfigurationUrl = trackingConfigurationUrl;
    }

    public boolean isAutoTracked() {
        return autoTracked;
    }

    public void setIsAutoTracking(boolean isAutoTracking) {
        this.autoTracked = isAutoTracking;
    }

    public boolean isErrorLogEnable() {return mErrorLogEnable;}
    public void setErrorLogEnable(boolean value) {mErrorLogEnable = value;}

    public int getErrorLogLevel() {return mErrorLogLevel;}
    public void setErrorLogLevel(int value) {mErrorLogLevel = value;}

    public TrackingParameter getGlobalTrackingParameter() {
        return globalTrackingParameter;
    }

    public void setGlobalTrackingParameter(TrackingParameter globalTrackingParameter) {
        this.globalTrackingParameter = globalTrackingParameter;
    }

    public void setAutoTracked(boolean autoTracked) {
        this.autoTracked = autoTracked;
    }

    public boolean isAutoTrackAppUpdate() {
        return autoTrackAppUpdate;
    }

    public void setAutoTrackAppUpdate(boolean autoTrackAppUpdate) {
        this.autoTrackAppUpdate = autoTrackAppUpdate;
    }

    public boolean isAutoTrackAdvertiserId() {
        return autoTrackAdvertiserId;
    }

    public void setAutoTrackAdvertiserId(boolean autoTrackAdvertiserId) {
        this.autoTrackAdvertiserId = autoTrackAdvertiserId;
    }

    public boolean isAutoTrackAppVersionName() {
        return autoTrackAppVersionName;
    }

    public void setAutoTrackAppVersionName(boolean autoTrackAppVersionName) {
        this.autoTrackAppVersionName = autoTrackAppVersionName;
    }

    public boolean isAutoTrackAppVersionCode() {
        return autoTrackAppVersionCode;
    }

    public void setAutoTrackAppVersionCode(boolean autoTrackAppVersionCode) {
        this.autoTrackAppVersionCode = autoTrackAppVersionCode;
    }

    public boolean isAutoTrackAppPreInstalled() {
        return autoTrackAppPreInstalled;
    }

    public void setAutoTrackAppPreInstalled(boolean autoTrackAppPreInstalled) {
        this.autoTrackAppPreInstalled = autoTrackAppPreInstalled;
    }

    public boolean isAutoTrackPlaystoreUsername() {
        return autoTrackPlaystoreUsername;
    }

    public void setAutoTrackPlaystoreUsername(boolean autoTrackPlaystoreUsername) {
        this.autoTrackPlaystoreUsername = autoTrackPlaystoreUsername;
    }

    public boolean isAutoTrackPlaystoreMail() {
        return autoTrackPlaystoreMail;
    }

    public void setAutoTrackPlaystoreMail(boolean autoTrackPlaystoreMail) {
        this.autoTrackPlaystoreMail = autoTrackPlaystoreMail;
    }

    public boolean isAutoTrackPlaystoreGivenName() {
        return autoTrackPlaystoreGivenName;
    }

    public void setAutoTrackPlaystoreGivenName(boolean autoTrackPlaystoreGivenName) {
        this.autoTrackPlaystoreGivenName = autoTrackPlaystoreGivenName;
    }

    public boolean isAutoTrackPlaystoreFamilyName() {
        return autoTrackPlaystoreFamilyName;
    }

    public void setAutoTrackPlaystoreFamilyName(boolean autoTrackPlaystoreFamilyName) {
        this.autoTrackPlaystoreFamilyName = autoTrackPlaystoreFamilyName;
    }

    public boolean isAutoTrackApiLevel() {
        return autoTrackApiLevel;
    }

    public void setAutoTrackApiLevel(boolean autoTrackApiLevel) {
        this.autoTrackApiLevel = autoTrackApiLevel;
    }

    public boolean isAutoTrackScreenorientation() {
        return autoTrackScreenorientation;
    }

    public void setAutoTrackScreenorientation(boolean autoTrackScreenorientation) {
        this.autoTrackScreenorientation = autoTrackScreenorientation;
    }

    public boolean isAutoTrackConnectionType() {
        return autoTrackConnectionType;
    }

    public void setAutoTrackConnectionType(boolean autoTrackConnectionType) {
        this.autoTrackConnectionType = autoTrackConnectionType;
    }

    public boolean isAutoTrackAdvertismentOptOut() {
        return autoTrackAdvertismentOptOut;
    }

    public void setAutoTrackAdvertismentOptOut(boolean autoTrackAdvertismentOptOut) {
        this.autoTrackAdvertismentOptOut = autoTrackAdvertismentOptOut;
    }

    public boolean isEnablePluginHelloWorld() {
        return enablePluginHelloWorld;
    }

    public void setEnablePluginHelloWorld(boolean enablePluginHelloWorld) {
        this.enablePluginHelloWorld = enablePluginHelloWorld;
    }

    public boolean isEnableRemoteConfiguration() {
        return enableRemoteConfiguration;
    }

    public void setEnableRemoteConfiguration(boolean enableRemoteConfiguration) {
        this.enableRemoteConfiguration = enableRemoteConfiguration;
    }

    public boolean isAutoTrackRequestUrlStoreSize() {
        return autoTrackRequestUrlStoreSize;
    }

    public void setAutoTrackRequestUrlStoreSize(boolean autoTrackRequestUrlStoreSize) {
        this.autoTrackRequestUrlStoreSize = autoTrackRequestUrlStoreSize;
    }

    public Map<String, String> getCustomParameter() {
        return customParameter;
    }

    public void setCustomParameter(Map<String, String> customParameter) {
        this.customParameter = customParameter;
    }

    public TrackingParameter getConstGlobalTrackingParameter() {
        return constGlobalTrackingParameter;
    }

    public void setConstGlobalTrackingParameter(TrackingParameter constGlobalTrackingParameter) {
        this.constGlobalTrackingParameter = constGlobalTrackingParameter;
    }

    public Map<String, String> getRecommendationConfiguration() {
        return mRecommendationConfiguration;
    }

    public void setRecommendationConfiguration(Map<String, String> recommendationConfiguration) {
        mRecommendationConfiguration = recommendationConfiguration;
    }

    // process custom Parameters
    public TrackingParameter getAutoTrackedParameters(Map<String, String> autoParameters){
        TrackingParameter tp  = new TrackingParameter();

        if (autoTrackScreenorientation) {
            addCustomParameter(tp, AutoTrackedParameters.screenOrientation, autoParameters);
        }
        if (autoTrackRequestUrlStoreSize) {
            addCustomParameter(tp, AutoTrackedParameters.requestUrlStoreSize, autoParameters);
        }
        if (autoTrackAppVersionName) {
            addCustomParameter(tp, AutoTrackedParameters.appVersion, autoParameters);
        }
        if (autoTrackAppVersionCode) {
            addCustomParameter(tp, AutoTrackedParameters.appVersionCode, autoParameters);
        }
        if (autoTrackConnectionType) {
            addCustomParameter(tp, AutoTrackedParameters.connectionType, autoParameters);
        }
        if (autoTrackAdvertiserId) {
            addCustomParameter(tp, AutoTrackedParameters.advertiserId, autoParameters);
        }
        if (autoTrackPlaystoreMail) {
            addCustomParameter(tp, AutoTrackedParameters.playstoreMail, autoParameters);
        }
        if (autoTrackPlaystoreGivenName) {
            addCustomParameter(tp, AutoTrackedParameters.playstoreGivenname, autoParameters);
        }
        if (autoTrackPlaystoreUsername) {
            addCustomParameter(tp, AutoTrackedParameters.playstoreFamilyname, autoParameters);
        }
        if (autoTrackAdvertismentOptOut) {
            addCustomParameter(tp, AutoTrackedParameters.advertisingOptOut, autoParameters);
        }
        if (autoTrackApiLevel) {
            addCustomParameter(tp, AutoTrackedParameters.apiLevel, autoParameters);
        }
        if (autoTrackAppUpdate) {
            addCustomParameter(tp, AutoTrackedParameters.appUpdated, autoParameters);
        }
        if (autoTrackAppPreInstalled) {
            addCustomParameter(tp, AutoTrackedParameters.appPreinstalled, autoParameters);
        }
        return tp;
    }

    private void addCustomParameter(TrackingParameter parList, AutoTrackedParameters par, Map<String, String> autoParameters){
        String parValue = autoParameters.get(par.name());

        if (parValue != null && !parValue.isEmpty()) {
            parList.add(par.getParType(), par.getValue(), parValue);
        }else
        {
            WebtrekkLogging.log("autotracking parameter " + par.name() + " isn't defined");
        }
    }

    public void checkParametersForAutoTrackOverride(TrackingParameter par)
    {
        if (par != null)
        {
            checkParametersArrayForAutoTrackOverride(par.getSessionParameter(), TrackingParameter.Parameter.SESSION);
            checkParametersArrayForAutoTrackOverride(par.getPageParameter(), TrackingParameter.Parameter.PAGE);
        }
    }

    private void checkParametersArrayForAutoTrackOverride(SortedMap<String, String> array, TrackingParameter.Parameter parType)
    {
        if (array == null)
            return;

        for (String key: array.keySet())
        {
            AutoTrackedParameters autoTrackedParameter = isInAutoParameterList(parType, key);
            if (autoTrackedParameter != null && parameterAutoTrackedStatus(autoTrackedParameter) && autoTrackedParameter.getParType() == parType)
            {
                WebtrekkLogging.log("Warning: auto parameter " +autoTrackedParameter.name()+ " will be overwritten. If you don't want it, remove "+
                        parType.name()+" custom parameter number"+autoTrackedParameter.getValue()+ " definition");
            }
        }
    }

    private AutoTrackedParameters isInAutoParameterList(TrackingParameter.Parameter parType, String parValue)
    {
        for (AutoTrackedParameters par: AutoTrackedParameters.values())
        {
            if (par.getParType() == parType && par.getValue().equals(parValue))
            {
                return par;
            }
        }
        return null;
    }

    private boolean parameterAutoTrackedStatus(AutoTrackedParameters par)
    {
        boolean retValue = false;

        switch (par){
            case screenOrientation:
                retValue = autoTrackScreenorientation;
                break;
            case requestUrlStoreSize:
                retValue = autoTrackRequestUrlStoreSize;
                break;
            case appVersion:
                retValue = autoTrackAppVersionName;
                break;
            case appVersionCode:
                retValue = autoTrackAppVersionCode;
                break;
            case connectionType:
                retValue = autoTrackConnectionType;
                break;
            case advertiserId:
                retValue = autoTrackAdvertiserId;
                break;
            case playstoreMail:
                retValue = autoTrackPlaystoreMail;
                break;
            case playstoreGivenname:
                retValue = autoTrackPlaystoreGivenName;
                break;
            case playstoreFamilyname:
                retValue = autoTrackPlaystoreFamilyName;
                break;
            case advertisingOptOut:
                retValue = autoTrackAdvertismentOptOut;
                break;
            case apiLevel:
                retValue = autoTrackApiLevel;
                break;
            case appUpdated:
                retValue = autoTrackAppUpdate;
                break;
            case appPreinstalled:
                retValue = autoTrackAppPreInstalled;
                break;
            default:
                WebtrekkLogging.log("Error: incorrect par name"+ par.name() + " for parameterAutotrackedStatus");
                break;
        }

        return retValue;
    }
}
