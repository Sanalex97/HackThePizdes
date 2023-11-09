package org.example;

import org.example.entity.Office;
import org.example.entity.RouteTimes;
import org.example.entity.TasksAgencyPoints;

import java.util.*;

import static org.example.BuildingRoutes.*;
import static org.example.entity.RouteTimes.TIME_ROUTE;
import static org.example.entity.Task.*;

public class ServiceTaskAssigment {
    private static List<Office> officeList = new ArrayList<>();

    public static void main(String[] args) {
        TasksAgencyPoints tasksAgencyPoints = new TasksAgencyPoints();

        Office office1 = new Office(1);
        Office office2 = new Office(2);
        Office office3 = new Office(3);

        officeList.add(office1);
        officeList.add(office2);
        officeList.add(office3);

        //todo реализовать класс офиса
        RouteTimes routeTimes = new RouteTimes(new int[]{office1.getId(), office2.getId(), office3.getId()},
                tasksAgencyPoints.getAgencyPointListLowPriority(),
                tasksAgencyPoints.getAgencyPointListMediumPriority(),
                tasksAgencyPoints.getAgencyPointListHighPriority());

        addDisFromOfficeToPoint(routeTimes, 1, officeList); // дистанция между офисом и задачами различного приоритета в минутах
        addDisFromOfficeToPoint(routeTimes, 2, officeList);
        addDisFromOfficeToPoint(routeTimes, 3, officeList);

        addRouteOffice(routeTimes, tasksAgencyPoints); // распределение маршрутов по офисам

        //todo на свежую голову сделать универсальный перебор
        findDuplicateRoutes(officeList.get(0).getListRoutesSignor(), officeList.get(1).getListRoutesSignor());

        findDuplicateRoutes(officeList.get(0).getListRoutesSignor(), officeList.get(2).getListRoutesSignor());

        findDuplicateRoutes(officeList.get(1).getListRoutesSignor(), officeList.get(2).getListRoutesSignor());

        findDuplicateRoutes(officeList.get(0).getListRoutesMiddle(), officeList.get(1).getListRoutesMiddle());

        findDuplicateRoutes(officeList.get(0).getListRoutesMiddle(), officeList.get(2).getListRoutesMiddle());

        findDuplicateRoutes(officeList.get(1).getListRoutesMiddle(), officeList.get(2).getListRoutesMiddle());

        findDuplicateRoutes(officeList.get(0).getListRoutesJunior(), officeList.get(1).getListRoutesJunior());

        findDuplicateRoutes(officeList.get(0).getListRoutesJunior(), officeList.get(2).getListRoutesJunior());

        findDuplicateRoutes(officeList.get(1).getListRoutesJunior(), officeList.get(2).getListRoutesJunior());

    }

