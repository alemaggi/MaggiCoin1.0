import java.security.*;
import java.security.spec.*;

public class Wallet {

	public PrivateKey privateKey; //is used to sign our transaction
	public PublicKey publicKey; //is our adress. We also send our public key along with the transaction 
								//and it can be used to verify that our signature is valid and data has not been tampered with.
	
	public Wallet() {
		generateKeyPair();
	}
	
	// this method is it uses Java.security.KeyPairGenerator to generate an Elliptic Curve KeyPair. 
	//This methods makes and sets our Public and Private keys
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	// Set the public and private keys from the keyPair
	        	privateKey = keyPair.getPrivate();
	        	publicKey = keyPair.getPublic();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
