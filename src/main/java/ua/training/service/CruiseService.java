package ua.training.service;

import ua.training.dao.CruiseDao;
import ua.training.entity.Cruise;
import ua.training.exception.CruiseNotFoundException;

import java.util.List;

public class CruiseService {
    private final CruiseDao cruiseDao;

    public CruiseService(CruiseDao cruiseDao) {
        this.cruiseDao = cruiseDao;
    }

    public List<Cruise> getAllCruises() {
        return cruiseDao.findAll();
    }


    public Cruise findById(long id) throws CruiseNotFoundException {
        return cruiseDao.findById(id)
                .orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id: " + id));
    }

    public boolean updateCruise(Cruise cruise) {
        return cruiseDao.update(cruise);
    }

    public boolean checkAmountPassenger(Cruise cruise) {
        return cruise.getShip().getCurrentPassengerAmount() < cruise.getShip().getMaxPassengerAmount();
    }
}
