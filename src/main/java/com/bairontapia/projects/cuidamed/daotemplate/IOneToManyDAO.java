package com.bairontapia.projects.cuidamed.daotemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface IOneToManyDAO<T, ID> extends IReadOnlyDAO<T, ID> {

  Collection<T> findAll(ID id) throws IOException, SQLException;

}
