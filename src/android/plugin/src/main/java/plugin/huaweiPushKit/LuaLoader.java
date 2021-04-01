// e.g. [Lua] require "plugin.HuaweiPushKit"
// Author Kayahan Baskeser

package plugin.huaweiPushKit;

import android.content.Context;
import android.util.Log;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.LazyInputStream;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("WeakerAccess")
public class LuaLoader implements JavaFunction, CoronaRuntimeListener {

    private static int fListener;

    private static final String EVENT_NAME = "HuaweiPushKit";

    public static CoronaRuntimeTaskDispatcher fDispatcher = null;

    public LuaLoader() {
        fListener = CoronaLua.REFNIL;
        CoronaEnvironment.addRuntimeListener(this);
    }

    @Override
    public int invoke(LuaState L) {
        NamedJavaFunction[] luaFunctions = new NamedJavaFunction[]{
                new init(),
                new HmsInstanceId(),
                new HmsMessaging(),
                new HmsProfile(),
                new OpenDeviceClient(),
        };
        String libName = L.toString(1);
        L.register(libName, luaFunctions);

        return 1;
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        CoronaLua.dispatchEvent(L, listener, 0);

                    } catch (Exception ex) {
                        Log.e(Constants.TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final JSONObject jsonObject) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushString(jsonObject.toString());
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);
                    } catch (Exception ex) {
                        Log.i(Constants.TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void dispatchEvent(final Boolean isError, final String message, final String type, final String provider, final JSONObject jsonObject) {
        CoronaEnvironment.getCoronaActivity().getRuntimeTaskDispatcher().send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime runtime) {
                LuaState L = runtime.getLuaState();

                CoronaLua.newEvent(L, EVENT_NAME);

                L.pushBoolean(isError);
                L.setField(-2, "isError");

                L.pushString(message);
                L.setField(-2, "message");

                L.pushString(type);
                L.setField(-2, "type");

                L.pushString(provider);
                L.setField(-2, "provider");

                L.pushString(jsonObject.toString());
                L.setField(-2, "data");

                try {
                    CoronaLua.dispatchEvent(L, fListener, 0);
                } catch (Exception ignored) {
                }
            }
        });
    }

    public static void dispatchEvent(final Boolean isError, final String message, final String type, final String provider) {
        CoronaEnvironment.getCoronaActivity().getRuntimeTaskDispatcher().send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime runtime) {
                LuaState L = runtime.getLuaState();

                CoronaLua.newEvent(L, EVENT_NAME);

                L.pushBoolean(isError);
                L.setField(-2, "isError");

                L.pushString(message);
                L.setField(-2, "message");

                L.pushString(type);
                L.setField(-2, "type");

                L.pushString(provider);
                L.setField(-2, "provider");

                try {
                    CoronaLua.dispatchEvent(L, fListener, 0);
                } catch (Exception ignored) {
                }
            }
        });
    }

    private static class init implements NamedJavaFunction {
        @Override
        public String getName() {
            return "init";
        }

        @Override
        public int invoke(LuaState L) {
            int listenerIndex = 1;

            if (CoronaLua.isListener(L, listenerIndex, EVENT_NAME)) {
                fListener = CoronaLua.newRef(L, listenerIndex);
            }

            CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
            AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(activity);
            config.overlayWith(new LazyInputStream(activity) {
                public InputStream get(Context context) {
                    try {
                        Log.i(Constants.TAG, "agconnect-services.json ");
                        return context.getAssets().open("agconnect-services.json");
                    } catch (IOException e) {
                        Log.i(Constants.TAG, "agconnect-services.json reading Exception " + e);
                        return null;
                    }
                }
            });

            fDispatcher = new CoronaRuntimeTaskDispatcher(L);

            return 0;
        }
    }

    public static class HmsInstanceId implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.HmsInstanceId;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getId:
                    return HmsInstanceIdWrapper.getId(L);
                case Constants.getAAID:
                    return HmsInstanceIdWrapper.getAAID(L);
                case Constants.getCreationTime:
                    return HmsInstanceIdWrapper.getCreationTime(L);
                case Constants.deleteAAID:
                    return HmsInstanceIdWrapper.deleteAAID(L);
                case Constants.getToken:
                    return HmsInstanceIdWrapper.getToken(L);
                case Constants.deleteToken:
                    return HmsInstanceIdWrapper.deleteToken(L);
                default:
                    return 0;
            }
        }
    }

    public static class HmsMessaging implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.HmsMessaging;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.isAutoInitEnabled:
                    return HmsMessagingWrapper.isAutoInitEnabled(L);
                case Constants.setAutoInitEnabled:
                    return HmsMessagingWrapper.setAutoInitEnabled(L);
                case Constants.subscribe:
                    return HmsMessagingWrapper.subscribe(L);
                case Constants.unsubscribe:
                    return HmsMessagingWrapper.unsubscribe(L);
                case Constants.send:
                    return HmsMessagingWrapper.send(L);
                case Constants.turnOnPush:
                    return HmsMessagingWrapper.turnOnPush(L);
                case Constants.turnOffPush:
                    return HmsMessagingWrapper.turnOffPush(L);
                default:
                    return 0;
            }
        }
    }

    public static class HmsProfile implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.HmsProfile;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.isSupportProfile:
                    return HmsProfileWrapper.isSupportProfile(L);
                case Constants.addProfile:
                    return HmsProfileWrapper.addProfile(L);
                case Constants.deleteProfile:
                    return HmsProfileWrapper.deleteProfile(L);
                case Constants.HUAWEI_PROFILE:
                    return HmsProfileWrapper.HUAWEI_PROFILE(L);
                case Constants.CUSTOM_PROFILE:
                    return HmsProfileWrapper.CUSTOM_PROFILE(L);
                default:
                    return 0;
            }
        }
    }

    public static class OpenDeviceClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.OpenDeviceClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            if (L.toString(1).equals(Constants.getOdid)) {
                return OpenDeviceClientWrapper.getOdid(L);
            } else {
                return 0;
            }
        }
    }

    @Override
    public void onLoaded(CoronaRuntime runtime) {
    }

    @Override
    public void onStarted(CoronaRuntime runtime) {
    }

    @Override
    public void onSuspended(CoronaRuntime runtime) {
    }

    @Override
    public void onResumed(CoronaRuntime runtime) {
    }

    @Override
    public void onExiting(CoronaRuntime runtime) {
        CoronaLua.deleteRef(runtime.getLuaState(), fListener);
        fListener = CoronaLua.REFNIL;
    }


}
