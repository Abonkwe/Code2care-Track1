package com.dgh.patientfeedbacksystem.service.impl;

import com.dgh.patientfeedbacksystem.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioServiceImpl implements TwilioService {

    @Value("${twilio.phone.number}")
    private String fromPhone;

    @Override
    public void sendSms(String toPhone, String message) {
        Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(fromPhone),
                message
        ).create();
    }

    @Override
    public void sendVoiceCall(String toPhone, String message) {
        String twiml = "<Response><Say>" + message + "</Say></Response>";

        Call.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(fromPhone),
                new Twiml(twiml)
        ).create();
    }
}
