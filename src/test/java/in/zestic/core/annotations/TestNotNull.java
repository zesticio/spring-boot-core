package io.zestic.core.annotations;

import io.zestic.core.annotation.NotNull;

public class TestNotNull {

  @NotNull(message = "Value cannot be null")
  private String name = null;

  public static void main(String[] args) {
  }
}
