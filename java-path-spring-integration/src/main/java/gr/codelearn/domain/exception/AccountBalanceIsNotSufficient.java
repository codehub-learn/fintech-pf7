package gr.codelearn.domain.exception;

public class AccountBalanceIsNotSufficient extends Exception {
    public AccountBalanceIsNotSufficient(String message){
        super(message);
    }
}
