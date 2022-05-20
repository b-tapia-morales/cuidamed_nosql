package com.bairontapia.projects.cuidamed.daotemplate;

import java.util.Collection;
import java.util.Optional;

public interface IReadOnlyDAO<T, ID> {

  Optional<T> find(ID id);

  Collection<T> findAll();

}
