package ru.oogis.controller.startController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class TestDefaultReqest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDefaultReqest() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/"));
        //   result.andDo(print());  выведет результат запроса

        result.andExpect(MockMvcResultMatchers.view().name("hello"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("enum"));
    }

}
