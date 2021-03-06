import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import java.util.Base64;
import java.security.Security;

public class MaggiChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>(); 

	//the method will loop through all blocks in the chain and compare the hashes. 
	//This method will need to check the hash variable is actually equal to the calculated hash, 
	//and the previous block’s hash is equal to the previousHash variable
	
	public static int difficulty = 5; //quella dei litecoin è nell' ordine dei milioni
	public static Wallet walletA;
	public static Wallet walletB;

	public static Boolean isChainValid() {
		
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through the blockchain to check hashes
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			//compare registered hash and calculate hash
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Current hashes not equal");
				return false;
			}
			//check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget) ) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}

		//added to test the wallet and the transaction
		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());

		return true;
	}
	
	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		
		blockchain.add(new Block("Maggi Alessandro - 04/09/1997 - MGGLSN97P04D969K", "0"));
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);
		
		blockchain.add(new Block("Maggi Roberto - 06/04/1954 - MGGRB54P875F",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new Block("Querci Cristina - 11/06/1954 - QRCCRST54F665G",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);	
		
		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}
}