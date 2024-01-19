package bank.service;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import messaging.Event;
import messaging.MessageQueue;
import java.math.BigDecimal;

public class DTUPayBankService {

    MessageQueue queue;

    BankService bank;


    public DTUPayBankService(MessageQueue q, BankService bank) {
        this.bank = bank;
        this.queue = q;
        this.queue.addHandler("BankAccountsAssigned",this::makeBankTransfer);
    }

    public void makeBankTransfer(Event event) {
        System.out.printf("payment event"+event.getType());
        var payment = event.getArgument(0,Payment.class);
        var amount = payment.getAmount();
        var customerBankId = payment.getCustomerBankId();
        var merchantBankId = payment.getMerchantBankId();


        try {

            bank.transferMoneyFromTo(
                    customerBankId, merchantBankId, BigDecimal.valueOf(amount), "paymentDesc");

        }catch (BankServiceException_Exception e){
            System.out.println(e.getMessage());
            return;
        }

        // Publish successful payment event
        Event paymentSuccessful = new Event("PaymentSuccessful",new Object[]{payment});
        queue.publish(paymentSuccessful);
    }
}
