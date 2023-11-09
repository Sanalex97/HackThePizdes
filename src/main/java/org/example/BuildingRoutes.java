package org.example;

import org.example.entity.TasksAgencyPoints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.entity.Task.*;

public class BuildingRoutes {
    public static List<List<Integer>> buildingRouteFromOfficeToPointToPoint(int[] disOffice, int[][] disPointToPoint, int[] priorityTask, int desiredRemainingTime, TasksAgencyPoints tasksAgencyPoints) {
        List<Integer> listAgencyPoints1 = switch (priorityTask[0]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask[0]);
        };

        List<Integer> listAgencyPoints2 = switch (priorityTask[1]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask[1]);
        };

        List<List<Integer>> listRoute = new ArrayList<>();
        int maxRestDis = 0;

        for (int i = 0; i < listAgencyPoints1.size(); i++) {
            for (int j = 0; j < listAgencyPoints2.size(); j++) {
                int point1;
                int point2;
                int disPoint1ToPoint2;

                point1 = listAgencyPoints1.get(i);
                point2 = listAgencyPoints2.get(j);
                disPoint1ToPoint2 = disPointToPoint[i][j];

                if (point1 != point2 && i != j) {
                    int restDis = TIME_MAX_TASK - (disOffice[i] + priorityTask[0] + disPoint1ToPoint2 + priorityTask[1]);
                    if (restDis >= desiredRemainingTime && restDis > maxRestDis) {
                        maxRestDis = restDis;

                        List<Integer> list = new ArrayList<>();
                        list.add(restDis);
                        list.add(point1);
                        list.add(point2);

                        listRoute.add(list);
                    }
                }
            }
        }

        listRoute = sortedList(listRoute);

        return listRoute;
    }

    public static List<List<Integer>> buildingRouteFromPointToPoint(List<List<Integer>> listRoute, int[][] disPointToPoint, int priorityTask, TasksAgencyPoints tasksAgencyPoints) {
        List<Integer> listAgencyPoints1 = switch (priorityTask) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask);
        };

        for (int i = 0; i < listRoute.size(); i++) {
            int maxDis = 0;
            int dis = listRoute.get(i).get(0); // оставшееся время у сотрудника в минутах

            int sizeRoute = listRoute.get(i).size();
            int[] pointsRoute = new int[sizeRoute]; //текущий маршрут


            for (int j = 0; j < pointsRoute.length; j++) {
                pointsRoute[j] = listRoute.get(i).get(j); //получение точек маршрута
            }

            listRoute.get(i).add(-1); // маршрут до следующей точки равен бесконечности

            int index = listAgencyPoints1.indexOf(pointsRoute[sizeRoute - 1]);

            for (int j = 0; j < disPointToPoint.length; j++) {
                if (pointsRoute[sizeRoute - 1] != -1 && index != -1) {
                    if (listAgencyPoints1.get(j) == pointsRoute[1] || listAgencyPoints1.get(j) == pointsRoute[2] || listAgencyPoints1.get(j) == pointsRoute[sizeRoute - 1]) {
                        continue;
                    }

                    int restDis = dis - (disPointToPoint[index][j] + priorityTask); //остаток рабочего времени

                    if (restDis >= maxDis) {
                        maxDis = restDis;
                        listRoute.get(i).set(0, restDis); // обновили остаток времени
                        listRoute.get(i).set(sizeRoute, listAgencyPoints1.get(j)); // в конец массива добавили точку
                    }
                }
            }
        }

        removeEmptyRoutes(listRoute);
        listRoute = sortedList(listRoute);

        return listRoute;
    }

    public static void findDuplicateRoutes(List<List<Integer>> listRoutesOffice1, List<List<Integer>> listRoutesOffice2) {
        List<List<Integer>> maxListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() > 0 ? listRoutesOffice1 : listRoutesOffice2;
        List<List<Integer>> minListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() < 0 ? listRoutesOffice1 : listRoutesOffice2;

        for (int i = 0; i < maxListRoutes.size(); i++) {
            for (int point : maxListRoutes.get(i).subList(1, maxListRoutes.get(i).size())) {
                for (int j = 0; j < minListRoutes.size(); j++) {
                    if (minListRoutes.get(j).subList(1, minListRoutes.get(j).size()).contains(point)) {
                        if (minListRoutes.get(j).get(0) - maxListRoutes.get(i).get(0) > 0 && maxListRoutes.size() > 1) {
                            maxListRoutes.get(i).set(maxListRoutes.get(i).size() - 1, -1);
                        } else {
                            minListRoutes.get(j).set(minListRoutes.get(j).size() - 1, -1);
                        }
                    }
                }
            }
        }

        removeEmptyRoutes(listRoutesOffice1);
        removeEmptyRoutes(listRoutesOffice2);
    }

    private static List<List<Integer>> sortedList(List<List<Integer>> listRoute) {
        listRoute = listRoute.stream().sorted((o1, o2) -> {
            for (int i = 0; i < Math.max(o1.size(), o2.size()); i++) {
                int c = o2.get(i).compareTo(o1.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(o1.size(), o2.size());
        }).collect(Collectors.toList());

        return listRoute;
    }

    private static List<List<Integer>> removeEmptyRoutes(List<List<Integer>> listRoute) {
        Iterator<List<Integer>> iterator = listRoute.iterator();
        while (iterator.hasNext()) {
            List<Integer> num = iterator.next();
            if (num.get(num.size() - 2) == -1 || num.get(num.size() - 1) == -1) {
                iterator.remove();
            }
        }
        return listRoute;
    }
}
