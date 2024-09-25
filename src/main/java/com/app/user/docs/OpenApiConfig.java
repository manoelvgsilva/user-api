package com.app.user.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Class to configure the OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

  @Override
  public void customise(OpenAPI openApi) {
    Info info = new Info()
        .title("API de usuarios")
        .description("Este projeto apresenta uma API RESTful que gerencia a " +
            "entrada e acesso de usuarios de acordo com o cargo da acesso " +
            "especial ao restante da aplicação"
            + " , possibilitando às pessoas usuárias criar, visualizar, atualizar e excluir"
            + " produtos, promoções, estoque, de forma intuitiva e prática. A" +
            " API " +
            "oferece" +
            " endpoints específicos para"
            + " operações CRUD (Create, Read, Update, Delete) em listas de " +
            "usuarios," +
            " visando"
            + " proporcionar uma experiência consistente e confiável.")
        .version("1.0.0");

    openApi.info(info);
  }
}