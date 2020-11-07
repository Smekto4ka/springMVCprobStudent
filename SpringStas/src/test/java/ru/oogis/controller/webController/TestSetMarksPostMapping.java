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
public class TestSetMarksPostMapping {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void setMarks() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/setMarks/5")
                .param("namePredmet", "History")
                .param("marks", "4")
                .param("marks", "5")
                .param("marks", "3")
                .param("marks", "5")
        );
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setMarksNotStudent() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/setMarks/22")
                .param("namePredmet", "History")
                .param("marks", "4")

        );
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setMarksNotPredmet() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/setMarks/2")
                .param("namePredmet", "")
                .param("marks", "4")

        );
        resultActions.andDo(MockMvcResultHandlers.print());
    }

   // неправильно , оценка должна быть от 0 до 5

    @Test
    public void setMarksWorningMarks() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/student/setMarks/2")
                .param("namePredmet", "History")
                .param("marks", "22")

        );
        resultActions.andDo(MockMvcResultHandlers.print());
    }


}
