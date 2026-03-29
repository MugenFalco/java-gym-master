package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(singleTrainingSession, mondaySessions.get(0));

        //Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(mondayChildTrainingSession, mondaySessions.get(0));

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessionsAt13.size());
        Assertions.assertEquals(singleTrainingSession, sessionsAt13.get(0));

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(sessionsAt14.isEmpty());
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        // Добавляем 3 тренировки одного тренера
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем, что вернулась одна запись с 3 тренировками
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3, result.get(coach));
    }

    @Test
    void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Алексеевич");
        Coach coach3 = new Coach("Сидорова", "Мария", "Владимировна");

        // Тренер1 проводит 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        // Тренер2 проводит 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(11, 0)));

        // Тренер3 проводит 1 тренировку
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем количество записей
        Assertions.assertEquals(3, result.size());

        // Проверяем количество тренировок для каждого тренера
        Assertions.assertEquals(2, result.get(coach1));
        Assertions.assertEquals(3, result.get(coach2));
        Assertions.assertEquals(1, result.get(coach3));
    }

    @Test
    void testGetCountByCoachesSortedByDescending() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coachMost = new Coach("Иванов", "Иван", "Иванович");
        Coach coachMedium = new Coach("Петров", "Петр", "Петрович");
        Coach coachLeast = new Coach("Сидоров", "Сидор", "Сидорович");

        // coachMost проводит 5 тренировок (самый активный)
        for (int i = 0; i < 5; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coachMost,
                    DayOfWeek.values()[i % 7], new TimeOfDay(10, 0)));
        }

        // coachMedium проводит 3 тренировки
        for (int i = 0; i < 3; i++) {
            timetable.addNewTrainingSession(new TrainingSession(group, coachMedium,
                    DayOfWeek.values()[i % 7], new TimeOfDay(11, 0)));
        }

        // coachLeast проводит 1 тренировку
        timetable.addNewTrainingSession(new TrainingSession(group, coachLeast,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем, что результат отсортирован по убыванию количества тренировок
        List<Integer> counts = new ArrayList<>(result.values());
        Assertions.assertEquals(5, counts.get(0));
        Assertions.assertEquals(3, counts.get(1));
        Assertions.assertEquals(1, counts.get(2));

        // Проверяем порядок тренеров
        List<Coach> coaches = new ArrayList<>(result.keySet());
        Assertions.assertEquals(coachMost, coaches.get(0));
        Assertions.assertEquals(coachMedium, coaches.get(1));
        Assertions.assertEquals(coachLeast, coaches.get(2));
    }

    @Test
    void testGetCountByCoachesWithSameCount() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach1 = new Coach("Антонов", "Антон", "Антонович");
        Coach coach2 = new Coach("Борисов", "Борис", "Борисович");
        Coach coach3 = new Coach("Владимиров", "Владимир", "Владимирович");

        // Все тренеры проводят по 2 тренировки
        for (Coach coach : Arrays.asList(coach1, coach2, coach3)) {
            timetable.addNewTrainingSession(new TrainingSession(group, coach,
                    DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
            timetable.addNewTrainingSession(new TrainingSession(group, coach,
                    DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        }

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем количество записей
        Assertions.assertEquals(3, result.size());

        // Проверяем, что у всех одинаковое количество тренировок
        for (Integer count : result.values()) {
            Assertions.assertEquals(2, count);
        }

        // Проверяем, что все тренеры присутствуют
        Assertions.assertTrue(result.containsKey(coach1));
        Assertions.assertTrue(result.containsKey(coach2));
        Assertions.assertTrue(result.containsKey(coach3));
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем, что вернулась пустая карта
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetCountByCoachesWithSameCoachMultipleSessionsSameDayAndTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        // Добавляем несколько тренировок одного тренера в один день и время
        // В реальном расписании так может быть, если тренировки разных групп
        Group group1 = new Group("Акробатика для детей 7-9 лет", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для детей 10-12 лет", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        Map<Coach, Integer> result = timetable.getCountByCoaches();

        // Проверяем, что все тренировки посчитались корректно
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3, result.get(coach));
    }
}
