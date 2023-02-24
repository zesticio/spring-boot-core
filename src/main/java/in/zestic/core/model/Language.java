package in.zestic.core.model;

import in.zestic.core.entity.Auditable;
import in.zestic.core.entity.Entity;

public class Language extends Entity<String, Language> implements Auditable {

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
