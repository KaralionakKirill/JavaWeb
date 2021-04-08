package com.epam.bar.service;

import com.epam.bar.dao.CocktailDao;
import com.epam.bar.dao.CocktailField;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

/**
 * The class provides a business logic of application connected with {@link Cocktail}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class CocktailService {
    private final CocktailDao cocktailDao = new CocktailDao();

    /**
     * Add cocktail
     *
     * @param cocktail the cocktail
     * @return the optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Find cocktails by field
     *
     * @param key   the key
     * @param field the field
     * @return the list
     * @throws ServiceException the service exception
     */
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

    /**
     * Find cocktails
     *
     * @return the list
     * @throws ServiceException the service exception
     */
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

    /**
     * Edit cocktail
     *
     * @param id          the id
     * @param name        the name
     * @param composition the composition
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Find cocktail by id
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Endorse cocktail optional.
     *
     * @param id the id
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Delete cocktail optional.
     *
     * @param id the id
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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
