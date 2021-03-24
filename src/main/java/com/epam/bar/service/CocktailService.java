package com.epam.bar.service;

import com.epam.bar.dao.CocktailDao;
import com.epam.bar.dao.field.CocktailField;
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
            if (!cocktailDao.add(cocktail)) {
                serverMessage = Optional.of("serverMessage.addCocktailException");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public List<Cocktail> findByField(String key, CocktailField field) throws ServiceException {
        List<Cocktail> cocktails;
        try {
            cocktails = cocktailDao.findByField(key, field);
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
                if (!cocktailDao.update(cocktail, id)) {
                    serverMessage = Optional.of("serverMessage.editCocktailException");
                }
            } catch (DaoException e) {
                log.error(e);
                throw new ServiceException(e);
            }
        } else {
            serverMessage = Optional.of("serverMessage.editCocktailException");
        }
        return serverMessage;
    }

    public Optional<Cocktail> findCocktailById(String id) throws ServiceException {
        Optional<Cocktail> cocktail = Optional.empty();
        try {
            List<Cocktail> cocktails = cocktailDao.findByField(id, CocktailField.ID);
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
                serverMessage = Optional.of("serverMessage.endorseCocktailException");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public Optional<String> deleteCocktail(String id) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        try {
            if (!cocktailDao.remove(id)) {
                serverMessage = Optional.of("serverMessage.deleteCocktailException");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }
}
