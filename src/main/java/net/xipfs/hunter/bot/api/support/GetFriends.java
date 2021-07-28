package net.xipfs.hunter.bot.api.support;

import net.xipfs.hunter.bot.api.BaseApi;

/**
 * @author xie hui
 */
public class GetFriends extends BaseApi {
    public GetFriends() {
    }
    @Override
    public String getAction() {
        return "get_friend_list";
    }
    @Override
    public Object getParams() {
        return "";
    }
    @Override
    public boolean needSleep() {
        return false;
    }
}
