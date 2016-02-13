package com.example.wlg.ratewer.Core;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jean on 13.02.2016.
 */
public class HashPassword
{
    public static String hashString(String string)
    {
        return sha256(string);
    }


    /**
     * simple java hash-function, replaced by sha256
     * not used anymore
     * @param string String to be hashed
     * @return hashed String
     */
    private static String simpleHash(String string)
    {
        //String passwordEncrypted = sha512(string);
        String passwordEncrypted = Integer.toString(string.hashCode());
        // should not happen because hashCode() should be 32 bit
        if(passwordEncrypted.length()>44)
        {
            passwordEncrypted = passwordEncrypted.substring(0,31);
        }
        System.out.println("Passwort: "+string);
        System.out.println("Passwort verschluesselt: "+passwordEncrypted);
        return passwordEncrypted;
    }



    private static String sha256 (String _TextString)
    {
        String stringHashed = "jsdnjfvklnsldfmvklslkvlks";
        try
        {
            MessageDigest MyMessageDigest = MessageDigest.getInstance("SHA-256");
            MyMessageDigest.update(_TextString.getBytes());

            byte ByteData[] = MyMessageDigest.digest();

            //convert the byte to hex format method 1
            StringBuffer MyStringBuffer = new StringBuffer();
            for (int i = 0; i < ByteData.length; i++)
            {
                MyStringBuffer.append(Integer.toString((ByteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            //convert the byte to hex format method 2
            StringBuffer HexString = new StringBuffer();
            for (int i=0;i<ByteData.length;i++)
            {
                String hex=Integer.toHexString(0xff & ByteData[i]);
                if(hex.length()==1) HexString.append('0');
                HexString.append(hex);
            }
            stringHashed = HexString.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return stringHashed;
    }

}
