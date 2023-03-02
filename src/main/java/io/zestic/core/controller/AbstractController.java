package io.zestic.core.controller;

import javax.servlet.http.HttpServletRequest;
import io.zestic.core.model.Language;

public class AbstractController {

  public final static String LANGUAGE = "LANGUAGE";

  protected Language getLanguage(HttpServletRequest request) {
    return (Language) request.getAttribute(LANGUAGE);
  }
}
