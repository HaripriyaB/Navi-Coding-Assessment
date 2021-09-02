package com.haripriya;

import java.io.*;
import java.util.*;

class Loan {
    String bankName;
    String borrowerName;
    int principal;
    int term;
    int rate;
    int interestPerMonth;
    List<Integer> payments;
    List<Integer> lumpSumMonth;

    Loan(String bankName, String borrowerName, int principal, int term, int rate) {
        this.bankName = bankName;
        this.borrowerName = borrowerName;
        this.principal = principal;
        this.term = term * 12;
        this.rate = rate;
        this.payments = new ArrayList<>();
        this.lumpSumMonth = new ArrayList<>();
    }

    public String getBankName() {
        return bankName;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getPrincipal() {
        return principal;
    }

    public int getRate() {
        return rate;
    }

    public int getTerm() {
        return term;
    }

    public int getInterestPerMonth() {
        double interest = ((double) principal * (double) rate * (double) (term / 12)) / 100;
        int totalInterest = (interest % 1 == 0.0 ? (int) interest : ((int) interest) + 1);
        double interestpm = ((double) totalInterest + (double) principal) / (double) term;
        interestPerMonth = (interestpm % 1 == 0.0 ? (int) interestpm : ((int) interestpm) + 1);
        return interestPerMonth;
    }

    public int getInterest() {
        double interest = ((double) principal * (double) rate * (double) (term / 12)) / 100;
        int totalInterest = (interest % 1 == 0.0 ? (int) interest : ((int) interest) + 1);
        return totalInterest;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public List<Integer> getLumpSumMonth() {
        return lumpSumMonth;
    }

    public void addPayment(int payment, int month) {
        this.payments.add(payment);
        this.lumpSumMonth.add(month);
    }
}

public class App {
    public static HashMap<String, Loan> bank = new HashMap<>();

    public static void addNewLoan(String bankName, String borrowerName, int principal, int term, int rate) {
        if (principal > 0 && term > 0 && rate > 0) {
            Loan newLoan = new Loan(bankName, borrowerName, principal, term, rate);
            bank.put(bankName + borrowerName, newLoan);
        }
    }

    public static void addLumpPayment(String bankName, String borrowerName, int amount, int emiNumber) {
        Loan loan = bank.get(bankName + borrowerName);
        if (amount > 0 && emiNumber > 0) {
            loan.addPayment(amount, emiNumber);
            bank.put(bankName + borrowerName, loan);
        }
    }

    public static String checkBalance(String bankName, String borrowerName, int emiNumber) {
        Loan loan = bank.get(bankName + borrowerName);
        List<Integer> payments = loan.getPayments();
        List<Integer> months = loan.getLumpSumMonth();
        int amountPaid = loan.getInterestPerMonth() * emiNumber;
        int term = loan.getTerm() - emiNumber;
        for (int i = 0; i < months.size(); i++) {
            if (emiNumber >= months.get(i)) {
                amountPaid += payments.get(i);
                int coverUpTerm = payments.get(i) / loan.getInterestPerMonth();
                term -= coverUpTerm;
            }
        }
        return (bankName + " " + borrowerName + " " + amountPaid + " " + term);
    }

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]); // Input file path
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] line = st.split(" ");
            if (line[0].equals("LOAN")) {
                String bankName = line[1];
                String borrowerName = line[2];
                int principal = Integer.parseInt(line[3]);
                int term = Integer.parseInt(line[4]);
                int rate = Integer.parseInt(line[5]);
                addNewLoan(bankName, borrowerName, principal, term, rate);
            } else if (line[0].equals("PAYMENT")) {
                String bankName = line[1];
                String borrowerName = line[2];
                int amount = Integer.parseInt(line[3]);
                int emiNumber = Integer.parseInt(line[4]);
                addLumpPayment(bankName, borrowerName, amount, emiNumber);
            } else if (line[0].equals("BALANCE")) {
                String bankName = line[1];
                String borrowerName = line[2];
                int emiNumber = Integer.parseInt(line[3]);
                System.out.println(checkBalance(bankName, borrowerName, emiNumber));
            }
        }
        br.close();
    }
}
