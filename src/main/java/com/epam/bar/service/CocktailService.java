package com.epam.bar.service;

import com.epam.bar.dao.impl.CocktailDaoImpl;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class CocktailService {
    private final CocktailDaoImpl cocktailDao =  new CocktailDaoImpl();

    public CocktailService() {
    }

    public Optional<String> addCocktail(Cocktail cocktail) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        try {
            cocktailDao.add(cocktail);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }
}
