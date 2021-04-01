package plugin.huaweiPushKit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.entity.AAIDResult;
import com.huawei.hms.common.ApiException;
import com.naef.jnlua.LuaState;

import static plugin.huaweiPushKit.LuaLoader.dispatchEvent;
import static plugin.huaweiPushKit.LuaLoader.sendDispatcher;

class HmsInstanceIdWrapper {
    @SuppressLint("StaticFieldLeak")
    private static HmsInstanceId hmsInstanceId;

    private static void initHmsInstanceId() {
        if (hmsInstanceId == null) {
            hmsInstanceId = HmsInstanceId.getInstance(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getId(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        try {
            L.pushString(hmsInstanceId.getId());
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "getId failed " + e);
            return 0;
        }
    }

    static int getAAID(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        hmsInstanceId.getAAID().addOnSuccessListener(new OnSuccessListener<AAIDResult>() {
            @Override
            public void onSuccess(AAIDResult data) {
                sendDispatcher(listener, false, data.getId(), Constants.getAAID, Constants.provider);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, "getAAID failed " + rtnCode + " - " + e.getMessage(), Constants.getAAID, Constants.provider);
                }
            }
        });

        return 0;
    }

    static int getCreationTime(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        try {
            L.pushInteger((int) hmsInstanceId.getCreationTime());
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "getCreationTime failed " + e);
            return 0;
        }
    }

    static int deleteAAID(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        try {
            hmsInstanceId.deleteAAID();
        } catch (Exception e) {
            Log.e(Constants.TAG, "deleteAAID failed " + e);
        }

        return 0;
    }

    static int getToken(final LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        new Thread() {
            @Override
            public void run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    String appId = AGConnectServicesConfig.fromContext(CoronaEnvironment.getCoronaActivity()).getString("client/app_id");
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(CoronaEnvironment.getCoronaActivity()).getToken(appId, tokenScope);
                    Log.i(Constants.TAG, "get token: " + token);
                    dispatchEvent(false, token, Constants.getToken, Constants.provider);
                } catch (ApiException e) {
                    Log.e(Constants.TAG, "get token failed, " + e);
                    dispatchEvent(true, "get token failed, " + e, Constants.getToken, Constants.provider);
                }
            }
        }.start();

        return 0;
    }

    static int deleteToken(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsInstanceId();

        try {
            String appId = AGConnectServicesConfig.fromContext(CoronaEnvironment.getCoronaActivity()).getString("client/app_id");
            String tokenScope = "HCM";
            hmsInstanceId.deleteToken(appId, tokenScope);
        } catch (Exception e) {
            Log.e(Constants.TAG, "deleteToken failed " + e);
        }
        return 0;
    }
}
