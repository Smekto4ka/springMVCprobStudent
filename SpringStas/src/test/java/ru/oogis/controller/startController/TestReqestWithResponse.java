package ru.oogis.controller.startController;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc  // тест без запуска сервера
public class TestReqestWithResponse {
     @Autowired
    private MockMvc mockMvc;
//Spring обрабатывает входящий запрос HTTP и передает его контроллеру. Таким образом, почти полный стек используется, и ваш код будет называться точно так же, как если бы он был обработки реального запроса HTTP, но без затрат на запуск сервера.
     @Test
    public void shouldReturnDefaultMessage() throws  Exception{
         this.mockMvc.perform(get("/hello")).andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(content()
                         .string(containsString("Hello world")));// не учитывается? или это выдается за ответ?
     }
}

  //  В этом тесте запущен полный контекст приложения Spring, но без сервера. Мы можем сузить тесты только до веб-слоя, используя , как показывает следующий листинг (из) показывает:@WebMvcTest