package com.chee.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

public class EncodeUtils {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String getEncoding(String str) {
        String encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception localException) {
            encode = "ISO-8859-1";
            try {
                if (str.equals(new String(str.getBytes(encode), encode))) {
                    return encode;
                }
            } catch (Exception localException1) {
                encode = "GB2312";
                try {
                    if (str.equals(new String(str.getBytes(encode), encode))) {
                        return encode;
                    }
                } catch (Exception localException2) {
                    encode = "GBK";
                    try {
                        if (str.equals(new String(str.getBytes(encode), encode))) {
                            return encode;
                        }
                    } catch (Exception localException3) {
                    }
                }
            }
        }
        return "";
    }

    public static String setEncode(String str, String encode) {
        try {
            String strEncode = getEncoding(str);
            return new String(str.getBytes(strEncode), encode);
        } catch (IOException localIOException) {
        }
        return null;
    }

    public static String ISO2GB(String text) {
        String result = "";
        try {
            result = new String(text.getBytes("ISO-8859-1"), "GB2312");
        } catch (UnsupportedEncodingException ex) {
            result = ex.toString();
        }
        return result;
    }

    public static String GB2ISO(String text) {
        String result = "";
        try {
            result = new String(text.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String Utf8URLencode(String text) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if ((c >= 0) && (c <= 'ÿ')) {
                result.append(c);
            } else {
                byte[] b = new byte[0];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (Exception localException) {
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return result.toString();
    }

    public static String Utf8URLdecode(String text) {
        String result = "";
        int p = 0;
        if ((text != null) && (text.length() > 0)) {
            text = text.toLowerCase();
            p = text.indexOf("%e");
            if (p == -1) {
                return text;
            }
            while (p != -1) {
                result = result + text.substring(0, p);
                text = text.substring(p, text.length());
                if ((text == "") || (text.length() < 9)) {
                    return result;
                }
                result = result + CodeToWord(text.substring(0, 9));
                text = text.substring(9, text.length());
                p = text.indexOf("%e");
            }
        }
        return result + text;
    }

    public static String CodeToWord(String text) {
        String result;
        if (Utf8codeCheck(text)) {
            byte[] code = new byte[3];
            code[0] = ((byte) (Integer.parseInt(text.substring(1, 3), 16) - 256));
            code[1] = ((byte) (Integer.parseInt(text.substring(4, 6), 16) - 256));
            code[2] = ((byte) (Integer.parseInt(text.substring(7, 9), 16) - 256));
            try {
                result = new String(code, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = null;
            }
        } else {
            result = text;
        }
        return result;
    }

    public static boolean Utf8codeCheck(String text) {
        String sign = "";
        if (text.startsWith("%e")) {
            int i = 0;
            for (int p = 0; p != -1; i++) {
                p = text.indexOf("%", p);
                if (p != -1) {
                    p++;
                }
                sign = sign + p;
            }
        }
        return sign.equals("147-1");
    }

    public static boolean isUtf8Url(String text) {
        text = text.toLowerCase();
        int p = text.indexOf("%");
        if ((p != -1) && (text.length() - p > 9)) {
            text = text.substring(p, p + 9);
        }
        return Utf8codeCheck(text);
    }

    public static String salt() {
        int random = (int) (10.0D + Math.random() * 10.0D);
        return UUID.randomUUID().toString().replace("-", "").substring(random);
    }

    public static void main(String[] args) {
        System.out.println(Utf8URLencode("http:10.9.6.7820130101005143_22826_0_桂138.jpg"));
    }
}
