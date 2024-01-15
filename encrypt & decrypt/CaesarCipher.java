import java.util.Scanner;

public class CaesarCipher {

	public static void main(String[] args) {

		Scanner num = new Scanner(System.in);
        System.out.println("Do you want to\n 1. Encrypt the file?\n 2. decrypt the file?");
        int choice = num.nextInt();
        
        Scanner sc = new Scanner(System.in);
        
		if(choice==1) {
		System.out.print("Enter a message to encrypt : ");
		String msg = sc.nextLine();
		System.out.print("Enter the key value for the message : ");
        int key = sc.nextInt();
		String encryptedMessage = encrypt(msg, key);
		System.out.println("Encrypted message : " + encryptedMessage);
		}

		else if(choice==2) {
   		System.out.print("Enter a message to decrypt : ");
		String msg = sc.nextLine();
		System.out.print("Enter the key value for the message : ");
        int key = sc.nextInt();
        String decryptedMessage = decrypt(msg, key);
        System.out.println("Decrypted message : " + decryptedMessage);
		}
		
		else {
			System.out.println("Please enter a valid number.");
		}
	}
				
	public static String encrypt(String msg, int key) {
		
		StringBuilder encryptedMessage = new StringBuilder();
		msg = msg.toLowerCase();
		for(int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			if(Character.isLetter(c)) {
				c = (char)((c - 'a' + key + 26) % 26 + 'a');
			}
			encryptedMessage.append(c);
		}
		return encryptedMessage.toString();
	}

    public static String decrypt(String msg, int key) {
		
		StringBuilder decryptedMessage = new StringBuilder();
		msg = msg.toLowerCase();
		for(int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			if(Character.isLetter(c)) {
				c = (char)((c - 'a' - key + 26) % 26 + 'a');
			}
			decryptedMessage.append(c);
		}
		return decryptedMessage.toString();
	}

}