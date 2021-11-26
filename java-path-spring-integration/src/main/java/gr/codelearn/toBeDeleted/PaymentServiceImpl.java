package gr.codelearn.toBeDeleted;

//@Service
//@AllArgsConstructor
//@Slf4j
//public class PaymentServiceImpl implements PaymentService {
//
//    private final String queueName = "payment.queue";
//
//    private AccountRepository accountRepository;
//    private ReportingService reportingService;
//
//    @Override
//    //@RabbitListener(queues = queueName)
//    public void processPayment(Payment payment) {
//        // todo payment id is received as null, is this OK?
//        // todo should this method validate if payment is OK again? hasn't this been validated in the API module?
//        try {
//            log.info("Payment received: {}.", payment);
//            Optional<Account> debtorOptional = accountRepository.findAccountByIban(payment.getDebtor().getIban());
//            if (debtorOptional.isPresent()) {
//                Account debtor = debtorOptional.get();
//                BigDecimal result = debtor.getBalance().subtract(payment.getPaymentAmount());
//                if (result.compareTo(BigDecimal.ZERO) < 0) {
//                    throw new AccountBalanceIsNotSufficient("Account " + debtor.getIban() + " has an insufficient balance for a payment.");
//                }
//                debtor.setBalance(result);
//                accountRepository.save(debtor);
//            }
//            Optional<Account> creditorOptional = accountRepository.findAccountByIban(payment.getCreditor().getIban());
//            if (creditorOptional.isPresent()) {
//                Account creditor = creditorOptional.get();
//                BigDecimal result = creditor.getBalance().add(payment.getPaymentAmount());
//                creditor.setBalance(result);
//                accountRepository.save(creditor);
//            }
//            Optional<Account> bankerOptional = accountRepository.findAccountByIban("GR00000000000001");
//            if (bankerOptional.isPresent()){
//                Account banker = bankerOptional.get();
//                BigDecimal result = banker.getBalance().add(payment.getFeeAmount());
//                banker.setBalance(result);
//                accountRepository.save(banker);
//            }
//            reportingService.logPayment(payment);
//            reportingService.logAccounts(accountRepository.findAll());
//        } catch (AccountBalanceIsNotSufficient e) {
//            log.error("{}.", e);
//        }
//    }
//}
