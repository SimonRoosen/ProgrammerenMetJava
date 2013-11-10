package business;

import business.Amount.Transaction;
import exceptions.NoNegativeAmountAllowed;

public class SavingAccount extends Account
{
	private static final double BASE_INTREST = 0.015;
	private static final double GROWTH = 0.02;
	private static final double LOYALTY = 0.005;

	public SavingAccount(final Amount beginSaldo)
	{
		super(beginSaldo);
	}

	@Override
	public void addTransaction(final Amount amount)
			throws NoNegativeAmountAllowed {
		Amount current = super.getCurrentValue();
		current.modify(Transaction.DEPOSIT, amount);

		if (current.isNegative()) {
			final String message = String
					.format("A saving account can not be negative!\nThe current value (begin saldo %10.2f - transactions %s) is: %10.2f\n\tA deposit/credit of %10.2f is not allowed. \n",
							this.getBeginSaldo().getAmount(),
							this.getTransactions(), current.getAmount(),
							amount.getAmount());
			throw new NoNegativeAmountAllowed(message);
		}
		super.getTransactions().add(amount);
	}

	private Amount baseIntrest(){
		Amount value = getBeginSaldo();
		value.modify(Amount.Transaction.MULTIPLY, new Amount(BASE_INTREST));
		return value;
	}
	private Amount growthPremium()
	{
		 Amount value = totalTransactions();
		if (!value.isNegative())
		{
			value.modify(Amount.Transaction.MULTIPLY, new Amount(GROWTH));
		}
		return value;
	}

	private Amount loyaltyPremium()
	{
		 Amount value = getBeginSaldo();
		value.modify(Amount.Transaction.MULTIPLY, new Amount(LOYALTY));
		return value;
	}

	@Override
	public Amount getCurrentValue()
	{
		 Amount current = super.getCurrentValue();
		 current.modify(Amount.Transaction.DEPOSIT, baseIntrest());
		 current.modify(Amount.Transaction.DEPOSIT, growthPremium());
		 current.modify(Amount.Transaction.DEPOSIT, loyaltyPremium());
		return current;
	}

	@Override
	public String toString()
	{
		return "\n\tSavingAccount [" + super.toString() + "]";
	}

}
