package com.epam.bar.service;

import com.epam.bar.dao.CocktailDao;
import com.epam.bar.dao.FieldType;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@Log4j2
public class CocktailService {
    private final CocktailDao cocktailDao = new CocktailDao();

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

    public List<Cocktail> findByField(String key, FieldType fieldType) throws ServiceException {
        List<Cocktail> cocktails;
        try {
            cocktails = cocktailDao.findByField(key, fieldType);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return cocktails;
    }

    public List<Cocktail> findAll() throws ServiceException {
        List<Cocktail> cocktails;
        try {
            cocktails = cocktailDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return cocktails;
    }

    public Optional<String> editCocktail(String id, String name, String composition) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        Optional<Cocktail> cocktailOptional = findCocktailById(id);
        if (cocktailOptional.isPresent()) {
            Cocktail cocktail = cocktailOptional.get();
            cocktail.setName(name);
            cocktail.setComposition(composition);
            try {
                cocktailDao.update(cocktail, id);
            } catch (DaoException e) {
                log.error(e);
                throw new ServiceException(e);
            }
        } else {
            serverMessage = Optional.of("");
        }
        return serverMessage;
    }

    public Optional<Cocktail> findCocktailById(String id) throws ServiceException {
        Optional<Cocktail> cocktail = Optional.empty();
        try {
            List<Cocktail> cocktails = cocktailDao.findByField(id, FieldType.ID);
            if (cocktails.size() > 0) {
                cocktail = Optional.of(cocktails.get(0));
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return cocktail;
    }

    public Optional<String> endorseCocktail(String id) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        try {
            if (!cocktailDao.endorseCocktail(id)) {
                serverMessage = Optional.of("");//todo
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }
}
