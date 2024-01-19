package behaviourtests;

import bank.service.*;
import dtu.ws.fastmoney.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;


public class BankServiceSteps {

    BankService bank = mock(BankService.class);
    MessageQueue queue = mock(MessageQueue.class);

    Payment payment;
    String paymentId;
    int amount;
    String merchantBankId;
    String customerBankId;
    DTUPayBankService service = new DTUPayBankService(queue,bank);

    @Given("a valid payment with merchantBankId, customerBankId and amount")
    public void a_valid_payment_with_merchant_bank_id_customer_bank_id_and_amount() {
        merchantBankId = "merchantBankId";
        customerBankId = "customerBankId";
        amount = 200;

        payment = new Payment();
        payment.setMerchantBankId(merchantBankId);
        payment.setAmount(amount);
        payment.setCustomerBankId(customerBankId);
        payment.setPaymentId(new PaymentId(UUID.randomUUID()));
    }

    @When("a {string} event is sent")
    public void aEventIsSent(String arg0) {
        BankAccountAssigned event = new BankAccountAssigned(payment.getPaymentId(),customerBankId,merchantBankId,amount);
        service.makeBankTransfer(event);
    }

    @Then("the request has been sent to the {string} bank service")
    public void theRequestHasBeenSentToTheBankService(String arg0) throws BankServiceException_Exception {
        verify(bank).transferMoneyFromTo(payment.getCustomerBankId(),payment.getMerchantBankId(), BigDecimal.valueOf(payment.getAmount()),"paymentDesc");
    }

    @Then("a {string} event is published")
    public void aEventIsPublishedWithAPaymentID(String arg0) {
        PaymentSuccessful paymentSuccessful = new PaymentSuccessful(payment.getPaymentId());

        verify(queue).publish(paymentSuccessful);

    }


}