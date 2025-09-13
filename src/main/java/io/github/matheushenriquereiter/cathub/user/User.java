package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
