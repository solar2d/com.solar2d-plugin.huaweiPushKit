package plugin.huaweiPushKit;

class Constants {

    static final String TAG = "HuaweiPushKit";
    static final String eventName = "HmsPushKit";
    static final String provider = "HmsPushKit";

    // Classes
    static final String HmsInstanceId = "HmsInstanceId";
    static final String HmsMessageService = "HmsMessageService";
    static final String HmsMessaging = "HmsMessaging";
    static final String HmsProfile = "HmsProfile";
    static final String OpenDeviceClient = "OpenDeviceClient";

    // HmsInstanceId
    static final String getId = "getId";
    static final String getAAID = "getAAID";
    static final String getCreationTime = "getCreationTime";
    static final String deleteAAID = "deleteAAID";
    static final String getToken = "getToken";
    static final String deleteToken = "deleteToken";

    // OpenDevice
    static final String getOdid = "getOdid";

    // HmsMessaging
    static final String isAutoInitEnabled = "isAutoInitEnabled";
    static final String setAutoInitEnabled = "setAutoInitEnabled";
    static final String subscribe = "subscribe";
    static final String unsubscribe = "unsubscribe";
    static final String send = "send";
    static final String turnOnPush = "turnOnPush";
    static final String turnOffPush = "turnOffPush";

    // HmsProfile
    static final String isSupportProfile = "isSupportProfile";
    static final String addProfile = "addProfile";
    static final String deleteProfile = "deleteProfile";
    static final String HUAWEI_PROFILE = "HUAWEI_PROFILE";
    static final String CUSTOM_PROFILE = "CUSTOM_PROFILE";

    // ProxySettings
    static final String setCountryCode = "setCountryCode";

    // HmsMessageService
    static final String onMessageReceived = "onMessageReceived";
    static final String onMessageSent = "onMessageSent";
    static final String onSendError = "onSendError";
    static final String onNewToken = "onNewToken";
    static final String onTokenError = "onTokenError";
    static final String onMessageDelivered = "onMessageDelivered";
}
