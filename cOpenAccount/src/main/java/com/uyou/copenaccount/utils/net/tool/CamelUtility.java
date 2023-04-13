package com.uyou.copenaccount.utils.net.tool;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by zdd on 2018/11/28.
 */

public class CamelUtility {

    public static final int SizeOfUUID = 16;
    private static final char[] HEX_CHAR_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] FULL_CHAR_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final BigInteger FULL_CHAR_BASE;
    private static final int[] MASK_ARRAY;

    static {
        FULL_CHAR_BASE = BigInteger.valueOf((long) FULL_CHAR_TABLE.length);
        MASK_ARRAY = new int[]{1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, '\uffff', 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, 2147483647};
    }

    public CamelUtility() {
    }

    public static int setMask(int origValue, int setValue, int startPos, int endPos) {
        int w = endPos - startPos + 1;
        return ~(MASK_ARRAY[w] << startPos) & origValue | setValue & MASK_ARRAY[w] << startPos;
    }

    public static byte[] uuidToByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        int i;
        for (i = 0; i < 8; ++i) {
            buffer[i] = (byte) ((int) (msb >>> 8 * (7 - i)));
        }

        for (i = 8; i < 16; ++i) {
            buffer[i] = (byte) ((int) (lsb >>> 8 * (7 - i)));
        }

        return buffer;
    }

    public static UUID byteArrayToUUID(byte[] byteArray) {
        return byteArrayToUUID(byteArray, 0);
    }

    public static UUID byteArrayToUUID(byte[] byteArray, int offset) {
        long msb = 0L;
        long lsb = 0L;

        int i;
        for (i = offset; i < 8 + offset; ++i) {
            msb = msb << 8 | (long) (byteArray[i] & 255);
        }

        for (i = 8 + offset; i < 16 + offset; ++i) {
            lsb = lsb << 8 | (long) (byteArray[i] & 255);
        }

        return new UUID(msb, lsb);
    }

    public static void formatAsHex(byte b, StringBuilder s) {
        s.append(HEX_CHAR_TABLE[b >>> 4 & 15]);
        s.append(HEX_CHAR_TABLE[b & 15]);
    }

    public static String hexToString(byte[] hex) {
        if (hex != null && hex.length != 0) {
            StringBuilder sb = new StringBuilder(hex.length * 2);
            byte[] var5 = hex;
            int var4 = hex.length;

            for (int var3 = 0; var3 < var4; ++var3) {
                byte b = var5[var3];
                formatAsHex(b, sb);
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String uuidToString(UUID uuid) {
        long[] bb = new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
        StringBuilder sb = new StringBuilder(32);
        long[] var7 = bb;
        int var6 = bb.length;

        for (int var5 = 0; var5 < var6; ++var5) {
            long l = var7[var5];

            for (int i = 7; i >= 0; --i) {
                formatAsHex((byte) ((int) (l >>> i * 8 & 255L)), sb);
            }
        }

        return sb.toString();
    }

    public static UUID stringToUUID(String str) {
        byte[] bytes = hexToByte(str);
        return byteArrayToUUID(bytes);
    }

    public static byte[] hexToByte(String hex) {
        if (hex != null && hex.length() != 0 && hex.length() % 2 == 0) {
            byte[] bytes = new byte[hex.length() / 2];
            int x = 0;

            for (int i = 0; i < hex.length(); ++i) {
                char c = hex.charAt(i);
                int b;
                if (c >= 48 && c <= 57) {
                    b = c - 48;
                } else if (c >= 97 && c <= 102) {
                    b = c - 97 + 10;
                } else if (c >= 65 && c <= 70) {
                    b = c - 65 + 10;
                } else {
                    b = 0;
                }

                switch (i & 1) {
                    case 0:
                        x = b;
                        break;
                    case 1:
                        bytes[i / 2] = (byte) (x << 4 | b);
                }
            }

            return bytes;
        } else {
            return new byte[0];
        }
    }

    public static String compactUUID2Str(byte[] uuid) {
        if (uuid != null && uuid.length >= 1) {
            if (uuid[0] < 0) {
                ByteBuffer uu = ByteBuffer.wrap(new byte[uuid.length + 1]);
                byte c = 0;
                uu.put(c);
                uu.put(uuid);
                uuid = uu.array();
            }

            StringBuilder str = new StringBuilder();
            BigInteger[] dr = new BigInteger[]{new BigInteger(uuid), BigInteger.ZERO};

            do {
                dr = dr[0].divideAndRemainder(FULL_CHAR_BASE);
                str.append(FULL_CHAR_TABLE[dr[1].intValue()]);
            } while (!BigInteger.ZERO.equals(dr[0]));

            return str.toString();
        } else {
            return "";
        }
    }

    public static byte[] compactStr2UUID(String str) {
        BigInteger uuid = BigInteger.ZERO;

        for (int i = str.length() - 1; i >= 0; --i) {
            uuid = uuid.multiply(FULL_CHAR_BASE);
            char c = str.charAt(i);
            if (c <= 57) {
                uuid = uuid.add(BigInteger.valueOf((long) (c - 48)));
            } else if (c <= 122) {
                uuid = uuid.add(BigInteger.valueOf((long) (c - 97 + 10)));
            } else {
                uuid = uuid.add(BigInteger.valueOf((long) (c - 65 + 36)));
            }
        }

        byte[] bin = uuid.toByteArray();
        if (bin.length == 16) {
            return bin;
        } else {
            byte[] fix = new byte[16];
            int i = bin.length - 1;

            for (int j = fix.length - 1; i >= 0 && j >= 0; --j) {
                fix[j] = bin[i];
                --i;
            }

            return fix;
        }
    }

    public static char getChineseCapital(char ch) throws UnsupportedEncodingException {
        if (ch >= 97 && ch <= 122) {
            return (char) (ch - 97 + 65);
        } else if (ch >= 65 && ch <= 90) {
            return ch;
        } else {
            String str = "" + ch;
            byte[] chBytes = str.getBytes("GBK");
            if (chBytes.length < 2) {
                return '#';
            } else {
                int tmp = (chBytes[0] << 8 & '\uff00') + (chBytes[1] & 255);
                return (char) (tmp >= '낡' && tmp <= '냄' ? 'A' : (tmp >= '냅' && tmp <= '닀' ? 'B' : (tmp >= '닁' && tmp <= '듭' ? 'C' : (tmp >= '듮' && tmp <= '뛩' ? 'D' : (tmp >= '뛪' && tmp <= '랡' ? 'E' : (tmp >= '랢' && tmp <= '룀' ? 'F' : (tmp >= '룁' && tmp <= '맽' ? 'G' : (tmp >= '맾' && tmp <= '믶' ? 'H' : (tmp >= '믷' && tmp <= '뾥' ? 'J' : (tmp >= '뾦' && tmp <= '삫' ? 'K' : (tmp >= '사' && tmp <= '싧' ? 'L' : (tmp >= '싨' && tmp <= '쓂' ? 'M' : (tmp >= '쓃' && tmp <= '억' ? 'N' : (tmp >= '얶' && tmp <= '얽' ? 'O' : (tmp >= '얾' && tmp <= '웙' ? 'P' : (tmp >= '웚' && tmp <= '좺' ? 'Q' : (tmp >= '좻' && tmp <= '죵' ? 'R' : (tmp >= '죶' && tmp <= '쯹' ? 'S' : (tmp >= '쯺' && tmp <= '췙' ? 'T' : (tmp >= '췚' && tmp <= '컳' ? 'W' : (tmp >= '컴' && tmp <= '톈' ? 'X' : (tmp >= '톹' && tmp <= '퓐' ? 'Y' : (tmp >= '퓑' && tmp <= '\uf351' ? 'Z' : '#')))))))))))))))))))))));
            }
        }
    }

    public static String stringXOR(String str, String key) {
        char[] StrChars = str.toCharArray();
        char[] KeyChars = key.toCharArray();
        int j = 0;

        for (int i = 0; i < str.length(); ++i) {
            StrChars[i] ^= KeyChars[j++ % key.length()];
        }

        return new String(StrChars);
    }

    public static String stringXOREncode(String src, String key) throws UnsupportedEncodingException {
        byte[] result = xor(src.getBytes("utf-8"), key.getBytes("utf-8"));
        return AES.parseByte2HexStr(result);
    }

    public static String stringXORDecode(String str, String key) throws UnsupportedEncodingException {
        byte[] src = AES.parseHexStr2Byte(str);
        byte[] result = xor(src, key.getBytes("utf-8"));
        return new String(result);
    }

    public static byte[] xor(byte[] src, byte[] key) {
        byte[] result = new byte[src.length];
        int j = 0;

        for (int i = 0; i < src.length; ++i) {
            result[i] = (byte) (src[i] ^ key[j++ % key.length]);
        }

        return result;
    }
}
