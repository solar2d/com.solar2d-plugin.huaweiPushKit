package plugin.huaweiPushKit;

import android.annotation.SuppressLint;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.opendevice.OpenDevice;
import com.huawei.hms.opendevice.OpenDeviceClient;
import com.huawei.hms.support.api.opendevice.OdidResult;
import com.naef.jnlua.LuaState;

import static plugin.huaweiPushKit.LuaLoader.sendDispatcher;

class OpenDeviceClientWrapper {

    @SuppressLint("StaticFieldLeak")
    private static OpenDeviceClient openDeviceCliente;

    private static void initOpenDeviceClient() {
        if (openDeviceCliente == null) {
            openDeviceCliente = OpenDevice.getOpenDeviceClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getOdid(final LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initOpenDeviceClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        openDeviceCliente.getOdid().addOnSuccessListener(new OnSuccessListener<OdidResult>() {
            @Override
            public void onSuccess(OdidResult data) {
                sendDispatcher(listener, false, data.getId(), Constants.getOdid, Constants.provider);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, Constants.getOdid + " Failed " + rtnCode, Constants.getOdid, Constants.provider);
                }
            }
        });
        
        return 0;
    }
}
