package plugin.huaweiPushKit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ansca.corona.CoronaEnvironment;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.push.SendException;

import org.json.JSONException;

import static plugin.huaweiPushKit.LuaLoader.dispatchEvent;
import static plugin.huaweiPushKit.Utils.bundleToJsonObject;
import static plugin.huaweiPushKit.Utils.remoteMessageToJsonObject;

public class HmsMessageServiceWrapper extends HmsMessageService {

    private final static String CODELABS_ACTION = CoronaEnvironment.getApplicationContext().getPackageName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.i(Constants.TAG, "onMessageReceived is called");
        if (message == null) {
            Log.e(Constants.TAG, "Received message entity is null!");
            return;
        }

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onMessageReceived");
        intent.putExtra("msg", "onMessageReceived called, message id:" + message.getMessageId() + ", payload data:"
                + message.getData());

        sendBroadcast(intent);

        try {
            dispatchEvent(false, "", Constants.onMessageReceived, Constants.provider, remoteMessageToJsonObject(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageSent(String msgId) {
        Log.i(Constants.TAG, "onMessageSent called, Message id:" + msgId);
        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onMessageSent");
        intent.putExtra("msg", "onMessageSent called, Message id:" + msgId);

        sendBroadcast(intent);

        dispatchEvent(false, msgId, Constants.onMessageSent, Constants.provider);
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        Log.i(Constants.TAG, "onSendError called, message id:" + msgId + ", ErrCode:"
                + ((SendException) exception).getErrorCode() + ", description:" + exception.getMessage());

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onSendError");
        intent.putExtra("msg", "onSendError called, message id:" + msgId + ", ErrCode:"
                + ((SendException) exception).getErrorCode() + ", description:" + exception.getMessage());

        sendBroadcast(intent);

        dispatchEvent(true, "Message Id : " + msgId + " Exception : " + exception, Constants.onSendError, Constants.provider);
    }

    @Override
    public void onNewToken(String token) {
        Log.i(Constants.TAG, "received refresh token:" + token);

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onNewToken");
        intent.putExtra("msg", "onNewToken called, token: " + token);

        sendBroadcast(intent);

        dispatchEvent(false, token, Constants.onNewToken, Constants.provider);
    }



    @Override
    public void onNewToken(String token, Bundle bundle) {
        Log.i(Constants.TAG, "received refresh token:" + token);

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onNewToken");
        intent.putExtra("msg", "onNewToken called, token: " + token);

        sendBroadcast(intent);

        try {
            dispatchEvent(false, token, Constants.onNewToken, Constants.provider, bundleToJsonObject(bundle));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTokenError(Exception exception) {
        dispatchEvent(true, "Exception : " + exception, Constants.onTokenError, Constants.provider);
    }

    @Override
    public void onTokenError(Exception exception, Bundle bundle) {
        try {
            dispatchEvent(true, "Exception : " + exception, Constants.onTokenError, Constants.provider, bundleToJsonObject(bundle));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageDelivered(String msgId, Exception exception) {
        dispatchEvent(true, "Message Id : " + msgId + " Exception : " + exception, Constants.onMessageDelivered, Constants.provider);
    }
}
