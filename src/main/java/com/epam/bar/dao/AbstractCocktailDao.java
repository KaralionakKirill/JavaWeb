package com.epam.bar.dao;

import com.epam.bar.dao.field.CocktailField;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;

import java.util.List;

public abstract class AbstractCocktailDao extends BaseDao<String, Cocktail>{

    public abstract List<Cocktail> findByField(String key, CocktailField field) throws DaoException;

    public abstract boolean endorseCocktail(String id) throws DaoException;
}
