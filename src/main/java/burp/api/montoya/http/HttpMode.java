/*
 * Copyright (c) 2022. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.http;

/**
 * This enum defines HTTP modes.
 */
public enum HttpMode
{
    AUTO,
    HTTP_1,
    HTTP_2,
    HTTP_2_IGNORE_ALPN
}
