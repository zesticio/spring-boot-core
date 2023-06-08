/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.core.entity;

import java.io.Serializable;
import java.util.List;
import io.zestic.core.exception.ApplicationException;

public interface EntityService<K extends Serializable & Comparable<K>, E extends Model<K, ?>> extends Transactional {

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
