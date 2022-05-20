package com.bairontapia.projects.cuidamed.daotemplate;

import java.io.IOException;
import java.sql.SQLException;

public interface ICrudDAO<T, ID> extends IReadAndWriteDAO<T, ID> {

  void update(T t) throws IOException, SQLException;

}
