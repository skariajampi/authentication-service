package com.skaria.aws.security.util.cognito;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Util {

    public static final String HMAC_SHA_256 = "HmacSHA256";

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {

        final String HMAC_SHA256_ALGORITHM = HMAC_SHA_256;

        logger.debug("Calculating secret hash...");
        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            logger.error("Exception while Calculating secret hash...{}", e.getMessage());
            throw new RuntimeException("Error while calculating ");
        }
    }
}
