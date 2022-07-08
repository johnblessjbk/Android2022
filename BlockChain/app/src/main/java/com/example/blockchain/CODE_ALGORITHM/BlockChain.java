package com.example.blockchain.CODE_ALGORITHM;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Scanner;

public class BlockChain {

    public static ArrayList<HashData> blockchain = new ArrayList<HashData>();
    public static int difficulty = 5;

    public static void main(String[] args){
        //add our blocks to the blockchain ArrayList:
        System.out.println("Request to transactions ");
        Scanner sc=new Scanner(System.in);
        String fb=sc.nextLine();
        blockchain.add(new HashData("jenisha", fb));
        System.out.println(" block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new HashData("varun",blockchain.get(blockchain.size()-1).hash));
        System.out.println("block 2... ");
        blockchain.get(1).mineBlock(difficulty);
        //blockchain.add(new Block(" third block",tb));
        blockchain.add(new HashData("kavin",blockchain.get(blockchain.size()-1).hash));
        System.out.println(" Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }
    public static Boolean isChainValid() {
        HashData currentBlock;
        HashData previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0','0');
        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:

            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current Hashes not equal");
                return false;
            }

            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous Hashes not equal");
                return false;
            }

            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)){
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
