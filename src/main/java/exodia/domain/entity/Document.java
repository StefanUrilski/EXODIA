package exodia.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity {

    private String title;
    private String content;
    private User user;

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
