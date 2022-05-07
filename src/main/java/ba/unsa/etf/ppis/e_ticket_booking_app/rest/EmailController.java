package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.service.DefaultEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    DefaultEmailService emailService;

    @GetMapping(value = "/email")
    public @ResponseBody ResponseEntity sendSimpleEmail() {

        try {
            emailService.sendSimpleEmail("neiranovalicc20@gmail.com", "Welcome", "This is a welcome email for your!!");
        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

}


