package com.bairontapia.projects.cuidamed.daotemplate;

import java.io.IOException;
import java.sql.SQLException;

public interface IReadAndWriteDAO<T, ID> extends IReadOnlyDAO<T, ID> {

  void save(T t) throws IOException, SQLException;

}
