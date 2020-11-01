package ru.oogis.controller.webController;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPostStudent {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postStudent() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/new")
                .param("firstName", "Maxim")
                .param("lastName", "Maximov")
                .param("years", "5"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void postStudentMisnomer() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/new")
                .param("firstName", "a")
                .param("lastName", "")
                .param("years", "2"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void postStudentWrongValueYars1() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/new")
                .param("firstName", "Maxim")
                .param("lastName", "Maximov")
                .param("years", "55555"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void postStudentWrongValueYars2() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/new")
                .param("firstName", "Maxim")
                .param("lastName", "Maximov")
                .param("years", "-1"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    /**
     * что делать с NumberFormatException
     *
     * @throws Exception
     */
    @Test
    public void postStudentWrongValueYars3() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/new")
                .param("firstName", "Maxim")
                .param("lastName", "Maximov")
                .param("years", ""));
        resultActions.andDo(MockMvcResultHandlers.print());

    }
}
