local Library = require "CoronaLibrary"

local lib = Library:new{ name='plugin.huaweiPushKit', publisherId='com.solar2d' }

local placeholder = function()
	print( "WARNING: The '" .. lib.name .. "' library is not available on this platform." )
end


lib.init = placeholder
lib.HmsInstanceId = placeholder
lib.HmsMessaging = placeholder
lib.HmsProfile = placeholder
lib.OpenDeviceClient = placeholder

-- Return an instance
return lib