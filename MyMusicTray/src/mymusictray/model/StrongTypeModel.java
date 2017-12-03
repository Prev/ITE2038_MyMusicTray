package mymusictray.model;

import mymusictray.core.Context;
import mymusictray.exception.ModelMisuseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Common Model of Common Strong-Typed Entity.
 *  `insert()`, `update()`, `delete()` is implemented by PK `id` and method `getSubAttributes()`
 *
 * @author Prev (0soo.2@prev.kr)
 */
public abstract class StrongTypeModel implements Model {

	/**
	 * Primary key attribute
	 */
	public int id;

	/**
	 * Name of this entity's table
	 */
	public String tableName;


	/**
	 * Constructor of StrongTypeModel.
	 *   tableName is required to automate [inert & update & delete] queries
	 * @param tableName
	 */
	public StrongTypeModel(String tableName) {
		this.id = -1;
		this.tableName = tableName;
	}

	/**
	 * Get attribute name and value set that is not a key.
	 * @return (name-value) set of attributes
	 */
	abstract public Map<String, String> getSubAttributes();


	/**
	 * Insert with dataset called by `getSubAttributes`
	 *   and change instance's id to auto-incremented value
	 */
	@Override
	public void insert() {
		if (this.id != -1) {
			throw new ModelMisuseException(ModelMisuseException.INSERT_MISUSE);
		}
		try {
			Map<String, String> attrs = this.getSubAttributes();

			String sql = "INSERT INTO `" + this.tableName + "` (" +
					String.join(",", attrs.keySet()) +
					") values('" +
					String.join("','", attrs.values()) +
					"');";

			PreparedStatement stmt = Context.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			this.id = rs.getInt(1); // Auto-incremented value

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update with dataset called by `getSubAttributes` and where condition by key `id`
	 */
	@Override
	public void update() {
		if (this.id == -1) {
			throw new ModelMisuseException(ModelMisuseException.UPDATE_MISUSE);
		}
		try {
			Map<String, String> attrs = this.getSubAttributes();
			List<String> attrUpdateQueries = new ArrayList<>();

			for (Map.Entry<String, String> entry: attrs.entrySet())
				attrUpdateQueries.add(entry.getKey() + " = '" + entry.getValue() + "'");

			String sql = "UPDATE `" + this.tableName + "` SET " +
					String.join(", ", attrUpdateQueries) +
					"WHERE id = '" + this.id + "'";


			PreparedStatement stmt = Context.getConnection().prepareStatement(sql);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove model by key `id`
	 */
	@Override
	public void remove() {
		if (this.id == -1) {
			throw new ModelMisuseException(ModelMisuseException.REMOVE_MISUSE);
		}
		try {
			PreparedStatement stmt = Context.getConnection().prepareStatement(
					"DELETE FROM `" + this.tableName + "` WHERE `id` = ?"
			);
			stmt.setInt(1, id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
