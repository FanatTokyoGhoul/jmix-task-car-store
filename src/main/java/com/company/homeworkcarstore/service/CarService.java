package com.company.homeworkcarstore.service;

import com.company.homeworkcarstore.entity.Car;
import com.company.homeworkcarstore.entity.CarStatus;
import com.company.homeworkcarstore.entity.EngineType;
import com.company.homeworkcarstore.entity.Manufacturer;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final DataManager dataManager;

    private final Notifications notifications;

    @Autowired
    public CarService(DataManager dataManager, Notifications notifications) {
        this.dataManager = dataManager;
        this.notifications = notifications;
    }

    public void calculateCars(Manufacturer manufacturer) {
        List<KeyValueEntity> result = dataManager.loadValues(
                        """
                                select c.model.engineType as engineType, count(c) as carCount \
                                from Car c where c.model.manufacturer = :manufacturer \
                                group by c.model.engineType"""
                )
                .parameter("manufacturer", manufacturer)
                .properties("engineType", "carCount")
                .list();

        Map<EngineType, Long> carsMap = result.stream()
                .collect(Collectors.toMap(
                        kv -> EngineType.fromId(kv.getValue("engineType").toString()),
                        kv -> (Long) kv.getValue("carCount")
                ));

        Long gasolineCount = carsMap.getOrDefault(EngineType.GASOLINE, 0L);
        Long electricCount = carsMap.getOrDefault(EngineType.ELECTRIC, 0L);

        notifications.create(String.join(
                "",
                "Electric cars: ",
                electricCount.toString(),
                ", gasoline cars: ",
                gasolineCount.toString()
        )).show();
    }

    public void markAsSold(Car car) {
        if(car.getStatus() == CarStatus.IN_STOCK) {
            car.setStatus(CarStatus.SOLD);
            car.setDateOfSale(LocalDate.now());
            dataManager.save(car);
            notifications.create("Done")
                    .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                    .withPosition(Notification.Position.TOP_END)
                    .show();

        } else  {
            notifications.create("Already Sold")
                    .withThemeVariant(NotificationVariant.LUMO_WARNING)
                    .withPosition(Notification.Position.TOP_END)
                    .show();
        }
    }
}
