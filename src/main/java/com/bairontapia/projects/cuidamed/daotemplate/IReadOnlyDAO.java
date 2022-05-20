package com.bairontapia.projects.cuidamed.daotemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface IReadOnlyDAO<T, ID> {

  Optional<T> find(ID id) throws IOException, SQLException;

  Collection<T> findAll() throws IOException, SQLException;

}
