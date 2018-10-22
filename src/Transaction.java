/*Each transaction will carry a certain amount of data:
- The public key(address) of the sender of funds.
- The public key(address) of the receiver of funds.
- The value/amount of funds to be transferred.
- Inputs, which are references to previous transactions that prove the sender has funds to send.
- Outputs, which shows the amount relevant addresses received in the transaction. ( These outputs are referenced as inputs in new transactions )
- A cryptographic signature, that proves the owner of the address is the one sending this transaction and that the data hasn’t been changed
*/

import java.security.*;
import java.util.ArrayList;

public class Transaction {
	
	public String transactionId; //this is also the hash of the transaction
	public PublicKey sender; //sender adress/public key
	public PublicKey reciepient; //Recipients address/public key
	public float value; 
	public byte[] signature; //this is to prevent anyone from spending funds in out wallet
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0; //a count of how many transaction have been generated
	
	//constructor
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	//this method calculate the transaction hash which will also be his ID
	private String calculateHash() {
		sequence++; //increse the sequence avoiding to have two transaction with the same ID
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(reciepient) +
				Float.toString(value) + sequence
				);		
	}

	//Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		signature = StringUtil.applyECDSASig(privateKey,data);		
	}
	//Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}
	//Signature will be verified by miners as new transaction are added to a block
	
}
