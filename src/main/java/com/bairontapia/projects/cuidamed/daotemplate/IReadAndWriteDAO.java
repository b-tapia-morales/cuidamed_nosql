package com.bairontapia.projects.cuidamed.daotemplate;

public interface IReadAndWriteDAO<T, ID> extends IReadOnlyDAO<T, ID> {

  void save(T t);

}
