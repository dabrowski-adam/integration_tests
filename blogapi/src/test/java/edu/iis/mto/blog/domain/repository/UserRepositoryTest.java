package edu.iis.mto.blog.domain.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {
        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {
        User persistedUser = repository.save(user);

        Assert.assertThat(persistedUser.getId(), Matchers.notNullValue());
    }

  @Test
  public void shouldFindUserByFirstName() {
    User persistedUser = entityManager.persist(user);
    String firstName = persistedUser.getFirstName().toLowerCase();
    String lastName = "abc";
    String email = "abc";
    List<User> foundUsers = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(firstName, lastName, email);
    Assert.assertThat(foundUsers, Matchers.contains(persistedUser));
  }

  @Test
  public void shouldFindUserByLastName() {
    User persistedUser = entityManager.persist(user);
    String firstName = "abc";
    String lastName = persistedUser.getLastName().toUpperCase();
    String email = "abc";
    List<User> foundUsers = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(firstName, lastName, email);
    Assert.assertThat(foundUsers, Matchers.contains(persistedUser));
  }

  @Test
  public void shouldFindUserByEmail() {
    User persistedUser = entityManager.persist(user);
    String firstName = "abc";
    String lastName = "abc";
    String email = persistedUser.getEmail().split("@")[0];
    List<User> foundUsers = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(firstName, lastName, email);
    Assert.assertThat(foundUsers, Matchers.contains(persistedUser));
  }
}
