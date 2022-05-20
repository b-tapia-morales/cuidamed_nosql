package com.bairontapia.projects.cuidamed.daotemplate;

import java.util.Collection;

public interface IOneToManyDAO<T, ID> extends IReadOnlyDAO<T, ID> {

  Collection<T> findAll(ID id);

}
