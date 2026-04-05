package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek(); //сохраняем занятие в расписании
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

        if (daySchedule != null) {
            if (!daySchedule.containsKey(time)) {
                daySchedule.put(time, new ArrayList<>());
            }
            daySchedule.get(time).add(trainingSession);
        }
    }
    
    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<TrainingSession> result = new ArrayList<>();
        if (daySchedule != null) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                result.addAll(sessions);
            }
        }
            return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule != null && daySchedule.containsKey(timeOfDay)) {
            return new ArrayList<>(daySchedule.get(timeOfDay));
        } else {
            return new ArrayList<>();
        }
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            if (daySchedule != null) {
                for (List<TrainingSession> sessionsAtTime : daySchedule.values()) {
                    if (sessionsAtTime != null) {
                        for (TrainingSession session : sessionsAtTime) {
                            if (session != null && session.getCoach() != null) {
                                Coach coach = session.getCoach();
                                coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                            }
                        }
                    }
                }
            }
        }

        List<CounterOfTrainings> statistics = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            statistics.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        statistics.sort((a, b) -> Integer.compare(b.getNumberOfTrainings(), a.getNumberOfTrainings()));

        return statistics;
    }
}

