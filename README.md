# Huawei Push Kit Solar2d Plugin

This plugin was created based on Huawei Push Kit. Please [check](https://developer.huawei.com/consumer/en/hms/huawei-pushkit/) for detailed information about Huawei Push Kit. 

In order to use the Huawei Push Kit, you must first create an account from developer.huawei.com. And after logging in with your account, and then you must create a project in the huawei console in order to use HMS kits.

## Project Setup

To use the plugin please add following to `build.settings`

```lua
{
    plugins = {
        ["plugin.huaweiPushKit"] = {
            publisherId = "com.solar2d",
        },
    },
}
```

After you need to define the plugin in main.lua.

```lua
local pushKit = require "plugin.huaweiPushKit"

local function listener( event )
    print( event.message )
end
pushKit.init( listener )
```

## HmsInstanceId
Provides methods for obtaining the AAID and tokens required for accessing Push Kit.

### getId
Obtains an AAID in synchronous mode.
```lua
	pushKit.HmsInstanceId("getId")

    --Result(String)
```

### getAAID
Obtains an AAID in asynchronous mode.
```lua
	pushKit.HmsInstanceId("getAAID", function(event) 
        print(event.message)
    end)

    --Result(String)
```

### getCreationTime
Obtains the generation timestamp of an AAID.
```lua
	pushKit.HmsInstanceId("getCreationTime")

    --Result(Integer)
```

### deleteAAID
Deletes a local AAID and its generation timestamp.
```lua
	pushKit.HmsInstanceId("deleteAAID")

    --Result(Void)
```

### getToken
Obtains a token required for accessing Push Kit.
```lua
	pushKit.HmsInstanceId("getToken")

    -- Result(String) 
    local function listener( event )
        if event.type == "getToken" then
            if not event.isError then 
                token = event.message
            end
        end
    end
```


### deleteToken
Deletes a token.
```lua
	pushKit.HmsInstanceId("deleteToken")

    --Result(Void)
```

### deleteToken(String subjectId)
Deletes a token.
```lua
	pushKit.HmsInstanceId("deleteToken", {subjectId=""})

    --Result(Void)
```

## OpenDeviceClient
Provides the method for obtaining an open device identifier (ODID).

### getOdid() 
Obtains an AAID in asynchronous mode.
```lua
	pushKit.OpenDeviceClient("getOdid", function(event) 
        print(event.isError, event.message)
    end)

    --Result(String)
```

## HmsMessaging
Provides methods for subscribing to topics and for enabling or disabling the function of receiving notification messages.

### isAutoInitEnabled
Checks whether automatic initialization is enabled.
```lua
	pushKit.HmsMessaging("isAutoInitEnabled")

    --Result(boolean)
```

### setAutoInitEnabled
Sets whether to enable automatic initialization.
```lua
	pushKit.HmsMessaging("setAutoInitEnabled", {enable=false})

    --Result(Void)
```

### subscribe
Subscribes to topics in asynchronous mode.
```lua
	pushKit.HmsMessaging("subscribe", {topic = ""}, function(event) 
        print(event.isError)
    end)

    --Result(Void)
```

### unsubscribe
Subscribes to topics in asynchronous mode.
```lua
	pushKit.HmsMessaging("unsubscribe", {topic = ""}, function(event) 
        print(event.isError)
    end)

    --Result(Void)
```

### turnOnPush
Enables the display of notification messages.
```lua
	pushKit.HmsMessaging("turnOnPush", function(event) 
        print(event.isError)
    end)

    --Result(Void)
```

### turnOffPush
Disables the display of notification messages.
```lua
	pushKit.HmsMessaging("turnOffPush", function(event) 
        print(event.isError)
    end)

    --Result(Void)
```

## HmsProfile
A class for checking whether to display messages for the user based on the account.

### isSupportProfile
Checks whether the device supports account verification.
```lua
	pushKit.HmsProfile("isSupportProfile")

    --Result(boolean)
```

### addProfile
Adds the relationship between the user and app on the device.
```lua
	pushKit.HmsProfile("addProfile", {profileId="", type=1}, function(event)
        print(event.isError)
    end)
    
	pushKit.HmsProfile("addProfile", {profileId="", type=1, subjectId=""}, function(event)
        print(event.isError)
    end)

    --Result(Void)
```


### deleteProfile
Deletes the relationship between the user and app on the device.
```lua
	pushKit.HmsProfile("deleteProfile", {profileId=""}, function(event) 
        print(event.isError)
    end)
    
	pushKit.HmsProfile("deleteProfile", {subjectId="", profileId=""}, function(event) 
        print(event.isError)
    end)
    --Result(Void)
```

```lua
	pushKit.HmsProfile("HUAWEI_PROFILE") 
	-- Result (1)
    
	pushKit.HmsProfile("CUSTOM_PROFILE")
	-- Result (2)
```

## References
HMS Push Kit  [here](https://developer.huawei.com/consumer/en/hms/huawei-pushkit/) 
HMS Push Kit Server Side Development [here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-server-dev-0000001050040110)
HMS Push Kit FAQ [here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/faq-0000001050042183)
HMS Push Kit Result Codes [here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/commonerror-0000001059816656)

## License
MIT
