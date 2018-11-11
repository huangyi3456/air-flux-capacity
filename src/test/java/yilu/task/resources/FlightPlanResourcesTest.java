package yilu.task.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import yilu.task.entity.FlightPlan;
import yilu.task.service.PlanServiceIml;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightPlanResourcesTest {
    @MockBean
    PlanServiceIml planService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldListAllFlightPlan() {

    }

    @Test
    public void shouldListOnlyMUCPlan() throws Exception {
        FlightPlan flightPlan1 = new FlightPlan();
        flightPlan1.setArrival(11);
        flightPlan1.setDeparture(10);
        flightPlan1.setDestination("TXL");
        flightPlan1.setEquipment("A321");
        flightPlan1.setOrigin("MUC");
        FlightPlan flightPlan2 = new FlightPlan();
        flightPlan2.setArrival(14);
        flightPlan2.setDeparture(13);
        flightPlan2.setDestination("TXL");
        flightPlan2.setEquipment("737");
        flightPlan2.setOrigin("MUC");
        FlightPlan flightPlan3 = new FlightPlan();
        flightPlan3.setArrival(16);
        flightPlan3.setDeparture(14);
        flightPlan3.setDestination("LHR");
        flightPlan3.setEquipment("747-400");
        flightPlan3.setOrigin("MUC");
        List<FlightPlan> MUCPlanList = Arrays.asList(flightPlan1, flightPlan2, flightPlan3);

        Mockito.when(planService.getFlightPlanByAirport(Mockito.anyString())).thenReturn(MUCPlanList);
        mockMvc.perform(get("/flightplan/MUC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].equipment", is("A321")))
                .andExpect(jsonPath("$[1].equipment", is("737")))
                .andExpect(jsonPath("$[2].equipment", is("747-400")));
        Mockito.verify(planService, times(1)).getFlightPlanByAirport(Mockito.anyString());
    }
}
