package ua.training.service;

import ua.training.exception.CruiseNotFoundException;
import ua.training.dao.CruiseDao;
import ua.training.dao.DaoFactory;
import ua.training.model.entity.Cruise;

import java.util.List;

public class CruiseService {
    private final CruiseDao cruiseDao;

    public CruiseService() {
        this.cruiseDao = DaoFactory.getInstance().createCruiseDao();
    }

    public List<Cruise> getAllCruises() {
        try {
            return cruiseDao.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public Cruise findById(long id) throws CruiseNotFoundException {
        return cruiseDao.findById(id)
                .orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id: ", id));
    }

    public boolean updateCruise(Cruise cruise) {
        return cruiseDao.update(cruise);
    }
}
