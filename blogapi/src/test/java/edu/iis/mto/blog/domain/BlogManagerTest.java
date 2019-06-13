package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository postRepository;

    @MockBean
    LikePostRepository likeRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    private User unconfirmedUser;
    private BlogPost post;

    @Before
    public void setUp() {
        unconfirmedUser = new User();
        unconfirmedUser.setId(1L);
        unconfirmedUser.setAccountStatus(AccountStatus.CONFIRMED);
        unconfirmedUser.setEmail("");

        post = new BlogPost();
        post.setId(0L);
        post.setUser(unconfirmedUser);
        post.setEntry("");
    }

  @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test(expected = DomainError.class)
    public void addLikeToPost_shouldFail_whenUserIsNotConfirmed() {
      Mockito.when(userRepository.findById(unconfirmedUser.getId())).thenReturn(Optional.of(unconfirmedUser));
      Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

      blogService.addLikeToPost(unconfirmedUser.getId(), post.getId());
    }

}
