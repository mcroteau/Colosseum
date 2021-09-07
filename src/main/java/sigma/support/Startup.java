package sigma.support;

import sigma.service.StartupService;
import qio.Qio;
import qio.annotate.Events;
import qio.support.QioEvents;

@Events
public class Startup implements QioEvents {
    @Override
    public void setupComplete(Qio qio) {
        try {
            StartupService startupService = (StartupService) qio.getElement("startupservice");
            startupService.init();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}