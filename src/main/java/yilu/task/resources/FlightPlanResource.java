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
import yilu.task.utils.TimeDateHelperUtil;
import yilu.task.vo.FlightPlanVO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Api(value="Flight Plan", description="Query flight plans")
public class FlightPlanResource {
    @Autowired
    PlanService planService;

    @RequestMapping(value="/flightplan", method = RequestMethod.GET, produces = "application/json")
    public List<FlightPlanVO> getFlightPlan(Model model) {

        return transformToVO(planService.getFlightPlan());
    }

    @RequestMapping(value="/flightplan/{airport}", method = RequestMethod.GET, produces = "application/json")
    public List<FlightPlanVO> getFlightPlanByAirport(@PathVariable String airport, Model model) {
        return transformToVO(planService.getFlightPlanByAirport(airport));
    }

    private List<FlightPlanVO> transformToVO(List<FlightPlan> planList) {
        return planList.stream().map(plan -> {
            FlightPlanVO vo = new FlightPlanVO();
            vo.setArrival(TimeDateHelperUtil.getStrFromEpochSec(plan.getArrival(), plan.getDestination()));
            vo.setDeparture(TimeDateHelperUtil.getStrFromEpochSec(plan.getDeparture(), plan.getOrigin()));
            vo.setDestination(plan.getDestination());
            vo.setEquipment(plan.getEquipment());
            vo.setOrigin(plan.getOrigin());
            return vo;
        }).collect(Collectors.toList());
    }
}
