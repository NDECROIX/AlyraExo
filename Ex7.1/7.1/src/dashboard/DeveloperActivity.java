
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
    "stars",
    "watchers",
    "commits_last_3_months",
    "commits_last_1_year",
    "lines_added_last_3_months",
    "lines_added_last_1_year",
    "lines_deleted_last_3_months",
    "lines_deleted_last_1_year"
})
public class DeveloperActivity {

    @JsonProperty("stars")
    private Integer stars;
    @JsonProperty("watchers")
    private Integer watchers;
    @JsonProperty("commits_last_3_months")
    private Integer commitsLast3Months;
    @JsonProperty("commits_last_1_year")
    private Integer commitsLast1Year;
    @JsonProperty("lines_added_last_3_months")
    private Integer linesAddedLast3Months;
    @JsonProperty("lines_added_last_1_year")
    private Integer linesAddedLast1Year;
    @JsonProperty("lines_deleted_last_3_months")
    private Integer linesDeletedLast3Months;
    @JsonProperty("lines_deleted_last_1_year")
    private Integer linesDeletedLast1Year;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("stars")
    public Integer getStars() {
        return stars;
    }

    @JsonProperty("stars")
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    @JsonProperty("watchers")
    public Integer getWatchers() {
        return watchers;
    }

    @JsonProperty("watchers")
    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    @JsonProperty("commits_last_3_months")
    public Integer getCommitsLast3Months() {
        return commitsLast3Months;
    }

    @JsonProperty("commits_last_3_months")
    public void setCommitsLast3Months(Integer commitsLast3Months) {
        this.commitsLast3Months = commitsLast3Months;
    }

    @JsonProperty("commits_last_1_year")
    public Integer getCommitsLast1Year() {
        return commitsLast1Year;
    }

    @JsonProperty("commits_last_1_year")
    public void setCommitsLast1Year(Integer commitsLast1Year) {
        this.commitsLast1Year = commitsLast1Year;
    }

    @JsonProperty("lines_added_last_3_months")
    public Integer getLinesAddedLast3Months() {
        return linesAddedLast3Months;
    }

    @JsonProperty("lines_added_last_3_months")
    public void setLinesAddedLast3Months(Integer linesAddedLast3Months) {
        this.linesAddedLast3Months = linesAddedLast3Months;
    }

    @JsonProperty("lines_added_last_1_year")
    public Integer getLinesAddedLast1Year() {
        return linesAddedLast1Year;
    }

    @JsonProperty("lines_added_last_1_year")
    public void setLinesAddedLast1Year(Integer linesAddedLast1Year) {
        this.linesAddedLast1Year = linesAddedLast1Year;
    }

    @JsonProperty("lines_deleted_last_3_months")
    public Integer getLinesDeletedLast3Months() {
        return linesDeletedLast3Months;
    }

    @JsonProperty("lines_deleted_last_3_months")
    public void setLinesDeletedLast3Months(Integer linesDeletedLast3Months) {
        this.linesDeletedLast3Months = linesDeletedLast3Months;
    }

    @JsonProperty("lines_deleted_last_1_year")
    public Integer getLinesDeletedLast1Year() {
        return linesDeletedLast1Year;
    }

    @JsonProperty("lines_deleted_last_1_year")
    public void setLinesDeletedLast1Year(Integer linesDeletedLast1Year) {
        this.linesDeletedLast1Year = linesDeletedLast1Year;
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
