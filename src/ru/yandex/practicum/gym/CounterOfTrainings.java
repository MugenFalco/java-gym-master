package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private final Coach coach;
    private final int numberOfTrainings;

    public CounterOfTrainings(Coach coach, int numberOfTrainings) {
        this.coach = coach;
        this.numberOfTrainings = numberOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", numberOfTrainings=" + numberOfTrainings +
                '}';
    }
}

