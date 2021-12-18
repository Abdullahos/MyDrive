package MyDrive.models;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Lob
    @Column(name = "file", columnDefinition="BLOB")
    private byte[] data;

    private String contentType;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public File(User user, String name, byte[] bytes, String contentType) {
        this.user = user;
        this.name = name;
        this.data = bytes;
        this.contentType = contentType;
    }

    public File() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
