package plugin.huaweiPushKit;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsMessaging;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import static plugin.huaweiPushKit.LuaLoader.dispatchEvent;
import static plugin.huaweiPushKit.LuaLoader.sendDispatcher;

class HmsMessagingWrapper {
    @SuppressLint("StaticFieldLeak")
    private static HmsMessaging hmsMessaging;

    private static void initHmsMessaging() {
        if (hmsMessaging == null) {
            hmsMessaging = HmsMessaging.getInstance(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int isAutoInitEnabled(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }
        initHmsMessaging();

        try {
            L.pushBoolean(hmsMessaging.isAutoInitEnabled());
            return 1;
        } catch (Exception e) {
            Log.e(Constants.TAG, "isAutoInitEnabled Failed " + e);
            dispatchEvent(true, e.getMessage(), Constants.isAutoInitEnabled, Constants.provider);
            return 0;
        }
    }

    static int setAutoInitEnabled(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "enable");
            if (L.isBoolean(-1)) {
                hmsMessaging.setAutoInitEnabled(L.toBoolean(-1));
                Log.i(Constants.TAG, "setAutoInitEnabled success");
            } else {
                Log.e(Constants.TAG, "setAutoInitEnabled (Boolean) expected, got " + L.typeName(1));
            }
        } else {
            Log.e(Constants.TAG, "setAutoInitEnabled (Boolean) expected, got " + L.typeName(1));
            dispatchEvent(true, "setAutoInitEnabled (Boolean) expected, got " + L.typeName(1), Constants.setAutoInitEnabled, Constants.provider);
        }
        return 0;
    }

    static int subscribe(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();

        final int listener = CoronaLua.isListener(L, 3, Constants.eventName) ? CoronaLua.newRef(L, 3) : CoronaLua.REFNIL;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "topic");
            if (L.isString(-1)) {
                try {
                    hmsMessaging.subscribe(L.toString(-1))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(Constants.TAG, "subscribe topic successfully");
                                        sendDispatcher(listener, false, "subscribe topic successfully", Constants.subscribe, Constants.provider);
                                    } else {
                                        Log.e(Constants.TAG, "subscribe topic failed, return value is " + task.getException().getMessage());
                                        sendDispatcher(listener, true, "subscribe failed", Constants.subscribe, Constants.provider);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Log.e(Constants.TAG, "subscribe (STRING) expected, got " + L.typeName(1));
                    sendDispatcher(listener, true, "subscribe (STRING) expected, got " + L.typeName(1), Constants.subscribe, Constants.provider);
                }
                return 0;
            } else {
                Log.e(Constants.TAG, "subscribe (STRING) expected, got " + L.typeName(1));
                dispatchEvent(true, "subscribe (STRING) expected, got " + L.typeName(1), Constants.subscribe, Constants.provider);
                sendDispatcher(listener, true, "subscribe (STRING) expected, got " + L.typeName(1), Constants.subscribe, Constants.provider);
                return 0;
            }
        } else {
            Log.e(Constants.TAG, "subscribe (STRING) expected, got " + L.typeName(1));
            sendDispatcher(listener, true, "subscribe (STRING) expected, got " + L.typeName(1), Constants.subscribe, Constants.provider);
            return 0;
        }
    }

    static int unsubscribe(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();
        final int listener = CoronaLua.isListener(L, 3, Constants.eventName) ? CoronaLua.newRef(L, 3) : CoronaLua.REFNIL;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "topic");
            if (L.isString(-1)) {
                try {
                    hmsMessaging.unsubscribe(L.toString(-1))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(Constants.TAG, "unsubscribe success");
                                        sendDispatcher(listener, false, "unsubscribe success", Constants.unsubscribe, Constants.provider);
                                    } else {
                                        Log.e(Constants.TAG, "unsubscribe topic failed, return value is " + task.getException().getMessage());
                                        sendDispatcher(listener, true, "unsubscribe topic failed, return value is " + task.getException().getMessage(), Constants.unsubscribe, Constants.provider);
                                    }
                                }
                            });
                } catch (Exception e) {
                    Log.e(Constants.TAG, "unsubscribe failed, catch exception : " + e.getMessage());
                    sendDispatcher(listener, true, "unsubscribe failed, catch exception: " + e.getMessage(), Constants.unsubscribe, Constants.provider);
                }
                return 0;
            } else {
                Log.e(Constants.TAG, "unsubscribe (STRING) expected, got " + L.typeName(1));
                sendDispatcher(listener, true, "unsubscribe (STRING) expected, got " + L.typeName(1), Constants.unsubscribe, Constants.provider);

                return 0;
            }
        } else {
            Log.e(Constants.TAG, "unsubscribe (STRING) expected, got " + L.typeName(1));
            sendDispatcher(listener, true, "unsubscribe (STRING) expected, got " + L.typeName(1), Constants.unsubscribe, Constants.provider);

            return 0;
        }
    }

    static int send(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();

        return 0;
    }

    static int turnOnPush(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();

        final int listener = CoronaLua.isListener(L, 2, Constants.eventName) ? CoronaLua.newRef(L, 2) : CoronaLua.REFNIL;

        try {
            hmsMessaging.turnOnPush().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(Constants.TAG, "turnOnPush successfully");
                    sendDispatcher(listener, false, "turnOnPush successfully", Constants.turnOnPush, Constants.provider);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e(Constants.TAG, "turnOnPush failed " + e.getMessage());
                    sendDispatcher(listener, true, "turnOnPush failed " + e.getMessage(), Constants.turnOnPush, Constants.provider);
                }
            });
        } catch (Exception e) {
            Log.e(Constants.TAG, "turnOnPush failed " + e.getMessage());
            sendDispatcher(listener, true, "turnOnPush failed " + e.getMessage(), Constants.turnOnPush, Constants.provider);
        }
        return 0;
    }

    static int turnOffPush(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initHmsMessaging();
        final int listener = CoronaLua.isListener(L, 2, Constants.eventName) ? CoronaLua.newRef(L, 2) : CoronaLua.REFNIL;

        try {
            hmsMessaging.turnOffPush().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(Constants.TAG, "turnOffPush success");
                    sendDispatcher(listener, false, "turnOffPush successfully", Constants.turnOffPush, Constants.provider);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e(Constants.TAG, "turnOffPush failed " + e);
                    sendDispatcher(listener, true, "turnOffPush failed " + e.getMessage(), Constants.turnOffPush, Constants.provider);
                }
            });
        } catch (Exception e) {
            Log.e(Constants.TAG, "turnOffPush failed " + e);
            sendDispatcher(listener, true, "turnOffPush failed " + e.getMessage(), Constants.turnOffPush, Constants.provider);
        }

        return 0;
    }
}
