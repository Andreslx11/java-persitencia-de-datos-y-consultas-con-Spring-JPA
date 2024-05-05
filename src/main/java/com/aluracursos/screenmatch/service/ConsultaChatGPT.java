package com.aluracursos.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obtenerTraduccion(String texto) {
//        String apiKey = System.getenv("OPENAI_API_KEY");
        OpenAiService service = new OpenAiService("");
// maxTokens la cantidad maxima de caracteres que puede tener esa respuesta
        // sin esta opción nunca va tener variaciones en la entra, con esto va tener variaciones
        // va se entrenda la iA y va ser mejores respuestas
        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}

