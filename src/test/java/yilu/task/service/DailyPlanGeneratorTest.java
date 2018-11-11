package yilu.task.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import yilu.task.AirFluxCapacityService;
import yilu.task.dao.AirplaneRepository;
import yilu.task.dao.ScheduleRepository;
import yilu.task.entity.Airplane;
import yilu.task.entity.FlightPlan;
import yilu.task.entity.OperationsPlan;
import yilu.task.entity.Schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = AirFluxCapacityService.class)
public class DailyPlanGeneratorTest {
    @Mock
    AirplaneRepository airplaneRepository;
    @Mock
    ScheduleRepository scheduleRepository;
    @Spy
    AirportControllerIml airportController;

    @InjectMocks
    DailyPlanGenerator generator;

    private Set<Airplane> airplaneList;

    @Before
    public void before() {
        airplaneList = new HashSet<>();
        Airplane airplane1 = new Airplane();
        airplane1.setBase("TXL");
        airplane1.setRegistration("FL-0001");
        airplane1.setType("737");
        Airplane airplane2 = new Airplane();
        airplane2.setBase("MUC");
        airplane2.setRegistration("FL-0002");
        airplane2.setType("A321");
        Airplane airplane3 = new Airplane();
        airplane3.setBase("LHR");
        airplane3.setRegistration("FL-0003");
        airplane3.setType("747-400");
        Airplane airplane4 = new Airplane();
        airplane4.setBase("HAM");
        airplane4.setRegistration("FL-0004");
        airplane4.setType("A320");
        airplaneList.add(airplane1);
        airplaneList.add(airplane2);
        airplaneList.add(airplane3);
        airplaneList.add(airplane4);
    }

    /**
     * An case that
     * 1, plane A from berlin to munich at 9
     * 2, plane B  from munich to berlin at 10
     * 2, after they arrive, both return home
     */
    @Test
    public void testGeneratePlan() {
        Schedule schedule1 = new Schedule();
        schedule1.setOrigin("TXL");
        schedule1.setDestination("MUC");
        schedule1.setDepatureTime(9);
        schedule1.setFlightTime(1);
        Schedule schedule2 = new Schedule();
        schedule2.setOrigin("MUC");
        schedule2.setDestination("TXL");
        schedule2.setDepatureTime(10);
        schedule2.setFlightTime(1);
        Schedule schedule3 = new Schedule();
        schedule3.setOrigin("TXL");
        schedule3.setDestination("MUC");
        schedule3.setDepatureTime(15);
        schedule3.setFlightTime(1);
        Schedule schedule4 = new Schedule();
        schedule4.setOrigin("MUC");
        schedule4.setDestination("TXL");
        schedule4.setDepatureTime(13);
        schedule4.setFlightTime(1);
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>(){{
            add(schedule1);
            add(schedule2);
            add(schedule3);
            add(schedule4);
        }};

        Mockito.when(airplaneRepository.getAirplane()).thenReturn(airplaneList);
        Mockito.when(scheduleRepository.getSchedule()).thenReturn(scheduleList);
        Mockito.when(airportController.initAirport(Mockito.anySet())).thenCallRealMethod();

        generator.generatePlan();
        Assert.assertEquals(2, generator.getOperationsPlanMap().keySet().size());
        //List<OperationsPlan> firstPlanJourney = new ArrayList<>();
        OperationsPlan to = new OperationsPlan();
        to.setDeparture(9);
        to.setDestination("MUC");
        to.setOrigin("TXL");
        OperationsPlan back = new OperationsPlan();
        back.setDeparture(13);
        back.setDestination("TXL");
        back.setOrigin("MUC");
        List<OperationsPlan> firstPlanJourney = generator.getOperationsPlanMap().get("FL-0001");
        Assert.assertEquals(2, firstPlanJourney.size());
        Assert.assertTrue(to.equals(firstPlanJourney.get(0)));
        Assert.assertTrue(back.equals(firstPlanJourney.get(1)));
    }

    /**
     * An case that there are 3 airports. Verify that it correctly selects an airplane
     * with respect to airplane's base.
     */
    @Test
    public void testGeneratePlan2() {
        Schedule schedule1 = new Schedule();
        schedule1.setOrigin("TXL");
        schedule1.setDestination("MUC");
        schedule1.setDepatureTime(9);
        schedule1.setFlightTime(1);
        Schedule schedule2 = new Schedule();
        schedule2.setOrigin("MUC");
        schedule2.setDestination("TXL");
        schedule2.setDepatureTime(10);
        schedule2.setFlightTime(1);
        Schedule schedule3 = new Schedule();
        schedule3.setOrigin("TXL");
        schedule3.setDestination("MUC");
        schedule3.setDepatureTime(15);
        schedule3.setFlightTime(1);
        Schedule schedule4 = new Schedule();
        schedule4.setOrigin("MUC");
        schedule4.setDestination("TXL");
        schedule4.setDepatureTime(13);
        schedule4.setFlightTime(1);
        Schedule schedule5 = new Schedule();
        schedule5.setOrigin("LHR");
        schedule5.setDestination("MUC");
        schedule5.setDepatureTime(9);
        schedule5.setFlightTime(2);
        Schedule schedule6 = new Schedule();
        schedule6.setOrigin("MUC");
        schedule6.setDestination("LHR");
        schedule6.setDepatureTime(14);
        schedule6.setFlightTime(2);
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>(){{
            add(schedule1);
            add(schedule2);
            add(schedule3);
            add(schedule4);
            add(schedule5);
            add(schedule6);
        }};

        Mockito.when(airplaneRepository.getAirplane()).thenReturn(airplaneList);
        Mockito.when(scheduleRepository.getSchedule()).thenReturn(scheduleList);
        Mockito.when(airportController.initAirport(Mockito.anySet())).thenCallRealMethod();

        generator.generatePlan();
        Assert.assertEquals(3, generator.getFlightPlanMap().keySet().size());
        List<FlightPlan> MUCPlan = generator.getFlightPlanMap().get("MUC");
        Assert.assertEquals(3, MUCPlan.size());
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
        Assert.assertTrue(flightPlan1.equals(MUCPlan.get(0)));
        Assert.assertTrue(flightPlan2.equals(MUCPlan.get(1)));
        Assert.assertTrue(flightPlan3.equals(MUCPlan.get(2)));
    }
}
