package entities.grid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {
    @JsonProperty("interviewer")
    private String interviewer;
    @JsonProperty("date")
    private String date;
    @JsonProperty("name")
    private String name;
    @JsonProperty("topic")
    private String topic;
    @JsonProperty("comment")
    private String comment;

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
