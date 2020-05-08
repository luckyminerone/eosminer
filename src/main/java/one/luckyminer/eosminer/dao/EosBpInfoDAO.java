package one.luckyminer.eosminer.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import one.luckyminer.eosminer.Utils.C3p0Util;
import one.luckyminer.eosminer.model.EosBpInfo;

public class EosBpInfoDAO {
	public List<EosBpInfo> queryEosBpInfoList() {

		QueryRunner runner = new QueryRunner(C3p0Util.getDataSource());
		String sql = "select * FROM `t_eos_bplist` ";
		List<EosBpInfo> resList = null;
		try {
			resList = runner.query(sql, new BeanListHandler<EosBpInfo>(EosBpInfo.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resList;
	}
}