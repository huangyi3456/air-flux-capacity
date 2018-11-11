package yilu.task.resources;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yilu.task.entity.FlightPlan;
import yilu.task.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/")
@Api(value="Flight Plan", description="Query flight plans")
public class FlightPlanResource {
    @Autowired
    PlanService planService;

    @RequestMapping(value="/flightplan", method = RequestMethod.GET, produces = "application/json")
    public List<FlightPlan> getFlightPlan(Model model) {
        return planService.getFlightPlan();
    }

    @RequestMapping(value="/flightplan/{airport}", method = RequestMethod.GET, produces = "application/json")
    public List<FlightPlan> getFlightPlanByAirport(@PathVariable String airport, Model model) {
        return planService.getFlightPlanByAirport(airport);
    }
}
