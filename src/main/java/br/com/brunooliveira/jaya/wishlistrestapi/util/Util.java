package br.com.brunooliveira.jaya.wishlistrestapi.util;

import java.util.UUID;

public class Util {


    public static String generateRequestId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
