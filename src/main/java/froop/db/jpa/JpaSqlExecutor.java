package froop.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JpaSqlExecutor {

  private final EntityManager entityManager;

  public JpaSqlExecutor(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public <T, R> List<R> queryMulti(
      Function<CriteriaBuilder, CriteriaQuery<T>> queryBuilder, Function<T, R> resultMapper) {
    return queryEntities(queryBuilder).stream().map(resultMapper).collect(Collectors.toList());
  }

  private <E> List<E> queryEntities(Function<CriteriaBuilder, CriteriaQuery<E>> function) {
    return execute(manager -> {
      CriteriaQuery<E> query = function.apply(manager.getCriteriaBuilder());
      return manager.createQuery(query).getResultList();
    });
  }

  public <R> Optional<R> querySingle(Function<EntityManager, R> function) {
    R res = execute(function);
    return Optional.ofNullable(res);
  }

  public void update(Consumer<EntityManager> consumer) {
    execute(manager -> {
      consumer.accept(manager);
      return null;
    });
  }

  private <R> R execute(Function<EntityManager, R> function) {
    return function.apply(entityManager);
  }
}
