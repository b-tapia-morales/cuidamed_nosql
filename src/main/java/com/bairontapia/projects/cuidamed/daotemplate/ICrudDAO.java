package com.bairontapia.projects.cuidamed.daotemplate;

public interface ICrudDAO<T, ID> extends IReadAndWriteDAO<T, ID> {

  void update(T t);

}
