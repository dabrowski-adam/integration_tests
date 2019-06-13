package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost post;
    private LikePost like;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        entityManager.persist(user);

        post = new BlogPost();
        post.setUser(user);
        post.setEntry("Lorem ipsum");

        entityManager.persist(post);

        like = new LikePost();
        like.setUser(user);
        like.setPost(post);
    }

    @Test
    public void likedPostList_containsSavedLike() {
        repository.save(like);
        List<LikePost> likes = repository.findAll();
        assertThat(likes, hasSize(1));
        assertThat(likes, contains(like));
    }

    @Test
    public void likedPostList_doesntContainRemovedLike() {
        repository.save(like);
        repository.delete(like);
        List<LikePost> likes = repository.findAll();
        assertThat(likes, hasSize(0));
        assertThat(likes, not(contains(like)));
    }

    @Test
    public void shouldFindLikeByUserAndPost() {
        repository.save(like);
        LikePost foundLike = repository.findByUserAndPost(user, post).orElse(null);
        assertThat(foundLike, is(like));
    }
}
