package bank.service;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import messaging.MessageQueue;
import java.math.BigDecimal;

public class DTUPayBankService {

    MessageQueue queue;

    BankService bank;


    public DTUPayBankService(MessageQueue q, BankService bank) {
        this.bank = bank;
        this.queue = q;
        this.queue.addHandler(BankAccountAssigned.class, e->makeBankTransfer((BankAccountAssigned)e));
    }

    public void makeBankTransfer(BankAccountAssigned event) {
        var paymendId = event.getPaymentId();
        var amount = event.getAmount();
        var customerBankId = event.getCustomerBankdId();
        var merchantBankId = event.getMerchantBankId();


        try {

            bank.transferMoneyFromTo(
                    customerBankId, merchantBankId, BigDecimal.valueOf(amount), "paymentDesc");

        }catch (BankServiceException_Exception e){
            System.out.println(e.getMessage());
            return;
        }

        // Publish successful payment event
        PaymentSuccessful paymentSuccessful = new PaymentSuccessful(paymendId);
        queue.publish(paymentSuccessful);
    }
}
