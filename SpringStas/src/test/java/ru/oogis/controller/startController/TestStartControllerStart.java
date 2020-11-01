package ru.oogis.controller.startController;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.oogis.controller.StartController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestStartControllerStart {



    @Autowired
    private StartController controller;

    @Test
    public void testReqestProb() throws  Exception {  // проверка на запуск, создание контроллера

        assertThat(controller).isNotNull();

    }

}
