
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
    "y_2050",
    "circulating"
})
public class Supply {

    @JsonProperty("y_2050")
    private Double y2050;
    @JsonProperty("circulating")
    private Integer circulating;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("y_2050")
    public Double getY2050() {
        return y2050;
    }

    @JsonProperty("y_2050")
    public void setY2050(Double y2050) {
        this.y2050 = y2050;
    }

    @JsonProperty("circulating")
    public Integer getCirculating() {
        return circulating;
    }

    @JsonProperty("circulating")
    public void setCirculating(Integer circulating) {
        this.circulating = circulating;
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
