package gr.codelearn.service;

//@Service
//@AllArgsConstructor
//@Slf4j
//public class PaymentServiceImpl implements PaymentService {
//
//    RabbitTemplate rabbitTemplate;
//    AccountService accountService;
//
//    @Override
//    public void pay(Payment payment) throws InvalidAccountException {
//        try {
//            boolean validated = validate(payment);
//            if (validated) {
//                rabbitTemplate.convertAndSend(AMQPConfiguration.exchangeName, AMQPConfiguration.routingKey, payment);
//                log.info("A payment has been queued.");
//            }
//        } catch (InvalidAccountException e) {
//            // I do not want the caller of this method to know exactly what the error in the back-end was
//            throw new InvalidAccountException("Account(s) details are wrong.");
//        }
//    }
//}
