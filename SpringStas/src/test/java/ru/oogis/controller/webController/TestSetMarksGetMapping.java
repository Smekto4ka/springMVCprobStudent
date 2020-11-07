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
public class TestSetMarksGetMapping {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void setValueGetMaping() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/setMarks/5?numberMarks=5"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setValueGetMapingWrongIdValue() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/setMarks/22?numberMarks=5"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void setValueGetMapingWrongKollValue1() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/setMarks/2?numberMarks=-5"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setValueGetMapingWrongKollValue2() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/setMarks/2?numberMarks="));
        resultActions.andDo(MockMvcResultHandlers.print());
    }


}
