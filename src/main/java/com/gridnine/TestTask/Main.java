package com.gridnine.TestTask;

import com.gridnine.TestTask.Rules.*;
import com.gridnine.TestTask.Entity.FlightBulder;
import com.gridnine.TestTask.Enum.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		RulesService rulesService = new RulesService();
		var flights = FlightBulder.createFlights();

		//Фильтр на валидные полеты (сегменты не летят "в прошлое", сегменты не пересекаются)
		Rules rulesValid = new IsValidRules();
		//фильтр на полеты от текущей даты
		Rules rulesFromNow = new FlightDateDepartureRules(LocalDateTime.now(), DateFiltering.AFTER);
        //Фильтр на время на земле
		Rules rulesLessThan2HoursOnGround = new TransferTimeRules(Duration.of(2, ChronoUnit.HOURS), DurationFiltering.LESS);

		//Выборка полетов по вышеупомянутым фильтрам
		var resultValid = rulesService.FilterFlights(flights, rulesValid);
		System.out.println("Валидные рейсы:");
		printListLineByLine(resultValid);

		var resultFromNow = rulesService.FilterFlights(flights, rulesFromNow);
		System.out.println("Рейсы от сегодняшнего числа:");
		printListLineByLine(resultFromNow);

		var resultLessThan2HoursOnGround = rulesService.FilterFlights(flights, rulesLessThan2HoursOnGround);
		System.out.println("Рейсы с пересадкой менее двух часов:");
		printListLineByLine(resultLessThan2HoursOnGround);
	}

	private static void printListLineByLine(List list) {
		for(var e : list) {
			System.out.println(e);
		}
	}

}
