package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/04/22.
 */

abstract class Api_Details {
    private static final String Client_ID = "wUNxKExU8wm7IfN1bIV4qBSh2cpeivmY";
    private static final String Client_Secret = "J8uuMCMov0TjPnxf0Sx4bRuCvi0UDSAP";
    private static final String soundURL = "https://soundcloud.com/connect?" +
            "state=SoundCloud_Dialog_176b9" +
            "&client_id=wUNxKExU8wm7IfN1bIV4qBSh2cpeivmY" +
            "&redirect_uri=https%3A%2F%2Fwww.eternalvibes.me%2FSoundAuthen" +
            "&response_type=code_and_token" +
            "&scope=non-expiring" +
            "&display=popup";

    public static String getClient_ID() {
        return Client_ID;
    }

    public static String getClient_Secret() {
        return Client_Secret;
    }

    public static String getSoundURL() {
        return soundURL;
    }
}
