package sigma.service;

import com.plivo.api.Plivo;
import com.plivo.api.models.message.MessageCreateResponse;
import qio.annotate.Property;
import qio.annotate.Service;

import java.util.Collections;

@Service
public class SmsService {

    @Property("plivo.api.key")
    String apiKey;

    @Property("plivo.secret.key")
    String secretKey;

    private static final String PLIVO_PHONE = "+18302026537";
    private static final String NOTIFY_PHONE = "+19079878652";

    public boolean validate(String phone){
        try{

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList("+1" + phone), "Okay! Setup complete!")
                    .create();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean send(String phones, String notification){
        try{

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList(phones), notification)
                    .create();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean support(String notification){
        try{

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList(NOTIFY_PHONE), notification)
                    .create();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}