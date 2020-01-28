package ua.training.model.service;

import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.implement.ConnectionPoolHolder;
import ua.training.model.entity.Cruise;

import java.util.List;

public class CruiseService {
    private final CruiseDao cruiseDao;

    public CruiseService()
    {
        this.cruiseDao = DaoFactory.getInstance().createCruiseDao(ConnectionPoolHolder.pool());
    }

    public List<Cruise> getAllCruises(){
        try{
            return cruiseDao.findAll();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
