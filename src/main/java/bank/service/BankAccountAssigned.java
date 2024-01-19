package bank.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;


@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankAccountAssigned extends Event{
    private static final long serialVersionUID = -3281411080003071594L;
    private PaymentId paymentId;
    private String customerBankdId;
    private String merchantBankId;
    private int amount;
}
