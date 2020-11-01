package ru.oogis.controller.webController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TestGetStudentById {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStudentById1() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/student/1"));
        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.view().name("student"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"));


    }

    @Test
    public void getStudentById2() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/student/22"));
        result.andDo(MockMvcResultHandlers.print());

        result.andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("warning"));

    }
}
