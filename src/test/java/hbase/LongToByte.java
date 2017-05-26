package hbase;

import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigInteger;

/**
 * Created by ES-BF-IT-126 on 2017/5/17.
 */
public class LongToByte {
    public static void main(String[] args) {
       // String h = "/x00/x00/x00/x00/x00/x005h";
        //BigInteger srch = new BigInteger(h, 16);
        //Integer.parseInt("x16", 16);
        //printHexString(Bytes.fromHex("20"));
        System.out.println( Bytes.bytesToVint(hexStringToBytes("\\x00\\x00\\x00\\x00\\x00\\x02\\xC6}")));

    }

    public static void printHexString( byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() );
        }

    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
