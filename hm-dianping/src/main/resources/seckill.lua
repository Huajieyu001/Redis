
local voucherId = KEYS[1];
local userId = ARGV[1];

local voucherKey = 'seckill:stock:' .. voucherId
local orderKey = 'seckill:order:' .. voucherId

if(tonumber(redis.call('get',voucherKey)) <= 0) then
    -- stock is lacked
    return 1;
end

if(redis.call('sismember',orderKey,userId) == 1) then
    -- repeated order
    return 2;
end

redis.call('incrby',voucherKey,-1)
redis.call('sadd',orderKey,userId)

return 0;