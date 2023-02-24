package in.zestic.core.entity;

import java.io.Serializable;
import java.util.List;
import in.zestic.core.exception.ApplicationException;

public interface EntityService<K extends Serializable & Comparable<K>, E extends Entity<K, ?>> extends Transactional {

  /**
   * @param entity
   * @throws ApplicationException
   */
  E create(E entity) throws ApplicationException;

  /**
   * @param entities
   * @throws ApplicationException
   */
  List<E> save(Iterable<E> entities) throws ApplicationException;

  /**
   * @param entity
   * @throws ApplicationException
   */
  E update(E entity) throws ApplicationException;

  /**
   * @param entity
   * @throws ApplicationException
   */
  void delete(E entity) throws ApplicationException;

  void delete(K id) throws ApplicationException;

  /**
   * @param id
   * @return
   */
  E findById(K id);

  /**
   * @return
   */
  List<E> list();

  /**
   * @return
   */
  Long count();

  /**
   *
   */
  void flush();
}
