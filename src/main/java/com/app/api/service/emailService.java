package com.app.api.service;

import com.app.api.model.accountModel;
import com.app.api.model.orderModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Random;

@Service
public class emailService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private accountService accountService;

        @Value("${spring.mail.username}")
        private String fromEmail;

        public void sendOtpEmail(String email,String id) {
            Random random = new Random();
            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                otp.append(random.nextInt(10));
            }

            accountModel accountModel = new accountModel();
            accountModel.setId(Integer.parseInt(id));
            accountModel.setOtp(String.valueOf(otp));
            if( this.accountService.updateOtp(accountModel)){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setSubject("OTP - FAST FOOD");
                message.setText("Mã của bạn là: " + otp);
                message.setTo(email);
                emailSender.send(message);
            }
        }

    public void sendNotificationWhenPayment(orderModel  orderModel) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper  helper = new MimeMessageHelper(message, true, "UTF-8");
            Context context = new Context();
            context.setVariable("title", "Đơn Hàng đã được chuyển tới Quý Khách từ '"+orderModel.getStoreModel().getName()+"' .Cảm ơn Quý Khách đã đặt hàng !");
            context.setVariable("product","Sản Phẩm: "+ orderModel.getOrderItemModel().getProductModel().getName());
            context.setVariable("quantity", "Số Lương: x"+orderModel.getOrderItemModel().getQuantity());
            context.setVariable("price", "Giá: "+orderModel.getOrderItemModel().getPrice()+"00 VNĐ");
            context.setVariable("time", "Đã giao lúc: "+orderModel.getReceive_at()+" .Nếu có thắc mắc về Đơn Hàng hãy gọi ngay cho chúng tôi theo số sau: "+orderModel.getStoreModel().getPhone()+" để chúng tôi có thể Hỗ trợ Bạn.");


            String htmlBody = templateEngine.process("email", context);

            helper.setTo(orderModel.getAccountModel().getEmail());
            helper.setSubject("FAST FOOD - Thông Báo Giao Hàng Thành Công ");
            helper.setText(htmlBody, true); // true indicates that the text is HTML
            helper.setFrom(fromEmail);

            emailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
