package plugin.huaweiPushKit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsProfile;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import static plugin.huaweiPushKit.LuaLoader.dispatchEvent;
import static plugin.huaweiPushKit.LuaLoader.sendDispatcher;

class HmsProfileWrapper {
    @SuppressLint("StaticFieldLeak")
    private static HmsProfile hmsProfile;

    private static void initHmsProfile() {
        if (hmsProfile == null) {
            hmsProfile = HmsProfile.getInstance(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int isSupportProfile(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsProfile();

        try {
            L.pushBoolean(hmsProfile.isSupportProfile());
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "isSupportProfile failed " + e);
            dispatchEvent(true, "isSupportProfile failed, catch exception : " + e.getMessage(), Constants.isSupportProfile, Constants.provider);
            return 0;
        }
    }

    static int addProfile(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsProfile();

        Task<Void> task;
        final int listener;
        listener = CoronaLua.isListener(L, 3, Constants.eventName) ? CoronaLua.newRef(L, 3) : CoronaLua.REFNIL;

        String subjectId = null, profileId = null;
        int type = 0;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "type");
            if (L.isNumber(-1)) {
                type = L.toInteger(-1);
                L.pop(1);
            } else {
                Log.e(Constants.TAG, "addProfile(int type, String profileId) expected");
                sendDispatcher(listener, true, "addProfile(int type, String profileId) expected ", Constants.addProfile, Constants.provider);
                return 0;
            }

            L.getField(2, "profileId");
            if (L.isString(-1)) {
                profileId = L.toString(-1);
                L.pop(1);
            } else {
                Log.e(Constants.TAG, "addProfile(int type, String profileId) expected");
                sendDispatcher(listener, true, "addProfile(int type, String profileId) expected", Constants.addProfile, Constants.provider);
                return 0;
            }

            L.getField(2, "subjectId");
            if (L.isString(-1)) {
                subjectId = L.toString(-1);
                L.pop(1);
            }
        } else {
            Log.e(Constants.TAG, "addProfile(int type, String profileId)");
            sendDispatcher(listener, true, "addProfile(int type, String profileId)", Constants.addProfile, Constants.provider);
            return 0;
        }

        if (type != 0 && profileId != null && subjectId != null) {
            task = hmsProfile.addProfile(subjectId, type, profileId);
        } else if (type != 0 && profileId != null) {
            task = hmsProfile.addProfile(type, profileId);
        } else {
            return 0;
        }

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendDispatcher(listener, false, "getAAID success ", Constants.addProfile, Constants.provider);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                sendDispatcher(listener, true, "getAAID failed " + e.getMessage(), Constants.addProfile, Constants.provider);
            }
        });
        return 0;
    }

    static int deleteProfile(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsProfile();

        Task<Void> task;
        final int listener;
        listener = CoronaLua.isListener(L, 3, Constants.eventName) ? CoronaLua.newRef(L, 3) : CoronaLua.REFNIL;

        String subjectId = null, profileId = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "profileId");
            if (L.isString(-1)) {
                profileId = L.toString(-1);
                L.pop(1);
            } else {
                Log.e(Constants.TAG, "addProfile(int type, String profileId) expected");
                sendDispatcher(listener, true, "addProfile(int type, String profileId) expected", Constants.deleteProfile, Constants.provider);
                return 0;
            }

            L.getField(2, "subjectId");
            if (L.isString(-1)) {
                subjectId = L.toString(-1);
                L.pop(1);
            }
        } else {
            Log.e(Constants.TAG, "addProfile(int type, String profileId)");
            sendDispatcher(listener, true, "addProfile(int type, String profileId) expected", Constants.deleteProfile, Constants.provider);
            return 0;
        }

        if (profileId != null && subjectId != null) {
            task = hmsProfile.deleteProfile(subjectId, profileId);
        } else if (profileId != null) {
            task = hmsProfile.deleteProfile(profileId);
        } else {
            sendDispatcher(listener, true, "deleteProfile(String) or deleteProfile(String, String) expected", Constants.addProfile, Constants.provider);
            Log.e(Constants.TAG, "deleteProfile(String) or deleteProfile(String, String) expected");
            return 0;
        }

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendDispatcher(listener, false, "", Constants.deleteProfile, Constants.provider);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                sendDispatcher(listener, true, "deleteProfile failed " + e.getMessage(), Constants.deleteProfile, Constants.provider);
            }
        });

        return 0;
    }

    static int HUAWEI_PROFILE(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsProfile();

        try {
            L.pushInteger(HmsProfile.HUAWEI_PROFILE);
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "HUAWEI_PROFILE failed " + e);
            dispatchEvent(true, "HUAWEI_PROFILE failed " + e, Constants.HUAWEI_PROFILE, Constants.provider);
            return 0;
        }
    }

    static int CUSTOM_PROFILE(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsProfile();

        try {
            L.pushInteger(HmsProfile.CUSTOM_PROFILE);
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "CUSTOM_PROFILE failed " + e);
            dispatchEvent(true, "CUSTOM_PROFILE failed " + e, Constants.CUSTOM_PROFILE, Constants.provider);
            return 0;
        }
    }

}
