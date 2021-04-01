local pushKit = require "plugin.huaweiPushKit"
local widget = require( "widget" )
local json = require("json")

local token = "";
TAG = "PushKit"

local function listener( event )
    print( TAG, event.message )
    if event.type == "getToken" then
        if not event.isError then 
            token = event.message
            print(TAG, token)  
        end      
    end
end

pushKit.init( listener )

-- //////////////////////////////////////// HmsInstanceId ////////////////////////////////////////
local header = display.newText( "Huawei Push Kit - HmsInstanceId", display.contentCenterX, 80, native.systemFont, 10 )
header:setFillColor( 255, 255, 255 )

local getId = widget.newButton(
    {
        left = 55,
        top = 135,
        id = "getId",
        label = "getId",
        onPress = function(event)
            print(TAG, pushKit.HmsInstanceId("getId"))        
        end,
        width = 210,
        height = 30
    }
)

local getAAID = widget.newButton(
    {
        left = 55,
        top = 170,
        id = "getAAID",
        label = "getAAID",
        onPress = function(event)
            pushKit.HmsInstanceId("getAAID", function(event) 
                print(TAG, event.message)
            end)
        end,
        width = 210,
        height = 30
    }
)

local getCreationTime = widget.newButton(
    {
        left = 55,
        top = 205,
        id = "getCreationTime",
        label = "getCreationTime",
        onPress = function(event)
            print(TAG, pushKit.HmsInstanceId("getCreationTime"))        
        end,
        width = 210,
        height = 30
    }
)

local deleteAAID = widget.newButton(
    {
        left = 55,
        top = 240,
        id = "deleteAAID",
        label = "deleteAAID",
        onPress = function(event)
            pushKit.HmsInstanceId("deleteAAID")
        end,
        width = 210,
        height = 30
    }
)

local getToken = widget.newButton(
    {
        left = 55,
        top = 275,
        id = "getToken",
        label = "getToken",
        onPress = function(event)
            pushKit.HmsInstanceId("getToken")
        end,
        width = 210,
        height = 30
    }
)

local deleteToken = widget.newButton(
    {
        left = 55,
        top = 310,
        id = "deleteToken",
        label = "deleteToken",
        onPress = function(event)
            pushKit.HmsInstanceId("deleteToken")
        end,
        width = 210,
        height = 30
    }
)

-- //////////////////////////////////////// HmsMessaging ////////////////////////////////////////
-- local header = display.newText( "Huawei Push Kit - HmsMessaging", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )

-- local isAutoInitEnabled = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "isAutoInitEnabled",
--         label = "isAutoInitEnabled",
--         onPress = function(event)
--             print(TAG, pushKit.HmsMessaging("isAutoInitEnabled"))        
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local setAutoInitEnabled = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "setAutoInitEnabled",
--         label = "setAutoInitEnabled",
--         onPress = function(event)
--             pushKit.HmsMessaging("setAutoInitEnabled", {enable=false})
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local subscribe = widget.newButton(
--     {
--         left = 55,
--         top = 205,
--         id = "subscribe",
--         label = "subscribe",
--         onPress = function(event)
--             pushKit.HmsMessaging("subscribe", {topic = ""}, function(event) 
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local unsubscribe = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "unsubscribe",
--         label = "unsubscribe",
--         onPress = function(event)
--             pushKit.HmsMessaging("unsubscribe", {topic = ""}, function(event) 
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local turnOnPush = widget.newButton(
--     {
--         left = 55,
--         top = 275,
--         id = "turnOnPush",
--         label = "turnOnPush",
--         onPress = function(event)
--             pushKit.HmsMessaging("turnOnPush", function(event) 
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local turnOffPush = widget.newButton(
--     {
--         left = 55,
--         top = 310,
--         id = "turnOffPush",
--         label = "turnOffPush",
--         onPress = function(event)
--             pushKit.HmsMessaging("turnOffPush", function(event) 
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// HmsProfile ////////////////////////////////////////
-- local header = display.newText( "Huawei Push Kit - HmsProfile", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )

-- local isSupportProfile = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "isSupportProfile",
--         label = "isSupportProfile",
--         onPress = function(event)
--             print(TAG, pushKit.HmsProfile("isSupportProfile"))        
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local addProfile = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "addProfile",
--         label = "addProfile",
--         onPress = function(event)
--             pushKit.HmsProfile("addProfile", {profileId="", type=1}, function(event)
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local deleteProfile = widget.newButton(
--     {
--         left = 55,
--         top = 205,
--         id = "deleteProfile",
--         label = "deleteProfile",
--         onPress = function(event)
--             pushKit.HmsProfile("deleteProfile", {profileId=""}, function(event) 
--                 print(TAG, event.isError)
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local HUAWEI_PROFILE = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "HUAWEI_PROFILE",
--         label = "HUAWEI_PROFILE",
--         onPress = function(event)
--             print(TAG, pushKit.HmsProfile("HUAWEI_PROFILE"))
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- local CUSTOM_PROFILE = widget.newButton(
--     {
--         left = 55,
--         top = 275,
--         id = "CUSTOM_PROFILE",
--         label = "CUSTOM_PROFILE",
--         onPress = function(event)
--             print(TAG, pushKit.HmsProfile("CUSTOM_PROFILE"))
--         end,
--         width = 210,
--         height = 30
--     }
-- )

//////////////////////////////////////// OpenDeviceClient ////////////////////////////////////////
local header = display.newText( "Huawei Push Kit - OpenDeviceClient", display.contentCenterX, 80, native.systemFont, 10 )
header:setFillColor( 255, 255, 255 )

local getOdid = widget.newButton(
    {
        left = 55,
        top = 135,
        id = "getOdid",
        label = "getOdid",
        onPress = function(event)
            pushKit.OpenDeviceClient("getOdid", function(event) 
                print(TAG, event.isError, event.message)
            end)
        end,
        width = 210,
        height = 30
    }
)