
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
    "percent_change_last_1_week",
    "percent_change_last_1_month",
    "percent_change_last_3_months",
    "percent_change_last_1_year"
})
public class RoiData {

    @JsonProperty("percent_change_last_1_week")
    private Double percentChangeLast1Week;
    @JsonProperty("percent_change_last_1_month")
    private Double percentChangeLast1Month;
    @JsonProperty("percent_change_last_3_months")
    private Double percentChangeLast3Months;
    @JsonProperty("percent_change_last_1_year")
    private Double percentChangeLast1Year;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("percent_change_last_1_week")
    public Double getPercentChangeLast1Week() {
        return percentChangeLast1Week;
    }

    @JsonProperty("percent_change_last_1_week")
    public void setPercentChangeLast1Week(Double percentChangeLast1Week) {
        this.percentChangeLast1Week = percentChangeLast1Week;
    }

    @JsonProperty("percent_change_last_1_month")
    public Double getPercentChangeLast1Month() {
        return percentChangeLast1Month;
    }

    @JsonProperty("percent_change_last_1_month")
    public void setPercentChangeLast1Month(Double percentChangeLast1Month) {
        this.percentChangeLast1Month = percentChangeLast1Month;
    }

    @JsonProperty("percent_change_last_3_months")
    public Double getPercentChangeLast3Months() {
        return percentChangeLast3Months;
    }

    @JsonProperty("percent_change_last_3_months")
    public void setPercentChangeLast3Months(Double percentChangeLast3Months) {
        this.percentChangeLast3Months = percentChangeLast3Months;
    }

    @JsonProperty("percent_change_last_1_year")
    public Double getPercentChangeLast1Year() {
        return percentChangeLast1Year;
    }

    @JsonProperty("percent_change_last_1_year")
    public void setPercentChangeLast1Year(Double percentChangeLast1Year) {
        this.percentChangeLast1Year = percentChangeLast1Year;
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
