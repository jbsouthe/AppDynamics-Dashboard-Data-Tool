package com.cisco.josouthe.auth;

public class AccessToken {
    public String access_token = null;
    public int expires_in = 0;
    public transient long expires_at = 0;
    public boolean isExpired() {
        return expires_at < System.currentTimeMillis();
    }
}
