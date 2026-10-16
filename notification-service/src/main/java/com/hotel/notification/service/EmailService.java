
package com.hotel.notification.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderConfirmationEmail(
            String customerEmail,
            Long orderId,
            String itemName,
            Integer quantity,
            Double totalAmount) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            String safeItemName = escapeHtml(itemName);

            String price = totalAmount == null
                    ? "Not available"
                    : String.format(Locale.US, "%,.2f", totalAmount);

            String html = """
                <div style="margin:0;background:#f1f4f8;padding:24px 10px;
                            font-family:Arial,Helvetica,sans-serif;color:#172b4d;">
                  <div style="max-width:600px;margin:0 auto;background:#ffffff;
                              border-radius:16px;overflow:hidden;
                              border:1px solid #e1e7ef;">

                    <div style="background:#102744;padding:32px 22px;text-align:center;">
                      <div style="color:#eec875;font-size:12px;letter-spacing:4px;
                                  font-weight:bold;">
                        HOTEL MANAGEMENT
                      </div>
                      <div style="font-size:34px;margin:14px 0 5px;color:#ffffff;">
                        &#10022;
                      </div>
                      <h1 style="font-size:27px;line-height:1.3;margin:0;color:#ffffff;">
                        Your order is confirmed!
                      </h1>
                      <p style="margin:12px 0 0;color:#dbe5f1;font-size:14px;">
                        A delicious experience is on its way.
                      </p>
                      <div style="width:65px;height:3px;background:#eec875;
                                  margin:22px auto 0;"></div>
                    </div>

                    <div style="padding:28px 22px 10px;text-align:center;">
                      <div style="display:inline-block;background:#e6f6ed;
                                  border:1px solid #b9e5ca;border-radius:30px;
                                  padding:10px 20px;color:#167447;font-size:13px;
                                  font-weight:bold;letter-spacing:1px;">
                        &#10003; &nbsp; ORDER CONFIRMED
                      </div>
                      <p style="font-size:16px;line-height:1.8;color:#526176;
                                margin:20px 0 8px;">
                        Dear Customer,<br/>
                        Thank you for choosing us! Your order has been confirmed.
                        We appreciate your trust in us.
                      </p>
                    </div>

                    <div style="margin:18px 20px;background:#ffffff;
                                border:1px solid #e2e8f0;border-radius:12px;
                                overflow:hidden;">
                      <div style="background:#f7f9fc;padding:19px 20px;
                                  border-bottom:1px solid #e2e8f0;">
                        <div style="font-size:11px;letter-spacing:2px;
                                    color:#75839a;font-weight:bold;">
                          YOUR ORDER SUMMARY
                        </div>
                        <div style="font-size:21px;font-weight:bold;color:#102744;
                                    margin-top:8px;">
                          Order #%s
                        </div>
                      </div>

                      <div style="padding:20px;">
                        <table role="presentation"
                               style="width:100%%;border-collapse:collapse;font-size:14px;">
                          <tr>
                            <td style="padding:13px 0;color:#738096;">
                              Food item
                            </td>
                            <td style="padding:13px 0;text-align:right;color:#172b4d;
                                       font-weight:bold;">
                              %s
                            </td>
                          </tr>
                          <tr>
                            <td style="padding:13px 0;border-top:1px solid #edf0f5;
                                       color:#738096;">
                              Quantity
                            </td>
                            <td style="padding:13px 0;text-align:right;
                                       border-top:1px solid #edf0f5;
                                       color:#172b4d;font-weight:bold;">
                              %s
                            </td>
                          </tr>
                        </table>

                        <div style="background:#102744;border-radius:10px;
                                    padding:19px 16px;margin-top:15px;">
                          <table role="presentation"
                                 style="width:100%%;border-collapse:collapse;">
                            <tr>
                              <td style="color:#e8edf5;font-size:14px;
                                         vertical-align:middle;">
                                TOTAL AMOUNT
                              </td>
                              <td style="text-align:right;color:#f4d17f;
                                         font-size:24px;font-weight:bold;
                                         vertical-align:middle;">
                                &#8377;%s
                              </td>
                            </tr>
                          </table>
                        </div>
                      </div>
                    </div>

                    <div style="margin:20px;padding:19px;background:#edf8f1;
                                border-radius:10px;text-align:center;">
                      <div style="font-size:25px;color:#176b43;">
                        &#10003;
                      </div>
                      <p style="margin:8px 0;color:#176b43;font-size:15px;
                                font-weight:bold;">
                        Confirmed with care!
                      </p>
                      <p style="margin:0;color:#47745b;font-size:13px;line-height:1.7;">
                        Thank you for placing your order with us.
                        We look forward to serving you.
                      </p>
                    </div>

                    <div style="margin:20px;background:#f7f9fc;
                                border:1px solid #e2e8f0;border-radius:12px;
                                padding:22px 20px;">
                      <h2 style="margin:0 0 10px;color:#102744;font-size:20px;">
                        Need assistance?
                      </h2>
                      <p style="font-size:13px;line-height:1.7;color:#65748b;
                                margin:0 0 16px;">
                        Have a question about your order? We're happy to help.
                      </p>

                      <div style="padding:13px 0;border-top:1px solid #e2e8f0;">
                        <div style="font-size:11px;letter-spacing:1px;
                                    color:#8995a7;font-weight:bold;">
                          CALL US
                        </div>
                        <a href="tel:9767240179"
                           style="display:inline-block;margin-top:6px;color:#102744;
                                  font-size:15px;font-weight:bold;text-decoration:none;">
                          &#9742; &nbsp; 9767240179
                        </a>
                      </div>

                      <div style="padding:13px 0;border-top:1px solid #e2e8f0;">
                        <div style="font-size:11px;letter-spacing:1px;
                                    color:#8995a7;font-weight:bold;">
                          EMAIL US
                        </div>
                        <a href="mailto:prathameshkadam0705@gmail.com"
                           style="display:inline-block;margin-top:6px;color:#102744;
                                  font-size:14px;font-weight:bold;text-decoration:none;
                                  overflow-wrap:anywhere;">
                          prathameshkadam0705@gmail.com
                        </a>
                      </div>
                    </div>

                    <div style="padding:24px 18px;background:#102744;text-align:center;">
                      <p style="margin:0;color:#f4d17f;font-size:20px;
                                font-family:Georgia,serif;font-style:italic;">
                        Thank you for being our guest!
                      </p>
                      <p style="margin:12px 0;color:#dbe5f1;font-size:13px;">
                        Warm regards,<br/>
                        <strong>Hotel Management Team</strong>
                      </p>
                      <div style="height:1px;background:#344964;margin:20px 0;"></div>
                      <p style="margin:0;color:#aab9cc;font-size:10px;
                                letter-spacing:2px;">
                        GOOD FOOD &bull; GREAT SERVICE &bull; HAPPY GUESTS
                      </p>
                    </div>

                  </div>
                </div>
                """.formatted(
                    orderId,
                    safeItemName,
                    quantity,
                    price
                );

            helper.setFrom(fromEmail);
            helper.setTo(customerEmail);
            helper.setSubject(
                    "Order Confirmed! #" + orderId + " | Hotel Management");
            helper.setText(html, true);

            mailSender.send(message);

            System.out.println(
                    "HTML confirmation email sent to: " + customerEmail);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to send confirmation email", e);
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
    

	public void sendOrderStatusEmail(
	        String customerEmail,
	        Long orderId,
	        String itemName,
	        Integer quantity,
	        Double totalAmount,
	        String status) {
	
	    String subject;
	    String heading;
	    String messageText;
	
	    switch (status.toUpperCase()) {
	        case "CONFIRMED":
	            subject = "Order Confirmed! #" + orderId
	                    + " | Hotel Management";
	            heading = "Your order is confirmed!";
	            messageText = "Great news! Your order has been confirmed "
	                    + "successfully. We're delighted to serve you.";
	            break;
	
	        case "CANCELLED":
	            subject = "Order Cancelled #" + orderId
	                    + " | Hotel Management";
	            heading = "Your order has been cancelled";
	            messageText = "Your order has been cancelled. "
	                    + "If you have questions, please contact our support team.";
	            break;
	
	        case "DELIVERED":
	            subject = "Order Delivered #" + orderId
	                    + " | Hotel Management";
	            heading = "Your order has been delivered!";
	            messageText = "Your order has been marked as delivered. "
	                    + "Thank you for choosing us!";
	            break;
	
	        default:
	            throw new IllegalArgumentException(
	                    "Unsupported order status: " + status);
	    }
	
	    sendStatusHtmlEmail(
	            customerEmail,
	            orderId,
	            itemName,
	            quantity,
	            totalAmount,
	            subject,
	            heading,
	            messageText,
	            status
	    );
	}
	

	private void sendStatusHtmlEmail(
	        String customerEmail,
	        Long orderId,
	        String itemName,
	        Integer quantity,
	        Double totalAmount,
	        String subject,
	        String heading,
	        String messageText,
	        String status) {
	
	    try {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper =
	                new MimeMessageHelper(mimeMessage, true, "UTF-8");
	
	        String safeItemName = escapeHtml(itemName);
	        String safeHeading = escapeHtml(heading);
	        String safeMessage = escapeHtml(messageText);
	        String safeStatus = escapeHtml(status);
	
	        String price = totalAmount == null
	                ? "Not available"
	                : String.format(java.util.Locale.US, "%,.2f", totalAmount);
	
	        String statusColor = "CANCELLED".equalsIgnoreCase(status)
	                ? "#b42318"
	                : "DELIVERED".equalsIgnoreCase(status)
	                    ? "#167447"
	                    : "#b8862b";
	
	        String html = """
	            <div style="background:#f1f4f8;padding:24px 10px;
	                        font-family:Arial,sans-serif;color:#172b4d;">
	              <div style="max-width:600px;margin:auto;background:#fff;
	                          border:1px solid #e1e7ef;border-radius:16px;
	                          overflow:hidden;">
	
	                <div style="background:#102744;padding:30px 20px;
	                            text-align:center;color:#fff;">
	                  <p style="margin:0;color:#f4d17f;font-size:12px;
	                            letter-spacing:4px;font-weight:bold;">
	                    HOTEL MANAGEMENT
	                  </p>
	                  <h1 style="font-size:26px;margin:20px 0 10px;">
	                    %s
	                  </h1>
	                  <div style="display:inline-block;background:#fff;
	                              color:%s;padding:9px 18px;border-radius:24px;
	                              font-size:12px;font-weight:bold;">
	                    %s
	                  </div>
	                </div>
	
	                <div style="padding:28px 22px;">
	                  <p style="font-size:15px;line-height:1.8;color:#526176;">
	                    Dear Customer,<br/><br/>
	                    %s
	                  </p>
	
	                  <div style="border:1px solid #e2e8f0;border-radius:12px;
	                              overflow:hidden;margin:24px 0;">
	                    <div style="background:#f7f9fc;padding:18px;
	                                font-size:12px;letter-spacing:2px;
	                                font-weight:bold;color:#75839a;">
	                      ORDER SUMMARY
	                    </div>
	                    <div style="padding:18px;">
	                      <table role="presentation"
	                             style="width:100%%;border-collapse:collapse;
	                                    font-size:14px;">
	                        <tr>
	                          <td style="padding:12px 0;color:#738096;">
	                            Order ID
	                          </td>
	                          <td style="padding:12px 0;text-align:right;
	                                     font-weight:bold;">#%s</td>
	                        </tr>
	                        <tr>
	                          <td style="padding:12px 0;border-top:1px solid #edf0f5;
	                                     color:#738096;">Item</td>
	                          <td style="padding:12px 0;text-align:right;
	                                     border-top:1px solid #edf0f5;
	                                     font-weight:bold;">%s</td>
	                        </tr>
	                        <tr>
	                          <td style="padding:12px 0;border-top:1px solid #edf0f5;
	                                     color:#738096;">Quantity</td>
	                          <td style="padding:12px 0;text-align:right;
	                                     border-top:1px solid #edf0f5;">%s</td>
	                        </tr>
	                      </table>
	
	                      <div style="background:#102744;padding:18px;
	                                  border-radius:9px;margin-top:14px;">
	                        <table role="presentation"
	                               style="width:100%%;border-collapse:collapse;">
	                          <tr>
	                            <td style="color:#fff;font-size:13px;">
	                              TOTAL AMOUNT
	                            </td>
	                            <td style="color:#f4d17f;text-align:right;
	                                       font-weight:bold;font-size:22px;">
	                              &#8377;%s
	                            </td>
	                          </tr>
	                        </table>
	                      </div>
	                    </div>
	                  </div>
	
	                  <div style="background:#f7f9fc;border-radius:10px;
	                              padding:20px;margin-top:24px;">
	                    <h3 style="margin:0 0 12px;color:#102744;">
	                      Need assistance?
	                    </h3>
	                    <p style="font-size:14px;line-height:1.9;color:#526176;">
	                      Phone:
	                      <a href="tel:9767240179"
	                         style="color:#102744;">9767240179</a>
	                      <br/>
	                      Email:
	                      <a href="mailto:prathameshkadam0705@gmail.com"
	                         style="color:#102744;">
	                        prathameshkadam0705@gmail.com
	                      </a>
	                    </p>
	                  </div>
	                </div>
	
	                <div style="background:#102744;text-align:center;
	                            padding:24px 15px;color:#dbe5f1;">
	                  <p style="margin:0;color:#f4d17f;font-size:18px;">
	                    Thank you for choosing us!
	                  </p>
	                  <p style="font-size:12px;margin:12px 0 0;">
	                    Hotel Management Team
	                  </p>
	                </div>
	              </div>
	            </div>
	            """.formatted(
	                safeHeading,
	                statusColor,
	                safeStatus,
	                safeMessage,
	                orderId,
	                safeItemName,
	                quantity,
	                price
	            );
	
	        helper.setFrom(fromEmail);
	        helper.setTo(customerEmail);
	        helper.setSubject(subject);
	        helper.setText(html, true);
	
	        mailSender.send(mimeMessage);
	
	        System.out.println(
	                "Status email sent to " + customerEmail
	                + " for order " + orderId + ": " + status);
	
	    } catch (Exception e) {
	        throw new IllegalStateException(
	                "Failed to send order status email", e);
	    }
	}


}
