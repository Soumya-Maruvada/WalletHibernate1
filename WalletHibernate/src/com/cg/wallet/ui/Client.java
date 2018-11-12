package com.cg.wallet.ui;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.wallet.bean.Customer;
import com.cg.wallet.bean.Wallet;
import com.cg.wallet.bean.WalletTransaction;
import com.cg.wallet.service.IWalletService;
import com.cg.wallet.service.WalletServiceImpl;

public class Client {
	static Scanner scanner = new Scanner(System.in);
	IWalletService walletService = new WalletServiceImpl();
	Customer customerBean = new Customer();
	public static void main(String[] args) throws Exception {
		
		Client client = new Client();
		while (true) {
			System.out.println("========Payment wallet application========");
			System.out.println("1. Create Account ");
			System.out.println("2. Show Balance ");
			System.out.println("3. Deposit ");
			System.out.println("4. Withdraw ");
			System.out.println("5. Fund Transfer ");
			System.out.println("6. Print Transactions ");
			System.out.println("7. Exit ");
			System.out.println(" Choose an option ");
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				client.createAccount();
				break;
			case 2:
				client.showBalance();
				break;
			case 3:
				client.deposit();
				break;
			case 4:
				client.withdraw();
				break;
			case 5:
				client.fundTransfer();
				break;
			case 6:
				client.printTransaction();
				break;
			case 7:
				System.exit(0);
				break;
			default:
				System.err.println("Invalid Option Choose from 1 to 7");
				System.out.println();
				break;

			}
	}
	}
	void createAccount() throws Exception {
		
		
		Customer customer = new Customer();
		
		System.out.println("\t\t Enter First name\t\t: ");
		String firstName = scanner.next();
		customer.setFirstName(firstName);
		
		System.out.println("\t\tEnter Last name\t\t: ");
		String lastName = scanner.next();
		customer.setLastName(lastName);
		
		System.out.println("\t\tEnter mobile number\t\t: ");
		BigInteger mobile = scanner.nextBigInteger();
		customer.setMobile(mobile);
		
		System.out.println("\t\tEnter customer email\t\t: ");
		String email = scanner.next();
		customer.setEmail(email);
		
		System.out.println("\t\tSet your password\t\t");
		String password = scanner.next();
		customer.setPassWord(password);
		
		
		Wallet wallet = new Wallet();
		System.out.println("Enter  Wallet ID");
		int walletId=scanner.nextInt();
		wallet.setWalletId(walletId);
		
		System.out.println("Enter Date of Opening (DD/MM/YYYY)");
		String accDateInput=scanner.next();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		Date dateOfOpening=sdf.parse(accDateInput);
		wallet.setDateOfOpenning(dateOfOpening);
		System.out.println("Enter balance to create account");
		double balance=scanner.nextDouble();
		wallet.setBalance(balance);
		
		wallet.setCustomer(customer);
		
		
		if ( walletService.validate(customer)) {
			
			boolean create = walletService.createWallet(wallet);
			if(create)
			{
				System.out.println("\n\n\t\tCongratulations Customer account has been created successfully...");
			}
			else
			{
				System.out.println("Enter valid Details");
			}
			
		}
		
	}
	
	
	private void showBalance() throws Exception {
		System.out.println("Enter Wallet ID");
		int walletId=scanner.nextInt();
		
		Wallet wallet=walletService.showBalance(walletId);
		
		if(wallet==null){
			System.out.println("Account Does not exist");
			
		}else {
			double balance=wallet.getBalance();
			/*System.out.println("-----Balance Detail-----");
			String firstName = wallet.getCustomer().getFirstName();
			String lastName = wallet.getCustomer().getLastName();
			System.out.println(firstName + " " + lastName);*/
			System.out.println("\t\tCurrent Balance\t\t:" + balance);
		}
		
	}
	private void deposit() throws Exception {
		System.out.println("Enter Wallet ID");
		int walletId=scanner.nextInt();
		
		Wallet wallet=walletService.showBalance(walletId);
		
		if(wallet==null){
			System.out.println("Account Does not exist");
			return;
		}else {
			System.out.println("Enter Ammount to deposit.");
			double depositAmt = scanner.nextDouble();
			/*double balance=wallet.getBalance();
			System.out.println("Your balance is: " +balance);*/
			
			WalletTransaction walletTransaction=new WalletTransaction();
			walletTransaction.setTransactionType(1);
			walletTransaction.setTransactionDate(new Date());
			walletTransaction.setTransactionAmt(depositAmt);
			walletTransaction.setBeneficiaryAccountBean(null);
			
			wallet.addTransation(walletTransaction);
			boolean deposit = walletService.deposit(wallet, depositAmt);
			if(deposit){
				System.out.println("Deposited Money into Account ");
			}else{
				System.out.println("NOT Deposited Money into Account ");
			}
		}
		
	}
	private void withdraw() throws Exception {
		System.out.println("Enter Wallet ID");
		int walletId=scanner.nextInt();
		
		Wallet wallet=walletService.showBalance(walletId);
		
		if(wallet==null){
			System.out.println("Account Does not exist");
			return;
		}else {
			System.out.println("Enter Ammount to deposit.");
			double withdrawAmt = scanner.nextDouble();
			/*double balance=wallet.getBalance();
			System.out.println("Your balance is: " +balance);*/
			
			WalletTransaction walletTransaction=new WalletTransaction();
			walletTransaction.setTransactionType(2);
			walletTransaction.setTransactionDate(new Date());
			walletTransaction.setTransactionAmt(withdrawAmt);
			walletTransaction.setBeneficiaryAccountBean(null);
			
			wallet.addTransation(walletTransaction);
			boolean deposit = walletService.withdraw(wallet, withdrawAmt);
			if(deposit){
				System.out.println("Money Withdrawen from Account ");
			}else{
				System.out.println(" Withdrawal Failed ");
			}
		}
		
	}
	private void printTransaction() throws Exception {
		System.out.println("Enter Senders Wallet ID");
		int sorceWalletId=scanner.nextInt();
		Wallet walletSorce=walletService.showBalance(sorceWalletId);
		
		
		System.out.println("Enter Recivers Wallet ID");
		int targetWalletId=scanner.nextInt();
		Wallet walletTareget=walletService.showBalance(targetWalletId);
		
		
		if (walletSorce == null) {
			System.out.println("Senders wallet not found");
			return;
		}
		if(walletTareget == null){
			System.out.println("Receivers wallet not found");
			return;
		}else {
			System.out.println("Enter Ammount to transfer.");
			double transfer = scanner.nextDouble();
			/*double balance=wallet.getBalance();
			System.out.println("Your balance is: " +balance);*/
			
			WalletTransaction walletTransaction=new WalletTransaction();
			walletTransaction.setTransactionType(3);
			walletTransaction.setTransactionDate(new Date());
			walletTransaction.setTransactionAmt(transfer);
			walletTransaction.setBeneficiaryAccountBean(null);
			
			walletSorce.addTransation(walletTransaction);
			boolean transferResult = walletService.fundTransfer(walletSorce, walletTareget, transfer);
			if(transferResult){
				System.out.println("Money Transfered from Account ");
			}else{
				System.out.println(" Money Transfer Failed ");
			}
		}	
		
	}
	private void fundTransfer() throws Exception {
		System.out.println("Enter Wallet ID");
		int walletId=scanner.nextInt();
		Wallet wallet=walletService.showBalance(walletId);
		
		List<WalletTransaction> transactions = wallet.getAllTransactions();
		
		System.out.println(wallet);
		System.out.println(wallet.getCustomer());
		
		System.out.println("------------------------------------------------------------------");
		
		for(WalletTransaction walletTransaction:transactions){
			
			String str="";
			if(walletTransaction.getTransactionType()==1){
				str=str+"DEPOSIT";
			}
			if(walletTransaction.getTransactionType()==2){
				str=str+"WITHDRAW";
			}
			if(walletTransaction.getTransactionType()==3){
				str=str+"FUND TRANSFER";
			}
			
			str=str+"\t\t"+walletTransaction.getTransactionDate();
			
			str=str+"\t\t"+walletTransaction.getTransactionAmt();
			System.out.println(str);
		}
		
		System.out.println("------------------------------------------------------------------");

		
	}	
	
}
