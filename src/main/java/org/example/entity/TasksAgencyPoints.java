package org.example.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TasksAgencyPoints {
    private List<AgencyPoint> agencyPointList = new ArrayList<>();
    private List<AgencyPoint> agencyPointListHighPriority = new ArrayList<>();
    private List<AgencyPoint> agencyPointListMediumPriority = new ArrayList<>();
    private List<AgencyPoint> agencyPointListLowPriority = new ArrayList<>();

    public TasksAgencyPoints() {
        init();
    }

    private void init() {

        //todo получить из базы данных текущее состояние по каждой точке AgencyPoint agencyPoint1 = new AgencyPoint(1, "вчера", false, 0, 0, 0);
        //todo добавить все точки в agencyPointList
        getTasksWithPriority(agencyPointList);
    }

    private List<List<AgencyPoint>> getTasksWithPriority(List<AgencyPoint> agencyPoints) {
        for (int i = 0; i < agencyPoints.size(); i++) {
            AgencyPoint agencyPoint = agencyPoints.get(i);
            if (getTasksWithHighPriority(agencyPoint)) {
                agencyPointListHighPriority.add(agencyPoint);
            } else if (getTasksWithMediumPriority(agencyPoint)) {
                agencyPointListMediumPriority.add(agencyPoint);
            } else if (getTasksWithLowPriority(agencyPoint)) {
                agencyPointListLowPriority.add(agencyPoint);
            }
        }

        return Arrays.asList(agencyPointListHighPriority, agencyPointListMediumPriority, agencyPointListLowPriority);
    }

    //todo перепроверить по правильности условия
    private boolean getTasksWithHighPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getNumberOfDaysOfIssue() > 14) {
            return true;
        } else if (agencyPoint.getNumberOfDaysOfIssue() > 7 && agencyPoint.getNumberOfApproved() > 0) {
            return true;
        }
        return false;
    }

    private boolean getTasksWithMediumPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getNumberOfIssued() > 0 && (agencyPoint.getNumberOfIssued() / agencyPoint.getNumberOfApproved()) * 100 < 50) {
            return true;
        }
        return false;
    }

    private boolean getTasksWithLowPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getPointsConnected().equals("вчера") || !agencyPoint.isDelivered()) {
            return true;
        }
        return false;
    }

    public List<Integer> getAgencyPointListHighPriority() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < agencyPointListHighPriority.size(); i++) {
            list.add(agencyPointListHighPriority.get(i).getId());
        }
        return list;
    }

    public List<Integer> getAgencyPointListMediumPriority() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < agencyPointListMediumPriority.size(); i++) {
            list.add(agencyPointListMediumPriority.get(i).getId());
        }
        return list;
    }

    public List<Integer> getAgencyPointListLowPriority() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < agencyPointListLowPriority.size(); i++) {
            list.add(agencyPointListLowPriority.get(i).getId());
        }
        return list;
    }
}
