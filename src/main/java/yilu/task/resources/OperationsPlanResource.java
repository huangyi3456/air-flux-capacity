package yilu.task.resources;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yilu.task.entity.OperationsPlan;
import yilu.task.service.PlanServiceIml;
import yilu.task.utils.TimeDateHelperUtil;
import yilu.task.vo.OperationsPlanVO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Api(value="Operations Plan", description="Query operations plans")
public class OperationsPlanResource {
    @Autowired
    PlanServiceIml planService;

    @RequestMapping(value="/operationsplan/{airplaneRegistration},", method = RequestMethod.GET, produces = "application/json")
    public List<OperationsPlanVO> getOperationsPlanByPlane(@PathVariable String airplaneRegistration, Model model) {
        return transformToVO(planService.getOperationsPlanByFlight(airplaneRegistration));
    }

    private List<OperationsPlanVO> transformToVO(List<OperationsPlan> planList) {
        return planList.stream().map(plan -> {
            OperationsPlanVO vo = new OperationsPlanVO();
            vo.setDeparture(TimeDateHelperUtil.getStrFromEpochSec(plan.getDeparture(), plan.getOrigin()));
            vo.setDestination(plan.getDestination());
            vo.setOrigin(plan.getDestination());
            return vo;
        }).collect(Collectors.toList());
    }
}
