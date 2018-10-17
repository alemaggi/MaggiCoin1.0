import java.util.*;

public class Block {
    public String hash; //will hold our digital signature
    public String previousHash; //hold the digital signature of the previous block
    private String data; //for the time being just a simple msg
    private long timeStamp; //as number of milliseconds since 1/1/1970
    private int nonce;
    
    //block constructor
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); //making sure we do this after we set the other values
    }

    //use applySha256 to calculate the has
    //must calculate the hash from all parts of the block we don't want to be tampered with
    public String calculateHash() {
        String calculateHash = StringUtil.applySha256(
            previousHash +
            Long.toString(timeStamp) +
            Integer.toString(nonce) +
            data
            );
        return calculateHash;
    }
    
    //We will require miners to do proof-of-work by trying different variable values
    //in the block until its hash starts with a certain number of 0’s
    public void mineBlock(int difficulty) {
    	String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
    }
}