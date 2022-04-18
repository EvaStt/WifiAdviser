package com.example.mydisapp;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * A structure to hold service information.
 */
public class WifiServiceInfo implements Serializable {
    String deviceName;
    String deviceAddress;
    String instanceName = null;
    String serviceRegistrationType = null;
    String msg = null;
    String username = null;
    Boolean wasMatched = false;
    InetAddress groupOwnerAddress = null;

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWasMatched(Boolean wasMatched) {
        this.wasMatched = wasMatched;
    }

    public Boolean msgContains(String wishMsg) {
        if (msg != null) {
            return msg.contains(wishMsg);
        }
        return false;
    }

    public void setGroupOwnerAddress(InetAddress address) {
        this.groupOwnerAddress = address;
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getUsername() {
                return this.username;
    }

    public Boolean getWasMatched() {
        return this.wasMatched;
    }

    public InetAddress getGroupOwnerAddress() {
        return this.groupOwnerAddress;
    }
}
