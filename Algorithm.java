package cipher_app;

public class Algorithm 
{
    // Encrypts text using a user defined shift  
    public static StringBuffer encrypt(String text, int shift) 
    {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) 
        {
            if (Character.isUpperCase(text.charAt(i))) 
            {
                char ch = (char) (((int) text.charAt(i) + shift - 65) % 26 + 65);
                result.append(ch);
            } 
            else 
            {
                char ch = (char) (((int) text.charAt(i) + shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }
        
    // Decrypts cipher using a user defined shift
    public static StringBuffer decrypt(String cipher, int shift) 
    {
        StringBuffer result = new StringBuffer();
 
        for (int i = 0; i < cipher.length(); i++) 
        {
            if (Character.isUpperCase(cipher.charAt(i))) 
            {
                char ch = (char) (((int) cipher.charAt(i) + (-shift) - 65) % 26 + 65);
                result.append(ch);
            } 
            else 
            {
                char ch = (char) (((int) cipher.charAt(i) + (-shift) - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }
}