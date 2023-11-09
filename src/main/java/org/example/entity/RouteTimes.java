package org.example.entity;

import java.util.List;

public class RouteTimes {
    //todo найти минимальное и максимальное время между агентскими точками
    public static final int[] TIME_ROUTE = {25, 70}; //время на дорогу минимальное и максимальное
    private int[][] disOfficeToLowTask, disOfficeToMediumTask, disOfficeToHighTask; // первая ячейка номер офиса
    private int[][] disMediumToMediumTask, disLowToLowTask, disLowToMediumTask, disLowToHighTask, disMediumToHighTask, disMediumToLowTask,
            disHighToMediumTask, disHighToLowTask;

    private List<Integer> pointsWithTaskLowPriority, pointsWithTaskMediumPriority, pointsWithTaskHighPriority;

    private int[] pointsOffice;

    public RouteTimes(int[] pointsOffice, List<Integer> pointsWithTaskLowPriority, List<Integer> pointsWithTaskMediumPriority, List<Integer> pointsWithTaskHighPriority) {
        this.pointsOffice = pointsOffice;
        this.pointsWithTaskLowPriority = pointsWithTaskLowPriority;
        this.pointsWithTaskMediumPriority = pointsWithTaskMediumPriority;
        this.pointsWithTaskHighPriority = pointsWithTaskHighPriority;
        init();
    }

    private void init() {
        int sizeOffice = pointsOffice.length;
        int sizeTasksHighPriority = pointsWithTaskHighPriority.size();
        int sizeTasksMediumPriority = pointsWithTaskMediumPriority.size();
        int sizeTasksLowPriority = pointsWithTaskLowPriority.size();

        if (sizeTasksLowPriority > 0) {
            disOfficeToLowTask = new int[sizeOffice][sizeTasksLowPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice[i], pointsWithTaskLowPriority, disOfficeToLowTask[i]);
            }

            disLowToLowTask = new int[sizeTasksLowPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskLowPriority, disLowToLowTask);
        }

        if (sizeTasksMediumPriority > 0) {
            disOfficeToMediumTask = new int[sizeOffice][sizeTasksMediumPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice[i], pointsWithTaskLowPriority, disOfficeToMediumTask[i]);
            }

            disMediumToMediumTask = new int[sizeTasksMediumPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskMediumPriority, disMediumToMediumTask);
        }

        if (sizeTasksHighPriority > 0) {
            disOfficeToHighTask = new int[sizeOffice][sizeTasksHighPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice[i], pointsWithTaskHighPriority, disOfficeToHighTask[i]);
            }
        }

        if (sizeTasksLowPriority > 0 && sizeTasksMediumPriority > 0) {
            disLowToMediumTask = new int[sizeTasksLowPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskMediumPriority, disLowToMediumTask);
            disMediumToLowTask = new int[sizeTasksMediumPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskLowPriority, disMediumToLowTask);
        }

        if (sizeTasksLowPriority > 0 && sizeTasksHighPriority > 0) {
            disLowToHighTask = new int[sizeTasksLowPriority][sizeTasksHighPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskHighPriority, disLowToHighTask);
            disHighToLowTask = new int[sizeTasksHighPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskHighPriority, pointsWithTaskLowPriority, disHighToLowTask);
        }

        if (sizeTasksMediumPriority > 0 && sizeTasksHighPriority > 0) {
            disMediumToHighTask = new int[sizeTasksMediumPriority][sizeTasksHighPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskHighPriority, disMediumToHighTask);
            disHighToMediumTask = new int[sizeTasksHighPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskHighPriority, pointsWithTaskMediumPriority, disHighToMediumTask);
        }
    }

    private void getRouteTimesOfficeToPointFromDB(int office, List<Integer> points1, int[] disOfficeToPoints) {
        for (int i = 0; i < points1.size(); i++) {
            //todo disOfficeToPoints[i] = получить точку офиса из БД, получить массив точек points1, измерить расстояние с каждой
        }
    }

    private void getRouteTimesPointToPointFromDB(List<Integer> points1, List<Integer> points2, int[][] disPointToPoint) {
        for (int i = 0; i < points1.size(); i++) {
            for (int j = 0; j < points2.size(); j++) {
                if (i == j) {
                    disPointToPoint[i][j] = 0;
                } else {
                    //todo  disPointsToPoints[i][j] = получить каждую точку points1 and points2 массива из БД и измерить между ними расстояние
                }
            }
        }
    }

    public int[][] getDisOfficeToLowTask() {
        return disOfficeToLowTask;
    }

    public int[][] getDisOfficeToMediumTask() {
        return disOfficeToMediumTask;
    }

    public int[][] getDisOfficeToHighTask() {
        return disOfficeToHighTask;
    }

    public int[][] getDisMediumToMediumTask() {
        return disMediumToMediumTask;
    }

    public int[][] getDisLowToLowTask() {
        return disLowToLowTask;
    }

    public int[][] getDisLowToMediumTask() {
        return disLowToMediumTask;
    }

    public int[][] getDisLowToHighTask() {
        return disLowToHighTask;
    }

    public int[][] getDisMediumToHighTask() {
        return disMediumToHighTask;
    }

    public int[][] getDisMediumToLowTask() {
        return disMediumToLowTask;
    }

    public int[][] getDisHighToMediumTask() {
        return disHighToMediumTask;
    }

    public int[][] getDisHighToLowTask() {
        return disHighToLowTask;
    }

    public List<Integer> getPointsWithTaskLowPriority() {
        return pointsWithTaskLowPriority;
    }

    public List<Integer> getPointsWithTaskMediumPriority() {
        return pointsWithTaskMediumPriority;
    }

    public List<Integer> getPointsWithTaskHighPriority() {
        return pointsWithTaskHighPriority;
    }

    public int[] getPointsOffice() {
        return pointsOffice;
    }
}
