package supporto;

import java.util.ArrayList;
import java.util.List;

public class SupportRequestCatalogue {

    private List<SupportRequest> supportRequests;

    public SupportRequestCatalogue() {
        this.supportRequests = new ArrayList<>();
    }

    public void addSupportRequest(SupportRequest supportRequest) {
        supportRequests.add(supportRequest);
    }

    public void removeSupportRequest(SupportRequest supportRequest) {
        supportRequests.remove(supportRequest);
    }

    public List<SupportRequest> getSupportRequests() {
        return supportRequests;
    }
}
