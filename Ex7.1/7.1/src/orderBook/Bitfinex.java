package orderBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bids",
        "asks"
})
public class Bitfinex {

    @JsonProperty("bids")
    private List<Bid> bids = null;
    @JsonProperty("asks")
    private List<Ask> asks = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bids")
    public List<Bid> getBids() {
        return bids;
    }

    @JsonProperty("bids")
    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    @JsonProperty("asks")
    public List<Ask> getAsks() {
        return asks;
    }

    @JsonProperty("asks")
    public void setAsks(List<Ask> asks) {
        this.asks = asks;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}