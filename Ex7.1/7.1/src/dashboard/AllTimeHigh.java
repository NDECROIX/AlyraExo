
package dashboard;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price",
    "days_since",
    "percent_down"
})
public class AllTimeHigh {

    @JsonProperty("price")
    private String price;
    @JsonProperty("days_since")
    private Integer daysSince;
    @JsonProperty("percent_down")
    private Double percentDown;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("days_since")
    public Integer getDaysSince() {
        return daysSince;
    }

    @JsonProperty("days_since")
    public void setDaysSince(Integer daysSince) {
        this.daysSince = daysSince;
    }

    @JsonProperty("percent_down")
    public Double getPercentDown() {
        return percentDown;
    }

    @JsonProperty("percent_down")
    public void setPercentDown(Double percentDown) {
        this.percentDown = percentDown;
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
