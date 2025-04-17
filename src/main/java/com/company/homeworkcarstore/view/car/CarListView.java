package com.company.homeworkcarstore.view.car;

import com.company.homeworkcarstore.entity.Car;
import com.company.homeworkcarstore.service.CarService;
import com.company.homeworkcarstore.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "cars", layout = MainView.class)
@ViewController(id = "Car.list")
@ViewDescriptor(path = "car-list-view.xml")
@LookupComponent("carsDataGrid")
@DialogMode(width = "64em")
public class CarListView extends StandardListView<Car> {

    @Autowired
    private CarService carService;
    @ViewComponent
    private DataGrid<Car> carsDataGrid;

    @Subscribe(id = "markSoldButton", subject = "clickListener")
    public void onMarkSoldButtonClick(final ClickEvent<JmixButton> event) {
        Car car = carsDataGrid.getSingleSelectedItem();
        if (car != null) {
            carService.markAsSold(car);
        }
    }
}