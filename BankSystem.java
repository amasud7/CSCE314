/*
 * Ayad Masud 4/10/25 section: 500
 * 
 * For full credit, comment any completed code to show your understanding.
 * For full credit, comment any code you complete to show your understanding.
 */

 import java.io.*;
 import java.util.*;
 import java.util.concurrent.locks.Lock;
 import java.util.concurrent.locks.ReentrantLock;
 
// Exception for insufficient funds
// This exception is thrown when a withdrawal is attempted with insufficient funds.
class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds available for this transaction");
    }
    
    public InsufficientFundsException(String message) {
        super(message);
    }
}
 
 // BankAccount Interface
 // This interface defines the basic operations for a bank account.
 // It includes methods for depositing, withdrawing, and checking the balance.
 interface BankAccount {
     void deposit(double amount);
     void withdraw(double amount) throws InsufficientFundsException;
     double getBalance();
     int getAccountNumber();
 }
 
 // Abstract Account Class
// This class implements the BankAccount interface and provides a base for specific account types.
// It includes a constructor for initializing the account number and balance.
// It also includes methods for depositing and withdrawing money, ensuring thread safety.
 abstract class AbstractBankAccount implements BankAccount {
     protected int accountNumber;
     protected double balance;
     protected final Lock lock = new ReentrantLock();
 
     public AbstractBankAccount(int accountNumber, double balance) {
         //complete this constructor
         this.accountNumber = accountNumber;
         this.balance = balance;
     }
 
     public void deposit(double amount) {
        // deposit function 
            if (amount > 0) {
                lock.lock();
                try {
                    balance += amount;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Deposit amount must be positive.");
            }
     }
 
    public void withdraw(double amount) throws InsufficientFundsException {
        // withdraw function
        if (amount <= 0) {
           System.out.println("Withdrawal amount must be positive.");
           return;
        }
        lock.lock(); // lock the account for thread safety
        try {
           if (balance >= amount) {
              balance -= amount;
           } else {
              throw new InsufficientFundsException();
           }
        } finally {
           lock.unlock(); // unlock to allow other threads to access the account
        }
    }
 
 }
 
// Checking Account Class
// This class extends the AbstractBankAccount class and represents a checking account.
 class CheckingAccount extends AbstractBankAccount {
     public CheckingAccount(int accountNumber, double balance) {
         super(accountNumber, balance);
     }

     @Override
     public int getAccountNumber() { // function to getAccountNumber
         return accountNumber;
     }

     @Override
     public double getBalance() { // function to getBalance
         return balance;
     }
 }
 
// Savings Account Class
// This class extends the AbstractBankAccount class and represents a savings account.
class SavingsAccount<T extends BankAccount> extends AbstractBankAccount {
 private final Map<Integer, T> accounts = new HashMap<>();
 
     public SavingsAccount(int accountNumber, double balance) {
         super(accountNumber, balance);
     }

     @Override
     public int getAccountNumber() { // override interface function for getAccountNumber
        return accountNumber;
     }

     @Override
     public double getBalance() { // override interface function for getBalance
        return balance;
     }

     public void addAccount(T account) { // addAccount function
         accounts.put(account.getAccountNumber(), account);
     }
 
     public T getAccount(int accountNumber) { // getAccount function
         return accounts.get(accountNumber);
     }
   }


// Bank Class
// This class manages a collection of bank accounts.
// It includes methods for adding accounts and retrieving them by account number.
 class Bank<T extends BankAccount> {
    private final Set<T> accounts = new HashSet<>();
     // chose appropriate collection (prob use a set)
 
     public void addAccount(T account) { // addAccount function to accounts list
         //complete this method
         accounts.add(account);
     }
 
    public T getAccount(int accountNumber) { // getAccount function to get account by account number
        for (T account : accounts) {
           if (account.getAccountNumber() == accountNumber) {
              return account;
           }
        }
        throw new NoSuchElementException("Account " + accountNumber + " not found");
    }
 }
 
// Transaction Runnable
// This class implements the Runnable interface and represents a transaction.
// It includes methods for transferring money between accounts, handling deadlocks,
 class Transaction implements Runnable {
     private final Bank<? extends BankAccount> bank;
     private final String type;
     private final int sourceAccount;
     private final int targetAccount;
     private final double amount;


    // constructor for Transaction class
     public Transaction(Bank<? extends BankAccount> bank, String type, int sourceAccount, int targetAccount, double amount) {
         this.bank = bank;
         this.type = type;
         this.sourceAccount = sourceAccount;
         this.targetAccount = targetAccount;
         this.amount = amount;
     }
 
     @Override
     public void run() { // run method for transaction from Runnable interface
         try {
             switch (type) {
                 //complete the switch statement to handle all transaction types.
                    case "transfer":
                        BankAccount fromAccount = bank.getAccount(sourceAccount);
                        BankAccount toAccount = bank.getAccount(targetAccount);
                        if (fromAccount != null && toAccount != null) {
                            transfer(fromAccount, toAccount, amount);
                        } else {
                            System.out.println("Invalid account numbers.");
                        }
                        break;
                    case "withdraw": // withdraw case 
                        BankAccount withdrawAccount = bank.getAccount(sourceAccount);
                        withdrawAccount.withdraw(amount);
                        System.out.println("Withdrawal successful: $" + amount + " from Account " + sourceAccount);
                        break;
                    case "deposit": // deposit case
                        BankAccount depositAccount = bank.getAccount(sourceAccount);
                        depositAccount.deposit(amount);
                        System.out.println("Deposit successful: $" + amount + " to Account " + sourceAccount);
                        break;
                    case "deadlock_demo": // deadlock_demo case 
                        BankAccount deadlockFrom = bank.getAccount(sourceAccount);
                        BankAccount deadlockTo = bank.getAccount(targetAccount);
                        if (deadlockFrom != null && deadlockTo != null) {
                            deadlockDemo(deadlockFrom, deadlockTo, amount);
                        } else {
                            System.out.println("Invalid account numbers.");
                        }
                        break;
                    case "race_condition_demo": // race condition demo case
                        BankAccount raceFrom = bank.getAccount(sourceAccount);
                        BankAccount raceTo = bank.getAccount(targetAccount);
                        if (raceFrom != null && raceTo != null) {
                            raceConditionDemo(raceFrom, raceTo, amount);
                        } else {
                            System.out.println("Invalid account numbers.");
                        }
                        break;
                    default: // default if none of the cases are matched
                        System.out.println("Invalid transaction type.");
                        break;
             }
         } catch (Exception e) {
             System.out.println("Transaction failed 1: " + e.getMessage());
         }
     }
 
    private void transfer(BankAccount from, BankAccount to, double amount) throws InsufficientFundsException {
        // transfer function to transfer money from one account to another
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }
        // synchronized functions to keep thread safety
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println("Transfer successful: $" + amount + " from Account " + from.getAccountNumber() + " to Account " + to.getAccountNumber());
                } else {
                    System.out.println("Transfer failed: Insufficient funds in Account " + from.getAccountNumber());
                }
            }
        }
    }
 
    private void deadlockDemo(BankAccount from, BankAccount to, double amount) {
        System.out.println("Transaction: deadlockDemo.");

        Thread t1 = new Thread(() -> {
            try {
                // Use ordered lock acquisition to prevent deadlocks
                BankAccount first = from.getAccountNumber() < to.getAccountNumber() ? from : to;
                BankAccount second = from.getAccountNumber() < to.getAccountNumber() ? to : from;
                
                synchronized (first) {
                    synchronized (second) {
                        // If we swapped the accounts, we need to call transfer with the right direction
                        if (first == from) {
                            from.withdraw(amount);
                            to.deposit(amount);
                            System.out.println("Transfer successful: $" + amount + " from Account " + 
                                              from.getAccountNumber() + " to Account " + to.getAccountNumber());
                        } else {
                            to.withdraw(amount);
                            from.deposit(amount);
                            System.out.println("Transfer successful: $" + amount + " from Account " + 
                                              to.getAccountNumber() + " to Account " + from.getAccountNumber());
                        }
                    }
                }
            } catch (InsufficientFundsException e) {
                System.out.println("Transaction failed 2: " + e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                // Same ordering strategy for the second thread
                BankAccount first = to.getAccountNumber() < from.getAccountNumber() ? to : from;
                BankAccount second = to.getAccountNumber() < from.getAccountNumber() ? from : to;
                
                synchronized (first) {
                    synchronized (second) {
                        // If we swapped the accounts, we need to call transfer with the right direction
                        if (first == to) {
                            to.withdraw(amount);
                            from.deposit(amount);
                            System.out.println("Transfer successful: $" + amount + " from Account " + 
                                              to.getAccountNumber() + " to Account " + from.getAccountNumber());
                        } else {
                            from.withdraw(amount);
                            to.deposit(amount);
                            System.out.println("Transfer successful: $" + amount + " from Account " + 
                                              from.getAccountNumber() + " to Account " + to.getAccountNumber());
                        }
                    }
                }
            } catch (InsufficientFundsException e) {
                System.out.println("Transaction failed 3: " + e.getMessage());
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Deadlock demo completed.");
    }
 
    private void raceConditionDemo(BankAccount from, BankAccount to, double amount) {
        System.out.println("Transaction: raceConditionDemo.");

        // Start threads to simulate race conditions
        Thread t1 = new Thread(() -> {
           try {
              from.withdraw(amount);
           } catch (InsufficientFundsException e) {
              System.out.println("Transaction failed 4: " + e.getMessage());
           }
        });

        Thread t2 = new Thread(() -> {
           try {
              from.withdraw(amount);
           } catch (InsufficientFundsException e) {
              System.out.println("Transaction failed 5: " + e.getMessage());
           }
        });

        Thread t3 = new Thread(() -> to.deposit(amount));
        Thread t4 = new Thread(() -> to.deposit(amount));

        // Start all threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads to complete
        try {
           t1.join();
           t2.join();
           t3.join();
           t4.join();
        } catch (InterruptedException e) {
           System.out.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Race condition demo completed.");
    }
 }
 
 // Main Class
 public class BankSystem {
    public static void main(String[] args) {
        // redirect output to txt file
        try {
            System.out.println("Output written to output.txt");
            // Redirect standard output to a file
            PrintStream fileOut = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(fileOut);
            
            Bank<BankAccount> bank = new Bank<>();
            loadAccounts(bank, "accounts.txt");
            executeTransactions(bank, "transactions.txt");
            
            // Close the PrintStream when done
            fileOut.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("Could not create output file: " + e.getMessage());
        }
    }
 
    // load accounts from file
    private static void loadAccounts(Bank<BankAccount> bank, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
           String line;
           while ((line = reader.readLine()) != null) {
              String[] parts = line.split(",");
              if (parts.length == 3) {
                 int accountNumber = Integer.parseInt(parts[0]);
                 String accountType = parts[1];
                 double balance = Double.parseDouble(parts[2]);

                 BankAccount account;
                 if (accountType.equalsIgnoreCase("Checking")) {
                    account = new CheckingAccount(accountNumber, balance);
                 } else if (accountType.equalsIgnoreCase("Savings")) {
                    account = new SavingsAccount<>(accountNumber, balance);
                 } else {
                    System.out.println("Unknown account type: " + accountType);
                    continue;
                 }

                 bank.addAccount(account);
              } else {
                 System.out.println("Invalid account data: " + line);
              }
           }
        } catch (IOException e) {
           System.out.println("Error reading accounts file: " + e.getMessage());
        }
    }
 
    // load transactions from file
    private static void executeTransactions(Bank<BankAccount> bank, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
           String line;
           List<Thread> threads = new ArrayList<>();
           while ((line = reader.readLine()) != null) {
              String[] parts = line.split(",");
              if (parts.length == 4) {
                 String transactionType = parts[0].trim();
                 int sourceAccount = Integer.parseInt(parts[1].trim());
                 int targetAccount = parts[2].trim().equals("-") ? -1 : Integer.parseInt(parts[2].trim());
                 double amount = Double.parseDouble(parts[3].trim());

                 Transaction transaction = new Transaction(bank, transactionType, sourceAccount, targetAccount, amount);
                 Thread transactionThread = new Thread(transaction);
                 threads.add(transactionThread);
                 transactionThread.start();
              } else {
                 System.out.println("Invalid transaction data: " + line);
              }
           }
           for (Thread thread : threads) {
              try {
                 thread.join();
              } catch (InterruptedException e) {
                 System.out.println("Thread interrupted: " + e.getMessage());
              }
           }
        } catch (IOException e) {
           System.out.println("Error reading transactions file: " + e.getMessage());
        }
    }
}