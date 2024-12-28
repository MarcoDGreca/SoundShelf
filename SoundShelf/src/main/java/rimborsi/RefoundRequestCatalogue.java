package rimborsi;

import java.util.ArrayList;
import java.util.List;

public class RefoundRequestCatalogue {

    private List<RefoundRequest> refundRequests;

    public RefoundRequestCatalogue() {
        this.refundRequests = new ArrayList<>();
    }

    public void addRefundRequest(RefoundRequest refundRequest) {
        refundRequests.add(refundRequest);
    }

    public void removeRefundRequest(RefoundRequest refundRequest) {
        refundRequests.remove(refundRequest);
    }

    public List<RefoundRequest> getRefundRequests() {
        return refundRequests;
    }
}
