package healthcare.doctors.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import dto.DoctorDTO;
import dto.ErrorDTO;
import utility.ConnectionBuilder;
import utility.Messages;

public class DoctorModel implements IDataModel {

	private final ConnectionBuilder cBuilder = new ConnectionBuilder();
	// private Connection MYSQLcon = cBuilder.MYSQLConnection();

	@Override
	public boolean connectionChecker(Connection MYSQLcon) {

		if (MYSQLcon == null) {
			return false;
		}
		return true;
	}

	@Override
	public DoctorDTO getAllDoctors(String ALL) {
		List<DoctorDTO> allDocList = new ArrayList<DoctorDTO>();
		DoctorDTO docObj = new DoctorDTO();
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		DoctorDTO Gdto = new DoctorDTO();
		if (this.connectionChecker(MYSQLcon)) {

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append("*\t");
			sBuilder.append("FROM\n");
			sBuilder.append("doctors");
			
			if (ALL !=null && ALL.equals("active")) {
				sBuilder.append(" \n WHERE doc_status_id = 1");
			}
			

			String qurtString = sBuilder.toString();

			try {
				Statement stmt = MYSQLcon.createStatement();
				ResultSet rs = stmt.executeQuery(qurtString);

				while (rs.next()) {
					DoctorDTO dto = new DoctorDTO();
					dto.setDoc_id(rs.getInt("doc_id"));
					dto.setDoc_reg_no(rs.getString("doc_reg_no"));
					dto.setDoc_first_name(rs.getString("doc_first_name"));
					dto.setDoc_last_name(rs.getString("doc_last_name"));
					dto.setDoc_address(rs.getString("doc_address"));
					dto.setDoc_city(rs.getString("doc_city"));
					dto.setDoc_tp1(rs.getString("doc_tp1"));
					dto.setDoc_tp2(rs.getString("doc_tp2"));
					dto.setDoc_tp3(rs.getString("doc_tp3"));
					dto.setDoc_email(rs.getString("doc_email"));
					dto.setDoc_status_id(rs.getInt("doc_status_id"));
					dto.setSpecification_id(rs.getInt("doc_specification_id"));
					allDocList.add(dto);
				}
				docObj.setResponse_status(1);
				docObj.setDoc_list(allDocList);
				
				return docObj;

			} catch (SQLException e) {
				
				docObj.setDoc_list(allDocList);
				ErrorDTO eDto = new ErrorDTO();
				eDto.setERROR_CODE(e.getErrorCode());
				eDto.setERROR_NAME(e.getMessage());
				
				docObj.setResponse_status(0);
				docObj.setError(eDto);
				return docObj;
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return null;
	}

	@Override
	public String insertIntoDoctors(DoctorDTO doctorDTOs) {
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		if (this.connectionChecker(MYSQLcon)) {

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("INSERT INTO doctors ( \n");
			sBuilder.append("doc_reg_no,");
			sBuilder.append("doc_first_name,");
			sBuilder.append("doc_last_name,");
			sBuilder.append("doc_address_no,");
			sBuilder.append("doc_address_lane1,");
			sBuilder.append("doc_address_lane2,");
			sBuilder.append("doc_address_lane3,");
			sBuilder.append("doc_city,");
			sBuilder.append("doc_tp1,");
			sBuilder.append("doc_tp2,");
			sBuilder.append("doc_tp3,");
			sBuilder.append("doc_email,");
			sBuilder.append("doc_status_id,");
			sBuilder.append("doc_specification_id)\n");
			sBuilder.append("VALUES (\n");
			sBuilder.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?\n");
			sBuilder.append(")");

			String queryString = sBuilder.toString();
			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
				pStatement.setString(1, doctorDTOs.getDoc_reg_no() != null ? doctorDTOs.getDoc_reg_no() : null);
				pStatement.setString(2, doctorDTOs.getDoc_first_name() != null ? doctorDTOs.getDoc_first_name() : null);
				pStatement.setString(3, doctorDTOs.getDoc_last_name() != null ? doctorDTOs.getDoc_last_name() : null);

				pStatement.setString(4, doctorDTOs.getDoc_address_no() != null ? doctorDTOs.getDoc_address_no() : null);
				pStatement.setString(5,
						doctorDTOs.getDoc_address_lane1() != null ? doctorDTOs.getDoc_address_lane1() : null);
				pStatement.setString(6,
						doctorDTOs.getDoc_address_lane2() != null ? doctorDTOs.getDoc_address_lane2() : null);
				pStatement.setString(7,
						doctorDTOs.getDoc_address_lane3() != null ? doctorDTOs.getDoc_address_lane3() : null);
				pStatement.setString(8, doctorDTOs.getDoc_city() != null ? doctorDTOs.getDoc_city() : null);

				pStatement.setString(9, doctorDTOs.getDoc_tp1() != null ? doctorDTOs.getDoc_tp1() : null);
				pStatement.setString(10, doctorDTOs.getDoc_tp2() != null ? doctorDTOs.getDoc_tp2() : null);
				pStatement.setString(11, doctorDTOs.getDoc_tp3() != null ? doctorDTOs.getDoc_tp3() : null);
				pStatement.setString(12, doctorDTOs.getDoc_email() != null ? doctorDTOs.getDoc_email() : null);

				pStatement.setInt(13, doctorDTOs.getDoc_status_id() != null ? doctorDTOs.getDoc_status_id() : 0);
				pStatement.setInt(14, doctorDTOs.getSpecification_id() != null ? doctorDTOs.getSpecification_id() : 0);

				boolean result = pStatement.execute();

				if (!result) {
					boolean val = this.insertIntoDocHospital(doctorDTOs.getHospital_list(), doctorDTOs.getDoc_reg_no());

					if (val) {
						return Messages.docAndHostSuccess;
					}
					return Messages.doctorSuccess;
				}
			} catch (SQLException e) {
				return e.getMessage();
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return Messages.connectionER;

	}

	@Override
	public boolean insertIntoDocHospital(String Hospitals, String RegNo) {

		boolean result = false;
		if (Hospitals == null || RegNo == null) {
			return false;
		} else {
			String docId = this.SelectDocId(RegNo);
			if (docId != null) {
				Connection MYSQLcon = cBuilder.MYSQLConnection();
				String[] hostArr = Hospitals.split(",");

				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("INSERT INTO doc_hospital ( \n");
				sBuilder.append("doc_id,\n");
				sBuilder.append("hostpital_id )\n");
				sBuilder.append("VALUES(?,?)");

				try {

					for (int i = 0; i < hostArr.length; i++) {
						String qurtString = sBuilder.toString();
						PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
						pStatement.setInt(1, Integer.parseInt(docId));
						pStatement.setInt(2, Integer.parseInt(hostArr[i]));
						result = pStatement.execute();
						if (result) {
							return false;
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				} finally {
					try {
						MYSQLcon.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!result) {
				return true;
			}
		}
		return result;

	}

	@Override
	public String SelectDocId(String regNO) {
		if (regNO != null) {
			Connection MYSQLcon = cBuilder.MYSQLConnection();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append("doc_id\n");
			sBuilder.append("FROM\n");
			sBuilder.append("doctors\n");
			sBuilder.append("WHERE doc_reg_no = ?");
			String qurtString = sBuilder.toString();

			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
				pStatement.setString(1, regNO.trim());
				ResultSet rs = pStatement.executeQuery();
				while (rs.next()) {
					return rs.getString("doc_id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String DocWorkAssign(DoctorDTO doctorDTO) {

		// Connection MYSQLcon = cBuilder.MYSQLConnection();
		// StringBuilder sBuilder = new StringBuilder();

		return null;
	}

	@Override
	public DoctorDTO SelectDocById(String id) {
		System.out.println(id);
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		DoctorDTO dto = new DoctorDTO();
		if (this.connectionChecker(MYSQLcon)) {

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append("*\t");
			sBuilder.append("FROM\n");
			sBuilder.append("doctors d\n");
			sBuilder.append("INNER JOIN doc_specification  s\n");
			sBuilder.append("ON d.doc_specification_id = s.specification_id\n");
			sBuilder.append("WHERE d.doc_id = ?");

			String qurtString = sBuilder.toString();

			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
				pStatement.setString(1, (id != null) ? id.trim() : null);
				ResultSet rs = pStatement.executeQuery();

				if (rs.next()) {

					dto.setDoc_id(rs.getInt("doc_id"));
					dto.setDoc_reg_no(rs.getString("doc_reg_no"));
					dto.setDoc_first_name(rs.getString("doc_first_name"));
					dto.setDoc_last_name(rs.getString("doc_last_name"));
					dto.setDoc_address_no(rs.getString("doc_address_no"));
					dto.setDoc_address_lane1(rs.getString("doc_address_lane1"));
					dto.setDoc_address_lane2(rs.getString("doc_address_lane2"));
					dto.setDoc_address_lane3(rs.getString("doc_address_lane3"));
					dto.setDoc_city(rs.getString("doc_city"));
					dto.setDoc_tp1(rs.getString("doc_tp1"));
					dto.setDoc_tp2(rs.getString("doc_tp2"));
					dto.setDoc_tp3(rs.getString("doc_tp3"));
					dto.setDoc_email(rs.getString("doc_email"));
					dto.setDoc_status_id(rs.getInt("doc_status_id"));
					dto.setSpecification_id(rs.getInt("doc_specification_id"));
					dto.setSpecification_name(rs.getString("specification_name"));
					dto.setHospilalIDList(this.GetDoctorHositalId(id));
					return dto;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return null;

	}

	private List<String> GetDoctorHositalId(String id) throws SQLException {

		List<String> list = new ArrayList<String>();
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT\n");
		sBuilder.append("hostpital_id \t");
		sBuilder.append("FROM\n");
		sBuilder.append("doc_hospital \n");
		sBuilder.append("WHERE doc_id = ?");
		String qurtString = sBuilder.toString();

		PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
		pStatement.setString(1, (id != null) ? id.trim() : null);
		ResultSet rs = pStatement.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getString("hostpital_id"));
			list.add(rs.getString("hostpital_id"));

		}
		MYSQLcon.close();
		return list;

	}

	@Override
	public String DeleteDocAll(int docID) {
		Connection MYSQLcon = cBuilder.MYSQLConnection();

		DoctorDTO currectDoctorDTO = this.SelectDocById(String.valueOf(docID));

		if (currectDoctorDTO != null) {

			String qurtString00 = "DELETE FROM doc_hospital WHERE doc_id = ? \n";
			String qurtString01 = "DELETE FROM doc_session WHERE doc_id = ? \n";
			String qurtString02 = "DELETE FROM doctors WHERE doc_id = ? \n";

		

			PreparedStatement pStatement;
			try {
				pStatement = MYSQLcon.prepareStatement(qurtString00);
				pStatement.setInt(1, docID);
				

				pStatement.execute();
				pStatement.close();

				pStatement = MYSQLcon.prepareStatement(qurtString01);
				pStatement.setInt(1, docID);
				pStatement.execute();

				pStatement = MYSQLcon.prepareStatement(qurtString02);
				pStatement.setInt(1, docID);
				pStatement.execute();

				return Messages.DeleteSuccess;
			} catch (SQLException e) {
			
				e.printStackTrace();
				return Messages.systemFail;
			}

		} else {
			return Messages.insertSessonErr+"Invalide Id";
		}

	}

	@Override
	public String UpdateDoc(String docID , DoctorDTO dto) {
		Connection MYSQLcon = cBuilder.MYSQLConnection();

		DoctorDTO currectDoctorDTO = this.SelectDocById(String.valueOf(docID));

		if (currectDoctorDTO != null) {
			DoctorDTO doctorDTOs = this.doctorUpdateDTOMapper(dto, currectDoctorDTO);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("UPDATE doctors SET \n");
			sBuilder.append("doc_reg_no = ?,");
			sBuilder.append("doc_first_name = ?,");
			sBuilder.append("doc_last_name = ?,");
			sBuilder.append("doc_address_no = ?,");
			sBuilder.append("doc_address_lane1 = ?,");
			sBuilder.append("doc_address_lane2 = ?,");
			sBuilder.append("doc_address_lane3 = ?,");
			sBuilder.append("doc_city = ?,");
			sBuilder.append("doc_tp1 = ?,");
			sBuilder.append("doc_tp2 = ?,");
			sBuilder.append("doc_tp3 = ?,");
			sBuilder.append("doc_email = ?,");
			sBuilder.append("doc_status_id = ?,");
			sBuilder.append("doc_specification_id = ?\n");
			sBuilder.append("WHERE doc_id = ? ");

			String queryString = sBuilder.toString();
			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
				pStatement.setString(1, doctorDTOs.getDoc_reg_no() != null ? doctorDTOs.getDoc_reg_no() : null);
				pStatement.setString(2, doctorDTOs.getDoc_first_name() != null ? doctorDTOs.getDoc_first_name() : null);
				pStatement.setString(3, doctorDTOs.getDoc_last_name() != null ? doctorDTOs.getDoc_last_name() : null);

				pStatement.setString(4, doctorDTOs.getDoc_address_no() != null ? doctorDTOs.getDoc_address_no() : null);
				pStatement.setString(5,
						doctorDTOs.getDoc_address_lane1() != null ? doctorDTOs.getDoc_address_lane1() : null);
				pStatement.setString(6,
						doctorDTOs.getDoc_address_lane2() != null ? doctorDTOs.getDoc_address_lane2() : null);
				pStatement.setString(7,
						doctorDTOs.getDoc_address_lane3() != null ? doctorDTOs.getDoc_address_lane3() : null);
				pStatement.setString(8, doctorDTOs.getDoc_city() != null ? doctorDTOs.getDoc_city() : null);

				pStatement.setString(9, doctorDTOs.getDoc_tp1() != null ? doctorDTOs.getDoc_tp1() : null);
				pStatement.setString(10, doctorDTOs.getDoc_tp2() != null ? doctorDTOs.getDoc_tp2() : null);
				pStatement.setString(11, doctorDTOs.getDoc_tp3() != null ? doctorDTOs.getDoc_tp3() : null);
				pStatement.setString(12, doctorDTOs.getDoc_email() != null ? doctorDTOs.getDoc_email() : null);

				pStatement.setInt(13, doctorDTOs.getDoc_status_id() != null ? doctorDTOs.getDoc_status_id() : 0);
				System.out.println("ID"+ doctorDTOs.getSpecification_id());
				pStatement.setInt(14, doctorDTOs.getSpecification_id() != null ? doctorDTOs.getSpecification_id() : 0);
				pStatement.setInt(15, Integer.valueOf(docID));
				pStatement.execute();
				pStatement.close();
				
				return Messages.updateSuccess;
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				return e.toString();
			}finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			}
			

		} else {
			return Messages.insertSessonErr+"Invalide Id";
		}
	}
	
	
	private DoctorDTO doctorUpdateDTOMapper(DoctorDTO dto , DoctorDTO currectDoctorDTO) {
		dto.setDoc_reg_no((dto.getDoc_reg_no() == null)?
				 currectDoctorDTO.getDoc_reg_no():dto.getDoc_reg_no());
		 dto.setDoc_first_name((dto.getDoc_first_name() == null)?
				 currectDoctorDTO.getDoc_first_name():dto.getDoc_first_name());
		 dto.setDoc_last_name((dto.getDoc_last_name() == null)?
				 currectDoctorDTO.getDoc_last_name():dto.getDoc_last_name());
		 dto.setDoc_address_no((dto.getDoc_address_no() == null)?
				 currectDoctorDTO.getDoc_address_no():dto.getDoc_address_no());	
		dto.setDoc_address_lane1((dto.getDoc_address_lane1() == null)?
				 currectDoctorDTO.getDoc_address_lane1():dto.getDoc_address_lane1());		
		dto.setDoc_address_lane2((dto.getDoc_address_lane2() == null)?
				 currectDoctorDTO.getDoc_address_lane2():dto.getDoc_address_lane2());
		dto.setDoc_address_lane3((dto.getDoc_address_lane3() == null)?
				 currectDoctorDTO.getDoc_address_lane3():dto.getDoc_address_lane3());
		dto.setDoc_city((dto.getDoc_city() == null)?
				 currectDoctorDTO.getDoc_city():dto.getDoc_city());
		dto.setDoc_tp1((dto.getDoc_tp1() == null)?
				 currectDoctorDTO.getDoc_tp1():dto.getDoc_tp1());
		dto.setDoc_tp2((dto.getDoc_tp2() == null)?
				 currectDoctorDTO.getDoc_tp2():dto.getDoc_tp2());
		dto.setDoc_tp3((dto.getDoc_tp3() == null)?
				 currectDoctorDTO.getDoc_tp3():dto.getDoc_tp3());
		dto.setDoc_email((dto.getDoc_email() == null)?
				 currectDoctorDTO.getDoc_email():dto.getDoc_email());
		dto.setDoc_status_id((dto.getDoc_status_id() == null)?
				 currectDoctorDTO.getDoc_status_id():dto.getDoc_status_id());
		dto.setSpecification_id((dto.getSpecification_id() == null)?
				 currectDoctorDTO.getSpecification_id():dto.getSpecification_id());
		
		return dto;
	}

}
