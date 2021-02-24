package com.epam.bar.service;

import com.epam.bar.dao.CocktailDao;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class CocktailService {
    private final CocktailDao cocktailDao =  new CocktailDao();

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

    public List<Cocktail> findAll() throws ServiceException{
        List<Cocktail> cocktails;
        try {
            cocktails = cocktailDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return cocktails;
    }
}
