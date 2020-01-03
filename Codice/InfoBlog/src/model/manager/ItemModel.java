package model.manager;

import java.sql.SQLException;
import java.util.Collection;

public interface ItemModel<T, S>
{
	public T doRetrieveByKey(S item_value) throws SQLException;
	public Collection<T> doRetrieveAll(String order) throws SQLException;
	public void doSave(T item) throws SQLException;
	public void doUpdate(T item) throws SQLException;
	public boolean doDelete(T item) throws SQLException;
}
