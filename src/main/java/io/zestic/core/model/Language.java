package io.zestic.core.model;

import io.zestic.core.entity.Auditable;
import io.zestic.core.entity.Model;

public class Language extends Model<String, Language> implements Auditable {

  private String id;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }
}
