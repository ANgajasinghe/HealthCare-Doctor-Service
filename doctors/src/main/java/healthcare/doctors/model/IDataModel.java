package healthcare.doctors.model;

import dto.DoctorDTO;

public interface IDataModel {

	DoctorDTO getAllDoctors(String ALL);

	DoctorDTO getAllSpecifications();

	DoctorDTO SelectDocById(String id);

	DoctorDTO insertIntoDoctors(DoctorDTO doctorDTOs);

	DoctorDTO DeleteDoc(int docID);

	DoctorDTO UpdateDoc(DoctorDTO dto);

	DoctorDTO SelectDocId(String regNO);

}
