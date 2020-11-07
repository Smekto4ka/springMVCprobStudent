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
public class TestFilterCriterionMinMax {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void filter() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/filter/minmax")
                .param("minimumBorder", "2.25")
                .param("maximumBorder", "3.25")
                .param("namePredmet", "history"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }
      @Test
    public void filter2() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/filter/minmax")
                .param("minimumBorder", "  .25")
                .param("maximumBorder", "    .35")
                .param("namePredmet", "history"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }
   @Test
    public void filterWrongPredmet() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/filter/minmax")
                .param("minimumBorder", "5.25")
                .param("maximumBorder", "7.35")
                .param("namePredmet", "aaaaa"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }
  @Test
    public void filterWrongValue() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/filter/minmax")
                .param("minimumBorder", "a")
                .param("maximumBorder", "")
                .param("namePredmet", "history"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

}
