package exodia.domain.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ScheduleCreateBindingModel {

    private String title;
    private String content;

    @NotNull
    @NotBlank
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @NotBlank
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
