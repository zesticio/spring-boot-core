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

package in.zestic.core.entity;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.SerializationUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class Entity<K extends Serializable & Comparable<K>, E extends Entity<K, ?>> implements Auditable, Comparable<E> {

  @JsonProperty("version")
  private Long version;
  @JsonProperty("created_at")
  private Date createdAt;
  @JsonProperty("modified_at")
  private Date modifiedAt;

  public Entity() {
  }

  public abstract K getId();

  public abstract void setId(K id);

  @JsonIgnore
  public String toJson() {
    String json = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      json = mapper.writeValueAsString(this);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  public byte[] toBytes() {
    return SerializationUtils.serialize(this);
  }

  protected boolean isValidUri(String url) {
    String[] schemes = {"http", "https"};
    UrlValidator validator = new UrlValidator();
    return validator.isValid(url);
  }

  public int compareTo(E o) {
    if (this == o) {
      return 0;
    }
    return this.getId().compareTo(o.getId());
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Entity<K, E> entity = (Entity<K, E>) object;
    K id = getId();

    if (id == null) {
      return false;
    }

    return id.equals(entity.getId());
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("entity.");
    builder.append("<");
    builder.append(getId());
    builder.append("-");
    builder.append(super.toString());
    builder.append(">");

    return builder.toString();
  }
}
