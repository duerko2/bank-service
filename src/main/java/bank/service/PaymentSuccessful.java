package bank.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentSuccessful extends Event{

    private static final long serialVersionUID = 8836547826948529579L;

    private PaymentId paymentId;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentSuccessful)) return false;
        PaymentSuccessful paymentSuccessful = (PaymentSuccessful) o;
        return paymentId.equals(paymentSuccessful.paymentId);
    }


}
