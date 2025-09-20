package io.github.matheushenriquereiter.cathub.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByUserId(Integer userId, Pageable pageable);
}
