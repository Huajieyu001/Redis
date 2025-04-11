local key=KEYS[1];
local threadId=KEYS[2];
local releaseTime=ARGV[1];



if(redis.call('exists',key) == 0) then
    redis.call('hset',key,threadId,'1');
    redis.call('expire',releaseTime);
end;

if(redis.call('hexists',key,threadId) == 1) then
    redis.call('hincrby',key,thread,'1');
    redis.call('expire',releaseTime);
    return 1;
end;

return 0;