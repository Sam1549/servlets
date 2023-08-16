package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> posts;
    private final AtomicLong idCounter = new AtomicLong(0);

    public PostRepository() {
        posts = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() != 0 && !posts.containsKey(post.getId())) {
            throw new NotFoundException();
        } else {
            if (posts.containsKey(post.getId())) {
                posts.put(post.getId(), post);
            }
            if (post.getId() == 0) {
                post.setId(idCounter.incrementAndGet());
                posts.put(post.getId(), post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Вы ввели не верный ID");
        }
    }
}
