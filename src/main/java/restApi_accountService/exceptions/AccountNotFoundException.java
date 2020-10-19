package restApi_accountService.exceptions;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException() {
		super("Account not found");
	}
}