    private static void addRouteOffice(RouteTimes routeTimes, TasksAgencyPoints tasksAgencyPoints) {
        for (int i = 0; i < officeList.size(); i++) {
            Office office = officeList.get(i);
            List<List<Integer>> routesSignor = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToHighTask(), routeTimes.getDisHighToMediumTask(), new int[]{TIME_HIGH_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);
            List<List<Integer>> routesMiddle = getRoutesFromOfficeToThreePoints(office.getListDisFromOfficeToMediumTask(), routeTimes.getDisMediumToMediumTask(), routeTimes.getDisMediumToMediumTask(), new int[]{TIME_MEDIUM_TASK, TIME_MEDIUM_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);
            List<List<Integer>> routesJunior = getRoutesFromOfficeToFourPoints(office.getListDisFromOfficeToLowTask(), routeTimes.getDisLowToLowTask(), routeTimes.getDisLowToLowTask(), routeTimes.getDisLowToLowTask(), new int[]{TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

            //todo добавить проверку есть ли вообще такой сотрудник в офисе и проверить, остались ли задачи нужного уровня
            if (routesSignor == null || routesSignor.isEmpty()) {
                routesSignor = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToMediumTask(), routeTimes.getDisMediumToMediumTask(), new int[]{TIME_MEDIUM_TASK, TIME_HIGH_TASK}, tasksAgencyPoints);

                if (routesSignor == null || routesSignor.isEmpty()) {
                    routesSignor = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToHighTask(), routeTimes.getDisHighToLowTask(), new int[]{TIME_HIGH_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

                    if (routesSignor == null || routesSignor.isEmpty()) {
                        routesSignor = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToLowTask(), routeTimes.getDisLowToHighTask(), new int[]{TIME_LOW_TASK, TIME_HIGH_TASK}, tasksAgencyPoints);
                    }
                }
            }

            if (routesMiddle == null || routesMiddle.isEmpty()) {
                routesMiddle = getRoutesFromOfficeToThreePoints(office.getListDisFromOfficeToMediumTask(), routeTimes.getDisMediumToMediumTask(), routeTimes.getDisLowToMediumTask(), new int[]{TIME_MEDIUM_TASK, TIME_MEDIUM_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

                if (routesMiddle == null || routesMiddle.isEmpty()) {
                    routesMiddle = getRoutesFromOfficeToThreePoints(office.getListDisFromOfficeToMediumTask(), routeTimes.getDisMediumToLowTask(), routeTimes.getDisLowToMediumTask(), new int[]{TIME_MEDIUM_TASK, TIME_LOW_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);

                    if (routesMiddle == null || routesMiddle.isEmpty()) {
                        routesMiddle = getRoutesFromOfficeToThreePoints(office.getListDisFromOfficeToLowTask(), routeTimes.getDisLowToMediumTask(), routeTimes.getDisMediumToMediumTask(), new int[]{TIME_LOW_TASK, TIME_MEDIUM_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);

                        if (routesMiddle == null || routesMiddle.isEmpty()) {
                            routesMiddle = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToMediumTask(), routeTimes.getDisMediumToLowTask(), new int[]{TIME_MEDIUM_TASK, TIME_LOW_TASK}, tasksAgencyPoints);
                        }
                    }
                }
            }
            if (routesJunior == null || routesJunior.isEmpty()) {
                routesJunior = getRoutesFromOfficeToThreePoints(office.getListDisFromOfficeToLowTask(), routeTimes.getDisLowToLowTask(), routeTimes.getDisLowToLowTask(), new int[]{TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

                if (routesJunior == null || routesJunior.isEmpty()) {
                    routesJunior = getRoutesFromOfficeToTwoPoints(office.getListDisFromOfficeToLowTask(), routeTimes.getDisLowToLowTask(), new int[]{TIME_LOW_TASK, TIME_LOW_TASK}, tasksAgencyPoints);
                }
            }

            /****** Проверка на дублирование *****/
            if (routesSignor != null && routesMiddle != null) {
                findDuplicateRoutes(routesSignor, routesMiddle);
            }
            if (routesSignor != null && routesJunior != null) {
                findDuplicateRoutes(routesSignor, routesJunior);
            }
            if (routesMiddle != null && routesJunior != null) {
                findDuplicateRoutes(routesMiddle, routesJunior);
            }

            office.setListRoutesSignor(routesSignor);
            office.setListRoutesMiddle(routesMiddle);
            office.setListRoutesJunior(routesJunior);
        }
    }

    private static void addDisFromOfficeToPoint(RouteTimes routeTimes, int priorityTask, List<Office> officeList) {
        Map<int[], Integer> mapDisOfficeToTask = getDisFromOfficeToPoint(routeTimes, priorityTask);
        Set<int[]> keySetDistance = mapDisOfficeToTask.keySet();
        for (int[] keyDistance : keySetDistance) {
            int office = mapDisOfficeToTask.get(keyDistance);
            switch (priorityTask) {
                case 1 -> officeList.get(office - 1).addListDisFromOfficeToHighTask(keyDistance);
                case 2 -> officeList.get(office - 1).addListDisFromOfficeToMiddleTask(keyDistance);
                case 3 -> officeList.get(office - 1).addListDisFromOfficeToLowTask(keyDistance);
            }
        }
    }

    private static Map<int[], Integer> getDisFromOfficeToPoint(RouteTimes routeTimes, int priorityTask) {
        Map<int[], Integer> mapRoutes = new LinkedHashMap<>();
        switch (priorityTask) {
            case 1 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToHighTask().length; i++) {
                    for (int j = 0; j < routeTimes.getDisOfficeToHighTask()[0].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToHighTask()[j], routeTimes.getDisOfficeToHighTask()[i][j]);
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToMediumTask().length; i++) {
                    for (int j = 0; j < routeTimes.getDisOfficeToMediumTask()[0].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToMediumTask()[j], routeTimes.getDisOfficeToMediumTask()[i][j]);
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToLowTask().length; i++) {
                    for (int j = 0; j < routeTimes.getDisOfficeToLowTask()[0].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToLowTask()[j], routeTimes.getDisOfficeToLowTask()[i][j]);
                    }
                }
            }
            default -> throw new IllegalStateException("Задачи с таким приоритетом нет пока что: " + priorityTask);
        }
        return mapRoutes;
    }

    private static List<List<Integer>> getRoutesFromOfficeToTwoPoints(int[] disOfficeToPointTask,
                                                                      int[][] disPoint1ToPoint2, int[] timeTask, TasksAgencyPoints tasksAgencyPoints) {
        return buildingRouteFromOfficeToPointToPoint(disOfficeToPointTask, disPoint1ToPoint2,
                new int[]{timeTask[0], timeTask[1]}, 0, tasksAgencyPoints);
    }

    private static List<List<Integer>> getRoutesFromOfficeToThreePoints(int[] disOfficeToPointTask,
                                                                        int[][] disPoint1ToPoint2Task, int[][] disPoint2ToPoint3Task, int[] timeTask, TasksAgencyPoints tasksAgencyPoints) {
        List<List<Integer>> routesFromOfficeToPointToPoint = buildingRouteFromOfficeToPointToPoint(disOfficeToPointTask,
                disPoint1ToPoint2Task, new int[]{timeTask[0], timeTask[1]}, timeTask[0] + TIME_ROUTE[0], tasksAgencyPoints);

        if (!routesFromOfficeToPointToPoint.isEmpty()) {
            return buildingRouteFromPointToPoint(routesFromOfficeToPointToPoint, disPoint2ToPoint3Task, timeTask[2], tasksAgencyPoints);
        }
        return null;
    }

    private static List<List<Integer>> getRoutesFromOfficeToFourPoints(int[] disOfficeToPointTask,
                                                                       int[][] disPoint1ToPoint2Task, int[][] disPoint2ToPoint3Task, int[][] disPoint3ToPoint4Task, int[] timeTask, TasksAgencyPoints tasksAgencyPoints) {
        List<List<Integer>> routesFromOfficeToPointToPoint = buildingRouteFromOfficeToPointToPoint(disOfficeToPointTask,
                disPoint1ToPoint2Task, new int[]{timeTask[0], timeTask[1]}, timeTask[0] + TIME_ROUTE[0], tasksAgencyPoints);

        List<List<Integer>> routesFromOfficeToPointToPointToPoint = buildingRouteFromPointToPoint(routesFromOfficeToPointToPoint, disPoint2ToPoint3Task, timeTask[2], tasksAgencyPoints);
        if (!routesFromOfficeToPointToPoint.isEmpty()) {
            return buildingRouteFromPointToPoint(routesFromOfficeToPointToPointToPoint, disPoint3ToPoint4Task, timeTask[3], tasksAgencyPoints);
        }
        return null;
    }
}