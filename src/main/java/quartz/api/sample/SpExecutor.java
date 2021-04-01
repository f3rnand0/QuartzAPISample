package quartz.api.sample;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class SpExecutor {

	public void executeSp(Connection connection, int timeout) throws SQLException,
			ClassNotFoundException {
//		Connection connection = getConnection(
//				"jdbc:sybase:Tds:172.19.2.28:7026/sample", "sa", "password");
		CallableStatement callableStatement = null;
		long t1 = System.currentTimeMillis();
		boolean execute = false;
		int returnValue = 0;

		try {
			
/*
 * 
 *  
 * @s_srv            varchar(30)  = "CTSDESA",
   @s_ssn_branch     int          = null,
   @s_ssn            int          = 3487,
   @s_date           datetime     = null,
   @s_user           login        = "admuser",
   @s_term           descripcion  = null,
   @s_ofi            smallint     = 30030,
   @t_rty            char(1)      = null,
   @t_trn            int          = 1900,
   @t_debug          char(1)      = 'N',
   @t_file           varchar(14)  = null,
   @t_from           varchar(30)  = null,
   @t_ssn_corr       int          = null,

   @i_num_doc        varchar(30)  = "79576932",       -- Titular de la cuenta Numero Identificacion
   @i_tipo_doc       catalogo     = "CC",       -- Titular de la cuenta tipo de documento
   @i_num_cta        cuenta ="400702177438",                    -- Numero de cuenta
   @i_tipo_cta       smallint     = 4,          -- Codigo tipo cuenta
   @i_monto          money        = 7,
   @i_monto_comision money        = 0,
   @i_cod_trans      int          = 0,          -- codigo transaccion externo
   @i_causal_trans   varchar(10)  = "139",       -- causal transaccion
   @i_causal_com     varchar(10)  = "",       -- causal comision

   -- ContextoTransaccional
   @i_trn            varchar(255) = "666555666",       -- identificadorTrans1accional
   @i_canal_orig     varchar(255) = "66",       -- codCanalOriginador
   @i_proceso_orig   varchar(255) = "99",       -- codProcesoOriginador
   @i_cod_func_orig  varchar(255) = "88",       -- codFuncionalidadOriginador
   @i_consumidor     varchar(255) = "77",       -- ipConsumidor
   @i_bpin           varchar(32)  = "",       -- Bpin
   @i_rubro          varchar(32)  = ""        -- Rubro

 */

			String statement = "{?=call cob_soa..sp_sr_notdebcred (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(statement);
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.setString(2, "CTSPROD");
			callableStatement.setInt(3,0);
			callableStatement.setInt(4, 34871562);
			callableStatement.setDate(5,
					new java.sql.Date(new Date().getTime()));
			callableStatement.setString(6, "admuser");
			callableStatement.setString(7, "TERM-ICB");
			callableStatement.setInt(8, 30030);
			callableStatement.setString(9, null);
			callableStatement.setInt(10, 1900);
			callableStatement.setString(11, "N");
			callableStatement.setString(12, null);
			callableStatement.setString(13, null);
			callableStatement.setInt(14, 0);
			
			/*
			callableStatement.setInt(5, 61);
			callableStatement.setString(7, "CTSDESA26");
			callableStatement.setInt(9, 22292);
			callableStatement.setString(10, "U");
			*/
			
			callableStatement.setString(15, "79576932");
			callableStatement.setString(16, "CC");
			callableStatement.setString(17, "400702177438");
			callableStatement.setInt(18, 4);
			callableStatement.setDouble(19, 7.0);
			callableStatement.setDouble(20, 0.0);
			callableStatement.setInt(21, 0);
			callableStatement.setString(22, "139");
			callableStatement.setString(23, "");
			callableStatement.setString(24, "666555666");
			callableStatement.setString(25, "66");
			callableStatement.setString(26, "99");
			callableStatement.setString(27, "88");
			callableStatement.setString(28, "77");
			callableStatement.setString(29, "");
			callableStatement.setString(30, "");
			
			callableStatement.setQueryTimeout(timeout);
			
			execute = callableStatement.execute();
			returnValue = callableStatement.getInt(1);
/*
 * cob_soa..sp_sr_notdebcred 
 * @i_num_doc='79576932',
 * @i_tipo_doc='CC',
 * @i_num_cta='400702177438',
 * @i_tipo_cta=4,
 * @i_monto=7.0,
 * @i_cod_trans=0,
 * @i_causal_trans='139',
 * @i_causal_com='',
 * @i_trn='666555666',
 * @i_canal_orig='66',
 * @i_proceso_orig='99',
 * @i_cod_func_orig='88',
 * @i_consumidor='77',
 * @i_bpin='',
 * @i_rubro='',
 * @t_trn=1900,
 * @i_tipo_nota='D',
 * @t_timeout=1,
 * @s_srv='CTSPROD',
 * @s_user='admuser',
 * @s_term='TERM-ICB',
 * @s_ofi=30030,
 * @s_rol=61,
 * @s_ssn=34872012,
 * @s_lsrv='CTSDESA26',
 * @s_date='09/19/2017',
 * @s_sesn=22292,
 * @s_org='U'  
 */
			ResultSet rs = callableStatement.getResultSet();
			while (callableStatement.getMoreResults()) {
				rs.getString("1");
				System.out.println("dd");
			}

		} catch (Exception e) {
			System.out.println("[SPExecutor]");
			Writer result = new StringWriter();
		    final PrintWriter printWriter = new PrintWriter(result);
		    e.printStackTrace(printWriter);
		    System.out.println(result.toString());
		} finally {
			long t2 = System.currentTimeMillis();
			System.out.println("result: " + execute);
			System.out.println("return: " + returnValue);
			System.out.println("total : " + (t2 - t1));
			if (callableStatement != null) {
				callableStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

	}

	private static Connection getConnection(String url, String usuario,
			String pwd) throws SQLException, ClassNotFoundException {
		Class.forName("com.sybase.jdbc3.jdbc.SybConnectionPoolDataSource");
		return DriverManager.getConnection(url, usuario, pwd);
	}

}
