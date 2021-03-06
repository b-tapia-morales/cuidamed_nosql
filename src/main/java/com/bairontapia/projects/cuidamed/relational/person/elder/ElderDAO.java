package com.bairontapia.projects.cuidamed.relational.person.elder;

import com.bairontapia.projects.cuidamed.connection.ConnectionSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.relational.CrudDAO;
import com.bairontapia.projects.cuidamed.utils.paths.DirectoryPathUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.io.IOUtils;

public class ElderDAO implements CrudDAO<Elder, String> {

  private static final ElderDAO INSTANCE = new ElderDAO();

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  private static final String RELATIVE_PATH_STRING =
      DirectoryPathUtils.pathBuilder("scripts", "class_queries", "person", "elder");

  private static final String FIND_BY_RESPONSIBLE_PATH =
      RELATIVE_PATH_STRING + "get_by_responsible.sql";
  private static final String FIND_ALL_QUERY_PATH = RELATIVE_PATH_STRING + "get_all.sql";
  private static final String FIND_QUERY_PATH = RELATIVE_PATH_STRING + "get.sql";
  private static final String SAVE_QUERY_PATH = RELATIVE_PATH_STRING + "save.sql";
  private static final String UPDATE_QUERY_PATH = RELATIVE_PATH_STRING + "update.sql";

  public static ElderDAO getInstance() {
    return INSTANCE;
  }

  public Optional<Elder> findByResponsibleId(String rut) throws IOException, SQLException {
    final var inputStream = CLASS_LOADER.getResourceAsStream(FIND_BY_RESPONSIBLE_PATH);
    final var query =
        IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
    final var connection = ConnectionSingleton.getInstance();
    final var statement = connection.prepareStatement(query);
    setKeyParameter(statement, rut);
    final var resultSet = statement.executeQuery();
    final var optional =
        resultSet.next() ? Optional.of(readTuple(resultSet)) : Optional.<Elder>empty();
    resultSet.close();
    statement.close();
    return optional;
  }

  @Override
  public String findQuery() throws IOException {
    final var inputStream = CLASS_LOADER.getResourceAsStream(FIND_QUERY_PATH);
    return IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
  }

  @Override
  public String findAllQuery() throws IOException {
    final var inputStream = CLASS_LOADER.getResourceAsStream(FIND_ALL_QUERY_PATH);
    return IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
  }

  @Override
  public String saveQuery() throws IOException {
    final var inputStream = CLASS_LOADER.getResourceAsStream(SAVE_QUERY_PATH);
    return IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
  }

  @Override
  public String updateQuery() throws IOException {
    final var inputStream = CLASS_LOADER.getResourceAsStream(UPDATE_QUERY_PATH);
    return IOUtils.toString(Objects.requireNonNull(inputStream), Charset.defaultCharset());
  }

  @Override
  public void setKeyParameter(PreparedStatement statement, String rut) throws SQLException {
    statement.setString(1, rut);
  }

  @Override
  public void saveTuple(final PreparedStatement statement, Elder elder) throws SQLException {
    statement.setString(1, elder.rut());
    statement.setString(2, elder.firstName());
    statement.setString(3, elder.lastName());
    statement.setString(4, elder.secondLastName());
    statement.setDate(5, Date.valueOf(elder.birthDate()));
    statement.setShort(6, (short) elder.gender().getIndex());
    statement.setString(7, elder.rut());
    statement.setBoolean(8, elder.isActive());
    statement.setDate(9, Date.valueOf(elder.admissionDate()));
    statement.setString(10, elder.responsibleRut());
    statement.executeUpdate();
  }

  @Override
  public void updateTuple(final PreparedStatement statement, Elder elder) throws SQLException {
    statement.setString(1, elder.firstName());
    statement.setString(2, elder.lastName());
    statement.setString(3, elder.secondLastName());
    statement.setDate(4, Date.valueOf(elder.birthDate()));
    statement.setShort(5, (short) elder.gender().getIndex());
    statement.setString(6, elder.rut());
    statement.setBoolean(7, elder.isActive());
    statement.setDate(8, Date.valueOf(elder.admissionDate()));
    statement.setString(9, elder.rut());
    statement.executeUpdate();
  }

  @Override
  public Elder readTuple(final ResultSet resultSet) throws SQLException {
    final var rut = resultSet.getString(1);
    final var firstName = resultSet.getString(2);
    final var lastName = resultSet.getString(3);
    final var secondLastName = resultSet.getString(4);
    final var birthDate = resultSet.getDate(5);
    final var genderCode = resultSet.getShort(6);
    final var isActive = resultSet.getBoolean(7);
    final var admissionDate = resultSet.getDate(8);
    final var responsibleRut = resultSet.getString(9);
    return Elder.createInstance(
        rut,
        firstName,
        lastName,
        secondLastName,
        birthDate,
        genderCode,
        isActive,
        admissionDate,
        responsibleRut);
  }
}
