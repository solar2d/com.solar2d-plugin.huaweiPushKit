package plugin.huaweiPushKit;

import android.os.Bundle;
import com.huawei.hms.push.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

class Utils {
    static JSONObject bundleToJsonObject(Bundle bundle) throws JSONException {
        JSONObject bundleJsonObject = new JSONObject();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                bundleJsonObject.put(key, bundle.getString(key));
            }
        }
        return bundleJsonObject;
    }

    static JSONObject remoteMessageToJsonObject(RemoteMessage message) throws JSONException {
        JSONObject remoteMessageJsonObject = new JSONObject();
        remoteMessageJsonObject.put("CollapseKey", message.getCollapseKey());
        remoteMessageJsonObject.put("Data", message.getData());
        remoteMessageJsonObject.put("DataOfMap", message.getDataOfMap());
        remoteMessageJsonObject.put("MessageId", message.getMessageId());
        remoteMessageJsonObject.put("MessageType", message.getMessageType());
        remoteMessageJsonObject.put("Notification", remoteMessageNotificationToJsonObject(message.getNotification()));
        remoteMessageJsonObject.put("OriginalUrgency", message.getOriginalUrgency());
        remoteMessageJsonObject.put("Urgency", message.getUrgency());
        remoteMessageJsonObject.put("Ttl", message.getTtl());
        remoteMessageJsonObject.put("SentTime", message.getSentTime());
        remoteMessageJsonObject.put("To", message.getTo());
        remoteMessageJsonObject.put("From", message.getFrom());
        remoteMessageJsonObject.put("Token", message.getToken());
        return remoteMessageJsonObject;
    }

    private static JSONObject remoteMessageNotificationToJsonObject(RemoteMessage.Notification notification) throws JSONException {
        JSONObject remoteMessageNotificationJsonObject = new JSONObject();
        remoteMessageNotificationJsonObject.put("Title", notification.getTitle());
        remoteMessageNotificationJsonObject.put("TitleLocalizationKey", notification.getTitleLocalizationKey());
        remoteMessageNotificationJsonObject.put("TitleLocalizationArgs", Arrays.toString(notification.getTitleLocalizationArgs()));
        remoteMessageNotificationJsonObject.put("BodyLocalizationKey", notification.getBodyLocalizationKey());
        remoteMessageNotificationJsonObject.put("BodyLocalizationArgs", Arrays.toString(notification.getBodyLocalizationArgs()));
        remoteMessageNotificationJsonObject.put("Body", notification.getBody());
        remoteMessageNotificationJsonObject.put("Icon", notification.getIcon());
        remoteMessageNotificationJsonObject.put("Sound", notification.getSound());
        remoteMessageNotificationJsonObject.put("Tag", notification.getTag());
        remoteMessageNotificationJsonObject.put("Color", notification.getColor());
        remoteMessageNotificationJsonObject.put("ClickAction", notification.getClickAction());
        remoteMessageNotificationJsonObject.put("ChannelId", notification.getChannelId());
        remoteMessageNotificationJsonObject.put("ImageUrl", notification.getImageUrl());
        remoteMessageNotificationJsonObject.put("Link", notification.getLink());
        remoteMessageNotificationJsonObject.put("NotifyId", notification.getNotifyId());
        remoteMessageNotificationJsonObject.put("DefaultSound", notification.isDefaultSound());
        remoteMessageNotificationJsonObject.put("DefaultVibrate", notification.isDefaultVibrate());
        remoteMessageNotificationJsonObject.put("When", notification.getWhen());
        remoteMessageNotificationJsonObject.put("LightSettings", Arrays.toString(notification.getLightSettings()));
        remoteMessageNotificationJsonObject.put("BadgeNumber", notification.getBadgeNumber());
        remoteMessageNotificationJsonObject.put("AutoCancel", notification.isAutoCancel());
        remoteMessageNotificationJsonObject.put("Importance", notification.getImportance());
        remoteMessageNotificationJsonObject.put("Ticker", notification.getTicker());
        remoteMessageNotificationJsonObject.put("VibrateConfig", Arrays.toString(notification.getVibrateConfig()));
        remoteMessageNotificationJsonObject.put("Visibility", notification.getVisibility());
        remoteMessageNotificationJsonObject.put("IntentUri", notification.getIntentUri());
        return remoteMessageNotificationJsonObject;
    }
}
