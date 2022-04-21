package com.dulcia.atm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ATM system
 */
public class ATM {
    public static void main(String[] args) {
        ArrayList<Account> accounts=new ArrayList<>();
        Scanner sc= new Scanner(System.in);

        //system homepage
        while (true) {
            System.out.println("====Dulcia's ATM system====");
            System.out.println("1.log in");
            System.out.println("2.sign up");
            System.out.println("please select your business");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    login(accounts,sc);
                    break;
                case 2:
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("sorry there is an error here please try again");
            }
        }
    }

    /**
     * log in
     * @param accounts array of all accounts
     * @param sc scanner
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=========log in operation==========");
        if (accounts.size()==0){
            System.out.println("sorry, there is no account in the system\n please sign up");
            return ;
        }
       // OUT:
        while (true) {
            System.out.println("please input your cardID");
            String cardId=sc.next();
            Account acc=getAccountById(cardId,accounts);
            if(acc!=null){
                while (true) {
                    System.out.println("please input your password");
                    String password=sc.next();
                    if(acc.getPassword().equals(password)){
                        System.out.println("congratulation "+acc.getUserName()+" log in successfully");
                        //show the operation page
                        showUserCommand(sc,acc,accounts);
                        return;
                        //break OUT;
                    }
                    else{
                        System.out.println("sorry wrong number please input again");
                    }
                }
            }
            else{
                System.out.println("sorry it is a wrong card number,please try again");
            }
        }
    }

    /**
     * show the operation page after log in
     */
    private static void showUserCommand(Scanner sc,Account acc,ArrayList<Account> accounts) {
        while (true) {
            System.out.println("======operation page=====");
            System.out.println("1.check your account");
            System.out.println("2.save money");
            System.out.println("3.withdraw money");
            System.out.println("4.transfer money");
            System.out.println("5.change the password");
            System.out.println("6.log out");
            System.out.println("7.cancel your account");
            System.out.println("please input your command");
            int command=sc.nextInt();
            switch(command){
                case 1:
                    showAccount(acc);
                    break;
                case 2:
                    depositMoney(sc,acc);
                    break;
                case 3:
                    withDrawMoney(sc,acc);
                    break;
                case 4:
                    transferMoney(sc,acc,accounts);
                    break;
                case 5:
                    changePassword(sc,acc);
                    break;
                case 6:
                    System.out.println("log out successfully, welcome next");
                    return;//stop the function
                case 7:
                    boolean a=deleteAccount(sc,acc,accounts);
                    if(a==false){
                        break;
                    }else{
                        return;
                    }
                default:
                    System.out.println("your operation command is incorrect please input again ");
            }
        }
    }

    /**
     * delete the account
     * @param sc scanner
     * @param acc account tha are log in now
     */
    private static boolean deleteAccount(Scanner sc, Account acc,ArrayList<Account> accounts) {
        System.out.println("Are you sure want to cancel your account y/n");
        if(sc.next().equals("y")){
            if(acc.getBalance()!=0){
                System.out.println("there is still money in your account, we can not delete your account!");
                return false;
            }else {
                accounts.remove(acc);
                System.out.println("we have already cancel account");
                return true;
            }
        }
        else{
            System.out.println("ok we will remain your account");
            return false;
        }
    }

    /**
     * change password
     * @param sc scanner
     * @param acc account
     */
    private static void changePassword(Scanner sc, Account acc) {
        System.out.println("====change password operation");
                while (true) {
                    System.out.println("please input the password of your account");
                    String password = sc.next();
                    if (password.equals(acc.getPassword())) {
                        while (true) {
                            System.out.println("please input your new password");
                            password = sc.next();
                            System.out.println("please input your new password again");
                            String okPassword = sc.next();
                            if(password.equals(okPassword)){
                                acc.setPassword(password);
                                System.out.println("congratulation password change successfully");
                                return;
                            }else{
                                System.out.println("the passwords you input are different please check and try again");
                            }
                        }
                    }else{
                        System.out.println("wrong password please try again!");
                    }
                }
            }



        /**
     * transfer money operation
     * @param sc scanner
     * @param acc the account log in now
     * @param accounts all of the account in the system
     */
    private static void transferMoney(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("====transfer money operation====");
        if(accounts.size()<2){
            System.out.println("sorry there is not enough account in the system");
            return;
        }
        else{
            if(acc.getBalance()<=0){
                System.out.println("you do not have money!");
                return;
            }
            else{
                while (true) {
                    System.out.println("please input the cardID that you want to transfer to");
                    String id = sc.next();
                    Account acc1 = getAccountById(id, accounts);
                    if (acc1 == null) {
                        System.out.println("wrong cardId please try again");
                    } else {
                        while(true)
                        {
                            System.out.println("please input the money you want to transfer");
                            double money=sc.nextDouble();
                            if(money>acc.getBalance()){
                                System.out.println("you do not have enough money, you only have"+acc.getBalance());
                            }
                            else {
                                if(money>acc.getQuotaMoney()){
                                    System.out.println("that is higher than your transfer quota ("+acc.getQuotaMoney()+")");
                                }else {
                                    System.out.println("please input the first letter of *"+ acc1.getUserName().substring(1)+"s name");
                                    if(acc1.getUserName().startsWith(sc.next())){
                                        acc.setBalance(acc.getBalance() - money);
                                        System.out.println("transfer successfully now you have " + acc.getBalance());
                                        acc1.setBalance(acc1.getBalance() - money);
                                        System.out.println(acc1.getUserName()+"have already received your money have a nice day!");
                                        return;
                                    }
                                    else{
                                        System.out.println("wrong username or cardID please try again");
                                    }
                                }
                            }
                        }
                    }
                }

                }
            }
        }

    /**
     * withdraw money
     * @param sc scanner
     * @param acc the account log in now
     */
    private static void withDrawMoney(Scanner sc, Account acc) {
        if(acc.getBalance()<100){
            System.out.println("you have less than 100 dollars, so you can not withdraw the money");
        }
        else{
            while (true) {
                System.out.println("please input the money you want to withdraw");
                double money=sc.nextDouble();
                if(money>acc.getQuotaMoney()) {
                    System.out.println("sorry, the money is higher than your quota money, please input again");
                    System.out.println("the highest money you can get from here this time is "+acc.getQuotaMoney());
                }
                else{
                    if(money>acc.getBalance()){
                        System.out.println("sorry you do not have enough money");
                    }else {
                        acc.setBalance(acc.getBalance() - money);
                        System.out.println("here is your money and your balance is now " + acc.getBalance());
                        return;
                    }
                }
            }

        }
        return;
    }

    /**
     * save money
     * @param sc scanner
     * @param acc the account log in now
     */
    private static void depositMoney(Scanner sc, Account acc) {
        System.out.println("==deposit money operation==");
        System.out.println("please input the money");
        double money=sc.nextDouble();
        acc.setBalance(acc.getBalance()+money);
        System.out.println(" congratulation ,your balance is "+acc.getBalance()+" now");
    }

    /**
     * show your account
     * @param acc the account that log in now
     */
    private static void showAccount(Account acc) {
        System.out.println("the information of your account");
        System.out.println("cardID: "+acc.getCardId());
        System.out.println("username: "+acc.getUserName());
        System.out.println("money: "+acc.getBalance());
        System.out.println("quota money: "+acc.getQuotaMoney());
    }

    /**
     * open an account
     * @param  accounts the array of account
     */
    private static void register(ArrayList<Account> accounts,Scanner sc) {
        System.out.println("==========open account operation=============");
        Account account=new Account();
        System.out.println("please input your username");
        String Username=sc.next();
        account.setUserName(Username);
        while (true) {
            System.out.println("please input your password");
            String password=sc.next();
            System.out.println("please input your password again");
            String okPassword=sc.next();
            if(password.equals(okPassword)){
                account.setPassword(password);//the password are same
                break;
            }else{
                System.out.println("the password are not the same please input again");
            }
        }
        System.out.println("please input the single transfer limit of your account");
        double quotaMoney=sc.nextDouble();
        account.setQuotaMoney(quotaMoney);
        //give your account a 8 number card number, which can be same with other card
        String cardId=getRandomCardId(accounts);
        account.setCardId(cardId);
        accounts.add(account);
        System.out.println("congratulation "+Username+" your success to open an account \n your card number is "+cardId+" please record it carefully");

    }

    /**
     * get a random card number that are different from other's card number
     * @param accounts account array
     * @return card number
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r=new Random();
        while (true) {
            String  cardId="";
            for (int i = 0; i < 8; i++) {
                cardId +=r.nextInt(10);
            }
            //If the cardId is same with others
            Account acc=getAccountById(cardId,accounts);
            if(acc==null){
                return cardId;
            }
        }
    }

    /**
     * get the user account from the cardId
     * @param Id card Id
     * @param accounts user's account
     * @return user's account
     */
    private static Account getAccountById(String Id,ArrayList<Account>accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc=accounts.get(i);
            if(acc.getCardId().equals(Id)){
                return acc;
            }
        }
        return null;
    }
}

